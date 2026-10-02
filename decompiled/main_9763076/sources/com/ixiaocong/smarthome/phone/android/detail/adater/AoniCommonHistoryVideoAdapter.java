package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.TimeZoneUtil;
import com.xiaocong.smarthome.httplib.model.CameraCommonStreamListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonHistoryVideoAdapter extends XcBaseRecyclerAdapter<CameraCommonStreamListModel.CommonStreamModel, BaseRecyclerViewHolder> {
    private Context mContext;

    public AoniCommonHistoryVideoAdapter(Context context) {
        super(R.layout.adapter_aoni_common_history_video);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, CameraCommonStreamListModel.CommonStreamModel model) {
        helper.setText(R.id.tv_aoni_common_video_history_adapter_time, TimeZoneUtil.timeToString(Long.valueOf(model.getBeginTime()), 6) + "-" + TimeZoneUtil.timeToString(Long.valueOf(model.getEndTime()), 6));
        Glide.with(this.mContext).load(model.getImage()).placeholder(R.drawable.camera_default_icon).into((ImageView) helper.getView(R.id.iv_aoni_common_video_history_adapter_bg));
    }
}
