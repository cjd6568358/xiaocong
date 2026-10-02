package com.facebook.react.flat;

import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class VerticalDrawCommandManager extends ClippingDrawCommandManager {
    VerticalDrawCommandManager(FlatViewGroup flatViewGroup, DrawCommand[] drawCommands) {
        super(flatViewGroup, drawCommands);
    }

    @Override // com.facebook.react.flat.ClippingDrawCommandManager
    int commandStartIndex() {
        int start = Arrays.binarySearch(this.mCommandMaxBottom, this.mClippingRect.top);
        return start < 0 ? start ^ (-1) : start;
    }

    @Override // com.facebook.react.flat.ClippingDrawCommandManager
    int commandStopIndex(int start) {
        int stop = Arrays.binarySearch(this.mCommandMinTop, start, this.mCommandMinTop.length, this.mClippingRect.bottom);
        return stop < 0 ? stop ^ (-1) : stop;
    }

    @Override // com.facebook.react.flat.ClippingDrawCommandManager
    int regionStopIndex(float touchX, float touchY) {
        int stop = Arrays.binarySearch(this.mRegionMinTop, 1.0E-4f + touchY);
        return stop < 0 ? stop ^ (-1) : stop;
    }

    @Override // com.facebook.react.flat.ClippingDrawCommandManager
    boolean regionAboveTouch(int index, float touchX, float touchY) {
        return this.mRegionMaxBottom[index] < touchY;
    }
}
