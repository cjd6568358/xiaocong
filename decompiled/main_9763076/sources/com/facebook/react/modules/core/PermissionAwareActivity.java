package com.facebook.react.modules.core;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface PermissionAwareActivity {
    void requestPermissions(String[] strArr, int i, PermissionListener permissionListener);

    boolean shouldShowRequestPermissionRationale(String str);
}
