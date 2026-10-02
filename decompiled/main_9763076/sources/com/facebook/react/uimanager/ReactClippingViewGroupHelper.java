package com.facebook.react.uimanager;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactClippingViewGroupHelper {
    private static final Rect sHelperRect = new Rect();

    public static void calculateClippingRect(View view, Rect outputRect) {
        ViewParent parent = view.getParent();
        if (parent == null) {
            outputRect.setEmpty();
            return;
        }
        if (parent instanceof ReactClippingViewGroup) {
            ReactClippingViewGroup clippingViewGroup = (ReactClippingViewGroup) parent;
            if (clippingViewGroup.getRemoveClippedSubviews()) {
                clippingViewGroup.getClippingRect(sHelperRect);
                if (!sHelperRect.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                    outputRect.setEmpty();
                    return;
                }
                sHelperRect.offset(-view.getLeft(), -view.getTop());
                sHelperRect.offset(view.getScrollX(), view.getScrollY());
                outputRect.set(sHelperRect);
                return;
            }
        }
        view.getDrawingRect(outputRect);
    }
}
