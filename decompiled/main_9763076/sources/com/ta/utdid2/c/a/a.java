package com.ta.utdid2.c.a;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: compiled from: FastXmlSerializer.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a implements XmlSerializer {
    private static final String[] a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private OutputStream f111a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private Writer f112a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private CharsetEncoder f114a;
    private boolean f;
    private int mPos;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private final char[] f115a = new char[8192];

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ByteBuffer f113a = ByteBuffer.allocate(8192);

    a() {
    }

    static {
        String[] strArr = new String[64];
        strArr[34] = "&quot;";
        strArr[38] = "&amp;";
        strArr[60] = "&lt;";
        strArr[62] = "&gt;";
        a = strArr;
    }

    private void append(char c) throws IOException {
        int i = this.mPos;
        if (i >= 8191) {
            flush();
            i = this.mPos;
        }
        this.f115a[i] = c;
        this.mPos = i + 1;
    }

    private void a(String str, int i, int i2) throws IOException {
        if (i2 > 8192) {
            int i3 = i + i2;
            while (i < i3) {
                int i4 = i + 8192;
                a(str, i, i4 < i3 ? 8192 : i3 - i);
                i = i4;
            }
            return;
        }
        int i5 = this.mPos;
        if (i5 + i2 > 8192) {
            flush();
            i5 = this.mPos;
        }
        str.getChars(i, i + i2, this.f115a, i5);
        this.mPos = i5 + i2;
    }

    private void append(char[] buf, int i, int length) throws IOException {
        if (length > 8192) {
            int i2 = i + length;
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 8192;
                append(buf, i3, i4 < i2 ? 8192 : i2 - i3);
                i3 = i4;
            }
            return;
        }
        int i5 = this.mPos;
        if (i5 + length > 8192) {
            flush();
            i5 = this.mPos;
        }
        System.arraycopy(buf, i, this.f115a, i5, length);
        this.mPos = i5 + length;
    }

    private void append(String str) throws IOException {
        a(str, 0, str.length());
    }

    private void a(String str) throws IOException {
        String str2;
        int i = 0;
        int length = str.length();
        char length2 = (char) a.length;
        String[] strArr = a;
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < length2 && (str2 = strArr[cCharAt]) != null) {
                if (i < i2) {
                    a(str, i, i2 - i);
                }
                i = i2 + 1;
                append(str2);
            }
            i2++;
        }
        if (i < i2) {
            a(str, i, i2 - i);
        }
    }

    private void a(char[] cArr, int i, int i2) throws IOException {
        String str;
        char length = (char) a.length;
        String[] strArr = a;
        int i3 = i + i2;
        int i4 = i;
        while (i < i3) {
            char c = cArr[i];
            if (c < length && (str = strArr[c]) != null) {
                if (i4 < i) {
                    append(cArr, i4, i - i4);
                }
                i4 = i + 1;
                append(str);
            }
            i++;
        }
        if (i4 < i) {
            append(cArr, i4, i - i4);
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer attribute(String namespace, String name, String value) throws IllegalStateException, IOException, IllegalArgumentException {
        append(' ');
        if (namespace != null) {
            append(namespace);
            append(':');
        }
        append(name);
        append("=\"");
        a(value);
        append('\"');
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String text) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String text) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String text) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IllegalStateException, IOException, IllegalArgumentException {
        flush();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer endTag(String namespace, String name) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.f) {
            append(" />\n");
        } else {
            append("</");
            if (namespace != null) {
                append(namespace);
                append(':');
            }
            append(name);
            append(">\n");
        }
        this.f = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String text) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    private void b() throws IOException {
        int iPosition = this.f113a.position();
        if (iPosition > 0) {
            this.f113a.flip();
            this.f111a.write(this.f113a.array(), 0, iPosition);
            this.f113a.clear();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
        if (this.mPos > 0) {
            if (this.f111a != null) {
                CharBuffer charBufferWrap = CharBuffer.wrap(this.f115a, 0, this.mPos);
                CoderResult coderResultEncode = this.f114a.encode(charBufferWrap, this.f113a, true);
                while (!coderResultEncode.isError()) {
                    if (coderResultEncode.isOverflow()) {
                        b();
                        coderResultEncode = this.f114a.encode(charBufferWrap, this.f113a, true);
                    } else {
                        b();
                        this.f111a.flush();
                    }
                }
                throw new IOException(coderResultEncode.toString());
            }
            this.f112a.write(this.f115a, 0, this.mPos);
            this.f112a.flush();
            this.mPos = 0;
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String name) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String namespace, boolean generatePrefix) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String name) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String text) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String text) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String name, boolean state) throws IllegalStateException, IllegalArgumentException {
        if (name.equals("http://xmlpull.org/v1/doc/features.html#indent-output")) {
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream os, String encoding) throws IllegalStateException, IOException, IllegalArgumentException {
        if (os == null) {
            throw new IllegalArgumentException();
        }
        try {
            this.f114a = Charset.forName(encoding).newEncoder();
            this.f111a = os;
        } catch (IllegalCharsetNameException e) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(encoding).initCause(e));
        } catch (UnsupportedCharsetException e2) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(encoding).initCause(e2));
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) throws IllegalStateException, IOException, IllegalArgumentException {
        this.f112a = writer;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String prefix, String namespace) throws IllegalStateException, IOException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String name, Object value) throws IllegalStateException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String encoding, Boolean standalone) throws IllegalStateException, IOException, IllegalArgumentException {
        append("<?xml version='1.0' encoding='utf-8' standalone='" + (standalone.booleanValue() ? "yes" : "no") + "' ?>\n");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer startTag(String namespace, String name) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.f) {
            append(">\n");
        }
        append('<');
        if (namespace != null) {
            append(namespace);
            append(':');
        }
        append(name);
        this.f = true;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(char[] buf, int start, int len) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.f) {
            append(">");
            this.f = false;
        }
        a(buf, start, len);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(String text) throws IllegalStateException, IOException, IllegalArgumentException {
        if (this.f) {
            append(">");
            this.f = false;
        }
        a(text);
        return this;
    }
}
