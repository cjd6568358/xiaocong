package bsh;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ClassGenerator {
    private static ClassGenerator cg;

    public static ClassGenerator getClassGenerator() throws UtilEvalError {
        if (cg == null) {
            try {
                cg = (ClassGenerator) Class.forName("bsh.ClassGeneratorImpl").newInstance();
            } catch (Exception e) {
                throw new Capabilities.Unavailable(new StringBuffer().append("ClassGenerator unavailable: ").append(e).toString());
            }
        }
        return cg;
    }

    public abstract Class generateClass(String str, Modifiers modifiers, Class[] clsArr, Class cls, BSHBlock bSHBlock, boolean z, CallStack callStack, Interpreter interpreter) throws EvalError;

    public abstract Object invokeSuperclassMethod(BshClassManager bshClassManager, Object obj, String str, Object[] objArr) throws ReflectError, UtilEvalError, InvocationTargetException;

    public abstract void setInstanceNameSpaceParent(Object obj, String str, NameSpace nameSpace);
}
