package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.HomeDeviceListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeAddGroupAdapter extends XcBaseRecyclerAdapter<HomeDeviceListModel.DeviceListBean, BaseRecyclerViewHolder> {
    private AddGroupListener listener;
    private StringBuffer mBuffer;
    private Context mContext;
    private Map<Integer, Boolean> mItemList;

    public interface AddGroupListener {
        void isSelectAll(Boolean bool);
    }

    public HomeAddGroupAdapter(Context context) {
        super(R.layout.adapter_home_add_group_layout);
        this.mItemList = new LinkedHashMap();
        this.mBuffer = new StringBuffer();
        this.listener = null;
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(final BaseRecyclerViewHolder helper, HomeDeviceListModel.DeviceListBean item) {
        helper.setIsRecyclable(false);
        helper.setText(R.id.tv_home_add_group_name, item.getName());
        if (this.mItemList.containsKey(Integer.valueOf(helper.getAdapterPosition()))) {
            helper.setChecked(R.id.cb_home_add_group_status, true);
        } else {
            helper.setChecked(R.id.cb_home_add_group_status, false);
        }
        helper.addOnClickListener(R.id.cb_home_add_group_status);
        CheckBox checkBox = (CheckBox) helper.convertView.findViewById(R.id.cb_home_add_group_status);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.adater.HomeAddGroupAdapter.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    HomeAddGroupAdapter.this.mItemList.put(Integer.valueOf(helper.getAdapterPosition()), true);
                    if (HomeAddGroupAdapter.this.getData() != null && HomeAddGroupAdapter.this.getData().size() == HomeAddGroupAdapter.this.mItemList.size()) {
                        XcLogger.i("tag", "全选");
                        if (HomeAddGroupAdapter.this.listener != null) {
                            HomeAddGroupAdapter.this.listener.isSelectAll(true);
                            return;
                        }
                        return;
                    }
                    return;
                }
                HomeAddGroupAdapter.this.mItemList.remove(Integer.valueOf(helper.getAdapterPosition()));
                if (HomeAddGroupAdapter.this.getData() != null && HomeAddGroupAdapter.this.getData().size() > HomeAddGroupAdapter.this.mItemList.size()) {
                    XcLogger.i("tag", "非全选");
                    if (HomeAddGroupAdapter.this.listener != null) {
                        HomeAddGroupAdapter.this.listener.isSelectAll(false);
                    }
                }
            }
        });
    }

    public void selectAll() {
        if (getData() != null && getData().size() > 0) {
            this.mItemList.clear();
            for (int i = 0; i < getData().size(); i++) {
                this.mItemList.put(Integer.valueOf(i), true);
            }
            notifyDataSetChanged();
            if (this.listener != null) {
                this.listener.isSelectAll(true);
                return;
            }
            return;
        }
        ToastUtils.showShort(this.mContext, "您当前没有可选择的设备,请先前去添加");
    }

    public void unSelectAll() {
        if (getData() != null && getData().size() > 0) {
            this.mItemList.clear();
            notifyDataSetChanged();
            if (this.listener != null) {
                this.listener.isSelectAll(false);
                return;
            }
            return;
        }
        ToastUtils.showShort(this.mContext, "您当前没有可选择的设备,请先前去添加");
    }

    public String getSelectItemId() {
        if (this.mItemList.size() == 0) {
            return null;
        }
        if (this.mItemList.size() == 1) {
            String id = null;
            for (Integer i : this.mItemList.keySet()) {
                id = ((HomeDeviceListModel.DeviceListBean) getData().get(i.intValue())).getDeviceId();
            }
            return id;
        }
        this.mBuffer.setLength(0);
        List<String> idList = new ArrayList<>();
        for (Integer i2 : this.mItemList.keySet()) {
            idList.add(((HomeDeviceListModel.DeviceListBean) getData().get(i2.intValue())).getDeviceId());
        }
        for (int i3 = 0; i3 < idList.size(); i3++) {
            if (i3 != idList.size() - 1) {
                this.mBuffer.append(idList.get(i3) + ",");
            } else {
                this.mBuffer.append(idList.get(i3));
            }
        }
        return this.mBuffer.toString();
    }

    public void setOnAddGroupListener(AddGroupListener listener) {
        this.listener = listener;
    }
}
