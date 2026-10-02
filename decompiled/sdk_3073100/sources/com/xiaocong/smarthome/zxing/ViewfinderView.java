package com.xiaocong.smarthome.zxing;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.google.zxing.ResultPoint;
import com.xiaocong.smarthome.zxing.camera.CameraManager;
import com.xiaocong.smarthome.zxing.utils.DPIUtil;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class ViewfinderView extends View {
    private static int MIDDLE_LINE_HEIGHT = DPIUtil.dip2px(55.0f);
    private static float density;
    private final int ScreenRate;
    private int bgPadding;
    boolean isFirst;
    boolean isLine;
    private Collection<ResultPoint> lastPossibleResultPoints;
    private final int maskColor;
    private Bitmap middleBmp;
    private final Paint paint;
    private Collection<ResultPoint> possibleResultPoints;
    private Bitmap resultBitmap;
    private final int resultColor;
    private final int resultPointColor;
    private int slideBottom;
    private int slideTop;
    private Button zoomMinus;
    private Button zoomPlus;
    private VerticalSeekBar zoomSeekBar;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bgPadding = DPIUtil.dip2px(10.0f);
        this.isLine = false;
        density = context.getResources().getDisplayMetrics().density;
        this.ScreenRate = (int) (20.0f * density);
        this.paint = new Paint();
        Resources resources = getResources();
        this.maskColor = resources.getColor(R.color.viewfinder_mask);
        this.resultColor = resources.getColor(R.color.white);
        this.resultPointColor = resources.getColor(R.color.possible_result_points);
        this.possibleResultPoints = new HashSet(5);
        this.middleBmp = ((BitmapDrawable) resources.getDrawable(R.mipmap.barcode_scan_line)).getBitmap();
    }

    @Override // android.view.View
    @TargetApi(11)
    public void onDraw(Canvas canvas) {
        Rect frame;
        CameraManager cm = CameraManager.get();
        if (cm != null && (frame = cm.getFramingRect()) != null) {
            if (!this.isFirst) {
                this.isFirst = true;
                this.slideTop = frame.top - MIDDLE_LINE_HEIGHT;
                this.slideBottom = frame.bottom - MIDDLE_LINE_HEIGHT;
            }
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            int padding = DPIUtil.dip2px(25.0f);
            this.paint.setColor(this.resultColor);
            canvas.drawRect(new RectF(0.0f, 0.0f, width + 1, frame.top - 1), this.paint);
            canvas.drawRect(new RectF(0.0f, frame.top - 1, frame.left - 1, frame.bottom + 1), this.paint);
            canvas.drawRect(new RectF(frame.right + 1, frame.top - 1, width + 1, frame.bottom + 1), this.paint);
            canvas.drawRect(new RectF(0.0f, frame.bottom + 1, width, height), this.paint);
            if (this.zoomSeekBar != null && Build.VERSION.SDK_INT >= 11) {
                this.zoomSeekBar.setTop(frame.top);
                this.zoomSeekBar.setBottom(frame.bottom);
                this.zoomSeekBar.setX(frame.right + (this.zoomSeekBar.getWidth() / 2));
                this.zoomPlus.setTop(frame.top);
                this.zoomPlus.setX(frame.right + (this.zoomSeekBar.getWidth() / 2));
                this.zoomMinus.setTop(frame.bottom - ((this.zoomSeekBar.getWidth() * 3) / 2));
                this.zoomMinus.setX(frame.right + (this.zoomSeekBar.getWidth() / 2));
            }
            this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
            if (this.resultBitmap != null) {
                this.paint.setAlpha(255);
                canvas.drawBitmap(this.resultBitmap, frame.left, frame.top, this.paint);
                return;
            }
            this.paint.setColor(Color.parseColor("#222222"));
            canvas.drawRect(frame.left - 25, frame.top - 25, (frame.left - 25) + this.ScreenRate, (frame.top - 25) + 4, this.paint);
            canvas.drawRect(frame.left - 25, frame.top - 25, (frame.left - 25) + 4, (frame.top - 25) + this.ScreenRate, this.paint);
            canvas.drawRect((frame.right + 25) - this.ScreenRate, frame.top - 25, frame.right + 25, (frame.top - 25) + 4, this.paint);
            canvas.drawRect((frame.right + 25) - 4, frame.top - 25, frame.right + 25, (frame.top - 25) + this.ScreenRate, this.paint);
            canvas.drawRect(frame.left - 25, (frame.bottom + 25) - 4, (frame.left - 25) + this.ScreenRate, frame.bottom + 25, this.paint);
            canvas.drawRect(frame.left - 25, (frame.bottom + 25) - this.ScreenRate, (frame.left - 25) + 4, frame.bottom + 25, this.paint);
            canvas.drawRect((frame.right + 25) - this.ScreenRate, (frame.bottom + 25) - 4, frame.right + 25, frame.bottom + 25, this.paint);
            canvas.drawRect((frame.right + 25) - 4, (frame.bottom + 25) - this.ScreenRate, frame.right + 25, frame.bottom + 25, this.paint);
            this.paint.setColor(2145838822);
            canvas.drawLine(frame.left - 1, frame.top - 1, frame.right + 1, frame.top - 1, this.paint);
            canvas.drawLine(frame.left - 1, frame.top - 1, frame.left - 1, frame.bottom + 1, this.paint);
            canvas.drawLine(frame.left - 1, frame.bottom + 1, frame.right + 1, frame.bottom + 1, this.paint);
            canvas.drawLine(frame.right + 1, frame.top - 1, frame.right + 1, frame.bottom + 1 + 1, this.paint);
            Bitmap bitmap = this.middleBmp;
            this.slideTop += 5;
            if (this.slideTop >= this.slideBottom) {
                this.slideTop = frame.top - MIDDLE_LINE_HEIGHT;
            }
            Rect dst = new Rect(frame.left, this.slideTop < frame.top ? frame.top : this.slideTop, frame.right, this.slideTop + MIDDLE_LINE_HEIGHT);
            if (bitmap != null && !bitmap.isRecycled()) {
                Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                canvas.drawBitmap(bitmap, src, dst, this.paint);
            }
            this.paint.setColor(-1);
            this.paint.setTextSize(16.0f * density);
            String hintString = getResources().getString(R.string.capture_tip);
            float txtWidth = this.paint.measureText(hintString);
            int rectCoreLeft = (frame.left + frame.right) / 2;
            canvas.drawText(hintString, (int) (rectCoreLeft - (txtWidth / 2.0f)), (frame.top - (padding * 2)) + (30.0f * density), this.paint);
            Collection<ResultPoint> currentPossible = this.possibleResultPoints;
            Collection<ResultPoint> currentLast = this.lastPossibleResultPoints;
            if (currentPossible.isEmpty()) {
                this.lastPossibleResultPoints = null;
            } else {
                this.possibleResultPoints = new HashSet(5);
                synchronized (this.possibleResultPoints) {
                    this.lastPossibleResultPoints = currentPossible;
                    this.paint.setAlpha(255);
                    this.paint.setColor(this.resultPointColor);
                    try {
                        for (ResultPoint point : currentPossible) {
                            canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 6.0f, this.paint);
                        }
                    } catch (ConcurrentModificationException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (currentLast != null) {
                synchronized (this.possibleResultPoints) {
                    this.paint.setAlpha(127);
                    this.paint.setColor(this.resultPointColor);
                    for (ResultPoint point2 : currentLast) {
                        canvas.drawCircle(frame.left + point2.getX(), frame.top + point2.getY(), 3.0f, this.paint);
                    }
                }
            }
            postInvalidateDelayed(3L, frame.left, frame.top, frame.right, frame.bottom);
        }
    }

    public void drawViewfinder() {
        this.resultBitmap = null;
        invalidate();
    }

    public void drawZoomBar(VerticalSeekBar zoomSeekBar, Button zoomPlus, Button zoomMinus) {
        this.zoomSeekBar = zoomSeekBar;
        this.zoomPlus = zoomPlus;
        this.zoomMinus = zoomMinus;
    }

    public void addPossibleResultPoint(ResultPoint point) {
        synchronized (this.possibleResultPoints) {
            this.possibleResultPoints.add(point);
        }
    }
}
