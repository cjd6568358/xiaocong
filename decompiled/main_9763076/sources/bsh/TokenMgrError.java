package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TokenMgrError extends Error {
    static final int INVALID_LEXICAL_STATE = 2;
    static final int LEXICAL_ERROR = 0;
    static final int LOOP_DETECTED = 3;
    static final int STATIC_LEXER_ERROR = 1;
    int errorCode;

    public TokenMgrError() {
    }

    public TokenMgrError(String str, int i) {
        super(str);
        this.errorCode = i;
    }

    public TokenMgrError(boolean z, int i, int i2, int i3, String str, char c, int i4) {
        this(LexicalError(z, i, i2, i3, str, c), i4);
    }

    protected static String LexicalError(boolean z, int i, int i2, int i3, String str, char c) {
        return new StringBuffer().append("Lexical error at line ").append(i2).append(", column ").append(i3).append(".  Encountered: ").append(z ? "<EOF> " : new StringBuffer().append("\"").append(addEscapes(String.valueOf(c))).append("\"").append(" (").append((int) c).append("), ").toString()).append("after : \"").append(addEscapes(str)).append("\"").toString();
    }

    protected static final String addEscapes(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 0:
                    break;
                case '\b':
                    stringBuffer.append("\\b");
                    break;
                case '\t':
                    stringBuffer.append("\\t");
                    break;
                case '\n':
                    stringBuffer.append("\\n");
                    break;
                case '\f':
                    stringBuffer.append("\\f");
                    break;
                case '\r':
                    stringBuffer.append("\\r");
                    break;
                case '\"':
                    stringBuffer.append("\\\"");
                    break;
                case '\'':
                    stringBuffer.append("\\'");
                    break;
                case '\\':
                    stringBuffer.append("\\\\");
                    break;
                default:
                    char cCharAt = str.charAt(i);
                    if (cCharAt < ' ' || cCharAt > '~') {
                        String string = new StringBuffer().append("0000").append(Integer.toString(cCharAt, 16)).toString();
                        stringBuffer.append(new StringBuffer().append("\\u").append(string.substring(string.length() - 4, string.length())).toString());
                    } else {
                        stringBuffer.append(cCharAt);
                    }
                    break;
            }
        }
        return stringBuffer.toString();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return super.getMessage();
    }
}
