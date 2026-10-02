package com.facebook.react.flat;

import android.view.ViewGroup;
import com.facebook.react.uimanager.RootViewManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class FlatRootViewManager extends RootViewManager {
    FlatRootViewManager() {
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public void removeAllViews(ViewGroup parent) {
        parent.removeAllViewsInLayout();
    }
}
