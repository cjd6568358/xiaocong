package rx.android.schedulers;

import android.os.Looper;
import java.util.concurrent.atomic.AtomicReference;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class AndroidSchedulers {
    private static final AtomicReference<AndroidSchedulers> INSTANCE = new AtomicReference<>();
    private final Scheduler mainThreadScheduler;

    private static AndroidSchedulers getInstance() {
        AndroidSchedulers current;
        do {
            current = INSTANCE.get();
            if (current != null) {
                break;
            }
            current = new AndroidSchedulers();
        } while (!INSTANCE.compareAndSet(null, current));
        return current;
    }

    private AndroidSchedulers() {
        RxAndroidSchedulersHook hook = RxAndroidPlugins.getInstance().getSchedulersHook();
        Scheduler main = hook.getMainThreadScheduler();
        if (main != null) {
            this.mainThreadScheduler = main;
        } else {
            this.mainThreadScheduler = new LooperScheduler(Looper.getMainLooper());
        }
    }

    public static Scheduler mainThread() {
        return getInstance().mainThreadScheduler;
    }
}
