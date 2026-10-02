package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AreaListV2 implements SerializableEx {
    private static final long serialVersionUID = 6997704511515772014L;
    public List<Area> areaList = new ArrayList();

    public static class Area implements SerializableEx {
        private static final long serialVersionUID = -3988062826668465271L;
        public int areaId;
        public String areaName;
    }
}
