package com.facebook.react.flat;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class FlatReactModalShadowNode extends FlatShadowNode {
    private boolean mPaddingChanged;
    private final Point mMinPoint = new Point();
    private final Point mMaxPoint = new Point();

    FlatReactModalShadowNode() {
        forceMountToView();
        forceMountChildrenToView();
    }

    @Override // com.facebook.react.flat.FlatShadowNode, com.facebook.react.uimanager.ReactShadowNode
    @TargetApi(16)
    public void addChildAt(ReactShadowNode child, int i) {
        int width;
        int height;
        super.addChildAt(child, i);
        Context context = getThemedContext();
        WindowManager wm = (WindowManager) context.getSystemService("window");
        Display display = wm.getDefaultDisplay();
        display.getCurrentSizeRange(this.mMinPoint, this.mMaxPoint);
        int rotation = display.getRotation();
        if (rotation == 0 || rotation == 2) {
            width = this.mMinPoint.x;
            height = this.mMaxPoint.y;
        } else {
            width = this.mMaxPoint.x;
            height = this.mMinPoint.y;
        }
        child.setStyleWidth(width);
        child.setStyleHeight(height);
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setPadding(int spacingType, float padding) {
        YogaValue current = getStylePadding(spacingType);
        if (current.unit != YogaUnit.PIXEL || current.value != padding) {
            super.setPadding(spacingType, padding);
            this.mPaddingChanged = true;
            markUpdated();
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setPaddingPercent(int spacingType, float percent) {
        YogaValue current = getStylePadding(spacingType);
        if (current.unit != YogaUnit.PERCENT || current.value != percent) {
            super.setPadding(spacingType, percent);
            this.mPaddingChanged = true;
            markUpdated();
        }
    }
}
