package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.util.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CoreXMLDeserializers implements Provider<StdDeserializer<?>> {
    static final DatatypeFactory _dataTypeFactory;

    static {
        try {
            _dataTypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.fasterxml.jackson.databind.util.Provider
    public Collection<StdDeserializer<?>> provide() {
        return Arrays.asList(new DurationDeserializer(), new GregorianCalendarDeserializer(), new QNameDeserializer());
    }

    public static class DurationDeserializer extends FromStringDeserializer<Duration> {
        public DurationDeserializer() {
            super(Duration.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public Duration _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
            return CoreXMLDeserializers._dataTypeFactory.newDuration(value);
        }
    }

    public static class GregorianCalendarDeserializer extends StdScalarDeserializer<XMLGregorianCalendar> {
        public GregorianCalendarDeserializer() {
            super((Class<?>) XMLGregorianCalendar.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public XMLGregorianCalendar deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            Date d = _parseDate(jp, ctxt);
            if (d == null) {
                return null;
            }
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(d);
            return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(calendar);
        }
    }

    public static class QNameDeserializer extends FromStringDeserializer<QName> {
        public QNameDeserializer() {
            super(QName.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public QName _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
            return QName.valueOf(value);
        }
    }
}
