package bsh;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Hashtable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XThis extends This {
    static Class class$java$lang$Object;
    Hashtable interfaces;
    InvocationHandler invocationHandler;

    class Handler implements Serializable, InvocationHandler {
        private final XThis this$0;

        Handler(XThis xThis) {
            this.this$0 = xThis;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            try {
                return invokeImpl(obj, method, objArr);
            } catch (TargetError e) {
                throw e.getTarget();
            } catch (EvalError e2) {
                if (Interpreter.DEBUG) {
                    Interpreter.debug(new StringBuffer().append("EvalError in scripted interface: ").append(this.this$0.toString()).append(": ").append(e2).toString());
                }
                throw e2;
            }
        }

        public Object invokeImpl(Object obj, Method method, Object[] objArr) throws EvalError {
            BshMethod method2;
            Class clsClass$;
            BshMethod method3 = null;
            String name = method.getName();
            new CallStack(this.this$0.namespace);
            try {
                NameSpace nameSpace = this.this$0.namespace;
                Class[] clsArr = new Class[1];
                if (XThis.class$java$lang$Object == null) {
                    clsClass$ = XThis.class$("java.lang.Object");
                    XThis.class$java$lang$Object = clsClass$;
                } else {
                    clsClass$ = XThis.class$java$lang$Object;
                }
                clsArr[0] = clsClass$;
                method2 = nameSpace.getMethod("equals", clsArr);
            } catch (UtilEvalError e) {
                method2 = null;
            }
            if (name.equals("equals") && method2 == null) {
                return new Boolean(obj == objArr[0]);
            }
            try {
                method3 = this.this$0.namespace.getMethod("toString", new Class[0]);
            } catch (UtilEvalError e2) {
            }
            if (!name.equals("toString") || method3 != null) {
                return Primitive.unwrap(this.this$0.invokeMethod(name, Primitive.wrap(objArr, method.getParameterTypes())));
            }
            Class<?>[] interfaces = obj.getClass().getInterfaces();
            StringBuffer stringBuffer = new StringBuffer(new StringBuffer().append(this.this$0.toString()).append("\nimplements:").toString());
            for (Class<?> cls : interfaces) {
                stringBuffer.append(new StringBuffer().append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(cls.getName()).append(interfaces.length > 1 ? "," : Constants.MAIN_VERSION_TAG).toString());
            }
            return stringBuffer.toString();
        }
    }

    public XThis(NameSpace nameSpace, Interpreter interpreter) {
        super(nameSpace, interpreter);
        this.invocationHandler = new Handler(this);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // bsh.This
    public Object getInterface(Class cls) {
        return getInterface(new Class[]{cls});
    }

    @Override // bsh.This
    public Object getInterface(Class[] clsArr) {
        if (this.interfaces == null) {
            this.interfaces = new Hashtable();
        }
        int iHashCode = 21;
        for (Class cls : clsArr) {
            iHashCode *= cls.hashCode() + 3;
        }
        Integer num = new Integer(iHashCode);
        Object obj = this.interfaces.get(num);
        if (obj != null) {
            return obj;
        }
        Object objNewProxyInstance = Proxy.newProxyInstance(clsArr[0].getClassLoader(), clsArr, this.invocationHandler);
        this.interfaces.put(num, objNewProxyInstance);
        return objNewProxyInstance;
    }

    @Override // bsh.This
    public String toString() {
        return new StringBuffer().append("'this' reference (XThis) to Bsh object: ").append(this.namespace).toString();
    }
}
