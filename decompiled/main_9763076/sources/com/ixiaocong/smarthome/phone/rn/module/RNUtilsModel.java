package com.ixiaocong.smarthome.phone.rn.module;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.ixiaocong.smarthome.phone.android.common.manager.LocationManager;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.EditDeviceDialog;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.LocationCallback;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LocationEvent;
import com.ixiaocong.smarthome.phone.rn.callback.RNParameterRenameCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.pickerview.PickerViewHelper;
import com.xiaocong.smarthome.pickerview.listener.OnTimeSelectListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNUtilsModel extends ReactContextBaseJavaModule {
    private Context mContext;

    public RNUtilsModel(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "RNUtilsModel";
    }

    @ReactMethod
    public void showDialog() {
        try {
            HttpLoadingHelper.getInstance().showProcessLoading(getCurrentActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void dismissDialog() {
        try {
            HttpLoadingHelper.getInstance().dismissProcessLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void toast(String msg) {
        try {
            ToastUtils.showShort(this.mContext, msg + Constants.MAIN_VERSION_TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void showEditDialog(String title, String hintText, final Callback callback) {
        try {
            EditDeviceDialog.renameParameterDialog(getCurrentActivity(), title, hintText, new RNParameterRenameCallback() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNUtilsModel.1
                @Override // com.ixiaocong.smarthome.phone.rn.callback.RNParameterRenameCallback
                public void neme(String name) {
                    callback.invoke(name);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void showHintDialog(String title, String msg, final Callback callback) {
        try {
            OperationHintDialog.getInstance().showHintDialog(getCurrentActivity(), new HintDialogCallback() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNUtilsModel.2
                @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
                public void hintDialogListener(boolean isSuccess) {
                    callback.invoke(new Object[0]);
                }
            }, title, msg, "确定");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void showSelectDialog(String title, String msg, final Callback callback) {
        try {
            OperationHintDialog.getInstance().showSelectDialog(getCurrentActivity(), new HintDialogCallback() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNUtilsModel.3
                @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
                public void hintDialogListener(boolean isSuccess) {
                    callback.invoke(new Object[0]);
                }
            }, title, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void getLocation(final Promise promise) {
        try {
            LocationManager.getInstance().getCurrentLocation(this.mContext, new LocationCallback() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNUtilsModel.4
                @Override // com.ixiaocong.smarthome.phone.android.event.callback.LocationCallback
                public void locationEvent(LocationEvent event) {
                    if (event != null && !TextUtils.isEmpty(event.getProvince()) && !TextUtils.isEmpty(event.getCityName()) && !TextUtils.isEmpty(event.getDistrict())) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("province", event.getProvince());
                        map.put("city", event.getCityName());
                        map.put("subLocality", event.getDistrict());
                        map.put("street", event.getStreet());
                        map.put(RNMessageModule.NAME, Constants.MAIN_VERSION_TAG);
                        map.put("latitude", event.getLat());
                        map.put("longitude", event.getLot());
                        promise.resolve(JSON.toJSONString(map));
                        XcLogger.i("RNUtilsModel", "getLocation:" + JSON.toJSONString(map));
                        return;
                    }
                    promise.reject("-1", "定位失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void saveToSp(String key, String value) {
        try {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                SpUtils.saveToLocal(getCurrentActivity(), "rn_sp_name", key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void getSp(String key, Promise promise) {
        try {
            if (!TextUtils.isEmpty(key)) {
                String str = (String) SpUtils.getFromLocal(getCurrentActivity(), "rn_sp_name", key, Constants.MAIN_VERSION_TAG);
                promise.resolve(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void showTimePicker(String title, String timeInMillis, String type, final Promise promise) {
        try {
            XcLogger.e("RNUtilsModel", type);
            if (!TextUtils.isEmpty(type)) {
                boolean[] types = new boolean[0];
                Calendar startDate = Calendar.getInstance();
                if (!TextUtils.isEmpty(timeInMillis)) {
                    long timeMillis = Long.valueOf(timeInMillis).longValue();
                    if (timeMillis > 0) {
                        startDate.setTimeInMillis(timeMillis);
                    }
                }
                if ("mm:ss".equals(type)) {
                    types = new boolean[]{false, false, false, false, true, true};
                    startDate.set(1970, 0, 0, 0, 0, 0);
                } else if ("HH:mm:ss".equals(type)) {
                    types = new boolean[]{false, false, false, true, true, true};
                } else if ("yyyy/MM/dd".equals(type)) {
                    types = new boolean[]{true, true, true, false, false, false};
                } else if ("HH:mm".equals(type)) {
                    types = new boolean[]{false, false, false, true, true, false};
                } else if ("yyyy/MM/dd HH:mm".equals(type)) {
                    types = new boolean[]{true, true, true, true, true, false};
                }
                PickerViewHelper.showCustomTimePiker(getCurrentActivity(), title, types, startDate, new OnTimeSelectListener() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNUtilsModel.5
                    public void onTimeSelect(Date date, View v) {
                        promise.resolve(date.getTime() + Constants.MAIN_VERSION_TAG);
                        XcLogger.e("RNUtilsModel", date.getTime() + Constants.MAIN_VERSION_TAG);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }
}
