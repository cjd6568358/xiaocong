package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PromptDialog extends Dialog implements View.OnClickListener {
    public View bodyView;
    public String btnHint;
    private TextView cancel;
    public int cancelColor;
    private View.OnClickListener cancelListener;
    private ImageView clean_txt;
    private TextView confirm;
    public int confirmColor;
    private View.OnClickListener confirmListener;
    public int dialogType;
    public String editHint;
    private EditText editText;
    public int editType;
    private TextView et_msg;
    public String hint;
    public View middleLine;
    public String msg;
    private TextView msg_view;
    private RelativeLayout rl_rename;
    public String title;
    public int titleColor;
    public int titleSize;
    private TextView title_view;

    public PromptDialog(Context context, int style) {
        super(context, style);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_prompt_layout);
        setCanceledOnTouchOutside(false);
        initLayout();
    }

    private void initLayout() {
        this.title_view = (TextView) findViewById(R.id.tv_prompt_title);
        this.middleLine = findViewById(R.id.prompt_middle_line);
        this.rl_rename = (RelativeLayout) findViewById(R.id.rl_prompt_rename);
        this.editText = (EditText) findViewById(R.id.et_prompt_device_name);
        this.clean_txt = (ImageView) findViewById(R.id.iv_prompt_clean_txt);
        this.msg_view = (TextView) findViewById(R.id.tv_prompt_msg);
        this.et_msg = (TextView) findViewById(R.id.et_prompt_msg);
        this.cancel = (TextView) findViewById(R.id.btn_prompt_cancel);
        if (this.cancelColor != 0) {
            this.cancel.setTextColor(getContext().getResources().getColor(this.cancelColor));
        }
        this.cancel.setOnClickListener(this);
        this.confirm = (TextView) findViewById(R.id.btn_prompt_confirm);
        this.confirm.setTextColor(getContext().getResources().getColor(this.confirmColor));
        this.confirm.setOnClickListener(this);
        if (!TextUtils.isEmpty(this.title)) {
            this.title_view.setText(this.title);
            this.title_view.setVisibility(0);
            if (this.titleColor != 0) {
                this.title_view.setTextColor(getContext().getResources().getColor(this.titleColor));
            }
            if (this.titleSize != 0) {
                this.title_view.setTextSize(this.titleSize);
            }
        }
        if (this.bodyView != null) {
            ViewGroup custom_view = (ViewGroup) findViewById(R.id.ll_prompt_custom_view);
            custom_view.addView(this.bodyView, -1, -2);
            custom_view.setVisibility(0);
        }
        if (this.dialogType == 1002) {
            this.rl_rename.setVisibility(0);
            this.clean_txt.setOnClickListener(this);
            this.et_msg.setText(this.msg);
            this.editText.setText(this.editHint);
            this.editText.setHint(this.hint);
            this.editText.setSelection(this.editText.getText().toString().length());
            if (this.editType == 0) {
                this.editText.setInputType(32);
            } else if (this.editType == 1) {
                this.editText.setInputType(3);
            } else if (this.editType == 2) {
                this.editText.setInputType(144);
            } else {
                this.editText.setInputType(1);
            }
            if (!TextUtils.isEmpty(this.et_msg.getText().toString())) {
                this.et_msg.setVisibility(0);
            } else {
                this.et_msg.setVisibility(8);
            }
        } else if (this.dialogType == 1001) {
            if (!TextUtils.isEmpty(this.msg)) {
                this.msg_view.setText(this.msg);
                this.msg_view.setVisibility(0);
            }
        } else if (this.dialogType == 1003) {
            if (!TextUtils.isEmpty(this.msg)) {
                this.msg_view.setText(this.msg);
                this.msg_view.setVisibility(0);
            }
            this.cancel.setVisibility(8);
            this.confirm.setText(this.btnHint);
        }
        this.msg_view.post(PromptDialog$$Lambda$1.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initLayout$0() {
        XcLogger.e("msg_view_line", Integer.valueOf(this.msg_view.getLineCount()));
        if (this.msg_view.getLineCount() <= 1) {
            this.msg_view.setGravity(17);
        } else {
            this.msg_view.setGravity(3);
        }
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setConfirmListener(View.OnClickListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public EditText getEditText() {
        return this.editText;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.btn_prompt_cancel /* 2131296327 */:
                if (this.cancelListener != null) {
                    this.cancelListener.onClick(arg0);
                } else {
                    dismiss();
                }
                break;
            case R.id.btn_prompt_confirm /* 2131296328 */:
                if (this.confirmListener != null) {
                    this.confirmListener.onClick(arg0);
                }
                break;
            case R.id.iv_prompt_clean_txt /* 2131296508 */:
                this.editText.setText(Constants.MAIN_VERSION_TAG);
                break;
        }
    }
}
