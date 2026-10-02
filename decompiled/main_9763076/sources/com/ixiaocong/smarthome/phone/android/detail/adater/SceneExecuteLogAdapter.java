package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.TimeZoneUtil;
import com.ixiaocong.smarthome.phone.android.complete.view.WrapContentLinearLayoutManager;
import com.xiaocong.smarthome.httplib.model.scene.SceneExecuteLogModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SceneExecuteLogAdapter extends XcBaseRecyclerAdapter<SceneExecuteLogModel.LogListModel, BaseRecyclerViewHolder> {
    private Context mContext;
    private RecyclerView mRvExecuteLogDesc;

    public SceneExecuteLogAdapter(Context context) {
        super(R.layout.adapter_scene_execute_log);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.support.v7.widget.RecyclerView$Adapter, com.ixiaocong.smarthome.phone.android.detail.adater.SceneExecuteLogAdapter$ExecuteLogDescAdapter] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void convert(BaseRecyclerViewHolder helper, SceneExecuteLogModel.LogListModel item) {
        helper.setText(R.id.tv_scene_execute_log_name, item.getName());
        helper.setText(R.id.tv_scene_execute_log_date, TimeZoneUtil.timeToString(Long.valueOf(item.getExecuteTime()), 3));
        helper.setText(R.id.tv_scene_execute_log_time, TimeZoneUtil.timeToString(Long.valueOf(item.getExecuteTime()), 8));
        this.mRvExecuteLogDesc = (RecyclerView) helper.getConvertView().findViewById(R.id.rv_scene_execute_log_desc);
        this.mRvExecuteLogDesc.setLayoutManager(new WrapContentLinearLayoutManager(this.mContext, 1, false));
        ?? executeLogDescAdapter = new ExecuteLogDescAdapter();
        this.mRvExecuteLogDesc.setAdapter(executeLogDescAdapter);
        this.mRvExecuteLogDesc.setNestedScrollingEnabled(false);
        executeLogDescAdapter.setNewData(item.getActionLog());
        executeLogDescAdapter.notifyDataSetChanged();
    }

    private class ExecuteLogDescAdapter extends XcBaseRecyclerAdapter<SceneExecuteLogModel.LogListModel.ActionLogModel, BaseRecyclerViewHolder> {
        public ExecuteLogDescAdapter() {
            super(R.layout.adapter_scene_execute_log_desc);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void convert(BaseRecyclerViewHolder helper, SceneExecuteLogModel.LogListModel.ActionLogModel item) {
            helper.setText(R.id.tv_scene_execute_log_device_desc, item.getDesc());
            if (item.getCode() == 0) {
                helper.setText(R.id.tv_scene_execute_log_device_status, "成功");
                helper.setTextColor(R.id.tv_scene_execute_log_device_status, this.mContext.getResources().getColor(R.color.master_color));
            } else {
                helper.setText(R.id.tv_scene_execute_log_device_status, "失败");
                helper.setTextColor(R.id.tv_scene_execute_log_device_status, this.mContext.getResources().getColor(R.color.red_ed));
            }
        }
    }
}
