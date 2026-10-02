package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.scene.UserIftttListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SceneSystemRecommendAdapter extends XcBaseRecyclerAdapter<UserIftttListModel.IftttModel, BaseRecyclerViewHolder> {
    private Context mContext;

    public SceneSystemRecommendAdapter(Context context) {
        super(R.layout.adapter_scene_system_recommend);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, UserIftttListModel.IftttModel item) {
        helper.setText(R.id.tv_scene_recommend_name, item.getTriggerName());
        if (!TextUtils.isEmpty(item.getExtendIntro())) {
            helper.setText(R.id.tv_scene_recommend_describe, item.getExtendIntro());
            helper.setTextColor(R.id.tv_scene_recommend_describe, this.mContext.getResources().getColor(R.color.hint_blue));
        } else {
            helper.setText(R.id.tv_scene_recommend_describe, item.getTriggerIntro());
            helper.setTextColor(R.id.tv_scene_recommend_describe, this.mContext.getResources().getColor(R.color.sub_tint_color));
        }
        Glide.with(this.mContext).load(item.getSceneIcon()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_scene_system_icon));
    }
}
