package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.lang.reflect.Type;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EnumDeserializer implements ObjectDeserializer {
    private final Class<?> enumClass;
    protected long[] enumNameHashCodes;
    protected final Enum[] enums;
    protected final Enum[] ordinalEnums;

    public EnumDeserializer(Class<?> enumClass) {
        this.enumClass = enumClass;
        this.ordinalEnums = (Enum[]) enumClass.getEnumConstants();
        long[] enumNameHashCodes = new long[this.ordinalEnums.length];
        this.enumNameHashCodes = new long[this.ordinalEnums.length];
        for (int i = 0; i < this.ordinalEnums.length; i++) {
            String name = this.ordinalEnums[i].name();
            long hash = -3750763034362895579L;
            for (int j = 0; j < name.length(); j++) {
                char ch = name.charAt(j);
                hash = (hash ^ ((long) ch)) * 1099511628211L;
            }
            enumNameHashCodes[i] = hash;
            this.enumNameHashCodes[i] = hash;
        }
        Arrays.sort(this.enumNameHashCodes);
        this.enums = new Enum[this.ordinalEnums.length];
        for (int i2 = 0; i2 < this.enumNameHashCodes.length; i2++) {
            for (int j2 = 0; j2 < enumNameHashCodes.length; j2++) {
                if (this.enumNameHashCodes[i2] == enumNameHashCodes[j2]) {
                    this.enums[i2] = this.ordinalEnums[j2];
                    break;
                }
            }
        }
    }

    public Enum getEnumByHashCode(long hashCode) {
        int enumIndex;
        if (this.enums != null && (enumIndex = Arrays.binarySearch(this.enumNameHashCodes, hashCode)) >= 0) {
            return this.enums[enumIndex];
        }
        return null;
    }

    public Enum<?> valueOf(int ordinal) {
        return this.ordinalEnums[ordinal];
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        try {
            JSONLexer jSONLexer = defaultJSONParser.lexer;
            int i = jSONLexer.token();
            if (i == 2) {
                int iIntValue = jSONLexer.intValue();
                jSONLexer.nextToken(16);
                if (iIntValue < 0 || iIntValue > this.ordinalEnums.length) {
                    throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + iIntValue);
                }
                return (T) this.ordinalEnums[iIntValue];
            }
            if (i == 4) {
                String strStringVal = jSONLexer.stringVal();
                jSONLexer.nextToken(16);
                if (strStringVal.length() != 0) {
                    return (T) Enum.valueOf(this.enumClass, strStringVal);
                }
                return null;
            }
            if (i == 8) {
                jSONLexer.nextToken(16);
                return null;
            }
            throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + defaultJSONParser.parse());
        } catch (JSONException e) {
            throw e;
        } catch (Exception e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
