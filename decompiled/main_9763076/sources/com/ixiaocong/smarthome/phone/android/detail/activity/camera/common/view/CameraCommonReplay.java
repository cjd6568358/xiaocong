package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.cloud.media.player.IMediaPlayer;
import com.baiducam.bdplayer.widget.BDCloudVideoView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import videocontrollerview.VideoControllerView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CameraCommonReplay extends ConstraintLayout implements VideoControllerView.MediaPlayerControlListener {
    private boolean isFullScreen;
    private Activity mActivity;
    private CheckBox mCbControl;
    private CheckBox mCbScreen;
    private Context mContext;
    private boolean mIsComplete;
    private ImageView mIvBack;
    private ImageView mIvScreenBack;
    private OrientationEventListener mOrientationEventListener;
    private int mPosition;
    private int mScreenChange;
    private TextView mTvTitle;
    private CommonTypeCallback mTypeCallback;
    private BDCloudVideoView mVideoView;
    private ViewGroup.LayoutParams mVideolayoutParams;

    public CameraCommonReplay(Context context) {
        super(context);
        this.isFullScreen = false;
        this.mPosition = -1;
        this.mContext = context;
        initCommonVideo();
    }

    public CameraCommonReplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isFullScreen = false;
        this.mPosition = -1;
        this.mContext = context;
        initCommonVideo();
    }

    private void initCommonVideo() {
        LayoutInflater.from(this.mContext).inflate(R.layout.layout_common_camera_replay, this);
        this.mTvTitle = (TextView) findViewById(R.id.centertxt_titlebar);
        this.mIvBack = (ImageView) findViewById(R.id.left_titlebar_image);
        this.mIvScreenBack = (ImageView) findViewById(R.id.iv_common_camera_replay_screen_back);
        this.mCbScreen = (CheckBox) findViewById(R.id.cb_common_camera_replay_vertical_screen_switch);
        this.mCbControl = (CheckBox) findViewById(R.id.cb_common_camera_replay_control);
        this.mVideoView = (BDCloudVideoView) findViewById(R.id.bdcvv_common_camera_replay_videoview);
        this.mVideolayoutParams = this.mVideoView.getLayoutParams();
        initControl();
        addListener();
    }

    private void initControl() {
        this.mVideoView.setVideoScalingMode(3);
    }

    public void initReplay(Activity activity, String title) {
        this.mActivity = activity;
        this.mTvTitle.setText(title);
        oreationChange();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        XcLogger.e("loadLiveUrl", "onConfigurationChanged: " + this.isFullScreen);
        if (!this.isFullScreen) {
        }
        this.isFullScreen = !this.isFullScreen;
    }

    public void setVisibleActionIcon(boolean isVisible) {
        if (isVisible) {
            this.mCbControl.setVisibility(0);
            this.mCbScreen.setVisibility(0);
        } else {
            this.mCbScreen.setVisibility(8);
            this.mCbControl.setVisibility(8);
        }
    }

    private void setVerticalScreen() {
        WindowManager.LayoutParams params = this.mActivity.getWindow().getAttributes();
        params.flags &= -1025;
        this.mActivity.getWindow().setAttributes(params);
        this.mActivity.getWindow().clearFlags(WXMediaMessage.TITLE_LENGTH_LIMIT);
        this.mVideoView.setLayoutParams(this.mVideolayoutParams);
        this.mIvScreenBack.setVisibility(8);
    }

    private void setFullScreen() {
        WindowManager.LayoutParams params = this.mActivity.getWindow().getAttributes();
        params.flags |= WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
        this.mActivity.getWindow().setAttributes(params);
        this.mActivity.getWindow().addFlags(WXMediaMessage.TITLE_LENGTH_LIMIT);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(-1, -1);
        this.mVideoView.setLayoutParams(layoutParams);
        this.mIvScreenBack.setVisibility(0);
    }

    private void oreationChange() {
        this.mOrientationEventListener = new OrientationEventListener(this.mContext) { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonReplay.1
            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int orientation) {
                if (orientation != -1) {
                    try {
                        CameraCommonReplay.this.mScreenChange = Settings.System.getInt(CameraCommonReplay.this.mContext.getContentResolver(), "accelerometer_rotation");
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (CameraCommonReplay.this.mScreenChange == 1) {
                        if (orientation > 350 || orientation < 10) {
                            if (CameraCommonReplay.this.isFullScreen) {
                                CameraCommonReplay.this.mActivity.setRequestedOrientation(1);
                                CameraCommonReplay.this.mCbScreen.setChecked(true);
                                return;
                            }
                            return;
                        }
                        if (orientation > 80 && orientation < 100) {
                            if (!CameraCommonReplay.this.isFullScreen) {
                                CameraCommonReplay.this.mActivity.setRequestedOrientation(8);
                                CameraCommonReplay.this.mCbScreen.setChecked(false);
                                return;
                            }
                            return;
                        }
                        if ((orientation <= 170 || orientation >= 190) && orientation > 260 && orientation < 280 && !CameraCommonReplay.this.isFullScreen) {
                            CameraCommonReplay.this.mActivity.setRequestedOrientation(0);
                            CameraCommonReplay.this.mCbScreen.setChecked(false);
                        }
                    }
                }
            }
        };
        this.mOrientationEventListener.enable();
    }

    public void replayVideo(String playUrl, int position) {
        this.mPosition = position;
        this.mVideoView.setVideoPath(playUrl);
        start();
    }

    private void addListener() {
        this.mCbControl.setOnCheckedChangeListener(CameraCommonReplay$$Lambda$1.lambdaFactory$(this));
        this.mCbScreen.setOnCheckedChangeListener(CameraCommonReplay$$Lambda$2.lambdaFactory$(this));
        this.mIvBack.setOnClickListener(CameraCommonReplay$$Lambda$3.lambdaFactory$(this));
        this.mIvScreenBack.setOnClickListener(CameraCommonReplay$$Lambda$4.lambdaFactory$(this));
        this.mVideoView.setOnCompletionListener(CameraCommonReplay$$Lambda$5.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            this.mVideoView.start();
            this.mTypeCallback.resultTypeCalllback(-1);
        } else {
            this.mVideoView.pause();
            this.mTypeCallback.resultTypeCalllback(-2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$1(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            this.mActivity.setRequestedOrientation(1);
            setVerticalScreen();
            this.mCbScreen.setChecked(true);
            this.mCbScreen.setVisibility(0);
            return;
        }
        this.mActivity.setRequestedOrientation(0);
        setFullScreen();
        this.mCbScreen.setChecked(false);
        this.mCbScreen.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$2(View v) {
        this.mActivity.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$3(View v) {
        this.mActivity.setRequestedOrientation(1);
        setVerticalScreen();
        this.mCbScreen.setChecked(true);
        this.mCbScreen.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$4(IMediaPlayer iMediaPlayer) {
        XcLogger.e("loadLiveUrl", "播发完成监听");
        if (this.mTypeCallback != null) {
            this.mTypeCallback.resultTypeCalllback(this.mPosition);
        }
    }

    public void start() {
        if (this.mVideoView != null) {
            this.mVideoView.start();
            this.mIsComplete = false;
            this.mCbControl.setChecked(true);
            this.mTypeCallback.resultTypeCalllback(-1);
            XcLogger.e("loadLiveUrl", MessageKey.MSG_ACCEPT_TIME_START);
        }
    }

    public void pause() {
        if (this.mVideoView != null) {
            this.mVideoView.pause();
            this.mCbControl.setChecked(false);
            this.mTypeCallback.resultTypeCalllback(-2);
            XcLogger.e("loadLiveUrl", "pause");
        }
    }

    public int getDuration() {
        XcLogger.e("loadLiveUrl", Integer.valueOf(new StringBuilder().append("duration").append(this.mVideoView).toString() != null ? this.mVideoView.getDuration() : 0));
        if (this.mVideoView != null) {
            return this.mVideoView.getDuration();
        }
        return 0;
    }

    public int getCurrentPosition() {
        XcLogger.e("loadLiveUrl", Integer.valueOf(new StringBuilder().append("currentPosition").append(this.mVideoView).toString() != null ? this.mVideoView.getCurrentPosition() : 0));
        if (this.mVideoView != null) {
            return this.mVideoView.getCurrentPosition();
        }
        return 0;
    }

    public void seekTo(int position) {
        if (this.mVideoView != null) {
            XcLogger.e("loadLiveUrl", "seekTo---" + position);
            this.mVideoView.seekTo(position);
        }
    }

    public boolean isPlaying() {
        XcLogger.e("loadLiveUrl", Boolean.valueOf(new StringBuilder().append("isPlaying---").append(this.mVideoView).toString() != null && this.mVideoView.isPlaying()));
        return this.mVideoView != null && this.mVideoView.isPlaying();
    }

    public boolean isComplete() {
        XcLogger.e("loadLiveUrl", "isComplete---" + this.mIsComplete);
        return this.mIsComplete;
    }

    public int getBufferPercentage() {
        XcLogger.e("loadLiveUrl", "getBufferPercentage----0");
        return 0;
    }

    public boolean isFullScreen() {
        return this.mActivity.getRequestedOrientation() == 0;
    }

    public void setTypeCallback(CommonTypeCallback typeCallback) {
        this.mTypeCallback = typeCallback;
    }

    public void onDestory() {
        if (this.mVideoView != null) {
            this.mVideoView.stopPlayback();
        }
        if (this.mOrientationEventListener != null) {
            this.mOrientationEventListener.disable();
        }
    }

    public void onBack() {
        if (isFullScreen()) {
            this.mActivity.setRequestedOrientation(1);
            this.mCbScreen.setChecked(true);
        }
    }
}
