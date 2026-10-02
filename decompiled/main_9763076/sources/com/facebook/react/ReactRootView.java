package com.facebook.react;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.JSTouchDispatcher;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactRootView extends SizeMonitoringFrameLayout implements RootView {
    private CustomGlobalLayoutListener mCustomGlobalLayoutListener;
    private boolean mIsAttachedToInstance;
    private String mJSModuleName;
    private final JSTouchDispatcher mJSTouchDispatcher;
    private Bundle mLaunchOptions;
    private ReactInstanceManager mReactInstanceManager;
    private ReactRootViewEventListener mRootViewEventListener;
    private int mRootViewTag;
    private boolean mWasMeasured;

    public interface ReactRootViewEventListener {
        void onAttachedToReactInstance(ReactRootView reactRootView);
    }

    public ReactRootView(Context context) {
        super(context);
        this.mWasMeasured = false;
        this.mIsAttachedToInstance = false;
        this.mJSTouchDispatcher = new JSTouchDispatcher(this);
    }

    public ReactRootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mWasMeasured = false;
        this.mIsAttachedToInstance = false;
        this.mJSTouchDispatcher = new JSTouchDispatcher(this);
    }

    public ReactRootView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mWasMeasured = false;
        this.mIsAttachedToInstance = false;
        this.mJSTouchDispatcher = new JSTouchDispatcher(this);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
        this.mWasMeasured = true;
        if (this.mReactInstanceManager != null && !this.mIsAttachedToInstance) {
            UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.ReactRootView.1
                @Override // java.lang.Runnable
                public void run() {
                    ReactRootView.this.attachToReactInstanceManager();
                }
            });
        }
    }

    @Override // com.facebook.react.uimanager.RootView
    public void onChildStartedNativeGesture(MotionEvent androidEvent) {
        if (this.mReactInstanceManager == null || !this.mIsAttachedToInstance || this.mReactInstanceManager.getCurrentReactContext() == null) {
            FLog.w("React", "Unable to dispatch touch to JS as the catalyst instance has not been attached");
            return;
        }
        ReactContext reactContext = this.mReactInstanceManager.getCurrentReactContext();
        EventDispatcher eventDispatcher = ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
        this.mJSTouchDispatcher.onChildStartedNativeGesture(androidEvent, eventDispatcher);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        dispatchJSTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        dispatchJSTouchEvent(ev);
        super.onTouchEvent(ev);
        return true;
    }

    private void dispatchJSTouchEvent(MotionEvent event) {
        if (this.mReactInstanceManager == null || !this.mIsAttachedToInstance || this.mReactInstanceManager.getCurrentReactContext() == null) {
            FLog.w("React", "Unable to dispatch touch to JS as the catalyst instance has not been attached");
            return;
        }
        ReactContext reactContext = this.mReactInstanceManager.getCurrentReactContext();
        EventDispatcher eventDispatcher = ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
        this.mJSTouchDispatcher.handleTouchEvent(event, eventDispatcher);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mIsAttachedToInstance) {
            getViewTreeObserver().addOnGlobalLayoutListener(getCustomGlobalLayoutListener());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mIsAttachedToInstance) {
            getViewTreeObserver().removeOnGlobalLayoutListener(getCustomGlobalLayoutListener());
        }
    }

    public void startReactApplication(ReactInstanceManager reactInstanceManager, String moduleName, Bundle launchOptions) {
        UiThreadUtil.assertOnUiThread();
        Assertions.assertCondition(this.mReactInstanceManager == null, "This root view has already been attached to a catalyst instance manager");
        this.mReactInstanceManager = reactInstanceManager;
        this.mJSModuleName = moduleName;
        this.mLaunchOptions = launchOptions;
        if (!this.mReactInstanceManager.hasStartedCreatingInitialContext()) {
            this.mReactInstanceManager.createReactContextInBackground();
        }
        if (this.mWasMeasured) {
            attachToReactInstanceManager();
        }
    }

    public void unmountReactApplication() {
        if (this.mReactInstanceManager != null && this.mIsAttachedToInstance) {
            this.mReactInstanceManager.detachRootView(this);
            this.mIsAttachedToInstance = false;
        }
    }

    public void onAttachedToReactInstance() {
        if (this.mRootViewEventListener != null) {
            this.mRootViewEventListener.onAttachedToReactInstance(this);
        }
    }

    public void setEventListener(ReactRootViewEventListener eventListener) {
        this.mRootViewEventListener = eventListener;
    }

    String getJSModuleName() {
        return (String) Assertions.assertNotNull(this.mJSModuleName);
    }

    Bundle getLaunchOptions() {
        return this.mLaunchOptions;
    }

    private CustomGlobalLayoutListener getCustomGlobalLayoutListener() {
        if (this.mCustomGlobalLayoutListener == null) {
            this.mCustomGlobalLayoutListener = new CustomGlobalLayoutListener();
        }
        return this.mCustomGlobalLayoutListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attachToReactInstanceManager() {
        if (!this.mIsAttachedToInstance) {
            this.mIsAttachedToInstance = true;
            ((ReactInstanceManager) Assertions.assertNotNull(this.mReactInstanceManager)).attachMeasuredRootView(this);
            getViewTreeObserver().addOnGlobalLayoutListener(getCustomGlobalLayoutListener());
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        Assertions.assertCondition(!this.mIsAttachedToInstance, "The application this ReactRootView was rendering was not unmounted before the ReactRootView was garbage collected. This usually means that your application is leaking large amounts of memory. To solve this, make sure to call ReactRootView#unmountReactApplication in the onDestroy() of your hosting Activity or in the onDestroyView() of your hosting Fragment.");
    }

    public int getRootViewTag() {
        return this.mRootViewTag;
    }

    public void setRootViewTag(int rootViewTag) {
        this.mRootViewTag = rootViewTag;
    }

    private class CustomGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private int mKeyboardHeight = 0;
        private int mDeviceRotation = 0;
        private final Rect mVisibleViewArea = new Rect();
        private final int mMinKeyboardHeightDetected = (int) PixelUtil.toPixelFromDIP(60.0f);

        CustomGlobalLayoutListener() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (ReactRootView.this.mReactInstanceManager != null && ReactRootView.this.mIsAttachedToInstance && ReactRootView.this.mReactInstanceManager.getCurrentReactContext() != null) {
                checkForKeyboardEvents();
                checkForDeviceOrientationChanges();
            }
        }

        private void checkForKeyboardEvents() {
            ReactRootView.this.getRootView().getWindowVisibleDisplayFrame(this.mVisibleViewArea);
            int heightDiff = DisplayMetricsHolder.getWindowDisplayMetrics().heightPixels - this.mVisibleViewArea.bottom;
            if (this.mKeyboardHeight != heightDiff && heightDiff > this.mMinKeyboardHeightDetected) {
                this.mKeyboardHeight = heightDiff;
                WritableMap params = Arguments.createMap();
                WritableMap coordinates = Arguments.createMap();
                coordinates.putDouble("screenY", PixelUtil.toDIPFromPixel(this.mVisibleViewArea.bottom));
                coordinates.putDouble("screenX", PixelUtil.toDIPFromPixel(this.mVisibleViewArea.left));
                coordinates.putDouble(IMediaFormat.KEY_WIDTH, PixelUtil.toDIPFromPixel(this.mVisibleViewArea.width()));
                coordinates.putDouble(IMediaFormat.KEY_HEIGHT, PixelUtil.toDIPFromPixel(this.mKeyboardHeight));
                params.putMap("endCoordinates", coordinates);
                sendEvent("keyboardDidShow", params);
                return;
            }
            if (this.mKeyboardHeight != 0 && heightDiff <= this.mMinKeyboardHeightDetected) {
                this.mKeyboardHeight = 0;
                sendEvent("keyboardDidHide", null);
            }
        }

        private void checkForDeviceOrientationChanges() {
            int rotation = ((WindowManager) ReactRootView.this.getContext().getSystemService("window")).getDefaultDisplay().getRotation();
            if (this.mDeviceRotation != rotation) {
                this.mDeviceRotation = rotation;
                DisplayMetricsHolder.initDisplayMetrics(ReactRootView.this.getContext());
                emitUpdateDimensionsEvent();
                emitOrientationChanged(rotation);
            }
        }

        private void emitOrientationChanged(int newRotation) {
            String name;
            double rotationDegrees;
            boolean isLandscape = false;
            switch (newRotation) {
                case 0:
                    name = "portrait-primary";
                    rotationDegrees = 0.0d;
                    break;
                case 1:
                    name = "landscape-primary";
                    rotationDegrees = -90.0d;
                    isLandscape = true;
                    break;
                case 2:
                    name = "portrait-secondary";
                    rotationDegrees = 180.0d;
                    break;
                case 3:
                    name = "landscape-secondary";
                    rotationDegrees = 90.0d;
                    isLandscape = true;
                    break;
                default:
                    return;
            }
            WritableMap map = Arguments.createMap();
            map.putString(RNMessageModule.NAME, name);
            map.putDouble("rotationDegrees", rotationDegrees);
            map.putBoolean("isLandscape", isLandscape);
            sendEvent("namedOrientationDidChange", map);
        }

        private void emitUpdateDimensionsEvent() {
            DisplayMetrics windowDisplayMetrics = DisplayMetricsHolder.getWindowDisplayMetrics();
            DisplayMetrics screenDisplayMetrics = DisplayMetricsHolder.getScreenDisplayMetrics();
            WritableMap windowDisplayMetricsMap = Arguments.createMap();
            windowDisplayMetricsMap.putInt(IMediaFormat.KEY_WIDTH, windowDisplayMetrics.widthPixels);
            windowDisplayMetricsMap.putInt(IMediaFormat.KEY_HEIGHT, windowDisplayMetrics.heightPixels);
            windowDisplayMetricsMap.putDouble("scale", windowDisplayMetrics.density);
            windowDisplayMetricsMap.putDouble("fontScale", windowDisplayMetrics.scaledDensity);
            windowDisplayMetricsMap.putDouble("densityDpi", windowDisplayMetrics.densityDpi);
            WritableMap screenDisplayMetricsMap = Arguments.createMap();
            screenDisplayMetricsMap.putInt(IMediaFormat.KEY_WIDTH, screenDisplayMetrics.widthPixels);
            screenDisplayMetricsMap.putInt(IMediaFormat.KEY_HEIGHT, screenDisplayMetrics.heightPixels);
            screenDisplayMetricsMap.putDouble("scale", screenDisplayMetrics.density);
            screenDisplayMetricsMap.putDouble("fontScale", screenDisplayMetrics.scaledDensity);
            screenDisplayMetricsMap.putDouble("densityDpi", screenDisplayMetrics.densityDpi);
            WritableMap dimensionsMap = Arguments.createMap();
            dimensionsMap.putMap("windowPhysicalPixels", windowDisplayMetricsMap);
            dimensionsMap.putMap("screenPhysicalPixels", screenDisplayMetricsMap);
            sendEvent("didUpdateDimensions", dimensionsMap);
        }

        private void sendEvent(String eventName, WritableMap params) {
            if (ReactRootView.this.mReactInstanceManager != null) {
                ((DeviceEventManagerModule.RCTDeviceEventEmitter) ReactRootView.this.mReactInstanceManager.getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(eventName, params);
            }
        }
    }
}
