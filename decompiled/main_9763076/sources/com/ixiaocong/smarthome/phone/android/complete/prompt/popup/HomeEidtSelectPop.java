package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import bsh.ParserConstants;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ScreenUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.HomeEidtSelectPopCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeEidtSelectPop {
    private LinearLayout mAddLayout;
    private TextView mAddTextView;
    private LinearLayout mScanLayout;
    private TextView mScanTextView;
    private PopupWindow mSelectPop;

    public static HomeEidtSelectPop getInstance() {
        return SelectAddPopHolder.INSTANCE;
    }

    public void showSelectPop(Context context, View view, String firstName, String SecondName, HomeEidtSelectPopCallback callback) {
        View popView = LayoutInflater.from(context).inflate(R.layout.pop_home_eidt_select_layout, (ViewGroup) null);
        this.mScanLayout = (LinearLayout) popView.findViewById(R.id.ll_scan_select_pop);
        this.mAddLayout = (LinearLayout) popView.findViewById(R.id.ll_add_select_pop);
        this.mScanTextView = (TextView) popView.findViewById(R.id.tv_scan_select_pop);
        this.mAddTextView = (TextView) popView.findViewById(R.id.tv_add_select_pop);
        if (!TextUtils.isEmpty(firstName)) {
            this.mScanTextView.setText(firstName);
        }
        if (!TextUtils.isEmpty(SecondName)) {
            this.mAddTextView.setText(SecondName);
        }
        this.mScanLayout.setOnClickListener(HomeEidtSelectPop$$Lambda$1.lambdaFactory$(this, callback));
        this.mAddLayout.setOnClickListener(HomeEidtSelectPop$$Lambda$2.lambdaFactory$(this, callback));
        this.mSelectPop = new PopupWindow();
        this.mSelectPop.setContentView(popView);
        this.mSelectPop.setWidth((int) context.getResources().getDimension(R.dimen.x210));
        this.mSelectPop.setHeight((int) context.getResources().getDimension(R.dimen.x146));
        this.mSelectPop.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mSelectPop.setBackgroundDrawable(new BitmapDrawable());
        this.mSelectPop.setOutsideTouchable(true);
        this.mSelectPop.setFocusable(true);
        int height = ScreenUtils.getStatusHeight(context) + ParserConstants.RSIGNEDSHIFTASSIGN;
        this.mSelectPop.showAtLocation(view, 53, 50, height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$0(HomeEidtSelectPopCallback callback, View v) {
        if (callback != null) {
            callback.OnFirstClick();
        }
        this.mSelectPop.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$1(HomeEidtSelectPopCallback callback, View v) {
        if (callback != null) {
            callback.OnSecondClick();
        }
        this.mSelectPop.dismiss();
    }

    private static class SelectAddPopHolder {
        private static final HomeEidtSelectPop INSTANCE = new HomeEidtSelectPop();
    }
}
