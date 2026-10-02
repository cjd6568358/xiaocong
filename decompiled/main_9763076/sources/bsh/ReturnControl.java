package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ReturnControl implements ParserConstants {
    public int kind;
    public SimpleNode returnPoint;
    public Object value;

    public ReturnControl(int i, Object obj, SimpleNode simpleNode) {
        this.kind = i;
        this.value = obj;
        this.returnPoint = simpleNode;
    }
}
