package com.youzan.spiderman.html;

import com.youzan.spiderman.utils.JsonUtil;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HtmlResponse {
    private Map<String, List<String>> a;
    private byte[] b;
    private String c;

    public HtmlResponse(Map<String, List<String>> header, byte[] content, String encoding) {
        this.a = header;
        this.b = content;
        this.c = encoding;
    }

    public Map<String, List<String>> getHeader() {
        return this.a;
    }

    public Map<String, String> getTransferHeader() {
        return l.c(this.a);
    }

    public ByteArrayInputStream getContentStream() {
        return new ByteArrayInputStream(this.b);
    }

    public String getEncoding() {
        return this.c;
    }

    public String getMimeType() {
        return "text/html";
    }

    public List<String> getResponseHeader(String field) {
        if (field != null) {
            return this.a.get(field);
        }
        return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nheader:  ").append(JsonUtil.toJson(this.a)).append("\nencoding:  ").append(this.c).append("\nhtml:  ").append(this.b).append(", size:" + this.b.length);
        return stringBuilder.toString();
    }
}
