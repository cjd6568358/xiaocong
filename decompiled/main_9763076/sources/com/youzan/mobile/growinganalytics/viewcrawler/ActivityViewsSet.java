package com.youzan.mobile.growinganalytics.viewcrawler;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import java.util.LinkedHashMap;
import java.util.Map;
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
/* JADX INFO: compiled from: ActivityViewsStack.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class ActivityViewsSet extends UIThreadSet<Activity> {
    private OnEventListener listener;
    private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());
    private final Map<String, ViewVisitor> pagesVisitors = new LinkedHashMap();

    @Override // com.youzan.mobile.growinganalytics.viewcrawler.UIThreadSet
    public void add(Activity item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        super.add(item);
        scanActivitiesOnUiThread();
    }

    private final void scanActivitiesOnUiThread() {
        if (Intrinsics.areEqual(Thread.currentThread(), this.uiThreadHandler.getLooper().getThread())) {
            scanActivities();
        } else {
            this.uiThreadHandler.post(new Runnable() { // from class: com.youzan.mobile.growinganalytics.viewcrawler.ActivityViewsSet.scanActivitiesOnUiThread.1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityViewsSet $receiver = ActivityViewsSet.this;
                    $receiver.scanActivities();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void scanActivities() {
        for (Activity activity : getAll()) {
            String actName = activity.getClass().getCanonicalName();
            View rootView = activity.getWindow().getDecorView().getRootView();
            Intrinsics.checkExpressionValueIsNotNull(rootView, "rootView");
            OnEventListener listener$iv = this.listener;
            ViewVisitor it = listener$iv != null ? new ViewAccessibilityEventVisitor(rootView, "click", listener$iv, 1) : null;
            if (it != null) {
                Map<String, ViewVisitor> map = this.pagesVisitors;
                Intrinsics.checkExpressionValueIsNotNull(actName, "actName");
                map.put(actName, it);
            }
        }
    }

    public final void setOnEventListener$growing_analytics_release(OnEventListener _listener) {
        Intrinsics.checkParameterIsNotNull(_listener, "_listener");
        this.listener = _listener;
    }
}
