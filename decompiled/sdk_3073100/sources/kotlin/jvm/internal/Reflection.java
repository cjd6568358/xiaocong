package kotlin.jvm.internal;

import kotlin.reflect.KClass;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Reflection {
    private static final KClass[] EMPTY_K_CLASS_ARRAY;
    private static final ReflectionFactory factory;

    static {
        ReflectionFactory impl;
        try {
            Class<?> implClass = Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl");
            impl = (ReflectionFactory) implClass.newInstance();
        } catch (ClassCastException e) {
            impl = null;
        } catch (ClassNotFoundException e2) {
            impl = null;
        } catch (IllegalAccessException e3) {
            impl = null;
        } catch (InstantiationException e4) {
            impl = null;
        }
        if (impl == null) {
            impl = new ReflectionFactory();
        }
        factory = impl;
        EMPTY_K_CLASS_ARRAY = new KClass[0];
    }

    public static String renderLambdaToString(Lambda lambda) {
        return factory.renderLambdaToString(lambda);
    }
}
