package com.luajava;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LuaException extends Exception {
    private static final long serialVersionUID = 1;

    public LuaException(String str) {
        super(str);
    }

    public LuaException(Exception exc) {
        super(exc.getCause() != null ? exc.getCause() : exc);
    }
}
