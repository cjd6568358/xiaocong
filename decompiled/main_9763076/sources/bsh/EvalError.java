package bsh;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EvalError extends Exception {
    CallStack callstack;
    String message;
    SimpleNode node;

    public EvalError(String str, SimpleNode simpleNode, CallStack callStack) {
        setMessage(str);
        this.node = simpleNode;
        if (callStack != null) {
            this.callstack = callStack.copy();
        }
    }

    public int getErrorLineNumber() {
        if (this.node != null) {
            return this.node.getLineNumber();
        }
        return -1;
    }

    public String getErrorSourceFile() {
        return this.node != null ? this.node.getSourceFile() : "<unknown file>";
    }

    public String getErrorText() {
        return this.node != null ? this.node.getText() : "<unknown error>";
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.message;
    }

    SimpleNode getNode() {
        return this.node;
    }

    public String getScriptStackTrace() {
        if (this.callstack == null) {
            return "<Unknown>";
        }
        String string = Constants.MAIN_VERSION_TAG;
        CallStack callStackCopy = this.callstack.copy();
        while (callStackCopy.depth() > 0) {
            NameSpace nameSpacePop = callStackCopy.pop();
            SimpleNode node = nameSpacePop.getNode();
            if (nameSpacePop.isMethod) {
                string = new StringBuffer().append(string).append("\nCalled from method: ").append(nameSpacePop.getName()).toString();
                if (node != null) {
                    string = new StringBuffer().append(string).append(" : at Line: ").append(node.getLineNumber()).append(" : in file: ").append(node.getSourceFile()).append(" : ").append(node.getText()).toString();
                }
            }
        }
        return string;
    }

    protected void prependMessage(String str) {
        if (str == null) {
            return;
        }
        if (this.message == null) {
            this.message = str;
        } else {
            this.message = new StringBuffer().append(str).append(" : ").append(this.message).toString();
        }
    }

    public void reThrow(String str) throws EvalError {
        prependMessage(str);
        throw this;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    void setNode(SimpleNode simpleNode) {
        this.node = simpleNode;
    }

    @Override // java.lang.Throwable
    public String toString() {
        String string = this.node != null ? new StringBuffer().append(" : at Line: ").append(this.node.getLineNumber()).append(" : in file: ").append(this.node.getSourceFile()).append(" : ").append(this.node.getText()).toString() : ": <at unknown location>";
        if (this.callstack != null) {
            string = new StringBuffer().append(string).append("\n").append(getScriptStackTrace()).toString();
        }
        return new StringBuffer().append(getMessage()).append(string).toString();
    }
}
