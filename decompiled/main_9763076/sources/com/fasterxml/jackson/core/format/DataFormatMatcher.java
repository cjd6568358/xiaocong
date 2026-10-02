package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.MergedStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DataFormatMatcher {
    protected final byte[] _bufferedData;
    protected final int _bufferedLength;
    protected final JsonFactory _match;
    protected final MatchStrength _matchStrength;
    protected final InputStream _originalStream;

    protected DataFormatMatcher(InputStream in, byte[] buffered, int bufferedLength, JsonFactory match, MatchStrength strength) {
        this._originalStream = in;
        this._bufferedData = buffered;
        this._bufferedLength = bufferedLength;
        this._match = match;
        this._matchStrength = strength;
    }

    public boolean hasMatch() {
        return this._match != null;
    }

    public MatchStrength getMatchStrength() {
        return this._matchStrength == null ? MatchStrength.INCONCLUSIVE : this._matchStrength;
    }

    public JsonFactory getMatch() {
        return this._match;
    }

    public String getMatchedFormatName() {
        return this._match.getFormatName();
    }

    public JsonParser createParserWithMatch() throws IOException {
        if (this._match == null) {
            return null;
        }
        if (this._originalStream == null) {
            return this._match.createJsonParser(this._bufferedData, 0, this._bufferedLength);
        }
        return this._match.createJsonParser(getDataStream());
    }

    public InputStream getDataStream() {
        return this._originalStream == null ? new ByteArrayInputStream(this._bufferedData, 0, this._bufferedLength) : new MergedStream(null, this._originalStream, this._bufferedData, 0, this._bufferedLength);
    }
}
