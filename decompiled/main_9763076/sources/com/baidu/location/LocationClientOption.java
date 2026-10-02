package com.baidu.location;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class LocationClientOption {
    protected LocationMode a;
    public String addrType;
    public float autoNotifyLocSensitivity;
    public int autoNotifyMaxInterval;
    public int autoNotifyMinDistance;
    public int autoNotifyMinTimeInterval;
    public String coorType;
    public boolean disableLocCache;
    public boolean enableSimulateGps;
    public boolean isIgnoreCacheException;
    public boolean isIgnoreKillProcess;
    public boolean isNeedAltitude;
    public boolean isNeedAptag;
    public boolean isNeedAptagd;
    public boolean isNeedPoiRegion;
    public boolean isNeedRegular;
    public boolean location_change_notify;
    public boolean mIsNeedDeviceDirect;
    public boolean openGps;
    public int priority;
    public String prodName;
    public int scanSpan;
    public String serviceName;
    public int timeOut;
    public int wifiCacheTimeOut;

    public enum LocationMode {
        Hight_Accuracy,
        Battery_Saving,
        Device_Sensors
    }

    public LocationClientOption() {
        this.coorType = "gcj02";
        this.addrType = "detail";
        this.openGps = false;
        this.scanSpan = 0;
        this.timeOut = 12000;
        this.prodName = "SDK6.0";
        this.priority = 1;
        this.location_change_notify = false;
        this.disableLocCache = true;
        this.enableSimulateGps = false;
        this.serviceName = "com.baidu.location.service_v2.9";
        this.isIgnoreCacheException = false;
        this.isIgnoreKillProcess = true;
        this.mIsNeedDeviceDirect = false;
        this.isNeedAptag = false;
        this.isNeedAptagd = false;
        this.isNeedPoiRegion = false;
        this.isNeedRegular = false;
        this.isNeedAltitude = false;
        this.autoNotifyMaxInterval = 0;
        this.autoNotifyLocSensitivity = 0.5f;
        this.autoNotifyMinTimeInterval = 0;
        this.autoNotifyMinDistance = 0;
        this.wifiCacheTimeOut = Integer.MAX_VALUE;
    }

    public LocationClientOption(LocationClientOption locationClientOption) {
        this.coorType = "gcj02";
        this.addrType = "detail";
        this.openGps = false;
        this.scanSpan = 0;
        this.timeOut = 12000;
        this.prodName = "SDK6.0";
        this.priority = 1;
        this.location_change_notify = false;
        this.disableLocCache = true;
        this.enableSimulateGps = false;
        this.serviceName = "com.baidu.location.service_v2.9";
        this.isIgnoreCacheException = false;
        this.isIgnoreKillProcess = true;
        this.mIsNeedDeviceDirect = false;
        this.isNeedAptag = false;
        this.isNeedAptagd = false;
        this.isNeedPoiRegion = false;
        this.isNeedRegular = false;
        this.isNeedAltitude = false;
        this.autoNotifyMaxInterval = 0;
        this.autoNotifyLocSensitivity = 0.5f;
        this.autoNotifyMinTimeInterval = 0;
        this.autoNotifyMinDistance = 0;
        this.wifiCacheTimeOut = Integer.MAX_VALUE;
        this.coorType = locationClientOption.coorType;
        this.addrType = locationClientOption.addrType;
        this.openGps = locationClientOption.openGps;
        this.scanSpan = locationClientOption.scanSpan;
        this.timeOut = locationClientOption.timeOut;
        this.prodName = locationClientOption.prodName;
        this.priority = locationClientOption.priority;
        this.location_change_notify = locationClientOption.location_change_notify;
        this.serviceName = locationClientOption.serviceName;
        this.disableLocCache = locationClientOption.disableLocCache;
        this.isIgnoreCacheException = locationClientOption.isIgnoreCacheException;
        this.isIgnoreKillProcess = locationClientOption.isIgnoreKillProcess;
        this.enableSimulateGps = locationClientOption.enableSimulateGps;
        this.a = locationClientOption.a;
        this.isNeedAptag = locationClientOption.isNeedAptag;
        this.isNeedAptagd = locationClientOption.isNeedAptagd;
        this.isNeedPoiRegion = locationClientOption.isNeedPoiRegion;
        this.isNeedRegular = locationClientOption.isNeedRegular;
        this.mIsNeedDeviceDirect = locationClientOption.mIsNeedDeviceDirect;
        this.isNeedAltitude = locationClientOption.isNeedAltitude;
        this.autoNotifyMaxInterval = locationClientOption.autoNotifyMaxInterval;
        this.autoNotifyLocSensitivity = locationClientOption.autoNotifyLocSensitivity;
        this.autoNotifyMinTimeInterval = locationClientOption.autoNotifyMinTimeInterval;
        this.autoNotifyMinDistance = locationClientOption.autoNotifyMinDistance;
        this.wifiCacheTimeOut = locationClientOption.wifiCacheTimeOut;
    }

    public void SetIgnoreCacheException(boolean z) {
        this.isIgnoreCacheException = z;
    }

    int a() {
        return this.autoNotifyMaxInterval;
    }

    float b() {
        return this.autoNotifyLocSensitivity;
    }

    public String getAddrType() {
        return this.addrType;
    }

    public int getAutoNotifyMinDistance() {
        return this.autoNotifyMinDistance;
    }

    public int getAutoNotifyMinTimeInterval() {
        return this.autoNotifyMinTimeInterval;
    }

    public String getCoorType() {
        return this.coorType;
    }

    public boolean optionEquals(LocationClientOption locationClientOption) {
        return this.coorType.equals(locationClientOption.coorType) && this.addrType.equals(locationClientOption.addrType) && this.openGps == locationClientOption.openGps && this.scanSpan == locationClientOption.scanSpan && this.timeOut == locationClientOption.timeOut && this.prodName.equals(locationClientOption.prodName) && this.location_change_notify == locationClientOption.location_change_notify && this.priority == locationClientOption.priority && this.disableLocCache == locationClientOption.disableLocCache && this.isIgnoreCacheException == locationClientOption.isIgnoreCacheException && this.isIgnoreKillProcess == locationClientOption.isIgnoreKillProcess && this.isNeedAptag == locationClientOption.isNeedAptag && this.isNeedAptagd == locationClientOption.isNeedAptagd && this.isNeedPoiRegion == locationClientOption.isNeedPoiRegion && this.isNeedRegular == locationClientOption.isNeedRegular && this.mIsNeedDeviceDirect == locationClientOption.mIsNeedDeviceDirect && this.autoNotifyMaxInterval == locationClientOption.autoNotifyMaxInterval && this.autoNotifyLocSensitivity == locationClientOption.autoNotifyLocSensitivity && this.autoNotifyMinTimeInterval == locationClientOption.autoNotifyMinTimeInterval && this.autoNotifyMinDistance == locationClientOption.autoNotifyMinDistance && this.wifiCacheTimeOut == locationClientOption.wifiCacheTimeOut && this.isNeedAltitude == locationClientOption.isNeedAltitude && this.a == locationClientOption.a;
    }

    public void setCoorType(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.equals("gcj02") || lowerCase.equals("bd09") || lowerCase.equals("bd09ll")) {
            this.coorType = lowerCase;
        }
    }

    public void setEnableSimulateGps(boolean z) {
        this.enableSimulateGps = z;
    }

    public void setIgnoreKillProcess(boolean z) {
        this.isIgnoreKillProcess = z;
    }

    public void setIsNeedAddress(boolean z) {
        if (z) {
            this.addrType = "all";
        } else {
            this.addrType = "noaddr";
        }
    }

    public void setIsNeedLocationDescribe(boolean z) {
        this.isNeedAptag = z;
    }

    public void setIsNeedLocationPoiList(boolean z) {
        this.isNeedAptagd = z;
    }

    public void setLocationMode(LocationMode locationMode) {
        switch (locationMode) {
            case Hight_Accuracy:
                this.openGps = true;
                this.priority = 1;
                break;
            case Battery_Saving:
                this.openGps = false;
                this.priority = 2;
                break;
            case Device_Sensors:
                this.priority = 3;
                this.openGps = true;
                break;
            default:
                throw new IllegalArgumentException("Illegal this mode : " + locationMode);
        }
        this.a = locationMode;
    }

    public void setLocationNotify(boolean z) {
        this.location_change_notify = z;
    }

    public void setOpenGps(boolean z) {
        this.openGps = z;
    }

    public void setScanSpan(int i) {
        this.scanSpan = i;
    }
}
