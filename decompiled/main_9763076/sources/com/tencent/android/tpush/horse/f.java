package com.tencent.android.tpush.horse;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends a {
    private static f a;

    private f() {
    }

    public static synchronized f i() {
        if (a == null) {
            a = new f();
        }
        return a;
    }

    @Override // com.tencent.android.tpush.horse.a
    public void e() {
        i().d().clear();
    }

    @Override // com.tencent.android.tpush.horse.a
    public void f() {
        i().a(-1);
    }
}
