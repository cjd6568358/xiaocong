package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WorkdayWeekModel {
    private boolean isChecked = false;
    private String weekName;
    private int weekValue;

    public String getWeekName() {
        return this.weekName;
    }

    public int getWeekValue() {
        return this.weekValue;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public void setWeekValue(int weekValue) {
        this.weekValue = weekValue;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }
}
