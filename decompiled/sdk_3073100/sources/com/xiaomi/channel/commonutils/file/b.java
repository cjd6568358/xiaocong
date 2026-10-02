package com.xiaomi.channel.commonutils.file;

import java.io.File;
import java.io.FileFilter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class b implements FileFilter {
    b() {
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        return file.isDirectory();
    }
}
