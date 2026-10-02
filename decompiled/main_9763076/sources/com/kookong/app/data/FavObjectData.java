package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FavObjectData implements SerializableEx {
    public List<FavObject> list = new ArrayList();
    public int total;

    public static class FavObject implements SerializableEx {
        public String cast;
        public int cnum;
        public String name;
        public int num;
        public String pic;
        public String resId;
        public short typeId;
    }
}
