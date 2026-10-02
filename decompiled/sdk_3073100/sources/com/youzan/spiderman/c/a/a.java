package com.youzan.spiderman.c.a;

import android.content.Context;
import android.text.TextUtils;
import com.youzan.spiderman.c.b.c;
import com.youzan.spiderman.c.b.f;
import com.youzan.spiderman.c.b.g;
import com.youzan.spiderman.c.b.h;
import com.youzan.spiderman.c.f.b;
import com.youzan.spiderman.cache.d;
import com.youzan.spiderman.utils.DeviceUuidFactory;
import com.youzan.spiderman.utils.JsonUtil;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.NetWorkUtil;
import com.youzan.spiderman.utils.StringUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: ConfigManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a {
    private static b a;
    private b b;
    private boolean c;

    private a() {
        b();
        i();
        this.b = b.a();
    }

    public static a a() {
        return C0019a.a;
    }

    /* JADX INFO: renamed from: com.youzan.spiderman.c.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: ConfigManager.java */
    private static class C0019a {
        private static final a a = new a();
    }

    public b b() {
        if (a == null) {
            a = (b) d.a(b.class, "config_pref");
            if (a == null) {
                a = new b();
            }
        }
        return a;
    }

    public boolean c() {
        return this.c;
    }

    private void i() {
        b configPref = b();
        this.c = configPref.a().b().a().b();
    }

    public g d() {
        b();
        c configEntity = a.a();
        com.youzan.spiderman.c.b.b configContent = configEntity.b();
        return configContent.b();
    }

    public h e() {
        b();
        c configEntity = a.a();
        com.youzan.spiderman.c.b.b configContent = configEntity.b();
        return configContent.c();
    }

    public com.youzan.spiderman.c.b.a f() {
        b();
        c configEntity = a.a();
        return configEntity.a();
    }

    public com.youzan.spiderman.c.b.d g() {
        b();
        c configEntity = a.a();
        com.youzan.spiderman.c.b.b configContent = configEntity.b();
        return configContent.d();
    }

    public void a(final Context context) {
        this.b.a(new com.youzan.spiderman.c.f.a() { // from class: com.youzan.spiderman.c.a.a.1
            @Override // com.youzan.spiderman.c.f.a
            public void a(String token) {
                String bizTag = com.youzan.spiderman.c.c.a();
                if (!TextUtils.isEmpty(bizTag)) {
                    a.this.a(context, token, bizTag);
                } else {
                    Logger.e("ConfigManager", "request config, bizTag should not be null", new Object[0]);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Context context, final String token, final String bizTag) {
        if (!NetWorkUtil.hasNetworkPermission(context)) {
            Logger.e("ConfigManager", "has no network permission to request config", new Object[0]);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("biz_tag", bizTag);
        params.putAll(com.youzan.spiderman.c.d.a());
        params.put("uuid_string", new DeviceUuidFactory(context).getDeviceUuid());
        params.put("access_token", token);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(com.youzan.spiderman.c.a.c()).post(com.youzan.spiderman.c.b.a(params)).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() { // from class: com.youzan.spiderman.c.a.a.2
            @Override // okhttp3.Callback
            public void onFailure(Call call2, IOException e) {
                Logger.e("ConfigManager", "config request fail", new Object[0]);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call2, Response response) throws IOException {
                f resourceConfig;
                if (!response.isSuccessful()) {
                    Logger.e("ConfigManager", "config request fail", new Object[0]);
                    return;
                }
                ResponseBody body = response.body();
                if (body != null) {
                    String content = body.string();
                    com.youzan.spiderman.c.d.a configResponse = null;
                    try {
                        configResponse = (com.youzan.spiderman.c.d.a) JsonUtil.fromJson(content, com.youzan.spiderman.c.d.a.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("ConfigManager", "json parse error: " + e.getMessage(), new Object[0]);
                    }
                    if (configResponse != null) {
                        com.youzan.spiderman.c.d.b errorResponse = configResponse.b();
                        if (errorResponse != null) {
                            Logger.e("ConfigManager", "config network request has error response", new Object[0]);
                            int code = errorResponse.a();
                            if (a.this.b.a(code)) {
                                a.this.b.a(token, new com.youzan.spiderman.c.f.a() { // from class: com.youzan.spiderman.c.a.a.2.1
                                    @Override // com.youzan.spiderman.c.f.a
                                    public void a(String token2) {
                                        if (!StringUtils.isEmpty(token2)) {
                                            a.this.a(context, token2, bizTag);
                                        }
                                    }
                                });
                            }
                        }
                        c configEntity = configResponse.a();
                        if (configEntity != null) {
                            com.youzan.spiderman.c.b.b configContent = configEntity.b();
                            Logger.d("ConfigManager", "config content not null, save it", new Object[0]);
                            if (configContent != null && (resourceConfig = configContent.a()) != null) {
                                List<String> ignoreSuffix = resourceConfig.c();
                                if (ignoreSuffix != null && !ignoreSuffix.isEmpty()) {
                                    com.youzan.spiderman.cache.b.a().a(ignoreSuffix);
                                }
                                List<String> ignoreResources = resourceConfig.a();
                                if (ignoreResources != null && !ignoreResources.isEmpty()) {
                                    com.youzan.spiderman.cache.b.a().b(ignoreResources);
                                }
                                a.this.c = resourceConfig.b();
                            }
                            b.a(configEntity);
                            a.a.b(configEntity);
                            d.a(a.a, "config_pref");
                        }
                    }
                }
            }
        });
    }
}
