package com.xiaocong.smarthome.wheel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class OnItemSelectedRunnable implements Runnable {
    public int oldIndex = -1;
    final WheelView wheelView;

    OnItemSelectedRunnable(WheelView wheelview) {
        this.wheelView = wheelview;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.wheelView.getSelectedPosition() >= 0 && this.wheelView.getSelectedPosition() != this.oldIndex) {
            this.oldIndex = this.wheelView.getSelectedPosition();
            this.wheelView.onItemSelectedListener.onItemSelected(this.wheelView.getSelectedPosition(), this.wheelView.getSelectedItem());
        }
    }
}
