package com.youzan.mobile.growinganalytics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ActivityLifecycleListener.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
@TargetApi(14)
public final class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {
    private final AnalyticsAPI analyticsAPI;
    private Runnable checker;
    private final AnalyticsConfig config;
    private String currentActivityName;
    private Handler handler;
    private boolean isForeground;
    private boolean isPaused;
    private int sessionBatchNo;
    private Long sessionStartTime;

    public ActivityLifecycleListener(AnalyticsAPI _analyticsAPI, AnalyticsConfig _config) {
        Intrinsics.checkParameterIsNotNull(_analyticsAPI, "_analyticsAPI");
        Intrinsics.checkParameterIsNotNull(_config, "_config");
        this.handler = new Handler(Looper.getMainLooper());
        this.isForeground = true;
        this.isPaused = true;
        this.analyticsAPI = _analyticsAPI;
        this.config = _config;
        resetSessionTime();
        Logger.Companion.d("session time reset from constructor");
    }

    private final void resetSessionTime() {
        this.sessionStartTime = Long.valueOf(System.currentTimeMillis());
        this.sessionBatchNo = 1;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        Logger.Companion.d(ActivityLifecycleListenerKt.TAG, "activity paused");
        this.isPaused = true;
        if (this.checker != null) {
            this.handler.removeCallbacks(this.checker);
        }
        if (AnalyticsAPI.Companion.isSendPageAction$growing_analytics_release()) {
            this.analyticsAPI.trackPageEnd(this.currentActivityName);
        }
        this.checker = new Runnable() { // from class: com.youzan.mobile.growinganalytics.ActivityLifecycleListener.onActivityPaused.1
            @Override // java.lang.Runnable
            public final void run() {
                if (ActivityLifecycleListener.this.isForeground && ActivityLifecycleListener.this.isPaused) {
                    ActivityLifecycleListener.this.isForeground = false;
                    Logger.Companion.d(ActivityLifecycleListenerKt.TAG, "activity not in foreground");
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    Long l = ActivityLifecycleListener.this.sessionStartTime;
                    long sessionLength = jCurrentTimeMillis - (l != null ? l.longValue() : 0L);
                    if (sessionLength >= ActivityLifecycleListener.this.config.getMinSessionDuration() && sessionLength < ActivityLifecycleListener.this.config.getSessionTimeoutDuration()) {
                        ActivityLifecycleListener.this.analyticsAPI.buildEvent$growing_analytics_release(AutoEvent.Session).track();
                    }
                    ActivityLifecycleListener.this.analyticsAPI.flush();
                }
            }
        };
        this.handler.postDelayed(this.checker, ActivityLifecycleListenerKt.getCHECK_DELAY());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.currentActivityName = activity != null ? getPageName(activity) : null;
        Logger.Companion.d(ActivityLifecycleListenerKt.TAG, "activity:" + this.currentActivityName + " resume");
        this.isPaused = false;
        boolean wasBackground = this.isForeground ? false : true;
        this.isForeground = true;
        if (this.checker != null) {
            this.handler.removeCallbacks(this.checker);
        }
        if (wasBackground) {
            Logger.Companion.d(ActivityLifecycleListenerKt.TAG, "session time reset from back");
            resetSessionTime();
        }
        if (AnalyticsAPI.Companion.isSendPageAction$growing_analytics_release()) {
            this.analyticsAPI.trackPageStart(this.currentActivityName);
        }
    }

    private final String getPageName(Activity $receiver) {
        return $receiver.getComponentName().getClassName();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public final Long getSessionStartTime() {
        return this.sessionStartTime;
    }

    public final int getSessionBatchNo() {
        int i;
        synchronized (Integer.valueOf(this.sessionBatchNo)) {
            i = this.sessionBatchNo;
        }
        return i;
    }

    public final void countSessionBatchNo() {
        synchronized (Integer.valueOf(this.sessionBatchNo)) {
            this.sessionBatchNo++;
        }
    }

    public final String getCurrentActivityName() {
        return this.currentActivityName;
    }
}
