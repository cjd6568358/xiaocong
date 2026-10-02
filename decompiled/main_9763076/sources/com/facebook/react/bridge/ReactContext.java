package com.facebook.react.bridge;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.LayoutInflater;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.queue.MessageQueueThread;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;
import com.facebook.react.common.LifecycleState;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactContext extends ContextWrapper {
    private final CopyOnWriteArraySet<ActivityEventListener> mActivityEventListeners;
    private CatalystInstance mCatalystInstance;
    private WeakReference<Activity> mCurrentActivity;
    private LayoutInflater mInflater;
    private MessageQueueThread mJSMessageQueueThread;
    private final CopyOnWriteArraySet<LifecycleEventListener> mLifecycleEventListeners;
    private LifecycleState mLifecycleState;
    private NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
    private MessageQueueThread mNativeModulesMessageQueueThread;
    private MessageQueueThread mUiMessageQueueThread;

    public ReactContext(Context base) {
        super(base);
        this.mLifecycleEventListeners = new CopyOnWriteArraySet<>();
        this.mActivityEventListeners = new CopyOnWriteArraySet<>();
        this.mLifecycleState = LifecycleState.BEFORE_CREATE;
    }

    public void initializeWithInstance(CatalystInstance catalystInstance) {
        if (catalystInstance == null) {
            throw new IllegalArgumentException("CatalystInstance cannot be null.");
        }
        if (this.mCatalystInstance != null) {
            throw new IllegalStateException("ReactContext has been already initialized");
        }
        this.mCatalystInstance = catalystInstance;
        ReactQueueConfiguration queueConfig = catalystInstance.getReactQueueConfiguration();
        this.mUiMessageQueueThread = queueConfig.getUIQueueThread();
        this.mNativeModulesMessageQueueThread = queueConfig.getNativeModulesQueueThread();
        this.mJSMessageQueueThread = queueConfig.getJSQueueThread();
    }

    public void setNativeModuleCallExceptionHandler(NativeModuleCallExceptionHandler nativeModuleCallExceptionHandler) {
        this.mNativeModuleCallExceptionHandler = nativeModuleCallExceptionHandler;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String name) {
        if (!"layout_inflater".equals(name)) {
            return getBaseContext().getSystemService(name);
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.mInflater;
    }

    public <T extends JavaScriptModule> T getJSModule(Class<T> cls) {
        if (this.mCatalystInstance == null) {
            throw new RuntimeException("Tried to access a JS module before the React instance was fully set up. Calls to ReactContext#getJSModule should only happen once initialize() has been called on your native module.");
        }
        return (T) this.mCatalystInstance.getJSModule(cls);
    }

    public <T extends JavaScriptModule> T getJSModule(ExecutorToken executorToken, Class<T> cls) {
        if (this.mCatalystInstance == null) {
            throw new RuntimeException("Tried to access a JS module before the React instance was fully set up. Calls to ReactContext#getJSModule should only happen once initialize() has been called on your native module.");
        }
        return (T) this.mCatalystInstance.getJSModule(executorToken, cls);
    }

    public <T extends NativeModule> T getNativeModule(Class<T> cls) {
        if (this.mCatalystInstance == null) {
            throw new RuntimeException("Trying to call native module before CatalystInstance has been set!");
        }
        return (T) this.mCatalystInstance.getNativeModule(cls);
    }

    public CatalystInstance getCatalystInstance() {
        return (CatalystInstance) Assertions.assertNotNull(this.mCatalystInstance);
    }

    public boolean hasActiveCatalystInstance() {
        return (this.mCatalystInstance == null || this.mCatalystInstance.isDestroyed()) ? false : true;
    }

    public void addLifecycleEventListener(final LifecycleEventListener listener) {
        this.mLifecycleEventListeners.add(listener);
        if (hasActiveCatalystInstance()) {
            switch (this.mLifecycleState) {
                case BEFORE_CREATE:
                case BEFORE_RESUME:
                    return;
                case RESUMED:
                    runOnUiQueueThread(new Runnable() { // from class: com.facebook.react.bridge.ReactContext.1
                        @Override // java.lang.Runnable
                        public void run() {
                            listener.onHostResume();
                        }
                    });
                    return;
                default:
                    throw new RuntimeException("Unhandled lifecycle state.");
            }
        }
    }

    public void removeLifecycleEventListener(LifecycleEventListener listener) {
        this.mLifecycleEventListeners.remove(listener);
    }

    public void onHostResume(Activity activity) {
        UiThreadUtil.assertOnUiThread();
        this.mLifecycleState = LifecycleState.RESUMED;
        this.mCurrentActivity = new WeakReference<>(activity);
        this.mLifecycleState = LifecycleState.RESUMED;
        for (LifecycleEventListener listener : this.mLifecycleEventListeners) {
            listener.onHostResume();
        }
    }

    public void onNewIntent(Activity activity, Intent intent) {
        UiThreadUtil.assertOnUiThread();
        this.mCurrentActivity = new WeakReference<>(activity);
        for (ActivityEventListener listener : this.mActivityEventListeners) {
            listener.onNewIntent(intent);
        }
    }

    public void onHostPause() {
        UiThreadUtil.assertOnUiThread();
        this.mLifecycleState = LifecycleState.BEFORE_RESUME;
        for (LifecycleEventListener listener : this.mLifecycleEventListeners) {
            listener.onHostPause();
        }
    }

    public void onHostDestroy() {
        UiThreadUtil.assertOnUiThread();
        this.mLifecycleState = LifecycleState.BEFORE_CREATE;
        for (LifecycleEventListener listener : this.mLifecycleEventListeners) {
            listener.onHostDestroy();
        }
        this.mCurrentActivity = null;
    }

    public void destroy() {
        UiThreadUtil.assertOnUiThread();
        if (this.mCatalystInstance != null) {
            this.mCatalystInstance.destroy();
        }
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        for (ActivityEventListener listener : this.mActivityEventListeners) {
            listener.onActivityResult(activity, requestCode, resultCode, data);
        }
    }

    public boolean isOnUiQueueThread() {
        return ((MessageQueueThread) Assertions.assertNotNull(this.mUiMessageQueueThread)).isOnThread();
    }

    public void runOnUiQueueThread(Runnable runnable) {
        ((MessageQueueThread) Assertions.assertNotNull(this.mUiMessageQueueThread)).runOnQueue(runnable);
    }

    public void assertOnNativeModulesQueueThread() {
        ((MessageQueueThread) Assertions.assertNotNull(this.mNativeModulesMessageQueueThread)).assertIsOnThread();
    }

    public void runOnNativeModulesQueueThread(Runnable runnable) {
        ((MessageQueueThread) Assertions.assertNotNull(this.mNativeModulesMessageQueueThread)).runOnQueue(runnable);
    }

    public void runOnJSQueueThread(Runnable runnable) {
        ((MessageQueueThread) Assertions.assertNotNull(this.mJSMessageQueueThread)).runOnQueue(runnable);
    }

    public void handleException(RuntimeException e) {
        if (this.mCatalystInstance != null && !this.mCatalystInstance.isDestroyed() && this.mNativeModuleCallExceptionHandler != null) {
            this.mNativeModuleCallExceptionHandler.handleException(e);
            return;
        }
        throw e;
    }

    public Activity getCurrentActivity() {
        if (this.mCurrentActivity == null) {
            return null;
        }
        return this.mCurrentActivity.get();
    }
}
