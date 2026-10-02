package com.ixiaocong.smarthome.phone.android.detail.activity.user;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.RenameRequestManager;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.RenameCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EditUserDataActivity extends XcBaseActivity implements View.OnClickListener, HintDialogCallback, RenameCallback {
    private int mCode;
    private String mDeviceId;
    private String mDeviceName;
    private EditText mEtNickname;
    private ImageView mIvBack;
    private String mNickname;
    private TextView mTvCommit;
    private TextView mTvTitle;

    protected int getLayoutId() {
        return R.layout.activity_edit_user_data;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvCommit = (TextView) $(R.id.right_titlebar_text);
        this.mEtNickname = (EditText) $(R.id.et_user_nick_name_edit);
        this.mTvTitle = (TextView) $(R.id.centertxt_titlebar);
    }

    protected void initData() {
        this.mCode = getIntent().getIntExtra("intentCode", 0);
        if (this.mCode == 1002 || this.mCode == 1001) {
            this.mDeviceName = getIntent().getStringExtra("deviceName");
            this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
            this.mTvTitle.setText("设备重命名");
            if (!TextUtils.isEmpty(this.mDeviceName)) {
                this.mEtNickname.setText(this.mDeviceName);
                this.mEtNickname.setSelection(this.mDeviceName.length());
                return;
            }
            return;
        }
        this.mNickname = getIntent().getStringExtra("userName");
        if (!TextUtils.isEmpty(this.mNickname)) {
            this.mEtNickname.setText(this.mNickname);
            this.mEtNickname.setSelection(this.mNickname.length());
            this.mTvTitle.setText("更改昵称");
        }
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvCommit.setOnClickListener(this);
        this.mEtNickname.addTextChangedListener(new TextWatcher() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.user.EditUserDataActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                if (this.mCode == 1002 || this.mCode == 1001) {
                    if (!this.mDeviceName.equals(this.mEtNickname.getText().toString().trim())) {
                        OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "设备重命名", "确定放弃当前设备名称修改吗?");
                    } else {
                        finish();
                    }
                } else if (!this.mNickname.equals(this.mEtNickname.getText().toString().trim())) {
                    OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "用户重命名", "确定放弃当前昵称修改吗?");
                } else {
                    finish();
                }
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                String commitName = this.mEtNickname.getText().toString().trim();
                if (this.mCode == 1002) {
                    if (TextUtils.isEmpty(commitName)) {
                        ToastUtils.showShort(this.mActivity, "设备名称不能为空");
                    } else if (!this.mDeviceName.equals(commitName)) {
                        RenameRequestManager.deviceRenameRequest(this.mActivity, this, this.mDeviceId, commitName);
                    } else {
                        finish();
                    }
                } else if (this.mCode == 1001) {
                    if (TextUtils.isEmpty(commitName)) {
                        ToastUtils.showShort(this.mActivity, "设备名称不能为空");
                    } else {
                        Intent intent = new Intent("renameParameterAction");
                        intent.putExtra("deviceName", commitName);
                        setResult(100, intent);
                        finish();
                    }
                } else {
                    if (TextUtils.isEmpty(commitName)) {
                        ToastUtils.showShort(this.mActivity, "用户昵称不能为空");
                    }
                    if (commitName.length() < 2 || commitName.length() > 18) {
                        ToastUtils.showShort(this.mActivity, "用户昵称长度为2～18位");
                    } else if (!this.mNickname.equals(commitName)) {
                        RenameRequestManager.userRenameRequest(this.mActivity, this, commitName);
                    } else {
                        finish();
                    }
                }
                break;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.RenameCallback
    public void userRenameListener(boolean isSuccess, String nickname) {
        if (isSuccess) {
            Intent intent = new Intent();
            intent.putExtra("userName", nickname);
            setResult(100, intent);
            finish();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.RenameCallback
    public void deviceRenameListener(boolean isSuccess, String deviceName) {
        if (isSuccess) {
            Intent intent = new Intent();
            intent.putExtra("deviceName", deviceName);
            setResult(100, intent);
            finish();
        }
    }

    public void onBackPressed() {
        String nickName = this.mEtNickname.getText().toString().trim();
        if (!TextUtils.isEmpty(nickName)) {
            if (this.mCode == 1002 || this.mCode == 1001) {
                if (!nickName.equals(this.mDeviceName)) {
                    OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "设备重命名", "确定放弃当前设备名称修改吗?");
                    return;
                }
            } else if (!nickName.equals(this.mNickname)) {
                OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "用户重命名", "确定放弃当前昵称修改吗?");
                return;
            }
        }
        super.onBackPressed();
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        if (isSuccess) {
            finish();
        }
    }
}
