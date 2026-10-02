package com.facebook.react.views.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactPicker extends Spinner {
    private int mMode;
    private OnSelectListener mOnSelectListener;
    private Integer mPrimaryColor;
    private Integer mStagedSelection;
    private boolean mSuppressNextEvent;
    private final Runnable measureAndLayout;

    public interface OnSelectListener {
        void onItemSelected(int i);
    }

    public ReactPicker(Context context) {
        super(context);
        this.mMode = 0;
        this.measureAndLayout = new Runnable() { // from class: com.facebook.react.views.picker.ReactPicker.1
            @Override // java.lang.Runnable
            public void run() {
                ReactPicker.this.measure(View.MeasureSpec.makeMeasureSpec(ReactPicker.this.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ReactPicker.this.getHeight(), 1073741824));
                ReactPicker.this.layout(ReactPicker.this.getLeft(), ReactPicker.this.getTop(), ReactPicker.this.getRight(), ReactPicker.this.getBottom());
            }
        };
    }

    public ReactPicker(Context context, int mode) {
        super(context, mode);
        this.mMode = 0;
        this.measureAndLayout = new Runnable() { // from class: com.facebook.react.views.picker.ReactPicker.1
            @Override // java.lang.Runnable
            public void run() {
                ReactPicker.this.measure(View.MeasureSpec.makeMeasureSpec(ReactPicker.this.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ReactPicker.this.getHeight(), 1073741824));
                ReactPicker.this.layout(ReactPicker.this.getLeft(), ReactPicker.this.getTop(), ReactPicker.this.getRight(), ReactPicker.this.getBottom());
            }
        };
        this.mMode = mode;
    }

    public ReactPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMode = 0;
        this.measureAndLayout = new Runnable() { // from class: com.facebook.react.views.picker.ReactPicker.1
            @Override // java.lang.Runnable
            public void run() {
                ReactPicker.this.measure(View.MeasureSpec.makeMeasureSpec(ReactPicker.this.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ReactPicker.this.getHeight(), 1073741824));
                ReactPicker.this.layout(ReactPicker.this.getLeft(), ReactPicker.this.getTop(), ReactPicker.this.getRight(), ReactPicker.this.getBottom());
            }
        };
    }

    public ReactPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mMode = 0;
        this.measureAndLayout = new Runnable() { // from class: com.facebook.react.views.picker.ReactPicker.1
            @Override // java.lang.Runnable
            public void run() {
                ReactPicker.this.measure(View.MeasureSpec.makeMeasureSpec(ReactPicker.this.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ReactPicker.this.getHeight(), 1073741824));
                ReactPicker.this.layout(ReactPicker.this.getLeft(), ReactPicker.this.getTop(), ReactPicker.this.getRight(), ReactPicker.this.getBottom());
            }
        };
    }

    @Override // android.widget.AbsSpinner, android.view.View, android.view.ViewParent
    public void requestLayout() {
        super.requestLayout();
        post(this.measureAndLayout);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        if (getOnItemSelectedListener() == null) {
            this.mSuppressNextEvent = true;
            setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.facebook.react.views.picker.ReactPicker.2
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!ReactPicker.this.mSuppressNextEvent && ReactPicker.this.mOnSelectListener != null) {
                        ReactPicker.this.mOnSelectListener.onItemSelected(position);
                    }
                    ReactPicker.this.mSuppressNextEvent = false;
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> parent) {
                    if (!ReactPicker.this.mSuppressNextEvent && ReactPicker.this.mOnSelectListener != null) {
                        ReactPicker.this.mOnSelectListener.onItemSelected(-1);
                    }
                    ReactPicker.this.mSuppressNextEvent = false;
                }
            });
        }
        this.mOnSelectListener = onSelectListener;
    }

    public OnSelectListener getOnSelectListener() {
        return this.mOnSelectListener;
    }

    public void setStagedSelection(int selection) {
        this.mStagedSelection = Integer.valueOf(selection);
    }

    public void updateStagedSelection() {
        if (this.mStagedSelection != null) {
            setSelectionWithSuppressEvent(this.mStagedSelection.intValue());
            this.mStagedSelection = null;
        }
    }

    private void setSelectionWithSuppressEvent(int position) {
        if (position != getSelectedItemPosition()) {
            this.mSuppressNextEvent = true;
            setSelection(position);
        }
    }

    public Integer getPrimaryColor() {
        return this.mPrimaryColor;
    }

    public void setPrimaryColor(Integer primaryColor) {
        this.mPrimaryColor = primaryColor;
    }

    public int getMode() {
        return this.mMode;
    }
}
