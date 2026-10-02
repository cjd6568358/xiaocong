package com.xiaocong.smarthome.timerRuler;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import com.xiaocong.smarthome.timerRuler.bean.TimeSlot;
import com.xiaocong.smarthome.timerRuler.listener.OnBarMoveListener;
import com.xiaocong.smarthome.timerRuler.listener.OnSelectedTimeListener;
import com.xiaocong.smarthome.timerRuler.utils.CUtils;
import com.xiaocong.smarthome.timerRuler.utils.DateUtils;
import com.xiaocong.smarthome.uilib.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@TargetApi(14)
public class TimeRulerView extends TextureView implements TextureView.SurfaceTextureListener, ScaleScroller.ScrollingListener {
    private int centerLineColor;
    private Paint centerLinePaint;
    private int centerLineWidth;
    private boolean changeFlag;
    private long currentDateStartTimeMillis;
    private float currentSecond;
    private boolean isMoving;
    private boolean isProtrait;
    private boolean isSelectTimeArea;
    private boolean isZoom;
    private String keyText;
    private float keyTextWidth;
    private float keyTextX;
    private TextPaint keyTickTextPaint;
    private Paint largeRulerPaint;
    private float lastPix;
    private Context mContext;
    private Handler mHandler;
    private ScaleScroller mScroller;
    private OnBarMoveListener onBarMoveListener;
    private OnSelectedTimeListener onSelectedTimeListener;
    private float pixSecond;
    private int rulerColor;
    private int rulerHeightBig;
    private int rulerHeightSamll;
    private int rulerSpace;
    private int rulerWidthBig;
    private int rulerWidthSamll;
    private int scaleMode;
    private Timer scrollTimer;
    private Paint selectAreaPaint;
    private int selectTimeAreaColor;
    private int selectTimeBorderColor;
    private long selectTimeMax;
    private long selectTimeMin;
    private float selectTimeStrokeWidth;
    private Paint smallRulerPaint;
    private int textColor;
    private int textSize;
    private int upAndDownLineColor;
    private Paint upAndDownLinePaint;
    private int upAndDownLineWidth;
    private Paint vedioArea;
    private Paint vedioAreaPaint;
    private RectF vedioAreaRect;
    private int vedioBg;
    private List<TimeSlot> vedioTimeSlot;
    private int viewBackgroundColor;
    private int view_height;
    private static final int DEFAULT_RULER_SPACE = CUtils.dip2px(12.0f);
    private static final int MAX_SCALE = CUtils.dip2px(39.0f);
    private static final int MIN_SCALE = CUtils.dip2px(6.0f);
    private static float selectTimeAreaDistanceLeft = -1.0f;
    private static float selectTimeAreaDistanceRight = -1.0f;
    private static long lastPortraitTime = 0;
    private static long lastLandscapeTime = 0;

    public TimeRulerView(Context context) {
        this(context, null);
    }

    public TimeRulerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeRulerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.currentSecond = 0.0f;
        this.smallRulerPaint = new Paint();
        this.rulerColor = -4868683;
        this.rulerWidthSamll = CUtils.dip2px(0.5f);
        this.rulerHeightSamll = CUtils.dip2px(10.0f);
        this.rulerSpace = DEFAULT_RULER_SPACE;
        this.largeRulerPaint = new Paint();
        this.rulerWidthBig = CUtils.dip2px(0.5f);
        this.rulerHeightBig = CUtils.dip2px(20.0f);
        this.upAndDownLinePaint = new Paint();
        this.upAndDownLineWidth = CUtils.dip2px(1.0f);
        this.upAndDownLineColor = this.rulerColor;
        this.keyTickTextPaint = new TextPaint();
        this.textColor = -12303806;
        this.textSize = CUtils.dip2px(12.0f);
        this.centerLinePaint = new Paint();
        this.centerLineColor = -9527297;
        this.centerLineWidth = CUtils.dip2px(2.0f);
        this.vedioAreaPaint = new Paint();
        this.vedioBg = 862887935;
        this.vedioAreaRect = new RectF();
        this.selectAreaPaint = new Paint();
        this.selectTimeBorderColor = -345244;
        this.vedioArea = new Paint();
        this.selectTimeAreaColor = 872069988;
        this.selectTimeStrokeWidth = CUtils.dip2px(8.0f);
        this.view_height = CUtils.dip2px(166.0f);
        this.viewBackgroundColor = -1;
        this.isMoving = true;
        this.isSelectTimeArea = false;
        this.selectTimeMin = 60L;
        this.selectTimeMax = 600L;
        this.currentDateStartTimeMillis = DateUtils.getTodayStart(System.currentTimeMillis());
        this.vedioTimeSlot = new ArrayList();
        this.scaleMode = 1;
        this.mHandler = new Handler() { // from class: com.xiaocong.smarthome.timerRuler.TimeRulerView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 447:
                        if (TimeRulerView.this.onBarMoveListener != null) {
                            TimeRulerView.this.onBarMoveListener.onBarMoving(TimeRulerView.this.getCurrentTimeMillis());
                        }
                        break;
                    case 448:
                        if (TimeRulerView.this.onBarMoveListener != null) {
                            TimeRulerView.this.onBarMoveListener.onBarMoveFinish(TimeRulerView.this.getCurrentTimeMillis());
                        }
                        break;
                    case 480:
                        if (TimeRulerView.this.onBarMoveListener != null) {
                            TimeRulerView.this.onBarMoveListener.onMaxScale();
                        }
                        break;
                    case 944:
                        if (TimeRulerView.this.onBarMoveListener != null) {
                            TimeRulerView.this.onBarMoveListener.onMinScale();
                        }
                        break;
                }
            }
        };
        this.changeFlag = false;
        this.pixSecond = 0.0f;
        this.keyText = "";
        this.keyTextX = 0.0f;
        this.keyTextWidth = 0.0f;
        this.lastPix = 0.0f;
        this.isProtrait = true;
        this.mContext = context;
        initAttr(attrs, defStyle);
        this.mScroller = new ScaleScroller(getContext(), this);
        setSurfaceTextureListener(this);
        initPaint();
        moveTimer();
    }

    private void initPaint() {
        this.smallRulerPaint.setAntiAlias(true);
        this.smallRulerPaint.setColor(this.rulerColor);
        this.smallRulerPaint.setStrokeWidth(this.rulerWidthSamll);
        this.largeRulerPaint.setAntiAlias(true);
        this.largeRulerPaint.setColor(this.rulerColor);
        this.largeRulerPaint.setStrokeWidth(this.rulerWidthBig);
        this.keyTickTextPaint.setAntiAlias(true);
        this.keyTickTextPaint.setColor(this.textColor);
        this.keyTickTextPaint.setTextSize(this.textSize);
        this.centerLinePaint.setAntiAlias(true);
        this.centerLinePaint.setStrokeWidth(this.centerLineWidth);
        this.centerLinePaint.setColor(this.centerLineColor);
        this.vedioAreaPaint.setAntiAlias(true);
        this.vedioAreaPaint.setColor(this.vedioBg);
        this.upAndDownLinePaint.setAntiAlias(true);
        this.upAndDownLinePaint.setColor(this.upAndDownLineColor);
        this.upAndDownLinePaint.setStrokeWidth(this.upAndDownLineWidth);
        this.selectAreaPaint.setColor(this.selectTimeBorderColor);
        this.selectAreaPaint.setAntiAlias(true);
        this.selectAreaPaint.setStrokeCap(Paint.Cap.ROUND);
        this.selectAreaPaint.setStyle(Paint.Style.STROKE);
        this.selectAreaPaint.setStrokeWidth(this.selectTimeStrokeWidth);
        this.vedioArea.setColor(this.selectTimeAreaColor);
        this.vedioArea.setAntiAlias(true);
    }

    private void initAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TimeRulerView, defStyleAttr, 0);
        int attrCount = a.getIndexCount();
        for (int index = 0; index < attrCount; index++) {
            int attr = a.getIndex(index);
            if (attr == R.styleable.TimeRulerView_centerLineColor) {
                this.centerLineColor = a.getColor(attr, this.centerLineColor);
            } else if (attr == R.styleable.TimeRulerView_centerLineSize) {
                this.centerLineWidth = (int) a.getDimension(attr, this.centerLineWidth);
            } else if (attr == R.styleable.TimeRulerView_vedioAreaColor) {
                this.vedioBg = a.getColor(attr, this.vedioBg);
            } else if (attr == R.styleable.TimeRulerView_rulerTextColor) {
                this.textColor = a.getColor(attr, this.textColor);
            } else if (attr == R.styleable.TimeRulerView_viewBackgroundColor) {
                this.viewBackgroundColor = a.getColor(attr, this.viewBackgroundColor);
            } else if (attr == R.styleable.TimeRulerView_rulerTextSize) {
                this.textSize = (int) a.getDimension(attr, this.textSize);
            } else if (attr == R.styleable.TimeRulerView_rulerLineColor) {
                this.rulerColor = a.getColor(attr, this.rulerColor);
                this.upAndDownLineColor = this.rulerColor;
            } else if (attr == R.styleable.TimeRulerView_selectTimeBorderColor) {
                this.selectTimeBorderColor = a.getColor(attr, this.selectTimeBorderColor);
            } else if (attr == R.styleable.TimeRulerView_selectTimeAreaColor) {
                this.selectTimeAreaColor = a.getColor(attr, this.selectTimeAreaColor);
            } else if (attr == R.styleable.TimeRulerView_samllRulerLineWidth) {
                this.rulerWidthSamll = (int) a.getDimension(attr, this.rulerWidthSamll);
            } else if (attr == R.styleable.TimeRulerView_samllRulerLineHeight) {
                this.rulerHeightSamll = (int) a.getDimension(attr, this.rulerHeightSamll);
            } else if (attr == R.styleable.TimeRulerView_largeRulerLineWidth) {
                this.rulerWidthBig = (int) a.getDimension(attr, this.rulerWidthBig);
            } else if (attr == R.styleable.TimeRulerView_largeRulerLineHeight) {
                this.rulerHeightBig = (int) a.getDimension(attr, this.rulerHeightBig);
            } else if (attr == R.styleable.TimeRulerView_selectTimeBorderSize) {
                this.selectTimeStrokeWidth = a.getDimension(attr, this.selectTimeStrokeWidth);
            }
        }
        a.recycle();
    }

    public void setSelectTimeArea(boolean selectTimeArea) {
        if (selectTimeArea) {
            if (this.scaleMode == 2) {
                this.scaleMode = 1;
                this.rulerSpace = DEFAULT_RULER_SPACE;
            }
            this.isMoving = false;
        }
        selectTimeAreaDistanceLeft = -1.0f;
        selectTimeAreaDistanceRight = -1.0f;
        this.isSelectTimeArea = selectTimeArea;
        setCurrentTimeMillisNoDelayed(getCurrentTimeMillis());
    }

    public void setOnBarMoveListener(OnBarMoveListener onBarMoveListener) {
        this.onBarMoveListener = onBarMoveListener;
    }

    public void setOnSelectedTimeListener(OnSelectedTimeListener onSelectedTimeListener) {
        this.onSelectedTimeListener = onSelectedTimeListener;
    }

    public void setViewHeightForDp(int view_height) {
        this.view_height = CUtils.dip2px(view_height);
        refreshCanvas();
    }

    public void setCurrentTimeMillis(final long currentTimeMillis) {
        postDelayed(new Runnable() { // from class: com.xiaocong.smarthome.timerRuler.TimeRulerView.2
            @Override // java.lang.Runnable
            public void run() {
                int itemWidth = TimeRulerView.this.rulerWidthSamll + TimeRulerView.this.rulerSpace;
                TimeRulerView.this.getPixSecond(itemWidth);
                TimeRulerView.this.currentDateStartTimeMillis = DateUtils.getTodayStart(currentTimeMillis);
                TimeRulerView.this.lastPix = (-(((currentTimeMillis - TimeRulerView.this.currentDateStartTimeMillis) / 1000.0f) - ((TimeRulerView.this.getWidth() / 2.0f) * TimeRulerView.this.pixSecond))) / TimeRulerView.this.pixSecond;
                TimeRulerView.this.refreshCanvas();
            }
        }, 50L);
    }

    private void setCurrentTimeMillisNoDelayed(long currentTimeMillis) {
        int itemWidth = this.rulerWidthSamll + this.rulerSpace;
        getPixSecond(itemWidth);
        this.lastPix = (-(((currentTimeMillis - this.currentDateStartTimeMillis) / 1000.0f) - ((getWidth() / 2.0f) * this.pixSecond))) / this.pixSecond;
        refreshCanvas();
    }

    public long getCurrentTimeMillis() {
        return this.currentDateStartTimeMillis + (((long) this.currentSecond) * 1000);
    }

    private void moveTimer() {
        new Timer().schedule(new TimerTask() { // from class: com.xiaocong.smarthome.timerRuler.TimeRulerView.3
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (TimeRulerView.this.isMoving) {
                    if (TimeRulerView.this.scaleMode == 1) {
                        TimeRulerView.this.lastPix = (float) (((double) TimeRulerView.this.lastPix) - (((double) (TimeRulerView.this.rulerWidthSamll + TimeRulerView.this.rulerSpace)) / 60.0d));
                    } else {
                        TimeRulerView.this.lastPix = (float) (((double) TimeRulerView.this.lastPix) - (((double) (TimeRulerView.this.rulerWidthSamll + TimeRulerView.this.rulerSpace)) / 600.0d));
                    }
                    TimeRulerView.this.refreshCanvas();
                    if (TimeRulerView.this.onBarMoveListener != null) {
                        if (TimeRulerView.this.getCurrentTimeMillis() >= TimeRulerView.this.currentDateStartTimeMillis + 86400000) {
                            TimeRulerView.this.onBarMoveListener.onBarMoveFinish(TimeRulerView.this.getCurrentTimeMillis());
                            TimeRulerView.this.setMoving(false);
                        } else {
                            TimeRulerView.this.mHandler.sendEmptyMessage(447);
                        }
                    }
                }
            }
        }, 0L, 1000L);
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMoving(boolean moving) {
        this.isMoving = moving;
    }

    public void openMove() {
        if (!isMoving()) {
            setMoving(true);
        }
    }

    public void closeMove() {
        if (isMoving()) {
            setMoving(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCanvas() {
        Canvas canvas = lockCanvas();
        if (canvas != null) {
            canvas.drawColor(this.viewBackgroundColor);
            drawUpAndDownLine(canvas);
            drawTextAndRuler(canvas);
            drawRecodeArea(canvas);
            drawCenterLine(canvas);
            drawSelectTimeArea(canvas);
        }
        unlockCanvasAndPost(canvas);
    }

    private void drawSelectTimeArea(Canvas canvas) {
        if (this.isSelectTimeArea) {
            if (selectTimeAreaDistanceLeft == -1.0f) {
                selectTimeAreaDistanceLeft = ((((getCurrentTimeMillis() - this.currentDateStartTimeMillis) / this.pixSecond) / 1000.0f) - (150.0f / this.pixSecond)) + this.lastPix;
            }
            if (selectTimeAreaDistanceRight == -1.0f) {
                selectTimeAreaDistanceRight = (((getCurrentTimeMillis() - this.currentDateStartTimeMillis) / this.pixSecond) / 1000.0f) + (150.0f / this.pixSecond) + this.lastPix;
            }
            this.selectAreaPaint.setStrokeWidth(this.selectTimeStrokeWidth);
            canvas.drawLine(selectTimeAreaDistanceLeft, this.selectTimeStrokeWidth / 2.0f, selectTimeAreaDistanceLeft, (this.view_height - (this.textSize * 1.2f)) - (this.selectTimeStrokeWidth / 2.0f), this.selectAreaPaint);
            canvas.drawLine(selectTimeAreaDistanceRight, this.selectTimeStrokeWidth / 2.0f, selectTimeAreaDistanceRight, (this.view_height - (this.textSize * 1.2f)) - (this.selectTimeStrokeWidth / 2.0f), this.selectAreaPaint);
            this.selectAreaPaint.setStrokeWidth(this.selectTimeStrokeWidth / 3.0f);
            canvas.drawLine(selectTimeAreaDistanceRight, 0.0f, selectTimeAreaDistanceLeft, 0.0f, this.selectAreaPaint);
            this.selectAreaPaint.setStrokeWidth(this.selectTimeStrokeWidth / 4.0f);
            canvas.drawLine(selectTimeAreaDistanceRight, (this.view_height - (this.textSize * 1.2f)) - (this.selectTimeStrokeWidth / 6.0f), selectTimeAreaDistanceLeft, (this.view_height - (this.textSize * 1.2f)) - (this.selectTimeStrokeWidth / 6.0f), this.selectAreaPaint);
            canvas.drawRect(selectTimeAreaDistanceLeft, 0.0f, selectTimeAreaDistanceRight, this.view_height - (this.textSize * 1.2f), this.vedioArea);
            this.onSelectedTimeListener.onDragging(getSelectStartTime(), getSelectEndTime());
        }
    }

    public long getSelectEndTime() {
        return selectTimeAreaDistanceRight == -1.0f ? this.currentDateStartTimeMillis + ((long) (this.currentSecond * 1000.0f)) + 150000 : this.currentDateStartTimeMillis + ((long) ((selectTimeAreaDistanceRight - this.lastPix) * this.pixSecond * 1000.0f));
    }

    public long getSelectStartTime() {
        return selectTimeAreaDistanceLeft == -1.0f ? (this.currentDateStartTimeMillis + ((long) (this.currentSecond * 1000.0f))) - 150000 : this.currentDateStartTimeMillis + ((long) (((selectTimeAreaDistanceLeft + (this.selectTimeStrokeWidth / 2.0f)) - this.lastPix) * this.pixSecond * 1000.0f));
    }

    private void drawRecodeArea(Canvas canvas) {
        for (TimeSlot timeSlot : this.vedioTimeSlot) {
            if (timeSlot.getEndTime() > getScreenLeftTimeSeconde() && timeSlot.getStartTime() < getScreenRightTimeSeconde()) {
                float startX = getRightXByTimeSeconde(timeSlot.getStartTime()) + this.lastPix;
                if (startX < 0.0f) {
                    startX = 0.0f;
                }
                float endX = getRightXByTimeSeconde(timeSlot.getEndTime()) + this.lastPix;
                if (endX > getWidth()) {
                    endX = getWidth();
                }
                this.vedioAreaRect.set(startX, 0.0f, endX, this.view_height - (this.textSize * 1.2f));
                canvas.drawRect(this.vedioAreaRect, this.vedioAreaPaint);
            }
        }
    }

    public List<TimeSlot> getVedioTimeSlot() {
        return this.vedioTimeSlot;
    }

    public void setVedioTimeSlot(List<TimeSlot> vedioTimeSlot) {
        this.vedioTimeSlot.clear();
        this.vedioTimeSlot.addAll(vedioTimeSlot);
        refreshCanvas();
    }

    private float getRightXByTimeSeconde(float timeSeconde) {
        return timeSeconde / this.pixSecond;
    }

    private float getScreenLeftTimeSeconde() {
        return getCurrentSecond() - ((getWidth() / 2) * this.pixSecond);
    }

    private float getScreenRightTimeSeconde() {
        return getCurrentSecond() + ((getWidth() / 2) * this.pixSecond);
    }

    private void drawCenterLine(Canvas canvas) {
        canvas.drawLine(getWidth() / 2, 0.0f, getWidth() / 2, this.view_height - (this.textSize * 1.2f), this.centerLinePaint);
    }

    public void setCurrentSecond(int currentSecond) {
        this.currentSecond = currentSecond;
        refreshCanvas();
    }

    public float getCurrentSecond() {
        return this.currentSecond;
    }

    private void drawTextAndRuler(Canvas canvas) {
        int divisor;
        int viewWidth = getWidth();
        int itemWidth = this.rulerWidthSamll + this.rulerSpace;
        getPixSecond(itemWidth);
        int count = viewWidth / itemWidth;
        if (this.lastPix < 0.0f) {
            count = (int) (count + ((-this.lastPix) / 10.0f));
        }
        int leftCound = 0;
        if (this.scaleMode == 1) {
            if (getCurrentTimeMillis() < this.currentDateStartTimeMillis + 900000) {
                leftCound = -60;
            }
        } else if (getCurrentTimeMillis() < this.currentDateStartTimeMillis + 21600000) {
            leftCound = -60;
        }
        for (int index = leftCound; index < count; index++) {
            float rightX = (index * itemWidth) + this.lastPix;
            if (index == 0) {
                if (rightX < 0.0f) {
                    this.currentSecond = ((viewWidth * this.pixSecond) / 2.0f) + Math.abs(this.pixSecond * rightX);
                } else {
                    this.currentSecond = ((viewWidth * this.pixSecond) / 2.0f) - (this.pixSecond * rightX);
                }
            }
            if (this.isProtrait) {
                lastPortraitTime = getCurrentTimeMillis();
            } else {
                lastLandscapeTime = getCurrentTimeMillis();
            }
            switch (this.scaleMode) {
                case 1:
                    divisor = 10;
                    break;
                case 2:
                    divisor = 6;
                    break;
                default:
                    divisor = 10;
                    break;
            }
            if (index % divisor == 0) {
                canvas.drawLine(rightX, 0.0f, rightX, this.rulerHeightBig, this.largeRulerPaint);
                canvas.drawLine(rightX, this.view_height - (this.textSize * 1.2f), rightX, (this.view_height - this.rulerHeightBig) - (this.textSize * 1.2f), this.largeRulerPaint);
                draText(canvas, index * 60, rightX);
            } else {
                canvas.drawLine(rightX, 0.0f, rightX, this.rulerHeightSamll, this.smallRulerPaint);
                canvas.drawLine(rightX, this.view_height - (this.textSize * 1.2f), rightX, (this.view_height - this.rulerHeightSamll) - (this.textSize * 1.2f), this.smallRulerPaint);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPixSecond(int itemWidth) {
        if (this.scaleMode == 1) {
            this.pixSecond = 60.0f / itemWidth;
        } else {
            this.pixSecond = 600.0f / itemWidth;
        }
    }

    private void drawUpAndDownLine(Canvas canvas) {
        int viewWidth = getWidth();
        canvas.drawLine(0.0f, this.upAndDownLineWidth / 2, viewWidth, this.rulerWidthSamll / 2, this.upAndDownLinePaint);
        canvas.drawLine(0.0f, this.view_height - (this.textSize * 1.2f), viewWidth, this.view_height - (this.textSize * 1.2f), this.upAndDownLinePaint);
    }

    public void draText(Canvas canvas, int time, float x) {
        if (this.scaleMode == 1) {
            if (time < 0) {
                this.keyText = DateUtils.getTimeByCurrentSecond(86400 + time);
            } else {
                this.keyText = DateUtils.getTimeByCurrentSecond(time);
            }
        } else if (time < 0) {
            this.keyText = DateUtils.getTimeByCurrentHours(86400 + time);
        } else {
            this.keyText = DateUtils.getTimeByCurrentHours(time);
        }
        this.keyTextWidth = this.keyTickTextPaint.measureText(this.keyText);
        this.keyTextX = x - (this.keyTextWidth / 2.0f);
        canvas.drawText(this.keyText, this.keyTextX, this.view_height - CUtils.dip2px(3.0f), this.keyTickTextPaint);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == 1073741824) {
            this.view_height = heightSize;
        }
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isSelectTimeArea) {
            switch (event.getAction()) {
                case 2:
                    float curX = event.getX();
                    if (Math.abs(curX - selectTimeAreaDistanceLeft) < Math.abs(curX - selectTimeAreaDistanceRight)) {
                        float currentInterval = ((selectTimeAreaDistanceRight - this.selectTimeStrokeWidth) - curX) * this.pixSecond;
                        if (this.selectTimeMin < currentInterval && currentInterval < this.selectTimeMax) {
                            selectTimeAreaDistanceLeft = curX;
                            if (this.onSelectedTimeListener != null) {
                                this.onSelectedTimeListener.onDragging(getSelectStartTime(), getSelectEndTime());
                            }
                        } else if (currentInterval >= this.selectTimeMax) {
                            this.onSelectedTimeListener.onMaxTime();
                        } else if (currentInterval <= this.selectTimeMin) {
                            this.onSelectedTimeListener.onMinTime();
                        }
                    } else {
                        float currentInterval2 = (curX - (selectTimeAreaDistanceLeft + this.selectTimeStrokeWidth)) * this.pixSecond;
                        if (this.selectTimeMin < currentInterval2 && currentInterval2 < this.selectTimeMax) {
                            selectTimeAreaDistanceRight = curX;
                            if (this.onSelectedTimeListener != null) {
                                this.onSelectedTimeListener.onDragging(getSelectStartTime(), getSelectEndTime());
                            }
                        } else if (this.onSelectedTimeListener != null) {
                            if (currentInterval2 >= this.selectTimeMax) {
                                this.onSelectedTimeListener.onMaxTime();
                            } else if (currentInterval2 <= this.selectTimeMin) {
                                this.onSelectedTimeListener.onMinTime();
                            }
                        }
                    }
                    refreshCanvas();
                    break;
            }
            return true;
        }
        this.mScroller.onTouchEvent(event);
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        refreshCanvas();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override // com.xiaocong.smarthome.timerRuler.ScaleScroller.ScrollingListener
    public void onScroll(int distance) {
        if (this.onBarMoveListener != null) {
            this.onBarMoveListener.onDragBar(distance > 0, getCurrentTimeMillis());
        }
        this.lastPix += distance;
        refreshCanvas();
    }

    @Override // com.xiaocong.smarthome.timerRuler.ScaleScroller.ScrollingListener
    public void onZoomFinished() {
    }

    @Override // com.xiaocong.smarthome.timerRuler.ScaleScroller.ScrollingListener
    public void onScrollFinished() {
        if (this.currentDateStartTimeMillis <= getCurrentTimeMillis() && getCurrentTimeMillis() <= (this.currentDateStartTimeMillis + 86400000) - 2000) {
            if (this.scrollTimer != null) {
                this.scrollTimer.cancel();
            }
            this.scrollTimer = new Timer();
            this.scrollTimer.schedule(new TimerTask() { // from class: com.xiaocong.smarthome.timerRuler.TimeRulerView.4
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    if (TimeRulerView.this.onBarMoveListener != null) {
                        TimeRulerView.this.mHandler.sendEmptyMessage(448);
                    }
                }
            }, 500L);
            return;
        }
        if (this.currentDateStartTimeMillis >= getCurrentTimeMillis()) {
            setCurrentTimeMillis(this.currentDateStartTimeMillis);
            if (this.onBarMoveListener != null) {
                this.onBarMoveListener.onMoveExceedStartTime();
                return;
            }
            return;
        }
        if (getCurrentTimeMillis() >= (this.currentDateStartTimeMillis + 86400000) - 500) {
            setCurrentTimeMillis((this.currentDateStartTimeMillis + 86400000) - 1000);
            if (this.onBarMoveListener != null) {
                this.onBarMoveListener.onMoveExceedEndTime();
            }
        }
    }

    public void setZoom(boolean zoom) {
        this.isZoom = zoom;
    }

    public boolean isZoom() {
        return this.isZoom;
    }

    @Override // com.xiaocong.smarthome.timerRuler.ScaleScroller.ScrollingListener
    public void onZoom(float mScale, double time) {
        if (isZoom()) {
            if (mScale > 1.0f) {
                if (this.rulerSpace < MAX_SCALE) {
                    this.rulerSpace += CUtils.dip2px(1.0f);
                } else {
                    this.mHandler.sendEmptyMessage(480);
                }
            } else if (this.rulerSpace > MIN_SCALE) {
                this.rulerSpace -= CUtils.dip2px(1.0f);
            } else {
                this.mHandler.sendEmptyMessage(944);
            }
            if (this.rulerSpace <= CUtils.dip2px(10.0f)) {
                this.scaleMode = 2;
            } else {
                this.scaleMode = 1;
            }
            setCurrentTimeMillisNoDelayed(getCurrentTimeMillis());
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
    }

    public void setRulerColor(int rulerColor) {
        this.rulerColor = rulerColor;
    }

    public void setRulerWidthSamll(int rulerWidthSamll) {
        this.rulerWidthSamll = rulerWidthSamll;
    }

    public void setRulerHeightSamll(int rulerHeightSamll) {
        this.rulerHeightSamll = rulerHeightSamll;
    }

    public void setRulerWidthBig(int rulerWidthBig) {
        this.rulerWidthBig = rulerWidthBig;
    }

    public void setRulerHeightBig(int rulerHeightBig) {
        this.rulerHeightBig = rulerHeightBig;
    }

    public void setUpAndDownLineWidth(int upAndDownLineWidth) {
        this.upAndDownLineWidth = upAndDownLineWidth;
    }

    public void setUpAndDownLineColor(int upAndDownLineColor) {
        this.upAndDownLineColor = upAndDownLineColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setCenterLineColor(int centerLineColor) {
        this.centerLineColor = centerLineColor;
    }

    public void setCenterLineWidth(int centerLineWidth) {
        this.centerLineWidth = centerLineWidth;
    }

    public void setVedioBg(int vedioBg) {
        this.vedioBg = vedioBg;
    }

    public void setSelectTimeBorderColor(int selectTimeBorderColor) {
        this.selectTimeBorderColor = selectTimeBorderColor;
    }

    public void setSelectTimeAreaColor(int selectTimeAreaColor) {
        this.selectTimeAreaColor = selectTimeAreaColor;
    }

    public void setSelectTimeStrokeWidth(float selectTimeStrokeWidth) {
        this.selectTimeStrokeWidth = selectTimeStrokeWidth;
    }

    public void setViewBackgroundColor(int viewBackgroundColor) {
        this.viewBackgroundColor = viewBackgroundColor;
    }
}
