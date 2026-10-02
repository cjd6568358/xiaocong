package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AccreditPermissionListAdapter extends XcBaseRecyclerAdapter<String, BaseRecyclerViewHolder> {
    public AccreditPermissionListAdapter() {
        super(R.layout.adapter_accredit_item_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, String item) {
        helper.setText(R.id.tv_permission_name, item);
    }
}
