package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ARTGroupShadowNode extends ARTVirtualNode {
    protected RectF mClipping;

    @ReactProp(name = "clipping")
    public void setClipping(ReadableArray clippingDims) {
        float[] clippingData = PropHelper.toFloatArray(clippingDims);
        if (clippingData != null) {
            this.mClipping = createClipping(clippingData);
            markUpdated();
        }
    }

    @Override // com.facebook.react.views.art.ARTVirtualNode, com.facebook.react.uimanager.ReactShadowNode
    public boolean isVirtual() {
        return true;
    }

    @Override // com.facebook.react.views.art.ARTVirtualNode
    public void draw(Canvas canvas, Paint paint, float opacity) {
        float opacity2 = opacity * this.mOpacity;
        if (opacity2 > 0.01f) {
            saveAndSetupCanvas(canvas);
            if (this.mClipping != null) {
                canvas.clipRect(this.mScale * this.mClipping.left, this.mScale * this.mClipping.top, this.mScale * this.mClipping.right, this.mScale * this.mClipping.bottom, Region.Op.REPLACE);
            }
            for (int i = 0; i < getChildCount(); i++) {
                ARTVirtualNode child = (ARTVirtualNode) getChildAt(i);
                child.draw(canvas, paint, opacity2);
                child.markUpdateSeen();
            }
            restoreCanvas(canvas);
        }
    }

    private static RectF createClipping(float[] data) {
        if (data.length != 4) {
            throw new JSApplicationIllegalArgumentException("Clipping should be array of length 4 (e.g. [x, y, width, height])");
        }
        RectF clippingRect = new RectF(data[0], data[1], data[0] + data[2], data[1] + data[3]);
        return clippingRect;
    }
}
