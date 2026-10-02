package com.ixiaocong.smarthome.phone.rn.module.control;

import android.content.Context;
import android.content.Intent;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.ixiaocong.smarthome.phone.android.common.manager.CheckDetailVersionManager;
import com.ixiaocong.smarthome.phone.android.common.utils.NoDoubleClickUtils;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.EditDeviceDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.add.DeviceAddCategoryActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceDetailSettingActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.RnCheckVersionCallback;
import com.ixiaocong.smarthome.phone.rn.RNDetailActivity;
import com.ixiaocong.smarthome.phone.rn.callback.RNParameterCallback;
import com.ixiaocong.smarthome.phone.rn.init.RNCacheViewManager;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.DeviceDetailV2Model;
import net.sqlcipher.database.SQLiteDatabase;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNOthorControlModule extends ReactContextBaseJavaModule implements RnCheckVersionCallback, RNParameterCallback {
    private Context mContext;

    public RNOthorControlModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "OtherControl";
    }

    @ReactMethod
    public void finishActivity() {
        getCurrentActivity().finish();
    }

    @ReactMethod
    public void startSetting(String deviceName, String deviceId, int productId, int admin) {
        Intent intent = new Intent(this.mContext, (Class<?>) DeviceDetailSettingActivity.class);
        intent.putExtra("deviceName", deviceName);
        intent.putExtra(Constants.FLAG_DEVICE_ID, deviceId);
        intent.putExtra("productId", productId);
        intent.putExtra("is_admin", admin);
        intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        this.mContext.startActivity(intent);
    }

    @ReactMethod
    public void renameParameter(String parameterId, String parameterName, Callback callback) {
        EditDeviceDialog.renameParSetting(getCurrentActivity(), this, callback, parameterId, parameterName);
    }

    @ReactMethod
    public void startAddDeviceDetail(String deviceId) {
        Intent intent = new Intent(this.mContext, (Class<?>) DeviceAddCategoryActivity.class);
        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        intent.putExtra(Constants.FLAG_DEVICE_ID, deviceId);
        intent.putExtra("isGw", "isGw");
        this.mContext.startActivity(intent);
    }

    @ReactMethod
    public void startChildDetail(String deviceId, String deviceName, String clientId, String snapshot, int admin, int productId, int status) {
        if (!NoDoubleClickUtils.isDoubleClick()) {
            CheckDetailVersionManager.getInstance().checkedDownloadDetail(getCurrentActivity(), this, productId, deviceId);
        }
    }

    @ReactMethod
    public void startDetail(String productId, String deviceId) {
        try {
            if (!NoDoubleClickUtils.isDoubleClick()) {
                CheckDetailVersionManager.getInstance().checkedDownloadDetail(getCurrentActivity(), this, Integer.valueOf(productId).intValue(), deviceId);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void testPromise(String msg, Promise promise) {
        XcLogger.e("RNOthorControlModule", "testPromise---");
        String result = msg + "-------";
        promise.resolve(result);
    }

    private void startActivityForDetail(DeviceDetailV2Model deviceDetailV2Model, boolean isDownload) {
        Intent intent = new Intent(this.mContext, (Class<?>) RNDetailActivity.class);
        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        intent.putExtra(Constants.FLAG_DEVICE_ID, deviceDetailV2Model.getDeviceId());
        intent.putExtra("deviceName", deviceDetailV2Model.getDeviceInfo().getDeviceName());
        intent.putExtra("productId", deviceDetailV2Model.getDeviceInfo().getProductId());
        intent.putExtra("is_admin", deviceDetailV2Model.getDeviceInfo().getIsAdmin());
        intent.putExtra("clientId", Constants.MAIN_VERSION_TAG);
        intent.putExtra("deviceStatus", deviceDetailV2Model.getStatus());
        intent.putExtra("imgUrl", "file://" + this.mContext.getFilesDir() + "/ixiaocong/js/img/drawable-mdpi/");
        intent.putExtra("snapshotMsg", deviceDetailV2Model.getSnapshot());
        intent.putExtra("isNowDownload", isDownload);
        this.mContext.startActivity(intent);
    }

    public void onSuccess(Callback callback, Object resultString) {
    }

    @Override // com.ixiaocong.smarthome.phone.rn.callback.RNParameterCallback
    public void onRenameParamter(Callback callback, boolean isSuccess, String parameterId, String parameterName) {
        if (isSuccess) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("parameterId", Integer.valueOf(parameterId));
                jsonObject.put("parameterName", parameterName);
                callback.invoke(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.RnCheckVersionCallback
    public void checkVersionCallback(int checkCode, DeviceDetailV2Model deviceDetailV2Model) {
        if (checkCode == 0) {
            startActivityForDetail(deviceDetailV2Model, true);
            return;
        }
        if (checkCode == 1) {
            RNCacheViewManager.getInstance().removeCache(String.valueOf(deviceDetailV2Model.getDeviceInfo().getProductId()));
            startActivityForDetail(deviceDetailV2Model, false);
        } else if (checkCode == 2) {
            ToastUtils.showShort(this.mContext, "没有找到当前设备的配置文件");
        } else if (checkCode == 3) {
            ToastUtils.showShort(this.mContext, "下载失败,请稍后重试");
        }
    }
}
