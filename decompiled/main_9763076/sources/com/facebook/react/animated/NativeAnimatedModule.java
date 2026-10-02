package com.facebook.react.animated;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.OnBatchCompleteListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.GuardedChoreographerFrameCallback;
import com.facebook.react.uimanager.ReactChoreographer;
import com.facebook.react.uimanager.UIManagerModule;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NativeAnimatedModule extends ReactContextBaseJavaModule implements LifecycleEventListener, OnBatchCompleteListener {
    private GuardedChoreographerFrameCallback mAnimatedFrameCallback;
    private ArrayList<UIThreadOperation> mOperations;
    private final Object mOperationsCopyLock;
    private ReactChoreographer mReactChoreographer;
    private volatile ArrayList<UIThreadOperation> mReadyOperations;

    private interface UIThreadOperation {
        void execute(NativeAnimatedNodesManager nativeAnimatedNodesManager);
    }

    public NativeAnimatedModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mOperationsCopyLock = new Object();
        this.mOperations = new ArrayList<>();
        this.mReadyOperations = null;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        this.mReactChoreographer = ReactChoreographer.getInstance();
        ReactApplicationContext reactCtx = getReactApplicationContext();
        UIManagerModule uiManager = (UIManagerModule) reactCtx.getNativeModule(UIManagerModule.class);
        final NativeAnimatedNodesManager nodesManager = new NativeAnimatedNodesManager(uiManager);
        this.mAnimatedFrameCallback = new GuardedChoreographerFrameCallback(reactCtx) { // from class: com.facebook.react.animated.NativeAnimatedModule.1
            @Override // com.facebook.react.uimanager.GuardedChoreographerFrameCallback
            protected void doFrameGuarded(long frameTimeNanos) {
                ArrayList<UIThreadOperation> operations;
                synchronized (NativeAnimatedModule.this.mOperationsCopyLock) {
                    operations = NativeAnimatedModule.this.mReadyOperations;
                    NativeAnimatedModule.this.mReadyOperations = null;
                }
                if (operations != null) {
                    int size = operations.size();
                    for (int i = 0; i < size; i++) {
                        operations.get(i).execute(nodesManager);
                    }
                }
                if (nodesManager.hasActiveAnimations()) {
                    nodesManager.runUpdates(frameTimeNanos);
                }
                ((ReactChoreographer) Assertions.assertNotNull(NativeAnimatedModule.this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, NativeAnimatedModule.this.mAnimatedFrameCallback);
            }
        };
        reactCtx.addLifecycleEventListener(this);
    }

    @Override // com.facebook.react.bridge.OnBatchCompleteListener
    public void onBatchComplete() {
        ArrayList<UIThreadOperation> operations = this.mOperations.isEmpty() ? null : this.mOperations;
        if (operations != null) {
            this.mOperations = new ArrayList<>();
            synchronized (this.mOperationsCopyLock) {
                if (this.mReadyOperations == null) {
                    this.mReadyOperations = operations;
                } else {
                    this.mReadyOperations.addAll(operations);
                }
            }
        }
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        enqueueFrameCallback();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        clearFrameCallback();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "NativeAnimatedModule";
    }

    private void clearFrameCallback() {
        ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).removeFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mAnimatedFrameCallback);
    }

    private void enqueueFrameCallback() {
        ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mAnimatedFrameCallback);
    }

    @ReactMethod
    public void createAnimatedNode(final int tag, final ReadableMap config) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.2
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.createAnimatedNode(tag, config);
            }
        });
    }

    @ReactMethod
    public void startListeningToAnimatedNodeValue(final int tag) {
        final AnimatedNodeValueListener listener = new AnimatedNodeValueListener() { // from class: com.facebook.react.animated.NativeAnimatedModule.3
            @Override // com.facebook.react.animated.AnimatedNodeValueListener
            public void onValueUpdate(double value) {
                WritableMap onAnimatedValueData = Arguments.createMap();
                onAnimatedValueData.putInt("tag", tag);
                onAnimatedValueData.putDouble("value", value);
                ((DeviceEventManagerModule.RCTDeviceEventEmitter) NativeAnimatedModule.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("onAnimatedValueUpdate", onAnimatedValueData);
            }
        };
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.4
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.startListeningToAnimatedNodeValue(tag, listener);
            }
        });
    }

    @ReactMethod
    public void stopListeningToAnimatedNodeValue(final int tag) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.5
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.stopListeningToAnimatedNodeValue(tag);
            }
        });
    }

    @ReactMethod
    public void dropAnimatedNode(final int tag) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.6
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.dropAnimatedNode(tag);
            }
        });
    }

    @ReactMethod
    public void setAnimatedNodeValue(final int tag, final double value) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.7
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.setAnimatedNodeValue(tag, value);
            }
        });
    }

    @ReactMethod
    public void setAnimatedNodeOffset(final int tag, final double value) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.8
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.setAnimatedNodeOffset(tag, value);
            }
        });
    }

    @ReactMethod
    public void flattenAnimatedNodeOffset(final int tag) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.9
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.flattenAnimatedNodeOffset(tag);
            }
        });
    }

    @ReactMethod
    public void extractAnimatedNodeOffset(final int tag) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.10
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.extractAnimatedNodeOffset(tag);
            }
        });
    }

    @ReactMethod
    public void startAnimatingNode(final int animationId, final int animatedNodeTag, final ReadableMap animationConfig, final Callback endCallback) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.11
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.startAnimatingNode(animationId, animatedNodeTag, animationConfig, endCallback);
            }
        });
    }

    @ReactMethod
    public void stopAnimation(final int animationId) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.12
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.stopAnimation(animationId);
            }
        });
    }

    @ReactMethod
    public void connectAnimatedNodes(final int parentNodeTag, final int childNodeTag) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.13
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.connectAnimatedNodes(parentNodeTag, childNodeTag);
            }
        });
    }

    @ReactMethod
    public void disconnectAnimatedNodes(final int parentNodeTag, final int childNodeTag) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.14
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.disconnectAnimatedNodes(parentNodeTag, childNodeTag);
            }
        });
    }

    @ReactMethod
    public void connectAnimatedNodeToView(final int animatedNodeTag, final int viewTag) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.15
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.connectAnimatedNodeToView(animatedNodeTag, viewTag);
            }
        });
    }

    @ReactMethod
    public void disconnectAnimatedNodeFromView(final int animatedNodeTag, final int viewTag) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.16
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.disconnectAnimatedNodeFromView(animatedNodeTag, viewTag);
            }
        });
    }

    @ReactMethod
    public void addAnimatedEventToView(final int viewTag, final String eventName, final ReadableMap eventMapping) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.17
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.addAnimatedEventToView(viewTag, eventName, eventMapping);
            }
        });
    }

    @ReactMethod
    public void removeAnimatedEventFromView(final int viewTag, final String eventName) {
        this.mOperations.add(new UIThreadOperation() { // from class: com.facebook.react.animated.NativeAnimatedModule.18
            @Override // com.facebook.react.animated.NativeAnimatedModule.UIThreadOperation
            public void execute(NativeAnimatedNodesManager animatedNodesManager) {
                animatedNodesManager.removeAnimatedEventFromView(viewTag, eventName);
            }
        });
    }
}
