package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ModulusAnimatedNode extends ValueAnimatedNode {
    private final int mInputNode;
    private final int mModulus;
    private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;

    public ModulusAnimatedNode(ReadableMap config, NativeAnimatedNodesManager nativeAnimatedNodesManager) {
        this.mNativeAnimatedNodesManager = nativeAnimatedNodesManager;
        this.mInputNode = config.getInt("input");
        this.mModulus = config.getInt("modulus");
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public void update() {
        AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(this.mInputNode);
        if (animatedNode != null && (animatedNode instanceof ValueAnimatedNode)) {
            this.mValue = ((ValueAnimatedNode) animatedNode).mValue % ((double) this.mModulus);
            return;
        }
        throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.modulus node");
    }
}
