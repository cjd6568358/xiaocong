package rx.plugins;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RxJavaPlugins {
    private static final RxJavaPlugins INSTANCE = new RxJavaPlugins();
    static final RxJavaErrorHandler DEFAULT_ERROR_HANDLER = new 1();
    private final AtomicReference<RxJavaErrorHandler> errorHandler = new AtomicReference<>();
    private final AtomicReference<RxJavaObservableExecutionHook> observableExecutionHook = new AtomicReference<>();
    private final AtomicReference<RxJavaSingleExecutionHook> singleExecutionHook = new AtomicReference<>();
    private final AtomicReference<RxJavaCompletableExecutionHook> completableExecutionHook = new AtomicReference<>();
    private final AtomicReference<RxJavaSchedulersHook> schedulersHook = new AtomicReference<>();

    @Deprecated
    public static RxJavaPlugins getInstance() {
        return INSTANCE;
    }

    RxJavaPlugins() {
    }

    public RxJavaErrorHandler getErrorHandler() {
        if (this.errorHandler.get() == null) {
            Object impl = getPluginImplementationViaProperty(RxJavaErrorHandler.class, System.getProperties());
            if (impl == null) {
                this.errorHandler.compareAndSet(null, DEFAULT_ERROR_HANDLER);
            } else {
                this.errorHandler.compareAndSet(null, (RxJavaErrorHandler) impl);
            }
        }
        return this.errorHandler.get();
    }

    public RxJavaObservableExecutionHook getObservableExecutionHook() {
        if (this.observableExecutionHook.get() == null) {
            Object impl = getPluginImplementationViaProperty(RxJavaObservableExecutionHook.class, System.getProperties());
            if (impl == null) {
                this.observableExecutionHook.compareAndSet(null, RxJavaObservableExecutionHookDefault.getInstance());
            } else {
                this.observableExecutionHook.compareAndSet(null, (RxJavaObservableExecutionHook) impl);
            }
        }
        return this.observableExecutionHook.get();
    }

    public RxJavaSingleExecutionHook getSingleExecutionHook() {
        if (this.singleExecutionHook.get() == null) {
            Object impl = getPluginImplementationViaProperty(RxJavaSingleExecutionHook.class, System.getProperties());
            if (impl == null) {
                this.singleExecutionHook.compareAndSet(null, RxJavaSingleExecutionHookDefault.getInstance());
            } else {
                this.singleExecutionHook.compareAndSet(null, (RxJavaSingleExecutionHook) impl);
            }
        }
        return this.singleExecutionHook.get();
    }

    public RxJavaCompletableExecutionHook getCompletableExecutionHook() {
        if (this.completableExecutionHook.get() == null) {
            Object impl = getPluginImplementationViaProperty(RxJavaCompletableExecutionHook.class, System.getProperties());
            if (impl == null) {
                this.completableExecutionHook.compareAndSet(null, new 2(this));
            } else {
                this.completableExecutionHook.compareAndSet(null, (RxJavaCompletableExecutionHook) impl);
            }
        }
        return this.completableExecutionHook.get();
    }

    static Object getPluginImplementationViaProperty(Class<?> pluginClass, Properties propsIn) {
        Properties props = (Properties) propsIn.clone();
        String classSimpleName = pluginClass.getSimpleName();
        String defaultKey = "rxjava.plugin." + classSimpleName + ".implementation";
        String implementingClass = props.getProperty(defaultKey);
        if (implementingClass == null) {
            for (Map.Entry<Object, Object> e : props.entrySet()) {
                String key = e.getKey().toString();
                if (key.startsWith("rxjava.plugin.") && key.endsWith(".class")) {
                    String value = e.getValue().toString();
                    if (classSimpleName.equals(value)) {
                        String index = key.substring(0, key.length() - ".class".length()).substring("rxjava.plugin.".length());
                        String implKey = "rxjava.plugin." + index + ".impl";
                        implementingClass = props.getProperty(implKey);
                        if (implementingClass != null) {
                            break;
                        }
                        throw new IllegalStateException("Implementing class declaration for " + classSimpleName + " missing: " + implKey);
                    }
                }
            }
        }
        if (implementingClass != null) {
            try {
                Class<?> cls = Class.forName(implementingClass);
                return cls.asSubclass(pluginClass).newInstance();
            } catch (ClassCastException e2) {
                throw new IllegalStateException(classSimpleName + " implementation is not an instance of " + classSimpleName + ": " + implementingClass, e2);
            } catch (ClassNotFoundException e3) {
                throw new IllegalStateException(classSimpleName + " implementation class not found: " + implementingClass, e3);
            } catch (IllegalAccessException e4) {
                throw new IllegalStateException(classSimpleName + " implementation not able to be accessed: " + implementingClass, e4);
            } catch (InstantiationException e5) {
                throw new IllegalStateException(classSimpleName + " implementation not able to be instantiated: " + implementingClass, e5);
            }
        }
        return null;
    }

    public RxJavaSchedulersHook getSchedulersHook() {
        if (this.schedulersHook.get() == null) {
            Object impl = getPluginImplementationViaProperty(RxJavaSchedulersHook.class, System.getProperties());
            if (impl == null) {
                this.schedulersHook.compareAndSet(null, RxJavaSchedulersHook.getDefaultInstance());
            } else {
                this.schedulersHook.compareAndSet(null, (RxJavaSchedulersHook) impl);
            }
        }
        return this.schedulersHook.get();
    }
}
