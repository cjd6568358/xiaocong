package org.eclipse.paho.client.mqttv3.logging;

import java.util.ResourceBundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface Logger {
    void fine(String str, String str2, String str3);

    void fine(String str, String str2, String str3, Object[] objArr);

    void fine(String str, String str2, String str3, Object[] objArr, Throwable th);

    void finer(String str, String str2, String str3);

    void initialise(ResourceBundle resourceBundle, String str, String str2);

    boolean isLoggable(int i);

    void setResourceName(String str);

    void severe(String str, String str2, String str3, Object[] objArr);

    void warning(String str, String str2, String str3);

    void warning(String str, String str2, String str3, Object[] objArr);
}
