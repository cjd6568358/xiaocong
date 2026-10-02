package com.facebook.react.bridge;

import com.facebook.infer.annotation.Assertions;
import com.facebook.systrace.SystraceMessage;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BaseJavaModule implements NativeModule {
    public static final String METHOD_TYPE_ASYNC = "async";
    public static final String METHOD_TYPE_PROMISE = "promise";
    public static final String METHOD_TYPE_SYNC = "sync";
    private Map<String, NativeModule.NativeMethod> mMethods;
    private static final ArgumentExtractor<Boolean> ARGUMENT_EXTRACTOR_BOOLEAN = new ArgumentExtractor<Boolean>() { // from class: com.facebook.react.bridge.BaseJavaModule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public Boolean extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            return Boolean.valueOf(jsArguments.getBoolean(atIndex));
        }
    };
    private static final ArgumentExtractor<Double> ARGUMENT_EXTRACTOR_DOUBLE = new ArgumentExtractor<Double>() { // from class: com.facebook.react.bridge.BaseJavaModule.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public Double extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            return Double.valueOf(jsArguments.getDouble(atIndex));
        }
    };
    private static final ArgumentExtractor<Float> ARGUMENT_EXTRACTOR_FLOAT = new ArgumentExtractor<Float>() { // from class: com.facebook.react.bridge.BaseJavaModule.3
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public Float extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            return Float.valueOf((float) jsArguments.getDouble(atIndex));
        }
    };
    private static final ArgumentExtractor<Integer> ARGUMENT_EXTRACTOR_INTEGER = new ArgumentExtractor<Integer>() { // from class: com.facebook.react.bridge.BaseJavaModule.4
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public Integer extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            return Integer.valueOf((int) jsArguments.getDouble(atIndex));
        }
    };
    private static final ArgumentExtractor<String> ARGUMENT_EXTRACTOR_STRING = new ArgumentExtractor<String>() { // from class: com.facebook.react.bridge.BaseJavaModule.5
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public String extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            return jsArguments.getString(atIndex);
        }
    };
    private static final ArgumentExtractor<ReadableNativeArray> ARGUMENT_EXTRACTOR_ARRAY = new ArgumentExtractor<ReadableNativeArray>() { // from class: com.facebook.react.bridge.BaseJavaModule.6
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public ReadableNativeArray extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            return jsArguments.getArray(atIndex);
        }
    };
    private static final ArgumentExtractor<Dynamic> ARGUMENT_EXTRACTOR_DYNAMIC = new ArgumentExtractor<Dynamic>() { // from class: com.facebook.react.bridge.BaseJavaModule.7
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public Dynamic extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            return DynamicFromArray.create(jsArguments, atIndex);
        }
    };
    private static final ArgumentExtractor<ReadableMap> ARGUMENT_EXTRACTOR_MAP = new ArgumentExtractor<ReadableMap>() { // from class: com.facebook.react.bridge.BaseJavaModule.8
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public ReadableMap extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            return jsArguments.getMap(atIndex);
        }
    };
    private static final ArgumentExtractor<Callback> ARGUMENT_EXTRACTOR_CALLBACK = new ArgumentExtractor<Callback>() { // from class: com.facebook.react.bridge.BaseJavaModule.9
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public Callback extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            if (jsArguments.isNull(atIndex)) {
                return null;
            }
            int id = (int) jsArguments.getDouble(atIndex);
            return new CallbackImpl(catalystInstance, executorToken, id);
        }
    };
    private static final ArgumentExtractor<Promise> ARGUMENT_EXTRACTOR_PROMISE = new ArgumentExtractor<Promise>() { // from class: com.facebook.react.bridge.BaseJavaModule.10
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public int getJSArgumentsNeeded() {
            return 2;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.facebook.react.bridge.BaseJavaModule.ArgumentExtractor
        public Promise extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray jsArguments, int atIndex) {
            Callback resolve = (Callback) BaseJavaModule.ARGUMENT_EXTRACTOR_CALLBACK.extractArgument(catalystInstance, executorToken, jsArguments, atIndex);
            Callback reject = (Callback) BaseJavaModule.ARGUMENT_EXTRACTOR_CALLBACK.extractArgument(catalystInstance, executorToken, jsArguments, atIndex + 1);
            return new PromiseImpl(resolve, reject);
        }
    };

    private static abstract class ArgumentExtractor<T> {
        public abstract T extractArgument(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray readableNativeArray, int i);

        private ArgumentExtractor() {
        }

        public int getJSArgumentsNeeded() {
            return 1;
        }
    }

    public class JavaMethod implements NativeModule.NativeMethod {
        private final ArgumentExtractor[] mArgumentExtractors;
        private final Object[] mArguments;
        private final int mJSArgumentsNeeded;
        private Method mMethod;
        private final String mSignature;
        private final String mTraceName;
        private String mType;

        public JavaMethod(Method method, boolean isSync) {
            this.mType = BaseJavaModule.METHOD_TYPE_ASYNC;
            this.mMethod = method;
            this.mMethod.setAccessible(true);
            if (isSync) {
                this.mType = BaseJavaModule.METHOD_TYPE_SYNC;
            }
            Class<?>[] parameterTypes = method.getParameterTypes();
            this.mArgumentExtractors = buildArgumentExtractors(parameterTypes);
            this.mSignature = buildSignature(this.mMethod, parameterTypes, isSync);
            this.mArguments = new Object[parameterTypes.length];
            this.mJSArgumentsNeeded = calculateJSArgumentsNeeded();
            this.mTraceName = BaseJavaModule.this.getName() + "." + this.mMethod.getName();
        }

        public Method getMethod() {
            return this.mMethod;
        }

        public String getSignature() {
            return this.mSignature;
        }

        private String buildSignature(Method method, Class[] paramTypes, boolean isSync) {
            StringBuilder builder = new StringBuilder(paramTypes.length + 2);
            if (isSync) {
                builder.append(BaseJavaModule.returnTypeToChar(method.getReturnType()));
                builder.append('.');
            } else {
                builder.append("v.");
            }
            int i = 0;
            while (i < paramTypes.length) {
                Class paramClass = paramTypes[i];
                if (paramClass == ExecutorToken.class) {
                    if (!BaseJavaModule.this.supportsWebWorkers()) {
                        throw new RuntimeException("Module " + BaseJavaModule.this + " doesn't support web workers, but " + this.mMethod.getName() + " takes an ExecutorToken.");
                    }
                } else if (paramClass == Promise.class) {
                    Assertions.assertCondition(i == paramTypes.length + (-1), "Promise must be used as last parameter only");
                    if (!isSync) {
                        this.mType = BaseJavaModule.METHOD_TYPE_PROMISE;
                    }
                }
                builder.append(BaseJavaModule.paramTypeToChar(paramClass));
                i++;
            }
            if (BaseJavaModule.this.supportsWebWorkers() && builder.charAt(2) != 'T') {
                throw new RuntimeException("Module " + BaseJavaModule.this + " supports web workers, but " + this.mMethod.getName() + "does not take an ExecutorToken as its first parameter.");
            }
            return builder.toString();
        }

        private ArgumentExtractor[] buildArgumentExtractors(Class[] paramTypes) {
            int executorTokenOffset = 0;
            if (BaseJavaModule.this.supportsWebWorkers()) {
                if (paramTypes[0] != ExecutorToken.class) {
                    throw new RuntimeException("Module " + BaseJavaModule.this + " supports web workers, but " + this.mMethod.getName() + "does not take an ExecutorToken as its first parameter.");
                }
                executorTokenOffset = 1;
            }
            ArgumentExtractor[] argumentExtractors = new ArgumentExtractor[paramTypes.length - executorTokenOffset];
            for (int i = 0; i < paramTypes.length - executorTokenOffset; i += argumentExtractors[i].getJSArgumentsNeeded()) {
                int paramIndex = i + executorTokenOffset;
                Class argumentClass = paramTypes[paramIndex];
                if (argumentClass == Boolean.class || argumentClass == Boolean.TYPE) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_BOOLEAN;
                } else if (argumentClass == Integer.class || argumentClass == Integer.TYPE) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_INTEGER;
                } else if (argumentClass == Double.class || argumentClass == Double.TYPE) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_DOUBLE;
                } else if (argumentClass == Float.class || argumentClass == Float.TYPE) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_FLOAT;
                } else if (argumentClass == String.class) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_STRING;
                } else if (argumentClass == Callback.class) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_CALLBACK;
                } else if (argumentClass == Promise.class) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_PROMISE;
                    Assertions.assertCondition(paramIndex == paramTypes.length + (-1), "Promise must be used as last parameter only");
                    this.mType = BaseJavaModule.METHOD_TYPE_PROMISE;
                } else if (argumentClass == ReadableMap.class) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_MAP;
                } else if (argumentClass == ReadableArray.class) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_ARRAY;
                } else if (argumentClass == Dynamic.class) {
                    argumentExtractors[i] = BaseJavaModule.ARGUMENT_EXTRACTOR_DYNAMIC;
                } else {
                    throw new RuntimeException("Got unknown argument class: " + argumentClass.getSimpleName());
                }
            }
            return argumentExtractors;
        }

        private int calculateJSArgumentsNeeded() {
            int n = 0;
            for (ArgumentExtractor extractor : this.mArgumentExtractors) {
                n += extractor.getJSArgumentsNeeded();
            }
            return n;
        }

        private String getAffectedRange(int startIndex, int jsArgumentsNeeded) {
            return jsArgumentsNeeded > 1 ? Constants.MAIN_VERSION_TAG + startIndex + "-" + ((startIndex + jsArgumentsNeeded) - 1) : Constants.MAIN_VERSION_TAG + startIndex;
        }

        @Override // com.facebook.react.bridge.NativeModule.NativeMethod
        public void invoke(CatalystInstance catalystInstance, ExecutorToken executorToken, ReadableNativeArray parameters) {
            SystraceMessage.beginSection(0L, "callJavaModuleMethod").arg(PushConstants.MZ_PUSH_MESSAGE_METHOD, this.mTraceName).flush();
            try {
                if (this.mJSArgumentsNeeded != parameters.size()) {
                    throw new NativeArgumentsParseException(BaseJavaModule.this.getName() + "." + this.mMethod.getName() + " got " + parameters.size() + " arguments, expected " + this.mJSArgumentsNeeded);
                }
                int jsArgumentsConsumed = 0;
                int executorTokenOffset = 0;
                if (BaseJavaModule.this.supportsWebWorkers()) {
                    this.mArguments[0] = executorToken;
                    executorTokenOffset = 1;
                }
                for (int i = 0; i < this.mArgumentExtractors.length; i++) {
                    try {
                        this.mArguments[i + executorTokenOffset] = this.mArgumentExtractors[i].extractArgument(catalystInstance, executorToken, parameters, jsArgumentsConsumed);
                        jsArgumentsConsumed += this.mArgumentExtractors[i].getJSArgumentsNeeded();
                    } catch (UnexpectedNativeTypeException e) {
                        throw new NativeArgumentsParseException(e.getMessage() + " (constructing arguments for " + BaseJavaModule.this.getName() + "." + this.mMethod.getName() + " at argument index " + getAffectedRange(jsArgumentsConsumed, this.mArgumentExtractors[i].getJSArgumentsNeeded()) + ")", e);
                    }
                }
                try {
                    this.mMethod.invoke(BaseJavaModule.this, this.mArguments);
                    com.facebook.systrace.Systrace.endSection(0L);
                } catch (IllegalAccessException iae) {
                    throw new RuntimeException("Could not invoke " + BaseJavaModule.this.getName() + "." + this.mMethod.getName(), iae);
                } catch (IllegalArgumentException ie) {
                    throw new RuntimeException("Could not invoke " + BaseJavaModule.this.getName() + "." + this.mMethod.getName(), ie);
                } catch (InvocationTargetException ite) {
                    if (ite.getCause() instanceof RuntimeException) {
                        throw ((RuntimeException) ite.getCause());
                    }
                    throw new RuntimeException("Could not invoke " + BaseJavaModule.this.getName() + "." + this.mMethod.getName(), ite);
                }
            } catch (Throwable th) {
                com.facebook.systrace.Systrace.endSection(0L);
                throw th;
            }
        }

        @Override // com.facebook.react.bridge.NativeModule.NativeMethod
        public String getType() {
            return this.mType;
        }
    }

    private void findMethods() {
        if (this.mMethods == null) {
            com.facebook.systrace.Systrace.beginSection(0L, "findMethods");
            this.mMethods = new HashMap();
            Method[] targetMethods = getClass().getDeclaredMethods();
            for (Method targetMethod : targetMethods) {
                ReactMethod annotation = (ReactMethod) targetMethod.getAnnotation(ReactMethod.class);
                if (annotation != null) {
                    String methodName = targetMethod.getName();
                    if (this.mMethods.containsKey(methodName)) {
                        throw new IllegalArgumentException("Java Module " + getName() + " method name already registered: " + methodName);
                    }
                    this.mMethods.put(methodName, new JavaMethod(targetMethod, annotation.isBlockingSynchronousMethod()));
                }
            }
            com.facebook.systrace.Systrace.endSection(0L);
        }
    }

    public final Map<String, NativeModule.NativeMethod> getMethods() {
        findMethods();
        return (Map) Assertions.assertNotNull(this.mMethods);
    }

    public Map<String, Object> getConstants() {
        return null;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public void initialize() {
    }

    @Override // com.facebook.react.bridge.NativeModule
    public boolean canOverrideExistingModule() {
        return false;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
    }

    public boolean supportsWebWorkers() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char paramTypeToChar(Class paramClass) {
        char tryCommon = commonTypeToChar(paramClass);
        if (tryCommon == 0) {
            if (paramClass == ExecutorToken.class) {
                return 'T';
            }
            if (paramClass == Callback.class) {
                return 'X';
            }
            if (paramClass == Promise.class) {
                return 'P';
            }
            if (paramClass == ReadableMap.class) {
                return 'M';
            }
            if (paramClass == ReadableArray.class) {
                return 'A';
            }
            if (paramClass == Dynamic.class) {
                return 'Y';
            }
            throw new RuntimeException("Got unknown param class: " + paramClass.getSimpleName());
        }
        return tryCommon;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char returnTypeToChar(Class returnClass) {
        char tryCommon = commonTypeToChar(returnClass);
        if (tryCommon == 0) {
            if (returnClass == Void.TYPE) {
                return 'v';
            }
            if (returnClass == WritableMap.class) {
                return 'M';
            }
            if (returnClass == WritableArray.class) {
                return 'A';
            }
            throw new RuntimeException("Got unknown return class: " + returnClass.getSimpleName());
        }
        return tryCommon;
    }

    private static char commonTypeToChar(Class typeClass) {
        if (typeClass == Boolean.TYPE) {
            return 'z';
        }
        if (typeClass == Boolean.class) {
            return 'Z';
        }
        if (typeClass == Integer.TYPE) {
            return 'i';
        }
        if (typeClass == Integer.class) {
            return 'I';
        }
        if (typeClass == Double.TYPE) {
            return 'd';
        }
        if (typeClass == Double.class) {
            return 'D';
        }
        if (typeClass == Float.TYPE) {
            return 'f';
        }
        if (typeClass == Float.class) {
            return 'F';
        }
        if (typeClass == String.class) {
            return 'S';
        }
        return (char) 0;
    }
}
