package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.SystemMsgModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SystemMsgAdapter extends XcBaseRecyclerAdapter<SystemMsgModel.MessagesBean, BaseRecyclerViewHolder> {
    private Context mContext;

    public SystemMsgAdapter(Context context) {
        super(R.layout.adapter_system_msg_layout);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, SystemMsgModel.MessagesBean item) {
        Glide.with(this.mContext).load(item.getIcon()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_system_msg_icon));
        helper.setText(R.id.tv_system_msg_title, item.getTitle());
        helper.setText(R.id.tv_system_msg_date, item.getDatetime());
        helper.setText(R.id.tv_system_msg_descirbe, item.getContent());
    }
}
