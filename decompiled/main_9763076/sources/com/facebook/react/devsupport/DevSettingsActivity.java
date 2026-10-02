package com.facebook.react.devsupport;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.facebook.react.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DevSettingsActivity extends PreferenceActivity {
    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.catalyst_settings_title);
        addPreferencesFromResource(R.xml.preferences);
    }
}
