package bsh;

import java.util.Hashtable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Capabilities {
    static Class class$java$lang$String;
    private static boolean accessibility = false;
    private static Hashtable classes = new Hashtable();

    public static class Unavailable extends UtilEvalError {
        public Unavailable(String str) {
            super(str);
        }
    }

    public static boolean canGenerateInterfaces() {
        return classExists("java.lang.reflect.Proxy");
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static boolean classExists(String str) {
        Object cls = classes.get(str);
        if (cls == null) {
            try {
                cls = Class.forName(str);
            } catch (ClassNotFoundException e) {
            }
            if (cls != null) {
                classes.put(cls, "unused");
            }
        }
        return cls != null;
    }

    public static boolean haveAccessibility() {
        return accessibility;
    }

    public static boolean haveSwing() {
        return classExists("javax.swing.JButton");
    }

    public static void setAccessibility(boolean z) throws Unavailable {
        Class clsClass$;
        if (!z) {
            accessibility = false;
            return;
        }
        if (!classExists("java.lang.reflect.AccessibleObject") || !classExists("bsh.reflect.ReflectManagerImpl")) {
            throw new Unavailable("Accessibility unavailable");
        }
        try {
            if (class$java$lang$String == null) {
                clsClass$ = class$("java.lang.String");
                class$java$lang$String = clsClass$;
            } else {
                clsClass$ = class$java$lang$String;
            }
            clsClass$.getDeclaredMethods();
            accessibility = true;
        } catch (SecurityException e) {
            throw new Unavailable(new StringBuffer().append("Accessibility unavailable: ").append(e).toString());
        }
    }
}
