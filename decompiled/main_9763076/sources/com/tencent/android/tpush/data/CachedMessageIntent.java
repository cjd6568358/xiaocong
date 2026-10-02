package com.tencent.android.tpush.data;

import com.tencent.android.tpush.common.Constants;
import java.io.Serializable;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CachedMessageIntent implements Serializable {
    private static final long serialVersionUID = 1724218633838690967L;
    public long msgId;
    public String pkgName = Constants.MAIN_VERSION_TAG;
    public String intent = Constants.MAIN_VERSION_TAG;

    public boolean equals(Object obj) {
        if (obj instanceof CachedMessageIntent) {
            CachedMessageIntent cachedMessageIntent = (CachedMessageIntent) obj;
            return cachedMessageIntent.pkgName.equals(this.pkgName) && cachedMessageIntent.msgId == this.msgId;
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        return "CachedMessageIntent [pkgName=" + this.pkgName + ", msgId=" + this.msgId + "]";
    }
}
