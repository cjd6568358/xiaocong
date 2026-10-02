package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public final class IndexedListSerializer extends AsArraySerializerBase<List<?>> {
    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    /* JADX INFO: renamed from: withResolved, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ AsArraySerializerBase<List<?>> withResolved2(BeanProperty x0, TypeSerializer x1, JsonSerializer x2) {
        return withResolved(x0, x1, (JsonSerializer<?>) x2);
    }

    public IndexedListSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts, BeanProperty property, JsonSerializer<Object> valueSerializer) {
        super(List.class, elemType, staticTyping, vts, property, valueSerializer);
    }

    public IndexedListSerializer(IndexedListSerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSerializer) {
        super(src, property, vts, valueSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    public AsArraySerializerBase<List<?>> withResolved(BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer) {
        return new IndexedListSerializer(this, property, vts, elementSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(List<?> value) {
        return value == null || value.isEmpty();
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public boolean hasSingleElement(List<?> value) {
        return value.size() == 1;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
        return new IndexedListSerializer(this._elementType, this._staticTyping, vts, this._property, this._elementSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    public void serializeContents(List<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (this._elementSerializer != null) {
            serializeContentsUsing(value, jgen, provider, this._elementSerializer);
            return;
        }
        if (this._valueTypeSerializer != null) {
            serializeTypedContents(value, jgen, provider);
            return;
        }
        int len = value.size();
        if (len != 0) {
            int i = 0;
            try {
                PropertySerializerMap serializers = this._dynamicSerializers;
                while (i < len) {
                    Object elem = value.get(i);
                    if (elem == null) {
                        provider.defaultSerializeNull(jgen);
                    } else {
                        Class<?> cc = elem.getClass();
                        JsonSerializer<Object> serializer = serializers.serializerFor(cc);
                        if (serializer == null) {
                            if (this._elementType.hasGenericTypes()) {
                                serializer = _findAndAddDynamic(serializers, provider.constructSpecializedType(this._elementType, cc), provider);
                            } else {
                                serializer = _findAndAddDynamic(serializers, cc, provider);
                            }
                            serializers = this._dynamicSerializers;
                        }
                        serializer.serialize(elem, jgen, provider);
                    }
                    i++;
                }
            } catch (Exception e) {
                wrapAndThrow(provider, e, value, i);
            }
        }
    }

    public void serializeContentsUsing(List<?> value, JsonGenerator jgen, SerializerProvider provider, JsonSerializer<Object> ser) throws IOException {
        int len = value.size();
        if (len != 0) {
            TypeSerializer typeSer = this._valueTypeSerializer;
            for (int i = 0; i < len; i++) {
                Object elem = value.get(i);
                if (elem == null) {
                    try {
                        provider.defaultSerializeNull(jgen);
                    } catch (Exception e) {
                        wrapAndThrow(provider, e, value, i);
                    }
                } else if (typeSer == null) {
                    ser.serialize(elem, jgen, provider);
                } else {
                    ser.serializeWithType(elem, jgen, provider, typeSer);
                }
            }
        }
    }

    public void serializeTypedContents(List<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        int len = value.size();
        if (len != 0) {
            int i = 0;
            try {
                TypeSerializer typeSer = this._valueTypeSerializer;
                PropertySerializerMap serializers = this._dynamicSerializers;
                while (i < len) {
                    Object elem = value.get(i);
                    if (elem == null) {
                        provider.defaultSerializeNull(jgen);
                    } else {
                        Class<?> cc = elem.getClass();
                        JsonSerializer<Object> serializer = serializers.serializerFor(cc);
                        if (serializer == null) {
                            if (this._elementType.hasGenericTypes()) {
                                serializer = _findAndAddDynamic(serializers, provider.constructSpecializedType(this._elementType, cc), provider);
                            } else {
                                serializer = _findAndAddDynamic(serializers, cc, provider);
                            }
                            serializers = this._dynamicSerializers;
                        }
                        serializer.serializeWithType(elem, jgen, provider, typeSer);
                    }
                    i++;
                }
            } catch (Exception e) {
                wrapAndThrow(provider, e, value, i);
            }
        }
    }
}
