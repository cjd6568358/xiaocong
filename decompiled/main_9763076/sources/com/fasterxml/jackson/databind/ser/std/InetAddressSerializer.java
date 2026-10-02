package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.net.InetAddress;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class InetAddressSerializer extends StdScalarSerializer<InetAddress> {
    public static final InetAddressSerializer instance = new InetAddressSerializer();

    public InetAddressSerializer() {
        super(InetAddress.class);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(InetAddress value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        String str = value.toString().trim();
        int ix = str.indexOf(47);
        if (ix >= 0) {
            if (ix == 0) {
                str = str.substring(1);
            } else {
                str = str.substring(0, ix);
            }
        }
        jgen.writeString(str);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(InetAddress value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForScalar(value, jgen, InetAddress.class);
        serialize(value, jgen, provider);
        typeSer.writeTypeSuffixForScalar(value, jgen);
    }
}
