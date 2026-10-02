package com.xiaocong.smarthome.pickerview.adapter;

import com.xiaocong.smarthome.wheelview.adapter.WheelAdapter;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ArrayWheelAdapter<T> implements WheelAdapter {
    private List<T> items;

    public ArrayWheelAdapter(List<T> items) {
        this.items = items;
    }

    @Override // com.xiaocong.smarthome.wheelview.adapter.WheelAdapter
    public Object getItem(int index) {
        return (index < 0 || index >= this.items.size()) ? "" : this.items.get(index);
    }

    @Override // com.xiaocong.smarthome.wheelview.adapter.WheelAdapter
    public int getItemsCount() {
        return this.items.size();
    }
}
