package com.ixiaocong.smarthome.phone.android.complete.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.ixiaocong.smarthome.phone.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SinusoidProgressbar extends View {
    private float mAmplitude;
    private int mCenterX;
    private int mCenterY;
    private Paint mCirclePaint;
    private float mCoordinateX;
    private float mCoordinateY;
    private int mCycleTime;
    private int mDelayTime;
    private int mFrequency;
    private Handler mHandler;
    private float mInterval;
    private float mMoveSection;
    private int mProgress;
    private float mRadius;
    private float mRingSize;
    private Paint mSinePaint;
    private Path mSinePath;
    private int mWaterColor;

    public SinusoidProgressbar(Context context) {
        this(context, null);
    }

    public SinusoidProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SinusoidProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mHandler = new Handler() { // from class: com.ixiaocong.smarthome.phone.android.complete.view.SinusoidProgressbar.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SinusoidProgressbar.this.invalidate();
                SinusoidProgressbar.this.mHandler.sendEmptyMessageDelayed(0, SinusoidProgressbar.this.mDelayTime);
            }
        };
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SinusoidProgressbar);
            this.mCycleTime = typedArray.getInt(1, BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN);
            this.mFrequency = typedArray.getInt(2, 1);
            this.mAmplitude = typedArray.getDimension(0, 0.0f);
            this.mRingSize = typedArray.getDimension(4, 10.0f);
            this.mInterval = typedArray.getDimension(3, 0.0f);
            this.mWaterColor = typedArray.getColor(5, -16711936);
            typedArray.recycle();
        }
        this.mDelayTime = 25;
        this.mCirclePaint = new Paint();
        this.mCirclePaint.setColor(Color.parseColor("#00c27f"));
        this.mCirclePaint.setStyle(Paint.Style.STROKE);
        this.mCirclePaint.setStrokeWidth(this.mRingSize);
        this.mSinePaint = new Paint();
        this.mSinePaint.setColor(this.mWaterColor);
        this.mSinePaint.setStyle(Paint.Style.FILL);
        this.mSinePath = new Path();
        this.mHandler.sendEmptyMessageDelayed(0, this.mDelayTime);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mCenterX = w / 2;
        this.mCenterY = h / 2;
        this.mRadius = Math.min(this.mCenterX, this.mCenterY);
        this.mMoveSection = ((this.mRadius * 2.0f) * this.mDelayTime) / this.mCycleTime;
        if (this.mAmplitude == 0.0f) {
            this.mAmplitude = this.mRadius / 4.0f;
        }
        if (this.mInterval == 0.0f) {
            this.mInterval = this.mRadius / 2.0f;
        }
        if (this.mFrequency == 0) {
            this.mFrequency = 1;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mRadius > 0.0f) {
            if (this.mCoordinateX == 0.0f || this.mCoordinateX > this.mCenterX - this.mRadius) {
                this.mCoordinateX = this.mCenterX - (3.0f * this.mRadius);
            }
            this.mCoordinateY = this.mCenterY - ((this.mRadius * (this.mProgress - 50)) / 50.0f);
            float amplitude = (this.mAmplitude * (10000 - ((this.mProgress - 50) * (this.mProgress - 50)))) / 10000.0f;
            drawSineCurve(canvas, amplitude, 0.0f);
            drawSineCurve(canvas, amplitude, this.mInterval);
            canvas.drawCircle(this.mCenterX, this.mCenterY, this.mRadius, this.mCirclePaint);
            this.mCoordinateX += this.mMoveSection;
        }
    }

    private void drawSineCurve(Canvas canvas, float amplitude, float interval) {
        canvas.translate(-interval, 0.0f);
        int i = 0;
        this.mSinePath.moveTo(this.mCoordinateX + ((0 * this.mRadius) / this.mFrequency), this.mCoordinateY);
        while (i < this.mFrequency * 6) {
            this.mSinePath.quadTo(this.mCoordinateX + ((((i * 2) + 1) * this.mRadius) / (this.mFrequency * 2)), this.mCoordinateY + ((((i % 2) * 2) - 1) * amplitude), this.mCoordinateX + (((i + 1) * this.mRadius) / this.mFrequency), this.mCoordinateY);
            i++;
        }
        this.mSinePath.lineTo(this.mCoordinateX + ((i * this.mRadius) / this.mFrequency), this.mCenterY + this.mRadius);
        this.mSinePath.lineTo(this.mCoordinateX + (((i - (this.mFrequency * 6)) * this.mRadius) / this.mFrequency), this.mCenterY + this.mRadius);
        this.mSinePath.close();
        canvas.drawPath(this.mSinePath, this.mSinePaint);
        this.mSinePath.reset();
        canvas.translate(interval, 0.0f);
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }

    public void setAmplitude(float amplitude) {
        this.mAmplitude = amplitude;
    }

    public void setCycleTime(int cycleTime) {
        this.mCycleTime = cycleTime;
    }

    public void setFrequency(int frequency) {
        this.mFrequency = frequency;
    }

    public void setWaterColor(int waterColor) {
        this.mWaterColor = waterColor;
        this.mSinePaint.setColor(waterColor);
    }
}
