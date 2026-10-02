'use strict';
/**
 * 小葱智能 · 线上服务器探测 / 热更新拉取工具
 *
 * ⚠️ 本工具会访问厂商仍在运营的线上服务器，使用**你自己的账号**，
 *    只做读取（client/config、登录、设备列表、查询下载地址）。
 *    请仅对自己的设备/账号使用。
 *
 * 在能访问 gw.ixiaocong.com 的网络下运行（公司网络通常被 DNS 劫持，请换手机热点）。
 *
 * 用法：
 *   # 1) 只拉 client/config —— 不需要登录，信息量最大
 *   node tools/probe_server.js
 *
 *   # 2) 登录并列出设备、拉热更新地址
 *   node tools/probe_server.js --phone=13800000000            # 先发短信
 *   node tools/probe_server.js --phone=13800000000 --code=123456
 *
 *   # 换服务器（默认 https://gw.ixiaocong.com/）
 *   node tools/probe_server.js --host=https://gw.ixiaocong.net/
 */
const https = require('https');
const http = require('http');
const crypto = require('crypto');
const { URL } = require('url');

// ---------------- 硬编码凭据（逆向自 App）----------------
const APP_ID = '27e33ff8a0634cc4b6f696c9872a2d1b';
const APP_KEY = '3wruosO36ZzBXVM8';

// ---------------- 参数 ----------------
const argv = process.argv.slice(2);
const arg = (k, d) => {
  const hit = argv.find((a) => a.startsWith('--' + k + '='));
  return hit ? hit.split('=').slice(1).join('=') : d;
};
const HOST = arg('host', 'https://gw.ixiaocong.com/');
const PHONE = arg('phone', '');
const CODE = arg('code', '');
const VERIFY = arg('verify', '');

// ---------------- 签名（逆向自 SignatureUtil#signMD5）----------------
function signMD5({ token, clientId, clientKey, udid, timestamp, params, isConfig }) {
  const sp = Object.assign({}, params || {});
  sp['xc-timestamp'] = String(timestamp);
  sp['xc-token'] = token || '';
  sp['clientId'] = clientId || '';
  sp['appId'] = APP_ID;
  sp['udid'] = udid || '';
  const keys = Object.keys(sp).sort((a, b) => {
    const x = a.toLowerCase(), y = b.toLowerCase();
    return x < y ? -1 : x > y ? 1 : 0;
  });
  let buf = '';
  for (const k of keys) buf += `${k}=${sp[k] == null ? '' : sp[k]}&`;
  buf += isConfig ? APP_KEY : clientKey;
  return crypto.createHash('md5').update(buf, 'utf8').digest('hex');
}

// ---------------- 请求 ----------------
function post(path, form, ctx) {
  return new Promise((resolve, reject) => {
    const ts = Date.now();
    const body = new URLSearchParams(form).toString();
    const url = new URL(path.replace(/^\/+/, ''), HOST);
    const isHttps = url.protocol === 'https:';
    const req = (isHttps ? https : http).request({
      hostname: url.hostname,
      port: url.port || (isHttps ? 443 : 80),
      path: url.pathname,
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Length': Buffer.byteLength(body),
        'User-Agent': 'XCAndroid/2.2.1',
      },
      timeout: 20000,
    }, (res) => {
      let data = '';
      res.on('data', (c) => { data += c; });
      res.on('end', () => resolve({ status: res.statusCode, raw: data }));
    });
    req.on('error', reject);
    req.on('timeout', () => req.destroy(new Error('请求超时（可能被网络阻断）')));
    req.write(body);
    req.end();
  });
}

/** 组装带签名的完整信封 */
async function call(path, extraParams, ctx, opts = {}) {
  const ts = Date.now();
  const base = {
    'xc-token': ctx.token || '',
    'xc-timestamp': String(ts),
    'appId': APP_ID,
    'clientId': ctx.clientId || '',
    'udid': ctx.udid,
    'platform': 'phone',
    'clientVersion': '2.2.1',
    'os': 'android',
    'osVersion': '29',
    'network': 'wifi',
    'brand': 'Xiaomi',
    'model': 'MI 9',
    'screen': '1080x2340',
    'clientUdid': ctx.udid,
    'channel': 'unknown channel',
  };
  const signParams = opts.sign === false ? {} : (extraParams || {});
  base['xc-sign'] = signMD5({
    token: ctx.token, clientId: ctx.clientId, clientKey: ctx.clientKey,
    udid: ctx.udid, timestamp: ts, params: signParams,
    isConfig: opts.isConfig || false,
  });
  const form = Object.assign({}, extraParams || {}, base);
  const r = await post(path, form, ctx);
  let parsed = null;
  try { parsed = JSON.parse(r.raw); } catch (e) { /* 非 JSON */ }
  return { status: r.status, parsed, raw: r.raw };
}

const ctx = {
  token: '', clientId: '', clientKey: '',
  udid: crypto.createHash('md5').update('probe-' + (PHONE || 'anon')).digest('hex'),
};

function show(title, r) {
  console.log('\n' + '='.repeat(60));
  console.log('  ' + title + '   [HTTP ' + r.status + ']');
  console.log('='.repeat(60));
  if (!r.parsed) { console.log(r.raw || '(空响应)'); return null; }
  console.log('  code=' + r.parsed.code + '  msg=' + JSON.stringify(r.parsed.msg));
  let d = r.parsed.data;
  if (typeof d === 'string') { try { d = JSON.parse(d); } catch (e) { /* 保持字符串 */ } }
  console.log('  data = ' + JSON.stringify(d, null, 2).slice(0, 3000));
  return d;
}

// ---------------- 主流程 ----------------
(async () => {
  console.log('目标服务器: ' + HOST);
  console.log('udid: ' + ctx.udid);

  // 1) client/config —— 不需要登录，信息量最大
  try {
    const r = await call('client/config', {}, ctx, { isConfig: true, sign: false });
    const d = show('client/config  （引导配置）', r);
    if (d) {
      ctx.clientId = d.clientId || '';
      ctx.clientKey = d.clientKey || '';
      console.log('\n  ★ liveServerUrl  = ' + d.liveServerUrl + '   <-- 设备/App 连的 MQTT 服务器');
      console.log('  ★ upgradeVersion = ' + d.upgradeVersion);
      console.log('  ★ upgradeUrl     = ' + d.upgradeDownloadUrl + '   <-- 可能有更新的 APK！');
      console.log('  clientId  = ' + d.clientId);
      console.log('  clientKey = ' + d.clientKey);
    }
  } catch (e) {
    console.error('\nclient/config 失败: ' + e.message);
    console.error('如果提示 DNS/超时，说明当前网络到不了厂商服务器，请换手机热点重试。');
    process.exit(1);
  }

  if (!PHONE) {
    console.log('\n(未提供 --phone，只做了 client/config。');
    console.log(' 要列设备/拉热更新，加 --phone=你的手机号 再跑)');
    return;
  }

  // 2) sms/send
  if (!CODE) {
    const r = await call('sms/send', { phone: PHONE }, ctx, { sign: false });
    show('sms/send  （注意：会真的发短信到 ' + PHONE + '）', r);
    console.log('\n请查看手机短信，然后用同样的 --phone 加上 --code=收到的验证码 再跑一次。');
    return;
  }

  // 3) user/login
  const lr = await call('user/login', { phone: PHONE, code: CODE, verifycode: VERIFY }, ctx, { sign: false });
  const ld = show('user/login', lr);
  if (!ld || !ld.token) { console.error('登录失败，检查验证码'); return; }
  ctx.token = ld.token;
  console.log('\n  token = ' + ld.token);

  // 4) 设备列表
  const ir = await call('index', {}, ctx, { sign: false });
  const idx = show('index  （首页聚合）', ir);
  const devices = (idx && idx.devices) || [];
  console.log('\n  共 ' + devices.length + ' 个设备');

  // 5) 逐设备拉 RN bundle 地址
  for (const dev of devices.slice(0, 10)) {
    const id = dev.deviceId;
    if (!id) continue;
    try {
      const vr = await call('device/v2/detail', { deviceId: id, rnVersion: '0.0.0' }, ctx, { sign: true });
      const vd = show('device/v2/detail  deviceId=' + id, vr);
      if (vd && vd.rnInfo) {
        console.log('\n  ★ RN bundle 下载地址 = ' + vd.rnInfo.downloadUrl);
        console.log('    upgrade=' + vd.rnInfo.upgrade + '  version=' + vd.rnInfo.version);
      }
    } catch (e) {
      console.error('device/v2/detail 失败: ' + e.message);
    }
  }

  console.log('\n完成。把上面的 liveServerUrl / upgradeDownloadUrl / RN 地址贴回来即可。');
})().catch((e) => { console.error('异常: ' + e.message); process.exit(1); });
