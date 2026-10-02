package skin.support.widget;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class SkinCompatHelper {
    public static final int checkResourceId(int resId) {
        String hexResId = Integer.toHexString(resId);
        if (hexResId.startsWith("1")) {
            return 0;
        }
        return resId;
    }
}
