package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.tencent.android.tpush.common.Constants;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JdkDeserializers {
    public static StdDeserializer<?>[] all() {
        return new StdDeserializer[]{new StringDeserializer(), new UUIDDeserializer(), new URLDeserializer(), new URIDeserializer(), new CurrencyDeserializer(), new PatternDeserializer(), new LocaleDeserializer(), new InetAddressDeserializer(), new CharsetDeserializer(), new AtomicBooleanDeserializer(), new ClassDeserializer(), new StackTraceElementDeserializer()};
    }

    @JacksonStdImpl
    public static final class StringDeserializer extends StdScalarDeserializer<String> {
        public StringDeserializer() {
            super((Class<?>) String.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken curr = jp.getCurrentToken();
            if (curr == JsonToken.VALUE_STRING) {
                return jp.getText();
            }
            if (curr == JsonToken.VALUE_EMBEDDED_OBJECT) {
                Object ob = jp.getEmbeddedObject();
                if (ob == null) {
                    return null;
                }
                if (ob instanceof byte[]) {
                    return Base64Variants.getDefaultVariant().encode((byte[]) ob, false);
                }
                return ob.toString();
            }
            if (curr.isScalarValue()) {
                return jp.getText();
            }
            throw ctxt.mappingException(this._valueClass, curr);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public String deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
            return deserialize(jp, ctxt);
        }
    }

    public static class UUIDDeserializer extends FromStringDeserializer<UUID> {
        public UUIDDeserializer() {
            super(UUID.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public UUID _deserialize(String value, DeserializationContext ctxt) throws IOException {
            return UUID.fromString(value);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public UUID _deserializeEmbedded(Object ob, DeserializationContext ctxt) throws IOException {
            if (ob instanceof byte[]) {
                byte[] bytes = (byte[]) ob;
                if (bytes.length != 16) {
                    ctxt.mappingException("Can only construct UUIDs from 16 byte arrays; got " + bytes.length + " bytes");
                }
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
                long l1 = in.readLong();
                long l2 = in.readLong();
                return new UUID(l1, l2);
            }
            super._deserializeEmbedded(ob, ctxt);
            return null;
        }
    }

    public static class URLDeserializer extends FromStringDeserializer<URL> {
        public URLDeserializer() {
            super(URL.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public URL _deserialize(String value, DeserializationContext ctxt) throws IOException {
            return new URL(value);
        }
    }

    public static class URIDeserializer extends FromStringDeserializer<URI> {
        public URIDeserializer() {
            super(URI.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public URI _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
            return URI.create(value);
        }
    }

    public static class CurrencyDeserializer extends FromStringDeserializer<Currency> {
        public CurrencyDeserializer() {
            super(Currency.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public Currency _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
            return Currency.getInstance(value);
        }
    }

    public static class PatternDeserializer extends FromStringDeserializer<Pattern> {
        public PatternDeserializer() {
            super(Pattern.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public Pattern _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
            return Pattern.compile(value);
        }
    }

    protected static class LocaleDeserializer extends FromStringDeserializer<Locale> {
        public LocaleDeserializer() {
            super(Locale.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public Locale _deserialize(String value, DeserializationContext ctxt) throws IOException {
            int ix = value.indexOf(95);
            if (ix < 0) {
                return new Locale(value);
            }
            String first = value.substring(0, ix);
            String value2 = value.substring(ix + 1);
            int ix2 = value2.indexOf(95);
            if (ix2 < 0) {
                return new Locale(first, value2);
            }
            String second = value2.substring(0, ix2);
            return new Locale(first, second, value2.substring(ix2 + 1));
        }
    }

    protected static class InetAddressDeserializer extends FromStringDeserializer<InetAddress> {
        public InetAddressDeserializer() {
            super(InetAddress.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public InetAddress _deserialize(String value, DeserializationContext ctxt) throws IOException {
            return InetAddress.getByName(value);
        }
    }

    protected static class CharsetDeserializer extends FromStringDeserializer<Charset> {
        public CharsetDeserializer() {
            super(Charset.class);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public Charset _deserialize(String value, DeserializationContext ctxt) throws IOException {
            return Charset.forName(value);
        }
    }

    public static class AtomicReferenceDeserializer extends StdScalarDeserializer<AtomicReference<?>> implements ContextualDeserializer {
        protected final JavaType _referencedType;
        protected final JsonDeserializer<?> _valueDeserializer;

        public AtomicReferenceDeserializer(JavaType referencedType) {
            this(referencedType, null);
        }

        public AtomicReferenceDeserializer(JavaType referencedType, JsonDeserializer<?> deser) {
            super((Class<?>) AtomicReference.class);
            this._referencedType = referencedType;
            this._valueDeserializer = deser;
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public AtomicReference<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return new AtomicReference<>(this._valueDeserializer.deserialize(jp, ctxt));
        }

        @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            JsonDeserializer<?> deser = this._valueDeserializer;
            return deser != null ? this : new AtomicReferenceDeserializer(this._referencedType, ctxt.findContextualValueDeserializer(this._referencedType, property));
        }
    }

    public static class AtomicBooleanDeserializer extends StdScalarDeserializer<AtomicBoolean> {
        public AtomicBooleanDeserializer() {
            super((Class<?>) AtomicBoolean.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public AtomicBoolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return new AtomicBoolean(_parseBooleanPrimitive(jp, ctxt));
        }
    }

    public static class StackTraceElementDeserializer extends StdScalarDeserializer<StackTraceElement> {
        public StackTraceElementDeserializer() {
            super((Class<?>) StackTraceElement.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public StackTraceElement deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken t = jp.getCurrentToken();
            if (t == JsonToken.START_OBJECT) {
                String className = Constants.MAIN_VERSION_TAG;
                String methodName = Constants.MAIN_VERSION_TAG;
                String fileName = Constants.MAIN_VERSION_TAG;
                int lineNumber = -1;
                while (true) {
                    JsonToken t2 = jp.nextValue();
                    if (t2 != JsonToken.END_OBJECT) {
                        String propName = jp.getCurrentName();
                        if ("className".equals(propName)) {
                            className = jp.getText();
                        } else if ("fileName".equals(propName)) {
                            fileName = jp.getText();
                        } else if ("lineNumber".equals(propName)) {
                            if (t2.isNumeric()) {
                                lineNumber = jp.getIntValue();
                            } else {
                                throw JsonMappingException.from(jp, "Non-numeric token (" + t2 + ") for property 'lineNumber'");
                            }
                        } else if ("methodName".equals(propName)) {
                            methodName = jp.getText();
                        } else if (!"nativeMethod".equals(propName)) {
                            handleUnknownProperty(jp, ctxt, this._valueClass, propName);
                        }
                    } else {
                        return new StackTraceElement(className, methodName, fileName, lineNumber);
                    }
                }
            } else {
                throw ctxt.mappingException(this._valueClass, t);
            }
        }
    }
}
