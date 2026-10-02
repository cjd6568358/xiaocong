package com.xiaocong.smarthome.wheelview.timer;

import com.xiaocong.smarthome.wheelview.view.WheelView;
import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class SmoothScrollTimerTask extends TimerTask {
    private int offset;
    private final WheelView wheelView;
    private int realTotalOffset = Integer.MAX_VALUE;
    private int realOffset = 0;

    public SmoothScrollTimerTask(WheelView wheelView, int offset) {
        this.wheelView = wheelView;
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
        if (Math.abs(this.realTotalOffset) <= 1) {
            this.wheelView.cancelFuture();
            this.wheelView.getHandler().sendEmptyMessage(3000);
            return;
        }
        this.wheelView.setTotalScrollY(this.wheelView.getTotalScrollY() + this.realOffset);
        if (!this.wheelView.isLoop()) {
            float itemHeight = this.wheelView.getItemHeight();
            float top = (-this.wheelView.getInitPosition()) * itemHeight;
            float bottom = ((this.wheelView.getItemsCount() - 1) - this.wheelView.getInitPosition()) * itemHeight;
            if (this.wheelView.getTotalScrollY() <= top || this.wheelView.getTotalScrollY() >= bottom) {
                this.wheelView.setTotalScrollY(this.wheelView.getTotalScrollY() - this.realOffset);
                this.wheelView.cancelFuture();
                this.wheelView.getHandler().sendEmptyMessage(3000);
                return;
            }
        }
        this.wheelView.getHandler().sendEmptyMessage(1000);
        this.realTotalOffset -= this.realOffset;
    }
}
