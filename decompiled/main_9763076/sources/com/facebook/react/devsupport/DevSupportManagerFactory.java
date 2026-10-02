package com.facebook.react.devsupport;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DevSupportManagerFactory {
    public static DevSupportManager create(Context applicationContext, ReactInstanceDevCommandsHandler reactInstanceCommandsHandler, String packagerPathForJSBundleName, boolean enableOnCreate, RedBoxHandler redBoxHandler) {
        if (!enableOnCreate) {
            return new DisabledDevSupportManager();
        }
        try {
            String className = "com.facebook.react.devsupport.DevSupportManagerImpl";
            Class<?> devSupportManagerClass = Class.forName(className);
            return (DevSupportManager) devSupportManagerClass.getConstructor(Context.class, ReactInstanceDevCommandsHandler.class, String.class, Boolean.TYPE, RedBoxHandler.class).newInstance(applicationContext, reactInstanceCommandsHandler, packagerPathForJSBundleName, true, redBoxHandler);
        } catch (Exception e) {
            throw new RuntimeException("Requested enabled DevSupportManager, but DevSupportManagerImpl class was not found or could not be created", e);
        }
    }
}
