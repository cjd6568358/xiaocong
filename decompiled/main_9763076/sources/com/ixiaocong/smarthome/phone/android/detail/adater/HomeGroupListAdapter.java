package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.HomeGroupListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeGroupListAdapter extends XcBaseRecyclerAdapter<HomeGroupListModel.GroupListBean, BaseRecyclerViewHolder> {
    private boolean isEdit;

    public HomeGroupListAdapter() {
        super(R.layout.adapter_home_group_list_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, HomeGroupListModel.GroupListBean item) {
        helper.setText(R.id.tv_home_group_list_item_home_name, item.getName());
        helper.addOnClickListener(R.id.tv_home_group_list_item_delete);
        if (this.isEdit) {
            helper.setVisible(R.id.tv_home_group_list_item_delete, 0);
        } else {
            helper.setVisible(R.id.tv_home_group_list_item_delete, 8);
        }
    }

    public void setEdit(boolean edit) {
        this.isEdit = edit;
        notifyDataSetChanged();
    }
}
