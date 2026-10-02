package com.tencent.android.tpush.service.cache;

import android.content.Context;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.k;
import com.tencent.android.tpush.common.n;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.horse.data.OptStrategyList;
import com.tencent.android.tpush.horse.data.StrategyItem;
import com.tencent.android.tpush.service.channel.exception.NullReturnException;
import com.tencent.android.tpush.service.channel.protocol.AppInfo;
import com.tencent.android.tpush.service.channel.protocol.UnregInfo;
import com.tencent.android.tpush.service.e.m;
import com.tencent.android.tpush.stat.b.c;
import com.tencent.mid.api.MidService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CacheManager {
    private static Map a = null;

    private CacheManager() {
    }

    public static Map getRegInfoByApps(Context context) {
        a aVar = new a(context);
        Thread thread = new Thread(aVar);
        thread.start();
        try {
            thread.join(3500L);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, th.toString());
        }
        return aVar.a();
    }

    public static String findValidPackageByAccessid(long j) {
        RegisterEntity registerEntity = (RegisterEntity) getRegisterEntityMap().get(Long.valueOf(j));
        if (registerEntity == null || !registerEntity.a()) {
            return null;
        }
        return registerEntity.packageName;
    }

    public static RegisterEntity findValidRegisterEntityByPkg(String str) {
        if (m.b(str)) {
            return null;
        }
        Iterator it = getRegisterEntityMap().entrySet().iterator();
        while (it.hasNext()) {
            RegisterEntity registerEntity = (RegisterEntity) ((Map.Entry) it.next()).getValue();
            if (registerEntity != null && str.equals(registerEntity.packageName)) {
                return registerEntity;
            }
        }
        return null;
    }

    public static void removeRegisterInfos(String str) {
        a(str, (byte) 1);
    }

    public static List getRegisterInfos(Context context) {
        ArrayList arrayList;
        ArrayList arrayList2 = new ArrayList();
        try {
            Iterator it = getRegisterEntityMap().entrySet().iterator();
            while (it.hasNext()) {
                RegisterEntity registerEntity = (RegisterEntity) ((Map.Entry) it.next()).getValue();
                if (registerEntity != null && !m.b(registerEntity.packageName) && registerEntity.a()) {
                    arrayList2.add(registerEntity.packageName);
                }
            }
            arrayList = arrayList2;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e);
            arrayList = new ArrayList();
        }
        if (!arrayList.contains(context.getPackageName())) {
            arrayList.add(context.getPackageName());
        }
        return arrayList;
    }

    public static synchronized Map getRegisterEntityMap() {
        if (a == null) {
            a = getRegInfoByApps(getContext());
        }
        return a;
    }

    public static RegisterEntity getCurrentAppRegisterEntity(Context context) {
        String strA = n.a(context, a("cur.register", ".reg"), Constants.MAIN_VERSION_TAG);
        if (m.b(strA)) {
            return null;
        }
        return RegisterEntity.a(strA);
    }

    public static void setCurrentAppRegisterEntity(Context context, RegisterEntity registerEntity) {
        n.b(context, a("cur.register", ".reg"), RegisterEntity.a(registerEntity));
    }

    public static Context getContext() {
        return com.tencent.android.tpush.service.n.f() != null ? com.tencent.android.tpush.service.n.f() : XGPushManager.getContext();
    }

    public static void addRegisterInfo(RegisterEntity registerEntity) {
        if (registerEntity != null && registerEntity.accessId > 0) {
            getRegisterEntityMap().put(Long.valueOf(registerEntity.accessId), registerEntity);
        }
    }

    public static List getRegisterInfo(Context context) {
        ArrayList arrayList = new ArrayList();
        if (context != null) {
            Iterator it = getRegisterEntityMap().entrySet().iterator();
            while (it.hasNext()) {
                RegisterEntity registerEntity = (RegisterEntity) ((Map.Entry) it.next()).getValue();
                if (registerEntity != null && registerEntity.a()) {
                    arrayList.add(registerEntity);
                }
            }
        }
        return arrayList;
    }

    public static List getUnregisterInfo(Context context) {
        ArrayList arrayList = new ArrayList();
        if (context != null) {
            Iterator it = getRegisterEntityMap().entrySet().iterator();
            while (it.hasNext()) {
                RegisterEntity registerEntity = (RegisterEntity) ((Map.Entry) it.next()).getValue();
                if (registerEntity != null && !m.b(registerEntity.packageName) && registerEntity.b()) {
                    arrayList.add(registerEntity);
                }
            }
        }
        return arrayList;
    }

    public static List getUninstallInfo(Context context) {
        ArrayList arrayList = new ArrayList();
        if (context != null) {
            Iterator it = getRegisterEntityMap().entrySet().iterator();
            while (it.hasNext()) {
                RegisterEntity registerEntity = (RegisterEntity) ((Map.Entry) it.next()).getValue();
                if (registerEntity != null && !m.b(registerEntity.packageName) && registerEntity.c()) {
                    arrayList.add(registerEntity);
                }
            }
        }
        return arrayList;
    }

    public static ArrayList getUninstallAndUnregisterInfo(Context context) {
        ArrayList arrayList = new ArrayList();
        if (context != null) {
            Iterator it = getRegisterEntityMap().entrySet().iterator();
            while (it.hasNext()) {
                RegisterEntity registerEntity = (RegisterEntity) ((Map.Entry) it.next()).getValue();
                if (registerEntity != null && (registerEntity.b() || registerEntity.c())) {
                    UnregInfo unregInfo = new UnregInfo();
                    unregInfo.appInfo = new AppInfo(registerEntity.accessId, registerEntity.accessKey, m.d(registerEntity.packageName), (byte) 0);
                    unregInfo.isUninstall = (byte) registerEntity.state;
                    unregInfo.timestamp = registerEntity.timestamp;
                    arrayList.add(unregInfo);
                }
            }
        }
        return arrayList;
    }

    public static RegisterEntity getRegisterInfoByPkgName(String str) {
        return findValidRegisterEntityByPkg(str);
    }

    public static void UnregisterInfoByPkgName(String str) {
        a(str, (byte) 1);
    }

    public static void UnregisterInfoSuccessByPkgName(String str) {
        a(str, (byte) 3);
    }

    private static void a(String str, byte b) {
        if (!m.b(str)) {
            Iterator it = getRegisterEntityMap().entrySet().iterator();
            while (it.hasNext()) {
                RegisterEntity registerEntity = (RegisterEntity) ((Map.Entry) it.next()).getValue();
                if (registerEntity != null && !m.b(registerEntity.packageName) && str.equals(registerEntity.packageName)) {
                    registerEntity.state = b;
                }
            }
        }
    }

    public static void UninstallInfoByPkgName(String str) {
        a(str, (byte) 2);
    }

    public static void UninstallInfoSuccessByPkgName(String str) {
        a(str, (byte) 4);
    }

    public static void removeRegisterInfoByPkgName(String str) {
        a(str);
    }

    private static void a(String str, String str2, int i) {
    }

    private static void a(String str) {
    }

    public static void updateUnregUninList(Context context, ArrayList arrayList) {
        if (context != null && arrayList != null && arrayList.size() > 0) {
            List unregisterInfo = getUnregisterInfo(context);
            List<RegisterEntity> uninstallInfo = getUninstallInfo(context);
            if (unregisterInfo != null) {
                for (int i = 0; i < arrayList.size(); i++) {
                    UnregInfo unregInfo = (UnregInfo) arrayList.get(i);
                    if (unregInfo.isUninstall == 1) {
                        for (int i2 = 0; i2 < unregisterInfo.size(); i2++) {
                            RegisterEntity registerEntity = (RegisterEntity) unregisterInfo.get(i2);
                            if (registerEntity.accessId == unregInfo.appInfo.accessId) {
                                a(registerEntity.packageName, a(registerEntity.packageName, ".reg"), 3);
                            }
                        }
                    }
                    if (unregInfo.isUninstall == 2) {
                        for (RegisterEntity registerEntity2 : uninstallInfo) {
                            if (registerEntity2.accessId == unregInfo.appInfo.accessId) {
                                a(registerEntity2.packageName, a(registerEntity2.packageName, ".reg"), 4);
                            }
                        }
                    }
                }
            }
        }
    }

    public static String getToken(Context context) {
        String strB = c.b(context);
        return MidService.isMidValid(strB) ? strB : MidService.getLocalMidOnly(context);
    }

    public static long getGuid(Context context) {
        return c.a(context);
    }

    public static boolean setToken(Context context, String str) {
        if (context == null || m.b(str) || str.equals(getToken(context))) {
            return false;
        }
        c.a(context, str);
        return true;
    }

    public static boolean setTokenAndGuid(Context context, String str, long j) {
        if (context != null) {
            try {
                if (!m.b(str)) {
                    long guid = getGuid(context);
                    String token = getToken(context);
                    if (j != guid || !str.equals(token)) {
                        if (j < 0) {
                            j = guid;
                        }
                        if (m.b(str) || !c.a(str)) {
                            str = token;
                        }
                        c.a(context, j, str);
                        return true;
                    }
                }
            } catch (Throwable th) {
            }
        }
        return false;
    }

    public static String getQua(Context context, long j) {
        if (context == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        return Rijndael.decrypt(n.a(context, ".com.tencent.tpush.cache.qua." + j, Constants.MAIN_VERSION_TAG));
    }

    public static void setQua(Context context, long j, String str) {
        if (context != null && !m.b(str) && j > 0) {
            n.b(context, ".com.tencent.tpush.cache.qua." + j, str);
        }
    }

    public static synchronized void addOptStrategyList(Context context, String str, OptStrategyList optStrategyList) {
        if (context != null && str != null) {
            addOptKey(context, str);
            String str2 = str + ".com.tencent.tpush.cache.redirect";
            try {
                optStrategyList.a(System.currentTimeMillis());
                n.b(context, str2, Rijndael.encrypt(k.a(optStrategyList)));
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e);
            }
        }
    }

    public static synchronized void removeOptStrategyList(Context context, String str) {
        addOptStrategyList(context, str, new OptStrategyList());
    }

    public static OptStrategyList getOptStrategyList(Context context, String str) throws NullReturnException {
        try {
            if (context == null || str == null) {
                throw new NullReturnException(new StringBuffer("getStrategy return null,contex is null(").append(context == null).append(") and key=").append(str).toString());
            }
            Object objA = k.a(Rijndael.decrypt(n.a(context, str + ".com.tencent.tpush.cache.redirect", Constants.MAIN_VERSION_TAG)));
            if (objA instanceof OptStrategyList) {
                return (OptStrategyList) objA;
            }
            throw new NullReturnException("getStrategy return null, because serializer object is not instanceof OptStrategyList");
        } catch (Exception e) {
            throw new NullReturnException("getOptStrategyList return null,deserialize err", e);
        }
    }

    public static synchronized void addOptStrategy(StrategyItem strategyItem) {
        OptStrategyList optStrategyList;
        String strM = m.m(com.tencent.android.tpush.service.n.f());
        try {
            optStrategyList = getOptStrategyList(com.tencent.android.tpush.service.n.f(), strM);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, ">> Can not get OptStrategyList from local", e);
            optStrategyList = new OptStrategyList();
        }
        if (strategyItem.d() == 1) {
            if (strategyItem.f() == 0) {
                optStrategyList.d(strategyItem);
            } else {
                optStrategyList.c(strategyItem);
            }
        } else if (strategyItem.f() == 0) {
            optStrategyList.b(strategyItem);
        } else {
            optStrategyList.a(strategyItem);
        }
        addOptStrategyList(com.tencent.android.tpush.service.n.f(), strM, optStrategyList);
    }

    public static void addServerItems(Context context, String str, ArrayList arrayList) {
        if (context != null && str != null) {
            saveDomainKey(context, str);
            try {
                n.b(context, str + ".com.tencent.tpush.cache.server", Rijndael.encrypt(k.a(arrayList)));
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e);
            }
        }
    }

    public static ArrayList getServerItems(Context context, String str) throws NullReturnException {
        if (str == null) {
            throw new NullReturnException("getServerItems return null,because key is null");
        }
        try {
            Object objA = k.a(Rijndael.decrypt(n.a(context, str + ".com.tencent.tpush.cache.server", Constants.MAIN_VERSION_TAG)));
            if (objA != null && (objA instanceof ArrayList)) {
                return (ArrayList) objA;
            }
            throw new NullReturnException("getServerItems return null,because object not instance of Arraylist<?>");
        } catch (Exception e) {
            throw new NullReturnException("getServerItem return null,deseriallize err", e);
        }
    }

    public static void addOptKeyList(Context context, HashSet hashSet) {
        if (context != null) {
            try {
                n.b(context, ".com.tencent.tpush.cache.keylist", Rijndael.encrypt(k.a(hashSet)));
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e);
            }
        }
    }

    public static void addOptKey(Context context, String str) {
        HashSet hashSet;
        try {
            hashSet = getOptKeyList(context);
        } catch (Exception e) {
            hashSet = new HashSet();
        }
        hashSet.add(str);
        addOptKeyList(context, hashSet);
    }

    public static HashSet getOptKeyList(Context context) throws NullReturnException {
        if (context == null) {
            throw new NullReturnException("getOptKeyList return null,because ctx is null");
        }
        try {
            Object objA = k.a(Rijndael.decrypt(n.a(context, ".com.tencent.tpush.cache.keylist", Constants.MAIN_VERSION_TAG)));
            if (objA instanceof HashSet) {
                return (HashSet) objA;
            }
            throw new NullReturnException("getOptKeyList return null,because object not instance of ArrayList<?>");
        } catch (Exception e) {
            throw new NullReturnException("getOptKeyList return null，deseriallize err", e);
        }
    }

    public static void clearOptKeyList(Context context) {
        if (context != null) {
            n.b(context, ".com.tencent.tpush.cache.keylist", Constants.MAIN_VERSION_TAG);
        }
    }

    public static void saveLoadIpTime(Context context, long j) {
        if (context != null && j > 0) {
            n.b(context, ".com.tencent.tpush.cache.load.ip.last.time", j);
        }
    }

    public static long getLastLoadIpTime(Context context) {
        if (context != null) {
            return n.a(context, ".com.tencent.tpush.cache.load.ip.last.time", 0L);
        }
        return 0L;
    }

    public static void saveSpeedTestList(Context context, ArrayList arrayList) {
        if (context != null) {
            try {
                n.b(context, ".com.tencent.tpush.cache.speed.test", Rijndael.encrypt(k.a(arrayList)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList getSpeedTestList(Context context) throws NullReturnException {
        if (context == null) {
            throw new NullReturnException("getSpeedTestList return null ,because ctx is null");
        }
        try {
            Object objA = k.a(Rijndael.decrypt(n.a(context, ".com.tencent.tpush.cache.speed.test", Constants.MAIN_VERSION_TAG)));
            if (objA instanceof ArrayList) {
                return (ArrayList) objA;
            }
            throw new NullReturnException("getSpeedTestList return null ,because instanceof err");
        } catch (Exception e) {
            throw new NullReturnException("getSpeedTestList return null ,because deserialize err", e);
        }
    }

    public static void saveDomain(Context context, String str) {
        if (context != null && str != null) {
            n.b(context, ".com.tencent.tpush.cache.domain", str);
        }
    }

    public static String getDomain(Context context) {
        if (context != null) {
            n.a(context, ".com.tencent.tpush.cache.domain", Constants.MAIN_VERSION_TAG);
            return Constants.MAIN_VERSION_TAG;
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static void saveDomainKeyList(Context context, ArrayList arrayList) {
        if (context != null) {
            String strA = Constants.MAIN_VERSION_TAG;
            if (arrayList != null) {
                try {
                    strA = k.a(arrayList);
                } catch (Exception e) {
                    com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e);
                    return;
                }
            }
            n.b(context, ".com.tencent.tpush.cache.domain.key", Rijndael.encrypt(strA));
        }
    }

    public static void saveDomainKey(Context context, String str) {
        ArrayList arrayList;
        if (context != null) {
            try {
                arrayList = getDomainKeyList(context);
            } catch (Exception e) {
                arrayList = new ArrayList();
            }
            arrayList.add(str);
            saveDomainKeyList(context, arrayList);
        }
    }

    public static ArrayList getDomainKeyList(Context context) throws NullReturnException {
        if (context == null) {
            throw new NullReturnException("getDomainKeyList return null,because ctx is null");
        }
        try {
            Object objA = k.a(Rijndael.decrypt(n.a(context, ".com.tencent.tpush.cache.domain.key", Constants.MAIN_VERSION_TAG)));
            if (objA instanceof ArrayList) {
                return (ArrayList) objA;
            }
            throw new NullReturnException("getDomainKeyList return null,because object not instance of ArrayList<?>");
        } catch (Exception e) {
            throw new NullReturnException("getDomainKeyList return null，deseriallize err", e);
        }
    }

    public static void clearDomainServerItem(Context context) {
        ArrayList arrayList;
        try {
            arrayList = getDomainKeyList(context);
        } catch (NullReturnException e) {
            arrayList = new ArrayList();
        }
        arrayList.add(String.valueOf(3));
        arrayList.add(String.valueOf(1));
        arrayList.add(String.valueOf(2));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                n.b(context, ((String) it.next()) + ".com.tencent.tpush.cache.server", Constants.MAIN_VERSION_TAG);
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e2);
            }
        }
    }

    private static String a(String str, String str2) {
        return str + ".com.tencent.tpush.cache" + str2;
    }
}
