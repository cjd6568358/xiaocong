package com.facebook.react.uimanager;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import com.facebook.react.animation.Animation;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.NativeModuleLogger;
import com.facebook.react.bridge.OnBatchCompleteListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UIManagerModule extends ReactContextBaseJavaModule implements LifecycleEventListener, NativeModuleLogger, OnBatchCompleteListener {
    private static final boolean DEBUG = false;
    protected static final String NAME = "UIManager";
    private static final int ROOT_VIEW_TAG_INCREMENT = 10;
    private int mBatchId;
    private final EventDispatcher mEventDispatcher;
    private final MemoryTrimCallback mMemoryTrimCallback;
    private final Map<String, Object> mModuleConstants;
    private int mNextRootViewTag;
    private final UIImplementation mUIImplementation;

    public UIManagerModule(ReactApplicationContext reactContext, List<ViewManager> viewManagerList, UIImplementationProvider uiImplementationProvider, boolean lazyViewManagersEnabled) {
        super(reactContext);
        this.mMemoryTrimCallback = new MemoryTrimCallback();
        this.mNextRootViewTag = 1;
        this.mBatchId = 0;
        DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(reactContext);
        this.mEventDispatcher = new EventDispatcher(reactContext);
        this.mModuleConstants = createConstants(viewManagerList, lazyViewManagersEnabled);
        this.mUIImplementation = uiImplementationProvider.createUIImplementation(reactContext, viewManagerList, this.mEventDispatcher);
        reactContext.addLifecycleEventListener(this);
    }

    public UIImplementation getUIImplementation() {
        return this.mUIImplementation;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        return this.mModuleConstants;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        getReactApplicationContext().registerComponentCallbacks(this.mMemoryTrimCallback);
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        this.mUIImplementation.onHostResume();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        this.mUIImplementation.onHostPause();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
        this.mUIImplementation.onHostDestroy();
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        this.mEventDispatcher.onCatalystInstanceDestroyed();
        getReactApplicationContext().unregisterComponentCallbacks(this.mMemoryTrimCallback);
        YogaNodePool.get().clear();
    }

    private static Map<String, Object> createConstants(List<ViewManager> viewManagerList, boolean lazyViewManagersEnabled) {
        ReactMarker.logMarker("CREATE_UI_MANAGER_MODULE_CONSTANTS_START");
        Systrace.beginSection(0L, "CreateUIManagerConstants");
        try {
            return UIManagerModuleConstantsHelper.createConstants(viewManagerList, lazyViewManagersEnabled);
        } finally {
            Systrace.endSection(0L);
            ReactMarker.logMarker("CREATE_UI_MANAGER_MODULE_CONSTANTS_END");
        }
    }

    public Map<String, Double> getPerformanceCounters() {
        Map<String, Double> perfMap = new HashMap<>();
        perfMap.put("LayoutCount", Double.valueOf(this.mUIImplementation.getLayoutCount()));
        perfMap.put("LayoutTimer", Double.valueOf(this.mUIImplementation.getLayoutTimer()));
        return perfMap;
    }

    public int addMeasuredRootView(SizeMonitoringFrameLayout rootView) {
        int width;
        int height;
        final int tag = this.mNextRootViewTag;
        this.mNextRootViewTag += 10;
        if (rootView.getLayoutParams() != null && rootView.getLayoutParams().width > 0 && rootView.getLayoutParams().height > 0) {
            width = rootView.getLayoutParams().width;
            height = rootView.getLayoutParams().height;
        } else {
            width = rootView.getWidth();
            height = rootView.getHeight();
        }
        ThemedReactContext themedRootContext = new ThemedReactContext(getReactApplicationContext(), rootView.getContext());
        this.mUIImplementation.registerRootView(rootView, tag, width, height, themedRootContext);
        rootView.setOnSizeChangedListener(new SizeMonitoringFrameLayout.OnSizeChangedListener() { // from class: com.facebook.react.uimanager.UIManagerModule.1
            @Override // com.facebook.react.uimanager.SizeMonitoringFrameLayout.OnSizeChangedListener
            public void onSizeChanged(final int width2, final int height2, int oldW, int oldH) {
                UIManagerModule.this.getReactApplicationContext().runOnNativeModulesQueueThread(new Runnable() { // from class: com.facebook.react.uimanager.UIManagerModule.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        UIManagerModule.this.updateNodeSize(tag, width2, height2);
                    }
                });
            }
        });
        return tag;
    }

    @ReactMethod
    public void removeRootView(int rootViewTag) {
        this.mUIImplementation.removeRootView(rootViewTag);
    }

    public void updateNodeSize(int nodeViewTag, int newWidth, int newHeight) {
        getReactApplicationContext().assertOnNativeModulesQueueThread();
        this.mUIImplementation.updateNodeSize(nodeViewTag, newWidth, newHeight);
    }

    @ReactMethod
    public void createView(int tag, String className, int rootViewTag, ReadableMap props) {
        this.mUIImplementation.createView(tag, className, rootViewTag, props);
    }

    @ReactMethod
    public void updateView(int tag, String className, ReadableMap props) {
        this.mUIImplementation.updateView(tag, className, props);
    }

    @ReactMethod
    public void manageChildren(int viewTag, ReadableArray moveFrom, ReadableArray moveTo, ReadableArray addChildTags, ReadableArray addAtIndices, ReadableArray removeFrom) {
        this.mUIImplementation.manageChildren(viewTag, moveFrom, moveTo, addChildTags, addAtIndices, removeFrom);
    }

    @ReactMethod
    public void setChildren(int viewTag, ReadableArray childrenTags) {
        this.mUIImplementation.setChildren(viewTag, childrenTags);
    }

    @ReactMethod
    public void replaceExistingNonRootView(int oldTag, int newTag) {
        this.mUIImplementation.replaceExistingNonRootView(oldTag, newTag);
    }

    @ReactMethod
    public void removeSubviewsFromContainerWithID(int containerTag) {
        this.mUIImplementation.removeSubviewsFromContainerWithID(containerTag);
    }

    @ReactMethod
    public void measure(int reactTag, Callback callback) {
        this.mUIImplementation.measure(reactTag, callback);
    }

    @ReactMethod
    public void measureInWindow(int reactTag, Callback callback) {
        this.mUIImplementation.measureInWindow(reactTag, callback);
    }

    @ReactMethod
    public void measureLayout(int tag, int ancestorTag, Callback errorCallback, Callback successCallback) {
        this.mUIImplementation.measureLayout(tag, ancestorTag, errorCallback, successCallback);
    }

    @ReactMethod
    public void measureLayoutRelativeToParent(int tag, Callback errorCallback, Callback successCallback) {
        this.mUIImplementation.measureLayoutRelativeToParent(tag, errorCallback, successCallback);
    }

    @ReactMethod
    public void findSubviewIn(int reactTag, ReadableArray point, Callback callback) {
        this.mUIImplementation.findSubviewIn(reactTag, Math.round(PixelUtil.toPixelFromDIP(point.getDouble(0))), Math.round(PixelUtil.toPixelFromDIP(point.getDouble(1))), callback);
    }

    public void registerAnimation(Animation animation) {
        this.mUIImplementation.registerAnimation(animation);
    }

    public void addAnimation(int reactTag, int animationID, Callback onSuccess) {
        this.mUIImplementation.addAnimation(reactTag, animationID, onSuccess);
    }

    public void removeAnimation(int reactTag, int animationID) {
        this.mUIImplementation.removeAnimation(reactTag, animationID);
    }

    @ReactMethod
    public void setJSResponder(int reactTag, boolean blockNativeResponder) {
        this.mUIImplementation.setJSResponder(reactTag, blockNativeResponder);
    }

    @ReactMethod
    public void clearJSResponder() {
        this.mUIImplementation.clearJSResponder();
    }

    @ReactMethod
    public void dispatchViewManagerCommand(int reactTag, int commandId, ReadableArray commandArgs) {
        this.mUIImplementation.dispatchViewManagerCommand(reactTag, commandId, commandArgs);
    }

    @ReactMethod
    public void showPopupMenu(int reactTag, ReadableArray items, Callback error, Callback success) {
        this.mUIImplementation.showPopupMenu(reactTag, items, error, success);
    }

    @ReactMethod
    public void setLayoutAnimationEnabledExperimental(boolean enabled) {
        this.mUIImplementation.setLayoutAnimationEnabledExperimental(enabled);
    }

    @ReactMethod
    public void configureNextLayoutAnimation(ReadableMap config, Callback success, Callback error) {
        this.mUIImplementation.configureNextLayoutAnimation(config, success, error);
    }

    @Override // com.facebook.react.bridge.OnBatchCompleteListener
    public void onBatchComplete() {
        int batchId = this.mBatchId;
        this.mBatchId++;
        SystraceMessage.beginSection(0L, "onBatchCompleteUI").arg("BatchId", batchId).flush();
        try {
            this.mUIImplementation.dispatchViewUpdates(batchId);
        } finally {
            Systrace.endSection(0L);
        }
    }

    public void setViewHierarchyUpdateDebugListener(NotThreadSafeViewHierarchyUpdateDebugListener listener) {
        this.mUIImplementation.setViewHierarchyUpdateDebugListener(listener);
    }

    public EventDispatcher getEventDispatcher() {
        return this.mEventDispatcher;
    }

    @ReactMethod
    public void sendAccessibilityEvent(int tag, int eventType) {
        this.mUIImplementation.sendAccessibilityEvent(tag, eventType);
    }

    public void addUIBlock(UIBlock block) {
        this.mUIImplementation.addUIBlock(block);
    }

    public int resolveRootTagFromReactTag(int reactTag) {
        return this.mUIImplementation.resolveRootTagFromReactTag(reactTag);
    }

    @Override // com.facebook.react.bridge.NativeModuleLogger
    public void startConstantsMapConversion() {
        ReactMarker.logMarker("UI_MANAGER_MODULE_CONSTANTS_CONVERT_START");
    }

    @Override // com.facebook.react.bridge.NativeModuleLogger
    public void endConstantsMapConversion() {
        ReactMarker.logMarker("UI_MANAGER_MODULE_CONSTANTS_CONVERT_END");
    }

    private class MemoryTrimCallback implements ComponentCallbacks2 {
        private MemoryTrimCallback() {
        }

        @Override // android.content.ComponentCallbacks2
        public void onTrimMemory(int level) {
            if (level >= 60) {
                YogaNodePool.get().clear();
            }
        }

        @Override // android.content.ComponentCallbacks
        public void onConfigurationChanged(Configuration newConfig) {
        }

        @Override // android.content.ComponentCallbacks
        public void onLowMemory() {
        }
    }
}
