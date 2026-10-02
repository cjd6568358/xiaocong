package com.ixiaocong.smarthome.phone.android.event.callback;

import android.support.design.widget.AppBarLayout;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
    private State mCurrentState = State.IDLE;

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

    @Override // android.support.design.widget.AppBarLayout.OnOffsetChangedListener
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset >= -40) {
            if (this.mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            this.mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() - 200) {
            if (this.mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            this.mCurrentState = State.COLLAPSED;
        } else {
            if (this.mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            this.mCurrentState = State.IDLE;
        }
    }
}
