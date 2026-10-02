package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RcTestRemoteKeyV3 implements SerializableEx, Comparable<RcTestRemoteKeyV3> {
    private static final long serialVersionUID = -4648818445422729544L;
    public String displayName;
    public int frequency;
    public int functionId;
    public String functionName;
    public String pulseData;
    public int rank;
    public String remoteIds;
    public List<RcRemoteKeyExt> remoteKeyExtList = new ArrayList();

    public String toString() {
        StringBuffer ret = new StringBuffer();
        ret.append("#com.kookong.app.data.RcTestRemoteKey\n");
        ret.append("remoteIds=" + this.remoteIds + "\n");
        ret.append("frequency=" + this.frequency + "\n");
        ret.append("functionId=" + this.functionId + "\n");
        ret.append("functionName=" + this.functionName + "\n");
        ret.append("displayName=" + this.displayName + "\n");
        ret.append("pulseData=" + this.pulseData + "\n");
        ret.append("rank=" + this.rank + "\n");
        ret.append("remoteKeyExtList=" + this.remoteKeyExtList + "\n");
        return ret.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(RcTestRemoteKeyV3 o) {
        int compare = o.remoteIds.split(",").length - this.remoteIds.split(",").length;
        int rank = o.rank - this.rank;
        if (rank < 0) {
            return 1;
        }
        if (rank != 0) {
            return -1;
        }
        if (compare <= 0) {
            return compare == 0 ? 0 : -1;
        }
        return 1;
    }
}
