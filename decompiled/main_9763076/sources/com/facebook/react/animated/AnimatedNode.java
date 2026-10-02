package com.facebook.react.animated;

import com.facebook.infer.annotation.Assertions;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class AnimatedNode {
    List<AnimatedNode> mChildren;
    int mActiveIncomingNodes = 0;
    int mBFSColor = 0;
    int mTag = -1;

    AnimatedNode() {
    }

    public final void addChild(AnimatedNode child) {
        if (this.mChildren == null) {
            this.mChildren = new ArrayList(1);
        }
        ((List) Assertions.assertNotNull(this.mChildren)).add(child);
        child.onAttachedToNode(this);
    }

    public final void removeChild(AnimatedNode child) {
        if (this.mChildren != null) {
            child.onDetachedFromNode(this);
            this.mChildren.remove(child);
        }
    }

    public void onAttachedToNode(AnimatedNode parent) {
    }

    public void onDetachedFromNode(AnimatedNode parent) {
    }

    public void update() {
    }
}
