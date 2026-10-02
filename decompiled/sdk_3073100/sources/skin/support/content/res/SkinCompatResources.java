package skin.support.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.text.TextUtils;
import android.util.TypedValue;
import skin.support.SkinCompatManager$SkinLoaderStrategy;
import skin.support.widget.SkinCompatDrawableManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatResources {
    private static volatile SkinCompatResources sInstance;
    private Resources mResources;
    private SkinCompatManager$SkinLoaderStrategy mStrategy;
    private String mSkinPkgName = "";
    private String mSkinName = "";
    private boolean isDefaultSkin = true;

    private SkinCompatResources() {
    }

    public static SkinCompatResources getInstance() {
        if (sInstance == null) {
            synchronized (SkinCompatResources.class) {
                if (sInstance == null) {
                    sInstance = new SkinCompatResources();
                }
            }
        }
        return sInstance;
    }

    public void reset() {
        this.mResources = null;
        this.mSkinPkgName = "";
        this.mSkinName = "";
        this.mStrategy = null;
        this.isDefaultSkin = true;
        SkinCompatDrawableManager.get().clearCaches();
    }

    public void setupSkin(Resources resources, String pkgName, String skinName, SkinCompatManager$SkinLoaderStrategy strategy) {
        this.mResources = resources;
        this.mSkinPkgName = pkgName;
        this.mSkinName = skinName;
        this.mStrategy = strategy;
        this.isDefaultSkin = TextUtils.isEmpty(skinName) || TextUtils.isEmpty(pkgName) || resources == null;
        SkinCompatDrawableManager.get().clearCaches();
    }

    private int getTargetResId(Context context, int resId) {
        String resName = null;
        try {
            if (this.mStrategy != null) {
                resName = this.mStrategy.getTargetResourceEntryName(context, this.mSkinName, resId);
            }
            if (TextUtils.isEmpty(resName)) {
                resName = context.getResources().getResourceEntryName(resId);
            }
            String type = context.getResources().getResourceTypeName(resId);
            return this.mResources.getIdentifier(resName, type, this.mSkinPkgName);
        } catch (Exception e) {
            return 0;
        }
    }

    private int getSkinColor(Context context, int resId) {
        int targetResId;
        return (this.isDefaultSkin || (targetResId = getTargetResId(context, resId)) == 0) ? context.getResources().getColor(resId) : this.mResources.getColor(targetResId);
    }

    private ColorStateList getSkinColorStateList(Context context, int resId) {
        int targetResId;
        return (this.isDefaultSkin || (targetResId = getTargetResId(context, resId)) == 0) ? context.getResources().getColorStateList(resId) : this.mResources.getColorStateList(targetResId);
    }

    private Drawable getSkinDrawable(Context context, int resId) {
        int targetResId;
        return (this.isDefaultSkin || (targetResId = getTargetResId(context, resId)) == 0) ? context.getResources().getDrawable(resId) : this.mResources.getDrawable(targetResId);
    }

    private Drawable getSkinDrawableCompat(Context context, int resId) {
        if (AppCompatDelegate.isCompatVectorFromResourcesEnabled()) {
            if (!this.isDefaultSkin) {
                try {
                    return SkinCompatDrawableManager.get().getDrawable(context, resId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return AppCompatResources.getDrawable(context, resId);
        }
        return getSkinDrawable(context, resId);
    }

    private XmlResourceParser getSkinXml(Context context, int resId) {
        int targetResId;
        return (this.isDefaultSkin || (targetResId = getTargetResId(context, resId)) == 0) ? context.getResources().getXml(resId) : this.mResources.getXml(targetResId);
    }

    private void getSkinValue(Context context, int resId, TypedValue outValue, boolean resolveRefs) {
        int targetResId;
        if (!this.isDefaultSkin && (targetResId = getTargetResId(context, resId)) != 0) {
            this.mResources.getValue(targetResId, outValue, resolveRefs);
        } else {
            context.getResources().getValue(resId, outValue, resolveRefs);
        }
    }

    public static int getColor(Context context, int resId) {
        return getInstance().getSkinColor(context, resId);
    }

    public static ColorStateList getColorStateList(Context context, int resId) {
        return getInstance().getSkinColorStateList(context, resId);
    }

    public static Drawable getDrawable(Context context, int resId) {
        return getInstance().getSkinDrawable(context, resId);
    }

    public static Drawable getDrawableCompat(Context context, int resId) {
        return getInstance().getSkinDrawableCompat(context, resId);
    }

    public static XmlResourceParser getXml(Context context, int resId) {
        return getInstance().getSkinXml(context, resId);
    }

    public static void getValue(Context context, int resId, TypedValue outValue, boolean resolveRefs) {
        getInstance().getSkinValue(context, resId, outValue, resolveRefs);
    }
}
