package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class ClippingDrawCommandManager extends DrawCommandManager {
    private final FlatViewGroup mFlatViewGroup;
    private int mStart;
    private int mStop;
    private DrawCommand[] mDrawCommands = DrawCommand.EMPTY_ARRAY;
    protected float[] mCommandMaxBottom = StateBuilder.EMPTY_FLOAT_ARRAY;
    protected float[] mCommandMinTop = StateBuilder.EMPTY_FLOAT_ARRAY;
    private NodeRegion[] mNodeRegions = NodeRegion.EMPTY_ARRAY;
    protected float[] mRegionMaxBottom = StateBuilder.EMPTY_FLOAT_ARRAY;
    protected float[] mRegionMinTop = StateBuilder.EMPTY_FLOAT_ARRAY;
    private SparseIntArray mDrawViewIndexMap = StateBuilder.EMPTY_SPARSE_INT;
    private final SparseArray<View> mClippedSubviews = new SparseArray<>();
    protected final Rect mClippingRect = new Rect();
    private final SparseArray<View> mViewsToRemove = new SparseArray<>();
    private final ArrayList<View> mViewsToKeep = new ArrayList<>();
    private final ArrayList<ReactClippingViewGroup> mClippingViewGroups = new ArrayList<>();

    abstract int commandStartIndex();

    abstract int commandStopIndex(int i);

    abstract boolean regionAboveTouch(int i, float f, float f2);

    abstract int regionStopIndex(float f, float f2);

    ClippingDrawCommandManager(FlatViewGroup flatViewGroup, DrawCommand[] drawCommands) {
        this.mFlatViewGroup = flatViewGroup;
        initialSetup(drawCommands);
    }

    private void initialSetup(DrawCommand[] drawCommands) {
        mountDrawCommands(drawCommands, this.mDrawViewIndexMap, this.mCommandMaxBottom, this.mCommandMinTop, true);
        updateClippingRect();
    }

    public void mountDrawCommands(DrawCommand[] drawCommands, SparseIntArray drawViewIndexMap, float[] maxBottom, float[] minTop, boolean willMountViews) {
        this.mDrawCommands = drawCommands;
        this.mCommandMaxBottom = maxBottom;
        this.mCommandMinTop = minTop;
        this.mDrawViewIndexMap = drawViewIndexMap;
        if (this.mClippingRect.bottom != this.mClippingRect.top) {
            this.mStart = commandStartIndex();
            this.mStop = commandStopIndex(this.mStart);
            if (!willMountViews) {
                updateClippingToCurrentRect();
            }
        }
    }

    @Override // com.facebook.react.flat.DrawCommandManager
    public NodeRegion virtualNodeRegionWithinBounds(float touchX, float touchY) {
        int i = regionStopIndex(touchX, touchY);
        while (true) {
            int i2 = i;
            i = i2 - 1;
            if (i2 <= 0) {
                break;
            }
            NodeRegion nodeRegion = this.mNodeRegions[i];
            if (nodeRegion.mIsVirtual) {
                if (regionAboveTouch(i, touchX, touchY)) {
                    break;
                }
                if (nodeRegion.withinBounds(touchX, touchY)) {
                    return nodeRegion;
                }
            }
        }
        return null;
    }

    @Override // com.facebook.react.flat.DrawCommandManager
    public NodeRegion anyNodeRegionWithinBounds(float touchX, float touchY) {
        NodeRegion nodeRegion;
        int i = regionStopIndex(touchX, touchY);
        do {
            int i2 = i;
            i = i2 - 1;
            if (i2 > 0) {
                nodeRegion = this.mNodeRegions[i];
                if (regionAboveTouch(i, touchX, touchY)) {
                }
            }
            return null;
        } while (!nodeRegion.withinBounds(touchX, touchY));
        return nodeRegion;
    }

    private void clip(int id, View view) {
        this.mClippedSubviews.put(id, view);
    }

    private void unclip(int id) {
        this.mClippedSubviews.remove(id);
    }

    private boolean isNotClipped(int id) {
        return this.mClippedSubviews.get(id) == null;
    }

    private static boolean animating(View view) {
        Animation animation = view.getAnimation();
        return (animation == null || animation.hasEnded()) ? false : true;
    }

    private boolean withinBounds(int i) {
        return this.mStart <= i && i < this.mStop;
    }

    @Override // com.facebook.react.flat.DrawCommandManager
    public boolean updateClippingRect() {
        ReactClippingViewGroupHelper.calculateClippingRect(this.mFlatViewGroup, this.mClippingRect);
        if (this.mFlatViewGroup.getParent() == null || this.mClippingRect.top == this.mClippingRect.bottom) {
            return false;
        }
        int start = commandStartIndex();
        int stop = commandStopIndex(start);
        if (this.mStart <= start && stop <= this.mStop) {
            updateClippingRecursively();
            return false;
        }
        this.mStart = start;
        this.mStop = stop;
        updateClippingToCurrentRect();
        updateClippingRecursively();
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateClippingRecursively() {
        int children = this.mClippingViewGroups.size();
        for (int i = 0; i < children; i++) {
            ReactClippingViewGroup reactClippingViewGroup = this.mClippingViewGroups.get(i);
            if (isNotClipped(((View) reactClippingViewGroup).getId())) {
                reactClippingViewGroup.updateClippingRect();
            }
        }
    }

    private void updateClippingToCurrentRect() {
        int childIndex;
        int size = this.mFlatViewGroup.getChildCount();
        for (int i = 0; i < size; i++) {
            View view = this.mFlatViewGroup.getChildAt(i);
            int index = this.mDrawViewIndexMap.get(view.getId());
            if (withinBounds(index) || animating(view)) {
                this.mViewsToKeep.add(view);
            } else {
                this.mViewsToRemove.append(i, view);
                clip(view.getId(), view);
            }
        }
        int removeSize = this.mViewsToRemove.size();
        boolean removeAll = removeSize > 2;
        if (!removeAll) {
            while (true) {
                int removeSize2 = removeSize;
                removeSize = removeSize2 - 1;
                if (removeSize2 <= 0) {
                    break;
                } else {
                    this.mFlatViewGroup.removeViewsInLayout(this.mViewsToRemove.keyAt(removeSize), 1);
                }
            }
        } else {
            this.mFlatViewGroup.detachAllViewsFromParent();
            for (int i2 = 0; i2 < removeSize; i2++) {
                this.mFlatViewGroup.removeDetachedView(this.mViewsToRemove.valueAt(i2));
            }
        }
        this.mViewsToRemove.clear();
        int current = this.mStart;
        int childIndex2 = 0;
        int size2 = this.mViewsToKeep.size();
        for (int i3 = 0; i3 < size2; i3++) {
            View view2 = this.mViewsToKeep.get(i3);
            int commandIndex = this.mDrawViewIndexMap.get(view2.getId());
            if (current <= commandIndex) {
                int childIndex3 = childIndex2;
                while (current != commandIndex) {
                    if (this.mDrawCommands[current] instanceof DrawView) {
                        DrawView drawView = (DrawView) this.mDrawCommands[current];
                        childIndex = childIndex3 + 1;
                        this.mFlatViewGroup.addViewInLayout((View) Assertions.assumeNotNull(this.mClippedSubviews.get(drawView.reactTag)), childIndex3);
                        unclip(drawView.reactTag);
                    } else {
                        childIndex = childIndex3;
                    }
                    current++;
                    childIndex3 = childIndex;
                }
                current++;
                childIndex2 = childIndex3;
            }
            if (removeAll) {
                this.mFlatViewGroup.attachViewToParent(view2, childIndex2);
            }
            childIndex2++;
        }
        this.mViewsToKeep.clear();
        while (true) {
            int childIndex4 = childIndex2;
            if (current < this.mStop) {
                if (this.mDrawCommands[current] instanceof DrawView) {
                    DrawView drawView2 = (DrawView) this.mDrawCommands[current];
                    childIndex2 = childIndex4 + 1;
                    this.mFlatViewGroup.addViewInLayout((View) Assertions.assumeNotNull(this.mClippedSubviews.get(drawView2.reactTag)), childIndex4);
                    unclip(drawView2.reactTag);
                } else {
                    childIndex2 = childIndex4;
                }
                current++;
            } else {
                return;
            }
        }
    }

    @Override // com.facebook.react.flat.DrawCommandManager
    public void getClippingRect(Rect outClippingRect) {
        outClippingRect.set(this.mClippingRect);
    }

    @Override // com.facebook.react.flat.DrawCommandManager
    public void draw(Canvas canvas) {
        int commandIndex = this.mStart;
        int size = this.mFlatViewGroup.getChildCount();
        for (int i = 0; i < size; i++) {
            int viewIndex = this.mDrawViewIndexMap.get(this.mFlatViewGroup.getChildAt(i).getId());
            if (this.mStop < viewIndex) {
                while (commandIndex < this.mStop) {
                    this.mDrawCommands[commandIndex].draw(this.mFlatViewGroup, canvas);
                    commandIndex++;
                }
            } else if (commandIndex <= viewIndex) {
                int commandIndex2 = commandIndex;
                while (commandIndex2 < viewIndex) {
                    this.mDrawCommands[commandIndex2].draw(this.mFlatViewGroup, canvas);
                    commandIndex2++;
                }
                commandIndex = commandIndex2 + 1;
            }
            this.mDrawCommands[viewIndex].draw(this.mFlatViewGroup, canvas);
        }
        while (commandIndex < this.mStop) {
            this.mDrawCommands[commandIndex].draw(this.mFlatViewGroup, canvas);
            commandIndex++;
        }
    }

    @Override // com.facebook.react.flat.DrawCommandManager
    void debugDraw(Canvas canvas) {
        for (DrawCommand drawCommand : this.mDrawCommands) {
            if (drawCommand instanceof DrawView) {
                if (isNotClipped(((DrawView) drawCommand).reactTag)) {
                    drawCommand.debugDraw(this.mFlatViewGroup, canvas);
                }
            } else {
                drawCommand.debugDraw(this.mFlatViewGroup, canvas);
            }
        }
    }
}
