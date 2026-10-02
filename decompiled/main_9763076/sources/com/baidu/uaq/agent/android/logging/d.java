package com.baidu.uaq.agent.android.logging;

/* JADX INFO: compiled from: DefaultAgentLog.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d implements a {
    private a bT = new e();

    public void b(a impl) {
        synchronized (this) {
            this.bT = impl;
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void E(String message) {
        synchronized (this) {
            this.bT.E(message);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void info(String message) {
        synchronized (this) {
            this.bT.info(message);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void warning(String message) {
        synchronized (this) {
            this.bT.warning(message);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void error(String message) {
        synchronized (this) {
            this.bT.error(message);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void a(String message, Throwable cause) {
        synchronized (this) {
            this.bT.a(message, cause);
        }
    }

    @Override // com.baidu.uaq.agent.android.logging.a
    public void setLevel(int level) {
        synchronized (this) {
            this.bT.setLevel(level);
        }
    }
}
