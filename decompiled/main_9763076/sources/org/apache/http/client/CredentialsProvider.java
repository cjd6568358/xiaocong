package org.apache.http.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface CredentialsProvider {
    void clear();

    Credentials getCredentials(AuthScope authScope);

    void setCredentials(AuthScope authScope, Credentials credentials);
}
