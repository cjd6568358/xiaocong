package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class Types {
    static final int ASSIGNMENT = 1;
    static final int BSH_ASSIGNABLE = 4;
    static final int CAST = 0;
    static final int FIRST_ROUND_ASSIGNABLE = 1;
    static final int JAVA_BASE_ASSIGNABLE = 1;
    static final int JAVA_BOX_TYPES_ASSIGABLE = 2;
    static final int JAVA_VARARGS_ASSIGNABLE = 3;
    static final int LAST_ROUND_ASSIGNABLE = 4;
    static Class class$bsh$Primitive;
    static Class class$bsh$This;
    static Class class$java$lang$Number;
    static Class class$java$lang$Object;
    static Primitive VALID_CAST = new Primitive(1);
    static Primitive INVALID_CAST = new Primitive(-1);

    Types() {
    }

    static UtilEvalError castError(Class cls, Class cls2, int i) {
        return castError(Reflect.normalizeClassName(cls), Reflect.normalizeClassName(cls2), i);
    }

    static UtilEvalError castError(String str, String str2, int i) {
        return i == 1 ? new UtilEvalError(new StringBuffer().append("Can't assign ").append(str2).append(" to ").append(str).toString()) : new UtilTargetError(new ClassCastException(new StringBuffer().append("Cannot cast ").append(str2).append(" to ").append(str).toString()));
    }

    private static Object castObject(Class cls, Class cls2, Object obj, int i, boolean z) throws UtilEvalError {
        Class clsClass$;
        Class clsClass$2;
        Class clsClass$3;
        if (z && obj != null) {
            throw new InterpreterError("bad cast params 1");
        }
        if (!z && obj == null) {
            throw new InterpreterError("bad cast params 2");
        }
        if (class$bsh$Primitive == null) {
            clsClass$ = class$("bsh.Primitive");
            class$bsh$Primitive = clsClass$;
        } else {
            clsClass$ = class$bsh$Primitive;
        }
        if (cls2 == clsClass$) {
            throw new InterpreterError("bad from Type, need to unwrap");
        }
        if (obj == Primitive.NULL && cls2 != null) {
            throw new InterpreterError("inconsistent args 1");
        }
        if (obj == Primitive.VOID && cls2 != Void.TYPE) {
            throw new InterpreterError("inconsistent args 2");
        }
        if (cls == Void.TYPE) {
            throw new InterpreterError("loose toType should be null");
        }
        if (cls == null || cls == cls2) {
            if (z) {
                obj = VALID_CAST;
            }
            return obj;
        }
        if (cls.isPrimitive()) {
            if (cls2 == Void.TYPE || cls2 == null || cls2.isPrimitive()) {
                return Primitive.castPrimitive(cls, cls2, (Primitive) obj, z, i);
            }
            if (Primitive.isWrapperType(cls2)) {
                Class clsUnboxType = Primitive.unboxType(cls2);
                return Primitive.castPrimitive(cls, clsUnboxType, z ? null : (Primitive) Primitive.wrap(obj, clsUnboxType), z, i);
            }
            if (z) {
                return INVALID_CAST;
            }
            throw castError(cls, cls2, i);
        }
        if (cls2 == Void.TYPE || cls2 == null || cls2.isPrimitive()) {
            if (Primitive.isWrapperType(cls) && cls2 != Void.TYPE && cls2 != null) {
                return z ? VALID_CAST : Primitive.castWrapper(Primitive.unboxType(cls), ((Primitive) obj).getValue());
            }
            if (class$java$lang$Object == null) {
                clsClass$2 = class$("java.lang.Object");
                class$java$lang$Object = clsClass$2;
            } else {
                clsClass$2 = class$java$lang$Object;
            }
            if (cls != clsClass$2 || cls2 == Void.TYPE || cls2 == null) {
                return Primitive.castPrimitive(cls, cls2, (Primitive) obj, z, i);
            }
            return z ? VALID_CAST : ((Primitive) obj).getValue();
        }
        if (cls.isAssignableFrom(cls2)) {
            if (z) {
                obj = VALID_CAST;
            }
            return obj;
        }
        if (cls.isInterface()) {
            if (class$bsh$This == null) {
                clsClass$3 = class$("bsh.This");
                class$bsh$This = clsClass$3;
            } else {
                clsClass$3 = class$bsh$This;
            }
            if (clsClass$3.isAssignableFrom(cls2) && Capabilities.canGenerateInterfaces()) {
                return z ? VALID_CAST : ((This) obj).getInterface(cls);
            }
        }
        if (Primitive.isWrapperType(cls) && Primitive.isWrapperType(cls2)) {
            return z ? VALID_CAST : Primitive.castWrapper(cls, obj);
        }
        if (z) {
            return INVALID_CAST;
        }
        throw castError(cls, cls2, i);
    }

    public static Object castObject(Object obj, Class cls, int i) throws UtilEvalError {
        if (obj == null) {
            throw new InterpreterError("null fromValue");
        }
        return castObject(cls, obj instanceof Primitive ? ((Primitive) obj).getType() : obj.getClass(), obj, i, false);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static Class[] getTypes(Object[] objArr) {
        int i = 0;
        if (objArr == null) {
            return new Class[0];
        }
        Class[] clsArr = new Class[objArr.length];
        while (true) {
            int i2 = i;
            if (i2 >= objArr.length) {
                return clsArr;
            }
            if (objArr[i2] == null) {
                clsArr[i2] = null;
            } else if (objArr[i2] instanceof Primitive) {
                clsArr[i2] = ((Primitive) objArr[i2]).getType();
            } else {
                clsArr[i2] = objArr[i2].getClass();
            }
            i = i2 + 1;
        }
    }

    static boolean isBshAssignable(Class cls, Class cls2) {
        try {
            return castObject(cls, cls2, null, 1, true) == VALID_CAST;
        } catch (UtilEvalError e) {
            throw new InterpreterError(new StringBuffer().append("err in cast check: ").append(e).toString());
        }
    }

    static boolean isJavaAssignable(Class cls, Class cls2) {
        return isJavaBaseAssignable(cls, cls2) || isJavaBoxTypesAssignable(cls, cls2);
    }

    static boolean isJavaBaseAssignable(Class cls, Class cls2) {
        if (cls == null) {
            return false;
        }
        if (cls2 == null) {
            return !cls.isPrimitive();
        }
        if (cls.isPrimitive() && cls2.isPrimitive()) {
            if (cls == cls2) {
                return true;
            }
            if (cls2 == Byte.TYPE && (cls == Short.TYPE || cls == Integer.TYPE || cls == Long.TYPE || cls == Float.TYPE || cls == Double.TYPE)) {
                return true;
            }
            if (cls2 == Short.TYPE && (cls == Integer.TYPE || cls == Long.TYPE || cls == Float.TYPE || cls == Double.TYPE)) {
                return true;
            }
            if (cls2 == Character.TYPE && (cls == Integer.TYPE || cls == Long.TYPE || cls == Float.TYPE || cls == Double.TYPE)) {
                return true;
            }
            if (cls2 == Integer.TYPE && (cls == Long.TYPE || cls == Float.TYPE || cls == Double.TYPE)) {
                return true;
            }
            if (cls2 == Long.TYPE && (cls == Float.TYPE || cls == Double.TYPE)) {
                return true;
            }
            if (cls2 == Float.TYPE && cls == Double.TYPE) {
                return true;
            }
        } else if (cls.isAssignableFrom(cls2)) {
            return true;
        }
        return false;
    }

    static boolean isJavaBoxTypesAssignable(Class cls, Class cls2) {
        Class clsClass$;
        Class clsClass$2;
        if (cls == null) {
            return false;
        }
        if (class$java$lang$Object == null) {
            clsClass$ = class$("java.lang.Object");
            class$java$lang$Object = clsClass$;
        } else {
            clsClass$ = class$java$lang$Object;
        }
        if (cls == clsClass$) {
            return true;
        }
        if (class$java$lang$Number == null) {
            clsClass$2 = class$("java.lang.Number");
            class$java$lang$Number = clsClass$2;
        } else {
            clsClass$2 = class$java$lang$Number;
        }
        return !(cls != clsClass$2 || cls2 == Character.TYPE || cls2 == Boolean.TYPE) || Primitive.wrapperMap.get(cls) == cls2;
    }

    static boolean isSignatureAssignable(Class[] clsArr, Class[] clsArr2, int i) {
        if (i != 3 && clsArr.length != clsArr2.length) {
            return false;
        }
        switch (i) {
            case 1:
                for (int i2 = 0; i2 < clsArr.length; i2++) {
                    if (!isJavaBaseAssignable(clsArr2[i2], clsArr[i2])) {
                        return false;
                    }
                }
                return true;
            case 2:
                for (int i3 = 0; i3 < clsArr.length; i3++) {
                    if (!isJavaBoxTypesAssignable(clsArr2[i3], clsArr[i3])) {
                        return false;
                    }
                }
                return true;
            case 3:
                return isSignatureVarargsAssignable(clsArr, clsArr2);
            case 4:
                for (int i4 = 0; i4 < clsArr.length; i4++) {
                    if (!isBshAssignable(clsArr2[i4], clsArr[i4])) {
                        return false;
                    }
                }
                return true;
            default:
                throw new InterpreterError("bad case");
        }
    }

    private static boolean isSignatureVarargsAssignable(Class[] clsArr, Class[] clsArr2) {
        return false;
    }
}
