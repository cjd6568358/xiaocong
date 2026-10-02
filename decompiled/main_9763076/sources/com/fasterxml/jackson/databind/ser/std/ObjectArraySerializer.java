package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.type.ArrayType;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class ObjectArraySerializer extends ArraySerializerBase<Object[]> implements ContextualSerializer {
    protected PropertySerializerMap _dynamicSerializers;
    protected JsonSerializer<Object> _elementSerializer;
    protected final JavaType _elementType;
    protected final boolean _staticTyping;
    protected final TypeSerializer _valueTypeSerializer;

    public ObjectArraySerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts, JsonSerializer<Object> elementSerializer) {
        super(Object[].class, (BeanProperty) null);
        this._elementType = elemType;
        this._staticTyping = staticTyping;
        this._valueTypeSerializer = vts;
        this._dynamicSerializers = PropertySerializerMap.emptyMap();
        this._elementSerializer = elementSerializer;
    }

    public ObjectArraySerializer(ObjectArraySerializer src, TypeSerializer vts) {
        super(src);
        this._elementType = src._elementType;
        this._valueTypeSerializer = vts;
        this._staticTyping = src._staticTyping;
        this._dynamicSerializers = src._dynamicSerializers;
        this._elementSerializer = src._elementSerializer;
    }

    public ObjectArraySerializer(ObjectArraySerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer) {
        super(src, property);
        this._elementType = src._elementType;
        this._valueTypeSerializer = vts;
        this._staticTyping = src._staticTyping;
        this._dynamicSerializers = src._dynamicSerializers;
        this._elementSerializer = elementSerializer;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
        return new ObjectArraySerializer(this._elementType, this._staticTyping, vts, this._elementSerializer);
    }

    public ObjectArraySerializer withResolved(BeanProperty prop, TypeSerializer vts, JsonSerializer<?> ser) {
        return (this._property == prop && ser == this._elementSerializer && this._valueTypeSerializer == vts) ? this : new ObjectArraySerializer(this, prop, vts, ser);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
        TypeSerializer vts = this._valueTypeSerializer;
        if (vts != null) {
            vts = vts.forProperty(property);
        }
        JsonSerializer<?> ser = this._elementSerializer;
        if (ser == null) {
            if (this._staticTyping) {
                ser = provider.findValueSerializer(this._elementType, property);
            }
        } else if (ser instanceof ContextualSerializer) {
            ser = ((ContextualSerializer) this._elementSerializer).createContextual(provider, property);
        }
        return withResolved(property, vts, ser);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JavaType getContentType() {
        return this._elementType;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JsonSerializer<?> getContentSerializer() {
        return this._elementSerializer;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(Object[] value) {
        return value == null || value.length == 0;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public boolean hasSingleElement(Object[] value) {
        return value.length == 1;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.ArraySerializerBase
    public void serializeContents(Object[] value, JsonGenerator jgen, SerializerProvider provider) throws Throwable {
        int len = value.length;
        if (len != 0) {
            if (this._elementSerializer != null) {
                serializeContentsUsing(value, jgen, provider, this._elementSerializer);
                return;
            }
            if (this._valueTypeSerializer != null) {
                serializeTypedContents(value, jgen, provider);
                return;
            }
            int i = 0;
            Object elem = null;
            try {
                PropertySerializerMap serializers = this._dynamicSerializers;
                while (i < len) {
                    elem = value[i];
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
                        }
                        serializer.serialize(elem, jgen, provider);
                    }
                    i++;
                }
            } catch (IOException ioe) {
                throw ioe;
            } catch (Exception e) {
                Throwable t = e;
                while ((t instanceof InvocationTargetException) && t.getCause() != null) {
                    t = t.getCause();
                }
                if (t instanceof Error) {
                    throw ((Error) t);
                }
                throw JsonMappingException.wrapWithPath(t, elem, i);
            }
        }
    }

    public void serializeContentsUsing(Object[] value, JsonGenerator jgen, SerializerProvider provider, JsonSerializer<Object> ser) throws Throwable {
        int len = value.length;
        TypeSerializer typeSer = this._valueTypeSerializer;
        Object elem = null;
        for (int i = 0; i < len; i++) {
            try {
                elem = value[i];
                if (elem == null) {
                    provider.defaultSerializeNull(jgen);
                } else if (typeSer == null) {
                    ser.serialize(elem, jgen, provider);
                } else {
                    ser.serializeWithType(elem, jgen, provider, typeSer);
                }
            } catch (IOException ioe) {
                throw ioe;
            } catch (Exception e) {
                Throwable t = e;
                while ((t instanceof InvocationTargetException) && t.getCause() != null) {
                    t = t.getCause();
                }
                if (t instanceof Error) {
                    throw ((Error) t);
                }
                throw JsonMappingException.wrapWithPath(t, elem, i);
            }
        }
    }

    public void serializeTypedContents(Object[] value, JsonGenerator jgen, SerializerProvider provider) throws Throwable {
        int len = value.length;
        TypeSerializer typeSer = this._valueTypeSerializer;
        int i = 0;
        Object elem = null;
        try {
            PropertySerializerMap serializers = this._dynamicSerializers;
            while (i < len) {
                elem = value[i];
                if (elem == null) {
                    provider.defaultSerializeNull(jgen);
                } else {
                    Class<?> cc = elem.getClass();
                    JsonSerializer<Object> serializer = serializers.serializerFor(cc);
                    if (serializer == null) {
                        serializer = _findAndAddDynamic(serializers, cc, provider);
                    }
                    serializer.serializeWithType(elem, jgen, provider, typeSer);
                }
                i++;
            }
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            Throwable t = e;
            while ((t instanceof InvocationTargetException) && t.getCause() != null) {
                t = t.getCause();
            }
            if (t instanceof Error) {
                throw ((Error) t);
            }
            throw JsonMappingException.wrapWithPath(t, elem, i);
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        ObjectNode o = createSchemaNode("array", true);
        if (typeHint != null) {
            JavaType javaType = provider.constructType(typeHint);
            if (javaType.isArrayType()) {
                Class<?> componentType = ((ArrayType) javaType).getContentType().getRawClass();
                if (componentType == Object.class) {
                    o.put("items", JsonSchema.getDefaultSchemaNode());
                } else {
                    Object objFindValueSerializer = provider.findValueSerializer(componentType, this._property);
                    JsonNode schemaNode = objFindValueSerializer instanceof SchemaAware ? ((SchemaAware) objFindValueSerializer).getSchema(provider, null) : JsonSchema.getDefaultSchemaNode();
                    o.put("items", schemaNode);
                }
            }
        }
        return o;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicSerializers = result.map;
        }
        return result.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, JavaType type, SerializerProvider provider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicSerializers = result.map;
        }
        return result.serializer;
    }
}
