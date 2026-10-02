package com.xiaocong.smarthome.httplib.swipeBack;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SwipeBackPreferenceActivity extends PreferenceActivity {
    private SwipeBackActivityHelper mHelper;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHelper = new SwipeBackActivityHelper(this);
        this.mHelper.onActivityCreate();
    }

    @Override // android.app.Activity
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mHelper.onPostCreate();
    }

    @Override // android.app.Activity
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && this.mHelper != null) {
            return this.mHelper.findViewById(id);
        }
        return v;
    }
}
