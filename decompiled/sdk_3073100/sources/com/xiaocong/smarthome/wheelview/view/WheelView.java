package com.xiaocong.smarthome.wheelview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.xiaocong.smarthome.uilib.R;
import com.xiaocong.smarthome.wheelview.adapter.WheelAdapter;
import com.xiaocong.smarthome.wheelview.interfaces.IPickerViewData;
import com.xiaocong.smarthome.wheelview.listener.LoopViewGestureListener;
import com.xiaocong.smarthome.wheelview.listener.OnItemSelectedListener;
import com.xiaocong.smarthome.wheelview.timer.InertiaTimerTask;
import com.xiaocong.smarthome.wheelview.timer.MessageHandler;
import com.xiaocong.smarthome.wheelview.timer.SmoothScrollTimerTask;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WheelView extends View {
    private float CENTER_CONTENT_OFFSET;
    private final float DEFAULT_TEXT_TARGET_SKEWX;
    private WheelAdapter adapter;
    private float centerY;
    private int change;
    private Context context;
    private int dividerColor;
    private DividerType dividerType;
    private int drawCenterContentStart;
    private int drawOutContentStart;
    private float firstLineY;
    private GestureDetector gestureDetector;
    private Handler handler;
    private int initPosition;
    private boolean isCenterLabel;
    private boolean isLoop;
    private boolean isOptions;
    private float itemHeight;
    private int itemsVisible;
    private String label;
    private float lineSpacingMultiplier;
    private ScheduledExecutorService mExecutor;
    private ScheduledFuture<?> mFuture;
    private int mGravity;
    private int mOffset;
    private int maxTextHeight;
    private int maxTextWidth;
    private int measuredHeight;
    private int measuredWidth;
    private OnItemSelectedListener onItemSelectedListener;
    private Paint paintCenterText;
    private Paint paintIndicator;
    private Paint paintOuterText;
    private int preCurrentIndex;
    private float previousY;
    private int radius;
    private float secondLineY;
    private int selectedItem;
    private long startTime;
    private int textColorCenter;
    private int textColorOut;
    private int textSize;
    private int textXOffset;
    private float totalScrollY;
    private Typeface typeface;
    private int widthMeasureSpec;

    public enum ACTION {
        CLICK,
        FLING,
        DAGGLE
    }

    public enum DividerType {
        FILL,
        WRAP
    }

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isOptions = false;
        this.isCenterLabel = true;
        this.mExecutor = Executors.newSingleThreadScheduledExecutor();
        this.typeface = Typeface.MONOSPACE;
        this.lineSpacingMultiplier = 1.6f;
        this.itemsVisible = 11;
        this.mOffset = 0;
        this.previousY = 0.0f;
        this.startTime = 0L;
        this.mGravity = 17;
        this.drawCenterContentStart = 0;
        this.drawOutContentStart = 0;
        this.DEFAULT_TEXT_TARGET_SKEWX = 0.5f;
        this.textSize = getResources().getDimensionPixelSize(R.dimen.pickerview_textsize);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        if (density < 1.0f) {
            this.CENTER_CONTENT_OFFSET = 2.4f;
        } else if (1.0f <= density && density < 2.0f) {
            this.CENTER_CONTENT_OFFSET = 3.6f;
        } else if (1.0f <= density && density < 2.0f) {
            this.CENTER_CONTENT_OFFSET = 4.5f;
        } else if (2.0f <= density && density < 3.0f) {
            this.CENTER_CONTENT_OFFSET = 6.0f;
        } else if (density >= 3.0f) {
            this.CENTER_CONTENT_OFFSET = 2.5f * density;
        }
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.pickerview, 0, 0);
            this.mGravity = a.getInt(R.styleable.pickerview_wheelview_gravity, 17);
            this.textColorOut = a.getColor(R.styleable.pickerview_wheelview_textColorOut, -5723992);
            this.textColorCenter = a.getColor(R.styleable.pickerview_wheelview_textColorCenter, -14013910);
            this.dividerColor = a.getColor(R.styleable.pickerview_wheelview_dividerColor, -2763307);
            this.textSize = a.getDimensionPixelOffset(R.styleable.pickerview_wheelview_textSize, this.textSize);
            this.lineSpacingMultiplier = a.getFloat(R.styleable.pickerview_wheelview_lineSpacingMultiplier, this.lineSpacingMultiplier);
            a.recycle();
        }
        judgeLineSpace();
        initLoopView(context);
    }

    private void judgeLineSpace() {
        if (this.lineSpacingMultiplier < 1.0f) {
            this.lineSpacingMultiplier = 1.0f;
        } else if (this.lineSpacingMultiplier > 4.0f) {
            this.lineSpacingMultiplier = 4.0f;
        }
    }

    private void initLoopView(Context context) {
        this.context = context;
        this.handler = new MessageHandler(this);
        this.gestureDetector = new GestureDetector(context, new LoopViewGestureListener(this));
        this.gestureDetector.setIsLongpressEnabled(false);
        this.isLoop = true;
        this.totalScrollY = 0.0f;
        this.initPosition = -1;
        initPaints();
    }

    private void initPaints() {
        this.paintOuterText = new Paint();
        this.paintOuterText.setColor(this.textColorOut);
        this.paintOuterText.setAntiAlias(true);
        this.paintOuterText.setTypeface(this.typeface);
        this.paintOuterText.setTextSize(this.textSize);
        this.paintCenterText = new Paint();
        this.paintCenterText.setColor(this.textColorCenter);
        this.paintCenterText.setAntiAlias(true);
        this.paintCenterText.setTextScaleX(1.1f);
        this.paintCenterText.setTypeface(this.typeface);
        this.paintCenterText.setTextSize(this.textSize);
        this.paintIndicator = new Paint();
        this.paintIndicator.setColor(this.dividerColor);
        this.paintIndicator.setAntiAlias(true);
        setLayerType(1, null);
    }

    private void remeasure() {
        if (this.adapter != null) {
            measureTextWidthHeight();
            int halfCircumference = (int) (this.itemHeight * (this.itemsVisible - 1));
            this.measuredHeight = (int) (((double) (halfCircumference * 2)) / 3.141592653589793d);
            this.radius = (int) (((double) halfCircumference) / 3.141592653589793d);
            this.measuredWidth = View.MeasureSpec.getSize(this.widthMeasureSpec);
            this.firstLineY = (this.measuredHeight - this.itemHeight) / 2.0f;
            this.secondLineY = (this.measuredHeight + this.itemHeight) / 2.0f;
            this.centerY = (this.secondLineY - ((this.itemHeight - this.maxTextHeight) / 2.0f)) - this.CENTER_CONTENT_OFFSET;
            if (this.initPosition == -1) {
                if (this.isLoop) {
                    this.initPosition = (this.adapter.getItemsCount() + 1) / 2;
                } else {
                    this.initPosition = 0;
                }
            }
            this.preCurrentIndex = this.initPosition;
        }
    }

    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < this.adapter.getItemsCount(); i++) {
            String s1 = getContentText(this.adapter.getItem(i));
            this.paintCenterText.getTextBounds(s1, 0, s1.length(), rect);
            int textWidth = rect.width();
            if (textWidth > this.maxTextWidth) {
                this.maxTextWidth = textWidth;
            }
            this.paintCenterText.getTextBounds("星期", 0, 2, rect);
            this.maxTextHeight = rect.height() + 2;
        }
        this.itemHeight = this.lineSpacingMultiplier * this.maxTextHeight;
    }

    public void smoothScroll(ACTION action) {
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            this.mOffset = (int) (((this.totalScrollY % this.itemHeight) + this.itemHeight) % this.itemHeight);
            if (this.mOffset > this.itemHeight / 2.0f) {
                this.mOffset = (int) (this.itemHeight - this.mOffset);
            } else {
                this.mOffset = -this.mOffset;
            }
        }
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, this.mOffset), 0L, 10L, TimeUnit.MILLISECONDS);
    }

    public final void scrollBy(float velocityY) {
        cancelFuture();
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, velocityY), 0L, 5L, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (this.mFuture != null && !this.mFuture.isCancelled()) {
            this.mFuture.cancel(true);
            this.mFuture = null;
        }
    }

    public final void setCyclic(boolean cyclic) {
        this.isLoop = cyclic;
    }

    public final void setTypeface(Typeface font) {
        this.typeface = font;
        this.paintOuterText.setTypeface(this.typeface);
        this.paintCenterText.setTypeface(this.typeface);
    }

    public final void setTextSize(float size) {
        if (size > 0.0f) {
            this.textSize = (int) (this.context.getResources().getDisplayMetrics().density * size);
            this.paintOuterText.setTextSize(this.textSize);
            this.paintCenterText.setTextSize(this.textSize);
        }
    }

    public final void setCurrentItem(int currentItem) {
        this.selectedItem = currentItem;
        this.initPosition = currentItem;
        this.totalScrollY = 0.0f;
        invalidate();
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener OnItemSelectedListener) {
        this.onItemSelectedListener = OnItemSelectedListener;
    }

    public final void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        remeasure();
        invalidate();
    }

    public final WheelAdapter getAdapter() {
        return this.adapter;
    }

    public final int getCurrentItem() {
        if (this.adapter == null) {
            return 0;
        }
        if (this.isLoop && (this.selectedItem < 0 || this.selectedItem >= this.adapter.getItemsCount())) {
            return Math.max(0, Math.min(Math.abs(Math.abs(this.selectedItem) - this.adapter.getItemsCount()), this.adapter.getItemsCount() - 1));
        }
        return Math.max(0, Math.min(this.selectedItem, this.adapter.getItemsCount() - 1));
    }

    public final void onItemSelected() {
        if (this.onItemSelectedListener != null) {
            postDelayed(new Runnable() { // from class: com.xiaocong.smarthome.wheelview.view.WheelView.1
                @Override // java.lang.Runnable
                public void run() {
                    WheelView.this.onItemSelectedListener.onItemSelected(WheelView.this.getCurrentItem());
                }
            }, 200L);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        String contentText;
        int i;
        float startX;
        if (this.adapter != null) {
            this.initPosition = Math.min(Math.max(0, this.initPosition), this.adapter.getItemsCount() - 1);
            Object[] visibles = new Object[this.itemsVisible];
            this.change = (int) (this.totalScrollY / this.itemHeight);
            try {
                this.preCurrentIndex = this.initPosition + (this.change % this.adapter.getItemsCount());
            } catch (ArithmeticException e) {
                Log.e("WheelView", "出错了！adapter.getItemsCount() == 0，联动数据不匹配");
            }
            if (!this.isLoop) {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = 0;
                }
                if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                    this.preCurrentIndex = this.adapter.getItemsCount() - 1;
                }
            } else {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = this.adapter.getItemsCount() + this.preCurrentIndex;
                }
                if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                    this.preCurrentIndex -= this.adapter.getItemsCount();
                }
            }
            float itemHeightOffset = this.totalScrollY % this.itemHeight;
            for (int counter = 0; counter < this.itemsVisible; counter++) {
                int index = this.preCurrentIndex - ((this.itemsVisible / 2) - counter);
                if (this.isLoop) {
                    visibles[counter] = this.adapter.getItem(getLoopMappingIndex(index));
                } else if (index < 0) {
                    visibles[counter] = "";
                } else if (index > this.adapter.getItemsCount() - 1) {
                    visibles[counter] = "";
                } else {
                    visibles[counter] = this.adapter.getItem(index);
                }
            }
            if (this.dividerType == DividerType.WRAP) {
                if (TextUtils.isEmpty(this.label)) {
                    startX = ((this.measuredWidth - this.maxTextWidth) / 2) - 12;
                } else {
                    startX = ((this.measuredWidth - this.maxTextWidth) / 4) - 12;
                }
                if (startX <= 0.0f) {
                    startX = 10.0f;
                }
                float endX = this.measuredWidth - startX;
                canvas.drawLine(startX, this.firstLineY, endX, this.firstLineY, this.paintIndicator);
                canvas.drawLine(startX, this.secondLineY, endX, this.secondLineY, this.paintIndicator);
            } else {
                canvas.drawLine(0.0f, this.firstLineY, this.measuredWidth, this.firstLineY, this.paintIndicator);
                canvas.drawLine(0.0f, this.secondLineY, this.measuredWidth, this.secondLineY, this.paintIndicator);
            }
            if (!TextUtils.isEmpty(this.label) && this.isCenterLabel) {
                int drawRightContentStart = this.measuredWidth - getTextWidth(this.paintCenterText, this.label);
                canvas.drawText(this.label, drawRightContentStart - this.CENTER_CONTENT_OFFSET, this.centerY, this.paintCenterText);
            }
            for (int counter2 = 0; counter2 < this.itemsVisible; counter2++) {
                canvas.save();
                double radian = ((this.itemHeight * counter2) - itemHeightOffset) / this.radius;
                float angle = (float) (90.0d - ((radian / 3.141592653589793d) * 180.0d));
                if (angle >= 90.0f || angle <= -90.0f) {
                    canvas.restore();
                } else {
                    float offsetCoefficient = (float) Math.pow(Math.abs(angle) / 90.0f, 2.2d);
                    if (!this.isCenterLabel && !TextUtils.isEmpty(this.label) && !TextUtils.isEmpty(getContentText(visibles[counter2]))) {
                        contentText = getContentText(visibles[counter2]) + this.label;
                    } else {
                        contentText = getContentText(visibles[counter2]);
                    }
                    reMeasureTextSize(contentText);
                    measuredCenterContentStart(contentText);
                    measuredOutContentStart(contentText);
                    float translateY = (float) ((((double) this.radius) - (Math.cos(radian) * ((double) this.radius))) - ((Math.sin(radian) * ((double) this.maxTextHeight)) / 2.0d));
                    canvas.translate(0.0f, translateY);
                    if (translateY <= this.firstLineY && this.maxTextHeight + translateY >= this.firstLineY) {
                        canvas.save();
                        canvas.clipRect(0.0f, 0.0f, this.measuredWidth, this.firstLineY - translateY);
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * 0.8f);
                        canvas.drawText(contentText, this.drawOutContentStart, this.maxTextHeight, this.paintOuterText);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0.0f, this.firstLineY - translateY, this.measuredWidth, (int) this.itemHeight);
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                        canvas.drawText(contentText, this.drawCenterContentStart, this.maxTextHeight - this.CENTER_CONTENT_OFFSET, this.paintCenterText);
                        canvas.restore();
                    } else if (translateY <= this.secondLineY && this.maxTextHeight + translateY >= this.secondLineY) {
                        canvas.save();
                        canvas.clipRect(0.0f, 0.0f, this.measuredWidth, this.secondLineY - translateY);
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                        canvas.drawText(contentText, this.drawCenterContentStart, this.maxTextHeight - this.CENTER_CONTENT_OFFSET, this.paintCenterText);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0.0f, this.secondLineY - translateY, this.measuredWidth, (int) this.itemHeight);
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * 0.8f);
                        canvas.drawText(contentText, this.drawOutContentStart, this.maxTextHeight, this.paintOuterText);
                        canvas.restore();
                    } else if (translateY >= this.firstLineY && this.maxTextHeight + translateY <= this.secondLineY) {
                        canvas.clipRect(0, 0, this.measuredWidth, this.maxTextHeight);
                        float Y = this.maxTextHeight - this.CENTER_CONTENT_OFFSET;
                        canvas.drawText(contentText, this.drawCenterContentStart, Y, this.paintCenterText);
                        this.selectedItem = this.preCurrentIndex - ((this.itemsVisible / 2) - counter2);
                    } else {
                        canvas.save();
                        canvas.clipRect(0, 0, this.measuredWidth, (int) this.itemHeight);
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * 0.8f);
                        Paint paint = this.paintOuterText;
                        if (this.textXOffset == 0) {
                            i = 0;
                        } else {
                            i = this.textXOffset > 0 ? 1 : -1;
                        }
                        paint.setTextSkewX((angle > 0.0f ? -1 : 1) * i * 0.5f * offsetCoefficient);
                        this.paintOuterText.setAlpha((int) ((1.0f - offsetCoefficient) * 255.0f));
                        canvas.drawText(contentText, this.drawOutContentStart + (this.textXOffset * offsetCoefficient), this.maxTextHeight, this.paintOuterText);
                        canvas.restore();
                    }
                    canvas.restore();
                    this.paintCenterText.setTextSize(this.textSize);
                }
            }
        }
    }

    private void reMeasureTextSize(String contentText) {
        Rect rect = new Rect();
        this.paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
        int size = this.textSize;
        for (int width = rect.width(); width > this.measuredWidth; width = rect.width()) {
            size--;
            this.paintCenterText.setTextSize(size);
            this.paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
        }
        this.paintOuterText.setTextSize(size);
    }

    private int getLoopMappingIndex(int index) {
        if (index < 0) {
            return getLoopMappingIndex(index + this.adapter.getItemsCount());
        }
        if (index > this.adapter.getItemsCount() - 1) {
            return getLoopMappingIndex(index - this.adapter.getItemsCount());
        }
        return index;
    }

    private String getContentText(Object item) {
        if (item == null) {
            return "";
        }
        if (item instanceof IPickerViewData) {
            return ((IPickerViewData) item).getPickerViewText();
        }
        return item instanceof Integer ? String.format(Locale.getDefault(), "%02d", Integer.valueOf(((Integer) item).intValue())) : item.toString();
    }

    private void measuredCenterContentStart(String content) {
        Rect rect = new Rect();
        this.paintCenterText.getTextBounds(content, 0, content.length(), rect);
        switch (this.mGravity) {
            case 3:
                this.drawCenterContentStart = 0;
                break;
            case 5:
                this.drawCenterContentStart = (this.measuredWidth - rect.width()) - ((int) this.CENTER_CONTENT_OFFSET);
                break;
            case 17:
                if (this.isOptions || this.label == null || this.label.equals("") || !this.isCenterLabel) {
                    this.drawCenterContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.5d);
                } else {
                    this.drawCenterContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.25d);
                }
                break;
        }
    }

    private void measuredOutContentStart(String content) {
        Rect rect = new Rect();
        this.paintOuterText.getTextBounds(content, 0, content.length(), rect);
        switch (this.mGravity) {
            case 3:
                this.drawOutContentStart = 0;
                break;
            case 5:
                this.drawOutContentStart = (this.measuredWidth - rect.width()) - ((int) this.CENTER_CONTENT_OFFSET);
                break;
            case 17:
                if (this.isOptions || this.label == null || this.label.equals("") || !this.isCenterLabel) {
                    this.drawOutContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.5d);
                } else {
                    this.drawOutContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.25d);
                }
                break;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        remeasure();
        setMeasuredDimension(this.measuredWidth, this.measuredHeight);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = this.gestureDetector.onTouchEvent(event);
        boolean isIgnore = false;
        float top = (-this.initPosition) * this.itemHeight;
        float bottom = ((this.adapter.getItemsCount() - 1) - this.initPosition) * this.itemHeight;
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
                    double L = Math.acos((this.radius - y) / this.radius) * ((double) this.radius);
                    int circlePosition = (int) ((((double) (this.itemHeight / 2.0f)) + L) / ((double) this.itemHeight));
                    float extraOffset = ((this.totalScrollY % this.itemHeight) + this.itemHeight) % this.itemHeight;
                    this.mOffset = (int) (((circlePosition - (this.itemsVisible / 2)) * this.itemHeight) - extraOffset);
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
                this.totalScrollY += dy;
                if (!this.isLoop) {
                    if ((this.totalScrollY - (this.itemHeight * 0.25f) < top && dy < 0.0f) || (this.totalScrollY + (this.itemHeight * 0.25f) > bottom && dy > 0.0f)) {
                        this.totalScrollY -= dy;
                        isIgnore = true;
                    } else {
                        isIgnore = false;
                    }
                }
                break;
        }
        if (!isIgnore && event.getAction() != 0) {
            invalidate();
            return true;
        }
        return true;
    }

    public int getItemsCount() {
        if (this.adapter != null) {
            return this.adapter.getItemsCount();
        }
        return 0;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void isCenterLabel(boolean isCenterLabel) {
        this.isCenterLabel = isCenterLabel;
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public void setIsOptions(boolean options) {
        this.isOptions = options;
    }

    public void setTextColorOut(int textColorOut) {
        if (textColorOut != 0) {
            this.textColorOut = textColorOut;
            this.paintOuterText.setColor(this.textColorOut);
        }
    }

    public void setTextColorCenter(int textColorCenter) {
        if (textColorCenter != 0) {
            this.textColorCenter = textColorCenter;
            this.paintCenterText.setColor(this.textColorCenter);
        }
    }

    public void setTextXOffset(int textXOffset) {
        this.textXOffset = textXOffset;
        if (textXOffset != 0) {
            this.paintCenterText.setTextScaleX(1.0f);
        }
    }

    public void setDividerColor(int dividerColor) {
        if (dividerColor != 0) {
            this.dividerColor = dividerColor;
            this.paintIndicator.setColor(this.dividerColor);
        }
    }

    public void setDividerType(DividerType dividerType) {
        this.dividerType = dividerType;
    }

    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        if (lineSpacingMultiplier != 0.0f) {
            this.lineSpacingMultiplier = lineSpacingMultiplier;
            judgeLineSpace();
        }
    }

    public boolean isLoop() {
        return this.isLoop;
    }

    public float getTotalScrollY() {
        return this.totalScrollY;
    }

    public void setTotalScrollY(float totalScrollY) {
        this.totalScrollY = totalScrollY;
    }

    public float getItemHeight() {
        return this.itemHeight;
    }

    public int getInitPosition() {
        return this.initPosition;
    }

    @Override // android.view.View
    public Handler getHandler() {
        return this.handler;
    }
}
