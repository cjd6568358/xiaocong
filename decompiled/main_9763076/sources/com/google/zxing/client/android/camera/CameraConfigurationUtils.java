package com.google.zxing.client.android.camera;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.http.HttpStatus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class CameraConfigurationUtils {
    private static final Pattern SEMICOLON = Pattern.compile(";");

    public static void setFocus(Camera.Parameters parameters, CameraSettings.FocusMode focusModeSetting, boolean safeMode) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        String focusMode = null;
        if (safeMode || focusModeSetting == CameraSettings.FocusMode.AUTO) {
            focusMode = findSettableValue("focus mode", supportedFocusModes, "auto");
        } else if (focusModeSetting == CameraSettings.FocusMode.CONTINUOUS) {
            focusMode = findSettableValue("focus mode", supportedFocusModes, "continuous-picture", "continuous-video", "auto");
        } else if (focusModeSetting == CameraSettings.FocusMode.INFINITY) {
            focusMode = findSettableValue("focus mode", supportedFocusModes, "infinity");
        } else if (focusModeSetting == CameraSettings.FocusMode.MACRO) {
            focusMode = findSettableValue("focus mode", supportedFocusModes, "macro");
        }
        if (!safeMode && focusMode == null) {
            focusMode = findSettableValue("focus mode", supportedFocusModes, "macro", "edof");
        }
        if (focusMode != null) {
            if (focusMode.equals(parameters.getFocusMode())) {
                Log.i("CameraConfiguration", "Focus mode already set to " + focusMode);
            } else {
                parameters.setFocusMode(focusMode);
            }
        }
    }

    public static void setTorch(Camera.Parameters parameters, boolean on) {
        String flashMode;
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (on) {
            flashMode = findSettableValue("flash mode", supportedFlashModes, "torch", "on");
        } else {
            flashMode = findSettableValue("flash mode", supportedFlashModes, "off");
        }
        if (flashMode != null) {
            if (flashMode.equals(parameters.getFlashMode())) {
                Log.i("CameraConfiguration", "Flash mode already set to " + flashMode);
            } else {
                Log.i("CameraConfiguration", "Setting flash mode to " + flashMode);
                parameters.setFlashMode(flashMode);
            }
        }
    }

    public static void setBestExposure(Camera.Parameters parameters, boolean lightOn) {
        int minExposure = parameters.getMinExposureCompensation();
        int maxExposure = parameters.getMaxExposureCompensation();
        float step = parameters.getExposureCompensationStep();
        if ((minExposure != 0 || maxExposure != 0) && step > 0.0f) {
            float targetCompensation = lightOn ? 0.0f : 1.5f;
            int compensationSteps = Math.round(targetCompensation / step);
            float actualCompensation = step * compensationSteps;
            int compensationSteps2 = Math.max(Math.min(compensationSteps, maxExposure), minExposure);
            if (parameters.getExposureCompensation() == compensationSteps2) {
                Log.i("CameraConfiguration", "Exposure compensation already set to " + compensationSteps2 + " / " + actualCompensation);
                return;
            } else {
                Log.i("CameraConfiguration", "Setting exposure compensation to " + compensationSteps2 + " / " + actualCompensation);
                parameters.setExposureCompensation(compensationSteps2);
                return;
            }
        }
        Log.i("CameraConfiguration", "Camera does not support exposure compensation");
    }

    public static void setBestPreviewFPS(Camera.Parameters parameters) {
        setBestPreviewFPS(parameters, 10, 20);
    }

    public static void setBestPreviewFPS(Camera.Parameters parameters, int minFPS, int maxFPS) {
        List<int[]> supportedPreviewFpsRanges = parameters.getSupportedPreviewFpsRange();
        Log.i("CameraConfiguration", "Supported FPS ranges: " + toString((Collection<int[]>) supportedPreviewFpsRanges));
        if (supportedPreviewFpsRanges != null && !supportedPreviewFpsRanges.isEmpty()) {
            int[] suitableFPSRange = null;
            for (int[] fpsRange : supportedPreviewFpsRanges) {
                int thisMin = fpsRange[0];
                int thisMax = fpsRange[1];
                if (thisMin >= minFPS * 1000 && thisMax <= maxFPS * 1000) {
                    suitableFPSRange = fpsRange;
                    break;
                }
            }
            if (suitableFPSRange == null) {
                Log.i("CameraConfiguration", "No suitable FPS range?");
                return;
            }
            int[] currentFpsRange = new int[2];
            parameters.getPreviewFpsRange(currentFpsRange);
            if (Arrays.equals(currentFpsRange, suitableFPSRange)) {
                Log.i("CameraConfiguration", "FPS range already set to " + Arrays.toString(suitableFPSRange));
            } else {
                Log.i("CameraConfiguration", "Setting FPS range to " + Arrays.toString(suitableFPSRange));
                parameters.setPreviewFpsRange(suitableFPSRange[0], suitableFPSRange[1]);
            }
        }
    }

    @TargetApi(15)
    public static void setFocusArea(Camera.Parameters parameters) {
        if (parameters.getMaxNumFocusAreas() > 0) {
            Log.i("CameraConfiguration", "Old focus areas: " + toString((Iterable<Camera.Area>) parameters.getFocusAreas()));
            List<Camera.Area> middleArea = buildMiddleArea(HttpStatus.SC_BAD_REQUEST);
            Log.i("CameraConfiguration", "Setting focus area to : " + toString((Iterable<Camera.Area>) middleArea));
            parameters.setFocusAreas(middleArea);
            return;
        }
        Log.i("CameraConfiguration", "Device does not support focus areas");
    }

    @TargetApi(15)
    public static void setMetering(Camera.Parameters parameters) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            Log.i("CameraConfiguration", "Old metering areas: " + parameters.getMeteringAreas());
            List<Camera.Area> middleArea = buildMiddleArea(HttpStatus.SC_BAD_REQUEST);
            Log.i("CameraConfiguration", "Setting metering area to : " + toString((Iterable<Camera.Area>) middleArea));
            parameters.setMeteringAreas(middleArea);
            return;
        }
        Log.i("CameraConfiguration", "Device does not support metering areas");
    }

    @TargetApi(15)
    private static List<Camera.Area> buildMiddleArea(int areaPer1000) {
        return Collections.singletonList(new Camera.Area(new Rect(-areaPer1000, -areaPer1000, areaPer1000, areaPer1000), 1));
    }

    @TargetApi(15)
    public static void setVideoStabilization(Camera.Parameters parameters) {
        if (parameters.isVideoStabilizationSupported()) {
            if (parameters.getVideoStabilization()) {
                Log.i("CameraConfiguration", "Video stabilization already enabled");
                return;
            } else {
                Log.i("CameraConfiguration", "Enabling video stabilization...");
                parameters.setVideoStabilization(true);
                return;
            }
        }
        Log.i("CameraConfiguration", "This device does not support video stabilization");
    }

    public static void setBarcodeSceneMode(Camera.Parameters parameters) {
        if ("barcode".equals(parameters.getSceneMode())) {
            Log.i("CameraConfiguration", "Barcode scene mode already set");
            return;
        }
        String sceneMode = findSettableValue("scene mode", parameters.getSupportedSceneModes(), "barcode");
        if (sceneMode != null) {
            parameters.setSceneMode(sceneMode);
        }
    }

    public static void setInvertColor(Camera.Parameters parameters) {
        if ("negative".equals(parameters.getColorEffect())) {
            Log.i("CameraConfiguration", "Negative effect already set");
            return;
        }
        String colorMode = findSettableValue("color effect", parameters.getSupportedColorEffects(), "negative");
        if (colorMode != null) {
            parameters.setColorEffect(colorMode);
        }
    }

    private static String findSettableValue(String name, Collection<String> supportedValues, String... desiredValues) {
        Log.i("CameraConfiguration", "Requesting " + name + " value from among: " + Arrays.toString(desiredValues));
        Log.i("CameraConfiguration", "Supported " + name + " values: " + supportedValues);
        if (supportedValues != null) {
            for (String desiredValue : desiredValues) {
                if (supportedValues.contains(desiredValue)) {
                    Log.i("CameraConfiguration", "Can set " + name + " to: " + desiredValue);
                    return desiredValue;
                }
            }
        }
        Log.i("CameraConfiguration", "No supported values match");
        return null;
    }

    private static String toString(Collection<int[]> arrays) {
        if (arrays == null || arrays.isEmpty()) {
            return "[]";
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        Iterator<int[]> it = arrays.iterator();
        while (it.hasNext()) {
            buffer.append(Arrays.toString(it.next()));
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append(']');
        return buffer.toString();
    }

    @TargetApi(15)
    private static String toString(Iterable<Camera.Area> areas) {
        if (areas == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (Camera.Area area : areas) {
            result.append(area.rect).append(':').append(area.weight).append(' ');
        }
        return result.toString();
    }
}
