package com.baidu.location.a;

import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class i {
    public static String c = null;
    public com.baidu.location.b.g a = null;
    public com.baidu.location.b.a b = null;
    private boolean e = true;
    private boolean f = true;
    private boolean g = false;
    final Handler d = new a();
    private String h = null;
    private String i = null;

    public class a extends Handler {
        public a() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (com.baidu.location.f.isServing) {
                switch (message.what) {
                    case 21:
                        i.this.a(message);
                        break;
                    case 62:
                    case 63:
                        i.this.a();
                        break;
                }
            }
        }
    }

    class b extends com.baidu.location.d.e {
        String a = null;
        String b = null;

        public b() {
            this.k = new HashMap();
        }

        @Override // com.baidu.location.d.e
        public void a() {
            this.h = com.baidu.location.d.j.c();
            if ((com.baidu.location.d.j.h || com.baidu.location.d.j.i) && i.this.h != null && i.this.i != null) {
                this.b += String.format(Locale.CHINA, "&ki=%s&sn=%s", i.this.h, i.this.i);
            }
            String strEncodeTp4 = Jni.encodeTp4(this.b);
            this.b = null;
            if (this.a == null) {
                this.a = v.b();
            }
            this.k.put("bloc", strEncodeTp4);
            if (this.a != null) {
                this.k.put("up", this.a);
            }
            this.k.put("trtm", String.format(Locale.CHINA, "%d", Long.valueOf(System.currentTimeMillis())));
        }

        public void a(String str) {
            this.b = str;
            b(com.baidu.location.d.j.f);
        }

        @Override // com.baidu.location.d.e
        public void a(boolean z) {
            BDLocation bDLocation;
            if (!z || this.j == null) {
                Message messageObtainMessage = i.this.d.obtainMessage(63);
                messageObtainMessage.obj = "HttpStatus error";
                messageObtainMessage.sendToTarget();
            } else {
                try {
                    String str = this.j;
                    i.c = str;
                    try {
                        bDLocation = new BDLocation(str);
                        if (bDLocation.getLocType() == 161) {
                            h.a().a(str);
                        }
                        bDLocation.setOperators(com.baidu.location.b.b.a().h());
                        if (n.a().d()) {
                            bDLocation.setDirection(n.a().e());
                        }
                    } catch (Exception e) {
                        bDLocation = new BDLocation();
                        bDLocation.setLocType(0);
                    }
                    this.a = null;
                    if (bDLocation.getLocType() == 0 && bDLocation.getLatitude() == Double.MIN_VALUE && bDLocation.getLongitude() == Double.MIN_VALUE) {
                        Message messageObtainMessage2 = i.this.d.obtainMessage(63);
                        messageObtainMessage2.obj = "HttpStatus error";
                        messageObtainMessage2.sendToTarget();
                    } else {
                        Message messageObtainMessage3 = i.this.d.obtainMessage(21);
                        messageObtainMessage3.obj = bDLocation;
                        messageObtainMessage3.sendToTarget();
                    }
                } catch (Exception e2) {
                    Message messageObtainMessage4 = i.this.d.obtainMessage(63);
                    messageObtainMessage4.obj = "HttpStatus error";
                    messageObtainMessage4.sendToTarget();
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    public String a(String str) {
        String strL;
        if (this.h == null) {
            this.h = j.b(com.baidu.location.f.getServiceContext());
        }
        if (this.i == null) {
            this.i = j.c(com.baidu.location.f.getServiceContext());
        }
        if (this.b == null || !this.b.a()) {
            this.b = com.baidu.location.b.b.a().f();
        }
        if (this.a == null || !this.a.i()) {
            this.a = com.baidu.location.b.h.a().o();
        }
        Location locationG = com.baidu.location.b.e.a().i() ? com.baidu.location.b.e.a().g() : null;
        if ((this.b == null || this.b.d() || this.b.c()) && ((this.a == null || this.a.a() == 0) && locationG == null)) {
            return null;
        }
        String strB = b();
        if (h.a().d() == -2) {
            strB = strB + "&imo=1";
        }
        int iB = com.baidu.location.d.j.b(com.baidu.location.f.getServiceContext());
        if (iB >= 0) {
            strB = strB + "&lmd=" + iB;
        }
        String str2 = ((this.a == null || this.a.a() == 0) && (strL = com.baidu.location.b.h.a().l()) != null) ? strL + strB : strB;
        if (!this.f) {
            return com.baidu.location.d.j.a(this.b, this.a, locationG, str2, 0);
        }
        this.f = false;
        return com.baidu.location.d.j.a(this.b, this.a, locationG, str2, 0, true);
    }

    public abstract void a();

    public abstract void a(Message message);

    public String b() {
        String strC = com.baidu.location.a.a.a().c();
        String str = com.baidu.location.b.h.i() ? "&cn=32" : String.format(Locale.CHINA, "&cn=%d", Integer.valueOf(com.baidu.location.b.b.a().e()));
        if (this.e) {
            this.e = false;
            String strQ = com.baidu.location.b.h.a().q();
            if (!TextUtils.isEmpty(strQ) && !strQ.equals("02:00:00:00:00:00")) {
                str = String.format(Locale.CHINA, "%s&mac=%s", str, strQ.replace(":", Constants.MAIN_VERSION_TAG));
            }
            if (Build.VERSION.SDK_INT > 17) {
            }
        } else if (!this.g) {
            String strF = v.f();
            if (strF != null) {
                str = str + strF;
            }
            this.g = true;
        }
        return str + strC;
    }
}
