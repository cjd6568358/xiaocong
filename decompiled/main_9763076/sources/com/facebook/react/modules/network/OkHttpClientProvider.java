package com.facebook.react.modules.network;

import android.os.Build;
import com.facebook.common.logging.FLog;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class OkHttpClientProvider {
    private static OkHttpClient sClient;

    public static OkHttpClient getOkHttpClient() {
        if (sClient == null) {
            sClient = createClient();
        }
        return sClient;
    }

    public static void replaceOkHttpClient(OkHttpClient client) {
        sClient = client;
    }

    private static OkHttpClient createClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder().connectTimeout(0L, TimeUnit.MILLISECONDS).readTimeout(0L, TimeUnit.MILLISECONDS).writeTimeout(0L, TimeUnit.MILLISECONDS).cookieJar(new ReactCookieJarContainer());
        return enableTls12OnPreLollipop(client).build();
    }

    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 19) {
            try {
                client.sslSocketFactory(new TLSSocketFactory());
                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(new TlsVersion[]{TlsVersion.TLS_1_2}).build();
                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);
                client.connectionSpecs(specs);
            } catch (Exception exc) {
                FLog.e("OkHttpClientProvider", "Error while enabling TLS 1.2", exc);
            }
        }
        return client;
    }
}
