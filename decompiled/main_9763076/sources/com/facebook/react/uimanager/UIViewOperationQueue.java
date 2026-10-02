package com.facebook.react.uimanager;

import com.facebook.react.animation.Animation;
import com.facebook.react.animation.AnimationRegistry;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UIViewOperationQueue {
    private final AnimationRegistry mAnimationRegistry;
    private final DispatchUIFrameCallback mDispatchUIFrameCallback;
    private final NativeViewHierarchyManager mNativeViewHierarchyManager;
    private final ReactApplicationContext mReactApplicationContext;
    private NotThreadSafeViewHierarchyUpdateDebugListener mViewHierarchyUpdateDebugListener;
    private final int[] mMeasureBuffer = new int[4];
    private final Object mDispatchRunnablesLock = new Object();
    private final Object mNonBatchedOperationsLock = new Object();
    private final ArrayList<Runnable> mDispatchUIRunnables = new ArrayList<>();
    private ArrayList<UIOperation> mOperations = new ArrayList<>();
    private ArrayDeque<UIOperation> mNonBatchedOperations = new ArrayDeque<>();
    private boolean mIsDispatchUIFrameCallbackEnqueued = false;

    public interface UIOperation {
        void execute();
    }

    private abstract class ViewOperation implements UIOperation {
        public int mTag;

        public ViewOperation(int tag) {
            this.mTag = tag;
        }
    }

    private final class RemoveRootViewOperation extends ViewOperation {
        public RemoveRootViewOperation(int tag) {
            super(tag);
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.removeRootView(this.mTag);
        }
    }

    private final class UpdatePropertiesOperation extends ViewOperation {
        private final ReactStylesDiffMap mProps;

        private UpdatePropertiesOperation(int tag, ReactStylesDiffMap props) {
            super(tag);
            this.mProps = props;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.updateProperties(this.mTag, this.mProps);
        }
    }

    private final class UpdateLayoutOperation extends ViewOperation {
        private final int mHeight;
        private final int mParentTag;
        private final int mWidth;
        private final int mX;
        private final int mY;

        public UpdateLayoutOperation(int parentTag, int tag, int x, int y, int width, int height) {
            super(tag);
            this.mParentTag = parentTag;
            this.mX = x;
            this.mY = y;
            this.mWidth = width;
            this.mHeight = height;
            Systrace.startAsyncFlow(0L, "updateLayout", this.mTag);
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            Systrace.endAsyncFlow(0L, "updateLayout", this.mTag);
            UIViewOperationQueue.this.mNativeViewHierarchyManager.updateLayout(this.mParentTag, this.mTag, this.mX, this.mY, this.mWidth, this.mHeight);
        }
    }

    private final class CreateViewOperation extends ViewOperation {
        private final String mClassName;
        private final ReactStylesDiffMap mInitialProps;
        private final ThemedReactContext mThemedContext;

        public CreateViewOperation(ThemedReactContext themedContext, int tag, String className, ReactStylesDiffMap initialProps) {
            super(tag);
            this.mThemedContext = themedContext;
            this.mClassName = className;
            this.mInitialProps = initialProps;
            Systrace.startAsyncFlow(0L, "createView", this.mTag);
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            Systrace.endAsyncFlow(0L, "createView", this.mTag);
            UIViewOperationQueue.this.mNativeViewHierarchyManager.createView(this.mThemedContext, this.mTag, this.mClassName, this.mInitialProps);
        }
    }

    private final class ManageChildrenOperation extends ViewOperation {
        private final int[] mIndicesToRemove;
        private final int[] mTagsToDelete;
        private final ViewAtIndex[] mViewsToAdd;

        public ManageChildrenOperation(int tag, int[] indicesToRemove, ViewAtIndex[] viewsToAdd, int[] tagsToDelete) {
            super(tag);
            this.mIndicesToRemove = indicesToRemove;
            this.mViewsToAdd = viewsToAdd;
            this.mTagsToDelete = tagsToDelete;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.manageChildren(this.mTag, this.mIndicesToRemove, this.mViewsToAdd, this.mTagsToDelete);
        }
    }

    private final class UpdateViewExtraData extends ViewOperation {
        private final Object mExtraData;

        public UpdateViewExtraData(int tag, Object extraData) {
            super(tag);
            this.mExtraData = extraData;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.updateViewExtraData(this.mTag, this.mExtraData);
        }
    }

    private final class ChangeJSResponderOperation extends ViewOperation {
        private final boolean mBlockNativeResponder;
        private final boolean mClearResponder;
        private final int mInitialTag;

        public ChangeJSResponderOperation(int tag, int initialTag, boolean clearResponder, boolean blockNativeResponder) {
            super(tag);
            this.mInitialTag = initialTag;
            this.mClearResponder = clearResponder;
            this.mBlockNativeResponder = blockNativeResponder;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            if (!this.mClearResponder) {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.setJSResponder(this.mTag, this.mInitialTag, this.mBlockNativeResponder);
            } else {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.clearJSResponder();
            }
        }
    }

    private final class DispatchCommandOperation extends ViewOperation {
        private final ReadableArray mArgs;
        private final int mCommand;

        public DispatchCommandOperation(int tag, int command, ReadableArray args) {
            super(tag);
            this.mCommand = command;
            this.mArgs = args;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.dispatchCommand(this.mTag, this.mCommand, this.mArgs);
        }
    }

    private final class ShowPopupMenuOperation extends ViewOperation {
        private final ReadableArray mItems;
        private final Callback mSuccess;

        public ShowPopupMenuOperation(int tag, ReadableArray items, Callback success) {
            super(tag);
            this.mItems = items;
            this.mSuccess = success;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.showPopupMenu(this.mTag, this.mItems, this.mSuccess);
        }
    }

    private static abstract class AnimationOperation implements UIOperation {
        protected final int mAnimationID;

        public AnimationOperation(int animationID) {
            this.mAnimationID = animationID;
        }
    }

    private class RegisterAnimationOperation extends AnimationOperation {
        private final Animation mAnimation;

        private RegisterAnimationOperation(Animation animation) {
            super(animation.getAnimationID());
            this.mAnimation = animation;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mAnimationRegistry.registerAnimation(this.mAnimation);
        }
    }

    private class AddAnimationOperation extends AnimationOperation {
        private final int mReactTag;
        private final Callback mSuccessCallback;

        private AddAnimationOperation(int reactTag, int animationID, Callback successCallback) {
            super(animationID);
            this.mReactTag = reactTag;
            this.mSuccessCallback = successCallback;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            Animation animation = UIViewOperationQueue.this.mAnimationRegistry.getAnimation(this.mAnimationID);
            if (animation != null) {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.startAnimationForNativeView(this.mReactTag, animation, this.mSuccessCallback);
                return;
            }
            throw new IllegalViewOperationException("Animation with id " + this.mAnimationID + " was not found");
        }
    }

    private final class RemoveAnimationOperation extends AnimationOperation {
        private RemoveAnimationOperation(int animationID) {
            super(animationID);
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            Animation animation = UIViewOperationQueue.this.mAnimationRegistry.getAnimation(this.mAnimationID);
            if (animation != null) {
                animation.cancel();
            }
        }
    }

    private class SetLayoutAnimationEnabledOperation implements UIOperation {
        private final boolean mEnabled;

        private SetLayoutAnimationEnabledOperation(boolean enabled) {
            this.mEnabled = enabled;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.setLayoutAnimationEnabled(this.mEnabled);
        }
    }

    private class ConfigureLayoutAnimationOperation implements UIOperation {
        private final ReadableMap mConfig;

        private ConfigureLayoutAnimationOperation(ReadableMap config) {
            this.mConfig = config;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.configureLayoutAnimation(this.mConfig);
        }
    }

    private final class MeasureOperation implements UIOperation {
        private final Callback mCallback;
        private final int mReactTag;

        private MeasureOperation(int reactTag, Callback callback) {
            this.mReactTag = reactTag;
            this.mCallback = callback;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            try {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, UIViewOperationQueue.this.mMeasureBuffer);
                float x = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[0]);
                float y = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[1]);
                float width = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[2]);
                float height = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[3]);
                this.mCallback.invoke(0, 0, Float.valueOf(width), Float.valueOf(height), Float.valueOf(x), Float.valueOf(y));
            } catch (NoSuchNativeViewException e) {
                this.mCallback.invoke(new Object[0]);
            }
        }
    }

    private final class MeasureInWindowOperation implements UIOperation {
        private final Callback mCallback;
        private final int mReactTag;

        private MeasureInWindowOperation(int reactTag, Callback callback) {
            this.mReactTag = reactTag;
            this.mCallback = callback;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            try {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.measureInWindow(this.mReactTag, UIViewOperationQueue.this.mMeasureBuffer);
                float x = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[0]);
                float y = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[1]);
                float width = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[2]);
                float height = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[3]);
                this.mCallback.invoke(Float.valueOf(x), Float.valueOf(y), Float.valueOf(width), Float.valueOf(height));
            } catch (NoSuchNativeViewException e) {
                this.mCallback.invoke(new Object[0]);
            }
        }
    }

    private final class FindTargetForTouchOperation implements UIOperation {
        private final Callback mCallback;
        private final int mReactTag;
        private final float mTargetX;
        private final float mTargetY;

        private FindTargetForTouchOperation(int reactTag, float targetX, float targetY, Callback callback) {
            this.mReactTag = reactTag;
            this.mTargetX = targetX;
            this.mTargetY = targetY;
            this.mCallback = callback;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            try {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, UIViewOperationQueue.this.mMeasureBuffer);
                float containerX = UIViewOperationQueue.this.mMeasureBuffer[0];
                float containerY = UIViewOperationQueue.this.mMeasureBuffer[1];
                int touchTargetReactTag = UIViewOperationQueue.this.mNativeViewHierarchyManager.findTargetTagForTouch(this.mReactTag, this.mTargetX, this.mTargetY);
                try {
                    UIViewOperationQueue.this.mNativeViewHierarchyManager.measure(touchTargetReactTag, UIViewOperationQueue.this.mMeasureBuffer);
                    float x = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[0] - containerX);
                    float y = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[1] - containerY);
                    float width = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[2]);
                    float height = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[3]);
                    this.mCallback.invoke(Integer.valueOf(touchTargetReactTag), Float.valueOf(x), Float.valueOf(y), Float.valueOf(width), Float.valueOf(height));
                } catch (IllegalViewOperationException e) {
                    this.mCallback.invoke(new Object[0]);
                }
            } catch (IllegalViewOperationException e2) {
                this.mCallback.invoke(new Object[0]);
            }
        }
    }

    private class UIBlockOperation implements UIOperation {
        private final UIBlock mBlock;

        public UIBlockOperation(UIBlock block) {
            this.mBlock = block;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            this.mBlock.execute(UIViewOperationQueue.this.mNativeViewHierarchyManager);
        }
    }

    private final class SendAccessibilityEvent extends ViewOperation {
        private final int mEventType;

        private SendAccessibilityEvent(int tag, int eventType) {
            super(tag);
            this.mEventType = eventType;
        }

        @Override // com.facebook.react.uimanager.UIViewOperationQueue.UIOperation
        public void execute() {
            UIViewOperationQueue.this.mNativeViewHierarchyManager.sendAccessibilityEvent(this.mTag, this.mEventType);
        }
    }

    public UIViewOperationQueue(ReactApplicationContext reactContext, NativeViewHierarchyManager nativeViewHierarchyManager) {
        this.mNativeViewHierarchyManager = nativeViewHierarchyManager;
        this.mAnimationRegistry = nativeViewHierarchyManager.getAnimationRegistry();
        this.mDispatchUIFrameCallback = new DispatchUIFrameCallback(reactContext);
        this.mReactApplicationContext = reactContext;
    }

    NativeViewHierarchyManager getNativeViewHierarchyManager() {
        return this.mNativeViewHierarchyManager;
    }

    public void setViewHierarchyUpdateDebugListener(NotThreadSafeViewHierarchyUpdateDebugListener listener) {
        this.mViewHierarchyUpdateDebugListener = listener;
    }

    public boolean isEmpty() {
        return this.mOperations.isEmpty();
    }

    public void addRootView(final int tag, final SizeMonitoringFrameLayout rootView, final ThemedReactContext themedRootContext) {
        if (UiThreadUtil.isOnUiThread()) {
            this.mNativeViewHierarchyManager.addRootView(tag, rootView, themedRootContext);
            return;
        }
        final Semaphore semaphore = new Semaphore(0);
        this.mReactApplicationContext.runOnUiQueueThread(new Runnable() { // from class: com.facebook.react.uimanager.UIViewOperationQueue.1
            @Override // java.lang.Runnable
            public void run() {
                UIViewOperationQueue.this.mNativeViewHierarchyManager.addRootView(tag, rootView, themedRootContext);
                semaphore.release();
            }
        });
        try {
            SoftAssertions.assertCondition(semaphore.tryAcquire(5000L, TimeUnit.MILLISECONDS), "Timed out adding root view");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void enqueueRemoveRootView(int rootViewTag) {
        this.mOperations.add(new RemoveRootViewOperation(rootViewTag));
    }

    public void enqueueSetJSResponder(int tag, int initialTag, boolean blockNativeResponder) {
        this.mOperations.add(new ChangeJSResponderOperation(tag, initialTag, false, blockNativeResponder));
    }

    public void enqueueClearJSResponder() {
        this.mOperations.add(new ChangeJSResponderOperation(0, 0, true, false));
    }

    public void enqueueDispatchCommand(int reactTag, int commandId, ReadableArray commandArgs) {
        this.mOperations.add(new DispatchCommandOperation(reactTag, commandId, commandArgs));
    }

    public void enqueueUpdateExtraData(int reactTag, Object extraData) {
        this.mOperations.add(new UpdateViewExtraData(reactTag, extraData));
    }

    public void enqueueShowPopupMenu(int reactTag, ReadableArray items, Callback error, Callback success) {
        this.mOperations.add(new ShowPopupMenuOperation(reactTag, items, success));
    }

    public void enqueueCreateView(ThemedReactContext themedContext, int viewReactTag, String viewClassName, ReactStylesDiffMap initialProps) {
        synchronized (this.mNonBatchedOperationsLock) {
            this.mNonBatchedOperations.addLast(new CreateViewOperation(themedContext, viewReactTag, viewClassName, initialProps));
        }
    }

    public void enqueueUpdateProperties(int reactTag, String className, ReactStylesDiffMap props) {
        this.mOperations.add(new UpdatePropertiesOperation(reactTag, props));
    }

    public void enqueueUpdateLayout(int parentTag, int reactTag, int x, int y, int width, int height) {
        this.mOperations.add(new UpdateLayoutOperation(parentTag, reactTag, x, y, width, height));
    }

    public void enqueueManageChildren(int reactTag, int[] indicesToRemove, ViewAtIndex[] viewsToAdd, int[] tagsToDelete) {
        this.mOperations.add(new ManageChildrenOperation(reactTag, indicesToRemove, viewsToAdd, tagsToDelete));
    }

    public void enqueueRegisterAnimation(Animation animation) {
        this.mOperations.add(new RegisterAnimationOperation(animation));
    }

    public void enqueueAddAnimation(int reactTag, int animationID, Callback onSuccess) {
        this.mOperations.add(new AddAnimationOperation(reactTag, animationID, onSuccess));
    }

    public void enqueueRemoveAnimation(int animationID) {
        this.mOperations.add(new RemoveAnimationOperation(animationID));
    }

    public void enqueueSetLayoutAnimationEnabled(boolean enabled) {
        this.mOperations.add(new SetLayoutAnimationEnabledOperation(enabled));
    }

    public void enqueueConfigureLayoutAnimation(ReadableMap config, Callback onSuccess, Callback onError) {
        this.mOperations.add(new ConfigureLayoutAnimationOperation(config));
    }

    public void enqueueMeasure(int reactTag, Callback callback) {
        this.mOperations.add(new MeasureOperation(reactTag, callback));
    }

    public void enqueueMeasureInWindow(int reactTag, Callback callback) {
        this.mOperations.add(new MeasureInWindowOperation(reactTag, callback));
    }

    public void enqueueFindTargetForTouch(int reactTag, float targetX, float targetY, Callback callback) {
        this.mOperations.add(new FindTargetForTouchOperation(reactTag, targetX, targetY, callback));
    }

    public void enqueueSendAccessibilityEvent(int tag, int eventType) {
        this.mOperations.add(new SendAccessibilityEvent(tag, eventType));
    }

    public void enqueueUIBlock(UIBlock block) {
        this.mOperations.add(new UIBlockOperation(block));
    }

    void dispatchViewUpdates(final int batchId) {
        final UIOperation[] nonBatchedOperations;
        final ArrayList<UIOperation> operations = this.mOperations.isEmpty() ? null : this.mOperations;
        if (operations != null) {
            this.mOperations = new ArrayList<>();
        }
        synchronized (this.mNonBatchedOperationsLock) {
            if (!this.mNonBatchedOperations.isEmpty()) {
                nonBatchedOperations = (UIOperation[]) this.mNonBatchedOperations.toArray(new UIOperation[this.mNonBatchedOperations.size()]);
                this.mNonBatchedOperations.clear();
            } else {
                nonBatchedOperations = null;
            }
        }
        if (this.mViewHierarchyUpdateDebugListener != null) {
            this.mViewHierarchyUpdateDebugListener.onViewHierarchyUpdateEnqueued();
        }
        synchronized (this.mDispatchRunnablesLock) {
            this.mDispatchUIRunnables.add(new Runnable() { // from class: com.facebook.react.uimanager.UIViewOperationQueue.2
                @Override // java.lang.Runnable
                public void run() {
                    SystraceMessage.beginSection(0L, "DispatchUI").arg("BatchId", batchId).flush();
                    try {
                        if (nonBatchedOperations != null) {
                            for (UIOperation op : nonBatchedOperations) {
                                op.execute();
                            }
                        }
                        if (operations != null) {
                            for (int i = 0; i < operations.size(); i++) {
                                ((UIOperation) operations.get(i)).execute();
                            }
                        }
                        UIViewOperationQueue.this.mNativeViewHierarchyManager.clearLayoutAnimation();
                        if (UIViewOperationQueue.this.mViewHierarchyUpdateDebugListener != null) {
                            UIViewOperationQueue.this.mViewHierarchyUpdateDebugListener.onViewHierarchyUpdateFinished();
                        }
                    } finally {
                        Systrace.endSection(0L);
                    }
                }
            });
        }
        if (!this.mIsDispatchUIFrameCallbackEnqueued) {
            UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.uimanager.UIViewOperationQueue.3
                @Override // java.lang.Runnable
                public void run() {
                    UIViewOperationQueue.this.flushPendingBatches();
                }
            });
        }
    }

    void resumeFrameCallback() {
        this.mIsDispatchUIFrameCallbackEnqueued = true;
        ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.DISPATCH_UI, this.mDispatchUIFrameCallback);
    }

    void pauseFrameCallback() {
        this.mIsDispatchUIFrameCallbackEnqueued = false;
        ReactChoreographer.getInstance().removeFrameCallback(ReactChoreographer.CallbackType.DISPATCH_UI, this.mDispatchUIFrameCallback);
        flushPendingBatches();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flushPendingBatches() {
        synchronized (this.mDispatchRunnablesLock) {
            for (int i = 0; i < this.mDispatchUIRunnables.size(); i++) {
                this.mDispatchUIRunnables.get(i).run();
            }
            this.mDispatchUIRunnables.clear();
        }
    }

    private class DispatchUIFrameCallback extends GuardedChoreographerFrameCallback {
        private DispatchUIFrameCallback(ReactContext reactContext) {
            super(reactContext);
        }

        @Override // com.facebook.react.uimanager.GuardedChoreographerFrameCallback
        public void doFrameGuarded(long frameTimeNanos) {
            Systrace.beginSection(0L, "dispatchNonBatchedUIOperations");
            try {
                dispatchPendingNonBatchedOperations(frameTimeNanos);
                Systrace.endSection(0L);
                UIViewOperationQueue.this.flushPendingBatches();
                ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.DISPATCH_UI, this);
            } catch (Throwable th) {
                Systrace.endSection(0L);
                throw th;
            }
        }

        private void dispatchPendingNonBatchedOperations(long frameTimeNanos) {
            UIOperation nextOperation;
            while (timeLeftInFrame >= 8) {
                synchronized (UIViewOperationQueue.this.mNonBatchedOperationsLock) {
                    if (!UIViewOperationQueue.this.mNonBatchedOperations.isEmpty()) {
                        nextOperation = (UIOperation) UIViewOperationQueue.this.mNonBatchedOperations.pollFirst();
                    } else {
                        return;
                    }
                }
                nextOperation.execute();
            }
        }
    }
}
