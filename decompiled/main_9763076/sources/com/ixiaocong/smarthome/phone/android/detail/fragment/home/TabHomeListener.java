package com.ixiaocong.smarthome.phone.android.detail.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.CheckDetailVersionManager;
import com.ixiaocong.smarthome.phone.android.common.manager.HomeDeviceContrlManager;
import com.ixiaocong.smarthome.phone.android.common.manager.SceneEditManager;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.AoniConsoleCameraActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.AoniPocketCameraActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.RnCheckVersionCallback;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LoginEvent;
import com.ixiaocong.smarthome.phone.rn.RNDetailActivity;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.greendao.model.insert.SceneDB;
import com.xiaocong.smarthome.httplib.callback.DowloadCallback;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.DeviceDetailV2Model;
import com.xiaocong.smarthome.httplib.model.DeviceListModel;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabHomeListener implements RnCheckVersionCallback {
    private String mClientId;
    private Context mContext;
    private List<DeviceListModel> mListModels;
    private int mPosition;

    public static TabHomeListener getInstance() {
        return TabHomeListenerHolder.homeListener;
    }

    public void deviceItemChildClick(Context context, View view, List<DeviceListModel> listModels, String clientId, int position) {
        if (listModels.get(position).getControlParameter().size() == 1) {
            switch (view.getId()) {
                case R.id.ll_right_tab_home_sbtn_layout /* 2131296601 */:
                    HomeDeviceContrlManager.contrlOnlyParameterDevice(context, clientId, listModels, position);
                    break;
            }
        }
        switch (view.getId()) {
            case R.id.ll_bot_four_tab_home_sbtn_layout /* 2131296579 */:
                HomeDeviceContrlManager.contrlMoreParameterDevice(context, clientId, listModels, position, 3);
                break;
            case R.id.ll_bot_one_tab_home_sbtn_layout /* 2131296580 */:
                HomeDeviceContrlManager.contrlMoreParameterDevice(context, clientId, listModels, position, 0);
                break;
            case R.id.ll_bot_three_tab_home_sbtn_layout /* 2131296581 */:
                HomeDeviceContrlManager.contrlMoreParameterDevice(context, clientId, listModels, position, 2);
                break;
            case R.id.ll_bot_two_tab_home_sbtn_layout /* 2131296582 */:
                HomeDeviceContrlManager.contrlMoreParameterDevice(context, clientId, listModels, position, 1);
                break;
        }
    }

    public void sceneItemClick(Context context, List<SceneDB> listModels, int position) {
        if (position == listModels.size() - 1) {
            LoginEvent event = new LoginEvent(false);
            EventBus.getDefault().post(event);
        } else {
            SceneEditManager.getInstance().startScene(context, Integer.decode(listModels.get(position).getSceneId()).intValue());
        }
    }

    public void deviceItemClick(Context context, int position, String clientId, List<DeviceListModel> listModels, DowloadCallback callback) {
        int productId = listModels.get(position).getProductId();
        if (productId == 381803) {
            Intent intent = new Intent(context, (Class<?>) AoniPocketCameraActivity.class);
            intent.putExtra(Constants.FLAG_DEVICE_ID, listModels.get(position).getDeviceId());
            intent.putExtra("deviceName", listModels.get(position).getDeviceName());
            intent.putExtra("productId", String.valueOf(listModels.get(position).getProductId()));
            intent.putExtra("is_admin", listModels.get(position).getIsAdmin());
            intent.putExtra("deviceStatus", listModels.get(position).getStatus());
            intent.putExtra("snapshotMsg", listModels.get(position).getSnapshot());
            context.startActivity(intent);
        } else if (productId == 381788) {
            Intent intent2 = new Intent(context, (Class<?>) AoniConsoleCameraActivity.class);
            intent2.putExtra(Constants.FLAG_DEVICE_ID, listModels.get(position).getDeviceId());
            intent2.putExtra("deviceName", listModels.get(position).getDeviceName());
            intent2.putExtra("productId", String.valueOf(listModels.get(position).getProductId()));
            intent2.putExtra("is_admin", listModels.get(position).getIsAdmin());
            intent2.putExtra("deviceStatus", listModels.get(position).getStatus());
            intent2.putExtra("snapshotMsg", listModels.get(position).getSnapshot());
            context.startActivity(intent2);
        } else if (productId != 381804) {
            this.mContext = context;
            this.mClientId = clientId;
            this.mListModels = listModels;
            this.mPosition = position;
            listModels.get(position).setDownload(true);
            CheckDetailVersionManager.getInstance().checkedDownloadDetail(context, callback, this, listModels.get(position).getProductId(), listModels.get(position).getDeviceId(), position);
        }
        XcLogger.e("mHomeDevRecycler", position + "===" + productId);
    }

    public void startActivityForDetail(Context context, DeviceDetailV2Model deviceDetailV2Model, boolean isDownload) {
        Intent intent = new Intent(context, (Class<?>) RNDetailActivity.class);
        intent.putExtra(Constants.FLAG_DEVICE_ID, deviceDetailV2Model.getDeviceId());
        intent.putExtra("deviceName", deviceDetailV2Model.getDeviceInfo().getDeviceName());
        intent.putExtra("productId", deviceDetailV2Model.getDeviceInfo().getProductId());
        intent.putExtra("is_admin", deviceDetailV2Model.getDeviceInfo().getIsAdmin());
        intent.putExtra("clientId", Constants.MAIN_VERSION_TAG);
        intent.putExtra("deviceStatus", deviceDetailV2Model.getStatus());
        intent.putExtra("imgUrl", "file://" + context.getFilesDir() + "/ixiaocong/js/img/drawable-mdpi/");
        intent.putExtra("snapshotMsg", deviceDetailV2Model.getSnapshot());
        intent.putExtra("isNowDownload", isDownload);
        context.startActivity(intent);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.RnCheckVersionCallback
    public void checkVersionCallback(int checkCode, DeviceDetailV2Model deviceDetailV2Model) {
        if (checkCode == 0) {
            startActivityForDetail(this.mContext, deviceDetailV2Model, true);
        } else if (checkCode != 1) {
            if (checkCode == 2) {
                ToastUtils.showShort(this.mContext, "没有找到当前设备的配置文件");
            } else {
                if (checkCode == 3) {
                }
            }
        }
    }

    private static class TabHomeListenerHolder {
        private static final TabHomeListener homeListener = new TabHomeListener();
    }
}
