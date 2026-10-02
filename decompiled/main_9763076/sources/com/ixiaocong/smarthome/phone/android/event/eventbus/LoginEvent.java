package com.ixiaocong.smarthome.phone.android.event.eventbus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LoginEvent {
    private boolean isLogin;

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return this.isLogin;
    }
}
