package com.baidu.cloud.media.player;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.baidu.cloud.media.player.misc.IMediaDataSource;
import com.baidu.cloud.media.player.misc.ITrackInfo;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface IMediaPlayer {
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_TIMED_OUT = -110;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    public static final int MEDIA_INFO_AUDIO_RENDERING_START = 10002;
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_BUFFERING_END = 702;
    public static final int MEDIA_INFO_BUFFERING_START = 701;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_STARTED_AS_NEXT = 2;
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    public static final int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i);
    }

    public interface OnCompletionListener {
        void onCompletion(IMediaPlayer iMediaPlayer);
    }

    public interface OnErrorListener {
        boolean onError(IMediaPlayer iMediaPlayer, int i, int i2);
    }

    public interface OnInfoListener {
        boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2);
    }

    public interface OnMetadataListener {
        void onMetadata(IMediaPlayer iMediaPlayer, Bundle bundle);
    }

    public interface OnPreparedListener {
        void onPrepared(IMediaPlayer iMediaPlayer);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete(IMediaPlayer iMediaPlayer);
    }

    public interface OnTimedTextListener {
        void onTimedText(IMediaPlayer iMediaPlayer, BDTimedText bDTimedText);
    }

    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4);
    }

    int getAudioSessionId();

    long getCurrentPosition();

    String getDataSource();

    long getDuration();

    c getMediaInfo();

    ITrackInfo[] getTrackInfo();

    int getVideoHeight();

    int getVideoSarDen();

    int getVideoSarNum();

    int getVideoWidth();

    boolean isLooping();

    boolean isPlayable();

    boolean isPlaying();

    void pause() throws IllegalStateException;

    void prepareAsync() throws IllegalStateException;

    void release();

    void reset();

    void seekTo(long j) throws IllegalStateException;

    void setAudioStreamType(int i);

    void setDataSource(Context context, Uri uri) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException;

    void setDataSource(Context context, Uri uri, Map<String, String> map) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException;

    void setDataSource(IMediaDataSource iMediaDataSource);

    void setDataSource(FileDescriptor fileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException;

    void setDataSource(String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException;

    void setDisplay(SurfaceHolder surfaceHolder);

    void setKeepInBackground(boolean z);

    void setLogEnabled(boolean z);

    void setLooping(boolean z);

    void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    void setOnCompletionListener(OnCompletionListener onCompletionListener);

    void setOnErrorListener(OnErrorListener onErrorListener);

    void setOnInfoListener(OnInfoListener onInfoListener);

    void setOnMetadataListener(OnMetadataListener onMetadataListener);

    void setOnPreparedListener(OnPreparedListener onPreparedListener);

    void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    void setOnTimedTextListener(OnTimedTextListener onTimedTextListener);

    void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener);

    void setScreenOnWhilePlaying(boolean z);

    void setSurface(Surface surface);

    void setVolume(float f, float f2);

    void setWakeMode(Context context, int i);

    void start() throws IllegalStateException;

    void stop() throws IllegalStateException;
}
