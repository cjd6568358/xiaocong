package com.tencent.android.tpush.horse;

import android.content.Context;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.OptStrategyList;
import com.tencent.android.tpush.service.cache.CacheManager;
import java.util.Iterator;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Tools {
    public static final String KEY = ".com.tencent.tpush.toolschannel";
    public static final String SHARE_NAME = ".com.tencent.tpush.toolschannel_name";
    public static final String STRATEGY = ".com.tencent.tpush.toolsstrategy";
    public static final String TOOLS_NAME_UNI_SUFFIX = ".com.tencent.tpush.tools";
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_HTTP = 2;
    public static final int TYPE_HTTP_WAP = 3;
    public static final int TYPE_TCP = 1;
    public static final int TYPE_TCP_OR_HTTP = 4;

    public static void clearOptKeyList(Context context) {
        if (context != null) {
            CacheManager.clearOptKeyList(context);
        }
    }

    public static void clearOptStrategyItem(Context context) {
        if (context != null) {
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.f(Constants.ServiceLogTag, "Action -> clearOptStrategyItem(" + context.getPackageName() + ")");
            }
            try {
                com.tencent.android.tpush.common.m.a(context, com.tencent.android.tpush.service.e.m.m(com.tencent.android.tpush.service.n.f()) + ".com.tencent.tpush.cache.redirect", Constants.MAIN_VERSION_TAG, true);
                Iterator it = CacheManager.getOptKeyList(context).iterator();
                while (it.hasNext()) {
                    CacheManager.addOptStrategyList(context, (String) it.next(), new OptStrategyList());
                }
            } catch (Exception e) {
                CacheManager.clearOptKeyList(com.tencent.android.tpush.service.n.f());
                com.tencent.android.tpush.a.a.d(Constants.ServiceLogTag, "clearOptStrategyItem error", e);
            }
            CacheManager.addOptStrategyList(context, com.tencent.android.tpush.service.e.m.m(context), new OptStrategyList());
        }
    }

    public static void clearCacheServerItems(Context context) {
        if (context != null) {
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.f(Constants.ServiceLogTag, "Action -> clearCacheServerItems(" + context.getPackageName() + ")");
            }
            try {
                CacheManager.clearDomainServerItem(context);
                CacheManager.saveDomain(context, Constants.MAIN_VERSION_TAG);
                CacheManager.saveDomainKeyList(context, null);
                com.tencent.android.tpush.service.a.a.a(context).a(0L);
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.d(Constants.ServiceLogTag, "clearCacheServerItems error", th);
            }
        }
    }

    public static void clearRegisterInfo(Context context, long j) {
        if (context != null) {
            try {
                CacheManager.removeRegisterInfoByPkgName(context.getPackageName());
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "clearRegisterInfo", e);
            }
        }
    }

    public static void setChannelType(Context context, int i) {
        if (context != null) {
            com.tencent.android.tpush.common.m.a(context, KEY, i);
        }
    }

    public static int getChannelType(Context context) {
        if (context != null) {
            return com.tencent.android.tpush.common.m.b(context, KEY, 0);
        }
        return 0;
    }

    public static void clearAll(Context context) {
        clearOptKeyList(context);
        clearCacheServerItems(context);
        clearOptStrategyItem(context);
        XGPushManager.clearLocalNotifications(context);
        com.tencent.android.tpush.common.m.a(context, Constants.IS_CACHE_CLEAR, Constants.IS_CLEAR_CACHE, true);
    }
}
