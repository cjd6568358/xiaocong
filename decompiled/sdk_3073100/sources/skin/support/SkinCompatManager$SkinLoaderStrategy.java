package skin.support;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface SkinCompatManager$SkinLoaderStrategy {
    String getTargetResourceEntryName(Context context, String str, int i);

    int getType();

    String loadSkinInBackground(Context context, String str);
}
