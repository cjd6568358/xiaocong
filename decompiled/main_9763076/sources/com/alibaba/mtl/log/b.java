package com.alibaba.mtl.log;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.mtl.log.e.i;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.tencent.android.tpush.common.Constants;
import com.ut.mini.UTAnalytics;
import com.ut.mini.core.appstatus.UTMCAppStatusRegHelper;
import com.ut.mini.core.sign.IUTRequestAuthentication;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: UTMCStatConfig.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static b a = new b();
    private Context mContext = null;
    private String C = null;
    private String D = null;
    private String E = null;
    private String F = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Application f25a = null;
    private String G = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private IUTRequestAuthentication f26a = null;
    private boolean s = false;
    private boolean t = false;

    private b() {
    }

    public static b a() {
        return a;
    }

    public void setAppVersion(String aAppVersion) {
        this.G = aAppVersion;
    }

    public String getAppVersion() {
        return this.G;
    }

    public void turnOnDebug() {
        i.d(true);
    }

    private void c(String str) {
        this.C = str;
        if (!TextUtils.isEmpty(str)) {
            this.D = str;
        }
        if (!TextUtils.isEmpty(str) && this.mContext != null) {
            try {
                SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences("UTCommon", 0).edit();
                editorEdit.putString("_lun", new String(com.alibaba.mtl.log.e.c.encode(str.getBytes(HTTP.UTF_8), 2)));
                editorEdit.commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void d(String str) {
        this.E = str;
        if (!TextUtils.isEmpty(str)) {
            this.F = str;
        }
        if (!TextUtils.isEmpty(str) && this.mContext != null) {
            try {
                SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences("UTCommon", 0).edit();
                editorEdit.putString("_luid", new String(com.alibaba.mtl.log.e.c.encode(str.getBytes(HTTP.UTF_8), 2)));
                editorEdit.commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUserAccount(String aUsernick, String aUserid) {
        c(aUsernick);
        d(aUserid);
        if (!TextUtils.isEmpty(aUsernick)) {
            UTAnalytics.getInstance().getDefaultTracker().send(new UTOriginalCustomHitBuilder("UT", ACConstants.TAG_UD_WIND_MODE1, aUsernick, aUserid, (String) null, (Map) null).build());
        }
    }

    public void setContext(Context aContext) {
        if (aContext != null) {
            this.mContext = aContext;
            SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("UTCommon", 0);
            String string = sharedPreferences.getString("_lun", Constants.MAIN_VERSION_TAG);
            if (!TextUtils.isEmpty(string)) {
                try {
                    this.D = new String(com.alibaba.mtl.log.e.c.decode(string.getBytes(), 2), HTTP.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            String string2 = sharedPreferences.getString("_luid", Constants.MAIN_VERSION_TAG);
            if (!TextUtils.isEmpty(string2)) {
                try {
                    this.F = new String(com.alibaba.mtl.log.e.c.decode(string2.getBytes(), 2), HTTP.UTF_8);
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }
            }
        }
        o();
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setAppApplicationInstance(Application aApplicationInstance) {
        this.f25a = aApplicationInstance;
        o();
    }

    private void o() {
        if (!this.s && Build.VERSION.SDK_INT >= 14) {
            try {
                if (a().m20a() != null) {
                    UTMCAppStatusRegHelper.registeActivityLifecycleCallbacks(a().m20a());
                    this.s = true;
                } else {
                    UTMCAppStatusRegHelper.registeActivityLifecycleCallbacks((Application) a().getContext().getApplicationContext());
                    this.s = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("UTEngine", "You need set a application instance for UT.");
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public Application m20a() {
        return this.f25a;
    }
}
