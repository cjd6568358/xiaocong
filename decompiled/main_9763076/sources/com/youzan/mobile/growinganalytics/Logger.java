package com.youzan.mobile.growinganalytics;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
/* JADX INFO: compiled from: Util.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class Logger {
    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: Util.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final void e(String log) {
            e(null, log);
        }

        public final void d(String log) {
            d(null, log);
        }

        public final void e(String label, String log) {
            String str;
            String str2 = AnalyticsAPI.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            if (label == null) {
                str = Constants.MAIN_VERSION_TAG;
            } else {
                str = '[' + label + "] ";
            }
            StringBuilder sbAppend = sb.append(str);
            if (log == null) {
                log = Constants.MAIN_VERSION_TAG;
            }
            Log.e(str2, sbAppend.append((Object) log).toString());
        }

        public final void d(final String label, final String log) {
            if (AnalyticsAPI.Companion.isDebug$growing_analytics_release()) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.youzan.mobile.growinganalytics.Logger$Companion$d$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str;
                        String str2 = AnalyticsAPI.LOG_TAG;
                        StringBuilder sb = new StringBuilder();
                        Logger.Companion companion = Logger.Companion;
                        String str3 = label;
                        if (str3 == null) {
                            str = Constants.MAIN_VERSION_TAG;
                        } else {
                            str = '[' + str3 + "] ";
                        }
                        StringBuilder sbAppend = sb.append(str);
                        String str4 = log;
                        if (str4 == null) {
                            str4 = Constants.MAIN_VERSION_TAG;
                        }
                        Log.d(str2, sbAppend.append((Object) str4).toString());
                    }
                });
            }
        }
    }
}
