package com.ut.mini;

import android.app.Activity;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.mtl.log.c;
import com.alibaba.mtl.log.e.i;
import com.ut.mini.base.UTMIVariables;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UTPageHitHelper {
    private static UTPageHitHelper a = new UTPageHitHelper();
    private boolean N = false;
    private Map<String, String> A = new HashMap();
    private Map<String, UTPageEventObject> B = new HashMap();
    private String ag = null;
    private Map<String, String> C = new HashMap();
    private String ah = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Queue<UTPageEventObject> f127a = new LinkedList();
    private Map<Object, String> D = new HashMap();

    public static UTPageHitHelper getInstance() {
        return a;
    }

    synchronized Map<String, String> c() {
        HashMap map;
        if (this.C == null || this.C.size() <= 0) {
            map = null;
        } else {
            map = new HashMap();
            map.putAll(this.C);
            this.C.clear();
        }
        return map;
    }

    synchronized void a(UTPageEventObject uTPageEventObject) {
        uTPageEventObject.resetPropertiesWithoutSkipFlagAndH5Flag();
        if (!this.f127a.contains(uTPageEventObject)) {
            this.f127a.add(uTPageEventObject);
        }
        if (this.f127a.size() > 200) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= 100) {
                    break;
                }
                UTPageEventObject uTPageEventObjectPoll = this.f127a.poll();
                if (uTPageEventObjectPoll != null && this.B.containsKey(uTPageEventObjectPoll.getCacheKey())) {
                    this.B.remove(uTPageEventObjectPoll.getCacheKey());
                }
                i = i2 + 1;
            }
        }
    }

    @Deprecated
    public synchronized void turnOffAutoPageTrack() {
        this.N = true;
    }

    public String getCurrentPageName() {
        return this.ah;
    }

    void pageAppearByAuto(Activity aActivity) {
        if (!this.N) {
            pageAppear(aActivity);
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private String m107a(Object obj) {
        String simpleName;
        if (obj instanceof String) {
            simpleName = (String) obj;
        } else {
            simpleName = obj.getClass().getSimpleName();
        }
        return simpleName + obj.hashCode();
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0018  */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    synchronized boolean m110a(Object obj) {
        boolean z;
        if (obj != null) {
            UTPageEventObject uTPageEventObjectA = a(obj);
            if (uTPageEventObjectA.getPageStatus() == null || uTPageEventObjectA.getPageStatus() != UTPageStatus.UT_H5_IN_WebView) {
                z = false;
            } else {
                z = true;
            }
        } else {
            z = false;
        }
        return z;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    synchronized void m109a(Object obj) {
        if (obj != null) {
            UTPageEventObject uTPageEventObjectA = a(obj);
            if (uTPageEventObjectA.getPageStatus() != null) {
                uTPageEventObjectA.setH5Called();
            }
        }
    }

    private synchronized UTPageEventObject a(Object obj) {
        UTPageEventObject uTPageEventObject;
        String strM107a = m107a(obj);
        if (this.B.containsKey(strM107a)) {
            uTPageEventObject = this.B.get(strM107a);
        } else {
            uTPageEventObject = new UTPageEventObject();
            this.B.put(strM107a, uTPageEventObject);
            uTPageEventObject.setCacheKey(strM107a);
        }
        return uTPageEventObject;
    }

    private synchronized void a(String str, UTPageEventObject uTPageEventObject) {
        this.B.put(str, uTPageEventObject);
    }

    private synchronized void b(UTPageEventObject uTPageEventObject) {
        if (this.B.containsKey(uTPageEventObject.getCacheKey())) {
            this.B.remove(uTPageEventObject.getCacheKey());
        }
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    private synchronized void m108b(Object obj) {
        String strM107a = m107a(obj);
        if (this.B.containsKey(strM107a)) {
            this.B.remove(strM107a);
        }
    }

    @Deprecated
    public synchronized void pageAppear(Object aPageObject) {
        a(aPageObject, null, false);
    }

    synchronized void a(Object obj, String str, boolean z) {
        if (obj != null) {
            String strM107a = m107a(obj);
            if (strM107a == null || !strM107a.equals(this.ag)) {
                if (this.ag != null) {
                    i.a("lost 2001", "Last page requires leave(" + this.ag + ").");
                }
                UTPageEventObject uTPageEventObjectA = a(obj);
                if (!z && uTPageEventObjectA.isSkipPage()) {
                    i.a("skip page[pageAppear]", "page name:" + obj.getClass().getSimpleName());
                } else {
                    String h5Url = UTMIVariables.getInstance().getH5Url();
                    if (h5Url != null) {
                        try {
                            this.A.put("spm", Uri.parse(h5Url).getQueryParameter("spm"));
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                        UTMIVariables.getInstance().setH5Url((String) null);
                    }
                    String strB = b(obj);
                    if (!TextUtils.isEmpty(str)) {
                        strB = str;
                    }
                    if (!TextUtils.isEmpty(uTPageEventObjectA.getPageName())) {
                        strB = uTPageEventObjectA.getPageName();
                    }
                    this.ah = strB;
                    uTPageEventObjectA.setPageName(strB);
                    uTPageEventObjectA.setPageStayTimstamp(SystemClock.elapsedRealtime());
                    uTPageEventObjectA.setRefPage(UTMIVariables.getInstance().getRefPage());
                    uTPageEventObjectA.setPageAppearCalled();
                    if (this.C != null) {
                        Map pageProperties = uTPageEventObjectA.getPageProperties();
                        if (pageProperties == null) {
                            uTPageEventObjectA.setPageProperties(this.C);
                        } else {
                            HashMap map = new HashMap();
                            map.putAll(pageProperties);
                            map.putAll(this.C);
                            uTPageEventObjectA.setPageProperties(map);
                        }
                    }
                    this.C = null;
                    this.ag = m107a(obj);
                    b(uTPageEventObjectA);
                    a(m107a(obj), uTPageEventObjectA);
                }
            }
        } else {
            i.a("pageAppear", "The page object should not be null");
        }
    }

    synchronized void pageAppear(Object aPageObject, String aCustomPageName) {
        a(aPageObject, aCustomPageName, false);
    }

    @Deprecated
    public synchronized void updatePageProperties(Map<String, String> aProperties) {
        if (aProperties != null) {
            this.A.putAll(aProperties);
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000b A[Catch: all -> 0x002b, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0005, B:10:0x0015, B:12:0x0027, B:17:0x002e, B:7:0x000b), top: B:19:0x0005 }] */
    synchronized void updatePageProperties(Object aPageObject, Map<String, String> aProperties) {
        if (aPageObject == null || aProperties == null) {
            i.a("updatePageProperties", "failed to update project, parameters should not be null and the map should not be empty");
        } else if (aProperties.size() == 0) {
            i.a("updatePageProperties", "failed to update project, parameters should not be null and the map should not be empty");
        } else {
            HashMap map = new HashMap();
            map.putAll(aProperties);
            UTPageEventObject uTPageEventObjectA = a(aPageObject);
            Map pageProperties = uTPageEventObjectA.getPageProperties();
            if (pageProperties == null) {
                uTPageEventObjectA.setPageProperties(map);
            } else {
                HashMap map2 = new HashMap();
                map2.putAll(pageProperties);
                map2.putAll(map);
                uTPageEventObjectA.setPageProperties(map2);
            }
        }
        throw th;
    }

    synchronized void updatePageName(Object aPageObject, String aPageName) {
        if (aPageObject != null) {
            if (!TextUtils.isEmpty(aPageName)) {
                a(aPageObject).setPageName(aPageName);
                this.ah = aPageName;
            }
        }
    }

    synchronized void updatePageUrl(Object aPageObject, Uri aUrl) {
        if (aPageObject != null && aUrl != null) {
            Log.i("url", "url" + aUrl.toString());
            a(aPageObject).setPageUrl(aUrl);
        }
    }

    synchronized void updatePageStatus(Object aPageObject, UTPageStatus aPageStatus) {
        if (aPageObject != null && aPageStatus != null) {
            a(aPageObject).setPageStatus(aPageStatus);
        }
    }

    synchronized void updateNextPageProperties(Map<String, String> aProperties) {
        if (aProperties != null) {
            HashMap map = new HashMap();
            map.putAll(aProperties);
            this.C = map;
        }
    }

    void pageDisAppearByAuto(Activity aActivity) {
        if (!this.N) {
            pageDisAppear(aActivity);
        }
    }

    synchronized void skipPage(Object aPageObject) {
        if (aPageObject != null) {
            a(aPageObject).setToSkipPage();
        }
    }

    /* JADX WARN: Code duplicated, block: B:97:0x01c7 A[PHI: r3 r4
  0x01c7: PHI (r3v9 java.util.Map<java.lang.String, java.lang.String>) = (r3v8 java.util.Map<java.lang.String, java.lang.String>), (r3v27 java.util.Map<java.lang.String, java.lang.String>) binds: [B:38:0x007b, B:48:0x00a7] A[DONT_GENERATE, DONT_INLINE]
  0x01c7: PHI (r4v7 java.lang.String) = (r4v6 java.lang.String), (r4v12 java.lang.String) binds: [B:38:0x007b, B:48:0x00a7] A[DONT_GENERATE, DONT_INLINE]] */
    @Deprecated
    public synchronized void pageDisAppear(Object aPageObject) {
        String str;
        String str2;
        Map<String, String> map;
        Uri uri;
        String queryParameter;
        if (aPageObject != null) {
            if (this.ag != null) {
                UTPageEventObject uTPageEventObjectA = a(aPageObject);
                if (uTPageEventObjectA.isPageAppearCalled()) {
                    if (uTPageEventObjectA.getPageStatus() != null && UTPageStatus.UT_H5_IN_WebView == uTPageEventObjectA.getPageStatus() && uTPageEventObjectA.isH5Called()) {
                        a(uTPageEventObjectA);
                    } else {
                        long jElapsedRealtime = SystemClock.elapsedRealtime() - uTPageEventObjectA.getPageStayTimstamp();
                        if (uTPageEventObjectA.getPageUrl() == null && (aPageObject instanceof Activity)) {
                            uTPageEventObjectA.setPageUrl(((Activity) aPageObject).getIntent().getData());
                        }
                        String pageName = uTPageEventObjectA.getPageName();
                        String refPage = uTPageEventObjectA.getRefPage();
                        if (refPage == null || refPage.length() == 0) {
                            refPage = "-";
                        }
                        Map<String, String> map2 = this.A;
                        if (map2 == null) {
                            map2 = new HashMap<>();
                        }
                        if (uTPageEventObjectA.getPageProperties() != null) {
                            map2.putAll(uTPageEventObjectA.getPageProperties());
                        }
                        if (aPageObject instanceof IUTPageTrack) {
                            IUTPageTrack iUTPageTrack = (IUTPageTrack) aPageObject;
                            String referPage = iUTPageTrack.getReferPage();
                            if (!TextUtils.isEmpty(referPage)) {
                                refPage = referPage;
                            }
                            Map<? extends String, ? extends String> pageProperties = iUTPageTrack.getPageProperties();
                            if (pageProperties != null && pageProperties.size() > 0) {
                                this.A.putAll(pageProperties);
                                map2 = this.A;
                            }
                            String pageName2 = iUTPageTrack.getPageName();
                            if (TextUtils.isEmpty(pageName2)) {
                                str = refPage;
                                str2 = pageName;
                                map = map2;
                            } else {
                                map = map2;
                                str = refPage;
                                str2 = pageName2;
                            }
                        } else {
                            str = refPage;
                            str2 = pageName;
                            map = map2;
                        }
                        Uri pageUrl = uTPageEventObjectA.getPageUrl();
                        if (pageUrl != null) {
                            try {
                                HashMap map3 = new HashMap();
                                String queryParameter2 = pageUrl.getQueryParameter("spm");
                                if (TextUtils.isEmpty(queryParameter2)) {
                                    try {
                                        pageUrl = Uri.parse(URLDecoder.decode(pageUrl.toString(), HTTP.UTF_8));
                                        uri = pageUrl;
                                        queryParameter = pageUrl.getQueryParameter("spm");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                        uri = pageUrl;
                                        queryParameter = queryParameter2;
                                    }
                                } else {
                                    uri = pageUrl;
                                    queryParameter = queryParameter2;
                                }
                                if (!TextUtils.isEmpty(queryParameter)) {
                                    boolean z = false;
                                    if (this.D.containsKey(aPageObject) && queryParameter.equals(this.D.get(aPageObject))) {
                                        z = true;
                                    }
                                    if (!z) {
                                        map3.put("spm", queryParameter);
                                        this.D.put(aPageObject, queryParameter);
                                    }
                                }
                                String queryParameter3 = uri.getQueryParameter("scm");
                                if (!TextUtils.isEmpty(queryParameter3)) {
                                    map3.put("scm", queryParameter3);
                                }
                                String strA = a(uri);
                                if (!TextUtils.isEmpty(strA)) {
                                    c.a().e(strA);
                                }
                                if (map3.size() > 0) {
                                    map.putAll(map3);
                                }
                            } catch (Throwable th) {
                                th.printStackTrace();
                            }
                        }
                        UTHitBuilders.UTPageHitBuilder uTPageHitBuilder = new UTHitBuilders.UTPageHitBuilder(str2);
                        uTPageHitBuilder.setReferPage(str).setDurationOnPage(jElapsedRealtime).setProperties(map);
                        UTMIVariables.getInstance().setRefPage(str2);
                        UTTracker defaultTracker = UTAnalytics.getInstance().getDefaultTracker();
                        if (defaultTracker != null) {
                            defaultTracker.send(uTPageHitBuilder.build());
                        } else {
                            i.a("Record page event error", "Fatal Error,must call setRequestAuthentication method first.");
                        }
                    }
                } else {
                    i.a("UT", "Please call pageAppear first(" + b(aPageObject) + ").");
                }
                this.A = new HashMap();
                if (uTPageEventObjectA.isSkipPage()) {
                    a(uTPageEventObjectA);
                } else if (uTPageEventObjectA.getPageStatus() != null && UTPageStatus.UT_H5_IN_WebView == uTPageEventObjectA.getPageStatus()) {
                    a(uTPageEventObjectA);
                } else {
                    m108b(aPageObject);
                }
                this.ag = null;
                this.ah = null;
            }
        } else {
            i.a("pageDisAppear", "The page object should not be null");
        }
    }

    private static String a(Uri uri) {
        List<String> queryParameters;
        if (uri != null && (queryParameters = uri.getQueryParameters("ttid")) != null) {
            for (String str : queryParameters) {
                if (!str.contains("@") && !str.contains("%40")) {
                    return str;
                }
            }
        }
        return null;
    }

    private static String b(Object obj) {
        String simpleName = obj.getClass().getSimpleName();
        if (simpleName != null && simpleName.toLowerCase().endsWith("activity")) {
            return simpleName.substring(0, simpleName.length() - 8);
        }
        return simpleName;
    }
}
