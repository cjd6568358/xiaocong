package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.model.CameraCommonConfigModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CameraCommonMakeTimePop {
    private PopupWindow mCommonPop;
    private View mOtherView;
    private RecyclerView mRvMakeTime;
    private CommonMakeTimeAdapter mTimeAdapter;

    public static CameraCommonMakeTimePop getInstance() {
        return CameraCommonMakeTimePopHolder.INSTANCE;
    }

    public void initCommonView(Context context, View rootView, CommonTypeCallback callback, CameraCommonConfigModel configModel, String deviceId) {
        View view = View.inflate(context, R.layout.layout_common_camera_make_time, null);
        this.mOtherView = view.findViewById(R.id.view_common_camera_make_time);
        initMakeTime(context, view, configModel);
        this.mCommonPop = new PopupWindow(view, -1, -1);
        this.mCommonPop.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mCommonPop.setBackgroundDrawable(new ColorDrawable(0));
        this.mCommonPop.setOutsideTouchable(true);
        this.mCommonPop.showAtLocation(rootView, 83, 0, 0);
        addListener(context, callback, deviceId);
    }

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
    private void initMakeTime(Context context, View view, CameraCommonConfigModel configModel) {
        this.mRvMakeTime = (RecyclerView) view.findViewById(R.id.rv_common_camera_make_time);
        this.mRvMakeTime.setLayoutManager(new LinearLayoutManager(context));
        String[] times = configModel.getShootTimeOptions().split(",");
        List<MakeTimeModel> timeModels = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            MakeTimeModel timeModel = new MakeTimeModel();
            timeModel.setTime(Integer.valueOf(times[i]).intValue());
            if (Integer.valueOf(times[i]).intValue() == configModel.getStreamTime()) {
                timeModel.setCheck(true);
            } else {
                timeModel.setCheck(false);
            }
            timeModels.add(timeModel);
        }
        this.mTimeAdapter = new CommonMakeTimeAdapter(context);
        this.mRvMakeTime.setAdapter(this.mTimeAdapter);
        this.mTimeAdapter.setNewData(timeModels);
        this.mTimeAdapter.notifyDataSetChanged();
    }

    public boolean isShowing() {
        return this.mCommonPop != null && this.mCommonPop.isShowing();
    }

    public void dismiss() {
        if (this.mCommonPop != null && this.mCommonPop.isShowing()) {
            this.mCommonPop.dismiss();
        }
    }

    private void addListener(final Context context, final CommonTypeCallback callback, final String deviceId) {
        this.mOtherView.setOnClickListener(CameraCommonMakeTimePop$$Lambda$1.lambdaFactory$(this));
        this.mRvMakeTime.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonMakeTimePop.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                CameraCommonMakeTimePop.this.notifyChecked(CameraCommonMakeTimePop.this.mTimeAdapter.getData(), position);
                CameraCommonMakeTimePop.this.updateMakeTime(context, callback, deviceId, ((MakeTimeModel) CameraCommonMakeTimePop.this.mTimeAdapter.getData().get(position)).getTime());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        if (this.mCommonPop != null && this.mCommonPop.isShowing()) {
            this.mCommonPop.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMakeTime(final Context context, final CommonTypeCallback callback, String deviceId, final int time) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("setAlarm", "1");
        params.put("setStream", PushConstants.PUSH_TYPE_NOTIFY);
        params.put("streamTime", time + Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/config/update");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonMakeTimePop.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, "录制时间设置成功");
                callback.resultTypeCalllback(time);
                CameraCommonMakeTimePop.this.mCommonPop.dismiss();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, "录制时间设置失败");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyChecked(List<MakeTimeModel> list, int position) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i == position) {
                    list.get(i).setCheck(true);
                } else {
                    list.get(i).setCheck(false);
                }
            }
            this.mTimeAdapter.notifyDataSetChanged();
        }
    }

    private class CommonMakeTimeAdapter extends XcBaseRecyclerAdapter<MakeTimeModel, BaseRecyclerViewHolder> {
        public CommonMakeTimeAdapter(Context context) {
            super(R.layout.adapter_commmon_make_time);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void convert(BaseRecyclerViewHolder helper, MakeTimeModel model) {
            helper.setText(R.id.tv_adapter_pop_common_make_time_value, model.getTime() + "秒");
            if (model.isCheck()) {
                helper.setVisible(R.id.iv_adapter_pop_common_make_time_icon, 0);
            } else {
                helper.setVisible(R.id.iv_adapter_pop_common_make_time_icon, 8);
            }
        }
    }

    private class MakeTimeModel {
        private boolean isCheck;
        private int time;

        private MakeTimeModel() {
        }

        public int getTime() {
            return this.time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public boolean isCheck() {
            return this.isCheck;
        }

        public void setCheck(boolean check) {
            this.isCheck = check;
        }
    }

    private static final class CameraCommonMakeTimePopHolder {
        private static final CameraCommonMakeTimePop INSTANCE = new CameraCommonMakeTimePop();
    }
}
