package bsh;

import java.io.Serializable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Token implements Serializable {
    public int beginColumn;
    public int beginLine;
    public int endColumn;
    public int endLine;
    public String image;
    public int kind;
    public Token next;
    public Token specialToken;

    public static final Token newToken(int i) {
        return new Token();
    }

    public String toString() {
        return this.image;
    }
}
