package com.ixiaocong.smarthome.phone.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.ixiaocong.smarthome.phone.android.crash.AppCrashHandler;
import com.ixiaocong.smarthome.phone.android.event.receiver.HttpReceiver;
import com.ixiaocong.smarthome.phone.rn.pack.RNDeviceControlPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNDeviceInfoPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNDeviceParameterPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNDoorLockModulePackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNInfraredTransmitModulePackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNMessagePackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNOthorControlPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNScenePanelPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNUtilsPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNVersatileInfraredPackage;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaocong.smarthome.LibApplication;
import com.xiaocong.smarthome.greendao.db.DaoMaster;
import com.xiaocong.smarthome.greendao.db.DaoSession;
import com.xiaocong.smarthome.httplib.config.AppSpConstans;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.sdk.XCSuperSDK;
import com.xiaocong.smarthome.util.XCActivityManager;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdk.basic.YouzanBasicSDKAdapter;
import java.util.Arrays;
import java.util.List;
import skin.support.SkinCompatManager;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XcApplication extends MultiDexApplication implements ReactApplication {
    private static XcApplication instance;
    private static DaoSession mDaoSession;
    private HttpReceiver mHttpReceiver;
    private IWXAPI mIWxAPI;
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) { // from class: com.ixiaocong.smarthome.phone.android.XcApplication.2
        @Override // com.facebook.react.ReactNativeHost
        public boolean getUseDeveloperSupport() {
            return false;
        }

        @Override // com.facebook.react.ReactNativeHost
        protected List<ReactPackage> getPackages() {
            return Arrays.asList(new MainReactPackage(), new RNDeviceControlPackage(), new RNDeviceInfoPackage(), new RNMessagePackage(), new RNOthorControlPackage(), new RNDeviceParameterPackage(), new RNDoorLockModulePackage(), new RNInfraredTransmitModulePackage(), new RNScenePanelPackage(), new RNUtilsPackage(), new RNVersatileInfraredPackage());
        }
    };
    private RefWatcher refWatcher;

    public static XcApplication getInstance() {
        return instance;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            instance = this;
            LibApplication.getInstance().init(this);
            XCSuperSDK.getInstance().init(this);
            CrashReport.initCrashReport(getApplicationContext());
            if (!LeakCanary.isInAnalyzerProcess(this)) {
                this.refWatcher = LeakCanary.install(this);
                if (this.mHttpReceiver == null) {
                    this.mHttpReceiver = new HttpReceiver();
                }
                LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.mHttpReceiver, new IntentFilter("XCSDK.HttpReceiver"));
                registerWX();
                YouzanSDK.init(this, "c16bd7900501b87245", new YouzanBasicSDKAdapter());
                SkinCompatManager.withoutActivity(this).addInflater(new SkinMaterialViewInflater()).addInflater(new SkinConstraintViewInflater()).setSkinStatusBarColorEnable(false).setSkinAllActivityEnable(false).loadSkin();
                new Thread(XcApplication$$Lambda$1.lambdaFactory$(this)).start();
            } else {
                return;
            }
        }
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.ixiaocong.smarthome.phone.android.XcApplication.1
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                XCActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        Process.setThreadPriority(10);
        AppCrashHandler.getInstance().init(getApplicationContext());
        SoLoader.init((Context) this, false);
        setupDatabase();
        initXgPush();
    }

    @Override // android.app.Application
    public void onTerminate() {
        if (this.mHttpReceiver != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mHttpReceiver);
        }
        super.onTerminate();
    }

    public IWXAPI registerWX() {
        this.mIWxAPI = WXAPIFactory.createWXAPI(getApplicationContext(), "wx0207ce9a1dff0963", true);
        this.mIWxAPI.registerApp("wx0207ce9a1dff0963");
        return this.mIWxAPI;
    }

    private void initXgPush() {
        if (isMainProcess()) {
            XGPushManager.setNotifactionCallback(XcApplication$$Lambda$2.instance);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initXgPush$1(XGNotifaction xgNotifaction) {
        XcLogger.i("receiver", "处理信鸽通知：" + xgNotifaction);
        xgNotifaction.getTitle();
        xgNotifaction.getContent();
        xgNotifaction.getCustomContent();
        xgNotifaction.doNotify();
    }

    public boolean isMainProcess() {
        ActivityManager am = (ActivityManager) getSystemService("activity");
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "ixiaocong.db", null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(database);
        mDaoSession = master.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public boolean isLogin(Context context) {
        return !TextUtils.isEmpty(AppSpConstans.getInstance().getToken(context));
    }

    @Override // com.facebook.react.ReactApplication
    public ReactNativeHost getReactNativeHost() {
        return this.mReactNativeHost;
    }

    public static RefWatcher getRefWatcher(Context context) {
        XcApplication application = (XcApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
