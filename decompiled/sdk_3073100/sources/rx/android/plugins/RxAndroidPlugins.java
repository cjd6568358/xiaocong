package rx.android.plugins;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class RxAndroidPlugins {
    private static final RxAndroidPlugins INSTANCE = new RxAndroidPlugins();
    private final AtomicReference<RxAndroidSchedulersHook> schedulersHook = new AtomicReference<>();

    public static RxAndroidPlugins getInstance() {
        return INSTANCE;
    }

    RxAndroidPlugins() {
    }

    public RxAndroidSchedulersHook getSchedulersHook() {
        if (this.schedulersHook.get() == null) {
            this.schedulersHook.compareAndSet(null, RxAndroidSchedulersHook.getDefaultInstance());
        }
        return this.schedulersHook.get();
    }
}
