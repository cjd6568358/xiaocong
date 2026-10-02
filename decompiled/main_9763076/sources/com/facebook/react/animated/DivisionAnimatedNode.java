package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class DivisionAnimatedNode extends ValueAnimatedNode {
    private final int[] mInputNodes;
    private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;

    public DivisionAnimatedNode(ReadableMap config, NativeAnimatedNodesManager nativeAnimatedNodesManager) {
        this.mNativeAnimatedNodesManager = nativeAnimatedNodesManager;
        ReadableArray inputNodes = config.getArray("input");
        this.mInputNodes = new int[inputNodes.size()];
        for (int i = 0; i < this.mInputNodes.length; i++) {
            this.mInputNodes[i] = inputNodes.getInt(i);
        }
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public void update() {
        for (int i = 0; i < this.mInputNodes.length; i++) {
            AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(this.mInputNodes[i]);
            if (animatedNode != null && (animatedNode instanceof ValueAnimatedNode)) {
                double value = ((ValueAnimatedNode) animatedNode).getValue();
                if (i == 0) {
                    this.mValue = value;
                } else {
                    if (value == 0.0d) {
                        throw new JSApplicationCausedNativeException("Detected a division by zero in Animated.divide node");
                    }
                    this.mValue /= value;
                }
            } else {
                throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.divide node");
            }
        }
    }
}
