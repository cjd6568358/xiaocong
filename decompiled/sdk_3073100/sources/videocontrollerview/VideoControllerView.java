package videocontrollerview;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.baiducam.bdplayer.R;
import java.util.Formatter;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class VideoControllerView extends FrameLayout implements VideoGestureListener {
    private ViewGroup mAnchorView;
    private AudioManager mAudioManager;
    private ImageButton mBackButton;
    private View.OnClickListener mBackListener;
    private View mBottomLayout;
    private boolean mCanControlBrightness;
    private boolean mCanControlVolume;
    private boolean mCanSeekVideo;
    private ImageView mCenterImage;
    private View mCenterLayout;
    private ProgressBar mCenterProgress;
    private Activity mContext;
    private float mCurBrightness;
    private int mCurVolume;
    private TextView mCurrentTime;
    private TextView mEndTime;
    private int mExitIcon;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private ImageButton mFullscreenButton;
    private View.OnClickListener mFullscreenListener;
    private GestureDetector mGestureDetector;
    private Handler mHandler;
    private boolean mIsDragging;
    private boolean mIsShowing;
    private int mMaxVolume;
    private MediaPlayerControlListener mMediaPlayerControlListener;
    private ImageButton mPauseButton;
    private int mPauseIcon;
    private View.OnClickListener mPauseListener;
    private int mPlayIcon;
    private View mRootView;
    private SeekBar mSeekBar;
    private SeekBar.OnSeekBarChangeListener mSeekListener;
    private int mShrinkIcon;
    private int mStretchIcon;
    private TextView mTitleText;
    private View mTopLayout;
    private String mVideoTitle;

    public interface MediaPlayerControlListener {
        int getBufferPercentage();

        int getCurrentPosition();

        int getDuration();

        boolean isComplete();

        boolean isFullScreen();

        boolean isPlaying();

        void seekTo(int i);
    }

    private View makeControllerView() {
        LayoutInflater inflate = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        this.mRootView = inflate.inflate(R.layout.media_controller, (ViewGroup) null);
        initControllerView();
        return this.mRootView;
    }

    private void initControllerView() {
        this.mTopLayout = this.mRootView.findViewById(R.id.layout_top);
        this.mBackButton = (ImageButton) this.mRootView.findViewById(R.id.top_back);
        this.mBackButton.setImageResource(this.mExitIcon);
        if (this.mBackButton != null) {
            this.mBackButton.requestFocus();
            this.mBackButton.setOnClickListener(this.mBackListener);
        }
        this.mTitleText = (TextView) this.mRootView.findViewById(R.id.top_title);
        this.mCenterLayout = this.mRootView.findViewById(R.id.layout_center);
        this.mCenterLayout.setVisibility(8);
        this.mCenterImage = (ImageView) this.mRootView.findViewById(R.id.image_center_bg);
        this.mCenterProgress = (ProgressBar) this.mRootView.findViewById(R.id.progress_center);
        this.mBottomLayout = this.mRootView.findViewById(R.id.layout_bottom);
        this.mPauseButton = (ImageButton) this.mRootView.findViewById(R.id.bottom_pause);
        if (this.mPauseButton != null) {
            this.mPauseButton.requestFocus();
            this.mPauseButton.setOnClickListener(this.mPauseListener);
        }
        this.mFullscreenButton = (ImageButton) this.mRootView.findViewById(R.id.bottom_fullscreen);
        if (this.mFullscreenButton != null) {
            this.mFullscreenButton.requestFocus();
            this.mFullscreenButton.setOnClickListener(this.mFullscreenListener);
        }
        this.mSeekBar = (SeekBar) this.mRootView.findViewById(R.id.bottom_seekbar);
        if (this.mSeekBar != null) {
            this.mSeekBar.setOnSeekBarChangeListener(this.mSeekListener);
            this.mSeekBar.setMax(1000);
        }
        this.mEndTime = (TextView) this.mRootView.findViewById(R.id.bottom_time);
        this.mCurrentTime = (TextView) this.mRootView.findViewById(R.id.bottom_time_current);
        this.mFormatBuilder = new StringBuilder();
        this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
    }

    private void show() {
        if (!this.mIsShowing && this.mAnchorView != null) {
            FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(-1, -2);
            this.mAnchorView.addView(this, tlp);
            ViewAnimator.putOn(this.mTopLayout).waitForSize(new ViewAnimator.Listeners.Size() { // from class: videocontrollerview.VideoControllerView.2
                @Override // videocontrollerview.ViewAnimator.Listeners.Size
                public void onSize(ViewAnimator viewAnimator) {
                    viewAnimator.animate().translationY(-VideoControllerView.this.mTopLayout.getHeight(), 0.0f).duration(300L).andAnimate(VideoControllerView.this.mBottomLayout).translationY(VideoControllerView.this.mBottomLayout.getHeight(), 0.0f).duration(300L).start(new ViewAnimator.Listeners.Start() { // from class: videocontrollerview.VideoControllerView.2.1
                        @Override // videocontrollerview.ViewAnimator.Listeners.Start
                        public void onStart() {
                            VideoControllerView.this.mIsShowing = true;
                            VideoControllerView.this.mHandler.sendEmptyMessage(2);
                        }
                    });
                }
            });
        }
        setSeekProgress();
        if (this.mPauseButton != null) {
            this.mPauseButton.requestFocus();
        }
        togglePausePlay();
        toggleFullScreen();
        this.mHandler.sendEmptyMessage(2);
    }

    public void toggleControllerView() {
        if (!isShowing()) {
            show();
            return;
        }
        Message msg = this.mHandler.obtainMessage(1);
        this.mHandler.removeMessages(1);
        this.mHandler.sendMessageDelayed(msg, 100L);
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    private String stringToTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        this.mFormatBuilder.setLength(0);
        return hours > 0 ? this.mFormatter.format("%d:%02d:%02d", Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)).toString() : this.mFormatter.format("%02d:%02d", Integer.valueOf(minutes), Integer.valueOf(seconds)).toString();
    }

    private int setSeekProgress() {
        if (this.mMediaPlayerControlListener == null || this.mIsDragging) {
            return 0;
        }
        int position = this.mMediaPlayerControlListener.getCurrentPosition();
        int duration = this.mMediaPlayerControlListener.getDuration();
        if (this.mSeekBar != null) {
            if (duration > 0) {
                long pos = (1000 * ((long) position)) / ((long) duration);
                this.mSeekBar.setProgress((int) pos);
            }
            int percent = this.mMediaPlayerControlListener.getBufferPercentage();
            this.mSeekBar.setSecondaryProgress(percent * 10);
        }
        if (this.mEndTime != null) {
            this.mEndTime.setText(stringToTime(duration));
        }
        if (this.mCurrentTime != null) {
            Log.e("VideoControllerView", "position:" + position + " -> duration:" + duration);
            this.mCurrentTime.setText(stringToTime(position));
            if (this.mMediaPlayerControlListener.isComplete()) {
                this.mCurrentTime.setText(stringToTime(duration));
            }
        }
        this.mTitleText.setText(this.mVideoTitle);
        return position;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 1:
                this.mCurVolume = -1;
                this.mCurBrightness = -1.0f;
                this.mCenterLayout.setVisibility(8);
                break;
        }
        if (this.mGestureDetector != null) {
            this.mGestureDetector.onTouchEvent(event);
            return true;
        }
        return true;
    }

    private void togglePausePlay() {
        if (this.mRootView != null && this.mPauseButton != null && this.mMediaPlayerControlListener != null) {
            if (this.mMediaPlayerControlListener.isPlaying()) {
                this.mPauseButton.setImageResource(this.mPauseIcon);
            } else {
                this.mPauseButton.setImageResource(this.mPlayIcon);
            }
        }
    }

    public void toggleFullScreen() {
        if (this.mRootView != null && this.mFullscreenButton != null && this.mMediaPlayerControlListener != null) {
            if (this.mMediaPlayerControlListener.isFullScreen()) {
                this.mFullscreenButton.setImageResource(this.mShrinkIcon);
            } else {
                this.mFullscreenButton.setImageResource(this.mStretchIcon);
            }
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        if (this.mPauseButton != null) {
            this.mPauseButton.setEnabled(enabled);
        }
        if (this.mSeekBar != null) {
            this.mSeekBar.setEnabled(enabled);
        }
        super.setEnabled(enabled);
    }

    public void setMediaPlayerControlListener(MediaPlayerControlListener mediaPlayerListener) {
        this.mMediaPlayerControlListener = mediaPlayerListener;
        togglePausePlay();
        toggleFullScreen();
    }

    private void setAnchorView(ViewGroup view) {
        this.mAnchorView = view;
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(-1, -2);
        removeAllViews();
        View v = makeControllerView();
        addView(v, frameParams);
        setGestureListener();
    }

    private void setGestureListener() {
        if (this.mCanControlVolume) {
            this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
            this.mMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
        }
        this.mGestureDetector = new GestureDetector(this.mContext, new ViewGestureListener(this.mContext, this));
    }

    @Override // videocontrollerview.VideoGestureListener
    public void onSingleTap() {
        toggleControllerView();
    }

    @Override // videocontrollerview.VideoGestureListener
    public void onHorizontalScroll(boolean seekForward) {
        if (this.mCanSeekVideo) {
            if (seekForward) {
                seekForWard();
            } else {
                seekBackWard();
            }
        }
    }

    private void seekBackWard() {
        if (this.mMediaPlayerControlListener != null) {
            int pos = this.mMediaPlayerControlListener.getCurrentPosition();
            this.mMediaPlayerControlListener.seekTo((int) (((long) pos) - 500));
            setSeekProgress();
            show();
        }
    }

    private void seekForWard() {
        if (this.mMediaPlayerControlListener != null) {
            int pos = this.mMediaPlayerControlListener.getCurrentPosition();
            this.mMediaPlayerControlListener.seekTo((int) (((long) pos) + 500));
            setSeekProgress();
            show();
        }
    }

    @Override // videocontrollerview.VideoGestureListener
    public void onVerticalScroll(float percent, int direction) {
        if (direction == 1) {
            if (this.mCanControlBrightness) {
                this.mCenterImage.setImageResource(R.drawable.video_bright_bg);
                updateBrightness(percent);
                return;
            }
            return;
        }
        if (this.mCanControlVolume) {
            this.mCenterImage.setImageResource(R.drawable.video_volume_bg);
            updateVolume(percent);
        }
    }

    private void updateVolume(float percent) {
        this.mCenterLayout.setVisibility(0);
        if (this.mCurVolume == -1) {
            this.mCurVolume = this.mAudioManager.getStreamVolume(3);
            if (this.mCurVolume < 0) {
                this.mCurVolume = 0;
            }
        }
        int volume = ((int) (this.mMaxVolume * percent)) + this.mCurVolume;
        if (volume > this.mMaxVolume) {
            volume = this.mMaxVolume;
        }
        if (volume < 0) {
            volume = 0;
        }
        this.mAudioManager.setStreamVolume(3, volume, 0);
        int progress = (volume * 100) / this.mMaxVolume;
        this.mCenterProgress.setProgress(progress);
    }

    private void updateBrightness(float percent) {
        if (this.mCurBrightness == -1.0f) {
            this.mCurBrightness = this.mContext.getWindow().getAttributes().screenBrightness;
            if (this.mCurBrightness <= 0.01f) {
                this.mCurBrightness = 0.01f;
            }
        }
        this.mCenterLayout.setVisibility(0);
        WindowManager.LayoutParams attributes = this.mContext.getWindow().getAttributes();
        attributes.screenBrightness = this.mCurBrightness + percent;
        if (attributes.screenBrightness >= 1.0f) {
            attributes.screenBrightness = 1.0f;
        } else if (attributes.screenBrightness <= 0.01f) {
            attributes.screenBrightness = 0.01f;
        }
        this.mContext.getWindow().setAttributes(attributes);
        float p = attributes.screenBrightness * 100.0f;
        this.mCenterProgress.setProgress((int) p);
    }
}
