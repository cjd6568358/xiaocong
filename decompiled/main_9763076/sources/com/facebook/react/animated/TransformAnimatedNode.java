package com.facebook.react.animated;

import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class TransformAnimatedNode extends AnimatedNode {
    private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
    private final List<TransformConfig> mTransformConfigs;

    private class TransformConfig {
        public String mProperty;

        private TransformConfig() {
        }
    }

    private class AnimatedTransformConfig extends TransformConfig {
        public int mNodeTag;

        private AnimatedTransformConfig() {
            super();
        }
    }

    private class StaticTransformConfig extends TransformConfig {
        public double mValue;

        private StaticTransformConfig() {
            super();
        }
    }

    TransformAnimatedNode(ReadableMap config, NativeAnimatedNodesManager nativeAnimatedNodesManager) {
        ReadableArray transforms = config.getArray("transforms");
        this.mTransformConfigs = new ArrayList(transforms.size());
        for (int i = 0; i < transforms.size(); i++) {
            ReadableMap transformConfigMap = transforms.getMap(i);
            String property = transformConfigMap.getString("property");
            String type = transformConfigMap.getString("type");
            if (type.equals("animated")) {
                AnimatedTransformConfig transformConfig = new AnimatedTransformConfig();
                transformConfig.mProperty = property;
                transformConfig.mNodeTag = transformConfigMap.getInt("nodeTag");
                this.mTransformConfigs.add(transformConfig);
            } else {
                StaticTransformConfig transformConfig2 = new StaticTransformConfig();
                transformConfig2.mProperty = property;
                transformConfig2.mValue = transformConfigMap.getDouble("value");
                this.mTransformConfigs.add(transformConfig2);
            }
        }
        this.mNativeAnimatedNodesManager = nativeAnimatedNodesManager;
    }

    public void collectViewUpdates(JavaOnlyMap propsMap) {
        double value;
        List<JavaOnlyMap> transforms = new ArrayList<>(this.mTransformConfigs.size());
        for (TransformConfig transformConfig : this.mTransformConfigs) {
            if (transformConfig instanceof AnimatedTransformConfig) {
                int nodeTag = ((AnimatedTransformConfig) transformConfig).mNodeTag;
                AnimatedNode node = this.mNativeAnimatedNodesManager.getNodeById(nodeTag);
                if (node == null) {
                    throw new IllegalArgumentException("Mapped style node does not exists");
                }
                if (node instanceof ValueAnimatedNode) {
                    value = ((ValueAnimatedNode) node).getValue();
                } else {
                    throw new IllegalArgumentException("Unsupported type of node used as a transform child node " + node.getClass());
                }
            } else {
                value = ((StaticTransformConfig) transformConfig).mValue;
            }
            transforms.add(JavaOnlyMap.of(transformConfig.mProperty, Double.valueOf(value)));
        }
        propsMap.putArray("transform", JavaOnlyArray.from(transforms));
    }
}
