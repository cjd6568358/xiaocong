package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHLiteral extends SimpleNode {
    public Object value;

    BSHLiteral(int i) {
        super(i);
    }

    private char getEscapeChar(char c) {
        switch (c) {
            case 'b':
                return '\b';
            case 'f':
                return '\f';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            default:
                return c;
        }
    }

    public void charSetup(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt == '\\') {
            char cCharAt2 = str.charAt(1);
            cCharAt = Character.isDigit(cCharAt2) ? (char) Integer.parseInt(str.substring(1), 8) : getEscapeChar(cCharAt2);
        }
        this.value = new Primitive(new Character(cCharAt).charValue());
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        if (this.value == null) {
            throw new InterpreterError(new StringBuffer().append("Null in bsh literal: ").append(this.value).toString());
        }
        return this.value;
    }

    void stringSetup(String str) {
        int i;
        char escapeChar;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < str.length(); i2 = i + 1) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\\') {
                int i3 = i2 + 1;
                char cCharAt2 = str.charAt(i3);
                if (Character.isDigit(cCharAt2)) {
                    i = i3;
                    while (i < i3 + 2 && Character.isDigit(str.charAt(i + 1))) {
                        i++;
                    }
                    escapeChar = (char) Integer.parseInt(str.substring(i3, i + 1), 8);
                } else {
                    escapeChar = getEscapeChar(cCharAt2);
                    i = i3;
                }
            } else {
                i = i2;
                escapeChar = cCharAt;
            }
            stringBuffer.append(escapeChar);
        }
        this.value = stringBuffer.toString().intern();
    }
}
