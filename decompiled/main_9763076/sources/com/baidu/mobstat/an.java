package com.baidu.mobstat;

import java.io.File;
import java.util.Comparator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class an implements Comparator<File> {
    final /* synthetic */ al a;

    an(al alVar) {
        this.a = alVar;
    }

    @Override // java.util.Comparator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(File file, File file2) {
        return (int) (file2.lastModified() - file.lastModified());
    }
}
