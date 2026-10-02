package skin.support.load;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import skin.support.SkinCompatManager;
import skin.support.SkinCompatManager$SkinLoaderStrategy;
import skin.support.content.res.SkinCompatResources;
import skin.support.utils.SkinFileUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class SkinSDCardLoader implements SkinCompatManager$SkinLoaderStrategy {
    protected abstract String getSkinPath(Context context, String str);

    @Override // skin.support.SkinCompatManager$SkinLoaderStrategy
    public String loadSkinInBackground(Context context, String skinName) {
        String skinPkgPath = getSkinPath(context, skinName);
        if (SkinFileUtils.isFileExists(skinPkgPath)) {
            String pkgName = SkinCompatManager.getInstance().getSkinPackageName(skinPkgPath);
            Resources resources = SkinCompatManager.getInstance().getSkinResources(skinPkgPath);
            if (resources != null && !TextUtils.isEmpty(pkgName)) {
                SkinCompatResources.getInstance().setupSkin(resources, pkgName, skinName, this);
                return skinName;
            }
        }
        return null;
    }

    @Override // skin.support.SkinCompatManager$SkinLoaderStrategy
    public String getTargetResourceEntryName(Context context, String skinName, int resId) {
        return null;
    }
}
