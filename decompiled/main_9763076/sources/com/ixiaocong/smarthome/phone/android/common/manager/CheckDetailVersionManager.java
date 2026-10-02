package com.ixiaocong.smarthome.phone.android.common.manager;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.android.common.utils.FileUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.RnCheckVersionCallback;
import com.ixiaocong.smarthome.phone.android.helper.db.RNVersionDBHelper;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.greendao.model.insert.RNVersionDB;
import com.xiaocong.smarthome.httplib.callback.DowloadCallback;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.DeviceDetailV2Model;
import com.xiaocong.smarthome.httplib.utils.ZIPUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.network.interfaces.IHttpRequest;
import com.xiaocong.smarthome.sdk.http.XCAsyncHttpClient;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.http.callback.XCDownloadFileCallBack;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CheckDetailVersionManager {
    IHttpRequest downloadRequest;

    public static CheckDetailVersionManager getInstance() {
        return CheckDetailVersionManagerHolder.INSTANCE;
    }

    public void checkedDownloadDetail(Context context, RnCheckVersionCallback checkVersionCallback, int productId, String deviceId) {
        checkedDownloadDetail(context, null, checkVersionCallback, productId, deviceId, 0);
    }

    public void checkedDownloadDetail(final Context context, final DowloadCallback callback, final RnCheckVersionCallback checkVersionCallback, final int productId, final String deviceId, final int position) {
        String versionCode;
        boolean isJSBundleExists = FileUtils.checkFileExists(context, "/ixiaocong/js/" + String.valueOf(productId) + ".jsbundle");
        if (isJSBundleExists && RNVersionDBHelper.loadAssign(productId) != null) {
            versionCode = RNVersionDBHelper.loadAssign(productId).getVersion();
        } else {
            versionCode = "0.0.0";
        }
        if (productId == 0) {
            if (checkVersionCallback != null) {
                checkVersionCallback.checkVersionCallback(3, null);
            }
            if (callback != null) {
                callback.dowloadFailureListener(position);
                return;
            }
            return;
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        paramsNoSign.put("rnVersion", versionCode);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("device/v2/detail");
        if (context instanceof Activity) {
            HttpLoadingHelper.getInstance().showProcessLoading(context);
        }
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.CheckDetailVersionManager.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                DeviceDetailV2Model deviceDetailV2Model = (DeviceDetailV2Model) JSON.parseObject(var1.getData(), DeviceDetailV2Model.class);
                if (PushConstants.PUSH_TYPE_NOTIFY.equals(deviceDetailV2Model.getRnInfo().getUpgrade())) {
                    if (checkVersionCallback != null) {
                        checkVersionCallback.checkVersionCallback(0, deviceDetailV2Model);
                    }
                    if (callback != null) {
                        callback.downloadFinishListener(position, false, deviceDetailV2Model);
                        return;
                    }
                    return;
                }
                if ("1".equals(deviceDetailV2Model.getRnInfo().getUpgrade())) {
                    RNVersionDB versionDB = new RNVersionDB(productId, deviceDetailV2Model.getRnInfo().getVersion());
                    RNVersionDBHelper.insertVersion(versionDB);
                    CheckDetailVersionManager.this.downloadFile(context, callback, position, deviceId, deviceDetailV2Model, checkVersionCallback);
                } else {
                    if ("2".equals(deviceDetailV2Model.getRnInfo().getUpgrade())) {
                        if (checkVersionCallback != null) {
                            checkVersionCallback.checkVersionCallback(2, deviceDetailV2Model);
                        }
                        if (callback != null) {
                            callback.downloadFinishListener(position, false, deviceDetailV2Model);
                            return;
                        }
                        return;
                    }
                    if (checkVersionCallback != null) {
                        checkVersionCallback.checkVersionCallback(2, deviceDetailV2Model);
                    }
                    if (callback != null) {
                        callback.downloadFinishListener(position, false, deviceDetailV2Model);
                    }
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                if (callback != null) {
                    callback.dowloadFailureListener(position);
                }
                if (checkVersionCallback != null) {
                    checkVersionCallback.checkVersionCallback(3, null);
                }
            }
        });
    }

    public void downloadFile(final Context context, final DowloadCallback callback, final int position, final String deviceId, final DeviceDetailV2Model deviceDetailV2Model, final RnCheckVersionCallback checkVersionCallback) {
        int productId = deviceDetailV2Model.getDeviceInfo().getProductId();
        String url = deviceDetailV2Model.getRnInfo().getDownloadUrl();
        if (productId == 0 || TextUtils.isEmpty(url)) {
            if (callback != null) {
                callback.dowloadFailureListener(position);
            }
            if (checkVersionCallback != null) {
                checkVersionCallback.checkVersionCallback(3, deviceDetailV2Model);
                return;
            }
            return;
        }
        final File outFile = new File(context.getFilesDir() + "/ixiaocong/js/" + productId + ".zip");
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setUrl(url);
        httpSetting.setDestinationFile(String.valueOf(outFile));
        httpSetting.setCallback(new XCDownloadFileCallBack() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.CheckDetailVersionManager.2
            public void onFailure(int statusCode, Map<String, String> headers, Throwable throwable) {
                XcLogger.e("downloadFile", "=============onFailure===============");
                if (callback != null) {
                    callback.dowloadFailureListener(position);
                }
                if (checkVersionCallback != null) {
                    checkVersionCallback.checkVersionCallback(3, null);
                }
            }

            public void onSuccess(int statusCode, Map<String, String> headers) {
                XcLogger.e("downloadFile", "downloadFile onSuccess");
                XcLogger.e("downloadFile", "onUIProgressFinish:");
                try {
                    ZIPUtils.UnZipFolder(outFile, context.getFilesDir() + "/ixiaocong/js");
                    if (callback != null) {
                        callback.downloadFinishListener(position, true, deviceDetailV2Model);
                    }
                    if (checkVersionCallback != null) {
                        checkVersionCallback.checkVersionCallback(1, deviceDetailV2Model);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.dowloadFailureListener(position);
                    }
                    if (checkVersionCallback != null) {
                        checkVersionCallback.checkVersionCallback(3, null);
                    }
                }
            }

            public void onProgress(int bytesWritten, int totalSize) {
                XcLogger.e("downloadFile", "totalBytes:" + totalSize);
                XcLogger.e("downloadFile", "downloadFile onProgress=" + ((int) (((bytesWritten * 1.0f) / totalSize) * 100.0f)));
                if (callback != null) {
                    callback.downloadSuccessListener((int) (((bytesWritten * 1.0f) / totalSize) * 100.0f), position, deviceId);
                }
            }

            public void onCancel() {
                super.onCancel();
                if (callback != null) {
                    callback.dowloadFailureListener(position);
                }
                if (checkVersionCallback != null) {
                    checkVersionCallback.checkVersionCallback(3, null);
                }
                XcLogger.e("downloadFile", "downloadFile onCancel");
            }
        });
        this.downloadRequest = XCAsyncHttpClient.downloadFile(context, httpSetting);
    }

    private static final class CheckDetailVersionManagerHolder {
        private static final CheckDetailVersionManager INSTANCE = new CheckDetailVersionManager();
    }
}
