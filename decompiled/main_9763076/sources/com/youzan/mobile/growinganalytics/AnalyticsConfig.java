package com.youzan.mobile.growinganalytics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: AnalyticsConfig.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class AnalyticsConfig {
    private static AnalyticsConfig _instance;
    private String appId;
    private final String appSecret;
    private final long dataExpiration;
    private final String dataServerUrl;
    private final long flushInterval;
    private final boolean isSendAppOpen;
    private final long minDatabaseLimit;
    private final int minSessionDuration;
    private OfflineMode offlineMode;
    private final long requestMaxLength;
    private final int sessionTimeoutDuration;
    private SSLSocketFactory sslFactory;
    public static final Companion Companion = new Companion(null);
    private static final String ANALYTICS_PREFS_NAME = "com.youzan.mobile.AnalyticsPrefs";
    private static final String ANALYTICS_SERVER_ADDRESS = "https://tj.youzanyun.com/v3/log";
    private static final Object instanceLock = new Object();

    /* JADX INFO: compiled from: AnalyticsConfig.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final String getANALYTICS_PREFS_NAME() {
            return AnalyticsConfig.ANALYTICS_PREFS_NAME;
        }

        public final String getANALYTICS_SERVER_ADDRESS() {
            return AnalyticsConfig.ANALYTICS_SERVER_ADDRESS;
        }

        private final Object getInstanceLock() {
            return AnalyticsConfig.instanceLock;
        }

        private final AnalyticsConfig get_instance() {
            return AnalyticsConfig._instance;
        }

        private final void set_instance(AnalyticsConfig analyticsConfig) {
            AnalyticsConfig._instance = analyticsConfig;
        }

        public final AnalyticsConfig readConfig(Context ctx) {
            Bundle configBundle;
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            Context context = ctx.getApplicationContext();
            String packageName = context.getPackageName();
            try {
                PackageManager packageManager = context.getPackageManager();
                ApplicationInfo appInfo = packageManager != null ? packageManager.getApplicationInfo(packageName, ParserConstants.LSHIFTASSIGN) : null;
                if (appInfo == null || (configBundle = appInfo.metaData) == null) {
                    configBundle = new Bundle();
                }
                Intrinsics.checkExpressionValueIsNotNull(context, "context");
                return new AnalyticsConfig(configBundle, context);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException("Can't configure AnalyticsSDK with package name " + packageName, e);
            }
        }

        public final AnalyticsConfig getInstance(Context ctx) {
            AnalyticsConfig analyticsConfig;
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            synchronized (getInstanceLock()) {
                if (AnalyticsConfig.Companion.get_instance() == null) {
                    AnalyticsConfig.Companion.set_instance(AnalyticsConfig.Companion.readConfig(ctx));
                }
                analyticsConfig = AnalyticsConfig.Companion.get_instance();
                if (analyticsConfig == null) {
                    Intrinsics.throwNpe();
                }
            }
            return analyticsConfig;
        }
    }

    public final long getFlushInterval() {
        return this.flushInterval;
    }

    public final long getMinDatabaseLimit() {
        return this.minDatabaseLimit;
    }

    public final int getMinSessionDuration() {
        return this.minSessionDuration;
    }

    public final int getSessionTimeoutDuration() {
        return this.sessionTimeoutDuration;
    }

    public final long getDataExpiration() {
        return this.dataExpiration;
    }

    public final String getDataServerUrl() {
        return this.dataServerUrl;
    }

    public final boolean isSendAppOpen() {
        return this.isSendAppOpen;
    }

    public final String getAppId() {
        return this.appId;
    }

    public final void setAppId(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.appId = str;
    }

    public final long getRequestMaxLength() {
        return this.requestMaxLength;
    }

    public AnalyticsConfig(Bundle configBundle, Context context) {
        Intrinsics.checkParameterIsNotNull(configBundle, "configBundle");
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.flushInterval = configBundle.getLong("com.youzan.mobile.Analytics.FlushInterval", 10000L);
        this.minSessionDuration = configBundle.getInt("com.youzan.mobile.Analytics.MinSessionDuration", 5000);
        String string = configBundle.getString("com.youzan.mobile.Analytics.AppId", Constants.MAIN_VERSION_TAG);
        Intrinsics.checkExpressionValueIsNotNull(string, "configBundle.getString(\"…ile.Analytics.AppId\", \"\")");
        this.appId = string;
        String string2 = configBundle.getString("com.youzan.mobile.Analytics.AppSecret", Constants.MAIN_VERSION_TAG);
        Intrinsics.checkExpressionValueIsNotNull(string2, "configBundle.getString(\"…Analytics.AppSecret\", \"\")");
        this.appSecret = string2;
        this.minDatabaseLimit = configBundle.getLong("com.youzan.mobile.Analytics.DbSizeLimit", 20971520L);
        this.dataExpiration = configBundle.getLong("com.youzan.mobile.Analytics.DataExpiration", 86400000L);
        String string3 = configBundle.getString("com.youzan.mobile.Analytics.DataServerUrl", Companion.getANALYTICS_SERVER_ADDRESS());
        Intrinsics.checkExpressionValueIsNotNull(string3, "configBundle.getString(\"…ANALYTICS_SERVER_ADDRESS)");
        this.dataServerUrl = string3;
        this.requestMaxLength = configBundle.getLong("com.youzan.mobile.Analytics.RequestMaxLength", 52428800L);
        this.sessionTimeoutDuration = configBundle.getInt("com.youzan.mobile.Analytics.SessionTimeoutDuration", Integer.MAX_VALUE);
        this.isSendAppOpen = configBundle.getBoolean("com.youzan.mobile.Analytics.IsSendAppOpen", false);
    }

    public final OfflineMode getOfflineMode() {
        return this.offlineMode;
    }

    public final SSLSocketFactory getSSLSocketFactory() {
        return this.sslFactory;
    }
}
