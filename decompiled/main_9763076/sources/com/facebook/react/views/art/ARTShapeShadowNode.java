package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ARTShapeShadowNode extends ARTVirtualNode {
    private float[] mFillColor;
    protected Path mPath;
    private float[] mStrokeColor;
    private float[] mStrokeDash;
    private float mStrokeWidth = 1.0f;
    private int mStrokeCap = 1;
    private int mStrokeJoin = 1;

    @ReactProp(name = "d")
    public void setShapePath(ReadableArray shapePath) {
        float[] pathData = PropHelper.toFloatArray(shapePath);
        this.mPath = createPath(pathData);
        markUpdated();
    }

    @ReactProp(name = "stroke")
    public void setStroke(ReadableArray strokeColors) {
        this.mStrokeColor = PropHelper.toFloatArray(strokeColors);
        markUpdated();
    }

    @ReactProp(name = "strokeDash")
    public void setStrokeDash(ReadableArray strokeDash) {
        this.mStrokeDash = PropHelper.toFloatArray(strokeDash);
        markUpdated();
    }

    @ReactProp(name = "fill")
    public void setFill(ReadableArray fillColors) {
        this.mFillColor = PropHelper.toFloatArray(fillColors);
        markUpdated();
    }

    @ReactProp(defaultFloat = 1.0f, name = "strokeWidth")
    public void setStrokeWidth(float strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        markUpdated();
    }

    @ReactProp(defaultInt = 1, name = "strokeCap")
    public void setStrokeCap(int strokeCap) {
        this.mStrokeCap = strokeCap;
        markUpdated();
    }

    @ReactProp(defaultInt = 1, name = "strokeJoin")
    public void setStrokeJoin(int strokeJoin) {
        this.mStrokeJoin = strokeJoin;
        markUpdated();
    }

    @Override // com.facebook.react.views.art.ARTVirtualNode
    public void draw(Canvas canvas, Paint paint, float opacity) {
        float opacity2 = opacity * this.mOpacity;
        if (opacity2 > 0.01f) {
            saveAndSetupCanvas(canvas);
            if (this.mPath == null) {
                throw new JSApplicationIllegalArgumentException("Shapes should have a valid path (d) prop");
            }
            if (setupFillPaint(paint, opacity2)) {
                canvas.drawPath(this.mPath, paint);
            }
            if (setupStrokePaint(paint, opacity2)) {
                canvas.drawPath(this.mPath, paint);
            }
            restoreCanvas(canvas);
        }
        markUpdateSeen();
    }

    protected boolean setupStrokePaint(Paint paint, float opacity) {
        if (this.mStrokeWidth == 0.0f || this.mStrokeColor == null || this.mStrokeColor.length == 0) {
            return false;
        }
        paint.reset();
        paint.setFlags(1);
        paint.setStyle(Paint.Style.STROKE);
        switch (this.mStrokeCap) {
            case 0:
                paint.setStrokeCap(Paint.Cap.BUTT);
                break;
            case 1:
                paint.setStrokeCap(Paint.Cap.ROUND);
                break;
            case 2:
                paint.setStrokeCap(Paint.Cap.SQUARE);
                break;
            default:
                throw new JSApplicationIllegalArgumentException("strokeCap " + this.mStrokeCap + " unrecognized");
        }
        switch (this.mStrokeJoin) {
            case 0:
                paint.setStrokeJoin(Paint.Join.MITER);
                break;
            case 1:
                paint.setStrokeJoin(Paint.Join.ROUND);
                break;
            case 2:
                paint.setStrokeJoin(Paint.Join.BEVEL);
                break;
            default:
                throw new JSApplicationIllegalArgumentException("strokeJoin " + this.mStrokeJoin + " unrecognized");
        }
        paint.setStrokeWidth(this.mStrokeWidth * this.mScale);
        paint.setARGB((int) (this.mStrokeColor.length > 3 ? this.mStrokeColor[3] * opacity * 255.0f : opacity * 255.0f), (int) (this.mStrokeColor[0] * 255.0f), (int) (this.mStrokeColor[1] * 255.0f), (int) (this.mStrokeColor[2] * 255.0f));
        if (this.mStrokeDash != null && this.mStrokeDash.length > 0) {
            paint.setPathEffect(new DashPathEffect(this.mStrokeDash, 0.0f));
        }
        return true;
    }

    protected boolean setupFillPaint(Paint paint, float opacity) {
        if (this.mFillColor == null || this.mFillColor.length <= 0) {
            return false;
        }
        paint.reset();
        paint.setFlags(1);
        paint.setStyle(Paint.Style.FILL);
        int colorType = (int) this.mFillColor[0];
        switch (colorType) {
            case 0:
                paint.setARGB((int) (this.mFillColor.length > 4 ? this.mFillColor[4] * opacity * 255.0f : opacity * 255.0f), (int) (this.mFillColor[1] * 255.0f), (int) (this.mFillColor[2] * 255.0f), (int) (this.mFillColor[3] * 255.0f));
                break;
            default:
                FLog.w("React", "ART: Color type " + colorType + " not supported!");
                break;
        }
        return true;
    }

    private Path createPath(float[] data) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        int i = 0;
        while (i < data.length) {
            int i2 = i + 1;
            int type = (int) data[i];
            switch (type) {
                case 0:
                    int i3 = i2 + 1;
                    path.moveTo(data[i2] * this.mScale, data[i3] * this.mScale);
                    i = i3 + 1;
                    break;
                case 1:
                    path.close();
                    i = i2;
                    break;
                case 2:
                    int i4 = i2 + 1;
                    path.lineTo(data[i2] * this.mScale, data[i4] * this.mScale);
                    i = i4 + 1;
                    break;
                case 3:
                    int i5 = i2 + 1;
                    float f = data[i2] * this.mScale;
                    int i6 = i5 + 1;
                    float f2 = data[i5] * this.mScale;
                    int i7 = i6 + 1;
                    float f3 = data[i6] * this.mScale;
                    int i8 = i7 + 1;
                    float f4 = data[i7] * this.mScale;
                    int i9 = i8 + 1;
                    path.cubicTo(f, f2, f3, f4, data[i8] * this.mScale, data[i9] * this.mScale);
                    i = i9 + 1;
                    break;
                case 4:
                    int i10 = i2 + 1;
                    float x = data[i2] * this.mScale;
                    int i11 = i10 + 1;
                    float y = data[i10] * this.mScale;
                    int i12 = i11 + 1;
                    float r = data[i11] * this.mScale;
                    int i13 = i12 + 1;
                    float start = (float) Math.toDegrees(data[i12]);
                    int i14 = i13 + 1;
                    float end = (float) Math.toDegrees(data[i13]);
                    int i15 = i14 + 1;
                    boolean clockwise = data[i14] == 0.0f;
                    if (!clockwise) {
                        end = 360.0f - end;
                    }
                    float sweep = start - end;
                    RectF oval = new RectF(x - r, y - r, x + r, y + r);
                    path.addArc(oval, start, sweep);
                    i = i15;
                    break;
                default:
                    throw new JSApplicationIllegalArgumentException("Unrecognized drawing instruction " + type);
            }
        }
        return path;
    }
}
