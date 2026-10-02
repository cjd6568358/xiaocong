package com.youzan.mobile.growinganalytics.viewcrawler;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
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
/* JADX INFO: compiled from: ViewVisitor.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class ViewAccessibilityEventVisitor extends ViewVisitor {
    private final int eventType;
    private final WeakHashMap<View, TrackingAccessibilityDelegate> watching;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewAccessibilityEventVisitor(View targetView, String eventName, OnEventListener listener, int eventType) {
        super(new ViewFinder(), eventName, listener, false);
        Intrinsics.checkParameterIsNotNull(targetView, "targetView");
        Intrinsics.checkParameterIsNotNull(eventName, "eventName");
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        this.eventType = eventType;
        this.watching = new WeakHashMap<>();
        getPathFinder().findTargetViewsInRoot(targetView, this);
    }

    @Override // com.youzan.mobile.growinganalytics.viewcrawler.Accumulator
    public void accumulate(View v) {
        Intrinsics.checkParameterIsNotNull(v, NotifyType.VIBRATE);
        if (Build.VERSION.SDK_INT >= 14) {
            View.AccessibilityDelegate oldDelegate = getOldDelegate(v);
            if (!(oldDelegate instanceof TrackingAccessibilityDelegate)) {
                oldDelegate = null;
            }
            TrackingAccessibilityDelegate $receiver = (TrackingAccessibilityDelegate) oldDelegate;
            if ($receiver != null) {
                $receiver.willFireEvent(getEventName());
                return;
            }
            TrackingAccessibilityDelegate newDelegate = new TrackingAccessibilityDelegate((View.AccessibilityDelegate) null);
            v.setAccessibilityDelegate(newDelegate);
            this.watching.put(v, newDelegate);
        }
    }

    /* JADX INFO: compiled from: ViewVisitor.kt */
    @Metadata
    @TargetApi(14)
    public final class TrackingAccessibilityDelegate extends View.AccessibilityDelegate {
        private final View.AccessibilityDelegate realDelegate;

        public TrackingAccessibilityDelegate(View.AccessibilityDelegate realDelegate) {
            this.realDelegate = realDelegate;
        }

        public final boolean willFireEvent(String eventName) {
            Intrinsics.checkParameterIsNotNull(eventName, "eventName");
            if (Intrinsics.areEqual(ViewAccessibilityEventVisitor.this.getEventName(), eventName)) {
                return true;
            }
            if (this.realDelegate instanceof TrackingAccessibilityDelegate) {
                return ((TrackingAccessibilityDelegate) this.realDelegate).willFireEvent(eventName);
            }
            return false;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void sendAccessibilityEvent(View host, int eventType) {
            if (host != null && ViewAccessibilityEventVisitor.this.eventType == eventType) {
                ViewAccessibilityEventVisitor.this.fireEvent(host);
            }
            if (this.realDelegate != null) {
                this.realDelegate.sendAccessibilityEvent(host, eventType);
            }
        }
    }

    @TargetApi(14)
    private final View.AccessibilityDelegate getOldDelegate(View v) {
        View.AccessibilityDelegate ret = (View.AccessibilityDelegate) null;
        try {
            Method m = v.getClass().getMethod("getAccessibilityDelegate", new Class[0]);
            Object objInvoke = m.invoke(v, new Object[0]);
            return (View.AccessibilityDelegate) (objInvoke instanceof View.AccessibilityDelegate ? objInvoke : null);
        } catch (IllegalAccessException e) {
            return ret;
        } catch (NoSuchMethodException e2) {
            return ret;
        } catch (InvocationTargetException e3) {
            return ret;
        }
    }
}
