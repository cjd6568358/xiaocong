package com.xiaocong.smarthome.wheel.ruler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import com.xiaocong.smarthome.uilib.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class RulerWheelView extends View implements GestureDetector.OnGestureListener {
    private String mAdditionCenterMark;
    private float mAdditionCenterMarkWidth;
    private float mBottomSpace;
    private int mCenterIndex;
    private Path mCenterIndicatorPath;
    private float mCenterMarkWidth;
    private float mCenterTextSize;
    private RectF mContentRectF;
    private float mCursorSize;
    private int mFadeMarkColor;
    private boolean mFling;
    private GestureDetectorCompat mGestureDetectorCompat;
    private int mHeight;
    private int mHighlightColor;
    private float mIntervalDis;
    private float mIntervalFactor;
    private List<String> mItems;
    private int mLastSelectedIndex;
    private int mMarkColor;
    private int mMarkCount;
    private Paint mMarkPaint;
    private float mMarkRatio;
    private int mMarkTextColor;
    private TextPaint mMarkTextPaint;
    private float mMarkWidth;
    private float mMaxOverScrollDistance;
    private int mMaxSelectableIndex;
    private int mMinSelectableIndex;
    private float mNormalTextSize;
    private OnWheelItemSelectedListener mOnWheelItemSelectedListener;
    private OverScroller mScroller;
    private float mTopSpace;
    private int mUnMarkTextColor;
    private int mViewScopeSize;

    public interface OnWheelItemSelectedListener {
        void onWheelItemChanged(RulerWheelView rulerWheelView, int i);

        void onWheelItemSelected(RulerWheelView rulerWheelView, int i);
    }

    public RulerWheelView(Context context) {
        super(context);
        this.mCenterIndex = -1;
        this.mIntervalFactor = 1.2f;
        this.mMarkRatio = 0.7f;
        this.mCenterIndicatorPath = new Path();
        this.mFling = false;
        this.mLastSelectedIndex = -1;
        this.mMinSelectableIndex = Integer.MIN_VALUE;
        this.mMaxSelectableIndex = Integer.MAX_VALUE;
        init(null);
    }

    public RulerWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCenterIndex = -1;
        this.mIntervalFactor = 1.2f;
        this.mMarkRatio = 0.7f;
        this.mCenterIndicatorPath = new Path();
        this.mFling = false;
        this.mLastSelectedIndex = -1;
        this.mMinSelectableIndex = Integer.MIN_VALUE;
        this.mMaxSelectableIndex = Integer.MAX_VALUE;
        init(attrs);
    }

    public RulerWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCenterIndex = -1;
        this.mIntervalFactor = 1.2f;
        this.mMarkRatio = 0.7f;
        this.mCenterIndicatorPath = new Path();
        this.mFling = false;
        this.mLastSelectedIndex = -1;
        this.mMinSelectableIndex = Integer.MIN_VALUE;
        this.mMaxSelectableIndex = Integer.MAX_VALUE;
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        float density = getResources().getDisplayMetrics().density;
        this.mCenterMarkWidth = (int) ((1.5f * density) + 0.5f);
        this.mMarkWidth = density;
        this.mHighlightColor = -570311;
        this.mMarkTextColor = -10066330;
        this.mUnMarkTextColor = 0;
        this.mMarkColor = -1118482;
        this.mCursorSize = density * 18.0f;
        this.mCenterTextSize = 22.0f * density;
        this.mNormalTextSize = density * 18.0f;
        this.mBottomSpace = 6.0f * density;
        TypedArray ta = attrs == null ? null : getContext().obtainStyledAttributes(attrs, R.styleable.rulerWheelView);
        if (ta != null) {
            this.mHighlightColor = ta.getColor(R.styleable.rulerWheelView_highlightColor, this.mHighlightColor);
            this.mMarkTextColor = ta.getColor(R.styleable.rulerWheelView_markTextColor, this.mMarkTextColor);
            this.mUnMarkTextColor = ta.getColor(R.styleable.rulerWheelView_unMarkTextColor, this.mUnMarkTextColor);
            this.mMarkColor = ta.getColor(R.styleable.rulerWheelView_markColor, this.mMarkColor);
            this.mIntervalFactor = ta.getFloat(R.styleable.rulerWheelView_intervalFactor, this.mIntervalFactor);
            this.mMarkRatio = ta.getFloat(R.styleable.rulerWheelView_markRatio, this.mMarkRatio);
            this.mAdditionCenterMark = ta.getString(R.styleable.rulerWheelView_additionalCenterMark);
            this.mCenterTextSize = ta.getDimension(R.styleable.rulerWheelView_centerMarkTextSize, this.mCenterTextSize);
            this.mNormalTextSize = ta.getDimension(R.styleable.rulerWheelView_markTextSize, this.mNormalTextSize);
            this.mCursorSize = ta.getDimension(R.styleable.rulerWheelView_cursorSize, this.mCursorSize);
        }
        this.mFadeMarkColor = this.mHighlightColor & (-1426063361);
        this.mIntervalFactor = Math.max(1.0f, this.mIntervalFactor);
        this.mMarkRatio = Math.min(1.0f, this.mMarkRatio);
        this.mTopSpace = this.mCursorSize + (2.0f * density);
        this.mMarkPaint = new Paint(1);
        this.mMarkTextPaint = new TextPaint(1);
        this.mMarkTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mMarkTextPaint.setColor(this.mHighlightColor);
        this.mMarkPaint.setColor(this.mMarkColor);
        this.mMarkPaint.setStrokeWidth(this.mCenterMarkWidth);
        this.mMarkTextPaint.setTextSize(this.mCenterTextSize);
        calcIntervalDis();
        this.mScroller = new OverScroller(getContext());
        this.mContentRectF = new RectF();
        this.mGestureDetectorCompat = new GestureDetectorCompat(getContext(), this);
        selectIndex(0);
    }

    private void calcIntervalDis() {
        if (this.mMarkTextPaint != null) {
            Rect temp = new Rect();
            int max = 0;
            if (this.mItems == null || this.mItems.size() <= 0) {
                this.mMarkTextPaint.getTextBounds("888888", 0, "888888".length(), temp);
                max = temp.width();
            } else {
                for (String i : this.mItems) {
                    this.mMarkTextPaint.getTextBounds(i, 0, i.length(), temp);
                    if (temp.width() > max) {
                        max = temp.width();
                    }
                }
            }
            if (!TextUtils.isEmpty(this.mAdditionCenterMark)) {
                this.mMarkTextPaint.setTextSize(this.mNormalTextSize);
                this.mMarkTextPaint.getTextBounds(this.mAdditionCenterMark, 0, this.mAdditionCenterMark.length(), temp);
                this.mAdditionCenterMarkWidth = temp.width();
                max += temp.width();
            }
            this.mIntervalDis = max * this.mIntervalFactor;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int measureMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int measureSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int result = getSuggestedMinimumWidth();
        switch (measureMode) {
            case Integer.MIN_VALUE:
            case 1073741824:
                return measureSize;
            default:
                return result;
        }
    }

    private int measureHeight(int heightMeasure) {
        int measureMode = View.MeasureSpec.getMode(heightMeasure);
        int measureSize = View.MeasureSpec.getSize(heightMeasure);
        int result = (int) (this.mBottomSpace + (this.mTopSpace * 2.0f) + this.mCenterTextSize);
        switch (measureMode) {
            case Integer.MIN_VALUE:
                return Math.min(result, measureSize);
            case 1073741824:
                return Math.max(result, measureSize);
            default:
                return result;
        }
    }

    public void fling(int velocityX, int velocityY) {
        this.mScroller.fling(getScrollX(), getScrollY(), velocityX, velocityY, (int) ((-this.mMaxOverScrollDistance) + (this.mMinSelectableIndex * this.mIntervalDis)), (int) ((this.mContentRectF.width() - this.mMaxOverScrollDistance) - (((this.mMarkCount - 1) - this.mMaxSelectableIndex) * this.mIntervalDis)), 0, 0, (int) this.mMaxOverScrollDistance, 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            this.mHeight = h;
            this.mMaxOverScrollDistance = w / 2.0f;
            this.mContentRectF.set(0.0f, 0.0f, (this.mMarkCount - 1) * this.mIntervalDis, h);
            this.mViewScopeSize = (int) Math.ceil(this.mMaxOverScrollDistance / this.mIntervalDis);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCenterIndicatorPath.reset();
        float sizeDiv2 = this.mCursorSize / 2.0f;
        float sizeDiv3 = this.mCursorSize / 3.0f;
        this.mCenterIndicatorPath.moveTo((this.mMaxOverScrollDistance - sizeDiv2) + getScrollX(), 0.0f);
        this.mCenterIndicatorPath.rLineTo(0.0f, sizeDiv3);
        this.mCenterIndicatorPath.rLineTo(sizeDiv2, sizeDiv2);
        this.mCenterIndicatorPath.rLineTo(sizeDiv2, -sizeDiv2);
        this.mCenterIndicatorPath.rLineTo(0.0f, -sizeDiv3);
        this.mCenterIndicatorPath.close();
        this.mMarkPaint.setColor(this.mHighlightColor);
        canvas.drawPath(this.mCenterIndicatorPath, this.mMarkPaint);
        int start = this.mCenterIndex - this.mViewScopeSize;
        int end = this.mCenterIndex + this.mViewScopeSize + 1;
        int start2 = Math.max(start, (-this.mViewScopeSize) * 2);
        int end2 = Math.min(end, this.mMarkCount + (this.mViewScopeSize * 2));
        if (this.mCenterIndex == this.mMaxSelectableIndex) {
            end2 += this.mViewScopeSize;
        } else if (this.mCenterIndex == this.mMinSelectableIndex) {
            start2 -= this.mViewScopeSize;
        }
        float x = start2 * this.mIntervalDis;
        float markHeight = ((this.mHeight - this.mBottomSpace) - this.mCenterTextSize) - this.mTopSpace;
        float smallMarkShrinkY = Math.min((markHeight - this.mMarkWidth) / 2.0f, ((1.0f - this.mMarkRatio) * markHeight) / 2.0f);
        for (int i = start2; i < end2; i++) {
            float tempDis = this.mIntervalDis / 5.0f;
            for (int offset = -2; offset < 3; offset++) {
                float ox = x + (offset * tempDis);
                if (i >= 0 && i <= this.mMarkCount && this.mCenterIndex == i) {
                    int tempOffset = Math.abs(offset);
                    if (tempOffset == 0) {
                        this.mMarkPaint.setColor(this.mHighlightColor);
                    } else if (tempOffset == 1) {
                        this.mMarkPaint.setColor(this.mFadeMarkColor);
                    } else {
                        this.mMarkPaint.setColor(this.mMarkColor);
                    }
                } else {
                    this.mMarkPaint.setColor(this.mMarkColor);
                }
                if (offset == 0) {
                    this.mMarkPaint.setStrokeWidth(this.mCenterMarkWidth);
                    canvas.drawLine(ox, this.mTopSpace, ox, this.mTopSpace + markHeight, this.mMarkPaint);
                } else {
                    this.mMarkPaint.setStrokeWidth(this.mMarkWidth);
                    canvas.drawLine(ox, this.mTopSpace + smallMarkShrinkY, ox, (this.mTopSpace + markHeight) - smallMarkShrinkY, this.mMarkPaint);
                }
            }
            if (this.mMarkCount > 0 && i >= 0 && i < this.mMarkCount) {
                CharSequence temp = this.mItems.get(i);
                if (this.mCenterIndex == i) {
                    this.mMarkTextPaint.setColor(this.mHighlightColor);
                    this.mMarkTextPaint.setTextSize(this.mCenterTextSize);
                    if (!TextUtils.isEmpty(this.mAdditionCenterMark)) {
                        float off = this.mAdditionCenterMarkWidth / 2.0f;
                        float tsize = this.mMarkTextPaint.measureText(temp, 0, temp.length());
                        canvas.drawText(temp, 0, temp.length(), x - off, this.mHeight - this.mBottomSpace, this.mMarkTextPaint);
                        this.mMarkTextPaint.setTextSize(this.mNormalTextSize);
                        canvas.drawText(this.mAdditionCenterMark, (tsize / 2.0f) + x, this.mHeight - this.mBottomSpace, this.mMarkTextPaint);
                    } else {
                        canvas.drawText(temp, 0, temp.length(), x, this.mHeight - this.mBottomSpace, this.mMarkTextPaint);
                    }
                } else {
                    this.mMarkTextPaint.setColor(this.mUnMarkTextColor);
                    this.mMarkTextPaint.setTextSize(this.mNormalTextSize);
                    canvas.drawText(temp, 0, temp.length(), x, this.mHeight - this.mBottomSpace, this.mMarkTextPaint);
                }
            }
            x += this.mIntervalDis;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mItems == null || this.mItems.size() == 0 || !isEnabled()) {
            return false;
        }
        boolean ret = this.mGestureDetectorCompat.onTouchEvent(event);
        if (!this.mFling && 1 == event.getAction()) {
            autoSettle();
            ret = true;
        }
        return ret || super.onTouchEvent(event);
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            refreshCenter();
            invalidate();
        } else if (this.mFling) {
            this.mFling = false;
            autoSettle();
        }
    }

    public void setAdditionCenterMark(String additionCenterMark) {
        this.mAdditionCenterMark = additionCenterMark;
        calcIntervalDis();
        invalidate();
    }

    private void autoSettle() {
        int sx = getScrollX();
        float dx = ((this.mCenterIndex * this.mIntervalDis) - sx) - this.mMaxOverScrollDistance;
        this.mScroller.startScroll(sx, 0, (int) dx, 0);
        postInvalidate();
        if (this.mLastSelectedIndex != this.mCenterIndex) {
            this.mLastSelectedIndex = this.mCenterIndex;
            if (this.mOnWheelItemSelectedListener != null) {
                this.mOnWheelItemSelectedListener.onWheelItemSelected(this, this.mCenterIndex);
            }
        }
    }

    private int safeCenter(int center) {
        if (center < this.mMinSelectableIndex) {
            return this.mMinSelectableIndex;
        }
        if (center > this.mMaxSelectableIndex) {
            return this.mMaxSelectableIndex;
        }
        return center;
    }

    private void refreshCenter(int offsetX) {
        int offset = (int) (offsetX + this.mMaxOverScrollDistance);
        int tempIndex = safeCenter(Math.round(offset / this.mIntervalDis));
        if (this.mCenterIndex != tempIndex) {
            this.mCenterIndex = tempIndex;
            if (this.mOnWheelItemSelectedListener != null) {
                this.mOnWheelItemSelectedListener.onWheelItemChanged(this, this.mCenterIndex);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCenter() {
        refreshCenter(getScrollX());
    }

    public void selectIndex(int index) {
        this.mCenterIndex = index;
        post(new Runnable() { // from class: com.xiaocong.smarthome.wheel.ruler.RulerWheelView.1
            @Override // java.lang.Runnable
            public void run() {
                RulerWheelView.this.scrollTo((int) ((RulerWheelView.this.mCenterIndex * RulerWheelView.this.mIntervalDis) - RulerWheelView.this.mMaxOverScrollDistance), 0);
                RulerWheelView.this.invalidate();
                RulerWheelView.this.refreshCenter();
            }
        });
    }

    public int getMinSelectableIndex() {
        return this.mMinSelectableIndex;
    }

    public void setMinSelectableIndex(int minSelectableIndex) {
        if (minSelectableIndex > this.mMaxSelectableIndex) {
            minSelectableIndex = this.mMaxSelectableIndex;
        }
        this.mMinSelectableIndex = minSelectableIndex;
        int afterCenter = safeCenter(this.mCenterIndex);
        if (afterCenter != this.mCenterIndex) {
            selectIndex(afterCenter);
        }
    }

    public int getMaxSelectableIndex() {
        return this.mMaxSelectableIndex;
    }

    public void setMaxSelectableIndex(int maxSelectableIndex) {
        if (maxSelectableIndex < this.mMinSelectableIndex) {
            maxSelectableIndex = this.mMinSelectableIndex;
        }
        this.mMaxSelectableIndex = maxSelectableIndex;
        int afterCenter = safeCenter(this.mCenterIndex);
        if (afterCenter != this.mCenterIndex) {
            selectIndex(afterCenter);
        }
    }

    public List<String> getItems() {
        return this.mItems;
    }

    public void setItems(List<String> items) {
        if (this.mItems == null) {
            this.mItems = new ArrayList();
        } else {
            this.mItems.clear();
        }
        this.mItems.addAll(items);
        this.mMarkCount = this.mItems == null ? 0 : this.mItems.size();
        if (this.mMarkCount > 0) {
            this.mMinSelectableIndex = Math.max(this.mMinSelectableIndex, 0);
            this.mMaxSelectableIndex = Math.min(this.mMaxSelectableIndex, this.mMarkCount - 1);
        }
        this.mContentRectF.set(0.0f, 0.0f, (this.mMarkCount - 1) * this.mIntervalDis, getMeasuredHeight());
        this.mCenterIndex = Math.min(this.mCenterIndex, this.mMarkCount);
        calcIntervalDis();
        invalidate();
    }

    public int getSelectedPosition() {
        return this.mCenterIndex;
    }

    public void setOnWheelItemSelectedListener(OnWheelItemSelectedListener onWheelItemSelectedListener) {
        this.mOnWheelItemSelectedListener = onWheelItemSelectedListener;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent e) {
        if (!this.mScroller.isFinished()) {
            this.mScroller.forceFinished(false);
        }
        this.mFling = false;
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent e) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent e) {
        playSoundEffect(0);
        refreshCenter((int) ((getScrollX() + e.getX()) - this.mMaxOverScrollDistance));
        autoSettle();
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent e) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float dis = distanceX;
        float scrollX = getScrollX();
        if (scrollX < (this.mMinSelectableIndex * this.mIntervalDis) - (2.0f * this.mMaxOverScrollDistance)) {
            dis = 0.0f;
        } else if (scrollX < (this.mMinSelectableIndex * this.mIntervalDis) - this.mMaxOverScrollDistance) {
            dis = distanceX / 4.0f;
        } else if (scrollX > this.mContentRectF.width() - (((this.mMarkCount - this.mMaxSelectableIndex) - 1) * this.mIntervalDis)) {
            dis = 0.0f;
        } else if (scrollX > (this.mContentRectF.width() - (((this.mMarkCount - this.mMaxSelectableIndex) - 1) * this.mIntervalDis)) - this.mMaxOverScrollDistance) {
            dis = distanceX / 4.0f;
        }
        scrollBy((int) dis, 0);
        refreshCenter();
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float scrollX = getScrollX();
        if (scrollX < (-this.mMaxOverScrollDistance) + (this.mMinSelectableIndex * this.mIntervalDis) || scrollX > (this.mContentRectF.width() - this.mMaxOverScrollDistance) - (((this.mMarkCount - 1) - this.mMaxSelectableIndex) * this.mIntervalDis)) {
            return false;
        }
        this.mFling = true;
        fling((int) (-velocityX), 0);
        return true;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.index = getSelectedPosition();
        ss.min = this.mMinSelectableIndex;
        ss.max = this.mMaxSelectableIndex;
        return ss;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mMinSelectableIndex = ss.min;
        this.mMaxSelectableIndex = ss.max;
        selectIndex(ss.index);
        requestLayout();
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.xiaocong.smarthome.wheel.ruler.RulerWheelView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int index;
        int max;
        int min;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.index = in.readInt();
            this.min = in.readInt();
            this.max = in.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.index);
            out.writeInt(this.min);
            out.writeInt(this.max);
        }

        public String toString() {
            return "WheelView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " index=" + this.index + " min=" + this.min + " max=" + this.max + "}";
        }
    }
}
