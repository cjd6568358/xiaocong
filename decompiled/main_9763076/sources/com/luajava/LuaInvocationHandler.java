package com.luajava;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LuaInvocationHandler implements InvocationHandler {
    private LuaState L;
    private LuaObject obj;
    private LuaObject print;

    public LuaInvocationHandler(LuaObject luaObject) {
        this.obj = luaObject;
        this.L = luaObject.L;
        this.print = this.L.getLuaObject("print");
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0089 A[PHI: r2 r4
  0x0089: PHI (r2v10 java.lang.Object) = (r2v5 java.lang.Object), (r2v11 java.lang.Object), (r2v11 java.lang.Object) binds: [B:33:0x0063, B:26:0x004f, B:28:0x0053] A[DONT_GENERATE, DONT_INLINE]
  0x0089: PHI (r4v7 ??) = (r4v8 ??), (r4v9 ??), (r4v10 ??) binds: [B:33:0x0063, B:26:0x004f, B:28:0x0053] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0089 -> B:14:0x002f). Please report as a decompilation issue!!! */
    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws LuaException {
        Object objCall;
        LuaException e;
        ?? r4;
        Object objConvertLuaNumber = null;
        synchronized (this.obj.L) {
            String name = method.getName();
            LuaObject field = this.obj.getField(name);
            if (!field.isNil()) {
                Class<?> returnType = method.getReturnType();
                try {
                    if (returnType.equals(Void.class) || returnType.equals(Void.TYPE)) {
                        field.call(objArr);
                        name = name;
                    } else {
                        objCall = field.call(objArr);
                        if (objCall != null) {
                            try {
                                r4 = name;
                                r4 = name;
                                if (objCall instanceof Double) {
                                    objConvertLuaNumber = LuaState.convertLuaNumber((Double) objCall, returnType);
                                    name = name;
                                } else {
                                    r4 = name;
                                    objConvertLuaNumber = objCall;
                                    name = r4;
                                }
                            } catch (LuaException e2) {
                                e = e2;
                                LuaObject luaObject = this.print;
                                StringBuilder sbAppend = new StringBuilder(String.valueOf(name)).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                                luaObject.call(sbAppend.append(e.getMessage()).toString());
                                r4 = sbAppend;
                            }
                        } else {
                            r4 = name;
                            objConvertLuaNumber = objCall;
                            name = r4;
                        }
                    }
                } catch (LuaException e3) {
                    objCall = objConvertLuaNumber;
                    e = e3;
                }
                if (objConvertLuaNumber == null && (returnType.equals(Boolean.TYPE) || returnType.equals(Boolean.class))) {
                    objConvertLuaNumber = false;
                }
            }
        }
        return objConvertLuaNumber;
    }
}
