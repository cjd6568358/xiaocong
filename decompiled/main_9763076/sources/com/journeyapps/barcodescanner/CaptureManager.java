package com.journeyapps.barcodescanner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import bsh.ParserConstants;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CaptureManager {
    private static final String TAG = CaptureManager.class.getSimpleName();
    private static int cameraPermissionReqCode = SQLiteDatabase.MAX_SQL_CACHE_SIZE;
    private Activity activity;
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private Handler handler;
    private InactivityTimer inactivityTimer;
    private int orientationLock = -1;
    private boolean returnBarcodeImagePath = false;
    private boolean destroyed = false;
    private BarcodeCallback callback = new BarcodeCallback() { // from class: com.journeyapps.barcodescanner.CaptureManager.1
        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void barcodeResult(final BarcodeResult result) {
            CaptureManager.this.barcodeView.pause();
            CaptureManager.this.beepManager.playBeepSoundAndVibrate();
            CaptureManager.this.handler.postDelayed(new Runnable() { // from class: com.journeyapps.barcodescanner.CaptureManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    CaptureManager.this.returnResult(result);
                }
            }, 150L);
        }

        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };
    private final CameraPreview.StateListener stateListener = new CameraPreview.StateListener() { // from class: com.journeyapps.barcodescanner.CaptureManager.2
        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewSized() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStarted() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStopped() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraError(Exception error) {
            CaptureManager.this.displayFrameworkBugMessageAndExit();
        }
    };
    private boolean askedPermission = false;

    public CaptureManager(Activity activity, DecoratedBarcodeView barcodeView) {
        this.activity = activity;
        this.barcodeView = barcodeView;
        barcodeView.getBarcodeView().addStateListener(this.stateListener);
        this.handler = new Handler();
        this.inactivityTimer = new InactivityTimer(activity, new Runnable() { // from class: com.journeyapps.barcodescanner.CaptureManager.3
            @Override // java.lang.Runnable
            public void run() {
                Log.d(CaptureManager.TAG, "Finishing due to inactivity");
                CaptureManager.this.finish();
            }
        });
        this.beepManager = new BeepManager(activity);
    }

    public void initializeFromIntent(Intent intent, Bundle savedInstanceState) {
        Window window = this.activity.getWindow();
        window.addFlags(ParserConstants.LSHIFTASSIGN);
        if (savedInstanceState != null) {
            this.orientationLock = savedInstanceState.getInt("SAVED_ORIENTATION_LOCK", -1);
        }
        if (intent != null) {
            if (this.orientationLock == -1) {
                boolean orientationLocked = intent.getBooleanExtra("SCAN_ORIENTATION_LOCKED", true);
                if (orientationLocked) {
                    lockOrientation();
                }
            }
            if ("com.google.zxing.client.android.SCAN".equals(intent.getAction())) {
                this.barcodeView.initializeFromIntent(intent);
            }
            if (!intent.getBooleanExtra("BEEP_ENABLED", true)) {
                this.beepManager.setBeepEnabled(false);
                this.beepManager.updatePrefs();
            }
            if (intent.hasExtra("TIMEOUT")) {
                Runnable runnable = new Runnable() { // from class: com.journeyapps.barcodescanner.CaptureManager.4
                    @Override // java.lang.Runnable
                    public void run() {
                        CaptureManager.this.returnResultTimeout();
                    }
                };
                this.handler.postDelayed(runnable, intent.getLongExtra("TIMEOUT", 0L));
            }
            if (intent.getBooleanExtra("BARCODE_IMAGE_ENABLED", false)) {
                this.returnBarcodeImagePath = true;
            }
        }
    }

    protected void lockOrientation() {
        if (this.orientationLock == -1) {
            Display display = this.activity.getWindowManager().getDefaultDisplay();
            int rotation = display.getRotation();
            int baseOrientation = this.activity.getResources().getConfiguration().orientation;
            int orientation = 0;
            if (baseOrientation == 2) {
                if (rotation == 0 || rotation == 1) {
                    orientation = 0;
                } else {
                    orientation = 8;
                }
            } else if (baseOrientation == 1) {
                if (rotation == 0 || rotation == 3) {
                    orientation = 1;
                } else {
                    orientation = 9;
                }
            }
            this.orientationLock = orientation;
        }
        this.activity.setRequestedOrientation(this.orientationLock);
    }

    public void decode() {
        this.barcodeView.decodeSingle(this.callback);
    }

    public void onResume() {
        if (Build.VERSION.SDK_INT >= 23) {
            openCameraWithPermission();
        } else {
            this.barcodeView.resume();
        }
        this.beepManager.updatePrefs();
        this.inactivityTimer.start();
    }

    @TargetApi(23)
    private void openCameraWithPermission() {
        if (ContextCompat.checkSelfPermission(this.activity, "android.permission.CAMERA") == 0) {
            this.barcodeView.resume();
        } else if (!this.askedPermission) {
            ActivityCompat.requestPermissions(this.activity, new String[]{"android.permission.CAMERA"}, cameraPermissionReqCode);
            this.askedPermission = true;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == cameraPermissionReqCode) {
            if (grantResults.length > 0 && grantResults[0] == 0) {
                this.barcodeView.resume();
            } else {
                displayFrameworkBugMessageAndExit();
            }
        }
    }

    public void onPause() {
        this.barcodeView.pause();
        this.inactivityTimer.cancel();
        this.beepManager.close();
    }

    public void onDestroy() {
        this.destroyed = true;
        this.inactivityTimer.cancel();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("SAVED_ORIENTATION_LOCK", this.orientationLock);
    }

    public static Intent resultIntent(BarcodeResult rawResult, String barcodeImagePath) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.addFlags(524288);
        intent.putExtra("SCAN_RESULT", rawResult.toString());
        intent.putExtra("SCAN_RESULT_FORMAT", rawResult.getBarcodeFormat().toString());
        byte[] rawBytes = rawResult.getRawBytes();
        if (rawBytes != null && rawBytes.length > 0) {
            intent.putExtra("SCAN_RESULT_BYTES", rawBytes);
        }
        Map<ResultMetadataType, ?> metadata = rawResult.getResultMetadata();
        if (metadata != null) {
            if (metadata.containsKey(ResultMetadataType.UPC_EAN_EXTENSION)) {
                intent.putExtra("SCAN_RESULT_UPC_EAN_EXTENSION", metadata.get(ResultMetadataType.UPC_EAN_EXTENSION).toString());
            }
            Number orientation = (Number) metadata.get(ResultMetadataType.ORIENTATION);
            if (orientation != null) {
                intent.putExtra("SCAN_RESULT_ORIENTATION", orientation.intValue());
            }
            String ecLevel = (String) metadata.get(ResultMetadataType.ERROR_CORRECTION_LEVEL);
            if (ecLevel != null) {
                intent.putExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL", ecLevel);
            }
            Iterable<byte[]> byteSegments = (Iterable) metadata.get(ResultMetadataType.BYTE_SEGMENTS);
            if (byteSegments != null) {
                int i = 0;
                for (byte[] byteSegment : byteSegments) {
                    intent.putExtra("SCAN_RESULT_BYTE_SEGMENTS_" + i, byteSegment);
                    i++;
                }
            }
        }
        if (barcodeImagePath != null) {
            intent.putExtra("SCAN_RESULT_IMAGE_PATH", barcodeImagePath);
        }
        return intent;
    }

    private String getBarcodeImagePath(BarcodeResult rawResult) {
        if (!this.returnBarcodeImagePath) {
            return null;
        }
        Bitmap bmp = rawResult.getBitmap();
        try {
            File bitmapFile = File.createTempFile("barcodeimage", ".jpg", this.activity.getCacheDir());
            FileOutputStream outputStream = new FileOutputStream(bitmapFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            String barcodeImagePath = bitmapFile.getAbsolutePath();
            return barcodeImagePath;
        } catch (IOException e) {
            Log.w(TAG, "Unable to create temporary file and store bitmap! " + e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish() {
        this.activity.finish();
    }

    protected void returnResultTimeout() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("TIMEOUT", true);
        this.activity.setResult(0, intent);
        finish();
    }

    protected void returnResult(BarcodeResult rawResult) {
        Intent intent = resultIntent(rawResult, getBarcodeImagePath(rawResult));
        this.activity.setResult(-1, intent);
        finish();
    }

    protected void displayFrameworkBugMessageAndExit() {
        if (!this.activity.isFinishing() && !this.destroyed) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
            builder.setTitle(this.activity.getString(R.string.zxing_app_name));
            builder.setMessage(this.activity.getString(R.string.zxing_msg_camera_framework_bug));
            builder.setPositiveButton(R.string.zxing_button_ok, new DialogInterface.OnClickListener() { // from class: com.journeyapps.barcodescanner.CaptureManager.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    CaptureManager.this.finish();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.journeyapps.barcodescanner.CaptureManager.6
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialog) {
                    CaptureManager.this.finish();
                }
            });
            builder.show();
        }
    }
}
