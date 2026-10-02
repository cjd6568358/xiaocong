package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IrDataList implements SerializableEx {
    private static final long serialVersionUID = -857251495051586423L;
    protected List<IrData> irDataList = new ArrayList();

    public List<IrData> getIrDataList() {
        return this.irDataList;
    }

    public void setIrDataList(List<IrData> irDataList) {
        this.irDataList = irDataList;
    }
}
