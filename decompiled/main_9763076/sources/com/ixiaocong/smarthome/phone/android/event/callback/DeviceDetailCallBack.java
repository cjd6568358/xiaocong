package com.ixiaocong.smarthome.phone.android.event.callback;

import com.xiaocong.smarthome.httplib.model.DevDetailModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DeviceDetailCallBack {
    void requestDetailSuccess(DevDetailModel devDetailModel);

    void unbindDeviceResponse(boolean z);
}
