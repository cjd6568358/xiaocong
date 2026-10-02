package org.apache.http;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface Header {
    HeaderElement[] getElements() throws ParseException;

    String getName();

    String getValue();
}
