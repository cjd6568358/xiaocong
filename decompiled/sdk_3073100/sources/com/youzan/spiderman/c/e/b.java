package com.youzan.spiderman.c.e;

import java.util.HashSet;
import java.util.Set;

/* JADX INFO: compiled from: ResourceListPref.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    private Set<String> a = new HashSet();

    public Set<String> a() {
        return this.a;
    }

    public synchronized void a(Set<String> resources) {
        this.a = resources;
    }
}
