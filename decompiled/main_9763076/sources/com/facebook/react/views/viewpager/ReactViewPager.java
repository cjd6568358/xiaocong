package com.facebook.react.views.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactViewPager extends ViewPager {
    private final EventDispatcher mEventDispatcher;
    private boolean mIsCurrentItemFromJs;
    private boolean mScrollEnabled;

    private class Adapter extends PagerAdapter {
        private boolean mIsViewPagerInIntentionallyInconsistentState;
        private final List<View> mViews;

        private Adapter() {
            this.mViews = new ArrayList();
            this.mIsViewPagerInIntentionallyInconsistentState = false;
        }

        void addView(View child, int index) {
            this.mViews.add(index, child);
            notifyDataSetChanged();
            ReactViewPager.this.setOffscreenPageLimit(this.mViews.size());
        }

        void removeViewAt(int index) {
            this.mViews.remove(index);
            notifyDataSetChanged();
            ReactViewPager.this.setOffscreenPageLimit(this.mViews.size());
        }

        void setViews(List<View> views) {
            this.mViews.clear();
            this.mViews.addAll(views);
            notifyDataSetChanged();
            this.mIsViewPagerInIntentionallyInconsistentState = false;
        }

        void removeAllViewsFromAdapter(ViewPager pager) {
            this.mViews.clear();
            pager.removeAllViews();
            this.mIsViewPagerInIntentionallyInconsistentState = true;
        }

        View getViewAt(int index) {
            return this.mViews.get(index);
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return this.mViews.size();
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getItemPosition(Object object) {
            if (this.mIsViewPagerInIntentionallyInconsistentState || !this.mViews.contains(object)) {
                return -2;
            }
            return this.mViews.indexOf(object);
        }

        @Override // android.support.v4.view.PagerAdapter
        public Object instantiateItem(ViewGroup container, int position) {
            View view = this.mViews.get(position);
            container.addView(view, 0, ReactViewPager.this.generateDefaultLayoutParams());
            return view;
        }

        @Override // android.support.v4.view.PagerAdapter
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override // android.support.v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        private PageChangeListener() {
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageScrollEvent(ReactViewPager.this.getId(), position, positionOffset));
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            if (!ReactViewPager.this.mIsCurrentItemFromJs) {
                ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageSelectedEvent(ReactViewPager.this.getId(), position));
            }
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
            String pageScrollState;
            switch (state) {
                case 0:
                    pageScrollState = "idle";
                    break;
                case 1:
                    pageScrollState = "dragging";
                    break;
                case 2:
                    pageScrollState = "settling";
                    break;
                default:
                    throw new IllegalStateException("Unsupported pageScrollState");
            }
            ReactViewPager.this.mEventDispatcher.dispatchEvent(new PageScrollStateChangedEvent(ReactViewPager.this.getId(), pageScrollState));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ReactViewPager(ReactContext reactContext) {
        super(reactContext);
        this.mScrollEnabled = true;
        this.mEventDispatcher = ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
        this.mIsCurrentItemFromJs = false;
        setOnPageChangeListener(new PageChangeListener());
        setAdapter(new Adapter());
    }

    @Override // android.support.v4.view.ViewPager
    public Adapter getAdapter() {
        return (Adapter) super.getAdapter();
    }

    @Override // android.support.v4.view.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.mScrollEnabled || !super.onInterceptTouchEvent(ev)) {
            return false;
        }
        NativeGestureUtil.notifyNativeGestureStarted(this, ev);
        return true;
    }

    @Override // android.support.v4.view.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mScrollEnabled) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    public void setCurrentItemFromJs(int item, boolean animated) {
        this.mIsCurrentItemFromJs = true;
        setCurrentItem(item, animated);
        this.mIsCurrentItemFromJs = false;
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        this.mScrollEnabled = scrollEnabled;
    }

    void addViewToAdapter(View child, int index) {
        getAdapter().addView(child, index);
    }

    void removeViewFromAdapter(int index) {
        getAdapter().removeViewAt(index);
    }

    int getViewCountInAdapter() {
        return getAdapter().getCount();
    }

    View getViewFromAdapter(int index) {
        return getAdapter().getViewAt(index);
    }

    public void setViews(List<View> views) {
        getAdapter().setViews(views);
    }

    public void removeAllViewsFromAdapter() {
        getAdapter().removeAllViewsFromAdapter(this);
    }
}
