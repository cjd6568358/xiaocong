package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.SystemMsgSettingModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.switchbutton.SwitchButton;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SystemMsgSettingDeviceAdapter extends XcBaseRecyclerAdapter<SystemMsgSettingModel.DevicesBean, BaseRecyclerViewHolder> {
    public SystemMsgSettingDeviceAdapter() {
        super(R.layout.adapter_system_msg_setting_device_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, SystemMsgSettingModel.DevicesBean item) {
        helper.setText(R.id.tv_home_device_list_name, item.getDeviceName());
        SwitchButton rightSbtn = helper.convertView.findViewById(R.id.sbtn_tab_home_dev_right_switch);
        helper.addOnClickListener(R.id.ll_right_tab_home_sbtn_layout);
        if (item.getNotification() == 1) {
            if (!rightSbtn.isChecked()) {
                rightSbtn.setCheckedNoEvent(true);
            }
        } else if (rightSbtn.isChecked()) {
            rightSbtn.setCheckedNoEvent(false);
        }
    }
}
