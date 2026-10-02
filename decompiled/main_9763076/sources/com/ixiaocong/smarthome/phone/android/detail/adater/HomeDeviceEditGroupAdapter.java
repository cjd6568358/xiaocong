package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.ixiaocong.smarthome.phone.R;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.model.HomeGroupListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeDeviceEditGroupAdapter extends XcBaseRecyclerAdapter<HomeGroupListModel.GroupListBean, BaseRecyclerViewHolder> {
    private EditGroupListener listener;
    private String mGroupId;
    private Map<Integer, Boolean> mItemList;

    public interface EditGroupListener {
    }

    public HomeDeviceEditGroupAdapter() {
        super(R.layout.adapter_home_device_edit_group_layout);
        this.mItemList = new LinkedHashMap();
        this.listener = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(final BaseRecyclerViewHolder helper, HomeGroupListModel.GroupListBean item) {
        helper.setIsRecyclable(false);
        helper.setText(R.id.tv_home_device_edit_group_name, item.getName());
        if (this.mGroupId.equals(item.getId())) {
            this.mItemList.put(Integer.valueOf(helper.getAdapterPosition()), true);
        }
        if (this.mItemList.containsKey(Integer.valueOf(helper.getAdapterPosition()))) {
            helper.setChecked(R.id.cb_home_device_edit_group_status, this.mItemList.get(Integer.valueOf(helper.getAdapterPosition())).booleanValue());
        } else {
            helper.setChecked(R.id.cb_home_device_edit_group_status, false);
        }
        helper.addOnClickListener(R.id.cb_home_device_edit_group_status);
        CheckBox checkBox = (CheckBox) helper.convertView.findViewById(R.id.cb_home_device_edit_group_status);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.adater.HomeDeviceEditGroupAdapter.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    HomeDeviceEditGroupAdapter.this.mGroupId = Constants.MAIN_VERSION_TAG;
                    HomeDeviceEditGroupAdapter.this.mItemList.clear();
                    HomeDeviceEditGroupAdapter.this.mItemList.put(Integer.valueOf(helper.getAdapterPosition()), true);
                    HomeDeviceEditGroupAdapter.this.notifyDataSetChanged();
                    return;
                }
                HomeDeviceEditGroupAdapter.this.mItemList.remove(Integer.valueOf(helper.getAdapterPosition()));
            }
        });
    }

    public void setSelect(String groupId) {
        if (TextUtils.isEmpty(groupId)) {
            this.mGroupId = Constants.MAIN_VERSION_TAG;
        } else {
            this.mGroupId = groupId;
            notifyDataSetChanged();
        }
    }

    public String getSelectItemId() {
        String id = null;
        if (this.mItemList.size() != 0 && this.mItemList.size() == 1) {
            id = null;
            for (Integer i : this.mItemList.keySet()) {
                id = ((HomeGroupListModel.GroupListBean) getData().get(i.intValue())).getId();
            }
        }
        return id;
    }
}
