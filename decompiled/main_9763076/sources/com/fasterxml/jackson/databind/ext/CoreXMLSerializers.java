package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.util.Provider;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CoreXMLSerializers implements Provider<Map.Entry<Class<?>, JsonSerializer<?>>> {
    static final HashMap<Class<?>, JsonSerializer<?>> _serializers = new HashMap<>();

    static {
        ToStringSerializer tss = ToStringSerializer.instance;
        _serializers.put(Duration.class, tss);
        _serializers.put(XMLGregorianCalendar.class, new XMLGregorianCalendarSerializer());
        _serializers.put(QName.class, tss);
    }

    @Override // com.fasterxml.jackson.databind.util.Provider
    public Collection<Map.Entry<Class<?>, JsonSerializer<?>>> provide() {
        return _serializers.entrySet();
    }

    public static class XMLGregorianCalendarSerializer extends StdSerializer<XMLGregorianCalendar> {
        public XMLGregorianCalendarSerializer() {
            super(XMLGregorianCalendar.class);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(XMLGregorianCalendar value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            CalendarSerializer.instance.serialize((Calendar) value.toGregorianCalendar(), jgen, provider);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
            return CalendarSerializer.instance.getSchema(provider, typeHint);
        }
    }
}
