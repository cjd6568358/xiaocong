package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DoubleSerializer implements ObjectSerializer {
    public static final DoubleSerializer instance = new DoubleSerializer();
    private DecimalFormat decimalFormat;

    public DoubleSerializer() {
        this.decimalFormat = null;
    }

    public DoubleSerializer(DecimalFormat decimalFormat) {
        this.decimalFormat = null;
        this.decimalFormat = decimalFormat;
    }

    public DoubleSerializer(String decimalFormat) {
        this(new DecimalFormat(decimalFormat));
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        double doubleValue = ((Double) object).doubleValue();
        if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
            out.writeNull();
        } else if (this.decimalFormat == null) {
            out.writeDouble(doubleValue, true);
        } else {
            String doubleText = this.decimalFormat.format(doubleValue);
            out.write(doubleText);
        }
    }
}
