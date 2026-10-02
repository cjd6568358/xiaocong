package com.xiaocong.smarthome.timerRuler.bean;

import com.xiaocong.smarthome.timerRuler.utils.DateUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TimeSlot {
    private long currentDayStartTimeMillis;
    private long endTime;
    private long startTime;

    public TimeSlot(long currentDayStartTimeMillis, long startTime, long endTime) {
        this.currentDayStartTimeMillis = currentDayStartTimeMillis;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public float getStartTime() {
        if (this.currentDayStartTimeMillis > this.startTime) {
            return 0.0f;
        }
        return (this.startTime - DateUtils.getTodayStart(this.startTime)) / 1000.0f;
    }

    public float getEndTime() {
        if (this.currentDayStartTimeMillis + 86400000 <= this.endTime) {
            return 86399.0f;
        }
        return (this.endTime - DateUtils.getTodayStart(this.endTime)) / 1000.0f;
    }

    public String toString() {
        return "TimeSlot{startTime=" + getStartTime() + ", endTime=" + getEndTime() + '}';
    }
}
