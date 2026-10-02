package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tencent.android.tpush.common.Constants;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JavaBeanInfo {
    public final Method buildMethod;
    public final Class<?> builderClass;
    public final Class<?> clazz;
    public final Constructor<?> creatorConstructor;
    public Type[] creatorConstructorParameterTypes;
    public String[] creatorConstructorParameters;
    public final Constructor<?> defaultConstructor;
    public final int defaultConstructorParameterSize;
    public final Method factoryMethod;
    public final FieldInfo[] fields;
    public final JSONType jsonType;
    public String[] orders;
    public final int parserFeatures;
    public final FieldInfo[] sortedFields;
    public final String typeKey;
    public final String typeName;

    public JavaBeanInfo(Class<?> clazz, Class<?> builderClass, Constructor<?> defaultConstructor, Constructor<?> creatorConstructor, Method factoryMethod, Method buildMethod, JSONType jsonType, List<FieldInfo> fieldList) {
        boolean match;
        int i;
        this.clazz = clazz;
        this.builderClass = builderClass;
        this.defaultConstructor = defaultConstructor;
        this.creatorConstructor = creatorConstructor;
        this.factoryMethod = factoryMethod;
        this.parserFeatures = TypeUtils.getParserFeatures(clazz);
        this.buildMethod = buildMethod;
        this.jsonType = jsonType;
        if (jsonType != null) {
            String typeName = jsonType.typeName();
            String typeKey = jsonType.typeKey();
            this.typeKey = typeKey.length() <= 0 ? null : typeKey;
            if (typeName.length() != 0) {
                this.typeName = typeName;
            } else {
                this.typeName = clazz.getName();
            }
            String[] orders = jsonType.orders();
            this.orders = orders.length == 0 ? null : orders;
        } else {
            this.typeName = clazz.getName();
            this.typeKey = null;
            this.orders = null;
        }
        this.fields = new FieldInfo[fieldList.size()];
        fieldList.toArray(this.fields);
        FieldInfo[] sortedFields = new FieldInfo[this.fields.length];
        if (this.orders != null) {
            LinkedHashMap<String, FieldInfo> map = new LinkedHashMap<>(fieldList.size());
            for (FieldInfo field : this.fields) {
                map.put(field.name, field);
            }
            int i2 = 0;
            String[] strArr = this.orders;
            int length = strArr.length;
            int i3 = 0;
            while (true) {
                i = i2;
                if (i3 >= length) {
                    break;
                }
                String item = strArr[i3];
                FieldInfo field2 = map.get(item);
                if (field2 != null) {
                    i2 = i + 1;
                    sortedFields[i] = field2;
                    map.remove(item);
                } else {
                    i2 = i;
                }
                i3++;
            }
            Iterator<FieldInfo> it = map.values().iterator();
            while (true) {
                int i4 = i;
                if (!it.hasNext()) {
                    break;
                }
                i = i4 + 1;
                sortedFields[i4] = it.next();
            }
        } else {
            System.arraycopy(this.fields, 0, sortedFields, 0, this.fields.length);
            Arrays.sort(sortedFields);
        }
        this.sortedFields = Arrays.equals(this.fields, sortedFields) ? this.fields : sortedFields;
        if (defaultConstructor != null) {
            this.defaultConstructorParameterSize = defaultConstructor.getParameterTypes().length;
        } else if (factoryMethod != null) {
            this.defaultConstructorParameterSize = factoryMethod.getParameterTypes().length;
        } else {
            this.defaultConstructorParameterSize = 0;
        }
        if (creatorConstructor != null) {
            this.creatorConstructorParameterTypes = creatorConstructor.getParameterTypes();
            if (this.creatorConstructorParameterTypes.length != this.fields.length) {
                match = false;
            } else {
                match = true;
                for (int i5 = 0; i5 < this.creatorConstructorParameterTypes.length; i5++) {
                    if (this.creatorConstructorParameterTypes[i5] != this.fields[i5].fieldClass) {
                        match = false;
                        break;
                    }
                }
            }
            if (!match) {
                boolean kotlin2 = TypeUtils.isKotlin(clazz);
                if (kotlin2) {
                    this.creatorConstructorParameters = TypeUtils.getKoltinConstructorParameters(clazz);
                    Annotation[][] paramAnnotationArrays = creatorConstructor.getParameterAnnotations();
                    for (int i6 = 0; i6 < this.creatorConstructorParameters.length && i6 < paramAnnotationArrays.length; i6++) {
                        Annotation[] paramAnnotations = paramAnnotationArrays[i6];
                        JSONField fieldAnnotation = null;
                        for (Annotation paramAnnotation : paramAnnotations) {
                            if (paramAnnotation instanceof JSONField) {
                                fieldAnnotation = (JSONField) paramAnnotation;
                                break;
                            }
                        }
                        if (fieldAnnotation != null) {
                            String fieldAnnotationName = fieldAnnotation.name();
                            if (fieldAnnotationName.length() > 0) {
                                this.creatorConstructorParameters[i6] = fieldAnnotationName;
                            }
                        }
                    }
                    return;
                }
                this.creatorConstructorParameters = ASMUtils.lookupParameterNames(creatorConstructor);
            }
        }
    }

    private static FieldInfo getField(List<FieldInfo> fieldList, String propertyName) {
        for (FieldInfo item : fieldList) {
            if (!item.name.equals(propertyName)) {
                Field field = item.field;
                if (field != null && item.getAnnotation() != null && field.getName().equals(propertyName)) {
                    return item;
                }
            } else {
                return item;
            }
        }
        return null;
    }

    static boolean add(List<FieldInfo> fieldList, FieldInfo field) {
        for (int i = fieldList.size() - 1; i >= 0; i--) {
            FieldInfo item = fieldList.get(i);
            if (item.name.equals(field.name) && (!item.getOnly || field.getOnly)) {
                if (item.fieldClass.isAssignableFrom(field.fieldClass)) {
                    fieldList.remove(i);
                    break;
                }
                int result = item.compareTo(field);
                if (result < 0) {
                    fieldList.remove(i);
                    break;
                }
                return false;
            }
        }
        fieldList.add(field);
        return true;
    }

    public static JavaBeanInfo build(Class<?> clazz, Type type, PropertyNamingStrategy propertyNamingStrategy) {
        return build(clazz, type, propertyNamingStrategy, false, TypeUtils.compatibleWithJavaBean);
    }

    /* JADX WARN: Code duplicated, block: B:183:0x0428 A[PHI: r19 r20 r21
  0x0428: PHI (r19v6 'ordinal' int) = (r19v5 'ordinal' int), (r19v7 'ordinal' int) binds: [B:177:0x03e2, B:181:0x0406] A[DONT_GENERATE, DONT_INLINE]
  0x0428: PHI (r20v6 'serialzeFeatures' int) = (r20v5 'serialzeFeatures' int), (r20v7 'serialzeFeatures' int) binds: [B:177:0x03e2, B:181:0x0406] A[DONT_GENERATE, DONT_INLINE]
  0x0428: PHI (r21v6 'parserFeatures' int) = (r21v5 'parserFeatures' int), (r21v7 'parserFeatures' int) binds: [B:177:0x03e2, B:181:0x0406] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:191:0x0487  */
    /* JADX WARN: Code duplicated, block: B:193:0x0491  */
    /* JADX WARN: Code duplicated, block: B:249:0x05dc A[PHI: r19 r20 r21
  0x05dc: PHI (r19v1 'ordinal' int) = (r19v0 'ordinal' int), (r19v4 'ordinal' int) binds: [B:243:0x0596, B:247:0x05ba] A[DONT_GENERATE, DONT_INLINE]
  0x05dc: PHI (r20v1 'serialzeFeatures' int) = (r20v0 'serialzeFeatures' int), (r20v4 'serialzeFeatures' int) binds: [B:243:0x0596, B:247:0x05ba] A[DONT_GENERATE, DONT_INLINE]
  0x05dc: PHI (r21v1 'parserFeatures' int) = (r21v0 'parserFeatures' int), (r21v4 'parserFeatures' int) binds: [B:243:0x0596, B:247:0x05ba] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:252:0x05e8  */
    /* JADX WARN: Code duplicated, block: B:254:0x05f5  */
    /* JADX WARN: Code duplicated, block: B:256:0x05fb  */
    /* JADX WARN: Code duplicated, block: B:258:0x05ff  */
    /* JADX WARN: Code duplicated, block: B:261:0x0616  */
    /* JADX WARN: Code duplicated, block: B:266:0x0654  */
    /* JADX WARN: Code duplicated, block: B:273:0x06a2  */
    /* JADX WARN: Code duplicated, block: B:285:0x0706 A[DONT_INVERT, PHI: r19 r20 r21 r37
  0x0706: PHI (r19v2 'ordinal' int) = (r19v1 'ordinal' int), (r19v1 'ordinal' int), (r19v3 'ordinal' int) binds: [B:265:0x0652, B:267:0x065e, B:271:0x0682] A[DONT_GENERATE, DONT_INLINE]
  0x0706: PHI (r20v2 'serialzeFeatures' int) = (r20v1 'serialzeFeatures' int), (r20v1 'serialzeFeatures' int), (r20v3 'serialzeFeatures' int) binds: [B:265:0x0652, B:267:0x065e, B:271:0x0682] A[DONT_GENERATE, DONT_INLINE]
  0x0706: PHI (r21v2 'parserFeatures' int) = (r21v1 'parserFeatures' int), (r21v1 'parserFeatures' int), (r21v3 'parserFeatures' int) binds: [B:265:0x0652, B:267:0x065e, B:271:0x0682] A[DONT_GENERATE, DONT_INLINE]
  0x0706: PHI (r37v3 'fieldAnnotation' com.alibaba.fastjson.annotation.JSONField) = 
  (r37v2 'fieldAnnotation' com.alibaba.fastjson.annotation.JSONField)
  (r37v5 'fieldAnnotation' com.alibaba.fastjson.annotation.JSONField)
  (r37v5 'fieldAnnotation' com.alibaba.fastjson.annotation.JSONField)
 binds: [B:265:0x0652, B:267:0x065e, B:271:0x0682] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:286:0x0708  */
    /* JADX WARN: Code duplicated, block: B:358:0x0148 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    public static JavaBeanInfo build(Class<?> clazz, Type type, PropertyNamingStrategy propertyNamingStrategy, boolean fieldBased, boolean compatibleWithJavaBean) {
        JSONField annotation;
        String propertyName;
        JSONField fieldAnnotation;
        char c3;
        String propertyName2;
        Field field;
        JSONField fieldAnnotation2;
        String methodName;
        StringBuilder properNameBuilder;
        String[] lookupParameterNames;
        int ordinal;
        int serialzeFeatures;
        int parserFeatures;
        PropertyNamingStrategy jsonTypeNaming;
        JSONType jsonType = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
        if (jsonType != null && (jsonTypeNaming = jsonType.naming()) != null && jsonTypeNaming != PropertyNamingStrategy.CamelCase) {
            propertyNamingStrategy = jsonTypeNaming;
        }
        Class<?> builderClass = getBuilderClass(clazz, jsonType);
        Field[] declaredFields = clazz.getDeclaredFields();
        Method[] methods = clazz.getMethods();
        boolean kotlin2 = TypeUtils.isKotlin(clazz);
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Constructor<?> defaultConstructor = null;
        if (!kotlin2 || declaredConstructors.length == 1) {
            if (builderClass == null) {
                defaultConstructor = getDefaultConstructor(clazz, declaredConstructors);
            } else {
                defaultConstructor = getDefaultConstructor(builderClass, builderClass.getDeclaredConstructors());
            }
        }
        Constructor<?> creatorConstructor = null;
        Method buildMethod = null;
        Method factoryMethod = null;
        List<FieldInfo> fieldList = new ArrayList<>();
        if (fieldBased) {
            for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
                Field[] fields = currentClass.getDeclaredFields();
                computeFields(clazz, type, propertyNamingStrategy, fieldList, fields);
            }
            return new JavaBeanInfo(clazz, builderClass, defaultConstructor, null, null, null, jsonType, fieldList);
        }
        boolean isInterfaceOrAbstract = clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers());
        if ((defaultConstructor == null && builderClass == null) || isInterfaceOrAbstract) {
            Constructor<?> creatorConstructor2 = getCreatorConstructor(declaredConstructors);
            if (creatorConstructor2 != null && !isInterfaceOrAbstract) {
                TypeUtils.setAccessible(creatorConstructor2);
                Class<?>[] types = creatorConstructor2.getParameterTypes();
                if (types.length > 0) {
                    Annotation[][] paramAnnotationArrays = creatorConstructor2.getParameterAnnotations();
                    for (int i = 0; i < types.length; i++) {
                        Annotation[] paramAnnotations = paramAnnotationArrays[i];
                        JSONField fieldAnnotation3 = null;
                        for (Annotation paramAnnotation : paramAnnotations) {
                            if (paramAnnotation instanceof JSONField) {
                                fieldAnnotation3 = (JSONField) paramAnnotation;
                                break;
                            }
                        }
                        if (fieldAnnotation3 == null) {
                            throw new JSONException("illegal json creator");
                        }
                        Class<?> fieldClass = types[i];
                        Type fieldType = creatorConstructor2.getGenericParameterTypes()[i];
                        Field field2 = TypeUtils.getField(clazz, fieldAnnotation3.name(), declaredFields);
                        int ordinal2 = fieldAnnotation3.ordinal();
                        int serialzeFeatures2 = SerializerFeature.of(fieldAnnotation3.serialzeFeatures());
                        int parserFeatures2 = Feature.of(fieldAnnotation3.parseFeatures());
                        FieldInfo fieldInfo = new FieldInfo(fieldAnnotation3.name(), clazz, fieldClass, fieldType, field2, ordinal2, serialzeFeatures2, parserFeatures2);
                        add(fieldList, fieldInfo);
                    }
                }
                creatorConstructor = creatorConstructor2;
            } else {
                factoryMethod = getFactoryMethod(clazz, methods);
                if (factoryMethod != null) {
                    TypeUtils.setAccessible(factoryMethod);
                    Class<?>[] types2 = factoryMethod.getParameterTypes();
                    if (types2.length > 0) {
                        Annotation[][] paramAnnotationArrays2 = factoryMethod.getParameterAnnotations();
                        for (int i2 = 0; i2 < types2.length; i2++) {
                            Annotation[] paramAnnotations2 = paramAnnotationArrays2[i2];
                            JSONField fieldAnnotation4 = null;
                            for (Annotation paramAnnotation2 : paramAnnotations2) {
                                if (paramAnnotation2 instanceof JSONField) {
                                    fieldAnnotation4 = (JSONField) paramAnnotation2;
                                    break;
                                }
                            }
                            if (fieldAnnotation4 == null) {
                                throw new JSONException("illegal json creator");
                            }
                            Class<?> fieldClass2 = types2[i2];
                            Type fieldType2 = factoryMethod.getGenericParameterTypes()[i2];
                            Field field3 = TypeUtils.getField(clazz, fieldAnnotation4.name(), declaredFields);
                            int ordinal3 = fieldAnnotation4.ordinal();
                            int serialzeFeatures3 = SerializerFeature.of(fieldAnnotation4.serialzeFeatures());
                            int parserFeatures3 = Feature.of(fieldAnnotation4.parseFeatures());
                            FieldInfo fieldInfo2 = new FieldInfo(fieldAnnotation4.name(), clazz, fieldClass2, fieldType2, field3, ordinal3, serialzeFeatures3, parserFeatures3);
                            add(fieldList, fieldInfo2);
                        }
                        return new JavaBeanInfo(clazz, builderClass, null, null, factoryMethod, null, jsonType, fieldList);
                    }
                    creatorConstructor = creatorConstructor2;
                } else if (isInterfaceOrAbstract) {
                    creatorConstructor = creatorConstructor2;
                } else {
                    String className = clazz.getName();
                    String[] paramNames = null;
                    if (kotlin2 && declaredConstructors.length > 0) {
                        paramNames = TypeUtils.getKoltinConstructorParameters(clazz);
                        creatorConstructor = TypeUtils.getKoltinConstructor(declaredConstructors);
                        TypeUtils.setAccessible(creatorConstructor);
                    } else {
                        creatorConstructor = creatorConstructor2;
                        for (Constructor<?> constructor : declaredConstructors) {
                            Class<?>[] parameterTypes = constructor.getParameterTypes();
                            if (className.equals("org.springframework.security.web.authentication.WebAuthenticationDetails") && parameterTypes.length == 2 && parameterTypes[0] == String.class && parameterTypes[1] == String.class) {
                                creatorConstructor = constructor;
                                creatorConstructor.setAccessible(true);
                                paramNames = ASMUtils.lookupParameterNames(constructor);
                                break;
                            }
                            if (className.equals("org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken") && parameterTypes.length == 3 && parameterTypes[0] == Object.class && parameterTypes[1] == Object.class && parameterTypes[2] == Collection.class) {
                                creatorConstructor = constructor;
                                creatorConstructor.setAccessible(true);
                                paramNames = new String[]{"principal", "credentials", "authorities"};
                                break;
                            }
                            if (className.equals("org.springframework.security.core.authority.SimpleGrantedAuthority") && parameterTypes.length == 1 && parameterTypes[0] == String.class) {
                                creatorConstructor = constructor;
                                paramNames = new String[]{"authority"};
                                break;
                            }
                            boolean is_public = (constructor.getModifiers() & 1) != 0;
                            if (is_public && (lookupParameterNames = ASMUtils.lookupParameterNames(constructor)) != null && lookupParameterNames.length != 0 && (creatorConstructor == null || paramNames == null || lookupParameterNames.length > paramNames.length)) {
                                paramNames = lookupParameterNames;
                                creatorConstructor = constructor;
                            }
                        }
                    }
                    Class<?>[] types3 = null;
                    if (paramNames != null) {
                        types3 = creatorConstructor.getParameterTypes();
                    }
                    if (paramNames != null && types3.length == paramNames.length) {
                        Annotation[][] paramAnnotationArrays3 = creatorConstructor.getParameterAnnotations();
                        for (int i3 = 0; i3 < types3.length; i3++) {
                            Annotation[] paramAnnotations3 = paramAnnotationArrays3[i3];
                            String paramName = paramNames[i3];
                            JSONField fieldAnnotation5 = null;
                            for (Annotation paramAnnotation3 : paramAnnotations3) {
                                if (paramAnnotation3 instanceof JSONField) {
                                    fieldAnnotation5 = (JSONField) paramAnnotation3;
                                    break;
                                }
                            }
                            Class<?> fieldClass3 = types3[i3];
                            Type fieldType3 = creatorConstructor.getGenericParameterTypes()[i3];
                            Field field4 = TypeUtils.getField(clazz, paramName, declaredFields);
                            if (field4 != null && fieldAnnotation5 == null) {
                                fieldAnnotation5 = (JSONField) field4.getAnnotation(JSONField.class);
                            }
                            if (fieldAnnotation5 == null) {
                                ordinal = 0;
                                serialzeFeatures = 0;
                                if ("org.springframework.security.core.userdetails.User".equals(className) && "password".equals(paramName)) {
                                    parserFeatures = Feature.InitStringFieldAsEmpty.mask;
                                } else {
                                    parserFeatures = 0;
                                }
                            } else {
                                String nameAnnotated = fieldAnnotation5.name();
                                if (nameAnnotated.length() != 0) {
                                    paramName = nameAnnotated;
                                }
                                ordinal = fieldAnnotation5.ordinal();
                                serialzeFeatures = SerializerFeature.of(fieldAnnotation5.serialzeFeatures());
                                parserFeatures = Feature.of(fieldAnnotation5.parseFeatures());
                            }
                            FieldInfo fieldInfo3 = new FieldInfo(paramName, clazz, fieldClass3, fieldType3, field4, ordinal, serialzeFeatures, parserFeatures);
                            add(fieldList, fieldInfo3);
                        }
                        if (!kotlin2 && !clazz.getName().equals("javax.servlet.http.Cookie")) {
                            return new JavaBeanInfo(clazz, builderClass, null, creatorConstructor, null, null, jsonType, fieldList);
                        }
                    } else {
                        throw new JSONException("default constructor not found. " + clazz);
                    }
                }
            }
        }
        if (defaultConstructor != null) {
            TypeUtils.setAccessible(defaultConstructor);
        }
        if (builderClass != null) {
            String withPrefix = null;
            JSONPOJOBuilder builderAnno = (JSONPOJOBuilder) builderClass.getAnnotation(JSONPOJOBuilder.class);
            if (builderAnno != null) {
                withPrefix = builderAnno.withPrefix();
            }
            if (withPrefix == null || withPrefix.length() == 0) {
                withPrefix = "with";
            }
            for (Method method : builderClass.getMethods()) {
                if (!Modifier.isStatic(method.getModifiers()) && method.getReturnType().equals(builderClass)) {
                    int ordinal4 = 0;
                    int serialzeFeatures4 = 0;
                    int parserFeatures4 = 0;
                    JSONField annotation2 = (JSONField) method.getAnnotation(JSONField.class);
                    if (annotation2 == null) {
                        annotation2 = TypeUtils.getSuperMethodAnnotation(clazz, method);
                    }
                    if (annotation2 != null) {
                        if (annotation2.deserialize()) {
                            ordinal4 = annotation2.ordinal();
                            serialzeFeatures4 = SerializerFeature.of(annotation2.serialzeFeatures());
                            parserFeatures4 = Feature.of(annotation2.parseFeatures());
                            if (annotation2.name().length() != 0) {
                                add(fieldList, new FieldInfo(annotation2.name(), method, null, clazz, type, ordinal4, serialzeFeatures4, parserFeatures4, annotation2, null, null));
                            } else {
                                methodName = method.getName();
                                if (!methodName.startsWith("set")) {
                                    if (!methodName.startsWith(withPrefix)) {
                                    }
                                } else if (!methodName.startsWith(withPrefix)) {
                                }
                            }
                        }
                    } else {
                        methodName = method.getName();
                        if (!methodName.startsWith("set") && methodName.length() > 3) {
                            properNameBuilder = new StringBuilder(methodName.substring(3));
                        } else if (!methodName.startsWith(withPrefix) && methodName.length() > withPrefix.length()) {
                            properNameBuilder = new StringBuilder(methodName.substring(withPrefix.length()));
                        }
                        char c0 = properNameBuilder.charAt(0);
                        if (Character.isUpperCase(c0)) {
                            properNameBuilder.setCharAt(0, Character.toLowerCase(c0));
                            add(fieldList, new FieldInfo(properNameBuilder.toString(), method, null, clazz, type, ordinal4, serialzeFeatures4, parserFeatures4, annotation2, null, null));
                        }
                    }
                }
            }
            if (builderClass != null) {
                JSONPOJOBuilder builderAnnotation = (JSONPOJOBuilder) builderClass.getAnnotation(JSONPOJOBuilder.class);
                String buildMethodName = null;
                if (builderAnnotation != null) {
                    buildMethodName = builderAnnotation.buildMethod();
                }
                if (buildMethodName == null || buildMethodName.length() == 0) {
                    buildMethodName = "build";
                }
                try {
                    buildMethod = builderClass.getMethod(buildMethodName, new Class[0]);
                } catch (NoSuchMethodException e) {
                } catch (SecurityException e2) {
                }
                if (buildMethod == null) {
                    try {
                        buildMethod = builderClass.getMethod("create", new Class[0]);
                    } catch (NoSuchMethodException e3) {
                    } catch (SecurityException e4) {
                    }
                }
                if (buildMethod == null) {
                    throw new JSONException("buildMethod not found.");
                }
                TypeUtils.setAccessible(buildMethod);
            }
        }
        for (Method method2 : methods) {
            int ordinal5 = 0;
            int serialzeFeatures5 = 0;
            int parserFeatures5 = 0;
            String methodName2 = method2.getName();
            if (!Modifier.isStatic(method2.getModifiers())) {
                Class<?> returnType = method2.getReturnType();
                if ((returnType.equals(Void.TYPE) || returnType.equals(method2.getDeclaringClass())) && method2.getDeclaringClass() != Object.class) {
                    Class<?>[] types4 = method2.getParameterTypes();
                    if (types4.length != 0 && types4.length <= 2) {
                        JSONField annotation3 = (JSONField) method2.getAnnotation(JSONField.class);
                        if (annotation3 != null && types4.length == 2 && types4[0] == String.class && types4[1] == Object.class) {
                            add(fieldList, new FieldInfo(Constants.MAIN_VERSION_TAG, method2, null, clazz, type, 0, 0, 0, annotation3, null, null));
                        } else if (types4.length == 1) {
                            if (annotation3 == null) {
                                annotation3 = TypeUtils.getSuperMethodAnnotation(clazz, method2);
                            }
                            if (annotation3 != null || methodName2.length() >= 4) {
                                if (annotation3 != null) {
                                    if (annotation3.deserialize()) {
                                        ordinal5 = annotation3.ordinal();
                                        serialzeFeatures5 = SerializerFeature.of(annotation3.serialzeFeatures());
                                        parserFeatures5 = Feature.of(annotation3.parseFeatures());
                                        if (annotation3.name().length() != 0) {
                                            add(fieldList, new FieldInfo(annotation3.name(), method2, null, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, null, null));
                                        } else if (annotation3 == null) {
                                            c3 = methodName2.charAt(3);
                                            if (!Character.isUpperCase(c3)) {
                                                if (TypeUtils.compatibleWithJavaBean) {
                                                    propertyName2 = TypeUtils.decapitalize(methodName2.substring(3));
                                                } else {
                                                    propertyName2 = Character.toLowerCase(methodName2.charAt(3)) + methodName2.substring(4);
                                                }
                                                field = TypeUtils.getField(clazz, propertyName2, declaredFields);
                                                if (field == null) {
                                                    String isFieldName = "is" + Character.toUpperCase(propertyName2.charAt(0)) + propertyName2.substring(1);
                                                    field = TypeUtils.getField(clazz, isFieldName, declaredFields);
                                                }
                                                fieldAnnotation2 = null;
                                                if (field == null) {
                                                    if (propertyNamingStrategy != null) {
                                                        propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                                    }
                                                    add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                                } else {
                                                    if (propertyNamingStrategy != null) {
                                                        propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                                    }
                                                    add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                                }
                                            } else {
                                                if (TypeUtils.compatibleWithJavaBean) {
                                                    propertyName2 = TypeUtils.decapitalize(methodName2.substring(3));
                                                } else {
                                                    propertyName2 = Character.toLowerCase(methodName2.charAt(3)) + methodName2.substring(4);
                                                }
                                                field = TypeUtils.getField(clazz, propertyName2, declaredFields);
                                                if (field == null) {
                                                    String isFieldName2 = "is" + Character.toUpperCase(propertyName2.charAt(0)) + propertyName2.substring(1);
                                                    field = TypeUtils.getField(clazz, isFieldName2, declaredFields);
                                                }
                                                fieldAnnotation2 = null;
                                                if (field == null) {
                                                    if (propertyNamingStrategy != null) {
                                                        propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                                    }
                                                    add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                                } else {
                                                    if (propertyNamingStrategy != null) {
                                                        propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                                    }
                                                    add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                                }
                                            }
                                        } else {
                                            c3 = methodName2.charAt(3);
                                            if (!Character.isUpperCase(c3)) {
                                                if (TypeUtils.compatibleWithJavaBean) {
                                                    propertyName2 = TypeUtils.decapitalize(methodName2.substring(3));
                                                } else {
                                                    propertyName2 = Character.toLowerCase(methodName2.charAt(3)) + methodName2.substring(4);
                                                }
                                                field = TypeUtils.getField(clazz, propertyName2, declaredFields);
                                                if (field == null) {
                                                    String isFieldName3 = "is" + Character.toUpperCase(propertyName2.charAt(0)) + propertyName2.substring(1);
                                                    field = TypeUtils.getField(clazz, isFieldName3, declaredFields);
                                                }
                                                fieldAnnotation2 = null;
                                                if (field == null) {
                                                    if (propertyNamingStrategy != null) {
                                                        propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                                    }
                                                    add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                                } else {
                                                    if (propertyNamingStrategy != null) {
                                                        propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                                    }
                                                    add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                                }
                                            } else {
                                                if (TypeUtils.compatibleWithJavaBean) {
                                                    propertyName2 = TypeUtils.decapitalize(methodName2.substring(3));
                                                } else {
                                                    propertyName2 = Character.toLowerCase(methodName2.charAt(3)) + methodName2.substring(4);
                                                }
                                                field = TypeUtils.getField(clazz, propertyName2, declaredFields);
                                                if (field == null) {
                                                    String isFieldName4 = "is" + Character.toUpperCase(propertyName2.charAt(0)) + propertyName2.substring(1);
                                                    field = TypeUtils.getField(clazz, isFieldName4, declaredFields);
                                                }
                                                fieldAnnotation2 = null;
                                                if (field == null) {
                                                    if (propertyNamingStrategy != null) {
                                                        propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                                    }
                                                    add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                                } else {
                                                    if (propertyNamingStrategy != null) {
                                                        propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                                    }
                                                    add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                                }
                                            }
                                        }
                                    }
                                } else if (annotation3 == null || methodName2.startsWith("set")) {
                                    c3 = methodName2.charAt(3);
                                    if (!Character.isUpperCase(c3) || c3 > 512) {
                                        if (TypeUtils.compatibleWithJavaBean) {
                                            propertyName2 = TypeUtils.decapitalize(methodName2.substring(3));
                                        } else {
                                            propertyName2 = Character.toLowerCase(methodName2.charAt(3)) + methodName2.substring(4);
                                        }
                                    } else if (c3 == '_') {
                                        propertyName2 = methodName2.substring(4);
                                    } else if (c3 == 'f') {
                                        propertyName2 = methodName2.substring(3);
                                    } else if (methodName2.length() >= 5 && Character.isUpperCase(methodName2.charAt(4))) {
                                        propertyName2 = TypeUtils.decapitalize(methodName2.substring(3));
                                    }
                                    field = TypeUtils.getField(clazz, propertyName2, declaredFields);
                                    if (field == null && types4[0] == Boolean.TYPE) {
                                        String isFieldName5 = "is" + Character.toUpperCase(propertyName2.charAt(0)) + propertyName2.substring(1);
                                        field = TypeUtils.getField(clazz, isFieldName5, declaredFields);
                                    }
                                    fieldAnnotation2 = null;
                                    if (field == null && (fieldAnnotation2 = (JSONField) field.getAnnotation(JSONField.class)) != null) {
                                        if (fieldAnnotation2.deserialize()) {
                                            ordinal5 = fieldAnnotation2.ordinal();
                                            serialzeFeatures5 = SerializerFeature.of(fieldAnnotation2.serialzeFeatures());
                                            parserFeatures5 = Feature.of(fieldAnnotation2.parseFeatures());
                                            if (fieldAnnotation2.name().length() != 0) {
                                                add(fieldList, new FieldInfo(fieldAnnotation2.name(), method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                            } else {
                                                if (propertyNamingStrategy != null) {
                                                    propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                                }
                                                add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                            }
                                        }
                                    } else {
                                        if (propertyNamingStrategy != null) {
                                            propertyName2 = propertyNamingStrategy.translate(propertyName2);
                                        }
                                        add(fieldList, new FieldInfo(propertyName2, method2, field, clazz, type, ordinal5, serialzeFeatures5, parserFeatures5, annotation3, fieldAnnotation2, null));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Field[] fields2 = clazz.getFields();
        computeFields(clazz, type, propertyNamingStrategy, fieldList, fields2);
        for (Method method3 : clazz.getMethods()) {
            String methodName3 = method3.getName();
            if (methodName3.length() >= 4 && !Modifier.isStatic(method3.getModifiers()) && builderClass == null && methodName3.startsWith("get") && Character.isUpperCase(methodName3.charAt(3)) && method3.getParameterTypes().length == 0 && ((Collection.class.isAssignableFrom(method3.getReturnType()) || Map.class.isAssignableFrom(method3.getReturnType()) || AtomicBoolean.class == method3.getReturnType() || AtomicInteger.class == method3.getReturnType() || AtomicLong.class == method3.getReturnType()) && ((annotation = (JSONField) method3.getAnnotation(JSONField.class)) == null || !annotation.deserialize()))) {
                if (annotation != null && annotation.name().length() > 0) {
                    propertyName = annotation.name();
                } else {
                    propertyName = Character.toLowerCase(methodName3.charAt(3)) + methodName3.substring(4);
                    Field field5 = TypeUtils.getField(clazz, propertyName, declaredFields);
                    if (field5 == null || (fieldAnnotation = (JSONField) field5.getAnnotation(JSONField.class)) == null || fieldAnnotation.deserialize()) {
                    }
                }
                FieldInfo fieldInfo4 = getField(fieldList, propertyName);
                if (fieldInfo4 == null) {
                    if (propertyNamingStrategy != null) {
                        propertyName = propertyNamingStrategy.translate(propertyName);
                    }
                    add(fieldList, new FieldInfo(propertyName, method3, null, clazz, type, 0, 0, 0, annotation, null, null));
                }
            }
        }
        return new JavaBeanInfo(clazz, builderClass, defaultConstructor, creatorConstructor, factoryMethod, buildMethod, jsonType, fieldList);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0051  */
    /* JADX WARN: Code duplicated, block: B:25:0x005c  */
    /* JADX WARN: Code duplicated, block: B:29:0x0073  */
    /* JADX WARN: Code duplicated, block: B:31:0x0084  */
    /* JADX WARN: Code duplicated, block: B:33:0x008a  */
    /* JADX WARN: Code duplicated, block: B:35:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:36:0x00ac A[DONT_INVERT, PHI: r3 r8 r9 r10
  0x00ac: PHI (r3v1 'propertyName' java.lang.String) = (r3v0 'propertyName' java.lang.String), (r3v0 'propertyName' java.lang.String), (r3v4 'propertyName' java.lang.String) binds: [B:30:0x0082, B:34:0x00a6, B:35:0x00a8] A[DONT_GENERATE, DONT_INLINE]
  0x00ac: PHI (r8v1 'ordinal' int) = (r8v0 'ordinal' int), (r8v2 'ordinal' int), (r8v2 'ordinal' int) binds: [B:30:0x0082, B:34:0x00a6, B:35:0x00a8] A[DONT_GENERATE, DONT_INLINE]
  0x00ac: PHI (r9v1 'serialzeFeatures' int) = (r9v0 'serialzeFeatures' int), (r9v2 'serialzeFeatures' int), (r9v2 'serialzeFeatures' int) binds: [B:30:0x0082, B:34:0x00a6, B:35:0x00a8] A[DONT_GENERATE, DONT_INLINE]
  0x00ac: PHI (r10v1 'parserFeatures' int) = (r10v0 'parserFeatures' int), (r10v2 'parserFeatures' int), (r10v2 'parserFeatures' int) binds: [B:30:0x0082, B:34:0x00a6, B:35:0x00a8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:37:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:44:0x0018 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x0018 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x0070 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:49:? A[LOOP:1: B:23:0x0056->B:49:?, LOOP_END, SYNTHETIC] */
    private static void computeFields(Class<?> clazz, Type type, PropertyNamingStrategy propertyNamingStrategy, List<FieldInfo> fieldList, Field[] fields) {
        boolean contains;
        int ordinal;
        int serialzeFeatures;
        int parserFeatures;
        String propertyName;
        JSONField fieldAnnotation;
        int length = fields.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < length) {
                Field field = fields[i2];
                int modifiers = field.getModifiers();
                if ((modifiers & 8) == 0) {
                    if ((modifiers & 16) != 0) {
                        Class<?> fieldType = field.getType();
                        boolean supportReadOnly = Map.class.isAssignableFrom(fieldType) || Collection.class.isAssignableFrom(fieldType) || AtomicLong.class.equals(fieldType) || AtomicInteger.class.equals(fieldType) || AtomicBoolean.class.equals(fieldType);
                        if (supportReadOnly) {
                            contains = false;
                            for (FieldInfo item : fieldList) {
                                if (item.name.equals(field.getName())) {
                                    contains = true;
                                    break;
                                }
                            }
                            if (!contains) {
                                ordinal = 0;
                                serialzeFeatures = 0;
                                parserFeatures = 0;
                                propertyName = field.getName();
                                fieldAnnotation = (JSONField) field.getAnnotation(JSONField.class);
                                if (fieldAnnotation == null) {
                                    if (fieldAnnotation.deserialize()) {
                                        ordinal = fieldAnnotation.ordinal();
                                        serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                                        parserFeatures = Feature.of(fieldAnnotation.parseFeatures());
                                        if (fieldAnnotation.name().length() != 0) {
                                            propertyName = fieldAnnotation.name();
                                        }
                                        if (propertyNamingStrategy != null) {
                                            propertyName = propertyNamingStrategy.translate(propertyName);
                                        }
                                        add(fieldList, new FieldInfo(propertyName, null, field, clazz, type, ordinal, serialzeFeatures, parserFeatures, null, fieldAnnotation, null));
                                    }
                                } else {
                                    if (propertyNamingStrategy != null) {
                                        propertyName = propertyNamingStrategy.translate(propertyName);
                                    }
                                    add(fieldList, new FieldInfo(propertyName, null, field, clazz, type, ordinal, serialzeFeatures, parserFeatures, null, fieldAnnotation, null));
                                }
                            }
                        }
                    } else {
                        contains = false;
                        while (r2.hasNext()) {
                            if (item.name.equals(field.getName())) {
                                contains = true;
                                break;
                            }
                        }
                        if (!contains) {
                            ordinal = 0;
                            serialzeFeatures = 0;
                            parserFeatures = 0;
                            propertyName = field.getName();
                            fieldAnnotation = (JSONField) field.getAnnotation(JSONField.class);
                            if (fieldAnnotation == null) {
                                if (fieldAnnotation.deserialize()) {
                                    ordinal = fieldAnnotation.ordinal();
                                    serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                                    parserFeatures = Feature.of(fieldAnnotation.parseFeatures());
                                    if (fieldAnnotation.name().length() != 0) {
                                        propertyName = fieldAnnotation.name();
                                    }
                                    if (propertyNamingStrategy != null) {
                                        propertyName = propertyNamingStrategy.translate(propertyName);
                                    }
                                    add(fieldList, new FieldInfo(propertyName, null, field, clazz, type, ordinal, serialzeFeatures, parserFeatures, null, fieldAnnotation, null));
                                }
                            } else {
                                if (propertyNamingStrategy != null) {
                                    propertyName = propertyNamingStrategy.translate(propertyName);
                                }
                                add(fieldList, new FieldInfo(propertyName, null, field, clazz, type, ordinal, serialzeFeatures, parserFeatures, null, fieldAnnotation, null));
                            }
                        }
                    }
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    static Constructor<?> getDefaultConstructor(Class<?> clazz, Constructor<?>[] constructors) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }
        Constructor<?> defaultConstructor = null;
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 0) {
                defaultConstructor = constructor;
                break;
            }
        }
        if (defaultConstructor == null && clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
            for (Constructor<?> constructor2 : constructors) {
                Class<?>[] types = constructor2.getParameterTypes();
                if (types.length == 1 && types[0].equals(clazz.getDeclaringClass())) {
                    return constructor2;
                }
            }
            return defaultConstructor;
        }
        return defaultConstructor;
    }

    public static Constructor<?> getCreatorConstructor(Constructor[] constructors) {
        Constructor constructor = null;
        for (Constructor constructor2 : constructors) {
            JSONCreator annotation = (JSONCreator) constructor2.getAnnotation(JSONCreator.class);
            if (annotation != null) {
                if (constructor != null) {
                    throw new JSONException("multi-JSONCreator");
                }
                constructor = constructor2;
            }
        }
        if (constructor != null) {
            return constructor;
        }
        for (Constructor constructor3 : constructors) {
            Annotation[][] paramAnnotationArrays = constructor3.getParameterAnnotations();
            if (paramAnnotationArrays.length != 0) {
                boolean match = true;
                for (Annotation[] paramAnnotationArray : paramAnnotationArrays) {
                    boolean paramMatch = false;
                    for (Annotation paramAnnotation : paramAnnotationArray) {
                        if (paramAnnotation instanceof JSONField) {
                            paramMatch = true;
                            break;
                        }
                    }
                    if (!paramMatch) {
                        match = false;
                        break;
                    }
                }
                if (!match) {
                    continue;
                } else {
                    if (constructor != null) {
                        throw new JSONException("multi-JSONCreator");
                    }
                    constructor = constructor3;
                }
            }
        }
        return constructor != null ? constructor : constructor;
    }

    private static Method getFactoryMethod(Class<?> clazz, Method[] methods) {
        Method factoryMethod = null;
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers()) && clazz.isAssignableFrom(method.getReturnType())) {
                JSONCreator annotation = (JSONCreator) method.getAnnotation(JSONCreator.class);
                if (annotation == null) {
                    continue;
                } else {
                    if (factoryMethod != null) {
                        throw new JSONException("multi-JSONCreator");
                    }
                    factoryMethod = method;
                }
            }
        }
        return factoryMethod;
    }

    public static Class<?> getBuilderClass(Class<?> clazz, JSONType type) {
        Class<?> builderClass;
        if (clazz != null && clazz.getName().equals("org.springframework.security.web.savedrequest.DefaultSavedRequest")) {
            return TypeUtils.loadClass("org.springframework.security.web.savedrequest.DefaultSavedRequest$Builder");
        }
        if (type == null || (builderClass = type.builder()) == Void.class) {
            return null;
        }
        return builderClass;
    }
}
