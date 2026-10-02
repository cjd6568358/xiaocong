package com.facebook.react.uimanager;

import android.util.SparseBooleanArray;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.views.view.ReactViewManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NativeViewHierarchyOptimizer {
    private final ShadowNodeRegistry mShadowNodeRegistry;
    private final SparseBooleanArray mTagsWithLayoutVisited = new SparseBooleanArray();
    private final UIViewOperationQueue mUIViewOperationQueue;

    private static class NodeIndexPair {
        public final int index;
        public final ReactShadowNode node;

        NodeIndexPair(ReactShadowNode node, int index) {
            this.node = node;
            this.index = index;
        }
    }

    public NativeViewHierarchyOptimizer(UIViewOperationQueue uiViewOperationQueue, ShadowNodeRegistry shadowNodeRegistry) {
        this.mUIViewOperationQueue = uiViewOperationQueue;
        this.mShadowNodeRegistry = shadowNodeRegistry;
    }

    public void handleCreateView(ReactShadowNode node, ThemedReactContext themedContext, ReactStylesDiffMap initialProps) {
        boolean isLayoutOnly = node.getViewClass().equals(ReactViewManager.REACT_CLASS) && isLayoutOnlyAndCollapsable(initialProps);
        node.setIsLayoutOnly(isLayoutOnly);
        if (!isLayoutOnly) {
            this.mUIViewOperationQueue.enqueueCreateView(themedContext, node.getReactTag(), node.getViewClass(), initialProps);
        }
    }

    public static void handleRemoveNode(ReactShadowNode node) {
        node.removeAllNativeChildren();
    }

    public void handleUpdateView(ReactShadowNode node, String className, ReactStylesDiffMap props) {
        boolean needsToLeaveLayoutOnly = node.isLayoutOnly() && !isLayoutOnlyAndCollapsable(props);
        if (needsToLeaveLayoutOnly) {
            transitionLayoutOnlyViewToNativeView(node, props);
        } else if (!node.isLayoutOnly()) {
            this.mUIViewOperationQueue.enqueueUpdateProperties(node.getReactTag(), className, props);
        }
    }

    public void handleManageChildren(ReactShadowNode nodeToManage, int[] indicesToRemove, int[] tagsToRemove, ViewAtIndex[] viewsToAdd, int[] tagsToDelete) {
        for (int tagToRemove : tagsToRemove) {
            boolean delete = false;
            for (int i : tagsToDelete) {
                if (i == tagToRemove) {
                    delete = true;
                    break;
                }
            }
            ReactShadowNode nodeToRemove = this.mShadowNodeRegistry.getNode(tagToRemove);
            removeNodeFromParent(nodeToRemove, delete);
        }
        for (ViewAtIndex toAdd : viewsToAdd) {
            ReactShadowNode nodeToAdd = this.mShadowNodeRegistry.getNode(toAdd.mTag);
            addNodeToNode(nodeToManage, nodeToAdd, toAdd.mIndex);
        }
    }

    public void handleSetChildren(ReactShadowNode nodeToManage, ReadableArray childrenTags) {
        for (int i = 0; i < childrenTags.size(); i++) {
            ReactShadowNode nodeToAdd = this.mShadowNodeRegistry.getNode(childrenTags.getInt(i));
            addNodeToNode(nodeToManage, nodeToAdd, i);
        }
    }

    public void handleUpdateLayout(ReactShadowNode node) {
        applyLayoutBase(node);
    }

    public void onBatchComplete() {
        this.mTagsWithLayoutVisited.clear();
    }

    private NodeIndexPair walkUpUntilNonLayoutOnly(ReactShadowNode node, int indexInNativeChildren) {
        while (node.isLayoutOnly()) {
            ReactShadowNode parent = node.getParent();
            if (parent == null) {
                return null;
            }
            indexInNativeChildren += parent.getNativeOffsetForChild(node);
            node = parent;
        }
        return new NodeIndexPair(node, indexInNativeChildren);
    }

    private void addNodeToNode(ReactShadowNode parent, ReactShadowNode child, int index) {
        int indexInNativeChildren = parent.getNativeOffsetForChild(parent.getChildAt(index));
        if (parent.isLayoutOnly()) {
            NodeIndexPair result = walkUpUntilNonLayoutOnly(parent, indexInNativeChildren);
            if (result != null) {
                parent = result.node;
                indexInNativeChildren = result.index;
            } else {
                return;
            }
        }
        if (!child.isLayoutOnly()) {
            addNonLayoutNode(parent, child, indexInNativeChildren);
        } else {
            addLayoutOnlyNode(parent, child, indexInNativeChildren);
        }
    }

    private void removeNodeFromParent(ReactShadowNode nodeToRemove, boolean shouldDelete) {
        ReactShadowNode nativeNodeToRemoveFrom = nodeToRemove.getNativeParent();
        if (nativeNodeToRemoveFrom != null) {
            int index = nativeNodeToRemoveFrom.indexOfNativeChild(nodeToRemove);
            nativeNodeToRemoveFrom.removeNativeChildAt(index);
            this.mUIViewOperationQueue.enqueueManageChildren(nativeNodeToRemoveFrom.getReactTag(), new int[]{index}, null, shouldDelete ? new int[]{nodeToRemove.getReactTag()} : null);
        } else {
            for (int i = nodeToRemove.getChildCount() - 1; i >= 0; i--) {
                removeNodeFromParent(nodeToRemove.getChildAt(i), shouldDelete);
            }
        }
    }

    private void addLayoutOnlyNode(ReactShadowNode nonLayoutOnlyNode, ReactShadowNode layoutOnlyNode, int index) {
        addGrandchildren(nonLayoutOnlyNode, layoutOnlyNode, index);
    }

    private void addNonLayoutNode(ReactShadowNode parent, ReactShadowNode child, int index) {
        parent.addNativeChildAt(child, index);
        this.mUIViewOperationQueue.enqueueManageChildren(parent.getReactTag(), null, new ViewAtIndex[]{new ViewAtIndex(child.getReactTag(), index)}, null);
    }

    private void addGrandchildren(ReactShadowNode nativeParent, ReactShadowNode child, int index) {
        Assertions.assertCondition(!nativeParent.isLayoutOnly());
        int currentIndex = index;
        for (int i = 0; i < child.getChildCount(); i++) {
            ReactShadowNode grandchild = child.getChildAt(i);
            Assertions.assertCondition(grandchild.getNativeParent() == null);
            if (grandchild.isLayoutOnly()) {
                int grandchildCountBefore = nativeParent.getNativeChildCount();
                addLayoutOnlyNode(nativeParent, grandchild, currentIndex);
                int grandchildCountAfter = nativeParent.getNativeChildCount();
                currentIndex += grandchildCountAfter - grandchildCountBefore;
            } else {
                addNonLayoutNode(nativeParent, grandchild, currentIndex);
                currentIndex++;
            }
        }
    }

    private void applyLayoutBase(ReactShadowNode node) {
        int tag = node.getReactTag();
        if (!this.mTagsWithLayoutVisited.get(tag)) {
            this.mTagsWithLayoutVisited.put(tag, true);
            int x = node.getScreenX();
            int y = node.getScreenY();
            for (ReactShadowNode parent = node.getParent(); parent != null && parent.isLayoutOnly(); parent = parent.getParent()) {
                x += Math.round(parent.getLayoutX());
                y += Math.round(parent.getLayoutY());
            }
            applyLayoutRecursive(node, x, y);
        }
    }

    private void applyLayoutRecursive(ReactShadowNode toUpdate, int x, int y) {
        if (!toUpdate.isLayoutOnly() && toUpdate.getNativeParent() != null) {
            int tag = toUpdate.getReactTag();
            this.mUIViewOperationQueue.enqueueUpdateLayout(toUpdate.getNativeParent().getReactTag(), tag, x, y, toUpdate.getScreenWidth(), toUpdate.getScreenHeight());
            return;
        }
        for (int i = 0; i < toUpdate.getChildCount(); i++) {
            ReactShadowNode child = toUpdate.getChildAt(i);
            int childTag = child.getReactTag();
            if (!this.mTagsWithLayoutVisited.get(childTag)) {
                this.mTagsWithLayoutVisited.put(childTag, true);
                int childX = child.getScreenX();
                int childY = child.getScreenY();
                applyLayoutRecursive(child, childX + x, childY + y);
            }
        }
    }

    private void transitionLayoutOnlyViewToNativeView(ReactShadowNode node, ReactStylesDiffMap props) {
        ReactShadowNode parent = node.getParent();
        if (parent == null) {
            node.setIsLayoutOnly(false);
            return;
        }
        int childIndex = parent.indexOf(node);
        parent.removeChildAt(childIndex);
        removeNodeFromParent(node, false);
        node.setIsLayoutOnly(false);
        this.mUIViewOperationQueue.enqueueCreateView(node.getRootNode().getThemedContext(), node.getReactTag(), node.getViewClass(), props);
        parent.addChildAt(node, childIndex);
        addNodeToNode(parent, node, childIndex);
        for (int i = 0; i < node.getChildCount(); i++) {
            addNodeToNode(node, node.getChildAt(i), i);
        }
        Assertions.assertCondition(this.mTagsWithLayoutVisited.size() == 0);
        applyLayoutBase(node);
        for (int i2 = 0; i2 < node.getChildCount(); i2++) {
            applyLayoutBase(node.getChildAt(i2));
        }
        this.mTagsWithLayoutVisited.clear();
    }

    private static boolean isLayoutOnlyAndCollapsable(ReactStylesDiffMap props) {
        if (props == null) {
            return true;
        }
        if (props.hasKey("collapsable") && !props.getBoolean("collapsable", true)) {
            return false;
        }
        ReadableMapKeySetIterator keyIterator = props.mBackingMap.keySetIterator();
        while (keyIterator.hasNextKey()) {
            if (!ViewProps.isLayoutOnly(props.mBackingMap, keyIterator.nextKey())) {
                return false;
            }
        }
        return true;
    }
}
