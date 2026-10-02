package com.alibaba.fastjson.parser;

import bsh.ParserConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.Bugly;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class JSONLexerBase implements JSONLexer, Closeable {
    protected int bp;
    protected char ch;
    protected int eofPos;
    protected int features;
    protected boolean hasSpecial;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue;
    protected int token;
    private static final ThreadLocal<char[]> SBUF_LOCAL = new ThreadLocal<>();
    protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    protected static final int[] digits = new int[103];
    protected Calendar calendar = null;
    protected TimeZone timeZone = JSON.defaultTimeZone;
    protected Locale locale = JSON.defaultLocale;
    public int matchStat = 0;

    public abstract String addSymbol(int i, int i2, int i3, SymbolTable symbolTable);

    protected abstract void arrayCopy(int i, char[] cArr, int i2, int i3);

    protected abstract boolean charArrayCompare(char[] cArr);

    public abstract char charAt(int i);

    protected abstract void copyTo(int i, int i2, char[] cArr);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract BigDecimal decimalValue();

    public abstract int indexOf(char c, int i);

    public abstract boolean isEOF();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract char next();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String numberString();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String stringVal();

    public abstract String subString(int i, int i2);

    protected abstract char[] sub_chars(int i, int i2);

    protected void lexError(String key, Object... args) {
        this.token = 1;
    }

    static {
        for (int i = 48; i <= 57; i++) {
            digits[i] = i - 48;
        }
        for (int i2 = 97; i2 <= 102; i2++) {
            digits[i2] = (i2 - 97) + 10;
        }
        for (int i3 = 65; i3 <= 70; i3++) {
            digits[i3] = (i3 - 65) + 10;
        }
    }

    public JSONLexerBase(int features) {
        this.stringDefaultValue = null;
        this.features = features;
        if ((Feature.InitStringFieldAsEmpty.mask & features) != 0) {
            this.stringDefaultValue = Constants.MAIN_VERSION_TAG;
        }
        this.sbuf = SBUF_LOCAL.get();
        if (this.sbuf == null) {
            this.sbuf = new char[WXMediaMessage.TITLE_LENGTH_LIMIT];
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken() {
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            if (this.ch == '/') {
                skipComment();
            } else {
                if (this.ch == '\"') {
                    scanString();
                    return;
                }
                if (this.ch == ',') {
                    next();
                    this.token = 16;
                    return;
                }
                if (this.ch >= '0' && this.ch <= '9') {
                    scanNumber();
                    return;
                }
                if (this.ch == '-') {
                    scanNumber();
                    return;
                }
                switch (this.ch) {
                    case '\b':
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                        next();
                        break;
                    case '\'':
                        if (!isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("Feature.AllowSingleQuotes is false");
                        }
                        scanStringSingleQuote();
                        return;
                    case '(':
                        next();
                        this.token = 10;
                        return;
                    case ')':
                        next();
                        this.token = 11;
                        return;
                    case '+':
                        next();
                        scanNumber();
                        return;
                    case '.':
                        next();
                        this.token = 25;
                        return;
                    case ':':
                        next();
                        this.token = 17;
                        return;
                    case ';':
                        next();
                        this.token = 24;
                        return;
                    case 'N':
                    case 'S':
                    case 'T':
                    case 'u':
                        scanIdent();
                        return;
                    case '[':
                        next();
                        this.token = 14;
                        return;
                    case ']':
                        next();
                        this.token = 15;
                        return;
                    case 'f':
                        scanFalse();
                        return;
                    case 'n':
                        scanNullOrNew();
                        return;
                    case 't':
                        scanTrue();
                        return;
                    case ParserConstants.STARASSIGN /* 120 */:
                        scanHex();
                        return;
                    case ParserConstants.ANDASSIGNX /* 123 */:
                        next();
                        this.token = 12;
                        return;
                    case ParserConstants.ORASSIGNX /* 125 */:
                        next();
                        this.token = 13;
                        return;
                    default:
                        if (isEOF()) {
                            if (this.token == 20) {
                                throw new JSONException("EOF error");
                            }
                            this.token = 20;
                            int i = this.eofPos;
                            this.bp = i;
                            this.pos = i;
                            return;
                        }
                        if (this.ch <= 31 || this.ch == 127) {
                            next();
                        } else {
                            lexError("illegal.char", String.valueOf((int) this.ch));
                            next();
                            return;
                        }
                        break;
                        break;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:6:0x0016  */
    /* JADX WARN: Code duplicated, block: B:97:0x0116 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:98:0x0030 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken(int expect) {
        this.sp = 0;
        while (true) {
            switch (expect) {
                case 2:
                    if (this.ch >= '0' && this.ch <= '9') {
                        this.pos = this.bp;
                        scanNumber();
                    } else if (this.ch == '\"') {
                        this.pos = this.bp;
                        scanString();
                    } else if (this.ch == '[') {
                        this.token = 14;
                        next();
                    } else {
                        if (this.ch == '{') {
                            this.token = 12;
                            next();
                        }
                        if (this.ch != ' ' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b') {
                            next();
                        } else {
                            nextToken();
                        }
                    }
                    break;
                case 3:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 13:
                case 17:
                case 19:
                default:
                    if (this.ch != ' ') {
                    }
                    next();
                    break;
                case 4:
                    if (this.ch == '\"') {
                        this.pos = this.bp;
                        scanString();
                    } else if (this.ch >= '0' && this.ch <= '9') {
                        this.pos = this.bp;
                        scanNumber();
                    } else if (this.ch == '[') {
                        this.token = 14;
                        next();
                    } else {
                        if (this.ch == '{') {
                            this.token = 12;
                            next();
                        }
                        if (this.ch != ' ') {
                        }
                        next();
                    }
                    break;
                case 12:
                    if (this.ch == '{') {
                        this.token = 12;
                        next();
                    } else {
                        if (this.ch == '[') {
                            this.token = 14;
                            next();
                        }
                        if (this.ch != ' ') {
                        }
                        next();
                    }
                    break;
                case 14:
                    if (this.ch == '[') {
                        this.token = 14;
                        next();
                    } else {
                        if (this.ch == '{') {
                            this.token = 12;
                            next();
                        }
                        if (this.ch != ' ') {
                        }
                        next();
                    }
                    break;
                case 15:
                    if (this.ch == ']') {
                        this.token = 15;
                        next();
                    }
                    if (this.ch == 26) {
                        this.token = 20;
                    }
                    if (this.ch != ' ') {
                    }
                    next();
                    break;
                case 16:
                    if (this.ch == ',') {
                        this.token = 16;
                        next();
                    } else if (this.ch == '}') {
                        this.token = 13;
                        next();
                    } else if (this.ch == ']') {
                        this.token = 15;
                        next();
                    } else {
                        if (this.ch == 26) {
                            this.token = 20;
                        }
                        if (this.ch != ' ') {
                        }
                        next();
                    }
                    break;
                case 18:
                    nextIdent();
                    break;
                case 20:
                    if (this.ch == 26) {
                        this.token = 20;
                    }
                    if (this.ch != ' ') {
                    }
                    next();
                    break;
            }
            return;
        }
    }

    public final void nextIdent() {
        while (isWhitespace(this.ch)) {
            next();
        }
        if (this.ch == '_' || this.ch == '$' || Character.isLetter(this.ch)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon() {
        nextTokenWithChar(':');
    }

    public final void nextTokenWithChar(char expect) {
        this.sp = 0;
        while (this.ch != expect) {
            if (this.ch == ' ' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b') {
                next();
            } else {
                throw new JSONException("not match " + expect + " - " + this.ch + ", info : " + info());
            }
        }
        next();
        nextToken();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int token() {
        return this.token;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String tokenName() {
        return JSONToken.name(this.token);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int pos() {
        return this.pos;
    }

    public final String stringDefaultValue() {
        return this.stringDefaultValue;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number integerValue() throws NumberFormatException {
        long limit;
        int i;
        long result = 0;
        boolean negative = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i2 = this.np;
        int max = this.np + this.sp;
        char type = ' ';
        switch (charAt(max - 1)) {
            case 'B':
                max--;
                type = 'B';
                break;
            case 'L':
                max--;
                type = 'L';
                break;
            case 'S':
                max--;
                type = 'S';
                break;
        }
        if (charAt(this.np) == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i = i2 + 1;
        } else {
            limit = -9223372036854775807L;
            i = i2;
        }
        if (i < max) {
            result = -(charAt(i) - '0');
            i++;
        }
        while (i < max) {
            int i3 = i + 1;
            int digit = charAt(i) - '0';
            if (result < -922337203685477580L) {
                return new BigInteger(numberString());
            }
            long result2 = result * 10;
            if (result2 < ((long) digit) + limit) {
                return new BigInteger(numberString());
            }
            result = result2 - ((long) digit);
            i = i3;
        }
        if (negative) {
            if (i <= this.np + 1) {
                throw new NumberFormatException(numberString());
            }
            if (result >= -2147483648L && type != 'L') {
                if (type == 'S') {
                    return Short.valueOf((short) result);
                }
                if (type == 'B') {
                    return Byte.valueOf((byte) result);
                }
                return Integer.valueOf((int) result);
            }
            return Long.valueOf(result);
        }
        long result3 = -result;
        if (result3 <= 2147483647L && type != 'L') {
            if (type == 'S') {
                return Short.valueOf((short) result3);
            }
            if (type == 'B') {
                return Byte.valueOf((byte) result3);
            }
            return Integer.valueOf((int) result3);
        }
        return Long.valueOf(result3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon(int expect) {
        nextTokenWithChar(':');
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public float floatValue() {
        char c0;
        String strVal = numberString();
        float floatValue = Float.parseFloat(strVal);
        if ((floatValue == 0.0f || floatValue == Float.POSITIVE_INFINITY) && (c0 = strVal.charAt(0)) > '0' && c0 <= '9') {
            throw new JSONException("float overflow : " + strVal);
        }
        return floatValue;
    }

    public double doubleValue() {
        return Double.parseDouble(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(Feature feature) {
        return isEnabled(feature.mask);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(int feature) {
        return (this.features & feature) != 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final char getCurrent() {
        return this.ch;
    }

    protected void skipComment() {
        next();
        if (this.ch == '/') {
            do {
                next();
                if (this.ch == '\n') {
                    next();
                    return;
                }
            } while (this.ch != 26);
            return;
        }
        if (this.ch == '*') {
            next();
            while (this.ch != 26) {
                if (this.ch == '*') {
                    next();
                    if (this.ch == '/') {
                        next();
                        return;
                    }
                } else {
                    next();
                }
            }
            return;
        }
        throw new JSONException("invalid comment");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable) {
        skipWhitespace();
        if (this.ch == '\"') {
            return scanSymbol(symbolTable, '\"');
        }
        if (this.ch == '\'') {
            if (!isEnabled(Feature.AllowSingleQuotes)) {
                throw new JSONException("syntax error");
            }
            return scanSymbol(symbolTable, '\'');
        }
        if (this.ch == '}') {
            next();
            this.token = 13;
            return null;
        }
        if (this.ch == ',') {
            next();
            this.token = 16;
            return null;
        }
        if (this.ch == 26) {
            this.token = 20;
            return null;
        }
        if (!isEnabled(Feature.AllowUnQuotedFieldNames)) {
            throw new JSONException("syntax error");
        }
        return scanSymbolUnQuoted(symbolTable);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable, char quote) {
        String value;
        int offset;
        int hash = 0;
        this.np = this.bp;
        this.sp = 0;
        boolean hasSpecial = false;
        while (true) {
            char chLocal = next();
            if (chLocal != quote) {
                if (chLocal == 26) {
                    throw new JSONException("unclosed.str");
                }
                if (chLocal == '\\') {
                    if (!hasSpecial) {
                        hasSpecial = true;
                        if (this.sp >= this.sbuf.length) {
                            int newCapcity = this.sbuf.length * 2;
                            if (this.sp > newCapcity) {
                                newCapcity = this.sp;
                            }
                            char[] newsbuf = new char[newCapcity];
                            System.arraycopy(this.sbuf, 0, newsbuf, 0, this.sbuf.length);
                            this.sbuf = newsbuf;
                        }
                        arrayCopy(this.np + 1, this.sbuf, 0, this.sp);
                    }
                    char chLocal2 = next();
                    switch (chLocal2) {
                        case '\"':
                            hash = (hash * 31) + 34;
                            putChar('\"');
                            break;
                        case '\'':
                            hash = (hash * 31) + 39;
                            putChar('\'');
                            break;
                        case '/':
                            hash = (hash * 31) + 47;
                            putChar('/');
                            break;
                        case '0':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 0);
                            break;
                        case '1':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 1);
                            break;
                        case '2':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 2);
                            break;
                        case '3':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 3);
                            break;
                        case '4':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 4);
                            break;
                        case '5':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 5);
                            break;
                        case '6':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 6);
                            break;
                        case '7':
                            hash = (hash * 31) + chLocal2;
                            putChar((char) 7);
                            break;
                        case 'F':
                        case 'f':
                            hash = (hash * 31) + 12;
                            putChar('\f');
                            break;
                        case '\\':
                            hash = (hash * 31) + 92;
                            putChar('\\');
                            break;
                        case 'b':
                            hash = (hash * 31) + 8;
                            putChar('\b');
                            break;
                        case 'n':
                            hash = (hash * 31) + 10;
                            putChar('\n');
                            break;
                        case 'r':
                            hash = (hash * 31) + 13;
                            putChar('\r');
                            break;
                        case 't':
                            hash = (hash * 31) + 9;
                            putChar('\t');
                            break;
                        case 'u':
                            int val = Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16);
                            hash = (hash * 31) + val;
                            putChar((char) val);
                            break;
                        case 'v':
                            hash = (hash * 31) + 11;
                            putChar((char) 11);
                            break;
                        case ParserConstants.STARASSIGN /* 120 */:
                            char x1 = next();
                            this.ch = x1;
                            char x2 = next();
                            this.ch = x2;
                            int x_val = (digits[x1] * 16) + digits[x2];
                            char x_char = (char) x_val;
                            hash = (hash * 31) + x_char;
                            putChar(x_char);
                            break;
                        default:
                            this.ch = chLocal2;
                            throw new JSONException("unclosed.str.lit");
                    }
                } else {
                    hash = (hash * 31) + chLocal;
                    if (!hasSpecial) {
                        this.sp++;
                    } else if (this.sp == this.sbuf.length) {
                        putChar(chLocal);
                    } else {
                        char[] cArr = this.sbuf;
                        int i = this.sp;
                        this.sp = i + 1;
                        cArr[i] = chLocal;
                    }
                }
            } else {
                this.token = 4;
                if (!hasSpecial) {
                    if (this.np == -1) {
                        offset = 0;
                    } else {
                        offset = this.np + 1;
                    }
                    value = addSymbol(offset, this.sp, hash, symbolTable);
                } else {
                    value = symbolTable.addSymbol(this.sbuf, 0, this.sp, hash);
                }
                this.sp = 0;
                next();
                return value;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void resetStringPosition() {
        this.sp = 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        return Constants.MAIN_VERSION_TAG;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        if (this.token == 1 && this.pos == 0 && this.bp == 1) {
            this.bp = 0;
        }
        boolean[] firstIdentifierFlags = IOUtils.firstIdentifierFlags;
        char first = this.ch;
        boolean firstFlag = this.ch >= firstIdentifierFlags.length || firstIdentifierFlags[first];
        if (!firstFlag) {
            throw new JSONException("illegal identifier : " + this.ch + info());
        }
        boolean[] identifierFlags = IOUtils.identifierFlags;
        int hash = first;
        this.np = this.bp;
        this.sp = 1;
        while (true) {
            char chLocal = next();
            if (chLocal < identifierFlags.length && !identifierFlags[chLocal]) {
                break;
            }
            hash = (hash * 31) + chLocal;
            this.sp++;
        }
        this.ch = charAt(this.bp);
        this.token = 18;
        if (this.sp == 4 && hash == 3392903 && charAt(this.np) == 'n' && charAt(this.np + 1) == 'u' && charAt(this.np + 2) == 'l' && charAt(this.np + 3) == 'l') {
            return null;
        }
        if (symbolTable == null) {
            return subString(this.np, this.sp);
        }
        return addSymbol(this.np, this.sp, hash, symbolTable);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanString() {
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char ch = next();
            if (ch != '\"') {
                if (ch == 26) {
                    if (!isEOF()) {
                        putChar((char) 26);
                    } else {
                        throw new JSONException("unclosed string : " + ch);
                    }
                } else if (ch == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        if (this.sp >= this.sbuf.length) {
                            int newCapcity = this.sbuf.length * 2;
                            if (this.sp > newCapcity) {
                                newCapcity = this.sp;
                            }
                            char[] newsbuf = new char[newCapcity];
                            System.arraycopy(this.sbuf, 0, newsbuf, 0, this.sbuf.length);
                            this.sbuf = newsbuf;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char ch2 = next();
                    switch (ch2) {
                        case '\"':
                            putChar('\"');
                            break;
                        case '\'':
                            putChar('\'');
                            break;
                        case '/':
                            putChar('/');
                            break;
                        case '0':
                            putChar((char) 0);
                            break;
                        case '1':
                            putChar((char) 1);
                            break;
                        case '2':
                            putChar((char) 2);
                            break;
                        case '3':
                            putChar((char) 3);
                            break;
                        case '4':
                            putChar((char) 4);
                            break;
                        case '5':
                            putChar((char) 5);
                            break;
                        case '6':
                            putChar((char) 6);
                            break;
                        case '7':
                            putChar((char) 7);
                            break;
                        case 'F':
                        case 'f':
                            putChar('\f');
                            break;
                        case '\\':
                            putChar('\\');
                            break;
                        case 'b':
                            putChar('\b');
                            break;
                        case 'n':
                            putChar('\n');
                            break;
                        case 'r':
                            putChar('\r');
                            break;
                        case 't':
                            putChar('\t');
                            break;
                        case 'u':
                            int val = Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16);
                            putChar((char) val);
                            break;
                        case 'v':
                            putChar((char) 11);
                            break;
                        case ParserConstants.STARASSIGN /* 120 */:
                            int x_val = (digits[next()] * 16) + digits[next()];
                            char x_char = (char) x_val;
                            putChar(x_char);
                            break;
                        default:
                            this.ch = ch2;
                            throw new JSONException("unclosed string : " + ch2);
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else if (this.sp == this.sbuf.length) {
                    putChar(ch);
                } else {
                    char[] cArr = this.sbuf;
                    int i = this.sp;
                    this.sp = i + 1;
                    cArr[i] = ch;
                }
            } else {
                this.token = 4;
                this.ch = next();
                return;
            }
        }
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Locale getLocale() {
        return this.locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int intValue() {
        int limit;
        int i;
        int i2;
        if (this.np == -1) {
            this.np = 0;
        }
        int result = 0;
        boolean negative = false;
        int i3 = this.np;
        int max = this.np + this.sp;
        if (charAt(this.np) == '-') {
            negative = true;
            limit = Integer.MIN_VALUE;
            i = i3 + 1;
        } else {
            limit = -2147483647;
            i = i3;
        }
        if (i < max) {
            result = -(charAt(i) - '0');
            i++;
        }
        while (true) {
            if (i >= max) {
                i2 = i;
                break;
            }
            i2 = i + 1;
            char chLocal = charAt(i);
            if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B') {
                break;
            }
            int digit = chLocal - '0';
            if (result < -214748364) {
                throw new NumberFormatException(numberString());
            }
            int result2 = result * 10;
            if (result2 < limit + digit) {
                throw new NumberFormatException(numberString());
            }
            result = result2 - digit;
            i = i2;
        }
        if (negative) {
            if (i2 <= this.np + 1) {
                throw new NumberFormatException(numberString());
            }
            return result;
        }
        return -result;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.sbuf.length <= 8192) {
            SBUF_LOCAL.set(this.sbuf);
        }
        this.sbuf = null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isRef() {
        return this.sp == 4 && charAt(this.np + 1) == '$' && charAt(this.np + 2) == 'r' && charAt(this.np + 3) == 'e' && charAt(this.np + 4) == 'f';
    }

    public final boolean matchField(char[] fieldName) {
        while (!charArrayCompare(fieldName)) {
            if (isWhitespace(this.ch)) {
                next();
            } else {
                return false;
            }
        }
        this.bp += fieldName.length;
        this.ch = charAt(this.bp);
        if (this.ch == '{') {
            next();
            this.token = 12;
        } else if (this.ch == '[') {
            next();
            this.token = 14;
        } else if (this.ch == 'S' && charAt(this.bp + 1) == 'e' && charAt(this.bp + 2) == 't' && charAt(this.bp + 3) == '[') {
            this.bp += 3;
            this.ch = charAt(this.bp);
            this.token = 21;
        } else {
            nextToken();
        }
        return true;
    }

    public String scanFieldString(char[] fieldName) {
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return stringDefaultValue();
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int startIndex = this.bp + fieldName.length + 1;
        int endIndex = indexOf('\"', startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str");
        }
        int startIndex2 = this.bp + fieldName.length + 1;
        String stringVal = subString(startIndex2, endIndex - startIndex2);
        if (stringVal.indexOf(92) != -1) {
            while (true) {
                int slashCount = 0;
                for (int i = endIndex - 1; i >= 0 && charAt(i) == '\\'; i--) {
                    slashCount++;
                }
                if (slashCount % 2 == 0) {
                    break;
                }
                endIndex = indexOf('\"', endIndex + 1);
            }
            int chars_len = endIndex - ((this.bp + fieldName.length) + 1);
            char[] chars = sub_chars(this.bp + fieldName.length + 1, chars_len);
            stringVal = readString(chars, chars_len);
        }
        int offset3 = offset2 + (endIndex - ((this.bp + fieldName.length) + 1)) + 1;
        int offset4 = offset3 + 1;
        char chLocal = charAt(this.bp + offset3);
        String str = stringVal;
        if (chLocal == ',') {
            this.bp += offset4;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return str;
        }
        if (chLocal == '}') {
            int offset5 = offset4 + 1;
            char chLocal2 = charAt(this.bp + offset4);
            if (chLocal2 == ',') {
                this.token = 16;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == ']') {
                this.token = 15;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == '}') {
                this.token = 13;
                this.bp += offset5;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == 26) {
                this.token = 20;
                this.bp += offset5 - 1;
                this.ch = (char) 26;
            } else {
                this.matchStat = -1;
                String strVal = stringDefaultValue();
                return strVal;
            }
            this.matchStat = 4;
            return str;
        }
        this.matchStat = -1;
        String strVal2 = stringDefaultValue();
        return strVal2;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanString(char expectNextChar) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        if (chLocal == 'n') {
            if (charAt(this.bp + 1) == 'u' && charAt(this.bp + 1 + 1) == 'l' && charAt(this.bp + 1 + 2) == 'l') {
                int i = offset + 3 + 1;
                if (charAt(this.bp + 4) == expectNextChar) {
                    this.bp += 5;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        while (chLocal != '\"') {
            if (isWhitespace(chLocal)) {
                chLocal = charAt(this.bp + offset);
                offset++;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        }
        int startIndex = this.bp + offset;
        int endIndex = indexOf('\"', startIndex);
        if (endIndex == -1) {
            throw new JSONException("unclosed str");
        }
        String stringVal = subString(this.bp + offset, endIndex - startIndex);
        if (stringVal.indexOf(92) != -1) {
            while (true) {
                int slashCount = 0;
                for (int i2 = endIndex - 1; i2 >= 0 && charAt(i2) == '\\'; i2--) {
                    slashCount++;
                }
                if (slashCount % 2 == 0) {
                    break;
                }
                endIndex = indexOf('\"', endIndex + 1);
            }
            int chars_len = endIndex - startIndex;
            char[] chars = sub_chars(this.bp + 1, chars_len);
            stringVal = readString(chars, chars_len);
        }
        int offset2 = offset + (endIndex - startIndex) + 1;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(this.bp + offset2);
        String str = stringVal;
        while (chLocal2 != expectNextChar) {
            if (isWhitespace(chLocal2)) {
                chLocal2 = charAt(this.bp + offset3);
                offset3++;
            } else {
                this.matchStat = -1;
                return str;
            }
        }
        this.bp += offset3;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        return str;
    }

    public long scanFieldSymbol(char[] fieldName) {
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0L;
        }
        int offset = fieldName.length;
        int offset2 = offset + 1;
        if (charAt(this.bp + offset) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long hash = -3750763034362895579L;
        do {
            int offset3 = offset2;
            offset2 = offset3 + 1;
            chLocal = charAt(this.bp + offset3);
            if (chLocal == '\"') {
                int offset4 = offset2 + 1;
                char chLocal2 = charAt(this.bp + offset2);
                if (chLocal2 == ',') {
                    this.bp += offset4;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                    return hash;
                }
                if (chLocal2 == '}') {
                    int offset5 = offset4 + 1;
                    char chLocal3 = charAt(this.bp + offset4);
                    if (chLocal3 == ',') {
                        this.token = 16;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == ']') {
                        this.token = 15;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == '}') {
                        this.token = 13;
                        this.bp += offset5;
                        this.ch = charAt(this.bp);
                    } else if (chLocal3 == 26) {
                        this.token = 20;
                        this.bp += offset5 - 1;
                        this.ch = (char) 26;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.matchStat = 4;
                    return hash;
                }
                this.matchStat = -1;
                return 0L;
            }
            hash = (hash ^ ((long) chLocal)) * 1099511628211L;
        } while (chLocal != '\\');
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Enum<?> scanEnum(Class<?> enumClass, SymbolTable symbolTable, char serperator) {
        String name = scanSymbolWithSeperator(symbolTable, serperator);
        if (name == null) {
            return null;
        }
        return Enum.valueOf(enumClass, name);
    }

    public String scanSymbolWithSeperator(SymbolTable symbolTable, char serperator) {
        char chLocal;
        String strAddSymbol = null;
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        if (chLocal2 == 'n') {
            if (charAt(this.bp + 1) == 'u' && charAt(this.bp + 1 + 1) == 'l' && charAt(this.bp + 1 + 2) == 'l') {
                int i = offset + 3 + 1;
                if (charAt(this.bp + 4) == serperator) {
                    this.bp += 5;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                } else {
                    this.matchStat = -1;
                }
            } else {
                this.matchStat = -1;
            }
        } else if (chLocal2 != '\"') {
            this.matchStat = -1;
        } else {
            int hash = 0;
            do {
                int offset2 = offset;
                offset = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal == '\"') {
                    int start = this.bp + 0 + 1;
                    int len = ((this.bp + offset) - start) - 1;
                    strAddSymbol = addSymbol(start, len, hash, symbolTable);
                    char chLocal3 = charAt(this.bp + offset);
                    int offset3 = offset + 1;
                    while (chLocal3 != serperator) {
                        if (isWhitespace(chLocal3)) {
                            chLocal3 = charAt(this.bp + offset3);
                            offset3++;
                        } else {
                            this.matchStat = -1;
                        }
                    }
                    this.bp += offset3;
                    this.ch = charAt(this.bp);
                    this.matchStat = 3;
                } else {
                    hash = (hash * 31) + chLocal;
                }
            } while (chLocal != '\\');
            this.matchStat = -1;
        }
        return strAddSymbol;
    }

    public int scanFieldInt(char[] fieldName) {
        int offset;
        int offset2;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int offset3 = fieldName.length;
        int offset4 = offset3 + 1;
        char chLocal2 = charAt(this.bp + offset3);
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset4 + 1;
            chLocal2 = charAt(this.bp + offset4);
        } else {
            offset = offset4;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            int value = chLocal2 - '0';
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                value = (value * 10) + (chLocal - '0');
                offset = offset2;
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0;
            }
            if ((value < 0 || offset2 > fieldName.length + 14) && (value != Integer.MIN_VALUE || offset2 != 17 || !negative)) {
                this.matchStat = -1;
                return 0;
            }
            if (chLocal == ',') {
                this.bp += offset2;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return negative ? -value : value;
            }
            if (chLocal == '}') {
                int offset5 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 == ',') {
                    this.token = 16;
                    this.bp += offset5;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == ']') {
                    this.token = 15;
                    this.bp += offset5;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == '}') {
                    this.token = 13;
                    this.bp += offset5;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == 26) {
                    this.token = 20;
                    this.bp += offset5 - 1;
                    this.ch = (char) 26;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
                this.matchStat = 4;
                return negative ? -value : value;
            }
            this.matchStat = -1;
            return 0;
        }
        this.matchStat = -1;
        return 0;
    }

    public final int[] scanFieldIntArray(char[] fieldName) {
        int offset;
        int offset2;
        int offset3;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset4 = fieldName.length;
        int offset5 = offset4 + 1;
        if (charAt(this.bp + offset4) != '[') {
            this.matchStat = -2;
            return null;
        }
        int offset6 = offset5 + 1;
        char chLocal2 = charAt(this.bp + offset5);
        int[] array = new int[16];
        int arrayIndex = 0;
        if (chLocal2 == ']') {
            chLocal = charAt(this.bp + offset6);
            offset3 = offset6 + 1;
        } else {
            while (true) {
                int arrayIndex2 = arrayIndex;
                int offset7 = offset6;
                boolean nagative = false;
                if (chLocal2 == '-') {
                    offset = offset7 + 1;
                    chLocal2 = charAt(this.bp + offset7);
                    nagative = true;
                } else {
                    offset = offset7;
                }
                if (chLocal2 >= '0' && chLocal2 <= '9') {
                    int value = chLocal2 - '0';
                    while (true) {
                        offset2 = offset + 1;
                        chLocal2 = charAt(this.bp + offset);
                        if (chLocal2 < '0' || chLocal2 > '9') {
                            break;
                        }
                        value = (value * 10) + (chLocal2 - '0');
                        offset = offset2;
                    }
                    if (arrayIndex2 >= array.length) {
                        int[] tmp = new int[(array.length * 3) / 2];
                        System.arraycopy(array, 0, tmp, 0, arrayIndex2);
                        array = tmp;
                    }
                    arrayIndex = arrayIndex2 + 1;
                    if (nagative) {
                        value = -value;
                    }
                    array[arrayIndex2] = value;
                    if (chLocal2 == ',') {
                        offset6 = offset2 + 1;
                        chLocal2 = charAt(this.bp + offset2);
                    } else {
                        if (chLocal2 == ']') {
                            offset3 = offset2 + 1;
                            chLocal = charAt(this.bp + offset2);
                            break;
                        }
                        offset6 = offset2;
                    }
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
        }
        if (arrayIndex != array.length) {
            int[] tmp2 = new int[arrayIndex];
            System.arraycopy(array, 0, tmp2, 0, arrayIndex);
            array = tmp2;
        }
        if (chLocal == ',') {
            this.bp += offset3 - 1;
            next();
            this.matchStat = 3;
            this.token = 16;
            return array;
        }
        if (chLocal == '}') {
            int offset8 = offset3 + 1;
            char chLocal3 = charAt(this.bp + offset3);
            if (chLocal3 == ',') {
                this.token = 16;
                this.bp += offset8 - 1;
                next();
            } else if (chLocal3 == ']') {
                this.token = 15;
                this.bp += offset8 - 1;
                next();
            } else if (chLocal3 == '}') {
                this.token = 13;
                this.bp += offset8 - 1;
                next();
            } else if (chLocal3 == 26) {
                this.bp += offset8 - 1;
                this.token = 20;
                this.ch = (char) 26;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return array;
        }
        this.matchStat = -1;
        return null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public boolean scanBoolean(char expectNext) {
        this.matchStat = 0;
        int offset = 0 + 1;
        char chLocal = charAt(this.bp + 0);
        boolean value = false;
        if (chLocal == 't') {
            if (charAt(this.bp + 1) == 'r' && charAt(this.bp + 1 + 1) == 'u' && charAt(this.bp + 1 + 2) == 'e') {
                chLocal = charAt(this.bp + 4);
                value = true;
                offset = offset + 3 + 1;
            } else {
                this.matchStat = -1;
                return false;
            }
        } else if (chLocal == 'f') {
            if (charAt(this.bp + 1) == 'a' && charAt(this.bp + 1 + 1) == 'l' && charAt(this.bp + 1 + 2) == 's' && charAt(this.bp + 1 + 3) == 'e') {
                chLocal = charAt(this.bp + 5);
                value = false;
                offset = offset + 4 + 1;
            } else {
                this.matchStat = -1;
                return false;
            }
        } else if (chLocal == '1') {
            chLocal = charAt(this.bp + 1);
            value = true;
            offset++;
        } else if (chLocal == '0') {
            chLocal = charAt(this.bp + 1);
            value = false;
            offset++;
        }
        while (chLocal != expectNext) {
            if (isWhitespace(chLocal)) {
                chLocal = charAt(this.bp + offset);
                offset++;
            } else {
                this.matchStat = -1;
                return value;
            }
        }
        this.bp += offset;
        this.ch = charAt(this.bp);
        this.matchStat = 3;
        return value;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int scanInt(char expectNext) {
        int offset;
        int offset2;
        char chLocal;
        this.matchStat = 0;
        int offset3 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + 1);
            offset3++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset3 + 1;
            chLocal2 = charAt(this.bp + offset3);
        } else {
            offset = offset3;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            int value = chLocal2 - '0';
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                value = (value * 10) + (chLocal - '0');
                offset = offset2;
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0;
            }
            if (value < 0) {
                this.matchStat = -1;
                return 0;
            }
            while (chLocal != expectNext) {
                if (isWhitespace(chLocal)) {
                    chLocal = charAt(this.bp + offset2);
                    offset2++;
                } else {
                    this.matchStat = -1;
                    if (negative) {
                        value = -value;
                    }
                    return value;
                }
            }
            this.bp += offset2;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            if (negative) {
                value = -value;
            }
            return value;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            this.matchStat = 5;
            int offset4 = offset + 3;
            int offset5 = offset4 + 1;
            char chLocal3 = charAt(this.bp + offset4);
            if (quote && chLocal3 == '\"') {
                chLocal3 = charAt(this.bp + offset5);
                offset5++;
            }
            while (chLocal3 != ',') {
                if (chLocal3 == ']') {
                    this.bp += offset5;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0;
                }
                if (isWhitespace(chLocal3)) {
                    chLocal3 = charAt(this.bp + offset5);
                    offset5++;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            }
            this.bp += offset5;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0;
        }
        this.matchStat = -1;
        return 0;
    }

    public boolean scanFieldBoolean(char[] fieldName) {
        boolean value;
        int offset;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal = charAt(this.bp + offset2);
        if (chLocal == 't') {
            int offset4 = offset3 + 1;
            if (charAt(this.bp + offset3) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int offset5 = offset4 + 1;
            if (charAt(this.bp + offset4) != 'u') {
                this.matchStat = -1;
                return false;
            }
            offset = offset5 + 1;
            if (charAt(this.bp + offset5) != 'e') {
                this.matchStat = -1;
                return false;
            }
            value = true;
        } else if (chLocal == 'f') {
            int offset6 = offset3 + 1;
            if (charAt(this.bp + offset3) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int offset7 = offset6 + 1;
            if (charAt(this.bp + offset6) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int offset8 = offset7 + 1;
            if (charAt(this.bp + offset7) != 's') {
                this.matchStat = -1;
                return false;
            }
            int offset9 = offset8 + 1;
            if (charAt(this.bp + offset8) != 'e') {
                this.matchStat = -1;
                return false;
            }
            value = false;
            offset = offset9;
        } else {
            this.matchStat = -1;
            return false;
        }
        int offset10 = offset + 1;
        char chLocal2 = charAt(this.bp + offset);
        if (chLocal2 == ',') {
            this.bp += offset10;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return value;
        }
        if (chLocal2 == '}') {
            int offset11 = offset10 + 1;
            char chLocal3 = charAt(this.bp + offset10);
            if (chLocal3 == ',') {
                this.token = 16;
                this.bp += offset11;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == ']') {
                this.token = 15;
                this.bp += offset11;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == '}') {
                this.token = 13;
                this.bp += offset11;
                this.ch = charAt(this.bp);
            } else if (chLocal3 == 26) {
                this.token = 20;
                this.bp += offset11 - 1;
                this.ch = (char) 26;
            } else {
                this.matchStat = -1;
                return false;
            }
            this.matchStat = 4;
            return value;
        }
        this.matchStat = -1;
        return false;
    }

    public long scanFieldLong(char[] fieldName) {
        int offset;
        int offset2;
        char chLocal;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0L;
        }
        int offset3 = fieldName.length;
        int offset4 = offset3 + 1;
        char chLocal2 = charAt(this.bp + offset3);
        boolean negative = false;
        if (chLocal2 == '-') {
            offset = offset4 + 1;
            chLocal2 = charAt(this.bp + offset4);
            negative = true;
        } else {
            offset = offset4;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            long value = chLocal2 - '0';
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                value = (10 * value) + ((long) (chLocal - '0'));
                offset = offset2;
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0L;
            }
            boolean valid = offset2 - fieldName.length < 21 && (value >= 0 || (value == Long.MIN_VALUE && negative));
            if (!valid) {
                this.matchStat = -1;
                return 0L;
            }
            if (chLocal == ',') {
                this.bp += offset2;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return negative ? -value : value;
            }
            if (chLocal == '}') {
                int offset5 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 == ',') {
                    this.token = 16;
                    this.bp += offset5;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == ']') {
                    this.token = 15;
                    this.bp += offset5;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == '}') {
                    this.token = 13;
                    this.bp += offset5;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == 26) {
                    this.token = 20;
                    this.bp += offset5 - 1;
                    this.ch = (char) 26;
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
                this.matchStat = 4;
                return negative ? -value : value;
            }
            this.matchStat = -1;
            return 0L;
        }
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char expectNextChar) {
        int offset;
        int offset2;
        char chLocal;
        this.matchStat = 0;
        int offset3 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + 1);
            offset3++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset3 + 1;
            chLocal2 = charAt(this.bp + offset3);
        } else {
            offset = offset3;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            long value = chLocal2 - '0';
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                value = (10 * value) + ((long) (chLocal - '0'));
                offset = offset2;
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0L;
            }
            boolean valid = value >= 0 || (value == Long.MIN_VALUE && negative);
            if (!valid) {
                String val = subString(this.bp, offset2 - 1);
                throw new NumberFormatException(val);
            }
            if (quote) {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return 0L;
                }
                chLocal = charAt(this.bp + offset2);
                offset2++;
            }
            while (chLocal != expectNextChar) {
                if (isWhitespace(chLocal)) {
                    chLocal = charAt(this.bp + offset2);
                    offset2++;
                } else {
                    this.matchStat = -1;
                    return value;
                }
            }
            this.bp += offset2;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            if (negative) {
                value = -value;
            }
            return value;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            this.matchStat = 5;
            int offset4 = offset + 3;
            int offset5 = offset4 + 1;
            char chLocal3 = charAt(this.bp + offset4);
            if (quote && chLocal3 == '\"') {
                chLocal3 = charAt(this.bp + offset5);
                offset5++;
            }
            while (chLocal3 != ',') {
                if (chLocal3 == ']') {
                    this.bp += offset5;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0L;
                }
                if (isWhitespace(chLocal3)) {
                    chLocal3 = charAt(this.bp + offset5);
                    offset5++;
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
            }
            this.bp += offset5;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0L;
        }
        this.matchStat = -1;
        return 0L;
    }

    public final float scanFieldFloat(char[] fieldName) {
        int offset;
        int offset2;
        char chLocal;
        int start;
        int count;
        float value;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int offset3 = fieldName.length;
        int offset4 = offset3 + 1;
        char chLocal2 = charAt(this.bp + offset3);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset4);
            offset4++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset4 + 1;
            chLocal2 = charAt(this.bp + offset4);
        } else {
            offset = offset4;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            int intVal = chLocal2 - '0';
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                intVal = (intVal * 10) + (chLocal - '0');
                offset = offset2;
            }
            int power = 1;
            boolean small = chLocal == '.';
            if (small) {
                int offset5 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 >= '0' && chLocal3 <= '9') {
                    intVal = (intVal * 10) + (chLocal3 - '0');
                    power = 10;
                    while (true) {
                        offset2 = offset5 + 1;
                        chLocal = charAt(this.bp + offset5);
                        if (chLocal < '0' || chLocal > '9') {
                            break;
                        }
                        intVal = (intVal * 10) + (chLocal - '0');
                        power *= 10;
                        offset5 = offset2;
                    }
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            boolean exp = chLocal == 'e' || chLocal == 'E';
            if (exp) {
                int offset6 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal == '+' || chLocal == '-') {
                    offset2 = offset6 + 1;
                    chLocal = charAt(this.bp + offset6);
                } else {
                    offset2 = offset6;
                }
                while (chLocal >= '0' && chLocal <= '9') {
                    chLocal = charAt(this.bp + offset2);
                    offset2++;
                }
            }
            if (quote) {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return 0.0f;
                }
                int offset7 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                start = this.bp + fieldName.length + 1;
                count = ((this.bp + offset7) - start) - 2;
                offset2 = offset7;
            } else {
                start = this.bp + fieldName.length;
                count = ((this.bp + offset2) - start) - 1;
            }
            if (!exp && count < 20) {
                value = intVal / power;
                if (negative) {
                    value = -value;
                }
            } else {
                String text = subString(start, count);
                value = Float.parseFloat(text);
            }
            if (chLocal == ',') {
                this.bp += offset2;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return value;
            }
            if (chLocal == '}') {
                int offset8 = offset2 + 1;
                char chLocal4 = charAt(this.bp + offset2);
                if (chLocal4 == ',') {
                    this.token = 16;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == ']') {
                    this.token = 15;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == '}') {
                    this.token = 13;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == 26) {
                    this.bp += offset8 - 1;
                    this.token = 20;
                    this.ch = (char) 26;
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
                this.matchStat = 4;
                return value;
            }
            this.matchStat = -1;
            return 0.0f;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            this.matchStat = 5;
            int offset9 = offset + 3;
            int offset10 = offset9 + 1;
            char chLocal5 = charAt(this.bp + offset9);
            if (quote && chLocal5 == '\"') {
                chLocal5 = charAt(this.bp + offset10);
                offset10++;
            }
            while (chLocal5 != ',') {
                if (chLocal5 == '}') {
                    this.bp += offset10;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return 0.0f;
                }
                if (isWhitespace(chLocal5)) {
                    chLocal5 = charAt(this.bp + offset10);
                    offset10++;
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            this.bp += offset10;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0f;
        }
        this.matchStat = -1;
        return 0.0f;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final float scanFloat(char seperator) {
        int offset;
        int offset2;
        char chLocal;
        int start;
        int count;
        int offset3;
        float value;
        this.matchStat = 0;
        int offset4 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + 1);
            offset4++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset4 + 1;
            chLocal2 = charAt(this.bp + offset4);
        } else {
            offset = offset4;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            long intVal = chLocal2 - '0';
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                intVal = (10 * intVal) + ((long) (chLocal - '0'));
                offset = offset2;
            }
            long power = 1;
            boolean small = chLocal == '.';
            if (small) {
                int offset5 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 >= '0' && chLocal3 <= '9') {
                    intVal = (10 * intVal) + ((long) (chLocal3 - '0'));
                    power = 10;
                    while (true) {
                        offset2 = offset5 + 1;
                        chLocal = charAt(this.bp + offset5);
                        if (chLocal < '0' || chLocal > '9') {
                            break;
                        }
                        intVal = (10 * intVal) + ((long) (chLocal - '0'));
                        power *= 10;
                        offset5 = offset2;
                    }
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            boolean exp = chLocal == 'e' || chLocal == 'E';
            if (exp) {
                int offset6 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal == '+' || chLocal == '-') {
                    offset2 = offset6 + 1;
                    chLocal = charAt(this.bp + offset6);
                } else {
                    offset2 = offset6;
                }
                while (chLocal >= '0' && chLocal <= '9') {
                    chLocal = charAt(this.bp + offset2);
                    offset2++;
                }
            }
            if (quote) {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return 0.0f;
                }
                offset3 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                start = this.bp + 1;
                count = ((this.bp + offset3) - start) - 2;
            } else {
                start = this.bp;
                count = ((this.bp + offset2) - start) - 1;
                offset3 = offset2;
            }
            if (!exp && count < 20) {
                value = intVal / power;
                if (negative) {
                    value = -value;
                }
            } else {
                String text = subString(start, count);
                value = Float.parseFloat(text);
            }
            if (chLocal == seperator) {
                this.bp += offset3;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return value;
            }
            this.matchStat = -1;
            return value;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            this.matchStat = 5;
            int offset7 = offset + 3;
            int offset8 = offset7 + 1;
            char chLocal4 = charAt(this.bp + offset7);
            if (quote && chLocal4 == '\"') {
                chLocal4 = charAt(this.bp + offset8);
                offset8++;
            }
            while (chLocal4 != ',') {
                if (chLocal4 == ']') {
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0f;
                }
                if (isWhitespace(chLocal4)) {
                    chLocal4 = charAt(this.bp + offset8);
                    offset8++;
                } else {
                    this.matchStat = -1;
                    return 0.0f;
                }
            }
            this.bp += offset8;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0f;
        }
        this.matchStat = -1;
        return 0.0f;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public double scanDouble(char seperator) {
        int offset;
        int offset2;
        char chLocal;
        int start;
        int count;
        int offset3;
        double value;
        this.matchStat = 0;
        int offset4 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + 1);
            offset4++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset4 + 1;
            chLocal2 = charAt(this.bp + offset4);
        } else {
            offset = offset4;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            long intVal = chLocal2 - '0';
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                intVal = (10 * intVal) + ((long) (chLocal - '0'));
                offset = offset2;
            }
            long power = 1;
            boolean small = chLocal == '.';
            if (small) {
                int offset5 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 >= '0' && chLocal3 <= '9') {
                    intVal = (10 * intVal) + ((long) (chLocal3 - '0'));
                    power = 10;
                    while (true) {
                        offset2 = offset5 + 1;
                        chLocal = charAt(this.bp + offset5);
                        if (chLocal < '0' || chLocal > '9') {
                            break;
                        }
                        intVal = (10 * intVal) + ((long) (chLocal - '0'));
                        power *= 10;
                        offset5 = offset2;
                    }
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            boolean exp = chLocal == 'e' || chLocal == 'E';
            if (exp) {
                int offset6 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal == '+' || chLocal == '-') {
                    offset2 = offset6 + 1;
                    chLocal = charAt(this.bp + offset6);
                } else {
                    offset2 = offset6;
                }
                while (chLocal >= '0' && chLocal <= '9') {
                    chLocal = charAt(this.bp + offset2);
                    offset2++;
                }
            }
            if (quote) {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return 0.0d;
                }
                offset3 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                start = this.bp + 1;
                count = ((this.bp + offset3) - start) - 2;
            } else {
                start = this.bp;
                count = ((this.bp + offset2) - start) - 1;
                offset3 = offset2;
            }
            if (!exp && count < 20) {
                value = intVal / power;
                if (negative) {
                    value = -value;
                }
            } else {
                String text = subString(start, count);
                value = Double.parseDouble(text);
            }
            if (chLocal == seperator) {
                this.bp += offset3;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return value;
            }
            this.matchStat = -1;
            return value;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            this.matchStat = 5;
            int offset7 = offset + 3;
            int offset8 = offset7 + 1;
            char chLocal4 = charAt(this.bp + offset7);
            if (quote && chLocal4 == '\"') {
                chLocal4 = charAt(this.bp + offset8);
                offset8++;
            }
            while (chLocal4 != ',') {
                if (chLocal4 == ']') {
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0.0d;
                }
                if (isWhitespace(chLocal4)) {
                    chLocal4 = charAt(this.bp + offset8);
                    offset8++;
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            this.bp += offset8;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0d;
        }
        this.matchStat = -1;
        return 0.0d;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public BigDecimal scanDecimal(char seperator) {
        int offset;
        int offset2;
        char chLocal;
        int start;
        int count;
        int offset3;
        this.matchStat = 0;
        int offset4 = 0 + 1;
        char chLocal2 = charAt(this.bp + 0);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + 1);
            offset4++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset4 + 1;
            chLocal2 = charAt(this.bp + offset4);
        } else {
            offset = offset4;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                offset = offset2;
            }
            boolean small = chLocal == '.';
            if (small) {
                int offset5 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 < '0' || chLocal3 > '9') {
                    this.matchStat = -1;
                    return null;
                }
                while (true) {
                    offset2 = offset5 + 1;
                    chLocal = charAt(this.bp + offset5);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    offset5 = offset2;
                }
            }
            boolean exp = chLocal == 'e' || chLocal == 'E';
            if (exp) {
                int offset6 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal == '+' || chLocal == '-') {
                    offset2 = offset6 + 1;
                    chLocal = charAt(this.bp + offset6);
                } else {
                    offset2 = offset6;
                }
                while (chLocal >= '0' && chLocal <= '9') {
                    chLocal = charAt(this.bp + offset2);
                    offset2++;
                }
            }
            if (quote) {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return null;
                }
                offset3 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                start = this.bp + 1;
                count = ((this.bp + offset3) - start) - 2;
            } else {
                start = this.bp;
                count = ((this.bp + offset2) - start) - 1;
                offset3 = offset2;
            }
            char[] chars = sub_chars(start, count);
            BigDecimal bigDecimal = new BigDecimal(chars);
            if (chLocal == ',') {
                this.bp += offset3;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return bigDecimal;
            }
            if (chLocal == ']') {
                int offset7 = offset3 + 1;
                char chLocal4 = charAt(this.bp + offset3);
                if (chLocal4 == ',') {
                    this.token = 16;
                    this.bp += offset7;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == ']') {
                    this.token = 15;
                    this.bp += offset7;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == '}') {
                    this.token = 13;
                    this.bp += offset7;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == 26) {
                    this.token = 20;
                    this.bp += offset7 - 1;
                    this.ch = (char) 26;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return bigDecimal;
            }
            this.matchStat = -1;
            return null;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            this.matchStat = 5;
            int offset8 = offset + 3;
            int offset9 = offset8 + 1;
            char chLocal5 = charAt(this.bp + offset8);
            if (quote && chLocal5 == '\"') {
                chLocal5 = charAt(this.bp + offset9);
                offset9++;
            }
            while (chLocal5 != ',') {
                if (chLocal5 == '}') {
                    this.bp += offset9;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (isWhitespace(chLocal5)) {
                    chLocal5 = charAt(this.bp + offset9);
                    offset9++;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            this.bp += offset9;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        this.matchStat = -1;
        return null;
    }

    public final float[] scanFieldFloatArray(char[] fieldName) {
        int offset;
        float value;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset2 = fieldName.length;
        int offset3 = offset2 + 1;
        char chLocal = charAt(this.bp + offset2);
        if (chLocal != '[') {
            this.matchStat = -2;
            return null;
        }
        int offset4 = offset3 + 1;
        char chLocal2 = charAt(this.bp + offset3);
        float[] array = new float[16];
        int arrayIndex = 0;
        while (true) {
            int start = (this.bp + offset4) - 1;
            boolean negative = chLocal2 == '-';
            if (negative) {
                chLocal2 = charAt(this.bp + offset4);
                offset4++;
            }
            if (chLocal2 < '0' || chLocal2 > '9') {
                break;
            }
            int intVal = chLocal2 - '0';
            while (true) {
                offset = offset4 + 1;
                chLocal2 = charAt(this.bp + offset4);
                if (chLocal2 < '0' || chLocal2 > '9') {
                    break;
                }
                intVal = (intVal * 10) + (chLocal2 - '0');
                offset4 = offset;
            }
            int power = 1;
            boolean small = chLocal2 == '.';
            if (small) {
                int offset5 = offset + 1;
                char chLocal3 = charAt(this.bp + offset);
                power = 10;
                if (chLocal3 >= '0' && chLocal3 <= '9') {
                    intVal = (intVal * 10) + (chLocal3 - '0');
                    while (true) {
                        offset = offset5 + 1;
                        chLocal2 = charAt(this.bp + offset5);
                        if (chLocal2 < '0' || chLocal2 > '9') {
                            break;
                        }
                        intVal = (intVal * 10) + (chLocal2 - '0');
                        power *= 10;
                        offset5 = offset;
                    }
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            boolean exp = chLocal2 == 'e' || chLocal2 == 'E';
            if (exp) {
                int offset6 = offset + 1;
                chLocal2 = charAt(this.bp + offset);
                if (chLocal2 == '+' || chLocal2 == '-') {
                    offset = offset6 + 1;
                    chLocal2 = charAt(this.bp + offset6);
                } else {
                    offset = offset6;
                }
                while (chLocal2 >= '0' && chLocal2 <= '9') {
                    chLocal2 = charAt(this.bp + offset);
                    offset++;
                }
            }
            offset4 = offset;
            int count = ((this.bp + offset4) - start) - 1;
            if (!exp && count < 10) {
                value = intVal / power;
                if (negative) {
                    value = -value;
                }
            } else {
                String text = subString(start, count);
                value = Float.parseFloat(text);
            }
            if (arrayIndex >= array.length) {
                float[] tmp = new float[(array.length * 3) / 2];
                System.arraycopy(array, 0, tmp, 0, arrayIndex);
                array = tmp;
            }
            int arrayIndex2 = arrayIndex + 1;
            array[arrayIndex] = value;
            if (chLocal2 == ',') {
                chLocal2 = charAt(this.bp + offset4);
                offset4++;
            } else if (chLocal2 == ']') {
                int offset7 = offset4 + 1;
                char chLocal4 = charAt(this.bp + offset4);
                if (arrayIndex2 != array.length) {
                    float[] tmp2 = new float[arrayIndex2];
                    System.arraycopy(array, 0, tmp2, 0, arrayIndex2);
                    array = tmp2;
                }
                if (chLocal4 == ',') {
                    this.bp += offset7 - 1;
                    next();
                    this.matchStat = 3;
                    this.token = 16;
                    return array;
                }
                if (chLocal4 == '}') {
                    int offset8 = offset7 + 1;
                    char chLocal5 = charAt(this.bp + offset7);
                    if (chLocal5 == ',') {
                        this.token = 16;
                        this.bp += offset8 - 1;
                        next();
                    } else if (chLocal5 == ']') {
                        this.token = 15;
                        this.bp += offset8 - 1;
                        next();
                    } else if (chLocal5 == '}') {
                        this.token = 13;
                        this.bp += offset8 - 1;
                        next();
                    } else if (chLocal5 == 26) {
                        this.bp += offset8 - 1;
                        this.token = 20;
                        this.ch = (char) 26;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                    this.matchStat = 4;
                    return array;
                }
                this.matchStat = -1;
                return null;
            }
            arrayIndex = arrayIndex2;
        }
        this.matchStat = -1;
        return null;
    }

    public final float[][] scanFieldFloatArray2(char[] fieldName) {
        int offset;
        float value;
        int offset2;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return (float[][]) null;
        }
        int offset3 = fieldName.length;
        int offset4 = offset3 + 1;
        char chLocal = charAt(this.bp + offset3);
        if (chLocal != '[') {
            this.matchStat = -2;
            return (float[][]) null;
        }
        char chLocal2 = charAt(this.bp + offset4);
        float[][] arrayarray = new float[16][];
        int arrayarrayIndex = 0;
        int offset5 = offset4 + 1;
        loop0: while (true) {
            if (chLocal2 == '[') {
                int offset6 = offset5 + 1;
                char chLocal3 = charAt(this.bp + offset5);
                float[] array = new float[16];
                int arrayIndex = 0;
                while (true) {
                    int start = (this.bp + offset6) - 1;
                    boolean negative = chLocal3 == '-';
                    if (negative) {
                        chLocal3 = charAt(this.bp + offset6);
                        offset6++;
                    }
                    if (chLocal3 < '0' || chLocal3 > '9') {
                        break loop0;
                    }
                    int intVal = chLocal3 - '0';
                    while (true) {
                        offset = offset6 + 1;
                        chLocal3 = charAt(this.bp + offset6);
                        if (chLocal3 < '0' || chLocal3 > '9') {
                            break;
                        }
                        intVal = (intVal * 10) + (chLocal3 - '0');
                        offset6 = offset;
                    }
                    int power = 1;
                    if (chLocal3 == '.') {
                        int offset7 = offset + 1;
                        char chLocal4 = charAt(this.bp + offset);
                        if (chLocal4 >= '0' && chLocal4 <= '9') {
                            intVal = (intVal * 10) + (chLocal4 - '0');
                            power = 10;
                            while (true) {
                                offset = offset7 + 1;
                                chLocal3 = charAt(this.bp + offset7);
                                if (chLocal3 < '0' || chLocal3 > '9') {
                                    break;
                                }
                                intVal = (intVal * 10) + (chLocal3 - '0');
                                power *= 10;
                                offset7 = offset;
                            }
                        } else {
                            this.matchStat = -1;
                            return (float[][]) null;
                        }
                    }
                    boolean exp = chLocal3 == 'e' || chLocal3 == 'E';
                    if (exp) {
                        int offset8 = offset + 1;
                        chLocal3 = charAt(this.bp + offset);
                        if (chLocal3 == '+' || chLocal3 == '-') {
                            offset = offset8 + 1;
                            chLocal3 = charAt(this.bp + offset8);
                        } else {
                            offset = offset8;
                        }
                        while (chLocal3 >= '0' && chLocal3 <= '9') {
                            chLocal3 = charAt(this.bp + offset);
                            offset++;
                        }
                    }
                    offset6 = offset;
                    int count = ((this.bp + offset6) - start) - 1;
                    if (!exp && count < 10) {
                        value = intVal / power;
                        if (negative) {
                            value = -value;
                        }
                    } else {
                        String text = subString(start, count);
                        value = Float.parseFloat(text);
                    }
                    if (arrayIndex >= array.length) {
                        float[] tmp = new float[(array.length * 3) / 2];
                        System.arraycopy(array, 0, tmp, 0, arrayIndex);
                        array = tmp;
                    }
                    int arrayIndex2 = arrayIndex + 1;
                    array[arrayIndex] = value;
                    if (chLocal3 == ',') {
                        chLocal3 = charAt(this.bp + offset6);
                        offset6++;
                    } else if (chLocal3 == ']') {
                        int offset9 = offset6 + 1;
                        chLocal2 = charAt(this.bp + offset6);
                        if (arrayIndex2 != array.length) {
                            float[] tmp2 = new float[arrayIndex2];
                            System.arraycopy(array, 0, tmp2, 0, arrayIndex2);
                            array = tmp2;
                        }
                        if (arrayarrayIndex >= arrayarray.length) {
                            float[][] tmp3 = new float[(arrayarray.length * 3) / 2][];
                            System.arraycopy(array, 0, tmp3, 0, arrayIndex2);
                            arrayarray = tmp3;
                        }
                        int arrayarrayIndex2 = arrayarrayIndex + 1;
                        arrayarray[arrayarrayIndex] = array;
                        if (chLocal2 == ',') {
                            offset2 = offset9 + 1;
                            chLocal2 = charAt(this.bp + offset9);
                        } else {
                            if (chLocal2 == ']') {
                                int offset10 = offset9 + 1;
                                char chLocal5 = charAt(this.bp + offset9);
                                if (arrayarrayIndex2 != arrayarray.length) {
                                    float[][] tmp4 = new float[arrayarrayIndex2][];
                                    System.arraycopy(arrayarray, 0, tmp4, 0, arrayarrayIndex2);
                                    arrayarray = tmp4;
                                }
                                if (chLocal5 == ',') {
                                    this.bp += offset10 - 1;
                                    next();
                                    this.matchStat = 3;
                                    this.token = 16;
                                    return arrayarray;
                                }
                                if (chLocal5 == '}') {
                                    int offset11 = offset10 + 1;
                                    char chLocal6 = charAt(this.bp + offset10);
                                    if (chLocal6 == ',') {
                                        this.token = 16;
                                        this.bp += offset11 - 1;
                                        next();
                                    } else if (chLocal6 == ']') {
                                        this.token = 15;
                                        this.bp += offset11 - 1;
                                        next();
                                    } else if (chLocal6 == '}') {
                                        this.token = 13;
                                        this.bp += offset11 - 1;
                                        next();
                                    } else if (chLocal6 == 26) {
                                        this.bp += offset11 - 1;
                                        this.token = 20;
                                        this.ch = (char) 26;
                                    } else {
                                        this.matchStat = -1;
                                        return (float[][]) null;
                                    }
                                    this.matchStat = 4;
                                    return arrayarray;
                                }
                                this.matchStat = -1;
                                return (float[][]) null;
                            }
                            offset2 = offset9;
                        }
                        arrayarrayIndex = arrayarrayIndex2;
                        offset5 = offset2;
                        break;
                    }
                    arrayIndex = arrayIndex2;
                }
            }
        }
        this.matchStat = -1;
        return (float[][]) null;
    }

    public final double scanFieldDouble(char[] fieldName) {
        int offset;
        int offset2;
        char chLocal;
        int start;
        int count;
        double value;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return 0.0d;
        }
        int offset3 = fieldName.length;
        int offset4 = offset3 + 1;
        char chLocal2 = charAt(this.bp + offset3);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset4);
            offset4++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset4 + 1;
            chLocal2 = charAt(this.bp + offset4);
        } else {
            offset = offset4;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            long intVal = chLocal2 - '0';
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                intVal = (10 * intVal) + ((long) (chLocal - '0'));
                offset = offset2;
            }
            long power = 1;
            boolean small = chLocal == '.';
            if (small) {
                int offset5 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 >= '0' && chLocal3 <= '9') {
                    intVal = (10 * intVal) + ((long) (chLocal3 - '0'));
                    power = 10;
                    while (true) {
                        offset2 = offset5 + 1;
                        chLocal = charAt(this.bp + offset5);
                        if (chLocal < '0' || chLocal > '9') {
                            break;
                        }
                        intVal = (10 * intVal) + ((long) (chLocal - '0'));
                        power *= 10;
                        offset5 = offset2;
                    }
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            boolean exp = chLocal == 'e' || chLocal == 'E';
            if (exp) {
                int offset6 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal == '+' || chLocal == '-') {
                    offset2 = offset6 + 1;
                    chLocal = charAt(this.bp + offset6);
                } else {
                    offset2 = offset6;
                }
                while (chLocal >= '0' && chLocal <= '9') {
                    chLocal = charAt(this.bp + offset2);
                    offset2++;
                }
            }
            if (quote) {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return 0.0d;
                }
                int offset7 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                start = this.bp + fieldName.length + 1;
                count = ((this.bp + offset7) - start) - 2;
                offset2 = offset7;
            } else {
                start = this.bp + fieldName.length;
                count = ((this.bp + offset2) - start) - 1;
            }
            if (!exp && count < 20) {
                value = intVal / power;
                if (negative) {
                    value = -value;
                }
            } else {
                String text = subString(start, count);
                value = Double.parseDouble(text);
            }
            if (chLocal == ',') {
                this.bp += offset2;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return value;
            }
            if (chLocal == '}') {
                int offset8 = offset2 + 1;
                char chLocal4 = charAt(this.bp + offset2);
                if (chLocal4 == ',') {
                    this.token = 16;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == ']') {
                    this.token = 15;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == '}') {
                    this.token = 13;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == 26) {
                    this.token = 20;
                    this.bp += offset8 - 1;
                    this.ch = (char) 26;
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
                this.matchStat = 4;
                return value;
            }
            this.matchStat = -1;
            return 0.0d;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            this.matchStat = 5;
            int offset9 = offset + 3;
            int offset10 = offset9 + 1;
            char chLocal5 = charAt(this.bp + offset9);
            if (quote && chLocal5 == '\"') {
                chLocal5 = charAt(this.bp + offset10);
                offset10++;
            }
            while (chLocal5 != ',') {
                if (chLocal5 == '}') {
                    this.bp += offset10;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return 0.0d;
                }
                if (isWhitespace(chLocal5)) {
                    chLocal5 = charAt(this.bp + offset10);
                    offset10++;
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            this.bp += offset10;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return 0.0d;
        }
        this.matchStat = -1;
        return 0.0d;
    }

    public BigDecimal scanFieldDecimal(char[] fieldName) {
        int offset;
        int offset2;
        char chLocal;
        int start;
        int count;
        int offset3;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset4 = fieldName.length;
        int offset5 = offset4 + 1;
        char chLocal2 = charAt(this.bp + offset4);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset5);
            offset5++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset5 + 1;
            chLocal2 = charAt(this.bp + offset5);
        } else {
            offset = offset5;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                offset = offset2;
            }
            boolean small = chLocal == '.';
            if (small) {
                int offset6 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 < '0' || chLocal3 > '9') {
                    this.matchStat = -1;
                    return null;
                }
                while (true) {
                    offset2 = offset6 + 1;
                    chLocal = charAt(this.bp + offset6);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    offset6 = offset2;
                }
            }
            boolean exp = chLocal == 'e' || chLocal == 'E';
            if (exp) {
                int offset7 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                if (chLocal == '+' || chLocal == '-') {
                    offset2 = offset7 + 1;
                    chLocal = charAt(this.bp + offset7);
                } else {
                    offset2 = offset7;
                }
                while (chLocal >= '0' && chLocal <= '9') {
                    chLocal = charAt(this.bp + offset2);
                    offset2++;
                }
            }
            if (quote) {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return null;
                }
                offset3 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                start = this.bp + fieldName.length + 1;
                count = ((this.bp + offset3) - start) - 2;
            } else {
                start = this.bp + fieldName.length;
                count = ((this.bp + offset2) - start) - 1;
                offset3 = offset2;
            }
            char[] chars = sub_chars(start, count);
            BigDecimal bigDecimal = new BigDecimal(chars);
            if (chLocal == ',') {
                this.bp += offset3;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return bigDecimal;
            }
            if (chLocal == '}') {
                int offset8 = offset3 + 1;
                char chLocal4 = charAt(this.bp + offset3);
                if (chLocal4 == ',') {
                    this.token = 16;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == ']') {
                    this.token = 15;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == '}') {
                    this.token = 13;
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                } else if (chLocal4 == 26) {
                    this.token = 20;
                    this.bp += offset8 - 1;
                    this.ch = (char) 26;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return bigDecimal;
            }
            this.matchStat = -1;
            return null;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            this.matchStat = 5;
            int offset9 = offset + 3;
            int offset10 = offset9 + 1;
            char chLocal5 = charAt(this.bp + offset9);
            if (quote && chLocal5 == '\"') {
                chLocal5 = charAt(this.bp + offset10);
                offset10++;
            }
            while (chLocal5 != ',') {
                if (chLocal5 == '}') {
                    this.bp += offset10;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (isWhitespace(chLocal5)) {
                    chLocal5 = charAt(this.bp + offset10);
                    offset10++;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            this.bp += offset10;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        this.matchStat = -1;
        return null;
    }

    public BigInteger scanFieldBigInteger(char[] fieldName) {
        int offset;
        int offset2;
        char chLocal;
        int start;
        int count;
        BigInteger value;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset3 = fieldName.length;
        int offset4 = offset3 + 1;
        char chLocal2 = charAt(this.bp + offset3);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(this.bp + offset4);
            offset4++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            offset = offset4 + 1;
            chLocal2 = charAt(this.bp + offset4);
        } else {
            offset = offset4;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            long intVal = chLocal2 - '0';
            while (true) {
                offset2 = offset + 1;
                chLocal = charAt(this.bp + offset);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                intVal = (10 * intVal) + ((long) (chLocal - '0'));
                offset = offset2;
            }
            if (quote) {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return null;
                }
                int offset5 = offset2 + 1;
                chLocal = charAt(this.bp + offset2);
                start = this.bp + fieldName.length + 1;
                count = ((this.bp + offset5) - start) - 2;
                offset2 = offset5;
            } else {
                start = this.bp + fieldName.length;
                count = ((this.bp + offset2) - start) - 1;
            }
            if (count < 20 || (negative && count < 21)) {
                if (negative) {
                    intVal = -intVal;
                }
                value = BigInteger.valueOf(intVal);
            } else {
                String strVal = subString(start, count);
                value = new BigInteger(strVal);
            }
            if (chLocal == ',') {
                this.bp += offset2;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return value;
            }
            if (chLocal == '}') {
                int offset6 = offset2 + 1;
                char chLocal3 = charAt(this.bp + offset2);
                if (chLocal3 == ',') {
                    this.token = 16;
                    this.bp += offset6;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == ']') {
                    this.token = 15;
                    this.bp += offset6;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == '}') {
                    this.token = 13;
                    this.bp += offset6;
                    this.ch = charAt(this.bp);
                } else if (chLocal3 == 26) {
                    this.token = 20;
                    this.bp += offset6 - 1;
                    this.ch = (char) 26;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return value;
            }
            this.matchStat = -1;
            return null;
        }
        if (chLocal2 == 'n' && charAt(this.bp + offset) == 'u' && charAt(this.bp + offset + 1) == 'l' && charAt(this.bp + offset + 2) == 'l') {
            this.matchStat = 5;
            int offset7 = offset + 3;
            int offset8 = offset7 + 1;
            char chLocal4 = charAt(this.bp + offset7);
            if (quote && chLocal4 == '\"') {
                chLocal4 = charAt(this.bp + offset8);
                offset8++;
            }
            while (chLocal4 != ',') {
                if (chLocal4 == '}') {
                    this.bp += offset8;
                    this.ch = charAt(this.bp);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (isWhitespace(chLocal4)) {
                    chLocal4 = charAt(this.bp + offset8);
                    offset8++;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            this.bp += offset8;
            this.ch = charAt(this.bp);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        this.matchStat = -1;
        return null;
    }

    public Date scanFieldDate(char[] fieldName) {
        int offset;
        Date dateVal;
        int offset2;
        int offset3;
        this.matchStat = 0;
        if (!charArrayCompare(fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int offset4 = fieldName.length;
        int offset5 = offset4 + 1;
        char chLocal = charAt(this.bp + offset4);
        if (chLocal == '\"') {
            int startIndex = this.bp + fieldName.length + 1;
            int endIndex = indexOf('\"', startIndex);
            if (endIndex == -1) {
                throw new JSONException("unclosed str");
            }
            int startIndex2 = this.bp + fieldName.length + 1;
            String stringVal = subString(startIndex2, endIndex - startIndex2);
            if (stringVal.indexOf(92) != -1) {
                while (true) {
                    int slashCount = 0;
                    for (int i = endIndex - 1; i >= 0 && charAt(i) == '\\'; i--) {
                        slashCount++;
                    }
                    if (slashCount % 2 == 0) {
                        break;
                    }
                    endIndex = indexOf('\"', endIndex + 1);
                }
                int chars_len = endIndex - ((this.bp + fieldName.length) + 1);
                char[] chars = sub_chars(this.bp + fieldName.length + 1, chars_len);
                stringVal = readString(chars, chars_len);
            }
            int offset6 = offset5 + (endIndex - ((this.bp + fieldName.length) + 1)) + 1;
            offset2 = offset6 + 1;
            chLocal = charAt(this.bp + offset6);
            JSONScanner dateLexer = new JSONScanner(stringVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.getCalendar();
                    dateVal = calendar.getTime();
                    dateLexer.close();
                } else {
                    this.matchStat = -1;
                    dateLexer.close();
                    return null;
                }
            } catch (Throwable th) {
                dateLexer.close();
                throw th;
            }
        } else if (chLocal == '-' || (chLocal >= '0' && chLocal <= '9')) {
            long millis = 0;
            boolean negative = false;
            if (chLocal == '-') {
                offset = offset5 + 1;
                chLocal = charAt(this.bp + offset5);
                negative = true;
            } else {
                offset = offset5;
            }
            if (chLocal >= '0' && chLocal <= '9') {
                millis = chLocal - '0';
                while (true) {
                    offset3 = offset + 1;
                    chLocal = charAt(this.bp + offset);
                    if (chLocal < '0' || chLocal > '9') {
                        break;
                    }
                    millis = (10 * millis) + ((long) (chLocal - '0'));
                    offset = offset3;
                }
                offset = offset3;
            }
            if (millis < 0) {
                this.matchStat = -1;
                return null;
            }
            if (negative) {
                millis = -millis;
            }
            dateVal = new Date(millis);
            offset2 = offset;
        } else {
            this.matchStat = -1;
            return null;
        }
        if (chLocal == ',') {
            this.bp += offset2;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            return dateVal;
        }
        if (chLocal == '}') {
            int offset7 = offset2 + 1;
            char chLocal2 = charAt(this.bp + offset2);
            if (chLocal2 == ',') {
                this.token = 16;
                this.bp += offset7;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == ']') {
                this.token = 15;
                this.bp += offset7;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == '}') {
                this.token = 13;
                this.bp += offset7;
                this.ch = charAt(this.bp);
            } else if (chLocal2 == 26) {
                this.token = 20;
                this.bp += offset7 - 1;
                this.ch = (char) 26;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return dateVal;
        }
        this.matchStat = -1;
        return null;
    }

    public final void scanTrue() {
        if (this.ch != 't') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'r') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'u') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == 26 || this.ch == '\f' || this.ch == '\b' || this.ch == ':' || this.ch == '/') {
            this.token = 6;
            return;
        }
        throw new JSONException("scan true error");
    }

    public final void scanNullOrNew() {
        if (this.ch != 'n') {
            throw new JSONException("error parse null or new");
        }
        next();
        if (this.ch == 'u') {
            next();
            if (this.ch != 'l') {
                throw new JSONException("error parse null");
            }
            next();
            if (this.ch != 'l') {
                throw new JSONException("error parse null");
            }
            next();
            if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == 26 || this.ch == '\f' || this.ch == '\b') {
                this.token = 8;
                return;
            }
            throw new JSONException("scan null error");
        }
        if (this.ch != 'e') {
            throw new JSONException("error parse new");
        }
        next();
        if (this.ch != 'w') {
            throw new JSONException("error parse new");
        }
        next();
        if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == 26 || this.ch == '\f' || this.ch == '\b') {
            this.token = 9;
            return;
        }
        throw new JSONException("scan new error");
    }

    public final void scanFalse() {
        if (this.ch != 'f') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'a') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 's') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch == ' ' || this.ch == ',' || this.ch == '}' || this.ch == ']' || this.ch == '\n' || this.ch == '\r' || this.ch == '\t' || this.ch == 26 || this.ch == '\f' || this.ch == '\b' || this.ch == ':' || this.ch == '/') {
            this.token = 7;
            return;
        }
        throw new JSONException("scan false error");
    }

    public final void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String ident = stringVal();
        if ("null".equalsIgnoreCase(ident)) {
            this.token = 8;
            return;
        }
        if ("new".equals(ident)) {
            this.token = 9;
            return;
        }
        if ("true".equals(ident)) {
            this.token = 6;
            return;
        }
        if (Bugly.SDK_IS_DEV.equals(ident)) {
            this.token = 7;
            return;
        }
        if ("undefined".equals(ident)) {
            this.token = 23;
            return;
        }
        if ("Set".equals(ident)) {
            this.token = 21;
        } else if ("TreeSet".equals(ident)) {
            this.token = 22;
        } else {
            this.token = 18;
        }
    }

    public static String readString(char[] chars, int chars_len) {
        int len;
        char[] sbuf = new char[chars_len];
        int i = 0;
        int len2 = 0;
        while (i < chars_len) {
            char ch = chars[i];
            if (ch != '\\') {
                len = len2 + 1;
                sbuf[len2] = ch;
            } else {
                i++;
                switch (chars[i]) {
                    case '\"':
                        len = len2 + 1;
                        sbuf[len2] = '\"';
                        break;
                    case '\'':
                        len = len2 + 1;
                        sbuf[len2] = '\'';
                        break;
                    case '/':
                        len = len2 + 1;
                        sbuf[len2] = '/';
                        break;
                    case '0':
                        len = len2 + 1;
                        sbuf[len2] = 0;
                        break;
                    case '1':
                        len = len2 + 1;
                        sbuf[len2] = 1;
                        break;
                    case '2':
                        len = len2 + 1;
                        sbuf[len2] = 2;
                        break;
                    case '3':
                        len = len2 + 1;
                        sbuf[len2] = 3;
                        break;
                    case '4':
                        len = len2 + 1;
                        sbuf[len2] = 4;
                        break;
                    case '5':
                        len = len2 + 1;
                        sbuf[len2] = 5;
                        break;
                    case '6':
                        len = len2 + 1;
                        sbuf[len2] = 6;
                        break;
                    case '7':
                        len = len2 + 1;
                        sbuf[len2] = 7;
                        break;
                    case 'F':
                    case 'f':
                        len = len2 + 1;
                        sbuf[len2] = '\f';
                        break;
                    case '\\':
                        len = len2 + 1;
                        sbuf[len2] = '\\';
                        break;
                    case 'b':
                        len = len2 + 1;
                        sbuf[len2] = '\b';
                        break;
                    case 'n':
                        len = len2 + 1;
                        sbuf[len2] = '\n';
                        break;
                    case 'r':
                        len = len2 + 1;
                        sbuf[len2] = '\r';
                        break;
                    case 't':
                        len = len2 + 1;
                        sbuf[len2] = '\t';
                        break;
                    case 'u':
                        len = len2 + 1;
                        int i2 = i + 1;
                        int i3 = i2 + 1;
                        int i4 = i3 + 1;
                        i = i4 + 1;
                        sbuf[len2] = (char) Integer.parseInt(new String(new char[]{chars[i2], chars[i3], chars[i4], chars[i]}), 16);
                        break;
                    case 'v':
                        len = len2 + 1;
                        sbuf[len2] = 11;
                        break;
                    case ParserConstants.STARASSIGN /* 120 */:
                        len = len2 + 1;
                        int i5 = i + 1;
                        int i6 = digits[chars[i5]] * 16;
                        i = i5 + 1;
                        sbuf[len2] = (char) (i6 + digits[chars[i]]);
                        break;
                    default:
                        throw new JSONException("unclosed.str.lit");
                }
            }
            i++;
            len2 = len;
        }
        return new String(sbuf, 0, len2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public boolean isBlankInput() {
        int i = 0;
        while (true) {
            char chLocal = charAt(i);
            if (chLocal == 26) {
                this.token = 20;
                return true;
            }
            if (isWhitespace(chLocal)) {
                i++;
            } else {
                return false;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void skipWhitespace() {
        while (this.ch <= '/') {
            if (this.ch == ' ' || this.ch == '\r' || this.ch == '\n' || this.ch == '\t' || this.ch == '\f' || this.ch == '\b') {
                next();
            } else if (this.ch == '/') {
                skipComment();
            } else {
                return;
            }
        }
    }

    private void scanStringSingleQuote() {
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char chLocal = next();
            if (chLocal != '\'') {
                if (chLocal == 26) {
                    if (!isEOF()) {
                        putChar((char) 26);
                    } else {
                        throw new JSONException("unclosed single-quote string");
                    }
                } else if (chLocal == '\\') {
                    if (!this.hasSpecial) {
                        this.hasSpecial = true;
                        if (this.sp > this.sbuf.length) {
                            char[] newsbuf = new char[this.sp * 2];
                            System.arraycopy(this.sbuf, 0, newsbuf, 0, this.sbuf.length);
                            this.sbuf = newsbuf;
                        }
                        copyTo(this.np + 1, this.sp, this.sbuf);
                    }
                    char chLocal2 = next();
                    switch (chLocal2) {
                        case '\"':
                            putChar('\"');
                            break;
                        case '\'':
                            putChar('\'');
                            break;
                        case '/':
                            putChar('/');
                            break;
                        case '0':
                            putChar((char) 0);
                            break;
                        case '1':
                            putChar((char) 1);
                            break;
                        case '2':
                            putChar((char) 2);
                            break;
                        case '3':
                            putChar((char) 3);
                            break;
                        case '4':
                            putChar((char) 4);
                            break;
                        case '5':
                            putChar((char) 5);
                            break;
                        case '6':
                            putChar((char) 6);
                            break;
                        case '7':
                            putChar((char) 7);
                            break;
                        case 'F':
                        case 'f':
                            putChar('\f');
                            break;
                        case '\\':
                            putChar('\\');
                            break;
                        case 'b':
                            putChar('\b');
                            break;
                        case 'n':
                            putChar('\n');
                            break;
                        case 'r':
                            putChar('\r');
                            break;
                        case 't':
                            putChar('\t');
                            break;
                        case 'u':
                            putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                            break;
                        case 'v':
                            putChar((char) 11);
                            break;
                        case ParserConstants.STARASSIGN /* 120 */:
                            putChar((char) ((digits[next()] * 16) + digits[next()]));
                            break;
                        default:
                            this.ch = chLocal2;
                            throw new JSONException("unclosed single-quote string");
                    }
                } else if (!this.hasSpecial) {
                    this.sp++;
                } else if (this.sp == this.sbuf.length) {
                    putChar(chLocal);
                } else {
                    char[] cArr = this.sbuf;
                    int i = this.sp;
                    this.sp = i + 1;
                    cArr[i] = chLocal;
                }
            } else {
                this.token = 4;
                next();
                return;
            }
        }
    }

    protected final void putChar(char ch) {
        if (this.sp == this.sbuf.length) {
            char[] newsbuf = new char[this.sbuf.length * 2];
            System.arraycopy(this.sbuf, 0, newsbuf, 0, this.sbuf.length);
            this.sbuf = newsbuf;
        }
        char[] cArr = this.sbuf;
        int i = this.sp;
        this.sp = i + 1;
        cArr[i] = ch;
    }

    public final void scanHex() {
        char ch;
        if (this.ch != 'x') {
            throw new JSONException("illegal state. " + this.ch);
        }
        next();
        if (this.ch != '\'') {
            throw new JSONException("illegal state. " + this.ch);
        }
        this.np = this.bp;
        next();
        int i = 0;
        while (true) {
            ch = next();
            if ((ch < '0' || ch > '9') && (ch < 'A' || ch > 'F')) {
                break;
            }
            this.sp++;
            i++;
        }
        if (ch == '\'') {
            this.sp++;
            next();
            this.token = 26;
            return;
        }
        throw new JSONException("illegal state. " + ch);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanNumber() {
        this.np = this.bp;
        if (this.ch == '-') {
            this.sp++;
            next();
        }
        while (this.ch >= '0' && this.ch <= '9') {
            this.sp++;
            next();
        }
        boolean isDouble = false;
        if (this.ch == '.') {
            this.sp++;
            next();
            isDouble = true;
            while (this.ch >= '0' && this.ch <= '9') {
                this.sp++;
                next();
            }
        }
        if (this.ch == 'L' || this.ch == 'S' || this.ch == 'B') {
            this.sp++;
            next();
        } else if (this.ch == 'F' || this.ch == 'D') {
            this.sp++;
            next();
            isDouble = true;
        } else if (this.ch == 'e' || this.ch == 'E') {
            this.sp++;
            next();
            if (this.ch == '+' || this.ch == '-') {
                this.sp++;
                next();
            }
            while (this.ch >= '0' && this.ch <= '9') {
                this.sp++;
                next();
            }
            if (this.ch == 'D' || this.ch == 'F') {
                this.sp++;
                next();
            }
            isDouble = true;
        }
        if (isDouble) {
            this.token = 3;
        } else {
            this.token = 2;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final long longValue() throws NumberFormatException {
        long limit;
        int i;
        int i2;
        long result = 0;
        boolean negative = false;
        if (this.np == -1) {
            this.np = 0;
        }
        int i3 = this.np;
        int max = this.np + this.sp;
        if (charAt(this.np) == '-') {
            negative = true;
            limit = Long.MIN_VALUE;
            i = i3 + 1;
        } else {
            limit = -9223372036854775807L;
            i = i3;
        }
        if (i < max) {
            result = -(charAt(i) - '0');
            i++;
        }
        while (true) {
            if (i >= max) {
                i2 = i;
                break;
            }
            i2 = i + 1;
            char chLocal = charAt(i);
            if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B') {
                break;
            }
            int digit = chLocal - '0';
            if (result < -922337203685477580L) {
                throw new NumberFormatException(numberString());
            }
            long result2 = result * 10;
            if (result2 < ((long) digit) + limit) {
                throw new NumberFormatException(numberString());
            }
            result = result2 - ((long) digit);
            i = i2;
        }
        if (negative) {
            if (i2 <= this.np + 1) {
                throw new NumberFormatException(numberString());
            }
            return result;
        }
        return -result;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number decimalValue(boolean decimal) {
        Number numberValueOf;
        char chLocal = charAt((this.np + this.sp) - 1);
        try {
            if (chLocal == 'F') {
                numberValueOf = Float.valueOf(Float.parseFloat(numberString()));
            } else if (chLocal == 'D') {
                numberValueOf = Double.valueOf(Double.parseDouble(numberString()));
            } else if (decimal) {
                numberValueOf = decimalValue();
            } else {
                numberValueOf = Double.valueOf(doubleValue());
            }
            return numberValueOf;
        } catch (NumberFormatException ex) {
            throw new JSONException(ex.getMessage() + ", " + info());
        }
    }

    public static boolean isWhitespace(char ch) {
        return ch <= ' ' && (ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '\f' || ch == '\b');
    }
}
