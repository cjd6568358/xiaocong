package com.xiaocong.smarthome.timerRuler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Scroller;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ScaleScroller {
    private static int lastX;
    private float afterLenght;
    private float beforeLength;
    private Context context;
    private GestureDetector gestureDetector;
    private float lastDistanceX;
    private ScrollingListener listener;
    private float mScale;
    private Scroller scroller;
    private double time;
    private final int ON_FLING = 1;
    private Handler handler = new Handler() { // from class: com.xiaocong.smarthome.timerRuler.ScaleScroller.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            boolean isFinished = ScaleScroller.this.scroller.computeScrollOffset();
            int curX = ScaleScroller.this.scroller.getCurrX();
            int unused = ScaleScroller.lastX = curX;
            if (isFinished) {
                ScaleScroller.this.handler.sendEmptyMessage(1);
            } else {
                ScaleScroller.this.listener.onScrollFinished();
            }
        }
    };
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.xiaocong.smarthome.timerRuler.ScaleScroller.2
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (!ScaleScroller.this.isDouble) {
                int unused = ScaleScroller.lastX = 0;
                ScaleScroller.this.scroller.fling(0, 0, (int) (-velocityX), 0, -2147483647, Integer.MAX_VALUE, 0, 0);
                ScaleScroller.this.handler.sendEmptyMessage(1);
                return true;
            }
            return true;
        }
    };
    private boolean isDouble = false;
    private boolean isCanScroll = true;

    public interface ScrollingListener {
        void onScroll(int i);

        void onScrollFinished();

        void onZoom(float f, double d);

        void onZoomFinished();
    }

    public ScaleScroller(Context context, ScrollingListener listener) {
        this.context = context;
        this.listener = listener;
        init();
    }

    private void init() {
        this.gestureDetector = new GestureDetector(this.context, this.simpleOnGestureListener);
        this.gestureDetector.setIsLongpressEnabled(false);
        this.scroller = new Scroller(this.context);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            this.isDouble = false;
            this.scroller.forceFinished(true);
            lastX = (int) event.getX();
        } else if (event.getAction() == 2) {
            if (event.getPointerCount() == 1 && !this.isDouble && this.isCanScroll) {
                int distanceX = (int) (event.getX() - lastX);
                if (distanceX != 0 && Math.abs(Math.abs(distanceX) - Math.abs(this.lastDistanceX)) < 150.0f) {
                    this.listener.onScroll(distanceX);
                    lastX = (int) event.getX();
                    this.lastDistanceX = distanceX;
                }
            } else if (event.getPointerCount() == 2 && this.isDouble) {
                this.isCanScroll = false;
                this.afterLenght = getDistance(event);
                if (this.beforeLength == 0.0f) {
                    this.beforeLength = this.afterLenght;
                }
                float gapLenght = this.afterLenght - this.beforeLength;
                if (Math.abs(gapLenght) > 5.0f) {
                    this.mScale = this.afterLenght / this.beforeLength;
                    this.listener.onZoom(this.mScale, this.time);
                    this.beforeLength = this.afterLenght;
                }
            }
        } else if (event.getAction() == 1) {
            if (event.getPointerCount() == 1 && !this.isDouble) {
                this.listener.onScrollFinished();
            } else if (this.isDouble) {
                new Timer().schedule(new TimerTask() { // from class: com.xiaocong.smarthome.timerRuler.ScaleScroller.3
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        ScaleScroller.this.isCanScroll = true;
                    }
                }, 500L);
                this.listener.onZoomFinished();
            }
        } else if ((event.getAction() & 255) == 5 && event.getPointerCount() == 2) {
            this.beforeLength = getDistance(event);
            this.isDouble = true;
        }
        this.gestureDetector.onTouchEvent(event);
        return true;
    }

    private float getDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }
}
