package com.youzan.androidsdk.basic.tool;

import android.content.Context;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.account.Token;

/* JADX INFO: compiled from: SessionManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class e {
    private e() {
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static void m52(Context context, YouzanToken token) {
        a.b.m17(context, token.getCookieKey(), token.getCookieValue());
        Token.save(token);
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static void m51(Context context) {
        a.C0016a.m14(context);
        a.C0016a.m8();
        Token.clear(context);
    }
}
