package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BeanPropertyWriter implements BeanProperty {
    public static final Object MARKER_FOR_EMPTY = new Object();
    protected final Method _accessorMethod;
    protected final JavaType _cfgSerializationType;
    protected final Annotations _contextAnnotations;
    protected final JavaType _declaredType;
    protected PropertySerializerMap _dynamicSerializers;
    protected final Field _field;
    protected final Class<?>[] _includeInViews;
    protected HashMap<Object, Object> _internalSettings;
    protected final AnnotatedMember _member;
    protected final SerializedString _name;
    protected JavaType _nonTrivialBaseType;
    protected JsonSerializer<Object> _nullSerializer;
    protected JsonSerializer<Object> _serializer;
    protected final boolean _suppressNulls;
    protected final Object _suppressableValue;
    protected TypeSerializer _typeSerializer;

    public BeanPropertyWriter(BeanPropertyDefinition propDef, AnnotatedMember member, Annotations contextAnnotations, JavaType declaredType, JsonSerializer<?> ser, TypeSerializer typeSer, JavaType serType, boolean suppressNulls, Object suppressableValue) {
        this._member = member;
        this._contextAnnotations = contextAnnotations;
        this._name = new SerializedString(propDef.getName());
        this._declaredType = declaredType;
        this._serializer = ser;
        this._dynamicSerializers = ser == null ? PropertySerializerMap.emptyMap() : null;
        this._typeSerializer = typeSer;
        this._cfgSerializationType = serType;
        if (member instanceof AnnotatedField) {
            this._accessorMethod = null;
            this._field = (Field) member.getMember();
        } else if (member instanceof AnnotatedMethod) {
            this._accessorMethod = (Method) member.getMember();
            this._field = null;
        } else {
            throw new IllegalArgumentException("Can not pass member of type " + member.getClass().getName());
        }
        this._suppressNulls = suppressNulls;
        this._suppressableValue = suppressableValue;
        this._includeInViews = propDef.findViews();
        this._nullSerializer = null;
    }

    protected BeanPropertyWriter(BeanPropertyWriter base) {
        this(base, base._name);
    }

    protected BeanPropertyWriter(BeanPropertyWriter base, SerializedString name) {
        this._name = name;
        this._member = base._member;
        this._contextAnnotations = base._contextAnnotations;
        this._declaredType = base._declaredType;
        this._accessorMethod = base._accessorMethod;
        this._field = base._field;
        this._serializer = base._serializer;
        this._nullSerializer = base._nullSerializer;
        if (base._internalSettings != null) {
            this._internalSettings = new HashMap<>(base._internalSettings);
        }
        this._cfgSerializationType = base._cfgSerializationType;
        this._dynamicSerializers = base._dynamicSerializers;
        this._suppressNulls = base._suppressNulls;
        this._suppressableValue = base._suppressableValue;
        this._includeInViews = base._includeInViews;
        this._typeSerializer = base._typeSerializer;
        this._nonTrivialBaseType = base._nonTrivialBaseType;
    }

    public BeanPropertyWriter rename(NameTransformer transformer) {
        String newName = transformer.transform(this._name.getValue());
        return newName.equals(this._name.toString()) ? this : new BeanPropertyWriter(this, new SerializedString(newName));
    }

    public void assignSerializer(JsonSerializer<Object> ser) {
        if (this._serializer != null && this._serializer != ser) {
            throw new IllegalStateException("Can not override serializer");
        }
        this._serializer = ser;
    }

    public void assignNullSerializer(JsonSerializer<Object> nullSer) {
        if (this._nullSerializer != null && this._nullSerializer != nullSer) {
            throw new IllegalStateException("Can not override null serializer");
        }
        this._nullSerializer = nullSer;
    }

    public BeanPropertyWriter unwrappingWriter(NameTransformer unwrapper) {
        return new UnwrappingBeanPropertyWriter(this, unwrapper);
    }

    public void setNonTrivialBaseType(JavaType t) {
        this._nonTrivialBaseType = t;
    }

    @Override // com.fasterxml.jackson.databind.BeanProperty, com.fasterxml.jackson.databind.util.Named
    public String getName() {
        return this._name.getValue();
    }

    @Override // com.fasterxml.jackson.databind.BeanProperty
    public JavaType getType() {
        return this._declaredType;
    }

    @Override // com.fasterxml.jackson.databind.BeanProperty
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) this._member.getAnnotation(cls);
    }

    @Override // com.fasterxml.jackson.databind.BeanProperty
    public <A extends Annotation> A getContextAnnotation(Class<A> cls) {
        return (A) this._contextAnnotations.get(cls);
    }

    @Override // com.fasterxml.jackson.databind.BeanProperty
    public AnnotatedMember getMember() {
        return this._member;
    }

    public Object getInternalSetting(Object key) {
        if (this._internalSettings == null) {
            return null;
        }
        return this._internalSettings.get(key);
    }

    public Object setInternalSetting(Object key, Object value) {
        if (this._internalSettings == null) {
            this._internalSettings = new HashMap<>();
        }
        return this._internalSettings.put(key, value);
    }

    public Object removeInternalSetting(Object key) {
        Object removed = null;
        if (this._internalSettings != null) {
            removed = this._internalSettings.remove(key);
            if (this._internalSettings.size() == 0) {
                this._internalSettings = null;
            }
        }
        return removed;
    }

    public SerializedString getSerializedName() {
        return this._name;
    }

    public boolean hasSerializer() {
        return this._serializer != null;
    }

    public boolean hasNullSerializer() {
        return this._nullSerializer != null;
    }

    public boolean willSuppressNulls() {
        return this._suppressNulls;
    }

    public JsonSerializer<Object> getSerializer() {
        return this._serializer;
    }

    public JavaType getSerializationType() {
        return this._cfgSerializationType;
    }

    public Class<?> getRawSerializationType() {
        if (this._cfgSerializationType == null) {
            return null;
        }
        return this._cfgSerializationType.getRawClass();
    }

    public Class<?> getPropertyType() {
        return this._accessorMethod != null ? this._accessorMethod.getReturnType() : this._field.getType();
    }

    public Type getGenericPropertyType() {
        return this._accessorMethod != null ? this._accessorMethod.getGenericReturnType() : this._field.getGenericType();
    }

    public Class<?>[] getViews() {
        return this._includeInViews;
    }

    public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov) throws Exception {
        Class<?> cls;
        PropertySerializerMap map;
        Object value = get(bean);
        if (value == null) {
            if (this._nullSerializer != null) {
                jgen.writeFieldName(this._name);
                this._nullSerializer.serialize(null, jgen, prov);
                return;
            }
            return;
        }
        JsonSerializer<?> jsonSerializerSerializerFor = this._serializer;
        if (jsonSerializerSerializerFor == null && (jsonSerializerSerializerFor = (map = this._dynamicSerializers).serializerFor((cls = value.getClass()))) == null) {
            jsonSerializerSerializerFor = _findAndAddDynamic(map, cls, prov);
        }
        if (this._suppressableValue != null) {
            if (MARKER_FOR_EMPTY == this._suppressableValue) {
                if (jsonSerializerSerializerFor.isEmpty(value)) {
                    return;
                }
            } else if (this._suppressableValue.equals(value)) {
                return;
            }
        }
        if (value == bean) {
            _handleSelfReference(bean, jsonSerializerSerializerFor);
        }
        jgen.writeFieldName(this._name);
        if (this._typeSerializer == null) {
            jsonSerializerSerializerFor.serialize(value, jgen, prov);
        } else {
            jsonSerializerSerializerFor.serializeWithType(value, jgen, prov, this._typeSerializer);
        }
    }

    protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult result;
        if (this._nonTrivialBaseType != null) {
            JavaType t = provider.constructSpecializedType(this._nonTrivialBaseType, type);
            result = map.findAndAddSerializer(t, provider, this);
        } else {
            result = map.findAndAddSerializer(type, provider, this);
        }
        if (map != result.map) {
            this._dynamicSerializers = result.map;
        }
        return result.serializer;
    }

    public final Object get(Object bean) throws Exception {
        return this._accessorMethod != null ? this._accessorMethod.invoke(bean, new Object[0]) : this._field.get(bean);
    }

    protected void _handleSelfReference(Object bean, JsonSerializer<?> ser) throws JsonMappingException {
        if (ser.usesObjectId()) {
        } else {
            throw new JsonMappingException("Direct self-reference leading to cycle");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        sb.append("property '").append(getName()).append("' (");
        if (this._accessorMethod != null) {
            sb.append("via method ").append(this._accessorMethod.getDeclaringClass().getName()).append("#").append(this._accessorMethod.getName());
        } else {
            sb.append("field \"").append(this._field.getDeclaringClass().getName()).append("#").append(this._field.getName());
        }
        if (this._serializer == null) {
            sb.append(", no static serializer");
        } else {
            sb.append(", static serializer of type " + this._serializer.getClass().getName());
        }
        sb.append(')');
        return sb.toString();
    }
}
