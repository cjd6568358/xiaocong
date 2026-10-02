package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.SerializableString;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class CharacterEscapes {
    public static final int ESCAPE_CUSTOM = -2;
    public static final int ESCAPE_NONE = 0;
    public static final int ESCAPE_STANDARD = -1;

    public abstract int[] getEscapeCodesForAscii();

    public abstract SerializableString getEscapeSequence(int i);

    public static int[] standardAsciiEscapesForJSON() {
        int[] esc = CharTypes.get7BitOutputEscapes();
        int len = esc.length;
        int[] result = new int[len];
        System.arraycopy(esc, 0, result, 0, esc.length);
        return result;
    }
}
