package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.TimeZoneUtil;
import com.xiaocong.smarthome.httplib.model.HomeHasSomeoneModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeHasSomeoneAdapter extends XcBaseRecyclerAdapter<HomeHasSomeoneModel.HasSomeoneModel, BaseRecyclerViewHolder> {
    public HomeHasSomeoneAdapter() {
        super(R.layout.adapter_home_has_someone);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, HomeHasSomeoneModel.HasSomeoneModel model) {
        helper.setText(R.id.tv_home_has_someone_time, TimeZoneUtil.timeToString(Long.valueOf(model.getTimestamp()), 2));
        helper.setText(R.id.tv_home_has_someone_status, model.getStatus() == 1 ? "有人" : "无人");
    }
}
