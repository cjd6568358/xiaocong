package skin.support.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinPreference {
    private static SkinPreference sInstance;
    private final Context mApp;
    private final SharedPreferences.Editor mEditor;
    private final SharedPreferences mPref;

    public static void init(Context context) {
        if (sInstance == null) {
            synchronized (SkinPreference.class) {
                if (sInstance == null) {
                    sInstance = new SkinPreference(context.getApplicationContext());
                }
            }
        }
    }

    public static SkinPreference getInstance() {
        return sInstance;
    }

    private SkinPreference(Context applicationContext) {
        this.mApp = applicationContext;
        this.mPref = this.mApp.getSharedPreferences("meta-data", 0);
        this.mEditor = this.mPref.edit();
    }

    public SkinPreference setSkinName(String skinName) {
        this.mEditor.putString("skin-name", skinName);
        return this;
    }

    public String getSkinName() {
        return this.mPref.getString("skin-name", "");
    }

    public SkinPreference setSkinStrategy(int strategy) {
        this.mEditor.putInt("skin-strategy", strategy);
        return this;
    }

    public int getSkinStrategy() {
        return this.mPref.getInt("skin-strategy", 0);
    }

    public void commitEditor() {
        this.mEditor.apply();
    }
}
