package com.ixiaocong.smarthome.phone.android.helper.db;

import com.ixiaocong.smarthome.phone.android.XcApplication;
import com.xiaocong.smarthome.greendao.model.insert.RNVersionDB;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNVersionDBHelper {
    public static void insertVersion(RNVersionDB versionDB) {
        XcApplication.getDaoSession().getRNVersionDBDao().insertOrReplace(versionDB);
    }

    public static void deleteAll() {
        XcApplication.getDaoSession().getRNVersionDBDao().deleteAll();
    }

    public static RNVersionDB loadAssign(long id) {
        return (RNVersionDB) XcApplication.getDaoSession().getRNVersionDBDao().load(Long.valueOf(id));
    }
}
