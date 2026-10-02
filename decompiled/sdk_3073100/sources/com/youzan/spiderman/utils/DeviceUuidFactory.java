package com.youzan.spiderman.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import java.util.UUID;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceUuidFactory {
    private static UUID uuid;

    @SuppressLint({"MissingPermission"})
    public DeviceUuidFactory(Context context) {
        if (uuid == null) {
            synchronized (DeviceUuidFactory.class) {
                if (uuid == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("device_id.xml", 0);
                    String id = sharedPreferences.getString("device_id", null);
                    if (id != null) {
                        uuid = UUID.fromString(id);
                    } else {
                        String androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
                        try {
                            if (!"9774d56d682e549c".equals(androidId)) {
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                                String deviceId = PermissionUtil.hasReadPhoneStatePermission(context) ? ((TelephonyManager) context.getSystemService("phone")).getDeviceId() : null;
                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sharedPreferences.edit().putString("device_id", uuid.toString()).apply();
                    }
                }
            }
        }
    }

    public String getDeviceUuid() {
        return uuid.toString();
    }
}
