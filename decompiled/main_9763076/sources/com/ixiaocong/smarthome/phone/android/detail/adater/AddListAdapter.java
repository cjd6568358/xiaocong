package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.ProductListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AddListAdapter extends XcBaseRecyclerAdapter<ProductListModel.SubProductModel, BaseRecyclerViewHolder> {
    private Context mContext;

    public AddListAdapter(Context context) {
        super(R.layout.adapter_add_list_layout);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, ProductListModel.SubProductModel item) {
        helper.setText(R.id.tv_append_dev_name, item.getProductName());
    }
}
