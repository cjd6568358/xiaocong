package com.xiaocong.smarthome.recycleradapter.base;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class LoadMoreView {
    private int mLoadMoreStatus = 1;
    private boolean mLoadMoreEndGone = false;

    public abstract int getLayoutId();

    protected abstract int getLoadEndViewId();

    protected abstract int getLoadFailViewId();

    protected abstract int getLoadingViewId();

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return this.mLoadMoreStatus;
    }

    public void convert(BaseRecyclerViewHolder holder) {
        switch (this.mLoadMoreStatus) {
            case 1:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
            case 2:
                visibleLoading(holder, true);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
            case 3:
                visibleLoading(holder, false);
                visibleLoadFail(holder, true);
                visibleLoadEnd(holder, false);
                break;
            case 4:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, true);
                break;
        }
    }

    private void visibleLoading(BaseRecyclerViewHolder holder, boolean visible) {
        holder.setVisible(getLoadingViewId(), visible);
    }

    private void visibleLoadFail(BaseRecyclerViewHolder holder, boolean visible) {
        holder.setVisible(getLoadFailViewId(), visible);
    }

    private void visibleLoadEnd(BaseRecyclerViewHolder holder, boolean visible) {
        int loadEndViewId = getLoadEndViewId();
        if (loadEndViewId != 0) {
            holder.setVisible(loadEndViewId, visible);
        }
    }

    public final boolean isLoadEndMoreGone() {
        if (getLoadEndViewId() == 0) {
            return true;
        }
        return this.mLoadMoreEndGone;
    }
}
