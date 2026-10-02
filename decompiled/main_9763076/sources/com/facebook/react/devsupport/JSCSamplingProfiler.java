package com.facebook.react.devsupport;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSCSamplingProfiler extends ReactContextBaseJavaModule {
    private static final HashSet<JSCSamplingProfiler> sRegisteredDumpers = new HashSet<>();
    private String mOperationError;
    private boolean mOperationInProgress;
    private int mOperationToken;
    private SamplingProfiler mSamplingProfiler;
    private String mSamplingProfilerResult;

    public interface SamplingProfiler extends JavaScriptModule {
        void poke(int i);
    }

    public static class ProfilerException extends Exception {
        ProfilerException(String message) {
            super(message);
        }
    }

    private static synchronized void registerSamplingProfiler(JSCSamplingProfiler dumper) {
        if (sRegisteredDumpers.contains(dumper)) {
            throw new RuntimeException("a JSCSamplingProfiler registered more than once");
        }
        sRegisteredDumpers.add(dumper);
    }

    private static synchronized void unregisterSamplingProfiler(JSCSamplingProfiler dumper) {
        sRegisteredDumpers.remove(dumper);
    }

    public static synchronized List<String> poke(long timeout) throws ProfilerException {
        LinkedList<String> results;
        results = new LinkedList<>();
        if (sRegisteredDumpers.isEmpty()) {
            throw new ProfilerException("No JSC registered");
        }
        for (JSCSamplingProfiler dumper : sRegisteredDumpers) {
            dumper.pokeHelper(timeout);
            results.add(dumper.mSamplingProfilerResult);
        }
        return results;
    }

    public JSCSamplingProfiler(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mSamplingProfiler = null;
        this.mOperationInProgress = false;
        this.mOperationToken = 0;
        this.mOperationError = null;
        this.mSamplingProfilerResult = null;
    }

    private synchronized void pokeHelper(long timeout) throws ProfilerException {
        if (this.mSamplingProfiler == null) {
            throw new ProfilerException("SamplingProfiler.js module not connected");
        }
        this.mSamplingProfiler.poke(getOperationToken());
        waitForOperation(timeout);
    }

    private int getOperationToken() throws ProfilerException {
        if (this.mOperationInProgress) {
            throw new ProfilerException("Another operation already in progress.");
        }
        this.mOperationInProgress = true;
        int i = this.mOperationToken + 1;
        this.mOperationToken = i;
        return i;
    }

    private void waitForOperation(long timeout) throws ProfilerException {
        try {
            wait(timeout);
            if (this.mOperationInProgress) {
                this.mOperationInProgress = false;
                throw new ProfilerException("heap capture timed out.");
            }
            if (this.mOperationError != null) {
                throw new ProfilerException(this.mOperationError);
            }
        } catch (InterruptedException e) {
            throw new ProfilerException("Waiting for heap capture failed: " + e.getMessage());
        }
    }

    @ReactMethod
    public synchronized void operationComplete(int token, String result, String error) {
        if (token == this.mOperationToken) {
            this.mOperationInProgress = false;
            this.mSamplingProfilerResult = result;
            this.mOperationError = error;
            notify();
        } else {
            throw new RuntimeException("Completed operation is not in progress.");
        }
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "JSCSamplingProfiler";
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        super.initialize();
        this.mSamplingProfiler = (SamplingProfiler) getReactApplicationContext().getJSModule(SamplingProfiler.class);
        registerSamplingProfiler(this);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        unregisterSamplingProfiler(this);
        this.mSamplingProfiler = null;
    }
}
