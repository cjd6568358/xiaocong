package com.youzan.mobile.growinganalytics;

import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.tencent.android.tpush.common.Constants;
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
public final class UserInfo {
    private final String deviceId;
    private final long firstOpenTime;
    private String loginId;
    private String mobile;

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof UserInfo)) {
                return false;
            }
            UserInfo userInfo = (UserInfo) obj;
            if (!Intrinsics.areEqual(this.deviceId, userInfo.deviceId)) {
                return false;
            }
            if (!(this.firstOpenTime == userInfo.firstOpenTime) || !Intrinsics.areEqual(this.loginId, userInfo.loginId) || !Intrinsics.areEqual(this.mobile, userInfo.mobile)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        String str = this.deviceId;
        int iHashCode = str != null ? str.hashCode() : 0;
        long j = this.firstOpenTime;
        int i = ((iHashCode * 31) + ((int) (j ^ (j >>> 32)))) * 31;
        String str2 = this.loginId;
        int iHashCode2 = ((str2 != null ? str2.hashCode() : 0) + i) * 31;
        String str3 = this.mobile;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        return "UserInfo(deviceId=" + this.deviceId + ", firstOpenTime=" + this.firstOpenTime + ", loginId=" + this.loginId + ", mobile=" + this.mobile + ")";
    }

    public UserInfo(String deviceId, long firstOpenTime, String loginId, String mobile) {
        Intrinsics.checkParameterIsNotNull(deviceId, Constants.FLAG_DEVICE_ID);
        Intrinsics.checkParameterIsNotNull(loginId, "loginId");
        Intrinsics.checkParameterIsNotNull(mobile, "mobile");
        this.deviceId = deviceId;
        this.firstOpenTime = firstOpenTime;
        this.loginId = loginId;
        this.mobile = mobile;
    }

    public final JSONObject toJson() throws JSONException {
        JSONObject $receiver = new JSONObject();
        $receiver.put("did", this.deviceId);
        $receiver.put("ftime", this.firstOpenTime);
        $receiver.put(AppIconSetting.LARGE_ICON_URL, this.loginId);
        $receiver.put("m", this.mobile);
        return $receiver;
    }
}
