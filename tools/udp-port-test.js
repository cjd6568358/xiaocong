'use strict';
/**
 * 判定远端 UDP 端口是「开放」还是「关闭」
 *
 * 原理（Windows）：把 UDP socket connect() 到目标后发包，
 * 若目标端口关闭 → 对方回 ICMP port unreachable → 本机下一次收发报 ECONNRESET(10054)。
 * 若端口开放且应用不理会 → 无任何错误。
 *
 * 用法：IP=192.168.1.23 PORTS=13078,13079,9999,12345 node tools/udp-port-test.js
 */
const dgram = require('dgram');

const IP = process.env.IP || '192.168.1.23';
const PORTS = (process.env.PORTS || '13078,13079,9999,12345').split(',').map((s) => +s.trim());
const WAIT = +(process.env.WAIT || 2500);

function probe(port) {
  return new Promise((resolve) => {
    const s = dgram.createSocket('udp4');
    let done = false;
    const finish = (r) => { if (!done) { done = true; try { s.close(); } catch (e) {} resolve(r); } };

    s.on('error', (e) => finish(`ECONNRESET/错误 → ${e.code || e.message}（判为【关闭】）`));
    s.on('message', (m, ri) => finish(`★ 收到 ${m.length}B 回复自 ${ri.address}:${ri.port}（【开放且响应】）`));

    s.connect(port, IP, () => {
      try { s.send(Buffer.from('probe-1')); } catch (e) { finish('SEND_ERR ' + e.message); }
      setTimeout(() => { try { s.send(Buffer.from('probe-2')); } catch (e) {} }, 400);
      setTimeout(() => finish('无错误、无回复 → 判为【开放但不响应】（或 ICMP 被抑制）'), WAIT);
    });
  });
}

(async () => {
  console.log(`目标 ${IP}，逐个探测 ${PORTS.join(', ')}（每端口最多等 ${WAIT}ms）\n`);
  for (const p of PORTS) {
    const r = await probe(p);
    console.log(`  UDP ${String(p).padStart(5)}  ${r}`);
  }
  console.log('\n对照说明：若"确定关闭"的端口(9999/12345)也报"无错误"，则本机 ICMP 被抑制，结论不可用。');
  process.exit(0);
})();
