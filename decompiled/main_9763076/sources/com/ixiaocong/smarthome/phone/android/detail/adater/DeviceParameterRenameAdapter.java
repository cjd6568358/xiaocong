package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.DeviceParameterModifiableModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceParameterRenameAdapter extends XcBaseRecyclerAdapter<DeviceParameterModifiableModel.ParametersBean, BaseRecyclerViewHolder> {
    public DeviceParameterRenameAdapter() {
        super(R.layout.adapter_device_parameter_rename_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, DeviceParameterModifiableModel.ParametersBean item) {
        helper.setText(R.id.tv_dev_parameter_name, item.getParameterName());
    }
}
