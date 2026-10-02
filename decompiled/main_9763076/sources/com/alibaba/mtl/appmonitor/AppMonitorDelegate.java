package com.alibaba.mtl.appmonitor;

import android.app.Application;
import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.a.e;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.d.j;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.Measure;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.appmonitor.model.Metric;
import com.alibaba.mtl.appmonitor.model.MetricRepo;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.l;
import com.alibaba.mtl.log.sign.BaseRequestAuth;
import com.alibaba.mtl.log.sign.IRequestAuth;
import com.alibaba.mtl.log.sign.SecurityRequestAuth;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AppMonitorDelegate {
    private static Application b;
    public static boolean IS_DEBUG = false;
    static volatile boolean i = false;

    public static synchronized void init(Application application) {
        i.a("AppMonitorDelegate", "start init");
        try {
            if (!i) {
                b = application;
                com.alibaba.mtl.log.a.init(application.getApplicationContext());
                b.init();
                c.init();
                a.init(application);
                l.a(application.getApplicationContext());
                i = true;
            }
        } catch (Throwable th) {
            destroy();
        }
    }

    public static synchronized void destroy() {
        try {
            i.a("AppMonitorDelegate", "start destory");
            if (i) {
                c.e();
                c.destroy();
                b.destroy();
                if (b != null) {
                    l.b(b.getApplicationContext());
                }
                i = false;
            }
        } catch (Throwable th) {
            com.alibaba.mtl.appmonitor.b.b.m16a(th);
        }
    }

    public static synchronized void triggerUpload() {
        try {
            i.a("AppMonitorDelegate", "triggerUpload");
            if (i && com.alibaba.mtl.log.a.a.m19f()) {
                c.e();
            }
        } catch (Throwable th) {
            com.alibaba.mtl.appmonitor.b.b.m16a(th);
        }
    }

    public static void setStatisticsInterval(int statisticsInterval) {
        for (f fVar : f.values()) {
            fVar.setStatisticsInterval(statisticsInterval);
            setStatisticsInterval(fVar, statisticsInterval);
        }
    }

    public static void setSampling(int sampling) {
        i.a("AppMonitorDelegate", "[setSampling]");
        for (f fVar : f.values()) {
            fVar.c(sampling);
            j.a().a(fVar, sampling);
        }
    }

    public static void enableLog(boolean open) {
        i.a("AppMonitorDelegate", "[enableLog]");
        i.d(open);
    }

    public static void register(String module, String monitorPoint, MeasureSet measures) {
        register(module, monitorPoint, measures, (DimensionSet) null);
    }

    public static void register(String module, String monitorPoint, MeasureSet measures, boolean isCommitDetail) {
        register(module, monitorPoint, measures, null, isCommitDetail);
    }

    public static void register(String module, String monitorPoint, MeasureSet measures, DimensionSet dimensions) {
        register(module, monitorPoint, measures, dimensions, false);
    }

    public static void register(String module, String monitorPoint, MeasureSet measures, DimensionSet dimensions, boolean isCommitDetail) {
        try {
            if (i) {
                if (com.alibaba.mtl.appmonitor.f.b.isBlank(module) || com.alibaba.mtl.appmonitor.f.b.isBlank(monitorPoint)) {
                    i.a("AppMonitorDelegate", "register stat event. module: ", module, " monitorPoint: ", monitorPoint);
                    if (IS_DEBUG) {
                        throw new com.alibaba.mtl.appmonitor.b.a("register error. module and monitorPoint can't be null");
                    }
                    return;
                }
                MetricRepo.getRepo().add(new Metric(module, monitorPoint, measures, dimensions, isCommitDetail));
            }
        } catch (Throwable th) {
            com.alibaba.mtl.appmonitor.b.b.m16a(th);
        }
    }

    public static class Alarm {
        public static void setStatisticsInterval(int statisticsInterval) {
            f.ALARM.setStatisticsInterval(statisticsInterval);
            AppMonitorDelegate.setStatisticsInterval(f.ALARM, statisticsInterval);
        }

        public static void setSampling(int sampling) {
            j.a().a(f.ALARM, sampling);
        }

        @Deprecated
        public static boolean checkSampled(String module, String monitorPoint) {
            return j.a(f.ALARM, module, monitorPoint);
        }

        public static void commitSuccess(String module, String monitorPoint, Map<String, String> exta) {
            commitSuccess(module, monitorPoint, null, exta);
        }

        public static void commitSuccess(String module, String monitorPoint, String arg, Map<String, String> exta) {
            try {
                if (TextUtils.isEmpty(module) || TextUtils.isEmpty(monitorPoint)) {
                    i.a("AppMonitorDelegate", "module & monitorPoint must not null");
                    return;
                }
                com.alibaba.mtl.log.b.a.B();
                if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.m19f() && f.ALARM.isOpen() && (AppMonitorDelegate.IS_DEBUG || j.a(module, monitorPoint, (Boolean) true, (Map<String, String>) null))) {
                    i.a("AppMonitorDelegate", "commitSuccess module:", module, " monitorPoint:", monitorPoint);
                    com.alibaba.mtl.log.b.a.C();
                    e.a().a(f.ALARM.m12a(), module, monitorPoint, arg, exta);
                    return;
                }
                i.a("log discard !", Constants.MAIN_VERSION_TAG);
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m16a(th);
            }
        }

        public static void commitFail(String module, String monitorPoint, String errorCode, String errorMsg, Map<String, String> exta) {
            commitFail(module, monitorPoint, null, errorCode, errorMsg, exta);
        }

        public static void commitFail(String module, String monitorPoint, String arg, String errorCode, String errorMsg, Map<String, String> exta) {
            try {
                if (TextUtils.isEmpty(module) || TextUtils.isEmpty(monitorPoint)) {
                    i.a("AppMonitorDelegate", "module & monitorPoint must not null");
                    return;
                }
                com.alibaba.mtl.log.b.a.B();
                HashMap map = new HashMap();
                map.put("_status", PushConstants.PUSH_TYPE_NOTIFY);
                if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.m19f() && f.ALARM.isOpen() && (AppMonitorDelegate.IS_DEBUG || j.a(module, monitorPoint, (Boolean) false, (Map<String, String>) map))) {
                    i.a("AppMonitorDelegate", "commitFail module:", module, " monitorPoint:", monitorPoint, " errorCode:", errorCode, "errorMsg:", errorMsg);
                    com.alibaba.mtl.log.b.a.C();
                    e.a().a(f.ALARM.m12a(), module, monitorPoint, arg, errorCode, errorMsg, exta);
                    return;
                }
                i.a("log discard !", Constants.MAIN_VERSION_TAG);
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m16a(th);
            }
        }
    }

    public static class Counter {
        public static void setStatisticsInterval(int statisticsInterval) {
            f.COUNTER.setStatisticsInterval(statisticsInterval);
            AppMonitorDelegate.setStatisticsInterval(f.COUNTER, statisticsInterval);
        }

        public static void setSampling(int sampling) {
            j.a().a(f.COUNTER, sampling);
        }

        @Deprecated
        public static boolean checkSampled(String module, String monitorPoint) {
            return j.a(f.COUNTER, module, monitorPoint);
        }

        public static void commit(String module, String monitorPoint, double value, Map<String, String> exta) {
            commit(module, monitorPoint, null, value, exta);
        }

        public static void commit(String module, String monitorPoint, String arg, double value, Map<String, String> exta) {
            try {
                if (TextUtils.isEmpty(module) || TextUtils.isEmpty(monitorPoint)) {
                    i.a("AppMonitorDelegate", "module & monitorPoint must not null");
                } else {
                    com.alibaba.mtl.log.b.a.z();
                    if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.m19f() && f.COUNTER.isOpen() && (AppMonitorDelegate.IS_DEBUG || j.a(f.COUNTER, module, monitorPoint))) {
                        i.a("AppMonitorDelegate", "commitCount module: ", module, " monitorPoint: ", monitorPoint, " value: ", Double.valueOf(value));
                        com.alibaba.mtl.log.b.a.A();
                        e.a().a(f.COUNTER.m12a(), module, monitorPoint, arg, value, exta);
                    }
                }
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m16a(th);
            }
        }
    }

    public static class OffLineCounter {
        public static void setStatisticsInterval(int statisticsInterval) {
            f.OFFLINE_COUNTER.setStatisticsInterval(statisticsInterval);
            AppMonitorDelegate.setStatisticsInterval(f.OFFLINE_COUNTER, statisticsInterval);
        }

        public static void setSampling(int sampling) {
            j.a().a(f.OFFLINE_COUNTER, sampling);
        }

        @Deprecated
        public static boolean checkSampled(String module, String monitorPoint) {
            return j.a(f.OFFLINE_COUNTER, module, monitorPoint);
        }

        public static void commit(String module, String monitorPoint, double value) {
            try {
                if (TextUtils.isEmpty(module) || TextUtils.isEmpty(monitorPoint)) {
                    i.a("AppMonitorDelegate", "module & monitorPoint must not null");
                } else {
                    com.alibaba.mtl.log.b.a.x();
                    if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.m19f() && f.OFFLINE_COUNTER.isOpen() && (AppMonitorDelegate.IS_DEBUG || j.a(f.OFFLINE_COUNTER, module, monitorPoint))) {
                        i.a("AppMonitorDelegate", "commitOffLineCount module: ", module, " monitorPoint: ", monitorPoint, " value: ", Double.valueOf(value));
                        com.alibaba.mtl.log.b.a.y();
                        e.a().a(f.OFFLINE_COUNTER.m12a(), module, monitorPoint, (String) null, value, (Map<String, String>) null);
                    }
                }
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m16a(th);
            }
        }
    }

    public static class Stat {
        public static void setStatisticsInterval(int statisticsInterval) {
            f.STAT.setStatisticsInterval(statisticsInterval);
            AppMonitorDelegate.setStatisticsInterval(f.STAT, statisticsInterval);
        }

        public static void setSampling(int sampling) {
            j.a().a(f.STAT, sampling);
        }

        @Deprecated
        public static boolean checkSampled(String module, String monitorPoint) {
            return j.a(f.STAT, module, monitorPoint);
        }

        public static void begin(String module, String monitorPoint, String measureName) {
            try {
                if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.m19f() && f.STAT.isOpen()) {
                    if (AppMonitorDelegate.IS_DEBUG || j.a(f.STAT, module, monitorPoint)) {
                        i.a("AppMonitorDelegate", "statEvent begin. module: ", module, " monitorPoint: ", monitorPoint, " measureName: ", measureName);
                        e.a().a(Integer.valueOf(f.STAT.m12a()), module, monitorPoint, measureName);
                    }
                }
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m16a(th);
            }
        }

        public static void end(String module, String monitorPoint, String measureName) {
            try {
                if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.m19f() && f.STAT.isOpen()) {
                    if (AppMonitorDelegate.IS_DEBUG || j.a(f.STAT, module, monitorPoint)) {
                        i.a("AppMonitorDelegate", "statEvent end. module: ", module, " monitorPoint: ", monitorPoint, " measureName: ", measureName);
                        e.a().a(module, monitorPoint, measureName);
                    }
                }
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m16a(th);
            }
        }

        public static void commit(String module, String monitorPoint, double value, Map<String, String> exta) {
            commit(module, monitorPoint, (DimensionValueSet) null, value, exta);
        }

        public static void commit(String module, String monitorPoint, DimensionValueSet dimensionValues, double value, Map<String, String> exta) {
            try {
                if (TextUtils.isEmpty(module) || TextUtils.isEmpty(monitorPoint)) {
                    i.a("AppMonitorDelegate", "module & monitorPoint must not null");
                } else {
                    com.alibaba.mtl.log.b.a.v();
                    if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.m19f() && f.STAT.isOpen() && (AppMonitorDelegate.IS_DEBUG || j.a(f.STAT, module, monitorPoint))) {
                        i.a("AppMonitorDelegate", "statEvent commit. module: ", module, " monitorPoint: ", monitorPoint);
                        Metric metric = MetricRepo.getRepo().getMetric(module, monitorPoint);
                        com.alibaba.mtl.log.b.a.w();
                        if (metric != null) {
                            List<Measure> measures = metric.getMeasureSet().getMeasures();
                            if (measures.size() == 1) {
                                commit(module, monitorPoint, dimensionValues, ((MeasureValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(MeasureValueSet.class, new Object[0])).setValue(measures.get(0).getName(), value), exta);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m16a(th);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0039, code lost:
        
            if (com.alibaba.mtl.appmonitor.d.j.a(com.alibaba.mtl.appmonitor.a.f.STAT, r7, r8, r9 != null ? r9.getMap() : null) != false) goto L21;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static void commit(String module, String monitorPoint, DimensionValueSet dimensionValues, MeasureValueSet measureValues, Map<String, String> exta) {
            try {
                if (TextUtils.isEmpty(module) || TextUtils.isEmpty(monitorPoint)) {
                    i.a("AppMonitorDelegate", "module & monitorPoint must not null");
                    return;
                }
                com.alibaba.mtl.log.b.a.v();
                if (AppMonitorDelegate.i && com.alibaba.mtl.log.a.a.m19f() && f.STAT.isOpen()) {
                    if (!AppMonitorDelegate.IS_DEBUG) {
                    }
                    i.a("statEvent commit success", "statEvent commit. module: ", module, " monitorPoint: ", monitorPoint);
                    com.alibaba.mtl.log.b.a.w();
                    e.a().a(f.STAT.m12a(), module, monitorPoint, measureValues, dimensionValues, exta);
                    return;
                }
                i.a("statEvent commit failed,log discard", " ,. module: ", module, " monitorPoint: ", monitorPoint);
            } catch (Throwable th) {
                com.alibaba.mtl.appmonitor.b.b.m16a(th);
            }
        }
    }

    static void setStatisticsInterval(f eventType, int statisticsInterval) {
        try {
            if (i && eventType != null) {
                c.a(eventType.m12a(), statisticsInterval);
                if (statisticsInterval > 0) {
                    eventType.b(true);
                } else {
                    eventType.b(false);
                }
            }
        } catch (Throwable th) {
            com.alibaba.mtl.appmonitor.b.b.m16a(th);
        }
    }

    public static void setRequestAuthInfo(boolean isSecurity, String appkey, String secret, String authcode) {
        IRequestAuth baseRequestAuth;
        if (isSecurity) {
            baseRequestAuth = new SecurityRequestAuth(appkey, authcode);
        } else {
            boolean z = false;
            if ("1".equalsIgnoreCase(authcode)) {
                z = true;
            }
            baseRequestAuth = new BaseRequestAuth(appkey, secret, z);
        }
        com.alibaba.mtl.log.a.a(baseRequestAuth);
        com.alibaba.mtl.log.a.a.init(b);
    }

    public static void setChannel(String channel) {
        com.alibaba.mtl.log.a.setChannel(channel);
    }

    public static void turnOnRealTimeDebug(Map<String, String> params) {
        com.alibaba.mtl.log.a.a.turnOnRealTimeDebug(params);
    }

    public static void turnOffRealTimeDebug() {
        i.a("AppMonitorDelegate", "[turnOffRealTimeDebug]");
    }
}
