package bsh;

import java.io.Serializable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
interface Node extends Serializable {
    void jjtAddChild(Node node, int i);

    void jjtClose();

    Node jjtGetChild(int i);

    int jjtGetNumChildren();

    Node jjtGetParent();

    void jjtOpen();

    void jjtSetParent(Node node);
}
