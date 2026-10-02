package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.ParameterValueModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ScenePopParaValueAdapter extends XcBaseRecyclerAdapter<ParameterValueModel, BaseRecyclerViewHolder> {
    public ScenePopParaValueAdapter() {
        super(R.layout.adapter_scene_edit_pop_layout);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, ParameterValueModel item) {
        helper.setText(R.id.tv_scene_edit_pop_name, item.getDesc());
    }
}
