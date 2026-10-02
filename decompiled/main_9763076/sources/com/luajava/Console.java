package com.luajava;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Console {
    public static void main(String[] strArr) {
        int i = 0;
        try {
            LuaState luaStateNewLuaState = LuaStateFactory.newLuaState();
            luaStateNewLuaState.openLibs();
            if (strArr.length <= 0) {
                System.out.println("API Lua Java - console mode.");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("> ");
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null || line.equals("exit")) {
                        break;
                    }
                    int iLloadBuffer = luaStateNewLuaState.LloadBuffer(line.getBytes(), "from console");
                    if (iLloadBuffer == 0) {
                        iLloadBuffer = luaStateNewLuaState.pcall(0, 0, 0);
                    }
                    if (iLloadBuffer != 0) {
                        System.err.println("Error on line: " + line);
                        System.err.println(luaStateNewLuaState.toString(-1));
                    }
                    System.out.print("> ");
                }
                luaStateNewLuaState.close();
                return;
            }
            while (true) {
                int i2 = i;
                if (i2 < strArr.length) {
                    int iLloadFile = luaStateNewLuaState.LloadFile(strArr[i2]);
                    if (iLloadFile == 0) {
                        iLloadFile = luaStateNewLuaState.pcall(0, 0, 0);
                    }
                    if (iLloadFile == 0) {
                        i = i2 + 1;
                    } else {
                        throw new LuaException("Error on file: " + strArr[i2] + ". " + luaStateNewLuaState.toString(-1));
                    }
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
