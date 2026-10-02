package com.facebook.react.modules.debug;

import android.os.Build;
import android.view.Choreographer;
import android.widget.Toast;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AnimationsDebugModule extends ReactContextBaseJavaModule {
    protected static final String NAME = "AnimationsDebugModule";
    private final DeveloperSettings mCatalystSettings;
    private FpsDebugFrameCallback mFrameCallback;

    public AnimationsDebugModule(ReactApplicationContext reactContext, DeveloperSettings catalystSettings) {
        super(reactContext);
        this.mCatalystSettings = catalystSettings;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void startRecordingFps() {
        if (this.mCatalystSettings != null && this.mCatalystSettings.isAnimationFpsDebugEnabled()) {
            if (this.mFrameCallback != null) {
                throw new JSApplicationCausedNativeException("Already recording FPS!");
            }
            checkAPILevel();
            this.mFrameCallback = new FpsDebugFrameCallback(Choreographer.getInstance(), getReactApplicationContext());
            this.mFrameCallback.startAndRecordFpsAtEachFrame();
        }
    }

    @ReactMethod
    public void stopRecordingFps(double animationStopTimeMs) {
        if (this.mFrameCallback != null) {
            checkAPILevel();
            this.mFrameCallback.stop();
            FpsDebugFrameCallback.FpsInfo fpsInfo = this.mFrameCallback.getFpsInfo((long) animationStopTimeMs);
            if (fpsInfo == null) {
                Toast.makeText(getReactApplicationContext(), "Unable to get FPS info", 1);
            } else {
                String fpsString = String.format(Locale.US, "FPS: %.2f, %d frames (%d expected)", Double.valueOf(fpsInfo.fps), Integer.valueOf(fpsInfo.totalFrames), Integer.valueOf(fpsInfo.totalExpectedFrames));
                String jsFpsString = String.format(Locale.US, "JS FPS: %.2f, %d frames (%d expected)", Double.valueOf(fpsInfo.jsFps), Integer.valueOf(fpsInfo.totalJsFrames), Integer.valueOf(fpsInfo.totalExpectedFrames));
                String debugString = fpsString + "\n" + jsFpsString + "\nTotal Time MS: " + String.format(Locale.US, "%d", Integer.valueOf(fpsInfo.totalTimeMs));
                FLog.d("React", debugString);
                Toast.makeText(getReactApplicationContext(), debugString, 1).show();
            }
            this.mFrameCallback = null;
        }
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        if (this.mFrameCallback != null) {
            this.mFrameCallback.stop();
            this.mFrameCallback = null;
        }
    }

    private static void checkAPILevel() {
        if (Build.VERSION.SDK_INT < 16) {
            throw new JSApplicationCausedNativeException("Animation debugging is not supported in API <16");
        }
    }
}
