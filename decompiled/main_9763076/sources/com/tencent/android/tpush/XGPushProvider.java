package com.tencent.android.tpush;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.cache.CacheManager;
import java.net.URISyntaxException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPushProvider extends ContentProvider {
    public static final String AUTH_PRIX = ".AUTH_XGPUSH";
    public static final String STR_GET_PULLUP = "pullupxg";
    public static final String TAG = XGPushProvider.class.getSimpleName();
    private Context a = null;
    private String b = null;
    private UriMatcher c = new UriMatcher(-1);

    private void a() {
        this.a = getContext().getApplicationContext();
        com.tencent.android.tpush.common.t.a(this.a);
        this.b = this.a.getPackageName();
        String str = this.b + AUTH_PRIX;
        this.c.addURI(str, "config", 10);
        this.c.addURI(str, "config/#", 11);
        this.c.addURI(str, "msg", 20);
        this.c.addURI(str, "msg/#", 21);
        this.c.addURI(str, "heart", 30);
        this.c.addURI(str, "heart/#", 31);
        this.c.addURI(str, "feedback", 40);
        this.c.addURI(str, "feedback/#", 41);
        this.c.addURI(str, Constants.FLAG_TOKEN, 50);
        this.c.addURI(str, "register", 60);
        this.c.addURI(str, "insert_mid_new", 80);
        this.c.addURI(str, "insert_mid_old", 81);
        this.c.addURI(str, STR_GET_PULLUP, 70);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        a();
        com.tencent.android.tpush.a.a.e(Constants.LogTag, "XGPushProvider onCreate");
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        com.tencent.android.tpush.a.a.e(Constants.LogTag, "query uri:" + uri + ",projection:" + strArr + ",selection:" + str + ",selectionArgs:" + strArr2 + ",sortOrder:" + str2);
        return null;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        int iMatch = this.c.match(uri);
        com.tencent.android.tpush.a.a.e(Constants.LogTag, "getType uri:" + uri + ",match:" + iMatch);
        switch (iMatch) {
            case 10:
                return "CONFIG";
            case 11:
                return "CONFIG_ALL";
            case 30:
                com.tencent.android.tpush.b.i.a(this.a).a(false);
                return null;
            case 31:
                return "HEARTBEAT_ALL";
            case 50:
                return Rijndael.encrypt(com.tencent.android.tpush.stat.b.i.a(this.a).c());
            case 60:
                RegisterEntity currentAppRegisterEntity = CacheManager.getCurrentAppRegisterEntity(this.a);
                com.tencent.android.tpush.a.a.e(Constants.LogTag, "get RegisterEntity:" + currentAppRegisterEntity);
                return Rijndael.encrypt(RegisterEntity.a(currentAppRegisterEntity));
            case 70:
                com.tencent.android.tpush.a.a.e(Constants.LogTag, "Start XGService by provider");
                com.tencent.android.tpush.service.n.a(this.a);
                return null;
            default:
                return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        int iMatch = this.c.match(uri);
        com.tencent.android.tpush.a.a.e(Constants.LogTag, "insert uri:" + uri + ",match:" + iMatch + ",values:" + contentValues);
        switch (iMatch) {
            case 20:
                try {
                    Intent uri2 = Intent.parseUri(contentValues.getAsString(SettingsContentProvider.KEY), 0);
                    uri2.addCategory("android.intent.category.BROWSABLE");
                    uri2.setComponent(null);
                    if (Build.VERSION.SDK_INT >= 15) {
                        try {
                            uri2.getClass().getMethod("setSelector", Intent.class).invoke(uri2, null);
                        } catch (Exception e) {
                            com.tencent.android.tpush.a.a.b(Constants.LogTag, "invoke intent.setComponent error.", e);
                        }
                    }
                    com.tencent.android.tpush.b.i.a(this.a).b(uri2);
                    break;
                } catch (URISyntaxException e2) {
                    e2.printStackTrace();
                }
                return null;
            case 21:
            default:
                return null;
            case 80:
                try {
                    com.tencent.android.tpush.stat.b.i.a(this.a).a(com.tencent.android.tpush.stat.b.d.a(contentValues.getAsString("mid")), false);
                    break;
                } catch (Throwable th) {
                }
                return null;
            case 81:
                try {
                    com.tencent.android.tpush.stat.b.i.a(this.a).b(com.tencent.android.tpush.stat.b.d.a(contentValues.getAsString("mid")), false);
                    break;
                } catch (Throwable th2) {
                }
                return null;
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        com.tencent.android.tpush.a.a.g(TAG, "delete uri:" + uri + ",selection:" + str + ",selectionArgs:" + strArr);
        return 0;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int iMatch = this.c.match(uri);
        com.tencent.android.tpush.a.a.e(Constants.LogTag, "update uri:" + uri + ",values:" + contentValues + ",selection:" + str + ",selectionArgs:" + strArr + ",match:" + iMatch);
        switch (iMatch) {
            case 40:
                com.tencent.android.tpush.a.a.e(Constants.LogTag, "feeback: " + Rijndael.decrypt(contentValues.getAsString("feedback")));
            case 41:
            default:
                return 0;
        }
    }
}
