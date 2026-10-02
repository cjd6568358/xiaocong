package com.tencent.android.tpush.data;

import java.io.Serializable;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UnregisterInfo implements Serializable {
    public static final byte TYPE_UNINSTALL = 1;
    public static final byte TYPE_UNREGISTER = 0;
    private static final long serialVersionUID = -2293449011577410421L;
    public long accessId;
    public String accessKey;
    public String appCert;
    public byte isUninstall;
    public String option;
    public String packName;
    public long timestamp;
    public String token;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------UnregisterInfo----------------\n").append("accessId=").append(this.accessId).append("\n").append("accesskey=").append(this.accessKey).append("\n").append("token=").append(this.token).append("\n").append("isUninstall=").append((int) this.isUninstall).append("\n").append("packName=").append(this.packName).append("\n").append("--------------UnregisterInfo End------------");
        return sb.toString();
    }
}
