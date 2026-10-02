package com.tencent.android.tpush.data;

import com.tencent.android.tpush.common.k;
import java.io.Serializable;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RegisterEntity implements Serializable {
    public static final byte TYPE_REGISTER = 0;
    public static final byte TYPE_REMOTE_UNINSTALL = 4;
    public static final byte TYPE_REMOTE_UNREGISTER = 3;
    public static final byte TYPE_UNINSTALL = 2;
    public static final byte TYPE_UNREGISTER = 1;
    private static final long serialVersionUID = -7991157757568940717L;
    public long accessId;
    public String accessKey;
    public String appVersion;
    public long guid;
    public String packageName;
    public int state;
    public long timestamp;
    public String token;
    public float xgSDKVersion = 3.24f;

    public static String a(RegisterEntity registerEntity) {
        try {
            return k.a(registerEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RegisterEntity a(String str) {
        try {
            return (RegisterEntity) k.a(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean a() {
        return this.state == 0;
    }

    public boolean b() {
        return this.state == 1;
    }

    public boolean c() {
        return this.state == 2;
    }

    public String toString() {
        return "RegisterEntity [accessId=" + this.accessId + ", accessKey=" + this.accessKey + ", token=" + this.token + ", packageName=" + this.packageName + ", state=" + this.state + ", timestamp=" + this.timestamp + ", xgSDKVersion=" + this.xgSDKVersion + ", appVersion=" + this.appVersion + ", guid=" + this.guid + "]";
    }
}
