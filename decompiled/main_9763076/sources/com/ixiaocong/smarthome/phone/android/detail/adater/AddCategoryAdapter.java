package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.ProductCategoryModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AddCategoryAdapter extends XcBaseRecyclerAdapter<ProductCategoryModel.ProductModel, BaseRecyclerViewHolder> {
    public AddCategoryAdapter() {
        super(R.layout.adapter_add_category_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, ProductCategoryModel.ProductModel item) {
        helper.setText(R.id.tv_category_name, item.getCategoryName());
    }
}
