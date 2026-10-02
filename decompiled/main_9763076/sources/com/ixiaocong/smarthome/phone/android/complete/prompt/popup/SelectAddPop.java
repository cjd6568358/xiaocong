package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import bsh.ParserConstants;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ScreenUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SelectAddPop {
    private LinearLayout mFirstLayout;
    private ImageView mIvFirst;
    private ImageView mIvSecond;
    private LinearLayout mSecondLayout;
    private PopupWindow mSelectPop;
    private TextView mTvFirst;
    private TextView mTvSecond;

    public static SelectAddPop getInstance() {
        return SelectAddPopHolder.INSTANCE;
    }

    public void showSelectPop(Context context, View view, CommonPopCallback callback, boolean isShowImg) {
        View popView = LayoutInflater.from(context).inflate(R.layout.pop_select_add_layout, (ViewGroup) null);
        this.mFirstLayout = (LinearLayout) popView.findViewById(R.id.ll_pop_select_first);
        this.mSecondLayout = (LinearLayout) popView.findViewById(R.id.ll_pop_select_second);
        this.mIvFirst = (ImageView) popView.findViewById(R.id.iv_pop_select_first);
        this.mIvSecond = (ImageView) popView.findViewById(R.id.iv_pop_select_second);
        this.mTvFirst = (TextView) popView.findViewById(R.id.tv_pop_select_first);
        this.mTvSecond = (TextView) popView.findViewById(R.id.tv_pop_select_second);
        if (isShowImg) {
            this.mIvFirst.setVisibility(0);
            this.mIvSecond.setVisibility(0);
            this.mTvFirst.setGravity(19);
            this.mTvSecond.setGravity(19);
        } else {
            this.mIvFirst.setVisibility(8);
            this.mIvSecond.setVisibility(8);
            this.mTvFirst.setGravity(17);
            this.mTvSecond.setGravity(17);
        }
        this.mFirstLayout.setOnClickListener(SelectAddPop$$Lambda$1.lambdaFactory$(this, callback));
        this.mSecondLayout.setOnClickListener(SelectAddPop$$Lambda$2.lambdaFactory$(this, callback));
        this.mSelectPop = new PopupWindow();
        this.mSelectPop.setContentView(popView);
        this.mSelectPop.setWidth((int) context.getResources().getDimension(R.dimen.x210));
        this.mSelectPop.setHeight((int) context.getResources().getDimension(R.dimen.x146));
        this.mSelectPop.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mSelectPop.setBackgroundDrawable(new BitmapDrawable());
        this.mSelectPop.setOutsideTouchable(true);
        this.mSelectPop.setFocusable(true);
        int height = ScreenUtils.getStatusHeight(context) + ParserConstants.RSIGNEDSHIFTASSIGN;
        this.mSelectPop.showAtLocation(view, 53, 100, height);
        popView.setFocusable(true);
        popView.setFocusableInTouchMode(true);
        popView.setOnKeyListener(new View.OnKeyListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.SelectAddPop.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                SelectAddPop.this.dismissPop();
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$0(CommonPopCallback callback, View v) {
        callback.commonFirstClick();
        this.mSelectPop.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$1(CommonPopCallback callback, View v) {
        callback.commonSecondClick();
        this.mSelectPop.dismiss();
    }

    public void setName(String firstName, String secondName) {
        this.mTvFirst.setText(firstName);
        this.mTvSecond.setText(secondName);
    }

    public void setIconImg(Drawable firstImg, Drawable secondImg) {
        this.mIvFirst.setBackground(firstImg);
        this.mIvSecond.setBackground(secondImg);
    }

    public void dismissPop() {
        if (this.mSelectPop != null) {
            this.mSelectPop.dismiss();
        }
    }

    private static class SelectAddPopHolder {
        private static final SelectAddPop INSTANCE = new SelectAddPop();
    }
}
