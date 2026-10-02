package com.facebook.react.devsupport;

import android.annotation.TargetApi;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.facebook.common.logging.FLog;
import com.facebook.react.modules.debug.FpsDebugFrameCallback;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@TargetApi(16)
public class FpsView extends FrameLayout {
    private final FPSMonitorRunnable mFPSMonitorRunnable;
    private final FpsDebugFrameCallback mFrameCallback;
    private final TextView mTextView;

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFrameCallback.reset();
        this.mFrameCallback.start();
        this.mFPSMonitorRunnable.start();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFrameCallback.stop();
        this.mFPSMonitorRunnable.stop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentFPS(double currentFPS, double currentJSFPS, int droppedUIFrames, int total4PlusFrameStutters) {
        String fpsString = String.format(Locale.US, "UI: %.1f fps\n%d dropped so far\n%d stutters (4+) so far\nJS: %.1f fps", Double.valueOf(currentFPS), Integer.valueOf(droppedUIFrames), Integer.valueOf(total4PlusFrameStutters), Double.valueOf(currentJSFPS));
        this.mTextView.setText(fpsString);
        FLog.d("React", fpsString);
    }

    private class FPSMonitorRunnable implements Runnable {
        private boolean mShouldStop;
        private int mTotal4PlusFrameStutters;
        private int mTotalFramesDropped;
        final /* synthetic */ FpsView this$0;

        @Override // java.lang.Runnable
        public void run() {
            if (!this.mShouldStop) {
                this.mTotalFramesDropped += this.this$0.mFrameCallback.getExpectedNumFrames() - this.this$0.mFrameCallback.getNumFrames();
                this.mTotal4PlusFrameStutters += this.this$0.mFrameCallback.get4PlusFrameStutters();
                this.this$0.setCurrentFPS(this.this$0.mFrameCallback.getFPS(), this.this$0.mFrameCallback.getJSFPS(), this.mTotalFramesDropped, this.mTotal4PlusFrameStutters);
                this.this$0.mFrameCallback.reset();
                this.this$0.postDelayed(this, 500L);
            }
        }

        public void start() {
            this.mShouldStop = false;
            this.this$0.post(this);
        }

        public void stop() {
            this.mShouldStop = true;
        }
    }
}
