package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ParameterValueNumModel {
    private int doubleLength;
    private int max;
    private int min;
    private float size;
    private String unit;

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getUnit() {
        return this.unit;
    }

    public int getMax() {
        return this.max;
    }

    public int getMin() {
        return this.min;
    }

    public float getSize() {
        return this.size;
    }

    public int getDoubleLength() {
        return this.doubleLength;
    }

    public void setDoubleLength(int doubleLength) {
        this.doubleLength = doubleLength;
    }
}
