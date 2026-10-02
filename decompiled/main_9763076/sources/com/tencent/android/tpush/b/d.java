package com.tencent.android.tpush.b;

import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.data.CachedMessageIntent;
import com.tencent.android.tpush.data.MessageId;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.cache.CacheManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private static d a = new d();
    private static final byte[] b = new byte[0];

    private d() {
    }

    public static d a() {
        return a;
    }

    public ArrayList a(Context context, String str) {
        ArrayList arrayList;
        Object objA;
        if (context == null || com.tencent.android.tpush.service.e.m.b(str) || (objA = a(context, str, ".tpns.msg.id")) == null) {
            arrayList = null;
        } else {
            arrayList = (ArrayList) objA;
        }
        if (arrayList == null) {
            return new ArrayList();
        }
        return arrayList;
    }

    public void a(Context context, String str, ArrayList arrayList) {
        synchronized (b) {
            if (context != null && arrayList != null) {
                a(context, str, ".tpns.msg.id", arrayList);
            }
        }
    }

    public ArrayList a(Context context) {
        if (context != null) {
            return com.tencent.android.tpush.d.a.c(context);
        }
        return null;
    }

    public void a(Context context, Intent intent) {
        if (context != null) {
            com.tencent.android.tpush.d.a.a(context, intent);
        }
    }

    public void b(Context context) {
        if (context != null) {
            com.tencent.android.tpush.d.a.a(context);
        }
    }

    public void a(Context context, long j) {
        if (context != null) {
            com.tencent.android.tpush.d.a.f(context, j);
        }
    }

    public void b(Context context, long j) {
        if (context != null) {
            com.tencent.android.tpush.d.a.e(context, j);
        }
    }

    public void c(Context context, long j) {
        if (context != null) {
            com.tencent.android.tpush.d.a.a(context, j);
        }
    }

    public void d(Context context, long j) {
        if (context != null) {
            com.tencent.android.tpush.d.a.b(context, j);
        }
    }

    public void e(Context context, long j) {
        if (context != null) {
            com.tencent.android.tpush.d.a.c(context, j);
        }
    }

    public void f(Context context, long j) {
        if (context != null) {
            com.tencent.android.tpush.d.a.d(context, j);
        }
    }

    public void c(Context context) {
        if (context != null) {
            com.tencent.android.tpush.d.a.b(context);
        }
    }

    public void a(Context context, String str, MessageId messageId) {
        ArrayList arrayList;
        synchronized (b) {
            if (context != null) {
                if (!com.tencent.android.tpush.service.e.m.b(str) && messageId != null) {
                    ArrayList arrayListA = a(context, str);
                    if (arrayListA == null) {
                        arrayList = new ArrayList();
                    } else {
                        int i = 0;
                        while (true) {
                            if (i >= arrayListA.size()) {
                                arrayList = arrayListA;
                                break;
                            } else if (((MessageId) arrayListA.get(i)).id != messageId.id) {
                                i++;
                            } else {
                                arrayListA.remove(i);
                                arrayList = arrayListA;
                                break;
                            }
                        }
                    }
                    arrayList.add(messageId);
                    a(context, str, arrayList);
                }
            }
        }
    }

    public MessageId a(Context context, String str, long j) {
        ArrayList<MessageId> arrayListA;
        if (context != null && !com.tencent.android.tpush.service.e.m.b(str) && j > 0 && (arrayListA = a(context, str)) != null && arrayListA.size() > 0) {
            for (MessageId messageId : arrayListA) {
                if (messageId.id == j) {
                    return messageId;
                }
            }
        }
        return null;
    }

    public boolean b(Context context, String str, long j) {
        ArrayList<MessageId> arrayListA;
        if (context != null && !com.tencent.android.tpush.service.e.m.b(str) && j > 0 && (arrayListA = a(context, str)) != null && arrayListA.size() > 0) {
            for (MessageId messageId : arrayListA) {
                if (messageId.id == j) {
                    return messageId.a();
                }
            }
        }
        return false;
    }

    public void b(Context context, String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            switch (jSONObject.optInt("action", 2)) {
                case 1:
                    String[] strArrSplit = jSONObject.optString("pushIdList", Constants.MAIN_VERSION_TAG).split(",");
                    for (String str2 : strArrSplit) {
                        a(context, Long.valueOf(str2).longValue());
                    }
                    break;
                case 2:
                    b(context);
                    break;
                case 3:
                    int iOptInt = jSONObject.optInt("enabled", -1);
                    com.tencent.android.tpush.a.a.f("MessageManager", "setLogToFile with cmd = " + iOptInt);
                    com.tencent.android.tpush.a.a.a(iOptInt);
                    break;
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("MessageManager", "onCrtlMsgHandle", e);
        }
    }

    public static String g(Context context, long j) {
        String strA = Constants.MAIN_VERSION_TAG + com.tencent.android.tpush.common.n.a(context, "tpush_msgId_" + j, Constants.MAIN_VERSION_TAG);
        if (strA == null || strA.trim().length() == 0) {
            strA = com.tencent.android.tpush.common.m.a(context, "tpush_msgId_" + j, true);
        }
        if (strA != null && strA.length() > 20480) {
            strA = strA.substring(0, strA.indexOf("@@", 5120));
        }
        return strA != null ? strA : Constants.MAIN_VERSION_TAG;
    }

    private void a(Context context, String str, String str2, ArrayList arrayList) {
        try {
            if (arrayList.size() > 50) {
                arrayList.subList(0, 10).clear();
            }
            com.tencent.android.tpush.common.n.b(context, str + str2, Rijndael.encrypt(com.tencent.android.tpush.common.k.a(arrayList)));
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("MessageManager", "putSettings", e);
        }
    }

    private Object a(Context context, String str, String str2) {
        try {
            return com.tencent.android.tpush.common.k.a(Rijndael.decrypt(com.tencent.android.tpush.common.n.a(context, str + str2, (String) null)));
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("MessageManager", "getSettings", e);
            return null;
        }
    }

    public ArrayList d(Context context) {
        List registerInfos;
        if (context == null || (registerInfos = CacheManager.getRegisterInfos(context)) == null || registerInfos.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = registerInfos.iterator();
        while (it.hasNext()) {
            ArrayList arrayListC = c(context, (String) it.next());
            if (arrayListC != null && arrayListC.size() > 0) {
                arrayList.addAll(arrayListC);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0022  */
    public ArrayList c(Context context, String str) {
        Object objA;
        ArrayList arrayList;
        if (context == null) {
            arrayList = null;
        } else {
            try {
                if (com.tencent.android.tpush.service.e.m.b(str) || (objA = a(context, str, ".tpns.msg.id.cached")) == null) {
                    arrayList = null;
                } else {
                    arrayList = (ArrayList) objA;
                }
            } catch (Throwable th) {
                return new ArrayList();
            }
        }
        if (arrayList == null) {
            return new ArrayList();
        }
        return arrayList;
    }

    public void a(Context context, String str, Intent intent) {
        ArrayList arrayList;
        synchronized (b) {
            if (context != null) {
                if (!com.tencent.android.tpush.service.e.m.b(str) && intent != null) {
                    CachedMessageIntent cachedMessageIntent = new CachedMessageIntent();
                    cachedMessageIntent.pkgName = str;
                    cachedMessageIntent.msgId = intent.getLongExtra(MessageKey.MSG_ID, -1L);
                    cachedMessageIntent.intent = Rijndael.encrypt(intent.toUri(1));
                    ArrayList arrayListC = c(context, str);
                    if (arrayListC == null) {
                        arrayList = new ArrayList();
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        for (int i = 0; i < arrayListC.size(); i++) {
                            CachedMessageIntent cachedMessageIntent2 = (CachedMessageIntent) arrayListC.get(i);
                            if (cachedMessageIntent2.equals(cachedMessageIntent)) {
                                arrayList2.add(cachedMessageIntent2);
                            }
                        }
                        arrayListC.removeAll(arrayList2);
                        arrayList = arrayListC;
                    }
                    int size = arrayList.size() / 2;
                    if (size >= 100) {
                        com.tencent.android.tpush.a.a.g("MessageManager", "too much cache msg, try to cut " + size);
                        arrayList.subList(0, size).clear();
                    }
                    arrayList.add(cachedMessageIntent);
                    b(context, str, arrayList);
                }
            }
        }
    }

    public void b(Context context, String str, ArrayList arrayList) {
        synchronized (b) {
            if (context != null && arrayList != null) {
                com.tencent.android.tpush.a.a.a(Constants.ServiceLogTag, "updateCachedMsgIntentByPkgName, size: " + arrayList.size());
                a(context, str, ".tpns.msg.id.cached", arrayList);
            }
        }
    }

    public void d(Context context, String str) {
        synchronized (b) {
            if (context != null) {
                b(context, str, new ArrayList());
            }
        }
    }

    public void c(Context context, String str, long j) {
        synchronized (b) {
            if (context != null) {
                ArrayList arrayListC = c(context, str);
                if (arrayListC != null && arrayListC.size() > 0) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < arrayListC.size(); i++) {
                        CachedMessageIntent cachedMessageIntent = (CachedMessageIntent) arrayListC.get(i);
                        if (cachedMessageIntent.msgId == j) {
                            arrayList.add(cachedMessageIntent);
                        }
                    }
                    if (arrayList != null && arrayList.size() == 0) {
                        com.tencent.android.tpush.a.a.i("MessageManager", "deleteCachedMsgIntentByPkgName do not have MessageId = " + j);
                    }
                    arrayListC.removeAll(arrayList);
                }
                b(context, str, arrayListC);
            }
        }
    }

    public void a(Context context, List list, ArrayList arrayList) {
        synchronized (b) {
            if (context != null && list != null) {
                if (list.size() > 0) {
                    try {
                        ArrayList arrayList2 = new ArrayList();
                        if (arrayList != null && arrayList.size() > 0) {
                            HashMap map = new HashMap();
                            int i = 0;
                            while (true) {
                                int i2 = i;
                                if (i2 >= arrayList.size()) {
                                    break;
                                }
                                CachedMessageIntent cachedMessageIntent = (CachedMessageIntent) arrayList.get(i2);
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    CachedMessageIntent cachedMessageIntent2 = (CachedMessageIntent) it.next();
                                    if (cachedMessageIntent.equals(cachedMessageIntent2)) {
                                        arrayList2.add(cachedMessageIntent);
                                        ArrayList arrayList3 = (ArrayList) map.get(cachedMessageIntent2.pkgName);
                                        if (arrayList3 == null) {
                                            arrayList3 = new ArrayList();
                                        }
                                        map.put(cachedMessageIntent2.pkgName, arrayList3);
                                    }
                                }
                                i = i2 + 1;
                            }
                            arrayList.removeAll(arrayList2);
                            Iterator it2 = arrayList.iterator();
                            while (it2.hasNext()) {
                                CachedMessageIntent cachedMessageIntent3 = (CachedMessageIntent) it2.next();
                                ArrayList arrayList4 = (ArrayList) map.get(cachedMessageIntent3.pkgName);
                                if (arrayList4 == null) {
                                    arrayList4 = new ArrayList();
                                }
                                arrayList4.add(cachedMessageIntent3);
                                map.put(cachedMessageIntent3.pkgName, arrayList4);
                            }
                            for (String str : map.keySet()) {
                                b(context, str, (ArrayList) map.get(str));
                            }
                        }
                    } catch (Exception e) {
                        com.tencent.android.tpush.a.a.c("MessageManager", "deleteCachedMsgIntent", e);
                    }
                }
            }
        }
    }
}
