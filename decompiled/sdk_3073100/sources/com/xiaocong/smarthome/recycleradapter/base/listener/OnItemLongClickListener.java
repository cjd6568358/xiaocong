package com.xiaocong.smarthome.recycleradapter.base.listener;

import android.view.View;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class OnItemLongClickListener extends SimpleClickListener {
    public abstract void onSimpleItemLongClick(XcBaseRecyclerAdapter xcBaseRecyclerAdapter, View view, int i);

    @Override // com.xiaocong.smarthome.recycleradapter.base.listener.SimpleClickListener
    public void onItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
    }

    @Override // com.xiaocong.smarthome.recycleradapter.base.listener.SimpleClickListener
    public void onItemLongClick(XcBaseRecyclerAdapter adapter, View view, int position) {
        onSimpleItemLongClick(adapter, view, position);
    }

    @Override // com.xiaocong.smarthome.recycleradapter.base.listener.SimpleClickListener
    public void onItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
    }

    @Override // com.xiaocong.smarthome.recycleradapter.base.listener.SimpleClickListener
    public void onItemChildLongClick(XcBaseRecyclerAdapter adapter, View view, int position) {
    }
}
