package com.facebook.react.uimanager;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.RadioButton;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class AccessibilityHelper {
    private static final View.AccessibilityDelegate BUTTON_DELEGATE = new View.AccessibilityDelegate() { // from class: com.facebook.react.uimanager.AccessibilityHelper.1
        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(Button.class.getName());
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setClassName(Button.class.getName());
        }
    };
    private static final View.AccessibilityDelegate RADIOBUTTON_CHECKED_DELEGATE = new View.AccessibilityDelegate() { // from class: com.facebook.react.uimanager.AccessibilityHelper.2
        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(RadioButton.class.getName());
            event.setChecked(true);
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setClassName(RadioButton.class.getName());
            info.setCheckable(true);
            info.setChecked(true);
        }
    };
    private static final View.AccessibilityDelegate RADIOBUTTON_UNCHECKED_DELEGATE = new View.AccessibilityDelegate() { // from class: com.facebook.react.uimanager.AccessibilityHelper.3
        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(RadioButton.class.getName());
            event.setChecked(false);
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setClassName(RadioButton.class.getName());
            info.setCheckable(true);
            info.setChecked(false);
        }
    };

    public static void updateAccessibilityComponentType(View view, String componentType) {
        if (componentType == null) {
            view.setAccessibilityDelegate(null);
        }
        switch (componentType) {
            case "button":
                view.setAccessibilityDelegate(BUTTON_DELEGATE);
                break;
            case "radiobutton_checked":
                view.setAccessibilityDelegate(RADIOBUTTON_CHECKED_DELEGATE);
                break;
            case "radiobutton_unchecked":
                view.setAccessibilityDelegate(RADIOBUTTON_UNCHECKED_DELEGATE);
                break;
            default:
                view.setAccessibilityDelegate(null);
                break;
        }
    }

    public static void sendAccessibilityEvent(View view, int eventType) {
        view.sendAccessibilityEvent(eventType);
    }
}
