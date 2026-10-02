package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MappingJsonFactory extends JsonFactory {
    public MappingJsonFactory() {
        this(null);
    }

    public MappingJsonFactory(ObjectMapper mapper) {
        super(mapper);
        if (mapper == null) {
            setCodec(new ObjectMapper(this));
        }
    }

    @Override // com.fasterxml.jackson.core.JsonFactory
    public final ObjectMapper getCodec() {
        return (ObjectMapper) this._objectCodec;
    }

    @Override // com.fasterxml.jackson.core.JsonFactory
    public String getFormatName() {
        return JsonFactory.FORMAT_NAME_JSON;
    }

    @Override // com.fasterxml.jackson.core.JsonFactory
    public MatchStrength hasFormat(InputAccessor acc) throws IOException {
        return hasJSONFormat(acc);
    }
}
