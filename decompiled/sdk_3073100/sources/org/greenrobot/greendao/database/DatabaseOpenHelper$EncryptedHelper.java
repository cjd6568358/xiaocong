package org.greenrobot.greendao.database;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/* JADX INFO: Access modifiers changed from: private */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DatabaseOpenHelper$EncryptedHelper extends SQLiteOpenHelper {
    final /* synthetic */ DatabaseOpenHelper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DatabaseOpenHelper$EncryptedHelper(DatabaseOpenHelper databaseOpenHelper, Context context, String name, int version, boolean loadLibs) {
        super(context, name, null, version);
        this.this$0 = databaseOpenHelper;
        if (loadLibs) {
            SQLiteDatabase.loadLibs(context);
        }
    }

    @Override // net.sqlcipher.database.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        this.this$0.onCreate(wrap(db));
    }

    @Override // net.sqlcipher.database.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.this$0.onUpgrade(wrap(db), oldVersion, newVersion);
    }

    @Override // net.sqlcipher.database.SQLiteOpenHelper
    public void onOpen(SQLiteDatabase db) {
        this.this$0.onOpen(wrap(db));
    }

    protected Database wrap(SQLiteDatabase sqLiteDatabase) {
        return new EncryptedDatabase(sqLiteDatabase);
    }
}
