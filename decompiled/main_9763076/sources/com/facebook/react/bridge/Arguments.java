package com.facebook.react.bridge;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Arguments {
    public static WritableArray createArray() {
        return new WritableNativeArray();
    }

    public static WritableMap createMap() {
        return new WritableNativeMap();
    }

    public static WritableNativeArray fromJavaArgs(Object[] args) {
        WritableNativeArray arguments = new WritableNativeArray();
        for (Object argument : args) {
            if (argument == null) {
                arguments.pushNull();
            } else {
                Class<?> cls = argument.getClass();
                if (cls == Boolean.class) {
                    arguments.pushBoolean(((Boolean) argument).booleanValue());
                } else if (cls == Integer.class) {
                    arguments.pushDouble(((Integer) argument).doubleValue());
                } else if (cls == Double.class) {
                    arguments.pushDouble(((Double) argument).doubleValue());
                } else if (cls == Float.class) {
                    arguments.pushDouble(((Float) argument).doubleValue());
                } else if (cls == String.class) {
                    arguments.pushString(argument.toString());
                } else if (cls == WritableNativeMap.class) {
                    arguments.pushMap((WritableNativeMap) argument);
                } else if (cls == WritableNativeArray.class) {
                    arguments.pushArray((WritableNativeArray) argument);
                } else {
                    throw new RuntimeException("Cannot convert argument of type " + cls);
                }
            }
        }
        return arguments;
    }
}
