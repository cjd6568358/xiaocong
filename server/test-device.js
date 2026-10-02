'use strict';
/**
 * 模拟插座（用于在没有真机时验证整条链路）
 *   node server/test-device.js [deviceId]
 *
 * 它做的事和真设备一样：
 *   1. 连 MQTT broker（明文 tcp，调试用）
 *   2. 订阅 control / manage
 *   3. 收到 control 指令后「执行」并回 snapshot
 */
const net = require('net');

const HOST = process.env.MQTT_HOST || '127.0.0.1';
const PORT = +(process.env.MQTT_PORT || 1883);
const DEVICE_ID = process.argv[2] || 'sim-device-0001';
const CLIENT_ID = 'sim-' + DEVICE_ID;

let snapshot = { '1': 0 };
const sock = net.connect(PORT, HOST, () => console.log(`[sim] connected to ${HOST}:${PORT} as ${DEVICE_ID}`));

// ---- 极简 MQTT 编码 ----
const encLen = (n) => { const o = []; do { let b = n % 128; n = Math.floor(n / 128); if (n > 0) b |= 0x80; o.push(b); } while (n > 0); return Buffer.from(o); };
const str = (s) => { const b = Buffer.from(s); const h = Buffer.alloc(2); h.writeUInt16BE(b.length, 0); return Buffer.concat([h, b]); };
const publish = (topic, obj) => {
  const body = Buffer.concat([str(topic), Buffer.from(JSON.stringify(obj))]);
  sock.write(Buffer.concat([Buffer.from([0x30]), encLen(body.length), body]));
};

sock.on('connect', () => {
  // CONNECT
  const vh = Buffer.concat([str('MQTT'), Buffer.from([4, 0x02, 0, 60])]);
  const payload = str(CLIENT_ID);
  const body = Buffer.concat([vh, payload]);
  sock.write(Buffer.concat([Buffer.from([0x10]), encLen(body.length), body]));

  // SUBSCRIBE control
  const sub = Buffer.concat([Buffer.from([0, 1]), str('control'), Buffer.from([0])]);
  sock.write(Buffer.concat([Buffer.from([0x82]), encLen(sub.length), sub]));

  // 上线通知
  setTimeout(() => publish('online', { senderId: DEVICE_ID, protocolVersion: '1.0.0', status: 1 }), 300);
  // 上报初始快照
  setTimeout(() => publish('snapshot', {
    code: 0, messageId: 1, networkType: 1, protocolVersion: '1.0.0',
    receiveId: '', senderId: DEVICE_ID, status: 1, snapshot,
  }), 600);
});

sock.on('data', (buf) => {
  // 只关心 PUBLISH：找 "control" 主题
  const s = buf.toString('latin1');
  if (s.includes('control')) {
    const jsonStart = s.indexOf('{');
    if (jsonStart < 0) return;
    let obj;
    try { obj = JSON.parse(s.slice(jsonStart)); } catch (e) { return; }
    const cmd = obj.command || {};
    for (const k of Object.keys(cmd)) snapshot[k] = cmd[k];
    console.log(`[sim] 收到控制 -> command=${JSON.stringify(cmd)}  现在 snapshot=${JSON.stringify(snapshot)}`);
    setTimeout(() => publish('snapshot', {
      code: 0, messageId: obj.messageId, networkType: 1, protocolVersion: '1.0.0',
      receiveId: obj.senderId, senderId: DEVICE_ID, status: 1, snapshot,
    }), 200);
  }
});

sock.on('error', (e) => console.error('[sim] error', e.message));
