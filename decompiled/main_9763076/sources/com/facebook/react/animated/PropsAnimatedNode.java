package com.facebook.react.animated;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIImplementation;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class PropsAnimatedNode extends AnimatedNode {
    int mConnectedViewTag = -1;
    private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
    private final Map<String, Integer> mPropMapping;

    PropsAnimatedNode(ReadableMap config, NativeAnimatedNodesManager nativeAnimatedNodesManager) {
        ReadableMap props = config.getMap("props");
        ReadableMapKeySetIterator iter = props.keySetIterator();
        this.mPropMapping = new HashMap();
        while (iter.hasNextKey()) {
            String propKey = iter.nextKey();
            int nodeIndex = props.getInt(propKey);
            this.mPropMapping.put(propKey, Integer.valueOf(nodeIndex));
        }
        this.mNativeAnimatedNodesManager = nativeAnimatedNodesManager;
    }

    public final void updateView(UIImplementation uiImplementation) {
        if (this.mConnectedViewTag == -1) {
            throw new IllegalStateException("Node has not been attached to a view");
        }
        JavaOnlyMap propsMap = new JavaOnlyMap();
        for (Map.Entry<String, Integer> entry : this.mPropMapping.entrySet()) {
            AnimatedNode node = this.mNativeAnimatedNodesManager.getNodeById(entry.getValue().intValue());
            if (node == null) {
                throw new IllegalArgumentException("Mapped property node does not exists");
            }
            if (node instanceof StyleAnimatedNode) {
                ((StyleAnimatedNode) node).collectViewUpdates(propsMap);
            } else if (node instanceof ValueAnimatedNode) {
                propsMap.putDouble(entry.getKey(), ((ValueAnimatedNode) node).getValue());
            } else {
                throw new IllegalArgumentException("Unsupported type of node used in property node " + node.getClass());
            }
        }
        uiImplementation.synchronouslyUpdateViewOnUIThread(this.mConnectedViewTag, new ReactStylesDiffMap(propsMap));
    }
}
