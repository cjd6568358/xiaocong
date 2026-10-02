package com.xiaocong.smarthome.wheel;

import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class SmoothScrollTimerTask extends TimerTask {
    int offset;
    final WheelView wheelView;
    int realTotalOffset = Integer.MAX_VALUE;
    int realOffset = 0;

    SmoothScrollTimerTask(WheelView wheelview, int offset) {
        this.wheelView = wheelview;
        this.offset = offset;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() {
        if (this.realTotalOffset == Integer.MAX_VALUE) {
            this.realTotalOffset = this.offset;
        }
        this.realOffset = (int) (this.realTotalOffset * 0.1f);
        if (this.realOffset == 0) {
            if (this.realTotalOffset < 0) {
                this.realOffset = -1;
            } else {
                this.realOffset = 1;
            }
        }
        if (Math.abs(this.realTotalOffset) <= 0) {
            this.wheelView.cancelFuture();
            this.wheelView.handler.sendEmptyMessage(3000);
        } else {
            this.wheelView.totalScrollY += this.realOffset;
            this.wheelView.handler.sendEmptyMessage(1000);
            this.realTotalOffset -= this.realOffset;
        }
    }
}
