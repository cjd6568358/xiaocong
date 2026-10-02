package bsh;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class SimpleNode implements Node {
    public static SimpleNode JAVACODE = new SimpleNode(-1) { // from class: bsh.SimpleNode.1
        @Override // bsh.SimpleNode
        public int getLineNumber() {
            return -1;
        }

        @Override // bsh.SimpleNode
        public String getSourceFile() {
            return "<Called from Java Code>";
        }

        @Override // bsh.SimpleNode
        public String getText() {
            return "<Compiled Java Code>";
        }
    };
    protected Node[] children;
    Token firstToken;
    protected int id;
    Token lastToken;
    protected Node parent;
    String sourceFile;

    public SimpleNode(int i) {
        this.id = i;
    }

    public void dump(String str) {
        System.out.println(toString(str));
        if (this.children == null) {
            return;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.children.length) {
                return;
            }
            SimpleNode simpleNode = (SimpleNode) this.children[i2];
            if (simpleNode != null) {
                simpleNode.dump(new StringBuffer().append(str).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString());
            }
            i = i2 + 1;
        }
    }

    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        throw new InterpreterError(new StringBuffer().append("Unimplemented or inappropriate for ").append(getClass().getName()).toString());
    }

    public SimpleNode getChild(int i) {
        return (SimpleNode) jjtGetChild(i);
    }

    public int getLineNumber() {
        return this.firstToken.beginLine;
    }

    public String getSourceFile() {
        if (this.sourceFile == null) {
            return this.parent != null ? ((SimpleNode) this.parent).getSourceFile() : "<unknown file>";
        }
        return this.sourceFile;
    }

    public String getText() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Token token = this.firstToken; token != null; token = token.next) {
            stringBuffer.append(token.image);
            if (!token.image.equals(".")) {
                stringBuffer.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
            if (token == this.lastToken || token.image.equals("{") || token.image.equals(";")) {
                break;
            }
        }
        return stringBuffer.toString();
    }

    @Override // bsh.Node
    public void jjtAddChild(Node node, int i) {
        if (this.children == null) {
            this.children = new Node[i + 1];
        } else if (i >= this.children.length) {
            Node[] nodeArr = new Node[i + 1];
            System.arraycopy(this.children, 0, nodeArr, 0, this.children.length);
            this.children = nodeArr;
        }
        this.children[i] = node;
    }

    @Override // bsh.Node
    public void jjtClose() {
    }

    @Override // bsh.Node
    public Node jjtGetChild(int i) {
        return this.children[i];
    }

    @Override // bsh.Node
    public int jjtGetNumChildren() {
        if (this.children == null) {
            return 0;
        }
        return this.children.length;
    }

    @Override // bsh.Node
    public Node jjtGetParent() {
        return this.parent;
    }

    @Override // bsh.Node
    public void jjtOpen() {
    }

    @Override // bsh.Node
    public void jjtSetParent(Node node) {
        this.parent = node;
    }

    public void prune() {
        jjtSetParent(null);
    }

    public void setSourceFile(String str) {
        this.sourceFile = str;
    }

    public String toString() {
        return ParserTreeConstants.jjtNodeName[this.id];
    }

    public String toString(String str) {
        return new StringBuffer().append(str).append(toString()).toString();
    }
}
