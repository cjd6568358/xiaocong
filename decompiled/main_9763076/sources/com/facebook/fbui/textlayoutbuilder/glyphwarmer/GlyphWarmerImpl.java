package com.facebook.fbui.textlayoutbuilder.glyphwarmer;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Layout;
import com.facebook.fbui.textlayoutbuilder.GlyphWarmer;
import com.facebook.fbui.textlayoutbuilder.util.LayoutMeasureUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GlyphWarmerImpl implements GlyphWarmer {
    private static WarmHandler sWarmHandler;

    @Override // com.facebook.fbui.textlayoutbuilder.GlyphWarmer
    public void warmLayout(Layout layout) {
        WarmHandler handler = getWarmHandler();
        handler.sendMessage(handler.obtainMessage(1, layout));
    }

    @SuppressLint({"BadMethodUse-android.os.HandlerThread._Constructor", "BadMethodUse-java.lang.Thread.start"})
    private WarmHandler getWarmHandler() {
        if (sWarmHandler == null) {
            HandlerThread warmerThread = new HandlerThread("GlyphWarmer");
            warmerThread.start();
            sWarmHandler = new WarmHandler(warmerThread.getLooper());
        }
        return sWarmHandler;
    }

    private static class WarmHandler extends Handler {
        private final Picture mPicture;

        public WarmHandler(Looper looper) {
            super(looper);
            this.mPicture = new Picture();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Layout layout = (Layout) msg.obj;
            try {
                Canvas canvas = this.mPicture.beginRecording(LayoutMeasureUtil.getWidth(layout), LayoutMeasureUtil.getHeight(layout));
                layout.draw(canvas);
                this.mPicture.endRecording();
            } catch (Exception e) {
            }
        }
    }
}
