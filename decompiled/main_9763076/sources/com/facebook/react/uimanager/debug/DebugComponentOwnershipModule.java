package com.facebook.react.uimanager.debug;

import android.util.SparseArray;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DebugComponentOwnershipModule extends ReactContextBaseJavaModule {
    private int mNextRequestId;
    private RCTDebugComponentOwnership mRCTDebugComponentOwnership;
    private final SparseArray<OwnerHierarchyCallback> mRequestIdToCallback;

    public interface OwnerHierarchyCallback {
        void onOwnerHierarchyLoaded(int i, ReadableArray readableArray);
    }

    public interface RCTDebugComponentOwnership extends JavaScriptModule {
        void getOwnerHierarchy(int i, int i2);
    }

    public DebugComponentOwnershipModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mRequestIdToCallback = new SparseArray<>();
        this.mNextRequestId = 0;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        super.initialize();
        this.mRCTDebugComponentOwnership = (RCTDebugComponentOwnership) getReactApplicationContext().getJSModule(RCTDebugComponentOwnership.class);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        this.mRCTDebugComponentOwnership = null;
    }

    @ReactMethod
    public synchronized void receiveOwnershipHierarchy(int requestId, int tag, ReadableArray owners) {
        OwnerHierarchyCallback callback = this.mRequestIdToCallback.get(requestId);
        if (callback == null) {
            throw new JSApplicationCausedNativeException("Got receiveOwnershipHierarchy for invalid request id: " + requestId);
        }
        this.mRequestIdToCallback.delete(requestId);
        callback.onOwnerHierarchyLoaded(tag, owners);
    }

    public synchronized void loadComponentOwnerHierarchy(int tag, OwnerHierarchyCallback callback) {
        int requestId = this.mNextRequestId;
        this.mNextRequestId++;
        this.mRequestIdToCallback.put(requestId, callback);
        ((RCTDebugComponentOwnership) Assertions.assertNotNull(this.mRCTDebugComponentOwnership)).getOwnerHierarchy(requestId, tag);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "DebugComponentOwnershipModule";
    }
}
