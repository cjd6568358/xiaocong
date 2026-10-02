package com.youzan.mobile.growinganalytics;

import com.meizu.cloud.pushsdk.notification.model.TimeDisplaySetting;
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
public final class PlatForm {
    private final String appId;
    private final String sdkType;
    private final String sdkVersion;

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof PlatForm) {
                PlatForm platForm = (PlatForm) obj;
                if (!Intrinsics.areEqual(this.appId, platForm.appId) || !Intrinsics.areEqual(this.sdkType, platForm.sdkType) || !Intrinsics.areEqual(this.sdkVersion, platForm.sdkVersion)) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.appId;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.sdkType;
        int iHashCode2 = ((str2 != null ? str2.hashCode() : 0) + iHashCode) * 31;
        String str3 = this.sdkVersion;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        return "PlatForm(appId=" + this.appId + ", sdkType=" + this.sdkType + ", sdkVersion=" + this.sdkVersion + ")";
    }

    public PlatForm(String appId, String sdkType, String sdkVersion) {
        Intrinsics.checkParameterIsNotNull(appId, "appId");
        Intrinsics.checkParameterIsNotNull(sdkType, "sdkType");
        Intrinsics.checkParameterIsNotNull(sdkVersion, "sdkVersion");
        this.appId = appId;
        this.sdkType = sdkType;
        this.sdkVersion = sdkVersion;
    }

    public final JSONObject toJson() throws JSONException {
        JSONObject $receiver = new JSONObject();
        $receiver.put(TimeDisplaySetting.START_SHOW_TIME, this.sdkType);
        $receiver.put("yai", this.appId);
        $receiver.put("sv", this.sdkVersion);
        return $receiver;
    }
}
