package com.facebook.react.views.slider;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;
import bsh.ParserConstants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactSlider extends SeekBar {
    private static int DEFAULT_TOTAL_STEPS = ParserConstants.LSHIFTASSIGN;
    private double mMaxValue;
    private double mMinValue;
    private double mStep;
    private double mStepCalculated;
    private double mValue;

    public ReactSlider(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        this.mMinValue = 0.0d;
        this.mMaxValue = 0.0d;
        this.mValue = 0.0d;
        this.mStep = 0.0d;
        this.mStepCalculated = 0.0d;
    }

    void setMaxValue(double max) {
        this.mMaxValue = max;
        updateAll();
    }

    void setMinValue(double min) {
        this.mMinValue = min;
        updateAll();
    }

    void setValue(double value) {
        this.mValue = value;
        updateValue();
    }

    void setStep(double step) {
        this.mStep = step;
        updateAll();
    }

    public double toRealProgress(int seekBarProgress) {
        return seekBarProgress == getMax() ? this.mMaxValue : (((double) seekBarProgress) * getStepValue()) + this.mMinValue;
    }

    private void updateAll() {
        if (this.mStep == 0.0d) {
            this.mStepCalculated = (this.mMaxValue - this.mMinValue) / ((double) DEFAULT_TOTAL_STEPS);
        }
        setMax(getTotalSteps());
        updateValue();
    }

    private void updateValue() {
        setProgress((int) Math.round(((this.mValue - this.mMinValue) / (this.mMaxValue - this.mMinValue)) * ((double) getTotalSteps())));
    }

    private int getTotalSteps() {
        return (int) Math.ceil((this.mMaxValue - this.mMinValue) / getStepValue());
    }

    private double getStepValue() {
        return this.mStep > 0.0d ? this.mStep : this.mStepCalculated;
    }
}
