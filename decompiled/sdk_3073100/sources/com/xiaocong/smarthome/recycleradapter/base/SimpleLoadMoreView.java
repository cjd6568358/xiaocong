package com.xiaocong.smarthome.recycleradapter.base;

import com.xiaocong.recycleradapter.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class SimpleLoadMoreView extends LoadMoreView {
    @Override // com.xiaocong.smarthome.recycleradapter.base.LoadMoreView
    public int getLayoutId() {
        return R.layout.quick_view_load_more;
    }

    @Override // com.xiaocong.smarthome.recycleradapter.base.LoadMoreView
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override // com.xiaocong.smarthome.recycleradapter.base.LoadMoreView
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override // com.xiaocong.smarthome.recycleradapter.base.LoadMoreView
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
