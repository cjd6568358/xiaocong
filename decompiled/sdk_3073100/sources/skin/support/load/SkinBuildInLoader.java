package skin.support.load;

import android.content.Context;
import skin.support.SkinCompatManager$SkinLoaderStrategy;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinBuildInLoader implements SkinCompatManager$SkinLoaderStrategy {
    @Override // skin.support.SkinCompatManager$SkinLoaderStrategy
    public String loadSkinInBackground(Context context, String skinName) {
        SkinCompatResources.getInstance().setupSkin(context.getResources(), context.getPackageName(), skinName, this);
        return skinName;
    }

    @Override // skin.support.SkinCompatManager$SkinLoaderStrategy
    public String getTargetResourceEntryName(Context context, String skinName, int resId) {
        return context.getResources().getResourceEntryName(resId) + "_" + skinName;
    }

    @Override // skin.support.SkinCompatManager$SkinLoaderStrategy
    public int getType() {
        return 1;
    }
}
