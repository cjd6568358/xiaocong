package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class JSONReaderScanner extends JSONLexerBase {
    private static final ThreadLocal<char[]> BUF_LOCAL = new ThreadLocal<>();
    private char[] buf;
    private int bufLength;
    private Reader reader;

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char charAt(int index) {
        if (index >= this.bufLength) {
            if (this.bufLength == -1) {
                if (index < this.sp) {
                    return this.buf[index];
                }
                return (char) 26;
            }
            if (this.bp == 0) {
                char[] buf = new char[(this.buf.length * 3) / 2];
                System.arraycopy(this.buf, this.bp, buf, 0, this.bufLength);
                try {
                    int len = this.reader.read(buf, this.bufLength, buf.length - this.bufLength);
                    this.bufLength += len;
                    this.buf = buf;
                } catch (IOException e) {
                    throw new JSONException(e.getMessage(), e);
                }
            } else {
                int rest = this.bufLength - this.bp;
                if (rest > 0) {
                    System.arraycopy(this.buf, this.bp, this.buf, 0, rest);
                }
                try {
                    this.bufLength = this.reader.read(this.buf, rest, this.buf.length - rest);
                    if (this.bufLength == 0) {
                        throw new JSONException("illegal state, textLength is zero");
                    }
                    if (this.bufLength == -1) {
                        return (char) 26;
                    }
                    this.bufLength += rest;
                    index -= this.bp;
                    this.np -= this.bp;
                    this.bp = 0;
                } catch (IOException e2) {
                    throw new JSONException(e2.getMessage(), e2);
                }
            }
        }
        return this.buf[index];
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int indexOf(char ch, int startIndex) {
        int offset = startIndex - this.bp;
        while (true) {
            int index = this.bp + offset;
            char chLoal = charAt(index);
            if (ch == chLoal) {
                return this.bp + offset;
            }
            if (chLoal != 26) {
                offset++;
            } else {
                return -1;
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String addSymbol(int offset, int len, int hash, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.buf, offset, len, hash);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final char next() {
        int index = this.bp + 1;
        this.bp = index;
        if (index >= this.bufLength) {
            if (this.bufLength == -1) {
                return (char) 26;
            }
            if (this.sp > 0) {
                int offset = this.bufLength - this.sp;
                if (this.ch == '\"' && offset > 0) {
                    offset--;
                }
                System.arraycopy(this.buf, offset, this.buf, 0, this.sp);
            }
            this.np = -1;
            index = this.sp;
            this.bp = index;
            try {
                int startPos = this.bp;
                int readLength = this.buf.length - startPos;
                if (readLength == 0) {
                    char[] newBuf = new char[this.buf.length * 2];
                    System.arraycopy(this.buf, 0, newBuf, 0, this.buf.length);
                    this.buf = newBuf;
                    readLength = this.buf.length - startPos;
                }
                this.bufLength = this.reader.read(this.buf, this.bp, readLength);
                if (this.bufLength == 0) {
                    throw new JSONException("illegal stat, textLength is zero");
                }
                if (this.bufLength == -1) {
                    this.ch = (char) 26;
                    return (char) 26;
                }
                this.bufLength += this.bp;
            } catch (IOException e) {
                throw new JSONException(e.getMessage(), e);
            }
        }
        char c = this.buf[index];
        this.ch = c;
        return c;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void copyTo(int offset, int count, char[] dest) {
        System.arraycopy(this.buf, offset, dest, 0, count);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final boolean charArrayCompare(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (charAt(this.bp + i) != chars[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public byte[] bytesValue() {
        if (this.token == 26) {
            throw new JSONException("TODO");
        }
        return IOUtils.decodeBase64(this.buf, this.np + 1, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void arrayCopy(int srcPos, char[] dest, int destPos, int length) {
        System.arraycopy(this.buf, srcPos, dest, destPos, length);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String stringVal() {
        if (this.hasSpecial) {
            return new String(this.sbuf, 0, this.sp);
        }
        int offset = this.np + 1;
        if (offset < 0) {
            throw new IllegalStateException();
        }
        if (offset > this.buf.length - this.sp) {
            throw new IllegalStateException();
        }
        return new String(this.buf, offset, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String subString(int offset, int count) {
        if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        }
        return new String(this.buf, offset, count);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char[] sub_chars(int offset, int count) {
        if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        }
        if (offset == 0) {
            return this.buf;
        }
        char[] chars = new char[count];
        System.arraycopy(this.buf, offset, chars, 0, count);
        return chars;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String numberString() {
        int offset = this.np;
        if (offset == -1) {
            offset = 0;
        }
        char chLocal = charAt((this.sp + offset) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        String value = new String(this.buf, offset, sp);
        return value;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final BigDecimal decimalValue() {
        int offset = this.np;
        if (offset == -1) {
            offset = 0;
        }
        char chLocal = charAt((this.sp + offset) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        return new BigDecimal(this.buf, offset, sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
        if (this.buf.length <= 65536) {
            BUF_LOCAL.set(this.buf);
        }
        this.buf = null;
        IOUtils.close(this.reader);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean isEOF() {
        return this.bufLength == -1 || this.bp == this.buf.length || (this.ch == 26 && this.bp + 1 == this.buf.length);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final boolean isBlankInput() {
        int i = 0;
        while (true) {
            char chLocal = this.buf[i];
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
}
