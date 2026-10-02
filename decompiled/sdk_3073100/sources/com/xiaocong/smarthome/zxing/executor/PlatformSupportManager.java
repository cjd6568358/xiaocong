package com.xiaocong.smarthome.zxing.executor;

import android.os.Build;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class PlatformSupportManager<T> {
    private static final String TAG = PlatformSupportManager.class.getSimpleName();
    private final T defaultImplementation;
    private final SortedMap<Integer, String> implementations;
    private final Class<T> managedInterface;

    protected PlatformSupportManager(Class<T> managedInterface, T defaultImplementation) {
        if (!managedInterface.isInterface()) {
            throw new IllegalArgumentException();
        }
        if (!managedInterface.isInstance(defaultImplementation)) {
            throw new IllegalArgumentException();
        }
        this.managedInterface = managedInterface;
        this.defaultImplementation = defaultImplementation;
        this.implementations = new TreeMap(Collections.reverseOrder());
    }

    public T build() {
        Iterator<Integer> it = this.implementations.keySet().iterator();
        while (it.hasNext()) {
            Integer next = it.next();
            if (Build.VERSION.SDK_INT >= next.intValue()) {
                try {
                    Class<? extends U> clsAsSubclass = Class.forName(this.implementations.get(next)).asSubclass(this.managedInterface);
                    Log.i(TAG, "Using implementation " + clsAsSubclass + " of " + this.managedInterface + " for SDK " + next);
                    return (T) clsAsSubclass.getConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (ClassNotFoundException e) {
                    Log.w(TAG, e);
                } catch (IllegalAccessException e2) {
                    Log.w(TAG, e2);
                } catch (InstantiationException e3) {
                    Log.w(TAG, e3);
                } catch (NoSuchMethodException e4) {
                    Log.w(TAG, e4);
                } catch (InvocationTargetException e5) {
                    Log.w(TAG, e5);
                }
            }
        }
        Log.i(TAG, "Using default implementation " + this.defaultImplementation.getClass() + " of " + this.managedInterface);
        return this.defaultImplementation;
    }
}
