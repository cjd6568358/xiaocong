package bsh;

import java.io.Serializable;
import java.util.Hashtable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class Primitive implements ParserConstants, Serializable {
    public static final Primitive NULL;
    public static final Primitive VOID;
    static Class class$bsh$Primitive;
    static Class class$java$lang$Boolean;
    static Class class$java$lang$Byte;
    static Class class$java$lang$Character;
    static Class class$java$lang$Double;
    static Class class$java$lang$Float;
    static Class class$java$lang$Integer;
    static Class class$java$lang$Long;
    static Class class$java$lang$Short;
    static Hashtable wrapperMap = new Hashtable();
    private Object value;

    private static class Special implements Serializable {
        public static final Special NULL_VALUE = new Special();
        public static final Special VOID_TYPE = new Special();

        private Special() {
        }
    }

    static {
        Class clsClass$;
        Class clsClass$2;
        Class clsClass$3;
        Class clsClass$4;
        Class clsClass$5;
        Class clsClass$6;
        Class clsClass$7;
        Class clsClass$8;
        Class clsClass$9;
        Class clsClass$10;
        Class clsClass$11;
        Class clsClass$12;
        Class clsClass$13;
        Class clsClass$14;
        Class clsClass$15;
        Class clsClass$16;
        Hashtable hashtable = wrapperMap;
        Class cls = Boolean.TYPE;
        if (class$java$lang$Boolean == null) {
            clsClass$ = class$("java.lang.Boolean");
            class$java$lang$Boolean = clsClass$;
        } else {
            clsClass$ = class$java$lang$Boolean;
        }
        hashtable.put(cls, clsClass$);
        Hashtable hashtable2 = wrapperMap;
        Class cls2 = Byte.TYPE;
        if (class$java$lang$Byte == null) {
            clsClass$2 = class$("java.lang.Byte");
            class$java$lang$Byte = clsClass$2;
        } else {
            clsClass$2 = class$java$lang$Byte;
        }
        hashtable2.put(cls2, clsClass$2);
        Hashtable hashtable3 = wrapperMap;
        Class cls3 = Short.TYPE;
        if (class$java$lang$Short == null) {
            clsClass$3 = class$("java.lang.Short");
            class$java$lang$Short = clsClass$3;
        } else {
            clsClass$3 = class$java$lang$Short;
        }
        hashtable3.put(cls3, clsClass$3);
        Hashtable hashtable4 = wrapperMap;
        Class cls4 = Character.TYPE;
        if (class$java$lang$Character == null) {
            clsClass$4 = class$("java.lang.Character");
            class$java$lang$Character = clsClass$4;
        } else {
            clsClass$4 = class$java$lang$Character;
        }
        hashtable4.put(cls4, clsClass$4);
        Hashtable hashtable5 = wrapperMap;
        Class cls5 = Integer.TYPE;
        if (class$java$lang$Integer == null) {
            clsClass$5 = class$("java.lang.Integer");
            class$java$lang$Integer = clsClass$5;
        } else {
            clsClass$5 = class$java$lang$Integer;
        }
        hashtable5.put(cls5, clsClass$5);
        Hashtable hashtable6 = wrapperMap;
        Class cls6 = Long.TYPE;
        if (class$java$lang$Long == null) {
            clsClass$6 = class$("java.lang.Long");
            class$java$lang$Long = clsClass$6;
        } else {
            clsClass$6 = class$java$lang$Long;
        }
        hashtable6.put(cls6, clsClass$6);
        Hashtable hashtable7 = wrapperMap;
        Class cls7 = Float.TYPE;
        if (class$java$lang$Float == null) {
            clsClass$7 = class$("java.lang.Float");
            class$java$lang$Float = clsClass$7;
        } else {
            clsClass$7 = class$java$lang$Float;
        }
        hashtable7.put(cls7, clsClass$7);
        Hashtable hashtable8 = wrapperMap;
        Class cls8 = Double.TYPE;
        if (class$java$lang$Double == null) {
            clsClass$8 = class$("java.lang.Double");
            class$java$lang$Double = clsClass$8;
        } else {
            clsClass$8 = class$java$lang$Double;
        }
        hashtable8.put(cls8, clsClass$8);
        Hashtable hashtable9 = wrapperMap;
        if (class$java$lang$Boolean == null) {
            clsClass$9 = class$("java.lang.Boolean");
            class$java$lang$Boolean = clsClass$9;
        } else {
            clsClass$9 = class$java$lang$Boolean;
        }
        hashtable9.put(clsClass$9, Boolean.TYPE);
        Hashtable hashtable10 = wrapperMap;
        if (class$java$lang$Byte == null) {
            clsClass$10 = class$("java.lang.Byte");
            class$java$lang$Byte = clsClass$10;
        } else {
            clsClass$10 = class$java$lang$Byte;
        }
        hashtable10.put(clsClass$10, Byte.TYPE);
        Hashtable hashtable11 = wrapperMap;
        if (class$java$lang$Short == null) {
            clsClass$11 = class$("java.lang.Short");
            class$java$lang$Short = clsClass$11;
        } else {
            clsClass$11 = class$java$lang$Short;
        }
        hashtable11.put(clsClass$11, Short.TYPE);
        Hashtable hashtable12 = wrapperMap;
        if (class$java$lang$Character == null) {
            clsClass$12 = class$("java.lang.Character");
            class$java$lang$Character = clsClass$12;
        } else {
            clsClass$12 = class$java$lang$Character;
        }
        hashtable12.put(clsClass$12, Character.TYPE);
        Hashtable hashtable13 = wrapperMap;
        if (class$java$lang$Integer == null) {
            clsClass$13 = class$("java.lang.Integer");
            class$java$lang$Integer = clsClass$13;
        } else {
            clsClass$13 = class$java$lang$Integer;
        }
        hashtable13.put(clsClass$13, Integer.TYPE);
        Hashtable hashtable14 = wrapperMap;
        if (class$java$lang$Long == null) {
            clsClass$14 = class$("java.lang.Long");
            class$java$lang$Long = clsClass$14;
        } else {
            clsClass$14 = class$java$lang$Long;
        }
        hashtable14.put(clsClass$14, Long.TYPE);
        Hashtable hashtable15 = wrapperMap;
        if (class$java$lang$Float == null) {
            clsClass$15 = class$("java.lang.Float");
            class$java$lang$Float = clsClass$15;
        } else {
            clsClass$15 = class$java$lang$Float;
        }
        hashtable15.put(clsClass$15, Float.TYPE);
        Hashtable hashtable16 = wrapperMap;
        if (class$java$lang$Double == null) {
            clsClass$16 = class$("java.lang.Double");
            class$java$lang$Double = clsClass$16;
        } else {
            clsClass$16 = class$java$lang$Double;
        }
        hashtable16.put(clsClass$16, Double.TYPE);
        NULL = new Primitive(Special.NULL_VALUE);
        VOID = new Primitive(Special.VOID_TYPE);
    }

    public Primitive(byte b) {
        this(new Byte(b));
    }

    public Primitive(char c) {
        this(new Character(c));
    }

    public Primitive(double d) {
        this(new Double(d));
    }

    public Primitive(float f) {
        this(new Float(f));
    }

    public Primitive(int i) {
        this(new Integer(i));
    }

    public Primitive(long j) {
        this(new Long(j));
    }

    public Primitive(Object obj) {
        if (obj == null) {
            throw new InterpreterError("Use Primitve.NULL instead of Primitive(null)");
        }
        if (obj != Special.NULL_VALUE && obj != Special.VOID_TYPE && !isWrapperType(obj.getClass())) {
            throw new InterpreterError(new StringBuffer().append("Not a wrapper type: ").append(obj).toString());
        }
        this.value = obj;
    }

    public Primitive(short s) {
        this(new Short(s));
    }

    public Primitive(boolean z) {
        this(new Boolean(z));
    }

    /* JADX WARN: Code duplicated, block: B:33:0x009b  */
    /* JADX WARN: Code duplicated, block: B:44:? A[RETURN, SYNTHETIC] */
    public static Object binaryOperation(Object obj, Object obj2, int i) throws UtilEvalError {
        Class<?> clsClass$;
        Class<?> clsClass$2;
        if (obj == NULL || obj2 == NULL) {
            throw new UtilEvalError("Null value or 'null' literal in binary operation");
        }
        if (obj == VOID || obj2 == VOID) {
            throw new UtilEvalError("Undefined variable, class, or 'void' literal in binary operation");
        }
        Class<?> cls = obj.getClass();
        Class<?> cls2 = obj2.getClass();
        if (obj instanceof Primitive) {
            obj = ((Primitive) obj).getValue();
        }
        if (obj2 instanceof Primitive) {
            obj2 = ((Primitive) obj2).getValue();
        }
        Object[] objArrPromotePrimitives = promotePrimitives(obj, obj2);
        Object obj3 = objArrPromotePrimitives[0];
        Object obj4 = objArrPromotePrimitives[1];
        if (obj3.getClass() != obj4.getClass()) {
            throw new UtilEvalError(new StringBuffer().append("Type mismatch in operator.  ").append(obj3.getClass()).append(" cannot be used with ").append(obj4.getClass()).toString());
        }
        try {
            Object objBinaryOperationImpl = binaryOperationImpl(obj3, obj4, i);
            if (class$bsh$Primitive == null) {
                clsClass$ = class$("bsh.Primitive");
                class$bsh$Primitive = clsClass$;
            } else {
                clsClass$ = class$bsh$Primitive;
            }
            if (cls == clsClass$) {
                if (class$bsh$Primitive == null) {
                    clsClass$2 = class$("bsh.Primitive");
                    class$bsh$Primitive = clsClass$2;
                } else {
                    clsClass$2 = class$bsh$Primitive;
                }
                if (cls2 != clsClass$2) {
                    if (!(objBinaryOperationImpl instanceof Boolean)) {
                        return objBinaryOperationImpl;
                    }
                }
            } else if (!(objBinaryOperationImpl instanceof Boolean)) {
                return objBinaryOperationImpl;
            }
            return new Primitive(objBinaryOperationImpl);
        } catch (ArithmeticException e) {
            throw new UtilTargetError("Arithemetic Exception in binary op", e);
        }
    }

    static Object binaryOperationImpl(Object obj, Object obj2, int i) throws UtilEvalError {
        if (obj instanceof Boolean) {
            return booleanBinaryOperation((Boolean) obj, (Boolean) obj2, i);
        }
        if (obj instanceof Integer) {
            return intBinaryOperation((Integer) obj, (Integer) obj2, i);
        }
        if (obj instanceof Long) {
            return longBinaryOperation((Long) obj, (Long) obj2, i);
        }
        if (obj instanceof Float) {
            return floatBinaryOperation((Float) obj, (Float) obj2, i);
        }
        if (obj instanceof Double) {
            return doubleBinaryOperation((Double) obj, (Double) obj2, i);
        }
        throw new UtilEvalError("Invalid types in binary operator");
    }

    static Boolean booleanBinaryOperation(Boolean bool, Boolean bool2, int i) {
        boolean zBooleanValue = bool.booleanValue();
        boolean zBooleanValue2 = bool2.booleanValue();
        switch (i) {
            case 90:
                return new Boolean(zBooleanValue == zBooleanValue2);
            case 91:
            case 92:
            case 93:
            case 94:
            default:
                throw new InterpreterError("unimplemented binary operator");
            case 95:
                return new Boolean(zBooleanValue != zBooleanValue2);
            case 96:
            case 97:
                return new Boolean(zBooleanValue || zBooleanValue2);
            case 98:
            case 99:
                return new Boolean(zBooleanValue && zBooleanValue2);
        }
    }

    static boolean booleanUnaryOperation(Boolean bool, int i) throws UtilEvalError {
        boolean zBooleanValue = bool.booleanValue();
        switch (i) {
            case 86:
                return !zBooleanValue;
            default:
                throw new UtilEvalError("Operator inappropriate for boolean");
        }
    }

    public static Class boxType(Class cls) {
        Class cls2 = (Class) wrapperMap.get(cls);
        if (cls2 != null) {
            return cls2;
        }
        throw new InterpreterError(new StringBuffer().append("Not a primitive type: ").append(cls).toString());
    }

    static Primitive castPrimitive(Class cls, Class cls2, Primitive primitive, boolean z, int i) throws UtilEvalError {
        if (z && primitive != null) {
            throw new InterpreterError("bad cast param 1");
        }
        if (!z && primitive == null) {
            throw new InterpreterError("bad cast param 2");
        }
        if (cls2 != null && !cls2.isPrimitive()) {
            throw new InterpreterError(new StringBuffer().append("bad fromType:").append(cls2).toString());
        }
        if (primitive == NULL && cls2 != null) {
            throw new InterpreterError("inconsistent args 1");
        }
        if (primitive == VOID && cls2 != Void.TYPE) {
            throw new InterpreterError("inconsistent args 2");
        }
        if (cls2 == Void.TYPE) {
            if (z) {
                return Types.INVALID_CAST;
            }
            throw Types.castError(Reflect.normalizeClassName(cls), "void value", i);
        }
        Object value = primitive != null ? primitive.getValue() : null;
        if (!cls.isPrimitive()) {
            if (cls2 == null) {
                return z ? Types.VALID_CAST : NULL;
            }
            if (z) {
                return Types.INVALID_CAST;
            }
            throw Types.castError(new StringBuffer().append("object type:").append(cls).toString(), "primitive value", i);
        }
        if (cls2 == null) {
            if (z) {
                return Types.INVALID_CAST;
            }
            throw Types.castError(new StringBuffer().append("primitive type:").append(cls).toString(), "Null value", i);
        }
        if (cls2 != Boolean.TYPE) {
            if (i != 1 || Types.isJavaAssignable(cls, cls2)) {
                return z ? Types.VALID_CAST : new Primitive(castWrapper(cls, value));
            }
            if (z) {
                return Types.INVALID_CAST;
            }
            throw Types.castError(cls, cls2, i);
        }
        if (cls != Boolean.TYPE) {
            if (z) {
                return Types.INVALID_CAST;
            }
            throw Types.castError(cls, cls2, i);
        }
        if (z) {
            primitive = Types.VALID_CAST;
        }
        return primitive;
    }

    static Object castWrapper(Class cls, Object obj) {
        if (!cls.isPrimitive()) {
            throw new InterpreterError(new StringBuffer().append("invalid type in castWrapper: ").append(cls).toString());
        }
        if (obj == null) {
            throw new InterpreterError("null value in castWrapper, guard");
        }
        if (obj instanceof Boolean) {
            if (cls != Boolean.TYPE) {
                throw new InterpreterError("bad wrapper cast of boolean");
            }
            return obj;
        }
        Object num = obj instanceof Character ? new Integer(((Character) obj).charValue()) : obj;
        if (!(num instanceof Number)) {
            throw new InterpreterError("bad type in cast");
        }
        Number number = (Number) num;
        if (cls == Byte.TYPE) {
            return new Byte(number.byteValue());
        }
        if (cls == Short.TYPE) {
            return new Short(number.shortValue());
        }
        if (cls == Character.TYPE) {
            return new Character((char) number.intValue());
        }
        if (cls == Integer.TYPE) {
            return new Integer(number.intValue());
        }
        if (cls == Long.TYPE) {
            return new Long(number.longValue());
        }
        if (cls == Float.TYPE) {
            return new Float(number.floatValue());
        }
        if (cls == Double.TYPE) {
            return new Double(number.doubleValue());
        }
        throw new InterpreterError("error in wrapper cast");
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    static Object doubleBinaryOperation(Double d, Double d2, int i) throws UtilEvalError {
        double dDoubleValue = d.doubleValue();
        double dDoubleValue2 = d2.doubleValue();
        switch (i) {
            case 82:
            case 83:
                return new Boolean(dDoubleValue > dDoubleValue2);
            case 84:
            case 85:
                return new Boolean(dDoubleValue < dDoubleValue2);
            case 86:
            case 87:
            case 88:
            case 89:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            default:
                throw new InterpreterError("Unimplemented binary double operator");
            case 90:
                return new Boolean(dDoubleValue == dDoubleValue2);
            case 91:
            case 92:
                return new Boolean(dDoubleValue <= dDoubleValue2);
            case 93:
            case 94:
                return new Boolean(dDoubleValue >= dDoubleValue2);
            case 95:
                return new Boolean(dDoubleValue != dDoubleValue2);
            case 102:
                return new Double(dDoubleValue + dDoubleValue2);
            case 103:
                return new Double(dDoubleValue - dDoubleValue2);
            case 104:
                return new Double(dDoubleValue * dDoubleValue2);
            case 105:
                return new Double(dDoubleValue / dDoubleValue2);
            case 111:
                return new Double(dDoubleValue % dDoubleValue2);
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
                throw new UtilEvalError("Can't shift doubles");
        }
    }

    static double doubleUnaryOperation(Double d, int i) {
        double dDoubleValue = d.doubleValue();
        switch (i) {
            case 102:
                return dDoubleValue;
            case 103:
                return -dDoubleValue;
            default:
                throw new InterpreterError("bad double unaryOperation");
        }
    }

    static Object floatBinaryOperation(Float f, Float f2, int i) throws UtilEvalError {
        float fFloatValue = f.floatValue();
        float fFloatValue2 = f2.floatValue();
        switch (i) {
            case 82:
            case 83:
                return new Boolean(fFloatValue > fFloatValue2);
            case 84:
            case 85:
                return new Boolean(fFloatValue < fFloatValue2);
            case 86:
            case 87:
            case 88:
            case 89:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            default:
                throw new InterpreterError("Unimplemented binary float operator");
            case 90:
                return new Boolean(fFloatValue == fFloatValue2);
            case 91:
            case 92:
                return new Boolean(fFloatValue <= fFloatValue2);
            case 93:
            case 94:
                return new Boolean(fFloatValue >= fFloatValue2);
            case 95:
                return new Boolean(fFloatValue != fFloatValue2);
            case 102:
                return new Float(fFloatValue + fFloatValue2);
            case 103:
                return new Float(fFloatValue - fFloatValue2);
            case 104:
                return new Float(fFloatValue * fFloatValue2);
            case 105:
                return new Float(fFloatValue / fFloatValue2);
            case 111:
                return new Float(fFloatValue % fFloatValue2);
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
                throw new UtilEvalError("Can't shift floats ");
        }
    }

    static float floatUnaryOperation(Float f, int i) {
        float fFloatValue = f.floatValue();
        switch (i) {
            case 102:
                return fFloatValue;
            case 103:
                return -fFloatValue;
            default:
                throw new InterpreterError("bad float unaryOperation");
        }
    }

    public static Primitive getDefaultValue(Class cls) {
        if (cls == null || !cls.isPrimitive()) {
            return NULL;
        }
        if (cls == Boolean.TYPE) {
            return new Primitive(false);
        }
        try {
            return new Primitive(0).castToType(cls, 0);
        } catch (UtilEvalError e) {
            throw new InterpreterError("bad cast");
        }
    }

    static Object intBinaryOperation(Integer num, Integer num2, int i) {
        int iIntValue = num.intValue();
        int iIntValue2 = num2.intValue();
        switch (i) {
            case 82:
            case 83:
                return new Boolean(iIntValue > iIntValue2);
            case 84:
            case 85:
                return new Boolean(iIntValue < iIntValue2);
            case 86:
            case 87:
            case 88:
            case 89:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            default:
                throw new InterpreterError("Unimplemented binary integer operator");
            case 90:
                return new Boolean(iIntValue == iIntValue2);
            case 91:
            case 92:
                return new Boolean(iIntValue <= iIntValue2);
            case 93:
            case 94:
                return new Boolean(iIntValue >= iIntValue2);
            case 95:
                return new Boolean(iIntValue != iIntValue2);
            case 102:
                return new Integer(iIntValue + iIntValue2);
            case 103:
                return new Integer(iIntValue - iIntValue2);
            case 104:
                return new Integer(iIntValue * iIntValue2);
            case 105:
                return new Integer(iIntValue / iIntValue2);
            case 106:
            case 107:
                return new Integer(iIntValue & iIntValue2);
            case 108:
            case 109:
                return new Integer(iIntValue | iIntValue2);
            case 110:
                return new Integer(iIntValue ^ iIntValue2);
            case 111:
                return new Integer(iIntValue % iIntValue2);
            case 112:
            case 113:
                return new Integer(iIntValue << iIntValue2);
            case 114:
            case 115:
                return new Integer(iIntValue >> iIntValue2);
            case 116:
            case 117:
                return new Integer(iIntValue >>> iIntValue2);
        }
    }

    static int intUnaryOperation(Integer num, int i) {
        int iIntValue = num.intValue();
        switch (i) {
            case 87:
                return iIntValue ^ (-1);
            case 100:
                return iIntValue + 1;
            case 101:
                return iIntValue - 1;
            case 102:
                return iIntValue;
            case 103:
                return -iIntValue;
            default:
                throw new InterpreterError("bad integer unaryOperation");
        }
    }

    public static boolean isWrapperType(Class cls) {
        return (wrapperMap.get(cls) == null || cls.isPrimitive()) ? false : true;
    }

    static Object longBinaryOperation(Long l, Long l2, int i) {
        long jLongValue = l.longValue();
        long jLongValue2 = l2.longValue();
        switch (i) {
            case 82:
            case 83:
                return new Boolean(jLongValue > jLongValue2);
            case 84:
            case 85:
                return new Boolean(jLongValue < jLongValue2);
            case 86:
            case 87:
            case 88:
            case 89:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            default:
                throw new InterpreterError("Unimplemented binary long operator");
            case 90:
                return new Boolean(jLongValue == jLongValue2);
            case 91:
            case 92:
                return new Boolean(jLongValue <= jLongValue2);
            case 93:
            case 94:
                return new Boolean(jLongValue >= jLongValue2);
            case 95:
                return new Boolean(jLongValue != jLongValue2);
            case 102:
                return new Long(jLongValue + jLongValue2);
            case 103:
                return new Long(jLongValue - jLongValue2);
            case 104:
                return new Long(jLongValue * jLongValue2);
            case 105:
                return new Long(jLongValue / jLongValue2);
            case 106:
            case 107:
                return new Long(jLongValue & jLongValue2);
            case 108:
            case 109:
                return new Long(jLongValue | jLongValue2);
            case 110:
                return new Long(jLongValue ^ jLongValue2);
            case 111:
                return new Long(jLongValue % jLongValue2);
            case 112:
            case 113:
                return new Long(jLongValue << ((int) jLongValue2));
            case 114:
            case 115:
                return new Long(jLongValue >> ((int) jLongValue2));
            case 116:
            case 117:
                return new Long(jLongValue >>> ((int) jLongValue2));
        }
    }

    static long longUnaryOperation(Long l, int i) {
        long jLongValue = l.longValue();
        switch (i) {
            case 87:
                return jLongValue ^ (-1);
            case 100:
                return jLongValue + 1;
            case 101:
                return jLongValue - 1;
            case 102:
                return jLongValue;
            case 103:
                return -jLongValue;
            default:
                throw new InterpreterError("bad long unaryOperation");
        }
    }

    static Object[] promotePrimitives(Object obj, Object obj2) {
        Object objPromoteToInteger = promoteToInteger(obj);
        Object objPromoteToInteger2 = promoteToInteger(obj2);
        if ((objPromoteToInteger instanceof Number) && (objPromoteToInteger2 instanceof Number)) {
            Number number = (Number) objPromoteToInteger;
            Number number2 = (Number) objPromoteToInteger2;
            boolean z = number instanceof Double;
            if (!z && !(number2 instanceof Double)) {
                boolean z2 = number instanceof Float;
                if (!z2 && !(number2 instanceof Float)) {
                    boolean z3 = number instanceof Long;
                    if (z3 || (number2 instanceof Long)) {
                        if (z3) {
                            objPromoteToInteger2 = new Long(number2.longValue());
                        } else {
                            objPromoteToInteger = new Long(number.longValue());
                        }
                    }
                } else if (z2) {
                    objPromoteToInteger2 = new Float(number2.floatValue());
                } else {
                    objPromoteToInteger = new Float(number.floatValue());
                }
            } else if (z) {
                objPromoteToInteger2 = new Double(number2.doubleValue());
            } else {
                objPromoteToInteger = new Double(number.doubleValue());
            }
        }
        return new Object[]{objPromoteToInteger, objPromoteToInteger2};
    }

    static Object promoteToInteger(Object obj) {
        if (obj instanceof Character) {
            return new Integer(((Character) obj).charValue());
        }
        return ((obj instanceof Byte) || (obj instanceof Short)) ? new Integer(((Number) obj).intValue()) : obj;
    }

    public static Primitive unaryOperation(Primitive primitive, int i) throws UtilEvalError {
        if (primitive == NULL) {
            throw new UtilEvalError("illegal use of null object or 'null' literal");
        }
        if (primitive == VOID) {
            throw new UtilEvalError("illegal use of undefined object or 'void' literal");
        }
        Class type = primitive.getType();
        Object objPromoteToInteger = promoteToInteger(primitive.getValue());
        if (objPromoteToInteger instanceof Boolean) {
            return new Primitive(booleanUnaryOperation((Boolean) objPromoteToInteger, i));
        }
        if (!(objPromoteToInteger instanceof Integer)) {
            if (objPromoteToInteger instanceof Long) {
                return new Primitive(longUnaryOperation((Long) objPromoteToInteger, i));
            }
            if (objPromoteToInteger instanceof Float) {
                return new Primitive(floatUnaryOperation((Float) objPromoteToInteger, i));
            }
            if (objPromoteToInteger instanceof Double) {
                return new Primitive(doubleUnaryOperation((Double) objPromoteToInteger, i));
            }
            throw new InterpreterError("An error occurred.  Please call technical support.");
        }
        int iIntUnaryOperation = intUnaryOperation((Integer) objPromoteToInteger, i);
        if (i == 100 || i == 101) {
            if (type == Byte.TYPE) {
                return new Primitive((byte) iIntUnaryOperation);
            }
            if (type == Short.TYPE) {
                return new Primitive((short) iIntUnaryOperation);
            }
            if (type == Character.TYPE) {
                return new Primitive((char) iIntUnaryOperation);
            }
        }
        return new Primitive(iIntUnaryOperation);
    }

    public static Class unboxType(Class cls) {
        Class cls2 = (Class) wrapperMap.get(cls);
        if (cls2 != null) {
            return cls2;
        }
        throw new InterpreterError(new StringBuffer().append("Not a primitive wrapper type: ").append(cls).toString());
    }

    public static Object unwrap(Object obj) {
        if (obj == VOID) {
            return null;
        }
        return obj instanceof Primitive ? ((Primitive) obj).getValue() : obj;
    }

    public static Object[] unwrap(Object[] objArr) {
        Object[] objArr2 = new Object[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            objArr2[i] = unwrap(objArr[i]);
        }
        return objArr2;
    }

    public static Object wrap(Object obj, Class cls) {
        if (cls == Void.TYPE) {
            return VOID;
        }
        if (obj == null) {
            return NULL;
        }
        return cls.isPrimitive() ? new Primitive(obj) : obj;
    }

    public static Object[] wrap(Object[] objArr, Class[] clsArr) {
        if (objArr == null) {
            return null;
        }
        Object[] objArr2 = new Object[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            objArr2[i] = wrap(objArr[i], clsArr[i]);
        }
        return objArr2;
    }

    public boolean booleanValue() throws UtilEvalError {
        if (this.value instanceof Boolean) {
            return ((Boolean) this.value).booleanValue();
        }
        throw new UtilEvalError("Primitive not a boolean");
    }

    public Primitive castToType(Class cls, int i) throws UtilEvalError {
        return castPrimitive(cls, getType(), this, false, i);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Primitive) {
            return ((Primitive) obj).value.equals(this.value);
        }
        return false;
    }

    public Class getType() {
        if (this == VOID) {
            return Void.TYPE;
        }
        if (this == NULL) {
            return null;
        }
        return unboxType(this.value.getClass());
    }

    public Object getValue() {
        if (this.value == Special.NULL_VALUE) {
            return null;
        }
        if (this.value == Special.VOID_TYPE) {
            throw new InterpreterError("attempt to unwrap void type");
        }
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode() * 21;
    }

    public int intValue() throws UtilEvalError {
        if (this.value instanceof Number) {
            return ((Number) this.value).intValue();
        }
        throw new UtilEvalError("Primitive not a number");
    }

    public boolean isNumber() {
        return ((this.value instanceof Boolean) || this == NULL || this == VOID) ? false : true;
    }

    public Number numberValue() throws UtilEvalError {
        Object num = this.value;
        if (num instanceof Character) {
            num = new Integer(((Character) num).charValue());
        }
        if (num instanceof Number) {
            return (Number) num;
        }
        throw new UtilEvalError("Primitive not a number");
    }

    public String toString() {
        if (this.value == Special.NULL_VALUE) {
            return "null";
        }
        return this.value == Special.VOID_TYPE ? "void" : this.value.toString();
    }
}
