package com.facebook.react.cxxbridge;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ExecutorToken;
import com.facebook.react.bridge.NativeArray;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.NativeModuleLogger;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
class JavaModuleWrapper {
    private final CatalystInstance mCatalystInstance;
    private final ArrayList<NativeModule.NativeMethod> mMethods = new ArrayList<>();
    private final ModuleHolder mModuleHolder;

    @DoNotStrip
    public class MethodDescriptor {

        @DoNotStrip
        Method method;

        @DoNotStrip
        String name;

        @DoNotStrip
        String signature;

        @DoNotStrip
        String type;

        public MethodDescriptor() {
        }
    }

    public JavaModuleWrapper(CatalystInstance catalystinstance, ModuleHolder moduleHolder) {
        this.mCatalystInstance = catalystinstance;
        this.mModuleHolder = moduleHolder;
    }

    @DoNotStrip
    public BaseJavaModule getModule() {
        return (BaseJavaModule) this.mModuleHolder.getModule();
    }

    @DoNotStrip
    public String getName() {
        return this.mModuleHolder.getInfo().name();
    }

    @DoNotStrip
    public List<MethodDescriptor> getMethodDescriptors() {
        ArrayList<MethodDescriptor> descs = new ArrayList<>();
        for (Map.Entry<String, NativeModule.NativeMethod> entry : getModule().getMethods().entrySet()) {
            MethodDescriptor md = new MethodDescriptor();
            md.name = entry.getKey();
            md.type = entry.getValue().getType();
            BaseJavaModule.JavaMethod method = (BaseJavaModule.JavaMethod) entry.getValue();
            if (md.type == BaseJavaModule.METHOD_TYPE_SYNC) {
                md.signature = method.getSignature();
                md.method = method.getMethod();
            }
            this.mMethods.add(method);
            descs.add(md);
        }
        return descs;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @DoNotStrip
    public NativeArray getConstants() {
        SystraceMessage.beginSection(0L, "Map constants").arg("moduleName", getName()).flush();
        BaseJavaModule module = getModule();
        Map<String, Object> map = module.getConstants();
        Systrace.endSection(0L);
        SystraceMessage.beginSection(0L, "WritableNativeMap constants").arg("moduleName", getName()).flush();
        if (module instanceof NativeModuleLogger) {
            ((NativeModuleLogger) module).startConstantsMapConversion();
        }
        try {
            WritableNativeMap writableNativeMap = Arguments.makeNativeMap(map);
            Systrace.endSection(0L);
            WritableNativeArray array = new WritableNativeArray();
            array.pushMap(writableNativeMap);
            if (module instanceof NativeModuleLogger) {
                ((NativeModuleLogger) module).endConstantsMapConversion();
            }
            return array;
        } catch (Throwable th) {
            Systrace.endSection(0L);
            throw th;
        }
    }

    @DoNotStrip
    public boolean supportsWebWorkers() {
        return getModule().supportsWebWorkers();
    }

    @DoNotStrip
    public void invoke(ExecutorToken token, int methodId, ReadableNativeArray parameters) {
        if (this.mMethods != null && methodId < this.mMethods.size()) {
            this.mMethods.get(methodId).invoke(this.mCatalystInstance, token, parameters);
        }
    }
}
