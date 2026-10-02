package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SymbolTable {
    private final int indexMask;
    private final String[] symbols;

    public SymbolTable(int tableSize) {
        this.indexMask = tableSize - 1;
        this.symbols = new String[tableSize];
        addSymbol("$ref", 0, 4, "$ref".hashCode());
        addSymbol(JSON.DEFAULT_TYPE_KEY, 0, JSON.DEFAULT_TYPE_KEY.length(), JSON.DEFAULT_TYPE_KEY.hashCode());
    }

    public String addSymbol(char[] buffer, int offset, int len, int hash) {
        int bucket = hash & this.indexMask;
        String symbol = this.symbols[bucket];
        if (symbol != null) {
            boolean eq = true;
            if (hash == symbol.hashCode() && len == symbol.length()) {
                for (int i = 0; i < len; i++) {
                    if (buffer[offset + i] != symbol.charAt(i)) {
                        eq = false;
                        break;
                    }
                }
            } else {
                eq = false;
            }
            return eq ? symbol : new String(buffer, offset, len);
        }
        String symbol2 = new String(buffer, offset, len).intern();
        this.symbols[bucket] = symbol2;
        return symbol2;
    }

    public String addSymbol(String buffer, int offset, int len, int hash) {
        return addSymbol(buffer, offset, len, hash, false);
    }

    public String addSymbol(String buffer, int offset, int len, int hash, boolean replace) {
        int bucket = hash & this.indexMask;
        String symbol = this.symbols[bucket];
        if (symbol != null) {
            if (hash == symbol.hashCode() && len == symbol.length() && buffer.startsWith(symbol, offset)) {
                return symbol;
            }
            String str = subString(buffer, offset, len);
            if (replace) {
                this.symbols[bucket] = str;
                return str;
            }
            return str;
        }
        String symbol2 = (len == buffer.length() ? buffer : subString(buffer, offset, len)).intern();
        this.symbols[bucket] = symbol2;
        return symbol2;
    }

    private static String subString(String src, int offset, int len) {
        char[] chars = new char[len];
        src.getChars(offset, offset + len, chars, 0);
        return new String(chars);
    }
}
