package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CharInfoData implements SerializableEx {
    private static final long serialVersionUID = 1;
    List<CharInfo> list = new ArrayList();

    public static class CharInfo implements SerializableEx {
        private static final long serialVersionUID = 1;
        public String an;
        public int cid;
        public String cn;
        public String sid;
        public String t;
    }
}
