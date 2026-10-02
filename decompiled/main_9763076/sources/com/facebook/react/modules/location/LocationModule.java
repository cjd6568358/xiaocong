package com.facebook.react.modules.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.SystemClock;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LocationModule extends ReactContextBaseJavaModule {
    private static final float RCT_DEFAULT_LOCATION_ACCURACY = 100.0f;
    private final LocationListener mLocationListener;
    private String mWatchedProvider;

    public LocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mLocationListener = new LocationListener() { // from class: com.facebook.react.modules.location.LocationModule.1
            @Override // android.location.LocationListener
            public void onLocationChanged(Location location) {
                ((DeviceEventManagerModule.RCTDeviceEventEmitter) LocationModule.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("geolocationDidChange", LocationModule.locationToMap(location));
            }

            @Override // android.location.LocationListener
            public void onStatusChanged(String provider, int status, Bundle extras) {
                if (status == 0) {
                    LocationModule.this.emitError(PositionError.POSITION_UNAVAILABLE, "Provider " + provider + " is out of service.");
                } else if (status == 1) {
                    LocationModule.this.emitError(PositionError.TIMEOUT, "Provider " + provider + " is temporarily unavailable.");
                }
            }

            @Override // android.location.LocationListener
            public void onProviderEnabled(String provider) {
            }

            @Override // android.location.LocationListener
            public void onProviderDisabled(String provider) {
            }
        };
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "LocationObserver";
    }

    private static class LocationOptions {
        private final float distanceFilter;
        private final boolean highAccuracy;
        private final double maximumAge;
        private final long timeout;

        private LocationOptions(long timeout, double maximumAge, boolean highAccuracy, float distanceFilter) {
            this.timeout = timeout;
            this.maximumAge = maximumAge;
            this.highAccuracy = highAccuracy;
            this.distanceFilter = distanceFilter;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static LocationOptions fromReactMap(ReadableMap map) {
            long timeout = map.hasKey("timeout") ? (long) map.getDouble("timeout") : Long.MAX_VALUE;
            double maximumAge = map.hasKey("maximumAge") ? map.getDouble("maximumAge") : Double.POSITIVE_INFINITY;
            boolean highAccuracy = map.hasKey("enableHighAccuracy") && map.getBoolean("enableHighAccuracy");
            float distanceFilter = map.hasKey("distanceFilter") ? (float) map.getDouble("distanceFilter") : LocationModule.RCT_DEFAULT_LOCATION_ACCURACY;
            return new LocationOptions(timeout, maximumAge, highAccuracy, distanceFilter);
        }
    }

    @ReactMethod
    public void getCurrentPosition(ReadableMap options, Callback success, Callback error) {
        LocationOptions locationOptions = LocationOptions.fromReactMap(options);
        try {
            LocationManager locationManager = (LocationManager) getReactApplicationContext().getSystemService("location");
            String provider = getValidProvider(locationManager, locationOptions.highAccuracy);
            if (provider == null) {
                error.invoke("No available location provider.");
            } else {
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null && SystemClock.currentTimeMillis() - location.getTime() < locationOptions.maximumAge) {
                    success.invoke(locationToMap(location));
                } else {
                    new SingleUpdateRequest(locationManager, provider, locationOptions.timeout, success, error).invoke();
                }
            }
        } catch (SecurityException e) {
            throwLocationPermissionMissing(e);
        }
    }

    @ReactMethod
    public void startObserving(ReadableMap options) {
        if (!"gps".equals(this.mWatchedProvider)) {
            LocationOptions locationOptions = LocationOptions.fromReactMap(options);
            try {
                LocationManager locationManager = (LocationManager) getReactApplicationContext().getSystemService("location");
                String provider = getValidProvider(locationManager, locationOptions.highAccuracy);
                if (provider == null) {
                    emitError(PositionError.PERMISSION_DENIED, "No location provider available.");
                    return;
                }
                if (!provider.equals(this.mWatchedProvider)) {
                    locationManager.removeUpdates(this.mLocationListener);
                    locationManager.requestLocationUpdates(provider, 1000L, locationOptions.distanceFilter, this.mLocationListener);
                }
                this.mWatchedProvider = provider;
            } catch (SecurityException e) {
                throwLocationPermissionMissing(e);
            }
        }
    }

    @ReactMethod
    public void stopObserving() {
        LocationManager locationManager = (LocationManager) getReactApplicationContext().getSystemService("location");
        locationManager.removeUpdates(this.mLocationListener);
        this.mWatchedProvider = null;
    }

    private static String getValidProvider(LocationManager locationManager, boolean highAccuracy) {
        String provider = highAccuracy ? "gps" : "network";
        if (!locationManager.isProviderEnabled(provider)) {
            provider = provider.equals("gps") ? "network" : "gps";
            if (!locationManager.isProviderEnabled(provider)) {
                return null;
            }
        }
        return provider;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WritableMap locationToMap(Location location) {
        WritableMap map = Arguments.createMap();
        WritableMap coords = Arguments.createMap();
        coords.putDouble("latitude", location.getLatitude());
        coords.putDouble("longitude", location.getLongitude());
        coords.putDouble("altitude", location.getAltitude());
        coords.putDouble("accuracy", location.getAccuracy());
        coords.putDouble("heading", location.getBearing());
        coords.putDouble("speed", location.getSpeed());
        map.putMap("coords", coords);
        map.putDouble("timestamp", location.getTime());
        if (Build.VERSION.SDK_INT >= 18) {
            map.putBoolean("mocked", location.isFromMockProvider());
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void emitError(int code, String message) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("geolocationError", PositionError.buildError(code, message));
    }

    private static void throwLocationPermissionMissing(SecurityException e) {
        throw new SecurityException("Looks like the app doesn't have the permission to access location.\nAdd the following line to your app's AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_FINE_LOCATION\" />", e);
    }

    private static class SingleUpdateRequest {
        private final Callback mError;
        private final Handler mHandler;
        private final LocationListener mLocationListener;
        private final LocationManager mLocationManager;
        private final String mProvider;
        private final Callback mSuccess;
        private final long mTimeout;
        private final Runnable mTimeoutRunnable;
        private boolean mTriggered;

        private SingleUpdateRequest(LocationManager locationManager, String provider, long timeout, Callback success, Callback error) {
            this.mHandler = new Handler();
            this.mTimeoutRunnable = new Runnable() { // from class: com.facebook.react.modules.location.LocationModule.SingleUpdateRequest.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (SingleUpdateRequest.this) {
                        if (!SingleUpdateRequest.this.mTriggered) {
                            SingleUpdateRequest.this.mError.invoke(PositionError.buildError(PositionError.TIMEOUT, "Location request timed out"));
                            SingleUpdateRequest.this.mLocationManager.removeUpdates(SingleUpdateRequest.this.mLocationListener);
                            SingleUpdateRequest.this.mTriggered = true;
                        }
                    }
                }
            };
            this.mLocationListener = new LocationListener() { // from class: com.facebook.react.modules.location.LocationModule.SingleUpdateRequest.2
                @Override // android.location.LocationListener
                public void onLocationChanged(Location location) {
                    synchronized (SingleUpdateRequest.this) {
                        if (!SingleUpdateRequest.this.mTriggered) {
                            SingleUpdateRequest.this.mSuccess.invoke(LocationModule.locationToMap(location));
                            SingleUpdateRequest.this.mHandler.removeCallbacks(SingleUpdateRequest.this.mTimeoutRunnable);
                            SingleUpdateRequest.this.mTriggered = true;
                        }
                    }
                }

                @Override // android.location.LocationListener
                public void onStatusChanged(String provider2, int status, Bundle extras) {
                }

                @Override // android.location.LocationListener
                public void onProviderEnabled(String provider2) {
                }

                @Override // android.location.LocationListener
                public void onProviderDisabled(String provider2) {
                }
            };
            this.mLocationManager = locationManager;
            this.mProvider = provider;
            this.mTimeout = timeout;
            this.mSuccess = success;
            this.mError = error;
        }

        public void invoke() {
            this.mLocationManager.requestSingleUpdate(this.mProvider, this.mLocationListener, (Looper) null);
            this.mHandler.postDelayed(this.mTimeoutRunnable, this.mTimeout);
        }
    }
}
