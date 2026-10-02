package com.xiaocong.smarthome.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.xiaocong.smarthome.uilib.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class BackgroundLayout extends FrameLayout {
    private float mCornerRadius;
    private Paint mPaint;
    private RectF mRect;

    public BackgroundLayout(Context context) {
        super(context);
        init();
    }

    public BackgroundLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackgroundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBaseColor(getContext().getResources().getColor(R.color.kprogresshud_default_color));
        setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
    }

    public void setCornerRadius(float radius) {
        this.mCornerRadius = Helper.dpToPixel(radius, getContext());
    }

    public void setBaseColor(int color) {
        this.mPaint = new Paint();
        this.mPaint.setColor(color);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mRect = new RectF(0.0f, 0.0f, w, h);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(this.mRect, this.mCornerRadius, this.mCornerRadius, this.mPaint);
    }
}
