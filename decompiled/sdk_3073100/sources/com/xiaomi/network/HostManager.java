package com.xiaomi.network;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Process;
import android.text.TextUtils;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaomi.channel.commonutils.network.d;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HostManager {
    private static HostManagerFactory factory;
    private static String sAppName;
    private static String sAppVersion;
    private static HostManager sInstance;
    protected Context sAppContext;
    private HostFilter sHostFilter;
    protected HttpGet sHttpGetter;
    private String sUserId;
    protected static Map<String, ArrayList<String>> mReservedHosts = new HashMap();
    protected static boolean hostLoaded = false;
    protected Map<String, Fallbacks> mHostsMapping = new HashMap();
    private long remoteRequestFailureCount = 0;
    private final long MAX_REQUEST_FAILURE_CNT = 15;
    private long lastRemoteRequestTimestamp = 0;
    private String currentISP = "isp_prov_city_country_ip";

    public interface HostManagerFactory {
        HostManager a(Context context, HostFilter hostFilter, HttpGet httpGet, String str);
    }

    public interface HttpGet {
        String a(String str);
    }

    protected HostManager(Context context, HostFilter hostFilter, HttpGet httpGet, String str, String str2, String str3) {
        this.sUserId = "0";
        this.sAppContext = context.getApplicationContext();
        if (this.sAppContext == null) {
            this.sAppContext = context;
        }
        this.sHttpGetter = httpGet;
        if (hostFilter == null) {
            this.sHostFilter = new a(this);
        } else {
            this.sHostFilter = hostFilter;
        }
        this.sUserId = str;
        sAppName = str2 == null ? context.getPackageName() : str2;
        sAppVersion = str3 == null ? getVersionName() : str3;
    }

    public static void addReservedHost(String str, String str2) {
        ArrayList<String> arrayList = mReservedHosts.get(str);
        synchronized (mReservedHosts) {
            try {
                if (arrayList == null) {
                    ArrayList<String> arrayList2 = new ArrayList<>();
                    arrayList2.add(str2);
                    mReservedHosts.put(str, arrayList2);
                } else if (!arrayList.contains(str2)) {
                    arrayList.add(str2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static synchronized HostManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("the host manager is not initialized yet.");
        }
        return sInstance;
    }

    private String getVersionName() {
        try {
            PackageInfo packageInfo = this.sAppContext.getPackageManager().getPackageInfo(this.sAppContext.getPackageName(), 16384);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (Exception e) {
        }
        return "0";
    }

    public static synchronized void init(Context context, HostFilter hostFilter, HttpGet httpGet, String str, String str2, String str3) {
        if (sInstance == null) {
            if (factory == null) {
                sInstance = new HostManager(context, hostFilter, httpGet, str, str2, str3);
            } else {
                sInstance = factory.a(context, hostFilter, httpGet, str);
            }
        }
    }

    public static <T> String join(Collection<T> collection, String str) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static String join(String[] strArr, String str) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            sb.append(str);
            sb.append(strArr[i]);
        }
        return sb.toString();
    }

    private ArrayList<Fallback> requestRemoteFallbacks(ArrayList<String> arrayList) {
        int i;
        purge();
        synchronized (this.mHostsMapping) {
            checkHostMapping();
            for (String str : this.mHostsMapping.keySet()) {
                if (!arrayList.contains(str)) {
                    arrayList.add(str);
                }
            }
        }
        synchronized (mReservedHosts) {
            for (String str2 : mReservedHosts.keySet()) {
                if (!arrayList.contains(str2)) {
                    arrayList.add(str2);
                }
            }
        }
        if (!arrayList.contains(getHost())) {
            arrayList.add(getHost());
        }
        ArrayList<Fallback> arrayList2 = new ArrayList<>(arrayList.size());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.add(null);
        }
        try {
            String activeNetworkLabel = d.f(this.sAppContext) ? NetworkUtils.NETWORKTYPE_WIFI : NetworkUtils.NETWORKTYPE_WAP;
            String remoteFallbackJSON = getRemoteFallbackJSON(arrayList, activeNetworkLabel, this.sUserId);
            if (!TextUtils.isEmpty(remoteFallbackJSON)) {
                JSONObject jSONObject = new JSONObject(remoteFallbackJSON);
                if ("OK".equalsIgnoreCase(jSONObject.getString("S"))) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("R");
                    String string = jSONObject2.getString("province");
                    String string2 = jSONObject2.getString("city");
                    String string3 = jSONObject2.getString("isp");
                    String string4 = jSONObject2.getString("ip");
                    String string5 = jSONObject2.getString("country");
                    JSONObject jSONObject3 = jSONObject2.getJSONObject(activeNetworkLabel);
                    if (activeNetworkLabel.equals(NetworkUtils.NETWORKTYPE_WAP)) {
                        activeNetworkLabel = getActiveNetworkLabel();
                    }
                    com.xiaomi.channel.commonutils.logger.b.a("get bucket: ip = " + string4 + " net = " + string3 + activeNetworkLabel + " hosts = " + jSONObject3.toString());
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        String str3 = arrayList.get(i3);
                        JSONArray jSONArrayOptJSONArray = jSONObject3.optJSONArray(str3);
                        if (jSONArrayOptJSONArray == null) {
                            com.xiaomi.channel.commonutils.logger.b.a("no bucket found for " + str3);
                        } else {
                            Fallback fallback = new Fallback(str3);
                            for (int i4 = 0; i4 < jSONArrayOptJSONArray.length(); i4++) {
                                String string6 = jSONArrayOptJSONArray.getString(i4);
                                if (!TextUtils.isEmpty(string6)) {
                                    fallback.a(new c(string6, jSONArrayOptJSONArray.length() - i4));
                                }
                            }
                            arrayList2.set(i3, fallback);
                            fallback.g = string5;
                            fallback.c = string;
                            fallback.e = string3;
                            fallback.f = string4;
                            fallback.d = string2;
                            if (jSONObject2.has("stat-percent")) {
                                fallback.a(jSONObject2.getDouble("stat-percent"));
                            }
                            if (jSONObject2.has("stat-domain")) {
                                fallback.b(jSONObject2.getString("stat-domain"));
                            }
                            if (jSONObject2.has("ttl")) {
                                fallback.a(((long) jSONObject2.getInt("ttl")) * 1000);
                            }
                            setCurrentISP(fallback.e());
                        }
                    }
                }
            }
            while (true) {
                int i5 = i;
                if (i5 >= arrayList.size()) {
                    persist();
                    return arrayList2;
                }
                Fallback fallback2 = arrayList2.get(i5);
                if (fallback2 != null) {
                    updateFallbacks(arrayList.get(i5), fallback2);
                }
                i = i5 + 1;
            }
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("failed to get bucket " + e.getMessage());
        }
        i = 0;
    }

    public static synchronized void setHostManagerFactory(HostManagerFactory hostManagerFactory) {
        factory = hostManagerFactory;
        sInstance = null;
    }

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
                    fromJSON(strLoadHosts);
                    com.xiaomi.channel.commonutils.logger.b.a("loading the new hosts succeed");
                    return true;
                }
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a("load host exception " + th.getMessage());
            }
            return false;
        }
    }

    public void clear() {
        synchronized (this.mHostsMapping) {
            this.mHostsMapping.clear();
        }
    }

    protected void fromJSON(String str) {
        synchronized (this.mHostsMapping) {
            this.mHostsMapping.clear();
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                Fallbacks fallbacksFromJSON = new Fallbacks().fromJSON(jSONArray.getJSONObject(i));
                this.mHostsMapping.put(fallbacksFromJSON.getHost(), fallbacksFromJSON);
            }
        }
    }

    public String getActiveNetworkLabel() {
        NetworkInfo activeNetworkInfo;
        String str;
        if (this.sAppContext == null) {
            return NetworkUtils.NETWORKTYPE_INVALID;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.sAppContext.getSystemService("connectivity");
            if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                str = NetworkUtils.NETWORKTYPE_INVALID;
            } else {
                if (activeNetworkInfo.getType() == 1) {
                    WifiManager wifiManager = (WifiManager) this.sAppContext.getSystemService(NetworkUtils.NETWORKTYPE_WIFI);
                    if (wifiManager != null && wifiManager.getConnectionInfo() != null) {
                        str = "WIFI-" + wifiManager.getConnectionInfo().getSSID();
                    }
                    return NetworkUtils.NETWORKTYPE_INVALID;
                }
                str = activeNetworkInfo.getTypeName() + "-" + activeNetworkInfo.getSubtypeName();
            }
            return str;
        } catch (Throwable th) {
        }
    }

    public Fallback getFallbacksByHost(String str) {
        return getFallbacksByHost(str, true);
    }

    public Fallback getFallbacksByHost(String str, boolean z) {
        Fallback fallbackRequestRemoteFallback;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the host is empty");
        }
        if (!this.sHostFilter.a(str)) {
            return null;
        }
        Fallback localFallback = getLocalFallback(str);
        if (localFallback == null || !localFallback.b()) {
            return (z && d.d(this.sAppContext) && (fallbackRequestRemoteFallback = requestRemoteFallback(str)) != null) ? fallbackRequestRemoteFallback : new b(this, str, localFallback);
        }
        return localFallback;
    }

    public Fallback getFallbacksByURL(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the url is empty");
        }
        return getFallbacksByHost(new URL(str).getHost(), true);
    }

    protected String getHost() {
        return "resolver.gslb.mi-idc.com";
    }

    protected Fallback getLocalFallback(String str) {
        Fallbacks fallbacks;
        Fallback fallback;
        synchronized (this.mHostsMapping) {
            checkHostMapping();
            fallbacks = this.mHostsMapping.get(str);
        }
        if (fallbacks == null || (fallback = fallbacks.getFallback()) == null) {
            return null;
        }
        return fallback;
    }

    protected String getProcessName() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.sAppContext.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == Process.myPid()) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        return "com.xiaomi";
    }

    /* JADX INFO: Removed unreachable split cross block B:23:0x0095 */
    protected String getRemoteFallbackJSON(ArrayList<String> arrayList, String str, String str2) throws IOException {
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<com.xiaomi.channel.commonutils.network.c> arrayList3 = new ArrayList();
        arrayList3.add(new com.xiaomi.channel.commonutils.network.a("type", str));
        arrayList3.add(new com.xiaomi.channel.commonutils.network.a("uuid", str2));
        arrayList3.add(new com.xiaomi.channel.commonutils.network.a("list", join(arrayList, ",")));
        Fallback localFallback = getLocalFallback("resolver.gslb.mi-idc.com");
        String str3 = String.format("http://%1$s/gslb/gslb/getbucket.asp?ver=3.0", "resolver.gslb.mi-idc.com");
        if (localFallback == null) {
            arrayList2.add(str3);
        } else {
            arrayList2 = localFallback.a(str3);
        }
        Iterator<String> it = arrayList2.iterator();
        IOException e = null;
        while (it.hasNext()) {
            Uri.Builder builderBuildUpon = Uri.parse(it.next()).buildUpon();
            for (com.xiaomi.channel.commonutils.network.c cVar : arrayList3) {
                builderBuildUpon.appendQueryParameter(cVar.a(), cVar.b());
            }
            try {
                return this.sHttpGetter == null ? d.a(this.sAppContext, new URL(builderBuildUpon.toString())) : this.sHttpGetter.a(builderBuildUpon.toString());
            } catch (IOException e2) {
                e = e2;
                com.xiaomi.channel.commonutils.logger.b.a("network ioErr: " + e.getMessage());
            }
        }
        if (e != null) {
            throw e;
        }
        return null;
    }

    protected String loadHosts() throws Throwable {
        Throwable th;
        BufferedReader bufferedReader;
        String string = null;
        try {
            try {
                File file = new File(this.sAppContext.getFilesDir(), getProcessName());
                if (file.isFile()) {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                        }
                        string = sb.toString();
                        com.xiaomi.channel.commonutils.file.a.a(bufferedReader);
                    } catch (Throwable th2) {
                        th = th2;
                        com.xiaomi.channel.commonutils.logger.b.a("load host exception " + th.getMessage());
                        com.xiaomi.channel.commonutils.file.a.a(bufferedReader);
                    }
                } else {
                    com.xiaomi.channel.commonutils.file.a.a((Reader) null);
                }
            } catch (Throwable th3) {
                th = th3;
                com.xiaomi.channel.commonutils.file.a.a((Reader) null);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            com.xiaomi.channel.commonutils.file.a.a((Reader) null);
            throw th;
        }
        return string;
    }

    public void persist() {
        purge();
        synchronized (this.mHostsMapping) {
            try {
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.sAppContext.openFileOutput(getProcessName(), 0)));
                    String string = toJSON().toString();
                    if (!TextUtils.isEmpty(string)) {
                        bufferedWriter.write(string);
                    }
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public void purge() {
        synchronized (this.mHostsMapping) {
            Iterator<Fallbacks> it = this.mHostsMapping.values().iterator();
            while (it.hasNext()) {
                it.next().purge(false);
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

    public void refreshFallbacks() {
        ArrayList<String> arrayList;
        synchronized (this.mHostsMapping) {
            checkHostMapping();
            arrayList = new ArrayList<>(this.mHostsMapping.keySet());
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Fallbacks fallbacks = this.mHostsMapping.get(arrayList.get(size));
                if (fallbacks != null && fallbacks.getFallback() != null) {
                    arrayList.remove(size);
                }
            }
        }
        ArrayList<Fallback> arrayListRequestRemoteFallbacks = requestRemoteFallbacks(arrayList);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= arrayList.size()) {
                return;
            }
            if (arrayListRequestRemoteFallbacks.get(i2) != null) {
                updateFallbacks(arrayList.get(i2), arrayListRequestRemoteFallbacks.get(i2));
            }
            i = i2 + 1;
        }
    }

    protected Fallback requestRemoteFallback(String str) {
        if (System.currentTimeMillis() - this.lastRemoteRequestTimestamp > this.remoteRequestFailureCount * 60 * 1000) {
            this.lastRemoteRequestTimestamp = System.currentTimeMillis();
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(str);
            Fallback fallback = requestRemoteFallbacks(arrayList).get(0);
            if (fallback != null) {
                this.remoteRequestFailureCount = 0L;
                return fallback;
            }
            if (this.remoteRequestFailureCount < 15) {
                this.remoteRequestFailureCount++;
            }
        }
        return null;
    }

    public void setCurrentISP(String str) {
        this.currentISP = str;
    }

    protected JSONArray toJSON() {
        JSONArray jSONArray;
        synchronized (this.mHostsMapping) {
            jSONArray = new JSONArray();
            Iterator<Fallbacks> it = this.mHostsMapping.values().iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next().toJSON());
            }
        }
        return jSONArray;
    }

    public void updateFallbacks(String str, Fallback fallback) {
        if (TextUtils.isEmpty(str) || fallback == null) {
            throw new IllegalArgumentException("the argument is invalid " + str + ", " + fallback);
        }
        if (this.sHostFilter.a(str)) {
            synchronized (this.mHostsMapping) {
                checkHostMapping();
                if (this.mHostsMapping.containsKey(str)) {
                    this.mHostsMapping.get(str).addFallback(fallback);
                } else {
                    Fallbacks fallbacks = new Fallbacks(str);
                    fallbacks.addFallback(fallback);
                    this.mHostsMapping.put(str, fallbacks);
                }
            }
        }
    }
}
