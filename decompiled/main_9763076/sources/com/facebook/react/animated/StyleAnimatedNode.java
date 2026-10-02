package com.facebook.react.animated;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class StyleAnimatedNode extends AnimatedNode {
    private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
    private final Map<String, Integer> mPropMapping;

    StyleAnimatedNode(ReadableMap config, NativeAnimatedNodesManager nativeAnimatedNodesManager) {
        ReadableMap style = config.getMap("style");
        ReadableMapKeySetIterator iter = style.keySetIterator();
        this.mPropMapping = new HashMap();
        while (iter.hasNextKey()) {
            String propKey = iter.nextKey();
            int nodeIndex = style.getInt(propKey);
            this.mPropMapping.put(propKey, Integer.valueOf(nodeIndex));
        }
        this.mNativeAnimatedNodesManager = nativeAnimatedNodesManager;
    }

    public void collectViewUpdates(JavaOnlyMap propsMap) {
        for (Map.Entry<String, Integer> entry : this.mPropMapping.entrySet()) {
            AnimatedNode node = this.mNativeAnimatedNodesManager.getNodeById(entry.getValue().intValue());
            if (node == null) {
                throw new IllegalArgumentException("Mapped style node does not exists");
            }
            if (node instanceof TransformAnimatedNode) {
                ((TransformAnimatedNode) node).collectViewUpdates(propsMap);
            } else if (node instanceof ValueAnimatedNode) {
                propsMap.putDouble(entry.getKey(), ((ValueAnimatedNode) node).getValue());
            } else {
                throw new IllegalArgumentException("Unsupported type of node used in property node " + node.getClass());
            }
        }
    }
}
