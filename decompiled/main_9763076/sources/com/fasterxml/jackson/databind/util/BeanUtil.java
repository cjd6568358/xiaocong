package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BeanUtil {
    public static String okNameForGetter(AnnotatedMethod am) {
        String name = am.getName();
        String str = okNameForIsGetter(am, name);
        if (str == null) {
            return okNameForRegularGetter(am, name);
        }
        return str;
    }

    public static String okNameForRegularGetter(AnnotatedMethod am, String name) {
        if (!name.startsWith("get")) {
            return null;
        }
        if ("getCallbacks".equals(name)) {
            if (isCglibGetCallbacks(am)) {
                return null;
            }
        } else if ("getMetaClass".equals(name) && isGroovyMetaClassGetter(am)) {
            return null;
        }
        return manglePropertyName(name.substring(3));
    }

    public static String okNameForIsGetter(AnnotatedMethod am, String name) {
        if (!name.startsWith("is")) {
            return null;
        }
        Class<?> rt = am.getRawType();
        if (rt == Boolean.class || rt == Boolean.TYPE) {
            return manglePropertyName(name.substring(2));
        }
        return null;
    }

    public static String okNameForSetter(AnnotatedMethod am) {
        String name = okNameForMutator(am, "set");
        if (name == null) {
            return null;
        }
        if ("metaClass".equals(name) && isGroovyMetaClassSetter(am)) {
            return null;
        }
        return name;
    }

    public static String okNameForMutator(AnnotatedMethod am, String prefix) {
        String name = am.getName();
        if (name.startsWith(prefix)) {
            return manglePropertyName(name.substring(prefix.length()));
        }
        return null;
    }

    protected static boolean isCglibGetCallbacks(AnnotatedMethod am) {
        Class<?> rt = am.getRawType();
        if (rt == null || !rt.isArray()) {
            return false;
        }
        Class<?> compType = rt.getComponentType();
        Package pkg = compType.getPackage();
        if (pkg == null) {
            return false;
        }
        String pname = pkg.getName();
        return pname.startsWith("net.sf.cglib") || pname.startsWith("org.hibernate.repackage.cglib");
    }

    protected static boolean isGroovyMetaClassSetter(AnnotatedMethod am) {
        Class<?> argType = am.getRawParameterType(0);
        Package pkg = argType.getPackage();
        return pkg != null && pkg.getName().startsWith("groovy.lang");
    }

    protected static boolean isGroovyMetaClassGetter(AnnotatedMethod am) {
        Package pkg;
        Class<?> rt = am.getRawType();
        return (rt == null || rt.isArray() || (pkg = rt.getPackage()) == null || !pkg.getName().startsWith("groovy.lang")) ? false : true;
    }

    protected static String manglePropertyName(String basename) {
        int len = basename.length();
        if (len == 0) {
            return null;
        }
        StringBuilder sb = null;
        for (int i = 0; i < len; i++) {
            char upper = basename.charAt(i);
            char lower = Character.toLowerCase(upper);
            if (upper == lower) {
                break;
            }
            if (sb == null) {
                sb = new StringBuilder(basename);
            }
            sb.setCharAt(i, lower);
        }
        return sb != null ? sb.toString() : basename;
    }
}
