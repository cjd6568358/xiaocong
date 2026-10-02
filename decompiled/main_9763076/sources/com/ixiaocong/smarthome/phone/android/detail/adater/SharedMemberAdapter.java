package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.SharedMemberModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedMemberAdapter extends XcBaseRecyclerAdapter<SharedMemberModel.MemberModel, BaseRecyclerViewHolder> {
    private boolean isShow;
    private Context mContext;

    public SharedMemberAdapter(Context context) {
        super(R.layout.adapter_shared_member_layout);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, SharedMemberModel.MemberModel item) {
        helper.setText(R.id.tv_shared_member_user_name, item.getNickname());
        Glide.with(this.mContext).load(item.getPortrait()).placeholder(R.mipmap.icon_shared_user_head).into((ImageView) helper.getView(R.id.iv_shared_member_user_icon));
        helper.addOnClickListener(R.id.iv_shared_user_edit_del);
        if (isShow()) {
            helper.setVisible(R.id.iv_shared_user_edit_del, 0);
        } else {
            helper.setVisible(R.id.iv_shared_user_edit_del, 8);
        }
    }

    public boolean isShow() {
        return this.isShow;
    }

    public void setShow(boolean show) {
        this.isShow = show;
    }
}
