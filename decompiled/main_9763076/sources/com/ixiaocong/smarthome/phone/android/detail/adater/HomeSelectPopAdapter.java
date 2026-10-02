package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.HomeListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeSelectPopAdapter extends XcBaseRecyclerAdapter<HomeListModel.HomeListBean, BaseRecyclerViewHolder> {
    public HomeSelectPopAdapter() {
        super(R.layout.adapter_tab_home_family_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, HomeListModel.HomeListBean item) {
        helper.setText(R.id.tv_tab_home_family_name, item.getName());
    }
}
