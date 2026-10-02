package org.greenrobot.greendao.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DatabaseOpenHelper extends SQLiteOpenHelper {
    private final Context context;
    private EncryptedHelper encryptedHelper;
    private boolean loadSQLCipherNativeLibs;
    private final String name;
    private final int version;

    public DatabaseOpenHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.loadSQLCipherNativeLibs = true;
        this.context = context;
        this.name = name;
        this.version = version;
    }

    public void setLoadSQLCipherNativeLibs(boolean loadSQLCipherNativeLibs) {
        this.loadSQLCipherNativeLibs = loadSQLCipherNativeLibs;
    }

    public Database getWritableDb() {
        return wrap(getWritableDatabase());
    }

    public Database getReadableDb() {
        return wrap(getReadableDatabase());
    }

    protected Database wrap(SQLiteDatabase sqLiteDatabase) {
        return new StandardDatabase(sqLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        onCreate(wrap(db));
    }

    public void onCreate(Database db) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(wrap(db), oldVersion, newVersion);
    }

    public void onUpgrade(Database db, int oldVersion, int newVersion) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onOpen(SQLiteDatabase db) {
        onOpen(wrap(db));
    }

    public void onOpen(Database db) {
    }

    private EncryptedHelper checkEncryptedHelper() {
        if (this.encryptedHelper == null) {
            this.encryptedHelper = new EncryptedHelper(this, this.context, this.name, this.version, this.loadSQLCipherNativeLibs);
        }
        return this.encryptedHelper;
    }

    public Database getEncryptedWritableDb(String password) {
        EncryptedHelper encryptedHelper = checkEncryptedHelper();
        return encryptedHelper.wrap(encryptedHelper.getReadableDatabase(password));
    }

    public Database getEncryptedWritableDb(char[] password) {
        EncryptedHelper encryptedHelper = checkEncryptedHelper();
        return encryptedHelper.wrap(encryptedHelper.getWritableDatabase(password));
    }

    public Database getEncryptedReadableDb(String password) {
        EncryptedHelper encryptedHelper = checkEncryptedHelper();
        return encryptedHelper.wrap(encryptedHelper.getReadableDatabase(password));
    }

    public Database getEncryptedReadableDb(char[] password) {
        EncryptedHelper encryptedHelper = checkEncryptedHelper();
        return encryptedHelper.wrap(encryptedHelper.getReadableDatabase(password));
    }
}
