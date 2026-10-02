package com.baiducam.bdplayer.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.baidu.cloud.media.player.IMediaPlayer;
import com.baiducam.bdplayer.R;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BDCloudVideoView extends FrameLayout implements MediaController.MediaPlayerControl {
    private RelativeLayout cachingHintViewRl;
    private ProgressBar cachingProgressBar;
    private TextView cachingProgressHint;
    private boolean isTryToPlaying;
    private AnimationDrawable mAniDrawable;
    private Context mAppContext;
    private int mBufferSizeInBytes;
    private IMediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener;
    private int mCacheTimeInMilliSeconds;
    private boolean mCanPause;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;
    private IMediaPlayer.OnCompletionListener mCompletionListener;
    private int mCurrentAspectRatio;
    private int mCurrentBufferPercentage;
    private PlayerState mCurrentState;
    private int mDecodeMode;
    private String mDrmToken;
    private IMediaPlayer.OnErrorListener mErrorListener;
    private Map<String, String> mHeaders;
    private IMediaPlayer.OnInfoListener mInfoListener;
    private long mInitPlayPositionInMilliSec;
    private ImageView mIvPiont;
    private float mLeftVolume;
    private boolean mLogEnabled;
    private boolean mLooping;
    private int mMaxCacheSizeInBytes;
    private int mMaxProbeSizeInBytes;
    private int mMaxProbeTimeInMs;
    private BDCloudMediaPlayer mMediaPlayer;
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private IMediaPlayer.OnCompletionListener mOnCompletionListener;
    private IMediaPlayer.OnErrorListener mOnErrorListener;
    private IMediaPlayer.OnInfoListener mOnInfoListener;
    private OnPlayerStateListener mOnPlayerStateListener;
    private IMediaPlayer.OnPreparedListener mOnPreparedListener;
    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
    IMediaPlayer.OnPreparedListener mPreparedListener;
    private IRenderView mRenderView;
    private float mRightVolume;
    IRenderView.IRenderCallback mSHCallback;
    private IMediaPlayer.OnSeekCompleteListener mSeekCompleteListener;
    IMediaPlayer.OnVideoSizeChangedListener mSizeChangedListener;
    private int mSurfaceHeight;
    private IRenderView.ISurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth;
    private Uri mUri;
    private boolean mUseApmDetect;
    private boolean mUseTextureViewFirst;
    private int mVideoHeight;
    private int mVideoRotationDegree;
    private int mVideoSarDen;
    private int mVideoSarNum;
    private int mVideoWidth;
    private int mWakeMode;
    private Handler mainThreadHandler;
    private boolean mbShowCacheInfo;
    private RelativeLayout renderRootView;

    public interface OnPlayerStateListener {
        void onPlayerStateChanged(PlayerState playerState);
    }

    public enum PlayerState {
        STATE_ERROR(-1),
        STATE_IDLE(0),
        STATE_PREPARING(1),
        STATE_PREPARED(2),
        STATE_PLAYING(3),
        STATE_PAUSED(4),
        STATE_PLAYBACK_COMPLETED(5);

        private int code;

        PlayerState(int oCode) {
            this.code = oCode;
        }
    }

    public PlayerState getCurrentPlayerState() {
        return this.mCurrentState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentState(PlayerState newState) {
        if (this.mCurrentState != newState) {
            this.mCurrentState = newState;
            if (this.mOnPlayerStateListener != null) {
                this.mOnPlayerStateListener.onPlayerStateChanged(this.mCurrentState);
            }
        }
    }

    public void setOnPlayerStateListener(OnPlayerStateListener listener) {
        this.mOnPlayerStateListener = listener;
    }

    public BDCloudVideoView(Context context) {
        super(context);
        this.mUseTextureViewFirst = true;
        this.mCurrentState = PlayerState.STATE_IDLE;
        this.isTryToPlaying = false;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mCanPause = true;
        this.mCanSeekBack = true;
        this.mCanSeekForward = true;
        this.mDrmToken = null;
        this.mCurrentAspectRatio = 0;
        this.mCacheTimeInMilliSeconds = 0;
        this.mbShowCacheInfo = true;
        this.mDecodeMode = 0;
        this.mLogEnabled = false;
        this.mInitPlayPositionInMilliSec = 0L;
        this.mWakeMode = 0;
        this.mLeftVolume = -1.0f;
        this.mRightVolume = -1.0f;
        this.mUseApmDetect = false;
        this.mMaxProbeTimeInMs = 0;
        this.mMaxProbeSizeInBytes = 0;
        this.mMaxCacheSizeInBytes = 0;
        this.mLooping = false;
        this.mBufferSizeInBytes = 0;
        this.cachingHintViewRl = null;
        this.cachingProgressBar = null;
        this.cachingProgressHint = null;
        this.renderRootView = null;
        this.mainThreadHandler = new Handler(Looper.getMainLooper()) { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    BDCloudVideoView.this.setCachingHintViewVisibility(msg.arg1 == 1);
                }
            }
        };
        this.mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.2
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
                Log.d("BDCloudVideoView", "onVideoSizeChanged width=" + width + ";height=" + height + ";sarNum=" + sarNum + ";sarDen=" + sarDen);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                BDCloudVideoView.this.mVideoSarNum = mp.getVideoSarNum();
                BDCloudVideoView.this.mVideoSarDen = mp.getVideoSarDen();
                if (BDCloudVideoView.this.mVideoWidth != 0 && BDCloudVideoView.this.mVideoHeight != 0) {
                    if (BDCloudVideoView.this.mRenderView != null) {
                        BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                        BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    }
                    BDCloudVideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.3
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnPreparedListener
            public void onPrepared(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onPrepared");
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PREPARED);
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                if (BDCloudVideoView.this.mOnPreparedListener != null) {
                    BDCloudVideoView.this.mOnPreparedListener.onPrepared(BDCloudVideoView.this.mMediaPlayer);
                }
                Log.d("BDCloudVideoView", "onPrepared: mVideoWidth=" + BDCloudVideoView.this.mVideoWidth + ";mVideoHeight=" + BDCloudVideoView.this.mVideoHeight + ";mSurfaceWidth=" + BDCloudVideoView.this.mSurfaceWidth + ";mSurfaceHeight=" + BDCloudVideoView.this.mSurfaceHeight);
                if (BDCloudVideoView.this.mVideoWidth == 0 || BDCloudVideoView.this.mVideoHeight == 0) {
                    if (BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                } else if (BDCloudVideoView.this.mRenderView != null) {
                    BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                    BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    if ((!BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mSurfaceWidth == BDCloudVideoView.this.mVideoWidth && BDCloudVideoView.this.mSurfaceHeight == BDCloudVideoView.this.mVideoHeight)) && BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                }
            }
        };
        this.mCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.4
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnCompletionListener
            public void onCompletion(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onCompletion");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PLAYBACK_COMPLETED);
                BDCloudVideoView.this.isTryToPlaying = false;
                if (BDCloudVideoView.this.mOnCompletionListener != null) {
                    BDCloudVideoView.this.mOnCompletionListener.onCompletion(BDCloudVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.5
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnInfoListener
            public boolean onInfo(IMediaPlayer mp, int arg1, int arg2) {
                Log.d("BDCloudVideoView", "onInfo: arg1=" + arg1 + "; arg2=" + arg2);
                if (BDCloudVideoView.this.mOnInfoListener != null) {
                    BDCloudVideoView.this.mOnInfoListener.onInfo(mp, arg1, arg2);
                }
                switch (arg1) {
                    case 3:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_RENDERING_START:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING /* 700 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START /* 701 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_START:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(true);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END /* 702 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_END:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH /* 703 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NETWORK_BANDWIDTH: " + arg2);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING /* 800 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BAD_INTERLEAVING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE /* 801 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NOT_SEEKABLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE /* 802 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_METADATA_UPDATE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE /* 901 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT /* 902 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                        return true;
                    case 10001:
                        BDCloudVideoView.this.mVideoRotationDegree = arg2;
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + arg2);
                        if (BDCloudVideoView.this.mRenderView != null) {
                            BDCloudVideoView.this.mRenderView.setVideoRotation(arg2);
                        }
                        return true;
                    case 10002:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_AUDIO_RENDERING_START:");
                        return true;
                    default:
                        return true;
                }
            }
        };
        this.mErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.6
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnErrorListener
            public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
                Log.d("BDCloudVideoView", "onError: " + framework_err + "," + impl_err);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_ERROR);
                BDCloudVideoView.this.isTryToPlaying = false;
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnErrorListener == null || BDCloudVideoView.this.mOnErrorListener.onError(BDCloudVideoView.this.mMediaPlayer, framework_err, impl_err)) {
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.7
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                BDCloudVideoView.this.mCurrentBufferPercentage = percent;
                if (BDCloudVideoView.this.mOnBufferingUpdateListener != null) {
                    BDCloudVideoView.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
                }
            }
        };
        this.mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.8
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onSeekComplete");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnSeekCompleteListener != null) {
                    BDCloudVideoView.this.mOnSeekCompleteListener.onSeekComplete(mp);
                }
            }
        };
        this.mSHCallback = new IRenderView.IRenderCallback() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.9
            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceChanged(IRenderView.ISurfaceHolder holder, int format, int w, int h) {
                Log.d("BDCloudVideoView", "mSHCallback onSurfaceChanged");
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceWidth = w;
                    BDCloudVideoView.this.mSurfaceHeight = h;
                    boolean isValidState = BDCloudVideoView.this.isTryToPlaying;
                    boolean hasValidSize = !BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mVideoWidth == w && BDCloudVideoView.this.mVideoHeight == h);
                    if (BDCloudVideoView.this.mMediaPlayer != null && isValidState && hasValidSize) {
                        BDCloudVideoView.this.start();
                        return;
                    }
                    return;
                }
                Log.e("BDCloudVideoView", "onSurfaceChanged: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceCreated(IRenderView.ISurfaceHolder holder, int width, int height) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = holder;
                    if (BDCloudVideoView.this.mMediaPlayer != null) {
                        BDCloudVideoView.this.bindSurfaceHolder(BDCloudVideoView.this.mMediaPlayer, holder);
                        return;
                    } else {
                        BDCloudVideoView.this.openVideo();
                        return;
                    }
                }
                Log.e("BDCloudVideoView", "onSurfaceCreated: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceDestroyed(IRenderView.ISurfaceHolder holder) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = null;
                    BDCloudVideoView.this.releaseWithoutStop();
                } else {
                    Log.e("BDCloudVideoView", "onSurfaceDestroyed: unmatched render callback\n");
                }
            }
        };
        initVideoView(context);
    }

    public BDCloudVideoView(Context context, boolean useTextureViewFirst) {
        super(context);
        this.mUseTextureViewFirst = true;
        this.mCurrentState = PlayerState.STATE_IDLE;
        this.isTryToPlaying = false;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mCanPause = true;
        this.mCanSeekBack = true;
        this.mCanSeekForward = true;
        this.mDrmToken = null;
        this.mCurrentAspectRatio = 0;
        this.mCacheTimeInMilliSeconds = 0;
        this.mbShowCacheInfo = true;
        this.mDecodeMode = 0;
        this.mLogEnabled = false;
        this.mInitPlayPositionInMilliSec = 0L;
        this.mWakeMode = 0;
        this.mLeftVolume = -1.0f;
        this.mRightVolume = -1.0f;
        this.mUseApmDetect = false;
        this.mMaxProbeTimeInMs = 0;
        this.mMaxProbeSizeInBytes = 0;
        this.mMaxCacheSizeInBytes = 0;
        this.mLooping = false;
        this.mBufferSizeInBytes = 0;
        this.cachingHintViewRl = null;
        this.cachingProgressBar = null;
        this.cachingProgressHint = null;
        this.renderRootView = null;
        this.mainThreadHandler = new Handler(Looper.getMainLooper()) { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    BDCloudVideoView.this.setCachingHintViewVisibility(msg.arg1 == 1);
                }
            }
        };
        this.mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.2
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
                Log.d("BDCloudVideoView", "onVideoSizeChanged width=" + width + ";height=" + height + ";sarNum=" + sarNum + ";sarDen=" + sarDen);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                BDCloudVideoView.this.mVideoSarNum = mp.getVideoSarNum();
                BDCloudVideoView.this.mVideoSarDen = mp.getVideoSarDen();
                if (BDCloudVideoView.this.mVideoWidth != 0 && BDCloudVideoView.this.mVideoHeight != 0) {
                    if (BDCloudVideoView.this.mRenderView != null) {
                        BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                        BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    }
                    BDCloudVideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.3
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnPreparedListener
            public void onPrepared(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onPrepared");
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PREPARED);
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                if (BDCloudVideoView.this.mOnPreparedListener != null) {
                    BDCloudVideoView.this.mOnPreparedListener.onPrepared(BDCloudVideoView.this.mMediaPlayer);
                }
                Log.d("BDCloudVideoView", "onPrepared: mVideoWidth=" + BDCloudVideoView.this.mVideoWidth + ";mVideoHeight=" + BDCloudVideoView.this.mVideoHeight + ";mSurfaceWidth=" + BDCloudVideoView.this.mSurfaceWidth + ";mSurfaceHeight=" + BDCloudVideoView.this.mSurfaceHeight);
                if (BDCloudVideoView.this.mVideoWidth == 0 || BDCloudVideoView.this.mVideoHeight == 0) {
                    if (BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                } else if (BDCloudVideoView.this.mRenderView != null) {
                    BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                    BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    if ((!BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mSurfaceWidth == BDCloudVideoView.this.mVideoWidth && BDCloudVideoView.this.mSurfaceHeight == BDCloudVideoView.this.mVideoHeight)) && BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                }
            }
        };
        this.mCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.4
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnCompletionListener
            public void onCompletion(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onCompletion");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PLAYBACK_COMPLETED);
                BDCloudVideoView.this.isTryToPlaying = false;
                if (BDCloudVideoView.this.mOnCompletionListener != null) {
                    BDCloudVideoView.this.mOnCompletionListener.onCompletion(BDCloudVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.5
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnInfoListener
            public boolean onInfo(IMediaPlayer mp, int arg1, int arg2) {
                Log.d("BDCloudVideoView", "onInfo: arg1=" + arg1 + "; arg2=" + arg2);
                if (BDCloudVideoView.this.mOnInfoListener != null) {
                    BDCloudVideoView.this.mOnInfoListener.onInfo(mp, arg1, arg2);
                }
                switch (arg1) {
                    case 3:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_RENDERING_START:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING /* 700 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START /* 701 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_START:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(true);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END /* 702 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_END:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH /* 703 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NETWORK_BANDWIDTH: " + arg2);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING /* 800 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BAD_INTERLEAVING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE /* 801 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NOT_SEEKABLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE /* 802 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_METADATA_UPDATE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE /* 901 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT /* 902 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                        return true;
                    case 10001:
                        BDCloudVideoView.this.mVideoRotationDegree = arg2;
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + arg2);
                        if (BDCloudVideoView.this.mRenderView != null) {
                            BDCloudVideoView.this.mRenderView.setVideoRotation(arg2);
                        }
                        return true;
                    case 10002:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_AUDIO_RENDERING_START:");
                        return true;
                    default:
                        return true;
                }
            }
        };
        this.mErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.6
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnErrorListener
            public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
                Log.d("BDCloudVideoView", "onError: " + framework_err + "," + impl_err);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_ERROR);
                BDCloudVideoView.this.isTryToPlaying = false;
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnErrorListener == null || BDCloudVideoView.this.mOnErrorListener.onError(BDCloudVideoView.this.mMediaPlayer, framework_err, impl_err)) {
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.7
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                BDCloudVideoView.this.mCurrentBufferPercentage = percent;
                if (BDCloudVideoView.this.mOnBufferingUpdateListener != null) {
                    BDCloudVideoView.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
                }
            }
        };
        this.mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.8
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onSeekComplete");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnSeekCompleteListener != null) {
                    BDCloudVideoView.this.mOnSeekCompleteListener.onSeekComplete(mp);
                }
            }
        };
        this.mSHCallback = new IRenderView.IRenderCallback() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.9
            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceChanged(IRenderView.ISurfaceHolder holder, int format, int w, int h) {
                Log.d("BDCloudVideoView", "mSHCallback onSurfaceChanged");
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceWidth = w;
                    BDCloudVideoView.this.mSurfaceHeight = h;
                    boolean isValidState = BDCloudVideoView.this.isTryToPlaying;
                    boolean hasValidSize = !BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mVideoWidth == w && BDCloudVideoView.this.mVideoHeight == h);
                    if (BDCloudVideoView.this.mMediaPlayer != null && isValidState && hasValidSize) {
                        BDCloudVideoView.this.start();
                        return;
                    }
                    return;
                }
                Log.e("BDCloudVideoView", "onSurfaceChanged: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceCreated(IRenderView.ISurfaceHolder holder, int width, int height) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = holder;
                    if (BDCloudVideoView.this.mMediaPlayer != null) {
                        BDCloudVideoView.this.bindSurfaceHolder(BDCloudVideoView.this.mMediaPlayer, holder);
                        return;
                    } else {
                        BDCloudVideoView.this.openVideo();
                        return;
                    }
                }
                Log.e("BDCloudVideoView", "onSurfaceCreated: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceDestroyed(IRenderView.ISurfaceHolder holder) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = null;
                    BDCloudVideoView.this.releaseWithoutStop();
                } else {
                    Log.e("BDCloudVideoView", "onSurfaceDestroyed: unmatched render callback\n");
                }
            }
        };
        this.mUseTextureViewFirst = useTextureViewFirst;
        initVideoView(context);
    }

    public BDCloudVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mUseTextureViewFirst = true;
        this.mCurrentState = PlayerState.STATE_IDLE;
        this.isTryToPlaying = false;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mCanPause = true;
        this.mCanSeekBack = true;
        this.mCanSeekForward = true;
        this.mDrmToken = null;
        this.mCurrentAspectRatio = 0;
        this.mCacheTimeInMilliSeconds = 0;
        this.mbShowCacheInfo = true;
        this.mDecodeMode = 0;
        this.mLogEnabled = false;
        this.mInitPlayPositionInMilliSec = 0L;
        this.mWakeMode = 0;
        this.mLeftVolume = -1.0f;
        this.mRightVolume = -1.0f;
        this.mUseApmDetect = false;
        this.mMaxProbeTimeInMs = 0;
        this.mMaxProbeSizeInBytes = 0;
        this.mMaxCacheSizeInBytes = 0;
        this.mLooping = false;
        this.mBufferSizeInBytes = 0;
        this.cachingHintViewRl = null;
        this.cachingProgressBar = null;
        this.cachingProgressHint = null;
        this.renderRootView = null;
        this.mainThreadHandler = new Handler(Looper.getMainLooper()) { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    BDCloudVideoView.this.setCachingHintViewVisibility(msg.arg1 == 1);
                }
            }
        };
        this.mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.2
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
                Log.d("BDCloudVideoView", "onVideoSizeChanged width=" + width + ";height=" + height + ";sarNum=" + sarNum + ";sarDen=" + sarDen);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                BDCloudVideoView.this.mVideoSarNum = mp.getVideoSarNum();
                BDCloudVideoView.this.mVideoSarDen = mp.getVideoSarDen();
                if (BDCloudVideoView.this.mVideoWidth != 0 && BDCloudVideoView.this.mVideoHeight != 0) {
                    if (BDCloudVideoView.this.mRenderView != null) {
                        BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                        BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    }
                    BDCloudVideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.3
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnPreparedListener
            public void onPrepared(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onPrepared");
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PREPARED);
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                if (BDCloudVideoView.this.mOnPreparedListener != null) {
                    BDCloudVideoView.this.mOnPreparedListener.onPrepared(BDCloudVideoView.this.mMediaPlayer);
                }
                Log.d("BDCloudVideoView", "onPrepared: mVideoWidth=" + BDCloudVideoView.this.mVideoWidth + ";mVideoHeight=" + BDCloudVideoView.this.mVideoHeight + ";mSurfaceWidth=" + BDCloudVideoView.this.mSurfaceWidth + ";mSurfaceHeight=" + BDCloudVideoView.this.mSurfaceHeight);
                if (BDCloudVideoView.this.mVideoWidth == 0 || BDCloudVideoView.this.mVideoHeight == 0) {
                    if (BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                } else if (BDCloudVideoView.this.mRenderView != null) {
                    BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                    BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    if ((!BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mSurfaceWidth == BDCloudVideoView.this.mVideoWidth && BDCloudVideoView.this.mSurfaceHeight == BDCloudVideoView.this.mVideoHeight)) && BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                }
            }
        };
        this.mCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.4
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnCompletionListener
            public void onCompletion(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onCompletion");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PLAYBACK_COMPLETED);
                BDCloudVideoView.this.isTryToPlaying = false;
                if (BDCloudVideoView.this.mOnCompletionListener != null) {
                    BDCloudVideoView.this.mOnCompletionListener.onCompletion(BDCloudVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.5
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnInfoListener
            public boolean onInfo(IMediaPlayer mp, int arg1, int arg2) {
                Log.d("BDCloudVideoView", "onInfo: arg1=" + arg1 + "; arg2=" + arg2);
                if (BDCloudVideoView.this.mOnInfoListener != null) {
                    BDCloudVideoView.this.mOnInfoListener.onInfo(mp, arg1, arg2);
                }
                switch (arg1) {
                    case 3:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_RENDERING_START:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING /* 700 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START /* 701 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_START:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(true);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END /* 702 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_END:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH /* 703 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NETWORK_BANDWIDTH: " + arg2);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING /* 800 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BAD_INTERLEAVING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE /* 801 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NOT_SEEKABLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE /* 802 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_METADATA_UPDATE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE /* 901 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT /* 902 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                        return true;
                    case 10001:
                        BDCloudVideoView.this.mVideoRotationDegree = arg2;
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + arg2);
                        if (BDCloudVideoView.this.mRenderView != null) {
                            BDCloudVideoView.this.mRenderView.setVideoRotation(arg2);
                        }
                        return true;
                    case 10002:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_AUDIO_RENDERING_START:");
                        return true;
                    default:
                        return true;
                }
            }
        };
        this.mErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.6
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnErrorListener
            public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
                Log.d("BDCloudVideoView", "onError: " + framework_err + "," + impl_err);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_ERROR);
                BDCloudVideoView.this.isTryToPlaying = false;
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnErrorListener == null || BDCloudVideoView.this.mOnErrorListener.onError(BDCloudVideoView.this.mMediaPlayer, framework_err, impl_err)) {
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.7
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                BDCloudVideoView.this.mCurrentBufferPercentage = percent;
                if (BDCloudVideoView.this.mOnBufferingUpdateListener != null) {
                    BDCloudVideoView.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
                }
            }
        };
        this.mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.8
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onSeekComplete");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnSeekCompleteListener != null) {
                    BDCloudVideoView.this.mOnSeekCompleteListener.onSeekComplete(mp);
                }
            }
        };
        this.mSHCallback = new IRenderView.IRenderCallback() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.9
            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceChanged(IRenderView.ISurfaceHolder holder, int format, int w, int h) {
                Log.d("BDCloudVideoView", "mSHCallback onSurfaceChanged");
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceWidth = w;
                    BDCloudVideoView.this.mSurfaceHeight = h;
                    boolean isValidState = BDCloudVideoView.this.isTryToPlaying;
                    boolean hasValidSize = !BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mVideoWidth == w && BDCloudVideoView.this.mVideoHeight == h);
                    if (BDCloudVideoView.this.mMediaPlayer != null && isValidState && hasValidSize) {
                        BDCloudVideoView.this.start();
                        return;
                    }
                    return;
                }
                Log.e("BDCloudVideoView", "onSurfaceChanged: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceCreated(IRenderView.ISurfaceHolder holder, int width, int height) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = holder;
                    if (BDCloudVideoView.this.mMediaPlayer != null) {
                        BDCloudVideoView.this.bindSurfaceHolder(BDCloudVideoView.this.mMediaPlayer, holder);
                        return;
                    } else {
                        BDCloudVideoView.this.openVideo();
                        return;
                    }
                }
                Log.e("BDCloudVideoView", "onSurfaceCreated: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceDestroyed(IRenderView.ISurfaceHolder holder) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = null;
                    BDCloudVideoView.this.releaseWithoutStop();
                } else {
                    Log.e("BDCloudVideoView", "onSurfaceDestroyed: unmatched render callback\n");
                }
            }
        };
        initVideoView(context);
    }

    public BDCloudVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mUseTextureViewFirst = true;
        this.mCurrentState = PlayerState.STATE_IDLE;
        this.isTryToPlaying = false;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mCanPause = true;
        this.mCanSeekBack = true;
        this.mCanSeekForward = true;
        this.mDrmToken = null;
        this.mCurrentAspectRatio = 0;
        this.mCacheTimeInMilliSeconds = 0;
        this.mbShowCacheInfo = true;
        this.mDecodeMode = 0;
        this.mLogEnabled = false;
        this.mInitPlayPositionInMilliSec = 0L;
        this.mWakeMode = 0;
        this.mLeftVolume = -1.0f;
        this.mRightVolume = -1.0f;
        this.mUseApmDetect = false;
        this.mMaxProbeTimeInMs = 0;
        this.mMaxProbeSizeInBytes = 0;
        this.mMaxCacheSizeInBytes = 0;
        this.mLooping = false;
        this.mBufferSizeInBytes = 0;
        this.cachingHintViewRl = null;
        this.cachingProgressBar = null;
        this.cachingProgressHint = null;
        this.renderRootView = null;
        this.mainThreadHandler = new Handler(Looper.getMainLooper()) { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    BDCloudVideoView.this.setCachingHintViewVisibility(msg.arg1 == 1);
                }
            }
        };
        this.mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.2
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
                Log.d("BDCloudVideoView", "onVideoSizeChanged width=" + width + ";height=" + height + ";sarNum=" + sarNum + ";sarDen=" + sarDen);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                BDCloudVideoView.this.mVideoSarNum = mp.getVideoSarNum();
                BDCloudVideoView.this.mVideoSarDen = mp.getVideoSarDen();
                if (BDCloudVideoView.this.mVideoWidth != 0 && BDCloudVideoView.this.mVideoHeight != 0) {
                    if (BDCloudVideoView.this.mRenderView != null) {
                        BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                        BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    }
                    BDCloudVideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.3
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnPreparedListener
            public void onPrepared(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onPrepared");
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PREPARED);
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                if (BDCloudVideoView.this.mOnPreparedListener != null) {
                    BDCloudVideoView.this.mOnPreparedListener.onPrepared(BDCloudVideoView.this.mMediaPlayer);
                }
                Log.d("BDCloudVideoView", "onPrepared: mVideoWidth=" + BDCloudVideoView.this.mVideoWidth + ";mVideoHeight=" + BDCloudVideoView.this.mVideoHeight + ";mSurfaceWidth=" + BDCloudVideoView.this.mSurfaceWidth + ";mSurfaceHeight=" + BDCloudVideoView.this.mSurfaceHeight);
                if (BDCloudVideoView.this.mVideoWidth == 0 || BDCloudVideoView.this.mVideoHeight == 0) {
                    if (BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                } else if (BDCloudVideoView.this.mRenderView != null) {
                    BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                    BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    if ((!BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mSurfaceWidth == BDCloudVideoView.this.mVideoWidth && BDCloudVideoView.this.mSurfaceHeight == BDCloudVideoView.this.mVideoHeight)) && BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                }
            }
        };
        this.mCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.4
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnCompletionListener
            public void onCompletion(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onCompletion");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PLAYBACK_COMPLETED);
                BDCloudVideoView.this.isTryToPlaying = false;
                if (BDCloudVideoView.this.mOnCompletionListener != null) {
                    BDCloudVideoView.this.mOnCompletionListener.onCompletion(BDCloudVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.5
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnInfoListener
            public boolean onInfo(IMediaPlayer mp, int arg1, int arg2) {
                Log.d("BDCloudVideoView", "onInfo: arg1=" + arg1 + "; arg2=" + arg2);
                if (BDCloudVideoView.this.mOnInfoListener != null) {
                    BDCloudVideoView.this.mOnInfoListener.onInfo(mp, arg1, arg2);
                }
                switch (arg1) {
                    case 3:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_RENDERING_START:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING /* 700 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START /* 701 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_START:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(true);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END /* 702 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_END:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH /* 703 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NETWORK_BANDWIDTH: " + arg2);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING /* 800 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BAD_INTERLEAVING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE /* 801 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NOT_SEEKABLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE /* 802 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_METADATA_UPDATE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE /* 901 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT /* 902 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                        return true;
                    case 10001:
                        BDCloudVideoView.this.mVideoRotationDegree = arg2;
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + arg2);
                        if (BDCloudVideoView.this.mRenderView != null) {
                            BDCloudVideoView.this.mRenderView.setVideoRotation(arg2);
                        }
                        return true;
                    case 10002:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_AUDIO_RENDERING_START:");
                        return true;
                    default:
                        return true;
                }
            }
        };
        this.mErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.6
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnErrorListener
            public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
                Log.d("BDCloudVideoView", "onError: " + framework_err + "," + impl_err);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_ERROR);
                BDCloudVideoView.this.isTryToPlaying = false;
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnErrorListener == null || BDCloudVideoView.this.mOnErrorListener.onError(BDCloudVideoView.this.mMediaPlayer, framework_err, impl_err)) {
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.7
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                BDCloudVideoView.this.mCurrentBufferPercentage = percent;
                if (BDCloudVideoView.this.mOnBufferingUpdateListener != null) {
                    BDCloudVideoView.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
                }
            }
        };
        this.mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.8
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onSeekComplete");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnSeekCompleteListener != null) {
                    BDCloudVideoView.this.mOnSeekCompleteListener.onSeekComplete(mp);
                }
            }
        };
        this.mSHCallback = new IRenderView.IRenderCallback() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.9
            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceChanged(IRenderView.ISurfaceHolder holder, int format, int w, int h) {
                Log.d("BDCloudVideoView", "mSHCallback onSurfaceChanged");
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceWidth = w;
                    BDCloudVideoView.this.mSurfaceHeight = h;
                    boolean isValidState = BDCloudVideoView.this.isTryToPlaying;
                    boolean hasValidSize = !BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mVideoWidth == w && BDCloudVideoView.this.mVideoHeight == h);
                    if (BDCloudVideoView.this.mMediaPlayer != null && isValidState && hasValidSize) {
                        BDCloudVideoView.this.start();
                        return;
                    }
                    return;
                }
                Log.e("BDCloudVideoView", "onSurfaceChanged: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceCreated(IRenderView.ISurfaceHolder holder, int width, int height) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = holder;
                    if (BDCloudVideoView.this.mMediaPlayer != null) {
                        BDCloudVideoView.this.bindSurfaceHolder(BDCloudVideoView.this.mMediaPlayer, holder);
                        return;
                    } else {
                        BDCloudVideoView.this.openVideo();
                        return;
                    }
                }
                Log.e("BDCloudVideoView", "onSurfaceCreated: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceDestroyed(IRenderView.ISurfaceHolder holder) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = null;
                    BDCloudVideoView.this.releaseWithoutStop();
                } else {
                    Log.e("BDCloudVideoView", "onSurfaceDestroyed: unmatched render callback\n");
                }
            }
        };
        initVideoView(context);
    }

    @TargetApi(21)
    public BDCloudVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mUseTextureViewFirst = true;
        this.mCurrentState = PlayerState.STATE_IDLE;
        this.isTryToPlaying = false;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mCanPause = true;
        this.mCanSeekBack = true;
        this.mCanSeekForward = true;
        this.mDrmToken = null;
        this.mCurrentAspectRatio = 0;
        this.mCacheTimeInMilliSeconds = 0;
        this.mbShowCacheInfo = true;
        this.mDecodeMode = 0;
        this.mLogEnabled = false;
        this.mInitPlayPositionInMilliSec = 0L;
        this.mWakeMode = 0;
        this.mLeftVolume = -1.0f;
        this.mRightVolume = -1.0f;
        this.mUseApmDetect = false;
        this.mMaxProbeTimeInMs = 0;
        this.mMaxProbeSizeInBytes = 0;
        this.mMaxCacheSizeInBytes = 0;
        this.mLooping = false;
        this.mBufferSizeInBytes = 0;
        this.cachingHintViewRl = null;
        this.cachingProgressBar = null;
        this.cachingProgressHint = null;
        this.renderRootView = null;
        this.mainThreadHandler = new Handler(Looper.getMainLooper()) { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    BDCloudVideoView.this.setCachingHintViewVisibility(msg.arg1 == 1);
                }
            }
        };
        this.mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.2
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnVideoSizeChangedListener
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
                Log.d("BDCloudVideoView", "onVideoSizeChanged width=" + width + ";height=" + height + ";sarNum=" + sarNum + ";sarDen=" + sarDen);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                BDCloudVideoView.this.mVideoSarNum = mp.getVideoSarNum();
                BDCloudVideoView.this.mVideoSarDen = mp.getVideoSarDen();
                if (BDCloudVideoView.this.mVideoWidth != 0 && BDCloudVideoView.this.mVideoHeight != 0) {
                    if (BDCloudVideoView.this.mRenderView != null) {
                        BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                        BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    }
                    BDCloudVideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new IMediaPlayer.OnPreparedListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.3
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnPreparedListener
            public void onPrepared(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onPrepared");
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PREPARED);
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.mVideoWidth = mp.getVideoWidth();
                BDCloudVideoView.this.mVideoHeight = mp.getVideoHeight();
                if (BDCloudVideoView.this.mOnPreparedListener != null) {
                    BDCloudVideoView.this.mOnPreparedListener.onPrepared(BDCloudVideoView.this.mMediaPlayer);
                }
                Log.d("BDCloudVideoView", "onPrepared: mVideoWidth=" + BDCloudVideoView.this.mVideoWidth + ";mVideoHeight=" + BDCloudVideoView.this.mVideoHeight + ";mSurfaceWidth=" + BDCloudVideoView.this.mSurfaceWidth + ";mSurfaceHeight=" + BDCloudVideoView.this.mSurfaceHeight);
                if (BDCloudVideoView.this.mVideoWidth == 0 || BDCloudVideoView.this.mVideoHeight == 0) {
                    if (BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                } else if (BDCloudVideoView.this.mRenderView != null) {
                    BDCloudVideoView.this.mRenderView.setVideoSize(BDCloudVideoView.this.mVideoWidth, BDCloudVideoView.this.mVideoHeight);
                    BDCloudVideoView.this.mRenderView.setVideoSampleAspectRatio(BDCloudVideoView.this.mVideoSarNum, BDCloudVideoView.this.mVideoSarDen);
                    if ((!BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mSurfaceWidth == BDCloudVideoView.this.mVideoWidth && BDCloudVideoView.this.mSurfaceHeight == BDCloudVideoView.this.mVideoHeight)) && BDCloudVideoView.this.isTryToPlaying) {
                        BDCloudVideoView.this.start();
                    }
                }
            }
        };
        this.mCompletionListener = new IMediaPlayer.OnCompletionListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.4
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnCompletionListener
            public void onCompletion(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onCompletion");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_PLAYBACK_COMPLETED);
                BDCloudVideoView.this.isTryToPlaying = false;
                if (BDCloudVideoView.this.mOnCompletionListener != null) {
                    BDCloudVideoView.this.mOnCompletionListener.onCompletion(BDCloudVideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new IMediaPlayer.OnInfoListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.5
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnInfoListener
            public boolean onInfo(IMediaPlayer mp, int arg1, int arg2) {
                Log.d("BDCloudVideoView", "onInfo: arg1=" + arg1 + "; arg2=" + arg2);
                if (BDCloudVideoView.this.mOnInfoListener != null) {
                    BDCloudVideoView.this.mOnInfoListener.onInfo(mp, arg1, arg2);
                }
                switch (arg1) {
                    case 3:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_RENDERING_START:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING /* 700 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START /* 701 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_START:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(true);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END /* 702 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BUFFERING_END:");
                        BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH /* 703 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NETWORK_BANDWIDTH: " + arg2);
                        return true;
                    case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING /* 800 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_BAD_INTERLEAVING:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE /* 801 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_NOT_SEEKABLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE /* 802 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_METADATA_UPDATE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE /* 901 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                        return true;
                    case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT /* 902 */:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                        return true;
                    case 10001:
                        BDCloudVideoView.this.mVideoRotationDegree = arg2;
                        Log.d("BDCloudVideoView", "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + arg2);
                        if (BDCloudVideoView.this.mRenderView != null) {
                            BDCloudVideoView.this.mRenderView.setVideoRotation(arg2);
                        }
                        return true;
                    case 10002:
                        Log.d("BDCloudVideoView", "MEDIA_INFO_AUDIO_RENDERING_START:");
                        return true;
                    default:
                        return true;
                }
            }
        };
        this.mErrorListener = new IMediaPlayer.OnErrorListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.6
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnErrorListener
            public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
                Log.d("BDCloudVideoView", "onError: " + framework_err + "," + impl_err);
                BDCloudVideoView.this.setCurrentState(PlayerState.STATE_ERROR);
                BDCloudVideoView.this.isTryToPlaying = false;
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnErrorListener == null || BDCloudVideoView.this.mOnErrorListener.onError(BDCloudVideoView.this.mMediaPlayer, framework_err, impl_err)) {
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.7
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnBufferingUpdateListener
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                BDCloudVideoView.this.mCurrentBufferPercentage = percent;
                if (BDCloudVideoView.this.mOnBufferingUpdateListener != null) {
                    BDCloudVideoView.this.mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
                }
            }
        };
        this.mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.8
            @Override // com.baidu.cloud.media.player.IMediaPlayer.OnSeekCompleteListener
            public void onSeekComplete(IMediaPlayer mp) {
                Log.d("BDCloudVideoView", "onSeekComplete");
                BDCloudVideoView.this.sendCachingHintViewVisibilityMessage(false);
                if (BDCloudVideoView.this.mOnSeekCompleteListener != null) {
                    BDCloudVideoView.this.mOnSeekCompleteListener.onSeekComplete(mp);
                }
            }
        };
        this.mSHCallback = new IRenderView.IRenderCallback() { // from class: com.baiducam.bdplayer.widget.BDCloudVideoView.9
            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceChanged(IRenderView.ISurfaceHolder holder, int format, int w, int h) {
                Log.d("BDCloudVideoView", "mSHCallback onSurfaceChanged");
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceWidth = w;
                    BDCloudVideoView.this.mSurfaceHeight = h;
                    boolean isValidState = BDCloudVideoView.this.isTryToPlaying;
                    boolean hasValidSize = !BDCloudVideoView.this.mRenderView.shouldWaitForResize() || (BDCloudVideoView.this.mVideoWidth == w && BDCloudVideoView.this.mVideoHeight == h);
                    if (BDCloudVideoView.this.mMediaPlayer != null && isValidState && hasValidSize) {
                        BDCloudVideoView.this.start();
                        return;
                    }
                    return;
                }
                Log.e("BDCloudVideoView", "onSurfaceChanged: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceCreated(IRenderView.ISurfaceHolder holder, int width, int height) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = holder;
                    if (BDCloudVideoView.this.mMediaPlayer != null) {
                        BDCloudVideoView.this.bindSurfaceHolder(BDCloudVideoView.this.mMediaPlayer, holder);
                        return;
                    } else {
                        BDCloudVideoView.this.openVideo();
                        return;
                    }
                }
                Log.e("BDCloudVideoView", "onSurfaceCreated: unmatched render callback\n");
            }

            @Override // com.baiducam.bdplayer.widget.IRenderView.IRenderCallback
            public void onSurfaceDestroyed(IRenderView.ISurfaceHolder holder) {
                if (holder.getRenderView() == BDCloudVideoView.this.mRenderView) {
                    BDCloudVideoView.this.mSurfaceHolder = null;
                    BDCloudVideoView.this.releaseWithoutStop();
                } else {
                    Log.e("BDCloudVideoView", "onSurfaceDestroyed: unmatched render callback\n");
                }
            }
        };
        initVideoView(context);
    }

    private void initVideoView(Context context) {
        this.mAppContext = context.getApplicationContext();
        this.renderRootView = new RelativeLayout(context);
        FrameLayout.LayoutParams fllp = new FrameLayout.LayoutParams(-1, -1);
        addView(this.renderRootView, fllp);
        reSetRender();
        addCachingHintView();
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        setCurrentState(PlayerState.STATE_IDLE);
    }

    private void addCachingHintView() {
        this.cachingHintViewRl = new RelativeLayout(getContext());
        FrameLayout.LayoutParams fllp = new FrameLayout.LayoutParams(-1, -1);
        this.cachingHintViewRl.setVisibility(8);
        addView(this.cachingHintViewRl, fllp);
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(-2, -2);
        rllp.addRule(13);
        View loadingView = View.inflate(getContext(), R.layout.camera_loading_layout, null);
        loadingView.setBackgroundColor(16777215);
        this.mIvPiont = (ImageView) loadingView.findViewById(R.id.iv_camera_loading_piont);
        this.mAniDrawable = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.white_point_camera_load_animation);
        this.mIvPiont.setBackground(this.mAniDrawable);
        this.mAniDrawable.start();
        this.cachingHintViewRl.addView(loadingView, rllp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCachingHintViewVisibility(boolean bShow) {
        if (bShow) {
            this.cachingHintViewRl.setVisibility(0);
        } else {
            this.cachingHintViewRl.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCachingHintViewVisibilityMessage(boolean bShow) {
        if (this.mbShowCacheInfo) {
            Message msg = this.mainThreadHandler.obtainMessage();
            msg.what = 1;
            msg.arg1 = bShow ? 1 : 0;
            this.mainThreadHandler.sendMessage(msg);
        }
    }

    protected void setRenderView(IRenderView renderView) {
        if (this.mRenderView != null) {
            if (this.mMediaPlayer != null) {
                this.mMediaPlayer.setDisplay(null);
            }
            View renderUIView = this.mRenderView.getView();
            this.mRenderView.removeRenderCallback(this.mSHCallback);
            this.mRenderView.release();
            this.mRenderView = null;
            this.mSurfaceHolder = null;
            this.renderRootView.removeView(renderUIView);
        }
        if (renderView != null) {
            this.mRenderView = renderView;
            renderView.setAspectRatio(this.mCurrentAspectRatio);
            if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
                renderView.setVideoSize(this.mVideoWidth, this.mVideoHeight);
            }
            if (this.mVideoSarNum > 0 && this.mVideoSarDen > 0) {
                renderView.setVideoSampleAspectRatio(this.mVideoSarNum, this.mVideoSarDen);
            }
            View renderUIView2 = this.mRenderView.getView();
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-2, -2);
            lp.addRule(13);
            renderUIView2.setLayoutParams(lp);
            this.renderRootView.addView(renderUIView2);
            this.mRenderView.addRenderCallback(this.mSHCallback);
            this.mRenderView.setVideoRotation(this.mVideoRotationDegree);
        }
    }

    public void reSetRender() {
        if (this.mUseTextureViewFirst && Build.VERSION.SDK_INT >= 16) {
            TextureRenderView renderView = new TextureRenderView(getContext());
            if (this.mMediaPlayer != null) {
                renderView.getSurfaceHolder().bindToMediaPlayer(this.mMediaPlayer);
                renderView.setVideoSize(this.mMediaPlayer.getVideoWidth(), this.mMediaPlayer.getVideoHeight());
                renderView.setVideoSampleAspectRatio(this.mMediaPlayer.getVideoSarNum(), this.mMediaPlayer.getVideoSarDen());
                renderView.setAspectRatio(this.mCurrentAspectRatio);
            }
            setRenderView(renderView);
            return;
        }
        setRenderView(new SurfaceRenderView(getContext()));
    }

    public void setVideoPath(String path) {
        setVideoPathWithToken(path, null);
    }

    public void setVideoPathWithToken(String path, String token) {
        this.mDrmToken = token;
        setVideoURI(Uri.parse(path));
    }

    public void setHeaders(Map<String, String> headers) {
        this.mHeaders = headers;
    }

    private void setVideoURI(Uri uri) {
        this.mUri = uri;
        Log.e("mUri", "mUri=" + this.mUri);
        openVideo();
        requestLayout();
        invalidate();
    }

    public void stopPlayback() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            setCurrentState(PlayerState.STATE_IDLE);
            this.isTryToPlaying = false;
            AudioManager am = (AudioManager) this.mAppContext.getSystemService("audio");
            am.abandonAudioFocus(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(14)
    public void openVideo() {
        if (this.mUri != null && this.mSurfaceHolder != null) {
            release(false);
            AudioManager am = (AudioManager) this.mAppContext.getSystemService("audio");
            am.requestAudioFocus(null, 3, 1);
            try {
                this.mMediaPlayer = createPlayer();
                if (!TextUtils.isEmpty(this.mDrmToken)) {
                    this.mMediaPlayer.setDecryptTokenForHLS(this.mDrmToken);
                }
                this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
                this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
                this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
                this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
                this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
                this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
                this.mMediaPlayer.setOnSeekCompleteListener(this.mSeekCompleteListener);
                this.mCurrentBufferPercentage = 0;
                this.mMediaPlayer.setDataSource(this.mAppContext, this.mUri, this.mHeaders);
                bindSurfaceHolder(this.mMediaPlayer, this.mSurfaceHolder);
                this.mMediaPlayer.setAudioStreamType(3);
                this.mMediaPlayer.setScreenOnWhilePlaying(true);
                this.mMediaPlayer.prepareAsync();
                this.mMediaPlayer.toggleFrameChasing(false);
                sendCachingHintViewVisibilityMessage(true);
                setCurrentState(PlayerState.STATE_PREPARING);
            } catch (IOException ex) {
                Log.w("BDCloudVideoView", "Unable to open content: " + this.mUri, ex);
                setCurrentState(PlayerState.STATE_ERROR);
                this.isTryToPlaying = false;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            } catch (IllegalArgumentException ex2) {
                Log.w("BDCloudVideoView", "Unable to open content: " + this.mUri, ex2);
                setCurrentState(PlayerState.STATE_ERROR);
                this.isTryToPlaying = false;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            }
        }
    }

    public BDCloudMediaPlayer createPlayer() {
        BDCloudMediaPlayer bdCloudMediaPlayer = new BDCloudMediaPlayer(getContext());
        bdCloudMediaPlayer.setLogEnabled(this.mLogEnabled);
        bdCloudMediaPlayer.setDecodeMode(this.mDecodeMode);
        if (this.mInitPlayPositionInMilliSec > -1) {
            bdCloudMediaPlayer.setInitPlayPosition(this.mInitPlayPositionInMilliSec);
            this.mInitPlayPositionInMilliSec = -1L;
        }
        if (this.mWakeMode > 0) {
            bdCloudMediaPlayer.setWakeMode(getContext(), this.mWakeMode);
        }
        if (this.mLeftVolume > -1.0f && this.mRightVolume > -1.0f) {
            bdCloudMediaPlayer.setVolume(this.mLeftVolume, this.mRightVolume);
        }
        if (this.mCacheTimeInMilliSeconds > 0) {
            bdCloudMediaPlayer.setBufferTimeInMs(this.mCacheTimeInMilliSeconds);
        }
        if (this.mMaxProbeTimeInMs > 0) {
            bdCloudMediaPlayer.setMaxProbeTime(this.mMaxProbeTimeInMs);
        }
        if (this.mMaxProbeSizeInBytes > 0) {
            bdCloudMediaPlayer.setMaxProbeSize(this.mMaxProbeSizeInBytes);
        }
        if (this.mMaxCacheSizeInBytes > 0) {
            bdCloudMediaPlayer.setMaxCacheSizeInBytes(this.mMaxCacheSizeInBytes);
        }
        if (this.mLooping) {
            bdCloudMediaPlayer.setLooping(this.mLooping);
        }
        if (this.mBufferSizeInBytes > 0) {
            bdCloudMediaPlayer.setBufferSizeInBytes(this.mBufferSizeInBytes);
        }
        return bdCloudMediaPlayer;
    }

    @Deprecated
    public void setBufferTimeInMs(int milliSeconds) {
        this.mCacheTimeInMilliSeconds = milliSeconds;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setBufferTimeInMs(this.mCacheTimeInMilliSeconds);
        }
    }

    public void setBufferSizeInBytes(int sizeInBytes) {
        this.mBufferSizeInBytes = sizeInBytes;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setBufferSizeInBytes(this.mBufferSizeInBytes);
        }
    }

    public void setLooping(boolean isLoop) {
        this.mLooping = isLoop;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setLooping(this.mLooping);
        }
    }

    public void setMaxCacheSizeInBytes(int sizeInBytes) {
        this.mMaxCacheSizeInBytes = sizeInBytes;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setMaxCacheSizeInBytes(this.mMaxCacheSizeInBytes);
        }
    }

    public void setMaxProbeSize(int maxProbeSizeInBytes) {
        this.mMaxProbeSizeInBytes = maxProbeSizeInBytes;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setMaxProbeSize(this.mMaxProbeSizeInBytes);
        }
    }

    public void setMaxProbeTime(int maxProbeTimeInMs) {
        this.mMaxProbeTimeInMs = maxProbeTimeInMs;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setMaxProbeTime(this.mMaxProbeTimeInMs);
        }
    }

    public void setUseApmDetect(boolean useApmDetect) {
        this.mUseApmDetect = useApmDetect;
        if (this.mMediaPlayer != null) {
        }
    }

    public void setInitPlayPosition(long milliSeconds) {
        this.mInitPlayPositionInMilliSec = milliSeconds;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setInitPlayPosition(this.mInitPlayPositionInMilliSec);
            this.mInitPlayPositionInMilliSec = -1L;
        }
    }

    public void setLogEnabled(boolean enabled) {
        this.mLogEnabled = enabled;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setLogEnabled(this.mLogEnabled);
        }
    }

    public void setDecodeMode(int decodeMode) {
        this.mDecodeMode = decodeMode;
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setDecodeMode(this.mDecodeMode);
        }
    }

    public IMediaPlayer getCurrentMediaPlayer() {
        return this.mMediaPlayer;
    }

    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener l) {
        this.mOnPreparedListener = l;
    }

    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener l) {
        this.mOnCompletionListener = l;
    }

    public void setOnErrorListener(IMediaPlayer.OnErrorListener l) {
        this.mOnErrorListener = l;
    }

    public void setOnInfoListener(IMediaPlayer.OnInfoListener l) {
        this.mOnInfoListener = l;
    }

    public void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener l) {
        this.mOnBufferingUpdateListener = l;
    }

    public void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener l) {
        this.mOnSeekCompleteListener = l;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindSurfaceHolder(IMediaPlayer mp, IRenderView.ISurfaceHolder holder) {
        if (mp != null) {
            if (holder == null) {
                mp.setDisplay(null);
            } else {
                holder.bindToMediaPlayer(mp);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseWithoutStop() {
        if (this.mMediaPlayer != null && (this.mRenderView instanceof SurfaceRenderView)) {
            this.mMediaPlayer.setDisplay(null);
        }
    }

    private void release(boolean cleartargetstate) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            setCurrentState(PlayerState.STATE_IDLE);
            if (cleartargetstate) {
                this.isTryToPlaying = false;
            }
            AudioManager am = (AudioManager) this.mAppContext.getSystemService("audio");
            am.abandonAudioFocus(null);
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void start() {
        if ((this.mMediaPlayer != null && this.mCurrentState == PlayerState.STATE_ERROR) || this.mCurrentState == PlayerState.STATE_PLAYBACK_COMPLETED) {
            if (this.mCurrentState == PlayerState.STATE_PLAYBACK_COMPLETED) {
                this.mMediaPlayer.stop();
            }
            this.mMediaPlayer.prepareAsync();
            sendCachingHintViewVisibilityMessage(true);
            setCurrentState(PlayerState.STATE_PREPARING);
        } else if (isInPlaybackState()) {
            this.mMediaPlayer.start();
            setCurrentState(PlayerState.STATE_PLAYING);
        }
        this.isTryToPlaying = true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            setCurrentState(PlayerState.STATE_PAUSED);
        }
        this.isTryToPlaying = false;
    }

    public String getCurrentPlayingUrl() {
        if (this.mUri != null) {
            return this.mUri.toString();
        }
        return null;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getDuration() {
        if (isInPlaybackState()) {
            return (int) this.mMediaPlayer.getDuration();
        }
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return (int) this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public void seekTo(int msec) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(msec);
            sendCachingHintViewVisibilityMessage(true);
        }
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    private boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == PlayerState.STATE_ERROR || this.mCurrentState == PlayerState.STATE_IDLE || this.mCurrentState == PlayerState.STATE_PREPARING) ? false : true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canPause() {
        return this.mCanPause;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public int getAudioSessionId() {
        return 0;
    }

    public int getVideoWidth() {
        return this.mVideoWidth;
    }

    public int getVideoHeight() {
        return this.mVideoHeight;
    }

    public void setVideoScalingMode(int mode) {
        if (mode == 1 || mode == 2 || mode == 3) {
            if (mode == 1) {
                this.mCurrentAspectRatio = 0;
            } else if (mode == 2) {
                this.mCurrentAspectRatio = 1;
            } else {
                this.mCurrentAspectRatio = 3;
            }
            if (this.mRenderView != null) {
                this.mRenderView.setAspectRatio(this.mCurrentAspectRatio);
                return;
            }
            return;
        }
        Log.e("BDCloudVideoView", "setVideoScalingMode: param should be VID");
    }

    public String[] getVariantInfo() {
        if (this.mMediaPlayer != null) {
            return this.mMediaPlayer.getVariantInfo();
        }
        return null;
    }

    public long getDownloadSpeed() {
        if (this.mMediaPlayer != null) {
            return this.mMediaPlayer.getDownloadSpeed();
        }
        return 0L;
    }

    public Bitmap getBitmap() {
        if (this.mRenderView != null) {
            return this.mRenderView.getBitmap();
        }
        return null;
    }

    public static void setAK(String ak) {
        BDCloudMediaPlayer.setAK(ak);
    }
}
