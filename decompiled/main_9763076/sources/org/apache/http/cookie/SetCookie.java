package org.apache.http.cookie;

import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface SetCookie extends Cookie {
    void setComment(String str);

    void setDomain(String str);

    void setExpiryDate(Date date);

    void setPath(String str);

    void setSecure(boolean z);

    void setValue(String str);

    void setVersion(int i);
}
