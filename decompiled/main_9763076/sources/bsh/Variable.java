package bsh;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.io.Serializable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Variable implements Serializable {
    static final int ASSIGNMENT = 1;
    static final int DECLARATION = 0;
    LHS lhs;
    Modifiers modifiers;
    String name;
    Class type;
    String typeDescriptor;
    Object value;

    Variable(String str, Class cls, LHS lhs) {
        this.type = null;
        this.name = str;
        this.lhs = lhs;
        this.type = cls;
    }

    Variable(String str, Class cls, Object obj, Modifiers modifiers) throws UtilEvalError {
        this.type = null;
        this.name = str;
        this.type = cls;
        this.modifiers = modifiers;
        setValue(obj, 0);
    }

    Variable(String str, Object obj, Modifiers modifiers) throws UtilEvalError {
        this(str, (Class) null, obj, modifiers);
    }

    Variable(String str, String str2, Object obj, Modifiers modifiers) throws UtilEvalError {
        this(str, (Class) null, obj, modifiers);
        this.typeDescriptor = str2;
    }

    public Modifiers getModifiers() {
        return this.modifiers;
    }

    public String getName() {
        return this.name;
    }

    public Class getType() {
        return this.type;
    }

    public String getTypeDescriptor() {
        return this.typeDescriptor;
    }

    Object getValue() throws UtilEvalError {
        return this.lhs != null ? this.lhs.getValue() : this.value;
    }

    public boolean hasModifier(String str) {
        return this.modifiers != null && this.modifiers.hasModifier(str);
    }

    public void setValue(Object obj, int i) throws UtilEvalError {
        if (hasModifier("final") && this.value != null) {
            throw new UtilEvalError("Final variable, can't re-assign.");
        }
        if (obj == null) {
            obj = Primitive.getDefaultValue(this.type);
        }
        if (this.lhs != null) {
            this.lhs.assign(obj, false);
            return;
        }
        if (this.type != null) {
            obj = Types.castObject(obj, this.type, i != 0 ? 1 : 0);
        }
        this.value = obj;
    }

    public String toString() {
        return new StringBuffer().append("Variable: ").append(super.toString()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(this.name).append(", type:").append(this.type).append(", value:").append(this.value).append(", lhs = ").append(this.lhs).toString();
    }
}
