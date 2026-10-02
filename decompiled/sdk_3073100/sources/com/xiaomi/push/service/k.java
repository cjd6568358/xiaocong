package com.xiaomi.push.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class k {
    private static volatile k c;
    private Context a;
    private AccountManager b;
    private ArrayList<a> d;
    private OnAccountsUpdateListener e;

    public interface a {
    }

    private k(Context context) {
        this.a = context;
        if (com.xiaomi.channel.commonutils.android.f.b(this.a)) {
            this.b = AccountManager.get(this.a);
            this.d = new ArrayList<>();
        }
    }

    public static k a(Context context) {
        if (c == null) {
            synchronized (k.class) {
                if (c == null) {
                    c = new k(context);
                }
            }
        }
        return c;
    }

    private String e() {
        Account accountA = com.xiaomi.channel.commonutils.android.f.a(this.a);
        return accountA == null ? "" : accountA.name;
    }

    public void b() {
        if (com.xiaomi.channel.commonutils.android.f.b(this.a) && this.e != null) {
            this.b.removeOnAccountsUpdatedListener(this.e);
        }
    }

    public void b(a aVar) {
        if (this.d == null || aVar == null) {
            return;
        }
        this.d.remove(aVar);
        if (this.d.size() == 0) {
            b();
        }
    }

    public String c() {
        String strE = e();
        if (TextUtils.isEmpty(strE)) {
            m.a(this.a).a("0");
            return "0";
        }
        m.a(this.a).a(strE);
        return strE;
    }
}
