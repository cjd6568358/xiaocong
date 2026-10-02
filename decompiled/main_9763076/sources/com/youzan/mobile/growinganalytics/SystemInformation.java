package com.youzan.mobile.growinganalytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    */
/* JADX INFO: compiled from: SystemInformation.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class SystemInformation {
    private final Integer appVersionCode;
    private final String appVersionName;
    private final Context context;
    private final DisplayMetrics displayMetrics;
    private final Boolean hasNFC;
    private final Boolean hasTelephony;

    /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.TypeCastException */
    public SystemInformation(Context _ctx) throws TypeCastException {
        Intrinsics.checkParameterIsNotNull(_ctx, "_ctx");
        this.context = _ctx;
        PackageManager pkgManager = this.context.getPackageManager();
        String v_name = (String) null;
        Integer v_code = (Integer) null;
        try {
            PackageInfo pkgInfo = pkgManager.getPackageInfo(this.context.getPackageName(), 0);
            v_name = pkgInfo.versionName;
            v_code = Integer.valueOf(pkgInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
        }
        this.appVersionName = v_name;
        this.appVersionCode = v_code;
        Method hasSystemFeatureMethod = (Method) null;
        try {
            hasSystemFeatureMethod = pkgManager.getClass().getMethod("hasSystemFeature", String.class);
        } catch (NoSuchMethodException e2) {
        }
        Boolean foundNFC = (Boolean) null;
        Boolean foundTelephony = (Boolean) null;
        if (hasSystemFeatureMethod != null) {
            try {
                Object objInvoke = hasSystemFeatureMethod.invoke(pkgManager, "android.hardware.nfc");
                if (objInvoke == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
                }
                foundNFC = (Boolean) objInvoke;
                Object objInvoke2 = hasSystemFeatureMethod.invoke(pkgManager, "android.hardware.telephony");
                if (objInvoke2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
                }
                foundTelephony = (Boolean) objInvoke2;
            } catch (IllegalAccessException e3) {
            } catch (InvocationTargetException e4) {
            }
        }
        this.hasNFC = foundNFC;
        this.hasTelephony = foundTelephony;
        this.displayMetrics = new DisplayMetrics();
        Object systemService = this.context.getSystemService("window");
        if (systemService == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
        }
        Display display = ((WindowManager) systemService).getDefaultDisplay();
        display.getMetrics(this.displayMetrics);
    }

    public final String getAppVersionName() {
        return this.appVersionName;
    }

    public final DisplayMetrics getMetrics() {
        return this.displayMetrics;
    }
}
