package com.baidu.cloud.media.player;

import android.os.Bundle;
import com.baidu.cloud.media.player.misc.IMediaDataSource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class AbstractMediaPlayer implements IMediaPlayer {
    private IMediaPlayer.OnPreparedListener a;
    private IMediaPlayer.OnCompletionListener b;
    private IMediaPlayer.OnBufferingUpdateListener c;
    private IMediaPlayer.OnSeekCompleteListener d;
    private IMediaPlayer.OnVideoSizeChangedListener e;
    private IMediaPlayer.OnErrorListener f;
    private IMediaPlayer.OnInfoListener g;
    private IMediaPlayer.OnTimedTextListener h;
    private IMediaPlayer.OnMetadataListener i;

    protected final void a() {
        if (this.a != null) {
            this.a.onPrepared(this);
        }
    }

    protected final void a(int i) {
        if (this.c != null) {
            this.c.onBufferingUpdate(this, i);
        }
    }

    protected final void a(int i, int i2, int i3, int i4) {
        if (this.e != null) {
            this.e.onVideoSizeChanged(this, i, i2, i3, i4);
        }
    }

    protected final void a(Bundle bundle) {
        if (this.i != null) {
            this.i.onMetadata(this, bundle);
        }
    }

    protected final void a(BDTimedText bDTimedText) {
        if (this.h != null) {
            this.h.onTimedText(this, bDTimedText);
        }
    }

    protected final boolean a(int i, int i2) {
        return this.f != null && this.f.onError(this, i, i2);
    }

    protected final void b() {
        if (this.b != null) {
            this.b.onCompletion(this);
        }
    }

    protected final boolean b(int i, int i2) {
        return this.g != null && this.g.onInfo(this, i, i2);
    }

    protected final void c() {
        if (this.d != null) {
            this.d.onSeekComplete(this);
        }
    }

    public void resetListeners() {
        this.a = null;
        this.c = null;
        this.b = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setDataSource(IMediaDataSource iMediaDataSource) {
        throw new UnsupportedOperationException();
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public final void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener) {
        this.c = onBufferingUpdateListener;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public final void setOnCompletionListener(IMediaPlayer.OnCompletionListener onCompletionListener) {
        this.b = onCompletionListener;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public final void setOnErrorListener(IMediaPlayer.OnErrorListener onErrorListener) {
        this.f = onErrorListener;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public final void setOnInfoListener(IMediaPlayer.OnInfoListener onInfoListener) {
        this.g = onInfoListener;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public final void setOnMetadataListener(IMediaPlayer.OnMetadataListener onMetadataListener) {
        this.i = onMetadataListener;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public final void setOnPreparedListener(IMediaPlayer.OnPreparedListener onPreparedListener) {
        this.a = onPreparedListener;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public final void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.d = onSeekCompleteListener;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public final void setOnTimedTextListener(IMediaPlayer.OnTimedTextListener onTimedTextListener) {
        this.h = onTimedTextListener;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public final void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.e = onVideoSizeChangedListener;
    }
}
