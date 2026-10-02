package com.tencent.android.tpush.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class af extends Thread {
    InputStream a;
    String b;
    final /* synthetic */ XGWatchdog c;

    af(XGWatchdog xGWatchdog, InputStream inputStream, String str) {
        this.c = xGWatchdog;
        this.a = inputStream;
        this.b = str;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.a));
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    if (this.b.equals("Error")) {
                        com.tencent.android.tpush.a.a.i(XGWatchdog.TAG, "Runtime exe return err: " + line);
                    } else {
                        com.tencent.android.tpush.a.a.i(XGWatchdog.TAG, "Runtime exe return " + line);
                    }
                } else {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
