package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.AnyGetterWriter;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BeanSerializerBase extends StdSerializer<Object> implements SchemaAware, ContextualSerializer, ResolvableSerializer {
    protected static final BeanPropertyWriter[] NO_PROPS = new BeanPropertyWriter[0];
    protected final AnyGetterWriter _anyGetterWriter;
    protected final BeanPropertyWriter[] _filteredProps;
    protected final ObjectIdWriter _objectIdWriter;
    protected final Object _propertyFilterId;
    protected final BeanPropertyWriter[] _props;
    protected final AnnotatedMember _typeId;

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public abstract void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    protected abstract BeanSerializerBase withIgnorals(String[] strArr);

    protected abstract BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter);

    protected BeanSerializerBase(JavaType type, BeanSerializerBuilder builder, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
        super(type);
        this._props = properties;
        this._filteredProps = filteredProperties;
        if (builder == null) {
            this._typeId = null;
            this._anyGetterWriter = null;
            this._propertyFilterId = null;
            this._objectIdWriter = null;
            return;
        }
        this._typeId = builder.getTypeId();
        this._anyGetterWriter = builder.getAnyGetter();
        this._propertyFilterId = builder.getFilterId();
        this._objectIdWriter = builder.getObjectIdWriter();
    }

    public BeanSerializerBase(BeanSerializerBase src, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
        super(src._handledType);
        this._props = properties;
        this._filteredProps = filteredProperties;
        this._typeId = src._typeId;
        this._anyGetterWriter = src._anyGetterWriter;
        this._objectIdWriter = src._objectIdWriter;
        this._propertyFilterId = src._propertyFilterId;
    }

    protected BeanSerializerBase(BeanSerializerBase src, ObjectIdWriter objectIdWriter) {
        super(src._handledType);
        this._props = src._props;
        this._filteredProps = src._filteredProps;
        this._typeId = src._typeId;
        this._anyGetterWriter = src._anyGetterWriter;
        this._objectIdWriter = objectIdWriter;
        this._propertyFilterId = src._propertyFilterId;
    }

    protected BeanSerializerBase(BeanSerializerBase src, String[] toIgnore) {
        super(src._handledType);
        HashSet<String> ignoredSet = ArrayBuilders.arrayToSet(toIgnore);
        BeanPropertyWriter[] propsIn = src._props;
        BeanPropertyWriter[] fpropsIn = src._filteredProps;
        int len = propsIn.length;
        ArrayList<BeanPropertyWriter> propsOut = new ArrayList<>(len);
        ArrayList<BeanPropertyWriter> fpropsOut = fpropsIn == null ? null : new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            BeanPropertyWriter bpw = propsIn[i];
            if (!ignoredSet.contains(bpw.getName())) {
                propsOut.add(bpw);
                if (fpropsIn != null) {
                    fpropsOut.add(fpropsIn[i]);
                }
            }
        }
        this._props = (BeanPropertyWriter[]) propsOut.toArray(new BeanPropertyWriter[propsOut.size()]);
        this._filteredProps = fpropsOut != null ? (BeanPropertyWriter[]) fpropsOut.toArray(new BeanPropertyWriter[fpropsOut.size()]) : null;
        this._typeId = src._typeId;
        this._anyGetterWriter = src._anyGetterWriter;
        this._objectIdWriter = src._objectIdWriter;
        this._propertyFilterId = src._propertyFilterId;
    }

    protected BeanSerializerBase(BeanSerializerBase src) {
        this(src, src._props, src._filteredProps);
    }

    protected BeanSerializerBase(BeanSerializerBase src, NameTransformer unwrapper) {
        this(src, rename(src._props, unwrapper), rename(src._filteredProps, unwrapper));
    }

    private static final BeanPropertyWriter[] rename(BeanPropertyWriter[] props, NameTransformer transformer) {
        if (props == null || props.length == 0 || transformer == null || transformer == NameTransformer.NOP) {
            return props;
        }
        int len = props.length;
        BeanPropertyWriter[] result = new BeanPropertyWriter[len];
        for (int i = 0; i < len; i++) {
            BeanPropertyWriter bpw = props[i];
            if (bpw != null) {
                result[i] = bpw.rename(transformer);
            }
        }
        return result;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0060 A[PHI: r7
  0x0060: PHI (r7v1 'type' com.fasterxml.jackson.databind.JavaType) = (r7v0 'type' com.fasterxml.jackson.databind.JavaType), (r7v2 'type' com.fasterxml.jackson.databind.JavaType) binds: [B:23:0x0040, B:25:0x004e] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.fasterxml.jackson.databind.ser.ResolvableSerializer
    public void resolve(SerializerProvider provider) throws JsonMappingException {
        JsonSerializer<Object> ser;
        BeanPropertyWriter w2;
        TypeSerializer typeSer;
        JsonSerializer<Object> nullSer;
        BeanPropertyWriter w3;
        int filteredCount = this._filteredProps == null ? 0 : this._filteredProps.length;
        int len = this._props.length;
        for (int i = 0; i < len; i++) {
            BeanPropertyWriter prop = this._props[i];
            if (!prop.willSuppressNulls() && !prop.hasNullSerializer() && (nullSer = provider.findNullValueSerializer(prop)) != null) {
                prop.assignNullSerializer(nullSer);
                if (i < filteredCount && (w3 = this._filteredProps[i]) != null) {
                    w3.assignNullSerializer(nullSer);
                }
            }
            if (!prop.hasSerializer()) {
                JavaType type = prop.getSerializationType();
                if (type == null) {
                    type = provider.constructType(prop.getGenericPropertyType());
                    if (!type.isFinal()) {
                        if (type.isContainerType() || type.containedTypeCount() > 0) {
                            prop.setNonTrivialBaseType(type);
                        }
                    } else {
                        ser = provider.findValueSerializer(type, prop);
                        if (type.isContainerType() && (typeSer = (TypeSerializer) type.getContentType().getTypeHandler()) != null && (ser instanceof ContainerSerializer)) {
                            JsonSerializer<Object> ser2 = ((ContainerSerializer) ser).withValueTypeSerializer(typeSer);
                            ser = ser2;
                        }
                        prop.assignSerializer(ser);
                        if (i >= filteredCount && (w2 = this._filteredProps[i]) != null) {
                            w2.assignSerializer(ser);
                        }
                    }
                } else {
                    ser = provider.findValueSerializer(type, prop);
                    if (type.isContainerType()) {
                        JsonSerializer<Object> ser3 = ((ContainerSerializer) ser).withValueTypeSerializer(typeSer);
                        ser = ser3;
                    }
                    prop.assignSerializer(ser);
                    if (i >= filteredCount) {
                    }
                }
            }
        }
        if (this._anyGetterWriter != null) {
            this._anyGetterWriter.resolve(provider);
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
        ObjectIdWriter oiw = this._objectIdWriter;
        String[] ignorals = null;
        AnnotationIntrospector intr = provider.getAnnotationIntrospector();
        if (property != null && intr != null) {
            AnnotatedMember accessor = property.getMember();
            ignorals = intr.findPropertiesToIgnore(accessor);
            ObjectIdInfo objectIdInfo = intr.findObjectIdInfo(accessor);
            if (objectIdInfo != null) {
                Class<?> implClass = objectIdInfo.getGeneratorType();
                JavaType type = provider.constructType(implClass);
                JavaType idType = provider.getTypeFactory().findTypeParameters(type, ObjectIdGenerator.class)[0];
                if (implClass == ObjectIdGenerators.PropertyGenerator.class) {
                    String propName = objectIdInfo.getPropertyName();
                    int i = 0;
                    int len = this._props.length;
                    while (true) {
                        if (i == len) {
                            throw new IllegalArgumentException("Invalid Object Id definition for " + this._handledType.getName() + ": can not find property with name '" + propName + "'");
                        }
                        BeanPropertyWriter prop = this._props[i];
                        if (!propName.equals(prop.getName())) {
                            i++;
                        } else {
                            if (i > 0) {
                                System.arraycopy(this._props, 0, this._props, 1, i);
                                this._props[0] = prop;
                                if (this._filteredProps != null) {
                                    BeanPropertyWriter fp = this._filteredProps[i];
                                    System.arraycopy(this._filteredProps, 0, this._filteredProps, 1, i);
                                    this._filteredProps[0] = fp;
                                }
                            }
                            JavaType idType2 = prop.getType();
                            ObjectIdGenerator<?> gen = new PropertyBasedObjectIdGenerator(objectIdInfo, prop);
                            oiw = ObjectIdWriter.construct(idType2, null, gen);
                            break;
                        }
                    }
                } else {
                    ObjectIdGenerator<?> gen2 = provider.objectIdGeneratorInstance(accessor, objectIdInfo);
                    oiw = ObjectIdWriter.construct(idType, objectIdInfo.getPropertyName(), gen2);
                }
            }
        }
        BeanSerializerBase contextual = this;
        if (oiw != null) {
            JsonSerializer<?> ser = provider.findValueSerializer(oiw.idType, property);
            ObjectIdWriter oiw2 = oiw.withSerializer(ser);
            if (oiw2 != this._objectIdWriter) {
                contextual = contextual.withObjectIdWriter(oiw2);
            }
        }
        if (ignorals != null && ignorals.length != 0) {
            return contextual.withIgnorals(ignorals);
        }
        return contextual;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public boolean usesObjectId() {
        return this._objectIdWriter != null;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(Object bean, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        if (this._objectIdWriter != null) {
            _serializeWithObjectId(bean, jgen, provider, typeSer);
            return;
        }
        String typeStr = this._typeId == null ? null : _customTypeId(bean);
        if (typeStr == null) {
            typeSer.writeTypePrefixForObject(bean, jgen);
        } else {
            typeSer.writeCustomTypePrefixForObject(bean, jgen, typeStr);
        }
        if (this._propertyFilterId != null) {
            serializeFieldsFiltered(bean, jgen, provider);
        } else {
            serializeFields(bean, jgen, provider);
        }
        if (typeStr == null) {
            typeSer.writeTypeSuffixForObject(bean, jgen);
        } else {
            typeSer.writeCustomTypeSuffixForObject(bean, jgen, typeStr);
        }
    }

    private final void _serializeWithObjectId(Object bean, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        ObjectIdWriter w = this._objectIdWriter;
        WritableObjectId oid = provider.findObjectId(bean, w.generator);
        Object id = oid.id;
        if (id != null) {
            oid.serializer.serialize(id, jgen, provider);
            return;
        }
        oid.serializer = w.serializer;
        Object id2 = oid.generator.generateId(bean);
        oid.id = id2;
        String typeStr = this._typeId == null ? null : _customTypeId(bean);
        if (typeStr == null) {
            typeSer.writeTypePrefixForObject(bean, jgen);
        } else {
            typeSer.writeCustomTypePrefixForObject(bean, jgen, typeStr);
        }
        SerializedString name = w.propertyName;
        if (name != null) {
            jgen.writeFieldName(name);
            w.serializer.serialize(id2, jgen, provider);
        }
        if (this._propertyFilterId != null) {
            serializeFieldsFiltered(bean, jgen, provider);
        } else {
            serializeFields(bean, jgen, provider);
        }
        if (typeStr == null) {
            typeSer.writeTypeSuffixForObject(bean, jgen);
        } else {
            typeSer.writeCustomTypeSuffixForObject(bean, jgen, typeStr);
        }
    }

    private final String _customTypeId(Object bean) {
        Object typeId = this._typeId.getValue(bean);
        if (typeId == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        return typeId instanceof String ? (String) typeId : typeId.toString();
    }

    protected void serializeFields(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        BeanPropertyWriter[] props;
        if (this._filteredProps != null && provider.getSerializationView() != null) {
            props = this._filteredProps;
        } else {
            props = this._props;
        }
        int i = 0;
        try {
            int len = props.length;
            while (i < len) {
                BeanPropertyWriter prop = props[i];
                if (prop != null) {
                    prop.serializeAsField(bean, jgen, provider);
                }
                i++;
            }
            if (this._anyGetterWriter != null) {
                this._anyGetterWriter.getAndSerialize(bean, jgen, provider);
            }
        } catch (Exception e) {
            String name = i == props.length ? "[anySetter]" : props[i].getName();
            wrapAndThrow(provider, e, bean, name);
        } catch (StackOverflowError e2) {
            JsonMappingException mapE = new JsonMappingException("Infinite recursion (StackOverflowError)");
            String name2 = i == props.length ? "[anySetter]" : props[i].getName();
            mapE.prependPath(new JsonMappingException.Reference(bean, name2));
            throw mapE;
        }
    }

    protected void serializeFieldsFiltered(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        BeanPropertyWriter[] props;
        if (this._filteredProps != null && provider.getSerializationView() != null) {
            props = this._filteredProps;
        } else {
            props = this._props;
        }
        BeanPropertyFilter filter = findFilter(provider);
        if (filter == null) {
            serializeFields(bean, jgen, provider);
            return;
        }
        int i = 0;
        try {
            int len = props.length;
            while (i < len) {
                BeanPropertyWriter prop = props[i];
                if (prop != null) {
                    filter.serializeAsField(bean, jgen, provider, prop);
                }
                i++;
            }
            if (this._anyGetterWriter != null) {
                this._anyGetterWriter.getAndSerialize(bean, jgen, provider);
            }
        } catch (Exception e) {
            String name = i == props.length ? "[anySetter]" : props[i].getName();
            wrapAndThrow(provider, e, bean, name);
        } catch (StackOverflowError e2) {
            JsonMappingException mapE = new JsonMappingException("Infinite recursion (StackOverflowError)");
            String name2 = i == props.length ? "[anySetter]" : props[i].getName();
            mapE.prependPath(new JsonMappingException.Reference(bean, name2));
            throw mapE;
        }
    }

    protected BeanPropertyFilter findFilter(SerializerProvider provider) throws JsonMappingException {
        Object filterId = this._propertyFilterId;
        FilterProvider filters = provider.getFilterProvider();
        if (filters == null) {
            throw new JsonMappingException("Can not resolve BeanPropertyFilter with id '" + filterId + "'; no FilterProvider configured");
        }
        BeanPropertyFilter filter = filters.findFilter(filterId);
        return filter;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        String id;
        ObjectNode o = createSchemaNode("object", true);
        JsonSerializableSchema ann = (JsonSerializableSchema) this._handledType.getAnnotation(JsonSerializableSchema.class);
        if (ann != null && (id = ann.id()) != null && id.length() > 0) {
            o.put("id", id);
        }
        ObjectNode propertiesNode = o.objectNode();
        for (int i = 0; i < this._props.length; i++) {
            BeanPropertyWriter prop = this._props[i];
            JavaType propType = prop.getSerializationType();
            Type hint = propType == null ? prop.getGenericPropertyType() : propType.getRawClass();
            Object serializer = prop.getSerializer();
            if (serializer == null) {
                Class<?> serType = prop.getRawSerializationType();
                if (serType == null) {
                    serType = prop.getPropertyType();
                }
                serializer = provider.findValueSerializer(serType, prop);
            }
            JsonNode schemaNode = serializer instanceof SchemaAware ? ((SchemaAware) serializer).getSchema(provider, hint) : JsonSchema.getDefaultSchemaNode();
            propertiesNode.put(prop.getName(), schemaNode);
        }
        o.put("properties", propertiesNode);
        return o;
    }
}
