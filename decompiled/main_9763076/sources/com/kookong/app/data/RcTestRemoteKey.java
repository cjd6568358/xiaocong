package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RcTestRemoteKey implements SerializableEx, Comparable<RcTestRemoteKey> {
    protected String dataCode;
    protected String displayName;
    protected int formatId;
    protected int frequency;
    protected String fullCode;
    protected int functionId;
    protected String functionName;
    protected String pulseData;
    protected int rank;
    protected String remoteIds;
    protected List<RcRemoteKeyExt> remoteKeyExtList = new ArrayList();
    protected int remoteKeyId;
    protected int repeatCount;
    protected short repeatType;
    protected String systemCode;
    protected short type;

    public int getRemoteKeyId() {
        return this.remoteKeyId;
    }

    public void setRemoteKeyId(int remoteKeyId) {
        this.remoteKeyId = remoteKeyId;
    }

    public String getRemoteIds() {
        return this.remoteIds;
    }

    public void setRemoteIds(String remoteIds) {
        this.remoteIds = remoteIds;
    }

    public int getFunctionId() {
        return this.functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public short getRepeatType() {
        return this.repeatType;
    }

    public void setRepeatType(short repeatType) {
        this.repeatType = repeatType;
    }

    public int getRepeatCount() {
        return this.repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public int getFormatId() {
        return this.formatId;
    }

    public void setFormatId(int formatId) {
        this.formatId = formatId;
    }

    public String getSystemCode() {
        return this.systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getDataCode() {
        return this.dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getFullCode() {
        return this.fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getPulseData() {
        return this.pulseData;
    }

    public void setPulseData(String pulseData) {
        this.pulseData = pulseData;
    }

    public List<RcRemoteKeyExt> getRemoteKeyExtList() {
        return this.remoteKeyExtList;
    }

    public void setRemoteKeyExtList(List<RcRemoteKeyExt> remoteKeyExtList) {
        this.remoteKeyExtList = remoteKeyExtList;
    }

    public short getType() {
        return this.type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String toString() {
        StringBuffer ret = new StringBuffer();
        ret.append("#com.kookong.app.data.RcTestRemoteKey\n");
        ret.append("frequency=" + this.frequency + "\n");
        ret.append("type=" + ((int) this.type) + "\n");
        ret.append("remoteKeyId=" + this.remoteKeyId + "\n");
        ret.append("remoteIds=" + this.remoteIds + "\n");
        ret.append("functionId=" + this.functionId + "\n");
        ret.append("functionName=" + this.functionName + "\n");
        ret.append("displayName=" + this.displayName + "\n");
        ret.append("repeatType=" + ((int) this.repeatType) + "\n");
        ret.append("repeatCount=" + this.repeatCount + "\n");
        ret.append("formatId=" + this.formatId + "\n");
        ret.append("systemCode=" + this.systemCode + "\n");
        ret.append("dataCode=" + this.dataCode + "\n");
        ret.append("fullCode=" + this.fullCode + "\n");
        ret.append("pulseData=" + this.pulseData + "\n");
        ret.append("rank=" + this.rank + "\n");
        ret.append("remoteKeyExtList=" + this.remoteKeyExtList + "\n");
        return ret.toString();
    }

    @Override // java.lang.Comparable
    public int compareTo(RcTestRemoteKey o) {
        int compare = o.getRemoteIds().split(",").length - getRemoteIds().split(",").length;
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
