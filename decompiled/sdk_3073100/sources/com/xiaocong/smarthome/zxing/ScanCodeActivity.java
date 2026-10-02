package com.xiaocong.smarthome.zxing;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.xiaocong.smarthome.zxing.camera.CameraManager;
import com.xiaocong.smarthome.zxing.utils.IntentJumpUtil;
import com.xiaocong.smarthome.zxing.utils.statusbar.StatusBarHelper;
import java.io.IOException;
import java.util.Vector;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ScanCodeActivity extends Activity implements SurfaceHolder.Callback {
    private static String TAG = "Barcode_CaptureActivity";
    private String characterSet;
    private Vector<BarcodeFormat> decodeFormats;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private ProgressDialog mProgressDialog;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private boolean vibrate;
    private ViewfinderView viewfinderView;
    private Button zoomMinus;
    private Button zoomPlus;
    private VerticalSeekBar zoomSeekBar;
    private boolean isZoomSupport = false;
    private boolean isLightOn = false;
    private boolean isCameraReady = false;
    private boolean isUserCancelThread = false;
    public SystemBarTintManager tintManager = null;
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() { // from class: com.xiaocong.smarthome.zxing.ScanCodeActivity.6
        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        StatusBarHelper.translucent(this);
        setContentView(R.layout.activity_scan);
        findViewById(R.id.scan_statusbar_view).setLayoutParams(new RelativeLayout.LayoutParams(-1, getStatusHeight()));
        Window window = getWindow();
        window.addFlags(128);
        CameraManager.init(getApplication());
        this.viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        this.zoomSeekBar = (VerticalSeekBar) findViewById(R.id.scan_zoom_seekbar);
        this.zoomPlus = (Button) findViewById(R.id.scan_zoom_btn_plus);
        this.zoomMinus = (Button) findViewById(R.id.scan_zoom_btn_minus);
        ImageView ivLeft = (ImageView) findViewById(R.id.left_scan_title_image);
        TextView tvTitle = (TextView) findViewById(R.id.center_txt_scan_title);
        tvTitle.setText("扫一扫");
        ivLeft.setOnClickListener(new View.OnClickListener() { // from class: com.xiaocong.smarthome.zxing.ScanCodeActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ScanCodeActivity.this.finish();
            }
        });
        findViewById(R.id.preview_view).setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaocong.smarthome.zxing.ScanCodeActivity.2
            private float oldDist = 0.0f;

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.view.View.OnTouchListener
            @SuppressLint({"ClickableViewAccessibility"})
            @TargetApi(5)
            public boolean onTouch(View v, MotionEvent event) {
                if (ScanCodeActivity.this.isCameraReady && event.getPointerCount() == 2) {
                    switch (event.getAction() & 255) {
                        case 1:
                        case 6:
                            this.oldDist = 0.0f;
                            break;
                        case 2:
                            float newDist = spacing(event);
                            if (this.oldDist <= 0.0f) {
                                this.oldDist = newDist;
                            } else if (newDist - this.oldDist >= 10.0f || newDist - this.oldDist <= -10.0f) {
                                int value = ((int) (newDist - this.oldDist)) / 10;
                                try {
                                    CameraManager.get().setZoom(value);
                                    ScanCodeActivity.this.zoomSeekBar.setProgress(CameraManager.get().getCurrentZoomValue());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                this.oldDist = newDist;
                            }
                            break;
                        case 5:
                            this.oldDist = spacing(event);
                            break;
                    }
                }
                return true;
            }

            @TargetApi(5)
            private float spacing(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return (float) Math.sqrt((x * x) + (y * y));
            }
        });
        this.zoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.xiaocong.smarthome.zxing.ScanCodeActivity.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (ScanCodeActivity.this.isCameraReady) {
                    try {
                        CameraManager.get().setZoomValue(progress);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.hasSurface = false;
        this.inactivityTimer = new InactivityTimer(this);
    }

    public void onClick(View v) {
        v.getId();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        this.viewfinderView.setVisibility(0);
        final SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (this.hasSurface) {
            post(new Runnable() { // from class: com.xiaocong.smarthome.zxing.ScanCodeActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    ScanCodeActivity.this.initCamera(surfaceHolder);
                }
            });
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(3);
        }
        this.decodeFormats = null;
        this.characterSet = null;
        this.playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService("audio");
        if (audioService.getRingerMode() != 2) {
            this.playBeep = false;
        }
        this.vibrate = true;
        if (!this.isCameraReady) {
            this.zoomSeekBar.setVisibility(4);
            this.zoomPlus.setVisibility(4);
            this.zoomMinus.setVisibility(4);
        }
        if (this.isLightOn) {
            this.isLightOn = false;
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        if (this.handler != null) {
            this.handler.quitSynchronously();
            this.handler.clear();
            this.handler = null;
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        try {
            CameraManager.get().closeDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.inactivityTimer.shutdown();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.viewfinderView = null;
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
        }
        this.mediaPlayer = null;
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
        }
        this.mProgressDialog = null;
    }

    public void handleDecode(Result result) {
        this.inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(this, "请扫描正确的二维码", 0).show();
        } else {
            IntentJumpUtil intentJumputil = new IntentJumpUtil(this, this.handler);
            intentJumputil.handlerQcodeString(resultString);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            int maxSeekBarProgress = CameraManager.get().getMaxZoomValue();
            if (maxSeekBarProgress > 0) {
                this.isZoomSupport = true;
                this.zoomSeekBar.setMax(maxSeekBarProgress);
                this.zoomSeekBar.setProgress(0);
                this.viewfinderView.drawZoomBar(this.zoomSeekBar, this.zoomPlus, this.zoomMinus);
            }
            if (Build.VERSION.SDK_INT >= 11) {
                this.zoomSeekBar.setVisibility(0);
                this.zoomPlus.setVisibility(0);
                this.zoomMinus.setVisibility(0);
            }
            this.isCameraReady = true;
            if (this.handler == null) {
                this.handler = new CaptureActivityHandler(this.decodeFormats, this.characterSet);
            }
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.please_check_camera_permission), 0).show();
            this.isCameraReady = false;
            finish();
        } catch (RuntimeException e2) {
            Toast.makeText(this, getString(R.string.please_check_camera_permission), 0).show();
            this.isCameraReady = false;
            finish();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(final SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        if (!this.hasSurface) {
            this.hasSurface = true;
            post(new Runnable() { // from class: com.xiaocong.smarthome.zxing.ScanCodeActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    ScanCodeActivity.this.initCamera(holder);
                }
            });
        }
    }

    public void post(Runnable action) {
        new Handler().post(action);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        this.hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return this.viewfinderView;
    }

    public Handler getHandler() {
        return this.handler;
    }

    public void drawViewfinder() {
        this.viewfinderView.drawViewfinder();
    }

    private void playBeepSoundAndVibrate() {
        if (this.playBeep && this.mediaPlayer != null) {
            this.mediaPlayer.start();
        }
        if (this.vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService("vibrator");
            vibrator.vibrate(200L);
        }
    }

    public final class CaptureActivityHandler extends Handler {
        private DecodeThread decodeThread;
        private XcSoftReference<ScanCodeActivity> softReference;
        private State state;

        public CaptureActivityHandler(Vector<BarcodeFormat> decodeFormats, String characterSet) {
            ScanCodeActivity captureActivity;
            this.softReference = new XcSoftReference<>(ScanCodeActivity.this);
            if (this.softReference == null || (captureActivity = this.softReference.get()) == null) {
                captureActivity = ScanCodeActivity.this;
                this.softReference = new XcSoftReference<>(ScanCodeActivity.this);
            }
            this.decodeThread = new DecodeThread(captureActivity, decodeFormats, characterSet, new ViewfinderResultPointCallback(ScanCodeActivity.this.getViewfinderView()));
            this.decodeThread.start();
            this.state = State.SUCCESS;
            try {
                CameraManager.get().startPreview();
            } catch (Exception e) {
            }
            restartPreviewAndDecode();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == R.id.auto_focus) {
                if (this.state == State.PREVIEW) {
                    try {
                        CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                return;
            }
            if (message.what == R.id.restart_preview) {
                restartPreviewAndDecode();
                return;
            }
            if (message.what == R.id.decode_succeeded) {
                this.state = State.SUCCESS;
                message.getData();
                ScanCodeActivity.this.handleDecode((Result) message.obj);
                return;
            }
            if (message.what == R.id.decode_failed) {
                this.state = State.PREVIEW;
                try {
                    CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), R.id.decode);
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            if (message.what == R.id.return_scan_result) {
                ScanCodeActivity.this.setResult(-1, (Intent) message.obj);
                ScanCodeActivity.this.finish();
            } else if (message.what == R.id.launch_product_query) {
                String url = (String) message.obj;
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                intent.addFlags(524288);
                ScanCodeActivity.this.startActivity(intent);
            }
        }

        public void quitSynchronously() {
            this.state = State.DONE;
            try {
                CameraManager.get().stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Message quit = Message.obtain(this.decodeThread.getHandler(), R.id.quit);
            quit.sendToTarget();
            try {
                this.decodeThread.join();
            } catch (InterruptedException e2) {
            }
            removeMessages(R.id.decode_succeeded);
            removeMessages(R.id.decode_failed);
        }

        private void restartPreviewAndDecode() {
            if (this.state == State.SUCCESS) {
                this.state = State.PREVIEW;
                try {
                    CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), R.id.decode);
                    CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ScanCodeActivity.this.drawViewfinder();
            }
        }

        public void clear() {
            if (this.decodeThread != null) {
                this.decodeThread.interrupt();
            }
            this.decodeThread = null;
        }
    }

    private int getStatusHeight() {
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            int statusHeight = getResources().getDimensionPixelSize(height);
            return statusHeight;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
