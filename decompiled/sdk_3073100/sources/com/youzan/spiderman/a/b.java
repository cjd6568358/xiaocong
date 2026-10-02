package com.youzan.spiderman.a;

import com.youzan.spiderman.utils.FileUtil;
import com.youzan.spiderman.utils.IOUtils;
import com.youzan.spiderman.utils.Logger;

/* JADX INFO: compiled from: SaveContentJob.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b extends a {
    private static final String a = b.class.getSimpleName();
    private String b;
    private String c;

    public static b a(String filePath, String content) {
        return new b(filePath, content);
    }

    protected b(String filePath, String content) {
        this.b = filePath;
        this.c = content;
    }

    @Override // com.youzan.spiderman.a.a
    public void a() throws Throwable {
        String originalFilePath = this.b;
        String tmpFilePath = String.format("%s_tmp", this.b);
        FileUtil.createFile(tmpFilePath);
        IOUtils.writeStringToFile(tmpFilePath, this.c);
        if (FileUtil.checkFileExists(originalFilePath)) {
            FileUtil.deleteFile(originalFilePath);
        }
        FileUtil.renameToFile(tmpFilePath, originalFilePath);
    }

    @Override // com.youzan.spiderman.a.a
    public void a(Throwable throwable) {
        Logger.e(a, throwable);
    }
}
