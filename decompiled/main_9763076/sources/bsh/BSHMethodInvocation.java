package bsh;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHMethodInvocation extends SimpleNode {
    BSHMethodInvocation(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        NameSpace pVar = callStack.top();
        BSHAmbiguousName nameNode = getNameNode();
        if (pVar.getParent() != null && pVar.getParent().isClass && (nameNode.text.equals("super") || nameNode.text.equals("this"))) {
            return Primitive.VOID;
        }
        Name name = nameNode.getName(pVar);
        try {
            return name.invokeMethod(interpreter, getArgsNode().getArguments(callStack, interpreter), callStack, this);
        } catch (ReflectError e) {
            throw new EvalError(new StringBuffer().append("Error in method invocation: ").append(e.getMessage()).toString(), this, callStack);
        } catch (UtilEvalError e2) {
            throw e2.toEvalError(this, callStack);
        } catch (InvocationTargetException e3) {
            String string = new StringBuffer().append("Method Invocation ").append(name).toString();
            Throwable targetException = e3.getTargetException();
            boolean zInNativeCode = true;
            if (targetException instanceof EvalError) {
                zInNativeCode = targetException instanceof TargetError ? ((TargetError) targetException).inNativeCode() : false;
            }
            throw new TargetError(string, targetException, this, callStack, zInNativeCode);
        }
    }

    BSHArguments getArgsNode() {
        return (BSHArguments) jjtGetChild(1);
    }

    BSHAmbiguousName getNameNode() {
        return (BSHAmbiguousName) jjtGetChild(0);
    }
}
