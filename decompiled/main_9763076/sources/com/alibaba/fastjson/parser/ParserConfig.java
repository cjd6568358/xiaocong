package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory;
import com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JSONPDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;
import com.alibaba.fastjson.parser.deserializer.NumberDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessable;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessableDeserializer;
import com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.StackTraceElementDeserializer;
import com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer;
import com.alibaba.fastjson.parser.deserializer.TimeDeserializer;
import com.alibaba.fastjson.serializer.AtomicCodec;
import com.alibaba.fastjson.serializer.AwtCodec;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.BigIntegerCodec;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.CharArrayCodec;
import com.alibaba.fastjson.serializer.CharacterCodec;
import com.alibaba.fastjson.serializer.CollectionCodec;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.FloatCodec;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.MiscCodec;
import com.alibaba.fastjson.serializer.ObjectArrayCodec;
import com.alibaba.fastjson.serializer.ReferenceCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.ServiceLoader;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.AccessControlException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import javax.xml.datatype.XMLGregorianCalendar;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ParserConfig {
    public static final boolean AUTO_SUPPORT;
    private static final String[] AUTO_TYPE_ACCEPT_LIST;
    public static final String[] DENYS;
    private static boolean awtError;
    public static ParserConfig global;
    private static boolean jdk8Error;
    private String[] acceptList;
    private boolean asmEnable;
    protected ASMDeserializerFactory asmFactory;
    private boolean autoTypeSupport;
    public boolean compatibleWithJavaBean;
    protected ClassLoader defaultClassLoader;
    private String[] denyList;
    private final IdentityHashMap<Type, ObjectDeserializer> deserializers;
    public final boolean fieldBased;
    private int maxTypeNameLength;
    public PropertyNamingStrategy propertyNamingStrategy;
    public final SymbolTable symbolTable;

    static {
        String property = IOUtils.getStringProperty("fastjson.parser.deny");
        DENYS = splitItemsFormProperty(property);
        String property2 = IOUtils.getStringProperty("fastjson.parser.autoTypeSupport");
        AUTO_SUPPORT = "true".equals(property2);
        String property3 = IOUtils.getStringProperty("fastjson.parser.autoTypeAccept");
        String[] items = splitItemsFormProperty(property3);
        if (items == null) {
            items = new String[0];
        }
        AUTO_TYPE_ACCEPT_LIST = items;
        global = new ParserConfig();
        awtError = false;
        jdk8Error = false;
    }

    public static ParserConfig getGlobalInstance() {
        return global;
    }

    public ParserConfig() {
        this(false);
    }

    public ParserConfig(boolean fieldBase) {
        this(null, null, fieldBase);
    }

    private ParserConfig(ASMDeserializerFactory asmFactory, ClassLoader parentClassLoader, boolean fieldBased) {
        this.deserializers = new IdentityHashMap<>();
        this.asmEnable = !ASMUtils.IS_ANDROID;
        this.symbolTable = new SymbolTable(4096);
        this.autoTypeSupport = AUTO_SUPPORT;
        this.denyList = "bsh,com.mchange,com.sun.,java.lang.Thread,java.net.Socket,java.rmi,javax.xml,org.apache.bcel,org.apache.commons.beanutils,org.apache.commons.collections.Transformer,org.apache.commons.collections.functors,org.apache.commons.collections4.comparators,org.apache.commons.fileupload,org.apache.myfaces.context.servlet,org.apache.tomcat,org.apache.wicket.util,org.apache.xalan,org.codehaus.groovy.runtime,org.hibernate,org.jboss,org.mozilla.javascript,org.python.core,org.springframework".split(",");
        this.acceptList = AUTO_TYPE_ACCEPT_LIST;
        this.maxTypeNameLength = 256;
        this.compatibleWithJavaBean = TypeUtils.compatibleWithJavaBean;
        this.fieldBased = fieldBased;
        if (asmFactory == null && !ASMUtils.IS_ANDROID) {
            try {
                if (parentClassLoader == null) {
                    asmFactory = new ASMDeserializerFactory(new ASMClassLoader());
                } else {
                    asmFactory = new ASMDeserializerFactory(parentClassLoader);
                }
            } catch (ExceptionInInitializerError e) {
            } catch (NoClassDefFoundError e2) {
            } catch (AccessControlException e3) {
            }
        }
        this.asmFactory = asmFactory;
        if (asmFactory == null) {
            this.asmEnable = false;
        }
        this.deserializers.put(SimpleDateFormat.class, MiscCodec.instance);
        this.deserializers.put(Timestamp.class, SqlDateDeserializer.instance_timestamp);
        this.deserializers.put(Date.class, SqlDateDeserializer.instance);
        this.deserializers.put(Time.class, TimeDeserializer.instance);
        this.deserializers.put(java.util.Date.class, DateCodec.instance);
        this.deserializers.put(Calendar.class, CalendarCodec.instance);
        this.deserializers.put(XMLGregorianCalendar.class, CalendarCodec.instance);
        this.deserializers.put(JSONObject.class, MapDeserializer.instance);
        this.deserializers.put(JSONArray.class, CollectionCodec.instance);
        this.deserializers.put(Map.class, MapDeserializer.instance);
        this.deserializers.put(HashMap.class, MapDeserializer.instance);
        this.deserializers.put(LinkedHashMap.class, MapDeserializer.instance);
        this.deserializers.put(TreeMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentMap.class, MapDeserializer.instance);
        this.deserializers.put(ConcurrentHashMap.class, MapDeserializer.instance);
        this.deserializers.put(Collection.class, CollectionCodec.instance);
        this.deserializers.put(List.class, CollectionCodec.instance);
        this.deserializers.put(ArrayList.class, CollectionCodec.instance);
        this.deserializers.put(Object.class, JavaObjectDeserializer.instance);
        this.deserializers.put(String.class, StringCodec.instance);
        this.deserializers.put(StringBuffer.class, StringCodec.instance);
        this.deserializers.put(StringBuilder.class, StringCodec.instance);
        this.deserializers.put(Character.TYPE, CharacterCodec.instance);
        this.deserializers.put(Character.class, CharacterCodec.instance);
        this.deserializers.put(Byte.TYPE, NumberDeserializer.instance);
        this.deserializers.put(Byte.class, NumberDeserializer.instance);
        this.deserializers.put(Short.TYPE, NumberDeserializer.instance);
        this.deserializers.put(Short.class, NumberDeserializer.instance);
        this.deserializers.put(Integer.TYPE, IntegerCodec.instance);
        this.deserializers.put(Integer.class, IntegerCodec.instance);
        this.deserializers.put(Long.TYPE, LongCodec.instance);
        this.deserializers.put(Long.class, LongCodec.instance);
        this.deserializers.put(BigInteger.class, BigIntegerCodec.instance);
        this.deserializers.put(BigDecimal.class, BigDecimalCodec.instance);
        this.deserializers.put(Float.TYPE, FloatCodec.instance);
        this.deserializers.put(Float.class, FloatCodec.instance);
        this.deserializers.put(Double.TYPE, NumberDeserializer.instance);
        this.deserializers.put(Double.class, NumberDeserializer.instance);
        this.deserializers.put(Boolean.TYPE, BooleanCodec.instance);
        this.deserializers.put(Boolean.class, BooleanCodec.instance);
        this.deserializers.put(Class.class, MiscCodec.instance);
        this.deserializers.put(char[].class, new CharArrayCodec());
        this.deserializers.put(AtomicBoolean.class, BooleanCodec.instance);
        this.deserializers.put(AtomicInteger.class, IntegerCodec.instance);
        this.deserializers.put(AtomicLong.class, LongCodec.instance);
        this.deserializers.put(AtomicReference.class, ReferenceCodec.instance);
        this.deserializers.put(WeakReference.class, ReferenceCodec.instance);
        this.deserializers.put(SoftReference.class, ReferenceCodec.instance);
        this.deserializers.put(UUID.class, MiscCodec.instance);
        this.deserializers.put(TimeZone.class, MiscCodec.instance);
        this.deserializers.put(Locale.class, MiscCodec.instance);
        this.deserializers.put(Currency.class, MiscCodec.instance);
        this.deserializers.put(InetAddress.class, MiscCodec.instance);
        this.deserializers.put(Inet4Address.class, MiscCodec.instance);
        this.deserializers.put(Inet6Address.class, MiscCodec.instance);
        this.deserializers.put(InetSocketAddress.class, MiscCodec.instance);
        this.deserializers.put(File.class, MiscCodec.instance);
        this.deserializers.put(URI.class, MiscCodec.instance);
        this.deserializers.put(URL.class, MiscCodec.instance);
        this.deserializers.put(Pattern.class, MiscCodec.instance);
        this.deserializers.put(Charset.class, MiscCodec.instance);
        this.deserializers.put(JSONPath.class, MiscCodec.instance);
        this.deserializers.put(Number.class, NumberDeserializer.instance);
        this.deserializers.put(AtomicIntegerArray.class, AtomicCodec.instance);
        this.deserializers.put(AtomicLongArray.class, AtomicCodec.instance);
        this.deserializers.put(StackTraceElement.class, StackTraceElementDeserializer.instance);
        this.deserializers.put(Serializable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(Cloneable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(Comparable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(Closeable.class, JavaObjectDeserializer.instance);
        this.deserializers.put(JSONPObject.class, new JSONPDeserializer());
        addItemsToDeny(DENYS);
        addItemsToAccept(AUTO_TYPE_ACCEPT_LIST);
    }

    private static String[] splitItemsFormProperty(String property) {
        if (property == null || property.length() <= 0) {
            return null;
        }
        return property.split(",");
    }

    private void addItemsToDeny(String[] items) {
        if (items != null) {
            for (String item : items) {
                addDeny(item);
            }
        }
    }

    private void addItemsToAccept(String[] items) {
        if (items != null) {
            for (String item : items) {
                addAccept(item);
            }
        }
    }

    public IdentityHashMap<Type, ObjectDeserializer> getDeserializers() {
        return this.deserializers;
    }

    public ObjectDeserializer getDeserializer(Type type) {
        ObjectDeserializer derializer = this.deserializers.get(type);
        if (derializer == null) {
            if (type instanceof Class) {
                return getDeserializer((Class) type, type);
            }
            if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                if (rawType instanceof Class) {
                    return getDeserializer((Class) rawType, type);
                }
                return getDeserializer(rawType);
            }
            if (type instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type;
                Type[] upperBounds = wildcardType.getUpperBounds();
                if (upperBounds.length == 1) {
                    Type upperBoundType = upperBounds[0];
                    return getDeserializer(upperBoundType);
                }
            }
            return JavaObjectDeserializer.instance;
        }
        return derializer;
    }

    public ObjectDeserializer getDeserializer(Class<?> clazz, Type type) {
        ObjectDeserializer derializer;
        Class<?> mappingTo;
        ObjectDeserializer derializer2 = this.deserializers.get(type);
        if (derializer2 != null) {
            return derializer2;
        }
        if (type == null) {
            type = clazz;
        }
        ObjectDeserializer derializer3 = this.deserializers.get(type);
        if (derializer3 != null) {
            return derializer3;
        }
        JSONType annotation = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
        if (annotation != null && (mappingTo = annotation.mappingTo()) != Void.class) {
            return getDeserializer(mappingTo, mappingTo);
        }
        if ((type instanceof WildcardType) || (type instanceof TypeVariable) || (type instanceof ParameterizedType)) {
            derializer3 = this.deserializers.get(clazz);
        }
        if (derializer3 != null) {
            return derializer3;
        }
        String className = clazz.getName().replace('$', '.');
        if (className.startsWith("java.awt.") && AwtCodec.support(clazz) && !awtError) {
            String[] names = {"java.awt.Point", "java.awt.Font", "java.awt.Rectangle", "java.awt.Color"};
            try {
                for (String name : names) {
                    if (name.equals(className)) {
                        IdentityHashMap<Type, ObjectDeserializer> identityHashMap = this.deserializers;
                        Class<?> cls = Class.forName(name);
                        ObjectDeserializer derializer4 = AwtCodec.instance;
                        identityHashMap.put(cls, derializer4);
                        return derializer4;
                    }
                }
            } catch (Throwable th) {
                awtError = true;
            }
            derializer3 = AwtCodec.instance;
        }
        if (!jdk8Error) {
            try {
                if (className.startsWith("java.time.")) {
                    String[] names2 = {"java.time.LocalDateTime", "java.time.LocalDate", "java.time.LocalTime", "java.time.ZonedDateTime", "java.time.OffsetDateTime", "java.time.OffsetTime", "java.time.ZoneOffset", "java.time.ZoneRegion", "java.time.ZoneId", "java.time.Period", "java.time.Duration", "java.time.Instant"};
                    for (String name2 : names2) {
                        if (name2.equals(className)) {
                            IdentityHashMap<Type, ObjectDeserializer> identityHashMap2 = this.deserializers;
                            Class<?> cls2 = Class.forName(name2);
                            ObjectDeserializer derializer5 = Jdk8DateCodec.instance;
                            identityHashMap2.put(cls2, derializer5);
                            return derializer5;
                        }
                    }
                } else if (className.startsWith("java.util.Optional")) {
                    String[] names3 = {"java.util.Optional", "java.util.OptionalDouble", "java.util.OptionalInt", "java.util.OptionalLong"};
                    for (String name3 : names3) {
                        if (name3.equals(className)) {
                            IdentityHashMap<Type, ObjectDeserializer> identityHashMap3 = this.deserializers;
                            Class<?> cls3 = Class.forName(name3);
                            ObjectDeserializer derializer6 = OptionalCodec.instance;
                            identityHashMap3.put(cls3, derializer6);
                            return derializer6;
                        }
                    }
                }
            } catch (Throwable th2) {
                jdk8Error = true;
            }
        }
        if (className.equals("java.nio.file.Path")) {
            IdentityHashMap<Type, ObjectDeserializer> identityHashMap4 = this.deserializers;
            derializer3 = MiscCodec.instance;
            identityHashMap4.put(clazz, derializer3);
        }
        if (clazz == Map.Entry.class) {
            IdentityHashMap<Type, ObjectDeserializer> identityHashMap5 = this.deserializers;
            derializer3 = MiscCodec.instance;
            identityHashMap5.put(clazz, derializer3);
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            for (AutowiredObjectDeserializer autowired : ServiceLoader.load(AutowiredObjectDeserializer.class, classLoader)) {
                for (Type forType : autowired.getAutowiredFor()) {
                    this.deserializers.put(forType, autowired);
                }
            }
        } catch (Exception e) {
        }
        if (derializer3 == null) {
            derializer3 = this.deserializers.get(type);
        }
        if (derializer3 != null) {
            return derializer3;
        }
        if (clazz.isEnum()) {
            JSONType jsonType = (JSONType) clazz.getAnnotation(JSONType.class);
            if (jsonType != null) {
                Class<?> deserClass = jsonType.deserializer();
                try {
                    ObjectDeserializer derializer7 = (ObjectDeserializer) deserClass.newInstance();
                    this.deserializers.put(clazz, derializer7);
                    return derializer7;
                } catch (Throwable th3) {
                }
            }
            derializer = new EnumDeserializer(clazz);
        } else if (clazz.isArray()) {
            derializer = ObjectArrayCodec.instance;
        } else if (clazz == Set.class || clazz == HashSet.class || clazz == Collection.class || clazz == List.class || clazz == ArrayList.class || Collection.class.isAssignableFrom(clazz)) {
            derializer = CollectionCodec.instance;
        } else if (Map.class.isAssignableFrom(clazz)) {
            derializer = MapDeserializer.instance;
        } else if (Throwable.class.isAssignableFrom(clazz)) {
            derializer = new ThrowableDeserializer(this, clazz);
        } else if (PropertyProcessable.class.isAssignableFrom(clazz)) {
            derializer = new PropertyProcessableDeserializer(clazz);
        } else {
            derializer = createJavaBeanDeserializer(clazz, type);
        }
        putDeserializer(type, derializer);
        return derializer;
    }

    public ObjectDeserializer createJavaBeanDeserializer(Class<?> clazz, Type type) {
        boolean asmEnable = this.asmEnable & (!this.fieldBased);
        if (asmEnable) {
            JSONType jsonType = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
            if (jsonType != null) {
                Class<?> deserializerClass = jsonType.deserializer();
                if (deserializerClass != Void.class) {
                    try {
                        Object deseralizer = deserializerClass.newInstance();
                        if (deseralizer instanceof ObjectDeserializer) {
                            return (ObjectDeserializer) deseralizer;
                        }
                    } catch (Throwable th) {
                    }
                }
                asmEnable = jsonType.asm();
            }
            if (asmEnable) {
                Class<?> superClass = JavaBeanInfo.getBuilderClass(clazz, jsonType);
                if (superClass == null) {
                    superClass = clazz;
                }
                do {
                    if (!Modifier.isPublic(superClass.getModifiers())) {
                        asmEnable = false;
                        break;
                    }
                    superClass = superClass.getSuperclass();
                    if (superClass == Object.class) {
                        break;
                    }
                } while (superClass != null);
            }
        }
        if (clazz.getTypeParameters().length != 0) {
            asmEnable = false;
        }
        if (asmEnable && this.asmFactory != null && this.asmFactory.classLoader.isExternalClass(clazz)) {
            asmEnable = false;
        }
        if (asmEnable) {
            asmEnable = ASMUtils.checkName(clazz.getSimpleName());
        }
        if (asmEnable) {
            if (clazz.isInterface()) {
                asmEnable = false;
            }
            JavaBeanInfo beanInfo = JavaBeanInfo.build(clazz, type, this.propertyNamingStrategy);
            if (asmEnable && beanInfo.fields.length > 200) {
                asmEnable = false;
            }
            Constructor<?> defaultConstructor = beanInfo.defaultConstructor;
            if (asmEnable && defaultConstructor == null && !clazz.isInterface()) {
                asmEnable = false;
            }
            for (FieldInfo fieldInfo : beanInfo.fields) {
                if (fieldInfo.getOnly) {
                    asmEnable = false;
                    break;
                }
                Class<?> fieldClass = fieldInfo.fieldClass;
                if (!Modifier.isPublic(fieldClass.getModifiers())) {
                    asmEnable = false;
                    break;
                }
                if (fieldClass.isMemberClass() && !Modifier.isStatic(fieldClass.getModifiers())) {
                    asmEnable = false;
                    break;
                }
                if (fieldInfo.getMember() != null && !ASMUtils.checkName(fieldInfo.getMember().getName())) {
                    asmEnable = false;
                    break;
                }
                JSONField annotation = fieldInfo.getAnnotation();
                if ((annotation != null && (!ASMUtils.checkName(annotation.name()) || annotation.format().length() != 0 || annotation.deserializeUsing() != Void.class || annotation.unwrapped())) || (fieldInfo.method != null && fieldInfo.method.getParameterTypes().length > 1)) {
                    asmEnable = false;
                    break;
                }
                if (fieldClass.isEnum()) {
                    ObjectDeserializer fieldDeser = getDeserializer(fieldClass);
                    if (!(fieldDeser instanceof EnumDeserializer)) {
                        asmEnable = false;
                        break;
                    }
                }
            }
        }
        if (asmEnable && clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
            asmEnable = false;
        }
        if (!asmEnable) {
            return new JavaBeanDeserializer(this, clazz, type);
        }
        JavaBeanInfo beanInfo2 = JavaBeanInfo.build(clazz, type, this.propertyNamingStrategy);
        try {
            return this.asmFactory.createJavaBeanDeserializer(this, beanInfo2);
        } catch (JSONException e) {
            return new JavaBeanDeserializer(this, beanInfo2);
        } catch (NoSuchMethodException e2) {
            return new JavaBeanDeserializer(this, clazz, type);
        } catch (Exception e3) {
            throw new JSONException("create asm deserializer error, " + clazz.getName(), e3);
        }
    }

    public FieldDeserializer createFieldDeserializer(ParserConfig mapping, JavaBeanInfo beanInfo, FieldInfo fieldInfo) {
        Class<?> clazz = beanInfo.clazz;
        Class<?> fieldClass = fieldInfo.fieldClass;
        Class<?> deserializeUsing = null;
        JSONField annotation = fieldInfo.getAnnotation();
        if (annotation != null && (deserializeUsing = annotation.deserializeUsing()) == Void.class) {
            deserializeUsing = null;
        }
        return (deserializeUsing == null && (fieldClass == List.class || fieldClass == ArrayList.class)) ? new ArrayListTypeFieldDeserializer(mapping, clazz, fieldInfo) : new DefaultFieldDeserializer(mapping, clazz, fieldInfo);
    }

    public void putDeserializer(Type type, ObjectDeserializer deserializer) {
        this.deserializers.put(type, deserializer);
    }

    public static boolean isPrimitive2(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == Boolean.class || clazz == Character.class || clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class || clazz == BigInteger.class || clazz == BigDecimal.class || clazz == String.class || clazz == java.util.Date.class || clazz == Date.class || clazz == Time.class || clazz == Timestamp.class || clazz.isEnum();
    }

    public static void parserAllFieldToCache(Class<?> clazz, Map<String, Field> fieldCacheMap) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!fieldCacheMap.containsKey(fieldName)) {
                fieldCacheMap.put(fieldName, field);
            }
        }
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            parserAllFieldToCache(clazz.getSuperclass(), fieldCacheMap);
        }
    }

    public static Field getFieldFromCache(String fieldName, Map<String, Field> fieldCacheMap) {
        Field field = fieldCacheMap.get(fieldName);
        if (field == null) {
            field = fieldCacheMap.get("_" + fieldName);
        }
        if (field == null) {
            field = fieldCacheMap.get("m_" + fieldName);
        }
        if (field == null) {
            char c0 = fieldName.charAt(0);
            if (c0 >= 'a' && c0 <= 'z') {
                char[] chars = fieldName.toCharArray();
                chars[0] = (char) (chars[0] - ' ');
                String fieldNameX = new String(chars);
                field = fieldCacheMap.get(fieldNameX);
            }
            if (fieldName.length() > 2) {
                char c1 = fieldName.charAt(1);
                if (fieldName.length() > 2 && c0 >= 'a' && c0 <= 'z' && c1 >= 'A' && c1 <= 'Z') {
                    for (Map.Entry<String, Field> entry : fieldCacheMap.entrySet()) {
                        if (fieldName.equalsIgnoreCase(entry.getKey())) {
                            return entry.getValue();
                        }
                    }
                    return field;
                }
                return field;
            }
            return field;
        }
        return field;
    }

    public ClassLoader getDefaultClassLoader() {
        return this.defaultClassLoader;
    }

    public void addDeny(String name) {
        if (name != null && name.length() != 0) {
            for (String item : this.denyList) {
                if (name.equals(item)) {
                    return;
                }
            }
            String[] denyList = new String[this.denyList.length + 1];
            System.arraycopy(this.denyList, 0, denyList, 0, this.denyList.length);
            denyList[denyList.length - 1] = name;
            this.denyList = denyList;
        }
    }

    public void addAccept(String name) {
        if (name != null && name.length() != 0) {
            for (String item : this.acceptList) {
                if (name.equals(item)) {
                    return;
                }
            }
            String[] acceptList = new String[this.acceptList.length + 1];
            System.arraycopy(this.acceptList, 0, acceptList, 0, this.acceptList.length);
            acceptList[acceptList.length - 1] = name;
            this.acceptList = acceptList;
        }
    }

    public Class<?> checkAutoType(String typeName, Class<?> expectClass) {
        if (typeName == null) {
            return null;
        }
        if (typeName.length() >= this.maxTypeNameLength) {
            throw new JSONException("autoType is not support. " + typeName);
        }
        String className = typeName.replace('$', '.');
        Class<?> clazz = null;
        if (this.autoTypeSupport || expectClass != null) {
            for (int i = 0; i < this.acceptList.length; i++) {
                String accept = this.acceptList[i];
                if (className.startsWith(accept) && (clazz = TypeUtils.loadClass(typeName, this.defaultClassLoader)) != null) {
                    return clazz;
                }
            }
            for (int i2 = 0; i2 < this.denyList.length; i2++) {
                String deny = this.denyList[i2];
                if (className.startsWith(deny) && TypeUtils.getClassFromMapping(typeName) == null) {
                    throw new JSONException("autoType is not support. " + typeName);
                }
            }
        }
        if (clazz == null) {
            clazz = TypeUtils.getClassFromMapping(typeName);
        }
        if (clazz == null) {
            clazz = this.deserializers.findClass(typeName);
        }
        if (clazz != null) {
            if (expectClass != null && clazz != HashMap.class && !expectClass.isAssignableFrom(clazz)) {
                throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
            }
            return clazz;
        }
        if (!this.autoTypeSupport) {
            for (int i3 = 0; i3 < this.denyList.length; i3++) {
                String deny2 = this.denyList[i3];
                if (className.startsWith(deny2)) {
                    throw new JSONException("autoType is not support. " + typeName);
                }
            }
            for (int i4 = 0; i4 < this.acceptList.length; i4++) {
                String accept2 = this.acceptList[i4];
                if (className.startsWith(accept2)) {
                    if (clazz == null) {
                        clazz = TypeUtils.loadClass(typeName, this.defaultClassLoader);
                    }
                    if (expectClass != null && expectClass.isAssignableFrom(clazz)) {
                        throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
                    }
                    return clazz;
                }
            }
        }
        if (clazz == null) {
            clazz = TypeUtils.loadClass(typeName, this.defaultClassLoader);
        }
        if (clazz != null) {
            if (TypeUtils.getAnnotation(clazz, JSONType.class) == null) {
                if (ClassLoader.class.isAssignableFrom(clazz) || DataSource.class.isAssignableFrom(clazz)) {
                    throw new JSONException("autoType is not support. " + typeName);
                }
                if (expectClass != null) {
                    if (!expectClass.isAssignableFrom(clazz)) {
                        throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
                    }
                    return clazz;
                }
                JavaBeanInfo beanInfo = JavaBeanInfo.build(clazz, clazz, this.propertyNamingStrategy);
                if (beanInfo.creatorConstructor != null && this.autoTypeSupport) {
                    throw new JSONException("autoType is not support. " + typeName);
                }
            } else {
                return clazz;
            }
        }
        if (!this.autoTypeSupport) {
            throw new JSONException("autoType is not support. " + typeName);
        }
        return clazz;
    }
}
