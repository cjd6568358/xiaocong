package com.google.zxing.oned.rss;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class Pair extends DataCharacter {
    private int count;
    private final FinderPattern finderPattern;

    Pair(int value, int checksumPortion, FinderPattern finderPattern) {
        super(value, checksumPortion);
        this.finderPattern = finderPattern;
    }

    FinderPattern getFinderPattern() {
        return this.finderPattern;
    }

    int getCount() {
        return this.count;
    }

    void incrementCount() {
        this.count++;
    }
}
