package com.kookong.app.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PlayingTimeDataV2 implements SerializableEx {
    private static final long serialVersionUID = 1;
    public String resId;
    public String thumb;
    public short typeId;
    public List<EPGDataV2> tvs = new ArrayList();
    public int now = 0;

    public static class EPGDataV2 implements SerializableEx {
        private static final long serialVersionUID = 1;
        public int channelId;
        public String countryCode;
        public Date endTime;
        public String epgName;
        public int epi;
        public Date startTime;
    }
}
