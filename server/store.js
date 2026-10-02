'use strict';
/**
 * 内存态设备/用户存储。真实用途是让你能把插座"绑"上来并控制它。
 * 生产用请换数据库。
 */

const state = {
  users: new Map(),   // token -> {uid, phone}
  devices: new Map(), // deviceId -> {deviceId, deviceMac, deviceName, productId, snapshot, status, ...}
  clients: new Map(), // clientId -> {clientKey, uid}
  discovered: new Map(), // checkCode -> {deviceId, productId, mac}
};

let seq = 1000;
const nextId = () => String(++seq);

function newClient(uid) {
  const clientId = 'srv-client-' + nextId();
  const clientKey = require('crypto').randomBytes(16).toString('hex');
  state.clients.set(clientId, { clientKey, uid });
  return { clientId, clientKey };
}

function newToken(phone) {
  const uid = 'srv-uid-' + nextId();
  const token = require('crypto').randomBytes(24).toString('hex');
  state.users.set(token, { uid, phone });
  return { token, uid };
}

/** 设备上线/上报快照时更新（MQTT broker 调） */
function upsertDevice(d) {
  const cur = state.devices.get(d.deviceId) || { deviceId: d.deviceId };
  state.devices.set(d.deviceId, Object.assign(cur, d, { status: 1 }));
  return state.devices.get(d.deviceId);
}

/** 转成 App 期望的 XCDeviceModel / XCSDKDeviceModel 形状 */
function deviceForApp(d) {
  return {
    deviceId: d.deviceId,
    deviceMac: d.deviceMac || '',
    deviceName: d.deviceName || ('插座-' + d.deviceId.slice(-4)),
    deviceSn: d.deviceSn || d.deviceId,
    productId: d.productId || '1',
    productImage: '',
    isAdmin: 1,
    isGw: 0,
    top: 0,
    update: 0,
    status: d.status == null ? 1 : d.status,
    snapshot: d.snapshot || {},
    displayMessage: '',
    partner: '',
    progress: 100,
    controlParameter: d.controlParameter || [
      { key: '1', name: '开关', type: 'bool', value: String((d.snapshot && d.snapshot['1']) || 0) },
    ],
  };
}

module.exports = { state, nextId, newClient, newToken, upsertDevice, deviceForApp };
