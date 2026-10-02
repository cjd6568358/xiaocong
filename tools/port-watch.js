'use strict';
/**
 * 端口监听网：在多个候选端口上监听 TCP，记录任何连入的连接。
 * 用途：设备被改成 domain=<本机> 后，看它到底连的是哪个端口、发了什么。
 *
 *   node tools/port-watch.js
 *   PORTS=80,443,8443,8888 node tools/port-watch.js
 *   SECS=300 node tools/port-watch.js
 */
const net = require('net');

const PORTS = (process.env.PORTS ||
  '80,443,8443,8883,8888,8889,1883,1884,8884,8080,8081,8090,9000,5000,7000,8000,9092,5683'
).split(',').map((s) => +s.trim());
const SECS = +(process.env.SECS || 300);

const hits = [];

function watch(port) {
  const srv = net.createServer((sock) => {
    const from = `${sock.remoteAddress}:${sock.remotePort}`;
    const ts = new Date().toISOString().slice(11, 19);
    console.log(`\n★★★ [${ts}] 端口 ${port} 收到连接 ← ${from}`);
    hits.push({ port, from, ts });
    let buf = Buffer.alloc(0);
    sock.on('data', (d) => {
      buf = Buffer.concat([buf, d]);
      if (buf.length <= 512) {
        console.log(`    收到 ${d.length}B: hex=${d.slice(0, 64).toString('hex')}`);
        const txt = d.slice(0, 96).toString('utf8').replace(/[^\x20-\x7e]/g, '.');
        if (/[a-zA-Z]{3}/.test(txt)) console.log(`    ascii="${txt}"`);
      }
    });
    sock.on('error', () => {});
    sock.on('close', () => console.log(`    连接关闭 ${from}（共收 ${buf.length}B）`));
    setTimeout(() => { try { sock.destroy(); } catch (e) {} }, 30000);
  });
  srv.on('error', (e) => console.log(`  端口 ${port} 监听失败：${e.code}（${e.code === 'EADDRINUSE' ? '已被占用，跳过' : e.message}）`));
  srv.listen(port, '0.0.0.0', () => console.log(`  ✅ 监听 ${port}`));
  return srv;
}

console.log(`端口监听网启动，持续 ${SECS}s`);
PORTS.forEach(watch);

setTimeout(() => {
  console.log(`\n===== 结束：${hits.length} 个连接 =====`);
  for (const h of hits) console.log(`  :${h.port} ← ${h.from}  (${h.ts})`);
  process.exit(hits.length ? 0 : 3);
}, SECS * 1000);
