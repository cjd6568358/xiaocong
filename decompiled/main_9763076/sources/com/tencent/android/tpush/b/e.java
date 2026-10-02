package com.tencent.android.tpush.b;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.encrypt.Rijndael;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    private static String f = null;
    private long a = 0;
    private long b = 0;
    private String c = Constants.MAIN_VERSION_TAG;
    private String d = null;
    private Context e;
    private Intent g;

    public e(Context context, Intent intent) {
        this.e = null;
        this.g = null;
        this.e = context.getApplicationContext();
        this.g = intent;
    }

    private boolean a() {
        if (f == null) {
            f = com.tencent.android.tpush.common.n.a(this.e, ".xg.push.cm.vrf", Constants.MAIN_VERSION_TAG);
        }
        if (f.contains(this.c)) {
            return true;
        }
        f = this.c + "," + f;
        if (f.length() > 10240) {
            f = f.substring(0, 2048);
        }
        com.tencent.android.tpush.common.n.b(this.e, ".xg.push.cm.vrf", f);
        return false;
    }

    public boolean a(n nVar, long j, long j2, long j3) {
        String string;
        a aVarG = nVar.g();
        String stringExtra = this.g.getStringExtra("title");
        if (j3 > 0 && stringExtra != null) {
            JSONObject jSONObject = new JSONObject(Rijndael.decrypt(stringExtra));
            com.tencent.android.tpush.a.a.e(Constants.LogTag, "title encry obj:" + jSONObject);
            this.c = com.tencent.android.tpush.service.channel.security.f.a(com.tencent.android.tpush.service.channel.security.a.a(jSONObject.getString("cipher"), 0));
            String[] strArrSplit = this.c.split("_");
            this.b = Long.valueOf(strArrSplit[0]).longValue();
            this.d = strArrSplit[1].toUpperCase();
            this.a = Long.valueOf(strArrSplit[2]).longValue();
            boolean z = true;
            if (this.b != j2) {
                z = false;
            } else if (j2 == 0) {
                z = j == this.a;
            }
            return z && !a() && j2 == this.b && aVarG.a().equalsIgnoreCase(this.d);
        }
        StringBuilder sb = new StringBuilder(ParserConstants.LSHIFTASSIGN);
        sb.append(nVar.c()).append(j3).append(this.e.getPackageName()).append(TextUtils.isEmpty(aVarG.e()) ? Constants.MAIN_VERSION_TAG : aVarG.e()).append(TextUtils.isEmpty(aVarG.f()) ? Constants.MAIN_VERSION_TAG : aVarG.f());
        String strG = aVarG.g();
        if (TextUtils.isEmpty(strG) || new JSONObject(strG).length() == 0) {
            string = Constants.MAIN_VERSION_TAG;
        } else {
            string = new JSONObject(strG).toString();
        }
        sb.append(string);
        if (aVarG instanceof f) {
            g gVarM = ((f) aVarG).m();
            sb.append(TextUtils.isEmpty(gVarM.f) ? Constants.MAIN_VERSION_TAG : gVarM.f).append(TextUtils.isEmpty(gVarM.d) ? Constants.MAIN_VERSION_TAG : gVarM.d).append(TextUtils.isEmpty(gVarM.b) ? Constants.MAIN_VERSION_TAG : gVarM.b);
        }
        String string2 = sb.toString();
        String str = Constants.LOCAL_MESSAGE_FLAG + com.tencent.android.tpush.encrypt.a.a(string2);
        long jA = com.tencent.android.tpush.common.n.a(this.e, str, 0L);
        com.tencent.android.tpush.common.n.a(this.e, str);
        long jCurrentTimeMillis = jA - System.currentTimeMillis();
        com.tencent.android.tpush.a.a.c(Constants.LogTag, string2 + ",localMsgTag:" + str + ",diff:" + jCurrentTimeMillis);
        return jCurrentTimeMillis >= 0;
    }
}
