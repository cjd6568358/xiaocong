package com.facebook.react.views.drawer;

import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.events.NativeGestureUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ReactDrawerLayout extends DrawerLayout {
    private int mDrawerPosition;
    private int mDrawerWidth;

    public ReactDrawerLayout(ReactContext reactContext) {
        super(reactContext);
        this.mDrawerPosition = 8388611;
        this.mDrawerWidth = -1;
    }

    @Override // android.support.v4.widget.DrawerLayout, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!super.onInterceptTouchEvent(ev)) {
            return false;
        }
        NativeGestureUtil.notifyNativeGestureStarted(this, ev);
        return true;
    }

    void openDrawer() {
        openDrawer(this.mDrawerPosition);
    }

    void closeDrawer() {
        closeDrawer(this.mDrawerPosition);
    }

    void setDrawerPosition(int drawerPosition) {
        this.mDrawerPosition = drawerPosition;
        setDrawerProperties();
    }

    void setDrawerWidth(int drawerWidthInPx) {
        this.mDrawerWidth = drawerWidthInPx;
        setDrawerProperties();
    }

    void setDrawerProperties() {
        if (getChildCount() == 2) {
            View drawerView = getChildAt(1);
            DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) drawerView.getLayoutParams();
            layoutParams.gravity = this.mDrawerPosition;
            layoutParams.width = this.mDrawerWidth;
            drawerView.setLayoutParams(layoutParams);
            drawerView.setClickable(true);
        }
    }
}
