package com.ixiaocong.smarthome.phone.android.complete.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SearchIcon extends View {
    private ValueAnimator animatorDone;
    private ValueAnimator animatorSearching;
    private ValueAnimator animatorStart;
    private float bigRadii;
    private float end;
    private Handler handler;
    private float length;
    private Animator.AnimatorListener listener;
    private int mHeight;
    private int mWidth;
    private PathMeasure measureBig;
    private PathMeasure measureSearch;
    private Paint paintNormal;
    private Path pathBig;
    private Path pathEnd;
    private Path pathSearch;
    private Path pathSearching;
    private Path pathStart;
    private float[] pointXY;
    private float smallRadii;
    private float start;
    private TYPE type;
    private ValueAnimator.AnimatorUpdateListener upListener;
    private float varySet;

    enum TYPE {
        NONE,
        START,
        SEARCHING,
        DONE
    }

    public SearchIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.smallRadii = 55.0f;
        this.bigRadii = 115.0f;
        this.varySet = 0.0f;
        this.pointXY = new float[2];
        this.measureSearch = new PathMeasure();
        this.measureBig = new PathMeasure();
        this.type = TYPE.NONE;
        this.handler = new Handler() { // from class: com.ixiaocong.smarthome.phone.android.complete.view.SearchIcon.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    switch (AnonymousClass4.$SwitchMap$com$ixiaocong$smarthome$phone$android$complete$view$SearchIcon$TYPE[SearchIcon.this.type.ordinal()]) {
                        case 1:
                            SearchIcon.this.type = TYPE.START;
                            SearchIcon.this.animatorStart.start();
                            break;
                        case 2:
                            SearchIcon.this.type = TYPE.SEARCHING;
                            SearchIcon.this.animatorSearching.start();
                            break;
                        case 3:
                            SearchIcon.this.type = TYPE.DONE;
                            SearchIcon.this.animatorDone.start();
                            break;
                        case 4:
                            SearchIcon.this.type = TYPE.NONE;
                            SearchIcon.this.animatorStart.start();
                            break;
                    }
                }
            }
        };
        this.upListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.view.SearchIcon.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                SearchIcon.this.varySet = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SearchIcon.this.invalidate();
            }
        };
        this.listener = new Animator.AnimatorListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.view.SearchIcon.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SearchIcon.this.handler.sendEmptyMessage(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }
        };
        initPaint();
        initPath();
        initValueAnimator();
    }

    private void initValueAnimator() {
        this.animatorStart = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.animatorStart.addUpdateListener(this.upListener);
        this.animatorStart.setInterpolator(new LinearInterpolator());
        this.animatorStart.addListener(this.listener);
        this.animatorStart.setRepeatCount(0);
        this.animatorStart.setDuration(1500L);
        this.animatorSearching = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.animatorSearching.addUpdateListener(this.upListener);
        this.animatorSearching.setInterpolator(new LinearInterpolator());
        this.animatorSearching.addListener(this.listener);
        this.animatorSearching.setRepeatCount(2);
        this.animatorSearching.setDuration(2000L);
        this.animatorDone = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.animatorDone.addUpdateListener(this.upListener);
        this.animatorDone.setInterpolator(new LinearInterpolator());
        this.animatorDone.addListener(this.listener);
        this.animatorDone.setRepeatCount(0);
        this.animatorDone.setDuration(1500L);
    }

    private void initPath() {
        this.pathSearch = new Path();
        this.pathSearch.addArc(new RectF(-this.smallRadii, -this.smallRadii, this.smallRadii, this.smallRadii), 45.0f, 359.9f);
        this.pathBig = new Path();
        this.pathBig.addArc(new RectF(-this.bigRadii, -this.bigRadii, this.bigRadii, this.bigRadii), 45.0f, 359.9f);
        this.measureBig = new PathMeasure();
        this.measureBig.setPath(this.pathBig, false);
        this.measureBig.getPosTan(0.0f, this.pointXY, null);
        this.pathSearch.lineTo(this.pointXY[0], this.pointXY[1]);
        this.measureSearch = new PathMeasure();
        this.measureSearch.setPath(this.pathSearch, false);
        this.length = (float) (((3.141592653589793d * ((double) this.bigRadii)) * 2.0d) / 4.0d);
    }

    private void initPaint() {
        this.paintNormal = new Paint();
        this.paintNormal.setAntiAlias(true);
        this.paintNormal.setStrokeWidth(5.0f);
        this.paintNormal.setColor(-1);
        this.paintNormal.setStyle(Paint.Style.STROKE);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(this.mWidth / 2, this.mHeight / 2);
        drawSearch(canvas);
    }

    private void drawSearch(Canvas canvas) {
        switch (this.type) {
            case NONE:
                canvas.drawPath(this.pathSearch, this.paintNormal);
                break;
            case START:
                this.pathStart = new Path();
                this.measureSearch.getSegment(this.varySet * this.measureSearch.getLength(), this.measureSearch.getLength(), this.pathStart, true);
                canvas.drawPath(this.pathStart, this.paintNormal);
                break;
            case SEARCHING:
                this.end = this.measureBig.getLength() * this.varySet;
                this.start = (float) (((double) this.end) - ((0.5d - Math.abs(((double) this.varySet) - 0.5d)) * ((double) this.length)));
                this.pathSearching = new Path();
                this.measureBig.getSegment(this.start, this.end, this.pathSearching, true);
                canvas.drawPath(this.pathSearching, this.paintNormal);
                break;
            case DONE:
                this.pathEnd = new Path();
                this.measureSearch.getSegment(this.varySet * this.measureSearch.getLength(), this.measureSearch.getLength(), this.pathEnd, true);
                canvas.drawPath(this.pathEnd, this.paintNormal);
                break;
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    public void start() {
        if (this.type == TYPE.NONE) {
            this.handler.sendEmptyMessage(0);
        }
    }
}
