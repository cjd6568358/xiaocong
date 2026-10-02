package com.ta.utdid2.device;

import android.content.Context;
import android.provider.Settings;
import com.ta.utdid2.b.a.g;
import com.ta.utdid2.b.a.i;
import com.tencent.android.tpush.common.Constants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: compiled from: UTUtdid.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private com.ta.utdid2.c.a.c f123a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private d f124a;
    private com.ta.utdid2.c.a.c b;
    private String m;
    private Context mContext;
    private String n;
    private static final Object f = new Object();
    private static c a = null;
    private static final String o = ".UTSystemConfig" + File.separator + "Global";
    private String l = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Pattern f125a = Pattern.compile("[^0-9a-zA-Z=/+]+");

    public c(Context context) {
        this.mContext = null;
        this.f124a = null;
        this.m = "xx_utdid_key";
        this.n = "xx_utdid_domain";
        this.f123a = null;
        this.b = null;
        this.mContext = context;
        this.b = new com.ta.utdid2.c.a.c(context, o, "Alvin2", false, true);
        this.f123a = new com.ta.utdid2.c.a.c(context, ".DataStorage", "ContextData", false, true);
        this.f124a = new d();
        this.m = String.format("K_%d", Integer.valueOf(i.a(this.m)));
        this.n = String.format("D_%d", Integer.valueOf(i.a(this.n)));
    }

    private void d() {
        boolean z = true;
        if (this.b != null) {
            if (i.m99a(this.b.getString("UTDID2"))) {
                String string = this.b.getString("UTDID");
                if (!i.m99a(string)) {
                    f(string);
                }
            }
            boolean z2 = false;
            if (!i.m99a(this.b.getString("DID"))) {
                this.b.remove("DID");
                z2 = true;
            }
            if (!i.m99a(this.b.getString("EI"))) {
                this.b.remove("EI");
                z2 = true;
            }
            if (i.m99a(this.b.getString("SI"))) {
                z = z2;
            } else {
                this.b.remove("SI");
            }
            if (z) {
                this.b.commit();
            }
        }
    }

    public static c a(Context context) {
        if (context != null && a == null) {
            synchronized (f) {
                if (a == null) {
                    a = new c(context);
                    a.d();
                }
            }
        }
        return a;
    }

    private void f(String str) {
        if (b(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.length() == 24 && this.b != null) {
                this.b.putString("UTDID2", str);
                this.b.commit();
            }
        }
    }

    private void g(String str) {
        if (str != null && this.f123a != null && !str.equals(this.f123a.getString(this.m))) {
            this.f123a.putString(this.m, str);
            this.f123a.commit();
        }
    }

    private void h(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0 && b(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (24 == str.length()) {
                String string = null;
                try {
                    string = Settings.System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
                } catch (Exception e) {
                }
                if (!b(string)) {
                    try {
                        Settings.System.putString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk", str);
                    } catch (Exception e2) {
                    }
                }
            }
        }
    }

    private void i(String str) {
        String string = null;
        try {
            string = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
        } catch (Exception e) {
        }
        if (!str.equals(string)) {
            try {
                Settings.System.putString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp", str);
            } catch (Exception e2) {
            }
        }
    }

    private void j(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0 && str != null) {
            i(str);
        }
    }

    private String g() {
        if (this.b != null) {
            String string = this.b.getString("UTDID2");
            if (!i.m99a(string) && this.f124a.a(string) != null) {
                return string;
            }
        }
        return null;
    }

    private boolean b(String str) {
        if (str == null) {
            return false;
        }
        if (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }
        return 24 == str.length() && !this.f125a.matcher(str).find();
    }

    public synchronized String getValue() {
        return this.l != null ? this.l : h();
    }

    /* JADX WARN: Code duplicated, block: B:70:0x0119  */
    public synchronized String h() {
        String strG;
        String string;
        String string2;
        strG = Constants.MAIN_VERSION_TAG;
        try {
            strG = Settings.System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
        } catch (Exception e) {
        }
        if (!b(strG)) {
            e eVar = new e();
            boolean z = false;
            try {
                string = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
            } catch (Exception e2) {
                string = null;
            }
            if (!i.m99a(string)) {
                strG = eVar.c(string);
                if (b(strG)) {
                    h(strG);
                } else {
                    String strB = eVar.b(string);
                    if (b(strB)) {
                        String strA = this.f124a.a(strB);
                        if (i.m99a(strA)) {
                            string2 = string;
                        } else {
                            j(strA);
                            try {
                                string2 = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
                            } catch (Exception e3) {
                                string2 = string;
                            }
                        }
                    } else {
                        string2 = string;
                    }
                    String strB2 = this.f124a.b(string2);
                    if (b(strB2)) {
                        this.l = strB2;
                        f(strB2);
                        g(string2);
                        h(this.l);
                        strG = this.l;
                    }
                }
            } else {
                z = true;
            }
            strG = g();
            if (b(strG)) {
                String strA2 = this.f124a.a(strG);
                if (z) {
                    j(strA2);
                }
                h(strG);
                g(strA2);
                this.l = strG;
            } else {
                String string3 = this.f123a.getString(this.m);
                if (!i.m99a(string3)) {
                    String strB3 = eVar.b(string3);
                    if (!b(strB3)) {
                        strB3 = this.f124a.b(string3);
                    }
                    if (b(strB3)) {
                        String strA3 = this.f124a.a(strB3);
                        if (!i.m99a(strB3)) {
                            this.l = strB3;
                            if (z) {
                                j(strA3);
                            }
                            f(this.l);
                            strG = this.l;
                        }
                    }
                }
                try {
                    byte[] bArrA = a();
                    if (bArrA != null) {
                        this.l = com.ta.utdid2.b.a.b.encodeToString(bArrA, 2);
                        f(this.l);
                        String strC = this.f124a.c(bArrA);
                        if (strC != null) {
                            if (z) {
                                j(strC);
                            }
                            g(strC);
                        }
                        strG = this.l;
                    } else {
                        strG = null;
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        }
        return strG;
    }

    private final byte[] a() throws Exception {
        String string;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNextInt = new Random().nextInt();
        byte[] bytes = com.ta.utdid2.b.a.e.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = com.ta.utdid2.b.a.e.getBytes(iNextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            string = g.a(this.mContext);
        } catch (Exception e) {
            string = new StringBuilder().append(new Random().nextInt()).toString();
        }
        byteArrayOutputStream.write(com.ta.utdid2.b.a.e.getBytes(i.a(string)), 0, 4);
        byteArrayOutputStream.write(com.ta.utdid2.b.a.e.getBytes(i.a(b(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    private static String b(byte[] bArr) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec("d6fc3a4a06adbde89223bvefedc24fecde188aaa9161".getBytes(), mac.getAlgorithm()));
        return com.ta.utdid2.b.a.b.encodeToString(mac.doFinal(bArr), 2);
    }
}
