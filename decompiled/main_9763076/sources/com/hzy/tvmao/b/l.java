package com.hzy.tvmao.b;

import com.hzy.tvmao.model.db.bean.ChannelInfo;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: compiled from: LineupControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class l extends a {
    public static String b = "getLineUps";
    public static String c = "getLineupDataAndSave";
    private static l d;
    private HashMap<ChannelInfo.a, ChannelInfo> e;
    private int f;

    public static l c() {
        if (d == null) {
            d = new l();
        }
        return d;
    }

    public void a(int i, int i2, a.c cVar) {
        new m(this, this, cVar, b, i, i2).a();
    }

    public ChannelInfo a(int i, String str, int i2) {
        if (this.e == null) {
            a(this.f);
        }
        ChannelInfo.a aVar = new ChannelInfo.a();
        aVar.a = i;
        aVar.b = str;
        aVar.c = i2;
        return this.e.get(aVar);
    }

    public void a(int i, int i2, int i3, a.c cVar) {
        new n(this, this, cVar, c, i3, i2, i).a();
    }

    public boolean a(int i) {
        if (this.e != null) {
            this.e.clear();
        }
        this.e = com.hzy.tvmao.model.db.a.a.a().a(i);
        this.f = i;
        return this.e != null;
    }

    public HashMap<ChannelInfo.a, ChannelInfo> d() {
        if (this.e == null) {
            a(this.f);
        }
        return this.e;
    }

    public List<ChannelInfo> b(int i) {
        return com.hzy.tvmao.model.db.a.a.a().b(i);
    }
}
