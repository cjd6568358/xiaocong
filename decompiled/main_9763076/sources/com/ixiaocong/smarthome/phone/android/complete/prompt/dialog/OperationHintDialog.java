package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class OperationHintDialog {
    public static OperationHintDialog getInstance() {
        return OperationHintDialogHolder.hintDialog;
    }

    public void showSelectDialog(Context context, HintDialogCallback callback, String title, String msg) {
        PromptDialog mAppSelectDialog = new PromptDialog(context, R.style.PromptDialog);
        mAppSelectDialog.confirmColor = R.color.master_color;
        mAppSelectDialog.cancelColor = R.color.gray_5;
        mAppSelectDialog.dialogType = 1001;
        mAppSelectDialog.title = title;
        mAppSelectDialog.titleColor = R.color.master_color;
        mAppSelectDialog.msg = msg;
        mAppSelectDialog.setConfirmListener(OperationHintDialog$$Lambda$1.lambdaFactory$(callback, mAppSelectDialog));
        mAppSelectDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showSelectDialog$0(HintDialogCallback callback, PromptDialog mAppSelectDialog, View v) {
        callback.hintDialogListener(true);
        mAppSelectDialog.dismiss();
    }

    public void showHintDialog(Context context, HintDialogCallback callback, String title, String msg, String btnMsg) {
        PromptDialog mAppHintDialog = new PromptDialog(context, R.style.PromptDialog);
        mAppHintDialog.confirmColor = R.color.master_color;
        mAppHintDialog.dialogType = ACConstants.TAG_TEMPERATURE1;
        mAppHintDialog.title = title;
        mAppHintDialog.titleColor = R.color.master_color;
        mAppHintDialog.msg = msg;
        mAppHintDialog.btnHint = btnMsg;
        mAppHintDialog.setConfirmListener(OperationHintDialog$$Lambda$2.lambdaFactory$(msg, context, callback, mAppHintDialog));
        mAppHintDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showHintDialog$1(String msg, Context context, HintDialogCallback callback, PromptDialog mAppHintDialog, View v) {
        if (msg.equals(context.getString(R.string.open_wifi_hint))) {
            callback.hintDialogListener(true);
        } else {
            callback.hintDialogListener(false);
        }
        mAppHintDialog.dismiss();
    }

    public void showEditDialog(Context context, EditDialogCallback callback, String title, String hintMsg) {
        PromptDialog mAppEditDialog = new PromptDialog(context, R.style.PromptDialog);
        mAppEditDialog.confirmColor = R.color.master_color;
        mAppEditDialog.dialogType = 1002;
        mAppEditDialog.title = title;
        mAppEditDialog.editHint = hintMsg;
        mAppEditDialog.titleColor = R.color.master_color;
        mAppEditDialog.setConfirmListener(OperationHintDialog$$Lambda$3.lambdaFactory$(mAppEditDialog, context, callback));
        mAppEditDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showEditDialog$2(PromptDialog mAppEditDialog, Context context, EditDialogCallback callback, View v) {
        String editMsg = mAppEditDialog.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(editMsg)) {
            ToastUtils.showShort(context, "输入不能为空");
        } else {
            callback.editMsgCallback(editMsg);
            mAppEditDialog.dismiss();
        }
    }

    private static class OperationHintDialogHolder {
        private static final OperationHintDialog hintDialog = new OperationHintDialog();
    }
}
