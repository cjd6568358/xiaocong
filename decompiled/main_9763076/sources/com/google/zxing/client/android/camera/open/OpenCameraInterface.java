package com.google.zxing.client.android.camera.open;

import android.hardware.Camera;
import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class OpenCameraInterface {
    private static final String TAG = OpenCameraInterface.class.getName();

    private OpenCameraInterface() {
    }

    public static int getCameraId(int requestedId) {
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            Log.w(TAG, "No cameras!");
            return -1;
        }
        int cameraId = requestedId;
        boolean explicitRequest = cameraId >= 0;
        if (!explicitRequest) {
            int index = 0;
            while (index < numCameras) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(index, cameraInfo);
                if (cameraInfo.facing == 0) {
                    break;
                }
                index++;
            }
            cameraId = index;
        }
        if (cameraId >= numCameras) {
            return explicitRequest ? -1 : 0;
        }
        return cameraId;
    }

    public static Camera open(int requestedId) {
        int cameraId = getCameraId(requestedId);
        if (cameraId == -1) {
            return null;
        }
        return Camera.open(cameraId);
    }
}
