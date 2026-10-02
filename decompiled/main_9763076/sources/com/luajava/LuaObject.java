package com.luajava;

import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.Array;
import java.lang.reflect.Proxy;
import java.util.StringTokenizer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LuaObject {
    protected LuaState L;
    protected int ref;

    protected LuaObject(LuaState luaState, String str) {
        synchronized (luaState) {
            this.L = luaState;
            luaState.getGlobal(str);
            registerValue(-1);
            luaState.pop(1);
        }
    }

    protected LuaObject(LuaObject luaObject, String str) throws LuaException {
        synchronized (luaObject.getLuaState()) {
            this.L = luaObject.getLuaState();
            if (!luaObject.isTable() && !luaObject.isUserdata()) {
                throw new LuaException("Object parent should be a table or userdata .");
            }
            luaObject.push();
            this.L.pushString(str);
            this.L.getTable(-2);
            this.L.remove(-2);
            registerValue(-1);
            this.L.pop(1);
        }
    }

    protected LuaObject(LuaObject luaObject, Number number) throws LuaException {
        synchronized (luaObject.getLuaState()) {
            this.L = luaObject.getLuaState();
            if (!luaObject.isTable() && !luaObject.isUserdata()) {
                throw new LuaException("Object parent should be a table or userdata .");
            }
            luaObject.push();
            this.L.pushNumber(number.doubleValue());
            this.L.getTable(-2);
            this.L.remove(-2);
            registerValue(-1);
            this.L.pop(1);
        }
    }

    protected LuaObject(LuaObject luaObject, LuaObject luaObject2) throws LuaException {
        if (luaObject.getLuaState() != luaObject2.getLuaState()) {
            throw new LuaException("LuaStates must be the same!");
        }
        synchronized (luaObject.getLuaState()) {
            if (!luaObject.isTable() && !luaObject.isUserdata()) {
                throw new LuaException("Object parent should be a table or userdata .");
            }
            this.L = luaObject.getLuaState();
            luaObject.push();
            luaObject2.push();
            this.L.getTable(-2);
            this.L.remove(-2);
            registerValue(-1);
            this.L.pop(1);
        }
    }

    protected LuaObject(LuaState luaState, int i) {
        synchronized (luaState) {
            this.L = luaState;
            registerValue(i);
        }
    }

    public LuaState getLuaState() {
        return this.L;
    }

    protected void registerValue(int i) {
        synchronized (this.L) {
            this.L.pushValue(i);
            this.ref = this.L.Lref(LuaState.LUA_REGISTRYINDEX);
        }
    }

    protected void finalize() {
        try {
            synchronized (this.L) {
                if (this.L.getCPtrPeer() != 0) {
                    this.L.LunRef(LuaState.LUA_REGISTRYINDEX, this.ref);
                }
            }
        } catch (Exception e) {
            System.err.println("Unable to release object " + this.ref);
        }
    }

    public void push() {
        this.L.rawGetI(LuaState.LUA_REGISTRYINDEX, this.ref);
    }

    public boolean isNil() {
        boolean zIsNil;
        synchronized (this.L) {
            push();
            zIsNil = this.L.isNil(-1);
            this.L.pop(1);
        }
        return zIsNil;
    }

    public boolean isBoolean() {
        boolean zIsBoolean;
        synchronized (this.L) {
            push();
            zIsBoolean = this.L.isBoolean(-1);
            this.L.pop(1);
        }
        return zIsBoolean;
    }

    public boolean isNumber() {
        boolean zIsNumber;
        synchronized (this.L) {
            push();
            zIsNumber = this.L.isNumber(-1);
            this.L.pop(1);
        }
        return zIsNumber;
    }

    public boolean isString() {
        boolean zIsString;
        synchronized (this.L) {
            push();
            zIsString = this.L.isString(-1);
            this.L.pop(1);
        }
        return zIsString;
    }

    public boolean isFunction() {
        boolean zIsFunction;
        synchronized (this.L) {
            push();
            zIsFunction = this.L.isFunction(-1);
            this.L.pop(1);
        }
        return zIsFunction;
    }

    public boolean isJavaObject() {
        boolean zIsObject;
        synchronized (this.L) {
            push();
            zIsObject = this.L.isObject(-1);
            this.L.pop(1);
        }
        return zIsObject;
    }

    public boolean isJavaFunction() {
        boolean zIsJavaFunction;
        synchronized (this.L) {
            push();
            zIsJavaFunction = this.L.isJavaFunction(-1);
            this.L.pop(1);
        }
        return zIsJavaFunction;
    }

    public boolean isTable() {
        boolean zIsTable;
        synchronized (this.L) {
            push();
            zIsTable = this.L.isTable(-1);
            this.L.pop(1);
        }
        return zIsTable;
    }

    public boolean isUserdata() {
        boolean zIsUserdata;
        synchronized (this.L) {
            push();
            zIsUserdata = this.L.isUserdata(-1);
            this.L.pop(1);
        }
        return zIsUserdata;
    }

    public int type() {
        int iType;
        synchronized (this.L) {
            push();
            iType = this.L.type(-1);
            this.L.pop(1);
        }
        return iType;
    }

    public boolean getBoolean() {
        boolean z;
        synchronized (this.L) {
            push();
            z = this.L.toBoolean(-1);
            this.L.pop(1);
        }
        return z;
    }

    public double getNumber() {
        double number;
        synchronized (this.L) {
            push();
            number = this.L.toNumber(-1);
            this.L.pop(1);
        }
        return number;
    }

    public String getString() {
        String string;
        synchronized (this.L) {
            push();
            string = this.L.toString(-1);
            this.L.pop(1);
        }
        return string;
    }

    public Object getObject() throws LuaException {
        Object objectFromUserdata;
        synchronized (this.L) {
            push();
            objectFromUserdata = this.L.getObjectFromUserdata(-1);
            this.L.pop(1);
        }
        return objectFromUserdata;
    }

    public LuaObject getField(String str) throws LuaException {
        return this.L.getLuaObject(this, str);
    }

    public void setField(String str, Object obj) {
        push();
        try {
            this.L.pushObjectValue(obj);
        } catch (LuaException e) {
            this.L.pushNil();
        }
        this.L.setField(-2, str);
        this.L.pop(1);
    }

    public LuaObject getI(long j) throws LuaException {
        return this.L.getLuaObject(this, Long.valueOf(j));
    }

    public void setI(long j, Object obj) {
        push();
        try {
            this.L.pushObjectValue(obj);
        } catch (LuaException e) {
            this.L.pushNil();
        }
        this.L.setI(-2, j);
        this.L.pop(1);
    }

    public Object[] callx(Object[] objArr, int i) throws LuaException {
        int i2;
        Object[] objArr2;
        String string;
        String str;
        synchronized (this.L) {
            if (!isFunction() && !isTable() && !isUserdata()) {
                throw new LuaException("Invalid object. Not a function, table or userdata .");
            }
            int top = this.L.getTop();
            push();
            if (objArr != null) {
                for (Object obj : objArr) {
                    this.L.pushObjectValue(obj);
                }
            } else {
                i2 = 0;
            }
            int iPcall = this.L.pcall(i2, i, 0);
            if (iPcall != 0) {
                if (this.L.isString(-1)) {
                    string = this.L.toString(-1);
                    this.L.pop(1);
                } else {
                    string = Constants.MAIN_VERSION_TAG;
                }
                if (iPcall == 2) {
                    str = "Runtime error. " + string;
                } else if (iPcall == 4) {
                    str = "Memory allocation error. " + string;
                } else if (iPcall == 6) {
                    str = "Error while running the error handler function. " + string;
                } else {
                    str = "Lua Error code " + iPcall + ". " + string;
                }
                throw new LuaException(str);
            }
            int top2 = i == -1 ? this.L.getTop() - top : i;
            if (this.L.getTop() - top < top2) {
                throw new LuaException("Invalid Number of Results .");
            }
            objArr2 = new Object[top2];
            while (top2 > 0) {
                objArr2[top2 - 1] = this.L.toJavaObject(-1);
                this.L.pop(1);
                top2--;
            }
        }
        return objArr2;
    }

    public Object call(Object... objArr) throws LuaException {
        return callx(objArr, 1)[0];
    }

    public byte[] dump() throws LuaException {
        byte[] bArrDump;
        synchronized (this.L) {
            if (!isFunction()) {
                throw new LuaException("Invalid object. Not a function .");
            }
            push();
            bArrDump = this.L.dump(-1);
            this.L.pop(1);
        }
        return bArrDump;
    }

    public Object[] asArray() throws LuaException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        Object[] objArr;
        synchronized (this.L) {
            if (!isTable()) {
                throw new LuaException("Invalid object. Not a table .");
            }
            push();
            int iObjLen = this.L.objLen(-1);
            Object objNewInstance = Array.newInstance((Class<?>) Object.class, iObjLen);
            for (int i = 1; i <= iObjLen; i++) {
                this.L.pushInteger(i);
                this.L.getTable(-2);
                Array.set(objNewInstance, i - 1, this.L.toJavaObject(-1));
                this.L.pop(1);
            }
            this.L.pop(1);
            objArr = (Object[]) objNewInstance;
        }
        return objArr;
    }

    public String toString() {
        String string = null;
        synchronized (this.L) {
            try {
                if (isNil()) {
                    string = "nil";
                } else if (isBoolean()) {
                    string = String.valueOf(getBoolean());
                } else if (isNumber()) {
                    string = String.valueOf(getNumber());
                } else if (isString()) {
                    string = getString();
                } else if (isFunction()) {
                    string = "Lua Function";
                } else if (isJavaObject()) {
                    string = getObject().toString();
                } else if (isUserdata()) {
                    string = "Userdata";
                } else if (isTable()) {
                    string = "Lua Table";
                } else if (isJavaFunction()) {
                    string = "Java Function";
                }
            } catch (LuaException e) {
            }
        }
        return string;
    }

    public Object createProxy(String str) throws LuaException, ClassNotFoundException {
        Object objNewProxyInstance;
        synchronized (this.L) {
            if (!isTable()) {
                throw new LuaException("Invalid Object. Must be Table.");
            }
            StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
            Class[] clsArr = new Class[stringTokenizer.countTokens()];
            int i = 0;
            while (stringTokenizer.hasMoreTokens()) {
                clsArr[i] = Class.forName(stringTokenizer.nextToken());
                i++;
            }
            objNewProxyInstance = Proxy.newProxyInstance(getClass().getClassLoader(), clsArr, new LuaInvocationHandler(this));
        }
        return objNewProxyInstance;
    }

    public Object createProxy(Class cls) throws LuaException {
        Object objNewProxyInstance;
        synchronized (this.L) {
            if (!isTable()) {
                throw new LuaException("Invalid Object. Must be Table.");
            }
            objNewProxyInstance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{cls}, new LuaInvocationHandler(this));
        }
        return objNewProxyInstance;
    }
}
