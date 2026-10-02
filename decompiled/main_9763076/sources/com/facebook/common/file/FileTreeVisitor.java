package com.facebook.common.file;

import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface FileTreeVisitor {
    void postVisitDirectory(File file);

    void preVisitDirectory(File file);

    void visitFile(File file);
}
