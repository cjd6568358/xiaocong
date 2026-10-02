package com.facebook.react.modules.debug;

import android.annotation.TargetApi;
import android.view.Choreographer;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@TargetApi(16)
public class FpsDebugFrameCallback implements Choreographer.FrameCallback {
    private final Choreographer mChoreographer;
    private final ReactContext mReactContext;
    private TreeMap<Long, FpsInfo> mTimeToFps;
    private final UIManagerModule mUIManagerModule;
    private boolean mShouldStop = false;
    private long mFirstFrameTime = -1;
    private long mLastFrameTime = -1;
    private int mNumFrameCallbacks = 0;
    private int mExpectedNumFramesPrev = 0;
    private int m4PlusFrameStutters = 0;
    private int mNumFrameCallbacksWithBatchDispatches = 0;
    private boolean mIsRecordingFpsInfoAtEachFrame = false;
    private final DidJSUpdateUiDuringFrameDetector mDidJSUpdateUiDuringFrameDetector = new DidJSUpdateUiDuringFrameDetector();

    public static class FpsInfo {
        public final double fps;
        public final double jsFps;
        public final int total4PlusFrameStutters;
        public final int totalExpectedFrames;
        public final int totalFrames;
        public final int totalJsFrames;
        public final int totalTimeMs;

        public FpsInfo(int totalFrames, int totalJsFrames, int totalExpectedFrames, int total4PlusFrameStutters, double fps, double jsFps, int totalTimeMs) {
            this.totalFrames = totalFrames;
            this.totalJsFrames = totalJsFrames;
            this.totalExpectedFrames = totalExpectedFrames;
            this.total4PlusFrameStutters = total4PlusFrameStutters;
            this.fps = fps;
            this.jsFps = jsFps;
            this.totalTimeMs = totalTimeMs;
        }
    }

    public FpsDebugFrameCallback(Choreographer choreographer, ReactContext reactContext) {
        this.mChoreographer = choreographer;
        this.mReactContext = reactContext;
        this.mUIManagerModule = (UIManagerModule) reactContext.getNativeModule(UIManagerModule.class);
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long l) {
        if (!this.mShouldStop) {
            if (this.mFirstFrameTime == -1) {
                this.mFirstFrameTime = l;
            }
            long lastFrameStartTime = this.mLastFrameTime;
            this.mLastFrameTime = l;
            if (this.mDidJSUpdateUiDuringFrameDetector.getDidJSHitFrameAndCleanup(lastFrameStartTime, l)) {
                this.mNumFrameCallbacksWithBatchDispatches++;
            }
            this.mNumFrameCallbacks++;
            int expectedNumFrames = getExpectedNumFrames();
            int framesDropped = (expectedNumFrames - this.mExpectedNumFramesPrev) - 1;
            if (framesDropped >= 4) {
                this.m4PlusFrameStutters++;
            }
            if (this.mIsRecordingFpsInfoAtEachFrame) {
                Assertions.assertNotNull(this.mTimeToFps);
                FpsInfo info = new FpsInfo(getNumFrames(), getNumJSFrames(), expectedNumFrames, this.m4PlusFrameStutters, getFPS(), getJSFPS(), getTotalTimeMS());
                this.mTimeToFps.put(Long.valueOf(System.currentTimeMillis()), info);
            }
            this.mExpectedNumFramesPrev = expectedNumFrames;
            this.mChoreographer.postFrameCallback(this);
        }
    }

    public void start() {
        this.mShouldStop = false;
        this.mReactContext.getCatalystInstance().addBridgeIdleDebugListener(this.mDidJSUpdateUiDuringFrameDetector);
        this.mUIManagerModule.setViewHierarchyUpdateDebugListener(this.mDidJSUpdateUiDuringFrameDetector);
        this.mChoreographer.postFrameCallback(this);
    }

    public void startAndRecordFpsAtEachFrame() {
        this.mTimeToFps = new TreeMap<>();
        this.mIsRecordingFpsInfoAtEachFrame = true;
        start();
    }

    public void stop() {
        this.mShouldStop = true;
        this.mReactContext.getCatalystInstance().removeBridgeIdleDebugListener(this.mDidJSUpdateUiDuringFrameDetector);
        this.mUIManagerModule.setViewHierarchyUpdateDebugListener(null);
    }

    public double getFPS() {
        if (this.mLastFrameTime == this.mFirstFrameTime) {
            return 0.0d;
        }
        return (((double) getNumFrames()) * 1.0E9d) / (this.mLastFrameTime - this.mFirstFrameTime);
    }

    public double getJSFPS() {
        if (this.mLastFrameTime == this.mFirstFrameTime) {
            return 0.0d;
        }
        return (((double) getNumJSFrames()) * 1.0E9d) / (this.mLastFrameTime - this.mFirstFrameTime);
    }

    public int getNumFrames() {
        return this.mNumFrameCallbacks - 1;
    }

    public int getNumJSFrames() {
        return this.mNumFrameCallbacksWithBatchDispatches - 1;
    }

    public int getExpectedNumFrames() {
        double totalTimeMS = getTotalTimeMS();
        int expectedFrames = (int) ((totalTimeMS / 16.9d) + 1.0d);
        return expectedFrames;
    }

    public int get4PlusFrameStutters() {
        return this.m4PlusFrameStutters;
    }

    public int getTotalTimeMS() {
        return ((int) (this.mLastFrameTime - this.mFirstFrameTime)) / 1000000;
    }

    public FpsInfo getFpsInfo(long upToTimeMs) {
        Assertions.assertNotNull(this.mTimeToFps, "FPS was not recorded at each frame!");
        Map.Entry<Long, FpsInfo> bestEntry = this.mTimeToFps.floorEntry(Long.valueOf(upToTimeMs));
        if (bestEntry == null) {
            return null;
        }
        return bestEntry.getValue();
    }

    public void reset() {
        this.mFirstFrameTime = -1L;
        this.mLastFrameTime = -1L;
        this.mNumFrameCallbacks = 0;
        this.m4PlusFrameStutters = 0;
        this.mNumFrameCallbacksWithBatchDispatches = 0;
        this.mIsRecordingFpsInfoAtEachFrame = false;
        this.mTimeToFps = null;
    }
}
