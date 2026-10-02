package com.baidu.mobstat;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DataCore {
    private static JSONObject a = new JSONObject();
    private static DataCore b = new DataCore();
    private JSONArray c = new JSONArray();
    private JSONArray d = new JSONArray();
    private JSONArray e = new JSONArray();
    private boolean f = false;
    private volatile int g = 0;
    private StatService.WearListener h;

    public static DataCore instance() {
        return b;
    }

    private DataCore() {
    }

    public void putSession(JSONObject jSONObject) {
        if (jSONObject != null) {
            if (a(jSONObject.toString())) {
                db.b("data to put exceed limit, will not put");
                return;
            }
            synchronized (this.c) {
                try {
                    this.c.put(this.c.length(), jSONObject);
                } catch (JSONException e) {
                    db.a(e);
                }
            }
        }
    }

    public void putSession(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals(new JSONObject().toString())) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                putSession(jSONObject);
                db.a("Load last session:" + jSONObject);
            } catch (JSONException e) {
                db.a(e);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0069 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:50:0x0107 A[Catch: all -> 0x0075, DONT_GENERATE, TRY_ENTER, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0005, B:6:0x000f, B:11:0x0026, B:13:0x0046, B:14:0x004c, B:30:0x0084, B:32:0x008c, B:34:0x0094, B:36:0x009c, B:42:0x00ac, B:44:0x00c3, B:47:0x00cd, B:48:0x00f6, B:50:0x0107, B:52:0x0109, B:53:0x0112, B:56:0x0115, B:28:0x0080, B:25:0x0079, B:8:0x0019, B:20:0x006f), top: B:63:0x0005, inners: #0, #4 }] */
    /* JADX WARN: Code duplicated, block: B:61:0x0109 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:70:0x0026 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x011e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:0x006b A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:8:0x0019 A[Catch: JSONException -> 0x006e, all -> 0x0075, TRY_LEAVE, TryCatch #4 {JSONException -> 0x006e, blocks: (B:6:0x000f, B:8:0x0019), top: B:68:0x000f, outer: #1 }] */
    private void a(JSONObject jSONObject, String str, String str2, String str3, long j, String str4, String str5, int i, boolean z) {
        int i2;
        int i3;
        JSONObject jSONObject2;
        long j2;
        int i4;
        synchronized (this.d) {
            int length = this.d.length();
            if (str3 == null) {
                jSONObject.put(NotifyType.SOUND, "0|");
                i2 = 0;
                i3 = length;
                while (true) {
                    if (i2 >= length) {
                        i2 = i3;
                        break;
                    }
                    jSONObject2 = this.d.getJSONObject(i2);
                    String string = jSONObject2.getString("i");
                    String string2 = jSONObject2.getString(NotifyType.LIGHTS);
                    j2 = jSONObject2.getLong("t") / 3600000;
                    i4 = 0;
                    i4 = jSONObject2.getInt("d");
                    String strOptString = jSONObject2.optString("h");
                    String strOptString2 = jSONObject2.optString("p");
                    int iOptInt = jSONObject2.optInt(NotifyType.VIBRATE);
                    boolean zOptBoolean = jSONObject2.optBoolean("at");
                    if (j2 == j) {
                        continue;
                    }
                    i2++;
                }
                if (i2 >= length) {
                    this.d.put(length, jSONObject);
                    return;
                }
                return;
            }
            try {
                if (str3.equals(Constants.MAIN_VERSION_TAG)) {
                    jSONObject.put(NotifyType.SOUND, "0|");
                    i2 = 0;
                    i3 = length;
                    while (true) {
                        if (i2 >= length) {
                            i2 = i3;
                            break;
                        }
                        try {
                            jSONObject2 = this.d.getJSONObject(i2);
                            String string3 = jSONObject2.getString("i");
                            String string4 = jSONObject2.getString(NotifyType.LIGHTS);
                            j2 = jSONObject2.getLong("t") / 3600000;
                            i4 = 0;
                            try {
                                i4 = jSONObject2.getInt("d");
                            } catch (JSONException e) {
                                db.a("old version data, No duration Tag");
                            }
                            String strOptString3 = jSONObject2.optString("h");
                            String strOptString4 = jSONObject2.optString("p");
                            int iOptInt2 = jSONObject2.optInt(NotifyType.VIBRATE);
                            boolean zOptBoolean2 = jSONObject2.optBoolean("at");
                            if (j2 == j || i4 != 0 || !string3.equals(str) || !string4.equals(str2) || !strOptString3.equals(str4) || !strOptString4.equals(str5) || iOptInt2 != i || zOptBoolean2 != z) {
                                i2++;
                            } else {
                                int i5 = jSONObject2.getInt("c") + jSONObject.getInt("c");
                                String strOptString5 = jSONObject2.optString(NotifyType.SOUND);
                                if (strOptString5 == null || strOptString5.equalsIgnoreCase(Constants.MAIN_VERSION_TAG)) {
                                    strOptString5 = "0|";
                                }
                                String str6 = strOptString5 + (jSONObject.getLong("t") - jSONObject2.getLong("t")) + "|";
                                try {
                                    jSONObject2.remove("c");
                                    jSONObject2.put("c", i5);
                                    jSONObject2.put(NotifyType.SOUND, str6);
                                    break;
                                } catch (JSONException e2) {
                                    e = e2;
                                    i3 = i2;
                                    db.a(e);
                                    i2++;
                                }
                            }
                        } catch (JSONException e3) {
                            e = e3;
                        }
                    }
                    if (i2 >= length) {
                        try {
                            this.d.put(length, jSONObject);
                        } catch (JSONException e4) {
                            db.a(e4);
                        }
                        return;
                    }
                    return;
                }
                i2 = 0;
                i3 = length;
                while (true) {
                    if (i2 >= length) {
                        i2 = i3;
                        break;
                    }
                    jSONObject2 = this.d.getJSONObject(i2);
                    String string5 = jSONObject2.getString("i");
                    String string6 = jSONObject2.getString(NotifyType.LIGHTS);
                    j2 = jSONObject2.getLong("t") / 3600000;
                    i4 = 0;
                    i4 = jSONObject2.getInt("d");
                    String strOptString6 = jSONObject2.optString("h");
                    String strOptString7 = jSONObject2.optString("p");
                    int iOptInt3 = jSONObject2.optInt(NotifyType.VIBRATE);
                    boolean zOptBoolean3 = jSONObject2.optBoolean("at");
                    if (j2 == j) {
                        continue;
                    }
                    i2++;
                }
                if (i2 >= length) {
                    this.d.put(length, jSONObject);
                    return;
                }
                return;
            } catch (JSONException e5) {
                db.a("event put s fail");
            }
            throw th;
        }
    }

    private boolean a(String str) {
        return (str.getBytes().length + ch.a().b()) + this.g > 204800;
    }

    public void putEvent(Context context, JSONObject jSONObject, boolean z) {
        if (jSONObject != null) {
            if (a(jSONObject.toString())) {
                db.b("data to put exceed limit, will not put");
                return;
            }
            int i = 0;
            try {
                String string = jSONObject.getString("i");
                String string2 = jSONObject.getString(NotifyType.LIGHTS);
                long j = jSONObject.getLong("t") / 3600000;
                String strOptString = jSONObject.optString(NotifyType.SOUND);
                String strOptString2 = jSONObject.optString("h");
                String strOptString3 = jSONObject.optString("p");
                int iOptInt = jSONObject.optInt(NotifyType.VIBRATE);
                boolean zOptBoolean = jSONObject.optBoolean("at");
                String strOptString4 = jSONObject.optString("ext");
                String strOptString5 = jSONObject.optString("attribute");
                try {
                    i = jSONObject.getInt("d");
                } catch (JSONException e) {
                    db.a("old version data, No duration Tag");
                }
                boolean z2 = false;
                if (!TextUtils.isEmpty(strOptString4) && !new JSONObject().toString().equals(strOptString4)) {
                    z2 = true;
                }
                boolean z3 = false;
                if (!TextUtils.isEmpty(strOptString5)) {
                    z3 = true;
                }
                if (i == 0 && !z2 && !z3) {
                    a(jSONObject, string, string2, strOptString, j, strOptString2, strOptString3, iOptInt, zOptBoolean);
                    return;
                }
                synchronized (this.d) {
                    int length = this.d.length();
                    try {
                        jSONObject.put(NotifyType.SOUND, PushConstants.PUSH_TYPE_NOTIFY);
                        this.d.put(length, jSONObject);
                    } catch (JSONException e2) {
                        db.a(e2);
                    }
                }
            } catch (JSONException e3) {
                db.a(e3);
            }
        }
    }

    public void putEvent(Context context, String str, String str2, int i, long j, long j2, String str3, String str4, int i2, boolean z, ExtraInfo extraInfo, Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("i", str);
            jSONObject.put(NotifyType.LIGHTS, str2);
            jSONObject.put("c", i);
            jSONObject.put("t", j);
            jSONObject.put("d", j2);
            jSONObject.put("h", str3);
            jSONObject.put("p", str4);
            jSONObject.put(NotifyType.VIBRATE, i2);
            jSONObject.put("at", z ? 1 : 0);
            if (extraInfo != null && extraInfo.dumpToJson().length() != 0) {
                jSONObject.put("ext", extraInfo.dumpToJson());
            }
            if (map != null) {
                JSONArray jSONArray = new JSONArray();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value) && !a(value, WXMediaMessage.DESCRIPTION_LENGTH_LIMIT)) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("k", key);
                        jSONObject2.put(NotifyType.VIBRATE, value);
                        jSONArray.put(jSONObject2);
                    }
                }
                if (jSONArray.length() != 0) {
                    jSONObject.put("attribute", jSONArray);
                }
            }
            putEvent(context, jSONObject, false);
            db.a("put event:" + jSONObject.toString());
        } catch (JSONException e) {
            db.a(e);
        }
    }

    private static boolean a(String str, int i) {
        int length;
        if (str == null) {
            return false;
        }
        try {
            length = str.getBytes().length;
        } catch (Exception e) {
            length = 0;
        }
        return length > i;
    }

    public void installHeader(Context context) {
        synchronized (a) {
            CooperService.a().getHeadObject().a(context, a);
        }
    }

    public synchronized void flush(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            synchronized (this.c) {
                jSONObject.put("pr", new JSONArray(this.c.toString()));
            }
            synchronized (this.d) {
                jSONObject.put("ev", new JSONArray(this.d.toString()));
            }
            synchronized (a) {
                jSONObject.put("he", new JSONObject(a.toString()));
            }
        } catch (Exception e) {
            db.a("flushLogWithoutHeader() construct cache error");
        }
        String string = jSONObject.toString();
        if (a()) {
            db.a("cache.json exceed 204800B,stop flush.");
        } else {
            int length = string.getBytes().length;
            if (length >= 204800) {
                a(true);
            } else {
                this.g = length;
                db.a("flush:cacheFileSize is:" + this.g + ", capacity is:204800");
                cu.a(context, de.q(context) + "__local_stat_cache.json", string, false);
                synchronized (this.e) {
                    String string2 = this.e.toString();
                    db.a("flush wifi data: " + string2);
                    cu.a(context, "__local_ap_info_cache.json", string2, false);
                }
            }
        }
    }

    private void a(boolean z) {
        this.f = z;
    }

    private boolean a() {
        return this.f;
    }

    public void loadLastSession(Context context) {
        if (context != null) {
            String str = de.q(context) + "__local_last_session.json";
            if (cu.c(context, str)) {
                String strA = cu.a(context, str);
                if (TextUtils.isEmpty(strA)) {
                    db.a("loadLastSession(): last_session.json file not found.");
                    return;
                }
                cu.a(context, str, new JSONObject().toString(), false);
                putSession(strA);
                flush(context);
            }
        }
    }

    public void loadStatData(Context context) {
        if (context != null) {
            String str = de.q(context) + "__local_stat_cache.json";
            if (cu.c(context, str)) {
                String strA = cu.a(context, str);
                if (strA.equals(Constants.MAIN_VERSION_TAG)) {
                    db.a("stat_cache file not found.");
                    return;
                }
                db.a("loadStatData, ");
                try {
                    this.g = strA.getBytes().length;
                    db.a("load Stat Data:cacheFileSize is:" + this.g);
                    JSONObject jSONObject = new JSONObject(strA);
                    db.a("Load cache:" + strA);
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    JSONArray jSONArray = jSONObject.getJSONArray("pr");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        if (jCurrentTimeMillis - jSONObject2.getLong(NotifyType.SOUND) <= 604800000) {
                            putSession(jSONObject2);
                        }
                    }
                    JSONArray jSONArray2 = jSONObject.getJSONArray("ev");
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                        if (jCurrentTimeMillis - jSONObject3.getLong("t") <= 604800000) {
                            putEvent(context, jSONObject3, true);
                        }
                    }
                    if (!isPartEmpty()) {
                        try {
                            JSONObject jSONObject4 = jSONObject.getJSONObject("he");
                            synchronized (a) {
                                try {
                                    a = jSONObject4;
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } catch (JSONException e) {
                            db.a(e);
                        }
                    }
                } catch (JSONException e2) {
                    db.a("Load stat data error:" + e2);
                }
            }
        }
    }

    private void a(Context context, JSONObject jSONObject, boolean z) {
        boolean z2 = true;
        if (jSONObject != null) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("app_session", z ? 1 : 0);
            } catch (Exception e) {
            }
            try {
                jSONObject2.put("failed_cnt", 0);
            } catch (Exception e2) {
            }
            try {
                jSONObject.put("trace", jSONObject2);
            } catch (Exception e3) {
                z2 = false;
            }
            if (z2) {
                a(context, jSONObject, jSONObject2);
            }
        }
    }

    private void a(Context context, JSONObject jSONObject, JSONObject jSONObject2) {
        long jCurrentTimeMillis;
        int iA = a(jSONObject);
        try {
            JSONObject jSONObject3 = jSONObject.getJSONObject("he");
            jCurrentTimeMillis = jSONObject3 != null ? jSONObject3.getLong("ss") : 0L;
        } catch (Exception e) {
            jCurrentTimeMillis = 0;
        }
        if (jCurrentTimeMillis == 0) {
            jCurrentTimeMillis = System.currentTimeMillis();
        }
        a(context, jSONObject2, jCurrentTimeMillis, iA);
    }

    private int a(JSONObject jSONObject) {
        int i;
        int i2 = 0;
        if (jSONObject == null) {
            return 0;
        }
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("he");
            i = (jSONObject2.getLong("ss") <= 0 || jSONObject2.getLong("sq") != 0) ? 0 : 1;
        } catch (Exception e) {
            i = 0;
        }
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("pr");
            if (jSONArray == null || jSONArray.length() == 0) {
                return i;
            }
            while (true) {
                try {
                    int i3 = i2;
                    i2 = i;
                    if (i3 < jSONArray.length()) {
                        JSONObject jSONObject3 = (JSONObject) jSONArray.get(i3);
                        i = (jSONObject3.getLong("e") == 0 || jSONObject3.getLong("c") != 0) ? i2 : i2 + 1;
                        i2 = i3 + 1;
                    } else {
                        return i2;
                    }
                } catch (Exception e2) {
                    return i2;
                }
            }
        } catch (Exception e3) {
            return i;
        }
    }

    private void a(Context context, JSONObject jSONObject, long j, int i) {
        long jLongValue;
        int iIntValue;
        long jIntValue;
        Object jSONArray;
        String[] strArrSplit;
        long jLongValue2 = cq.a().b(context).longValue();
        if (jLongValue2 <= 0 && i != 0) {
            cq.a().a(context, j);
            jLongValue2 = j;
        }
        a(jSONObject, "first", Long.valueOf(jLongValue2));
        if (i != 0) {
            long jLongValue3 = cq.a().c(context).longValue();
            jLongValue = j - jLongValue3;
            if (jLongValue3 != 0 && jLongValue <= 0) {
                jLongValue = -1;
            } else if (jLongValue3 == 0) {
                jLongValue = 0;
            }
            cq.a().b(context, j);
            cq.a().c(context, jLongValue);
        } else {
            jLongValue = cq.a().d(context).longValue();
        }
        a(jSONObject, "session_last_interval", Long.valueOf(jLongValue));
        String str = Constants.MAIN_VERSION_TAG;
        String str2 = Constants.MAIN_VERSION_TAG;
        String strE = cq.a().e(context);
        if (!TextUtils.isEmpty(strE) && strE.contains(":") && (strArrSplit = strE.split(":")) != null && strArrSplit.length == 2) {
            str = strArrSplit[0];
            str2 = strArrSplit[1];
        }
        if (TextUtils.isEmpty(str2)) {
            iIntValue = 0;
        } else {
            try {
                iIntValue = Integer.valueOf(str2).intValue();
            } catch (Exception e) {
                iIntValue = 0;
            }
        }
        String strA = dg.a(j);
        int i2 = (TextUtils.isEmpty(str) || strA.equals(str)) ? i + iIntValue : i;
        if (i != 0) {
            cq.a().a(context, strA + ":" + i2);
        }
        a(jSONObject, "session_today_cnt", Integer.valueOf(i2));
        if (TextUtils.isEmpty(str)) {
            jIntValue = 0;
        } else {
            try {
                jIntValue = Integer.valueOf(str).intValue();
            } catch (Exception e2) {
                jIntValue = 0;
            }
        }
        if (jIntValue != 0 && !TextUtils.isEmpty(str) && !strA.equals(str) && i != 0) {
            JSONArray jSONArrayA = a(context, jIntValue, iIntValue);
            cq.a().b(context, jSONArrayA.toString());
            a(jSONObject, "recent", jSONArrayA);
            return;
        }
        String strF = cq.a().f(context);
        if (TextUtils.isEmpty(strF)) {
            jSONArray = null;
        } else {
            try {
                jSONArray = new JSONArray(strF);
            } catch (Exception e3) {
                jSONArray = null;
            }
        }
        if (jSONArray == null) {
            jSONArray = new JSONArray();
        }
        a(jSONObject, "recent", jSONArray);
    }

    private JSONArray a(Context context, long j, long j2) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        String strF = cq.a().f(context);
        if (!TextUtils.isEmpty(strF)) {
            try {
                JSONArray jSONArray = new JSONArray(strF);
                if (jSONArray != null && jSONArray.length() != 0) {
                    for (int i = 0; i < jSONArray.length(); i++) {
                        arrayList.add((JSONObject) jSONArray.get(i));
                    }
                }
            } catch (Exception e) {
            }
        }
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            try {
                if (((JSONObject) it.next()).getLong("day") == j) {
                    z = false;
                    break;
                }
            } catch (Exception e2) {
            }
        }
        if (z) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("day", j);
                jSONObject.put("count", j2);
                arrayList.add(jSONObject);
            } catch (Exception e3) {
            }
        }
        int size = arrayList.size();
        return new JSONArray((Collection) (size > 5 ? arrayList.subList(size - 5, size) : arrayList));
    }

    private void a(JSONObject jSONObject, String str, Object obj) {
        if (jSONObject != null) {
            if (!jSONObject.has("visit")) {
                try {
                    jSONObject.put("visit", new JSONObject());
                } catch (Exception e) {
                }
            }
            try {
                ((JSONObject) jSONObject.get("visit")).put(str, obj);
            } catch (Exception e2) {
            }
        }
    }

    public void saveLogDataToSend(Context context, boolean z, boolean z2) {
        db.a("sendLogData() begin.");
        bu headObject = CooperService.a().getHeadObject();
        if (headObject != null) {
            synchronized (a) {
                if (TextUtils.isEmpty(headObject.f)) {
                    headObject.a(context, a);
                } else {
                    headObject.b(context, a);
                }
                db.a("constructHeader() begin." + a + a.length());
            }
            if (TextUtils.isEmpty(headObject.f)) {
                db.c("不能在manifest.xml中找到APP Key||can't find app key in manifest.xml.");
                return;
            }
        }
        JSONObject jSONObject = new JSONObject();
        synchronized (a) {
            try {
                a.put("t", System.currentTimeMillis());
                a.put("sq", z ? 0 : 1);
                a.put("ss", ch.a().e());
                synchronized (this.e) {
                    a.put("wl2", this.e);
                }
                a.put("sign", CooperService.a().getUUID());
                jSONObject.put("he", a);
                synchronized (this.c) {
                    try {
                        jSONObject.put("pr", this.c);
                        synchronized (this.d) {
                            try {
                                jSONObject.put("ev", this.d);
                                try {
                                    jSONObject.put("ex", new JSONArray());
                                    a(context, jSONObject, z2);
                                    String string = jSONObject.toString();
                                    db.a("---Send Data is:" + string);
                                    a(context, string);
                                    clearCache(context);
                                } catch (JSONException e) {
                                    db.a(e);
                                }
                            } catch (JSONException e2) {
                                db.a(e2);
                            }
                        }
                    } catch (JSONException e3) {
                        db.a(e3);
                    }
                }
            } catch (Exception e4) {
                db.a(e4);
            }
        }
    }

    private void a(Context context, String str) throws Throwable {
        if (this.h != null && this.h.onSendLogData(str)) {
            db.a("log data has been passed to app level");
        } else {
            by.a().a(context, str);
        }
    }

    public boolean isPartEmpty() {
        boolean z;
        synchronized (this.c) {
            z = this.c.length() == 0;
        }
        return z;
    }

    public void clearCache(Context context) {
        a(false);
        synchronized (a) {
            a = new JSONObject();
        }
        installHeader(context);
        a(context);
    }

    private void a(Context context) {
        synchronized (this.d) {
            this.d = new JSONArray();
        }
        synchronized (this.c) {
            this.c = new JSONArray();
        }
        synchronized (this.e) {
            this.e = new JSONArray();
        }
        flush(context);
    }
}
