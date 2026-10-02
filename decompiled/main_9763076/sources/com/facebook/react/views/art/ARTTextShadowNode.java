package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ARTTextShadowNode extends ARTShapeShadowNode {
    private ReadableMap mFrame;
    private int mTextAlignment = 0;

    @ReactProp(name = "frame")
    public void setFrame(ReadableMap frame) {
        this.mFrame = frame;
    }

    @ReactProp(defaultInt = 0, name = "alignment")
    public void setAlignment(int alignment) {
        this.mTextAlignment = alignment;
    }

    @Override // com.facebook.react.views.art.ARTShapeShadowNode, com.facebook.react.views.art.ARTVirtualNode
    public void draw(Canvas canvas, Paint paint, float opacity) {
        ReadableArray linesProp;
        if (this.mFrame != null) {
            float opacity2 = opacity * this.mOpacity;
            if (opacity2 > 0.01f && this.mFrame.hasKey("lines") && (linesProp = this.mFrame.getArray("lines")) != null && linesProp.size() != 0) {
                saveAndSetupCanvas(canvas);
                String[] lines = new String[linesProp.size()];
                for (int i = 0; i < lines.length; i++) {
                    lines[i] = linesProp.getString(i);
                }
                String text = TextUtils.join("\n", lines);
                if (setupStrokePaint(paint, opacity2)) {
                    applyTextPropertiesToPaint(paint);
                    if (this.mPath == null) {
                        canvas.drawText(text, 0.0f, -paint.ascent(), paint);
                    } else {
                        canvas.drawTextOnPath(text, this.mPath, 0.0f, 0.0f, paint);
                    }
                }
                if (setupFillPaint(paint, opacity2)) {
                    applyTextPropertiesToPaint(paint);
                    if (this.mPath == null) {
                        canvas.drawText(text, 0.0f, -paint.ascent(), paint);
                    } else {
                        canvas.drawTextOnPath(text, this.mPath, 0.0f, 0.0f, paint);
                    }
                }
                restoreCanvas(canvas);
                markUpdateSeen();
            }
        }
    }

    private void applyTextPropertiesToPaint(Paint paint) {
        ReadableMap font;
        int fontStyle;
        int alignment = this.mTextAlignment;
        switch (alignment) {
            case 0:
                paint.setTextAlign(Paint.Align.LEFT);
                break;
            case 1:
                paint.setTextAlign(Paint.Align.RIGHT);
                break;
            case 2:
                paint.setTextAlign(Paint.Align.CENTER);
                break;
        }
        if (this.mFrame != null && this.mFrame.hasKey("font") && (font = this.mFrame.getMap("font")) != null) {
            float fontSize = 12.0f;
            if (font.hasKey("fontSize")) {
                fontSize = (float) font.getDouble("fontSize");
            }
            paint.setTextSize(this.mScale * fontSize);
            boolean isBold = font.hasKey("fontWeight") && "bold".equals(font.getString("fontWeight"));
            boolean isItalic = font.hasKey("fontStyle") && "italic".equals(font.getString("fontStyle"));
            if (isBold && isItalic) {
                fontStyle = 3;
            } else if (isBold) {
                fontStyle = 1;
            } else if (isItalic) {
                fontStyle = 2;
            } else {
                fontStyle = 0;
            }
            paint.setTypeface(Typeface.create(font.getString("fontFamily"), fontStyle));
        }
    }
}
