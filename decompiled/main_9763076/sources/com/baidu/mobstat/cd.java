package com.baidu.mobstat;

import java.io.File;
import java.io.FilenameFilter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class cd implements FilenameFilter {
    final /* synthetic */ cc a;

    cd(cc ccVar) {
        this.a = ccVar;
    }

    @Override // java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return str.startsWith("__send_data_");
    }
}
