package com.ixiaocong.smarthome.phone.android.event.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.config.AppSpConstans;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XgTokenService extends Service {
    private static final String ACTION_START = "XgTokenService.START";
    private static final String ACTION_STOP = "XgTokenService.STOP";
    private static final String ACTION_UID = "XgTokenService.UID";
    private static final String XG_TAG = "XgTokenService";
    private Handler mHandler;
    private Message mXgMsg;

    public static void actionStart(Context context) {
        Intent i = new Intent(context, (Class<?>) XgTokenService.class);
        i.setAction(ACTION_START);
        context.startService(i);
    }

    public static void actionStop(Context context) {
        Intent i = new Intent(context, (Class<?>) XgTokenService.class);
        i.setAction(ACTION_STOP);
        context.startService(i);
    }

    public static void actionUid(Context context, String uid) {
        Intent i = new Intent(context, (Class<?>) XgTokenService.class);
        i.setAction(ACTION_UID);
        i.putExtra("uid", uid);
        context.startService(i);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String action = intent.getAction();
        XcLogger.i(XG_TAG, "Received action of " + action);
        if (action == null) {
            XcLogger.i(XG_TAG, "Starting service with no action Probably from a crash");
            return 3;
        }
        if (action.equals(ACTION_START)) {
            start();
            return 3;
        }
        if (action.equals(ACTION_STOP)) {
            stop();
            return 3;
        }
        if (action.equals(ACTION_UID)) {
            String uid = intent.getStringExtra("uid");
            if (!TextUtils.isEmpty(uid)) {
                registerUid(uid);
                return 3;
            }
            return 3;
        }
        return 3;
    }

    private void start() {
        XcLogger.i(XG_TAG, "start--------------");
        XGPushConfig.enableDebug(this, true);
        this.mHandler = new HandlerExtension(this);
        this.mXgMsg = this.mHandler.obtainMessage();
        AppSpConstans.getInstance().initConstans(getApplicationContext());
        initConfig();
    }

    private void initConfig() {
        XGPushConfig.setHuaweiDebug(true);
        XGPushConfig.setMiPushAppId(getApplicationContext(), "2882303761517767942");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "5281776757942");
        XGPushConfig.setMzPushAppId(this, "1000289");
        XGPushConfig.setMzPushAppKey(this, "e2ebedea91374ca78415f4d5fc87d433");
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        String uid = (String) SpUtils.getFromLocal(this, "xiao_cong_uid", "xiao_cong_uid", Constants.MAIN_VERSION_TAG);
        if (!TextUtils.isEmpty(uid)) {
            initUidXgPush(uid);
        } else {
            initOrdinaryXgPush();
        }
    }

    private void stop() {
        stopSelf();
    }

    private void registerUid(String uid) {
        if (this.mHandler == null) {
            this.mHandler = new HandlerExtension(this);
            this.mXgMsg = this.mHandler.obtainMessage();
        }
        initUidXgPush(uid);
    }

    private void initUidXgPush(final String uid) {
        XGPushManager.registerPush(getApplicationContext(), uid, new XGIOperateCallback() { // from class: com.ixiaocong.smarthome.phone.android.event.service.XgTokenService.1
            @Override // com.tencent.android.tpush.XGIOperateCallback
            public void onSuccess(Object data, int flag) {
                XcLogger.i(XgTokenService.XG_TAG, "+++ register push uid sucess. token---" + data + "///uid---//" + uid);
                XgTokenService.this.mXgMsg.obj = "register push uid sucess. token:" + data;
                XgTokenService.this.mXgMsg.sendToTarget();
            }

            @Override // com.tencent.android.tpush.XGIOperateCallback
            public void onFail(Object data, int errCode, String msg) {
                XcLogger.i(XgTokenService.XG_TAG, "+++ register push uid fail. token---" + data + ", errCode---" + errCode + ",msg---" + msg);
                XgTokenService.this.mXgMsg.obj = "+++ register push uid fail. token:" + data + ", errCode:" + errCode + ",msg:" + msg;
                XgTokenService.this.mXgMsg.sendToTarget();
            }
        });
    }

    private void initOrdinaryXgPush() {
        XGPushManager.registerPush(getApplicationContext(), new XGIOperateCallback() { // from class: com.ixiaocong.smarthome.phone.android.event.service.XgTokenService.2
            @Override // com.tencent.android.tpush.XGIOperateCallback
            public void onSuccess(Object data, int flag) {
                XcLogger.i(XgTokenService.XG_TAG, "+++ register push sucess. token---" + data + "///flag----" + flag);
                XgTokenService.this.mXgMsg.obj = "register push sucess. token:" + data;
                XgTokenService.this.mXgMsg.sendToTarget();
            }

            @Override // com.tencent.android.tpush.XGIOperateCallback
            public void onFail(Object data, int errCode, String msg) {
                XcLogger.i(XgTokenService.XG_TAG, "+++ register push fail. token---" + data + ", errCode---" + errCode + ",msg---" + msg);
                XgTokenService.this.mXgMsg.obj = "+++ register push fail. token:" + data + ", errCode:" + errCode + ",msg:" + msg;
                XgTokenService.this.mXgMsg.sendToTarget();
            }
        });
    }

    private static class HandlerExtension extends Handler {
        WeakReference<XgTokenService> mWeakReference;

        HandlerExtension(XgTokenService activity) {
            this.mWeakReference = new WeakReference<>(activity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            XgTokenService theService = this.mWeakReference.get();
            if (theService == null) {
                theService = new XgTokenService();
            }
            if (msg != null) {
                XcLogger.i(XgTokenService.XG_TAG, msg.obj.toString() + "-----" + XGPushConfig.getToken(theService));
                XgTokenService.uploadXgToken(theService, XGPushConfig.getToken(theService));
            } else {
                XgTokenService.actionStop(theService);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void uploadXgToken(final Context context, String token) {
        if (TextUtils.isEmpty(token)) {
            actionStop(context);
            return;
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("xingeToken", token);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("client/xinge/token");
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.event.service.XgTokenService.3
            public void onComplete(XCResponseBean var1) {
                XcLogger.i(XgTokenService.XG_TAG, "uploadXgToken------onSuccess");
                XgTokenService.actionStop(context);
            }

            public void onError(XCErrorMessage var1) {
                XcLogger.i(XgTokenService.XG_TAG, "uploadXgToken-------onFailure");
                XgTokenService.actionStop(context);
            }
        });
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }
}
