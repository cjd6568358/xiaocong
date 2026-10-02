package com.xiaocong.smarthome.pickerview.adapter;

import com.xiaocong.smarthome.wheelview.adapter.WheelAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class NumericWheelAdapter implements WheelAdapter {
    private int maxValue;
    private int minValue;

    public NumericWheelAdapter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override // com.xiaocong.smarthome.wheelview.adapter.WheelAdapter
    public Object getItem(int index) {
        if (index < 0 || index >= getItemsCount()) {
            return 0;
        }
        int value = this.minValue + index;
        return Integer.valueOf(value);
    }

    @Override // com.xiaocong.smarthome.wheelview.adapter.WheelAdapter
    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }
}
