package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.scene.SceneParameterModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ScenePopParameterAdapter extends XcBaseRecyclerAdapter<SceneParameterModel.ParameterModel, BaseRecyclerViewHolder> {
    public ScenePopParameterAdapter() {
        super(R.layout.adapter_scene_edit_pop_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, SceneParameterModel.ParameterModel item) {
        helper.setText(R.id.tv_scene_edit_pop_name, item.getParameterName());
    }
}
