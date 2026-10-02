package org.eclipse.paho.android.service;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface MqttTraceHandler {
    void traceDebug(String str, String str2);

    void traceError(String str, String str2);

    void traceException(String str, String str2, Exception exc);
}
