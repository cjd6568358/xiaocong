package com.ixiaocong.smarthome.phone.android.common.manager;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftInputManager {
    public static void hidenSoftInputFromWindow(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }
}
