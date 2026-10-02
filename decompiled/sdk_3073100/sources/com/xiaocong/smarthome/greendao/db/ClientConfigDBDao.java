package com.xiaocong.smarthome.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.xiaocong.smarthome.greendao.model.insert.ClientConfigDB;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ClientConfigDBDao extends AbstractDao<ClientConfigDB, Long> {
    public static final String TABLENAME = "CLIENT_CONFIG_DB";

    public static class Properties {
        public static final Property Id = new Property(0, Long.TYPE, "id", true, "_id");
        public static final Property Live = new Property(1, String.class, "live", false, "LIVE");
        public static final Property CliendId = new Property(2, String.class, "cliendId", false, "CLIEND_ID");
        public static final Property Uid = new Property(3, String.class, "uid", false, "UID");
    }

    public ClientConfigDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"CLIENT_CONFIG_DB\" (\"_id\" INTEGER PRIMARY KEY NOT NULL ,\"LIVE\" TEXT,\"CLIEND_ID\" TEXT,\"UID\" TEXT);");
    }

    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CLIENT_CONFIG_DB\"";
        db.execSQL(sql);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement stmt, ClientConfigDB entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        String live = entity.getLive();
        if (live != null) {
            stmt.bindString(2, live);
        }
        String cliendId = entity.getCliendId();
        if (cliendId != null) {
            stmt.bindString(3, cliendId);
        }
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(4, uid);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement stmt, ClientConfigDB entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        String live = entity.getLive();
        if (live != null) {
            stmt.bindString(2, live);
        }
        String cliendId = entity.getCliendId();
        if (cliendId != null) {
            stmt.bindString(3, cliendId);
        }
        String uid = entity.getUid();
        if (uid != null) {
            stmt.bindString(4, uid);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Long readKey(Cursor cursor, int offset) {
        return Long.valueOf(cursor.getLong(offset + 0));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public ClientConfigDB readEntity(Cursor cursor, int offset) {
        ClientConfigDB entity = new ClientConfigDB(cursor.getLong(offset + 0), cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        return entity;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, ClientConfigDB entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setLive(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCliendId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(ClientConfigDB entity, long rowId) {
        entity.setId(rowId);
        return Long.valueOf(rowId);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(ClientConfigDB entity) {
        if (entity != null) {
            return Long.valueOf(entity.getId());
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(ClientConfigDB entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }
}
