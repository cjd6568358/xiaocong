package bsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JavaCharStream {
    public static final boolean staticFlag = false;
    int available;
    protected int[] bufcolumn;
    protected char[] buffer;
    protected int[] bufline;
    public int bufpos;
    int bufsize;
    protected int column;
    protected int inBuf;
    protected Reader inputStream;
    protected int line;
    protected int maxNextCharInd;
    protected char[] nextCharBuf;
    protected int nextCharInd;
    protected boolean prevCharIsCR;
    protected boolean prevCharIsLF;
    int tokenBegin;

    public JavaCharStream(InputStream inputStream) {
        this(inputStream, 1, 1, 4096);
    }

    public JavaCharStream(InputStream inputStream, int i, int i2) {
        this(inputStream, i, i2, 4096);
    }

    public JavaCharStream(InputStream inputStream, int i, int i2, int i3) {
        this(new InputStreamReader(inputStream), i, i2, 4096);
    }

    public JavaCharStream(Reader reader) {
        this(reader, 1, 1, 4096);
    }

    public JavaCharStream(Reader reader, int i, int i2) {
        this(reader, i, i2, 4096);
    }

    public JavaCharStream(Reader reader, int i, int i2, int i3) {
        this.bufpos = -1;
        this.column = 0;
        this.line = 1;
        this.prevCharIsCR = false;
        this.prevCharIsLF = false;
        this.maxNextCharInd = 0;
        this.nextCharInd = -1;
        this.inBuf = 0;
        this.inputStream = reader;
        this.line = i;
        this.column = i2 - 1;
        this.bufsize = i3;
        this.available = i3;
        this.buffer = new char[i3];
        this.bufline = new int[i3];
        this.bufcolumn = new int[i3];
        this.nextCharBuf = new char[4096];
    }

    static final int hexval(char c) throws IOException {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
            case 'a':
                return 10;
            case 'B':
            case 'b':
                return 11;
            case 'C':
            case 'c':
                return 12;
            case 'D':
            case 'd':
                return 13;
            case 'E':
            case 'e':
                return 14;
            case 'F':
            case 'f':
                return 15;
            default:
                throw new IOException();
        }
    }

    protected void AdjustBuffSize() {
        if (this.available == this.bufsize) {
            if (this.tokenBegin <= 2048) {
                ExpandBuff(false);
                return;
            } else {
                this.bufpos = 0;
                this.available = this.tokenBegin;
                return;
            }
        }
        if (this.available > this.tokenBegin) {
            this.available = this.bufsize;
        } else if (this.tokenBegin - this.available < 2048) {
            ExpandBuff(true);
        } else {
            this.available = this.tokenBegin;
        }
    }

    public char BeginToken() throws IOException {
        if (this.inBuf <= 0) {
            this.tokenBegin = 0;
            this.bufpos = -1;
            return readChar();
        }
        this.inBuf--;
        int i = this.bufpos + 1;
        this.bufpos = i;
        if (i == this.bufsize) {
            this.bufpos = 0;
        }
        this.tokenBegin = this.bufpos;
        return this.buffer[this.bufpos];
    }

    public void Done() {
        this.nextCharBuf = null;
        this.buffer = null;
        this.bufline = null;
        this.bufcolumn = null;
    }

    protected void ExpandBuff(boolean z) {
        char[] cArr = new char[this.bufsize + 2048];
        int[] iArr = new int[this.bufsize + 2048];
        int[] iArr2 = new int[this.bufsize + 2048];
        try {
            if (z) {
                System.arraycopy(this.buffer, this.tokenBegin, cArr, 0, this.bufsize - this.tokenBegin);
                System.arraycopy(this.buffer, 0, cArr, this.bufsize - this.tokenBegin, this.bufpos);
                this.buffer = cArr;
                System.arraycopy(this.bufline, this.tokenBegin, iArr, 0, this.bufsize - this.tokenBegin);
                System.arraycopy(this.bufline, 0, iArr, this.bufsize - this.tokenBegin, this.bufpos);
                this.bufline = iArr;
                System.arraycopy(this.bufcolumn, this.tokenBegin, iArr2, 0, this.bufsize - this.tokenBegin);
                System.arraycopy(this.bufcolumn, 0, iArr2, this.bufsize - this.tokenBegin, this.bufpos);
                this.bufcolumn = iArr2;
                this.bufpos += this.bufsize - this.tokenBegin;
            } else {
                System.arraycopy(this.buffer, this.tokenBegin, cArr, 0, this.bufsize - this.tokenBegin);
                this.buffer = cArr;
                System.arraycopy(this.bufline, this.tokenBegin, iArr, 0, this.bufsize - this.tokenBegin);
                this.bufline = iArr;
                System.arraycopy(this.bufcolumn, this.tokenBegin, iArr2, 0, this.bufsize - this.tokenBegin);
                this.bufcolumn = iArr2;
                this.bufpos -= this.tokenBegin;
            }
            int i = this.bufsize + 2048;
            this.bufsize = i;
            this.available = i;
            this.tokenBegin = 0;
        } catch (Throwable th) {
            throw new Error(th.getMessage());
        }
    }

    protected void FillBuff() throws IOException {
        if (this.maxNextCharInd == 4096) {
            this.nextCharInd = 0;
            this.maxNextCharInd = 0;
        }
        try {
            int i = this.inputStream.read(this.nextCharBuf, this.maxNextCharInd, 4096 - this.maxNextCharInd);
            if (i == -1) {
                this.inputStream.close();
                throw new IOException();
            }
            this.maxNextCharInd = i + this.maxNextCharInd;
        } catch (IOException e) {
            if (this.bufpos != 0) {
                this.bufpos--;
                backup(0);
            } else {
                this.bufline[this.bufpos] = this.line;
                this.bufcolumn[this.bufpos] = this.column;
            }
            throw e;
        }
    }

    public String GetImage() {
        return this.bufpos >= this.tokenBegin ? new String(this.buffer, this.tokenBegin, (this.bufpos - this.tokenBegin) + 1) : new StringBuffer().append(new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin)).append(new String(this.buffer, 0, this.bufpos + 1)).toString();
    }

    public char[] GetSuffix(int i) {
        char[] cArr = new char[i];
        if (this.bufpos + 1 >= i) {
            System.arraycopy(this.buffer, (this.bufpos - i) + 1, cArr, 0, i);
        } else {
            System.arraycopy(this.buffer, this.bufsize - ((i - this.bufpos) - 1), cArr, 0, (i - this.bufpos) - 1);
            System.arraycopy(this.buffer, 0, cArr, (i - this.bufpos) - 1, this.bufpos + 1);
        }
        return cArr;
    }

    public void ReInit(InputStream inputStream) {
        ReInit(inputStream, 1, 1, 4096);
    }

    public void ReInit(InputStream inputStream, int i, int i2) {
        ReInit(inputStream, i, i2, 4096);
    }

    public void ReInit(InputStream inputStream, int i, int i2, int i3) {
        ReInit(new InputStreamReader(inputStream), i, i2, 4096);
    }

    public void ReInit(Reader reader) {
        ReInit(reader, 1, 1, 4096);
    }

    public void ReInit(Reader reader, int i, int i2) {
        ReInit(reader, i, i2, 4096);
    }

    public void ReInit(Reader reader, int i, int i2, int i3) {
        this.inputStream = reader;
        this.line = i;
        this.column = i2 - 1;
        if (this.buffer == null || i3 != this.buffer.length) {
            this.bufsize = i3;
            this.available = i3;
            this.buffer = new char[i3];
            this.bufline = new int[i3];
            this.bufcolumn = new int[i3];
            this.nextCharBuf = new char[4096];
        }
        this.prevCharIsCR = false;
        this.prevCharIsLF = false;
        this.maxNextCharInd = 0;
        this.inBuf = 0;
        this.tokenBegin = 0;
        this.bufpos = -1;
        this.nextCharInd = -1;
    }

    protected char ReadByte() throws IOException {
        int i = this.nextCharInd + 1;
        this.nextCharInd = i;
        if (i >= this.maxNextCharInd) {
            FillBuff();
        }
        return this.nextCharBuf[this.nextCharInd];
    }

    protected void UpdateLineColumn(char c) {
        this.column++;
        if (this.prevCharIsLF) {
            this.prevCharIsLF = false;
            int i = this.line;
            this.column = 1;
            this.line = i + 1;
        } else if (this.prevCharIsCR) {
            this.prevCharIsCR = false;
            if (c == '\n') {
                this.prevCharIsLF = true;
            } else {
                int i2 = this.line;
                this.column = 1;
                this.line = i2 + 1;
            }
        }
        switch (c) {
            case '\t':
                this.column--;
                this.column += 8 - (this.column & 7);
                break;
            case '\n':
                this.prevCharIsLF = true;
                break;
            case '\r':
                this.prevCharIsCR = true;
                break;
        }
        this.bufline[this.bufpos] = this.line;
        this.bufcolumn[this.bufpos] = this.column;
    }

    public void adjustBeginLineColumn(int i, int i2) {
        int i3 = 0;
        int i4 = this.tokenBegin;
        int i5 = this.bufpos >= this.tokenBegin ? (this.bufpos - this.tokenBegin) + this.inBuf + 1 : (this.bufsize - this.tokenBegin) + this.bufpos + 1 + this.inBuf;
        int i6 = 0;
        int i7 = 0;
        while (i7 < i5) {
            int[] iArr = this.bufline;
            i3 = i4 % this.bufsize;
            int i8 = iArr[i3];
            int[] iArr2 = this.bufline;
            i4++;
            int i9 = i4 % this.bufsize;
            if (i8 != iArr2[i9]) {
                break;
            }
            this.bufline[i3] = i;
            int i10 = (this.bufcolumn[i9] + i6) - this.bufcolumn[i3];
            this.bufcolumn[i3] = i6 + i2;
            i7++;
            i6 = i10;
        }
        if (i7 < i5) {
            this.bufline[i3] = i;
            this.bufcolumn[i3] = i6 + i2;
            int i11 = i + 1;
            int i12 = i4;
            while (true) {
                i7++;
                if (i7 >= i5) {
                    break;
                }
                int[] iArr3 = this.bufline;
                i3 = i12 % this.bufsize;
                int i13 = iArr3[i3];
                i12++;
                if (i13 != this.bufline[i12 % this.bufsize]) {
                    this.bufline[i3] = i11;
                    i11++;
                } else {
                    this.bufline[i3] = i11;
                }
            }
        }
        int i14 = i3;
        this.line = this.bufline[i14];
        this.column = this.bufcolumn[i14];
    }

    public void backup(int i) {
        this.inBuf += i;
        int i2 = this.bufpos - i;
        this.bufpos = i2;
        if (i2 < 0) {
            this.bufpos += this.bufsize;
        }
    }

    public int getBeginColumn() {
        return this.bufcolumn[this.tokenBegin];
    }

    public int getBeginLine() {
        return this.bufline[this.tokenBegin];
    }

    public int getColumn() {
        return this.bufcolumn[this.bufpos];
    }

    public int getEndColumn() {
        return this.bufcolumn[this.bufpos];
    }

    public int getEndLine() {
        return this.bufline[this.bufpos];
    }

    public int getLine() {
        return this.bufline[this.bufpos];
    }

    public char readChar() throws IOException {
        char cReadByte;
        char cReadByte2;
        if (this.inBuf > 0) {
            this.inBuf--;
            int i = this.bufpos + 1;
            this.bufpos = i;
            if (i == this.bufsize) {
                this.bufpos = 0;
            }
            return this.buffer[this.bufpos];
        }
        int i2 = this.bufpos + 1;
        this.bufpos = i2;
        if (i2 == this.available) {
            AdjustBuffSize();
        }
        char[] cArr = this.buffer;
        int i3 = this.bufpos;
        char cReadByte3 = ReadByte();
        cArr[i3] = cReadByte3;
        if (cReadByte3 != '\\') {
            UpdateLineColumn(cReadByte3);
            return cReadByte3;
        }
        UpdateLineColumn(cReadByte3);
        int i4 = 1;
        while (true) {
            int i5 = this.bufpos + 1;
            this.bufpos = i5;
            if (i5 == this.available) {
                AdjustBuffSize();
            }
            try {
                char[] cArr2 = this.buffer;
                int i6 = this.bufpos;
                cReadByte = ReadByte();
                cArr2[i6] = cReadByte;
                if (cReadByte != '\\') {
                    break;
                }
                UpdateLineColumn(cReadByte);
                i4++;
            } catch (IOException e) {
                if (i4 > 1) {
                    backup(i4);
                }
                return '\\';
            }
        }
        UpdateLineColumn(cReadByte);
        if (cReadByte != 'u' || (i4 & 1) != 1) {
            backup(i4);
            return '\\';
        }
        int i7 = this.bufpos - 1;
        this.bufpos = i7;
        if (i7 < 0) {
            this.bufpos = this.bufsize - 1;
        }
        while (true) {
            try {
                cReadByte2 = ReadByte();
                if (cReadByte2 != 'u') {
                    break;
                }
                this.column++;
            } catch (IOException e2) {
                throw new Error(new StringBuffer().append("Invalid escape character at line ").append(this.line).append(" column ").append(this.column).append(".").toString());
            }
        }
        char[] cArr3 = this.buffer;
        int i8 = this.bufpos;
        char cHexval = (char) ((hexval(cReadByte2) << 12) | (hexval(ReadByte()) << 8) | (hexval(ReadByte()) << 4) | hexval(ReadByte()));
        cArr3[i8] = cHexval;
        this.column += 4;
        if (i4 == 1) {
            return cHexval;
        }
        backup(i4 - 1);
        return '\\';
    }
}
