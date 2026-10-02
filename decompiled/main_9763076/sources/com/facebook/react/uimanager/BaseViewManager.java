package com.facebook.react.uimanager;

import android.os.Build;
import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.annotations.ReactProp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BaseViewManager<T extends View, C extends LayoutShadowNode> extends ViewManager<T, C> {
    private static final String PROP_ACCESSIBILITY_COMPONENT_TYPE = "accessibilityComponentType";
    private static final String PROP_ACCESSIBILITY_LABEL = "accessibilityLabel";
    private static final String PROP_ACCESSIBILITY_LIVE_REGION = "accessibilityLiveRegion";
    private static final String PROP_BACKGROUND_COLOR = "backgroundColor";
    private static final String PROP_ELEVATION = "elevation";
    private static final String PROP_IMPORTANT_FOR_ACCESSIBILITY = "importantForAccessibility";
    private static final String PROP_OPACITY = "opacity";
    private static final String PROP_RENDER_TO_HARDWARE_TEXTURE = "renderToHardwareTextureAndroid";
    private static final String PROP_ROTATION = "rotation";
    private static final String PROP_SCALE_X = "scaleX";
    private static final String PROP_SCALE_Y = "scaleY";
    public static final String PROP_TEST_ID = "testID";
    private static final String PROP_TRANSFORM = "transform";
    private static final String PROP_TRANSLATE_X = "translateX";
    private static final String PROP_TRANSLATE_Y = "translateY";
    private static final String PROP_Z_INDEX = "zIndex";
    private static MatrixMathHelper.MatrixDecompositionContext sMatrixDecompositionContext = new MatrixMathHelper.MatrixDecompositionContext();
    private static double[] sTransformDecompositionArray = new double[16];

    @ReactProp(customType = "Color", defaultInt = 0, name = PROP_BACKGROUND_COLOR)
    public void setBackgroundColor(T view, int backgroundColor) {
        view.setBackgroundColor(backgroundColor);
    }

    @ReactProp(name = PROP_TRANSFORM)
    public void setTransform(T view, ReadableArray matrix) {
        if (matrix == null) {
            resetTransformProperty(view);
        } else {
            setTransformProperty(view, matrix);
        }
    }

    @ReactProp(defaultFloat = 1.0f, name = PROP_OPACITY)
    public void setOpacity(T view, float opacity) {
        view.setAlpha(opacity);
    }

    @ReactProp(name = PROP_ELEVATION)
    public void setElevation(T view, float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.setElevation(PixelUtil.toPixelFromDIP(elevation));
        }
    }

    @ReactProp(name = PROP_Z_INDEX)
    public void setZIndex(T view, float zIndex) {
        int integerZIndex = Math.round(zIndex);
        ViewGroupManager.setViewZIndex(view, integerZIndex);
    }

    @ReactProp(name = PROP_RENDER_TO_HARDWARE_TEXTURE)
    public void setRenderToHardwareTexture(T view, boolean useHWTexture) {
        view.setLayerType(useHWTexture ? 2 : 0, null);
    }

    @ReactProp(name = PROP_TEST_ID)
    public void setTestId(T view, String testId) {
        view.setTag(testId);
    }

    @ReactProp(name = PROP_ACCESSIBILITY_LABEL)
    public void setAccessibilityLabel(T view, String accessibilityLabel) {
        view.setContentDescription(accessibilityLabel);
    }

    @ReactProp(name = PROP_ACCESSIBILITY_COMPONENT_TYPE)
    public void setAccessibilityComponentType(T view, String accessibilityComponentType) {
        AccessibilityHelper.updateAccessibilityComponentType(view, accessibilityComponentType);
    }

    @ReactProp(name = PROP_IMPORTANT_FOR_ACCESSIBILITY)
    public void setImportantForAccessibility(T view, String importantForAccessibility) {
        if (importantForAccessibility == null || importantForAccessibility.equals("auto")) {
            view.setImportantForAccessibility(0);
            return;
        }
        if (importantForAccessibility.equals("yes")) {
            view.setImportantForAccessibility(1);
        } else if (importantForAccessibility.equals("no")) {
            view.setImportantForAccessibility(2);
        } else if (importantForAccessibility.equals("no-hide-descendants")) {
            view.setImportantForAccessibility(4);
        }
    }

    @ReactProp(name = PROP_ROTATION)
    @Deprecated
    public void setRotation(T view, float rotation) {
        view.setRotation(rotation);
    }

    @ReactProp(defaultFloat = 1.0f, name = PROP_SCALE_X)
    @Deprecated
    public void setScaleX(T view, float scaleX) {
        view.setScaleX(scaleX);
    }

    @ReactProp(defaultFloat = 1.0f, name = PROP_SCALE_Y)
    @Deprecated
    public void setScaleY(T view, float scaleY) {
        view.setScaleY(scaleY);
    }

    @ReactProp(defaultFloat = 0.0f, name = PROP_TRANSLATE_X)
    @Deprecated
    public void setTranslateX(T view, float translateX) {
        view.setTranslationX(PixelUtil.toPixelFromDIP(translateX));
    }

    @ReactProp(defaultFloat = 0.0f, name = PROP_TRANSLATE_Y)
    @Deprecated
    public void setTranslateY(T view, float translateY) {
        view.setTranslationY(PixelUtil.toPixelFromDIP(translateY));
    }

    @ReactProp(name = PROP_ACCESSIBILITY_LIVE_REGION)
    public void setAccessibilityLiveRegion(T view, String liveRegion) {
        if (Build.VERSION.SDK_INT >= 19) {
            if (liveRegion == null || liveRegion.equals("none")) {
                view.setAccessibilityLiveRegion(0);
            } else if (liveRegion.equals("polite")) {
                view.setAccessibilityLiveRegion(1);
            } else if (liveRegion.equals("assertive")) {
                view.setAccessibilityLiveRegion(2);
            }
        }
    }

    private static void setTransformProperty(View view, ReadableArray transforms) {
        TransformHelper.processTransform(transforms, sTransformDecompositionArray);
        MatrixMathHelper.decomposeMatrix(sTransformDecompositionArray, sMatrixDecompositionContext);
        view.setTranslationX(PixelUtil.toPixelFromDIP((float) sMatrixDecompositionContext.translation[0]));
        view.setTranslationY(PixelUtil.toPixelFromDIP((float) sMatrixDecompositionContext.translation[1]));
        view.setRotation((float) sMatrixDecompositionContext.rotationDegrees[2]);
        view.setRotationX((float) sMatrixDecompositionContext.rotationDegrees[0]);
        view.setRotationY((float) sMatrixDecompositionContext.rotationDegrees[1]);
        view.setScaleX((float) sMatrixDecompositionContext.scale[0]);
        view.setScaleY((float) sMatrixDecompositionContext.scale[1]);
    }

    private static void resetTransformProperty(View view) {
        view.setTranslationX(PixelUtil.toPixelFromDIP(0.0f));
        view.setTranslationY(PixelUtil.toPixelFromDIP(0.0f));
        view.setRotation(0.0f);
        view.setRotationX(0.0f);
        view.setRotationY(0.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
    }
}
