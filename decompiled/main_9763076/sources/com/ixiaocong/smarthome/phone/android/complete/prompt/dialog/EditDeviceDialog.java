package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.facebook.react.bridge.Callback;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.rn.callback.RNParameterCallback;
import com.ixiaocong.smarthome.phone.rn.callback.RNParameterRenameCallback;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EditDeviceDialog {
    public static void renameParSetting(Context context, RNParameterCallback parameterCallback, Callback callback, String parameterId, String parName) {
        PromptDialog renameParDialog = new PromptDialog(context, R.style.inputDialog);
        renameParDialog.confirmColor = R.color.master_color;
        renameParDialog.cancelColor = R.color.gray_6;
        renameParDialog.dialogType = 1002;
        renameParDialog.title = "设备参数重命名";
        renameParDialog.editHint = parName;
        renameParDialog.setConfirmListener(EditDeviceDialog$$Lambda$2.lambdaFactory$(renameParDialog, context, parameterCallback, callback, parameterId));
        renameParDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$renameParSetting$1(PromptDialog renameParDialog, Context context, RNParameterCallback parameterCallback, Callback callback, String parameterId, View v) {
        String renameParamter = renameParDialog.getEditText().getText().toString().trim();
        if (!TextUtils.isEmpty(renameParamter)) {
            sendParamterRenameHttp(context, parameterCallback, callback, parameterId, renameParamter);
            renameParDialog.dismiss();
        } else {
            ToastUtils.showShort(context, "设备参数名称不能为空");
        }
    }

    public static void renameParameterDialog(Context context, String title, String defaultValue, RNParameterRenameCallback callback) {
        PromptDialog renameParDialog = new PromptDialog(context, R.style.inputDialog);
        renameParDialog.confirmColor = R.color.master_color;
        renameParDialog.cancelColor = R.color.gray_6;
        renameParDialog.dialogType = 1002;
        if (TextUtils.isEmpty(title)) {
            title = "设备参数重命名";
        }
        renameParDialog.title = title;
        renameParDialog.editHint = defaultValue;
        renameParDialog.setConfirmListener(EditDeviceDialog$$Lambda$3.lambdaFactory$(renameParDialog, callback, context));
        renameParDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$renameParameterDialog$2(PromptDialog renameParDialog, RNParameterRenameCallback callback, Context context, View v) {
        String renameParamter = renameParDialog.getEditText().getText().toString().trim();
        if (!TextUtils.isEmpty(renameParamter)) {
            callback.neme(renameParamter);
            renameParDialog.dismiss();
        } else {
            ToastUtils.showShort(context, "设备参数名称不能为空");
        }
    }

    private static void sendParamterRenameHttp(final Context context, final RNParameterCallback parameterCallback, final Callback callback, final String parameterId, final String parameterName) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("deviceParameterId", parameterId);
        params.put("parameterName", parameterName);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("parameter/rename");
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.EditDeviceDialog.1
            public void onComplete(XCResponseBean var1) {
                parameterCallback.onRenameParamter(callback, true, parameterId, parameterName);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
            }
        });
    }
}
