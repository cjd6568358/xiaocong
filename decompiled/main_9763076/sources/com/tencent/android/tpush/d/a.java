package com.tencent.android.tpush.d;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.e.m;
import java.net.URISyntaxException;
import java.util.ArrayList;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static long a = 0;
    private static long b = 172800000;
    private static com.tencent.android.tpush.d.a.a c;

    private static com.tencent.android.tpush.d.a.a d(Context context) {
        if (c == null) {
            synchronized (a.class) {
                if (c == null) {
                    c = new com.tencent.android.tpush.d.a.a(context.getApplicationContext());
                }
            }
        }
        return c;
    }

    public static boolean a(Context context, Intent intent) {
        boolean z = true;
        long longExtra = intent.getLongExtra(MessageKey.MSG_ID, -1L);
        ContentValues contentValues = new ContentValues();
        contentValues.put("msgid", Long.valueOf(longExtra));
        contentValues.put("message", Rijndael.encrypt(intent.toUri(1)));
        contentValues.put("time", Long.valueOf(m.b(intent)));
        contentValues.put("busiid", Long.valueOf(intent.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, 0L)));
        contentValues.put("showedtime", (Integer) 0);
        contentValues.put("status", (Integer) 0);
        try {
            SQLiteDatabase writableDatabase = d(context).getWritableDatabase();
            if (writableDatabase.insert("messagetoshow", null, contentValues) <= 0) {
                com.tencent.android.tpush.a.a.i("MessageInfoManager", "addCacheMessage Error! ");
                z = false;
            }
            writableDatabase.close();
            return z;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i("MessageInfoManager", "addNewCacheMessage Error! " + th);
            return false;
        }
    }

    public static boolean a(Context context, long j) {
        e(context);
        return a(context, j, 1);
    }

    public static boolean b(Context context, long j) {
        e(context);
        return a(context, j, 2);
    }

    public static boolean c(Context context, long j) {
        return a(context, j, 3);
    }

    public static boolean d(Context context, long j) {
        return a(context, j, 4);
    }

    private static boolean a(Context context, long j, int i) {
        boolean z = true;
        ContentValues contentValues = new ContentValues();
        if (i == 1 || i == 2) {
            contentValues.put("showedtime", Long.valueOf(System.currentTimeMillis()));
        }
        contentValues.put("status", Integer.valueOf(i));
        try {
            SQLiteDatabase writableDatabase = d(context).getWritableDatabase();
            if (writableDatabase.update("messagetoshow", contentValues, "msgid=?", new String[]{j + Constants.MAIN_VERSION_TAG}) <= 0) {
                com.tencent.android.tpush.a.a.i("MessageInfoManager", "updateCacheMessage Error! msgId:" + j + ", status:" + i);
                z = false;
            }
            writableDatabase.close();
            return z;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i("MessageInfoManager", "updateCacheMessage Error! " + th);
            return false;
        }
    }

    private static boolean e(Context context) {
        boolean z = true;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (a != 0 && jCurrentTimeMillis - a <= 86400000) {
            return true;
        }
        a = jCurrentTimeMillis;
        long j = jCurrentTimeMillis - b;
        try {
            SQLiteDatabase writableDatabase = d(context).getWritableDatabase();
            if (writableDatabase.delete("messagetoshow", "status >= ? AND showedtime < ? ", new String[]{"1", j + Constants.MAIN_VERSION_TAG}) <= 0) {
                com.tencent.android.tpush.a.a.i("MessageInfoManager", "delOldShowedCacheMessage Error! toDelTime: " + j);
                z = false;
            }
            writableDatabase.close();
            return z;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i("MessageInfoManager", "delOldShowedCacheMessage Error! " + th);
            return false;
        }
    }

    public static boolean e(Context context, long j) {
        boolean z = false;
        SQLiteDatabase writableDatabase = null;
        try {
            try {
                writableDatabase = d(context).getWritableDatabase();
                if (writableDatabase.delete("messagetoshow", "msgid=?", new String[]{j + Constants.MAIN_VERSION_TAG}) <= 0) {
                    com.tencent.android.tpush.a.a.i("MessageInfoManager", "delCacheMessage Error! msgid to delete: " + j);
                    if (writableDatabase != null) {
                        try {
                            writableDatabase.close();
                        } catch (Throwable th) {
                        }
                    }
                } else {
                    if (writableDatabase != null) {
                        try {
                            writableDatabase.close();
                        } catch (Throwable th2) {
                        }
                    }
                    z = true;
                }
            } catch (Throwable th3) {
                com.tencent.android.tpush.a.a.i("MessageInfoManager", "delCacheMessage Error! msgid to delete: " + j + th3);
                if (writableDatabase != null) {
                    try {
                        writableDatabase.close();
                    } catch (Throwable th4) {
                    }
                }
            }
            return z;
        } catch (Throwable th5) {
            if (writableDatabase != null) {
                try {
                    writableDatabase.close();
                } catch (Throwable th6) {
                }
            }
            throw th5;
        }
    }

    public static boolean a(Context context) {
        boolean z = true;
        try {
            SQLiteDatabase writableDatabase = d(context).getWritableDatabase();
            if (writableDatabase.delete("messagetoshow", null, null) <= 0) {
                com.tencent.android.tpush.a.a.g("MessageInfoManager", "delAllCacheMessage but no mssgage in db");
                z = false;
            }
            writableDatabase.close();
            return z;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i("MessageInfoManager", "delAllCacheMessage Error! " + th);
            return false;
        }
    }

    public static boolean f(Context context, long j) {
        boolean z = true;
        try {
            SQLiteDatabase writableDatabase = d(context).getWritableDatabase();
            if (writableDatabase.delete("messagetoshow", "busiid=?", new String[]{j + Constants.MAIN_VERSION_TAG}) <= 0) {
                com.tencent.android.tpush.a.a.i("MessageInfoManager", "delCacheMessageByBusiId Error! msgid to delete which busiId = : " + j);
                z = false;
            }
            writableDatabase.close();
            return z;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i("MessageInfoManager", "delCacheMessageByBusiId Error! " + th);
            return false;
        }
    }

    public static boolean b(Context context) {
        boolean z = true;
        try {
            SQLiteDatabase writableDatabase = d(context).getWritableDatabase();
            if (writableDatabase.delete("messagetoshow", "msgid < 0", null) <= 0) {
                com.tencent.android.tpush.a.a.i("MessageInfoManager", "deleteAllLocalCacheMsgIntent Error! ");
                z = false;
            }
            writableDatabase.close();
            return z;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i("MessageInfoManager", "deleteAllLocalCacheMsgIntent Error! " + th);
            return false;
        }
    }

    public static ArrayList c(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            SQLiteDatabase readableDatabase = d(context).getReadableDatabase();
            SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
            sQLiteQueryBuilder.setTables("messagetoshow");
            Cursor cursorQuery = sQLiteQueryBuilder.query(readableDatabase, new String[]{"message"}, "status=0", null, null, null, null);
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    try {
                        Intent uri = Intent.parseUri(Rijndael.decrypt(cursorQuery.getString(0)), 1);
                        uri.addCategory("android.intent.category.BROWSABLE");
                        uri.setComponent(null);
                        if (Build.VERSION.SDK_INT >= 15) {
                            try {
                                uri.getClass().getMethod("setSelector", Intent.class).invoke(uri, null);
                            } catch (Exception e) {
                                com.tencent.android.tpush.a.a.b(Constants.LogTag, "invoke intent.setComponent error.", e);
                            }
                        }
                        arrayList.add(uri);
                    } catch (URISyntaxException e2) {
                        com.tencent.android.tpush.a.a.i("MessageInfoManager", "getCacheMessages Error: " + e2);
                    }
                }
                cursorQuery.close();
            }
            readableDatabase.close();
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i("MessageInfoManager", "getNewCacheMessages Error! " + th);
        }
        return arrayList;
    }
}
