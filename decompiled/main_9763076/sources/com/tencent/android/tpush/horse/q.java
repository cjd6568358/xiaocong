package com.tencent.android.tpush.horse;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class q extends a {
    private static q a;

    private q() {
    }

    public static synchronized q i() {
        if (a == null) {
            a = new q();
        }
        return a;
    }

    @Override // com.tencent.android.tpush.horse.a
    public void e() {
        f.i().d().clear();
    }

    @Override // com.tencent.android.tpush.horse.a
    public void f() {
        f.i().a(-1);
        f.i().a();
    }
}
