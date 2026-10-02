package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.WorkdayWeekModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WorkdayWeekAdapter extends XcBaseRecyclerAdapter<WorkdayWeekModel, BaseRecyclerViewHolder> {
    public WorkdayWeekAdapter(Context context) {
        super(R.layout.adapter_workday_week);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, WorkdayWeekModel weekModel) {
        helper.setIsRecyclable(false);
        helper.setText(R.id.tv_workday_week_name, weekModel.getWeekName());
        helper.setChecked(R.id.cb_workday_week_check, weekModel.isChecked());
        helper.addOnClickListener(R.id.cb_workday_week_check);
    }
}
