package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonSecondPopCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CommonBottomSecondPop {
    private TextView mCommonFirst;
    private TextView mCommonSecond;
    private PopupWindow mPopupWindow;

    public static CommonBottomSecondPop getInstance() {
        return CommonBottomPopHolder.bottomPop;
    }

    public void showCommonBootomPopup(Context context, CommonSecondPopCallback callback, View showView, String firstName, String secondName) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_common_bottom_second_select_pop, (ViewGroup) null);
        this.mCommonFirst = (TextView) view.findViewById(R.id.tv_common_select_first);
        this.mCommonFirst.setText(firstName);
        this.mCommonSecond = (TextView) view.findViewById(R.id.tv_common_select_second);
        this.mCommonSecond.setText(secondName);
        View otherLayout = view.findViewById(R.id.common_outside_pop_view);
        this.mCommonFirst.setOnClickListener(CommonBottomSecondPop$$Lambda$1.lambdaFactory$(this, callback));
        this.mCommonSecond.setOnClickListener(CommonBottomSecondPop$$Lambda$2.lambdaFactory$(this, callback));
        otherLayout.setOnClickListener(CommonBottomSecondPop$$Lambda$3.lambdaFactory$(this));
        this.mPopupWindow = new PopupWindow(view, -1, -2);
        this.mPopupWindow.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.showAtLocation(showView, 83, 0, 0);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.CommonBottomSecondPop.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                CommonBottomSecondPop.this.dismissPop();
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCommonBootomPopup$0(CommonSecondPopCallback callback, View v) {
        callback.commonPopOneClick();
        dismissPop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCommonBootomPopup$1(CommonSecondPopCallback callback, View v) {
        callback.commonPopTwoClick();
        dismissPop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCommonBootomPopup$2(View v) {
        this.mPopupWindow.dismiss();
    }

    public void dismissPop() {
        if (this.mPopupWindow != null && this.mPopupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
    }

    public boolean isShow() {
        return this.mPopupWindow != null && this.mPopupWindow.isShowing();
    }

    private static class CommonBottomPopHolder {
        private static final CommonBottomSecondPop bottomPop = new CommonBottomSecondPop();
    }
}
