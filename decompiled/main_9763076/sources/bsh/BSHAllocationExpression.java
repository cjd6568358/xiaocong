package bsh;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHAllocationExpression extends SimpleNode {
    private static int innerClassCount = 0;

    BSHAllocationExpression(int i) {
        super(i);
    }

    private Object arrayAllocation(BSHArrayDimensions bSHArrayDimensions, Class cls, CallStack callStack, Interpreter interpreter) throws EvalError {
        Object objEval = bSHArrayDimensions.eval(cls, callStack, interpreter);
        return objEval != Primitive.VOID ? objEval : arrayNewInstance(cls, bSHArrayDimensions, callStack);
    }

    private Object arrayNewInstance(Class cls, BSHArrayDimensions bSHArrayDimensions, CallStack callStack) throws EvalError {
        if (bSHArrayDimensions.numUndefinedDims > 0) {
            cls = Array.newInstance((Class<?>) cls, new int[bSHArrayDimensions.numUndefinedDims]).getClass();
        }
        try {
            return Array.newInstance((Class<?>) cls, bSHArrayDimensions.definedDimensions);
        } catch (NegativeArraySizeException e) {
            throw new TargetError(e, this, callStack);
        } catch (Exception e2) {
            throw new EvalError(new StringBuffer().append("Can't construct primitive array: ").append(e2.getMessage()).toString(), this, callStack);
        }
    }

    private Object constructObject(Class cls, Object[] objArr, CallStack callStack) throws EvalError {
        NameSpace classNameSpace;
        try {
            Object objConstructObject = Reflect.constructObject(cls, objArr);
            String name = cls.getName();
            if (name.indexOf("$") != -1 && (classNameSpace = Name.getClassNameSpace(callStack.top().getThis(null).getNameSpace())) != null && name.startsWith(new StringBuffer().append(classNameSpace.getName()).append("$").toString())) {
                try {
                    ClassGenerator.getClassGenerator().setInstanceNameSpaceParent(objConstructObject, name, classNameSpace);
                } catch (UtilEvalError e) {
                    throw e.toEvalError(this, callStack);
                }
            }
            return objConstructObject;
        } catch (ReflectError e2) {
            throw new EvalError(new StringBuffer().append("Constructor error: ").append(e2.getMessage()).toString(), this, callStack);
        } catch (InvocationTargetException e3) {
            Interpreter.debug(new StringBuffer().append("The constructor threw an exception:\n\t").append(e3.getTargetException()).toString());
            throw new TargetError("Object constructor", e3.getTargetException(), this, callStack, true);
        }
    }

    private Object constructWithClassBody(Class cls, Object[] objArr, BSHBlock bSHBlock, CallStack callStack, Interpreter interpreter) throws EvalError {
        StringBuffer stringBufferAppend = new StringBuffer().append(callStack.top().getName()).append("$");
        int i = innerClassCount + 1;
        innerClassCount = i;
        String string = stringBufferAppend.append(i).toString();
        Modifiers modifiers = new Modifiers();
        modifiers.addModifier(0, "public");
        try {
            try {
                return Reflect.constructObject(ClassGenerator.getClassGenerator().generateClass(string, modifiers, null, cls, bSHBlock, false, callStack, interpreter), objArr);
            } catch (Exception e) {
                e = e;
                if (e instanceof InvocationTargetException) {
                    e = (Exception) ((InvocationTargetException) e).getTargetException();
                }
                throw new EvalError(new StringBuffer().append("Error constructing inner class instance: ").append(e).toString(), this, callStack);
            }
        } catch (UtilEvalError e2) {
            throw e2.toEvalError(this, callStack);
        }
    }

    private Object constructWithInterfaceBody(Class cls, Object[] objArr, BSHBlock bSHBlock, CallStack callStack, Interpreter interpreter) throws EvalError {
        NameSpace nameSpace = new NameSpace(callStack.top(), "AnonymousBlock");
        callStack.push(nameSpace);
        bSHBlock.eval(callStack, interpreter, true);
        callStack.pop();
        nameSpace.importStatic(cls);
        try {
            return nameSpace.getThis(interpreter).getInterface(cls);
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        }
    }

    private Object objectAllocation(BSHAmbiguousName bSHAmbiguousName, BSHArguments bSHArguments, CallStack callStack, Interpreter interpreter) throws EvalError {
        callStack.top();
        Object[] arguments = bSHArguments.getArguments(callStack, interpreter);
        if (arguments == null) {
            throw new EvalError("Null args in new.", this, callStack);
        }
        bSHAmbiguousName.toObject(callStack, interpreter, false);
        Object object = bSHAmbiguousName.toObject(callStack, interpreter, true);
        if (!(object instanceof ClassIdentifier)) {
            throw new EvalError(new StringBuffer().append("Unknown class: ").append(bSHAmbiguousName.text).toString(), this, callStack);
        }
        Class targetClass = ((ClassIdentifier) object).getTargetClass();
        if (!(jjtGetNumChildren() > 2)) {
            return constructObject(targetClass, arguments, callStack);
        }
        BSHBlock bSHBlock = (BSHBlock) jjtGetChild(2);
        return targetClass.isInterface() ? constructWithInterfaceBody(targetClass, arguments, bSHBlock, callStack, interpreter) : constructWithClassBody(targetClass, arguments, bSHBlock, callStack, interpreter);
    }

    private Object objectArrayAllocation(BSHAmbiguousName bSHAmbiguousName, BSHArrayDimensions bSHArrayDimensions, CallStack callStack, Interpreter interpreter) throws EvalError {
        NameSpace pVar = callStack.top();
        Class cls = bSHAmbiguousName.toClass(callStack, interpreter);
        if (cls == null) {
            throw new EvalError(new StringBuffer().append("Class ").append(bSHAmbiguousName.getName(pVar)).append(" not found.").toString(), this, callStack);
        }
        return arrayAllocation(bSHArrayDimensions, cls, callStack, interpreter);
    }

    private Object primitiveArrayAllocation(BSHPrimitiveType bSHPrimitiveType, BSHArrayDimensions bSHArrayDimensions, CallStack callStack, Interpreter interpreter) throws EvalError {
        return arrayAllocation(bSHArrayDimensions, bSHPrimitiveType.getType(), callStack, interpreter);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        SimpleNode simpleNode = (SimpleNode) jjtGetChild(0);
        SimpleNode simpleNode2 = (SimpleNode) jjtGetChild(1);
        if (!(simpleNode instanceof BSHAmbiguousName)) {
            return primitiveArrayAllocation((BSHPrimitiveType) simpleNode, (BSHArrayDimensions) simpleNode2, callStack, interpreter);
        }
        BSHAmbiguousName bSHAmbiguousName = (BSHAmbiguousName) simpleNode;
        return simpleNode2 instanceof BSHArguments ? objectAllocation(bSHAmbiguousName, (BSHArguments) simpleNode2, callStack, interpreter) : objectArrayAllocation(bSHAmbiguousName, (BSHArrayDimensions) simpleNode2, callStack, interpreter);
    }
}
