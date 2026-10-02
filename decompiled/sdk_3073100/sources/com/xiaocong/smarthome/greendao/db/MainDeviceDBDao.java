package com.xiaocong.smarthome.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.xiaocong.smarthome.greendao.model.insert.MainDeviceDB;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MainDeviceDBDao extends AbstractDao<MainDeviceDB, String> {
    public static final String TABLENAME = "MAIN_DEVICE_DB";

    public static class Properties {
        public static final Property DeviceId = new Property(0, String.class, "deviceId", true, "DEVICE_ID");
        public static final Property DeviceName = new Property(1, String.class, "deviceName", false, "DEVICE_NAME");
        public static final Property ProductId = new Property(2, Integer.TYPE, "productId", false, "PRODUCT_ID");
        public static final Property ProductImage = new Property(3, String.class, "productImage", false, "PRODUCT_IMAGE");
        public static final Property IsAdmin = new Property(4, Integer.TYPE, "isAdmin", false, "IS_ADMIN");
        public static final Property Top = new Property(5, Integer.TYPE, "top", false, "TOP");
        public static final Property Snapshot = new Property(6, String.class, "snapshot", false, "SNAPSHOT");
        public static final Property Status = new Property(7, Integer.TYPE, "status", false, "STATUS");
        public static final Property Partner = new Property(8, String.class, "partner", false, "PARTNER");
        public static final Property DeviceMac = new Property(9, String.class, "deviceMac", false, "DEVICE_MAC");
        public static final Property DeviceSn = new Property(10, String.class, "deviceSn", false, "DEVICE_SN");
    }

    public MainDeviceDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"MAIN_DEVICE_DB\" (\"DEVICE_ID\" TEXT PRIMARY KEY NOT NULL ,\"DEVICE_NAME\" TEXT,\"PRODUCT_ID\" INTEGER NOT NULL ,\"PRODUCT_IMAGE\" TEXT,\"IS_ADMIN\" INTEGER NOT NULL ,\"TOP\" INTEGER NOT NULL ,\"SNAPSHOT\" TEXT,\"STATUS\" INTEGER NOT NULL ,\"PARTNER\" TEXT,\"DEVICE_MAC\" TEXT,\"DEVICE_SN\" TEXT);");
    }

    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MAIN_DEVICE_DB\"";
        db.execSQL(sql);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement stmt, MainDeviceDB entity) {
        stmt.clearBindings();
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(1, deviceId);
        }
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(2, deviceName);
        }
        stmt.bindLong(3, entity.getProductId());
        String productImage = entity.getProductImage();
        if (productImage != null) {
            stmt.bindString(4, productImage);
        }
        stmt.bindLong(5, entity.getIsAdmin());
        stmt.bindLong(6, entity.getTop());
        String snapshot = entity.getSnapshot();
        if (snapshot != null) {
            stmt.bindString(7, snapshot);
        }
        stmt.bindLong(8, entity.getStatus());
        String partner = entity.getPartner();
        if (partner != null) {
            stmt.bindString(9, partner);
        }
        String deviceMac = entity.getDeviceMac();
        if (deviceMac != null) {
            stmt.bindString(10, deviceMac);
        }
        String deviceSn = entity.getDeviceSn();
        if (deviceSn != null) {
            stmt.bindString(11, deviceSn);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement stmt, MainDeviceDB entity) {
        stmt.clearBindings();
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(1, deviceId);
        }
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(2, deviceName);
        }
        stmt.bindLong(3, entity.getProductId());
        String productImage = entity.getProductImage();
        if (productImage != null) {
            stmt.bindString(4, productImage);
        }
        stmt.bindLong(5, entity.getIsAdmin());
        stmt.bindLong(6, entity.getTop());
        String snapshot = entity.getSnapshot();
        if (snapshot != null) {
            stmt.bindString(7, snapshot);
        }
        stmt.bindLong(8, entity.getStatus());
        String partner = entity.getPartner();
        if (partner != null) {
            stmt.bindString(9, partner);
        }
        String deviceMac = entity.getDeviceMac();
        if (deviceMac != null) {
            stmt.bindString(10, deviceMac);
        }
        String deviceSn = entity.getDeviceSn();
        if (deviceSn != null) {
            stmt.bindString(11, deviceSn);
        }
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
    public MainDeviceDB readEntity(Cursor cursor, int offset) {
        MainDeviceDB entity = new MainDeviceDB(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), cursor.getInt(offset + 2), cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), cursor.getInt(offset + 4), cursor.getInt(offset + 5), cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), cursor.getInt(offset + 7), cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        return entity;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, MainDeviceDB entity, int offset) {
        entity.setDeviceId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDeviceName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setProductId(cursor.getInt(offset + 2));
        entity.setProductImage(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIsAdmin(cursor.getInt(offset + 4));
        entity.setTop(cursor.getInt(offset + 5));
        entity.setSnapshot(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setStatus(cursor.getInt(offset + 7));
        entity.setPartner(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDeviceMac(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDeviceSn(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.greenrobot.greendao.AbstractDao
    public final String updateKeyAfterInsert(MainDeviceDB entity, long rowId) {
        return entity.getDeviceId();
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public String getKey(MainDeviceDB entity) {
        if (entity != null) {
            return entity.getDeviceId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(MainDeviceDB entity) {
        return entity.getDeviceId() != null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    protected final boolean isEntityUpdateable() {
        return true;
    }
}
