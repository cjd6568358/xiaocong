package com.ut.mini;

import com.ut.mini.core.sign.IUTRequestAuthentication;
import com.ut.mini.crashhandler.IUTCrashCaughtListner;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface IUTApplication {
    String getUTAppVersion();

    String getUTChannel();

    IUTCrashCaughtListner getUTCrashCraughtListener();

    IUTRequestAuthentication getUTRequestAuthInstance();

    boolean isAliyunOsSystem();

    boolean isUTCrashHandlerDisable();

    boolean isUTLogEnable();
}
