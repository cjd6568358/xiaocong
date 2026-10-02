package com.ut.mini.crashhandler;

import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface IUTCrashCaughtListner {
    Map<String, String> onCrashCaught(Thread thread, Throwable th);
}
