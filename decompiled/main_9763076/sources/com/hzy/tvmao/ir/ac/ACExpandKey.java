package com.hzy.tvmao.ir.ac;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ACExpandKey implements Serializable {
    private static final long serialVersionUID = 1;
    private int curState;
    private int fid;
    private int maxState;
    private int minState;
    private List<Integer> supportModelList = new ArrayList();
    private int supportPower;

    public void changeState(int i, int i2) {
        if (isCanUsed(i, i2)) {
            this.curState++;
            this.curState = this.curState > this.maxState ? this.minState : this.curState;
        }
    }

    public void changeToTargetState(int i) {
        this.curState = i;
    }

    public boolean keyIsManyState() {
        return this.maxState - this.minState > 1;
    }

    public boolean keyIsSingleState() {
        return this.maxState - this.minState == 0;
    }

    public boolean keyIsSupportClose() {
        return this.minState == 0;
    }

    public int getExpandKeyState(int i, int i2) {
        if (isCanUsed(i, i2)) {
            return this.curState;
        }
        return -1;
    }

    public boolean isUsedAtPower(int i) {
        if (this.supportPower == 2) {
            return true;
        }
        if (i == 1) {
            if (this.supportPower == 0) {
                return false;
            }
            if (this.supportPower == 1) {
                return true;
            }
        } else {
            if (this.supportPower == 1) {
                return false;
            }
            if (this.supportPower == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isSupportModel(int i) {
        return this.supportModelList.indexOf(Integer.valueOf(i)) != -1;
    }

    public boolean isCanUsed(int i, int i2) {
        return isUsedAtPower(i) && isSupportModel(i2);
    }

    public int getFid() {
        return this.fid;
    }

    public void setFid(int i) {
        this.fid = i;
    }

    public int getMinState() {
        return this.minState;
    }

    public void setMinState(int i) {
        this.minState = i;
    }

    public int getMaxState() {
        return this.maxState;
    }

    public void setMaxState(int i) {
        this.maxState = i;
    }

    public void setCurState(int i) {
        this.curState = i;
    }

    public int getSupportPower() {
        return this.supportPower;
    }

    public void setSupportPower(int i) {
        this.supportPower = i;
    }

    public int getCurState() {
        return this.curState;
    }

    public List<Integer> getSupportModelList() {
        return this.supportModelList;
    }

    public void setSupportModelList(List<Integer> list) {
        this.supportModelList = list;
    }
}
