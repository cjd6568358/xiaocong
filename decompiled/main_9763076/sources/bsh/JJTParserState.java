package bsh;

import java.util.Stack;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class JJTParserState {
    private boolean node_created;
    private Stack nodes = new Stack();
    private Stack marks = new Stack();
    private int sp = 0;
    private int mk = 0;

    JJTParserState() {
    }

    void clearNodeScope(Node node) {
        while (this.sp > this.mk) {
            popNode();
        }
        this.mk = ((Integer) this.marks.pop()).intValue();
    }

    void closeNodeScope(Node node, int i) {
        this.mk = ((Integer) this.marks.pop()).intValue();
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                node.jjtClose();
                pushNode(node);
                this.node_created = true;
                return;
            } else {
                Node nodePopNode = popNode();
                nodePopNode.jjtSetParent(node);
                node.jjtAddChild(nodePopNode, i2);
                i = i2;
            }
        }
    }

    void closeNodeScope(Node node, boolean z) {
        if (!z) {
            this.mk = ((Integer) this.marks.pop()).intValue();
            this.node_created = false;
            return;
        }
        int iNodeArity = nodeArity();
        this.mk = ((Integer) this.marks.pop()).intValue();
        while (true) {
            int i = iNodeArity;
            iNodeArity = i - 1;
            if (i <= 0) {
                node.jjtClose();
                pushNode(node);
                this.node_created = true;
                return;
            } else {
                Node nodePopNode = popNode();
                nodePopNode.jjtSetParent(node);
                node.jjtAddChild(nodePopNode, iNodeArity);
            }
        }
    }

    int nodeArity() {
        return this.sp - this.mk;
    }

    boolean nodeCreated() {
        return this.node_created;
    }

    void openNodeScope(Node node) {
        this.marks.push(new Integer(this.mk));
        this.mk = this.sp;
        node.jjtOpen();
    }

    Node peekNode() {
        return (Node) this.nodes.peek();
    }

    Node popNode() {
        int i = this.sp - 1;
        this.sp = i;
        if (i < this.mk) {
            this.mk = ((Integer) this.marks.pop()).intValue();
        }
        return (Node) this.nodes.pop();
    }

    void pushNode(Node node) {
        this.nodes.push(node);
        this.sp++;
    }

    void reset() {
        this.nodes.removeAllElements();
        this.marks.removeAllElements();
        this.sp = 0;
        this.mk = 0;
    }

    Node rootNode() {
        return (Node) this.nodes.elementAt(0);
    }
}
