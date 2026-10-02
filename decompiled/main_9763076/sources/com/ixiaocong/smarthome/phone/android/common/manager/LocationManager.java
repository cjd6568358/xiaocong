package com.ixiaocong.smarthome.phone.android.common.manager;

import android.content.Context;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ixiaocong.smarthome.phone.android.event.callback.LocationCallback;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LocationEvent;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LocationManager implements BDLocationListener {
    private static LocationManager mManager;
    private LocationClient mLocationClient = null;
    private LocationCallback callback = null;

    public static LocationManager getInstance() {
        if (mManager == null) {
            synchronized (LocationManager.class) {
                if (mManager == null) {
                    mManager = new LocationManager();
                }
            }
        }
        return mManager;
    }

    public void getCurrentLocation(Context context) {
        this.mLocationClient = new LocationClient(context);
        this.mLocationClient.registerLocationListener(mManager);
        initLocation();
        this.mLocationClient.start();
    }

    public void getCurrentLocation(Context context, LocationCallback callback) {
        this.callback = callback;
        this.mLocationClient = new LocationClient(context);
        this.mLocationClient.registerLocationListener(mManager);
        initLocation();
        this.mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setCoorType("gcj02");
        option.setScanSpan(0);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(false);
        option.setIsNeedLocationDescribe(false);
        option.setIsNeedLocationPoiList(false);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(true);
        option.setEnableSimulateGps(false);
        this.mLocationClient.setLocOption(option);
    }

    @Override // com.baidu.location.BDLocationListener
    public void onReceiveLocation(BDLocation location) {
        LocationEvent event = new LocationEvent();
        if (location.getLocType() == 61 || location.getLocType() == 161 || location.getLocType() == 66) {
            event.setCityName(location.getCity());
            event.setLat(location.getLatitude() + Constants.MAIN_VERSION_TAG);
            event.setLot(location.getLongitude() + Constants.MAIN_VERSION_TAG);
            event.setAdCode(location.getAdCode());
            event.setProvince(location.getProvince());
            event.setDistrict(location.getDistrict());
            event.setStreet(location.getStreet());
        } else if (location.getLocType() == 167 || location.getLocType() == 63 || location.getLocType() == 62) {
            event.setCityName(location.getCity());
            event.setLat(Constants.MAIN_VERSION_TAG);
            event.setLot(Constants.MAIN_VERSION_TAG);
        }
        if (this.callback != null) {
            this.callback.locationEvent(event);
            this.callback = null;
        }
        EventBus.getDefault().post(event);
        XcLogger.i("BaiduLocationApiDem", "BaiduLocation,city:" + event.getCityName() + ",经度:" + event.getLot() + ",纬度:" + event.getLat() + ",adcode:" + event.getAdCode() + ",code:" + location.getLocType());
        locationStop();
    }

    public void locationStop() {
        if (this.mLocationClient != null) {
            this.mLocationClient.stop();
            XcLogger.i("BaiduLocationApiDem", "locationStop--");
        }
    }
}
