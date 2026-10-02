package skin.support.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import java.lang.reflect.Field;
import java.util.WeakHashMap;
import skin.support.SkinCompatManager;
import skin.support.annotation.Skinable;
import skin.support.content.res.SkinCompatResources;
import skin.support.observe.SkinObservable;
import skin.support.observe.SkinObserver;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;
import skin.support.widget.SkinCompatThemeUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private static volatile SkinActivityLifecycle sInstance = null;
    private WeakHashMap<Context, SkinCompatDelegate> mSkinDelegateMap;
    private WeakHashMap<Context, SkinObserver> mSkinObserverMap;

    public static SkinActivityLifecycle init(Application application) {
        if (sInstance == null) {
            synchronized (SkinActivityLifecycle.class) {
                if (sInstance == null) {
                    sInstance = new SkinActivityLifecycle(application);
                }
            }
        }
        return sInstance;
    }

    private SkinActivityLifecycle(Application application) {
        application.registerActivityLifecycleCallbacks(this);
        installLayoutFactory(application);
        SkinCompatManager.getInstance().addObserver(getObserver(application));
    }

    private void installLayoutFactory(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        try {
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);
            LayoutInflaterCompat.setFactory(layoutInflater, getSkinDelegate(context));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SkinCompatDelegate getSkinDelegate(Context context) {
        if (this.mSkinDelegateMap == null) {
            this.mSkinDelegateMap = new WeakHashMap<>();
        }
        SkinCompatDelegate mSkinDelegate = this.mSkinDelegateMap.get(context);
        if (mSkinDelegate == null) {
            mSkinDelegate = SkinCompatDelegate.create(context);
        }
        this.mSkinDelegateMap.put(context, mSkinDelegate);
        return mSkinDelegate;
    }

    private SkinObserver getObserver(final Context context) {
        if (this.mSkinObserverMap == null) {
            this.mSkinObserverMap = new WeakHashMap<>();
        }
        SkinObserver observer = this.mSkinObserverMap.get(context);
        if (observer == null) {
            observer = new SkinObserver() { // from class: skin.support.app.SkinActivityLifecycle.1
                @Override // skin.support.observe.SkinObserver
                public void updateSkin(SkinObservable observable, Object o) {
                    if ((context instanceof Activity) && SkinActivityLifecycle.this.isContextSkinEnable(context)) {
                        SkinActivityLifecycle.this.updateStatusBarColor((Activity) context);
                        SkinActivityLifecycle.this.updateWindowBackground((Activity) context);
                    }
                    SkinActivityLifecycle.this.getSkinDelegate(context).applySkin();
                    if (context instanceof SkinCompatSupportable) {
                        ((SkinCompatSupportable) context).applySkin();
                    }
                }
            };
        }
        this.mSkinObserverMap.put(context, observer);
        return observer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStatusBarColor(Activity activity) {
        if (SkinCompatManager.getInstance().isSkinStatusBarColorEnable() && Build.VERSION.SDK_INT >= 21) {
            int statusBarColorResId = SkinCompatThemeUtils.getStatusBarColorResId(activity);
            int colorPrimaryDarkResId = SkinCompatThemeUtils.getColorPrimaryDarkResId(activity);
            if (SkinCompatHelper.checkResourceId(statusBarColorResId) != 0) {
                activity.getWindow().setStatusBarColor(SkinCompatResources.getColor(activity, statusBarColorResId));
            } else if (SkinCompatHelper.checkResourceId(colorPrimaryDarkResId) != 0) {
                activity.getWindow().setStatusBarColor(SkinCompatResources.getColor(activity, colorPrimaryDarkResId));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWindowBackground(Activity activity) {
        Drawable drawable;
        if (SkinCompatManager.getInstance().isSkinWindowBackgroundEnable()) {
            int windowBackgroundResId = SkinCompatThemeUtils.getWindowBackgroundResId(activity);
            if (SkinCompatHelper.checkResourceId(windowBackgroundResId) != 0 && (drawable = SkinCompatResources.getDrawableCompat(activity, windowBackgroundResId)) != null) {
                activity.getWindow().setBackgroundDrawable(drawable);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (isContextSkinEnable(activity)) {
            installLayoutFactory(activity);
            updateStatusBarColor(activity);
            updateWindowBackground(activity);
            if (activity instanceof SkinCompatSupportable) {
                ((SkinCompatSupportable) activity).applySkin();
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (isContextSkinEnable(activity)) {
            SkinCompatManager.getInstance().addObserver(getObserver(activity));
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        if (isContextSkinEnable(activity)) {
            SkinCompatManager.getInstance().deleteObserver(getObserver(activity));
            this.mSkinObserverMap.remove(activity);
            this.mSkinDelegateMap.remove(activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isContextSkinEnable(Context context) {
        return SkinCompatManager.getInstance().isSkinAllActivityEnable() || context.getClass().getAnnotation(Skinable.class) != null || (context instanceof SkinCompatSupportable);
    }
}
