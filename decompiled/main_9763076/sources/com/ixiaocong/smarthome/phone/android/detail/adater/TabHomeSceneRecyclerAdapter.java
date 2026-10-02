package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.text.TextUtils;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.greendao.model.insert.SceneDB;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabHomeSceneRecyclerAdapter extends XcBaseRecyclerAdapter<SceneDB, BaseRecyclerViewHolder> {
    private Context context;
    private int[] mImage;

    public TabHomeSceneRecyclerAdapter(Context context, int[] mImage) {
        super(R.layout.adapter_tab_home_scene_layout);
        this.context = context;
        this.mImage = mImage;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, SceneDB item) {
        if (TextUtils.isEmpty(item.getSceneIcon())) {
            helper.setText(R.id.tv_tab_home_scene_name, "全部");
            helper.setImageResource(R.id.iv_tab_home_scene_icon, this.mImage[3]);
        } else {
            helper.setImageResource(R.id.iv_tab_home_scene_icon, this.mImage[helper.getAdapterPosition()]);
            helper.setText(R.id.tv_tab_home_scene_name, item.getSceneName());
        }
    }
}
