package com.alibaba.fastjson.asm;

import com.tencent.android.tpush.SettingsContentProvider;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TypeCollector {
    private static final Map<String, String> primitives = new HashMap<String, String>() { // from class: com.alibaba.fastjson.asm.TypeCollector.1
        {
            put("int", "I");
            put(SettingsContentProvider.BOOLEAN_TYPE, "Z");
            put("byte", "B");
            put("char", "C");
            put("short", "S");
            put(SettingsContentProvider.FLOAT_TYPE, "F");
            put(SettingsContentProvider.LONG_TYPE, "J");
            put("double", "D");
        }
    };
    protected MethodCollector collector = null;
    private final String methodName;
    private final Class<?>[] parameterTypes;

    public TypeCollector(String methodName, Class<?>[] parameterTypes) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    protected MethodCollector visitMethod(int access, String name, String desc) {
        if (this.collector == null && name.equals(this.methodName)) {
            Type[] argTypes = Type.getArgumentTypes(desc);
            int longOrDoubleQuantity = 0;
            for (Type t : argTypes) {
                String className = t.getClassName();
                if (className.equals(SettingsContentProvider.LONG_TYPE) || className.equals("double")) {
                    longOrDoubleQuantity++;
                }
            }
            if (argTypes.length != this.parameterTypes.length) {
                return null;
            }
            for (int i = 0; i < argTypes.length; i++) {
                if (!correctTypeName(argTypes[i], this.parameterTypes[i].getName())) {
                    return null;
                }
            }
            MethodCollector methodCollector = new MethodCollector(Modifier.isStatic(access) ? 0 : 1, argTypes.length + longOrDoubleQuantity);
            this.collector = methodCollector;
            return methodCollector;
        }
        return null;
    }

    private boolean correctTypeName(Type type, String paramTypeName) {
        String s = type.getClassName();
        String braces = Constants.MAIN_VERSION_TAG;
        while (s.endsWith("[]")) {
            braces = braces + "[";
            s = s.substring(0, s.length() - 2);
        }
        if (!braces.equals(Constants.MAIN_VERSION_TAG)) {
            if (primitives.containsKey(s)) {
                s = braces + primitives.get(s);
            } else {
                s = braces + "L" + s + ";";
            }
        }
        return s.equals(paramTypeName);
    }

    public String[] getParameterNamesForMethod() {
        return (this.collector == null || !this.collector.debugInfoPresent) ? new String[0] : this.collector.getResult().split(",");
    }
}
