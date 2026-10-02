package com.xiaocong.smarthome.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.xiaocong.smarthome.greendao.model.insert.UserInfoDB;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UserInfoDBDao extends AbstractDao<UserInfoDB, String> {
    public static final String TABLENAME = "USER_INFO_DB";

    public static class Properties {
        public static final Property UserId = new Property(0, String.class, "userId", true, "USER_ID");
        public static final Property Nickname = new Property(1, String.class, "nickname", false, "NICKNAME");
        public static final Property Phone = new Property(2, String.class, "phone", false, "PHONE");
        public static final Property UserImg = new Property(3, String.class, "userImg", false, "USER_IMG");
        public static final Property Uid = new Property(4, String.class, "uid", false, "UID");
    }

    public UserInfoDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO_DB\" (\"USER_ID\" TEXT PRIMARY KEY NOT NULL ,\"NICKNAME\" TEXT NOT NULL ,\"PHONE\" TEXT NOT NULL ,\"USER_IMG\" TEXT,\"UID\" TEXT NOT NULL );");
    }

    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO_DB\"";
        db.execSQL(sql);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement stmt, UserInfoDB entity) {
        stmt.clearBindings();
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(1, userId);
        }
        stmt.bindString(2, entity.getNickname());
        stmt.bindString(3, entity.getPhone());
        String userImg = entity.getUserImg();
        if (userImg != null) {
            stmt.bindString(4, userImg);
        }
        stmt.bindString(5, entity.getUid());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement stmt, UserInfoDB entity) {
        stmt.clearBindings();
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(1, userId);
        }
        stmt.bindString(2, entity.getNickname());
        stmt.bindString(3, entity.getPhone());
        String userImg = entity.getUserImg();
        if (userImg != null) {
            stmt.bindString(4, userImg);
        }
        stmt.bindString(5, entity.getUid());
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public String readKey(Cursor cursor, int offset) {
        if (cursor.isNull(offset + 0)) {
            return null;
        }
        return cursor.getString(offset + 0);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public UserInfoDB readEntity(Cursor cursor, int offset) {
        UserInfoDB entity = new UserInfoDB(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), cursor.getString(offset + 1), cursor.getString(offset + 2), cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), cursor.getString(offset + 4));
        return entity;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, UserInfoDB entity, int offset) {
        entity.setUserId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setNickname(cursor.getString(offset + 1));
        entity.setPhone(cursor.getString(offset + 2));
        entity.setUserImg(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUid(cursor.getString(offset + 4));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final String updateKeyAfterInsert(UserInfoDB entity, long rowId) {
        return entity.getUserId();
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public String getKey(UserInfoDB entity) {
        if (entity != null) {
            return entity.getUserId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(UserInfoDB entity) {
        return entity.getUserId() != null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }
}
