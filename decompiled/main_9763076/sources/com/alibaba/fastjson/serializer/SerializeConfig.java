package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.ServiceLoader;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import javax.xml.datatype.XMLGregorianCalendar;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SerializeConfig {
    private boolean asm;
    private ASMSerializerFactory asmFactory;
    private final boolean fieldBased;
    public PropertyNamingStrategy propertyNamingStrategy;
    private final IdentityHashMap<Type, ObjectSerializer> serializers;
    protected String typeKey;
    public static final SerializeConfig globalInstance = new SerializeConfig();
    private static boolean awtError = false;
    private static boolean jdk8Error = false;
    private static boolean oracleJdbcError = false;
    private static boolean springfoxError = false;
    private static boolean guavaError = false;
    private static boolean jsonnullError = false;

    private final JavaBeanSerializer createASMSerializer(SerializeBeanInfo beanInfo) throws Exception {
        JavaBeanSerializer serializer = this.asmFactory.createJavaBeanSerializer(beanInfo);
        for (int i = 0; i < serializer.sortedGetters.length; i++) {
            FieldSerializer fieldDeser = serializer.sortedGetters[i];
            Class<?> fieldClass = fieldDeser.fieldInfo.fieldClass;
            if (fieldClass.isEnum()) {
                ObjectSerializer fieldSer = getObjectWriter(fieldClass);
                if (!(fieldSer instanceof EnumSerializer)) {
                    serializer.writeDirect = false;
                }
            }
        }
        return serializer;
    }

    public final ObjectSerializer createJavaBeanSerializer(Class<?> clazz) {
        SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, null, this.propertyNamingStrategy, this.fieldBased);
        return (beanInfo.fields.length == 0 && Iterable.class.isAssignableFrom(clazz)) ? MiscCodec.instance : createJavaBeanSerializer(beanInfo);
    }

    public ObjectSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) {
        JSONType jsonType = beanInfo.jsonType;
        if (jsonType != null) {
            Class<?> serializerClass = jsonType.serializer();
            if (serializerClass != Void.class) {
                try {
                    Object seralizer = serializerClass.newInstance();
                    if (seralizer instanceof ObjectSerializer) {
                        return (ObjectSerializer) seralizer;
                    }
                } catch (Throwable th) {
                }
            }
            if (!jsonType.asm()) {
                this.asm = false;
            }
            for (SerializerFeature feature : jsonType.serialzeFeatures()) {
                if (SerializerFeature.WriteNonStringValueAsString == feature || SerializerFeature.WriteEnumUsingToString == feature || SerializerFeature.NotWriteDefaultValue == feature) {
                    this.asm = false;
                    break;
                }
            }
        }
        Class<?> clazz = beanInfo.beanType;
        if (!Modifier.isPublic(beanInfo.beanType.getModifiers())) {
            return new JavaBeanSerializer(beanInfo);
        }
        boolean asm = this.asm && !this.fieldBased;
        if ((asm && this.asmFactory.classLoader.isExternalClass(clazz)) || clazz == Serializable.class || clazz == Object.class) {
            asm = false;
        }
        if (asm && !ASMUtils.checkName(clazz.getSimpleName())) {
            asm = false;
        }
        if (asm && beanInfo.beanType.isInterface()) {
            asm = false;
        }
        if (asm) {
            FieldInfo[] fieldInfoArr = beanInfo.fields;
            int length = fieldInfoArr.length;
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= length) {
                    break;
                }
                FieldInfo fieldInfo = fieldInfoArr[i2];
                Field field = fieldInfo.field;
                if (field != null && !field.getType().equals(fieldInfo.fieldClass)) {
                    asm = false;
                    break;
                }
                Method method = fieldInfo.method;
                if (method != null && !method.getReturnType().equals(fieldInfo.fieldClass)) {
                    asm = false;
                    break;
                }
                JSONField annotation = fieldInfo.getAnnotation();
                if (annotation != null) {
                    String format = annotation.format();
                    if (format.length() != 0 && (fieldInfo.fieldClass != String.class || !"trim".equals(format))) {
                        asm = false;
                        break;
                    }
                    if (!ASMUtils.checkName(annotation.name()) || annotation.jsonDirect() || annotation.serializeUsing() != Void.class || annotation.unwrapped()) {
                        asm = false;
                        break;
                    }
                    for (SerializerFeature feature2 : annotation.serialzeFeatures()) {
                        if (SerializerFeature.WriteNonStringValueAsString == feature2 || SerializerFeature.WriteEnumUsingToString == feature2 || SerializerFeature.NotWriteDefaultValue == feature2 || SerializerFeature.WriteClassName == feature2) {
                            asm = false;
                            break;
                        }
                    }
                    if (TypeUtils.isAnnotationPresentOneToMany(method) || TypeUtils.isAnnotationPresentManyToMany(method)) {
                        asm = true;
                        break;
                    }
                }
                i = i2 + 1;
            }
        }
        if (asm) {
            try {
                ObjectSerializer asmSerializer = createASMSerializer(beanInfo);
                if (asmSerializer != null) {
                    return asmSerializer;
                }
            } catch (ClassCastException e) {
            } catch (ClassFormatError e2) {
            } catch (ClassNotFoundException e3) {
            } catch (Throwable e4) {
                throw new JSONException("create asm serializer error, class " + clazz, e4);
            }
        }
        return new JavaBeanSerializer(beanInfo);
    }

    public static SerializeConfig getGlobalInstance() {
        return globalInstance;
    }

    public SerializeConfig() {
        this(8192);
    }

    public SerializeConfig(int tableSize) {
        this(tableSize, false);
    }

    public SerializeConfig(int tableSize, boolean fieldBase) {
        this.asm = !ASMUtils.IS_ANDROID;
        this.typeKey = JSON.DEFAULT_TYPE_KEY;
        this.fieldBased = fieldBase;
        this.serializers = new IdentityHashMap<>(tableSize);
        try {
            if (this.asm) {
                this.asmFactory = new ASMSerializerFactory();
            }
        } catch (Throwable th) {
            this.asm = false;
        }
        put(Boolean.class, BooleanCodec.instance);
        put(Character.class, CharacterCodec.instance);
        put(Byte.class, IntegerCodec.instance);
        put(Short.class, IntegerCodec.instance);
        put(Integer.class, IntegerCodec.instance);
        put(Long.class, LongCodec.instance);
        put(Float.class, FloatCodec.instance);
        put(Double.class, DoubleSerializer.instance);
        put(BigDecimal.class, BigDecimalCodec.instance);
        put(BigInteger.class, BigIntegerCodec.instance);
        put(String.class, StringCodec.instance);
        put(byte[].class, PrimitiveArraySerializer.instance);
        put(short[].class, PrimitiveArraySerializer.instance);
        put(int[].class, PrimitiveArraySerializer.instance);
        put(long[].class, PrimitiveArraySerializer.instance);
        put(float[].class, PrimitiveArraySerializer.instance);
        put(double[].class, PrimitiveArraySerializer.instance);
        put(boolean[].class, PrimitiveArraySerializer.instance);
        put(char[].class, PrimitiveArraySerializer.instance);
        put(Object[].class, ObjectArrayCodec.instance);
        put(Class.class, MiscCodec.instance);
        put(SimpleDateFormat.class, MiscCodec.instance);
        put(Currency.class, new MiscCodec());
        put(TimeZone.class, MiscCodec.instance);
        put(InetAddress.class, MiscCodec.instance);
        put(Inet4Address.class, MiscCodec.instance);
        put(Inet6Address.class, MiscCodec.instance);
        put(InetSocketAddress.class, MiscCodec.instance);
        put(File.class, MiscCodec.instance);
        put(Appendable.class, AppendableSerializer.instance);
        put(StringBuffer.class, AppendableSerializer.instance);
        put(StringBuilder.class, AppendableSerializer.instance);
        put(Charset.class, ToStringSerializer.instance);
        put(Pattern.class, ToStringSerializer.instance);
        put(Locale.class, ToStringSerializer.instance);
        put(URI.class, ToStringSerializer.instance);
        put(URL.class, ToStringSerializer.instance);
        put(UUID.class, ToStringSerializer.instance);
        put(AtomicBoolean.class, AtomicCodec.instance);
        put(AtomicInteger.class, AtomicCodec.instance);
        put(AtomicLong.class, AtomicCodec.instance);
        put(AtomicReference.class, ReferenceCodec.instance);
        put(AtomicIntegerArray.class, AtomicCodec.instance);
        put(AtomicLongArray.class, AtomicCodec.instance);
        put(WeakReference.class, ReferenceCodec.instance);
        put(SoftReference.class, ReferenceCodec.instance);
        put(LinkedList.class, CollectionCodec.instance);
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        return getObjectWriter(clazz, true);
    }

    private ObjectSerializer getObjectWriter(Class<?> clazz, boolean create) {
        ClassLoader classLoader;
        ObjectSerializer writer = this.serializers.get(clazz);
        if (writer == null) {
            try {
                for (Object o : ServiceLoader.load(AutowiredObjectSerializer.class, Thread.currentThread().getContextClassLoader())) {
                    if (o instanceof AutowiredObjectSerializer) {
                        AutowiredObjectSerializer autowired = (AutowiredObjectSerializer) o;
                        for (Type forType : autowired.getAutowiredFor()) {
                            put(forType, autowired);
                        }
                    }
                }
            } catch (ClassCastException e) {
            }
            writer = this.serializers.get(clazz);
        }
        if (writer == null && (classLoader = JSON.class.getClassLoader()) != Thread.currentThread().getContextClassLoader()) {
            try {
                for (Object o2 : ServiceLoader.load(AutowiredObjectSerializer.class, classLoader)) {
                    if (o2 instanceof AutowiredObjectSerializer) {
                        AutowiredObjectSerializer autowired2 = (AutowiredObjectSerializer) o2;
                        for (Type forType2 : autowired2.getAutowiredFor()) {
                            put(forType2, autowired2);
                        }
                    }
                }
            } catch (ClassCastException e2) {
            }
            writer = this.serializers.get(clazz);
        }
        if (writer == null) {
            String className = clazz.getName();
            if (Map.class.isAssignableFrom(clazz)) {
                writer = MapSerializer.instance;
                put(clazz, writer);
            } else if (List.class.isAssignableFrom(clazz)) {
                writer = ListSerializer.instance;
                put(clazz, writer);
            } else if (Collection.class.isAssignableFrom(clazz)) {
                writer = CollectionCodec.instance;
                put(clazz, writer);
            } else if (Date.class.isAssignableFrom(clazz)) {
                writer = DateCodec.instance;
                put(clazz, writer);
            } else if (JSONAware.class.isAssignableFrom(clazz)) {
                writer = JSONAwareSerializer.instance;
                put(clazz, writer);
            } else if (JSONSerializable.class.isAssignableFrom(clazz)) {
                writer = JSONSerializableSerializer.instance;
                put(clazz, writer);
            } else if (JSONStreamAware.class.isAssignableFrom(clazz)) {
                writer = MiscCodec.instance;
                put(clazz, writer);
            } else if (clazz.isEnum()) {
                JSONType jsonType = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
                if (jsonType != null && jsonType.serializeEnumAsJavaBean()) {
                    writer = createJavaBeanSerializer(clazz);
                    put(clazz, writer);
                } else {
                    writer = EnumSerializer.instance;
                    put(clazz, writer);
                }
            } else {
                Class<?> superClass = clazz.getSuperclass();
                if (superClass != null && superClass.isEnum()) {
                    JSONType jsonType2 = (JSONType) TypeUtils.getAnnotation(superClass, JSONType.class);
                    if (jsonType2 != null && jsonType2.serializeEnumAsJavaBean()) {
                        writer = createJavaBeanSerializer(clazz);
                        put(clazz, writer);
                    } else {
                        writer = EnumSerializer.instance;
                        put(clazz, writer);
                    }
                } else if (clazz.isArray()) {
                    Class<?> componentType = clazz.getComponentType();
                    ObjectSerializer compObjectSerializer = getObjectWriter(componentType);
                    writer = new ArraySerializer(componentType, compObjectSerializer);
                    put(clazz, writer);
                } else if (Throwable.class.isAssignableFrom(clazz)) {
                    SerializeBeanInfo beanInfo = TypeUtils.buildBeanInfo(clazz, null, this.propertyNamingStrategy);
                    beanInfo.features |= SerializerFeature.WriteClassName.mask;
                    writer = new JavaBeanSerializer(beanInfo);
                    put(clazz, writer);
                } else if (TimeZone.class.isAssignableFrom(clazz) || Map.Entry.class.isAssignableFrom(clazz)) {
                    writer = MiscCodec.instance;
                    put(clazz, writer);
                } else if (Appendable.class.isAssignableFrom(clazz)) {
                    writer = AppendableSerializer.instance;
                    put(clazz, writer);
                } else if (Charset.class.isAssignableFrom(clazz)) {
                    writer = ToStringSerializer.instance;
                    put(clazz, writer);
                } else if (Enumeration.class.isAssignableFrom(clazz)) {
                    writer = EnumerationSerializer.instance;
                    put(clazz, writer);
                } else if (Calendar.class.isAssignableFrom(clazz) || XMLGregorianCalendar.class.isAssignableFrom(clazz)) {
                    writer = CalendarCodec.instance;
                    put(clazz, writer);
                } else if (Clob.class.isAssignableFrom(clazz)) {
                    writer = ClobSeriliazer.instance;
                    put(clazz, writer);
                } else if (TypeUtils.isPath(clazz)) {
                    writer = ToStringSerializer.instance;
                    put(clazz, writer);
                } else if (Iterator.class.isAssignableFrom(clazz)) {
                    writer = MiscCodec.instance;
                    put(clazz, writer);
                } else {
                    if (className.startsWith("java.awt.") && AwtCodec.support(clazz) && !awtError) {
                        try {
                            String[] names = {"java.awt.Color", "java.awt.Font", "java.awt.Point", "java.awt.Rectangle"};
                            for (String name : names) {
                                if (name.equals(className)) {
                                    Type cls = Class.forName(name);
                                    writer = AwtCodec.instance;
                                    put(cls, writer);
                                    return writer;
                                }
                            }
                        } catch (Throwable th) {
                            awtError = true;
                        }
                    }
                    if (!jdk8Error && (className.startsWith("java.time.") || className.startsWith("java.util.Optional") || className.equals("java.util.concurrent.atomic.LongAdder") || className.equals("java.util.concurrent.atomic.DoubleAdder"))) {
                        try {
                            String[] names2 = {"java.time.LocalDateTime", "java.time.LocalDate", "java.time.LocalTime", "java.time.ZonedDateTime", "java.time.OffsetDateTime", "java.time.OffsetTime", "java.time.ZoneOffset", "java.time.ZoneRegion", "java.time.Period", "java.time.Duration", "java.time.Instant"};
                            for (String name2 : names2) {
                                if (name2.equals(className)) {
                                    Type cls2 = Class.forName(name2);
                                    ObjectSerializer writer2 = Jdk8DateCodec.instance;
                                    put(cls2, writer2);
                                    return writer2;
                                }
                            }
                            String[] names3 = {"java.util.Optional", "java.util.OptionalDouble", "java.util.OptionalInt", "java.util.OptionalLong"};
                            for (String name3 : names3) {
                                if (name3.equals(className)) {
                                    Type cls3 = Class.forName(name3);
                                    ObjectSerializer writer3 = OptionalCodec.instance;
                                    put(cls3, writer3);
                                    return writer3;
                                }
                            }
                            String[] names4 = {"java.util.concurrent.atomic.LongAdder", "java.util.concurrent.atomic.DoubleAdder"};
                            for (String name4 : names4) {
                                if (name4.equals(className)) {
                                    Type cls4 = Class.forName(name4);
                                    ObjectSerializer writer4 = AdderSerializer.instance;
                                    put(cls4, writer4);
                                    return writer4;
                                }
                            }
                        } catch (Throwable th2) {
                            jdk8Error = true;
                        }
                    }
                    if (!oracleJdbcError && className.startsWith("oracle.sql.")) {
                        try {
                            String[] names5 = {"oracle.sql.DATE", "oracle.sql.TIMESTAMP"};
                            for (String name5 : names5) {
                                if (name5.equals(className)) {
                                    Type cls5 = Class.forName(name5);
                                    writer = DateCodec.instance;
                                    put(cls5, writer);
                                    return writer;
                                }
                            }
                        } catch (Throwable th3) {
                            oracleJdbcError = true;
                        }
                    }
                    if (!springfoxError && className.equals("springfox.documentation.spring.web.json.Json")) {
                        try {
                            Type cls6 = Class.forName("springfox.documentation.spring.web.json.Json");
                            writer = SwaggerJsonSerializer.instance;
                            put(cls6, writer);
                            return writer;
                        } catch (ClassNotFoundException e3) {
                            springfoxError = true;
                        }
                    }
                    if (!guavaError && className.startsWith("com.google.common.collect.")) {
                        try {
                            String[] names6 = {"com.google.common.collect.HashMultimap", "com.google.common.collect.LinkedListMultimap", "com.google.common.collect.ArrayListMultimap", "com.google.common.collect.TreeMultimap"};
                            for (String name6 : names6) {
                                if (name6.equals(className)) {
                                    Type cls7 = Class.forName(name6);
                                    writer = GuavaCodec.instance;
                                    put(cls7, writer);
                                    return writer;
                                }
                            }
                        } catch (ClassNotFoundException e4) {
                            guavaError = true;
                        }
                    }
                    if (!jsonnullError && className.equals("net.sf.json.JSONNull")) {
                        try {
                            Type cls8 = Class.forName("net.sf.json.JSONNull");
                            writer = MiscCodec.instance;
                            put(cls8, writer);
                            return writer;
                        } catch (ClassNotFoundException e5) {
                            jsonnullError = true;
                        }
                    }
                    Class<?>[] interfaces = clazz.getInterfaces();
                    if (interfaces.length == 1 && interfaces[0].isAnnotation()) {
                        return AnnotationSerializer.instance;
                    }
                    if (TypeUtils.isProxy(clazz)) {
                        Class<?> superClazz = clazz.getSuperclass();
                        ObjectSerializer superWriter = getObjectWriter(superClazz);
                        put(clazz, superWriter);
                        return superWriter;
                    }
                    if (Proxy.isProxyClass(clazz)) {
                        Class<?> cls9 = null;
                        if (interfaces.length == 2) {
                            cls9 = interfaces[1];
                        } else {
                            for (Class<?> cls10 : interfaces) {
                                if (!cls10.getName().startsWith("org.springframework.aop.")) {
                                    if (cls9 != null) {
                                        cls9 = null;
                                        break;
                                    }
                                    cls9 = cls10;
                                }
                            }
                        }
                        if (cls9 != null) {
                            ObjectSerializer superWriter2 = getObjectWriter(cls9);
                            put(clazz, superWriter2);
                            return superWriter2;
                        }
                    }
                    if (create) {
                        writer = createJavaBeanSerializer(clazz);
                        put(clazz, writer);
                    }
                }
            }
            if (writer == null) {
                writer = this.serializers.get(clazz);
            }
        }
        return writer;
    }

    public boolean put(Type type, ObjectSerializer value) {
        return this.serializers.put(type, value);
    }
}
