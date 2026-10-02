package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftApScanPop {
    private Button mBtnCancel;
    private Button mBtnConfirm;
    private PopupWindow mPopupWindow;
    private TextView mTvMsg;

    public static SoftApScanPop getInstance() {
        return SoftApScanPopHolder.INSTANCE;
    }

    public void showSoftApScanPopPopup(Context context, CommonTypeCallback callback, View showView, String msg, String confirmMsg) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_common_bottom_hint_pop, (ViewGroup) null);
        this.mBtnCancel = (Button) view.findViewById(R.id.btn_common_bot_cancel);
        this.mBtnConfirm = (Button) view.findViewById(R.id.btn_common_bot_confirm);
        this.mTvMsg = (TextView) view.findViewById(R.id.tv_common_bot_msg);
        this.mTvMsg.setText(msg);
        this.mBtnConfirm.setText(confirmMsg);
        this.mBtnCancel.setOnClickListener(SoftApScanPop$$Lambda$1.lambdaFactory$(this, confirmMsg, callback));
        this.mBtnConfirm.setOnClickListener(SoftApScanPop$$Lambda$2.lambdaFactory$(this, confirmMsg, callback));
        this.mPopupWindow = new PopupWindow(view, -1, -2);
        this.mPopupWindow.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.showAtLocation(showView, 83, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSoftApScanPopPopup$0(String confirmMsg, CommonTypeCallback callback, View v) {
        if (confirmMsg.equals("重试")) {
            callback.resultTypeCalllback(1);
        } else {
            callback.resultTypeCalllback(0);
        }
        this.mPopupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSoftApScanPopPopup$1(String confirmMsg, CommonTypeCallback callback, View v) {
        if (confirmMsg.equals("重试")) {
            callback.resultTypeCalllback(0);
        } else {
            callback.resultTypeCalllback(1);
        }
        this.mPopupWindow.dismiss();
    }

    private static class SoftApScanPopHolder {
        private static final SoftApScanPop INSTANCE = new SoftApScanPop();
    }
}
