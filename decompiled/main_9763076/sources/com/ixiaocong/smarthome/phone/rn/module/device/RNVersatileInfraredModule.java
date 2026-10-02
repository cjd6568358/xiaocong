package com.ixiaocong.smarthome.phone.rn.module.device;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.hzy.tvmao.KKACManagerV2;
import com.hzy.tvmao.KKSingleMatchManager;
import com.hzy.tvmao.KookongSDK;
import com.hzy.tvmao.interf.IRequestResult;
import com.hzy.tvmao.interf.ISingleMatchResult;
import com.hzy.tvmao.ir.ac.ACStateV2;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.kookong.app.data.IrData;
import com.kookong.app.data.IrDataList;
import com.kookong.app.data.RcTestRemoteKeyV3;
import com.kookong.app.data.RemoteList;
import com.kookong.app.data.SpList;
import com.kookong.app.data.StbList;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNVersatileInfraredModule extends ReactContextBaseJavaModule {
    private Context mContext;
    private int mCount;
    private String mDeviceId;
    private KKACManagerV2 mKKACManager;
    private KKSingleMatchManager singleMatch;

    public RNVersatileInfraredModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.singleMatch = new KKSingleMatchManager();
        this.mKKACManager = new KKACManagerV2();
        this.mContext = reactContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "VersatileInfrared";
    }

    @ReactMethod
    public void initialKookongSDKWithDeviceId(String deviceId) {
        try {
            this.mDeviceId = deviceId;
            int a = ((Integer) SpUtils.getFromLocal(this.mContext, "kukong_device_Id", deviceId, 0)).intValue();
            this.mCount = a;
            XcLogger.i("RNVersatileInfraredModule", "a:" + a);
            if (a == 0) {
                KookongSDK.init(this.mContext, "AAA2B616E8BEFA8BD8FB44CFFCC3E669", deviceId);
                XcLogger.i("RNVersatileInfraredModule", "mDeviceId:" + deviceId);
            } else {
                KookongSDK.init(this.mContext, "AAA2B616E8BEFA8BD8FB44CFFCC3E669", deviceId + "_" + (a > 2 ? 2 : a));
                XcLogger.i("RNVersatileInfraredModule", "mDeviceId:" + deviceId + "_" + a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void getRemoteIdsWithDeviceTypeId(final String deviceTypeId, String brandId, final Promise promise) {
        try {
            final Map<String, Object> map = new HashMap<>();
            KookongSDK.getAllRemoteIds(Integer.valueOf(deviceTypeId).intValue(), Integer.valueOf(brandId).intValue(), 0, 0, new IRequestResult<RemoteList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.1
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(String msg, RemoteList result) {
                    List<Integer> rids = result.rids;
                    if (rids != null && rids.size() > 0) {
                        String remoteIds = RNVersatileInfraredModule.this.listToStr(rids);
                        RNVersatileInfraredModule.this.singleMatch.getMatchKey(Integer.valueOf(deviceTypeId).intValue(), remoteIds, false, new ISingleMatchResult() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.1.1
                            @Override // com.hzy.tvmao.interf.ISingleMatchResult
                            public void onMatchedIR(String s) {
                                map.put("matchKeysArray", null);
                                map.put("matchType", 0);
                                map.put("remoteid", s);
                                promise.resolve(JSON.toJSONString(map));
                            }

                            @Override // com.hzy.tvmao.interf.ISingleMatchResult
                            public void onNextGroupKey(List<RcTestRemoteKeyV3> list) {
                                map.put("matchKeysArray", list);
                                map.put("matchType", 1);
                                map.put("remoteid", null);
                                promise.resolve(JSON.toJSONString(map));
                            }

                            @Override // com.hzy.tvmao.interf.ISingleMatchResult
                            public void onNotMatchIR() {
                                map.put("matchKeysArray", null);
                                map.put("matchType", 2);
                                map.put("remoteid", null);
                                promise.resolve(JSON.toJSONString(map));
                            }

                            @Override // com.hzy.tvmao.interf.ISingleMatchResult
                            public void onError() {
                                map.put("matchKeysArray", null);
                                map.put("matchType", 3);
                                map.put("remoteid", null);
                                promise.resolve(JSON.toJSONString(map));
                            }
                        });
                    } else {
                        map.put("matchKeysArray", null);
                        map.put("matchType", 3);
                        map.put("remoteid", null);
                        promise.resolve(JSON.toJSONString(map));
                    }
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer errorCode, String msg) {
                    map.put("matchKeysArray", null);
                    map.put("matchType", 3);
                    map.put("remoteid", null);
                    promise.resolve(JSON.toJSONString(map));
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void reportKeyIsWorkingWithKey(ReadableMap singleKey, final Promise promise) {
        try {
            ReadableNativeMap key = (ReadableNativeMap) singleKey;
            String str = JSON.toJSONString(key.toHashMap());
            RcTestRemoteKeyV3 rcTestRemoteKeyV3 = (RcTestRemoteKeyV3) JSON.parseObject(str, RcTestRemoteKeyV3.class);
            final Map<String, Object> map = new HashMap<>();
            this.singleMatch.keyIsWorking(rcTestRemoteKeyV3, new ISingleMatchResult() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.2
                @Override // com.hzy.tvmao.interf.ISingleMatchResult
                public void onMatchedIR(String s) {
                    map.put("matchKeysArray", null);
                    map.put("matchType", 0);
                    map.put("remoteid", s);
                    promise.resolve(JSON.toJSONString(map));
                }

                @Override // com.hzy.tvmao.interf.ISingleMatchResult
                public void onNextGroupKey(List<RcTestRemoteKeyV3> list) {
                    map.put("matchKeysArray", list);
                    map.put("matchType", 1);
                    map.put("remoteid", null);
                    promise.resolve(JSON.toJSONString(map));
                }

                @Override // com.hzy.tvmao.interf.ISingleMatchResult
                public void onNotMatchIR() {
                    map.put("matchKeysArray", null);
                    map.put("matchType", 2);
                    map.put("remoteid", null);
                    promise.resolve(JSON.toJSONString(map));
                }

                @Override // com.hzy.tvmao.interf.ISingleMatchResult
                public void onError() {
                    map.put("matchKeysArray", null);
                    map.put("matchType", 3);
                    map.put("remoteid", null);
                    promise.resolve(JSON.toJSONString(map));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void rereportAllKeysNotWorkingResolver(String matchKeys, final Promise promise) {
        try {
            List<RcTestRemoteKeyV3> rcTestRemoteKeyV3s = JSONArray.parseArray(matchKeys, RcTestRemoteKeyV3.class);
            final Map<String, Object> map = new HashMap<>();
            this.singleMatch.groupKeyNotWork(rcTestRemoteKeyV3s, new ISingleMatchResult() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.3
                @Override // com.hzy.tvmao.interf.ISingleMatchResult
                public void onMatchedIR(String s) {
                    map.put("matchKeysArray", null);
                    map.put("matchType", 0);
                    map.put("remoteid", s);
                    promise.resolve(JSON.toJSONString(map));
                }

                @Override // com.hzy.tvmao.interf.ISingleMatchResult
                public void onNextGroupKey(List<RcTestRemoteKeyV3> list) {
                    map.put("matchKeysArray", list);
                    map.put("matchType", 1);
                    map.put("remoteid", null);
                    promise.resolve(JSON.toJSONString(map));
                }

                @Override // com.hzy.tvmao.interf.ISingleMatchResult
                public void onNotMatchIR() {
                    XcLogger.i("RNVersatileInfrared", "rereportAllKeysNotWorkingResolver onNotMatchIR");
                    map.put("matchKeysArray", null);
                    map.put("matchType", 2);
                    map.put("remoteid", null);
                    promise.resolve(JSON.toJSONString(map));
                }

                @Override // com.hzy.tvmao.interf.ISingleMatchResult
                public void onError() {
                    XcLogger.i("RNVersatileInfrared", "rereportAllKeysNotWorkingResolver onError");
                    map.put("matchKeysArray", null);
                    map.put("matchType", 3);
                    map.put("remoteid", null);
                    promise.resolve(JSON.toJSONString(map));
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void downloadIRDataByIdWithRemoteId(String remoteId, String deviceTypeId, final Promise promise) {
        try {
            KookongSDK.getIRDataById(remoteId, Integer.valueOf(deviceTypeId).intValue(), new IRequestResult<IrDataList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.4
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(String msg, IrDataList result) {
                    List<IrData> list = result.getIrDataList();
                    Map<String, Object> map = new HashMap<>();
                    map.put("fre", Integer.valueOf(list.get(0).fre));
                    map.put("type", Short.valueOf(list.get(0).type));
                    map.put("exts", JSON.toJSONString(list.get(0).exts));
                    map.put("keys", JSON.toJSONString(list.get(0).keys));
                    promise.resolve(JSON.toJSONString(map));
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer errorCode, String msg) {
                    promise.reject(String.valueOf(errorCode), msg);
                    ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "加载失败,请重试!");
                    if (msg.equals("code 8") || msg.equals("code 10") || errorCode.intValue() == -3) {
                        RNVersatileInfraredModule.this.initKuKongSDK();
                        XcLogger.e("RNVersatileInfraredModule", "downloadIRDataByIdWithRemoteId下载设备总数据超过五十套," + msg);
                        if (RNVersatileInfraredModule.this.mCount >= 2) {
                            ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "红外设备下载总数据超过限制");
                        }
                    }
                    if (errorCode.intValue() == -2) {
                        ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "设备总数超过了授权的额度");
                    }
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getACRemoteIdsWithDeviceTypeId(final String deviceTypeId, String brandId, final Promise promise) {
        try {
            new HashMap();
            KookongSDK.getAllRemoteIds(Integer.valueOf(deviceTypeId).intValue(), Integer.valueOf(brandId).intValue(), 0, 0, new IRequestResult<RemoteList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.5
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(String msg, RemoteList result) {
                    List<Integer> rids = result.rids;
                    if (rids != null && rids.size() > 0) {
                        String remoteIds = RNVersatileInfraredModule.this.listToStr(rids);
                        XcLogger.i("RNVersatileInfraredModule", "remoteIds" + remoteIds);
                        KookongSDK.testIRDataById(remoteIds, Integer.valueOf(deviceTypeId).intValue(), new IRequestResult<IrDataList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.5.1
                            @Override // com.hzy.tvmao.interf.IRequestResult
                            public void onSuccess(String s, IrDataList irDataList) {
                                List<IrData> irDatas = irDataList.getIrDataList();
                                List<Map<String, Object>> irString = new ArrayList<>();
                                for (IrData irData : irDatas) {
                                    HashMap<String, String> tempExts = new HashMap<>();
                                    for (Object map : irData.exts.entrySet()) {
                                        tempExts.put(String.valueOf(((Map.Entry) map).getKey()), ((Map.Entry) map).getValue());
                                    }
                                    List<HashMap<String, Object>> list = new ArrayList<>();
                                    if (irData.keys != null) {
                                        for (IrData.IrKey map2 : irData.keys) {
                                            HashMap<String, Object> tempKey = new HashMap<>();
                                            tempKey.put("dcode", map2.dcode);
                                            tempKey.put("fid", Integer.valueOf(map2.fid));
                                            tempKey.put("fkey", map2.fkey);
                                            tempKey.put("fname", map2.fname);
                                            tempKey.put("pulse", map2.pulse);
                                            tempKey.put("scode", map2.scode);
                                            list.add(tempKey);
                                        }
                                    }
                                    Map<String, Object> map3 = new HashMap<>();
                                    map3.put("rid", Integer.valueOf(irData.rid));
                                    map3.put("type", Short.valueOf(irData.type));
                                    map3.put("exts", JSON.toJSONString(tempExts));
                                    map3.put("keys", list);
                                    map3.put("fre", Integer.valueOf(irData.fre));
                                    irString.add(map3);
                                    XcLogger.i("RNVersatileInfraredModule", map3);
                                }
                                promise.resolve(JSON.toJSONString(irString));
                            }

                            @Override // com.hzy.tvmao.interf.IRequestResult
                            public void onFail(Integer integer, String s) {
                                String s2;
                                promise.reject(String.valueOf(integer), s);
                                if (integer.intValue() == -2) {
                                    s2 = "设备总数超过了授权的额度";
                                } else {
                                    s2 = "加载失败,请稍后重试";
                                }
                                ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, s2);
                            }
                        });
                    }
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer errorCode, String msg) {
                    promise.reject(String.valueOf(errorCode), msg);
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void initACIRData(String remoteId, String exts, String keys) {
        try {
            XcLogger.i("RNVersatileInfraredModule", "remoteId:" + remoteId);
            XcLogger.i("RNVersatileInfraredModule", "exts:" + exts);
            HashMap maps = (HashMap) JSON.parseObject(exts, HashMap.class);
            HashMap<Integer, String> hashMapExts = new HashMap<>();
            for (Object map : maps.entrySet()) {
                if (((Map.Entry) map).getKey() instanceof Integer) {
                    hashMapExts.put((Integer) ((Map.Entry) map).getKey(), (String) ((Map.Entry) map).getValue());
                } else {
                    hashMapExts.put(Integer.valueOf((String) ((Map.Entry) map).getKey()), (String) ((Map.Entry) map).getValue());
                }
            }
            this.mKKACManager.initIRData(Integer.valueOf(remoteId).intValue(), hashMapExts, null);
            this.mKKACManager.setACStateV2FromString(Constants.MAIN_VERSION_TAG);
            XcLogger.i("RNVersatileInfraredModule", "initACIRData:" + this.mKKACManager.getACStateV2InString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void matchACWithKey(String type, String typeValue, Promise promise) {
        try {
            XcLogger.i("RNVersatileInfraredModule", "type:" + type + "typeValue:" + typeValue);
            switch (type) {
                case "switch":
                    this.mKKACManager.changePowerState();
                    promise.resolve(this.mKKACManager.getACIRPattern());
                    XcLogger.i("RNVersatileInfraredModule", "changePowerState:" + this.mKKACManager.getACIRPattern());
                    break;
                case "mode":
                    if (TextUtils.isEmpty(typeValue)) {
                        promise.reject("mode typeValue is empty");
                        return;
                    }
                    if (typeValue.equals("AC_MODE_HEAT")) {
                        this.mKKACManager.changeACTargetModel(1);
                    } else if (typeValue.equals("AC_MODE_COOL")) {
                        this.mKKACManager.changeACTargetModel(0);
                    } else if (typeValue.equals("AC_MODE_FAN")) {
                        this.mKKACManager.changeACTargetModel(3);
                    } else if (typeValue.equals("AC_MODE_DRY")) {
                        this.mKKACManager.changeACTargetModel(4);
                    } else if (typeValue.equals("AC_MODE_AUTO")) {
                        this.mKKACManager.changeACTargetModel(2);
                    }
                    promise.resolve(this.mKKACManager.getACIRPattern());
                    XcLogger.i("RNVersatileInfraredModule", "changeACTargetModel:" + this.mKKACManager.getACIRPattern());
                    break;
                    break;
                case "setTemperature":
                    if (typeValue.equals("up")) {
                        this.mKKACManager.increaseTmp();
                    } else if (typeValue.equals("down")) {
                        this.mKKACManager.decreaseTmp();
                    }
                    promise.resolve(this.mKKACManager.getACIRPattern());
                    XcLogger.i("RNVersatileInfraredModule", "setTemperature:" + this.mKKACManager.getACIRPattern());
                    break;
                case "sweep_wind":
                    this.mKKACManager.changeUDWindDirect(ACStateV2.UDWindDirectKey.UDDIRECT_KEY_SWING);
                    promise.resolve(this.mKKACManager.getACIRPattern());
                    XcLogger.i("RNVersatileInfraredModule", "changeUDWindDirect UDDIRECT_KEY_SWING:" + this.mKKACManager.getACIRPattern());
                    break;
                case "no_swipe":
                    this.mKKACManager.changeUDWindDirect(ACStateV2.UDWindDirectKey.UDDIRECT_KEY_SWING);
                    promise.resolve(this.mKKACManager.getACIRPattern());
                    XcLogger.i("RNVersatileInfraredModule", "changeUDWindDirect UDDIRECT_KEY_SWING:" + this.mKKACManager.getACIRPattern());
                    break;
                case "switch_dir":
                    this.mKKACManager.changeUDWindDirect(ACStateV2.UDWindDirectKey.UDDIRECT_KEY_FIX);
                    promise.resolve(this.mKKACManager.getACIRPattern());
                    XcLogger.i("RNVersatileInfraredModule", "changeUDWindDirect UDDIRECT_KEY_FIX:" + this.mKKACManager.getACIRPattern());
                    break;
            }
            XcLogger.i("RNVersatileInfraredModule", "getCurTemp:" + this.mKKACManager.getCurTemp());
            XcLogger.i("RNVersatileInfraredModule", "getCurModelType:" + this.mKKACManager.getCurModelType());
            XcLogger.i("RNVersatileInfraredModule", "getCurWindSpeed:" + this.mKKACManager.getCurWindSpeed());
            XcLogger.i("RNVersatileInfraredModule", "getPowerState:" + this.mKKACManager.getPowerState());
            XcLogger.i("RNVersatileInfraredModule", "getCurUDDirect:" + this.mKKACManager.getCurUDDirect());
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getACState(Promise promise) {
        try {
            Map<String, Object> acState = new HashMap<>();
            acState.put("switch", Integer.valueOf(this.mKKACManager.getPowerState() == 1 ? 0 : 1));
            acState.put("mode", this.mKKACManager.getCurModelType() + Constants.MAIN_VERSION_TAG);
            if (this.mKKACManager.getCurTemp() != -1) {
                acState.put("setTemperature", this.mKKACManager.getCurTemp() + Constants.MAIN_VERSION_TAG);
            }
            if (this.mKKACManager.getCurWindSpeed() != -1) {
                acState.put("wind", this.mKKACManager.getCurWindSpeed() + Constants.MAIN_VERSION_TAG);
            }
            promise.resolve(JSON.toJSONString(acState));
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void matchACGetKeysClickable(Promise promise) {
        boolean wind_direct_type = true;
        try {
            if (this.mKKACManager.getCurUDDirectType() == ACStateV2.UDWindDirectType.UDDIRECT_ONLY_FIX || this.mKKACManager.getCurUDDirectType() == ACStateV2.UDWindDirectType.UDDIRECT_ONLY_SWING) {
                wind_direct_type = false;
            }
            Map<String, Boolean> keysClickAbleMap = new HashMap<>();
            keysClickAbleMap.put("switch", true);
            keysClickAbleMap.put("ac_mode_heat", Boolean.valueOf(this.mKKACManager.isContainsTargetModel(1)));
            keysClickAbleMap.put("ac_mode_cool", Boolean.valueOf(this.mKKACManager.isContainsTargetModel(0)));
            keysClickAbleMap.put("ac_mode_fan", Boolean.valueOf(this.mKKACManager.isContainsTargetModel(3)));
            keysClickAbleMap.put("ac_mode_dry", Boolean.valueOf(this.mKKACManager.isContainsTargetModel(4)));
            keysClickAbleMap.put("ac_mode_auto", Boolean.valueOf(this.mKKACManager.isContainsTargetModel(2)));
            keysClickAbleMap.put("temperature_up", Boolean.valueOf(this.mKKACManager.isTempCanControl()));
            keysClickAbleMap.put("temperature_down", Boolean.valueOf(this.mKKACManager.isTempCanControl()));
            keysClickAbleMap.put("wind_direct_type", Boolean.valueOf(wind_direct_type));
            promise.resolve(JSON.toJSONString(keysClickAbleMap));
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void downloadNoStateIRDataById(String remoteId, String deviceTypeId, final Promise promise) {
        try {
            KookongSDK.getNoStateIRDataById(remoteId, Integer.valueOf(deviceTypeId).intValue(), new IRequestResult<IrDataList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.6
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(String msg, IrDataList result) {
                    List<IrData> list = result.getIrDataList();
                    Map<String, Object> map = new HashMap<>();
                    map.put("fre", Integer.valueOf(list.get(0).fre));
                    map.put("type", Short.valueOf(list.get(0).type));
                    map.put("exts", JSON.toJSONString(list.get(0).exts));
                    map.put("keys", JSON.toJSONString(list.get(0).keys));
                    promise.resolve(JSON.toJSONString(map));
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer errorCode, String msg) {
                    promise.reject(String.valueOf(errorCode), msg);
                    ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "加载失败,请重试!");
                    if (msg.equals("code 8") || msg.equals("code 10") || errorCode.intValue() == -3) {
                        RNVersatileInfraredModule.this.initKuKongSDK();
                        XcLogger.e("RNVersatileInfraredModule", "downloadNoStateIRDataById下载设备总数据超过五十套," + msg);
                        if (RNVersatileInfraredModule.this.mCount >= 2) {
                            ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "红外设备下载总数据超过限制");
                        }
                    }
                    if (errorCode.intValue() == -2) {
                        ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "设备总数超过了授权的额度");
                    }
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getAreaId(String province, String city, String sub, final Promise promise) {
        try {
            KookongSDK.getAreaId(province, city, sub, new IRequestResult<Integer>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.7
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(String msg, Integer result) {
                    promise.resolve(result + Constants.MAIN_VERSION_TAG);
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer errorCode, String msg) {
                    promise.reject(errorCode + Constants.MAIN_VERSION_TAG, msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getOperatersWithAreaId(String areaId, final Promise promise) {
        try {
            KookongSDK.getOperaters(Integer.valueOf(areaId).intValue(), new IRequestResult<SpList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.8
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(String msg, SpList result) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("spList", result.spList);
                    promise.resolve(JSON.toJSONString(map));
                    XcLogger.e("RNVersatileInfraredModule", "msg" + msg);
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer errorCode, String msg) {
                    promise.reject(errorCode + Constants.MAIN_VERSION_TAG, msg);
                    XcLogger.e("RNVersatileInfraredModule", "errorCode" + errorCode + ",msg" + msg);
                    ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "加载失败,请重试!");
                    if (msg.equals("code 6")) {
                        RNVersatileInfraredModule.this.initKuKongSDK();
                        XcLogger.e("RNVersatileInfraredModule", "getOperatersWithAreaId获取运营商数据次数超限," + msg);
                        if (RNVersatileInfraredModule.this.mCount >= 2) {
                            ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "获取运营商数据次数超限");
                        }
                    }
                    if (errorCode.intValue() == -2) {
                        ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "设备总数超过了授权的额度");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getIPTVWithSpId(String spId, final Promise promise) {
        try {
            KookongSDK.getIPTV(Integer.valueOf(spId).intValue(), new IRequestResult<StbList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.9
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(String msg, StbList result) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("stbList", result.stbList);
                    promise.resolve(JSON.toJSONString(map));
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer errorCode, String msg) {
                    promise.reject(errorCode + Constants.MAIN_VERSION_TAG, msg);
                    ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "加载失败,请重试!");
                    if (msg.equals("code 6")) {
                        RNVersatileInfraredModule.this.initKuKongSDK();
                        XcLogger.e("RNVersatileInfraredModule", "getOperatersWithAreaId获取运营商数据次数超限," + msg);
                        ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "请求受限，请不要频繁更换地址");
                    }
                    if (errorCode.intValue() == -2) {
                        ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "设备总数超过了授权的额度");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getJDHRidsWithAreaId(String areaId, String spId, final String dId, String bId, final Promise promise) {
        try {
            final Map<String, Object> map = new HashMap<>();
            KookongSDK.getAllRemoteIds(Integer.valueOf(dId).intValue(), Integer.valueOf(bId).intValue(), Integer.valueOf(spId).intValue(), Integer.valueOf(areaId).intValue(), new IRequestResult<RemoteList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.10
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(String msg, RemoteList result) {
                    List<Integer> rids = result.rids;
                    if (rids != null && rids.size() > 0) {
                        String remoteIds = RNVersatileInfraredModule.this.listToStr(rids);
                        RNVersatileInfraredModule.this.singleMatch.getMatchKey(Integer.valueOf(dId).intValue(), remoteIds, false, new ISingleMatchResult() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.10.1
                            @Override // com.hzy.tvmao.interf.ISingleMatchResult
                            public void onMatchedIR(String s) {
                                map.put("matchKeysArray", null);
                                map.put("matchType", 0);
                                map.put("remoteid", s);
                                promise.resolve(JSON.toJSONString(map));
                            }

                            @Override // com.hzy.tvmao.interf.ISingleMatchResult
                            public void onNextGroupKey(List<RcTestRemoteKeyV3> list) {
                                map.put("matchKeysArray", list);
                                map.put("matchType", 1);
                                map.put("remoteid", null);
                                promise.resolve(JSON.toJSONString(map));
                            }

                            @Override // com.hzy.tvmao.interf.ISingleMatchResult
                            public void onNotMatchIR() {
                                map.put("matchKeysArray", null);
                                map.put("matchType", 2);
                                map.put("remoteid", null);
                                promise.resolve(JSON.toJSONString(map));
                            }

                            @Override // com.hzy.tvmao.interf.ISingleMatchResult
                            public void onError() {
                                map.put("matchKeysArray", null);
                                map.put("matchType", 3);
                                map.put("remoteid", null);
                                promise.resolve(JSON.toJSONString(map));
                            }
                        });
                    } else {
                        map.put("matchKeysArray", null);
                        map.put("matchType", 3);
                        map.put("remoteid", null);
                        promise.resolve(JSON.toJSONString(map));
                    }
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer errorCode, String msg) {
                    map.put("matchKeysArray", null);
                    map.put("matchType", 3);
                    map.put("remoteid", null);
                    promise.resolve(JSON.toJSONString(map));
                    if (errorCode.intValue() == -3) {
                        RNVersatileInfraredModule.this.initKuKongSDK();
                        XcLogger.e("RNVersatileInfraredModule", "下载设备总数据超过五十套");
                    } else if (errorCode.intValue() == -2) {
                        ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "设备总数超过了授权的额度");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getJDHAllKeyMatchRDataWithAreaId(String areaId, String spId, final String dId, String bId, final Promise promise) {
        try {
            XcLogger.i("RNVersatileInfraredModule", "bId" + bId);
            new HashMap();
            KookongSDK.getAllRemoteIds(Integer.valueOf(dId).intValue(), Integer.valueOf(bId).intValue(), Integer.valueOf(spId).intValue(), Integer.valueOf(areaId).intValue(), new IRequestResult<RemoteList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.11
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(final String msg, RemoteList result) {
                    List<Integer> rids = result.rids;
                    if (rids != null && rids.size() > 0) {
                        String remoteIds = RNVersatileInfraredModule.this.listToStr(rids);
                        XcLogger.i("RNVersatileInfraredModule", "remoteIds" + remoteIds);
                        KookongSDK.testIRDataById(remoteIds, Integer.valueOf(dId).intValue(), new IRequestResult<IrDataList>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNVersatileInfraredModule.11.1
                            @Override // com.hzy.tvmao.interf.IRequestResult
                            public void onSuccess(String s, IrDataList irDataList) {
                                List<IrData> irDatas = irDataList.getIrDataList();
                                List<Map<String, Object>> irString = new ArrayList<>();
                                for (IrData irData : irDatas) {
                                    HashMap<String, String> tempExts = new HashMap<>();
                                    for (Object map : irData.exts.entrySet()) {
                                        tempExts.put(String.valueOf(((Map.Entry) map).getKey()), ((Map.Entry) map).getValue());
                                    }
                                    List<HashMap<String, Object>> list = new ArrayList<>();
                                    if (irData.keys != null) {
                                        for (IrData.IrKey map2 : irData.keys) {
                                            HashMap<String, Object> tempKey = new HashMap<>();
                                            tempKey.put("dcode", map2.dcode);
                                            tempKey.put("fid", Integer.valueOf(map2.fid));
                                            tempKey.put("fkey", map2.fkey);
                                            tempKey.put("fname", map2.fname);
                                            tempKey.put("pulse", map2.pulse);
                                            tempKey.put("scode", map2.scode);
                                            list.add(tempKey);
                                        }
                                    }
                                    Map<String, Object> map3 = new HashMap<>();
                                    map3.put("rid", Integer.valueOf(irData.rid));
                                    map3.put("type", Short.valueOf(irData.type));
                                    map3.put("exts", JSON.toJSONString(tempExts));
                                    map3.put("keys", list);
                                    map3.put("fre", Integer.valueOf(irData.fre));
                                    irString.add(map3);
                                    XcLogger.i("RNVersatileInfraredModule", map3);
                                }
                                promise.resolve(JSON.toJSONString(irString));
                            }

                            @Override // com.hzy.tvmao.interf.IRequestResult
                            public void onFail(Integer errorCode, String s) {
                                promise.reject(String.valueOf(errorCode), msg);
                                if (msg.equals("code 6") || msg.equals("code 8") || msg.equals("code 10")) {
                                    RNVersatileInfraredModule.this.initKuKongSDK();
                                    XcLogger.e("RNVersatileInfraredModule", "下载设备总数据超过五十套");
                                }
                                ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "加载失败,请重试!");
                                if (errorCode.intValue() == -2) {
                                    ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "设备总数超过了授权的额度");
                                }
                            }
                        });
                        return;
                    }
                    promise.reject(Constants.MAIN_VERSION_TAG);
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer errorCode, String msg) {
                    promise.reject(String.valueOf(errorCode), msg);
                    ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "加载失败,请重试!");
                    if (msg.equals("code 8") || msg.equals("code 10") || errorCode.intValue() == -3) {
                        RNVersatileInfraredModule.this.initKuKongSDK();
                        XcLogger.e("RNVersatileInfraredModule", "downloadNoStateIRDataById下载设备总数据超过五十套," + msg);
                        if (RNVersatileInfraredModule.this.mCount >= 2) {
                            ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "红外设备下载总数据超过限制");
                        }
                    }
                    if (errorCode.intValue() == -2) {
                        ToastUtils.showShort(RNVersatileInfraredModule.this.mContext, "设备总数超过了授权的额度");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getACWindState(Promise promise) {
        try {
            int windDirect = this.mKKACManager.getCurUDDirect();
            XcLogger.i("RNVersatileInfraredModule", "getCurUDDirect" + windDirect);
            promise.resolve(windDirect + Constants.MAIN_VERSION_TAG);
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setACStatus(String power, String mode, String windPower, String temp, String windStatus) {
        try {
            XcLogger.i("RNVersatileInfraredModule", "setACStatus:power:" + power + ",mode:" + mode + ",windPower:" + windPower + ",temp:" + temp + ",windStatus:" + windStatus);
            int powerState = this.mKKACManager.getPowerState();
            if ("1".equals(power)) {
                if (powerState != 0) {
                    this.mKKACManager.changePowerState();
                }
            } else if (powerState == 0) {
                this.mKKACManager.changePowerState();
            }
            if ("1".equals(mode)) {
                this.mKKACManager.changeACTargetModel(1);
            } else if (PushConstants.PUSH_TYPE_NOTIFY.equals(mode)) {
                this.mKKACManager.changeACTargetModel(0);
            } else if ("3".equals(mode)) {
                this.mKKACManager.changeACTargetModel(3);
            } else if ("4".equals(mode)) {
                this.mKKACManager.changeACTargetModel(4);
            } else if ("2".equals(mode)) {
                this.mKKACManager.changeACTargetModel(2);
            }
            if ("1".equals(windPower)) {
                this.mKKACManager.setTargetWindSpeed(1);
            } else if ("2".equals(windPower)) {
                this.mKKACManager.setTargetWindSpeed(2);
            } else if ("3".equals(windPower)) {
                this.mKKACManager.setTargetWindSpeed(3);
            } else if (PushConstants.PUSH_TYPE_NOTIFY.equals(windPower)) {
                this.mKKACManager.setTargetWindSpeed(0);
            }
            this.mKKACManager.setTargetTemp(Integer.valueOf(temp).intValue());
            this.mKKACManager.setTargetUDWindDirect(Integer.valueOf(windStatus).intValue());
            XcLogger.i("RNVersatileInfraredModule", "setACStatus:" + this.mKKACManager.getACStateV2InString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String listToStr(List<Integer> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(list.get(i));
            if (i < list.size() - 1) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initKuKongSDK() {
        int a = ((Integer) SpUtils.getFromLocal(this.mContext, "kukong_device_Id", this.mDeviceId, 0)).intValue();
        XcLogger.i("RNVersatileInfraredModule", "initKuKongSDK a++:" + (a + 1) + ",a:" + a);
        if (a >= 2) {
            SpUtils.saveToLocal(this.mContext, "kukong_device_Id", this.mDeviceId, 2);
        } else {
            SpUtils.saveToLocal(this.mContext, "kukong_device_Id", this.mDeviceId, Integer.valueOf(a + 1));
        }
        initialKookongSDKWithDeviceId(this.mDeviceId);
    }
}
