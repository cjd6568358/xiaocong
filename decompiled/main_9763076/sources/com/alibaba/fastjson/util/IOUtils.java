package com.alibaba.fastjson.util;

import bsh.ParserConstants;
import com.alibaba.fastjson.JSONException;
import com.hzy.tvmao.ir.ac.ACConstants;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.MalformedInputException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Properties;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IOUtils {
    public static final char[] ASCII_CHARS;
    public static final char[] CA;
    static final char[] DigitOnes;
    static final char[] DigitTens;
    public static final int[] IA;
    static final char[] digits;
    public static final boolean[] identifierFlags;
    public static final char[] replaceChars;
    static final int[] sizeTable;
    public static final byte[] specicalFlags_doubleQuotes;
    public static final boolean[] specicalFlags_doubleQuotesFlags;
    public static final byte[] specicalFlags_singleQuotes;
    public static final boolean[] specicalFlags_singleQuotesFlags;
    public static final Properties DEFAULT_PROPERTIES = new Properties();
    public static final Charset UTF8 = Charset.forName(HTTP.UTF_8);
    public static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final boolean[] firstIdentifierFlags = new boolean[256];

    static {
        for (char c = 0; c < firstIdentifierFlags.length; c = (char) (c + 1)) {
            if (c >= 'A' && c <= 'Z') {
                firstIdentifierFlags[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                firstIdentifierFlags[c] = true;
            } else if (c == '_' || c == '$') {
                firstIdentifierFlags[c] = true;
            }
        }
        identifierFlags = new boolean[256];
        for (char c2 = 0; c2 < identifierFlags.length; c2 = (char) (c2 + 1)) {
            if (c2 >= 'A' && c2 <= 'Z') {
                identifierFlags[c2] = true;
            } else if (c2 >= 'a' && c2 <= 'z') {
                identifierFlags[c2] = true;
            } else if (c2 == '_') {
                identifierFlags[c2] = true;
            } else if (c2 >= '0' && c2 <= '9') {
                identifierFlags[c2] = true;
            }
        }
        try {
            loadPropertiesFromFile();
        } catch (Throwable th) {
        }
        specicalFlags_doubleQuotes = new byte[161];
        specicalFlags_singleQuotes = new byte[161];
        specicalFlags_doubleQuotesFlags = new boolean[161];
        specicalFlags_singleQuotesFlags = new boolean[161];
        replaceChars = new char[93];
        specicalFlags_doubleQuotes[0] = 4;
        specicalFlags_doubleQuotes[1] = 4;
        specicalFlags_doubleQuotes[2] = 4;
        specicalFlags_doubleQuotes[3] = 4;
        specicalFlags_doubleQuotes[4] = 4;
        specicalFlags_doubleQuotes[5] = 4;
        specicalFlags_doubleQuotes[6] = 4;
        specicalFlags_doubleQuotes[7] = 4;
        specicalFlags_doubleQuotes[8] = 1;
        specicalFlags_doubleQuotes[9] = 1;
        specicalFlags_doubleQuotes[10] = 1;
        specicalFlags_doubleQuotes[11] = 4;
        specicalFlags_doubleQuotes[12] = 1;
        specicalFlags_doubleQuotes[13] = 1;
        specicalFlags_doubleQuotes[34] = 1;
        specicalFlags_doubleQuotes[92] = 1;
        specicalFlags_singleQuotes[0] = 4;
        specicalFlags_singleQuotes[1] = 4;
        specicalFlags_singleQuotes[2] = 4;
        specicalFlags_singleQuotes[3] = 4;
        specicalFlags_singleQuotes[4] = 4;
        specicalFlags_singleQuotes[5] = 4;
        specicalFlags_singleQuotes[6] = 4;
        specicalFlags_singleQuotes[7] = 4;
        specicalFlags_singleQuotes[8] = 1;
        specicalFlags_singleQuotes[9] = 1;
        specicalFlags_singleQuotes[10] = 1;
        specicalFlags_singleQuotes[11] = 4;
        specicalFlags_singleQuotes[12] = 1;
        specicalFlags_singleQuotes[13] = 1;
        specicalFlags_singleQuotes[92] = 1;
        specicalFlags_singleQuotes[39] = 1;
        for (int i = 14; i <= 31; i++) {
            specicalFlags_doubleQuotes[i] = 4;
            specicalFlags_singleQuotes[i] = 4;
        }
        for (int i2 = ParserConstants.MODASSIGN; i2 < 160; i2++) {
            specicalFlags_doubleQuotes[i2] = 4;
            specicalFlags_singleQuotes[i2] = 4;
        }
        for (int i3 = 0; i3 < 161; i3++) {
            specicalFlags_doubleQuotesFlags[i3] = specicalFlags_doubleQuotes[i3] != 0;
            specicalFlags_singleQuotesFlags[i3] = specicalFlags_singleQuotes[i3] != 0;
        }
        replaceChars[0] = '0';
        replaceChars[1] = '1';
        replaceChars[2] = '2';
        replaceChars[3] = '3';
        replaceChars[4] = '4';
        replaceChars[5] = '5';
        replaceChars[6] = '6';
        replaceChars[7] = '7';
        replaceChars[8] = 'b';
        replaceChars[9] = 't';
        replaceChars[10] = 'n';
        replaceChars[11] = 'v';
        replaceChars[12] = 'f';
        replaceChars[13] = 'r';
        replaceChars[34] = '\"';
        replaceChars[39] = '\'';
        replaceChars[47] = '/';
        replaceChars[92] = '\\';
        ASCII_CHARS = new char[]{'0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'A', '0', 'B', '0', 'C', '0', 'D', '0', 'E', '0', 'F', '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9', '1', 'A', '1', 'B', '1', 'C', '1', 'D', '1', 'E', '1', 'F', '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9', '2', 'A', '2', 'B', '2', 'C', '2', 'D', '2', 'E', '2', 'F'};
        digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        DigitTens = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
        DigitOnes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        sizeTable = new int[]{9, 99, 999, 9999, ACConstants.TAG_REMOTE_PARAMS, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
        CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        IA = new int[256];
        Arrays.fill(IA, -1);
        int iS = CA.length;
        for (int i4 = 0; i4 < iS; i4++) {
            IA[CA[i4]] = i4;
        }
        IA[61] = 0;
    }

    public static String getStringProperty(String name) {
        String prop = null;
        try {
            prop = System.getProperty(name);
        } catch (SecurityException e) {
        }
        if (prop != null) {
            return prop;
        }
        String prop2 = DEFAULT_PROPERTIES.getProperty(name);
        return prop2;
    }

    public static void loadPropertiesFromFile() {
        InputStream imputStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() { // from class: com.alibaba.fastjson.util.IOUtils.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.security.PrivilegedAction
            public InputStream run() {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                return cl != null ? cl.getResourceAsStream("fastjson.properties") : ClassLoader.getSystemResourceAsStream("fastjson.properties");
            }
        });
        if (imputStream != null) {
            try {
                DEFAULT_PROPERTIES.load(imputStream);
                imputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public static void close(Closeable x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
            }
        }
    }

    public static int stringSize(long x) {
        long p = 10;
        for (int i = 1; i < 19; i++) {
            if (x >= p) {
                p *= 10;
            } else {
                return i;
            }
        }
        return 19;
    }

    public static void getChars(long i, int index, char[] buf) {
        int charPos = index;
        char sign = 0;
        if (i < 0) {
            sign = '-';
            i = -i;
        }
        while (i > 2147483647L) {
            long q = i / 100;
            int r = (int) (i - (((q << 6) + (q << 5)) + (q << 2)));
            i = q;
            int charPos2 = charPos - 1;
            buf[charPos2] = DigitOnes[r];
            charPos = charPos2 - 1;
            buf[charPos] = DigitTens[r];
        }
        int i2 = (int) i;
        while (i2 >= 65536) {
            int q2 = i2 / 100;
            int r2 = i2 - (((q2 << 6) + (q2 << 5)) + (q2 << 2));
            i2 = q2;
            int charPos3 = charPos - 1;
            buf[charPos3] = DigitOnes[r2];
            charPos = charPos3 - 1;
            buf[charPos] = DigitTens[r2];
        }
        do {
            int q3 = (52429 * i2) >>> 19;
            charPos--;
            buf[charPos] = digits[i2 - ((q3 << 3) + (q3 << 1))];
            i2 = q3;
        } while (i2 != 0);
        if (sign != 0) {
            buf[charPos - 1] = sign;
        }
    }

    public static void getChars(int i, int index, char[] buf) {
        int p = index;
        char sign = 0;
        if (i < 0) {
            sign = '-';
            i = -i;
        }
        while (i >= 65536) {
            int q = i / 100;
            int r = i - (((q << 6) + (q << 5)) + (q << 2));
            i = q;
            int p2 = p - 1;
            buf[p2] = DigitOnes[r];
            p = p2 - 1;
            buf[p] = DigitTens[r];
        }
        do {
            int q2 = (52429 * i) >>> 19;
            p--;
            buf[p] = digits[i - ((q2 << 3) + (q2 << 1))];
            i = q2;
        } while (i != 0);
        if (sign != 0) {
            buf[p - 1] = sign;
        }
    }

    public static int stringSize(int x) {
        int i = 0;
        while (x > sizeTable[i]) {
            i++;
        }
        return i + 1;
    }

    public static void decode(CharsetDecoder charsetDecoder, ByteBuffer byteBuf, CharBuffer charByte) {
        try {
            CoderResult cr = charsetDecoder.decode(byteBuf, charByte, true);
            if (!cr.isUnderflow()) {
                cr.throwException();
            }
            CoderResult cr2 = charsetDecoder.flush(charByte);
            if (!cr2.isUnderflow()) {
                cr2.throwException();
            }
        } catch (CharacterCodingException x) {
            throw new JSONException("utf8 decode error, " + x.getMessage(), x);
        }
    }

    public static boolean firstIdentifier(char ch) {
        return ch < firstIdentifierFlags.length && firstIdentifierFlags[ch];
    }

    public static boolean isIdent(char ch) {
        return ch < identifierFlags.length && identifierFlags[ch];
    }

    public static byte[] decodeBase64(char[] chars, int offset, int charsLen) {
        int pad;
        int sepCnt;
        int sIx;
        if (charsLen == 0) {
            return new byte[0];
        }
        int sIx2 = offset;
        int eIx = (offset + charsLen) - 1;
        while (sIx2 < eIx && IA[chars[sIx2]] < 0) {
            sIx2++;
        }
        while (eIx > 0 && IA[chars[eIx]] < 0) {
            eIx--;
        }
        if (chars[eIx] == '=') {
            pad = chars[eIx + (-1)] == '=' ? 2 : 1;
        } else {
            pad = 0;
        }
        int cCnt = (eIx - sIx2) + 1;
        if (charsLen > 76) {
            sepCnt = (chars[76] == '\r' ? cCnt / 78 : 0) << 1;
        } else {
            sepCnt = 0;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] bytes = new byte[len];
        int cc = 0;
        int eLen = (len / 3) * 3;
        int d = 0;
        int sIx3 = sIx2;
        while (d < eLen) {
            int sIx4 = sIx3 + 1;
            int i = IA[chars[sIx3]] << 18;
            int sIx5 = sIx4 + 1;
            int i2 = i | (IA[chars[sIx4]] << 12);
            int sIx6 = sIx5 + 1;
            int i3 = i2 | (IA[chars[sIx5]] << 6);
            int sIx7 = sIx6 + 1;
            int i4 = i3 | IA[chars[sIx6]];
            int d2 = d + 1;
            bytes[d] = (byte) (i4 >> 16);
            int d3 = d2 + 1;
            bytes[d2] = (byte) (i4 >> 8);
            int d4 = d3 + 1;
            bytes[d3] = (byte) i4;
            if (sepCnt <= 0 || (cc = cc + 1) != 19) {
                sIx = sIx7;
            } else {
                sIx = sIx7 + 2;
                cc = 0;
            }
            d = d4;
            sIx3 = sIx;
        }
        if (d < len) {
            int i5 = 0;
            int j = 0;
            while (sIx3 <= eIx - pad) {
                i5 |= IA[chars[sIx3]] << (18 - (j * 6));
                j++;
                sIx3++;
            }
            int r = 16;
            while (d < len) {
                bytes[d] = (byte) (i5 >> r);
                r -= 8;
                d++;
            }
        }
        return bytes;
    }

    public static byte[] decodeBase64(String chars, int offset, int charsLen) {
        int pad;
        int sepCnt;
        int sIx;
        if (charsLen == 0) {
            return new byte[0];
        }
        int sIx2 = offset;
        int eIx = (offset + charsLen) - 1;
        while (sIx2 < eIx && IA[chars.charAt(sIx2)] < 0) {
            sIx2++;
        }
        while (eIx > 0 && IA[chars.charAt(eIx)] < 0) {
            eIx--;
        }
        if (chars.charAt(eIx) == '=') {
            pad = chars.charAt(eIx + (-1)) == '=' ? 2 : 1;
        } else {
            pad = 0;
        }
        int cCnt = (eIx - sIx2) + 1;
        if (charsLen > 76) {
            sepCnt = (chars.charAt(76) == '\r' ? cCnt / 78 : 0) << 1;
        } else {
            sepCnt = 0;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] bytes = new byte[len];
        int cc = 0;
        int eLen = (len / 3) * 3;
        int d = 0;
        int sIx3 = sIx2;
        while (d < eLen) {
            int sIx4 = sIx3 + 1;
            int i = IA[chars.charAt(sIx3)] << 18;
            int sIx5 = sIx4 + 1;
            int i2 = i | (IA[chars.charAt(sIx4)] << 12);
            int sIx6 = sIx5 + 1;
            int i3 = i2 | (IA[chars.charAt(sIx5)] << 6);
            int sIx7 = sIx6 + 1;
            int i4 = i3 | IA[chars.charAt(sIx6)];
            int d2 = d + 1;
            bytes[d] = (byte) (i4 >> 16);
            int d3 = d2 + 1;
            bytes[d2] = (byte) (i4 >> 8);
            int d4 = d3 + 1;
            bytes[d3] = (byte) i4;
            if (sepCnt <= 0 || (cc = cc + 1) != 19) {
                sIx = sIx7;
            } else {
                sIx = sIx7 + 2;
                cc = 0;
            }
            d = d4;
            sIx3 = sIx;
        }
        if (d < len) {
            int i5 = 0;
            int j = 0;
            while (sIx3 <= eIx - pad) {
                i5 |= IA[chars.charAt(sIx3)] << (18 - (j * 6));
                j++;
                sIx3++;
            }
            int r = 16;
            while (d < len) {
                bytes[d] = (byte) (i5 >> r);
                r -= 8;
                d++;
            }
        }
        return bytes;
    }

    public static byte[] decodeBase64(String s) {
        int pad;
        int sepCnt;
        int sIx;
        int sLen = s.length();
        if (sLen == 0) {
            return new byte[0];
        }
        int sIx2 = 0;
        int eIx = sLen - 1;
        while (sIx2 < eIx && IA[s.charAt(sIx2) & 255] < 0) {
            sIx2++;
        }
        while (eIx > 0 && IA[s.charAt(eIx) & 255] < 0) {
            eIx--;
        }
        if (s.charAt(eIx) == '=') {
            pad = s.charAt(eIx + (-1)) == '=' ? 2 : 1;
        } else {
            pad = 0;
        }
        int cCnt = (eIx - sIx2) + 1;
        if (sLen > 76) {
            sepCnt = (s.charAt(76) == '\r' ? cCnt / 78 : 0) << 1;
        } else {
            sepCnt = 0;
        }
        int len = (((cCnt - sepCnt) * 6) >> 3) - pad;
        byte[] dArr = new byte[len];
        int cc = 0;
        int eLen = (len / 3) * 3;
        int d = 0;
        int sIx3 = sIx2;
        while (d < eLen) {
            int sIx4 = sIx3 + 1;
            int i = IA[s.charAt(sIx3)] << 18;
            int sIx5 = sIx4 + 1;
            int i2 = i | (IA[s.charAt(sIx4)] << 12);
            int sIx6 = sIx5 + 1;
            int i3 = i2 | (IA[s.charAt(sIx5)] << 6);
            int sIx7 = sIx6 + 1;
            int i4 = i3 | IA[s.charAt(sIx6)];
            int d2 = d + 1;
            dArr[d] = (byte) (i4 >> 16);
            int d3 = d2 + 1;
            dArr[d2] = (byte) (i4 >> 8);
            int d4 = d3 + 1;
            dArr[d3] = (byte) i4;
            if (sepCnt <= 0 || (cc = cc + 1) != 19) {
                sIx = sIx7;
            } else {
                sIx = sIx7 + 2;
                cc = 0;
            }
            d = d4;
            sIx3 = sIx;
        }
        if (d < len) {
            int i5 = 0;
            int j = 0;
            while (sIx3 <= eIx - pad) {
                i5 |= IA[s.charAt(sIx3)] << (18 - (j * 6));
                j++;
                sIx3++;
            }
            int r = 16;
            while (d < len) {
                dArr[d] = (byte) (i5 >> r);
                r -= 8;
                d++;
            }
        }
        return dArr;
    }

    public static int encodeUTF8(char[] chars, int offset, int len, byte[] bytes) {
        int dp;
        int uc;
        int sl = offset + len;
        int dlASCII = 0 + Math.min(len, bytes.length);
        int dp2 = 0;
        int offset2 = offset;
        while (dp2 < dlASCII && chars[offset2] < 128) {
            bytes[dp2] = (byte) chars[offset2];
            dp2++;
            offset2++;
        }
        while (offset2 < sl) {
            int offset3 = offset2 + 1;
            char c = chars[offset2];
            if (c < 128) {
                dp = dp2 + 1;
                bytes[dp2] = (byte) c;
            } else if (c < 2048) {
                int dp3 = dp2 + 1;
                bytes[dp2] = (byte) ((c >> 6) | 192);
                bytes[dp3] = (byte) ((c & '?') | ParserConstants.LSHIFTASSIGN);
                dp = dp3 + 1;
            } else if (c >= 55296 && c < 57344) {
                int ip = offset3 - 1;
                if (Character.isHighSurrogate(c)) {
                    if (sl - ip < 2) {
                        uc = -1;
                    } else {
                        char d = chars[ip + 1];
                        if (Character.isLowSurrogate(d)) {
                            uc = Character.toCodePoint(c, d);
                        } else {
                            throw new JSONException("encodeUTF8 error", new MalformedInputException(1));
                        }
                    }
                } else {
                    if (Character.isLowSurrogate(c)) {
                        throw new JSONException("encodeUTF8 error", new MalformedInputException(1));
                    }
                    uc = c;
                }
                if (uc < 0) {
                    dp = dp2 + 1;
                    bytes[dp2] = 63;
                } else {
                    int dp4 = dp2 + 1;
                    bytes[dp2] = (byte) ((uc >> 18) | 240);
                    int dp5 = dp4 + 1;
                    bytes[dp4] = (byte) (((uc >> 12) & 63) | ParserConstants.LSHIFTASSIGN);
                    int dp6 = dp5 + 1;
                    bytes[dp5] = (byte) (((uc >> 6) & 63) | ParserConstants.LSHIFTASSIGN);
                    bytes[dp6] = (byte) ((uc & 63) | ParserConstants.LSHIFTASSIGN);
                    offset3++;
                    dp = dp6 + 1;
                }
            } else {
                int dp7 = dp2 + 1;
                bytes[dp2] = (byte) ((c >> '\f') | 224);
                int dp8 = dp7 + 1;
                bytes[dp7] = (byte) (((c >> 6) & 63) | ParserConstants.LSHIFTASSIGN);
                dp = dp8 + 1;
                bytes[dp8] = (byte) ((c & '?') | ParserConstants.LSHIFTASSIGN);
            }
            dp2 = dp;
            offset2 = offset3;
        }
        return dp2;
    }

    public static int decodeUTF8(byte[] sa, int sp, int len, char[] da) {
        int sl = sp + len;
        int dlASCII = Math.min(len, da.length);
        int dp = 0;
        int sp2 = sp;
        while (dp < dlASCII && sa[sp2] >= 0) {
            da[dp] = (char) sa[sp2];
            dp++;
            sp2++;
        }
        while (sp2 < sl) {
            int sp3 = sp2 + 1;
            int b1 = sa[sp2];
            if (b1 >= 0) {
                da[dp] = (char) b1;
                dp++;
                sp2 = sp3;
            } else if ((b1 >> 5) == -2 && (b1 & 30) != 0) {
                if (sp3 < sl) {
                    sp2 = sp3 + 1;
                    int b2 = sa[sp3];
                    if ((b2 & 192) != 128) {
                        return -1;
                    }
                    da[dp] = (char) (((b1 << 6) ^ b2) ^ 3968);
                    dp++;
                } else {
                    return -1;
                }
            } else if ((b1 >> 4) == -2) {
                if (sp3 + 1 < sl) {
                    int sp4 = sp3 + 1;
                    int b3 = sa[sp3];
                    int sp5 = sp4 + 1;
                    int b4 = sa[sp4];
                    if ((b1 == -32 && (b3 & 224) == 128) || (b3 & 192) != 128 || (b4 & 192) != 128) {
                        return -1;
                    }
                    char c = (char) (((b1 << 12) ^ (b3 << 6)) ^ ((-123008) ^ b4));
                    boolean isSurrogate = c >= 55296 && c < 57344;
                    if (isSurrogate) {
                        return -1;
                    }
                    da[dp] = c;
                    dp++;
                    sp2 = sp5;
                } else {
                    return -1;
                }
            } else if ((b1 >> 3) == -2 && sp3 + 2 < sl) {
                int sp6 = sp3 + 1;
                int b5 = sa[sp3];
                int sp7 = sp6 + 1;
                int b6 = sa[sp6];
                sp2 = sp7 + 1;
                int b7 = sa[sp7];
                int uc = (((b1 << 18) ^ (b5 << 12)) ^ (b6 << 6)) ^ (3678080 ^ b7);
                if ((b5 & 192) != 128 || (b6 & 192) != 128 || (b7 & 192) != 128 || !Character.isSupplementaryCodePoint(uc)) {
                    return -1;
                }
                int dp2 = dp + 1;
                da[dp] = (char) ((uc >>> 10) + 55232);
                dp = dp2 + 1;
                da[dp2] = (char) ((uc & 1023) + 56320);
            } else {
                return -1;
            }
        }
        return dp;
    }
}
