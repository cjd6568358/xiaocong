package com.alibaba.mtl.appmonitor;

import android.app.Application;
import android.os.RemoteException;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.alibaba.mtl.log.e.i;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Monitor extends IMonitor.Stub {
    private Application b;

    Monitor(Application application) {
        this.b = application;
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void init() throws RemoteException {
        AppMonitorDelegate.init(this.b);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void destroy() throws RemoteException {
        AppMonitorDelegate.destroy();
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void triggerUpload() throws RemoteException {
        AppMonitorDelegate.triggerUpload();
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setSampling(int sampling) throws RemoteException {
        AppMonitorDelegate.setSampling(sampling);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void enableLog(boolean open) throws RemoteException {
        AppMonitorDelegate.enableLog(open);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setStatisticsInterval2(int event, int statisticsInterval) throws RemoteException {
        AppMonitorDelegate.setStatisticsInterval(a(event), statisticsInterval);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setRequestAuthInfo(boolean isSecurity, String appkey, String secret, String authcode) throws RemoteException {
        AppMonitorDelegate.setRequestAuthInfo(isSecurity, appkey, secret, authcode);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setChannel(String channel) throws RemoteException {
        AppMonitorDelegate.setChannel(channel);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void turnOnRealTimeDebug(Map params) throws RemoteException {
        AppMonitorDelegate.turnOnRealTimeDebug(params);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void turnOffRealTimeDebug() throws RemoteException {
        AppMonitorDelegate.turnOffRealTimeDebug();
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void counter_setStatisticsInterval(int statisticsInterval) throws RemoteException {
        AppMonitorDelegate.Counter.setStatisticsInterval(statisticsInterval);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void counter_setSampling(int sampling) throws RemoteException {
        AppMonitorDelegate.Counter.setSampling(sampling);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public boolean counter_checkSampled(String module, String monitorPoint) throws RemoteException {
        return AppMonitorDelegate.Counter.checkSampled(module, monitorPoint);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void counter_commit1(String module, String monitorPoint, double value, Map exta) throws RemoteException {
        AppMonitorDelegate.Counter.commit(module, monitorPoint, value, exta);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void counter_commit2(String module, String monitorPoint, String arg, double value, Map exta) throws RemoteException {
        AppMonitorDelegate.Counter.commit(module, monitorPoint, arg, value, exta);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_setStatisticsInterval(int statisticsInterval) throws RemoteException {
        AppMonitorDelegate.Alarm.setStatisticsInterval(statisticsInterval);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_setSampling(int sampling) throws RemoteException {
        AppMonitorDelegate.Alarm.setSampling(sampling);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public boolean alarm_checkSampled(String module, String monitorPoint) throws RemoteException {
        return AppMonitorDelegate.Alarm.checkSampled(module, monitorPoint);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_commitSuccess1(String module, String monitorPoint, Map exta) throws RemoteException {
        AppMonitorDelegate.Alarm.commitSuccess(module, monitorPoint, exta);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_commitSuccess2(String module, String monitorPoint, String arg, Map exta) throws RemoteException {
        AppMonitorDelegate.Alarm.commitSuccess(module, monitorPoint, arg, exta);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_commitFail1(String module, String monitorPoint, String errorCode, String errorMsg, Map exta) throws RemoteException {
        AppMonitorDelegate.Alarm.commitFail(module, monitorPoint, errorCode, errorMsg, exta);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void alarm_commitFail2(String module, String monitorPoint, String arg, String errorCode, String errorMsg, Map exta) throws RemoteException {
        AppMonitorDelegate.Alarm.commitFail(module, monitorPoint, arg, errorCode, errorMsg, exta);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void offlinecounter_setStatisticsInterval(int statisticsInterval) throws RemoteException {
        AppMonitorDelegate.OffLineCounter.setStatisticsInterval(statisticsInterval);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void offlinecounter_setSampling(int sampling) throws RemoteException {
        AppMonitorDelegate.OffLineCounter.setSampling(sampling);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public boolean offlinecounter_checkSampled(String module, String monitorPoint) throws RemoteException {
        return AppMonitorDelegate.OffLineCounter.checkSampled(module, monitorPoint);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void offlinecounter_commit(String module, String monitorPoint, double value) throws RemoteException {
        AppMonitorDelegate.OffLineCounter.commit(module, monitorPoint, value);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void setStatisticsInterval1(int statisticsInterval) throws RemoteException {
        AppMonitorDelegate.setStatisticsInterval(statisticsInterval);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void register1(String module, String monitorPoint, MeasureSet measures) throws RemoteException {
        AppMonitorDelegate.register(module, monitorPoint, measures);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void register2(String module, String monitorPoint, MeasureSet measures, boolean isCommitDetail) throws RemoteException {
        AppMonitorDelegate.register(module, monitorPoint, measures, isCommitDetail);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void register3(String module, String monitorPoint, MeasureSet measures, DimensionSet dimensions) throws RemoteException {
        AppMonitorDelegate.register(module, monitorPoint, measures, dimensions);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void register4(String module, String monitorPoint, MeasureSet measures, DimensionSet dimensions, boolean isCommitDetail) throws RemoteException {
        AppMonitorDelegate.register(module, monitorPoint, measures, dimensions, isCommitDetail);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_begin(String module, String monitorPoint, String measureName) throws RemoteException {
        AppMonitorDelegate.Stat.begin(module, monitorPoint, measureName);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_end(String module, String monitorPoint, String measureName) throws RemoteException {
        AppMonitorDelegate.Stat.end(module, monitorPoint, measureName);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_setStatisticsInterval(int statisticsInterval) throws RemoteException {
        AppMonitorDelegate.Stat.setStatisticsInterval(statisticsInterval);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_setSampling(int sampling) throws RemoteException {
        AppMonitorDelegate.Stat.setSampling(sampling);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public boolean stat_checkSampled(String module, String monitorPoint) throws RemoteException {
        return AppMonitorDelegate.Stat.checkSampled(module, monitorPoint);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_commit1(String module, String monitorPoint, double value, Map exta) throws RemoteException {
        AppMonitorDelegate.Stat.commit(module, monitorPoint, value, exta);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_commit2(String module, String monitorPoint, DimensionValueSet dimensionValues, double value, Map exta) throws RemoteException {
        AppMonitorDelegate.Stat.commit(module, monitorPoint, dimensionValues, value, (Map<String, String>) exta);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void stat_commit3(String module, String monitorPoint, DimensionValueSet dimensionValues, MeasureValueSet measureValues, Map exta) throws RemoteException {
        i.a("Monitor", "[stat_commit3]");
        AppMonitorDelegate.Stat.commit(module, monitorPoint, dimensionValues, measureValues, (Map<String, String>) exta);
    }

    private f a(int i) {
        return f.a(i);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void transaction_begin(Transaction transaction, String measureName) throws RemoteException {
        TransactionDelegate.begin(transaction, measureName);
    }

    @Override // com.alibaba.mtl.appmonitor.IMonitor
    public void transaction_end(Transaction transaction, String measureName) throws RemoteException {
        TransactionDelegate.end(transaction, measureName);
    }
}
