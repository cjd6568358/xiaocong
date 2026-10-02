package com.tencent.android.tpush;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGLocalMessage {
    private static final String a = XGLocalMessage.class.getSimpleName();
    private long w;
    private int b = 1;
    private String c = Constants.MAIN_VERSION_TAG;
    private String d = Constants.MAIN_VERSION_TAG;
    private String e = Constants.MAIN_VERSION_TAG;
    private String f = "00";
    private String g = "00";
    private int h = 1;
    private int i = 1;
    private int j = 1;
    private int k = 0;
    private int l = 1;
    private String m = Constants.MAIN_VERSION_TAG;
    private String n = Constants.MAIN_VERSION_TAG;
    private String o = Constants.MAIN_VERSION_TAG;
    private int p = 1;
    private String q = Constants.MAIN_VERSION_TAG;
    private String r = Constants.MAIN_VERSION_TAG;
    private String s = Constants.MAIN_VERSION_TAG;
    private String t = Constants.MAIN_VERSION_TAG;
    private String u = Constants.MAIN_VERSION_TAG;
    private String v = "{}";
    private int x = 0;
    private long y = System.currentTimeMillis() * (-1);
    private long z = 0;
    private int A = 2592000;
    private long B = System.currentTimeMillis() + (((long) this.A) * 1000);

    public long getExpirationTimeMs() {
        return this.B;
    }

    public void setExpirationTimeMs(long j) {
        if (j > System.currentTimeMillis()) {
            this.A = (int) ((j - System.currentTimeMillis()) / 1000);
            if (this.A < 0) {
                this.A = Integer.MAX_VALUE;
            }
            this.B = j;
        }
    }

    public int getTtl() {
        return this.A;
    }

    public int getType() {
        return this.b;
    }

    public void setType(int i) {
        this.b = i;
    }

    public String getTitle() {
        return this.c;
    }

    public void setTitle(String str) {
        this.c = str;
    }

    public String getContent() {
        return this.d;
    }

    public void setContent(String str) {
        this.d = str;
    }

    public void setCustomContent(HashMap map) {
        this.v = new JSONObject(map).toString();
    }

    public String getCustom_content() {
        return this.v;
    }

    public String getHour() {
        if (this.f.length() < 1) {
            return "00";
        }
        if (this.f.length() > 0 && this.f.length() < 2) {
            return PushConstants.PUSH_TYPE_NOTIFY + this.f;
        }
        return this.f;
    }

    public void setHour(String str) {
        this.f = str;
    }

    public String getMin() {
        if (this.g.length() < 1) {
            return "00";
        }
        if (this.g.length() > 0 && this.g.length() < 2) {
            return PushConstants.PUSH_TYPE_NOTIFY + this.g;
        }
        return this.g;
    }

    public void setMin(String str) {
        this.g = str;
    }

    public long getBuilderId() {
        return this.w;
    }

    public void setBuilderId(long j) {
        this.w = j;
    }

    public String getDate() {
        if (!com.tencent.android.tpush.service.e.m.b(this.e)) {
            try {
                this.e = this.e.substring(0, 8);
                Long.parseLong(this.e);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                simpleDateFormat.setLenient(false);
                simpleDateFormat.parse(this.e);
            } catch (ParseException e) {
                com.tencent.android.tpush.a.a.c(a, "XGLocalMessage.getDate()", e);
                return new SimpleDateFormat("yyyyMMdd").format(new Date());
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(a, "XGLocalMessage.getDate()", e2);
                return new SimpleDateFormat("yyyyMMdd").format(new Date());
            }
        }
        return this.e;
    }

    public void setDate(String str) {
        this.e = str;
    }

    public void setRing(int i) {
        this.h = i;
    }

    public int getRing() {
        return this.h;
    }

    public void setVibrate(int i) {
        this.i = i;
    }

    public int getVibrate() {
        return this.i;
    }

    public void setLights(int i) {
        this.j = i;
    }

    public int getLights() {
        return this.j;
    }

    public void setIcon_type(int i) {
        this.k = i;
    }

    public int getIcon_type() {
        return this.k;
    }

    public void setStyle_id(int i) {
        this.l = i;
    }

    public int getStyle_id() {
        return this.l;
    }

    public void setRing_raw(String str) {
        this.m = str;
    }

    public String getRing_raw() {
        return this.m;
    }

    public void setIcon_res(String str) {
        this.n = str;
    }

    public String getIcon_res() {
        return this.n;
    }

    public void setSmall_icon(String str) {
        this.o = str;
    }

    public String getSmall_icon() {
        return this.o;
    }

    public void setAction_type(int i) {
        this.p = i;
    }

    public int getAction_type() {
        return this.p;
    }

    public void setActivity(String str) {
        this.q = str;
    }

    public String getActivity() {
        return this.q;
    }

    public void setUrl(String str) {
        this.r = str;
    }

    public String getUrl() {
        return this.r;
    }

    public void setIntent(String str) {
        this.s = str;
    }

    public String getIntent() {
        return this.s;
    }

    public void setPackageDownloadUrl(String str) {
        this.t = str;
    }

    public String getPackageDownloadUrl() {
        return this.t;
    }

    public void setPackageName(String str) {
        this.u = str;
    }

    public String getPackageName() {
        return this.u;
    }

    public int getNotificationId() {
        return this.x;
    }

    public void setNotificationId(int i) {
        this.x = i;
    }

    public long getMsgId() {
        return this.y;
    }

    public void setMsgId(long j) {
        this.y = j;
    }

    public long getBusiMsgId() {
        return this.z;
    }

    public void setBusiMsgId(long j) {
        this.z = j;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("XGLocalMessage [type=").append(this.b).append(", title=").append(this.c).append(", content=").append(this.d).append(", date=").append(this.e).append(", hour=").append(this.f).append(", min=").append(this.g).append(", builderId=").append(this.w).append(", msgid=").append(this.y).append(", busiMsgId=").append(this.z).append("]");
        return sb.toString();
    }
}
