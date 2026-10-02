package com.xiaocong.smarthome.zxing;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class PreferencesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private CheckBoxPreference decode1D;
    private CheckBoxPreference decodeDataMatrix;
    private CheckBoxPreference decodeQR;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.zxing_preferences);
        PreferenceScreen preferences = getPreferenceScreen();
        preferences.getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        this.decode1D = (CheckBoxPreference) preferences.findPreference("preferences_decode_1D");
        this.decodeQR = (CheckBoxPreference) preferences.findPreference("preferences_decode_QR");
        this.decodeDataMatrix = (CheckBoxPreference) preferences.findPreference("preferences_decode_Data_Matrix");
        disableLastCheckedPref();
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        disableLastCheckedPref();
    }

    private void disableLastCheckedPref() {
        Collection<CheckBoxPreference> checked = new ArrayList<>(3);
        if (this.decode1D.isChecked()) {
            checked.add(this.decode1D);
        }
        if (this.decodeQR.isChecked()) {
            checked.add(this.decodeQR);
        }
        if (this.decodeDataMatrix.isChecked()) {
            checked.add(this.decodeDataMatrix);
        }
        boolean disable = checked.size() < 2;
        CheckBoxPreference[] checkBoxPreferences = {this.decode1D, this.decodeQR, this.decodeDataMatrix};
        for (CheckBoxPreference pref : checkBoxPreferences) {
            pref.setEnabled((disable && checked.contains(pref)) ? false : true);
        }
    }
}
