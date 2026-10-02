'use strict';
/**
 * 局域网 CoAP 深度探测（真机验证用）
 *
 * 比 coap-scan.js 多做三件事：
 *   1) 额外 bind 一个监听在 13078 的 socket —— 覆盖"设备回包打到固定端口而非源端口"的情况
 *   2) 遍历 payload 变体（scanType 0/1/2、带不带 productId、Getkey）
 *   3) 同时打 广播(255.255.255.255) / 子网广播(192.168.1.255) / 单播(插座IP)
 *
 * 用法：
 *   COAP_TARGET=192.168.1.23 node tools/coap-probe.js
 */
const dgram = require('dgram');
const crypto = require('crypto');

const PORT = +(process.env.COAP_PORT || 13078);
const IP = process.env.COAP_TARGET || '192.168.1.23';
const SECS = +(process.env.COAP_SECS || 24);

function build(path, payloadObj, withPayload) {
  const token = crypto.randomBytes(4);
  const mid = crypto.randomBytes(2);
  const header = Buffer.from([0x44, 0x01, mid[0], mid[1]]);
  const opt = Buffer.concat([Buffer.from([0xB0 | path.length]), Buffer.from(path, 'ascii')]);
  const head = Buffer.concat([header, token, opt]);
  if (!withPayload) return head;
  return Buffer.concat([head, Buffer.from([0xFF]), Buffer.from(JSON.stringify(payloadObj), 'utf8')]);
}

const variants = [
  { name: 'Scan {"scanType":"0"}', path: 'Scan', obj: { scanType: '0' }, wp: true },
  { name: 'Scan {"scanType":"1","productId":"381785"}', path: 'Scan', obj: { scanType: '1', productId: '381785' }, wp: true },
  { name: 'Scan {"scanType":"2","productId":"381785"}', path: 'Scan', obj: { scanType: '2', productId: '381785' }, wp: true },
  { name: 'Scan {"scanType":"0","mac":"B4E62D3A6E7C"}', path: 'Scan', obj: { scanType: '0', mac: 'B4E62D3A6E7C' }, wp: true },
  { name: 'Scan (无 payload)', path: 'Scan', obj: null, wp: false },
  { name: 'Getkey {"Query":"1"}', path: 'Getkey', obj: { Query: '1' }, wp: true },
];

let hits = 0;
function onMsg(tag) {
  return (msg, rinfo) => {
    hits++;
    console.log(`\n★★★ [${tag}] 收到响应 ${rinfo.address}:${rinfo.port}  ${msg.length} 字节`);
    console.log('  hex :', msg.toString('hex'));
    console.log('  text:', JSON.stringify(msg.toString('utf8')));
  };
}

const sockA = dgram.createSocket({ type: 'udp4', reuseAddr: true });
sockA.on('error', (e) => console.error('[A] error:', e.message));
sockA.on('message', onMsg('A/临时端口'));

let sockB = null;
try {
  sockB = dgram.createSocket({ type: 'udp4', reuseAddr: true });
  sockB.on('error', (e) => console.error('[B] error:', e.message));
  sockB.on('message', onMsg('B/13078'));
} catch (e) { console.error('创建 13078 监听失败:', e.message); }

sockA.bind(() => {
  sockA.setBroadcast(true);
  console.log(`socket A 已就绪（临时端口），目标端口 ${PORT}`);
  if (sockB) {
    sockB.bind(PORT, () => {
      sockB.setBroadcast(true);
      console.log(`socket B 已就绪：监听 ${PORT}`);
    });
  }
});

const targets = ['255.255.255.255', '192.168.1.255', IP];
console.log(`目标：${targets.join(', ')}   持续 ${SECS}s\n`);

let idx = 0;
const timer = setInterval(() => {
  if (idx >= variants.length) return;
  const v = variants[idx++];
  const pkt = build(v.path, v.obj, v.wp);
  for (const t of targets) {
    sockA.send(pkt, PORT, t, (e) => { if (e) console.error(`send → ${t} 失败:`, e.message); });
  }
  console.log(`[${idx}/${variants.length}] 已发 ${v.name}  → ${targets.length} 个目标  (${pkt.length}B)`);
}, 1200);

setTimeout(() => {
  clearInterval(timer);
  console.log(`\n===== 结束：共收到 ${hits} 个响应 =====`);
  try { sockA.close(); } catch (e) {}
  try { if (sockB) sockB.close(); } catch (e) {}
  process.exit(hits ? 0 : 3);
}, SECS * 1000);
