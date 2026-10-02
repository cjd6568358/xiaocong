package com.baiducam.bdplayer.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.baidu.cloud.media.player.IMediaPlayer;
import com.tencent.android.tpush.common.Constants;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SurfaceRenderView extends SurfaceView implements IRenderView {
    private MeasureHelper mMeasureHelper;
    private SurfaceCallback mSurfaceCallback;

    public SurfaceRenderView(Context context) {
        super(context);
        initView(context);
    }

    public SurfaceRenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SurfaceRenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mMeasureHelper = new MeasureHelper(this);
        this.mSurfaceCallback = new SurfaceCallback(this);
        getHolder().addCallback(this.mSurfaceCallback);
        getHolder().setType(0);
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public View getView() {
        return this;
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public boolean shouldWaitForResize() {
        return true;
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public void setVideoSize(int videoWidth, int videoHeight) {
        if (videoWidth > 0 && videoHeight > 0) {
            this.mMeasureHelper.setVideoSize(videoWidth, videoHeight);
            getHolder().setFixedSize(videoWidth, videoHeight);
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
        Log.e(Constants.MAIN_VERSION_TAG, "SurfaceView doesn't support rotation (" + degree + ")!\n");
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public void setAspectRatio(int aspectRatio) {
        this.mMeasureHelper.setAspectRatio(aspectRatio);
        requestLayout();
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(this.mMeasureHelper.getMeasuredWidth(), this.mMeasureHelper.getMeasuredHeight());
    }

    private static final class InternalSurfaceHolder implements IRenderView.ISurfaceHolder {
        private SurfaceHolder mSurfaceHolder;
        private SurfaceRenderView mSurfaceView;

        public InternalSurfaceHolder(SurfaceRenderView surfaceView, SurfaceHolder surfaceHolder) {
            this.mSurfaceView = surfaceView;
            this.mSurfaceHolder = surfaceHolder;
        }

        @Override // com.baiducam.bdplayer.widget.IRenderView.ISurfaceHolder
        public void bindToMediaPlayer(IMediaPlayer mp) {
            if (mp != null) {
                mp.setDisplay(this.mSurfaceHolder);
            }
        }

        @Override // com.baiducam.bdplayer.widget.IRenderView.ISurfaceHolder
        public IRenderView getRenderView() {
            return this.mSurfaceView;
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
    public void release() {
    }

    @Override // com.baiducam.bdplayer.widget.IRenderView
    public Bitmap getBitmap() {
        return null;
    }

    private static final class SurfaceCallback implements SurfaceHolder.Callback {
        private int mFormat;
        private int mHeight;
        private boolean mIsFormatChanged;
        private Map<IRenderView.IRenderCallback, Object> mRenderCallbackMap = new ConcurrentHashMap();
        private SurfaceHolder mSurfaceHolder;
        private WeakReference<SurfaceRenderView> mWeakSurfaceView;
        private int mWidth;

        public SurfaceCallback(SurfaceRenderView surfaceView) {
            this.mWeakSurfaceView = new WeakReference<>(surfaceView);
        }

        public void addRenderCallback(IRenderView.IRenderCallback callback) {
            this.mRenderCallbackMap.put(callback, callback);
            IRenderView.ISurfaceHolder surfaceHolder = null;
            if (this.mSurfaceHolder != null) {
                if (0 == 0) {
                    surfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
                }
                callback.onSurfaceCreated(surfaceHolder, this.mWidth, this.mHeight);
            }
            if (this.mIsFormatChanged) {
                if (surfaceHolder == null) {
                    surfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
                }
                callback.onSurfaceChanged(surfaceHolder, this.mFormat, this.mWidth, this.mHeight);
            }
        }

        public void removeRenderCallback(IRenderView.IRenderCallback callback) {
            this.mRenderCallbackMap.remove(callback);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder holder) {
            this.mSurfaceHolder = holder;
            this.mIsFormatChanged = false;
            this.mFormat = 0;
            this.mWidth = 0;
            this.mHeight = 0;
            Log.d("SurfaceRenderView", "surfaceCreated: " + holder.getSurface());
            IRenderView.ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
            for (IRenderView.IRenderCallback renderCallback : this.mRenderCallbackMap.keySet()) {
                renderCallback.onSurfaceCreated(surfaceHolder, 0, 0);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder holder) {
            this.mSurfaceHolder = null;
            this.mIsFormatChanged = false;
            this.mFormat = 0;
            this.mWidth = 0;
            this.mHeight = 0;
            IRenderView.ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
            for (IRenderView.IRenderCallback renderCallback : this.mRenderCallbackMap.keySet()) {
                renderCallback.onSurfaceDestroyed(surfaceHolder);
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.mSurfaceHolder = holder;
            this.mIsFormatChanged = true;
            this.mFormat = format;
            this.mWidth = width;
            this.mHeight = height;
            IRenderView.ISurfaceHolder surfaceHolder = new InternalSurfaceHolder(this.mWeakSurfaceView.get(), this.mSurfaceHolder);
            for (IRenderView.IRenderCallback renderCallback : this.mRenderCallbackMap.keySet()) {
                renderCallback.onSurfaceChanged(surfaceHolder, format, width, height);
            }
        }
    }

    @Override // android.view.View
    @TargetApi(14)
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(SurfaceRenderView.class.getName());
    }

    @Override // android.view.View
    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        if (Build.VERSION.SDK_INT >= 14) {
            info.setClassName(SurfaceRenderView.class.getName());
        }
    }
}
