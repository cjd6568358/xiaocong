package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.HomeDeviceListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeDeviceListAdapter extends XcBaseRecyclerAdapter<HomeDeviceListModel.DeviceListBean, BaseRecyclerViewHolder> {
    private Context mContext;

    public HomeDeviceListAdapter(Context context) {
        super(R.layout.adapter_home_device_list_layout);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, HomeDeviceListModel.DeviceListBean item) {
        helper.setText(R.id.tv_home_device_list_name, item.getName());
        Glide.with(this.mContext).load(item.getImage()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_home_device_list_img));
    }
}
