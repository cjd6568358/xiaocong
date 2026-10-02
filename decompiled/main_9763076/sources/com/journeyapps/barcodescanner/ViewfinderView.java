package com.journeyapps.barcodescanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import bsh.ParserConstants;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ViewfinderView extends View {
    protected CameraPreview cameraPreview;
    protected Rect framingRect;
    protected final int laserColor;
    protected List<ResultPoint> lastPossibleResultPoints;
    protected final int maskColor;
    protected final Paint paint;
    protected List<ResultPoint> possibleResultPoints;
    protected Rect previewFramingRect;
    protected Bitmap resultBitmap;
    protected final int resultColor;
    protected final int resultPointColor;
    protected int scannerAlpha;
    protected static final String TAG = ViewfinderView.class.getSimpleName();
    protected static final int[] SCANNER_ALPHA = {0, 64, ParserConstants.LSHIFTASSIGN, 192, 255, 192, ParserConstants.LSHIFTASSIGN, 64};

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint(1);
        Resources resources = getResources();
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.zxing_finder);
        this.maskColor = attributes.getColor(R.styleable.zxing_finder_zxing_viewfinder_mask, resources.getColor(R.color.zxing_viewfinder_mask));
        this.resultColor = attributes.getColor(R.styleable.zxing_finder_zxing_result_view, resources.getColor(R.color.zxing_result_view));
        this.laserColor = attributes.getColor(R.styleable.zxing_finder_zxing_viewfinder_laser, resources.getColor(R.color.zxing_viewfinder_laser));
        this.resultPointColor = attributes.getColor(R.styleable.zxing_finder_zxing_possible_result_points, resources.getColor(R.color.zxing_possible_result_points));
        attributes.recycle();
        this.scannerAlpha = 0;
        this.possibleResultPoints = new ArrayList(5);
        this.lastPossibleResultPoints = null;
    }

    public void setCameraPreview(CameraPreview view) {
        this.cameraPreview = view;
        view.addStateListener(new CameraPreview.StateListener() { // from class: com.journeyapps.barcodescanner.ViewfinderView.1
            @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
            public void previewSized() {
                ViewfinderView.this.refreshSizes();
                ViewfinderView.this.invalidate();
            }

            @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
            public void previewStarted() {
            }

            @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
            public void previewStopped() {
            }

            @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
            public void cameraError(Exception error) {
            }
        });
    }

    protected void refreshSizes() {
        if (this.cameraPreview != null) {
            Rect framingRect = this.cameraPreview.getFramingRect();
            Rect previewFramingRect = this.cameraPreview.getPreviewFramingRect();
            if (framingRect != null && previewFramingRect != null) {
                this.framingRect = framingRect;
                this.previewFramingRect = previewFramingRect;
            }
        }
    }

    @Override // android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (this.framingRect != null && this.previewFramingRect != null) {
            Rect frame = this.framingRect;
            Rect previewFrame = this.previewFramingRect;
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
            canvas.drawRect(0.0f, 0.0f, width, frame.top, this.paint);
            canvas.drawRect(0.0f, frame.top, frame.left, frame.bottom + 1, this.paint);
            canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, this.paint);
            canvas.drawRect(0.0f, frame.bottom + 1, width, height, this.paint);
            if (this.resultBitmap != null) {
                this.paint.setAlpha(160);
                canvas.drawBitmap(this.resultBitmap, (Rect) null, frame, this.paint);
                return;
            }
            this.paint.setColor(this.laserColor);
            this.paint.setAlpha(SCANNER_ALPHA[this.scannerAlpha]);
            this.scannerAlpha = (this.scannerAlpha + 1) % SCANNER_ALPHA.length;
            int middle = (frame.height() / 2) + frame.top;
            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, this.paint);
            float scaleX = frame.width() / previewFrame.width();
            float scaleY = frame.height() / previewFrame.height();
            List<ResultPoint> currentPossible = this.possibleResultPoints;
            List<ResultPoint> currentLast = this.lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                this.lastPossibleResultPoints = null;
            } else {
                this.possibleResultPoints = new ArrayList(5);
                this.lastPossibleResultPoints = currentPossible;
                this.paint.setAlpha(160);
                this.paint.setColor(this.resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(((int) (point.getX() * scaleX)) + frameLeft, ((int) (point.getY() * scaleY)) + frameTop, 6.0f, this.paint);
                }
            }
            if (currentLast != null) {
                this.paint.setAlpha(80);
                this.paint.setColor(this.resultPointColor);
                for (ResultPoint point2 : currentLast) {
                    canvas.drawCircle(((int) (point2.getX() * scaleX)) + frameLeft, ((int) (point2.getY() * scaleY)) + frameTop, 3.0f, this.paint);
                }
            }
            postInvalidateDelayed(80L, frame.left - 6, frame.top - 6, frame.right + 6, frame.bottom + 6);
        }
    }

    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = this.possibleResultPoints;
        points.add(point);
        int size = points.size();
        if (size > 20) {
            points.subList(0, size - 10).clear();
        }
    }
}
