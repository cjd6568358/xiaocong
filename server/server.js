'use strict';
/**
 * 小葱智能网关 —— 自建服务端（零依赖纯 Node）
 *
 *   HTTP  :  模拟 https://gw.ixiaocong.com/ 的 REST API
 *   MQTT  :  内嵌 broker（TLS），设备与 App 都连这里
 *
 * 用法：
 *   node server/server.js                       # 明文 HTTP + 明文 MQTT（调试）
 *   node server/server.js --tls                 # HTTPS + MQTT over TLS（需要证书，见 gen-certs.sh）
 *
 * 端点契约见 docs/01-HTTP-API与签名.md
 * MQTT 协议见 docs/02-MQTT协议.md
 */
const http = require('http');
const https = require('https');
const fs = require('fs');
const path = require('path');
const { URLSearchParams } = require('url');
const { sign } = require('./sign');
const S = require('./store');
const { Broker } = require('./mqtt-broker');

// ---------------- 配置 ----------------
const CFG = {
  APP_ID: '27e33ff8a0634cc4b6f696c9872a2d1b',
  APP_KEY: '3wruosO36ZzBXVM8',
  HTTP_PORT: +(process.env.HTTP_PORT || 8080),
  TCP_PORT: +(process.env.TCP_PORT || 1883),
  TLS_PORT: +(process.env.TLS_PORT || 8883),
  // 下发给 App 的 MQTT 地址（client/config 的 liveServerUrl）
  LIVE_SERVER: process.env.LIVE_SERVER || '127.0.0.1:8883',
  USE_TLS: process.argv.includes('--tls'),
  CERTS: path.join(__dirname, 'certs'),
};

// ---------------- MQTT broker ----------------
let tlsOptions = null;
if (CFG.USE_TLS) {
  try {
    tlsOptions = {
      key: fs.readFileSync(path.join(CFG.CERTS, 'server.key')),
      cert: fs.readFileSync(path.join(CFG.CERTS, 'server.crt')),
    };
  } catch (e) {
    console.error('缺少证书，请先运行 server/gen-certs.sh 生成到 server/certs/');
    process.exit(1);
  }
}

const broker = new Broker({
  tcpPort: CFG.TCP_PORT,
  tlsPort: CFG.USE_TLS ? CFG.TLS_PORT : 0,
  tlsOptions,
});

// 设备上行消息 → 更新设备状态
broker.on('message', (m) => {
  try {
    const json = JSON.parse(m.payload.toString('utf8'));
    const senderId = json.senderId;
    if (!senderId) return;
    if (m.topic === 'snapshot') {
      const d = S.upsertDevice({ deviceId: senderId, snapshot: json.snapshot || {}, status: json.status == null ? 1 : json.status });
      console.log(`[mqtt] snapshot  ${senderId} -> ${JSON.stringify(d.snapshot)}`);
    } else if (m.topic === 'online') {
      S.upsertDevice({ deviceId: senderId, status: 1 });
      console.log(`[mqtt] online    ${senderId}`);
    } else if (m.topic === 'offline') {
      const d = S.state.devices.get(senderId);
      if (d) d.status = 0;
      console.log(`[mqtt] offline   ${senderId}`);
    }
  } catch (e) { /* 非 JSON 或无关消息 */ }
});

broker.on('connect', (c) => console.log(`[mqtt] connect   clientId=${c.clientId} user=${c.username} (${c.kind})`));

// ---------------- HTTP 工具 ----------------
function envelope(code, msg = '', data = '') {
  return JSON.stringify({ code, msg, data, success: code === 0 });
}
function readBody(req) {
  return new Promise((resolve) => {
    let b = '';
    req.on('data', (c) => { b += c; });
    req.on('end', () => resolve(b));
  });
}
function parseForm(body) { return Object.fromEntries(new URLSearchParams(body)); }

function ok(res, data) { res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' }); res.end(data); }
function fail(res, code, msg) { ok(res, envelope(code, msg)); }

// ---------------- 端点实现 ----------------
// 每个 handler(params, ctx) -> 对象（会被 JSON.stringify 放进 data 字段）
const handlers = {
  // ---- 引导 ----
  'client/config': (p) => {
    const uid = p.uid || 'guest';
    const { clientId, clientKey } = S.newClient(uid);
    return {
      clientId, clientKey,
      liveServerUrl: CFG.LIVE_SERVER,   // ★ 关键：把 MQTT 指向我们
      timestamp: Date.now(),
      uid,
      upgrade: '0',
    };
  },

  // ---- 认证 ----
  'sms/send': () => ({ code: '123456' }),  // 任意验证码都接受，见 user/login
  'user/login': (p) => {
    const phone = p.phone || 'unknown';
    const { token, uid } = S.newToken(phone);
    return { token, uid, phone, nickname: 'user_' + uid.slice(-4), portrait: '', openId: '' };
  },
  'user/info': (p, ctx) => ({ user: { uid: ctx.uid || 'guest', phone: ctx.phone || '', nickname: 'user', portrait: '' } }),
  'user/logout': () => ({}),
  'user/config': () => ({ notification: 1 }),
  'user/update/config': () => ({}),

  // ---- 设备 ----
  'index': () => {
    const devices = [...S.state.devices.values()].map(S.deviceForApp);
    return { devices, scenes: [], homes: [], groups: [], home: null, greetings: '', weatherHome: null };
  },
  'device/list': () => [...S.state.devices.values()].map(S.deviceForApp),
  'device/list/gw': () => ({ devices: [] }),
  'device/list/gw/child': () => ({ devices: [] }),

  'device/detail': (p) => {
    const d = S.state.devices.get(p.deviceId || '');
    if (!d) return { device: {} };
    const a = S.deviceForApp(d);
    return { device: { ...a, update: 0, isGw: 0 } };
  },
  'device/detail/mini': (p) => handlers['device/detail'](p),
  'device/v2/detail': (p) => {
    const d = S.state.devices.get(p.deviceId || '') || { deviceId: p.deviceId };
    return { deviceInfo: S.deviceForApp(d), rnInfo: { upgrade: '0', version: '0.0.0', downloadUrl: '' } };
  },
  'device/update': (p) => {
    const d = S.state.devices.get(p.deviceId || '');
    if (d && p.deviceName) d.deviceName = p.deviceName;
    return {};
  },
  'device/getName': (p) => ({ deviceName: (S.state.devices.get(p.deviceId) || {}).deviceName || '' }),
  'device/snapshots': () => ({ list: [], totalItem: 0 }),
  'device/top': () => ({}),
  'device/top/cancel': () => ({}),
  'device/unbind': (p) => { S.state.devices.delete(p.deviceId); return {}; },

  // 配网相关 ★
  'device/getDiscovered': (p) => {
    // 真实实现里设备连上 WiFi 后会向云端注册；这里按 checkCode 查
    return S.state.discovered.get(p.checkCode || '') || { ret: 'Notfound' };
  },
  'device/getBindInfo': (p) => {
    const d = S.state.devices.get(p.deviceId || '');
    return {
      deviceId: p.deviceId, isBind: d ? 1 : 0, isAdmin: 1,
      deviceName: d ? d.deviceName : '插座',
      productId: d ? d.productId : '1', productName: '智能插座', productImg: '', phone: '',
    };
  },
  'device/bind': (p) => {
    const id = p.deviceId || '';
    if (id) S.upsertDevice({ deviceId: id, deviceMac: p.deviceMac || '', discoverWay: p.discoverWay || 'api' });
    console.log(`[bind] ${id} via ${p.discoverWay}`);
    return {};
  },
  'device/bind/log': () => ({}),
  'device/subscribe/all': () => {
    // 真实 broker 会在这里给客户端绑定订阅。我们的 broker 由客户端自己 SUBSCRIBE，
    // 所以这里只需返回成功。（App 会认为订阅已生效）
    return {};
  },

  // 固件 OTA（先返回"无更新"，避免 App 去下载不存在的包）
  'device/sdk/check': () => ({ upgrade: '0', upgradeMsg: '', sdkId: '' }),
  'device/sdk/detail': () => ({}),
  'device/sdk/upgrade': () => ({}),

  // 参数 ★ 设备详情页靠它渲染开关
  'parameter/list': (p) => {
    const d = S.state.devices.get(p.deviceId || '');
    const snap = (d && d.snapshot) || {};
    return [{ key: '1', name: '开关', type: 'bool', value: String(snap['1'] || 0), productParameterId: '1' }];
  },
  'parameter/modifiable/list': () => [],
  'parameter/rename': () => ({}),

  // ---- 产品目录 ★ 配网方式也在这里定 ----
  'product/category': () => ({ list: [{ categoryId: '1', categoryCode: 'socket', categoryName: '插座' }] }),
  'product/list': () => ({ products: [{ productId: '1', productName: '智能插座', productImage: '', isGw: 0 }] }),
  'product/help': () => ({
    help: {
      productId: '1',
      // 可选：'softap1.0' | 'xconfig' | 'easylink'
      // 建议先用 easylink（ESP-Touch）或 xconfig，视固件支持情况
      netconfigCode: process.env.NETCONFIG || 'softap1.0',
      xConfigKey: '',
      image: '', intro: '长按开关键 5 秒进入配网', faq: '',
    },
  }),

  // ---- 家庭 / 分组 / 天气 ----
  'home/list': () => [],
  'home/listDetail': () => ({}),
  'home/detail': () => ({}),
  'home/device/list': () => [...S.state.devices.values()].map(S.deviceForApp),
  'group/list': () => [],
  'district/list': () => [],
  'weather/today/outdoors': () => ({ text: '晴', temp: '25', humidity: '50', pm25: '10', airValue: '10', airText: '优', icon: '', windDir: '东', windLevel: '1' }),
  'electricity/count': () => ({ count: 0 }),
  'electricity/list': () => ({ list: [], totalItem: 0 }),
  'room/air/v2': () => ({}),

  // ---- 场景 / 自动化（存根） ----
  'scene/start': () => ({}),
  'scene/list': () => ({}),
  'ifttt/list': () => ({ sceneList: [] }),
  'ifttt/log': () => ({ list: [], totalItem: 0 }),
  'relation/device/parameter/list': () => [],
  'share/users': () => ({ list: [] }),
  'share/device/users': () => [],
  'share/devices/shareable': () => ({ list: [] }),
  'share/devices/sharedToMine': () => ({ list: [] }),
};

// ---------------- 服务器 ----------------
async function onRequest(req, res) {
  const url = new URL(req.url, 'http://localhost');
  const p = url.pathname.replace(/^\/+|\/+$/g, '');

  if (req.method === 'GET' && (p === '' || p === 'health')) {
    return ok(res, JSON.stringify({ ok: true, devices: [...S.state.devices.keys()] }));
  }
  if (req.method !== 'POST') return fail(res, 405, 'method not allowed');

  const form = parseForm(await readBody(req));
  // 身份（不校验签名——已死厂商，没有需要保护的资产；需要时再打开）
  const token = form['xc-token'] || '';
  const u = S.state.users.get(token) || {};

  const h = handlers[p];
  if (!h) {
    console.log(`[http] ${p}  (未实现，返回空)`);
    return ok(res, envelope(0, '', ''));
  }
  const data = h(form, { uid: u.uid, phone: u.phone, token });
  console.log(`[http] ${p}`);
  return ok(res, envelope(0, '', JSON.stringify(data)));
}

const server = CFG.USE_TLS
  ? https.createServer({ key: tlsOptions.key, cert: tlsOptions.cert }, onRequest)
  : http.createServer(onRequest);

server.listen(CFG.HTTP_PORT, () => {
  console.log('======================================================');
  console.log('  小葱智能 自建服务端');
  console.log(`  HTTP   ${CFG.USE_TLS ? 'https' : 'http'}://0.0.0.0:${CFG.HTTP_PORT}`);
  console.log(`  MQTT   ${CFG.USE_TLS ? 'ssl' : 'tcp'}://0.0.0.0:${CFG.USE_TLS ? CFG.TLS_PORT : CFG.TCP_PORT}`);
  console.log(`  下发给 App 的 liveServerUrl = ${CFG.LIVE_SERVER}`);
  console.log('======================================================');
  broker.listen((addrs) => addrs.forEach((a) => console.log('  MQTT listening on ' + a)));
});

module.exports = { broker, server, CFG };
