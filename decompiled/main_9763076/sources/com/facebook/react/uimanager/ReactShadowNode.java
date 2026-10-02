package com.facebook.react.uimanager;

import com.facebook.infer.annotation.Assertions;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaValue;
import com.facebook.yoga.YogaWrap;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactShadowNode {
    private float mAbsoluteBottom;
    private float mAbsoluteLeft;
    private float mAbsoluteRight;
    private float mAbsoluteTop;
    private ArrayList<ReactShadowNode> mChildren;
    private boolean mIsLayoutOnly;
    private ArrayList<ReactShadowNode> mNativeChildren;
    private ReactShadowNode mNativeParent;
    private ReactShadowNode mParent;
    private int mReactTag;
    private ReactShadowNode mRootNode;
    private boolean mShouldNotifyOnLayout;
    private ThemedReactContext mThemedContext;
    private String mViewClassName;
    private final YogaNode mYogaNode;
    private boolean mNodeUpdated = true;
    private int mTotalNativeChildren = 0;
    private final Spacing mDefaultPadding = new Spacing(0.0f);
    private final float[] mPadding = new float[9];
    private final boolean[] mPaddingIsPercent = new boolean[9];

    public ReactShadowNode() {
        if (!isVirtual()) {
            YogaNode node = YogaNodePool.get().acquire();
            this.mYogaNode = node == null ? new YogaNode() : node;
            Arrays.fill(this.mPadding, Float.NaN);
            return;
        }
        this.mYogaNode = null;
    }

    public boolean isVirtual() {
        return false;
    }

    public boolean isVirtualAnchor() {
        return false;
    }

    public final String getViewClass() {
        return (String) Assertions.assertNotNull(this.mViewClassName);
    }

    public final boolean hasUpdates() {
        return this.mNodeUpdated || hasNewLayout() || isDirty();
    }

    public final void markUpdateSeen() {
        this.mNodeUpdated = false;
        if (hasNewLayout()) {
            markLayoutSeen();
        }
    }

    public void markUpdated() {
        if (!this.mNodeUpdated) {
            this.mNodeUpdated = true;
            ReactShadowNode parent = getParent();
            if (parent != null) {
                parent.markUpdated();
            }
        }
    }

    public void dirty() {
        if (!isVirtual()) {
            this.mYogaNode.dirty();
        }
    }

    public final boolean isDirty() {
        return this.mYogaNode != null && this.mYogaNode.isDirty();
    }

    public void addChildAt(ReactShadowNode child, int i) {
        if (child.mParent != null) {
            throw new IllegalViewOperationException("Tried to add child that already has a parent! Remove it from its parent first.");
        }
        if (this.mChildren == null) {
            this.mChildren = new ArrayList<>(4);
        }
        this.mChildren.add(i, child);
        child.mParent = this;
        if (this.mYogaNode != null && !this.mYogaNode.isMeasureDefined()) {
            YogaNode childYogaNode = child.mYogaNode;
            if (childYogaNode == null) {
                throw new RuntimeException("Cannot add a child that doesn't have a YogaNode to a parent without a measure function! (Trying to add a '" + child.getClass().getSimpleName() + "' to a '" + getClass().getSimpleName() + "')");
            }
            this.mYogaNode.addChildAt(childYogaNode, i);
        }
        markUpdated();
        int increase = child.mIsLayoutOnly ? child.mTotalNativeChildren : 1;
        this.mTotalNativeChildren += increase;
        updateNativeChildrenCountInParent(increase);
    }

    public ReactShadowNode removeChildAt(int i) {
        if (this.mChildren == null) {
            throw new ArrayIndexOutOfBoundsException("Index " + i + " out of bounds: node has no children");
        }
        ReactShadowNode removed = this.mChildren.remove(i);
        removed.mParent = null;
        if (this.mYogaNode != null && !this.mYogaNode.isMeasureDefined()) {
            this.mYogaNode.removeChildAt(i);
        }
        markUpdated();
        int decrease = removed.mIsLayoutOnly ? removed.mTotalNativeChildren : 1;
        this.mTotalNativeChildren -= decrease;
        updateNativeChildrenCountInParent(-decrease);
        return removed;
    }

    public final int getChildCount() {
        if (this.mChildren == null) {
            return 0;
        }
        return this.mChildren.size();
    }

    public final ReactShadowNode getChildAt(int i) {
        if (this.mChildren == null) {
            throw new ArrayIndexOutOfBoundsException("Index " + i + " out of bounds: node has no children");
        }
        return this.mChildren.get(i);
    }

    public final int indexOf(ReactShadowNode child) {
        if (this.mChildren == null) {
            return -1;
        }
        return this.mChildren.indexOf(child);
    }

    public void removeAndDisposeAllChildren() {
        if (getChildCount() != 0) {
            int decrease = 0;
            for (int i = getChildCount() - 1; i >= 0; i--) {
                if (this.mYogaNode != null && !this.mYogaNode.isMeasureDefined()) {
                    this.mYogaNode.removeChildAt(i);
                }
                ReactShadowNode toRemove = getChildAt(i);
                toRemove.mParent = null;
                toRemove.dispose();
                decrease += toRemove.mIsLayoutOnly ? toRemove.mTotalNativeChildren : 1;
            }
            ((ArrayList) Assertions.assertNotNull(this.mChildren)).clear();
            markUpdated();
            this.mTotalNativeChildren -= decrease;
            updateNativeChildrenCountInParent(-decrease);
        }
    }

    private void updateNativeChildrenCountInParent(int delta) {
        if (this.mIsLayoutOnly) {
            for (ReactShadowNode parent = getParent(); parent != null; parent = parent.getParent()) {
                parent.mTotalNativeChildren += delta;
                if (!parent.mIsLayoutOnly) {
                    return;
                }
            }
        }
    }

    public void onBeforeLayout() {
    }

    public final void updateProperties(ReactStylesDiffMap props) {
        ViewManagerPropertyUpdater.updateProps(this, props);
        onAfterUpdateTransaction();
    }

    public void onAfterUpdateTransaction() {
    }

    public void onCollectExtraUpdates(UIViewOperationQueue uiViewOperationQueue) {
    }

    boolean dispatchUpdates(float absoluteX, float absoluteY, UIViewOperationQueue uiViewOperationQueue, NativeViewHierarchyOptimizer nativeViewHierarchyOptimizer) {
        if (this.mNodeUpdated) {
            onCollectExtraUpdates(uiViewOperationQueue);
        }
        if (!hasNewLayout()) {
            return false;
        }
        this.mAbsoluteLeft = Math.round(getLayoutX() + absoluteX);
        this.mAbsoluteTop = Math.round(getLayoutY() + absoluteY);
        this.mAbsoluteRight = Math.round(getLayoutX() + absoluteX + getLayoutWidth());
        this.mAbsoluteBottom = Math.round(getLayoutY() + absoluteY + getLayoutHeight());
        nativeViewHierarchyOptimizer.handleUpdateLayout(this);
        return true;
    }

    public final int getReactTag() {
        return this.mReactTag;
    }

    public void setReactTag(int reactTag) {
        this.mReactTag = reactTag;
    }

    public final ReactShadowNode getRootNode() {
        return (ReactShadowNode) Assertions.assertNotNull(this.mRootNode);
    }

    final void setRootNode(ReactShadowNode rootNode) {
        this.mRootNode = rootNode;
    }

    final void setViewClassName(String viewClassName) {
        this.mViewClassName = viewClassName;
    }

    public final ReactShadowNode getParent() {
        return this.mParent;
    }

    public final ThemedReactContext getThemedContext() {
        return (ThemedReactContext) Assertions.assertNotNull(this.mThemedContext);
    }

    public void setThemedContext(ThemedReactContext themedContext) {
        this.mThemedContext = themedContext;
    }

    public final boolean shouldNotifyOnLayout() {
        return this.mShouldNotifyOnLayout;
    }

    public void calculateLayout() {
        this.mYogaNode.calculateLayout();
    }

    public final boolean hasNewLayout() {
        if (this.mYogaNode == null) {
            return false;
        }
        return this.mYogaNode.hasNewLayout();
    }

    public final void markLayoutSeen() {
        if (this.mYogaNode != null) {
            this.mYogaNode.markLayoutSeen();
        }
    }

    public final void addNativeChildAt(ReactShadowNode child, int nativeIndex) {
        Assertions.assertCondition(!this.mIsLayoutOnly);
        Assertions.assertCondition(child.mIsLayoutOnly ? false : true);
        if (this.mNativeChildren == null) {
            this.mNativeChildren = new ArrayList<>(4);
        }
        this.mNativeChildren.add(nativeIndex, child);
        child.mNativeParent = this;
    }

    public final ReactShadowNode removeNativeChildAt(int i) {
        Assertions.assertNotNull(this.mNativeChildren);
        ReactShadowNode removed = this.mNativeChildren.remove(i);
        removed.mNativeParent = null;
        return removed;
    }

    public final void removeAllNativeChildren() {
        if (this.mNativeChildren != null) {
            for (int i = this.mNativeChildren.size() - 1; i >= 0; i--) {
                this.mNativeChildren.get(i).mNativeParent = null;
            }
            this.mNativeChildren.clear();
        }
    }

    public final int getNativeChildCount() {
        if (this.mNativeChildren == null) {
            return 0;
        }
        return this.mNativeChildren.size();
    }

    public final int indexOfNativeChild(ReactShadowNode nativeChild) {
        Assertions.assertNotNull(this.mNativeChildren);
        return this.mNativeChildren.indexOf(nativeChild);
    }

    public final ReactShadowNode getNativeParent() {
        return this.mNativeParent;
    }

    public final void setIsLayoutOnly(boolean isLayoutOnly) {
        Assertions.assertCondition(getParent() == null, "Must remove from no opt parent first");
        Assertions.assertCondition(this.mNativeParent == null, "Must remove from native parent first");
        Assertions.assertCondition(getNativeChildCount() == 0, "Must remove all native children first");
        this.mIsLayoutOnly = isLayoutOnly;
    }

    public final boolean isLayoutOnly() {
        return this.mIsLayoutOnly;
    }

    public final int getTotalNativeChildren() {
        return this.mTotalNativeChildren;
    }

    public final int getNativeOffsetForChild(ReactShadowNode child) {
        int index = 0;
        boolean found = false;
        for (int i = 0; i < getChildCount(); i++) {
            ReactShadowNode current = getChildAt(i);
            if (child == current) {
                found = true;
                break;
            }
            index += current.mIsLayoutOnly ? current.getTotalNativeChildren() : 1;
        }
        if (!found) {
            throw new RuntimeException("Child " + child.mReactTag + " was not a child of " + this.mReactTag);
        }
        return index;
    }

    public final float getLayoutX() {
        return this.mYogaNode.getLayoutX();
    }

    public final float getLayoutY() {
        return this.mYogaNode.getLayoutY();
    }

    public final float getLayoutWidth() {
        return this.mYogaNode.getLayoutWidth();
    }

    public final float getLayoutHeight() {
        return this.mYogaNode.getLayoutHeight();
    }

    public int getScreenX() {
        return Math.round(getLayoutX());
    }

    public int getScreenY() {
        return Math.round(getLayoutY());
    }

    public int getScreenWidth() {
        return Math.round(this.mAbsoluteRight - this.mAbsoluteLeft);
    }

    public int getScreenHeight() {
        return Math.round(this.mAbsoluteBottom - this.mAbsoluteTop);
    }

    public final YogaDirection getLayoutDirection() {
        return this.mYogaNode.getLayoutDirection();
    }

    public void setLayoutDirection(YogaDirection direction) {
        this.mYogaNode.setDirection(direction);
    }

    public void setStyleWidth(float widthPx) {
        this.mYogaNode.setWidth(widthPx);
    }

    public void setStyleWidthPercent(float percent) {
        this.mYogaNode.setWidthPercent(percent);
    }

    public void setStyleMinWidth(float widthPx) {
        this.mYogaNode.setMinWidth(widthPx);
    }

    public void setStyleMinWidthPercent(float percent) {
        this.mYogaNode.setMinWidthPercent(percent);
    }

    public void setStyleMaxWidth(float widthPx) {
        this.mYogaNode.setMaxWidth(widthPx);
    }

    public void setStyleMaxWidthPercent(float percent) {
        this.mYogaNode.setMaxWidthPercent(percent);
    }

    public void setStyleHeight(float heightPx) {
        this.mYogaNode.setHeight(heightPx);
    }

    public void setStyleHeightPercent(float percent) {
        this.mYogaNode.setHeightPercent(percent);
    }

    public void setStyleMinHeight(float widthPx) {
        this.mYogaNode.setMinHeight(widthPx);
    }

    public void setStyleMinHeightPercent(float percent) {
        this.mYogaNode.setMinHeightPercent(percent);
    }

    public void setStyleMaxHeight(float widthPx) {
        this.mYogaNode.setMaxHeight(widthPx);
    }

    public void setStyleMaxHeightPercent(float percent) {
        this.mYogaNode.setMaxHeightPercent(percent);
    }

    public void setFlex(float flex) {
        this.mYogaNode.setFlex(flex);
    }

    public void setFlexGrow(float flexGrow) {
        this.mYogaNode.setFlexGrow(flexGrow);
    }

    public void setFlexShrink(float flexShrink) {
        this.mYogaNode.setFlexShrink(flexShrink);
    }

    public void setFlexBasis(float flexBasis) {
        this.mYogaNode.setFlexBasis(flexBasis);
    }

    public void setFlexBasisPercent(float percent) {
        this.mYogaNode.setFlexBasisPercent(percent);
    }

    public void setStyleAspectRatio(float aspectRatio) {
        this.mYogaNode.setAspectRatio(aspectRatio);
    }

    public void setFlexDirection(YogaFlexDirection flexDirection) {
        this.mYogaNode.setFlexDirection(flexDirection);
    }

    public void setFlexWrap(YogaWrap wrap) {
        this.mYogaNode.setWrap(wrap);
    }

    public void setAlignSelf(YogaAlign alignSelf) {
        this.mYogaNode.setAlignSelf(alignSelf);
    }

    public void setAlignItems(YogaAlign alignItems) {
        this.mYogaNode.setAlignItems(alignItems);
    }

    public void setJustifyContent(YogaJustify justifyContent) {
        this.mYogaNode.setJustifyContent(justifyContent);
    }

    public void setOverflow(YogaOverflow overflow) {
        this.mYogaNode.setOverflow(overflow);
    }

    public void setMargin(int spacingType, float margin) {
        this.mYogaNode.setMargin(YogaEdge.fromInt(spacingType), margin);
    }

    public void setMarginPercent(int spacingType, float percent) {
        this.mYogaNode.setMarginPercent(YogaEdge.fromInt(spacingType), percent);
    }

    public final float getPadding(int spacingType) {
        return this.mYogaNode.getLayoutPadding(YogaEdge.fromInt(spacingType));
    }

    public final YogaValue getStylePadding(int spacingType) {
        return this.mYogaNode.getPadding(YogaEdge.fromInt(spacingType));
    }

    public void setDefaultPadding(int spacingType, float padding) {
        this.mDefaultPadding.set(spacingType, padding);
        updatePadding();
    }

    public void setPadding(int spacingType, float padding) {
        this.mPadding[spacingType] = padding;
        this.mPaddingIsPercent[spacingType] = false;
        updatePadding();
    }

    public void setPaddingPercent(int spacingType, float percent) {
        this.mPadding[spacingType] = percent;
        this.mPaddingIsPercent[spacingType] = !YogaConstants.isUndefined(percent);
        updatePadding();
    }

    /* JADX WARN: Code duplicated, block: B:33:0x0090  */
    /* JADX WARN: Code duplicated, block: B:35:0x0096  */
    /* JADX WARN: Code duplicated, block: B:36:0x00a4  */
    private void updatePadding() {
        for (int spacingType = 0; spacingType <= 8; spacingType++) {
            if (spacingType == 0 || spacingType == 2 || spacingType == 4 || spacingType == 5) {
                if (YogaConstants.isUndefined(this.mPadding[spacingType]) && YogaConstants.isUndefined(this.mPadding[6]) && YogaConstants.isUndefined(this.mPadding[8])) {
                    this.mYogaNode.setPadding(YogaEdge.fromInt(spacingType), this.mDefaultPadding.getRaw(spacingType));
                } else if (this.mPaddingIsPercent[spacingType]) {
                    this.mYogaNode.setPaddingPercent(YogaEdge.fromInt(spacingType), this.mPadding[spacingType]);
                } else {
                    this.mYogaNode.setPadding(YogaEdge.fromInt(spacingType), this.mPadding[spacingType]);
                }
            } else if (spacingType == 1 || spacingType == 3) {
                if (YogaConstants.isUndefined(this.mPadding[spacingType]) && YogaConstants.isUndefined(this.mPadding[7]) && YogaConstants.isUndefined(this.mPadding[8])) {
                    this.mYogaNode.setPadding(YogaEdge.fromInt(spacingType), this.mDefaultPadding.getRaw(spacingType));
                } else if (this.mPaddingIsPercent[spacingType]) {
                    this.mYogaNode.setPaddingPercent(YogaEdge.fromInt(spacingType), this.mPadding[spacingType]);
                } else {
                    this.mYogaNode.setPadding(YogaEdge.fromInt(spacingType), this.mPadding[spacingType]);
                }
            } else if (YogaConstants.isUndefined(this.mPadding[spacingType])) {
                this.mYogaNode.setPadding(YogaEdge.fromInt(spacingType), this.mDefaultPadding.getRaw(spacingType));
            } else if (this.mPaddingIsPercent[spacingType]) {
                this.mYogaNode.setPaddingPercent(YogaEdge.fromInt(spacingType), this.mPadding[spacingType]);
            } else {
                this.mYogaNode.setPadding(YogaEdge.fromInt(spacingType), this.mPadding[spacingType]);
            }
        }
    }

    public void setBorder(int spacingType, float borderWidth) {
        this.mYogaNode.setBorder(YogaEdge.fromInt(spacingType), borderWidth);
    }

    public void setPosition(int spacingType, float position) {
        this.mYogaNode.setPosition(YogaEdge.fromInt(spacingType), position);
    }

    public void setPositionPercent(int spacingType, float percent) {
        this.mYogaNode.setPositionPercent(YogaEdge.fromInt(spacingType), percent);
    }

    public void setPositionType(YogaPositionType positionType) {
        this.mYogaNode.setPositionType(positionType);
    }

    public void setShouldNotifyOnLayout(boolean shouldNotifyOnLayout) {
        this.mShouldNotifyOnLayout = shouldNotifyOnLayout;
    }

    public void setMeasureFunction(YogaMeasureFunction measureFunction) {
        if (((measureFunction == null) ^ this.mYogaNode.isMeasureDefined()) && getChildCount() != 0) {
            throw new RuntimeException("Since a node with a measure function does not add any native yoga children, it's not safe to transition to/from having a measure function unless a node has no children");
        }
        this.mYogaNode.setMeasureFunction(measureFunction);
    }

    public String toString() {
        return this.mYogaNode.toString();
    }

    public void dispose() {
        if (this.mYogaNode != null) {
            this.mYogaNode.reset();
            YogaNodePool.get().release(this.mYogaNode);
        }
    }
}
