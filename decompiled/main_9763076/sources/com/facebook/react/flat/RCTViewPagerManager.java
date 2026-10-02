package com.facebook.react.flat;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.views.viewpager.ReactViewPager;
import com.facebook.react.views.viewpager.ReactViewPagerManager;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RCTViewPagerManager extends ReactViewPagerManager {
    @Override // com.facebook.react.uimanager.ViewGroupManager
    public /* bridge */ /* synthetic */ void addViews(ViewGroup viewGroup, List list) {
        addViews((ReactViewPager) viewGroup, (List<View>) list);
    }

    public void addViews(ReactViewPager parent, List<View> views) {
        parent.setViews(views);
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public void removeAllViews(ReactViewPager parent) {
        parent.removeAllViewsFromAdapter();
    }
}
