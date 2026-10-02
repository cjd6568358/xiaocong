'use strict';
/**
 * 小葱智能插座 · SoftAP 配网客户端（自写，不依赖厂商 App / libsoftAp.so）
 *
 * 协议依据：docs/03-配网与native.md §5
 *   UDP <bcast>:5658
 *     → {"type":"ch_pubk","pubkey":"<b64 64B 公钥>"}                 明文 JSON
 *     ← {"type":"ch_pubk","pubkey":"<b64 64B 公钥>"}                 明文 JSON
 *     → base64(AES-128-CBC(ch_data))   key = ECDH 共享密钥低 16B, iv = "abcdefghijklmnop"
 *     ← base64(AES-128-CBC({"mac":..,"product_id":..}))
 *     → base64(AES-128-CBC({"type":"end"}))
 *
 * 用法：
 *   node tools/provision.js --ssid="家里WiFi" --pass="密码" \
 *        [--domain=your.server.com] [--crt-file=server/certs/ca.crt] \
 *        [--client=<clientId>] [--check=123456] \
 *        [--host=192.168.4.255,192.168.4.1] [--port=5658]
 *
 *   本地自测（配合 tools/fake-plug-softap.js）：
 *   node tools/provision.js --ssid=x --pass=y --host=127.0.0.1
 *
 * 前置：
 *   1. 插座长按 5 秒进入配网模式，开热点 smart-<pid>-<mac6>-<ck>
 *   2. 本机连上该热点（Windows: netsh wlan connect ...，见 tools/wifi.ps1）
 *   3. 运行本脚本；Windows 首次会弹防火墙授权，务必允许
 *
 * 真机保险（相对官方实现多做三件事，都是纯兜底、不影响正常流程）：
 *   a) 同时向「子网广播地址」和「AP 自身地址」发包 —— 少数 AP 不把广播回送给自己
 *   b) ch_data 发出后若 N 秒收不到设备回包，仍补发 {"type":"end"}
 *      （逆向文档对 end 与设备回包的先后描述有歧义，两条路都走通）
 *   c) 打印本机在该网段的 IPv4，确认确实拿到了 192.168.4.x
 *
 * 退出码：
 *   0 = 设备接受并回了 mac/product_id（配网成功）
 *   2 = 超时未收到任何回包（可能没连上热点 / 设备没收到包）
 *   3 = 设备重发 ch_pubk 明确 NACK（收到了 ch_data 但拒绝 —— 真机在 crt 非空时就是这种）
 */
const dgram = require('dgram');
const crypto = require('crypto');
const fs = require('fs');
const os = require('os');

// ---------------- 参数 ----------------
function arg(k, d) {
  const hit = process.argv.find((a) => a.startsWith('--' + k + '='));
  return hit ? hit.slice(k.length + 3) : d;
}
const SSID = arg('ssid', process.env.XC_SSID || '');
const PASS = arg('pass', process.env.XC_PASS || '');
const DOMAIN = arg('domain', process.env.XC_DOMAIN || '');
let CRT = arg('crt', process.env.XC_CRT || '');
const CRT_FILE = arg('crt-file', '');
if (!CRT && CRT_FILE) CRT = fs.readFileSync(CRT_FILE, 'utf8');
const CLIENT_ID = arg('client', crypto.randomBytes(16).toString('hex'));
const CHECK = arg('check', String(Math.floor(100000 + Math.random() * 900000)));
// 多目标：默认「子网广播 + AP 自身地址」双发
const HOSTS = arg('host', process.env.XC_HOST || '192.168.4.255,192.168.4.1')
  .split(',').map((s) => s.trim()).filter(Boolean);
const PORT = +(arg('port', '5658'));
const TIMEOUT_MS = +(arg('timeout', '60000'));
const END_FALLBACK_MS = +(arg('end-fallback', '3000'));
const IV = Buffer.from('abcdefghijklmnop');

if (!SSID) {
  console.error('缺少 --ssid=（家里 WiFi 名称）；也可用环境变量 XC_SSID');
  process.exit(1);
}

const log = (...a) => console.log('[provision]', ...a);

// ---------------- 1. secp256k1 密钥对 ----------------
const ecdh = crypto.createECDH('secp256k1');
ecdh.generateKeys();
const myPub64 = ecdh.getPublicKey().slice(1); // 去掉 0x04 前缀 → 64B X||Y
const myPubB64 = myPub64.toString('base64');

let aesKey = null;
let step = 0;          // 0 = 等设备 ch_pubk, 1 = 已发 ch_data, 2 = 完成
let endSent = false;
let pubkTimer = null;
let endTimer = null;

// 设备端公钥（用于判断它重发的 ch_pubk 是不是同一把密钥）
let devPubKey = null;
let nackCount = 0;
let cdataRetry = 0;
const MAX_CDATA_RETRY = +(arg('cdata-retry', '2'));

// ---------------- 加解密 ----------------
function enc(obj) {
  const c = crypto.createCipheriv('aes-128-cbc', aesKey, IV);
  return Buffer.concat([c.update(JSON.stringify(obj), 'utf8'), c.final()]).toString('base64');
}
function dec(b64) {
  const d = crypto.createDecipheriv('aes-128-cbc', aesKey, IV);
  return JSON.parse(Buffer.concat([d.update(Buffer.from(b64, 'base64')), d.final()]).toString('utf8'));
}
/** 64B 裸公钥补 0x04 前缀，供 OpenSSL 使用 */
function toPoint(b64) {
  const raw = Buffer.from(b64, 'base64');
  return raw.length === 64 ? Buffer.concat([Buffer.from([4]), raw]) : raw;
}
function sendEnd(why) {
  if (endSent || !aesKey) return;
  endSent = true;
  log(`→ 发送 {"type":"end"}（${why}）`);
  send(Buffer.from(enc({ type: 'end' })));
}
function sendChData(why) {
  const payload = {
    type: 'ch_data', ssid: SSID, password: PASS,
    clientId: CLIENT_ID, checkCode: CHECK, domain: DOMAIN, crt: CRT,
  };
  log(`→ 发送 ch_data${why ? `（${why}）` : ''}：ssid="${SSID}" domain="${DOMAIN || '(空→固件默认)'}" crt=${CRT.length} 字节 checkCode=${CHECK}`);
  send(Buffer.from(enc(payload)));
}

// ---------------- socket ----------------
const sock = dgram.createSocket({ type: 'udp4', reuseAddr: true });
sock.on('error', (e) => { console.error('[provision] socket error:', e.message); process.exit(1); });
const send = (buf) => {
  for (const h of HOSTS) {
    sock.send(buf, 0, buf.length, PORT, h, (e) => { if (e) console.error(`[provision] send error → ${h}:`, e.message); });
  }
};

sock.on('message', (msg, rinfo) => {
  const text = msg.toString('utf8').trim();

  if (step === 0) {
    let obj = null;
    try { obj = JSON.parse(text); } catch (e) { /* 非 JSON */ }
    if (obj && obj.type === 'ch_pubk' && obj.pubkey) {
      log(`← 收到设备 ch_pubk（来自 ${rinfo.address}:${rinfo.port}）  devPub=${obj.pubkey.slice(0, 24)}...`);
      devPubKey = obj.pubkey;
      const shared = ecdh.computeSecret(toPoint(obj.pubkey));   // 32B
      aesKey = shared.slice(0, 16);
      log(`  ECDH 完成，AES key = ${aesKey.toString('hex')}`);
      clearInterval(pubkTimer);
      step = 1;
      sendChData('');
      // 兜底：官方时序里 end 可能不等设备回包就发；等不到回包也要把 end 补上
      endTimer = setTimeout(() => {
        if (step === 1) { log(`  ${END_FALLBACK_MS}ms 内未收到设备回包，按官方时序先补发 end`); sendEnd('超时兜底'); }
      }, END_FALLBACK_MS);
      return;
    }
    if (obj) log(`← 忽略报文：${text.slice(0, 100)}`);
    return;
  }

  if (step === 1) {
    // 设备重发明文 ch_pubk = 明确 NACK：它没接受我们刚发的 ch_data。
    // 真机实测：crt 非空时就是这样（重发同一把公钥），而 crt 为空时会回加密的 mac/product_id。
    if (text.charAt(0) === '{') {
      let o = null;
      try { o = JSON.parse(text); } catch (e) { /* ignore */ }
      if (o && o.type === 'ch_pubk') {
        nackCount++;
        const same = o.pubkey === devPubKey;
        log(`⚠️ 设备重发 ch_pubk（${same ? '同一把密钥 → 未重启，只是拒绝了 ch_data' : '新密钥 → 设备已重启'}）  [NACK #${nackCount}]`);
        if (cdataRetry < MAX_CDATA_RETRY) {
          cdataRetry++;
          log(`  ${600}ms 后重发 ch_data（第 ${cdataRetry}/${MAX_CDATA_RETRY} 次重试，排除 UDP 丢包）`);
          setTimeout(() => { if (step === 1) sendChData(`NACK 重试 ${cdataRetry}/${MAX_CDATA_RETRY}`); }, 600);
        } else {
          log(`  ❌ 重试 ${MAX_CDATA_RETRY} 次仍被拒 → 设备是主动拒绝这个 crt/domain，不是丢包`);
        }
        return;
      }
    }
    try {
      const r = dec(text);
      log('← 设备回包：', JSON.stringify(r));
      if (r.mac && r.product_id) {
        log(`★ 配网成功：mac=${r.mac}  product_id=${r.product_id}`);
        clearTimeout(endTimer);
        sendEnd('收到设备回包');
        step = 2;
        setTimeout(() => { sock.close(); process.exit(0); }, 800);
      }
    } catch (e) {
      log('回包解密失败（可能是无关报文）：', text.slice(0, 80));
    }
  }
});

sock.bind(() => {
  sock.setBroadcast(true);
  const local = [];
  const nis = os.networkInterfaces();
  for (const k of Object.keys(nis)) {
    for (const ni of nis[k] || []) {
      if (ni.family === 'IPv4' && !ni.internal) local.push(`${ni.address}(${k})`);
    }
  }
  log(`已就绪：目标 ${HOSTS.join(', ')}:${PORT}`);
  log(`  本机 IPv4：${local.join('  ') || '(无)'}`);
  if (!local.some((s) => s.startsWith('192.168.4.'))) {
    log('  ⚠️ 本机不在 192.168.4.0/24 —— 可能没连上插座热点，发包会失败');
  }
  log(`  checkCode = ${CHECK}`);
  log(`  本地公钥(64B b64) = ${myPubB64}`);
  const beat = () => { log('→ 广播 ch_pubk'); send(Buffer.from(JSON.stringify({ type: 'ch_pubk', pubkey: myPubB64 }))); };
  beat();
  pubkTimer = setInterval(beat, 4500);   // 与官方一致：每 4.5s 重播
});

setTimeout(() => {
  if (step < 2) {
    if (nackCount > 0) {
      log(`超时 ${TIMEOUT_MS}ms。设备共 ${nackCount} 次重发 ch_pubk → 主动拒绝本次配置。`);
      sock.close(); process.exit(3);          // 3 = 设备明确 NACK（不是超时/丢包）
    }
    log(`超时 ${TIMEOUT_MS}ms，未完成配网`);
    sock.close(); process.exit(2);
  }
}, TIMEOUT_MS);
