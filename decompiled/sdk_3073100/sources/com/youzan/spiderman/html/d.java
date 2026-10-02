package com.youzan.spiderman.html;

import com.google.gson.JsonParseException;
import com.youzan.spiderman.utils.FileUtil;
import com.youzan.spiderman.utils.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: compiled from: FetchInterceptor.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class d {
    public static HtmlResponse a(i htmlData, o htmlUrl) throws Throwable {
        File headerFile = new File(com.youzan.spiderman.cache.g.i(), htmlData.b());
        if (!headerFile.exists()) {
            return null;
        }
        l header = null;
        try {
            String headerStr = FileUtil.getFileContent(headerFile);
            header = l.a(headerStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonParseException je) {
            je.printStackTrace();
        }
        if (header == null) {
            return null;
        }
        File htmlFile = new File(com.youzan.spiderman.cache.g.h(), htmlData.b());
        try {
            FileInputStream inputStream = new FileInputStream(htmlFile);
            byte[] content = b.a(inputStream);
            if (content != null) {
                return new HtmlResponse(header.a(), content, htmlData.c());
            }
            return null;
        } catch (IOException e2) {
            Logger.e("FetchInterceptor", e2);
            return null;
        }
    }
}
