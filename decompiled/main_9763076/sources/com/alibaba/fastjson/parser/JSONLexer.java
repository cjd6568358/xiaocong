package com.alibaba.fastjson.parser;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface JSONLexer {
    byte[] bytesValue();

    void close();

    Number decimalValue(boolean z);

    BigDecimal decimalValue();

    float floatValue();

    char getCurrent();

    Locale getLocale();

    TimeZone getTimeZone();

    String info();

    int intValue();

    Number integerValue();

    boolean isBlankInput();

    boolean isEnabled(int i);

    boolean isEnabled(Feature feature);

    boolean isRef();

    long longValue();

    char next();

    void nextToken();

    void nextToken(int i);

    void nextTokenWithColon();

    void nextTokenWithColon(int i);

    String numberString();

    int pos();

    void resetStringPosition();

    boolean scanBoolean(char c);

    BigDecimal scanDecimal(char c);

    double scanDouble(char c);

    Enum<?> scanEnum(Class<?> cls, SymbolTable symbolTable, char c);

    float scanFloat(char c);

    int scanInt(char c);

    long scanLong(char c);

    void scanNumber();

    String scanString(char c);

    void scanString();

    String scanSymbol(SymbolTable symbolTable);

    String scanSymbol(SymbolTable symbolTable, char c);

    String scanSymbolUnQuoted(SymbolTable symbolTable);

    void skipWhitespace();

    String stringVal();

    int token();

    String tokenName();
}
