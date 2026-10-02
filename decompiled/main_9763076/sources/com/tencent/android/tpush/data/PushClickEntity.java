package com.tencent.android.tpush.data;

import com.tencent.android.tpush.common.Constants;
import java.io.Serializable;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PushClickEntity implements Serializable {
    private static final long serialVersionUID = -166678396447407161L;
    public long accessId;
    public int action;
    public long broadcastId;
    public long clickTime;
    public long msgId;
    public String pkgName;
    public long timestamp;
    public long type;

    public PushClickEntity() {
        this.msgId = 0L;
        this.accessId = 0L;
        this.broadcastId = 0L;
        this.timestamp = 0L;
        this.pkgName = Constants.MAIN_VERSION_TAG;
        this.type = 1L;
        this.clickTime = 0L;
        this.action = 0;
    }

    public PushClickEntity(long j, long j2, long j3, long j4, String str, long j5, long j6, int i) {
        this.msgId = 0L;
        this.accessId = 0L;
        this.broadcastId = 0L;
        this.timestamp = 0L;
        this.pkgName = Constants.MAIN_VERSION_TAG;
        this.type = 1L;
        this.clickTime = 0L;
        this.action = 0;
        this.msgId = j;
        this.accessId = j2;
        this.broadcastId = j3;
        this.timestamp = j4;
        this.pkgName = str;
        this.type = j5;
        this.clickTime = j6;
        this.action = i;
    }
}
