package bsh;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TargetError extends EvalError {
    boolean inNativeCode;
    Throwable target;

    public TargetError(String str, Throwable th, SimpleNode simpleNode, CallStack callStack, boolean z) {
        super(str, simpleNode, callStack);
        this.target = th;
        this.inNativeCode = z;
    }

    public TargetError(Throwable th, SimpleNode simpleNode, CallStack callStack) {
        this("TargetError", th, simpleNode, callStack, false);
    }

    public Throwable getTarget() {
        return this.target instanceof InvocationTargetException ? ((InvocationTargetException) this.target).getTargetException() : this.target;
    }

    public boolean inNativeCode() {
        return this.inNativeCode;
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        printStackTrace(false, System.err);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        printStackTrace(false, printStream);
    }

    public void printStackTrace(boolean z, PrintStream printStream) {
        if (z) {
            super.printStackTrace(printStream);
            printStream.println("--- Target Stack Trace ---");
        }
        this.target.printStackTrace(printStream);
    }

    public String printTargetError(Throwable th) {
        String string = this.target.toString();
        return Capabilities.canGenerateInterfaces() ? new StringBuffer().append(string).append("\n").append(xPrintTargetError(th)).toString() : string;
    }

    @Override // bsh.EvalError, java.lang.Throwable
    public String toString() {
        return new StringBuffer().append(super.toString()).append("\nTarget exception: ").append(printTargetError(this.target)).toString();
    }

    public String xPrintTargetError(Throwable th) {
        Interpreter interpreter = new Interpreter();
        try {
            interpreter.set("target", th);
            return (String) interpreter.eval("import java.lang.reflect.UndeclaredThrowableException;String result=\"\";while ( target instanceof UndeclaredThrowableException ) {\ttarget=target.getUndeclaredThrowable(); \tresult+=\"Nested: \"+target.toString();}return result;");
        } catch (EvalError e) {
            throw new InterpreterError(new StringBuffer().append("xprintarget: ").append(e.toString()).toString());
        }
    }
}
