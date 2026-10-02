package com.xiaocong.smarthome.network.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.text.TextUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@SuppressLint({"DefaultLocale"})
public class HttpClientUtil {
    private static final String CMWAP = "cmwap";
    private static final String CTWAP = "ctwap";
    private static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
    private static final String TAG = "HttpClientUtil";
    private static final int TYPE_CM_CU_WAP = 1;
    private static final int TYPE_CT_WAP = 2;
    private static final int TYPE_NET_WORK_DISABLED = 0;
    private static final int TYPE_OTHER_NET = 3;
    private static final String UNIWAP = "uniwap";
    private static final String _3GWAP = "3gwap";

    public static void setProxy(Context context, HttpClient httpClient) {
        int networkType = checkNetworkType(context);
        switch (networkType) {
            case 1:
                String host = Proxy.getDefaultHost();
                if (host == null) {
                    host = "10.0.0.172";
                }
                Proxy.getPort(context);
                HttpHost httpHost = new HttpHost(host, 80);
                httpClient.getParams().setParameter("http.route.default-proxy", httpHost);
                XCHttpLog.e("联通网络");
                break;
            case 2:
                String host2 = Proxy.getDefaultHost();
                if (host2 == null) {
                    host2 = "10.0.0.200";
                }
                int port = Proxy.getPort(context);
                HttpHost httpHost2 = new HttpHost(host2, port);
                httpClient.getParams().setParameter("http.route.default-proxy", httpHost2);
                XCHttpLog.e("电信网络");
                break;
            case 3:
                XCHttpLog.e("wifi和运营商net网络");
                break;
        }
    }

    public static int checkNetworkType(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isAvailable()) {
                return 0;
            }
            int type = networkInfo.getType();
            if (type != 1 && type == 0) {
                Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                    String user = c.getString(c.getColumnIndex("user"));
                    if (!TextUtils.isEmpty(user) && user.startsWith(CTWAP)) {
                        return 2;
                    }
                }
                c.close();
                String extraInfo = networkInfo.getExtraInfo();
                if (extraInfo != null) {
                    String extraInfo2 = extraInfo.toLowerCase();
                    if (extraInfo2.equals(CMWAP) || extraInfo2.equals(_3GWAP) || extraInfo2.equals(UNIWAP)) {
                        return 1;
                    }
                }
            }
            return 3;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
