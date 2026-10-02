package android.support.v4.media;

import android.os.Bundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MediaBrowserCompatUtils {
    public static boolean areSameOptions(Bundle options1, Bundle options2) {
        if (options1 == options2) {
            return true;
        }
        if (options1 == null) {
            return options2.getInt("android.media.browse.extra.PAGE", -1) == -1 && options2.getInt("android.media.browse.extra.PAGE_SIZE", -1) == -1;
        }
        if (options2 == null) {
            return options1.getInt("android.media.browse.extra.PAGE", -1) == -1 && options1.getInt("android.media.browse.extra.PAGE_SIZE", -1) == -1;
        }
        return options1.getInt("android.media.browse.extra.PAGE", -1) == options2.getInt("android.media.browse.extra.PAGE", -1) && options1.getInt("android.media.browse.extra.PAGE_SIZE", -1) == options2.getInt("android.media.browse.extra.PAGE_SIZE", -1);
    }
}
