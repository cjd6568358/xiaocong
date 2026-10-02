package android.support.v7.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class CardViewApi17Impl extends CardViewBaseImpl {
    CardViewApi17Impl() {
    }

    @Override // android.support.v7.widget.CardViewBaseImpl, android.support.v7.widget.CardViewImpl
    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper = new RoundRectDrawableWithShadow.RoundRectHelper() { // from class: android.support.v7.widget.CardViewApi17Impl.1
            @Override // android.support.v7.widget.RoundRectDrawableWithShadow.RoundRectHelper
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint) {
                canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
            }
        };
    }
}
