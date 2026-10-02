'use strict';
/**
 * AXML（二进制 AndroidManifest.xml）外科式补丁工具
 *
 * 用途：把 <application android:name="..."> 从乐固壳入口
 *       (com.tencent.StubShell.TxAppEntry) 改成真正的 Application
 *       (com.ixiaocong.smarthome.phone.android.XcApplication)
 *
 * 原理：AXML 里属性的值存的是「字符串池索引」，不是字符串本身。
 *       所以只要把属性指向的索引从 274 改成 138 即可 —— 4 字节。
 *       字符串池完全不用动，也就不需要 apktool 重编译。
 *
 * 用法：
 *   node tools/patch_axml.js <in-manifest> <out-manifest>
 */
const fs = require('fs');

// ---------- AXML 解析 ----------
function parseStrings(b) {
  const off = 8;
  const strCount = b.readUInt32LE(off + 8);
  const flags = b.readUInt32LE(off + 16);
  const stringsStart = b.readUInt32LE(off + 20);
  const poolBase = off + stringsStart;
  const utf8 = !!(flags & 0x100);
  const rLen = (p) => { let l = b[p++]; if (l & 0x80) { l = ((l & 0x7f) << 8) | b[p++]; } return [l, p]; };
  const strs = [];
  for (let i = 0; i < strCount; i++) {
    const so = b.readUInt32LE(off + 28 + i * 4);
    let p = poolBase + so, s;
    if (utf8) { let a; [a, p] = rLen(p); let c; [c, p] = rLen(p); s = b.slice(p, p + c).toString('utf8'); }
    else { let a; [a, p] = rLen(p); s = b.slice(p, p + a * 2).toString('utf16le'); }
    strs.push(s);
  }
  return strs;
}

/** 遍历 chunk，找到 START_TAG == element 的那个 chunk 及其属性表位置 */
function findStartTag(b, element) {
  const total = b.readUInt32LE(4);
  const strings = parseStrings(b);
  let p = 8;
  while (p + 8 <= total) {
    const type = b.readUInt16LE(p);
    const size = b.readUInt32LE(p + 4);
    if (size <= 0) break;

    // ResXMLTree_node 头 = type(2) headerSize(2) size(4) lineNumber(4) comment(4) = 16
    // 其后是 ResXMLTree_attrExt: ns(4) name(4) attributeStart(2) attributeSize(2)
    //                            attributeCount(2) idIndex(2) classIndex(2) styleIndex(2)
    if (type === 0x0102 && p + 16 + 20 <= b.length) {
      const nameIdx = b.readUInt32LE(p + 20);
      const attributeStart = b.readUInt16LE(p + 24);
      const attributeSize = b.readUInt16LE(p + 26);
      const attributeCount = b.readUInt16LE(p + 28);
      const attrBase = p + 16 + attributeStart; // 相对 attrExt 起点

      if (strings[nameIdx] === element) {
        const attrs = [];
        for (let i = 0; i < attributeCount; i++) {
          const ap = attrBase + i * attributeSize; // ResXMLTree_attribute = 20 字节
          attrs.push({
            offset: ap,
            ns: b.readUInt32LE(ap),
            name: b.readUInt32LE(ap + 4),
            rawValue: b.readUInt32LE(ap + 8),
            dataType: b[ap + 15],
            data: b.readUInt32LE(ap + 16),
          });
        }
        return { chunkOffset: p, attrs, strings };
      }
    }
    p += size;
  }
  return null;
}

// ---------- 主流程 ----------
const [inFile, outFile] = process.argv.slice(2);
const argv = process.argv.slice(2);
const ORIG = process.env.ORIG_CLASS || 'com.tencent.StubShell.TxAppEntry';
const NEW = process.env.NEW_CLASS || 'com.ixiaocong.smarthome.phone.android.XcApplication';

const b = fs.readFileSync(inFile);
const tag = findStartTag(b, 'application');
if (!tag) { console.error('找不到 <application> 标签'); process.exit(1); }

const origIdx = tag.strings.indexOf(ORIG);
const newIdx = tag.strings.indexOf(NEW);
if (origIdx < 0) { console.error('字符串池里没有 ' + ORIG); process.exit(1); }
if (newIdx < 0) { console.error('字符串池里没有 ' + NEW); process.exit(1); }

console.log(`字符串池: ${ORIG} -> index ${origIdx}`);
console.log(`字符串池: ${NEW} -> index ${newIdx}`);

// 找属性：name 指向 "name"，且 ns 指向 android 命名空间
let patched = 0;
for (const a of tag.attrs) {
  const nameStr = tag.strings[a.name];
  const nsStr = a.ns ? tag.strings[a.ns] : '';
  const valStr = a.rawValue ? tag.strings[a.rawValue] : '';
  const isAndroid = /schemas\.android\.com\/apk\/res\/android/.test(nsStr);
  console.log(`  属性 name=${JSON.stringify(nameStr)} ns=${JSON.stringify(nsStr.slice(-20))} rawValue=${JSON.stringify(valStr)} (idx ${a.rawValue})`);

  if (isAndroid && nameStr === 'name' && a.rawValue === origIdx) {
    b.writeUInt32LE(newIdx, a.offset + 8);   // rawValue
    if (a.dataType === 0x03) b.writeUInt32LE(newIdx, a.offset + 16); // typedValue.data
    patched++;
    console.log(`  >>> 已打补丁：offset ${a.offset}，${origIdx} -> ${newIdx}`);
  }
}

if (patched === 0) { console.error('没有找到需要修改的 android:name 属性'); process.exit(1); }

fs.writeFileSync(outFile, b);
console.log(`\n完成，已写出 ${outFile}（改了 ${patched} 处）`);
