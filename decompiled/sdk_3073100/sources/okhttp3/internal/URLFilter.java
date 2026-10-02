package okhttp3.internal;

import java.io.IOException;
import java.net.URL;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface URLFilter {
    void checkURLPermitted(URL url) throws IOException;
}
