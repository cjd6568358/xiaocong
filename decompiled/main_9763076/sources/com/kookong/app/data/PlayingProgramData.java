package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PlayingProgramData implements SerializableEx {
    public Date nowTime;
    public List<PairPlayingProgram> pgs = new ArrayList();

    public static class PairPlayingProgram implements SerializableEx {
        public int cid;
        public short ishd;
        public String ctry = Constants.MAIN_VERSION_TAG;
        public String sn = Constants.MAIN_VERSION_TAG;
        public int epi = 0;
    }
}
