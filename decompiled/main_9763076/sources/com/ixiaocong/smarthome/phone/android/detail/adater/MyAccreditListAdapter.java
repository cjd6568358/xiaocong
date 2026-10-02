package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.MyAccreditModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MyAccreditListAdapter extends XcBaseRecyclerAdapter<MyAccreditModel.AuthoredAppListBean, BaseRecyclerViewHolder> {
    public MyAccreditListAdapter() {
        super(R.layout.adapter_my_accredit_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, MyAccreditModel.AuthoredAppListBean item) {
        helper.setText(R.id.tv_category_name, item.getName());
    }
}
