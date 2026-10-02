package com.tencent.mid.b;

import android.content.Context;
import com.tencent.mid.util.Util;
import com.tencent.mid.util.i;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e extends f {
    protected static com.tencent.mid.util.f a = Util.getLogger();

    public e(Context context, int i) {
        super(context, i);
    }

    @Override // com.tencent.mid.b.f
    public int a() {
        return 1;
    }

    @Override // com.tencent.mid.b.f
    protected void a(a aVar) {
        synchronized (this) {
            a.b("write CheckEntity to Settings.System:" + aVar.toString());
            i.a(this.c).a(g(), aVar.toString());
        }
    }

    @Override // com.tencent.mid.b.f
    protected void a(String str) {
        synchronized (this) {
            a.b("write mid to Settings.System");
            i.a(this.c).a(h(), str);
        }
    }

    @Override // com.tencent.mid.b.f
    protected boolean b() {
        return Util.checkPermission(this.c, "android.permission.WRITE_SETTINGS");
    }

    @Override // com.tencent.mid.b.f
    protected String c() {
        String strA;
        synchronized (this) {
            a.b("read mid from Settings.System");
            strA = i.a(this.c).a(h());
        }
        return strA;
    }

    @Override // com.tencent.mid.b.f
    protected a d() {
        a aVar;
        synchronized (this) {
            aVar = new a(i.a(this.c).a(g()));
            a.b("read readCheckEntity from Settings.System:" + aVar.toString());
        }
        return aVar;
    }
}
