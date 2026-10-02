package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import bsh.ParserConstants;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ScreenUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.TabHomeDeviceSelecetPopCallback;
import com.xiaocong.smarthome.httplib.model.DeviceListModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabHomeLongClickPop {
    private int isTop;
    private String mDeviceId;
    private String mDeviceName;
    private LinearLayout mEditLayout;
    private LinearLayout mRenameLayout;
    private PopupWindow mSelectPop;
    private LinearLayout mTopLayout;
    private TextView mTvTop;

    public static TabHomeLongClickPop getInstance() {
        return SelectAddPopHolder.INSTANCE;
    }

    public void showSelectPop(Context context, View view, DeviceListModel deviceListModel, TabHomeDeviceSelecetPopCallback callback) {
        View popView = LayoutInflater.from(context).inflate(R.layout.pop_tab_home_long_click_layout, (ViewGroup) null);
        this.mEditLayout = (LinearLayout) popView.findViewById(R.id.ll_tab_home_device_edit_group_pop);
        this.mTopLayout = (LinearLayout) popView.findViewById(R.id.ll_tab_home_device_top_pop);
        this.mRenameLayout = (LinearLayout) popView.findViewById(R.id.ll_tab_home_device_rename_pop);
        this.mTvTop = (TextView) popView.findViewById(R.id.tv_tab_home_device_top_pop);
        if (deviceListModel != null) {
            this.isTop = deviceListModel.getTop();
            if (this.isTop == 1) {
                this.mTvTop.setText("取消置顶");
            }
            this.mDeviceId = deviceListModel.getDeviceId();
            this.mDeviceName = deviceListModel.getDeviceName();
        }
        this.mEditLayout.setOnClickListener(TabHomeLongClickPop$$Lambda$1.lambdaFactory$(this, callback, deviceListModel));
        this.mTopLayout.setOnClickListener(TabHomeLongClickPop$$Lambda$2.lambdaFactory$(this, context, callback));
        this.mRenameLayout.setOnClickListener(TabHomeLongClickPop$$Lambda$3.lambdaFactory$(this, callback));
        this.mSelectPop = new PopupWindow();
        this.mSelectPop.setContentView(popView);
        this.mSelectPop.setWidth((int) context.getResources().getDimension(R.dimen.x210));
        this.mSelectPop.setHeight((int) context.getResources().getDimension(R.dimen.x219));
        this.mSelectPop.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mSelectPop.setBackgroundDrawable(new BitmapDrawable());
        this.mSelectPop.setOutsideTouchable(true);
        this.mSelectPop.setFocusable(true);
        int statusHeight = ScreenUtils.getStatusHeight(context) + ParserConstants.RSIGNEDSHIFTASSIGN;
        this.mSelectPop.showAsDropDown(view, (int) context.getResources().getDimension(R.dimen.x400), -20);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$0(TabHomeDeviceSelecetPopCallback callback, DeviceListModel deviceListModel, View v) {
        if (callback != null) {
            callback.editDeviceGroup(deviceListModel.getDeviceId(), deviceListModel.getDeviceName(), deviceListModel.getGroupId());
        }
        this.mSelectPop.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$1(Context context, TabHomeDeviceSelecetPopCallback callback, View v) {
        if (TextUtils.isEmpty(this.mDeviceId)) {
            ToastUtils.showShort(context, "设备置顶失败,请稍后重试!");
            return;
        }
        if (callback != null) {
            callback.deviceTop(this.mDeviceId, this.isTop);
        }
        this.mSelectPop.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$2(TabHomeDeviceSelecetPopCallback callback, View v) {
        if (callback != null) {
            callback.deviceRename(this.mDeviceId, this.mDeviceName);
        }
        this.mSelectPop.dismiss();
    }

    private static class SelectAddPopHolder {
        private static final TabHomeLongClickPop INSTANCE = new TabHomeLongClickPop();
    }
}
