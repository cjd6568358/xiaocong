package com.alibaba.mtl.log.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.AppMonitorDelegate;
import com.alibaba.mtl.appmonitor.d.j;
import com.alibaba.mtl.log.e.i;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Config.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static String N;
    private static boolean a;
    private static long c = 300000;
    private static long d = 30000;
    public static String L = "http://m-adash.m.taobao.com/rest/sur";
    public static String M = "https://adashbc.ut.taobao.com/rest/sur";
    public static boolean z = false;
    public static volatile boolean A = false;
    private static boolean B = false;
    private static boolean C = false;
    static List<String> f = new ArrayList();
    static List<String> g = new ArrayList();
    static List<String> h = new ArrayList();
    static List<String> i = new ArrayList();
    static List<String> j = new ArrayList();
    static List<String> k = new ArrayList();
    private static String O = "net";

    static {
        g.add("stm_bcx");
        h.add("stm_x");
        i.add("stm_p");
        j.add("stm_c");
        f.add("stm_d");
        k.add("stm_nc");
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static List<String> m17a(String str) {
        int i2;
        try {
            i2 = Integer.parseInt(str);
        } catch (Exception e) {
            i2 = -1;
        }
        if (i2 == -1) {
            return f;
        }
        if (i2 == 1 || i2 == 61005 || i2 == 61006 || i2 == 65501 || i2 == 65502 || i2 == 65503 || i2 == 65133) {
            return g;
        }
        if ((i2 > 1 && i2 < 100) || ((i2 > 15300 && i2 < 15400) || ((i2 > 21000 && i2 < 30000) || ((i2 > 61000 && i2 < 61200) || (i2 > 65000 && i2 < 65300))))) {
            return h;
        }
        if (i2 > 1000 && i2 < 2100) {
            return i;
        }
        if (i2 > 2100 && i2 < 2200) {
            return j;
        }
        if (i2 == 19999 || ((i2 > 4000 && i2 < 10000) || ((i2 > 64000 && i2 < 65000) || ((i2 > 65500 && i2 < 65600) || (i2 > 66000 && i2 < 67000))))) {
            return f;
        }
        return k;
    }

    public static void turnOnRealTimeDebug(Map<String, String> params) {
        i.a("Config", "[turnOnRealTimeDebug] params:", params);
        if (params != null) {
            String str = params.get("debug_api_url");
            String str2 = params.get("debug_key");
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                M = str;
                z = true;
                N = str2;
            }
            if (params.containsKey("debug_sampling_option")) {
                AppMonitorDelegate.IS_DEBUG = true;
                com.alibaba.mtl.log.a.n();
            }
        }
    }

    public static boolean e() {
        return z;
    }

    /* JADX INFO: renamed from: f, reason: collision with other method in class */
    public static boolean m19f() {
        return !A;
    }

    public static long a() {
        return d;
    }

    public static long b() {
        return c;
    }

    private static int b(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            return Integer.valueOf(str).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    public static void f(String str) {
        int iB;
        if (!C && (iB = b(str)) != -1) {
            c = iB * 1000;
        }
    }

    public static void g(String str) {
        int iB;
        if (!B && (iB = b(str)) != -1) {
            d = iB * 1000;
        }
    }

    public static void h(String str) {
        JSONObject jSONObject;
        Iterator<String> itKeys;
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(com.alibaba.mtl.log.a.getContext()).edit();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                if (jSONObject2 != null && (jSONObject = jSONObject2.getJSONObject("config")) != null && (itKeys = jSONObject.keys()) != null) {
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        JSONObject jSONObject3 = jSONObject.getJSONObject(next);
                        if (jSONObject3 != null) {
                            String str2 = jSONObject3.remove("timestamp") + Constants.MAIN_VERSION_TAG;
                            editorEdit.putString("ut_" + next + "_config", jSONObject3.toString());
                            editorEdit.putString("ut_" + next + "_timestamp", str2);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editorEdit.apply();
    }

    public static String f() {
        JSONObject jSONObject;
        if ("l0".equalsIgnoreCase(O)) {
            i.a("Config", O, "l0 config");
            return "{\"SYSTEM\":{\"discardap\":\"1\"}}";
        }
        if ("l1".equalsIgnoreCase(O)) {
            i.a("Config", O, "l1 config");
            return "{\"SYSTEM\":{\"useut\":\"0\",\"bg_interval\":600000,\"fg_interval\":60000},\"STAT\":{\"sampling\":0,\"metrics\":[{\"module\":\"networkPrefer\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"network\",\"sampling\":10},{\"monitorPoint\":\"NetworkError\",\"sampling\":10000}]},{\"module\":\"mtopsdk\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"mtopStats\",\"sampling\":10},{\"monitorPoint\":\"mtopExceptions\",\"sampling\":10},{\"monitorPoint\":\"uploadStats\",\"sampling\":10000}]},{\"module\":\"ImageLib\",\"sampling\":10000,\"monitorPoints\":[{\"monitorPoint\":\"ImageFlow\",\"sampling\":50},{\"monitorPoint\":\"ImageExceptions\",\"sampling\":50}]},{\"module\":\"accs\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"netperformance\",\"sampling\":10}]},{\"module\":\"ACDS\",\"sampling\":1000,\"monitorPoints\":[{\"monitorPoint\":\"initdata\",\"sampling\":20}]},{\"module\":\"WindVane\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"AllPackageApps\",\"sampling\":10000},{\"monitorPoint\":\"Config\",\"sampling\":5000},{\"monitorPoint\":\"PackageApp\",\"sampling\":2000},{\"monitorPoint\":\"H5\",\"sampling\":1},{\"monitorPoint\":\"WebViewVisit\",\"sampling\":5000}]},{\"module\":\"order\",\"sampling\":10000,\"monitorPoints\":[{\"monitorPoint\":\"Page_OrderList\",\"sampling\":500},{\"monitorPoint\":\"Page_OrderDetail\",\"sampling\":1000}]},{\"module\":\"wopc\",\"sampling\":10000},{\"module\":\"WeApp+\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"load\",\"sampling\":10000}]},{\"module\":\"NetworkSDK\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"TrafficStats\",\"sampling\":20}]}]},\"COUNTER\":{\"sampling\":0,\"cacheCount\":60,\"metrics\":[{\"module\":\"accs\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"to_buss\",\"sampling\":1},{\"monitorPoint\":\"resend\",\"sampling\":1}]},{\"module\":\"configCenter\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"SyncConfigsFromApi\",\"sampling\":2500},{\"monitorPoint\":\"SynconfigUpdateConfig\",\"sampling\":1},{\"monitorPoint\":\"NotifyConfigChanged\",\"sampling\":1},{\"monitorPoint\":\"RegisterToken\",\"sampling\":1},{\"monitorPoint\":\"SyncReferFromCdn\",\"sampling\":2500},{\"monitorPoint\":\"XcmdUpdateConfig\",\"sampling\":1},{\"monitorPoint\":\"SyncXcommandFromCdn\",\"sampling\":2500},{\"monitorPoint\":\"SaveConfigError\",\"sampling\":2500},{\"monitorPoint\":\"ParseTokenError\",\"sampling\":2500},{\"monitorPoint\":\"NotifyLoadConfig\",\"sampling\":2500}]},{\"module\":\"ACDS\",\"sampling\":250},{\"module\":\"Page_hotpatch\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hotpatch\",\"sampling\":250}]}]},\"ALARM\":{\"sampling\":0,\"successSampling\":0,\"failSampling\":0,\"metrics\":[{\"module\":\"Page_Home\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"HomeItemLoad\",\"sampling\":2500},{\"monitorPoint\":\"HomeNetwork\",\"sampling\":2500}]},{\"module\":\"Page_Detail\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"LoadDetail\",\"sampling\":2500},{\"monitorPoint\":\"LoadDesc\",\"sampling\":2500}]},{\"module\":\"Page_SearchItemList\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"SearchItem\",\"sampling\":2000}]},{\"module\":\"Page_Shop\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"loadShop\",\"sampling\":2500}]},{\"module\":\"Page_AutoLogin\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"autoLogin\",\"sampling\":2500}]},{\"module\":\"Page_Shop_Search_List\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"searchShopList\",\"sampling\":2500}]},{\"module\":\"Page_ShoppingCart\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"QueryData\",\"sampling\":2500},{\"monitorPoint\":\"ShowCart\",\"sampling\":2500},{\"monitorPoint\":\"UpdateCart\",\"sampling\":2500}]},{\"module\":\"Page_Pay\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"Alipay\",\"sampling\":2500}]},{\"module\":\"Page_ShowOrder\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"ShowOrder\",\"sampling\":2500},{\"monitorPoint\":\"CreateOrder\",\"sampling\":2500}]},{\"module\":\"Page_Attention\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"loadAttention\",\"sampling\":2500},{\"monitorPoint\":\"refreshAttention\",\"sampling\":2500}]},{\"module\":\"Page_SearchShopList\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"SearchShop\",\"sampling\":2500}]},{\"module\":\"WindVane\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"NativeError\",\"sampling\":1250},{\"monitorPoint\":\"AllPackageApps\",\"sampling\":1250},{\"monitorPoint\":\"Config\",\"sampling\":1250},{\"monitorPoint\":\"JavaScriptError\",\"sampling\":1250},{\"monitorPoint\":\"PackageApp\",\"sampling\":2000}]},{\"module\":\"WeApp+\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"domModule\",\"sampling\":2500},{\"monitorPoint\":\"enviroment\",\"sampling\":2500},{\"monitorPoint\":\"jsBridge\",\"sampling\":2500},{\"monitorPoint\":\"streamModule\",\"sampling\":2500}]},{\"module\":\"ACDS\",\"sampling\":250,\"monitorPoints\":[{\"monitorPoint\":\"huichang\",\"sampling\":0}]},{\"module\":\"Page_HuiChangTab\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hcconfig\",\"sampling\":250}]},{\"module\":\"Page_2015MainStage\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hcjs_cdn\",\"sampling\":250},{\"monitorPoint\":\"hcjs_mtop\",\"sampling\":250},{\"monitorPoint\":\"hcmaindegrade\",\"sampling\":2500}]},{\"module\":\"Page_2015SubStage\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hcjs_cdn\",\"sampling\":250},{\"monitorPoint\":\"hcjs_mtop\",\"sampling\":250},{\"monitorPoint\":\"hcsubdegrade\",\"sampling\":2500}]}]}}";
        }
        if ("l2".equalsIgnoreCase(O)) {
            i.a("Config", O, "l2 config");
            return "{\"SYSTEM\":{\"useut\":\"0\",\"bg_interval\":600000,\"fg_interval\":60000},\"STAT\":{\"sampling\":0,\"metrics\":[{\"module\":\"networkPrefer\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"network\",\"sampling\":10},{\"monitorPoint\":\"NetworkError\",\"sampling\":10000}]},{\"module\":\"mtopsdk\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"mtopStats\",\"sampling\":10},{\"monitorPoint\":\"mtopExceptions\",\"sampling\":10},{\"monitorPoint\":\"uploadStats\",\"sampling\":10000}]},{\"module\":\"ImageLib\",\"sampling\":10000,\"monitorPoints\":[{\"monitorPoint\":\"ImageFlow\",\"sampling\":50},{\"monitorPoint\":\"ImageExceptions\",\"sampling\":50}]},{\"module\":\"accs\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"netperformance\",\"sampling\":10}]},{\"module\":\"ACDS\",\"sampling\":1000,\"monitorPoints\":[{\"monitorPoint\":\"initdata\",\"sampling\":20}]},{\"module\":\"WindVane\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"AllPackageApps\",\"sampling\":10000},{\"monitorPoint\":\"Config\",\"sampling\":5000},{\"monitorPoint\":\"PackageApp\",\"sampling\":2000},{\"monitorPoint\":\"H5\",\"sampling\":1},{\"monitorPoint\":\"WebViewVisit\",\"sampling\":5000}]},{\"module\":\"order\",\"sampling\":10000,\"monitorPoints\":[{\"monitorPoint\":\"Page_OrderList\",\"sampling\":500},{\"monitorPoint\":\"Page_OrderDetail\",\"sampling\":1000}]},{\"module\":\"wopc\",\"sampling\":10000},{\"module\":\"WeApp+\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"load\",\"sampling\":10000}]},{\"module\":\"NetworkSDK\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"TrafficStats\",\"sampling\":20}]}]},\"COUNTER\":{\"sampling\":0,\"cacheCount\":60,\"metrics\":[{\"module\":\"accs\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"to_buss\",\"sampling\":1},{\"monitorPoint\":\"resend\",\"sampling\":1}]},{\"module\":\"configCenter\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"SyncConfigsFromApi\",\"sampling\":5000},{\"monitorPoint\":\"SynconfigUpdateConfig\",\"sampling\":1},{\"monitorPoint\":\"NotifyConfigChanged\",\"sampling\":1},{\"monitorPoint\":\"RegisterToken\",\"sampling\":1},{\"monitorPoint\":\"SyncReferFromCdn\",\"sampling\":5000},{\"monitorPoint\":\"XcmdUpdateConfig\",\"sampling\":1},{\"monitorPoint\":\"SyncXcommandFromCdn\",\"sampling\":5000},{\"monitorPoint\":\"SaveConfigError\",\"sampling\":5000},{\"monitorPoint\":\"ParseTokenError\",\"sampling\":5000},{\"monitorPoint\":\"NotifyLoadConfig\",\"sampling\":5000}]},{\"module\":\"ACDS\",\"sampling\":500},{\"module\":\"Page_hotpatch\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hotpatch\",\"sampling\":500}]}]},\"ALARM\":{\"sampling\":0,\"successSampling\":0,\"failSampling\":0,\"metrics\":[{\"module\":\"Page_Home\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"HomeItemLoad\",\"sampling\":5000},{\"monitorPoint\":\"HomeNetwork\",\"sampling\":5000}]},{\"module\":\"Page_Detail\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"LoadDetail\",\"sampling\":5000},{\"monitorPoint\":\"LoadDesc\",\"sampling\":5000}]},{\"module\":\"Page_SearchItemList\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"SearchItem\",\"sampling\":5000}]},{\"module\":\"Page_Shop\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"loadShop\",\"sampling\":5000}]},{\"module\":\"Page_AutoLogin\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"autoLogin\",\"sampling\":5000}]},{\"module\":\"Page_Shop_Search_List\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"searchShopList\",\"sampling\":5000}]},{\"module\":\"Page_ShoppingCart\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"QueryData\",\"sampling\":5000},{\"monitorPoint\":\"ShowCart\",\"sampling\":5000},{\"monitorPoint\":\"UpdateCart\",\"sampling\":5000}]},{\"module\":\"Page_Pay\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"Alipay\",\"sampling\":5000}]},{\"module\":\"Page_ShowOrder\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"ShowOrder\",\"sampling\":5000},{\"monitorPoint\":\"CreateOrder\",\"sampling\":5000}]},{\"module\":\"Page_Attention\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"loadAttention\",\"sampling\":5000},{\"monitorPoint\":\"refreshAttention\",\"sampling\":5000}]},{\"module\":\"Page_SearchShopList\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"SearchShop\",\"sampling\":5000}]},{\"module\":\"WindVane\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"NativeError\",\"sampling\":2500},{\"monitorPoint\":\"AllPackageApps\",\"sampling\":2500},{\"monitorPoint\":\"Config\",\"sampling\":2500},{\"monitorPoint\":\"JavaScriptError\",\"sampling\":2500},{\"monitorPoint\":\"PackageApp\",\"sampling\":2000}]},{\"module\":\"WeApp+\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"domModule\",\"sampling\":5000},{\"monitorPoint\":\"enviroment\",\"sampling\":5000},{\"monitorPoint\":\"jsBridge\",\"sampling\":5000},{\"monitorPoint\":\"streamModule\",\"sampling\":5000}]},{\"module\":\"ACDS\",\"sampling\":500,\"monitorPoints\":[{\"monitorPoint\":\"huichang\",\"sampling\":0}]},{\"module\":\"Page_HuiChangTab\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hcconfig\",\"sampling\":500}]},{\"module\":\"Page_2015MainStage\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hcjs_cdn\",\"sampling\":500},{\"monitorPoint\":\"hcjs_mtop\",\"sampling\":500},{\"monitorPoint\":\"hcmaindegrade\",\"sampling\":5000}]},{\"module\":\"Page_2015SubStage\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hcjs_cdn\",\"sampling\":500},{\"monitorPoint\":\"hcjs_mtop\",\"sampling\":500},{\"monitorPoint\":\"hcsubdegrade\",\"sampling\":5000}]}]}}";
        }
        if ("l3".equalsIgnoreCase(O)) {
            i.a("Config", O, "l3 config");
            return "{\"SYSTEM\":{\"useut\":\"0\",\"bg_interval\":600000,\"fg_interval\":60000},\"STAT\":{\"sampling\":0,\"metrics\":[{\"module\":\"networkPrefer\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"network\",\"sampling\":10},{\"monitorPoint\":\"NetworkError\",\"sampling\":10000}]},{\"module\":\"mtopsdk\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"mtopStats\",\"sampling\":10},{\"monitorPoint\":\"mtopExceptions\",\"sampling\":10},{\"monitorPoint\":\"uploadStats\",\"sampling\":10000}]},{\"module\":\"ImageLib\",\"sampling\":10000,\"monitorPoints\":[{\"monitorPoint\":\"ImageFlow\",\"sampling\":50},{\"monitorPoint\":\"ImageExceptions\",\"sampling\":50}]},{\"module\":\"accs\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"netperformance\",\"sampling\":10}]},{\"module\":\"ACDS\",\"sampling\":1000,\"monitorPoints\":[{\"monitorPoint\":\"initdata\",\"sampling\":20}]},{\"module\":\"WindVane\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"AllPackageApps\",\"sampling\":10000},{\"monitorPoint\":\"Config\",\"sampling\":5000},{\"monitorPoint\":\"PackageApp\",\"sampling\":2000},{\"monitorPoint\":\"H5\",\"sampling\":1},{\"monitorPoint\":\"WebViewVisit\",\"sampling\":5000}]},{\"module\":\"order\",\"sampling\":10000,\"monitorPoints\":[{\"monitorPoint\":\"Page_OrderList\",\"sampling\":500},{\"monitorPoint\":\"Page_OrderDetail\",\"sampling\":1000}]},{\"module\":\"wopc\",\"sampling\":10000},{\"module\":\"WeApp+\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"load\",\"sampling\":10000}]},{\"module\":\"NetworkSDK\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"TrafficStats\",\"sampling\":20}]}]},\"COUNTER\":{\"sampling\":0,\"cacheCount\":60,\"metrics\":[{\"module\":\"accs\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"to_buss\",\"sampling\":1},{\"monitorPoint\":\"resend\",\"sampling\":1}]},{\"module\":\"configCenter\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"SyncConfigsFromApi\",\"sampling\":10000},{\"monitorPoint\":\"SynconfigUpdateConfig\",\"sampling\":1},{\"monitorPoint\":\"NotifyConfigChanged\",\"sampling\":1},{\"monitorPoint\":\"RegisterToken\",\"sampling\":1},{\"monitorPoint\":\"SyncReferFromCdn\",\"sampling\":10000},{\"monitorPoint\":\"XcmdUpdateConfig\",\"sampling\":1},{\"monitorPoint\":\"SyncXcommandFromCdn\",\"sampling\":10000},{\"monitorPoint\":\"SaveConfigError\",\"sampling\":10000},{\"monitorPoint\":\"ParseTokenError\",\"sampling\":10000},{\"monitorPoint\":\"NotifyLoadConfig\",\"sampling\":10000}]},{\"module\":\"ACDS\",\"sampling\":1000},{\"module\":\"Page_hotpatch\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hotpatch\",\"sampling\":1000}]}]},\"ALARM\":{\"sampling\":0,\"successSampling\":0,\"failSampling\":0,\"metrics\":[{\"module\":\"Page_Home\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"HomeItemLoad\",\"sampling\":10000},{\"monitorPoint\":\"HomeNetwork\",\"sampling\":10000}]},{\"module\":\"Page_Detail\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"LoadDetail\",\"sampling\":10000},{\"monitorPoint\":\"LoadDesc\",\"sampling\":10000}]},{\"module\":\"Page_SearchItemList\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"SearchItem\",\"sampling\":10000}]},{\"module\":\"Page_Shop\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"loadShop\",\"sampling\":10000}]},{\"module\":\"Page_AutoLogin\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"autoLogin\",\"sampling\":10000}]},{\"module\":\"Page_Shop_Search_List\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"searchShopList\",\"sampling\":10000}]},{\"module\":\"Page_ShoppingCart\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"QueryData\",\"sampling\":10000},{\"monitorPoint\":\"ShowCart\",\"sampling\":10000},{\"monitorPoint\":\"UpdateCart\",\"sampling\":10000}]},{\"module\":\"Page_Pay\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"Alipay\",\"sampling\":10000}]},{\"module\":\"Page_ShowOrder\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"ShowOrder\",\"sampling\":10000},{\"monitorPoint\":\"CreateOrder\",\"sampling\":10000}]},{\"module\":\"Page_Attention\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"loadAttention\",\"sampling\":10000},{\"monitorPoint\":\"refreshAttention\",\"sampling\":10000}]},{\"module\":\"Page_SearchShopList\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"SearchShop\",\"sampling\":10000}]},{\"module\":\"WindVane\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"NativeError\",\"sampling\":5000},{\"monitorPoint\":\"AllPackageApps\",\"sampling\":5000},{\"monitorPoint\":\"Config\",\"sampling\":5000},{\"monitorPoint\":\"JavaScriptError\",\"sampling\":5000},{\"monitorPoint\":\"PackageApp\",\"sampling\":2000}]},{\"module\":\"WeApp+\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"domModule\",\"sampling\":10000},{\"monitorPoint\":\"enviroment\",\"sampling\":10000},{\"monitorPoint\":\"jsBridge\",\"sampling\":10000},{\"monitorPoint\":\"streamModule\",\"sampling\":10000}]},{\"module\":\"ACDS\",\"sampling\":1000,\"monitorPoints\":[{\"monitorPoint\":\"huichang\",\"sampling\":0}]},{\"module\":\"Page_HuiChangTab\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hcconfig\",\"sampling\":1000}]},{\"module\":\"Page_2015MainStage\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hcjs_cdn\",\"sampling\":1000},{\"monitorPoint\":\"hcjs_mtop\",\"sampling\":1000},{\"monitorPoint\":\"hcmaindegrade\",\"sampling\":10000}]},{\"module\":\"Page_2015SubStage\",\"sampling\":0,\"monitorPoints\":[{\"monitorPoint\":\"hcjs_cdn\",\"sampling\":1000},{\"monitorPoint\":\"hcjs_mtop\",\"sampling\":1000},{\"monitorPoint\":\"hcsubdegrade\",\"sampling\":10000}]}]}}";
        }
        i.a("Config", O, "net config");
        String strA = a("b01na");
        if (strA != null) {
            try {
                JSONObject jSONObject2 = new JSONObject(strA);
                if (jSONObject2 != null && (jSONObject = jSONObject2.getJSONObject("content")) != null) {
                    return jSONObject.getString("c_content");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void q() {
        try {
            String strF = f();
            j.a().b(strF);
            e.i(strF);
            d.a().b(a("b01n15"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String a(String str) {
        return PreferenceManager.getDefaultSharedPreferences(com.alibaba.mtl.log.a.getContext()).getString("ut_" + str + "_config", null);
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    public static String m18b(String str) {
        return PreferenceManager.getDefaultSharedPreferences(com.alibaba.mtl.log.a.getContext()).getString("ut_" + str + "_timestamp", PushConstants.PUSH_TYPE_NOTIFY);
    }

    public static synchronized void init(Context c2) {
        if (!a) {
            a = true;
            try {
                q();
                j.a().init(c2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            b.a().r();
        }
    }
}
