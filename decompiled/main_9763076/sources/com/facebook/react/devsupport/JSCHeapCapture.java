package com.facebook.react.devsupport;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSCHeapCapture extends ReactContextBaseJavaModule {
    private static final HashSet<JSCHeapCapture> sRegisteredDumpers = new HashSet<>();
    private PerCaptureCallback mCaptureInProgress;
    private HeapCapture mHeapCapture;

    public interface CaptureCallback {
        void onComplete(List<File> list, List<CaptureException> list2);
    }

    public interface HeapCapture extends JavaScriptModule {
        void captureHeap(String str);
    }

    private interface PerCaptureCallback {
        void onFailure(CaptureException captureException);

        void onSuccess(File file);
    }

    public static class CaptureException extends Exception {
        CaptureException(String message) {
            super(message);
        }

        CaptureException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static synchronized void registerHeapCapture(JSCHeapCapture dumper) {
        if (sRegisteredDumpers.contains(dumper)) {
            throw new RuntimeException("a JSCHeapCapture registered more than once");
        }
        sRegisteredDumpers.add(dumper);
    }

    private static synchronized void unregisterHeapCapture(JSCHeapCapture dumper) {
        sRegisteredDumpers.remove(dumper);
    }

    public static synchronized void captureHeap(String path, final CaptureCallback callback) {
        final LinkedList<File> captureFiles = new LinkedList<>();
        final LinkedList<CaptureException> captureFailures = new LinkedList<>();
        if (sRegisteredDumpers.isEmpty()) {
            captureFailures.add(new CaptureException("No JSC registered"));
            callback.onComplete(captureFiles, captureFailures);
        } else {
            int disambiguate = 0;
            File f = new File(path + "/capture" + Integer.toString(0) + ".json");
            while (f.delete()) {
                disambiguate++;
                f = new File(path + "/capture" + Integer.toString(disambiguate) + ".json");
            }
            final int numRegisteredDumpers = sRegisteredDumpers.size();
            for (JSCHeapCapture dumper : sRegisteredDumpers) {
                File file = new File(path + "/capture" + Integer.toString(0) + ".json");
                dumper.captureHeapHelper(file, new PerCaptureCallback() { // from class: com.facebook.react.devsupport.JSCHeapCapture.1
                    @Override // com.facebook.react.devsupport.JSCHeapCapture.PerCaptureCallback
                    public void onSuccess(File capture) {
                        captureFiles.add(capture);
                        if (captureFiles.size() + captureFailures.size() == numRegisteredDumpers) {
                            callback.onComplete(captureFiles, captureFailures);
                        }
                    }

                    @Override // com.facebook.react.devsupport.JSCHeapCapture.PerCaptureCallback
                    public void onFailure(CaptureException cause) {
                        captureFailures.add(cause);
                        if (captureFiles.size() + captureFailures.size() == numRegisteredDumpers) {
                            callback.onComplete(captureFiles, captureFailures);
                        }
                    }
                });
            }
        }
    }

    public JSCHeapCapture(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mHeapCapture = null;
        this.mCaptureInProgress = null;
    }

    private synchronized void captureHeapHelper(File file, PerCaptureCallback callback) {
        if (this.mHeapCapture == null) {
            callback.onFailure(new CaptureException("HeapCapture.js module not connected"));
        } else if (this.mCaptureInProgress != null) {
            callback.onFailure(new CaptureException("Heap capture already in progress"));
        } else {
            this.mCaptureInProgress = callback;
            this.mHeapCapture.captureHeap(file.getPath());
        }
    }

    @ReactMethod
    public synchronized void captureComplete(String path, String error) {
        if (this.mCaptureInProgress != null) {
            if (error == null) {
                this.mCaptureInProgress.onSuccess(new File(path));
            } else {
                this.mCaptureInProgress.onFailure(new CaptureException(error));
            }
            this.mCaptureInProgress = null;
        }
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "JSCHeapCapture";
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        super.initialize();
        this.mHeapCapture = (HeapCapture) getReactApplicationContext().getJSModule(HeapCapture.class);
        registerHeapCapture(this);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        unregisterHeapCapture(this);
        this.mHeapCapture = null;
    }
}
