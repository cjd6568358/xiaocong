package com.xiaocong.smarthome.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.xiaocong.smarthome.greendao.model.insert.RNVersionDB;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class RNVersionDBDao extends AbstractDao<RNVersionDB, Long> {
    public static final String TABLENAME = "RNVERSION_DB";

    public static class Properties {
        public static final Property XcId = new Property(0, Long.TYPE, "xcId", true, "_id");
        public static final Property Version = new Property(1, String.class, "version", false, "VERSION");
    }

    public RNVersionDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"RNVERSION_DB\" (\"_id\" INTEGER PRIMARY KEY NOT NULL ,\"VERSION\" TEXT);");
    }

    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RNVERSION_DB\"";
        db.execSQL(sql);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement stmt, RNVersionDB entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getXcId());
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(2, version);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement stmt, RNVersionDB entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getXcId());
        String version = entity.getVersion();
        if (version != null) {
            stmt.bindString(2, version);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Long readKey(Cursor cursor, int offset) {
        return Long.valueOf(cursor.getLong(offset + 0));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public RNVersionDB readEntity(Cursor cursor, int offset) {
        RNVersionDB entity = new RNVersionDB(cursor.getLong(offset + 0), cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        return entity;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, RNVersionDB entity, int offset) {
        entity.setXcId(cursor.getLong(offset + 0));
        entity.setVersion(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(RNVersionDB entity, long rowId) {
        entity.setXcId(rowId);
        return Long.valueOf(rowId);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(RNVersionDB entity) {
        if (entity != null) {
            return Long.valueOf(entity.getXcId());
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(RNVersionDB entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }
}
