package com.ixiaocong.smarthome.phone.android.common.manager;

import android.content.Context;
import android.text.TextUtils;
import com.xiaocong.smarthome.httplib.model.DeviceListModel;
import com.xiaocong.smarthome.httplib.model.inside.DeviceParameterModel;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeDeviceContrlManager {
    public static void contrlMoreParameterDevice(Context context, String clientId, List<DeviceListModel> deviceListModels, int position, int location) {
        if (deviceListModels != null) {
            try {
                if (!TextUtils.isEmpty(deviceListModels.get(position).getSnapshot())) {
                    JSONObject jsonObj = new JSONObject(deviceListModels.get(position).getSnapshot());
                    if (jsonObj.optInt(((DeviceParameterModel) deviceListModels.get(position).getControlParameter().get(location)).getKey()) == 0) {
                        XCDeviceController.getInstance().publishJsonObject(context, deviceListModels.get(position).getDeviceId(), ((DeviceParameterModel) deviceListModels.get(position).getControlParameter().get(location)).getKey(), 1);
                    } else {
                        XCDeviceController.getInstance().publishJsonObject(context, deviceListModels.get(position).getDeviceId(), ((DeviceParameterModel) deviceListModels.get(position).getControlParameter().get(location)).getKey(), 0);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void contrlOnlyParameterDevice(Context context, String clientId, List<DeviceListModel> deviceListModels, int position) {
        if (deviceListModels != null) {
            try {
                if (!TextUtils.isEmpty(deviceListModels.get(position).getSnapshot())) {
                    JSONObject jsonObj = new JSONObject(deviceListModels.get(position).getSnapshot());
                    if (jsonObj.optInt(((DeviceParameterModel) deviceListModels.get(position).getControlParameter().get(0)).getKey()) == 0) {
                        XCDeviceController.getInstance().publishJsonObject(context, deviceListModels.get(position).getDeviceId(), ((DeviceParameterModel) deviceListModels.get(position).getControlParameter().get(0)).getKey(), 1);
                    } else {
                        XCDeviceController.getInstance().publishJsonObject(context, deviceListModels.get(position).getDeviceId(), ((DeviceParameterModel) deviceListModels.get(position).getControlParameter().get(0)).getKey(), 0);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
