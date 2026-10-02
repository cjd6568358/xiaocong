package org.apache.http;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface HeaderElement {
    String getName();

    NameValuePair getParameter(int i);

    NameValuePair getParameterByName(String str);

    int getParameterCount();

    NameValuePair[] getParameters();

    String getValue();
}
