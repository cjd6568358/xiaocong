package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ViewGroupManager<T extends ViewGroup> extends BaseViewManager<T, LayoutShadowNode> {
    public static WeakHashMap<View, Integer> mZIndexHash = new WeakHashMap<>();

    @Override // com.facebook.react.uimanager.ViewManager
    public LayoutShadowNode createShadowNodeInstance() {
        return new LayoutShadowNode();
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Class<? extends LayoutShadowNode> getShadowNodeClass() {
        return LayoutShadowNode.class;
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void updateExtraData(T root, Object extraData) {
    }

    public void addView(T parent, View child, int index) {
        parent.addView(child, index);
        reorderChildrenByZIndex(parent);
    }

    public void addViews(T parent, List<View> views) {
        int size = views.size();
        for (int i = 0; i < size; i++) {
            addView(parent, views.get(i), i);
        }
    }

    public static void setViewZIndex(View view, int zIndex) {
        mZIndexHash.put(view, Integer.valueOf(zIndex));
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            reorderChildrenByZIndex(parent);
        }
    }

    public static void reorderChildrenByZIndex(ViewGroup view) {
        Collection<Integer> zIndexes = mZIndexHash.values();
        boolean containsZIndexedElement = false;
        for (Integer zIndex : zIndexes) {
            if (zIndex.intValue() != 0) {
                containsZIndexedElement = true;
                break;
            }
        }
        if (containsZIndexedElement) {
            ArrayList<View> viewsToSort = new ArrayList<>();
            for (int i = 0; i < view.getChildCount(); i++) {
                viewsToSort.add(view.getChildAt(i));
            }
            Collections.sort(viewsToSort, new Comparator<View>() { // from class: com.facebook.react.uimanager.ViewGroupManager.1
                @Override // java.util.Comparator
                public int compare(View view1, View view2) {
                    Integer view1ZIndex = ViewGroupManager.mZIndexHash.get(view1);
                    if (view1ZIndex == null) {
                        view1ZIndex = 0;
                    }
                    Integer view2ZIndex = ViewGroupManager.mZIndexHash.get(view2);
                    if (view2ZIndex == null) {
                        view2ZIndex = 0;
                    }
                    return view1ZIndex.intValue() - view2ZIndex.intValue();
                }
            });
            for (int i2 = 0; i2 < viewsToSort.size(); i2++) {
                viewsToSort.get(i2).bringToFront();
            }
            view.invalidate();
        }
    }

    public int getChildCount(T parent) {
        return parent.getChildCount();
    }

    public View getChildAt(T parent, int index) {
        return parent.getChildAt(index);
    }

    public void removeViewAt(T parent, int index) {
        parent.removeViewAt(index);
    }

    public void removeView(T parent, View view) {
        for (int i = 0; i < getChildCount(parent); i++) {
            if (getChildAt(parent, i) == view) {
                removeViewAt(parent, i);
                return;
            }
        }
    }

    public void removeAllViews(T parent) {
        for (int i = getChildCount(parent) - 1; i >= 0; i--) {
            removeViewAt(parent, i);
        }
    }

    public boolean needsCustomLayoutForChildren() {
        return false;
    }

    public boolean shouldPromoteGrandchildren() {
        return false;
    }
}
