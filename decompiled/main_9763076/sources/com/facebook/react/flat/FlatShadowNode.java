package com.facebook.react.flat;

import android.graphics.Rect;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.annotations.ReactProp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class FlatShadowNode extends LayoutShadowNode {
    float mClipRadius;
    private DrawBackgroundColor mDrawBackground;
    private DrawView mDrawView;
    private boolean mForceMountChildrenToView;
    private boolean mOverflowsContainer;
    private int mViewBottom;
    private int mViewLeft;
    private int mViewRight;
    private int mViewTop;
    static final FlatShadowNode[] EMPTY_ARRAY = new FlatShadowNode[0];
    private static final Rect LOGICAL_OFFSET_EMPTY = new Rect();
    private static final DrawView EMPTY_DRAW_VIEW = new DrawView(0);
    private DrawCommand[] mDrawCommands = DrawCommand.EMPTY_ARRAY;
    private AttachDetachListener[] mAttachDetachListeners = AttachDetachListener.EMPTY_ARRAY;
    private NodeRegion[] mNodeRegions = NodeRegion.EMPTY_ARRAY;
    private FlatShadowNode[] mNativeChildren = EMPTY_ARRAY;
    private NodeRegion mNodeRegion = NodeRegion.EMPTY;
    private boolean mIsUpdated = true;
    private Rect mLogicalOffset = LOGICAL_OFFSET_EMPTY;
    boolean mClipToBounds = false;

    FlatShadowNode() {
    }

    final void forceMountChildrenToView() {
        if (!this.mForceMountChildrenToView) {
            this.mForceMountChildrenToView = true;
            int childCount = getChildCount();
            for (int i = 0; i != childCount; i++) {
                ReactShadowNode child = getChildAt(i);
                if (child instanceof FlatShadowNode) {
                    ((FlatShadowNode) child).forceMountToView();
                }
            }
        }
    }

    @ReactProp(name = "backgroundColor")
    public void setBackgroundColor(int backgroundColor) {
        this.mDrawBackground = backgroundColor == 0 ? null : new DrawBackgroundColor(backgroundColor);
        invalidate();
    }

    @Override // com.facebook.react.uimanager.LayoutShadowNode
    public void setOverflow(String overflow) {
        super.setOverflow(overflow);
        this.mClipToBounds = "hidden".equals(overflow);
        if (this.mClipToBounds) {
            this.mOverflowsContainer = false;
            if (this.mClipRadius > 0.5f) {
                forceMountToView();
            }
        } else {
            updateOverflowsContainer();
        }
        invalidate();
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public final int getScreenX() {
        return this.mViewLeft;
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public final int getScreenY() {
        return this.mViewTop;
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public final int getScreenWidth() {
        return mountsToView() ? this.mViewRight - this.mViewLeft : Math.round(this.mNodeRegion.getRight() - this.mNodeRegion.getLeft());
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public final int getScreenHeight() {
        return mountsToView() ? this.mViewBottom - this.mViewTop : Math.round(this.mNodeRegion.getBottom() - this.mNodeRegion.getTop());
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void addChildAt(ReactShadowNode child, int i) {
        super.addChildAt(child, i);
        if (this.mForceMountChildrenToView && (child instanceof FlatShadowNode)) {
            ((FlatShadowNode) child).forceMountToView();
        }
    }

    protected final void invalidate() {
        FlatShadowNode node = this;
        while (true) {
            if (node.mountsToView()) {
                if (!node.mIsUpdated) {
                    node.mIsUpdated = true;
                } else {
                    return;
                }
            }
            ReactShadowNode parent = node.getParent();
            if (parent == null) {
                return;
            } else {
                node = (FlatShadowNode) parent;
            }
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void markUpdated() {
        super.markUpdated();
        this.mIsUpdated = true;
        invalidate();
    }

    final void updateOverflowsContainer() {
        boolean overflowsContainer = false;
        int width = (int) (this.mNodeRegion.getRight() - this.mNodeRegion.getLeft());
        int height = (int) (this.mNodeRegion.getBottom() - this.mNodeRegion.getTop());
        float leftBound = 0.0f;
        float rightBound = width;
        float topBound = 0.0f;
        float bottomBound = height;
        Rect logicalOffset = null;
        if (!this.mClipToBounds && height > 0 && width > 0) {
            for (NodeRegion region : this.mNodeRegions) {
                if (region.getLeft() < leftBound) {
                    leftBound = region.getLeft();
                    overflowsContainer = true;
                }
                if (region.getRight() > rightBound) {
                    rightBound = region.getRight();
                    overflowsContainer = true;
                }
                if (region.getTop() < topBound) {
                    topBound = region.getTop();
                    overflowsContainer = true;
                }
                if (region.getBottom() > bottomBound) {
                    bottomBound = region.getBottom();
                    overflowsContainer = true;
                }
            }
            if (overflowsContainer) {
                logicalOffset = new Rect((int) leftBound, (int) topBound, (int) (rightBound - width), (int) (bottomBound - height));
            }
        }
        if (!overflowsContainer && this.mNodeRegion != NodeRegion.EMPTY) {
            int children = getChildCount();
            for (int i = 0; i < children; i++) {
                ReactShadowNode node = getChildAt(i);
                if ((node instanceof FlatShadowNode) && ((FlatShadowNode) node).mOverflowsContainer) {
                    Rect childLogicalOffset = ((FlatShadowNode) node).mLogicalOffset;
                    if (logicalOffset == null) {
                        logicalOffset = new Rect();
                    }
                    logicalOffset.union(childLogicalOffset);
                    overflowsContainer = true;
                }
            }
        }
        if (this.mOverflowsContainer != overflowsContainer) {
            this.mOverflowsContainer = overflowsContainer;
            if (logicalOffset == null) {
                logicalOffset = LOGICAL_OFFSET_EMPTY;
            }
            this.mLogicalOffset = logicalOffset;
        }
    }

    final void forceMountToView() {
        if (!isVirtual() && this.mDrawView == null) {
            this.mDrawView = EMPTY_DRAW_VIEW;
            invalidate();
            this.mNodeRegion = NodeRegion.EMPTY;
        }
    }

    final boolean mountsToView() {
        return this.mDrawView != null;
    }
}
