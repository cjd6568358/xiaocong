package org.apache.http.client.protocol;

import java.util.List;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public class ClientContextConfigurer implements ClientContext {
    public ClientContextConfigurer(HttpContext context) {
        throw new RuntimeException("Stub!");
    }

    public void setCookieSpecRegistry(CookieSpecRegistry registry) {
        throw new RuntimeException("Stub!");
    }

    public void setAuthSchemeRegistry(AuthSchemeRegistry registry) {
        throw new RuntimeException("Stub!");
    }

    public void setCookieStore(CookieStore store) {
        throw new RuntimeException("Stub!");
    }

    public void setCredentialsProvider(CredentialsProvider provider) {
        throw new RuntimeException("Stub!");
    }

    public void setAuthSchemePref(List<String> list) {
        throw new RuntimeException("Stub!");
    }
}
