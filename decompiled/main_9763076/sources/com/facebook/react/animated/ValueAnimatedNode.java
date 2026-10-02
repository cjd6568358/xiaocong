package com.facebook.react.animated;

import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ValueAnimatedNode extends AnimatedNode {
    double mOffset;
    double mValue;
    private AnimatedNodeValueListener mValueListener;

    public ValueAnimatedNode() {
        this.mValue = Double.NaN;
        this.mOffset = 0.0d;
    }

    public ValueAnimatedNode(ReadableMap config) {
        this.mValue = Double.NaN;
        this.mOffset = 0.0d;
        this.mValue = config.getDouble("value");
        this.mOffset = config.getDouble(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_OFFSET);
    }

    public double getValue() {
        return this.mOffset + this.mValue;
    }

    public void flattenOffset() {
        this.mValue += this.mOffset;
        this.mOffset = 0.0d;
    }

    public void extractOffset() {
        this.mOffset += this.mValue;
        this.mValue = 0.0d;
    }

    public void onValueUpdate() {
        if (this.mValueListener != null) {
            this.mValueListener.onValueUpdate(this.mValue);
        }
    }

    public void setValueListener(AnimatedNodeValueListener listener) {
        this.mValueListener = listener;
    }
}
