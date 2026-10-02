package com.facebook.react.views.modal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.R;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.JSTouchDispatcher;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.view.ReactViewGroup;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactModalHostView extends ViewGroup implements LifecycleEventListener {
    private String mAnimationType;
    private Dialog mDialog;
    private boolean mHardwareAccelerated;
    private DialogRootViewGroup mHostView;
    private OnRequestCloseListener mOnRequestCloseListener;
    private DialogInterface.OnShowListener mOnShowListener;
    private boolean mPropertyRequiresNewDialog;
    private boolean mTransparent;

    public interface OnRequestCloseListener {
        void onRequestClose(DialogInterface dialogInterface);
    }

    public ReactModalHostView(Context context) {
        super(context);
        ((ReactContext) context).addLifecycleEventListener(this);
        this.mHostView = new DialogRootViewGroup(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override // android.view.ViewGroup
    public void addView(View child, int index) {
        this.mHostView.addView(child, index);
    }

    @Override // android.view.ViewGroup
    public int getChildCount() {
        return this.mHostView.getChildCount();
    }

    @Override // android.view.ViewGroup
    public View getChildAt(int index) {
        return this.mHostView.getChildAt(index);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View child) {
        this.mHostView.removeView(child);
    }

    @Override // android.view.ViewGroup
    public void removeViewAt(int index) {
        View child = getChildAt(index);
        this.mHostView.removeView(child);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addChildrenForAccessibility(ArrayList<View> outChildren) {
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    public void onDropInstance() {
        ((ReactContext) getContext()).removeLifecycleEventListener(this);
        dismiss();
    }

    private void dismiss() {
        if (this.mDialog != null) {
            this.mDialog.dismiss();
            this.mDialog = null;
            ViewGroup parent = (ViewGroup) this.mHostView.getParent();
            parent.removeViewAt(0);
        }
    }

    protected void setOnRequestCloseListener(OnRequestCloseListener listener) {
        this.mOnRequestCloseListener = listener;
    }

    protected void setOnShowListener(DialogInterface.OnShowListener listener) {
        this.mOnShowListener = listener;
    }

    protected void setTransparent(boolean transparent) {
        this.mTransparent = transparent;
    }

    protected void setAnimationType(String animationType) {
        this.mAnimationType = animationType;
        this.mPropertyRequiresNewDialog = true;
    }

    protected void setHardwareAccelerated(boolean hardwareAccelerated) {
        this.mHardwareAccelerated = hardwareAccelerated;
        this.mPropertyRequiresNewDialog = true;
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        showOrUpdate();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        dismiss();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
        onDropInstance();
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    protected void showOrUpdate() {
        if (this.mDialog != null) {
            if (this.mPropertyRequiresNewDialog) {
                dismiss();
            } else {
                updateProperties();
                return;
            }
        }
        this.mPropertyRequiresNewDialog = false;
        int theme = R.style.Theme_FullScreenDialog;
        if (this.mAnimationType.equals("fade")) {
            theme = R.style.Theme_FullScreenDialogAnimatedFade;
        } else if (this.mAnimationType.equals("slide")) {
            theme = R.style.Theme_FullScreenDialogAnimatedSlide;
        }
        this.mDialog = new Dialog(getContext(), theme);
        this.mDialog.setContentView(getContentView());
        updateProperties();
        this.mDialog.setOnShowListener(this.mOnShowListener);
        this.mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.facebook.react.views.modal.ReactModalHostView.1
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == 1) {
                    if (keyCode == 4) {
                        Assertions.assertNotNull(ReactModalHostView.this.mOnRequestCloseListener, "setOnRequestCloseListener must be called by the manager");
                        ReactModalHostView.this.mOnRequestCloseListener.onRequestClose(dialog);
                        return true;
                    }
                    Activity currentActivity = ((ReactContext) ReactModalHostView.this.getContext()).getCurrentActivity();
                    if (currentActivity != null) {
                        return currentActivity.onKeyUp(keyCode, event);
                    }
                }
                return false;
            }
        });
        this.mDialog.getWindow().setSoftInputMode(16);
        if (this.mHardwareAccelerated) {
            this.mDialog.getWindow().addFlags(16777216);
        }
        this.mDialog.show();
    }

    private View getContentView() {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.addView(this.mHostView);
        frameLayout.setFitsSystemWindows(true);
        return frameLayout;
    }

    private void updateProperties() {
        Assertions.assertNotNull(this.mDialog, "mDialog must exist when we call updateProperties");
        if (this.mTransparent) {
            this.mDialog.getWindow().clearFlags(2);
        } else {
            this.mDialog.getWindow().setDimAmount(0.5f);
            this.mDialog.getWindow().setFlags(2, 2);
        }
    }

    static class DialogRootViewGroup extends ReactViewGroup implements RootView {
        private final JSTouchDispatcher mJSTouchDispatcher;

        public DialogRootViewGroup(Context context) {
            super(context);
            this.mJSTouchDispatcher = new JSTouchDispatcher(this);
        }

        @Override // com.facebook.react.views.view.ReactViewGroup, android.view.View
        protected void onSizeChanged(final int w, final int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            if (getChildCount() > 0) {
                ((ReactContext) getContext()).runOnNativeModulesQueueThread(new Runnable() { // from class: com.facebook.react.views.modal.ReactModalHostView.DialogRootViewGroup.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ((UIManagerModule) ((ReactContext) DialogRootViewGroup.this.getContext()).getNativeModule(UIManagerModule.class)).updateNodeSize(DialogRootViewGroup.this.getChildAt(0).getId(), w, h);
                    }
                });
            }
        }

        @Override // com.facebook.react.views.view.ReactViewGroup, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent event) {
            this.mJSTouchDispatcher.handleTouchEvent(event, getEventDispatcher());
            return super.onInterceptTouchEvent(event);
        }

        @Override // com.facebook.react.views.view.ReactViewGroup, android.view.View
        public boolean onTouchEvent(MotionEvent event) {
            this.mJSTouchDispatcher.handleTouchEvent(event, getEventDispatcher());
            super.onTouchEvent(event);
            return true;
        }

        @Override // com.facebook.react.uimanager.RootView
        public void onChildStartedNativeGesture(MotionEvent androidEvent) {
            this.mJSTouchDispatcher.onChildStartedNativeGesture(androidEvent, getEventDispatcher());
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }

        private EventDispatcher getEventDispatcher() {
            ReactContext reactContext = (ReactContext) getContext();
            return ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
        }
    }
}
