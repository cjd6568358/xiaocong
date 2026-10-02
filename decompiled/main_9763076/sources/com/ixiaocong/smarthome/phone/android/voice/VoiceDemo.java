package com.ixiaocong.smarthome.phone.android.voice;

import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class VoiceDemo extends XcBaseActivity implements View.OnClickListener {
    private ImageView mIvSpeek;
    private TextView mTvClose;
    private TextView mTvVoiceText;
    private TextView mTvVolumeText;

    protected int getLayoutId() {
        return R.layout.layout_voice_demo;
    }

    protected void initView() {
        this.mTvClose = (TextView) $(R.id.tv_voice_close);
        this.mTvVoiceText = (TextView) $(R.id.tv_voice_text);
        this.mTvVolumeText = (TextView) $(R.id.tv_volume_text);
        this.mIvSpeek = (ImageView) $(R.id.iv_voice_speek);
    }

    protected void initData() {
    }

    public void addListener() {
        super.addListener();
        this.mTvClose.setOnClickListener(this);
        this.mIvSpeek.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_voice_speek /* 2131296547 */:
                if (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.RECORD_AUDIO") != 0) {
                    ActivityCompat.requestPermissions(this.mActivity, new String[]{"android.permission.RECORD_AUDIO"}, 100);
                }
                break;
            case R.id.tv_voice_close /* 2131297121 */:
                finish();
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100 && grantResults[0] != 0) {
            ToastUtils.showShort(this.mActivity, "您当前未授权App访问录音功能,语音功能无法执行");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
