package bsh;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHType extends SimpleNode implements BshClassManager.Listener {
    private int arrayDims;
    private Class baseType;
    String descriptor;
    private Class type;

    BSHType(int i) {
        super(i);
    }

    public static String getTypeDescriptor(Class cls) {
        if (cls == Boolean.TYPE) {
            return "Z";
        }
        if (cls == Character.TYPE) {
            return "C";
        }
        if (cls == Byte.TYPE) {
            return "B";
        }
        if (cls == Short.TYPE) {
            return "S";
        }
        if (cls == Integer.TYPE) {
            return "I";
        }
        if (cls == Long.TYPE) {
            return "J";
        }
        if (cls == Float.TYPE) {
            return "F";
        }
        if (cls == Double.TYPE) {
            return "D";
        }
        if (cls == Void.TYPE) {
            return "V";
        }
        String strReplace = cls.getName().replace('.', '/');
        return (strReplace.startsWith("[") || strReplace.endsWith(";")) ? strReplace : new StringBuffer().append("L").append(strReplace.replace('.', '/')).append(";").toString();
    }

    public void addArrayDimension() {
        this.arrayDims++;
    }

    @Override // bsh.BshClassManager.Listener
    public void classLoaderChanged() {
        this.type = null;
        this.baseType = null;
    }

    public int getArrayDims() {
        return this.arrayDims;
    }

    public Class getBaseType() {
        return this.baseType;
    }

    public Class getType(CallStack callStack, Interpreter interpreter) throws EvalError {
        if (this.type != null) {
            return this.type;
        }
        SimpleNode typeNode = getTypeNode();
        if (typeNode instanceof BSHPrimitiveType) {
            this.baseType = ((BSHPrimitiveType) typeNode).getType();
        } else {
            this.baseType = ((BSHAmbiguousName) typeNode).toClass(callStack, interpreter);
        }
        if (this.arrayDims > 0) {
            try {
                this.type = Array.newInstance((Class<?>) this.baseType, new int[this.arrayDims]).getClass();
            } catch (Exception e) {
                throw new EvalError("Couldn't construct array type", this, callStack);
            }
        } else {
            this.type = this.baseType;
        }
        interpreter.getClassManager().addListener(this);
        return this.type;
    }

    public String getTypeDescriptor(CallStack callStack, Interpreter interpreter, String str) {
        Class cls;
        String str2;
        String typeDescriptor;
        if (this.descriptor != null) {
            return this.descriptor;
        }
        SimpleNode typeNode = getTypeNode();
        if (typeNode instanceof BSHPrimitiveType) {
            typeDescriptor = getTypeDescriptor(((BSHPrimitiveType) typeNode).type);
        } else {
            String str3 = ((BSHAmbiguousName) typeNode).text;
            String classBeingDefined = interpreter.getClassManager().getClassBeingDefined(str3);
            if (classBeingDefined == null) {
                try {
                    cls = ((BSHAmbiguousName) typeNode).toClass(callStack, interpreter);
                    str2 = str3;
                } catch (EvalError e) {
                    cls = null;
                    str2 = str3;
                }
            } else {
                cls = null;
                str2 = classBeingDefined;
            }
            typeDescriptor = cls != null ? getTypeDescriptor(cls) : (str == null || Name.isCompound(str2)) ? new StringBuffer().append("L").append(str2.replace('.', '/')).append(";").toString() : new StringBuffer().append("L").append(str.replace('.', '/')).append("/").append(str2).append(";").toString();
        }
        String string = typeDescriptor;
        for (int i = 0; i < this.arrayDims; i++) {
            string = new StringBuffer().append("[").append(string).toString();
        }
        this.descriptor = string;
        return string;
    }

    SimpleNode getTypeNode() {
        return (SimpleNode) jjtGetChild(0);
    }
}
