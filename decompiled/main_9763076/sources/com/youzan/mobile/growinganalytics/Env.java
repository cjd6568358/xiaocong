package com.youzan.mobile.growinganalytics;

import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: RequestModel.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class Env {
    private final String appChannel;
    private final String appVersion;
    private final String deviceType;
    private final String ip;
    private final String networkType;
    private final String os;
    private final String osVersion;
    private final int screenHeight;
    private final int screenWidth;

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof Env)) {
                return false;
            }
            Env env = (Env) obj;
            if (!Intrinsics.areEqual(this.appVersion, env.appVersion) || !Intrinsics.areEqual(this.appChannel, env.appChannel) || !Intrinsics.areEqual(this.os, env.os) || !Intrinsics.areEqual(this.osVersion, env.osVersion) || !Intrinsics.areEqual(this.networkType, env.networkType) || !Intrinsics.areEqual(this.deviceType, env.deviceType)) {
                return false;
            }
            if (!(this.screenWidth == env.screenWidth)) {
                return false;
            }
            if (!(this.screenHeight == env.screenHeight) || !Intrinsics.areEqual(this.ip, env.ip)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        String str = this.appVersion;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.appChannel;
        int iHashCode2 = ((str2 != null ? str2.hashCode() : 0) + iHashCode) * 31;
        String str3 = this.os;
        int iHashCode3 = ((str3 != null ? str3.hashCode() : 0) + iHashCode2) * 31;
        String str4 = this.osVersion;
        int iHashCode4 = ((str4 != null ? str4.hashCode() : 0) + iHashCode3) * 31;
        String str5 = this.networkType;
        int iHashCode5 = ((str5 != null ? str5.hashCode() : 0) + iHashCode4) * 31;
        String str6 = this.deviceType;
        int iHashCode6 = ((((((str6 != null ? str6.hashCode() : 0) + iHashCode5) * 31) + this.screenWidth) * 31) + this.screenHeight) * 31;
        String str7 = this.ip;
        return iHashCode6 + (str7 != null ? str7.hashCode() : 0);
    }

    public String toString() {
        return "Env(appVersion=" + this.appVersion + ", appChannel=" + this.appChannel + ", os=" + this.os + ", osVersion=" + this.osVersion + ", networkType=" + this.networkType + ", deviceType=" + this.deviceType + ", screenWidth=" + this.screenWidth + ", screenHeight=" + this.screenHeight + ", ip=" + this.ip + ")";
    }

    public Env(String appVersion, String appChannel, String os, String osVersion, String networkType, String deviceType, int screenWidth, int screenHeight, String ip) {
        Intrinsics.checkParameterIsNotNull(appVersion, "appVersion");
        Intrinsics.checkParameterIsNotNull(appChannel, "appChannel");
        Intrinsics.checkParameterIsNotNull(os, "os");
        Intrinsics.checkParameterIsNotNull(osVersion, "osVersion");
        Intrinsics.checkParameterIsNotNull(networkType, "networkType");
        Intrinsics.checkParameterIsNotNull(deviceType, "deviceType");
        Intrinsics.checkParameterIsNotNull(ip, BDCloudMediaPlayer.OnNativeInvokeListener.ARG_IP);
        this.appVersion = appVersion;
        this.appChannel = appChannel;
        this.os = os;
        this.osVersion = osVersion;
        this.networkType = networkType;
        this.deviceType = deviceType;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.ip = ip;
    }

    public final JSONObject toJson() throws JSONException {
        JSONObject $receiver = new JSONObject();
        $receiver.put("av", this.appVersion);
        $receiver.put("ac", this.appChannel);
        $receiver.put("os", this.os);
        $receiver.put("osv", this.osVersion);
        $receiver.put("net", this.networkType);
        $receiver.put("dt", this.deviceType);
        $receiver.put("sw", this.screenWidth);
        $receiver.put("sh", this.screenHeight);
        $receiver.put(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_IP, this.ip);
        return $receiver;
    }
}
