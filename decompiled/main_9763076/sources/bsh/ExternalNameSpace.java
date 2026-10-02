package bsh;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ExternalNameSpace extends NameSpace {
    private Map externalMap;

    public ExternalNameSpace() {
        this(null, "External Map Namespace", null);
    }

    public ExternalNameSpace(NameSpace nameSpace, String str, Map map) {
        super(nameSpace, str);
        this.externalMap = map == null ? new HashMap() : map;
    }

    @Override // bsh.NameSpace
    public void clear() {
        super.clear();
        this.externalMap.clear();
    }

    @Override // bsh.NameSpace
    protected void getAllNamesAux(Vector vector) {
        super.getAllNamesAux(vector);
    }

    @Override // bsh.NameSpace
    public Variable[] getDeclaredVariables() {
        return super.getDeclaredVariables();
    }

    public Map getMap() {
        return this.externalMap;
    }

    @Override // bsh.NameSpace
    public BshMethod getMethod(String str, Class[] clsArr, boolean z) throws UtilEvalError {
        return super.getMethod(str, clsArr, z);
    }

    @Override // bsh.NameSpace
    protected Variable getVariableImpl(String str, boolean z) throws UtilEvalError {
        Object obj = this.externalMap.get(str);
        if (obj == null) {
            super.unsetVariable(str);
            return super.getVariableImpl(str, z);
        }
        Variable variableImpl = super.getVariableImpl(str, false);
        return variableImpl == null ? new Variable(str, (Class) null, obj, (Modifiers) null) : variableImpl;
    }

    @Override // bsh.NameSpace
    public String[] getVariableNames() {
        HashSet hashSet = new HashSet();
        hashSet.addAll(Arrays.asList(super.getVariableNames()));
        hashSet.addAll(this.externalMap.keySet());
        return (String[]) hashSet.toArray(new String[0]);
    }

    protected void putExternalMap(String str, Object obj) {
        Object objUnwrapVariable;
        if (obj instanceof Variable) {
            try {
                objUnwrapVariable = unwrapVariable((Variable) obj);
            } catch (UtilEvalError e) {
                throw new InterpreterError("unexpected UtilEvalError");
            }
        } else {
            objUnwrapVariable = obj;
        }
        if (objUnwrapVariable instanceof Primitive) {
            objUnwrapVariable = Primitive.unwrap((Primitive) objUnwrapVariable);
        }
        this.externalMap.put(str, objUnwrapVariable);
    }

    public void setMap(Map map) {
        this.externalMap = null;
        clear();
        this.externalMap = map;
    }

    @Override // bsh.NameSpace
    public void setMethod(String str, BshMethod bshMethod) throws UtilEvalError {
        super.setMethod(str, bshMethod);
    }

    @Override // bsh.NameSpace
    public void setTypedVariable(String str, Class cls, Object obj, Modifiers modifiers) throws UtilEvalError {
        super.setTypedVariable(str, cls, obj, modifiers);
        putExternalMap(str, obj);
    }

    @Override // bsh.NameSpace
    void setVariable(String str, Object obj, boolean z, boolean z2) throws UtilEvalError {
        super.setVariable(str, obj, z, z2);
        putExternalMap(str, obj);
    }

    @Override // bsh.NameSpace
    public void unsetVariable(String str) {
        super.unsetVariable(str);
        this.externalMap.remove(str);
    }
}
