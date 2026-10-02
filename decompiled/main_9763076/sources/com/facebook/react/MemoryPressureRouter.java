package com.facebook.react;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import com.facebook.react.bridge.MemoryPressure;
import com.facebook.react.bridge.MemoryPressureListener;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MemoryPressureRouter {
    private final Set<MemoryPressureListener> mListeners = Collections.synchronizedSet(new LinkedHashSet());
    private final ComponentCallbacks2 mCallbacks = new ComponentCallbacks2() { // from class: com.facebook.react.MemoryPressureRouter.1
        @Override // android.content.ComponentCallbacks2
        public void onTrimMemory(int level) {
            MemoryPressureRouter.this.trimMemory(level);
        }

        @Override // android.content.ComponentCallbacks
        public void onConfigurationChanged(Configuration newConfig) {
        }

        @Override // android.content.ComponentCallbacks
        public void onLowMemory() {
        }
    };

    MemoryPressureRouter(Context context) {
        context.getApplicationContext().registerComponentCallbacks(this.mCallbacks);
    }

    public void addMemoryPressureListener(MemoryPressureListener listener) {
        this.mListeners.add(listener);
    }

    public void removeMemoryPressureListener(MemoryPressureListener listener) {
        this.mListeners.remove(listener);
    }

    public void destroy(Context context) {
        context.getApplicationContext().unregisterComponentCallbacks(this.mCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trimMemory(int level) {
        if (level >= 80) {
            dispatchMemoryPressure(MemoryPressure.CRITICAL);
            return;
        }
        if (level >= 40 || level == 15) {
            dispatchMemoryPressure(MemoryPressure.MODERATE);
        } else if (level == 20) {
            dispatchMemoryPressure(MemoryPressure.UI_HIDDEN);
        }
    }

    private void dispatchMemoryPressure(MemoryPressure level) {
        MemoryPressureListener[] listeners = (MemoryPressureListener[]) this.mListeners.toArray(new MemoryPressureListener[this.mListeners.size()]);
        for (MemoryPressureListener listener : listeners) {
            listener.handleMemoryPressure(level);
        }
    }
}
