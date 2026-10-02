package com.xiaocong.smarthome.wheel;

import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class InertiaTimerTask extends TimerTask {
    float a = 2.1474836E9f;
    final float velocityY;
    final WheelView wheelView;

    InertiaTimerTask(WheelView wheelview, float velocityY) {
        this.wheelView = wheelview;
        this.velocityY = velocityY;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() {
        if (this.a == 2.1474836E9f) {
            if (Math.abs(this.velocityY) <= 2000.0f) {
                this.a = this.velocityY;
            } else if (this.velocityY > 0.0f) {
                this.a = 2000.0f;
            } else {
                this.a = -2000.0f;
            }
        }
        if (Math.abs(this.a) >= 0.0f && Math.abs(this.a) <= 20.0f) {
            this.wheelView.cancelFuture();
            this.wheelView.handler.sendEmptyMessage(2000);
            return;
        }
        int i = (int) ((this.a * 10.0f) / 1000.0f);
        WheelView wheelview = this.wheelView;
        wheelview.totalScrollY -= i;
        if (!this.wheelView.isLoop) {
            float itemHeight = this.wheelView.itemHeightOuter;
            if (this.wheelView.totalScrollY <= ((int) ((-this.wheelView.initPosition) * itemHeight))) {
                this.a = 40.0f;
                this.wheelView.totalScrollY = (int) ((-this.wheelView.initPosition) * itemHeight);
            } else if (this.wheelView.totalScrollY >= ((int) (((this.wheelView.items.size() - 1) - this.wheelView.initPosition) * itemHeight))) {
                this.wheelView.totalScrollY = (int) (((this.wheelView.items.size() - 1) - this.wheelView.initPosition) * itemHeight);
                this.a = -40.0f;
            }
        }
        if (this.a < 0.0f) {
            this.a += 20.0f;
        } else {
            this.a -= 20.0f;
        }
        this.wheelView.handler.sendEmptyMessage(1000);
    }
}
