package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TvcsectionData implements SerializableEx {
    private static final long serialVersionUID = 1;
    public List<String> cfgRoleNames;
    public Map<Integer, String> resultCfgMap;
    public int seasonId;
    public String secDesc;
    public Date secPlayDate;
    public String secTitle;
    public int sectionId;
    public int ssnum;
    public int stnum;
    public String tvcId;
    public short vflag = 0;
    public Map<String, List<SimpleRole>> topResults = null;
    public Map<String, List<SimpleRole>> roleMapList = null;

    public static class SimpleRole implements SerializableEx, Comparator<SimpleRole> {
        private static final long serialVersionUID = 1;
        public String name;
        public int orderId;
        public int roleId;
        public String thumb;
        public boolean isShowOrder = false;
        public int result = 0;
        public String resaultTag = Constants.MAIN_VERSION_TAG;

        @Override // java.util.Comparator
        public int compare(SimpleRole r0, SimpleRole r1) {
            int int0 = r0.result;
            int int1 = r1.result;
            if (int0 > 0 && int1 > 0) {
                return int0 - int1;
            }
            if (int0 != 0) {
                return (int1 != 0 && int0 - int1 <= 0) ? 1 : -1;
            }
            return 1;
        }
    }
}
