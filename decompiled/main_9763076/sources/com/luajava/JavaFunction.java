package com.luajava;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class JavaFunction {
    protected LuaState L;

    public abstract int execute() throws LuaException;

    public JavaFunction(LuaState luaState) {
        this.L = luaState;
    }

    public LuaObject getParam(int i) {
        return this.L.getLuaObject(i);
    }

    public void register(String str) throws LuaException {
        synchronized (this.L) {
            this.L.pushJavaFunction(this);
            this.L.setGlobal(str);
        }
    }
}
