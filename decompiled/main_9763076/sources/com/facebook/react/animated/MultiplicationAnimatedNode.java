package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class MultiplicationAnimatedNode extends ValueAnimatedNode {
    private final int[] mInputNodes;
    private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;

    public MultiplicationAnimatedNode(ReadableMap config, NativeAnimatedNodesManager nativeAnimatedNodesManager) {
        this.mNativeAnimatedNodesManager = nativeAnimatedNodesManager;
        ReadableArray inputNodes = config.getArray("input");
        this.mInputNodes = new int[inputNodes.size()];
        for (int i = 0; i < this.mInputNodes.length; i++) {
            this.mInputNodes[i] = inputNodes.getInt(i);
        }
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public void update() {
        this.mValue = 1.0d;
        for (int i = 0; i < this.mInputNodes.length; i++) {
            AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(this.mInputNodes[i]);
            if (animatedNode != null && (animatedNode instanceof ValueAnimatedNode)) {
                this.mValue *= ((ValueAnimatedNode) animatedNode).getValue();
            } else {
                throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.multiply node");
            }
        }
    }
}
