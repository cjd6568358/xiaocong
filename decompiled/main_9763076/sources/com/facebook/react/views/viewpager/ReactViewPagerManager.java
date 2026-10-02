package com.facebook.react.views.viewpager;

import android.view.View;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactViewPagerManager extends ViewGroupManager<ReactViewPager> {
    public static final int COMMAND_SET_PAGE = 1;
    public static final int COMMAND_SET_PAGE_WITHOUT_ANIMATION = 2;
    protected static final String REACT_CLASS = "AndroidViewPager";

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public ReactViewPager createViewInstance(ThemedReactContext reactContext) {
        return new ReactViewPager(reactContext);
    }

    @ReactProp(defaultBoolean = true, name = "scrollEnabled")
    public void setScrollEnabled(ReactViewPager viewPager, boolean value) {
        viewPager.setScrollEnabled(value);
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of("topPageScroll", MapBuilder.of("registrationName", "onPageScroll"), "topPageScrollStateChanged", MapBuilder.of("registrationName", "onPageScrollStateChanged"), "topPageSelected", MapBuilder.of("registrationName", "onPageSelected"));
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("setPage", 1, "setPageWithoutAnimation", 2);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void receiveCommand(ReactViewPager viewPager, int commandType, ReadableArray args) {
        Assertions.assertNotNull(viewPager);
        Assertions.assertNotNull(args);
        switch (commandType) {
            case 1:
                viewPager.setCurrentItemFromJs(args.getInt(0), true);
                return;
            case 2:
                viewPager.setCurrentItemFromJs(args.getInt(0), false);
                return;
            default:
                throw new IllegalArgumentException(String.format("Unsupported command %d received by %s.", Integer.valueOf(commandType), getClass().getSimpleName()));
        }
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public void addView(ReactViewPager parent, View child, int index) {
        parent.addViewToAdapter(child, index);
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public int getChildCount(ReactViewPager parent) {
        return parent.getViewCountInAdapter();
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public View getChildAt(ReactViewPager parent, int index) {
        return parent.getViewFromAdapter(index);
    }

    @Override // com.facebook.react.uimanager.ViewGroupManager
    public void removeViewAt(ReactViewPager parent, int index) {
        parent.removeViewFromAdapter(index);
    }

    @ReactProp(defaultFloat = 0.0f, name = "pageMargin")
    public void setPageMargin(ReactViewPager pager, float margin) {
        pager.setPageMargin((int) PixelUtil.toPixelFromDIP(margin));
    }
}
