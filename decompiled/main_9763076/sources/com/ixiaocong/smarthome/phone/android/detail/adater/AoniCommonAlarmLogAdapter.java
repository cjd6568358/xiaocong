package com.ixiaocong.smarthome.phone.android.detail.adater;

import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.TimeZoneUtil;
import com.xiaocong.smarthome.httplib.model.CameraCommonAlarmListModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonAlarmLogAdapter extends XcBaseRecyclerAdapter<CameraCommonAlarmListModel.CameraAlarmModel, BaseRecyclerViewHolder> {
    public AoniCommonAlarmLogAdapter() {
        super(R.layout.adapter_aoni_common_alarm_log);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, CameraCommonAlarmListModel.CameraAlarmModel alarmModel) {
        if (alarmModel.getAlarm() == 0) {
            helper.setText(R.id.tv_aoni_common_alarm_item_name, "无告警");
            helper.setVisible(R.id.tv_aoni_common_look_alarm, 8);
        } else {
            helper.setText(R.id.tv_aoni_common_alarm_item_name, "移动告警");
            helper.setVisible(R.id.tv_aoni_common_look_alarm, 0);
        }
        try {
            helper.setText(R.id.tv_aoni_common_alarm_item_date, TimeZoneUtil.timeToString(Long.valueOf(Long.parseLong(alarmModel.getBeginTime())), 6));
        } catch (Exception e) {
        }
        helper.addOnClickListener(R.id.tv_aoni_common_look_alarm);
    }
}
