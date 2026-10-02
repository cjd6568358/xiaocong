package com.youzan.spiderman.c.g;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.youzan.spiderman.c.b.h;
import com.youzan.spiderman.c.d;
import com.youzan.spiderman.cache.CacheUrl;
import com.youzan.spiderman.utils.DeviceUuidFactory;
import com.youzan.spiderman.utils.JsonUtil;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.NetWorkUtil;
import com.youzan.spiderman.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: UploadManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a {
    private static a a;
    private com.youzan.spiderman.c.f.b b = com.youzan.spiderman.c.f.b.a();

    private a() {
    }

    public static a a() {
        if (a == null) {
            a = new a();
        }
        return a;
    }

    public void a(Context context, c uploadUrl) {
        String bizTag = com.youzan.spiderman.c.c.a();
        if (TextUtils.isEmpty(bizTag)) {
            Logger.i("UploadManager", "upload bizTag should not be null", new Object[0]);
            return;
        }
        h uploadConfig = com.youzan.spiderman.c.a.a.a().e();
        if (!uploadConfig.a()) {
            Logger.i("UploadManager", "upload api unable", new Object[0]);
            return;
        }
        com.youzan.spiderman.c.b.a certificate = com.youzan.spiderman.c.a.a.a().f();
        if (!a(certificate)) {
            Logger.e("UploadManager", "certificate has expired", new Object[0]);
        } else if (b.a(uploadConfig, uploadUrl)) {
            Logger.i("UploadManager", "this url match success", new Object[0]);
            a(context, bizTag, uploadUrl, new DeviceUuidFactory(context).getDeviceUuid());
        } else {
            Logger.i("UploadManager", "this url don't allow upload, url:", new Object[0]);
        }
    }

    private boolean a(com.youzan.spiderman.c.b.a certificate) {
        long now = System.currentTimeMillis();
        return certificate.a() <= now && now - certificate.a() <= certificate.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> a(List<CacheUrl> urls) {
        List<String> rawUrls = new ArrayList<>();
        if (urls != null) {
            Uri.Builder rawBuilder = new Uri.Builder();
            for (CacheUrl cacheUrl : urls) {
                if (cacheUrl != null && cacheUrl.isImg()) {
                    Uri uri = cacheUrl.getUri();
                    rawUrls.add(rawBuilder.path(uri.getPath()).encodedQuery(uri.getQuery()).fragment(uri.getFragment()).build().toString());
                }
            }
        }
        return rawUrls;
    }

    private void a(final Context context, final String bizTag, final c uploadUrl, final String uuid) {
        this.b.a(new com.youzan.spiderman.c.f.a() { // from class: com.youzan.spiderman.c.g.a.1
            @Override // com.youzan.spiderman.c.f.a
            public void a(String token) {
                if (!StringUtils.isEmpty(token)) {
                    List<String> urls = a.this.a(uploadUrl.b());
                    if (!urls.isEmpty()) {
                        a.this.a(context, token, bizTag, uuid, urls);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Context context, final String token, final String bizTag, final String uuid, final List<String> urls) {
        if (!NetWorkUtil.hasNetworkPermission(context)) {
            Logger.e("UploadManager", "has no network permission to request upload", new Object[0]);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.putAll(d.a());
        params.put("access_token", token);
        params.put("biz_tag", bizTag);
        params.put("uuid_string", uuid);
        params.put("resource_path", StringUtils.join(urls));
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(com.youzan.spiderman.c.a.b()).post(com.youzan.spiderman.c.b.a(params)).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() { // from class: com.youzan.spiderman.c.g.a.2
            @Override // okhttp3.Callback
            public void onFailure(Call call2, IOException e) {
                Logger.e("UploadManager", "upload request fail: " + e, new Object[0]);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call2, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Logger.i("UploadManager", "upload request is not successful", new Object[0]);
                    return;
                }
                ResponseBody body = response.body();
                if (body != null) {
                    String content = body.string();
                    com.youzan.spiderman.c.d.d uploadResponse = null;
                    try {
                        uploadResponse = (com.youzan.spiderman.c.d.d) JsonUtil.fromJson(content, com.youzan.spiderman.c.d.d.class);
                    } catch (Exception e) {
                        Logger.e("UploadManager", "parse upload response exception: ", e);
                        e.printStackTrace();
                    }
                    if (uploadResponse != null) {
                        com.youzan.spiderman.c.d.b errorResponse = uploadResponse.b();
                        if (errorResponse != null) {
                            Logger.e("UploadManager", "upload error response", new Object[0]);
                            int code = errorResponse.a();
                            if (a.this.b.a(code)) {
                                a.this.b.a(token, new com.youzan.spiderman.c.f.a() { // from class: com.youzan.spiderman.c.g.a.2.1
                                    @Override // com.youzan.spiderman.c.f.a
                                    public void a(String token2) {
                                        if (!StringUtils.isEmpty(token2)) {
                                            a.this.a(context, token2, bizTag, uuid, urls);
                                        }
                                    }
                                });
                                return;
                            }
                            return;
                        }
                        if (!uploadResponse.a()) {
                            Logger.e("UploadManager", "upload response is false", new Object[0]);
                        } else {
                            Logger.i("UploadManager", "upload response is true, upload " + urls.size() + " resources", new Object[0]);
                        }
                    }
                }
            }
        });
    }
}
