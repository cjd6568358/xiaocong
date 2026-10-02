package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultChannelList implements SerializableEx {
    private static final long serialVersionUID = 6817780118420169169L;
    public List<DefaultChannel> list = new ArrayList();

    public static class DefaultChannel implements SerializableEx, Comparable {
        private static final long serialVersionUID = -2556000116916727680L;
        public int cid;
        public String ctrid = Constants.MAIN_VERSION_TAG;
        public short fee;
        public String fl;
        public String logo;
        public String name;
        public short type;

        @Override // java.lang.Comparable
        public int compareTo(Object o) {
            DefaultChannel defaultChannel = (DefaultChannel) o;
            return this.fl.compareTo(defaultChannel.fl);
        }
    }
}
