package com.xiaocong.smarthome.greendao.db;

import com.xiaocong.smarthome.greendao.model.insert.ClientConfigDB;
import com.xiaocong.smarthome.greendao.model.insert.MainDeviceDB;
import com.xiaocong.smarthome.greendao.model.insert.RNVersionDB;
import com.xiaocong.smarthome.greendao.model.insert.UserInfoDB;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DaoSession extends AbstractDaoSession {
    private final ClientConfigDBDao clientConfigDBDao;
    private final DaoConfig clientConfigDBDaoConfig;
    private final MainDeviceDBDao mainDeviceDBDao;
    private final DaoConfig mainDeviceDBDaoConfig;
    private final RNVersionDBDao rNVersionDBDao;
    private final DaoConfig rNVersionDBDaoConfig;
    private final UserInfoDBDao userInfoDBDao;
    private final DaoConfig userInfoDBDaoConfig;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        this.mainDeviceDBDaoConfig = daoConfigMap.get(MainDeviceDBDao.class).clone();
        this.mainDeviceDBDaoConfig.initIdentityScope(type);
        this.userInfoDBDaoConfig = daoConfigMap.get(UserInfoDBDao.class).clone();
        this.userInfoDBDaoConfig.initIdentityScope(type);
        this.clientConfigDBDaoConfig = daoConfigMap.get(ClientConfigDBDao.class).clone();
        this.clientConfigDBDaoConfig.initIdentityScope(type);
        this.rNVersionDBDaoConfig = daoConfigMap.get(RNVersionDBDao.class).clone();
        this.rNVersionDBDaoConfig.initIdentityScope(type);
        this.mainDeviceDBDao = new MainDeviceDBDao(this.mainDeviceDBDaoConfig, this);
        this.userInfoDBDao = new UserInfoDBDao(this.userInfoDBDaoConfig, this);
        this.clientConfigDBDao = new ClientConfigDBDao(this.clientConfigDBDaoConfig, this);
        this.rNVersionDBDao = new RNVersionDBDao(this.rNVersionDBDaoConfig, this);
        registerDao(MainDeviceDB.class, this.mainDeviceDBDao);
        registerDao(UserInfoDB.class, this.userInfoDBDao);
        registerDao(ClientConfigDB.class, this.clientConfigDBDao);
        registerDao(RNVersionDB.class, this.rNVersionDBDao);
    }

    public UserInfoDBDao getUserInfoDBDao() {
        return this.userInfoDBDao;
    }

    public RNVersionDBDao getRNVersionDBDao() {
        return this.rNVersionDBDao;
    }
}
