package com.baiducam.bdplayer.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.baidu.cloud.media.player.IMediaPlayer;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@TargetApi(14)
public class TextureRenderView extends TextureView implements IRenderView {
    private SurfaceTexture lastSurfaceTexture;
    private MeasureHelper mMeasureHelper;
    private int mMediaPlayerCode;
    private SurfaceCallback mSurfaceCallback;

    public TextureRenderView(Context context) {
        super(context);
        this.mMediaPlayerCode = 0;
        initView(context);
    }

    public TextureRenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMediaPlayerCode = 0;
        initView(context);
    }

    public TextureRenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mMediaPlayerCode = 0;
        initView(context);
    }

    private void initView(Context context) {
        this.mMeasureHelper = new MeasureHelper(this);
        this.mSurfaceCallback = new SurfaceCallback(this);
        setSurfaceTextureListener(this.mSurfaceCallback);
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public View getView() {
        return this;
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public boolean shouldWaitForResize() {
        return false;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("TextureRenderView", "onDetachedFromWindow");
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public void setVideoSize(int videoWidth, int videoHeight) {
        if (videoWidth > 0 && videoHeight > 0) {
            this.mMeasureHelper.setVideoSize(videoWidth, videoHeight);
            requestLayout();
        }
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public void setVideoSampleAspectRatio(int videoSarNum, int videoSarDen) {
        if (videoSarNum > 0 && videoSarDen > 0) {
            this.mMeasureHelper.setVideoSampleAspectRatio(videoSarNum, videoSarDen);
            requestLayout();
        }
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public void setVideoRotation(int degree) {
        this.mMeasureHelper.setVideoRotation(degree);
        setRotation(degree);
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public void setAspectRatio(int aspectRatio) {
        this.mMeasureHelper.setAspectRatio(aspectRatio);
        requestLayout();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(this.mMeasureHelper.getMeasuredWidth(), this.mMeasureHelper.getMeasuredHeight());
    }

    public void setCurrentMediaPlayerCode(int currentCode) {
        this.mMediaPlayerCode = currentCode;
    }

    public int getCurrentMediaPlayerCode() {
        return this.mMediaPlayerCode;
    }

    public SurfaceTexture getLastSurfaceTexture() {
        return this.lastSurfaceTexture;
    }

    public void setLastSurfaceTexture(SurfaceTexture surfaceTexture) {
        this.lastSurfaceTexture = surfaceTexture;
    }

    public IRenderView.ISurfaceHolder getSurfaceHolder() {
        return new InternalSurfaceHolder(this);
    }

    private static final class InternalSurfaceHolder implements IRenderView.ISurfaceHolder {
        private TextureRenderView mTextureView;

        public InternalSurfaceHolder(TextureRenderView textureView) {
            this.mTextureView = textureView;
        }

        @Override // com.baiducam.bdplayer.widget.IRenderView.ISurfaceHolder
        @TargetApi(16)
        public void bindToMediaPlayer(IMediaPlayer mp) {
            if (mp != null) {
                if (mp.hashCode() != this.mTextureView.getCurrentMediaPlayerCode()) {
                    mp.setSurface(openSurface());
                } else if (!this.mTextureView.getLastSurfaceTexture().equals(this.mTextureView.getSurfaceTexture())) {
                    this.mTextureView.setSurfaceTexture(this.mTextureView.getLastSurfaceTexture());
                }
                this.mTextureView.setCurrentMediaPlayerCode(mp.hashCode());
            }
        }

        @Override // com.baiducam.bdplayer.widget.IRenderView.ISurfaceHolder
        public IRenderView getRenderView() {
            return this.mTextureView;
        }

        public Surface openSurface() {
            return new Surface(this.mTextureView.getSurfaceTexture());
        }
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public void addRenderCallback(IRenderView.IRenderCallback callback) {
        this.mSurfaceCallback.addRenderCallback(callback);
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public void removeRenderCallback(IRenderView.IRenderCallback callback) {
        this.mSurfaceCallback.removeRenderCallback(callback);
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    @TargetApi(16)
    public void release() {
        if (this.lastSurfaceTexture != null) {
            if (isAvailable()) {
                this.mSurfaceCallback.setOwnSurfaceTexture(true);
            } else {
                this.lastSurfaceTexture.release();
                this.lastSurfaceTexture = null;
            }
        }
    }

    private static final class SurfaceCallback implements TextureView.SurfaceTextureListener {
        private int mHeight;
        private boolean mIsFormatChanged;
        private volatile boolean mOwnSurfaceTexture = false;
        private Map<IRenderView.IRenderCallback, Object> mRenderCallbackMap = new ConcurrentHashMap();
        private SurfaceTexture mSurfaceTexture;
        private WeakReference<TextureRenderView> mWeakRenderView;
        private int mWidth;

        public SurfaceCallback(TextureRenderView renderView) {
            this.mWeakRenderView = new WeakReference<>(renderView);
        }

        public void setOwnSurfaceTexture(boolean ownSurfaceTexture) {
            this.mOwnSurfaceTexture = ownSurfaceTexture;
        }

        public void addRenderCallback(IRenderView.IRenderCallback callback) {
            this.mRenderCallbackMap.put(callback, callback);
            IRenderView.ISurfaceHolder surfaceHolder = null;
            if (this.mSurfaceTexture != null) {
                if (0 == 0) {
                    surfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get());
                }
                callback.onSurfaceCreated(surfaceHolder, this.mWidth, this.mHeight);
            }
            if (this.mIsFormatChanged) {
                if (surfaceHolder == null) {
                    surfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get());
                }
                callback.onSurfaceChanged(surfaceHolder, 0, this.mWidth, this.mHeight);
            }
        }

        public void removeRenderCallback(IRenderView.IRenderCallback callback) {
            this.mRenderCallbackMap.remove(callback);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        @TargetApi(16)
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            this.mSurfaceTexture = surface;
            if (this.mWeakRenderView.get() == null) {
                Log.e("TextureRenderView", "!!!!!Too bad, textureview in callback is released. function will not work normally");
            } else if (this.mWeakRenderView.get().getLastSurfaceTexture() == null) {
                this.mWeakRenderView.get().setLastSurfaceTexture(surface);
            }
            this.mIsFormatChanged = false;
            this.mWidth = 0;
            this.mHeight = 0;
            IRenderView.ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get());
            for (IRenderView.IRenderCallback renderCallback : this.mRenderCallbackMap.keySet()) {
                renderCallback.onSurfaceCreated(surfaceHolder, 0, 0);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            this.mSurfaceTexture = surface;
            this.mIsFormatChanged = true;
            this.mWidth = width;
            this.mHeight = height;
            IRenderView.ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get());
            for (IRenderView.IRenderCallback renderCallback : this.mRenderCallbackMap.keySet()) {
                renderCallback.onSurfaceChanged(surfaceHolder, 0, width, height);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            this.mSurfaceTexture = surface;
            this.mIsFormatChanged = false;
            this.mWidth = 0;
            this.mHeight = 0;
            IRenderView.ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(this.mWeakRenderView.get());
            for (IRenderView.IRenderCallback renderCallback : this.mRenderCallbackMap.keySet()) {
                renderCallback.onSurfaceDestroyed(surfaceHolder);
            }
            return this.mOwnSurfaceTexture;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(TextureRenderView.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(TextureRenderView.class.getName());
    }
}
