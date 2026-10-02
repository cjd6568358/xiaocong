package com.kookong.app.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SearchDataList implements SerializableEx {
    private static final long serialVersionUID = -5583206361464565727L;
    public List<SearchData> list = new ArrayList();
    public long now = 0;

    public static class SearchData implements SerializableEx {
        private static final long serialVersionUID = -3679007135004033905L;
        public int channelId;
        public String countryId;
        public Date endTime;
        public int epi;
        public String name;
        public String resId;
        public Date startTime;
        public String thumb;
        public short typeId;
    }
}
