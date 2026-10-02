package com.scwang.smartrefresh.layout.constant;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum RefreshState {
    None(0, false),
    PullDownToRefresh(1, true),
    PullUpToLoad(2, true),
    PullDownCanceled(1, false),
    PullUpCanceled(2, false),
    ReleaseToRefresh(1, true),
    ReleaseToLoad(2, true),
    ReleaseToTwoLevel(1, true),
    TwoLevelReleased(1, false),
    RefreshReleased(1, false),
    LoadReleased(2, false),
    Refreshing(1, false, true),
    Loading(2, false, true),
    TwoLevel(1, false, true),
    RefreshFinish(1, false, false, true),
    LoadFinish(2, false, false, true),
    TwoLevelFinish(1, false, false, true);

    public final boolean dragging;
    public final boolean finishing;
    public final boolean opening;
    private final int role;

    RefreshState(int role, boolean dragging) {
        this.role = role;
        this.dragging = dragging;
        this.opening = false;
        this.finishing = false;
    }

    RefreshState(int role, boolean dragging, boolean opening) {
        this.role = role;
        this.dragging = dragging;
        this.opening = opening;
        this.finishing = false;
    }

    RefreshState(int role, boolean dragging, boolean opening, boolean finishing) {
        this.role = role;
        this.dragging = dragging;
        this.opening = opening;
        this.finishing = finishing;
    }

    public boolean isHeader() {
        return this.role == 1;
    }

    public boolean isFooter() {
        return this.role == 2;
    }
}
