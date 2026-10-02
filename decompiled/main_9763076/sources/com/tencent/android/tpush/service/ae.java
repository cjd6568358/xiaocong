package com.tencent.android.tpush.service;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ae implements Comparable {
    public String a = Constants.MAIN_VERSION_TAG;
    public float b = 1.0f;
    public long c = 0;

    ae() {
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(ae aeVar) {
        if (this.b > aeVar.b) {
            return -1;
        }
        if (this.b < aeVar.b) {
            return 1;
        }
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("pkgName:").append(this.a).append(",accid:").append(this.c).append(",ver:").append(this.b);
        return sb.toString();
    }
}
