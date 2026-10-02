package com.facebook.react.uimanager;

import com.facebook.react.common.MapBuilder;
import com.facebook.systrace.Systrace;
import com.facebook.systrace.SystraceMessage;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class UIManagerModuleConstantsHelper {
    static Map<String, Object> createConstants(List<ViewManager> viewManagers, boolean lazyViewManagersEnabled) {
        Map<String, Object> constants = UIManagerModuleConstants.getConstants();
        Map bubblingEventTypesConstants = UIManagerModuleConstants.getBubblingEventTypeConstants();
        Map directEventTypesConstants = UIManagerModuleConstants.getDirectEventTypeConstants();
        for (ViewManager viewManager : viewManagers) {
            SystraceMessage.beginSection(0L, "constants for ViewManager").arg("ViewManager", viewManager.getName()).flush();
            try {
                Map<String, Object> exportedCustomBubblingEventTypeConstants = viewManager.getExportedCustomBubblingEventTypeConstants();
                if (exportedCustomBubblingEventTypeConstants != null) {
                    recursiveMerge(bubblingEventTypesConstants, exportedCustomBubblingEventTypeConstants);
                }
                Map<String, Object> exportedCustomDirectEventTypeConstants = viewManager.getExportedCustomDirectEventTypeConstants();
                if (exportedCustomDirectEventTypeConstants != null) {
                    recursiveMerge(directEventTypesConstants, exportedCustomDirectEventTypeConstants);
                }
                Map viewManagerConstants = MapBuilder.newHashMap();
                Map<String, Object> exportedViewConstants = viewManager.getExportedViewConstants();
                if (exportedViewConstants != null) {
                    viewManagerConstants.put("Constants", exportedViewConstants);
                }
                Map<String, Integer> commandsMap = viewManager.getCommandsMap();
                if (commandsMap != null) {
                    viewManagerConstants.put("Commands", commandsMap);
                }
                Map<String, String> viewManagerNativeProps = viewManager.getNativeProps();
                if (!viewManagerNativeProps.isEmpty()) {
                    viewManagerConstants.put("NativeProps", viewManagerNativeProps);
                }
                if (!viewManagerConstants.isEmpty()) {
                    constants.put(viewManager.getName(), viewManagerConstants);
                }
                Systrace.endSection(0L);
            } catch (Throwable th) {
                Systrace.endSection(0L);
                throw th;
            }
        }
        constants.put("customBubblingEventTypes", bubblingEventTypesConstants);
        constants.put("customDirectEventTypes", directEventTypesConstants);
        constants.put("AndroidLazyViewManagersEnabled", Boolean.valueOf(lazyViewManagersEnabled));
        return constants;
    }

    private static void recursiveMerge(Map dest, Map source) {
        for (Object key : source.keySet()) {
            Object sourceValue = source.get(key);
            Object destValue = dest.get(key);
            if (destValue != null && (sourceValue instanceof Map) && (destValue instanceof Map)) {
                recursiveMerge((Map) destValue, (Map) sourceValue);
            } else {
                dest.put(key, sourceValue);
            }
        }
    }
}
