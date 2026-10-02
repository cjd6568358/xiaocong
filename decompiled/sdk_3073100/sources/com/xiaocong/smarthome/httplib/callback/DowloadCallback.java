package com.xiaocong.smarthome.httplib.callback;

import com.xiaocong.smarthome.httplib.model.DeviceDetailV2Model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface DowloadCallback {
    void dowloadFailureListener(int i);

    void downloadFinishListener(int i, boolean z, DeviceDetailV2Model deviceDetailV2Model);

    void downloadSuccessListener(int i, int i2, String str);
}
