package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.SharedDeviceModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedHomeDeviceAdapter extends XcBaseRecyclerAdapter<SharedDeviceModel.DeviceListBean, BaseRecyclerViewHolder> {
    private Context mContext;

    public SharedHomeDeviceAdapter(Context context) {
        super(R.layout.adapter_shared_device_home_layout);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, SharedDeviceModel.DeviceListBean item) {
        helper.setText(R.id.tv_shared_home_device_name, item.getDeviceName());
        helper.setText(R.id.tv_shared_home_device_describe, "已分享给：" + item.getCount() + "人");
        Glide.with(this.mContext).load(item.getProductImage()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_shared_home_device_icon));
    }
}
