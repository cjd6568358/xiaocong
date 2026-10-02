package com.luajava;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class LuaStateFactory {
    private static final List<LuaState> states = new ArrayList();

    private LuaStateFactory() {
    }

    public static synchronized LuaState newLuaState() {
        LuaState luaState;
        int nextStateIndex = getNextStateIndex();
        luaState = new LuaState(nextStateIndex);
        states.add(nextStateIndex, luaState);
        return luaState;
    }

    public static synchronized LuaState getExistingState(int i) {
        return states.get(i);
    }

    public static synchronized int insertLuaState(LuaState luaState) {
        int nextStateIndex;
        for (int i = 0; i < states.size(); i++) {
            LuaState luaState2 = states.get(i);
            if (luaState2 != null && luaState2.getCPtrPeer() == luaState.getCPtrPeer()) {
                nextStateIndex = i;
            }
        }
        nextStateIndex = getNextStateIndex();
        states.set(nextStateIndex, luaState);
        return nextStateIndex;
    }

    public static synchronized void removeLuaState(int i) {
        states.add(i, null);
    }

    private static synchronized int getNextStateIndex() {
        int i;
        i = 0;
        while (i < states.size() && states.get(i) != null) {
            i++;
        }
        return i;
    }
}
