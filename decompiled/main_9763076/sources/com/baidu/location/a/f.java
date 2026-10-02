package com.baidu.location.a;

import android.os.Environment;
import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class f extends Thread {
    final /* synthetic */ d a;

    f(d dVar) {
        this.a = dVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this.a.a(new File(Environment.getExternalStorageDirectory() + "/baidu/tempdata", "intime.dat"), "http://itsdata.map.baidu.com/long-conn-gps/sdk.php");
    }
}
