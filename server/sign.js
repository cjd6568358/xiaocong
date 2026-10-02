'use strict';
/**
 * 小葱智能（ixiaocong）网关签名实现
 *
 * 逆向自 com.xiaocong.smarthome.sdk.http.util.SignatureUtil#signMD5
 *
 * 算法：
 *   1. 收集参与签名的参数：endpoint 的 sign 参数 + xc-timestamp / xc-token / clientId / appId / udid
 *   2. key 按大小写不敏感排序
 *   3. 拼成 "k1=v1&k2=v2&"（每对后都带 &，包括最后一个）
 *   4. 追加密钥（无分隔符）：client/config 用 appKey，其余用 clientKey
 *   5. MD5(UTF-8) → 十六进制 → 转小写
 */
const crypto = require('crypto');

const md5hex = (s) => crypto.createHash('md5').update(s, 'utf8').digest('hex');

/**
 * @param {object} o
 * @param {string} o.token       登录 token（未登录时可为空）
 * @param {string} o.appId
 * @param {string} o.clientId    服务端下发
 * @param {string} o.clientKey   服务端下发
 * @param {string} o.appKey      client/config 阶段使用
 * @param {string} o.udid
 * @param {number|string} o.timestamp
 * @param {object} [o.params]    该 endpoint 参与签名的参数
 * @param {boolean} [o.isConfig] true 时用 appKey 而非 clientKey
 * @returns {string} 小写十六进制 MD5
 */
function sign(o) {
  const sp = Object.assign({}, o.params || {});
  sp['xc-timestamp'] = String(o.timestamp);
  sp['xc-token'] = o.token || '';
  sp['clientId'] = o.clientId || '';
  sp['appId'] = o.appId || '';
  sp['udid'] = o.udid || '';

  const keys = Object.keys(sp).sort((a, b) => {
    const x = a.toLowerCase(), y = b.toLowerCase();
    return x < y ? -1 : x > y ? 1 : 0;
  });

  let buf = '';
  for (const k of keys) {
    const v = sp[k] == null ? '' : sp[k];
    buf += `${k}=${v}&`;
  }
  buf += (o.isConfig ? o.appKey : o.clientKey) || '';

  return md5hex(buf);
}

module.exports = { sign, md5hex };
