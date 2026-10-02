package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CollectionSerializer extends AsArraySerializerBase<Collection<?>> {
    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    public /* bridge */ /* synthetic */ AsArraySerializerBase<Collection<?>> withResolved(BeanProperty x0, TypeSerializer x1, JsonSerializer x2) {
        return withResolved2(x0, x1, (JsonSerializer<?>) x2);
    }

    public CollectionSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts, BeanProperty property, JsonSerializer<Object> valueSerializer) {
        super(Collection.class, elemType, staticTyping, vts, property, valueSerializer);
    }

    public CollectionSerializer(CollectionSerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSerializer) {
        super(src, property, vts, valueSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
        return new CollectionSerializer(this._elementType, this._staticTyping, vts, this._property, this._elementSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    /* JADX INFO: renamed from: withResolved, reason: avoid collision after fix types in other method */
    public AsArraySerializerBase<Collection<?>> withResolved2(BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer) {
        return new CollectionSerializer(this, property, vts, elementSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(Collection<?> value) {
        return value == null || value.isEmpty();
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public boolean hasSingleElement(Collection<?> value) {
        Iterator<?> it = value.iterator();
        if (!it.hasNext()) {
            return false;
        }
        it.next();
        return !it.hasNext();
    }

    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    public void serializeContents(Collection<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (this._elementSerializer != null) {
            serializeContentsUsing(value, jgen, provider, this._elementSerializer);
            return;
        }
        Iterator<?> it = value.iterator();
        if (it.hasNext()) {
            PropertySerializerMap serializers = this._dynamicSerializers;
            TypeSerializer typeSer = this._valueTypeSerializer;
            int i = 0;
            do {
                try {
                    Object elem = it.next();
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
                        if (typeSer == null) {
                            serializer.serialize(elem, jgen, provider);
                        } else {
                            serializer.serializeWithType(elem, jgen, provider, typeSer);
                        }
                    }
                    i++;
                } catch (Exception e) {
                    wrapAndThrow(provider, e, value, i);
                    return;
                }
            } while (it.hasNext());
        }
    }

    public void serializeContentsUsing(Collection<?> value, JsonGenerator jgen, SerializerProvider provider, JsonSerializer<Object> ser) throws IOException {
        Iterator<?> it = value.iterator();
        if (it.hasNext()) {
            TypeSerializer typeSer = this._valueTypeSerializer;
            int i = 0;
            do {
                Object elem = it.next();
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
                i++;
            } while (it.hasNext());
        }
    }
}
