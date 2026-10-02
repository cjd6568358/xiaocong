package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class TintInfo {
    public boolean mHasTintList;
    public boolean mHasTintMode;
    public ColorStateList mTintList;
    public PorterDuff.Mode mTintMode;

    TintInfo() {
    }

    void clear() {
        this.mTintList = null;
        this.mHasTintList = false;
        this.mTintMode = null;
        this.mHasTintMode = false;
    }
}
