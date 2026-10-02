package com.xiaocong.smarthome.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.xiaocong.smarthome.uilib.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TipDialog extends Dialog {
    public TipDialog(Context context) {
        this(context, R.style.XCUI_TipDialog);
    }

    public TipDialog(Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogWidth();
    }

    private void initDialogWidth() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams wmLp = window.getAttributes();
            wmLp.width = -1;
            window.setAttributes(wmLp);
        }
    }

    public static class Builder {
        private Context mContext;
        private int mCurrentIconType = 0;
        private CharSequence mTipWord;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setIconType(int iconType) {
            this.mCurrentIconType = iconType;
            return this;
        }

        public Builder setTipWord(CharSequence tipWord) {
            this.mTipWord = tipWord;
            return this;
        }

        @SuppressLint({"ResourceType"})
        public TipDialog create() {
            TipDialog dialog = new TipDialog(this.mContext);
            dialog.setContentView(R.layout.xcui_tip_dialog_layout);
            ViewGroup contentWrap = (ViewGroup) dialog.findViewById(R.id.contentWrap);
            if (this.mCurrentIconType == 1) {
                LoadingView loadingView = new LoadingView(this.mContext);
                loadingView.setColor(-1);
                loadingView.setSize(DisplayHelper.dp2px(this.mContext, 32));
                LinearLayout.LayoutParams loadingViewLP = new LinearLayout.LayoutParams(-2, -2);
                loadingView.setLayoutParams(loadingViewLP);
                contentWrap.addView(loadingView);
            } else if (this.mCurrentIconType == 2 || this.mCurrentIconType == 3 || this.mCurrentIconType == 4) {
                ImageView imageView = new ImageView(this.mContext);
                LinearLayout.LayoutParams imageViewLP = new LinearLayout.LayoutParams(-2, -2);
                imageView.setLayoutParams(imageViewLP);
                if (this.mCurrentIconType == 2) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.icon_notify_done));
                } else if (this.mCurrentIconType == 3) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.icon_notify_error));
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.icon_notify_info));
                }
                contentWrap.addView(imageView);
            }
            return dialog;
        }
    }
}
