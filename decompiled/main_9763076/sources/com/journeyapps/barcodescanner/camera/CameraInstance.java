package com.journeyapps.barcodescanner.camera;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.Util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CameraInstance {
    private static final String TAG = CameraInstance.class.getSimpleName();
    private CameraManager cameraManager;
    private CameraThread cameraThread;
    private DisplayConfiguration displayConfiguration;
    private Handler readyHandler;
    private CameraSurface surface;
    private boolean open = false;
    private CameraSettings cameraSettings = new CameraSettings();
    private Runnable opener = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.3
        @Override // java.lang.Runnable
        public void run() {
            try {
                Log.d(CameraInstance.TAG, "Opening camera");
                CameraInstance.this.cameraManager.open();
            } catch (Exception e) {
                CameraInstance.this.notifyError(e);
                Log.e(CameraInstance.TAG, "Failed to open camera", e);
            }
        }
    };
    private Runnable configure = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.4
        @Override // java.lang.Runnable
        public void run() {
            try {
                Log.d(CameraInstance.TAG, "Configuring camera");
                CameraInstance.this.cameraManager.configure();
                if (CameraInstance.this.readyHandler != null) {
                    CameraInstance.this.readyHandler.obtainMessage(R.id.zxing_prewiew_size_ready, CameraInstance.this.getPreviewSize()).sendToTarget();
                }
            } catch (Exception e) {
                CameraInstance.this.notifyError(e);
                Log.e(CameraInstance.TAG, "Failed to configure camera", e);
            }
        }
    };
    private Runnable previewStarter = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.5
        @Override // java.lang.Runnable
        public void run() {
            try {
                Log.d(CameraInstance.TAG, "Starting preview");
                CameraInstance.this.cameraManager.setPreviewDisplay(CameraInstance.this.surface);
                CameraInstance.this.cameraManager.startPreview();
            } catch (Exception e) {
                CameraInstance.this.notifyError(e);
                Log.e(CameraInstance.TAG, "Failed to start preview", e);
            }
        }
    };
    private Runnable closer = new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.6
        @Override // java.lang.Runnable
        public void run() {
            try {
                Log.d(CameraInstance.TAG, "Closing camera");
                CameraInstance.this.cameraManager.stopPreview();
                CameraInstance.this.cameraManager.close();
            } catch (Exception e) {
                Log.e(CameraInstance.TAG, "Failed to close camera", e);
            }
            CameraInstance.this.cameraThread.decrementInstances();
        }
    };

    public CameraInstance(Context context) {
        Util.validateMainThread();
        this.cameraThread = CameraThread.getInstance();
        this.cameraManager = new CameraManager(context);
        this.cameraManager.setCameraSettings(this.cameraSettings);
    }

    public void setDisplayConfiguration(DisplayConfiguration configuration) {
        this.displayConfiguration = configuration;
        this.cameraManager.setDisplayConfiguration(configuration);
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return this.displayConfiguration;
    }

    public void setReadyHandler(Handler readyHandler) {
        this.readyHandler = readyHandler;
    }

    public void setSurface(CameraSurface surface) {
        this.surface = surface;
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        if (!this.open) {
            this.cameraSettings = cameraSettings;
            this.cameraManager.setCameraSettings(cameraSettings);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Size getPreviewSize() {
        return this.cameraManager.getPreviewSize();
    }

    public void open() {
        Util.validateMainThread();
        this.open = true;
        this.cameraThread.incrementAndEnqueue(this.opener);
    }

    public void configureCamera() {
        Util.validateMainThread();
        validateOpen();
        this.cameraThread.enqueue(this.configure);
    }

    public void startPreview() {
        Util.validateMainThread();
        validateOpen();
        this.cameraThread.enqueue(this.previewStarter);
    }

    public void setTorch(final boolean on) {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.1
                @Override // java.lang.Runnable
                public void run() {
                    CameraInstance.this.cameraManager.setTorch(on);
                }
            });
        }
    }

    public void close() {
        Util.validateMainThread();
        if (this.open) {
            this.cameraThread.enqueue(this.closer);
        }
        this.open = false;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void requestPreview(final PreviewCallback callback) {
        validateOpen();
        this.cameraThread.enqueue(new Runnable() { // from class: com.journeyapps.barcodescanner.camera.CameraInstance.2
            @Override // java.lang.Runnable
            public void run() {
                CameraInstance.this.cameraManager.requestPreviewFrame(callback);
            }
        });
    }

    private void validateOpen() {
        if (!this.open) {
            throw new IllegalStateException("CameraInstance is not open");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(Exception error) {
        if (this.readyHandler != null) {
            this.readyHandler.obtainMessage(R.id.zxing_camera_error, error).sendToTarget();
        }
    }
}
