package com.google.zxing.oned.rss.expanded.decoders;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class BlockParsedResult {
    private final DecodedInformation decodedInformation;
    private final boolean finished;

    BlockParsedResult(boolean finished) {
        this(null, finished);
    }

    BlockParsedResult(DecodedInformation information, boolean finished) {
        this.finished = finished;
        this.decodedInformation = information;
    }

    DecodedInformation getDecodedInformation() {
        return this.decodedInformation;
    }

    boolean isFinished() {
        return this.finished;
    }
}
