package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleImageView;
import com.xiaocong.smarthome.httplib.model.SharedMemberModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedSelectUserAdapter extends XcBaseRecyclerAdapter<SharedMemberModel.MemberModel, BaseRecyclerViewHolder> {
    private Context mContext;

    public SharedSelectUserAdapter(Context context) {
        super(R.layout.adapter_shared_select_user_layout);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, SharedMemberModel.MemberModel item) {
        helper.setText(R.id.tv_shared_select_user_name, item.getNickname());
        CircleImageView circleImageView = (CircleImageView) helper.getView(R.id.iv_shared_select_user_icon);
        Glide.with(this.mContext).load(item.getPortrait()).placeholder(R.mipmap.icon_shared_user_head).priority(Priority.NORMAL).into(circleImageView);
    }
}
