package skin.support;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.SparseArray;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import skin.support.app.SkinActivityLifecycle;
import skin.support.app.SkinLayoutInflater;
import skin.support.load.SkinAssetsLoader;
import skin.support.load.SkinBuildInLoader;
import skin.support.load.SkinPrefixBuildInLoader;
import skin.support.observe.SkinObservable;
import skin.support.utils.SkinPreference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SkinCompatManager extends SkinObservable {
    private static volatile SkinCompatManager sInstance;
    private final Context mAppContext;
    private final Object mLock = new Object();
    private boolean mLoading = false;
    private List<SkinLayoutInflater> mInflaters = new ArrayList();
    private List<SkinLayoutInflater> mHookInflaters = new ArrayList();
    private SparseArray<SkinLoaderStrategy> mStrategyMap = new SparseArray<>();
    private boolean mSkinAllActivityEnable = true;
    private boolean mSkinStatusBarColorEnable = true;
    private boolean mSkinWindowBackgroundColorEnable = true;

    public static SkinCompatManager init(Context context) {
        if (sInstance == null) {
            synchronized (SkinCompatManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinCompatManager(context);
                }
            }
        }
        SkinPreference.init(context);
        return sInstance;
    }

    public static SkinCompatManager getInstance() {
        return sInstance;
    }

    public static SkinCompatManager withoutActivity(Application application) {
        init(application);
        SkinActivityLifecycle.init(application);
        return sInstance;
    }

    private SkinCompatManager(Context context) {
        this.mAppContext = context.getApplicationContext();
        initLoaderStrategy();
    }

    private void initLoaderStrategy() {
        this.mStrategyMap.put(0, new SkinAssetsLoader());
        this.mStrategyMap.put(1, new SkinBuildInLoader());
        this.mStrategyMap.put(2, new SkinPrefixBuildInLoader());
    }

    public SkinCompatManager addInflater(SkinLayoutInflater inflater) {
        this.mInflaters.add(inflater);
        return this;
    }

    public List<SkinLayoutInflater> getInflaters() {
        return this.mInflaters;
    }

    public List<SkinLayoutInflater> getHookInflaters() {
        return this.mHookInflaters;
    }

    public void restoreDefaultTheme() {
        loadSkin(Constants.MAIN_VERSION_TAG);
    }

    public SkinCompatManager setSkinAllActivityEnable(boolean enable) {
        this.mSkinAllActivityEnable = enable;
        return this;
    }

    public boolean isSkinAllActivityEnable() {
        return this.mSkinAllActivityEnable;
    }

    public SkinCompatManager setSkinStatusBarColorEnable(boolean enable) {
        this.mSkinStatusBarColorEnable = enable;
        return this;
    }

    public boolean isSkinStatusBarColorEnable() {
        return this.mSkinStatusBarColorEnable;
    }

    public boolean isSkinWindowBackgroundEnable() {
        return this.mSkinWindowBackgroundColorEnable;
    }

    public AsyncTask loadSkin() {
        String skin2 = SkinPreference.getInstance().getSkinName();
        int strategy = SkinPreference.getInstance().getSkinStrategy();
        if (TextUtils.isEmpty(skin2) || strategy == -1) {
            return null;
        }
        return loadSkin(skin2, null, strategy);
    }

    @Deprecated
    public AsyncTask loadSkin(String skinName) {
        return loadSkin(skinName, null);
    }

    @Deprecated
    public AsyncTask loadSkin(String skinName, SkinLoaderListener listener) {
        return loadSkin(skinName, listener, 0);
    }

    public AsyncTask loadSkin(String skinName, SkinLoaderListener listener, int strategy) {
        return new SkinLoadTask(this, listener, this.mStrategyMap.get(strategy)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{skinName});
    }

    public String getSkinPackageName(String skinPkgPath) {
        PackageManager mPm = this.mAppContext.getPackageManager();
        PackageInfo info = mPm.getPackageArchiveInfo(skinPkgPath, 1);
        return info.packageName;
    }

    public Resources getSkinResources(String skinPkgPath) {
        try {
            AssetManager assetManager = (AssetManager) AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPkgPath);
            Resources superRes = this.mAppContext.getResources();
            return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
