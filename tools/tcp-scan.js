'use strict';
/**
 * 轻量 TCP 端口扫描（ESP8266 设备常开 80/8080/8266/23 等）
 * 用法：IP=192.168.1.23 node tools/tcp-scan.js
 */
const net = require('net');
const IP = process.env.IP || '192.168.1.23';
const PORTS = (process.env.PORTS ||
  '21,22,23,53,80,81,443,554,1883,1884,4000,5000,5683,6668,7000,8000,8080,8081,8266,8883,8888,9000,9999,13078,49152'
).split(',').map((s) => +s.trim());
const TIMEOUT = +(process.env.TIMEOUT || 700);
const CONC = +(process.env.CONC || 24);

function probe(port) {
  return new Promise((resolve) => {
    const s = new net.Socket();
    let done = false;
    const finish = (r) => { if (!done) { done = true; s.destroy(); resolve(r); } };
    s.setTimeout(TIMEOUT);
    s.on('connect', () => finish({ port, state: 'OPEN' }));
    s.on('timeout', () => finish({ port, state: 'filtered' }));
    s.on('error', (e) => finish({ port, state: e.code === 'ECONNREFUSED' ? 'closed' : e.code }));
    s.connect(port, IP);
  });
}

(async () => {
  console.log(`TCP 扫描 ${IP}，${PORTS.length} 个端口，超时 ${TIMEOUT}ms\n`);
  const open = [];
  let i = 0;
  async function worker() {
    while (i < PORTS.length) {
      const p = PORTS[i++];
      const r = await probe(p);
      if (r.state === 'OPEN') { open.push(r.port); console.log(`  ★ ${r.port} OPEN`); }
    }
  }
  await Promise.all(Array.from({ length: CONC }, worker));
  console.log(`\n===== 开放的 TCP 端口：${open.length ? open.join(', ') : '无'} =====`);
  process.exit(open.length ? 0 : 3);
})();
