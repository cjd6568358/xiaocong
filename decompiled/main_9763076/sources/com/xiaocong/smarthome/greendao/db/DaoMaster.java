package com.xiaocong.smarthome.greendao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DaoMaster extends AbstractDaoMaster {
    public static void createAllTables(Database db, boolean ifNotExists) {
        MainDeviceDBDao.createTable(db, ifNotExists);
        UserInfoDBDao.createTable(db, ifNotExists);
        ClientConfigDBDao.createTable(db, ifNotExists);
        RNVersionDBDao.createTable(db, ifNotExists);
    }

    public static void dropAllTables(Database db, boolean ifExists) {
        MainDeviceDBDao.dropTable(db, ifExists);
        UserInfoDBDao.dropTable(db, ifExists);
        ClientConfigDBDao.dropTable(db, ifExists);
        RNVersionDBDao.dropTable(db, ifExists);
    }

    public DaoMaster(SQLiteDatabase db) {
        this((Database) new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, 1);
        registerDaoClass(MainDeviceDBDao.class);
        registerDaoClass(UserInfoDBDao.class);
        registerDaoClass(ClientConfigDBDao.class);
        registerDaoClass(RNVersionDBDao.class);
    }

    @Override // org.greenrobot.greendao.AbstractDaoMaster
    public DaoSession newSession() {
        return new DaoSession(this.db, IdentityScopeType.Session, this.daoConfigMap);
    }

    @Override // org.greenrobot.greendao.AbstractDaoMaster
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(this.db, type, this.daoConfigMap);
    }

    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, 1);
        }

        @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version 1");
            DaoMaster.createAllTables(db, false);
        }
    }

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            DaoMaster.dropAllTables(db, true);
            onCreate(db);
        }
    }
}
