package com.google.zxing.oned.rss.expanded.decoders;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class CurrentParsingState {
    private int position = 0;
    private State encoding = State.NUMERIC;

    private enum State {
        NUMERIC,
        ALPHA,
        ISO_IEC_646
    }

    CurrentParsingState() {
    }

    int getPosition() {
        return this.position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    void incrementPosition(int delta) {
        this.position += delta;
    }

    boolean isAlpha() {
        return this.encoding == State.ALPHA;
    }

    boolean isIsoIec646() {
        return this.encoding == State.ISO_IEC_646;
    }

    void setNumeric() {
        this.encoding = State.NUMERIC;
    }

    void setAlpha() {
        this.encoding = State.ALPHA;
    }

    void setIsoIec646() {
        this.encoding = State.ISO_IEC_646;
    }
}
