package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.HomeListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DevSelectHomePopAdapter extends XcBaseRecyclerAdapter<HomeListModel.HomeListBean, BaseRecyclerViewHolder> {
    public DevSelectHomePopAdapter() {
        super(R.layout.adapter_pop_select_dev_home_or_group_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, HomeListModel.HomeListBean item) {
        helper.setText(R.id.tv_adapter_pop_select_home_or_group_name, item.getName());
    }
}
