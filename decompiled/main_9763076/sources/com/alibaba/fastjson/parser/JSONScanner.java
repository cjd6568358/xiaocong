package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IOUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class JSONScanner extends JSONLexerBase {
    private final int len;
    private final String text;

    public JSONScanner(String input) {
        this(input, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(String input, int features) {
        super(features);
        this.text = input;
        this.len = this.text.length();
        this.bp = -1;
        next();
        if (this.ch == 65279) {
            next();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char charAt(int index) {
        if (index >= this.len) {
            return (char) 26;
        }
        return this.text.charAt(index);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final char next() {
        int index = this.bp + 1;
        this.bp = index;
        char cCharAt = index >= this.len ? (char) 26 : this.text.charAt(index);
        this.ch = cCharAt;
        return cCharAt;
    }

    public JSONScanner(char[] input, int inputLength, int features) {
        this(new String(input, 0, inputLength), features);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void copyTo(int offset, int count, char[] dest) {
        this.text.getChars(offset, offset + count, dest, 0);
    }

    static boolean charArrayCompare(String src, int offset, char[] dest) {
        int destLen = dest.length;
        if (destLen + offset > src.length()) {
            return false;
        }
        for (int i = 0; i < destLen; i++) {
            if (dest[i] != src.charAt(offset + i)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final boolean charArrayCompare(char[] chars) {
        return charArrayCompare(this.text, this.bp, chars);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int indexOf(char ch, int startIndex) {
        return this.text.indexOf(ch, startIndex);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String addSymbol(int offset, int len, int hash, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, offset, len, hash);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public byte[] bytesValue() {
        if (this.token == 26) {
            int start = this.np + 1;
            int len = this.sp;
            if (len % 2 != 0) {
                throw new JSONException("illegal state. " + len);
            }
            byte[] bytes = new byte[len / 2];
            for (int i = 0; i < bytes.length; i++) {
                char c0 = this.text.charAt((i * 2) + start);
                char c1 = this.text.charAt((i * 2) + start + 1);
                int b0 = c0 - (c0 <= '9' ? '0' : '7');
                int b1 = c1 - (c1 <= '9' ? '0' : '7');
                bytes[i] = (byte) ((b0 << 4) | b1);
            }
            return bytes;
        }
        return IOUtils.decodeBase64(this.text, this.np + 1, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String stringVal() {
        return !this.hasSpecial ? subString(this.np + 1, this.sp) : new String(this.sbuf, 0, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String subString(int offset, int count) {
        if (ASMUtils.IS_ANDROID) {
            if (count < this.sbuf.length) {
                this.text.getChars(offset, offset + count, this.sbuf, 0);
                return new String(this.sbuf, 0, count);
            }
            char[] chars = new char[count];
            this.text.getChars(offset, offset + count, chars, 0);
            return new String(chars);
        }
        return this.text.substring(offset, offset + count);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char[] sub_chars(int offset, int count) {
        if (ASMUtils.IS_ANDROID && count < this.sbuf.length) {
            this.text.getChars(offset, offset + count, this.sbuf, 0);
            return this.sbuf;
        }
        char[] chars = new char[count];
        this.text.getChars(offset, offset + count, chars, 0);
        return chars;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String numberString() {
        char chLocal = charAt((this.np + this.sp) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        return subString(this.np, sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final BigDecimal decimalValue() {
        char chLocal = charAt((this.np + this.sp) - 1);
        int sp = this.sp;
        if (chLocal == 'L' || chLocal == 'S' || chLocal == 'B' || chLocal == 'F' || chLocal == 'D') {
            sp--;
        }
        int offset = this.np;
        int count = sp;
        if (count < this.sbuf.length) {
            this.text.getChars(offset, offset + count, this.sbuf, 0);
            return new BigDecimal(this.sbuf, 0, count);
        }
        char[] chars = new char[count];
        this.text.getChars(offset, offset + count, chars, 0);
        return new BigDecimal(chars);
    }

    public boolean scanISO8601DateIfMatch() {
        return scanISO8601DateIfMatch(true);
    }

    public boolean scanISO8601DateIfMatch(boolean strict) {
        int rest = this.len - this.bp;
        return scanISO8601DateIfMatch(strict, rest);
    }

    private boolean scanISO8601DateIfMatch(boolean strict, int rest) {
        char M0;
        char M1;
        char d0;
        char d1;
        int hour;
        int minute;
        int seconds;
        int millis;
        char h0;
        char h1;
        char m0;
        char m1;
        char s0;
        char s1;
        char y0;
        char y1;
        char y2;
        char y3;
        char M2;
        char M3;
        char d2;
        char d3;
        char c10;
        if (rest < 8) {
            return false;
        }
        char c0 = charAt(this.bp);
        char c1 = charAt(this.bp + 1);
        char c2 = charAt(this.bp + 2);
        char c3 = charAt(this.bp + 3);
        char c4 = charAt(this.bp + 4);
        char c5 = charAt(this.bp + 5);
        char c6 = charAt(this.bp + 6);
        char c7 = charAt(this.bp + 7);
        if (!strict && rest > 13) {
            char c_r0 = charAt((this.bp + rest) - 1);
            char c_r1 = charAt((this.bp + rest) - 2);
            if (c0 == '/' && c1 == 'D' && c2 == 'a' && c3 == 't' && c4 == 'e' && c5 == '(' && c_r0 == '/' && c_r1 == ')') {
                int plusIndex = -1;
                for (int i = 6; i < rest; i++) {
                    char c = charAt(this.bp + i);
                    if (c == '+') {
                        plusIndex = i;
                    } else if (c < '0' || c > '9') {
                        break;
                    }
                }
                if (plusIndex == -1) {
                    return false;
                }
                int offset = this.bp + 6;
                String numberText = subString(offset, (this.bp + plusIndex) - offset);
                long millis2 = Long.parseLong(numberText);
                this.calendar = Calendar.getInstance(this.timeZone, this.locale);
                this.calendar.setTimeInMillis(millis2);
                this.token = 5;
                return true;
            }
        }
        if (rest == 8 || rest == 14 || ((rest == 16 && ((c10 = charAt(this.bp + 10)) == 'T' || c10 == ' ')) || (rest == 17 && charAt(this.bp + 6) != '-'))) {
            if (strict) {
                return false;
            }
            char c8 = charAt(this.bp + 8);
            boolean c_47 = c4 == '-' && c7 == '-';
            boolean sperate16 = c_47 && rest == 16;
            boolean sperate17 = c_47 && rest == 17;
            if (sperate17 || sperate16) {
                M0 = c5;
                M1 = c6;
                d0 = c8;
                d1 = charAt(this.bp + 9);
            } else {
                M0 = c4;
                M1 = c5;
                d0 = c6;
                d1 = c7;
            }
            if (!checkDate(c0, c1, c2, c3, M0, M1, d0, d1)) {
                return false;
            }
            setCalendar(c0, c1, c2, c3, M0, M1, d0, d1);
            if (rest != 8) {
                char c9 = charAt(this.bp + 9);
                char c11 = charAt(this.bp + 10);
                char c12 = charAt(this.bp + 11);
                char c13 = charAt(this.bp + 12);
                char c14 = charAt(this.bp + 13);
                if ((sperate17 && c11 == 'T' && c14 == ':' && charAt(this.bp + 16) == 'Z') || (sperate16 && ((c11 == ' ' || c11 == 'T') && c14 == ':'))) {
                    h0 = c12;
                    h1 = c13;
                    m0 = charAt(this.bp + 14);
                    m1 = charAt(this.bp + 15);
                    s0 = '0';
                    s1 = '0';
                } else {
                    h0 = c8;
                    h1 = c9;
                    m0 = c11;
                    m1 = c12;
                    s0 = c13;
                    s1 = c14;
                }
                if (!checkTime(h0, h1, m0, m1, s0, s1)) {
                    return false;
                }
                if (rest == 17 && !sperate17) {
                    char S0 = charAt(this.bp + 14);
                    char S1 = charAt(this.bp + 15);
                    char S2 = charAt(this.bp + 16);
                    if (S0 < '0' || S0 > '9' || S1 < '0' || S1 > '9' || S2 < '0' || S2 > '9') {
                        return false;
                    }
                    millis = ((S0 - '0') * 100) + ((S1 - '0') * 10) + (S2 - '0');
                } else {
                    millis = 0;
                }
                hour = ((h0 - '0') * 10) + (h1 - '0');
                minute = ((m0 - '0') * 10) + (m1 - '0');
                seconds = ((s0 - '0') * 10) + (s1 - '0');
            } else {
                hour = 0;
                minute = 0;
                seconds = 0;
                millis = 0;
            }
            this.calendar.set(11, hour);
            this.calendar.set(12, minute);
            this.calendar.set(13, seconds);
            this.calendar.set(14, millis);
            this.token = 5;
            return true;
        }
        if (rest < 9) {
            return false;
        }
        char c15 = charAt(this.bp + 8);
        char c16 = charAt(this.bp + 9);
        int date_len = 10;
        if ((c4 == '-' && c7 == '-') || (c4 == '/' && c7 == '/')) {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;
            M2 = c5;
            M3 = c6;
            d2 = c15;
            d3 = c16;
        } else if (c4 == '-' && c6 == '-') {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;
            M2 = '0';
            M3 = c5;
            if (c15 == ' ') {
                d2 = '0';
                d3 = c7;
                date_len = 8;
            } else {
                d2 = c7;
                d3 = c15;
                date_len = 9;
            }
        } else if ((c2 == '.' && c5 == '.') || (c2 == '-' && c5 == '-')) {
            d2 = c0;
            d3 = c1;
            M2 = c3;
            M3 = c4;
            y0 = c6;
            y1 = c7;
            y2 = c15;
            y3 = c16;
        } else if (c4 == 24180 || c4 == 45380) {
            y0 = c0;
            y1 = c1;
            y2 = c2;
            y3 = c3;
            if (c7 == 26376 || c7 == 50900) {
                M2 = c5;
                M3 = c6;
                if (c16 == 26085 || c16 == 51068) {
                    d2 = '0';
                    d3 = c15;
                } else if (charAt(this.bp + 10) == 26085 || charAt(this.bp + 10) == 51068) {
                    d2 = c15;
                    d3 = c16;
                    date_len = 11;
                } else {
                    return false;
                }
            } else if (c6 == 26376 || c6 == 50900) {
                M2 = '0';
                M3 = c5;
                if (c15 == 26085 || c15 == 51068) {
                    d2 = '0';
                    d3 = c7;
                } else if (c16 == 26085 || c16 == 51068) {
                    d2 = c7;
                    d3 = c15;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        if (!checkDate(y0, y1, y2, y3, M2, M3, d2, d3)) {
            return false;
        }
        setCalendar(y0, y1, y2, y3, M2, M3, d2, d3);
        char t = charAt(this.bp + date_len);
        if (t == 'T' || (t == ' ' && !strict)) {
            if (rest < date_len + 9 || charAt(this.bp + date_len + 3) != ':' || charAt(this.bp + date_len + 6) != ':') {
                return false;
            }
            char h2 = charAt(this.bp + date_len + 1);
            char h3 = charAt(this.bp + date_len + 2);
            char m2 = charAt(this.bp + date_len + 4);
            char m3 = charAt(this.bp + date_len + 5);
            char s2 = charAt(this.bp + date_len + 7);
            char s3 = charAt(this.bp + date_len + 8);
            if (!checkTime(h2, h3, m2, m3, s2, s3)) {
                return false;
            }
            setTime(h2, h3, m2, m3, s2, s3);
            char dot = charAt(this.bp + date_len + 9);
            if (dot == '.') {
                if (rest < date_len + 11) {
                    return false;
                }
                char S3 = charAt(this.bp + date_len + 10);
                if (S3 < '0' || S3 > '9') {
                    return false;
                }
                int millis3 = S3 - '0';
                int millisLen = 1;
                if (rest > date_len + 11) {
                    char S4 = charAt(this.bp + date_len + 11);
                    if (S4 >= '0' && S4 <= '9') {
                        millis3 = (millis3 * 10) + (S4 - '0');
                        millisLen = 2;
                    }
                }
                if (millisLen == 2) {
                    char S5 = charAt(this.bp + date_len + 12);
                    if (S5 >= '0' && S5 <= '9') {
                        millis3 = (millis3 * 10) + (S5 - '0');
                        millisLen = 3;
                    }
                }
                this.calendar.set(14, millis3);
                int timzeZoneLength = 0;
                char timeZoneFlag = charAt(this.bp + date_len + 10 + millisLen);
                if (timeZoneFlag == '+' || timeZoneFlag == '-') {
                    char t0 = charAt(this.bp + date_len + 10 + millisLen + 1);
                    if (t0 < '0' || t0 > '1') {
                        return false;
                    }
                    char t1 = charAt(this.bp + date_len + 10 + millisLen + 2);
                    if (t1 < '0' || t1 > '9') {
                        return false;
                    }
                    char t2 = charAt(this.bp + date_len + 10 + millisLen + 3);
                    char t3 = '0';
                    char t4 = '0';
                    if (t2 == ':') {
                        t3 = charAt(this.bp + date_len + 10 + millisLen + 4);
                        if (t3 != '0' && t3 != '3') {
                            return false;
                        }
                        t4 = charAt(this.bp + date_len + 10 + millisLen + 5);
                        if (t4 != '0') {
                            return false;
                        }
                        timzeZoneLength = 6;
                    } else if (t2 == '0') {
                        t3 = charAt(this.bp + date_len + 10 + millisLen + 4);
                        if (t3 != '0' && t3 != '3') {
                            return false;
                        }
                        timzeZoneLength = 5;
                    } else {
                        timzeZoneLength = 3;
                    }
                    setTimeZone(timeZoneFlag, t0, t1, t3, t4);
                } else if (timeZoneFlag == 'Z') {
                    timzeZoneLength = 1;
                    if (this.calendar.getTimeZone().getRawOffset() != 0) {
                        String[] timeZoneIDs = TimeZone.getAvailableIDs(0);
                        if (timeZoneIDs.length > 0) {
                            TimeZone timeZone = TimeZone.getTimeZone(timeZoneIDs[0]);
                            this.calendar.setTimeZone(timeZone);
                        }
                    }
                }
                char end = charAt(this.bp + date_len + 10 + millisLen + timzeZoneLength);
                if (end != 26 && end != '\"') {
                    return false;
                }
                int i2 = this.bp + date_len + 10 + millisLen + timzeZoneLength;
                this.bp = i2;
                this.ch = charAt(i2);
                this.token = 5;
                return true;
            }
            this.calendar.set(14, 0);
            int i3 = this.bp + date_len + 9;
            this.bp = i3;
            this.ch = charAt(i3);
            this.token = 5;
            if (dot == 'Z' && this.calendar.getTimeZone().getRawOffset() != 0) {
                String[] timeZoneIDs2 = TimeZone.getAvailableIDs(0);
                if (timeZoneIDs2.length > 0) {
                    TimeZone timeZone2 = TimeZone.getTimeZone(timeZoneIDs2[0]);
                    this.calendar.setTimeZone(timeZone2);
                }
            }
            return true;
        }
        if (t == '\"' || t == 26 || t == 26085 || t == 51068) {
            this.calendar.set(11, 0);
            this.calendar.set(12, 0);
            this.calendar.set(13, 0);
            this.calendar.set(14, 0);
            int i4 = this.bp + date_len;
            this.bp = i4;
            this.ch = charAt(i4);
            this.token = 5;
            return true;
        }
        if ((t != '+' && t != '-') || this.len != date_len + 6 || charAt(this.bp + date_len + 3) != ':' || charAt(this.bp + date_len + 4) != '0' || charAt(this.bp + date_len + 5) != '0') {
            return false;
        }
        setTime('0', '0', '0', '0', '0', '0');
        this.calendar.set(14, 0);
        setTimeZone(t, charAt(this.bp + date_len + 1), charAt(this.bp + date_len + 2));
        return true;
    }

    protected void setTime(char h0, char h1, char m0, char m1, char s0, char s1) {
        int hour = ((h0 - '0') * 10) + (h1 - '0');
        int minute = ((m0 - '0') * 10) + (m1 - '0');
        int seconds = ((s0 - '0') * 10) + (s1 - '0');
        this.calendar.set(11, hour);
        this.calendar.set(12, minute);
        this.calendar.set(13, seconds);
    }

    protected void setTimeZone(char timeZoneFlag, char t0, char t1) {
        setTimeZone(timeZoneFlag, t0, t1, '0', '0');
    }

    protected void setTimeZone(char timeZoneFlag, char t0, char t1, char t3, char t4) {
        int timeZoneOffset = ((((t0 - '0') * 10) + (t1 - '0')) * 3600 * 1000) + ((((t3 - '0') * 10) + (t4 - '0')) * 60 * 1000);
        if (timeZoneFlag == '-') {
            timeZoneOffset = -timeZoneOffset;
        }
        if (this.calendar.getTimeZone().getRawOffset() != timeZoneOffset) {
            String[] timeZoneIDs = TimeZone.getAvailableIDs(timeZoneOffset);
            if (timeZoneIDs.length > 0) {
                TimeZone timeZone = TimeZone.getTimeZone(timeZoneIDs[0]);
                this.calendar.setTimeZone(timeZone);
            }
        }
    }

    private boolean checkTime(char h0, char h1, char m0, char m1, char s0, char s1) {
        if (h0 == '0') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 == '1') {
            if (h1 < '0' || h1 > '9') {
                return false;
            }
        } else if (h0 != '2' || h1 < '0' || h1 > '4') {
            return false;
        }
        if (m0 >= '0' && m0 <= '5') {
            if (m1 < '0' || m1 > '9') {
                return false;
            }
        } else if (m0 != '6' || m1 != '0') {
            return false;
        }
        if (s0 >= '0' && s0 <= '5') {
            if (s1 < '0' || s1 > '9') {
                return false;
            }
        } else if (s0 != '6' || s1 != '0') {
            return false;
        }
        return true;
    }

    private void setCalendar(char y0, char y1, char y2, char y3, char M0, char M1, char d0, char d1) {
        this.calendar = Calendar.getInstance(this.timeZone, this.locale);
        int year = ((y0 - '0') * 1000) + ((y1 - '0') * 100) + ((y2 - '0') * 10) + (y3 - '0');
        int month = (((M0 - '0') * 10) + (M1 - '0')) - 1;
        int day = ((d0 - '0') * 10) + (d1 - '0');
        this.calendar.set(1, year);
        this.calendar.set(2, month);
        this.calendar.set(5, day);
    }

    static boolean checkDate(char y0, char y1, char y2, char y3, char M0, char M1, int d0, int d1) {
        if (y0 < '1' || y0 > '3' || y1 < '0' || y1 > '9' || y2 < '0' || y2 > '9' || y3 < '0' || y3 > '9') {
            return false;
        }
        if (M0 == '0') {
            if (M1 < '1' || M1 > '9') {
                return false;
            }
        } else {
            if (M0 != '1') {
                return false;
            }
            if (M1 != '0' && M1 != '1' && M1 != '2') {
                return false;
            }
        }
        if (d0 == 48) {
            if (d1 < 49 || d1 > 57) {
                return false;
            }
        } else if (d0 == 49 || d0 == 50) {
            if (d1 < 48 || d1 > 57) {
                return false;
            }
        } else {
            if (d0 != 51) {
                return false;
            }
            if (d1 != 48 && d1 != 49) {
                return false;
            }
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean isEOF() {
        return this.bp == this.len || (this.ch == 26 && this.bp + 1 == this.len);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public int scanFieldInt(char[] fieldName) {
        int index;
        int index2;
        char ch;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return 0;
        }
        int index3 = this.bp + fieldName.length;
        int index4 = index3 + 1;
        char ch2 = charAt(index3);
        boolean quote = ch2 == '\"';
        if (quote) {
            ch2 = charAt(index4);
            index4++;
        }
        boolean negative = ch2 == '-';
        if (negative) {
            index = index4 + 1;
            ch2 = charAt(index4);
        } else {
            index = index4;
        }
        if (ch2 >= '0' && ch2 <= '9') {
            int value = ch2 - '0';
            while (true) {
                index2 = index + 1;
                ch = charAt(index);
                if (ch < '0' || ch > '9') {
                    break;
                }
                value = (value * 10) + (ch - '0');
                index = index2;
            }
            if (ch == '.') {
                this.matchStat = -1;
                return 0;
            }
            if (value < 0) {
                this.matchStat = -1;
                return 0;
            }
            if (quote) {
                if (ch != '\"') {
                    this.matchStat = -1;
                    return 0;
                }
                ch = charAt(index2);
                index2++;
            }
            while (ch != ',' && ch != '}') {
                if (isWhitespace(ch)) {
                    ch = charAt(index2);
                    index2++;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            }
            this.bp = index2 - 1;
            if (ch == ',') {
                int i = this.bp + 1;
                this.bp = i;
                this.ch = charAt(i);
                this.matchStat = 3;
                this.token = 16;
                return negative ? -value : value;
            }
            if (ch == '}') {
                this.bp = index2 - 1;
                int i2 = this.bp + 1;
                this.bp = i2;
                char ch3 = charAt(i2);
                while (true) {
                    if (ch3 == ',') {
                        this.token = 16;
                        int i3 = this.bp + 1;
                        this.bp = i3;
                        this.ch = charAt(i3);
                        break;
                    }
                    if (ch3 == ']') {
                        this.token = 15;
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        this.ch = charAt(i4);
                        break;
                    }
                    if (ch3 == '}') {
                        this.token = 13;
                        int i5 = this.bp + 1;
                        this.bp = i5;
                        this.ch = charAt(i5);
                        break;
                    }
                    if (ch3 == 26) {
                        this.token = 20;
                        break;
                    }
                    if (isWhitespace(ch3)) {
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        ch3 = charAt(i6);
                    } else {
                        this.bp = startPos;
                        this.ch = startChar;
                        this.matchStat = -1;
                        return 0;
                    }
                }
                this.matchStat = 4;
            }
            return negative ? -value : value;
        }
        this.matchStat = -1;
        return 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String scanFieldString(char[] fieldName) {
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        while (!charArrayCompare(this.text, this.bp, fieldName)) {
            if (isWhitespace(this.ch)) {
                next();
            } else {
                this.matchStat = -2;
                return stringDefaultValue();
            }
        }
        int index = this.bp + fieldName.length;
        int index2 = index + 1;
        char ch = charAt(index);
        if (ch != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int endIndex = indexOf('\"', index2);
        if (endIndex != -1) {
            String stringVal = subString(index2, endIndex - index2);
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
            char ch2 = charAt(endIndex + 1);
            while (ch2 != ',' && ch2 != '}') {
                if (isWhitespace(ch2)) {
                    endIndex++;
                    ch2 = charAt(endIndex + 1);
                } else {
                    this.matchStat = -1;
                    return stringDefaultValue();
                }
            }
            this.bp = endIndex + 1;
            this.ch = ch2;
            String str = stringVal;
            if (ch2 == ',') {
                int i2 = this.bp + 1;
                this.bp = i2;
                this.ch = charAt(i2);
                this.matchStat = 3;
                return str;
            }
            int i3 = this.bp + 1;
            this.bp = i3;
            char ch3 = charAt(i3);
            if (ch3 == ',') {
                this.token = 16;
                int i4 = this.bp + 1;
                this.bp = i4;
                this.ch = charAt(i4);
            } else if (ch3 == ']') {
                this.token = 15;
                int i5 = this.bp + 1;
                this.bp = i5;
                this.ch = charAt(i5);
            } else if (ch3 == '}') {
                this.token = 13;
                int i6 = this.bp + 1;
                this.bp = i6;
                this.ch = charAt(i6);
            } else if (ch3 == 26) {
                this.token = 20;
            } else {
                this.bp = startPos;
                this.ch = startChar;
                this.matchStat = -1;
                String strVal = stringDefaultValue();
                return strVal;
            }
            this.matchStat = 4;
            return str;
        }
        throw new JSONException("unclosed str");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanFieldDate(char[] fieldName) {
        int index;
        Date dateVal;
        int index2;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return null;
        }
        int index3 = this.bp + fieldName.length;
        int index4 = index3 + 1;
        char ch = charAt(index3);
        if (ch == '\"') {
            int endIndex = indexOf('\"', index4);
            if (endIndex == -1) {
                throw new JSONException("unclosed str");
            }
            int rest = endIndex - index4;
            this.bp = index4;
            if (scanISO8601DateIfMatch(false, rest)) {
                dateVal = this.calendar.getTime();
                ch = charAt(endIndex + 1);
                this.bp = startPos;
                while (ch != ',' && ch != '}') {
                    if (isWhitespace(ch)) {
                        endIndex++;
                        ch = charAt(endIndex + 1);
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                }
                this.bp = endIndex + 1;
                this.ch = ch;
            } else {
                this.bp = startPos;
                this.matchStat = -1;
                return null;
            }
        } else if (ch == '-' || (ch >= '0' && ch <= '9')) {
            long millis = 0;
            boolean negative = false;
            if (ch == '-') {
                index = index4 + 1;
                ch = charAt(index4);
                negative = true;
            } else {
                index = index4;
            }
            if (ch >= '0' && ch <= '9') {
                millis = ch - '0';
                while (true) {
                    index2 = index + 1;
                    ch = charAt(index);
                    if (ch < '0' || ch > '9') {
                        break;
                    }
                    millis = (10 * millis) + ((long) (ch - '0'));
                    index = index2;
                }
                if (ch == ',' || ch == '}') {
                    this.bp = index2 - 1;
                }
            }
            if (millis < 0) {
                this.matchStat = -1;
                return null;
            }
            if (negative) {
                millis = -millis;
            }
            dateVal = new Date(millis);
        } else {
            this.matchStat = -1;
            return null;
        }
        if (ch == ',') {
            int i = this.bp + 1;
            this.bp = i;
            this.ch = charAt(i);
            this.matchStat = 3;
            this.token = 16;
            return dateVal;
        }
        int i2 = this.bp + 1;
        this.bp = i2;
        char ch2 = charAt(i2);
        if (ch2 == ',') {
            this.token = 16;
            int i3 = this.bp + 1;
            this.bp = i3;
            this.ch = charAt(i3);
        } else if (ch2 == ']') {
            this.token = 15;
            int i4 = this.bp + 1;
            this.bp = i4;
            this.ch = charAt(i4);
        } else if (ch2 == '}') {
            this.token = 13;
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
        } else if (ch2 == 26) {
            this.token = 20;
        } else {
            this.bp = startPos;
            this.ch = startChar;
            this.matchStat = -1;
            return null;
        }
        this.matchStat = 4;
        return dateVal;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldSymbol(char[] fieldName) {
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return 0L;
        }
        int index = this.bp + fieldName.length;
        int index2 = index + 1;
        if (charAt(index) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long hash = -3750763034362895579L;
        while (true) {
            int index3 = index2;
            index2 = index3 + 1;
            char ch = charAt(index3);
            if (ch == '\"') {
                this.bp = index2;
                char ch2 = charAt(this.bp);
                this.ch = ch2;
                while (ch2 != ',') {
                    if (ch2 == '}') {
                        next();
                        skipWhitespace();
                        char ch3 = getCurrent();
                        if (ch3 == ',') {
                            this.token = 16;
                            int i = this.bp + 1;
                            this.bp = i;
                            this.ch = charAt(i);
                        } else if (ch3 == ']') {
                            this.token = 15;
                            int i2 = this.bp + 1;
                            this.bp = i2;
                            this.ch = charAt(i2);
                        } else if (ch3 == '}') {
                            this.token = 13;
                            int i3 = this.bp + 1;
                            this.bp = i3;
                            this.ch = charAt(i3);
                        } else if (ch3 == 26) {
                            this.token = 20;
                        } else {
                            this.matchStat = -1;
                            return 0L;
                        }
                        this.matchStat = 4;
                        return hash;
                    }
                    if (isWhitespace(ch2)) {
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        ch2 = charAt(i4);
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                }
                int i5 = this.bp + 1;
                this.bp = i5;
                this.ch = charAt(i5);
                this.matchStat = 3;
                return hash;
            }
            if (index2 > this.len) {
                this.matchStat = -1;
                return 0L;
            }
            hash = (hash ^ ((long) ch)) * 1099511628211L;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldLong(char[] fieldName) {
        int index;
        int index2;
        char ch;
        int index3;
        this.matchStat = 0;
        int startPos = this.bp;
        char startChar = this.ch;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return 0L;
        }
        int index4 = this.bp + fieldName.length;
        int index5 = index4 + 1;
        char ch2 = charAt(index4);
        boolean quote = ch2 == '\"';
        if (quote) {
            ch2 = charAt(index5);
            index5++;
        }
        boolean negative = false;
        if (ch2 == '-') {
            index = index5 + 1;
            ch2 = charAt(index5);
            negative = true;
        } else {
            index = index5;
        }
        if (ch2 >= '0' && ch2 <= '9') {
            long value = ch2 - '0';
            while (true) {
                index2 = index + 1;
                ch = charAt(index);
                if (ch < '0' || ch > '9') {
                    break;
                }
                value = (10 * value) + ((long) (ch - '0'));
                index = index2;
            }
            if (ch == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (!quote) {
                index3 = index2;
            } else {
                if (ch != '\"') {
                    this.matchStat = -1;
                    return 0L;
                }
                index3 = index2 + 1;
                ch = charAt(index2);
            }
            if (ch == ',' || ch == '}') {
                this.bp = index3 - 1;
            }
            boolean valid = value >= 0 || (value == Long.MIN_VALUE && negative);
            if (!valid) {
                this.bp = startPos;
                this.ch = startChar;
                this.matchStat = -1;
                return 0L;
            }
            while (true) {
                int index6 = index3;
                if (ch == ',') {
                    int i = this.bp + 1;
                    this.bp = i;
                    this.ch = charAt(i);
                    this.matchStat = 3;
                    this.token = 16;
                    return negative ? -value : value;
                }
                if (ch == '}') {
                    int i2 = this.bp + 1;
                    this.bp = i2;
                    char ch3 = charAt(i2);
                    while (true) {
                        if (ch3 == ',') {
                            this.token = 16;
                            int i3 = this.bp + 1;
                            this.bp = i3;
                            this.ch = charAt(i3);
                            break;
                        }
                        if (ch3 == ']') {
                            this.token = 15;
                            int i4 = this.bp + 1;
                            this.bp = i4;
                            this.ch = charAt(i4);
                            break;
                        }
                        if (ch3 == '}') {
                            this.token = 13;
                            int i5 = this.bp + 1;
                            this.bp = i5;
                            this.ch = charAt(i5);
                            break;
                        }
                        if (ch3 == 26) {
                            this.token = 20;
                            break;
                        }
                        if (isWhitespace(ch3)) {
                            int i6 = this.bp + 1;
                            this.bp = i6;
                            ch3 = charAt(i6);
                        } else {
                            this.bp = startPos;
                            this.ch = startChar;
                            this.matchStat = -1;
                            return 0L;
                        }
                    }
                    this.matchStat = 4;
                    return negative ? -value : value;
                }
                if (isWhitespace(ch)) {
                    this.bp = index6;
                    index3 = index6 + 1;
                    ch = charAt(index6);
                } else {
                    this.matchStat = -1;
                    return 0L;
                }
            }
        } else {
            this.bp = startPos;
            this.ch = startChar;
            this.matchStat = -1;
            return 0L;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean scanFieldBoolean(char[] fieldName) {
        int index;
        char ch;
        boolean z;
        int index2;
        int index3;
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, fieldName)) {
            this.matchStat = -2;
            return false;
        }
        int startPos = this.bp;
        int index4 = this.bp + fieldName.length;
        int index5 = index4 + 1;
        char ch2 = charAt(index4);
        boolean quote = ch2 == '\"';
        if (quote) {
            ch2 = charAt(index5);
            index5++;
        }
        if (ch2 == 't') {
            int index6 = index5 + 1;
            if (charAt(index5) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int index7 = index6 + 1;
            if (charAt(index6) != 'u') {
                this.matchStat = -1;
                return false;
            }
            int index8 = index7 + 1;
            if (charAt(index7) != 'e') {
                this.matchStat = -1;
                return false;
            }
            if (quote) {
                int index9 = index8 + 1;
                if (charAt(index8) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
                index8 = index9;
            }
            this.bp = index8;
            ch = charAt(this.bp);
            z = true;
        } else if (ch2 == 'f') {
            int index10 = index5 + 1;
            if (charAt(index5) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int index11 = index10 + 1;
            if (charAt(index10) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int index12 = index11 + 1;
            if (charAt(index11) != 's') {
                this.matchStat = -1;
                return false;
            }
            int index13 = index12 + 1;
            if (charAt(index12) != 'e') {
                this.matchStat = -1;
                return false;
            }
            if (quote) {
                index3 = index13 + 1;
                if (charAt(index13) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                index3 = index13;
            }
            this.bp = index3;
            ch = charAt(this.bp);
            z = false;
        } else if (ch2 == '1') {
            if (quote) {
                index2 = index5 + 1;
                if (charAt(index5) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                index2 = index5;
            }
            this.bp = index2;
            ch = charAt(this.bp);
            z = true;
        } else if (ch2 == '0') {
            if (quote) {
                index = index5 + 1;
                if (charAt(index5) != '\"') {
                    this.matchStat = -1;
                    return false;
                }
            } else {
                index = index5;
            }
            this.bp = index;
            ch = charAt(this.bp);
            z = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        while (ch != ',') {
            if (ch == '}') {
                int i = this.bp + 1;
                this.bp = i;
                char ch3 = charAt(i);
                while (ch3 != ',') {
                    if (ch3 == ']') {
                        this.token = 15;
                        int i2 = this.bp + 1;
                        this.bp = i2;
                        this.ch = charAt(i2);
                    } else if (ch3 == '}') {
                        this.token = 13;
                        int i3 = this.bp + 1;
                        this.bp = i3;
                        this.ch = charAt(i3);
                    } else if (ch3 == 26) {
                        this.token = 20;
                    } else if (isWhitespace(ch3)) {
                        int i4 = this.bp + 1;
                        this.bp = i4;
                        ch3 = charAt(i4);
                    } else {
                        this.matchStat = -1;
                        return false;
                    }
                    this.matchStat = 4;
                    return z;
                }
                this.token = 16;
                int i5 = this.bp + 1;
                this.bp = i5;
                this.ch = charAt(i5);
                this.matchStat = 4;
                return z;
            }
            if (isWhitespace(ch)) {
                int i6 = this.bp + 1;
                this.bp = i6;
                ch = charAt(i6);
            } else {
                this.bp = startPos;
                charAt(this.bp);
                this.matchStat = -1;
                return false;
            }
        }
        int i7 = this.bp + 1;
        this.bp = i7;
        this.ch = charAt(i7);
        this.matchStat = 3;
        this.token = 16;
        return z;
    }

    /* JADX WARN: Code duplicated, block: B:58:0x00f0 A[PHI: r3
  0x00f0: PHI (r3v5 'offset' int) = (r3v4 'offset' int), (r3v6 'offset' int) binds: [B:37:0x007c, B:41:0x0090] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final int scanInt(char expectNext) {
        int offset;
        int offset2;
        char chLocal;
        int offset3;
        this.matchStat = 0;
        int offset4 = this.bp;
        int offset5 = offset4 + 1;
        char chLocal2 = charAt(offset4);
        while (true) {
            offset = offset5;
            if (!isWhitespace(chLocal2)) {
                break;
            }
            offset5 = offset + 1;
            chLocal2 = charAt(offset);
        }
        boolean quote = chLocal2 == '\"';
        if (quote) {
            offset2 = offset + 1;
            chLocal2 = charAt(offset);
        } else {
            offset2 = offset;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(offset2);
            offset2++;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            int value = chLocal2 - '0';
            while (true) {
                int offset6 = offset2;
                offset2 = offset6 + 1;
                chLocal = charAt(offset6);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                value = (value * 10) + (chLocal - '0');
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0;
            }
            if (!quote) {
                offset3 = offset2;
            } else {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return 0;
                }
                offset3 = offset2 + 1;
                chLocal = charAt(offset2);
            }
            if (value < 0) {
                this.matchStat = -1;
                return 0;
            }
            int offset7 = offset3;
            while (chLocal != expectNext) {
                if (isWhitespace(chLocal)) {
                    chLocal = charAt(offset7);
                    offset7++;
                } else {
                    this.matchStat = -1;
                    if (negative) {
                        value = -value;
                    }
                    return value;
                }
            }
            this.bp = offset7;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            if (negative) {
                value = -value;
            }
            return value;
        }
        if (chLocal2 == 'n') {
            int offset8 = offset2 + 1;
            if (charAt(offset2) == 'u') {
                offset2 = offset8 + 1;
                if (charAt(offset8) == 'l') {
                    int offset9 = offset2 + 1;
                    if (charAt(offset2) == 'l') {
                        this.matchStat = 5;
                        int offset10 = offset9 + 1;
                        char chLocal3 = charAt(offset9);
                        if (quote && chLocal3 == '\"') {
                            chLocal3 = charAt(offset10);
                            offset10++;
                        }
                        while (chLocal3 != ',') {
                            if (chLocal3 == ']') {
                                this.bp = offset10;
                                this.ch = charAt(this.bp);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0;
                            }
                            if (isWhitespace(chLocal3)) {
                                chLocal3 = charAt(offset10);
                                offset10++;
                            } else {
                                this.matchStat = -1;
                                return 0;
                            }
                        }
                        this.bp = offset10;
                        this.ch = charAt(this.bp);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0;
    }

    /* JADX WARN: Code duplicated, block: B:97:0x0270 A[PHI: r9
  0x0270: PHI (r9v3 'offset' int) = (r9v2 'offset' int), (r9v4 'offset' int) binds: [B:75:0x019c, B:79:0x01bc] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public double scanDouble(char seperator) {
        char chLocal;
        int start;
        int count;
        int offset;
        double value;
        this.matchStat = 0;
        int offset2 = this.bp;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(offset2);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            long intVal = chLocal2 - '0';
            while (true) {
                int offset4 = offset3;
                offset3 = offset4 + 1;
                chLocal = charAt(offset4);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                intVal = (10 * intVal) + ((long) (chLocal - '0'));
            }
            long power = 1;
            boolean small = chLocal == '.';
            if (small) {
                int offset5 = offset3 + 1;
                char chLocal3 = charAt(offset3);
                if (chLocal3 >= '0' && chLocal3 <= '9') {
                    intVal = (10 * intVal) + ((long) (chLocal3 - '0'));
                    power = 10;
                    while (true) {
                        offset3 = offset5 + 1;
                        chLocal = charAt(offset5);
                        if (chLocal < '0' || chLocal > '9') {
                            break;
                        }
                        intVal = (10 * intVal) + ((long) (chLocal - '0'));
                        power *= 10;
                        offset5 = offset3;
                    }
                } else {
                    this.matchStat = -1;
                    return 0.0d;
                }
            }
            boolean exp = chLocal == 'e' || chLocal == 'E';
            if (exp) {
                int offset6 = offset3 + 1;
                chLocal = charAt(offset3);
                if (chLocal == '+' || chLocal == '-') {
                    offset3 = offset6 + 1;
                    chLocal = charAt(offset6);
                } else {
                    offset3 = offset6;
                }
                while (chLocal >= '0' && chLocal <= '9') {
                    chLocal = charAt(offset3);
                    offset3++;
                }
            }
            if (quote) {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return 0.0d;
                }
                offset = offset3 + 1;
                chLocal = charAt(offset3);
                start = this.bp + 1;
                count = (offset - start) - 2;
            } else {
                start = this.bp;
                count = (offset3 - start) - 1;
                offset = offset3;
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
                this.bp = offset;
                this.ch = charAt(this.bp);
                this.matchStat = 3;
                this.token = 16;
                return value;
            }
            this.matchStat = -1;
            return value;
        }
        if (chLocal2 == 'n') {
            int offset7 = offset3 + 1;
            if (charAt(offset3) == 'u') {
                offset3 = offset7 + 1;
                if (charAt(offset7) == 'l') {
                    int offset8 = offset3 + 1;
                    if (charAt(offset3) == 'l') {
                        this.matchStat = 5;
                        int offset9 = offset8 + 1;
                        char chLocal4 = charAt(offset8);
                        if (quote && chLocal4 == '\"') {
                            chLocal4 = charAt(offset9);
                            offset9++;
                        }
                        while (chLocal4 != ',') {
                            if (chLocal4 == ']') {
                                this.bp = offset9;
                                this.ch = charAt(this.bp);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0.0d;
                            }
                            if (isWhitespace(chLocal4)) {
                                chLocal4 = charAt(offset9);
                                offset9++;
                            } else {
                                this.matchStat = -1;
                                return 0.0d;
                            }
                        }
                        this.bp = offset9;
                        this.ch = charAt(this.bp);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0.0d;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0.0d;
    }

    /* JADX WARN: Code duplicated, block: B:71:0x011f A[PHI: r3
  0x011f: PHI (r3v3 'offset' int) = (r3v2 'offset' int), (r3v4 'offset' int) binds: [B:49:0x00a1, B:53:0x00b5] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char seperator) {
        char chLocal;
        int offset;
        this.matchStat = 0;
        int offset2 = this.bp;
        int offset3 = offset2 + 1;
        char chLocal2 = charAt(offset2);
        boolean quote = chLocal2 == '\"';
        if (quote) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        boolean negative = chLocal2 == '-';
        if (negative) {
            chLocal2 = charAt(offset3);
            offset3++;
        }
        if (chLocal2 >= '0' && chLocal2 <= '9') {
            long value = chLocal2 - '0';
            while (true) {
                int offset4 = offset3;
                offset3 = offset4 + 1;
                chLocal = charAt(offset4);
                if (chLocal < '0' || chLocal > '9') {
                    break;
                }
                value = (10 * value) + ((long) (chLocal - '0'));
            }
            if (chLocal == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (!quote) {
                offset = offset3;
            } else {
                if (chLocal != '\"') {
                    this.matchStat = -1;
                    return 0L;
                }
                offset = offset3 + 1;
                chLocal = charAt(offset3);
            }
            boolean valid = value >= 0 || (value == Long.MIN_VALUE && negative);
            if (!valid) {
                this.matchStat = -1;
                return 0L;
            }
            int offset5 = offset;
            while (chLocal != seperator) {
                if (isWhitespace(chLocal)) {
                    chLocal = charAt(offset5);
                    offset5++;
                } else {
                    this.matchStat = -1;
                    return value;
                }
            }
            this.bp = offset5;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            if (negative) {
                value = -value;
            }
            return value;
        }
        if (chLocal2 == 'n') {
            int offset6 = offset3 + 1;
            if (charAt(offset3) == 'u') {
                offset3 = offset6 + 1;
                if (charAt(offset6) == 'l') {
                    int offset7 = offset3 + 1;
                    if (charAt(offset3) == 'l') {
                        this.matchStat = 5;
                        int offset8 = offset7 + 1;
                        char chLocal3 = charAt(offset7);
                        if (quote && chLocal3 == '\"') {
                            chLocal3 = charAt(offset8);
                            offset8++;
                        }
                        while (chLocal3 != ',') {
                            if (chLocal3 == ']') {
                                this.bp = offset8;
                                this.ch = charAt(this.bp);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0L;
                            }
                            if (isWhitespace(chLocal3)) {
                                chLocal3 = charAt(offset8);
                                offset8++;
                            } else {
                                this.matchStat = -1;
                                return 0L;
                            }
                        }
                        this.bp = offset8;
                        this.ch = charAt(this.bp);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0L;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0L;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void arrayCopy(int srcPos, char[] dest, int destPos, int length) {
        this.text.getChars(srcPos, srcPos + length, dest, destPos);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        return "pos " + this.bp + ", json : " + (this.text.length() < 65536 ? this.text : this.text.substring(0, 65536));
    }
}
