package com.xiaocong.smarthome.timerRuler.listener;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface OnBarMoveListener {
    void onBarMoveFinish(long j);

    void onBarMoving(long j);

    void onDragBar(boolean z, long j);

    void onMaxScale();

    void onMinScale();

    void onMoveExceedEndTime();

    void onMoveExceedStartTime();
}
