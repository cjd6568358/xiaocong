package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Rect;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class DrawCommandManager {
    abstract NodeRegion anyNodeRegionWithinBounds(float f, float f2);

    abstract void debugDraw(Canvas canvas);

    abstract void draw(Canvas canvas);

    abstract void getClippingRect(Rect rect);

    abstract boolean updateClippingRect();

    abstract NodeRegion virtualNodeRegionWithinBounds(float f, float f2);

    DrawCommandManager() {
    }

    static DrawCommandManager getVerticalClippingInstance(FlatViewGroup flatViewGroup, DrawCommand[] drawCommands) {
        return new VerticalDrawCommandManager(flatViewGroup, drawCommands);
    }
}
