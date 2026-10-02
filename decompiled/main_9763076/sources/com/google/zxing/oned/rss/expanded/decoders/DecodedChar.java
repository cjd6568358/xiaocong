package com.google.zxing.oned.rss.expanded.decoders;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class DecodedChar extends DecodedObject {
    private final char value;

    DecodedChar(int newPosition, char value) {
        super(newPosition);
        this.value = value;
    }

    char getValue() {
        return this.value;
    }

    boolean isFNC1() {
        return this.value == '$';
    }
}
