package android.support.design.widget;

import android.graphics.Outline;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class CircularBorderDrawableLollipop extends CircularBorderDrawable {
    CircularBorderDrawableLollipop() {
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        copyBounds(this.mRect);
        outline.setOval(this.mRect);
    }
}
