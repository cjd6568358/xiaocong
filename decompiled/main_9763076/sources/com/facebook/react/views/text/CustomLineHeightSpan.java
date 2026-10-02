package com.facebook.react.views.text;

import android.graphics.Paint;
import android.text.style.LineHeightSpan;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CustomLineHeightSpan implements LineHeightSpan {
    private final int mHeight;

    CustomLineHeightSpan(float height) {
        this.mHeight = (int) Math.ceil(height);
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {
        if ((-fm.ascent) > this.mHeight) {
            int i = -this.mHeight;
            fm.ascent = i;
            fm.top = i;
            fm.descent = 0;
            fm.bottom = 0;
            return;
        }
        if ((-fm.ascent) + fm.descent > this.mHeight) {
            fm.top = fm.ascent;
            int i2 = this.mHeight + fm.ascent;
            fm.descent = i2;
            fm.bottom = i2;
            return;
        }
        if ((-fm.ascent) + fm.bottom > this.mHeight) {
            fm.top = fm.ascent;
            fm.bottom = fm.ascent + this.mHeight;
        } else {
            if ((-fm.top) + fm.bottom > this.mHeight) {
                fm.top = fm.bottom - this.mHeight;
                return;
            }
            int additional = this.mHeight - ((-fm.top) + fm.bottom);
            fm.top -= additional;
            fm.ascent -= additional;
        }
    }
}
