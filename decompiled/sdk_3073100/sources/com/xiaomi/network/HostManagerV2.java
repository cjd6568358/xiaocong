package com.xiaomi.network;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaomi.channel.commonutils.network.d;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HostManagerV2 extends HostManager {
    private final int a;
    private final int b;
    private int c;

    protected HostManagerV2(Context context, HostFilter hostFilter, HostManager.HttpGet httpGet, String str) {
        this(context, hostFilter, httpGet, str, null, null);
    }

    protected HostManagerV2(Context context, HostFilter hostFilter, HostManager.HttpGet httpGet, String str, String str2, String str3) {
        super(context, hostFilter, httpGet, str, str2, str3);
        this.a = 80;
        this.b = 5222;
        this.c = 80;
        addReservedHost("resolver.msg.xiaomi.net", "resolver.msg.xiaomi.net:5222");
    }

    static String a(String str) {
        try {
            int length = str.length();
            byte[] bytes = str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                if ((b & 240) != 240) {
                    int i2 = b & 240;
                    bytes[i] = (byte) (((b & 15) ^ ((byte) (((b >> 4) + length) & 15))) | i2);
                }
            }
            return new String(bytes);
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    protected JSONObject a() {
        JSONObject jSONObject;
        synchronized (this.mHostsMapping) {
            jSONObject = new JSONObject();
            jSONObject.put("ver", 2);
            jSONObject.put("data", toJSON());
        }
        return jSONObject;
    }

    protected void b(String str) {
        synchronized (this.mHostsMapping) {
            this.mHostsMapping.clear();
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("ver") != 2) {
                throw new JSONException("Bad version");
            }
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                Fallbacks fallbacksFromJSON = new Fallbacks().fromJSON(jSONArrayOptJSONArray.getJSONObject(i));
                this.mHostsMapping.put(fallbacksFromJSON.getHost(), fallbacksFromJSON);
            }
        }
    }

    @Override // com.xiaomi.network.HostManager
    protected boolean checkHostMapping() {
        synchronized (this.mHostsMapping) {
            if (hostLoaded) {
                return true;
            }
            hostLoaded = true;
            this.mHostsMapping.clear();
            try {
                String strLoadHosts = loadHosts();
                if (!TextUtils.isEmpty(strLoadHosts)) {
                    b(strLoadHosts);
                    com.xiaomi.channel.commonutils.logger.b.b("loading the new hosts succeed");
                    return true;
                }
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a("load bucket failure: " + th.getMessage());
            }
            return false;
        }
    }

    @Override // com.xiaomi.network.HostManager
    protected String getHost() {
        return "resolver.msg.xiaomi.net";
    }

    /* JADX INFO: Removed unreachable split cross block B:38:0x00fa */
    @Override // com.xiaomi.network.HostManager
    protected String getRemoteFallbackJSON(ArrayList<String> arrayList, String str, String str2) {
        ArrayList<String> arrayListA;
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<com.xiaomi.channel.commonutils.network.c> arrayList3 = new ArrayList();
        arrayList3.add(new com.xiaomi.channel.commonutils.network.a("type", str));
        if (str.equals(NetworkUtils.NETWORKTYPE_WAP)) {
            arrayList3.add(new com.xiaomi.channel.commonutils.network.a("conpt", a(d.k(this.sAppContext))));
        }
        arrayList3.add(new com.xiaomi.channel.commonutils.network.a("uuid", str2));
        arrayList3.add(new com.xiaomi.channel.commonutils.network.a("list", join(arrayList, ",")));
        Fallback localFallback = getLocalFallback("resolver.msg.xiaomi.net");
        String str3 = String.format(Locale.US, "http://%1$s/gslb/?ver=3.0", "resolver.msg.xiaomi.net:" + this.c);
        if (localFallback == null) {
            arrayList2.add(str3);
            synchronized (mReservedHosts) {
                Iterator<String> it = mReservedHosts.get("resolver.msg.xiaomi.net").iterator();
                while (it.hasNext()) {
                    arrayList2.add(String.format(Locale.US, "http://%1$s/gslb/?ver=3.0", it.next()));
                }
            }
            arrayListA = arrayList2;
        } else {
            arrayListA = localFallback.a(str3);
        }
        Iterator<String> it2 = arrayListA.iterator();
        IOException e = null;
        while (it2.hasNext()) {
            Uri.Builder builderBuildUpon = Uri.parse(it2.next()).buildUpon();
            for (com.xiaomi.channel.commonutils.network.c cVar : arrayList3) {
                builderBuildUpon.appendQueryParameter(cVar.a(), cVar.b());
            }
            try {
                return this.sHttpGetter == null ? d.a(this.sAppContext, new URL(builderBuildUpon.toString())) : this.sHttpGetter.a(builderBuildUpon.toString());
            } catch (IOException e2) {
                e = e2;
            }
        }
        if (e != null) {
            return super.getRemoteFallbackJSON(arrayList, str, str2);
        }
        return null;
    }

    @Override // com.xiaomi.network.HostManager
    public void persist() {
        synchronized (this.mHostsMapping) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.sAppContext.openFileOutput(getProcessName(), 0)));
                String string = a().toString();
                if (!TextUtils.isEmpty(string)) {
                    bufferedWriter.write(string);
                }
                bufferedWriter.close();
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a("persist bucket failure: " + e.getMessage());
            }
        }
    }

    @Override // com.xiaomi.network.HostManager
    public void purge() {
        synchronized (this.mHostsMapping) {
            Iterator<Fallbacks> it = this.mHostsMapping.values().iterator();
            while (it.hasNext()) {
                it.next().purge(true);
            }
            boolean z = false;
            while (!z) {
                Iterator<String> it2 = this.mHostsMapping.keySet().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = true;
                        break;
                    }
                    String next = it2.next();
                    if (this.mHostsMapping.get(next).getFallbacks().isEmpty()) {
                        this.mHostsMapping.remove(next);
                        z = false;
                        break;
                    }
                }
            }
        }
    }
}
