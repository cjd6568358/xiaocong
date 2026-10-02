package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.ixiaocong.smarthome.phone.R;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.model.SwitchRelationTriggerParameterModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceSwitchRelationActionParameterAdapter extends XcBaseRecyclerAdapter<SwitchRelationTriggerParameterModel.ListBean, BaseRecyclerViewHolder> {
    private EditGroupListener listener;
    private Map<Integer, Boolean> mItemList;

    public interface EditGroupListener {
    }

    public DeviceSwitchRelationActionParameterAdapter() {
        super(R.layout.adapter_device_switch_relation_action_parameter_layout);
        this.mItemList = new LinkedHashMap();
        this.listener = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(final BaseRecyclerViewHolder helper, SwitchRelationTriggerParameterModel.ListBean item) {
        helper.setIsRecyclable(false);
        helper.setText(R.id.tv_switch_relation_device_name, item.getRelationDeviceName());
        helper.setText(R.id.tv_switch_relation_parameter_name, item.getRelationParameterName());
        if ("1".equals(item.getSelected())) {
            this.mItemList.put(Integer.valueOf(helper.getAdapterPosition()), true);
        }
        if (this.mItemList.containsKey(Integer.valueOf(helper.getAdapterPosition()))) {
            helper.setChecked(R.id.cb_home_device_edit_group_status, this.mItemList.get(Integer.valueOf(helper.getAdapterPosition())).booleanValue());
        } else {
            helper.setChecked(R.id.cb_home_device_edit_group_status, false);
        }
        helper.addOnClickListener(R.id.cb_home_device_edit_group_status);
        CheckBox checkBox = (CheckBox) helper.convertView.findViewById(R.id.cb_home_device_edit_group_status);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.adater.DeviceSwitchRelationActionParameterAdapter.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    DeviceSwitchRelationActionParameterAdapter.this.mItemList.remove(Integer.valueOf(helper.getAdapterPosition()));
                    return;
                }
                if (!TextUtils.isEmpty(DeviceSwitchRelationActionParameterAdapter.this.getSelectItemId())) {
                    ((SwitchRelationTriggerParameterModel.ListBean) DeviceSwitchRelationActionParameterAdapter.this.getData().get(Integer.valueOf(DeviceSwitchRelationActionParameterAdapter.this.getSelectItemId()).intValue())).setSelected(PushConstants.PUSH_TYPE_NOTIFY);
                }
                DeviceSwitchRelationActionParameterAdapter.this.mItemList.clear();
                DeviceSwitchRelationActionParameterAdapter.this.mItemList.put(Integer.valueOf(helper.getAdapterPosition()), true);
                DeviceSwitchRelationActionParameterAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public String getSelectItemId() {
        String id = null;
        if (this.mItemList.size() != 0 && this.mItemList.size() == 1) {
            id = null;
            for (Integer i : this.mItemList.keySet()) {
                id = i + Constants.MAIN_VERSION_TAG;
            }
        }
        return id;
    }
}
