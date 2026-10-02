package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ProgramData implements SerializableEx {
    public Date nowTime;
    public List<PairProgram> pgs = new ArrayList();

    public static class PairProgram implements SerializableEx, Cloneable {
        public String cate;
        public Date cdate;
        public Date cedate;
        public int cid;
        public int cnum;
        public int ctype;
        public String flcate;
        public int ilike;
        public short ishd;
        public Date ndate;
        public Date nedate;
        public short typeId;
        public int weight;
        public int wnum;
        public String ctry = Constants.MAIN_VERSION_TAG;
        public String resId = Constants.MAIN_VERSION_TAG;
        public int epi = 0;
        public String sn = Constants.MAIN_VERSION_TAG;
        public String thumb = Constants.MAIN_VERSION_TAG;
        public String nn = Constants.MAIN_VERSION_TAG;

        public PairProgram shallowCopy() throws CloneNotSupportedException {
            return (PairProgram) super.clone();
        }
    }
}
