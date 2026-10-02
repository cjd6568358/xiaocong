package com.facebook.react.devsupport;

import android.content.SharedPreferences;
import com.facebook.react.modules.debug.DeveloperSettings;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DevInternalSettings implements SharedPreferences.OnSharedPreferenceChangeListener, DeveloperSettings {
    private final Listener mListener;
    private final SharedPreferences mPreferences;

    public interface Listener {
        void onInternalSettingsChanged();
    }

    @Override // com.facebook.react.modules.debug.DeveloperSettings
    public boolean isAnimationFpsDebugEnabled() {
        return this.mPreferences.getBoolean("animations_debug", false);
    }

    public String getDebugServerHost() {
        return this.mPreferences.getString("debug_http_host", null);
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (this.mListener != null) {
            if ("fps_debug".equals(key) || "reload_on_js_change".equals(key) || "js_dev_mode_debug".equals(key) || "js_minify_debug".equals(key)) {
                this.mListener.onInternalSettingsChanged();
            }
        }
    }

    @Override // com.facebook.react.modules.debug.DeveloperSettings
    public boolean isRemoteJSDebugEnabled() {
        return this.mPreferences.getBoolean("remote_js_debug", false);
    }
}
