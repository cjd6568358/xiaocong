package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BlockNameSpace extends NameSpace {
    public BlockNameSpace(NameSpace nameSpace) throws EvalError {
        super(nameSpace, new StringBuffer().append(nameSpace.getName()).append("/BlockNameSpace").toString());
    }

    private NameSpace getNonBlockParent() {
        NameSpace parent = super.getParent();
        return parent instanceof BlockNameSpace ? ((BlockNameSpace) parent).getNonBlockParent() : parent;
    }

    private boolean weHaveVar(String str) {
        try {
            return super.getVariableImpl(str, false) != null;
        } catch (UtilEvalError e) {
            return false;
        }
    }

    @Override // bsh.NameSpace
    public This getSuper(Interpreter interpreter) {
        return getNonBlockParent().getSuper(interpreter);
    }

    @Override // bsh.NameSpace
    This getThis(Interpreter interpreter) {
        return getNonBlockParent().getThis(interpreter);
    }

    @Override // bsh.NameSpace
    public void importClass(String str) {
        getParent().importClass(str);
    }

    @Override // bsh.NameSpace
    public void importPackage(String str) {
        getParent().importPackage(str);
    }

    public void setBlockVariable(String str, Object obj) throws UtilEvalError {
        super.setVariable(str, obj, false, false);
    }

    @Override // bsh.NameSpace
    public void setMethod(String str, BshMethod bshMethod) throws UtilEvalError {
        getParent().setMethod(str, bshMethod);
    }

    @Override // bsh.NameSpace
    public void setVariable(String str, Object obj, boolean z, boolean z2) throws UtilEvalError {
        if (weHaveVar(str)) {
            super.setVariable(str, obj, z, false);
        } else {
            getParent().setVariable(str, obj, z, z2);
        }
    }
}
