package com.alibaba.mtl.log;

import java.util.Map;

/* JADX INFO: compiled from: UTMCVariables.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    public static final c a = new c();

    /* JADX INFO: renamed from: u, reason: collision with other field name */
    private boolean f30u = false;
    private boolean v = false;
    private String H = null;
    private Map<String, String> u = null;
    private boolean w = false;
    private boolean x = false;
    private String I = null;
    private String J = null;
    private String K = null;
    private boolean y = false;

    public static c a() {
        return a;
    }

    public synchronized void e(String str) {
        this.I = str;
    }

    public synchronized void p() {
        this.x = true;
    }

    public synchronized boolean d() {
        return this.x;
    }

    public synchronized void c(Map<String, String> map) {
        this.u = map;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public synchronized Map<String, String> m21a() {
        return this.u;
    }
}
