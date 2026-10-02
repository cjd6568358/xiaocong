package com.scwang.smartrefresh.layout.util;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DesignUtil {
    public static void checkCoordinatorLayout(View content, RefreshKernel kernel, CoordinatorLayoutListener listener) {
        try {
            if (content instanceof CoordinatorLayout) {
                kernel.getRefreshLayout().setEnableNestedScroll(false);
                wrapperCoordinatorLayout((ViewGroup) content, kernel.getRefreshLayout(), listener);
            }
        } catch (Throwable th) {
        }
    }

    private static void wrapperCoordinatorLayout(ViewGroup layout, final RefreshLayout refreshLayout, final CoordinatorLayoutListener listener) {
        for (int i = layout.getChildCount() - 1; i >= 0; i--) {
            View view = layout.getChildAt(i);
            if (view instanceof AppBarLayout) {
                ((AppBarLayout) view).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.scwang.smartrefresh.layout.util.DesignUtil.1
                    @Override // android.support.design.widget.AppBarLayout.OnOffsetChangedListener
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        listener.update(verticalOffset >= 0, refreshLayout.isEnableLoadMore() && appBarLayout.getTotalScrollRange() + verticalOffset <= 0);
                    }
                });
            }
        }
    }
}
