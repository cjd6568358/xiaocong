package com.tencent.mid.api;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mid.b.g;
import com.tencent.mid.util.Util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MidProvider extends ContentProvider {
    public static final int CMD_GET_PRIVATE_MID = 1;
    public static final int CMD_GET_PRIVATE_MID_ENTITY = 2;
    public static final int CMD_GET_PRIVATE_NEW_VERSION_MID_ENTITY = 3;
    public static final int CMD_INSERT_NEW_VERSION_MID_ENTITY = 10;
    public static final int CMD_INSERT_NEW_VERSION_MID_OLD_ENTITY = 11;

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        String string = null;
        String lastPathSegment = uri.getLastPathSegment();
        Context applicationContext = getContext().getApplicationContext();
        applicationContext.getPackageName();
        if (Util.isEmpty(lastPathSegment)) {
            return "-1";
        }
        try {
            switch (Integer.parseInt(lastPathSegment)) {
                case 1:
                    MidEntity midEntityI = g.a(applicationContext).i();
                    if (midEntityI != null) {
                        string = midEntityI.getMid();
                    }
                    break;
                case 2:
                    MidEntity midEntityI2 = g.a(applicationContext).i();
                    if (midEntityI2 != null) {
                        midEntityI2.setImei(Constants.MAIN_VERSION_TAG);
                        midEntityI2.setImsi(Constants.MAIN_VERSION_TAG);
                        midEntityI2.setMac(Constants.MAIN_VERSION_TAG);
                        string = midEntityI2.toString();
                    }
                    break;
                case 3:
                    MidEntity midEntityC = g.a(applicationContext).c();
                    if (midEntityC != null) {
                        midEntityC.setImei(Constants.MAIN_VERSION_TAG);
                        midEntityC.setImsi(Constants.MAIN_VERSION_TAG);
                        midEntityC.setMac(Constants.MAIN_VERSION_TAG);
                        string = midEntityC.toString();
                    }
                    break;
                default:
                    string = Constants.MAIN_VERSION_TAG;
                    break;
            }
            return string;
        } catch (Throwable th) {
            th.printStackTrace();
            return "-2";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        String lastPathSegment = uri.getLastPathSegment();
        Context applicationContext = getContext().getApplicationContext();
        if (applicationContext != null) {
            applicationContext.getPackageName();
            if (!Util.isEmpty(lastPathSegment)) {
                try {
                    switch (Integer.parseInt(lastPathSegment)) {
                        case 10:
                            try {
                                String asString = contentValues.getAsString("mid");
                                if (!Util.isMidValid(MidService.getLocalMidOnly(getContext().getApplicationContext()))) {
                                    g.a(applicationContext).b(MidEntity.parse(asString), false);
                                }
                            } catch (Throwable th) {
                            }
                            break;
                        case 11:
                            try {
                                String asString2 = contentValues.getAsString("mid");
                                if (!Util.isMidValid(MidService.getLocalMidOnly(getContext().getApplicationContext()))) {
                                    g.a(applicationContext).a(MidEntity.parse(asString2), false);
                                }
                            } catch (Throwable th2) {
                            }
                            break;
                    }
                } catch (Throwable th3) {
                    th3.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return false;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
