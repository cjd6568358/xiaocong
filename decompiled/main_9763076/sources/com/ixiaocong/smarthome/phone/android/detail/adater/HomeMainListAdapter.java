package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.text.TextUtils;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.HomeMainListDetailModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeMainListAdapter extends XcBaseRecyclerAdapter<HomeMainListDetailModel.HomeListBean, BaseRecyclerViewHolder> {
    private boolean isEdit;

    public HomeMainListAdapter() {
        super(R.layout.adapter_home_main_list_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, HomeMainListDetailModel.HomeListBean item) {
        helper.setText(R.id.tv_home_main_list_item_home_name, item.getName());
        if (TextUtils.isEmpty(item.getId())) {
            helper.setText(R.id.tv_home_main_list_item_device_number, "0个设备");
        } else {
            helper.setText(R.id.tv_home_main_list_item_device_number, item.getDeviceSum() + "个设备");
        }
        helper.addOnClickListener(R.id.tv_home_main_list_item_delete);
        if (this.isEdit) {
            helper.setVisible(R.id.tv_home_main_list_item_delete, 0);
        } else {
            helper.setVisible(R.id.tv_home_main_list_item_delete, 8);
        }
    }

    public void setEdit(boolean edit) {
        this.isEdit = edit;
        notifyDataSetChanged();
    }
}
