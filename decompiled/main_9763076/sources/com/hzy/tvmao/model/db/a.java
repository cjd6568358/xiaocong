package com.hzy.tvmao.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.hzy.tvmao.KookongSDK;
import com.hzy.tvmao.utils.LogUtil;

/* JADX INFO: compiled from: TvMaoDb.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a c;
    private SQLiteDatabase a;
    private C0022a b;

    private a(Context context) {
        if (this.a == null || !this.a.isOpen()) {
            try {
                if (this.b == null) {
                    this.b = new C0022a(context);
                }
                this.a = this.b.getWritableDatabase();
                LogUtil.d("mSqlLiteDb is getWritableDatabase");
            } catch (SQLiteException e) {
                e.printStackTrace();
                LogUtil.e("数据库创建失败", e);
            }
        }
    }

    public static synchronized a a() {
        if (c == null) {
            c = new a(KookongSDK.getContext());
        }
        return c;
    }

    public SQLiteDatabase b() {
        return this.a;
    }

    public void c() {
        this.a.close();
        this.b.close();
    }

    public void a(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    protected void finalize() throws Throwable {
        c();
        super.finalize();
    }

    /* JADX INFO: renamed from: com.hzy.tvmao.model.db.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: TvMaoDb.java */
    private static class C0022a extends SQLiteOpenHelper {
        C0022a(Context context) {
            super(context, "TvMaoDb", (SQLiteDatabase.CursorFactory) null, 3);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            LogUtil.d("SQLiteDatabase onCreate");
            b.a(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            b.a(sQLiteDatabase, i, i2);
        }
    }
}
