'use strict';
/**
 * 模拟插座（SoftAP 配网侧的"设备端"）
 * 用途：没有真机时，在本机验证 tools/provision.js 的报文构造与错误判定是否正确。
 *
 *   node tools/fake-plug-softap.js [--port=5658]
 *   # 另一个终端：
 *   node tools/provision.js --ssid=test --pass=test --host=127.0.0.1
 *
 * 行为与真设备一致：
 *   收到 ch_pubk → 回自己的 ch_pubk → ECDH → 解密 ch_data → 回 {"type":"ch_data","mac","product_id"}
 *   真机回包实测带 "type":"ch_data" 字段（见 docs/03 §5.7），本模拟器也带上。
 *
 * 可选：模拟真机的 crt 拒绝行为（用于自测 tools/experiment-crt.ps1 的判定逻辑）
 *   CRT_MAX=512        # crt 长度 > 512 就 NACK（模拟固件固定缓冲区 H1）
 *   CRT_EMPTY_ONLY=1   # 只要 crt 非空就 NACK（模拟固件只收厂商 crt H2）
 *
 *   NACK 的表现与真机一致：**不回加密包，而是重发明文 ch_pubk**。
 */
const dgram = require('dgram');
const crypto = require('crypto');

function arg(k, d) {
  const hit = process.argv.find((a) => a.startsWith('--' + k + '='));
  return hit ? hit.slice(k.length + 3) : d;
}
const PORT = +(arg('port', '5658'));
const IV = Buffer.from('abcdefghijklmnop');
const CRT_MAX = +(process.env.CRT_MAX || '0');            // 0 = 不限制
const CRT_EMPTY_ONLY = process.env.CRT_EMPTY_ONLY === '1';
const DEV_MAC = 'B4E62D3A6E7C';
const DEV_PID = '381785';

// 假设备的 secp256k1 密钥对
const ecdh = crypto.createECDH('secp256k1');
ecdh.generateKeys();
const devPubB64 = ecdh.getPublicKey().slice(1).toString('base64');

let aesKey = null;
let peer = null;                 // 记住对端地址，便于 NACK 时主动重发
const log = (...a) => console.log('[fake-plug]', ...a);

const sock = dgram.createSocket({ type: 'udp4', reuseAddr: true });
sock.on('error', (e) => { console.error('[fake-plug] socket error:', e.message); process.exit(1); });

function sendPubk(why) {
  if (!peer) return;
  log(`→ 重发 ch_pubk（${why}）`);
  sock.send(Buffer.from(JSON.stringify({ type: 'ch_pubk', pubkey: devPubB64 })), peer.port, peer.address);
}

sock.on('message', (msg, rinfo) => {
  const text = msg.toString('utf8').trim();

  // 明文握手
  let obj = null;
  try { obj = JSON.parse(text); } catch (e) { /* 非 JSON */ }
  if (obj && obj.type === 'ch_pubk') {
    log(`← ch_pubk（来自 ${rinfo.address}:${rinfo.port}），回自己的公钥`);
    peer = rinfo;
    const raw = Buffer.from(obj.pubkey, 'base64');
    const p = raw.length === 64 ? Buffer.concat([Buffer.from([4]), raw]) : raw;
    aesKey = ecdh.computeSecret(p).slice(0, 16);
    log(`  ECDH 完成，AES key = ${aesKey.toString('hex')}`);
    sendPubk('响应握手');
    return;
  }

  // 加密报文
  if (aesKey) {
    try {
      const d = crypto.createDecipheriv('aes-128-cbc', aesKey, IV);
      const plain = Buffer.concat([d.update(Buffer.from(text, 'base64')), d.final()]).toString('utf8');
      const p = JSON.parse(plain);
      log('← 解密报文：', JSON.stringify(p, null, 2));
      if (p.type === 'ch_data') {
        const crtLen = (p.crt || '').length;
        log(`  ★ domain="${p.domain}"  crt=${crtLen} 字节  ssid="${p.ssid}"  checkCode=${p.checkCode}`);

        // 模拟固件的 crt 拒绝 → 与真机一致：重发明文 ch_pubk，不回加密包
        const reject = (CRT_EMPTY_ONLY && crtLen > 0) || (CRT_MAX > 0 && crtLen > CRT_MAX);
        if (reject) {
          const why = CRT_EMPTY_ONLY ? 'crt 非空即拒 (H2)' : `crt ${crtLen}B > 上限 ${CRT_MAX}B (H1)`;
          setTimeout(() => sendPubk(`NACK: ${why}`), 300);
          return;
        }

        const c = crypto.createCipheriv('aes-128-cbc', aesKey, IV);
        const out = Buffer.concat([c.update(JSON.stringify({ type: 'ch_data', mac: DEV_MAC, product_id: DEV_PID }), 'utf8'), c.final()]);
        // 真设备发的是 base64 文本（见 docs/03 §5.4 app_step1）
        setTimeout(() => sock.send(Buffer.from(out.toString('base64')), rinfo.port, rinfo.address), 300);
      } else if (p.type === 'end') {
        log('  收到 {"type":"end"}，配网流程结束 ✅');
      }
    } catch (e) {
      log('解密失败：', e.message);
    }
  }
});

sock.bind(PORT, () => {
  log(`监听 UDP ${PORT}，本机公钥=${devPubB64.slice(0, 20)}...`);
  if (CRT_MAX > 0) log(`  模拟 H1：crt 长度 > ${CRT_MAX} 即 NACK`);
  if (CRT_EMPTY_ONLY) log('  模拟 H2：crt 非空即 NACK');
});

