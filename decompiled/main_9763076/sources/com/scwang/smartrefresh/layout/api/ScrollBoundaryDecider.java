package com.scwang.smartrefresh.layout.api;

import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ScrollBoundaryDecider {
    boolean canLoadMore(View view);

    boolean canRefresh(View view);
}
