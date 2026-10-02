package com.scwang.smartrefresh.layout.api;

import android.view.ViewGroup;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface RefreshLayout {
    RefreshLayout finishRefresh();

    ViewGroup getLayout();

    boolean isEnableLoadMore();

    RefreshLayout setEnableAutoLoadMore(boolean z);

    RefreshLayout setEnableNestedScroll(boolean z);

    RefreshLayout setEnableOverScrollDrag(boolean z);

    RefreshLayout setEnableRefresh(boolean z);

    RefreshLayout setOnRefreshListener(OnRefreshListener onRefreshListener);
}
