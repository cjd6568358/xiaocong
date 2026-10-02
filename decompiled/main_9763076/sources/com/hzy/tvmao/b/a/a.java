package com.hzy.tvmao.b.a;

import com.hzy.tvmao.model.legacy.api.i;
import java.util.List;

/* JADX INFO: compiled from: ControlResponseBean.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private String a;
    private int b;
    private String c;
    private byte[] d;
    private Object e;
    private List<?> f;

    public a(int i) {
        this.d = null;
        this.e = null;
        this.f = null;
        this.b = i;
    }

    public a(i<?> iVar) {
        this.d = null;
        this.e = null;
        this.f = null;
        if (iVar != null) {
            this.b = iVar.a;
            this.c = iVar.b;
            this.e = iVar.e;
            this.d = iVar.d;
            return;
        }
        this.b = 0;
        this.c = "null was responsed";
    }

    public a(int i, String str, Object obj) {
        this.d = null;
        this.e = null;
        this.f = null;
        this.b = i;
        this.c = str;
        this.e = obj;
    }

    public void a(String str) {
        this.a = str;
    }

    public int a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public Object c() {
        return this.e;
    }

    public boolean d() {
        return this.b == 1;
    }

    public String toString() {
        return "ModelBean [ taskKey=" + this.a + ", respCode=" + this.b + ", respMsg=" + this.c + ", data=" + this.e + "]";
    }
}
