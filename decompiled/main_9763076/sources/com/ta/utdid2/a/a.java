package com.ta.utdid2.a;

import android.content.Context;
import android.util.Log;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.ta.utdid2.b.a.f;
import com.ta.utdid2.b.a.i;
import com.ta.utdid2.b.a.j;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: AidManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private Context mContext;
    private static a a = null;
    private static final String TAG = a.class.getName();

    public static synchronized a a(Context context) {
        if (a == null) {
            a = new a(context);
        }
        return a;
    }

    private a(Context context) {
        this.mContext = context;
    }

    public void a(String str, String str2, String str3, com.ut.device.a aVar) {
        if (aVar == null) {
            Log.e(TAG, "callback is null!");
            return;
        }
        if (this.mContext == null || i.m99a(str) || i.m99a(str2)) {
            Log.e(TAG, "mContext:" + this.mContext + "; callback:" + aVar + "; has appName:" + (!i.m99a(str)) + "; has token:" + (i.m99a(str2) ? false : true));
            aVar.a(1002, Constants.MAIN_VERSION_TAG);
            return;
        }
        String strM96a = c.m96a(this.mContext, str, str2);
        if (!i.m99a(strM96a) && j.a(c.a(this.mContext, str, str2), 1)) {
            aVar.a(1001, strM96a);
        } else if (f.m98a(this.mContext)) {
            b.a(this.mContext).a(str, str2, str3, strM96a, aVar);
        } else {
            aVar.a(ACConstants.TAG_TEMPERATURE1, strM96a);
        }
    }

    public String a(String str, String str2, String str3) {
        if (this.mContext == null || i.m99a(str) || i.m99a(str2)) {
            Log.e(TAG, "mContext:" + this.mContext + "; has appName:" + (!i.m99a(str)) + "; has token:" + (i.m99a(str2) ? false : true));
            return Constants.MAIN_VERSION_TAG;
        }
        String strM96a = c.m96a(this.mContext, str, str2);
        if ((i.m99a(strM96a) || !j.a(c.a(this.mContext, str, str2), 1)) && f.m98a(this.mContext)) {
            return b(str, str2, str3);
        }
        return strM96a;
    }

    private synchronized String b(String str, String str2, String str3) {
        String strA;
        if (this.mContext == null) {
            Log.e(TAG, "no context!");
            strA = Constants.MAIN_VERSION_TAG;
        } else {
            strA = Constants.MAIN_VERSION_TAG;
            if (f.m98a(this.mContext)) {
                strA = b.a(this.mContext).a(str, str2, str3, c.m96a(this.mContext, str, str2));
            }
            c.a(this.mContext, str, strA, str2);
        }
        return strA;
    }
}
