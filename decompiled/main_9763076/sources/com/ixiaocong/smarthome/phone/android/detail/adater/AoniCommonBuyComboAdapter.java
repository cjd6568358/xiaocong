package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.CameraCommonBuyComboModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonBuyComboAdapter extends XcBaseRecyclerAdapter<CameraCommonBuyComboModel.BuyComboModel, BaseRecyclerViewHolder> {
    public AoniCommonBuyComboAdapter() {
        super(R.layout.adapter_aoni_common_buy_combo);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, CameraCommonBuyComboModel.BuyComboModel model) {
        helper.setText(R.id.tv_camera_buy_combo_name, model.getTitle());
        helper.setText(R.id.tv_camera_buy_combo_price, model.getPrice());
    }
}
