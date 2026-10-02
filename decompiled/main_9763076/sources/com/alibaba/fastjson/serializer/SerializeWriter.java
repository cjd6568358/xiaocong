package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.Bugly;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class SerializeWriter extends Writer {
    private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
    private static final ThreadLocal<byte[]> bytesBufLocal = new ThreadLocal<>();
    static final int nonDirectFeatures = ((((((((SerializerFeature.UseSingleQuotes.mask | 0) | SerializerFeature.BrowserCompatible.mask) | SerializerFeature.PrettyFormat.mask) | SerializerFeature.WriteEnumUsingToString.mask) | SerializerFeature.WriteNonStringValueAsString.mask) | SerializerFeature.WriteSlashAsSpecial.mask) | SerializerFeature.IgnoreErrorGetter.mask) | SerializerFeature.WriteClassName.mask) | SerializerFeature.NotWriteDefaultValue.mask;
    protected boolean beanToArray;
    protected boolean browserSecure;
    protected char[] buf;
    protected int count;
    protected boolean disableCircularReferenceDetect;
    protected int features;
    protected char keySeperator;
    protected int maxBufSize;
    protected boolean notWriteDefaultValue;
    protected boolean quoteFieldNames;
    protected long sepcialBits;
    protected boolean sortField;
    protected boolean useSingleQuotes;
    protected boolean writeDirect;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected boolean writeNonStringValueAsString;
    private final Writer writer;

    public SerializeWriter() {
        this((Writer) null);
    }

    public SerializeWriter(Writer writer) {
        this(writer, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
    }

    public SerializeWriter(Writer writer, int defaultFeatures, SerializerFeature... features) {
        this.maxBufSize = -1;
        this.writer = writer;
        this.buf = bufLocal.get();
        if (this.buf != null) {
            bufLocal.set(null);
        } else {
            this.buf = new char[2048];
        }
        int featuresValue = defaultFeatures;
        for (SerializerFeature feature : features) {
            featuresValue |= feature.getMask();
        }
        this.features = featuresValue;
        computeFeatures();
    }

    public void config(SerializerFeature feature, boolean state) {
        if (state) {
            this.features |= feature.getMask();
            if (feature == SerializerFeature.WriteEnumUsingToString) {
                this.features &= SerializerFeature.WriteEnumUsingName.getMask() ^ (-1);
            } else if (feature == SerializerFeature.WriteEnumUsingName) {
                this.features &= SerializerFeature.WriteEnumUsingToString.getMask() ^ (-1);
            }
        } else {
            this.features &= feature.getMask() ^ (-1);
        }
        computeFeatures();
    }

    protected void computeFeatures() {
        long j;
        this.quoteFieldNames = (this.features & SerializerFeature.QuoteFieldNames.mask) != 0;
        this.useSingleQuotes = (this.features & SerializerFeature.UseSingleQuotes.mask) != 0;
        this.sortField = (this.features & SerializerFeature.SortField.mask) != 0;
        this.disableCircularReferenceDetect = (this.features & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
        this.beanToArray = (this.features & SerializerFeature.BeanToArray.mask) != 0;
        this.writeNonStringValueAsString = (this.features & SerializerFeature.WriteNonStringValueAsString.mask) != 0;
        this.notWriteDefaultValue = (this.features & SerializerFeature.NotWriteDefaultValue.mask) != 0;
        this.writeEnumUsingName = (this.features & SerializerFeature.WriteEnumUsingName.mask) != 0;
        this.writeEnumUsingToString = (this.features & SerializerFeature.WriteEnumUsingToString.mask) != 0;
        this.writeDirect = this.quoteFieldNames && (this.features & nonDirectFeatures) == 0 && (this.beanToArray || this.writeEnumUsingName);
        this.keySeperator = this.useSingleQuotes ? '\'' : '\"';
        this.browserSecure = (this.features & SerializerFeature.BrowserSecure.mask) != 0;
        if (this.browserSecure) {
            j = 5764610843043954687L;
        } else {
            j = (this.features & SerializerFeature.WriteSlashAsSpecial.mask) != 0 ? 140758963191807L : 21474836479L;
        }
        this.sepcialBits = j;
    }

    public boolean isEnabled(SerializerFeature feature) {
        return (this.features & feature.mask) != 0;
    }

    public boolean isEnabled(int feature) {
        return (this.features & feature) != 0;
    }

    @Override // java.io.Writer
    public void write(int c) {
        int newcount = this.count + 1;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                flush();
                newcount = 1;
            }
        }
        this.buf[this.count] = (char) c;
        this.count = newcount;
    }

    @Override // java.io.Writer
    public void write(char[] c, int off, int len) {
        if (off < 0 || off > c.length || len < 0 || off + len > c.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (len != 0) {
            int newcount = this.count + len;
            if (newcount > this.buf.length) {
                if (this.writer == null) {
                    expandCapacity(newcount);
                } else {
                    do {
                        int rest = this.buf.length - this.count;
                        System.arraycopy(c, off, this.buf, this.count, rest);
                        this.count = this.buf.length;
                        flush();
                        len -= rest;
                        off += rest;
                    } while (len > this.buf.length);
                    newcount = len;
                }
            }
            System.arraycopy(c, off, this.buf, this.count, len);
            this.count = newcount;
        }
    }

    public void expandCapacity(int minimumCapacity) {
        if (this.maxBufSize != -1 && minimumCapacity >= this.maxBufSize) {
            throw new JSONException("serialize exceeded MAX_OUTPUT_LENGTH=" + this.maxBufSize + ", minimumCapacity=" + minimumCapacity);
        }
        int newCapacity = this.buf.length + (this.buf.length >> 1) + 1;
        if (newCapacity < minimumCapacity) {
            newCapacity = minimumCapacity;
        }
        char[] newValue = new char[newCapacity];
        System.arraycopy(this.buf, 0, newValue, 0, this.count);
        this.buf = newValue;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence csq) {
        String s = csq == null ? "null" : csq.toString();
        write(s, 0, s.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        String s = csq.subSequence(start, end).toString();
        write(s, 0, s.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(char c) {
        write(c);
        return this;
    }

    @Override // java.io.Writer
    public void write(String str, int off, int len) {
        int newcount = this.count + len;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                do {
                    int rest = this.buf.length - this.count;
                    str.getChars(off, off + rest, this.buf, this.count);
                    this.count = this.buf.length;
                    flush();
                    len -= rest;
                    off += rest;
                } while (len > this.buf.length);
                newcount = len;
            }
        }
        str.getChars(off, off + len, this.buf, this.count);
        this.count = newcount;
    }

    public int writeToEx(OutputStream out, Charset charset) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (charset == IOUtils.UTF8) {
            return encodeToUTF8(out);
        }
        byte[] bytes = new String(this.buf, 0, this.count).getBytes(charset);
        out.write(bytes);
        return bytes.length;
    }

    public byte[] toBytes(Charset charset) {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        return charset == IOUtils.UTF8 ? encodeToUTF8Bytes() : new String(this.buf, 0, this.count).getBytes(charset);
    }

    private int encodeToUTF8(OutputStream out) throws IOException {
        int bytesLength = (int) (((double) this.count) * 3.0d);
        byte[] bytes = bytesBufLocal.get();
        if (bytes == null) {
            bytes = new byte[8192];
            bytesBufLocal.set(bytes);
        }
        if (bytes.length < bytesLength) {
            bytes = new byte[bytesLength];
        }
        int position = IOUtils.encodeUTF8(this.buf, 0, this.count, bytes);
        out.write(bytes, 0, position);
        return position;
    }

    private byte[] encodeToUTF8Bytes() {
        int bytesLength = (int) (((double) this.count) * 3.0d);
        byte[] bytes = bytesBufLocal.get();
        if (bytes == null) {
            bytes = new byte[8192];
            bytesBufLocal.set(bytes);
        }
        if (bytes.length < bytesLength) {
            bytes = new byte[bytesLength];
        }
        int position = IOUtils.encodeUTF8(this.buf, 0, this.count, bytes);
        byte[] copy = new byte[position];
        System.arraycopy(bytes, 0, copy, 0, position);
        return copy;
    }

    public String toString() {
        return new String(this.buf, 0, this.count);
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.writer != null && this.count > 0) {
            flush();
        }
        if (this.buf.length <= 131072) {
            bufLocal.set(this.buf);
        }
        this.buf = null;
    }

    @Override // java.io.Writer
    public void write(String text) {
        if (text == null) {
            writeNull();
        } else {
            write(text, 0, text.length());
        }
    }

    public void writeInt(int i) {
        if (i == Integer.MIN_VALUE) {
            write("-2147483648");
            return;
        }
        int size = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int newcount = this.count + size;
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                char[] chars = new char[size];
                IOUtils.getChars(i, size, chars);
                write(chars, 0, chars.length);
                return;
            }
        }
        IOUtils.getChars(i, newcount, this.buf);
        this.count = newcount;
    }

    public void writeByteArray(byte[] bytes) {
        if (isEnabled(SerializerFeature.WriteClassName.mask)) {
            writeHex(bytes);
            return;
        }
        int bytesLen = bytes.length;
        char quote = this.useSingleQuotes ? '\'' : '\"';
        if (bytesLen == 0) {
            String emptyString = this.useSingleQuotes ? "''" : "\"\"";
            write(emptyString);
            return;
        }
        char[] CA = IOUtils.CA;
        int eLen = (bytesLen / 3) * 3;
        int charsLen = (((bytesLen - 1) / 3) + 1) << 2;
        int offset = this.count;
        int newcount = this.count + charsLen + 2;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(quote);
                int s = 0;
                while (true) {
                    int s2 = s;
                    if (s2 >= eLen) {
                        break;
                    }
                    int s3 = s2 + 1;
                    int i = (bytes[s2] & Constants.NETWORK_TYPE_UNCONNECTED) << 16;
                    int s4 = s3 + 1;
                    int i2 = i | ((bytes[s3] & Constants.NETWORK_TYPE_UNCONNECTED) << 8);
                    s = s4 + 1;
                    int i3 = i2 | (bytes[s4] & Constants.NETWORK_TYPE_UNCONNECTED);
                    write(CA[(i3 >>> 18) & 63]);
                    write(CA[(i3 >>> 12) & 63]);
                    write(CA[(i3 >>> 6) & 63]);
                    write(CA[i3 & 63]);
                }
                int left = bytesLen - eLen;
                if (left > 0) {
                    int i4 = ((bytes[eLen] & Constants.NETWORK_TYPE_UNCONNECTED) << 10) | (left == 2 ? (bytes[bytesLen - 1] & Constants.NETWORK_TYPE_UNCONNECTED) << 2 : 0);
                    write(CA[i4 >> 12]);
                    write(CA[(i4 >>> 6) & 63]);
                    write(left == 2 ? CA[i4 & 63] : '=');
                    write(61);
                }
                write(quote);
                return;
            }
            expandCapacity(newcount);
        }
        this.count = newcount;
        this.buf[offset] = quote;
        int s5 = 0;
        int d = offset + 1;
        while (true) {
            int s6 = s5;
            if (s6 >= eLen) {
                break;
            }
            int s7 = s6 + 1;
            int i5 = (bytes[s6] & Constants.NETWORK_TYPE_UNCONNECTED) << 16;
            int s8 = s7 + 1;
            int i6 = i5 | ((bytes[s7] & Constants.NETWORK_TYPE_UNCONNECTED) << 8);
            s5 = s8 + 1;
            int i7 = i6 | (bytes[s8] & Constants.NETWORK_TYPE_UNCONNECTED);
            int d2 = d + 1;
            this.buf[d] = CA[(i7 >>> 18) & 63];
            int d3 = d2 + 1;
            this.buf[d2] = CA[(i7 >>> 12) & 63];
            int d4 = d3 + 1;
            this.buf[d3] = CA[(i7 >>> 6) & 63];
            d = d4 + 1;
            this.buf[d4] = CA[i7 & 63];
        }
        int left2 = bytesLen - eLen;
        if (left2 > 0) {
            int i8 = ((bytes[eLen] & Constants.NETWORK_TYPE_UNCONNECTED) << 10) | (left2 == 2 ? (bytes[bytesLen - 1] & Constants.NETWORK_TYPE_UNCONNECTED) << 2 : 0);
            this.buf[newcount - 5] = CA[i8 >> 12];
            this.buf[newcount - 4] = CA[(i8 >>> 6) & 63];
            this.buf[newcount - 3] = left2 == 2 ? CA[i8 & 63] : '=';
            this.buf[newcount - 2] = '=';
        }
        this.buf[newcount - 1] = quote;
    }

    public void writeHex(byte[] bytes) {
        int newcount = this.count + (bytes.length * 2) + 3;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                char[] chars = new char[bytes.length + 3];
                int pos = 0 + 1;
                chars[0] = 'x';
                int pos2 = pos + 1;
                chars[pos] = '\'';
                for (byte b : bytes) {
                    int a = b & Constants.NETWORK_TYPE_UNCONNECTED;
                    int b0 = a >> 4;
                    int b1 = a & 15;
                    int pos3 = pos2 + 1;
                    chars[pos2] = (char) ((b0 < 10 ? 48 : 55) + b0);
                    pos2 = pos3 + 1;
                    chars[pos3] = (char) ((b1 < 10 ? 48 : 55) + b1);
                }
                int i = pos2 + 1;
                chars[pos2] = '\'';
                try {
                    this.writer.write(chars);
                    return;
                } catch (IOException ex) {
                    throw new JSONException("writeBytes error.", ex);
                }
            }
            expandCapacity(newcount);
        }
        char[] cArr = this.buf;
        int i2 = this.count;
        this.count = i2 + 1;
        cArr[i2] = 'x';
        char[] cArr2 = this.buf;
        int i3 = this.count;
        this.count = i3 + 1;
        cArr2[i3] = '\'';
        for (byte b2 : bytes) {
            int a2 = b2 & Constants.NETWORK_TYPE_UNCONNECTED;
            int b3 = a2 >> 4;
            int b4 = a2 & 15;
            char[] cArr3 = this.buf;
            int i4 = this.count;
            this.count = i4 + 1;
            cArr3[i4] = (char) ((b3 < 10 ? 48 : 55) + b3);
            char[] cArr4 = this.buf;
            int i5 = this.count;
            this.count = i5 + 1;
            cArr4[i5] = (char) ((b4 < 10 ? 48 : 55) + b4);
        }
        char[] cArr5 = this.buf;
        int i6 = this.count;
        this.count = i6 + 1;
        cArr5[i6] = '\'';
    }

    public void writeFloat(float value, boolean checkWriteClassName) {
        if (Float.isNaN(value) || Float.isInfinite(value)) {
            writeNull();
            return;
        }
        String floatText = Float.toString(value);
        if (isEnabled(SerializerFeature.WriteNullNumberAsZero) && floatText.endsWith(".0")) {
            floatText = floatText.substring(0, floatText.length() - 2);
        }
        write(floatText);
        if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
            write(70);
        }
    }

    public void writeDouble(double doubleValue, boolean checkWriteClassName) {
        if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
            writeNull();
            return;
        }
        String doubleText = Double.toString(doubleValue);
        if (isEnabled(SerializerFeature.WriteNullNumberAsZero) && doubleText.endsWith(".0")) {
            doubleText = doubleText.substring(0, doubleText.length() - 2);
        }
        write(doubleText);
        if (checkWriteClassName && isEnabled(SerializerFeature.WriteClassName)) {
            write(68);
        }
    }

    public void writeEnum(Enum<?> value) {
        if (value == null) {
            writeNull();
            return;
        }
        String strVal = null;
        if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            strVal = value.name();
        } else if (this.writeEnumUsingToString) {
            strVal = value.toString();
        }
        if (strVal != null) {
            char quote = isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : '\"';
            write(quote);
            write(strVal);
            write(quote);
            return;
        }
        writeInt(value.ordinal());
    }

    public void writeLong(long i) {
        boolean needQuotationMark = isEnabled(SerializerFeature.BrowserCompatible) && !isEnabled(SerializerFeature.WriteClassName) && (i > 9007199254740991L || i < -9007199254740991L);
        if (i == Long.MIN_VALUE) {
            if (!needQuotationMark) {
                write("-9223372036854775808");
                return;
            } else {
                write("\"-9223372036854775808\"");
                return;
            }
        }
        int size = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int newcount = this.count + size;
        if (needQuotationMark) {
            newcount += 2;
        }
        if (newcount > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(newcount);
            } else {
                char[] chars = new char[size];
                IOUtils.getChars(i, size, chars);
                if (needQuotationMark) {
                    write(34);
                    write(chars, 0, chars.length);
                    write(34);
                    return;
                }
                write(chars, 0, chars.length);
                return;
            }
        }
        if (needQuotationMark) {
            this.buf[this.count] = '\"';
            IOUtils.getChars(i, newcount - 1, this.buf);
            this.buf[newcount - 1] = '\"';
        } else {
            IOUtils.getChars(i, newcount, this.buf);
        }
        this.count = newcount;
    }

    public void writeNull() {
        write("null");
    }

    public void writeNull(SerializerFeature feature) {
        writeNull(0, feature.mask);
    }

    public void writeNull(int beanFeatures, int feature) {
        if ((beanFeatures & feature) == 0 && (this.features & feature) == 0) {
            writeNull();
            return;
        }
        if (feature == SerializerFeature.WriteNullListAsEmpty.mask) {
            write("[]");
            return;
        }
        if (feature == SerializerFeature.WriteNullStringAsEmpty.mask) {
            writeString(Constants.MAIN_VERSION_TAG);
            return;
        }
        if (feature == SerializerFeature.WriteNullBooleanAsFalse.mask) {
            write(Bugly.SDK_IS_DEV);
        } else if (feature == SerializerFeature.WriteNullNumberAsZero.mask) {
            write(48);
        } else {
            writeNull();
        }
    }

    /* JADX WARN: Code duplicated, block: B:66:0x023a  */
    public void writeStringWithDoubleQuote(String text, char seperator) {
        if (text == null) {
            writeNull();
            if (seperator != 0) {
                write(seperator);
                return;
            }
            return;
        }
        int len = text.length();
        int newcount = this.count + len + 2;
        if (seperator != 0) {
            newcount++;
        }
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(34);
                for (int i = 0; i < text.length(); i++) {
                    char ch = text.charAt(i);
                    if (isEnabled(SerializerFeature.BrowserSecure) && (ch == '(' || ch == ')' || ch == '<' || ch == '>')) {
                        write(92);
                        write(117);
                        write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                        write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                        write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                        write(IOUtils.DIGITS[ch & 15]);
                    } else if (isEnabled(SerializerFeature.BrowserCompatible)) {
                        if (ch == '\b' || ch == '\f' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '\"' || ch == '/' || ch == '\\') {
                            write(92);
                            write(IOUtils.replaceChars[ch]);
                        } else if (ch < ' ') {
                            write(92);
                            write(117);
                            write(48);
                            write(48);
                            write(IOUtils.ASCII_CHARS[ch * 2]);
                            write(IOUtils.ASCII_CHARS[(ch * 2) + 1]);
                        } else if (ch >= 127) {
                            write(92);
                            write(117);
                            write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                            write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                            write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                            write(IOUtils.DIGITS[ch & 15]);
                        } else {
                            write(ch);
                        }
                    } else if ((ch < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch] != 0) || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        if (IOUtils.specicalFlags_doubleQuotes[ch] == 4) {
                            write(117);
                            write(IOUtils.DIGITS[(ch >>> '\f') & 15]);
                            write(IOUtils.DIGITS[(ch >>> '\b') & 15]);
                            write(IOUtils.DIGITS[(ch >>> 4) & 15]);
                            write(IOUtils.DIGITS[ch & 15]);
                        } else {
                            write(IOUtils.replaceChars[ch]);
                        }
                    } else {
                        write(ch);
                    }
                }
                write(34);
                if (seperator != 0) {
                    write(seperator);
                    return;
                }
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count + 1;
        int end = start + len;
        this.buf[this.count] = '\"';
        text.getChars(0, len, this.buf, start);
        this.count = newcount;
        if (isEnabled(SerializerFeature.BrowserCompatible)) {
            int lastSpecialIndex = -1;
            for (int i2 = start; i2 < end; i2++) {
                char ch2 = this.buf[i2];
                if (ch2 == '\"' || ch2 == '/' || ch2 == '\\') {
                    lastSpecialIndex = i2;
                    newcount++;
                } else if (ch2 == '\b' || ch2 == '\f' || ch2 == '\n' || ch2 == '\r' || ch2 == '\t') {
                    lastSpecialIndex = i2;
                    newcount++;
                } else if (ch2 < ' ') {
                    lastSpecialIndex = i2;
                    newcount += 5;
                } else if (ch2 >= 127) {
                    lastSpecialIndex = i2;
                    newcount += 5;
                }
            }
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            this.count = newcount;
            for (int i3 = lastSpecialIndex; i3 >= start; i3--) {
                char ch3 = this.buf[i3];
                if (ch3 == '\b' || ch3 == '\f' || ch3 == '\n' || ch3 == '\r' || ch3 == '\t') {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 2, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = IOUtils.replaceChars[ch3];
                    end++;
                } else if (ch3 == '\"' || ch3 == '/' || ch3 == '\\') {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 2, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = ch3;
                    end++;
                } else if (ch3 < ' ') {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 6, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = 'u';
                    this.buf[i3 + 2] = '0';
                    this.buf[i3 + 3] = '0';
                    this.buf[i3 + 4] = IOUtils.ASCII_CHARS[ch3 * 2];
                    this.buf[i3 + 5] = IOUtils.ASCII_CHARS[(ch3 * 2) + 1];
                    end += 5;
                } else if (ch3 >= 127) {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 6, (end - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = 'u';
                    this.buf[i3 + 2] = IOUtils.DIGITS[(ch3 >>> '\f') & 15];
                    this.buf[i3 + 3] = IOUtils.DIGITS[(ch3 >>> '\b') & 15];
                    this.buf[i3 + 4] = IOUtils.DIGITS[(ch3 >>> 4) & 15];
                    this.buf[i3 + 5] = IOUtils.DIGITS[ch3 & 15];
                    end += 5;
                }
            }
            if (seperator != 0) {
                this.buf[this.count - 2] = '\"';
                this.buf[this.count - 1] = seperator;
                return;
            } else {
                this.buf[this.count - 1] = '\"';
                return;
            }
        }
        int specialCount = 0;
        int lastSpecialIndex2 = -1;
        int firstSpecialIndex = -1;
        char lastSpecial = 0;
        for (int i4 = start; i4 < end; i4++) {
            char ch4 = this.buf[i4];
            if (ch4 >= ']') {
                if (ch4 >= 127 && (ch4 == 8232 || ch4 == 8233 || ch4 < 160)) {
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i4;
                    }
                    specialCount++;
                    lastSpecialIndex2 = i4;
                    lastSpecial = ch4;
                    newcount += 4;
                }
            } else {
                boolean special = (ch4 < '@' && (this.sepcialBits & (1 << ch4)) != 0) || ch4 == '\\';
                if (special) {
                    specialCount++;
                    lastSpecialIndex2 = i4;
                    lastSpecial = ch4;
                    if (ch4 == '(' || ch4 == ')' || ch4 == '<' || ch4 == '>' || (ch4 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch4] == 4)) {
                        newcount += 4;
                    }
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i4;
                    }
                }
            }
        }
        if (specialCount > 0) {
            int newcount2 = newcount + specialCount;
            if (newcount2 > this.buf.length) {
                expandCapacity(newcount2);
            }
            this.count = newcount2;
            if (specialCount == 1) {
                if (lastSpecial == 8232) {
                    int srcPos = lastSpecialIndex2 + 1;
                    int destPos = lastSpecialIndex2 + 6;
                    int LengthOfCopy = (end - lastSpecialIndex2) - 1;
                    System.arraycopy(this.buf, srcPos, this.buf, destPos, LengthOfCopy);
                    this.buf[lastSpecialIndex2] = '\\';
                    int lastSpecialIndex3 = lastSpecialIndex2 + 1;
                    this.buf[lastSpecialIndex3] = 'u';
                    int lastSpecialIndex4 = lastSpecialIndex3 + 1;
                    this.buf[lastSpecialIndex4] = '2';
                    int lastSpecialIndex5 = lastSpecialIndex4 + 1;
                    this.buf[lastSpecialIndex5] = '0';
                    int lastSpecialIndex6 = lastSpecialIndex5 + 1;
                    this.buf[lastSpecialIndex6] = '2';
                    this.buf[lastSpecialIndex6 + 1] = '8';
                } else if (lastSpecial == 8233) {
                    int srcPos2 = lastSpecialIndex2 + 1;
                    int destPos2 = lastSpecialIndex2 + 6;
                    int LengthOfCopy2 = (end - lastSpecialIndex2) - 1;
                    System.arraycopy(this.buf, srcPos2, this.buf, destPos2, LengthOfCopy2);
                    this.buf[lastSpecialIndex2] = '\\';
                    int lastSpecialIndex7 = lastSpecialIndex2 + 1;
                    this.buf[lastSpecialIndex7] = 'u';
                    int lastSpecialIndex8 = lastSpecialIndex7 + 1;
                    this.buf[lastSpecialIndex8] = '2';
                    int lastSpecialIndex9 = lastSpecialIndex8 + 1;
                    this.buf[lastSpecialIndex9] = '0';
                    int lastSpecialIndex10 = lastSpecialIndex9 + 1;
                    this.buf[lastSpecialIndex10] = '2';
                    this.buf[lastSpecialIndex10 + 1] = '9';
                } else if (lastSpecial == '(' || lastSpecial == ')' || lastSpecial == '<' || lastSpecial == '>') {
                    int srcPos3 = lastSpecialIndex2 + 1;
                    int destPos3 = lastSpecialIndex2 + 6;
                    int LengthOfCopy3 = (end - lastSpecialIndex2) - 1;
                    System.arraycopy(this.buf, srcPos3, this.buf, destPos3, LengthOfCopy3);
                    this.buf[lastSpecialIndex2] = '\\';
                    int lastSpecialIndex11 = lastSpecialIndex2 + 1;
                    this.buf[lastSpecialIndex11] = 'u';
                    char ch5 = lastSpecial;
                    int lastSpecialIndex12 = lastSpecialIndex11 + 1;
                    this.buf[lastSpecialIndex12] = IOUtils.DIGITS[(ch5 >>> '\f') & 15];
                    int lastSpecialIndex13 = lastSpecialIndex12 + 1;
                    this.buf[lastSpecialIndex13] = IOUtils.DIGITS[(ch5 >>> '\b') & 15];
                    int lastSpecialIndex14 = lastSpecialIndex13 + 1;
                    this.buf[lastSpecialIndex14] = IOUtils.DIGITS[(ch5 >>> 4) & 15];
                    this.buf[lastSpecialIndex14 + 1] = IOUtils.DIGITS[ch5 & 15];
                } else {
                    char ch6 = lastSpecial;
                    if (ch6 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch6] == 4) {
                        int srcPos4 = lastSpecialIndex2 + 1;
                        int destPos4 = lastSpecialIndex2 + 6;
                        int LengthOfCopy4 = (end - lastSpecialIndex2) - 1;
                        System.arraycopy(this.buf, srcPos4, this.buf, destPos4, LengthOfCopy4);
                        int bufIndex = lastSpecialIndex2;
                        int bufIndex2 = bufIndex + 1;
                        this.buf[bufIndex] = '\\';
                        int bufIndex3 = bufIndex2 + 1;
                        this.buf[bufIndex2] = 'u';
                        int bufIndex4 = bufIndex3 + 1;
                        this.buf[bufIndex3] = IOUtils.DIGITS[(ch6 >>> '\f') & 15];
                        int bufIndex5 = bufIndex4 + 1;
                        this.buf[bufIndex4] = IOUtils.DIGITS[(ch6 >>> '\b') & 15];
                        int bufIndex6 = bufIndex5 + 1;
                        this.buf[bufIndex5] = IOUtils.DIGITS[(ch6 >>> 4) & 15];
                        int i5 = bufIndex6 + 1;
                        this.buf[bufIndex6] = IOUtils.DIGITS[ch6 & 15];
                    } else {
                        int srcPos5 = lastSpecialIndex2 + 1;
                        int destPos5 = lastSpecialIndex2 + 2;
                        int LengthOfCopy5 = (end - lastSpecialIndex2) - 1;
                        System.arraycopy(this.buf, srcPos5, this.buf, destPos5, LengthOfCopy5);
                        this.buf[lastSpecialIndex2] = '\\';
                        this.buf[lastSpecialIndex2 + 1] = IOUtils.replaceChars[ch6];
                    }
                }
            } else if (specialCount > 1) {
                int textIndex = firstSpecialIndex - start;
                int bufIndex7 = firstSpecialIndex;
                for (int i6 = textIndex; i6 < text.length(); i6++) {
                    char ch7 = text.charAt(i6);
                    if (this.browserSecure && (ch7 == '(' || ch7 == ')' || ch7 == '<' || ch7 == '>')) {
                        int bufIndex8 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        int bufIndex9 = bufIndex8 + 1;
                        this.buf[bufIndex8] = 'u';
                        int bufIndex10 = bufIndex9 + 1;
                        this.buf[bufIndex9] = IOUtils.DIGITS[(ch7 >>> '\f') & 15];
                        int bufIndex11 = bufIndex10 + 1;
                        this.buf[bufIndex10] = IOUtils.DIGITS[(ch7 >>> '\b') & 15];
                        int bufIndex12 = bufIndex11 + 1;
                        this.buf[bufIndex11] = IOUtils.DIGITS[(ch7 >>> 4) & 15];
                        bufIndex7 = bufIndex12 + 1;
                        this.buf[bufIndex12] = IOUtils.DIGITS[ch7 & 15];
                        end += 5;
                    } else if ((ch7 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch7] != 0) || (ch7 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int bufIndex13 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[ch7] == 4) {
                            int bufIndex14 = bufIndex13 + 1;
                            this.buf[bufIndex13] = 'u';
                            int bufIndex15 = bufIndex14 + 1;
                            this.buf[bufIndex14] = IOUtils.DIGITS[(ch7 >>> '\f') & 15];
                            int bufIndex16 = bufIndex15 + 1;
                            this.buf[bufIndex15] = IOUtils.DIGITS[(ch7 >>> '\b') & 15];
                            int bufIndex17 = bufIndex16 + 1;
                            this.buf[bufIndex16] = IOUtils.DIGITS[(ch7 >>> 4) & 15];
                            bufIndex7 = bufIndex17 + 1;
                            this.buf[bufIndex17] = IOUtils.DIGITS[ch7 & 15];
                            end += 5;
                        } else {
                            bufIndex7 = bufIndex13 + 1;
                            this.buf[bufIndex13] = IOUtils.replaceChars[ch7];
                            end++;
                        }
                    } else if (ch7 == 8232 || ch7 == 8233) {
                        int bufIndex18 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        int bufIndex19 = bufIndex18 + 1;
                        this.buf[bufIndex18] = 'u';
                        int bufIndex20 = bufIndex19 + 1;
                        this.buf[bufIndex19] = IOUtils.DIGITS[(ch7 >>> '\f') & 15];
                        int bufIndex21 = bufIndex20 + 1;
                        this.buf[bufIndex20] = IOUtils.DIGITS[(ch7 >>> '\b') & 15];
                        int bufIndex22 = bufIndex21 + 1;
                        this.buf[bufIndex21] = IOUtils.DIGITS[(ch7 >>> 4) & 15];
                        bufIndex7 = bufIndex22 + 1;
                        this.buf[bufIndex22] = IOUtils.DIGITS[ch7 & 15];
                        end += 5;
                    } else {
                        this.buf[bufIndex7] = ch7;
                        bufIndex7++;
                    }
                }
            }
        }
        if (seperator != 0) {
            this.buf[this.count - 2] = '\"';
            this.buf[this.count - 1] = seperator;
        } else {
            this.buf[this.count - 1] = '\"';
        }
    }

    public void write(boolean value) {
        if (value) {
            write("true");
        } else {
            write(Bugly.SDK_IS_DEV);
        }
    }

    public void writeFieldValue(char seperator, String name, int value) {
        if (value == Integer.MIN_VALUE || !this.quoteFieldNames) {
            write(seperator);
            writeFieldName(name);
            writeInt(value);
            return;
        }
        int intSize = value < 0 ? IOUtils.stringSize(-value) + 1 : IOUtils.stringSize(value);
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeFieldName(name);
                writeInt(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        this.buf[start] = seperator;
        int nameEnd = start + nameLen + 1;
        this.buf[start + 1] = this.keySeperator;
        name.getChars(0, nameLen, this.buf, start + 2);
        this.buf[nameEnd + 1] = this.keySeperator;
        this.buf[nameEnd + 2] = ':';
        IOUtils.getChars(value, this.count, this.buf);
    }

    public void writeFieldValue(char seperator, String name, long value) {
        if (value == Long.MIN_VALUE || !this.quoteFieldNames) {
            write(seperator);
            writeFieldName(name);
            writeLong(value);
            return;
        }
        int intSize = value < 0 ? IOUtils.stringSize(-value) + 1 : IOUtils.stringSize(value);
        int nameLen = name.length();
        int newcount = this.count + nameLen + 4 + intSize;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeFieldName(name);
                writeLong(value);
                return;
            }
            expandCapacity(newcount);
        }
        int start = this.count;
        this.count = newcount;
        this.buf[start] = seperator;
        int nameEnd = start + nameLen + 1;
        this.buf[start + 1] = this.keySeperator;
        name.getChars(0, nameLen, this.buf, start + 2);
        this.buf[nameEnd + 1] = this.keySeperator;
        this.buf[nameEnd + 2] = ':';
        IOUtils.getChars(value, this.count, this.buf);
    }

    public void writeFieldValue(char seperator, String name, double value) {
        write(seperator);
        writeFieldName(name);
        writeDouble(value, false);
    }

    public void writeFieldValue(char seperator, String name, String value) {
        if (this.quoteFieldNames) {
            if (this.useSingleQuotes) {
                write(seperator);
                writeFieldName(name);
                if (value == null) {
                    writeNull();
                    return;
                } else {
                    writeString(value);
                    return;
                }
            }
            if (isEnabled(SerializerFeature.BrowserCompatible)) {
                write(seperator);
                writeStringWithDoubleQuote(name, ':');
                writeStringWithDoubleQuote(value, (char) 0);
                return;
            }
            writeFieldValueStringWithDoubleQuoteCheck(seperator, name, value);
            return;
        }
        write(seperator);
        writeFieldName(name);
        if (value == null) {
            writeNull();
        } else {
            writeString(value);
        }
    }

    public void writeFieldValueStringWithDoubleQuoteCheck(char seperator, String name, String value) {
        int valueLen;
        int newcount;
        int nameLen = name.length();
        int newcount2 = this.count;
        if (value == null) {
            valueLen = 4;
            newcount = newcount2 + nameLen + 8;
        } else {
            valueLen = value.length();
            newcount = newcount2 + nameLen + valueLen + 6;
        }
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                write(seperator);
                writeStringWithDoubleQuote(name, ':');
                writeStringWithDoubleQuote(value, (char) 0);
                return;
            }
            expandCapacity(newcount);
        }
        this.buf[this.count] = seperator;
        int nameStart = this.count + 2;
        int nameEnd = nameStart + nameLen;
        this.buf[this.count + 1] = '\"';
        name.getChars(0, nameLen, this.buf, nameStart);
        this.count = newcount;
        this.buf[nameEnd] = '\"';
        int index = nameEnd + 1;
        int index2 = index + 1;
        this.buf[index] = ':';
        if (value == null) {
            int index3 = index2 + 1;
            this.buf[index2] = 'n';
            int index4 = index3 + 1;
            this.buf[index3] = 'u';
            int index5 = index4 + 1;
            this.buf[index4] = 'l';
            int i = index5 + 1;
            this.buf[index5] = 'l';
            return;
        }
        int index6 = index2 + 1;
        this.buf[index2] = '\"';
        int valueEnd = index6 + valueLen;
        value.getChars(0, valueLen, this.buf, index6);
        int specialCount = 0;
        int lastSpecialIndex = -1;
        int firstSpecialIndex = -1;
        char lastSpecial = 0;
        for (int i2 = index6; i2 < valueEnd; i2++) {
            char ch = this.buf[i2];
            if (ch >= ']') {
                if (ch >= 127 && (ch == 8232 || ch == 8233 || ch < 160)) {
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i2;
                    }
                    specialCount++;
                    lastSpecialIndex = i2;
                    lastSpecial = ch;
                    newcount += 4;
                }
            } else {
                boolean special = (ch < '@' && (this.sepcialBits & (1 << ch)) != 0) || ch == '\\';
                if (special) {
                    specialCount++;
                    lastSpecialIndex = i2;
                    lastSpecial = ch;
                    if (ch == '(' || ch == ')' || ch == '<' || ch == '>' || (ch < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch] == 4)) {
                        newcount += 4;
                    }
                    if (firstSpecialIndex == -1) {
                        firstSpecialIndex = i2;
                    }
                }
            }
        }
        if (specialCount > 0) {
            int newcount3 = newcount + specialCount;
            if (newcount3 > this.buf.length) {
                expandCapacity(newcount3);
            }
            this.count = newcount3;
            if (specialCount == 1) {
                if (lastSpecial == 8232) {
                    int srcPos = lastSpecialIndex + 1;
                    int destPos = lastSpecialIndex + 6;
                    int LengthOfCopy = (valueEnd - lastSpecialIndex) - 1;
                    System.arraycopy(this.buf, srcPos, this.buf, destPos, LengthOfCopy);
                    this.buf[lastSpecialIndex] = '\\';
                    int lastSpecialIndex2 = lastSpecialIndex + 1;
                    this.buf[lastSpecialIndex2] = 'u';
                    int lastSpecialIndex3 = lastSpecialIndex2 + 1;
                    this.buf[lastSpecialIndex3] = '2';
                    int lastSpecialIndex4 = lastSpecialIndex3 + 1;
                    this.buf[lastSpecialIndex4] = '0';
                    int lastSpecialIndex5 = lastSpecialIndex4 + 1;
                    this.buf[lastSpecialIndex5] = '2';
                    this.buf[lastSpecialIndex5 + 1] = '8';
                } else if (lastSpecial == 8233) {
                    int srcPos2 = lastSpecialIndex + 1;
                    int destPos2 = lastSpecialIndex + 6;
                    int LengthOfCopy2 = (valueEnd - lastSpecialIndex) - 1;
                    System.arraycopy(this.buf, srcPos2, this.buf, destPos2, LengthOfCopy2);
                    this.buf[lastSpecialIndex] = '\\';
                    int lastSpecialIndex6 = lastSpecialIndex + 1;
                    this.buf[lastSpecialIndex6] = 'u';
                    int lastSpecialIndex7 = lastSpecialIndex6 + 1;
                    this.buf[lastSpecialIndex7] = '2';
                    int lastSpecialIndex8 = lastSpecialIndex7 + 1;
                    this.buf[lastSpecialIndex8] = '0';
                    int lastSpecialIndex9 = lastSpecialIndex8 + 1;
                    this.buf[lastSpecialIndex9] = '2';
                    this.buf[lastSpecialIndex9 + 1] = '9';
                } else if (lastSpecial == '(' || lastSpecial == ')' || lastSpecial == '<' || lastSpecial == '>') {
                    char ch2 = lastSpecial;
                    int srcPos3 = lastSpecialIndex + 1;
                    int destPos3 = lastSpecialIndex + 6;
                    int LengthOfCopy3 = (valueEnd - lastSpecialIndex) - 1;
                    System.arraycopy(this.buf, srcPos3, this.buf, destPos3, LengthOfCopy3);
                    int bufIndex = lastSpecialIndex;
                    int bufIndex2 = bufIndex + 1;
                    this.buf[bufIndex] = '\\';
                    int bufIndex3 = bufIndex2 + 1;
                    this.buf[bufIndex2] = 'u';
                    int bufIndex4 = bufIndex3 + 1;
                    this.buf[bufIndex3] = IOUtils.DIGITS[(ch2 >>> '\f') & 15];
                    int bufIndex5 = bufIndex4 + 1;
                    this.buf[bufIndex4] = IOUtils.DIGITS[(ch2 >>> '\b') & 15];
                    int bufIndex6 = bufIndex5 + 1;
                    this.buf[bufIndex5] = IOUtils.DIGITS[(ch2 >>> 4) & 15];
                    int i3 = bufIndex6 + 1;
                    this.buf[bufIndex6] = IOUtils.DIGITS[ch2 & 15];
                } else {
                    char ch3 = lastSpecial;
                    if (ch3 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch3] == 4) {
                        int srcPos4 = lastSpecialIndex + 1;
                        int destPos4 = lastSpecialIndex + 6;
                        int LengthOfCopy4 = (valueEnd - lastSpecialIndex) - 1;
                        System.arraycopy(this.buf, srcPos4, this.buf, destPos4, LengthOfCopy4);
                        int bufIndex7 = lastSpecialIndex;
                        int bufIndex8 = bufIndex7 + 1;
                        this.buf[bufIndex7] = '\\';
                        int bufIndex9 = bufIndex8 + 1;
                        this.buf[bufIndex8] = 'u';
                        int bufIndex10 = bufIndex9 + 1;
                        this.buf[bufIndex9] = IOUtils.DIGITS[(ch3 >>> '\f') & 15];
                        int bufIndex11 = bufIndex10 + 1;
                        this.buf[bufIndex10] = IOUtils.DIGITS[(ch3 >>> '\b') & 15];
                        int bufIndex12 = bufIndex11 + 1;
                        this.buf[bufIndex11] = IOUtils.DIGITS[(ch3 >>> 4) & 15];
                        int i4 = bufIndex12 + 1;
                        this.buf[bufIndex12] = IOUtils.DIGITS[ch3 & 15];
                    } else {
                        int srcPos5 = lastSpecialIndex + 1;
                        int destPos5 = lastSpecialIndex + 2;
                        int LengthOfCopy5 = (valueEnd - lastSpecialIndex) - 1;
                        System.arraycopy(this.buf, srcPos5, this.buf, destPos5, LengthOfCopy5);
                        this.buf[lastSpecialIndex] = '\\';
                        this.buf[lastSpecialIndex + 1] = IOUtils.replaceChars[ch3];
                    }
                }
            } else if (specialCount > 1) {
                int textIndex = firstSpecialIndex - index6;
                int bufIndex13 = firstSpecialIndex;
                for (int i5 = textIndex; i5 < value.length(); i5++) {
                    char ch4 = value.charAt(i5);
                    if (this.browserSecure && (ch4 == '(' || ch4 == ')' || ch4 == '<' || ch4 == '>')) {
                        int bufIndex14 = bufIndex13 + 1;
                        this.buf[bufIndex13] = '\\';
                        int bufIndex15 = bufIndex14 + 1;
                        this.buf[bufIndex14] = 'u';
                        int bufIndex16 = bufIndex15 + 1;
                        this.buf[bufIndex15] = IOUtils.DIGITS[(ch4 >>> '\f') & 15];
                        int bufIndex17 = bufIndex16 + 1;
                        this.buf[bufIndex16] = IOUtils.DIGITS[(ch4 >>> '\b') & 15];
                        int bufIndex18 = bufIndex17 + 1;
                        this.buf[bufIndex17] = IOUtils.DIGITS[(ch4 >>> 4) & 15];
                        bufIndex13 = bufIndex18 + 1;
                        this.buf[bufIndex18] = IOUtils.DIGITS[ch4 & 15];
                        valueEnd += 5;
                    } else if ((ch4 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[ch4] != 0) || (ch4 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int bufIndex19 = bufIndex13 + 1;
                        this.buf[bufIndex13] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[ch4] == 4) {
                            int bufIndex20 = bufIndex19 + 1;
                            this.buf[bufIndex19] = 'u';
                            int bufIndex21 = bufIndex20 + 1;
                            this.buf[bufIndex20] = IOUtils.DIGITS[(ch4 >>> '\f') & 15];
                            int bufIndex22 = bufIndex21 + 1;
                            this.buf[bufIndex21] = IOUtils.DIGITS[(ch4 >>> '\b') & 15];
                            int bufIndex23 = bufIndex22 + 1;
                            this.buf[bufIndex22] = IOUtils.DIGITS[(ch4 >>> 4) & 15];
                            bufIndex13 = bufIndex23 + 1;
                            this.buf[bufIndex23] = IOUtils.DIGITS[ch4 & 15];
                            valueEnd += 5;
                        } else {
                            bufIndex13 = bufIndex19 + 1;
                            this.buf[bufIndex19] = IOUtils.replaceChars[ch4];
                            valueEnd++;
                        }
                    } else if (ch4 == 8232 || ch4 == 8233) {
                        int bufIndex24 = bufIndex13 + 1;
                        this.buf[bufIndex13] = '\\';
                        int bufIndex25 = bufIndex24 + 1;
                        this.buf[bufIndex24] = 'u';
                        int bufIndex26 = bufIndex25 + 1;
                        this.buf[bufIndex25] = IOUtils.DIGITS[(ch4 >>> '\f') & 15];
                        int bufIndex27 = bufIndex26 + 1;
                        this.buf[bufIndex26] = IOUtils.DIGITS[(ch4 >>> '\b') & 15];
                        int bufIndex28 = bufIndex27 + 1;
                        this.buf[bufIndex27] = IOUtils.DIGITS[(ch4 >>> 4) & 15];
                        bufIndex13 = bufIndex28 + 1;
                        this.buf[bufIndex28] = IOUtils.DIGITS[ch4 & 15];
                        valueEnd += 5;
                    } else {
                        this.buf[bufIndex13] = ch4;
                        bufIndex13++;
                    }
                }
            }
        }
        this.buf[this.count - 1] = '\"';
    }

    public void writeString(String text) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(text);
        } else {
            writeStringWithDoubleQuote(text, (char) 0);
        }
    }

    public void writeString(char[] chars) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(chars);
        } else {
            String text = new String(chars);
            writeStringWithDoubleQuote(text, (char) 0);
        }
    }

    protected void writeStringWithSingleQuote(String text) {
        if (text == null) {
            int newcount = this.count + 4;
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            "null".getChars(0, 4, this.buf, this.count);
            this.count = newcount;
            return;
        }
        int len = text.length();
        int newcount2 = this.count + len + 2;
        if (newcount2 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                for (int i = 0; i < text.length(); i++) {
                    char ch = text.charAt(i);
                    if (ch <= '\r' || ch == '\\' || ch == '\'' || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[ch]);
                    } else {
                        write(ch);
                    }
                }
                write(39);
                return;
            }
            expandCapacity(newcount2);
        }
        int start = this.count + 1;
        int end = start + len;
        this.buf[this.count] = '\'';
        text.getChars(0, len, this.buf, start);
        this.count = newcount2;
        int specialCount = 0;
        int lastSpecialIndex = -1;
        char lastSpecial = 0;
        for (int i2 = start; i2 < end; i2++) {
            char ch2 = this.buf[i2];
            if (ch2 <= '\r' || ch2 == '\\' || ch2 == '\'' || (ch2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                specialCount++;
                lastSpecialIndex = i2;
                lastSpecial = ch2;
            }
        }
        int newcount3 = newcount2 + specialCount;
        if (newcount3 > this.buf.length) {
            expandCapacity(newcount3);
        }
        this.count = newcount3;
        if (specialCount == 1) {
            System.arraycopy(this.buf, lastSpecialIndex + 1, this.buf, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            this.buf[lastSpecialIndex] = '\\';
            this.buf[lastSpecialIndex + 1] = IOUtils.replaceChars[lastSpecial];
        } else if (specialCount > 1) {
            System.arraycopy(this.buf, lastSpecialIndex + 1, this.buf, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            this.buf[lastSpecialIndex] = '\\';
            int lastSpecialIndex2 = lastSpecialIndex + 1;
            this.buf[lastSpecialIndex2] = IOUtils.replaceChars[lastSpecial];
            int end2 = end + 1;
            for (int i3 = lastSpecialIndex2 - 2; i3 >= start; i3--) {
                char ch3 = this.buf[i3];
                if (ch3 <= '\r' || ch3 == '\\' || ch3 == '\'' || (ch3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    System.arraycopy(this.buf, i3 + 1, this.buf, i3 + 2, (end2 - i3) - 1);
                    this.buf[i3] = '\\';
                    this.buf[i3 + 1] = IOUtils.replaceChars[ch3];
                    end2++;
                }
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    protected void writeStringWithSingleQuote(char[] chars) {
        if (chars == null) {
            int newcount = this.count + 4;
            if (newcount > this.buf.length) {
                expandCapacity(newcount);
            }
            "null".getChars(0, 4, this.buf, this.count);
            this.count = newcount;
            return;
        }
        int len = chars.length;
        int newcount2 = this.count + len + 2;
        if (newcount2 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                for (char ch : chars) {
                    if (ch <= '\r' || ch == '\\' || ch == '\'' || (ch == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[ch]);
                    } else {
                        write(ch);
                    }
                }
                write(39);
                return;
            }
            expandCapacity(newcount2);
        }
        int start = this.count + 1;
        int end = start + len;
        this.buf[this.count] = '\'';
        System.arraycopy(chars, 0, this.buf, start, chars.length);
        this.count = newcount2;
        int specialCount = 0;
        int lastSpecialIndex = -1;
        char lastSpecial = 0;
        for (int i = start; i < end; i++) {
            char ch2 = this.buf[i];
            if (ch2 <= '\r' || ch2 == '\\' || ch2 == '\'' || (ch2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                specialCount++;
                lastSpecialIndex = i;
                lastSpecial = ch2;
            }
        }
        int newcount3 = newcount2 + specialCount;
        if (newcount3 > this.buf.length) {
            expandCapacity(newcount3);
        }
        this.count = newcount3;
        if (specialCount == 1) {
            System.arraycopy(this.buf, lastSpecialIndex + 1, this.buf, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            this.buf[lastSpecialIndex] = '\\';
            this.buf[lastSpecialIndex + 1] = IOUtils.replaceChars[lastSpecial];
        } else if (specialCount > 1) {
            System.arraycopy(this.buf, lastSpecialIndex + 1, this.buf, lastSpecialIndex + 2, (end - lastSpecialIndex) - 1);
            this.buf[lastSpecialIndex] = '\\';
            int lastSpecialIndex2 = lastSpecialIndex + 1;
            this.buf[lastSpecialIndex2] = IOUtils.replaceChars[lastSpecial];
            int end2 = end + 1;
            for (int i2 = lastSpecialIndex2 - 2; i2 >= start; i2--) {
                char ch3 = this.buf[i2];
                if (ch3 <= '\r' || ch3 == '\\' || ch3 == '\'' || (ch3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    System.arraycopy(this.buf, i2 + 1, this.buf, i2 + 2, (end2 - i2) - 1);
                    this.buf[i2] = '\\';
                    this.buf[i2 + 1] = IOUtils.replaceChars[ch3];
                    end2++;
                }
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    public void writeFieldName(String key) {
        writeFieldName(key, false);
    }

    public void writeFieldName(String key, boolean checkSpecial) {
        if (key == null) {
            write("null:");
            return;
        }
        if (this.useSingleQuotes) {
            if (this.quoteFieldNames) {
                writeStringWithSingleQuote(key);
                write(58);
                return;
            } else {
                writeKeyWithSingleQuoteIfHasSpecial(key);
                return;
            }
        }
        if (this.quoteFieldNames) {
            writeStringWithDoubleQuote(key, ':');
            return;
        }
        boolean hashSpecial = key.length() == 0;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            boolean special = (ch < '@' && (this.sepcialBits & (1 << ch)) != 0) || ch == '\\';
            if (special) {
                hashSpecial = true;
                break;
            }
        }
        if (hashSpecial) {
            writeStringWithDoubleQuote(key, ':');
        } else {
            write(key);
            write(58);
        }
    }

    private void writeKeyWithSingleQuoteIfHasSpecial(String text) {
        byte[] specicalFlags_singleQuotes = IOUtils.specicalFlags_singleQuotes;
        int len = text.length();
        int newcount = this.count + len + 1;
        if (newcount > this.buf.length) {
            if (this.writer != null) {
                if (len == 0) {
                    write(39);
                    write(39);
                    write(58);
                    return;
                }
                boolean hasSpecial = false;
                for (int i = 0; i < len; i++) {
                    char ch = text.charAt(i);
                    if (ch < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[ch] != 0) {
                        hasSpecial = true;
                        break;
                    }
                }
                if (hasSpecial) {
                    write(39);
                }
                for (int i2 = 0; i2 < len; i2++) {
                    char ch2 = text.charAt(i2);
                    if (ch2 < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[ch2] != 0) {
                        write(92);
                        write(IOUtils.replaceChars[ch2]);
                    } else {
                        write(ch2);
                    }
                }
                if (hasSpecial) {
                    write(39);
                }
                write(58);
                return;
            }
            expandCapacity(newcount);
        }
        if (len == 0) {
            int newCount = this.count + 3;
            if (newCount > this.buf.length) {
                expandCapacity(this.count + 3);
            }
            char[] cArr = this.buf;
            int i3 = this.count;
            this.count = i3 + 1;
            cArr[i3] = '\'';
            char[] cArr2 = this.buf;
            int i4 = this.count;
            this.count = i4 + 1;
            cArr2[i4] = '\'';
            char[] cArr3 = this.buf;
            int i5 = this.count;
            this.count = i5 + 1;
            cArr3[i5] = ':';
            return;
        }
        int start = this.count;
        int end = start + len;
        text.getChars(0, len, this.buf, start);
        this.count = newcount;
        boolean hasSpecial2 = false;
        int i6 = start;
        while (i6 < end) {
            char ch3 = this.buf[i6];
            if (ch3 < specicalFlags_singleQuotes.length && specicalFlags_singleQuotes[ch3] != 0) {
                if (!hasSpecial2) {
                    newcount += 3;
                    if (newcount > this.buf.length) {
                        expandCapacity(newcount);
                    }
                    this.count = newcount;
                    System.arraycopy(this.buf, i6 + 1, this.buf, i6 + 3, (end - i6) - 1);
                    System.arraycopy(this.buf, 0, this.buf, 1, i6);
                    this.buf[start] = '\'';
                    int i7 = i6 + 1;
                    this.buf[i7] = '\\';
                    i6 = i7 + 1;
                    this.buf[i6] = IOUtils.replaceChars[ch3];
                    end += 2;
                    this.buf[this.count - 2] = '\'';
                    hasSpecial2 = true;
                } else {
                    newcount++;
                    if (newcount > this.buf.length) {
                        expandCapacity(newcount);
                    }
                    this.count = newcount;
                    System.arraycopy(this.buf, i6 + 1, this.buf, i6 + 2, end - i6);
                    this.buf[i6] = '\\';
                    i6++;
                    this.buf[i6] = IOUtils.replaceChars[ch3];
                    end++;
                }
            }
            i6++;
        }
        this.buf[newcount - 1] = ':';
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        if (this.writer != null) {
            try {
                this.writer.write(this.buf, 0, this.count);
                this.writer.flush();
                this.count = 0;
            } catch (IOException e) {
                throw new JSONException(e.getMessage(), e);
            }
        }
    }
}
