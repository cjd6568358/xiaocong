package bsh;

import com.tencent.android.tpush.common.Constants;
import java.io.Serializable;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class LHS implements ParserConstants, Serializable {
    static final int FIELD = 1;
    static final int INDEX = 3;
    static final int METHOD_EVAL = 4;
    static final int PROPERTY = 2;
    static final int VARIABLE = 0;
    Field field;
    int index;
    boolean localVar;
    NameSpace nameSpace;
    Object object;
    String propName;
    int type;
    String varName;

    LHS(NameSpace nameSpace, String str) {
        throw new Error("namespace lhs");
    }

    LHS(NameSpace nameSpace, String str, boolean z) {
        this.type = 0;
        this.localVar = z;
        this.varName = str;
        this.nameSpace = nameSpace;
    }

    LHS(Object obj, int i) {
        if (obj == null) {
            throw new NullPointerException("constructed empty LHS");
        }
        this.type = 3;
        this.object = obj;
        this.index = i;
    }

    LHS(Object obj, String str) {
        if (obj == null) {
            throw new NullPointerException("constructed empty LHS");
        }
        this.type = 2;
        this.object = obj;
        this.propName = str;
    }

    LHS(Object obj, Field field) {
        if (obj == null) {
            throw new NullPointerException("constructed empty LHS");
        }
        this.type = 1;
        this.object = obj;
        this.field = field;
    }

    LHS(Field field) {
        this.type = 1;
        this.object = null;
        this.field = field;
    }

    public Object assign(Object obj, boolean z) throws UtilEvalError {
        if (this.type == 0) {
            if (this.localVar) {
                this.nameSpace.setLocalVariable(this.varName, obj, z);
            } else {
                this.nameSpace.setVariable(this.varName, obj, z);
            }
        } else if (this.type == 1) {
            try {
                Object value = obj instanceof Primitive ? ((Primitive) obj).getValue() : obj;
                ReflectManager.RMSetAccessible(this.field);
                this.field.set(this.object, value);
            } catch (IllegalAccessException e) {
                throw new UtilEvalError(new StringBuffer().append("LHS (").append(this.field.getName()).append(") can't access field: ").append(e).toString());
            } catch (IllegalArgumentException e2) {
                String name = obj instanceof Primitive ? ((Primitive) obj).getType().getName() : obj.getClass().getName();
                StringBuffer stringBufferAppend = new StringBuffer().append("Argument type mismatch. ");
                if (obj == null) {
                    name = "null";
                }
                throw new UtilEvalError(stringBufferAppend.append(name).append(" not assignable to field ").append(this.field.getName()).toString());
            } catch (NullPointerException e3) {
                throw new UtilEvalError(new StringBuffer().append("LHS (").append(this.field.getName()).append(") not a static field.").toString());
            }
        } else if (this.type == 2) {
            CollectionManager collectionManager = CollectionManager.getCollectionManager();
            if (collectionManager.isMap(this.object)) {
                collectionManager.putInMap(this.object, this.propName, obj);
            } else {
                try {
                    Reflect.setObjectProperty(this.object, this.propName, obj);
                } catch (ReflectError e4) {
                    Interpreter.debug(new StringBuffer().append("Assignment: ").append(e4.getMessage()).toString());
                    throw new UtilEvalError(new StringBuffer().append("No such property: ").append(this.propName).toString());
                }
            }
        } else {
            if (this.type != 3) {
                throw new InterpreterError("unknown lhs");
            }
            try {
                Reflect.setIndex(this.object, this.index, obj);
            } catch (UtilTargetError e5) {
                throw e5;
            } catch (Exception e6) {
                throw new UtilEvalError(new StringBuffer().append("Assignment: ").append(e6.getMessage()).toString());
            }
        }
        return obj;
    }

    public Object getValue() throws UtilEvalError {
        if (this.type == 0) {
            return this.nameSpace.getVariable(this.varName);
        }
        if (this.type == 1) {
            try {
                return Primitive.wrap(this.field.get(this.object), this.field.getType());
            } catch (IllegalAccessException e) {
                throw new UtilEvalError(new StringBuffer().append("Can't read field: ").append(this.field).toString());
            }
        }
        if (this.type == 2) {
            try {
                return Reflect.getObjectProperty(this.object, this.propName);
            } catch (ReflectError e2) {
                Interpreter.debug(e2.getMessage());
                throw new UtilEvalError(new StringBuffer().append("No such property: ").append(this.propName).toString());
            }
        }
        if (this.type != 3) {
            throw new InterpreterError("LHS type");
        }
        try {
            return Reflect.getIndex(this.object, this.index);
        } catch (Exception e3) {
            throw new UtilEvalError(new StringBuffer().append("Array access: ").append(e3).toString());
        }
    }

    public String toString() {
        return new StringBuffer().append("LHS: ").append(this.field != null ? new StringBuffer().append("field = ").append(this.field.toString()).toString() : Constants.MAIN_VERSION_TAG).append(this.varName != null ? new StringBuffer().append(" varName = ").append(this.varName).toString() : Constants.MAIN_VERSION_TAG).append(this.nameSpace != null ? new StringBuffer().append(" nameSpace = ").append(this.nameSpace.toString()).toString() : Constants.MAIN_VERSION_TAG).toString();
    }
}
