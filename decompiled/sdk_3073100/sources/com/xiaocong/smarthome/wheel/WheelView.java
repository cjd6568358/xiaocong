package com.xiaocong.smarthome.wheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.xiaocong.smarthome.uilib.R;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WheelView extends View {
    private int baselineCenter;
    private int baselineOuter;
    private float bottom;
    int change;
    Context context;
    int firstLineY;
    private GestureDetector gestureDetector;
    int halfCircumference;
    Handler handler;
    private int heightMeasureSpec;
    int initPosition;
    boolean isLoop;
    public float itemHeightCenter;
    public float itemHeightOuter;
    List<String> items;
    int itemsVisible;
    int lineColor;
    float lineSpaceingDimens;
    ScheduledExecutorService mExecutor;
    private ScheduledFuture<?> mFuture;
    private int mOffset;
    int maxTextHeightCenter;
    int maxTextHeightOuter;
    int maxTextWidth;
    int measuredHeight;
    int measuredWidth;
    public int oldIndex;
    OnItemSelectedListener onItemSelectedListener;
    Paint paintCenterText;
    Paint paintIndicatorLine;
    Paint paintOuterText;
    int preCurrentIndex;
    private float previousY;
    int radius;
    private float scaleX;
    int secondLineY;
    private int selectedItem;
    long startTime;
    private Rect tempRect;
    int textColorCenter;
    int textColorOuter;
    float textSizeCenter;
    float textSizeOuter;
    private float top;
    int totalScrollY;
    private int wheelGravity;
    private int widthMeasureSpec;

    public enum ACTION {
        CLICK,
        FLING,
        DAGGLE
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int i, String str);
    }

    public void setWheelGravity(int wheelGravity) {
        this.wheelGravity = wheelGravity;
    }

    public OnItemSelectedListener getOnItemSelectedListener() {
        return this.onItemSelectedListener;
    }

    public List<String> getItems() {
        return this.items;
    }

    public int getSize() {
        return this.items.size();
    }

    public WheelView(Context context) {
        super(context);
        this.oldIndex = -1;
        this.scaleX = 1.0f;
        this.widthMeasureSpec = 0;
        this.heightMeasureSpec = 0;
        this.wheelGravity = 354;
        this.mExecutor = Executors.newSingleThreadScheduledExecutor();
        this.itemsVisible = 7;
        this.textSizeCenter = 18.0f;
        this.textSizeOuter = 13.0f;
        this.textColorOuter = -4473925;
        this.textColorCenter = -11711155;
        this.lineColor = -1644826;
        this.isLoop = false;
        this.totalScrollY = 0;
        this.initPosition = -1;
        this.mOffset = 0;
        this.startTime = 0L;
        this.tempRect = new Rect();
        initWheelView(context);
    }

    public WheelView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        this.oldIndex = -1;
        this.scaleX = 1.0f;
        this.widthMeasureSpec = 0;
        this.heightMeasureSpec = 0;
        this.wheelGravity = 354;
        this.mExecutor = Executors.newSingleThreadScheduledExecutor();
        this.itemsVisible = 7;
        this.textSizeCenter = 18.0f;
        this.textSizeOuter = 13.0f;
        this.textColorOuter = -4473925;
        this.textColorCenter = -11711155;
        this.lineColor = -1644826;
        this.isLoop = false;
        this.totalScrollY = 0;
        this.initPosition = -1;
        this.mOffset = 0;
        this.startTime = 0L;
        this.tempRect = new Rect();
        initWheelView(context, attributeset);
    }

    public WheelView(Context context, AttributeSet attributeset, int defStyleAttr) {
        super(context, attributeset, defStyleAttr);
        this.oldIndex = -1;
        this.scaleX = 1.0f;
        this.widthMeasureSpec = 0;
        this.heightMeasureSpec = 0;
        this.wheelGravity = 354;
        this.mExecutor = Executors.newSingleThreadScheduledExecutor();
        this.itemsVisible = 7;
        this.textSizeCenter = 18.0f;
        this.textSizeOuter = 13.0f;
        this.textColorOuter = -4473925;
        this.textColorCenter = -11711155;
        this.lineColor = -1644826;
        this.isLoop = false;
        this.totalScrollY = 0;
        this.initPosition = -1;
        this.mOffset = 0;
        this.startTime = 0L;
        this.tempRect = new Rect();
        initWheelView(context, attributeset);
    }

    private void initWheelView(Context context, AttributeSet attributeset) {
        TypedArray attribute = context.obtainStyledAttributes(attributeset, R.styleable.WheelView);
        this.lineColor = attribute.getColor(R.styleable.WheelView_lineColor, this.lineColor);
        this.itemsVisible = getFixedItemsVisible(attribute.getInt(R.styleable.WheelView_itemVisibleNum, this.itemsVisible));
        this.isLoop = attribute.getBoolean(R.styleable.WheelView_isLoop, this.isLoop);
        this.textColorCenter = attribute.getColor(R.styleable.WheelView_textColorCenter, this.textColorCenter);
        this.textColorOuter = attribute.getColor(R.styleable.WheelView_textColorOuter, this.textColorOuter);
        this.textSizeCenter = attribute.getDimension(R.styleable.WheelView_textSizeCenter, Common.dip2px(context, 18.0f));
        this.textSizeOuter = attribute.getDimension(R.styleable.WheelView_textSizeOuter, Common.dip2px(context, 13.0f));
        this.lineSpaceingDimens = attribute.getDimension(R.styleable.WheelView_lineSpaceingDimens, Common.dip2px(context, 6.0f));
        this.wheelGravity = attribute.getInt(R.styleable.WheelView_wheelGravity, this.wheelGravity);
        attribute.recycle();
        initWheelView(context);
    }

    private int getFixedItemsVisible(int originalNum) {
        if (originalNum < 3) {
            return 3;
        }
        if (originalNum % 2 == 0) {
            return originalNum + 1;
        }
        return originalNum;
    }

    private void initWheelView(Context context) {
        this.context = context;
        this.handler = new MessageHandler(this);
        this.gestureDetector = new GestureDetector(context, new WheelViewGestureListener(this));
        this.gestureDetector.setIsLongpressEnabled(false);
        initPaints();
    }

    public void reset() {
        this.totalScrollY = 0;
    }

    private void initPaints() {
        this.paintOuterText = new Paint();
        this.paintOuterText.setColor(this.textColorOuter);
        this.paintOuterText.setAntiAlias(true);
        this.paintOuterText.setTypeface(Typeface.MONOSPACE);
        this.paintOuterText.setTextSize(this.textSizeOuter);
        this.paintCenterText = new Paint();
        this.paintCenterText.setColor(this.textColorCenter);
        this.paintCenterText.setAntiAlias(true);
        this.paintCenterText.setTypeface(Typeface.MONOSPACE);
        this.paintCenterText.setTextSize(this.textSizeCenter);
        this.paintIndicatorLine = new Paint();
        this.paintIndicatorLine.setColor(this.lineColor);
        this.paintIndicatorLine.setAntiAlias(true);
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
    }

    private void remeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        if (this.items != null) {
            measureTextWidthHeight();
            this.halfCircumference = (int) (this.itemHeightCenter * (this.itemsVisible - 1));
            this.measuredHeight = (int) (((double) (this.halfCircumference * 2)) / 3.141592653589793d);
            this.measuredHeight = (int) (this.itemHeightCenter + (this.itemHeightOuter * (this.itemsVisible - 1)) + (this.lineSpaceingDimens * 2.0f));
            this.radius = (int) (((double) this.halfCircumference) / 3.141592653589793d);
            this.measuredWidth = this.maxTextWidth;
            if (widthMode == 1073741824) {
                this.measuredWidth = widthSize;
            }
            this.firstLineY = (int) ((this.itemHeightOuter * (this.itemsVisible - 1)) / 2.0f);
            this.secondLineY = (int) (((this.itemHeightOuter * (this.itemsVisible - 1)) / 2.0f) + this.itemHeightCenter);
            if (this.initPosition == -1) {
                if (this.isLoop) {
                    this.initPosition = (this.items.size() + 1) / 2;
                } else {
                    this.initPosition = 0;
                }
            }
            this.bottom = ((this.items.size() - 1) - this.initPosition) * this.itemHeightOuter;
            this.top = (-this.initPosition) * this.itemHeightOuter;
            this.preCurrentIndex = this.initPosition;
        }
    }

    private void measureTextWidthHeight() {
        for (int i = 0; i < this.items.size(); i++) {
            String s1 = this.items.get(i);
            this.paintCenterText.getTextBounds(s1, 0, s1.length(), this.tempRect);
            int textWidth = (int) this.paintCenterText.measureText(s1);
            if (textWidth > this.maxTextWidth) {
                this.maxTextWidth = (int) (textWidth * this.scaleX);
            }
        }
        this.paintCenterText.getTextBounds("星期", 0, 2, this.tempRect);
        this.maxTextHeightCenter = this.tempRect.height();
        this.paintOuterText.getTextBounds("星期", 0, 2, this.tempRect);
        this.maxTextHeightOuter = this.tempRect.height();
        this.itemHeightOuter = this.maxTextHeightOuter + (this.lineSpaceingDimens * 2.0f);
        this.itemHeightCenter = this.maxTextHeightCenter + (this.lineSpaceingDimens * 2.0f);
        Paint.FontMetricsInt fontMetricsOuter = this.paintOuterText.getFontMetricsInt();
        this.baselineOuter = (int) ((((this.itemHeightOuter - fontMetricsOuter.bottom) + fontMetricsOuter.top) / 2.0f) - fontMetricsOuter.top);
        Paint.FontMetricsInt fontMetricsCenter = this.paintCenterText.getFontMetricsInt();
        this.baselineCenter = (int) ((((this.itemHeightCenter - fontMetricsCenter.bottom) + fontMetricsCenter.top) / 2.0f) - fontMetricsCenter.top);
    }

    void smoothScroll(ACTION action) {
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            this.mOffset = (int) (((this.totalScrollY % this.itemHeightOuter) + this.itemHeightOuter) % this.itemHeightOuter);
            if (this.mOffset > this.itemHeightOuter / 2.0f) {
                this.mOffset = (int) (this.itemHeightOuter - this.mOffset);
            } else {
                this.mOffset = -this.mOffset;
            }
        }
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, this.mOffset), 0L, 10L, TimeUnit.MILLISECONDS);
    }

    protected final void scrollBy(float velocityY) {
        cancelFuture();
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, velocityY), 0L, 15, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (this.mFuture != null && !this.mFuture.isCancelled()) {
            this.mFuture.cancel(true);
            this.mFuture = null;
        }
    }

    public final void setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    private final void setInitPosition(int initPosition) {
        if (initPosition < 0) {
            this.initPosition = 0;
        } else {
            this.initPosition = initPosition;
        }
        this.oldIndex = initPosition;
        this.selectedItem = initPosition;
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener OnItemSelectedListener2) {
        this.onItemSelectedListener = OnItemSelectedListener2;
    }

    private final void setItems(List<String> items) {
        reset();
        if (items == null) {
            String[] empty = {"--"};
            this.items = Arrays.asList(empty);
        } else {
            this.items = items;
        }
        remeasure(this.widthMeasureSpec, this.heightMeasureSpec);
        invalidate();
    }

    public final void setItems(List<String> items, int initPosition) {
        setInitPosition(initPosition);
        setItems(items);
    }

    public final String getSelectedItem() {
        return (this.selectedItem >= this.items.size() || this.selectedItem < 0) ? "" : this.items.get(this.selectedItem);
    }

    public final int getSelectedPosition() {
        return this.selectedItem;
    }

    protected final void onItemSelected() {
        if (this.onItemSelectedListener != null) {
            postDelayed(new OnItemSelectedRunnable(this), 200L);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        String as;
        if (this.items != null) {
            canvas.translate(0.0f, this.lineSpaceingDimens);
            canvas.clipRect(0.0f, 0.0f, this.measuredWidth, this.measuredHeight - (2.0f * this.lineSpaceingDimens));
            canvas.save();
            this.change = (int) (this.totalScrollY / this.itemHeightOuter);
            this.preCurrentIndex = this.initPosition + (this.change % this.items.size());
            if (!this.isLoop) {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = 0;
                }
                if (this.preCurrentIndex > this.items.size() - 1) {
                    this.preCurrentIndex = this.items.size() - 1;
                }
            } else {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = this.items.size() + this.preCurrentIndex;
                }
                if (this.preCurrentIndex > this.items.size() - 1) {
                    this.preCurrentIndex -= this.items.size();
                }
            }
            int j3 = (int) (this.totalScrollY % this.itemHeightOuter);
            canvas.drawLine(0.0f, this.firstLineY, this.measuredWidth, this.firstLineY, this.paintIndicatorLine);
            canvas.drawLine(0.0f, this.secondLineY, this.measuredWidth, this.secondLineY, this.paintIndicatorLine);
            int j1 = 0;
            int translateY = getTranslateY(0, j3);
            int translateYNext = getTranslateY(1, j3);
            while (j1 < this.itemsVisible + 2) {
                int l1 = (this.preCurrentIndex - ((this.itemsVisible / 2) - j1)) - 1;
                if (this.isLoop) {
                    l1 %= this.items.size();
                    if (l1 < 0) {
                        l1 += this.items.size();
                    }
                    as = this.items.get(l1);
                } else if (l1 < 0 || l1 > this.items.size() - 1) {
                    as = "";
                } else {
                    as = this.items.get(l1);
                }
                canvas.save();
                canvas.translate(0.0f, translateY);
                if (translateY < this.firstLineY && translateYNext > this.firstLineY) {
                    canvas.save();
                    canvas.clipRect(0, 0, this.measuredWidth, this.firstLineY - translateY);
                    canvas.drawText(as, getTextX(as, this.paintOuterText, this.tempRect), this.baselineOuter, this.paintOuterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, this.firstLineY - translateY, this.measuredWidth, (int) this.itemHeightCenter);
                    canvas.drawText(as, getTextX(as, this.paintCenterText, this.tempRect), this.baselineCenter, this.paintCenterText);
                    canvas.restore();
                } else if (translateY < this.secondLineY && translateYNext > this.secondLineY) {
                    canvas.save();
                    canvas.clipRect(0, 0, this.measuredWidth, this.secondLineY - translateY);
                    canvas.drawText(as, getTextX(as, this.paintCenterText, this.tempRect), this.baselineCenter, this.paintCenterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, this.secondLineY - translateY, this.measuredWidth, (int) this.itemHeightCenter);
                    canvas.drawText(as, getTextX(as, this.paintOuterText, this.tempRect), (this.baselineOuter + (translateYNext - translateY)) - this.itemHeightOuter, this.paintOuterText);
                    canvas.restore();
                } else if (translateY >= this.firstLineY && translateYNext <= this.secondLineY) {
                    canvas.clipRect(0, 0, this.measuredWidth, (int) this.itemHeightCenter);
                    canvas.drawText(as, getTextX(as, this.paintCenterText, this.tempRect), this.baselineCenter, this.paintCenterText);
                } else {
                    canvas.clipRect(0, 0, this.measuredWidth, (int) this.itemHeightOuter);
                    canvas.drawText(as, getTextX(as, this.paintOuterText, this.tempRect), this.baselineOuter, this.paintOuterText);
                }
                if ((translateY >= this.firstLineY && translateY < (this.firstLineY + this.secondLineY) / 2) || (translateYNext > (this.firstLineY + this.secondLineY) / 2 && translateYNext <= this.secondLineY)) {
                    this.selectedItem = l1;
                }
                canvas.restore();
                j1++;
                translateY = translateYNext;
                translateYNext = getTranslateY(j1 + 1, j3);
            }
        }
    }

    private int getTranslateY(int j1, int j3) {
        if (this.totalScrollY >= 0) {
            if (j1 <= ((this.itemsVisible - 1) / 2) + 1) {
                return (int) (((this.itemHeightOuter * j1) - this.itemHeightOuter) - j3);
            }
            if (j1 == ((this.itemsVisible - 1) / 2) + 2) {
                return (int) ((((this.itemHeightOuter * (this.itemsVisible - 1)) / 2.0f) + this.itemHeightCenter) - ((j3 * this.itemHeightCenter) / this.itemHeightOuter));
            }
            return (int) ((((this.itemHeightOuter * j1) - this.itemHeightOuter) - j3) + (this.itemHeightCenter - this.itemHeightOuter));
        }
        if (j1 < ((this.itemsVisible - 1) / 2) + 1) {
            return (int) (((this.itemHeightOuter * j1) - this.itemHeightOuter) - j3);
        }
        if (j1 == ((this.itemsVisible - 1) / 2) + 1) {
            return (int) (((this.itemHeightOuter * (((this.itemsVisible - 1) / 2) + 1)) - this.itemHeightOuter) - ((j3 * this.itemHeightCenter) / this.itemHeightOuter));
        }
        return (int) ((((this.itemHeightOuter * j1) - this.itemHeightOuter) - j3) + (this.itemHeightCenter - this.itemHeightOuter));
    }

    private int getTextX(String a, Paint paint, Rect rect) {
        paint.getTextBounds(a, 0, a.length(), rect);
        rect.width();
        int textWidth = (int) (((int) paint.measureText(a)) * this.scaleX);
        if (this.wheelGravity == 894) {
            return ((this.maxTextWidth / 2) - (textWidth / 2)) + (this.measuredWidth - this.maxTextWidth);
        }
        if (this.wheelGravity == 234) {
            return (this.maxTextWidth / 2) - (textWidth / 2);
        }
        return (this.measuredWidth - textWidth) / 2;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        remeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(this.measuredWidth, this.measuredHeight);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int pos;
        boolean eventConsumed = this.gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case 0:
                this.startTime = System.currentTimeMillis();
                cancelFuture();
                this.previousY = event.getRawY();
                break;
            case 1:
            default:
                if (!eventConsumed) {
                    float y = event.getY();
                    double l = Math.acos((this.radius - y) / this.radius) * ((double) this.radius);
                    int circlePosition = (int) ((((double) (this.itemHeightCenter / 2.0f)) + l) / ((double) this.itemHeightCenter));
                    float extraOffset = ((this.totalScrollY % this.itemHeightOuter) + this.itemHeightOuter) % this.itemHeightOuter;
                    this.mOffset = (int) (((circlePosition - (this.itemsVisible / 2)) * this.itemHeightCenter) - extraOffset);
                    if (y <= this.firstLineY) {
                        pos = (int) (y / this.itemHeightOuter);
                    } else if (y >= this.secondLineY) {
                        pos = (int) ((((int) (y - this.itemHeightCenter)) / this.itemHeightOuter) + 1.0f);
                        if (pos > this.itemsVisible - 1) {
                            pos = this.itemsVisible - 1;
                        }
                    } else {
                        pos = this.itemsVisible / 2;
                    }
                    this.mOffset = (int) (((pos - (this.itemsVisible / 2)) * this.itemHeightOuter) - extraOffset);
                    if (!this.isLoop) {
                        if (this.totalScrollY + this.mOffset > this.bottom) {
                            this.mOffset = (int) (this.bottom - this.totalScrollY);
                        }
                        if (this.totalScrollY + this.mOffset < this.top) {
                            this.mOffset = (int) (this.top - this.totalScrollY);
                        }
                    }
                    if (System.currentTimeMillis() - this.startTime > 120) {
                        smoothScroll(ACTION.DAGGLE);
                    } else {
                        smoothScroll(ACTION.CLICK);
                    }
                }
                break;
            case 2:
                float dy = this.previousY - event.getRawY();
                this.previousY = event.getRawY();
                this.totalScrollY = (int) (this.totalScrollY + dy);
                if (!this.isLoop) {
                    if (this.totalScrollY < this.top) {
                        this.totalScrollY = (int) this.top;
                    } else if (this.totalScrollY > this.bottom) {
                        this.totalScrollY = (int) this.bottom;
                    }
                }
                break;
        }
        invalidate();
        return true;
    }
}
