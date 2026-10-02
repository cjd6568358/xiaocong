package com.kookong.app.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StillsData implements SerializableEx {
    private static final long serialVersionUID = 4825538694625631616L;
    public List<Stills> list = new ArrayList();

    public static class Stills implements SerializableEx {
        private static final long serialVersionUID = 3107688179041007340L;
        public String burl;
        public int cnum;
        public Date date;
        public String desc;
        public int id;
        public String tag;
        public short tagId;
        public String url;
    }
}
