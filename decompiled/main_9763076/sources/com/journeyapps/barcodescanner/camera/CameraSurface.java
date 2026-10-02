package com.journeyapps.barcodescanner.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.view.SurfaceHolder;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CameraSurface {
    private SurfaceHolder surfaceHolder;
    private SurfaceTexture surfaceTexture;

    public CameraSurface(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalArgumentException("surfaceHolder may not be null");
        }
        this.surfaceHolder = surfaceHolder;
    }

    public CameraSurface(SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            throw new IllegalArgumentException("surfaceTexture may not be null");
        }
        this.surfaceTexture = surfaceTexture;
    }

    public void setPreview(Camera camera) throws IOException {
        if (this.surfaceHolder != null) {
            camera.setPreviewDisplay(this.surfaceHolder);
        } else {
            if (Build.VERSION.SDK_INT >= 11) {
                camera.setPreviewTexture(this.surfaceTexture);
                return;
            }
            throw new IllegalStateException("SurfaceTexture not supported.");
        }
    }
}
