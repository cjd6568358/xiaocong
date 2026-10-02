package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FavChannelData implements SerializableEx {
    public List<FavChannel> list = new ArrayList();
    public int total;

    public static class FavChannel implements SerializableEx {
        public int cid;
        public String ctry = Constants.MAIN_VERSION_TAG;
        public short ishd;
        public String logo;
        public String name;
    }
}
