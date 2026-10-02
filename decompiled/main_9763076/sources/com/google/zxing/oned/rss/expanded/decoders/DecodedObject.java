package com.google.zxing.oned.rss.expanded.decoders;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class DecodedObject {
    private final int newPosition;

    DecodedObject(int newPosition) {
        this.newPosition = newPosition;
    }

    final int getNewPosition() {
        return this.newPosition;
    }
}
