package com.ut.mini;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.mtl.log.e.i;
import com.ut.mini.base.UTMIVariables;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UTHybridHelper {
    private static UTHybridHelper a = new UTHybridHelper();

    public static UTHybridHelper getInstance() {
        return a;
    }

    public void setH5Url(String url) {
        if (url != null) {
            UTMIVariables.getInstance().setH5Url(url);
        }
    }

    public void h5UT(Map<String, String> dataMap, Object view) {
        if (dataMap == null || dataMap.size() == 0) {
            i.a("h5UT", "dataMap is empty");
            return;
        }
        String str = dataMap.get("functype");
        if (str == null) {
            i.a("h5UT", "funcType is null");
            return;
        }
        String str2 = dataMap.get("utjstype");
        if (str2 != null && !str2.equals("0") && !str2.equals("1")) {
            i.a("h5UT", "utjstype should be 1 or 0 or null");
            return;
        }
        dataMap.remove("functype");
        Date date = new Date();
        if (str.equals("2001")) {
            a(date, dataMap, view);
        } else if (str.equals("2101")) {
            a(date, dataMap);
        }
    }

    private void a(Date date, Map<String, String> map, Object obj) {
        Map<String, String> mapC;
        if (map != null && map.size() != 0) {
            String strB = b(map.get("urlpagename"), map.get("url"));
            if (strB == null || TextUtils.isEmpty(strB)) {
                i.a("h5Page", "pageName is null,return");
                return;
            }
            String refPage = UTMIVariables.getInstance().getRefPage();
            String str = map.get("utjstype");
            map.remove("utjstype");
            if (str == null || str.equals("0")) {
                mapC = c(map);
            } else {
                mapC = str.equals("1") ? d(map) : null;
            }
            int i = UTPageHitHelper.getInstance().a(obj) ? 2001 : 2006;
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(strB, i, refPage, null, null, mapC);
            if (2001 == i) {
                UTMIVariables.getInstance().setRefPage(strB);
            }
            Map<String, String> mapC2 = UTPageHitHelper.getInstance().c();
            if (mapC2 != null && mapC2.size() > 0) {
                uTOriginalCustomHitBuilder.setProperties(mapC2);
            }
            UTTracker defaultTracker = UTAnalytics.getInstance().getDefaultTracker();
            if (defaultTracker != null) {
                defaultTracker.send(uTOriginalCustomHitBuilder.build());
            } else {
                i.a("h5Page event error", "Fatal Error,must call setRequestAuthentication method first.");
            }
            UTPageHitHelper.getInstance().a(obj);
        }
    }

    private void a(Date date, Map<String, String> map) {
        Map<String, String> mapE;
        if (map != null && map.size() != 0) {
            String strB = b(map.get("urlpagename"), map.get("url"));
            if (strB == null || TextUtils.isEmpty(strB)) {
                i.a("h5Ctrl", new String[]{"pageName is null,return"});
                return;
            }
            String str = map.get("logkey");
            if (str == null || TextUtils.isEmpty(str)) {
                i.a("h5Ctrl", new String[]{"logkey is null,return"});
                return;
            }
            String str2 = map.get("utjstype");
            map.remove("utjstype");
            if (str2 == null || str2.equals("0")) {
                mapE = e(map);
            } else {
                mapE = str2.equals("1") ? f(map) : null;
            }
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(strB, 2101, str, null, null, mapE);
            UTTracker defaultTracker = UTAnalytics.getInstance().getDefaultTracker();
            if (defaultTracker != null) {
                defaultTracker.send(uTOriginalCustomHitBuilder.build());
            } else {
                i.a("h5Ctrl event error", "Fatal Error,must call setRequestAuthentication method first.");
            }
        }
    }

    private Map<String, String> c(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        HashMap map2 = new HashMap();
        String str = map.get("url");
        map2.put("_h5url", str == null ? "" : str);
        if (str != null) {
            Uri uri = Uri.parse(str);
            String queryParameter = uri.getQueryParameter("spm");
            if (queryParameter != null && !TextUtils.isEmpty(queryParameter)) {
                map2.put("spm", queryParameter);
            } else {
                map2.put("spm", "0.0.0.0");
            }
            String queryParameter2 = uri.getQueryParameter("scm");
            if (queryParameter2 != null && !TextUtils.isEmpty(queryParameter2)) {
                map2.put("scm", queryParameter2);
            }
        } else {
            map2.put("spm", "0.0.0.0");
        }
        String str2 = map.get("spmcnt");
        if (str2 == null) {
            str2 = "";
        }
        map2.put("_spmcnt", str2);
        String str3 = map.get("spmpre");
        if (str3 == null) {
            str3 = "";
        }
        map2.put("_spmpre", str3);
        String str4 = map.get("lzsid");
        if (str4 == null) {
            str4 = "";
        }
        map2.put("_lzsid", str4);
        String str5 = map.get("extendargs");
        if (str5 == null) {
            str5 = "";
        }
        map2.put("_h5ea", str5);
        String str6 = map.get("cna");
        if (str6 == null) {
            str6 = "";
        }
        map2.put("_cna", str6);
        map2.put("_ish5", "1");
        return map2;
    }

    private Map<String, String> d(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        HashMap map2 = new HashMap();
        String str = map.get("url");
        if (str == null) {
            str = "";
        }
        map2.put("_h5url", str);
        String str2 = map.get("extendargs");
        if (str2 == null) {
            str2 = "";
        }
        map2.put("_h5ea", str2);
        map2.put("_ish5", "1");
        return map2;
    }

    private Map<String, String> e(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        HashMap map2 = new HashMap();
        String str = map.get("logkeyargs");
        if (str == null) {
            str = "";
        }
        map2.put("_lka", str);
        String str2 = map.get("cna");
        if (str2 == null) {
            str2 = "";
        }
        map2.put("_cna", str2);
        String str3 = map.get("extendargs");
        if (str3 == null) {
            str3 = "";
        }
        map2.put("_h5ea", str3);
        map2.put("_ish5", "1");
        return map2;
    }

    private Map<String, String> f(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        HashMap map2 = new HashMap();
        String str = map.get("extendargs");
        if (str == null) {
            str = "";
        }
        map2.put("_h5ea", str);
        map2.put("_ish5", "1");
        return map2;
    }

    private String b(String str, String str2) {
        if (str == null || TextUtils.isEmpty(str)) {
            if (TextUtils.isEmpty(str2)) {
                return "";
            }
            int iIndexOf = str2.indexOf("?");
            return iIndexOf == -1 ? str2 : str2.substring(0, iIndexOf);
        }
        return str;
    }
}
