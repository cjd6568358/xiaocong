package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.CameraCommonBuyHistoryModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonBuyHistoryAdapter extends XcBaseRecyclerAdapter<CameraCommonBuyHistoryModel.CommonBuyHistoryModel, BaseRecyclerViewHolder> {
    private Context mContext;

    public AoniCommonBuyHistoryAdapter(Context context) {
        super(R.layout.adapter_aoni_common_buy_history);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, CameraCommonBuyHistoryModel.CommonBuyHistoryModel model) {
        helper.setText(R.id.tv_aoni_common_buy_combo_date, model.getOrderTime());
        helper.setText(R.id.tv_aoni_common_buy_combo_msg, model.getOrderName());
        if (model.getOrderStatus() == -1) {
            helper.setText(R.id.tv_aoni_common_buy_combo_succeed, "过期失效");
            helper.setTextColor(R.id.tv_aoni_common_buy_combo_succeed, this.mContext.getResources().getColor(R.color.red_ed));
        } else if (model.getOrderStatus() == 0) {
            helper.setText(R.id.tv_aoni_common_buy_combo_succeed, " 待支付");
            helper.setTextColor(R.id.tv_aoni_common_buy_combo_succeed, this.mContext.getResources().getColor(R.color.hint_blue));
        } else if (model.getOrderStatus() == 1) {
            helper.setText(R.id.tv_aoni_common_buy_combo_succeed, " 支付成功");
            helper.setTextColor(R.id.tv_aoni_common_buy_combo_succeed, this.mContext.getResources().getColor(R.color.master_text_color));
        } else if (model.getOrderStatus() == 2) {
            helper.setText(R.id.tv_aoni_common_buy_combo_succeed, " 充值失败");
            helper.setTextColor(R.id.tv_aoni_common_buy_combo_succeed, this.mContext.getResources().getColor(R.color.red_ed));
        } else if (model.getOrderStatus() == 3) {
            helper.setText(R.id.tv_aoni_common_buy_combo_succeed, " 充值成功");
            helper.setTextColor(R.id.tv_aoni_common_buy_combo_succeed, this.mContext.getResources().getColor(R.color.master_text_color));
        }
        helper.addOnClickListener(R.id.tv_aoni_common_buy_combo_succeed);
    }
}
