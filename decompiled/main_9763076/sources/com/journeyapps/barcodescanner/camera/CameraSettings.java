package com.journeyapps.barcodescanner.camera;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CameraSettings {
    private int requestedCameraId = -1;
    private boolean scanInverted = false;
    private boolean barcodeSceneModeEnabled = false;
    private boolean meteringEnabled = false;
    private boolean autoFocusEnabled = true;
    private boolean continuousFocusEnabled = false;
    private boolean exposureEnabled = false;
    private boolean autoTorchEnabled = false;
    private FocusMode focusMode = FocusMode.AUTO;

    public enum FocusMode {
        AUTO,
        CONTINUOUS,
        INFINITY,
        MACRO
    }

    public int getRequestedCameraId() {
        return this.requestedCameraId;
    }

    public void setRequestedCameraId(int requestedCameraId) {
        this.requestedCameraId = requestedCameraId;
    }

    public boolean isScanInverted() {
        return this.scanInverted;
    }

    public boolean isBarcodeSceneModeEnabled() {
        return this.barcodeSceneModeEnabled;
    }

    public boolean isExposureEnabled() {
        return this.exposureEnabled;
    }

    public boolean isMeteringEnabled() {
        return this.meteringEnabled;
    }

    public boolean isAutoFocusEnabled() {
        return this.autoFocusEnabled;
    }

    public FocusMode getFocusMode() {
        return this.focusMode;
    }

    public boolean isAutoTorchEnabled() {
        return this.autoTorchEnabled;
    }
}
