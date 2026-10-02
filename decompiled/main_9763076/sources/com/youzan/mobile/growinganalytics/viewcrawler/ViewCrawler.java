package com.youzan.mobile.growinganalytics.viewcrawler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.tencent.android.tpush.common.Constants;
import com.youzan.mobile.growinganalytics.AnalyticsAPI;
import com.youzan.mobile.growinganalytics.AnalyticsConfig;
import com.youzan.mobile.growinganalytics.Logger;
import kotlin.Metadata;
import kotlin.TypeCastException;
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
/* JADX INFO: compiled from: ViewCrawler.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class ViewCrawler {
    private final ActivityViewsSet activitySet;
    private final AnalyticsAPI analyticsAPI;
    private final AnalyticsConfig config;
    private final Context context;

    /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.TypeCastException */
    public ViewCrawler(Context context, AnalyticsAPI analyticsAPI) throws TypeCastException {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(analyticsAPI, "analyticsAPI");
        this.context = context;
        this.analyticsAPI = analyticsAPI;
        this.config = AnalyticsConfig.Companion.getInstance(this.context);
        Context applicationContext = this.context.getApplicationContext();
        if (applicationContext == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.app.Application");
        }
        Application app = (Application) applicationContext;
        this.activitySet = new ActivityViewsSet();
        this.activitySet.setOnEventListener$growing_analytics_release(new MyEventListener());
        if (Build.VERSION.SDK_INT >= 14) {
            app.registerActivityLifecycleCallbacks(new LifecycleCallbacks());
        }
    }

    public final AnalyticsAPI getAnalyticsAPI() {
        return this.analyticsAPI;
    }

    /* JADX INFO: compiled from: ViewCrawler.kt */
    @Metadata
    public final class MyEventListener implements OnEventListener {
        public MyEventListener() {
        }

        @Override // com.youzan.mobile.growinganalytics.viewcrawler.OnEventListener
        public void onEvent(View host, String eventName, boolean debounce) {
            String viewId;
            String desc;
            Intrinsics.checkParameterIsNotNull(host, "host");
            Intrinsics.checkParameterIsNotNull(eventName, "eventName");
            if (AnalyticsAPI.Companion.isSendAutoEvent$growing_analytics_release()) {
                Logger.Companion.d("ViewCrawler", "View class:" + host.getClass().getCanonicalName() + " id:" + host.getId() + " click");
                try {
                    viewId = host.getResources().getResourceEntryName(host.getId());
                } catch (Exception e) {
                    viewId = null;
                }
                if (viewId != null) {
                    AnalyticsAPI.EventBuildDelegate eventBuilder = ViewCrawler.this.getAnalyticsAPI().buildEvent(viewId).type("click");
                    if (host instanceof Button) {
                        desc = ((Button) host).getText().toString();
                    } else if (host instanceof TextView) {
                        desc = ((TextView) host).getText().toString();
                    } else {
                        desc = host instanceof CompoundButton ? ((CompoundButton) host).getText().toString() : Constants.MAIN_VERSION_TAG;
                    }
                    eventBuilder.desc(desc).track();
                }
            }
        }
    }

    /* JADX INFO: compiled from: ViewCrawler.kt */
    @Metadata
    @TargetApi(14)
    public final class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        public LifecycleCallbacks() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            if (activity != null) {
                Logger.Companion.d("ViewCrawler", "Remove activity " + activity.getComponentName() + " to scan");
                ViewCrawler.this.activitySet.remove(activity);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            if (activity != null) {
                Logger.Companion.d("ViewCrawler", "Add activity " + activity.getComponentName() + " to scan");
                ViewCrawler.this.activitySet.add(activity);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle data) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle data) {
        }
    }
}
