package com.youzan.spiderman.c.e;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.JsonParseException;
import com.youzan.spiderman.c.b.g;
import com.youzan.spiderman.utils.JsonUtil;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.NetWorkUtil;
import com.youzan.spiderman.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: SyncManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class d {
    private static d a;
    private long b;
    private int c;
    private int d;
    private boolean e;
    private List<String> f;
    private com.youzan.spiderman.c.f.b g = com.youzan.spiderman.c.f.b.a();

    private d() {
    }

    public static synchronized d a() {
        if (a == null) {
            a = new d();
        }
        return a;
    }

    public void a(final Context context) {
        final String bizTag = com.youzan.spiderman.c.c.a();
        if (TextUtils.isEmpty(bizTag)) {
            Logger.e("SyncManager", "syncModifyResource bizTag should not be null", new Object[0]);
            return;
        }
        final g syncConfig = com.youzan.spiderman.c.a.a.a().d();
        final f syncResourceManager = new f();
        syncResourceManager.a(context, syncConfig);
        boolean cacheEnable = com.youzan.spiderman.c.a.a.a().c();
        if (cacheEnable) {
            a(syncResourceManager);
            final e syncPref = (e) com.youzan.spiderman.cache.d.a(e.class, "sync_pref");
            final long lastUpTime = syncPref.a();
            long localLastSyncTime = syncPref.b();
            long syncInterval = syncConfig.a();
            long now = System.currentTimeMillis();
            if (now - localLastSyncTime < syncInterval && now > localLastSyncTime) {
                Logger.i("SyncManager", "in sync interval, return", new Object[0]);
            } else {
                this.g.a(new com.youzan.spiderman.c.f.a() { // from class: com.youzan.spiderman.c.e.d.1
                    @Override // com.youzan.spiderman.c.f.a
                    public void a(String token) {
                        if (!StringUtils.isEmpty(token)) {
                            d.this.a(context, bizTag, token, lastUpTime, syncConfig, syncPref, syncResourceManager);
                        }
                    }
                });
            }
        }
    }

    private void a(f syncResourceManager) {
        Set<String> resources = syncResourceManager.a();
        syncResourceManager.a(resources);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, String bizTag, String token, long lastUpTime, g syncConfig, e syncPref, f syncResourceManager) {
        this.b = System.currentTimeMillis();
        this.c = 0;
        this.d = 50;
        this.f = new ArrayList();
        b(context, bizTag, token, lastUpTime, syncConfig, syncPref, syncResourceManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final Context context, final String bizTag, final String token, final long lastUpTime, final g syncConfig, final e syncPref, final f syncResourceManager) {
        if (!NetWorkUtil.hasNetworkPermission(context)) {
            Logger.e("SyncManager", "has no network permission to request sync", new Object[0]);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.putAll(com.youzan.spiderman.c.d.a());
        params.put("start", String.valueOf(this.c));
        params.put("offset", String.valueOf(this.d));
        params.put("query_condition", StringUtils.join(this.f));
        params.put("last_update_time", lastUpTime > 0 ? String.valueOf(lastUpTime) : String.valueOf(0));
        params.put("biz_tag", bizTag);
        params.put("access_token", token);
        this.e = false;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(com.youzan.spiderman.c.b.a(com.youzan.spiderman.c.a.a(), params)).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() { // from class: com.youzan.spiderman.c.e.d.2
            @Override // okhttp3.Callback
            public void onFailure(Call call2, IOException e) {
                Logger.e("SyncManager", "sync modify resource failed", e);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call2, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Logger.e("SyncManager", "sync modify resource not successful", Integer.valueOf(response.code()), response.message());
                    onFailure(call2, new IOException());
                    return;
                }
                ResponseBody body = response.body();
                if (body == null) {
                    onFailure(call2, new IOException("download file content is null"));
                    return;
                }
                String content = body.string();
                com.youzan.spiderman.c.d.c syncResponse = null;
                try {
                    syncResponse = (com.youzan.spiderman.c.d.c) JsonUtil.fromJson(content, com.youzan.spiderman.c.d.c.class);
                } catch (JsonParseException e) {
                    Logger.e("SyncManager", "parse sync modify response exception", e);
                }
                if (syncResponse == null) {
                    onFailure(call2, new IOException());
                    return;
                }
                com.youzan.spiderman.c.d.b errorResponse = syncResponse.a();
                if (errorResponse != null) {
                    Logger.e("SyncManager", "sync modify error response:" + syncResponse.a(), new Object[0]);
                    onFailure(call2, new IOException());
                    int code = errorResponse.a();
                    if (d.this.g.a(code)) {
                        d.this.g.a(token, new com.youzan.spiderman.c.f.a() { // from class: com.youzan.spiderman.c.e.d.2.1
                            @Override // com.youzan.spiderman.c.f.a
                            public void a(String token2) {
                                if (!StringUtils.isEmpty(token2)) {
                                    d.this.a(context, bizTag, token2, lastUpTime, syncConfig, syncPref, syncResourceManager);
                                }
                            }
                        });
                        return;
                    }
                    return;
                }
                com.youzan.spiderman.c.b.e modifiedResource = syncResponse.b();
                if (modifiedResource != null) {
                    if (d.this.c == 0 && modifiedResource.d() > syncPref.c()) {
                        com.youzan.spiderman.c.a.a.a().a(context);
                        syncPref.c(modifiedResource.d());
                    }
                    List<String> globalResourceList = modifiedResource.a();
                    List<String> privateResourceList = modifiedResource.b();
                    syncResourceManager.a(globalResourceList);
                    syncResourceManager.a(privateResourceList);
                    d.this.f.clear();
                    if (globalResourceList != null && globalResourceList.size() >= d.this.d) {
                        d.this.e = true;
                        d.this.f.add("global");
                    }
                    if (privateResourceList != null && privateResourceList.size() >= d.this.d) {
                        d.this.e = true;
                        d.this.f.add("private");
                    }
                    if (d.this.e) {
                        d.this.c += d.this.d;
                        d.this.b(context, bizTag, token, lastUpTime, syncConfig, syncPref, syncResourceManager);
                        return;
                    }
                    long upTime = modifiedResource.c();
                    e eVar = syncPref;
                    if (upTime <= 0) {
                        upTime = System.currentTimeMillis();
                    }
                    eVar.a(upTime);
                    syncPref.b(d.this.b);
                    com.youzan.spiderman.cache.d.a(syncPref, "sync_pref");
                    return;
                }
                Logger.i("SyncManager", "sync modify get null modified resource", new Object[0]);
                onFailure(call2, new IOException());
            }
        });
    }
}
