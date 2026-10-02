package org.apache.http.auth;

import java.security.Principal;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface Credentials {
    String getPassword();

    Principal getUserPrincipal();
}
