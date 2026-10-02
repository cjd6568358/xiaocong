package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ProgramGuideList implements SerializableEx {
    private static final long serialVersionUID = -1313833530073934139L;
    public List<PairProgramGuide> pgs = new ArrayList();

    public static class PairProgramGuide implements SerializableEx {
        private static final long serialVersionUID = 5776972153550917325L;
        public String cate;
        public int cid;
        public Date edate;
        public short ishd;
        public Date sdate;
        public short typeId;
        public String resId = Constants.MAIN_VERSION_TAG;
        public int epi = 0;
        public String sn = Constants.MAIN_VERSION_TAG;
        public String thumb = Constants.MAIN_VERSION_TAG;
    }
}
