package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.HomeGroupListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DevSelectGroupPopAdapter extends XcBaseRecyclerAdapter<HomeGroupListModel.GroupListBean, BaseRecyclerViewHolder> {
    public DevSelectGroupPopAdapter() {
        super(R.layout.adapter_pop_select_dev_home_or_group_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, HomeGroupListModel.GroupListBean item) {
        helper.setText(R.id.tv_adapter_pop_select_home_or_group_name, item.getName());
    }
}
