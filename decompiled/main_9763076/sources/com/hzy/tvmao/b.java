package com.hzy.tvmao;

import java.io.IOException;

/* JADX INFO: compiled from: KookongSDK.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements Runnable {
    b() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.hzy.tvmao.c.a.a();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
