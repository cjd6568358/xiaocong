package com.youzan.spiderman.html;

import com.youzan.spiderman.utils.FileUtil;
import com.youzan.spiderman.utils.Logger;
import java.io.ByteArrayInputStream;
import java.io.File;

/* JADX INFO: compiled from: HtmlCacheWriter.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class g {
    private o a;

    public g(o htmlUrl) {
        this.a = htmlUrl;
    }

    public void a(l responseHeader, i htmlData, byte[] htmlContent) throws Throwable {
        String hash = this.a.c();
        File headerFile = new File(com.youzan.spiderman.cache.g.i(), hash);
        boolean headerSucceed = FileUtil.writeContentToFile(headerFile, l.a(responseHeader));
        if (headerSucceed) {
            File htmlFile = new File(com.youzan.spiderman.cache.g.h(), hash);
            ByteArrayInputStream htmlStream = new ByteArrayInputStream(htmlContent);
            boolean htmlSucceed = FileUtil.writeStreamToFile(htmlFile, htmlStream);
            if (htmlSucceed) {
                com.youzan.spiderman.b.f.a().a(htmlData.b());
                j htmlDataPool = j.a();
                htmlDataPool.a(htmlData);
                htmlDataPool.b();
                return;
            }
            Logger.e("HtmlCacheWriter", "write html content to local failed, htmlFile:" + htmlFile, new Object[0]);
            return;
        }
        Logger.e("HtmlCacheWriter", "write html header to local failed, headerFile:" + headerFile, new Object[0]);
    }

    public void a() {
        String hash = this.a.c();
        File headerFile = new File(com.youzan.spiderman.cache.g.i(), hash);
        if (headerFile.exists()) {
            headerFile.delete();
        }
        File htmlFile = new File(com.youzan.spiderman.cache.g.h(), hash);
        if (htmlFile.exists()) {
            htmlFile.delete();
        }
        j htmlDataPool = j.a();
        i htmlData = htmlDataPool.b(hash);
        if (htmlData != null) {
            htmlDataPool.b();
        }
    }
}
