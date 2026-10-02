package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.ShareableDevicesModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedAddDeviceAdapter extends XcBaseRecyclerAdapter<ShareableDevicesModel.DeviceListBean, BaseRecyclerViewHolder> {
    private Context mContext;

    public SharedAddDeviceAdapter(Context context) {
        super(R.layout.adapter_shared_device_add_layout);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, ShareableDevicesModel.DeviceListBean item) {
        helper.setIsRecyclable(false);
        helper.setText(R.id.tv_shared_append_select_device_name, item.getDeviceName());
        Glide.with(this.mContext).load(item.getProductImage()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_shared_append_device_icon));
        helper.addOnClickListener(R.id.cb_shared_append_device_status);
        helper.setChecked(R.id.cb_shared_append_device_status, item.isChecked());
    }
}
