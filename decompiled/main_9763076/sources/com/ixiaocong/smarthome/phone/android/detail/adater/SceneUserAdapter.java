package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.scene.UserIftttListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.switchbutton.SwitchButton;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SceneUserAdapter extends XcBaseRecyclerAdapter<UserIftttListModel.IftttModel, BaseRecyclerViewHolder> {
    private Context mContext;
    private SwitchButton mSBtn;

    public SceneUserAdapter(Context context) {
        super(R.layout.adapter_scene_user);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, UserIftttListModel.IftttModel item) {
        helper.setText(R.id.tv_scene_user_name, item.getTriggerName());
        helper.setText(R.id.tv_scene_user_describe, item.getTriggerIntro());
        Glide.with(this.mContext).load(item.getSceneIcon()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_scene_user_icon));
        if (item.getType().equals("auto")) {
            helper.setVisible(R.id.sb_scene_user_switch, 0);
            helper.setVisible(R.id.btn_scene_user_click, 4);
        } else {
            helper.setVisible(R.id.sb_scene_user_switch, 4);
            helper.setVisible(R.id.btn_scene_user_click, 0);
        }
        helper.addOnClickListener(R.id.btn_scene_user_click);
        helper.addOnClickListener(R.id.sb_scene_user_switch);
        this.mSBtn = helper.getConvertView().findViewById(R.id.sb_scene_user_switch);
        if (item.getStatus() == 1) {
            this.mSBtn.setChecked(true);
        } else {
            this.mSBtn.setChecked(false);
        }
    }
}
