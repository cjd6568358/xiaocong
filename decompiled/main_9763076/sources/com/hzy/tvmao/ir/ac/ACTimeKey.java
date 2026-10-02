package com.hzy.tvmao.ir.ac;

import com.hzy.tvmao.utils.e;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ACTimeKey implements Serializable {
    private static final long serialVersionUID = 1;
    private int curSetTime;
    private int fid;
    private int timeDisplayValue;
    private List<Integer> timeRangeList = new ArrayList();
    private long timeingEndTime;

    public int increaseTime() {
        int iIndexOf = this.timeRangeList.indexOf(Integer.valueOf(this.timeDisplayValue)) + 1;
        List<Integer> list = this.timeRangeList;
        if (iIndexOf >= this.timeRangeList.size()) {
            iIndexOf = 0;
        }
        this.timeDisplayValue = list.get(iIndexOf).intValue();
        return this.timeDisplayValue;
    }

    public int decreaseTime() {
        int iIndexOf = this.timeRangeList.indexOf(Integer.valueOf(this.timeDisplayValue)) - 1;
        List<Integer> list = this.timeRangeList;
        if (iIndexOf < 0) {
            iIndexOf = this.timeRangeList.size() - 1;
        }
        this.timeDisplayValue = list.get(iIndexOf).intValue();
        return this.timeDisplayValue;
    }

    public void addTimeing() {
        setCurSetTime(getTimeDisplayValue());
        this.timeingEndTime = new Date(e.a() + ((long) (this.curSetTime * 60 * 1000))).getTime();
    }

    public void cancleTimeing() {
        setCurSetTime(0);
        this.timeingEndTime = 0L;
        if (this.timeRangeList.size() > 0) {
            this.timeDisplayValue = this.timeRangeList.get(0).intValue();
        } else {
            this.timeDisplayValue = 0;
        }
    }

    public int cutTimeing() {
        int i;
        if (!timeIsHaveRegular()) {
            return this.curSetTime;
        }
        int iA = (int) ((this.timeingEndTime - e.a()) / 60000);
        int iIndexOf = this.timeRangeList.indexOf(Integer.valueOf(this.curSetTime));
        if (iIndexOf == -1) {
            cancleTimeing();
            return this.curSetTime;
        }
        for (int i2 = 0; i2 <= iIndexOf; i2++) {
            if (iA <= this.timeRangeList.get(i2).intValue()) {
                i = i2;
                return this.timeRangeList.get(i).intValue();
            }
        }
        i = 0;
        return this.timeRangeList.get(i).intValue();
    }

    public int getTimeDisplayValue() {
        return this.timeDisplayValue;
    }

    public void setTimeDisplayValue(int i) {
        this.timeDisplayValue = i;
    }

    public int getFid() {
        return this.fid;
    }

    public void setFid(int i) {
        this.fid = i;
    }

    public List<Integer> getTimeRangeList() {
        return this.timeRangeList;
    }

    public void setTimeRangeList(List<Integer> list) {
        this.timeRangeList = list;
    }

    public int getCurSetTime() {
        return this.curSetTime;
    }

    public void setCurSetTime(int i) {
        this.curSetTime = i;
    }

    public boolean timeIsHaveRegular() {
        return this.curSetTime != 0 && this.timeingEndTime > e.a();
    }

    public void timingCheck() {
        if (this.timeingEndTime <= e.a()) {
            cancleTimeing();
        }
    }

    public long getTimeingEndTime() {
        return this.timeingEndTime;
    }

    public void setTimeingEndTime(long j) {
        this.timeingEndTime = j;
    }
}
