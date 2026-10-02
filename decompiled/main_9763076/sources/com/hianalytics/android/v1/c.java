package com.hianalytics.android.v1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class c implements Runnable {
    boolean a;
    private Context b;
    private JSONObject c;

    public c(Context context, JSONObject jSONObject, boolean z) {
        this.b = context;
        this.c = jSONObject;
        this.a = z;
    }

    private String a(byte[] bArr) {
        String str = String.format("%016d", Long.valueOf(Math.abs(new SecureRandom().nextLong() % 10000000000000000L)));
        try {
            byte[] bArrA = com.hianalytics.android.b.a.b.a(str, bArr);
            byte[] bytes = str.getBytes(HTTP.UTF_8);
            RSAPublicKey rSAPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(new BigInteger("24907259431961377209480304447420314675278854956424737688244507998454379688588314890162679979323703303509240796245532111474023047392580178709435281576624542294613207523485034492914828565153172773053351891188090398210811384185501117117991603774176386409127476628856566065613009756131651597266262540467980974946876675842468600552312158771248419700603327630677244315755445967726919102965015263135288381740211593751262078285738436597133664401598420056690274760726854877181978220226448211936820860496708860964018593025172845041095854180953040116559241637133730839837036910305932797451786785855051024967644159284784940216337"), new BigInteger("65537")));
            if (rSAPublicKey == null) {
                throw new UnsupportedEncodingException();
            }
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING");
            cipher.init(1, rSAPublicKey);
            return "{\"vs\":\"" + com.hianalytics.android.b.a.a.e(this.b) + "\",\"ed\":\"" + com.hianalytics.android.b.a.a.b(bArrA) + "\",\"ek\":\"" + com.hianalytics.android.b.a.a.b(cipher.doFinal(bytes)) + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean a(JSONObject jSONObject, String str) throws Throwable {
        String strA;
        String lowerCase = str.toLowerCase();
        try {
            byte[] bArrA = com.hianalytics.android.b.a.a.a(jSONObject.toString().getBytes(HTTP.UTF_8));
            if (bArrA == null || (strA = a(bArrA)) == null) {
                return false;
            }
            try {
                byte[] bytes = strA.getBytes(HTTP.UTF_8);
                if (lowerCase.indexOf("https") >= 0) {
                    return false;
                }
                com.hianalytics.android.b.a.a.h();
                return b.a(str, bytes);
            } catch (UnsupportedEncodingException e) {
                new StringBuilder("UnsupportedEncodingException:").append(e.getMessage());
                com.hianalytics.android.b.a.a.h();
                return false;
            }
        } catch (UnsupportedEncodingException e2) {
            new StringBuilder("UnsupportedEncodingException:").append(e2.getMessage());
            com.hianalytics.android.b.a.a.h();
            return false;
        }
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        JSONArray jSONArray;
        JSONArray jSONArray2;
        try {
            if (this.c.getString("type") == null) {
                return;
            }
            Context context = this.b;
            JSONObject jSONObject = this.c;
            boolean z = this.a;
            Object objA = com.hianalytics.android.a.a.a.a(context);
            if (objA == null) {
                com.hianalytics.android.b.a.a.h();
                return;
            }
            JSONObject jSONObjectB = com.hianalytics.android.b.a.c.b(context, "cached");
            JSONObject jSONObject2 = new JSONObject();
            try {
                String string = jSONObject.getString("type");
                if (string != null) {
                    jSONObject.remove("type");
                    boolean z2 = true;
                    if (jSONObjectB == null) {
                        jSONObjectB = new JSONObject();
                        jSONArray = new JSONArray();
                    } else if (jSONObjectB.isNull(string)) {
                        jSONArray = new JSONArray();
                    } else {
                        z2 = false;
                        jSONArray = jSONObjectB.getJSONArray(string);
                    }
                    if (z && z2) {
                        com.hianalytics.android.b.a.a.h();
                        return;
                    }
                    if (!z) {
                        jSONArray.put(jSONObject);
                    }
                    JSONArray jSONArray3 = new JSONArray();
                    int length = jSONArray.length();
                    for (int i = 0; i <= length - 1; i++) {
                        JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                        if (jSONObject3.has("b")) {
                            JSONArray jSONArray4 = jSONObject3.getJSONArray("b");
                            if (jSONArray4 != null && jSONArray4.length() > 0) {
                                String[] strArrSplit = jSONArray4.getString(jSONArray4.length() - 1).split(",");
                                if (((System.currentTimeMillis() / 1000) - com.hianalytics.android.b.a.a.a(strArrSplit[1])) - Long.parseLong(strArrSplit[2]) < com.hianalytics.android.b.a.a.b().longValue()) {
                                    jSONArray3.put(jSONObject3);
                                } else {
                                    com.hianalytics.android.b.a.a.h();
                                }
                            }
                        } else if (jSONObject3.has("e") && (jSONArray2 = jSONObject3.getJSONArray("e")) != null && jSONArray2.length() > 0) {
                            if ((System.currentTimeMillis() / 1000) - com.hianalytics.android.b.a.a.a(jSONArray2.getString(jSONArray2.length() - 1).split(",")[2]) < com.hianalytics.android.b.a.a.b().longValue()) {
                                jSONArray3.put(jSONObject3);
                            } else {
                                com.hianalytics.android.b.a.a.h();
                            }
                        }
                    }
                    if (jSONArray3.length() <= 0) {
                        com.hianalytics.android.b.a.a.h();
                        return;
                    }
                    jSONObjectB.remove(string);
                    jSONObjectB.put(string, jSONArray3);
                    jSONObject2.put("g", objA);
                    jSONObject2.put(NotifyType.SOUND, jSONArray3);
                    new StringBuilder("message=").append(jSONObject2.toString());
                    com.hianalytics.android.b.a.a.h();
                    if (!a(jSONObject2, com.hianalytics.android.b.a.a.i())) {
                        com.hianalytics.android.b.a.c.a(context, jSONObjectB, "cached");
                        com.hianalytics.android.b.a.a.h();
                        return;
                    }
                    SharedPreferences sharedPreferencesA = com.hianalytics.android.b.a.c.a(context, "flag");
                    if (com.hianalytics.android.b.a.a.f(context)) {
                        SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
                        editorEdit.putString("rom_version", Build.DISPLAY);
                        editorEdit.commit();
                    }
                    com.hianalytics.android.b.a.c.c(context, "cached");
                    com.hianalytics.android.b.a.a.h();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                com.hianalytics.android.b.a.c.c(context, "cached");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            com.hianalytics.android.b.a.c.c(this.b, "cached");
        }
    }
}
