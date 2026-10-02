package com.ut.mini;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.mtl.log.a;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.p;
import com.alibaba.mtl.log.model.LogField;
import com.ut.mini.base.UTMIVariables;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UTTracker {
    private static Pattern a = Pattern.compile("(\\|\\||[\t\r\n])+");
    private String g;
    private String al = null;
    private Map<String, String> E = new HashMap();

    void p(String str) {
        this.al = str;
    }

    public synchronized void setGlobalProperty(String aKey, String aValue) {
        if (!TextUtils.isEmpty(aKey) && aValue != null) {
            this.E.put(aKey, aValue);
        } else {
            i.a("setGlobalProperty", "key is null or key is empty or value is null,please check it!");
        }
    }

    public synchronized String getGlobalProperty(String aKey) {
        return aKey != null ? this.E.get(aKey) : null;
    }

    public synchronized void removeGlobalProperty(String aKey) {
        if (aKey != null) {
            if (this.E.containsKey(aKey)) {
                this.E.remove(aKey);
            }
        }
    }

    private static String f(String str) {
        if (str != null && !"".equals(str)) {
            return a.matcher(str).replaceAll("");
        }
        return str;
    }

    private static String d(String str) {
        return f(str);
    }

    private Map<String, String> g(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        HashMap map2 = new HashMap();
        Iterator<String> it = map.keySet().iterator();
        if (it != null) {
            while (it.hasNext()) {
                String next = it.next();
                if (next != null) {
                    map2.put(next, d(map.get(next)));
                }
            }
        }
        return map2;
    }

    public void send(Map<String, String> aLogMap) {
        if (aLogMap != null) {
            HashMap map = new HashMap();
            map.putAll(this.E);
            map.putAll(aLogMap);
            if (!TextUtils.isEmpty(this.g)) {
                map.put(LogField.APPKEY.toString(), this.g);
            }
            Map<String, String> mapG = g((Map<String, String>) map);
            if (!TextUtils.isEmpty(this.al)) {
                mapG.put(UTFields.TRACK_ID, this.al);
            }
            if (UTMIVariables.getInstance().isAliyunOSPlatform()) {
            }
            f(mapG);
            d(mapG);
            m2g(mapG);
            h(mapG);
            a.a(mapG.remove(LogField.PAGE.toString()), mapG.remove(LogField.EVENTID.toString()), mapG.remove(LogField.ARG1.toString()), mapG.remove(LogField.ARG2.toString()), mapG.remove(LogField.ARG3.toString()), mapG);
        }
    }

    private static void f(Map<String, String> map) {
        if (map != null) {
            if (map.containsKey(LogField.IMEI.toString())) {
                map.remove(LogField.IMEI.toString());
            }
            if (map.containsKey(LogField.IMSI.toString())) {
                map.remove(LogField.IMSI.toString());
            }
            if (map.containsKey(LogField.CARRIER.toString())) {
                map.remove(LogField.CARRIER.toString());
            }
            if (map.containsKey(LogField.ACCESS.toString())) {
                map.remove(LogField.ACCESS.toString());
            }
            if (map.containsKey(LogField.ACCESS_SUBTYPE.toString())) {
                map.remove(LogField.ACCESS_SUBTYPE.toString());
            }
            if (map.containsKey(LogField.CHANNEL.toString())) {
                map.remove(LogField.CHANNEL.toString());
            }
            if (map.containsKey(LogField.LL_USERNICK.toString())) {
                map.remove(LogField.LL_USERNICK.toString());
            }
            if (map.containsKey(LogField.USERNICK.toString())) {
                map.remove(LogField.USERNICK.toString());
            }
            if (map.containsKey(LogField.LL_USERID.toString())) {
                map.remove(LogField.LL_USERID.toString());
            }
            if (map.containsKey(LogField.USERID.toString())) {
                map.remove(LogField.USERID.toString());
            }
            if (map.containsKey(LogField.SDKVERSION.toString())) {
                map.remove(LogField.SDKVERSION.toString());
            }
            if (map.containsKey(LogField.START_SESSION_TIMESTAMP.toString())) {
                map.remove(LogField.START_SESSION_TIMESTAMP.toString());
            }
            if (map.containsKey(LogField.UTDID.toString())) {
                map.remove(LogField.UTDID.toString());
            }
            if (map.containsKey(LogField.SDKTYPE.toString())) {
                map.remove(LogField.SDKTYPE.toString());
            }
            if (map.containsKey(LogField.RESERVE2.toString())) {
                map.remove(LogField.RESERVE2.toString());
            }
            if (map.containsKey(LogField.RESERVE3.toString())) {
                map.remove(LogField.RESERVE3.toString());
            }
            if (map.containsKey(LogField.RESERVE4.toString())) {
                map.remove(LogField.RESERVE4.toString());
            }
            if (map.containsKey(LogField.RESERVE5.toString())) {
                map.remove(LogField.RESERVE5.toString());
            }
            if (map.containsKey(LogField.RESERVES.toString())) {
                map.remove(LogField.RESERVES.toString());
            }
            if (map.containsKey(LogField.RECORD_TIMESTAMP.toString())) {
                map.remove(LogField.RECORD_TIMESTAMP.toString());
            }
        }
    }

    private static void d(Map<String, String> map) {
        if (map != null) {
            if (map.containsKey(UTFields.OS)) {
                String str = map.get(UTFields.OS);
                map.remove(UTFields.OS);
                map.put(LogField.OS.toString(), str);
            }
            if (map.containsKey(UTFields.OS_VERSION)) {
                String str2 = map.get(UTFields.OS_VERSION);
                map.remove(UTFields.OS_VERSION);
                map.put(LogField.OSVERSION.toString(), str2);
            }
        }
    }

    /* JADX INFO: renamed from: g, reason: collision with other method in class */
    private static void m2g(Map<String, String> map) {
        map.put(LogField.SDKTYPE.toString(), "mini");
    }

    private static void h(Map<String, String> map) {
        HashMap map2 = new HashMap();
        if (map.containsKey(UTFields.TRACK_ID)) {
            String str = map.get(UTFields.TRACK_ID);
            map.remove(UTFields.TRACK_ID);
            if (!TextUtils.isEmpty(str)) {
                map2.put("_tkid", str);
            }
        }
        if (map2.size() > 0) {
            map.put(LogField.RESERVES.toString(), p.d(map2));
        }
        if (!map.containsKey(LogField.PAGE.toString())) {
            map.put(LogField.PAGE.toString(), "UT");
        }
    }

    public void pageAppear(Object aPageObject) {
        UTPageHitHelper.getInstance().pageAppear(aPageObject);
    }

    public void pageAppearDonotSkip(Object aPageObject) {
        UTPageHitHelper.getInstance().a(aPageObject, (String) null, true);
    }

    public void pageAppearDonotSkip(Object aPageObject, String aCustomPageName) {
        UTPageHitHelper.getInstance().a(aPageObject, aCustomPageName, true);
    }

    public void pageAppear(Object aPageObject, String aCustomPageName) {
        UTPageHitHelper.getInstance().pageAppear(aPageObject, aCustomPageName);
    }

    public void pageDisAppear(Object aPageObject) {
        UTPageHitHelper.getInstance().pageDisAppear(aPageObject);
    }

    public void updateNextPageProperties(Map<String, String> aProperties) {
        UTPageHitHelper.getInstance().updateNextPageProperties(aProperties);
    }

    public void updatePageName(Object aPageObject, String aPageName) {
        UTPageHitHelper.getInstance().updatePageName(aPageObject, aPageName);
    }

    public void updatePageProperties(Object aPageObject, Map<String, String> aProperties) {
        UTPageHitHelper.getInstance().updatePageProperties(aPageObject, aProperties);
    }

    public void updatePageStatus(Object aPageObject, UTPageStatus aPageStatus) {
        UTPageHitHelper.getInstance().updatePageStatus(aPageObject, aPageStatus);
    }

    public void updatePageUrl(Object aPageObject, Uri aUri) {
        UTPageHitHelper.getInstance().updatePageUrl(aPageObject, aUri);
    }

    public void skipPage(Object aPageObject) {
        UTPageHitHelper.getInstance().skipPage(aPageObject);
    }

    protected void q(String str) {
        this.g = str;
    }
}
