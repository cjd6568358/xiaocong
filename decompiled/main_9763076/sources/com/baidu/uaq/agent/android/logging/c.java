package com.baidu.uaq.agent.android.logging;

import android.util.Log;

/* JADX INFO: compiled from: AndroidAgentLog.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c implements a {
    private int level = 3;

    @Override // com.baidu.uaq.agent.android.logging.a
    public void E(String message) {
        if (this.level == 5) {
            Log.d("com.baidu.uaq.agent.android", message);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void info(String message) {
        if (this.level >= 3) {
            Log.i("com.baidu.uaq.agent.android", message);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void warning(String message) {
        if (this.level >= 2) {
            Log.w("com.baidu.uaq.agent.android", message);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void error(String message) {
        if (this.level >= 1) {
            Log.e("com.baidu.uaq.agent.android", message);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void a(String message, Throwable cause) {
        if (this.level >= 1) {
            Log.e("com.baidu.uaq.agent.android", message, cause);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void setLevel(int level) {
        if (level <= 5 && level >= 1) {
            this.level = level;
            return;
        }
        throw new IllegalArgumentException("Log level is not between ERROR and DEBUG");
    }
}
