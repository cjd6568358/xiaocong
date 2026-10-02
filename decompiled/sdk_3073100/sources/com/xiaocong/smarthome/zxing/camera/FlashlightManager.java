package com.xiaocong.smarthome.zxing.camera;

import android.os.IBinder;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class FlashlightManager {
    private static final String TAG = FlashlightManager.class.getSimpleName();
    private static final Object iHardwareService = getHardwareService();
    private static final Method setFlashEnabledMethod = getSetFlashEnabledMethod(iHardwareService);

    static {
        if (iHardwareService == null) {
        }
    }

    private FlashlightManager() {
    }

    private static Object getHardwareService() {
        Method getServiceMethod;
        Object hardwareService;
        Class<?> iHardwareServiceStubClass;
        Method asInterfaceMethod;
        Class<?> serviceManagerClass = maybeForName("android.os.ServiceManager");
        if (serviceManagerClass == null || (getServiceMethod = maybeGetMethod(serviceManagerClass, "getService", String.class)) == null || (hardwareService = invoke(getServiceMethod, null, "hardware")) == null || (iHardwareServiceStubClass = maybeForName("android.os.IHardwareService$Stub")) == null || (asInterfaceMethod = maybeGetMethod(iHardwareServiceStubClass, "asInterface", IBinder.class)) == null) {
            return null;
        }
        return invoke(asInterfaceMethod, null, hardwareService);
    }

    private static Method getSetFlashEnabledMethod(Object iHardwareService2) {
        if (iHardwareService2 == null) {
            return null;
        }
        Class<?> proxyClass = iHardwareService2.getClass();
        return maybeGetMethod(proxyClass, "setFlashlightEnabled", Boolean.TYPE);
    }

    private static Class<?> maybeForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        } catch (RuntimeException e2) {
            return null;
        }
    }

    private static Method maybeGetMethod(Class<?> clazz, String name, Class<?>... argClasses) {
        try {
            return clazz.getMethod(name, argClasses);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (RuntimeException e2) {
            return null;
        }
    }

    private static Object invoke(Method method, Object instance, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (Exception e) {
            return null;
        }
    }

    static void disableFlashlight() {
        setFlashlight(false);
    }

    private static void setFlashlight(boolean active) {
        if (iHardwareService != null) {
            invoke(setFlashEnabledMethod, iHardwareService, Boolean.valueOf(active));
        }
    }
}
