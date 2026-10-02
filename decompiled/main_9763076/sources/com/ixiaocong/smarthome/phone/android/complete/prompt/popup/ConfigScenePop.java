package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttRecommendDetailActivity;
import com.xiaocong.smarthome.greendao.model.insert.SceneDB;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ConfigScenePop {
    private Button mBtnCancel;
    private Button mBtnConfirm;
    private PopupWindow mPopupWindow;
    private TextView mTvMsg;

    public static ConfigScenePop getInstance() {
        return ConfigScenePopHolder.configScenePop;
    }

    public void showConfigScenePopup(Context context, List<SceneDB> listModels, int position, View showView) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_common_bottom_hint_pop, (ViewGroup) null);
        this.mBtnCancel = (Button) view.findViewById(R.id.btn_common_bot_cancel);
        this.mBtnConfirm = (Button) view.findViewById(R.id.btn_common_bot_confirm);
        this.mTvMsg = (TextView) view.findViewById(R.id.tv_common_bot_msg);
        this.mTvMsg.setText("当前场景还未配置,需配置后才能使用");
        View otherLayout = view.findViewById(R.id.common_bot_outside_pop_view);
        this.mBtnCancel.setOnClickListener(ConfigScenePop$$Lambda$1.lambdaFactory$(this));
        this.mBtnConfirm.setOnClickListener(ConfigScenePop$$Lambda$2.lambdaFactory$(this, listModels, position, context));
        otherLayout.setOnClickListener(ConfigScenePop$$Lambda$3.lambdaFactory$(this));
        this.mPopupWindow = new PopupWindow(view, -1, -2);
        this.mPopupWindow.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.showAtLocation(showView, 83, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showConfigScenePopup$0(View v) {
        this.mPopupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showConfigScenePopup$1(List listModels, int position, Context context, View v) {
        Intent intent = new Intent();
        intent.putExtra("triggerId", ((SceneDB) listModels.get(position)).getSceneId());
        intent.putExtra("triggerName", ((SceneDB) listModels.get(position)).getSceneName());
        intent.setClass(context, DeviceIftttRecommendDetailActivity.class);
        context.startActivity(intent);
        this.mPopupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showConfigScenePopup$2(View v) {
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

    private static class ConfigScenePopHolder {
        private static final ConfigScenePop configScenePop = new ConfigScenePop();
    }
}
