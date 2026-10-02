package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.scene.RelateActionModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceCreateIftttAdapter extends XcBaseRecyclerAdapter<RelateActionModel, BaseRecyclerViewHolder> {
    private boolean isShow;
    private Context mContext;

    public DeviceCreateIftttAdapter(Context context) {
        super(R.layout.adapter_device_ifttt_detail_layout);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, RelateActionModel item) {
        helper.setText(R.id.tv_ifttt_edit_dev_status_adapter, item.getActionDesc());
        if (TextUtils.isEmpty(item.getActionIcon())) {
            helper.setImageResource(R.id.iv_ifttt_edit_dev_icon_adapter, R.drawable.icon_ifttt_delay);
        } else {
            Glide.with(this.mContext).load(item.getActionIcon()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_ifttt_edit_dev_icon_adapter));
        }
        helper.addOnClickListener(R.id.rl_ifttt_relate_edit_layout);
        helper.addOnClickListener(R.id.iv_ifttt_edit_del_adapter);
        if (isShow()) {
            helper.setVisible(R.id.iv_ifttt_edit_del_adapter, 0);
        } else {
            helper.setVisible(R.id.iv_ifttt_edit_del_adapter, 8);
        }
    }

    public boolean isShow() {
        return this.isShow;
    }

    public void setShow(boolean show) {
        this.isShow = show;
    }
}
