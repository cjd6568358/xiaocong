package com.xiaocong.smarthome.pickerview.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.xiaocong.smarthome.pickerview.configure.PickerOptions;
import com.xiaocong.smarthome.pickerview.listener.OnDismissListener;
import com.xiaocong.smarthome.pickerview.utils.PickerViewAnimateUtil;
import com.xiaocong.smarthome.uilib.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class BasePickerView {
    protected View clickView;
    protected ViewGroup contentContainer;
    private Context context;
    private ViewGroup dialogView;
    private boolean dismissing;
    private Animation inAnim;
    private boolean isShowing;
    private Dialog mDialog;
    protected PickerOptions mPickerOptions;
    private OnDismissListener onDismissListener;
    private Animation outAnim;
    private ViewGroup rootView;
    protected int animGravity = 80;
    private boolean isAnim = true;
    private View.OnKeyListener onKeyBackListener = new View.OnKeyListener() { // from class: com.xiaocong.smarthome.pickerview.view.BasePickerView.4
        @Override // android.view.View.OnKeyListener
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode != 4 || event.getAction() != 0 || !BasePickerView.this.isShowing()) {
                return false;
            }
            BasePickerView.this.dismiss();
            return true;
        }
    };
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() { // from class: com.xiaocong.smarthome.pickerview.view.BasePickerView.5
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 0) {
                BasePickerView.this.dismiss();
                return false;
            }
            return false;
        }
    };

    public BasePickerView(Context context) {
        this.context = context;
    }

    protected void initViews() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -2, 80);
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        if (isDialog()) {
            this.dialogView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, (ViewGroup) null, false);
            this.dialogView.setBackgroundColor(0);
            this.contentContainer = (ViewGroup) this.dialogView.findViewById(R.id.content_container);
            params.leftMargin = 30;
            params.rightMargin = 30;
            this.contentContainer.setLayoutParams(params);
            createDialog();
            this.dialogView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaocong.smarthome.pickerview.view.BasePickerView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    BasePickerView.this.dismiss();
                }
            });
        } else {
            if (this.mPickerOptions.decorView == null) {
                this.mPickerOptions.decorView = (ViewGroup) ((Activity) this.context).getWindow().getDecorView();
            }
            this.rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, this.mPickerOptions.decorView, false);
            this.rootView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            if (this.mPickerOptions.backgroundId != -1) {
                this.rootView.setBackgroundColor(this.mPickerOptions.backgroundId);
            }
            this.contentContainer = (ViewGroup) this.rootView.findViewById(R.id.content_container);
            this.contentContainer.setLayoutParams(params);
        }
        setKeyBackCancelable(true);
    }

    protected void initAnim() {
        this.inAnim = getInAnimation();
        this.outAnim = getOutAnimation();
    }

    public void show() {
        if (isDialog()) {
            showDialog();
        } else if (!isShowing()) {
            this.isShowing = true;
            onAttached(this.rootView);
            this.rootView.requestFocus();
        }
    }

    private void onAttached(View view) {
        this.mPickerOptions.decorView.addView(view);
        if (this.isAnim) {
            this.contentContainer.startAnimation(this.inAnim);
        }
    }

    public boolean isShowing() {
        if (isDialog()) {
            return false;
        }
        return this.rootView.getParent() != null || this.isShowing;
    }

    public void dismiss() {
        if (isDialog()) {
            dismissDialog();
            return;
        }
        if (!this.dismissing) {
            if (this.isAnim) {
                this.outAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaocong.smarthome.pickerview.view.BasePickerView.2
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        BasePickerView.this.dismissImmediately();
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                this.contentContainer.startAnimation(this.outAnim);
            } else {
                dismissImmediately();
            }
            this.dismissing = true;
        }
    }

    public void dismissImmediately() {
        this.mPickerOptions.decorView.post(new Runnable() { // from class: com.xiaocong.smarthome.pickerview.view.BasePickerView.3
            @Override // java.lang.Runnable
            public void run() {
                BasePickerView.this.mPickerOptions.decorView.removeView(BasePickerView.this.rootView);
                BasePickerView.this.isShowing = false;
                BasePickerView.this.dismissing = false;
                if (BasePickerView.this.onDismissListener != null) {
                    BasePickerView.this.onDismissListener.onDismiss(BasePickerView.this);
                }
            }
        });
    }

    private Animation getInAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.animGravity, true);
        return AnimationUtils.loadAnimation(this.context, res);
    }

    private Animation getOutAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.animGravity, false);
        return AnimationUtils.loadAnimation(this.context, res);
    }

    public void setKeyBackCancelable(boolean isCancelable) {
        ViewGroup View;
        if (isDialog()) {
            View = this.dialogView;
        } else {
            View = this.rootView;
        }
        View.setFocusable(isCancelable);
        View.setFocusableInTouchMode(isCancelable);
        if (isCancelable) {
            View.setOnKeyListener(this.onKeyBackListener);
        } else {
            View.setOnKeyListener(null);
        }
    }

    protected BasePickerView setOutSideCancelable(boolean isCancelable) {
        if (this.rootView != null) {
            View view = this.rootView.findViewById(R.id.outmost_container);
            if (isCancelable) {
                view.setOnTouchListener(this.onCancelableTouchListener);
            } else {
                view.setOnTouchListener(null);
            }
        }
        return this;
    }

    public void setDialogOutSideCancelable() {
        if (this.mDialog != null) {
            this.mDialog.setCancelable(this.mPickerOptions.cancelable);
        }
    }

    public View findViewById(int id) {
        return this.contentContainer.findViewById(id);
    }

    public void createDialog() {
        if (this.dialogView != null) {
            this.mDialog = new Dialog(this.context, R.style.custom_dialog2);
            this.mDialog.setCancelable(this.mPickerOptions.cancelable);
            this.mDialog.setContentView(this.dialogView);
            Window dialogWindow = this.mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_scale_anim);
                dialogWindow.setGravity(17);
            }
            this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaocong.smarthome.pickerview.view.BasePickerView.6
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialog) {
                    if (BasePickerView.this.onDismissListener != null) {
                        BasePickerView.this.onDismissListener.onDismiss(BasePickerView.this);
                    }
                }
            });
        }
    }

    private void showDialog() {
        if (this.mDialog != null) {
            this.mDialog.show();
        }
    }

    private void dismissDialog() {
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
    }

    public ViewGroup getDialogContainerLayout() {
        return this.contentContainer;
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public boolean isDialog() {
        return false;
    }
}
