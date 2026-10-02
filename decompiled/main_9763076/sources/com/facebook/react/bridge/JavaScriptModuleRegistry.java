package com.facebook.react.bridge;

import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JavaScriptModuleRegistry {
    private final WeakHashMap<ExecutorToken, HashMap<Class<? extends JavaScriptModule>, JavaScriptModule>> mModuleInstances = new WeakHashMap<>();
    private final HashMap<Class<? extends JavaScriptModule>, JavaScriptModuleRegistration> mModuleRegistrations = new HashMap<>();

    public JavaScriptModuleRegistry(List<JavaScriptModuleRegistration> config) {
        for (JavaScriptModuleRegistration registration : config) {
            this.mModuleRegistrations.put(registration.getModuleInterface(), registration);
        }
    }

    public synchronized <T extends JavaScriptModule> T getJavaScriptModule(CatalystInstance catalystInstance, ExecutorToken executorToken, Class<T> cls) {
        T t;
        HashMap<Class<? extends JavaScriptModule>, JavaScriptModule> map = this.mModuleInstances.get(executorToken);
        if (map == null) {
            map = new HashMap<>();
            this.mModuleInstances.put(executorToken, map);
        }
        t = (T) map.get(cls);
        if (t == null) {
            JavaScriptModule javaScriptModule = (JavaScriptModule) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new JavaScriptModuleInvocationHandler(executorToken, catalystInstance, (JavaScriptModuleRegistration) Assertions.assertNotNull(this.mModuleRegistrations.get(cls), "JS module " + cls.getSimpleName() + " hasn't been registered!")));
            map.put(cls, javaScriptModule);
            t = (T) javaScriptModule;
        }
        return t;
    }

    public static class Builder {
        private List<JavaScriptModuleRegistration> mModules = new ArrayList();

        public Builder add(Class<? extends JavaScriptModule> moduleInterfaceClass) {
            this.mModules.add(new JavaScriptModuleRegistration(moduleInterfaceClass));
            return this;
        }

        public JavaScriptModuleRegistry build() {
            return new JavaScriptModuleRegistry(this.mModules);
        }
    }

    private static class JavaScriptModuleInvocationHandler implements InvocationHandler {
        private final CatalystInstance mCatalystInstance;
        private final WeakReference<ExecutorToken> mExecutorToken;
        private final JavaScriptModuleRegistration mModuleRegistration;

        public JavaScriptModuleInvocationHandler(ExecutorToken executorToken, CatalystInstance catalystInstance, JavaScriptModuleRegistration moduleRegistration) {
            this.mExecutorToken = new WeakReference<>(executorToken);
            this.mCatalystInstance = catalystInstance;
            this.mModuleRegistration = moduleRegistration;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            ExecutorToken executorToken = this.mExecutorToken.get();
            if (executorToken == null) {
                FLog.w("React", "Dropping JS call, ExecutorToken went away...");
            } else {
                NativeArray jsArgs = args != null ? Arguments.fromJavaArgs(args) : new WritableNativeArray();
                this.mCatalystInstance.callFunction(executorToken, this.mModuleRegistration.getName(), method.getName(), jsArgs);
            }
            return null;
        }
    }
}
