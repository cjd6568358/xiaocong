package com.facebook.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ScalingUtils {

    public interface ScaleType {
        public static final ScaleType FIT_XY = ScaleTypeFitXY.INSTANCE;
        public static final ScaleType FIT_START = ScaleTypeFitStart.INSTANCE;
        public static final ScaleType FIT_CENTER = ScaleTypeFitCenter.INSTANCE;
        public static final ScaleType FIT_END = ScaleTypeFitEnd.INSTANCE;
        public static final ScaleType CENTER = ScaleTypeCenter.INSTANCE;
        public static final ScaleType CENTER_INSIDE = ScaleTypeCenterInside.INSTANCE;
        public static final ScaleType CENTER_CROP = ScaleTypeCenterCrop.INSTANCE;
        public static final ScaleType FOCUS_CROP = ScaleTypeFocusCrop.INSTANCE;

        Matrix getTransform(Matrix matrix, Rect rect, int i, int i2, float f, float f2);
    }

    public interface StatefulScaleType {
        Object getState();
    }

    public static abstract class AbstractScaleType implements ScaleType {
        public abstract void getTransformImpl(Matrix matrix, Rect rect, int i, int i2, float f, float f2, float f3, float f4);

        @Override // com.facebook.drawee.drawable.ScalingUtils.ScaleType
        public Matrix getTransform(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY) {
            float sX = parentRect.width() / childWidth;
            float sY = parentRect.height() / childHeight;
            getTransformImpl(outTransform, parentRect, childWidth, childHeight, focusX, focusY, sX, sY);
            return outTransform;
        }
    }

    private static class ScaleTypeFitXY extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitXY();

        private ScaleTypeFitXY() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float dx = parentRect.left;
            float dy = parentRect.top;
            outTransform.setScale(scaleX, scaleY);
            outTransform.postTranslate((int) (dx + 0.5f), (int) (0.5f + dy));
        }
    }

    private static class ScaleTypeFitStart extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitStart();

        private ScaleTypeFitStart() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            float dx = parentRect.left;
            float dy = parentRect.top;
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((int) (dx + 0.5f), (int) (0.5f + dy));
        }
    }

    private static class ScaleTypeFitCenter extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitCenter();

        private ScaleTypeFitCenter() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            float dx = parentRect.left + ((parentRect.width() - (childWidth * scale)) * 0.5f);
            float dy = parentRect.top + ((parentRect.height() - (childHeight * scale)) * 0.5f);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        }
    }

    private static class ScaleTypeFitEnd extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitEnd();

        private ScaleTypeFitEnd() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            float dx = parentRect.left + (parentRect.width() - (childWidth * scale));
            float dy = parentRect.top + (parentRect.height() - (childHeight * scale));
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        }
    }

    private static class ScaleTypeCenter extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenter();

        private ScaleTypeCenter() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float dx = parentRect.left + ((parentRect.width() - childWidth) * 0.5f);
            float dy = parentRect.top + ((parentRect.height() - childHeight) * 0.5f);
            outTransform.setTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        }
    }

    private static class ScaleTypeCenterInside extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterInside();

        private ScaleTypeCenterInside() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(Math.min(scaleX, scaleY), 1.0f);
            float dx = parentRect.left + ((parentRect.width() - (childWidth * scale)) * 0.5f);
            float dy = parentRect.top + ((parentRect.height() - (childHeight * scale)) * 0.5f);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        }
    }

    private static class ScaleTypeCenterCrop extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterCrop();

        private ScaleTypeCenterCrop() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale;
            float dx;
            float dy;
            if (scaleY > scaleX) {
                scale = scaleY;
                dx = parentRect.left + ((parentRect.width() - (childWidth * scale)) * 0.5f);
                dy = parentRect.top;
            } else {
                scale = scaleX;
                dx = parentRect.left;
                dy = parentRect.top + ((parentRect.height() - (childHeight * scale)) * 0.5f);
            }
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        }
    }

    private static class ScaleTypeFocusCrop extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFocusCrop();

        private ScaleTypeFocusCrop() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale;
            float dx;
            float dy;
            if (scaleY > scaleX) {
                scale = scaleY;
                float dx2 = (parentRect.width() * 0.5f) - ((childWidth * scale) * focusX);
                dx = parentRect.left + Math.max(Math.min(dx2, 0.0f), parentRect.width() - (childWidth * scale));
                dy = parentRect.top;
            } else {
                scale = scaleX;
                dx = parentRect.left;
                float dy2 = (parentRect.height() * 0.5f) - ((childHeight * scale) * focusY);
                dy = parentRect.top + Math.max(Math.min(dy2, 0.0f), parentRect.height() - (childHeight * scale));
            }
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((int) (0.5f + dx), (int) (0.5f + dy));
        }
    }
}
