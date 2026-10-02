package com.alibaba.fastjson.serializer;

import bsh.ParserConstants;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.asm.ClassWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.alibaba.fastjson.asm.Label;
import com.alibaba.fastjson.asm.MethodVisitor;
import com.alibaba.fastjson.asm.MethodWriter;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ASMSerializerFactory {
    protected final ASMClassLoader classLoader = new ASMClassLoader();
    private final AtomicLong seed = new AtomicLong();
    static final String JSONSerializer = ASMUtils.type(JSONSerializer.class);
    static final String ObjectSerializer = ASMUtils.type(ObjectSerializer.class);
    static final String ObjectSerializer_desc = "L" + ObjectSerializer + ";";
    static final String SerializeWriter = ASMUtils.type(SerializeWriter.class);
    static final String SerializeWriter_desc = "L" + SerializeWriter + ";";
    static final String JavaBeanSerializer = ASMUtils.type(JavaBeanSerializer.class);
    static final String JavaBeanSerializer_desc = "L" + ASMUtils.type(JavaBeanSerializer.class) + ";";
    static final String SerialContext_desc = ASMUtils.desc((Class<?>) SerialContext.class);
    static final String SerializeFilterable_desc = ASMUtils.desc((Class<?>) SerializeFilterable.class);

    static class Context {
        static int fieldName = 6;
        static int original = 7;
        static int processValue = 8;
        private final SerializeBeanInfo beanInfo;
        private final String className;
        private final FieldInfo[] getters;
        private final boolean nonContext;
        private final boolean writeDirect;
        private Map<String, Integer> variants = new HashMap();
        private int variantIndex = 9;

        public Context(FieldInfo[] getters, SerializeBeanInfo beanInfo, String className, boolean writeDirect, boolean nonContext) {
            this.getters = getters;
            this.className = className;
            this.beanInfo = beanInfo;
            this.writeDirect = writeDirect;
            this.nonContext = nonContext || beanInfo.beanType.isEnum();
        }

        public int var(String name) {
            Integer i = this.variants.get(name);
            if (i == null) {
                Map<String, Integer> map = this.variants;
                int i2 = this.variantIndex;
                this.variantIndex = i2 + 1;
                map.put(name, Integer.valueOf(i2));
            }
            Integer i3 = this.variants.get(name);
            return i3.intValue();
        }

        public int var(String name, int increment) {
            Integer i = this.variants.get(name);
            if (i == null) {
                this.variants.put(name, Integer.valueOf(this.variantIndex));
                this.variantIndex += increment;
            }
            Integer i2 = this.variants.get(name);
            return i2.intValue();
        }

        public int getFieldOrinal(String name) {
            int size = this.getters.length;
            for (int i = 0; i < size; i++) {
                FieldInfo item = this.getters[i];
                if (item.name.equals(name)) {
                    int fieldIndex = i;
                    return fieldIndex;
                }
            }
            return -1;
        }
    }

    public JavaBeanSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) throws Exception {
        String methodName;
        String methodName2;
        Class<?> clazz = beanInfo.beanType;
        if (clazz.isPrimitive()) {
            throw new JSONException("unsupportd class " + clazz.getName());
        }
        JSONType jsonType = (JSONType) TypeUtils.getAnnotation(clazz, JSONType.class);
        FieldInfo[] unsortedGetters = beanInfo.fields;
        for (FieldInfo fieldInfo : unsortedGetters) {
            if (fieldInfo.field == null && fieldInfo.method != null && fieldInfo.method.getDeclaringClass().isInterface()) {
                return new JavaBeanSerializer(clazz);
            }
        }
        FieldInfo[] getters = beanInfo.sortedFields;
        boolean nativeSorted = beanInfo.sortedFields == beanInfo.fields;
        if (getters.length > 256) {
            return new JavaBeanSerializer(clazz);
        }
        for (FieldInfo getter : getters) {
            if (!ASMUtils.checkName(getter.getMember().getName())) {
                return new JavaBeanSerializer(clazz);
            }
        }
        String className = "ASMSerializer_" + this.seed.incrementAndGet() + "_" + clazz.getSimpleName();
        String packageName = ASMSerializerFactory.class.getPackage().getName();
        String classNameType = packageName.replace('.', '/') + "/" + className;
        String classNameFull = packageName + "." + className;
        ClassWriter cw = new ClassWriter();
        cw.visit(49, 33, classNameType, JavaBeanSerializer, new String[]{ObjectSerializer});
        for (FieldInfo fieldInfo2 : getters) {
            if (!fieldInfo2.fieldClass.isPrimitive() && fieldInfo2.fieldClass != String.class) {
                new FieldWriter(cw, 1, fieldInfo2.name + "_asm_fieldType", "Ljava/lang/reflect/Type;").visitEnd();
                if (List.class.isAssignableFrom(fieldInfo2.fieldClass)) {
                    new FieldWriter(cw, 1, fieldInfo2.name + "_asm_list_item_ser_", ObjectSerializer_desc).visitEnd();
                }
                new FieldWriter(cw, 1, fieldInfo2.name + "_asm_ser_", ObjectSerializer_desc).visitEnd();
            }
        }
        MethodVisitor mw = new MethodWriter(cw, 1, "<init>", "(" + ASMUtils.desc((Class<?>) SerializeBeanInfo.class) + ")V", null, null);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(183, JavaBeanSerializer, "<init>", "(" + ASMUtils.desc((Class<?>) SerializeBeanInfo.class) + ")V");
        for (int i = 0; i < getters.length; i++) {
            FieldInfo fieldInfo3 = getters[i];
            if (!fieldInfo3.fieldClass.isPrimitive() && fieldInfo3.fieldClass != String.class) {
                mw.visitVarInsn(25, 0);
                if (fieldInfo3.method != null) {
                    mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo3.declaringClass)));
                    mw.visitLdcInsn(fieldInfo3.method.getName());
                    mw.visitMethodInsn(184, ASMUtils.type(ASMUtils.class), "getMethodType", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Type;");
                } else {
                    mw.visitVarInsn(25, 0);
                    mw.visitLdcInsn(Integer.valueOf(i));
                    mw.visitMethodInsn(183, JavaBeanSerializer, "getFieldType", "(I)Ljava/lang/reflect/Type;");
                }
                mw.visitFieldInsn(181, classNameType, fieldInfo3.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            }
        }
        mw.visitInsn(177);
        mw.visitMaxs(4, 4);
        mw.visitEnd();
        boolean DisableCircularReferenceDetect = false;
        if (jsonType != null) {
            for (SerializerFeature featrues : jsonType.serialzeFeatures()) {
                if (featrues == SerializerFeature.DisableCircularReferenceDetect) {
                    DisableCircularReferenceDetect = true;
                    break;
                }
            }
        }
        for (int i2 = 0; i2 < 3; i2++) {
            boolean nonContext = DisableCircularReferenceDetect;
            boolean writeDirect = false;
            if (i2 == 0) {
                methodName2 = "write";
                writeDirect = true;
            } else if (i2 == 1) {
                methodName2 = "writeNormal";
            } else {
                writeDirect = true;
                nonContext = true;
                methodName2 = "writeDirectNonContext";
            }
            Context context = new Context(getters, beanInfo, classNameType, writeDirect, nonContext);
            MethodVisitor mw2 = new MethodWriter(cw, 1, methodName2, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            Label endIf_ = new Label();
            mw2.visitVarInsn(25, 2);
            mw2.visitJumpInsn(199, endIf_);
            mw2.visitVarInsn(25, 1);
            mw2.visitMethodInsn(182, JSONSerializer, "writeNull", "()V");
            mw2.visitInsn(177);
            mw2.visitLabel(endIf_);
            mw2.visitVarInsn(25, 1);
            mw2.visitFieldInsn(180, JSONSerializer, "out", SerializeWriter_desc);
            mw2.visitVarInsn(58, context.var("out"));
            if (!nativeSorted && !context.writeDirect && (jsonType == null || jsonType.alphabetic())) {
                Label _else = new Label();
                mw2.visitVarInsn(25, context.var("out"));
                mw2.visitMethodInsn(182, SerializeWriter, "isSortField", "()Z");
                mw2.visitJumpInsn(154, _else);
                mw2.visitVarInsn(25, 0);
                mw2.visitVarInsn(25, 1);
                mw2.visitVarInsn(25, 2);
                mw2.visitVarInsn(25, 3);
                mw2.visitVarInsn(25, 4);
                mw2.visitVarInsn(21, 5);
                mw2.visitMethodInsn(182, classNameType, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                mw2.visitInsn(177);
                mw2.visitLabel(_else);
            }
            if (context.writeDirect && !nonContext) {
                Label _direct = new Label();
                Label _directElse = new Label();
                mw2.visitVarInsn(25, 0);
                mw2.visitVarInsn(25, 1);
                mw2.visitMethodInsn(182, JavaBeanSerializer, "writeDirect", "(L" + JSONSerializer + ";)Z");
                mw2.visitJumpInsn(154, _directElse);
                mw2.visitVarInsn(25, 0);
                mw2.visitVarInsn(25, 1);
                mw2.visitVarInsn(25, 2);
                mw2.visitVarInsn(25, 3);
                mw2.visitVarInsn(25, 4);
                mw2.visitVarInsn(21, 5);
                mw2.visitMethodInsn(182, classNameType, "writeNormal", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                mw2.visitInsn(177);
                mw2.visitLabel(_directElse);
                mw2.visitVarInsn(25, context.var("out"));
                mw2.visitLdcInsn(Integer.valueOf(SerializerFeature.DisableCircularReferenceDetect.mask));
                mw2.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
                mw2.visitJumpInsn(153, _direct);
                mw2.visitVarInsn(25, 0);
                mw2.visitVarInsn(25, 1);
                mw2.visitVarInsn(25, 2);
                mw2.visitVarInsn(25, 3);
                mw2.visitVarInsn(25, 4);
                mw2.visitVarInsn(21, 5);
                mw2.visitMethodInsn(182, classNameType, "writeDirectNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                mw2.visitInsn(177);
                mw2.visitLabel(_direct);
            }
            mw2.visitVarInsn(25, 2);
            mw2.visitTypeInsn(192, ASMUtils.type(clazz));
            mw2.visitVarInsn(58, context.var("entity"));
            generateWriteMethod(clazz, mw2, getters, context);
            mw2.visitInsn(177);
            mw2.visitMaxs(7, context.variantIndex + 2);
            mw2.visitEnd();
        }
        if (!nativeSorted) {
            Context context2 = new Context(getters, beanInfo, classNameType, false, DisableCircularReferenceDetect);
            MethodVisitor mw3 = new MethodWriter(cw, 1, "writeUnsorted", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            mw3.visitVarInsn(25, 1);
            mw3.visitFieldInsn(180, JSONSerializer, "out", SerializeWriter_desc);
            mw3.visitVarInsn(58, context2.var("out"));
            mw3.visitVarInsn(25, 2);
            mw3.visitTypeInsn(192, ASMUtils.type(clazz));
            mw3.visitVarInsn(58, context2.var("entity"));
            generateWriteMethod(clazz, mw3, unsortedGetters, context2);
            mw3.visitInsn(177);
            mw3.visitMaxs(7, context2.variantIndex + 2);
            mw3.visitEnd();
        }
        for (int i3 = 0; i3 < 3; i3++) {
            boolean nonContext2 = DisableCircularReferenceDetect;
            boolean writeDirect2 = false;
            if (i3 == 0) {
                methodName = "writeAsArray";
                writeDirect2 = true;
            } else if (i3 == 1) {
                methodName = "writeAsArrayNormal";
            } else {
                writeDirect2 = true;
                nonContext2 = true;
                methodName = "writeAsArrayNonContext";
            }
            Context context3 = new Context(getters, beanInfo, classNameType, writeDirect2, nonContext2);
            MethodVisitor mw4 = new MethodWriter(cw, 1, methodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V", null, new String[]{"java/io/IOException"});
            mw4.visitVarInsn(25, 1);
            mw4.visitFieldInsn(180, JSONSerializer, "out", SerializeWriter_desc);
            mw4.visitVarInsn(58, context3.var("out"));
            mw4.visitVarInsn(25, 2);
            mw4.visitTypeInsn(192, ASMUtils.type(clazz));
            mw4.visitVarInsn(58, context3.var("entity"));
            generateWriteAsArray(clazz, mw4, getters, context3);
            mw4.visitInsn(177);
            mw4.visitMaxs(7, context3.variantIndex + 2);
            mw4.visitEnd();
        }
        byte[] code = cw.toByteArray();
        Class<?> serializerClass = this.classLoader.defineClassPublic(classNameFull, code, 0, code.length);
        Constructor<?> constructor = serializerClass.getConstructor(SerializeBeanInfo.class);
        Object instance = constructor.newInstance(beanInfo);
        return (JavaBeanSerializer) instance;
    }

    private void generateWriteAsArray(Class<?> clazz, MethodVisitor mw, FieldInfo[] getters, Context context) throws Exception {
        java.lang.reflect.Type elementType;
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(16, 91);
        mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        int size = getters.length;
        if (size == 0) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, 93);
            mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            return;
        }
        int i = 0;
        while (i < size) {
            char seperator = i == size + (-1) ? ']' : ',';
            FieldInfo fieldInfo = getters[i];
            Class<?> fieldClass = fieldInfo.fieldClass;
            mw.visitLdcInsn(fieldInfo.name);
            mw.visitVarInsn(58, Context.fieldName);
            if (fieldClass == Byte.TYPE || fieldClass == Short.TYPE || fieldClass == Integer.TYPE) {
                mw.visitVarInsn(25, context.var("out"));
                mw.visitInsn(89);
                _get(mw, context, fieldInfo);
                mw.visitMethodInsn(182, SerializeWriter, "writeInt", "(I)V");
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (fieldClass == Long.TYPE) {
                mw.visitVarInsn(25, context.var("out"));
                mw.visitInsn(89);
                _get(mw, context, fieldInfo);
                mw.visitMethodInsn(182, SerializeWriter, "writeLong", "(J)V");
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (fieldClass == Float.TYPE) {
                mw.visitVarInsn(25, context.var("out"));
                mw.visitInsn(89);
                _get(mw, context, fieldInfo);
                mw.visitInsn(4);
                mw.visitMethodInsn(182, SerializeWriter, "writeFloat", "(FZ)V");
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (fieldClass == Double.TYPE) {
                mw.visitVarInsn(25, context.var("out"));
                mw.visitInsn(89);
                _get(mw, context, fieldInfo);
                mw.visitInsn(4);
                mw.visitMethodInsn(182, SerializeWriter, "writeDouble", "(DZ)V");
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (fieldClass == Boolean.TYPE) {
                mw.visitVarInsn(25, context.var("out"));
                mw.visitInsn(89);
                _get(mw, context, fieldInfo);
                mw.visitMethodInsn(182, SerializeWriter, "write", "(Z)V");
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (fieldClass == Character.TYPE) {
                mw.visitVarInsn(25, context.var("out"));
                _get(mw, context, fieldInfo);
                mw.visitMethodInsn(184, "java/lang/Character", "toString", "(C)Ljava/lang/String;");
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
            } else if (fieldClass == String.class) {
                mw.visitVarInsn(25, context.var("out"));
                _get(mw, context, fieldInfo);
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "writeString", "(Ljava/lang/String;C)V");
            } else if (fieldClass.isEnum()) {
                mw.visitVarInsn(25, context.var("out"));
                mw.visitInsn(89);
                _get(mw, context, fieldInfo);
                mw.visitMethodInsn(182, SerializeWriter, "writeEnum", "(Ljava/lang/Enum;)V");
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else if (List.class.isAssignableFrom(fieldClass)) {
                java.lang.reflect.Type fieldType = fieldInfo.fieldType;
                if (fieldType instanceof Class) {
                    elementType = Object.class;
                } else {
                    elementType = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
                }
                Class<?> elementClass = null;
                if ((elementType instanceof Class) && (elementClass = (Class) elementType) == Object.class) {
                    elementClass = null;
                }
                _get(mw, context, fieldInfo);
                mw.visitTypeInsn(192, "java/util/List");
                mw.visitVarInsn(58, context.var("list"));
                if (elementClass == String.class && context.writeDirect) {
                    mw.visitVarInsn(25, context.var("out"));
                    mw.visitVarInsn(25, context.var("list"));
                    mw.visitMethodInsn(182, SerializeWriter, "write", "(Ljava/util/List;)V");
                } else {
                    Label nullEnd_ = new Label();
                    Label nullElse_ = new Label();
                    mw.visitVarInsn(25, context.var("list"));
                    mw.visitJumpInsn(199, nullElse_);
                    mw.visitVarInsn(25, context.var("out"));
                    mw.visitMethodInsn(182, SerializeWriter, "writeNull", "()V");
                    mw.visitJumpInsn(167, nullEnd_);
                    mw.visitLabel(nullElse_);
                    mw.visitVarInsn(25, context.var("list"));
                    mw.visitMethodInsn(185, "java/util/List", "size", "()I");
                    mw.visitVarInsn(54, context.var("size"));
                    mw.visitVarInsn(25, context.var("out"));
                    mw.visitVarInsn(16, 91);
                    mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
                    Label for_ = new Label();
                    Label forFirst_ = new Label();
                    Label forEnd_ = new Label();
                    mw.visitInsn(3);
                    mw.visitVarInsn(54, context.var("i"));
                    mw.visitLabel(for_);
                    mw.visitVarInsn(21, context.var("i"));
                    mw.visitVarInsn(21, context.var("size"));
                    mw.visitJumpInsn(162, forEnd_);
                    mw.visitVarInsn(21, context.var("i"));
                    mw.visitJumpInsn(153, forFirst_);
                    mw.visitVarInsn(25, context.var("out"));
                    mw.visitVarInsn(16, 44);
                    mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
                    mw.visitLabel(forFirst_);
                    mw.visitVarInsn(25, context.var("list"));
                    mw.visitVarInsn(21, context.var("i"));
                    mw.visitMethodInsn(185, "java/util/List", "get", "(I)Ljava/lang/Object;");
                    mw.visitVarInsn(58, context.var("list_item"));
                    Label forItemNullEnd_ = new Label();
                    Label forItemNullElse_ = new Label();
                    mw.visitVarInsn(25, context.var("list_item"));
                    mw.visitJumpInsn(199, forItemNullElse_);
                    mw.visitVarInsn(25, context.var("out"));
                    mw.visitMethodInsn(182, SerializeWriter, "writeNull", "()V");
                    mw.visitJumpInsn(167, forItemNullEnd_);
                    mw.visitLabel(forItemNullElse_);
                    Label forItemClassIfEnd_ = new Label();
                    Label forItemClassIfElse_ = new Label();
                    if (elementClass != null && Modifier.isPublic(elementClass.getModifiers())) {
                        mw.visitVarInsn(25, context.var("list_item"));
                        mw.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                        mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                        mw.visitJumpInsn(166, forItemClassIfElse_);
                        _getListFieldItemSer(context, mw, fieldInfo, elementClass);
                        mw.visitVarInsn(58, context.var("list_item_desc"));
                        Label instanceOfElse_ = new Label();
                        Label instanceOfEnd_ = new Label();
                        if (context.writeDirect) {
                            mw.visitVarInsn(25, context.var("list_item_desc"));
                            mw.visitTypeInsn(193, JavaBeanSerializer);
                            mw.visitJumpInsn(153, instanceOfElse_);
                            mw.visitVarInsn(25, context.var("list_item_desc"));
                            mw.visitTypeInsn(192, JavaBeanSerializer);
                            mw.visitVarInsn(25, 1);
                            mw.visitVarInsn(25, context.var("list_item"));
                            if (context.nonContext) {
                                mw.visitInsn(1);
                            } else {
                                mw.visitVarInsn(21, context.var("i"));
                                mw.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                            }
                            mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                            mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                            mw.visitMethodInsn(182, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                            mw.visitJumpInsn(167, instanceOfEnd_);
                            mw.visitLabel(instanceOfElse_);
                        }
                        mw.visitVarInsn(25, context.var("list_item_desc"));
                        mw.visitVarInsn(25, 1);
                        mw.visitVarInsn(25, context.var("list_item"));
                        if (context.nonContext) {
                            mw.visitInsn(1);
                        } else {
                            mw.visitVarInsn(21, context.var("i"));
                            mw.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                        }
                        mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                        mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                        mw.visitMethodInsn(185, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                        mw.visitLabel(instanceOfEnd_);
                        mw.visitJumpInsn(167, forItemClassIfEnd_);
                    }
                    mw.visitLabel(forItemClassIfElse_);
                    mw.visitVarInsn(25, 1);
                    mw.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        mw.visitInsn(1);
                    } else {
                        mw.visitVarInsn(21, context.var("i"));
                        mw.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    if (elementClass != null && Modifier.isPublic(elementClass.getModifiers())) {
                        mw.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) elementType)));
                        mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                        mw.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    } else {
                        mw.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                    }
                    mw.visitLabel(forItemClassIfEnd_);
                    mw.visitLabel(forItemNullEnd_);
                    mw.visitIincInsn(context.var("i"), 1);
                    mw.visitJumpInsn(167, for_);
                    mw.visitLabel(forEnd_);
                    mw.visitVarInsn(25, context.var("out"));
                    mw.visitVarInsn(16, 93);
                    mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
                    mw.visitLabel(nullEnd_);
                }
                mw.visitVarInsn(25, context.var("out"));
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            } else {
                Label notNullEnd_ = new Label();
                Label notNullElse_ = new Label();
                _get(mw, context, fieldInfo);
                mw.visitInsn(89);
                mw.visitVarInsn(58, context.var("field_" + fieldInfo.fieldClass.getName()));
                mw.visitJumpInsn(199, notNullElse_);
                mw.visitVarInsn(25, context.var("out"));
                mw.visitMethodInsn(182, SerializeWriter, "writeNull", "()V");
                mw.visitJumpInsn(167, notNullEnd_);
                mw.visitLabel(notNullElse_);
                Label classIfEnd_ = new Label();
                Label classIfElse_ = new Label();
                mw.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                mw.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldClass)));
                mw.visitJumpInsn(166, classIfElse_);
                _getFieldSer(context, mw, fieldInfo);
                mw.visitVarInsn(58, context.var("fied_ser"));
                Label instanceOfElse_2 = new Label();
                Label instanceOfEnd_2 = new Label();
                if (context.writeDirect && Modifier.isPublic(fieldClass.getModifiers())) {
                    mw.visitVarInsn(25, context.var("fied_ser"));
                    mw.visitTypeInsn(193, JavaBeanSerializer);
                    mw.visitJumpInsn(153, instanceOfElse_2);
                    mw.visitVarInsn(25, context.var("fied_ser"));
                    mw.visitTypeInsn(192, JavaBeanSerializer);
                    mw.visitVarInsn(25, 1);
                    mw.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                    mw.visitVarInsn(25, Context.fieldName);
                    mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldClass)));
                    mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                    mw.visitMethodInsn(182, JavaBeanSerializer, "writeAsArrayNonContext", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    mw.visitJumpInsn(167, instanceOfEnd_2);
                    mw.visitLabel(instanceOfElse_2);
                }
                mw.visitVarInsn(25, context.var("fied_ser"));
                mw.visitVarInsn(25, 1);
                mw.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                mw.visitVarInsn(25, Context.fieldName);
                mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldClass)));
                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                mw.visitMethodInsn(185, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                mw.visitLabel(instanceOfEnd_2);
                mw.visitJumpInsn(167, classIfEnd_);
                mw.visitLabel(classIfElse_);
                String format = fieldInfo.getFormat();
                mw.visitVarInsn(25, 1);
                mw.visitVarInsn(25, context.var("field_" + fieldInfo.fieldClass.getName()));
                if (format != null) {
                    mw.visitLdcInsn(format);
                    mw.visitMethodInsn(182, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
                } else {
                    mw.visitVarInsn(25, Context.fieldName);
                    if ((fieldInfo.fieldType instanceof Class) && ((Class) fieldInfo.fieldType).isPrimitive()) {
                        mw.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
                    } else {
                        mw.visitVarInsn(25, 0);
                        mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                        mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                        mw.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    }
                }
                mw.visitLabel(classIfEnd_);
                mw.visitLabel(notNullEnd_);
                mw.visitVarInsn(25, context.var("out"));
                mw.visitVarInsn(16, seperator);
                mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            }
            i++;
        }
    }

    private void generateWriteMethod(Class<?> clazz, MethodVisitor mw, FieldInfo[] getters, Context context) throws Exception {
        String writeAsArrayMethodName;
        Label end = new Label();
        if (!context.writeDirect) {
            Label endSupper_ = new Label();
            Label supper_ = new Label();
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.PrettyFormat.mask));
            mw.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(154, supper_);
            boolean hasMethod = false;
            for (FieldInfo getter : getters) {
                if (getter.method != null) {
                    hasMethod = true;
                }
            }
            if (hasMethod) {
                mw.visitVarInsn(25, context.var("out"));
                mw.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreErrorGetter.mask));
                mw.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
                mw.visitJumpInsn(153, endSupper_);
            } else {
                mw.visitJumpInsn(167, endSupper_);
            }
            mw.visitLabel(supper_);
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(21, 5);
            mw.visitMethodInsn(183, JavaBeanSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitInsn(177);
            mw.visitLabel(endSupper_);
        }
        if (!context.nonContext) {
            Label endRef_ = new Label();
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(21, 5);
            mw.visitMethodInsn(182, JavaBeanSerializer, "writeReference", "(L" + JSONSerializer + ";Ljava/lang/Object;I)Z");
            mw.visitJumpInsn(153, endRef_);
            mw.visitInsn(177);
            mw.visitLabel(endRef_);
        }
        if (context.writeDirect) {
            if (context.nonContext) {
                writeAsArrayMethodName = "writeAsArrayNonContext";
            } else {
                writeAsArrayMethodName = "writeAsArray";
            }
        } else {
            writeAsArrayMethodName = "writeAsArrayNormal";
        }
        if ((context.beanInfo.features & SerializerFeature.BeanToArray.mask) == 0) {
            Label endWriteAsArray_ = new Label();
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.BeanToArray.mask));
            mw.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(153, endWriteAsArray_);
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(21, 5);
            mw.visitMethodInsn(182, context.className, writeAsArrayMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitInsn(177);
            mw.visitLabel(endWriteAsArray_);
        } else {
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(21, 5);
            mw.visitMethodInsn(182, context.className, writeAsArrayMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitInsn(177);
        }
        if (!context.nonContext) {
            mw.visitVarInsn(25, 1);
            mw.visitMethodInsn(182, JSONSerializer, "getContext", "()" + SerialContext_desc);
            mw.visitVarInsn(58, context.var("parent"));
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("parent"));
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, 3);
            mw.visitLdcInsn(Integer.valueOf(context.beanInfo.features));
            mw.visitMethodInsn(182, JSONSerializer, "setContext", "(" + SerialContext_desc + "Ljava/lang/Object;Ljava/lang/Object;I)V");
        }
        boolean writeClasName = (context.beanInfo.features & SerializerFeature.WriteClassName.mask) != 0;
        if (writeClasName || !context.writeDirect) {
            Label end_ = new Label();
            Label else_ = new Label();
            Label writeClass_ = new Label();
            if (!writeClasName) {
                mw.visitVarInsn(25, 1);
                mw.visitVarInsn(25, 4);
                mw.visitVarInsn(25, 2);
                mw.visitMethodInsn(182, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
                mw.visitJumpInsn(153, else_);
            }
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(25, 2);
            mw.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            mw.visitJumpInsn(165, else_);
            mw.visitLabel(writeClass_);
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, ParserConstants.ANDASSIGNX);
            mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            if (context.beanInfo.typeKey != null) {
                mw.visitLdcInsn(context.beanInfo.typeKey);
            } else {
                mw.visitInsn(1);
            }
            mw.visitVarInsn(25, 2);
            mw.visitMethodInsn(182, JavaBeanSerializer, "writeClassName", "(L" + JSONSerializer + ";Ljava/lang/String;Ljava/lang/Object;)V");
            mw.visitVarInsn(16, 44);
            mw.visitJumpInsn(167, end_);
            mw.visitLabel(else_);
            mw.visitVarInsn(16, ParserConstants.ANDASSIGNX);
            mw.visitLabel(end_);
        } else {
            mw.visitVarInsn(16, ParserConstants.ANDASSIGNX);
        }
        mw.visitVarInsn(54, context.var("seperator"));
        if (!context.writeDirect) {
            _before(mw, context);
        }
        if (!context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitMethodInsn(182, SerializeWriter, "isNotWriteDefaultValue", "()Z");
            mw.visitVarInsn(54, context.var("notWriteDefaultValue"));
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 0);
            mw.visitMethodInsn(182, JSONSerializer, "checkValue", "(" + SerializeFilterable_desc + ")Z");
            mw.visitVarInsn(54, context.var("checkValue"));
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 0);
            mw.visitMethodInsn(182, JSONSerializer, "hasNameFilters", "(" + SerializeFilterable_desc + ")Z");
            mw.visitVarInsn(54, context.var("hasNameFilters"));
        }
        for (FieldInfo property : getters) {
            Class<?> propertyClass = property.fieldClass;
            mw.visitLdcInsn(property.name);
            mw.visitVarInsn(58, Context.fieldName);
            if (propertyClass == Byte.TYPE || propertyClass == Short.TYPE || propertyClass == Integer.TYPE) {
                _int(clazz, mw, property, context, context.var(propertyClass.getName()), 'I');
            } else if (propertyClass == Long.TYPE) {
                _long(clazz, mw, property, context);
            } else if (propertyClass == Float.TYPE) {
                _float(clazz, mw, property, context);
            } else if (propertyClass == Double.TYPE) {
                _double(clazz, mw, property, context);
            } else if (propertyClass == Boolean.TYPE) {
                _int(clazz, mw, property, context, context.var(SettingsContentProvider.BOOLEAN_TYPE), 'Z');
            } else if (propertyClass == Character.TYPE) {
                _int(clazz, mw, property, context, context.var("char"), 'C');
            } else if (propertyClass == String.class) {
                _string(clazz, mw, property, context);
            } else if (propertyClass == BigDecimal.class) {
                _decimal(clazz, mw, property, context);
            } else if (List.class.isAssignableFrom(propertyClass)) {
                _list(clazz, mw, property, context);
            } else if (propertyClass.isEnum()) {
                _enum(clazz, mw, property, context);
            } else {
                _object(clazz, mw, property, context);
            }
        }
        if (!context.writeDirect) {
            _after(mw, context);
        }
        Label _else = new Label();
        Label _end_if = new Label();
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitIntInsn(16, ParserConstants.ANDASSIGNX);
        mw.visitJumpInsn(160, _else);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(16, ParserConstants.ANDASSIGNX);
        mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        mw.visitLabel(_else);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(16, ParserConstants.ORASSIGNX);
        mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        mw.visitLabel(_end_if);
        mw.visitLabel(end);
        if (!context.nonContext) {
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("parent"));
            mw.visitMethodInsn(182, JSONSerializer, "setContext", "(" + SerialContext_desc + ")V");
        }
    }

    private void _object(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label _end = new Label();
        _nameApply(mw, property, context, _end);
        _get(mw, context, property);
        mw.visitVarInsn(58, context.var("object"));
        _filters(mw, property, context, _end);
        _writeObject(mw, property, context, _end);
        mw.visitLabel(_end);
    }

    private void _enum(Class<?> clazz, MethodVisitor mw, FieldInfo fieldInfo, Context context) {
        Label _not_null = new Label();
        Label _end_if = new Label();
        Label _end = new Label();
        _nameApply(mw, fieldInfo, context, _end);
        _get(mw, context, fieldInfo);
        mw.visitTypeInsn(192, "java/lang/Enum");
        mw.visitVarInsn(58, context.var("enum"));
        _filters(mw, fieldInfo, context, _end);
        mw.visitVarInsn(25, context.var("enum"));
        mw.visitJumpInsn(199, _not_null);
        _if_write_null(mw, fieldInfo, context);
        mw.visitJumpInsn(167, _end_if);
        mw.visitLabel(_not_null);
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, context.var("enum"));
            mw.visitMethodInsn(182, "java/lang/Enum", RNMessageModule.NAME, "()Ljava/lang/String;");
            mw.visitMethodInsn(182, SerializeWriter, "writeFieldValueStringWithDoubleQuote", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitInsn(3);
            mw.visitMethodInsn(182, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("enum"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
            mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            mw.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
        }
        _seperator(mw, context);
        mw.visitLabel(_end_if);
        mw.visitLabel(_end);
    }

    private void _int(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context, int var, char type) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(54, var);
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(21, var);
        mw.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;" + type + ")V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _long(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(55, context.var(SettingsContentProvider.LONG_TYPE, 2));
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(22, context.var(SettingsContentProvider.LONG_TYPE, 2));
        mw.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;J)V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _float(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(56, context.var(SettingsContentProvider.FLOAT_TYPE));
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(23, context.var(SettingsContentProvider.FLOAT_TYPE));
        mw.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;F)V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _double(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(57, context.var("double", 2));
        _filters(mw, property, context, end_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(24, context.var("double", 2));
        mw.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;D)V");
        _seperator(mw, context);
        mw.visitLabel(end_);
    }

    private void _get(MethodVisitor mw, Context context, FieldInfo fieldInfo) {
        Method method = fieldInfo.method;
        if (method != null) {
            mw.visitVarInsn(25, context.var("entity"));
            Class<?> declaringClass = method.getDeclaringClass();
            mw.visitMethodInsn(declaringClass.isInterface() ? 185 : 182, ASMUtils.type(declaringClass), method.getName(), ASMUtils.desc(method));
            if (!method.getReturnType().equals(fieldInfo.fieldClass)) {
                mw.visitTypeInsn(192, ASMUtils.type(fieldInfo.fieldClass));
                return;
            }
            return;
        }
        mw.visitVarInsn(25, context.var("entity"));
        Field field = fieldInfo.field;
        mw.visitFieldInsn(180, ASMUtils.type(fieldInfo.declaringClass), field.getName(), ASMUtils.desc(field.getType()));
        if (!field.getType().equals(fieldInfo.fieldClass)) {
            mw.visitTypeInsn(192, ASMUtils.type(fieldInfo.fieldClass));
        }
    }

    private void _decimal(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(58, context.var("decimal"));
        _filters(mw, property, context, end_);
        Label if_ = new Label();
        Label else_ = new Label();
        Label endIf_ = new Label();
        mw.visitLabel(if_);
        mw.visitVarInsn(25, context.var("decimal"));
        mw.visitJumpInsn(199, else_);
        _if_write_null(mw, property, context);
        mw.visitJumpInsn(167, endIf_);
        mw.visitLabel(else_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitVarInsn(25, Context.fieldName);
        mw.visitVarInsn(25, context.var("decimal"));
        mw.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/math/BigDecimal;)V");
        _seperator(mw, context);
        mw.visitJumpInsn(167, endIf_);
        mw.visitLabel(endIf_);
        mw.visitLabel(end_);
    }

    private void _string(Class<?> clazz, MethodVisitor mw, FieldInfo property, Context context) {
        Label end_ = new Label();
        if (property.name.equals(context.beanInfo.typeKey)) {
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 4);
            mw.visitVarInsn(25, 2);
            mw.visitMethodInsn(182, JSONSerializer, "isWriteClassName", "(Ljava/lang/reflect/Type;Ljava/lang/Object;)Z");
            mw.visitJumpInsn(154, end_);
        }
        _nameApply(mw, property, context, end_);
        _get(mw, context, property);
        mw.visitVarInsn(58, context.var(SettingsContentProvider.STRING_TYPE));
        _filters(mw, property, context, end_);
        Label else_ = new Label();
        Label endIf_ = new Label();
        mw.visitVarInsn(25, context.var(SettingsContentProvider.STRING_TYPE));
        mw.visitJumpInsn(199, else_);
        _if_write_null(mw, property, context);
        mw.visitJumpInsn(167, endIf_);
        mw.visitLabel(else_);
        if ("trim".equals(property.format)) {
            mw.visitVarInsn(25, context.var(SettingsContentProvider.STRING_TYPE));
            mw.visitMethodInsn(182, "java/lang/String", "trim", "()Ljava/lang/String;");
            mw.visitVarInsn(58, context.var(SettingsContentProvider.STRING_TYPE));
        }
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, context.var(SettingsContentProvider.STRING_TYPE));
            mw.visitMethodInsn(182, SerializeWriter, "writeFieldValueStringWithDoubleQuoteCheck", "(CLjava/lang/String;Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(21, context.var("seperator"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, context.var(SettingsContentProvider.STRING_TYPE));
            mw.visitMethodInsn(182, SerializeWriter, "writeFieldValue", "(CLjava/lang/String;Ljava/lang/String;)V");
        }
        _seperator(mw, context);
        mw.visitLabel(endIf_);
        mw.visitLabel(end_);
    }

    private void _list(Class<?> clazz, MethodVisitor mw, FieldInfo fieldInfo, Context context) {
        java.lang.reflect.Type propertyType = fieldInfo.fieldType;
        java.lang.reflect.Type elementType = TypeUtils.getCollectionItemType(propertyType);
        Class<?> elementClass = null;
        if (elementType instanceof Class) {
            elementClass = (Class) elementType;
        }
        if (elementClass == Object.class || elementClass == Serializable.class) {
            elementClass = null;
        }
        Label end_ = new Label();
        Label else_ = new Label();
        Label endIf_ = new Label();
        _nameApply(mw, fieldInfo, context, end_);
        _get(mw, context, fieldInfo);
        mw.visitTypeInsn(192, "java/util/List");
        mw.visitVarInsn(58, context.var("list"));
        _filters(mw, fieldInfo, context, end_);
        mw.visitVarInsn(25, context.var("list"));
        mw.visitJumpInsn(199, else_);
        _if_write_null(mw, fieldInfo, context);
        mw.visitJumpInsn(167, endIf_);
        mw.visitLabel(else_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        _writeFieldName(mw, context);
        mw.visitVarInsn(25, context.var("list"));
        mw.visitMethodInsn(185, "java/util/List", "size", "()I");
        mw.visitVarInsn(54, context.var("size"));
        Label _else_3 = new Label();
        Label _end_if_3 = new Label();
        mw.visitVarInsn(21, context.var("size"));
        mw.visitInsn(3);
        mw.visitJumpInsn(160, _else_3);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitLdcInsn("[]");
        mw.visitMethodInsn(182, SerializeWriter, "write", "(Ljava/lang/String;)V");
        mw.visitJumpInsn(167, _end_if_3);
        mw.visitLabel(_else_3);
        if (!context.nonContext) {
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("list"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitMethodInsn(182, JSONSerializer, "setContext", "(Ljava/lang/Object;Ljava/lang/Object;)V");
        }
        if (elementType == String.class && context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, context.var("list"));
            mw.visitMethodInsn(182, SerializeWriter, "write", "(Ljava/util/List;)V");
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, 91);
            mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            Label for_ = new Label();
            Label forFirst_ = new Label();
            Label forEnd_ = new Label();
            mw.visitInsn(3);
            mw.visitVarInsn(54, context.var("i"));
            mw.visitLabel(for_);
            mw.visitVarInsn(21, context.var("i"));
            mw.visitVarInsn(21, context.var("size"));
            mw.visitJumpInsn(162, forEnd_);
            mw.visitVarInsn(21, context.var("i"));
            mw.visitJumpInsn(153, forFirst_);
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, 44);
            mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
            mw.visitLabel(forFirst_);
            mw.visitVarInsn(25, context.var("list"));
            mw.visitVarInsn(21, context.var("i"));
            mw.visitMethodInsn(185, "java/util/List", "get", "(I)Ljava/lang/Object;");
            mw.visitVarInsn(58, context.var("list_item"));
            Label forItemNullEnd_ = new Label();
            Label forItemNullElse_ = new Label();
            mw.visitVarInsn(25, context.var("list_item"));
            mw.visitJumpInsn(199, forItemNullElse_);
            mw.visitVarInsn(25, context.var("out"));
            mw.visitMethodInsn(182, SerializeWriter, "writeNull", "()V");
            mw.visitJumpInsn(167, forItemNullEnd_);
            mw.visitLabel(forItemNullElse_);
            Label forItemClassIfEnd_ = new Label();
            Label forItemClassIfElse_ = new Label();
            if (elementClass != null && Modifier.isPublic(elementClass.getModifiers())) {
                mw.visitVarInsn(25, context.var("list_item"));
                mw.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
                mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                mw.visitJumpInsn(166, forItemClassIfElse_);
                _getListFieldItemSer(context, mw, fieldInfo, elementClass);
                mw.visitVarInsn(58, context.var("list_item_desc"));
                Label instanceOfElse_ = new Label();
                Label instanceOfEnd_ = new Label();
                if (context.writeDirect) {
                    String writeMethodName = (context.nonContext && context.writeDirect) ? "writeDirectNonContext" : "write";
                    mw.visitVarInsn(25, context.var("list_item_desc"));
                    mw.visitTypeInsn(193, JavaBeanSerializer);
                    mw.visitJumpInsn(153, instanceOfElse_);
                    mw.visitVarInsn(25, context.var("list_item_desc"));
                    mw.visitTypeInsn(192, JavaBeanSerializer);
                    mw.visitVarInsn(25, 1);
                    mw.visitVarInsn(25, context.var("list_item"));
                    if (context.nonContext) {
                        mw.visitInsn(1);
                    } else {
                        mw.visitVarInsn(21, context.var("i"));
                        mw.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                    }
                    mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                    mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                    mw.visitMethodInsn(182, JavaBeanSerializer, writeMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                    mw.visitJumpInsn(167, instanceOfEnd_);
                    mw.visitLabel(instanceOfElse_);
                }
                mw.visitVarInsn(25, context.var("list_item_desc"));
                mw.visitVarInsn(25, 1);
                mw.visitVarInsn(25, context.var("list_item"));
                if (context.nonContext) {
                    mw.visitInsn(1);
                } else {
                    mw.visitVarInsn(21, context.var("i"));
                    mw.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                }
                mw.visitLdcInsn(Type.getType(ASMUtils.desc(elementClass)));
                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                mw.visitMethodInsn(185, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
                mw.visitLabel(instanceOfEnd_);
                mw.visitJumpInsn(167, forItemClassIfEnd_);
            }
            mw.visitLabel(forItemClassIfElse_);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("list_item"));
            if (context.nonContext) {
                mw.visitInsn(1);
            } else {
                mw.visitVarInsn(21, context.var("i"));
                mw.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            }
            if (elementClass != null && Modifier.isPublic(elementClass.getModifiers())) {
                mw.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) elementType)));
                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                mw.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            } else {
                mw.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            }
            mw.visitLabel(forItemClassIfEnd_);
            mw.visitLabel(forItemNullEnd_);
            mw.visitIincInsn(context.var("i"), 1);
            mw.visitJumpInsn(167, for_);
            mw.visitLabel(forEnd_);
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(16, 93);
            mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        }
        mw.visitVarInsn(25, 1);
        mw.visitMethodInsn(182, JSONSerializer, "popContext", "()V");
        mw.visitLabel(_end_if_3);
        _seperator(mw, context);
        mw.visitLabel(endIf_);
        mw.visitLabel(end_);
    }

    private void _filters(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        if (property.fieldTransient) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.SkipTransientField.mask));
            mw.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(154, _end);
        }
        _notWriteDefault(mw, property, context, _end);
        if (!context.writeDirect) {
            _apply(mw, property, context);
            mw.visitJumpInsn(153, _end);
            _processKey(mw, property, context);
            _processValue(mw, property, context, _end);
        }
    }

    private void _nameApply(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        if (!context.writeDirect) {
            mw.visitVarInsn(25, 0);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, 2);
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitMethodInsn(182, JavaBeanSerializer, "applyName", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;)Z");
            mw.visitJumpInsn(153, _end);
            _labelApply(mw, property, context, _end);
        }
        if (property.field == null) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.IgnoreNonFieldGetter.mask));
            mw.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(154, _end);
        }
    }

    private void _labelApply(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(property.label);
        mw.visitMethodInsn(182, JavaBeanSerializer, "applyLabel", "(L" + JSONSerializer + ";Ljava/lang/String;)Z");
        mw.visitJumpInsn(153, _end);
    }

    private void _writeObject(MethodVisitor mw, FieldInfo fieldInfo, Context context, Label _end) {
        String writeMethodName;
        String format = fieldInfo.getFormat();
        Class<?> fieldClass = fieldInfo.fieldClass;
        Label notNull_ = new Label();
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("object"));
        } else {
            mw.visitVarInsn(25, Context.processValue);
        }
        mw.visitInsn(89);
        mw.visitVarInsn(58, context.var("object"));
        mw.visitJumpInsn(199, notNull_);
        _if_write_null(mw, fieldInfo, context);
        mw.visitJumpInsn(167, _end);
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        _writeFieldName(mw, context);
        Label classIfEnd_ = new Label();
        Label classIfElse_ = new Label();
        if (Modifier.isPublic(fieldClass.getModifiers()) && !ParserConfig.isPrimitive2(fieldClass)) {
            mw.visitVarInsn(25, context.var("object"));
            mw.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldClass)));
            mw.visitJumpInsn(166, classIfElse_);
            _getFieldSer(context, mw, fieldInfo);
            mw.visitVarInsn(58, context.var("fied_ser"));
            Label instanceOfElse_ = new Label();
            Label instanceOfEnd_ = new Label();
            mw.visitVarInsn(25, context.var("fied_ser"));
            mw.visitTypeInsn(193, JavaBeanSerializer);
            mw.visitJumpInsn(153, instanceOfElse_);
            boolean disableCircularReferenceDetect = (fieldInfo.serialzeFeatures & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
            boolean fieldBeanToArray = (fieldInfo.serialzeFeatures & SerializerFeature.BeanToArray.mask) != 0;
            if (disableCircularReferenceDetect || (context.nonContext && context.writeDirect)) {
                writeMethodName = fieldBeanToArray ? "writeAsArrayNonContext" : "writeDirectNonContext";
            } else {
                writeMethodName = fieldBeanToArray ? "writeAsArray" : "write";
            }
            mw.visitVarInsn(25, context.var("fied_ser"));
            mw.visitTypeInsn(192, JavaBeanSerializer);
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("object"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, 0);
            mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            mw.visitMethodInsn(182, JavaBeanSerializer, writeMethodName, "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitJumpInsn(167, instanceOfEnd_);
            mw.visitLabel(instanceOfElse_);
            mw.visitVarInsn(25, context.var("fied_ser"));
            mw.visitVarInsn(25, 1);
            mw.visitVarInsn(25, context.var("object"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitVarInsn(25, 0);
            mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
            mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
            mw.visitMethodInsn(185, ObjectSerializer, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            mw.visitLabel(instanceOfEnd_);
            mw.visitJumpInsn(167, classIfEnd_);
        }
        mw.visitLabel(classIfElse_);
        mw.visitVarInsn(25, 1);
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("object"));
        } else {
            mw.visitVarInsn(25, Context.processValue);
        }
        if (format != null) {
            mw.visitLdcInsn(format);
            mw.visitMethodInsn(182, JSONSerializer, "writeWithFormat", "(Ljava/lang/Object;Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, Context.fieldName);
            if ((fieldInfo.fieldType instanceof Class) && ((Class) fieldInfo.fieldType).isPrimitive()) {
                mw.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;)V");
            } else {
                if (fieldInfo.fieldClass == String.class) {
                    mw.visitLdcInsn(Type.getType(ASMUtils.desc((Class<?>) String.class)));
                } else {
                    mw.visitVarInsn(25, 0);
                    mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_fieldType", "Ljava/lang/reflect/Type;");
                }
                mw.visitLdcInsn(Integer.valueOf(fieldInfo.serialzeFeatures));
                mw.visitMethodInsn(182, JSONSerializer, "writeWithFieldName", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/reflect/Type;I)V");
            }
        }
        mw.visitLabel(classIfEnd_);
        _seperator(mw, context);
    }

    private void _before(MethodVisitor mw, Context context) {
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(182, JavaBeanSerializer, "writeBefore", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        mw.visitVarInsn(54, context.var("seperator"));
    }

    private void _after(MethodVisitor mw, Context context) {
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(182, JavaBeanSerializer, "writeAfter", "(L" + JSONSerializer + ";Ljava/lang/Object;C)C");
        mw.visitVarInsn(54, context.var("seperator"));
    }

    private void _notWriteDefault(MethodVisitor mw, FieldInfo property, Context context, Label _end) {
        if (!context.writeDirect) {
            Label elseLabel = new Label();
            mw.visitVarInsn(21, context.var("notWriteDefaultValue"));
            mw.visitJumpInsn(153, elseLabel);
            Class<?> propertyClass = property.fieldClass;
            if (propertyClass == Boolean.TYPE) {
                mw.visitVarInsn(21, context.var(SettingsContentProvider.BOOLEAN_TYPE));
                mw.visitJumpInsn(153, _end);
            } else if (propertyClass == Byte.TYPE) {
                mw.visitVarInsn(21, context.var("byte"));
                mw.visitJumpInsn(153, _end);
            } else if (propertyClass == Short.TYPE) {
                mw.visitVarInsn(21, context.var("short"));
                mw.visitJumpInsn(153, _end);
            } else if (propertyClass == Integer.TYPE) {
                mw.visitVarInsn(21, context.var("int"));
                mw.visitJumpInsn(153, _end);
            } else if (propertyClass == Long.TYPE) {
                mw.visitVarInsn(22, context.var(SettingsContentProvider.LONG_TYPE));
                mw.visitInsn(9);
                mw.visitInsn(148);
                mw.visitJumpInsn(153, _end);
            } else if (propertyClass == Float.TYPE) {
                mw.visitVarInsn(23, context.var(SettingsContentProvider.FLOAT_TYPE));
                mw.visitInsn(11);
                mw.visitInsn(149);
                mw.visitJumpInsn(153, _end);
            } else if (propertyClass == Double.TYPE) {
                mw.visitVarInsn(24, context.var("double"));
                mw.visitInsn(14);
                mw.visitInsn(151);
                mw.visitJumpInsn(153, _end);
            }
            mw.visitLabel(elseLabel);
        }
    }

    private void _apply(MethodVisitor mw, FieldInfo property, Context context) {
        Class<?> propertyClass = property.fieldClass;
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, Context.fieldName);
        if (propertyClass == Byte.TYPE) {
            mw.visitVarInsn(21, context.var("byte"));
            mw.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (propertyClass == Short.TYPE) {
            mw.visitVarInsn(21, context.var("short"));
            mw.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (propertyClass == Integer.TYPE) {
            mw.visitVarInsn(21, context.var("int"));
            mw.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (propertyClass == Character.TYPE) {
            mw.visitVarInsn(21, context.var("char"));
            mw.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (propertyClass == Long.TYPE) {
            mw.visitVarInsn(22, context.var(SettingsContentProvider.LONG_TYPE, 2));
            mw.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (propertyClass == Float.TYPE) {
            mw.visitVarInsn(23, context.var(SettingsContentProvider.FLOAT_TYPE));
            mw.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (propertyClass == Double.TYPE) {
            mw.visitVarInsn(24, context.var("double", 2));
            mw.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (propertyClass == Boolean.TYPE) {
            mw.visitVarInsn(21, context.var(SettingsContentProvider.BOOLEAN_TYPE));
            mw.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (propertyClass == BigDecimal.class) {
            mw.visitVarInsn(25, context.var("decimal"));
        } else if (propertyClass == String.class) {
            mw.visitVarInsn(25, context.var(SettingsContentProvider.STRING_TYPE));
        } else if (propertyClass.isEnum()) {
            mw.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(propertyClass)) {
            mw.visitVarInsn(25, context.var("list"));
        } else {
            mw.visitVarInsn(25, context.var("object"));
        }
        mw.visitMethodInsn(182, JavaBeanSerializer, "apply", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z");
    }

    private void _processValue(MethodVisitor mw, FieldInfo fieldInfo, Context context, Label _end) {
        Label processKeyElse_ = new Label();
        Class<?> fieldClass = fieldInfo.fieldClass;
        if (fieldClass.isPrimitive()) {
            Label checkValueEnd_ = new Label();
            mw.visitVarInsn(21, context.var("checkValue"));
            mw.visitJumpInsn(154, checkValueEnd_);
            mw.visitInsn(1);
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(58, Context.processValue);
            mw.visitJumpInsn(167, processKeyElse_);
            mw.visitLabel(checkValueEnd_);
        }
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 0);
        mw.visitLdcInsn(Integer.valueOf(context.getFieldOrinal(fieldInfo.name)));
        mw.visitMethodInsn(182, JavaBeanSerializer, "getBeanContext", "(I)" + ASMUtils.desc((Class<?>) BeanContext.class));
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, Context.fieldName);
        if (fieldClass == Byte.TYPE) {
            mw.visitVarInsn(21, context.var("byte"));
            mw.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Short.TYPE) {
            mw.visitVarInsn(21, context.var("short"));
            mw.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Integer.TYPE) {
            mw.visitVarInsn(21, context.var("int"));
            mw.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Character.TYPE) {
            mw.visitVarInsn(21, context.var("char"));
            mw.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Long.TYPE) {
            mw.visitVarInsn(22, context.var(SettingsContentProvider.LONG_TYPE, 2));
            mw.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Float.TYPE) {
            mw.visitVarInsn(23, context.var(SettingsContentProvider.FLOAT_TYPE));
            mw.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Double.TYPE) {
            mw.visitVarInsn(24, context.var("double", 2));
            mw.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == Boolean.TYPE) {
            mw.visitVarInsn(21, context.var(SettingsContentProvider.BOOLEAN_TYPE));
            mw.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
            mw.visitInsn(89);
            mw.visitVarInsn(58, Context.original);
        } else if (fieldClass == BigDecimal.class) {
            mw.visitVarInsn(25, context.var("decimal"));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        } else if (fieldClass == String.class) {
            mw.visitVarInsn(25, context.var(SettingsContentProvider.STRING_TYPE));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        } else if (fieldClass.isEnum()) {
            mw.visitVarInsn(25, context.var("enum"));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        } else if (List.class.isAssignableFrom(fieldClass)) {
            mw.visitVarInsn(25, context.var("list"));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        } else {
            mw.visitVarInsn(25, context.var("object"));
            mw.visitVarInsn(58, Context.original);
            mw.visitVarInsn(25, Context.original);
        }
        mw.visitMethodInsn(182, JavaBeanSerializer, "processValue", "(L" + JSONSerializer + ";" + ASMUtils.desc((Class<?>) BeanContext.class) + "Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;");
        mw.visitVarInsn(58, Context.processValue);
        mw.visitVarInsn(25, Context.original);
        mw.visitVarInsn(25, Context.processValue);
        mw.visitJumpInsn(165, processKeyElse_);
        _writeObject(mw, fieldInfo, context, _end);
        mw.visitJumpInsn(167, _end);
        mw.visitLabel(processKeyElse_);
    }

    private void _processKey(MethodVisitor mw, FieldInfo property, Context context) {
        Label _else_processKey = new Label();
        mw.visitVarInsn(21, context.var("hasNameFilters"));
        mw.visitJumpInsn(153, _else_processKey);
        Class<?> propertyClass = property.fieldClass;
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitVarInsn(25, 2);
        mw.visitVarInsn(25, Context.fieldName);
        if (propertyClass == Byte.TYPE) {
            mw.visitVarInsn(21, context.var("byte"));
            mw.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (propertyClass == Short.TYPE) {
            mw.visitVarInsn(21, context.var("short"));
            mw.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (propertyClass == Integer.TYPE) {
            mw.visitVarInsn(21, context.var("int"));
            mw.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (propertyClass == Character.TYPE) {
            mw.visitVarInsn(21, context.var("char"));
            mw.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (propertyClass == Long.TYPE) {
            mw.visitVarInsn(22, context.var(SettingsContentProvider.LONG_TYPE, 2));
            mw.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (propertyClass == Float.TYPE) {
            mw.visitVarInsn(23, context.var(SettingsContentProvider.FLOAT_TYPE));
            mw.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (propertyClass == Double.TYPE) {
            mw.visitVarInsn(24, context.var("double", 2));
            mw.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        } else if (propertyClass == Boolean.TYPE) {
            mw.visitVarInsn(21, context.var(SettingsContentProvider.BOOLEAN_TYPE));
            mw.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (propertyClass == BigDecimal.class) {
            mw.visitVarInsn(25, context.var("decimal"));
        } else if (propertyClass == String.class) {
            mw.visitVarInsn(25, context.var(SettingsContentProvider.STRING_TYPE));
        } else if (propertyClass.isEnum()) {
            mw.visitVarInsn(25, context.var("enum"));
        } else if (List.class.isAssignableFrom(propertyClass)) {
            mw.visitVarInsn(25, context.var("list"));
        } else {
            mw.visitVarInsn(25, context.var("object"));
        }
        mw.visitMethodInsn(182, JavaBeanSerializer, "processKey", "(L" + JSONSerializer + ";Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;");
        mw.visitVarInsn(58, Context.fieldName);
        mw.visitLabel(_else_processKey);
    }

    private void _if_write_null(MethodVisitor mw, FieldInfo fieldInfo, Context context) {
        Class<?> propertyClass = fieldInfo.fieldClass;
        Label _if = new Label();
        Label _else = new Label();
        Label _write_null = new Label();
        Label _end_if = new Label();
        mw.visitLabel(_if);
        JSONField annotation = fieldInfo.getAnnotation();
        int features = 0;
        if (annotation != null) {
            features = SerializerFeature.of(annotation.serialzeFeatures());
        }
        if ((SerializerFeature.WRITE_MAP_NULL_FEATURES & features) == 0) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.WRITE_MAP_NULL_FEATURES));
            mw.visitMethodInsn(182, SerializeWriter, "isEnabled", "(I)Z");
            mw.visitJumpInsn(153, _else);
        }
        mw.visitLabel(_write_null);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitVarInsn(21, context.var("seperator"));
        mw.visitMethodInsn(182, SerializeWriter, "write", "(I)V");
        _writeFieldName(mw, context);
        mw.visitVarInsn(25, context.var("out"));
        mw.visitLdcInsn(Integer.valueOf(features));
        if (propertyClass == String.class || propertyClass == Character.class) {
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullStringAsEmpty.mask));
        } else if (Number.class.isAssignableFrom(propertyClass)) {
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullNumberAsZero.mask));
        } else if (propertyClass == Boolean.class) {
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullBooleanAsFalse.mask));
        } else if (Collection.class.isAssignableFrom(propertyClass) || propertyClass.isArray()) {
            mw.visitLdcInsn(Integer.valueOf(SerializerFeature.WriteNullListAsEmpty.mask));
        } else {
            mw.visitLdcInsn(0);
        }
        mw.visitMethodInsn(182, SerializeWriter, "writeNull", "(II)V");
        _seperator(mw, context);
        mw.visitJumpInsn(167, _end_if);
        mw.visitLabel(_else);
        mw.visitLabel(_end_if);
    }

    private void _writeFieldName(MethodVisitor mw, Context context) {
        if (context.writeDirect) {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitMethodInsn(182, SerializeWriter, "writeFieldNameDirect", "(Ljava/lang/String;)V");
        } else {
            mw.visitVarInsn(25, context.var("out"));
            mw.visitVarInsn(25, Context.fieldName);
            mw.visitInsn(3);
            mw.visitMethodInsn(182, SerializeWriter, "writeFieldName", "(Ljava/lang/String;Z)V");
        }
    }

    private void _seperator(MethodVisitor mw, Context context) {
        mw.visitVarInsn(16, 44);
        mw.visitVarInsn(54, context.var("seperator"));
    }

    private void _getListFieldItemSer(Context context, MethodVisitor mw, FieldInfo fieldInfo, Class<?> itemType) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        mw.visitJumpInsn(199, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(Type.getType(ASMUtils.desc(itemType)));
        mw.visitMethodInsn(182, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        mw.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_list_item_ser_", ObjectSerializer_desc);
    }

    private void _getFieldSer(Context context, MethodVisitor mw, FieldInfo fieldInfo) {
        Label notNull_ = new Label();
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        mw.visitJumpInsn(199, notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitVarInsn(25, 1);
        mw.visitLdcInsn(Type.getType(ASMUtils.desc(fieldInfo.fieldClass)));
        mw.visitMethodInsn(182, JSONSerializer, "getObjectWriter", "(Ljava/lang/Class;)" + ObjectSerializer_desc);
        mw.visitFieldInsn(181, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
        mw.visitLabel(notNull_);
        mw.visitVarInsn(25, 0);
        mw.visitFieldInsn(180, context.className, fieldInfo.name + "_asm_ser_", ObjectSerializer_desc);
    }
}
