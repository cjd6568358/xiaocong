package bsh;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ParseException extends EvalError {
    public Token currentToken;
    protected String eol;
    public int[][] expectedTokenSequences;
    String sourceFile;
    protected boolean specialConstructor;
    public String[] tokenImage;

    public ParseException() {
        this(Constants.MAIN_VERSION_TAG);
        this.specialConstructor = false;
    }

    public ParseException(Token token, int[][] iArr, String[] strArr) {
        this();
        this.specialConstructor = true;
        this.currentToken = token;
        this.expectedTokenSequences = iArr;
        this.tokenImage = strArr;
    }

    public ParseException(String str) {
        super(str, null, null);
        this.sourceFile = "<unknown>";
        this.eol = System.getProperty("line.separator", "\n");
        this.specialConstructor = false;
    }

    protected String add_escapes(String str) {
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

    @Override // bsh.EvalError
    public int getErrorLineNumber() {
        return this.currentToken.next.beginLine;
    }

    @Override // bsh.EvalError
    public String getErrorSourceFile() {
        return this.sourceFile;
    }

    @Override // bsh.EvalError
    public String getErrorText() {
        int length = 0;
        for (int i = 0; i < this.expectedTokenSequences.length; i++) {
            if (length < this.expectedTokenSequences[i].length) {
                length = this.expectedTokenSequences[i].length;
            }
        }
        Token token = this.currentToken.next;
        String string = Constants.MAIN_VERSION_TAG;
        int i2 = 0;
        while (i2 < length) {
            if (i2 != 0) {
                string = new StringBuffer().append(string).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
            }
            if (token.kind == 0) {
                return new StringBuffer().append(string).append(this.tokenImage[0]).toString();
            }
            String string2 = new StringBuffer().append(string).append(add_escapes(token.image)).toString();
            token = token.next;
            i2++;
            string = string2;
        }
        return string;
    }

    @Override // bsh.EvalError, java.lang.Throwable
    public String getMessage() {
        return getMessage(false);
    }

    public String getMessage(boolean z) {
        if (!this.specialConstructor) {
            return super.getMessage();
        }
        String string = Constants.MAIN_VERSION_TAG;
        int i = 0;
        int i2 = 0;
        while (i < this.expectedTokenSequences.length) {
            int length = i2 < this.expectedTokenSequences[i].length ? this.expectedTokenSequences[i].length : i2;
            String string2 = string;
            for (int i3 = 0; i3 < this.expectedTokenSequences[i].length; i3++) {
                string2 = new StringBuffer().append(string2).append(this.tokenImage[this.expectedTokenSequences[i][i3]]).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
            }
            if (this.expectedTokenSequences[i][this.expectedTokenSequences[i].length - 1] != 0) {
                string2 = new StringBuffer().append(string2).append("...").toString();
            }
            i++;
            string = new StringBuffer().append(string2).append(this.eol).append("    ").toString();
            i2 = length;
        }
        String string3 = new StringBuffer().append("In file: ").append(this.sourceFile).append(" Encountered \"").toString();
        Token token = this.currentToken.next;
        String string4 = string3;
        int i4 = 0;
        while (i4 < i2) {
            if (i4 != 0) {
                string4 = new StringBuffer().append(string4).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString();
            }
            if (token.kind == 0) {
                string4 = new StringBuffer().append(string4).append(this.tokenImage[0]).toString();
                break;
            }
            String string5 = new StringBuffer().append(string4).append(add_escapes(token.image)).toString();
            token = token.next;
            i4++;
            string4 = string5;
        }
        String string6 = new StringBuffer().append(string4).append("\" at line ").append(this.currentToken.next.beginLine).append(", column ").append(this.currentToken.next.beginColumn).append(".").append(this.eol).toString();
        if (z) {
            return new StringBuffer().append(this.expectedTokenSequences.length == 1 ? new StringBuffer().append(string6).append("Was expecting:").append(this.eol).append("    ").toString() : new StringBuffer().append(string6).append("Was expecting one of:").append(this.eol).append("    ").toString()).append(string).toString();
        }
        return string6;
    }

    public void setErrorSourceFile(String str) {
        this.sourceFile = str;
    }

    @Override // bsh.EvalError, java.lang.Throwable
    public String toString() {
        return getMessage();
    }
}
