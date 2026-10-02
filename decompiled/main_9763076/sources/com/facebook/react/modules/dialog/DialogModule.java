package com.facebook.react.modules.dialog;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DialogModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    static final String FRAGMENT_TAG = "com.facebook.catalyst.react.dialog.DialogModule";
    static final String KEY_CANCELABLE = "cancelable";
    static final String KEY_ITEMS = "items";
    static final String KEY_MESSAGE = "message";
    static final String KEY_TITLE = "title";
    static final String NAME = "DialogManagerAndroid";
    private boolean mIsInForeground;
    static final String ACTION_BUTTON_CLICKED = "buttonClicked";
    static final String ACTION_DISMISSED = "dismissed";
    static final String KEY_BUTTON_POSITIVE = "buttonPositive";
    static final String KEY_BUTTON_NEGATIVE = "buttonNegative";
    static final String KEY_BUTTON_NEUTRAL = "buttonNeutral";
    static final Map<String, Object> CONSTANTS = MapBuilder.of(ACTION_BUTTON_CLICKED, ACTION_BUTTON_CLICKED, ACTION_DISMISSED, ACTION_DISMISSED, KEY_BUTTON_POSITIVE, -1, KEY_BUTTON_NEGATIVE, -2, KEY_BUTTON_NEUTRAL, -3);

    public DialogModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    private class FragmentManagerHelper {
        private final FragmentManager mFragmentManager;
        private Object mFragmentToShow;
        private final android.support.v4.app.FragmentManager mSupportFragmentManager;

        private boolean isUsingSupportLibrary() {
            return this.mSupportFragmentManager != null;
        }

        public FragmentManagerHelper(android.support.v4.app.FragmentManager supportFragmentManager) {
            this.mFragmentManager = null;
            this.mSupportFragmentManager = supportFragmentManager;
        }

        public FragmentManagerHelper(FragmentManager fragmentManager) {
            this.mFragmentManager = fragmentManager;
            this.mSupportFragmentManager = null;
        }

        public void showPendingAlert() {
            if (this.mFragmentToShow != null) {
                if (isUsingSupportLibrary()) {
                    ((SupportAlertFragment) this.mFragmentToShow).show(this.mSupportFragmentManager, DialogModule.FRAGMENT_TAG);
                } else {
                    ((AlertFragment) this.mFragmentToShow).show(this.mFragmentManager, DialogModule.FRAGMENT_TAG);
                }
                this.mFragmentToShow = null;
            }
        }

        private void dismissExisting() {
            if (isUsingSupportLibrary()) {
                SupportAlertFragment oldFragment = (SupportAlertFragment) this.mSupportFragmentManager.findFragmentByTag(DialogModule.FRAGMENT_TAG);
                if (oldFragment != null) {
                    oldFragment.dismiss();
                    return;
                }
                return;
            }
            AlertFragment oldFragment2 = (AlertFragment) this.mFragmentManager.findFragmentByTag(DialogModule.FRAGMENT_TAG);
            if (oldFragment2 != null) {
                oldFragment2.dismiss();
            }
        }

        public void showNewAlert(boolean isInForeground, Bundle arguments, Callback actionCallback) {
            dismissExisting();
            AlertFragmentListener actionListener = actionCallback != null ? DialogModule.this.new AlertFragmentListener(actionCallback) : null;
            if (isUsingSupportLibrary()) {
                SupportAlertFragment alertFragment = new SupportAlertFragment(actionListener, arguments);
                if (isInForeground) {
                    if (arguments.containsKey(DialogModule.KEY_CANCELABLE)) {
                        alertFragment.setCancelable(arguments.getBoolean(DialogModule.KEY_CANCELABLE));
                    }
                    alertFragment.show(this.mSupportFragmentManager, DialogModule.FRAGMENT_TAG);
                    return;
                }
                this.mFragmentToShow = alertFragment;
                return;
            }
            AlertFragment alertFragment2 = new AlertFragment(actionListener, arguments);
            if (isInForeground) {
                if (arguments.containsKey(DialogModule.KEY_CANCELABLE)) {
                    alertFragment2.setCancelable(arguments.getBoolean(DialogModule.KEY_CANCELABLE));
                }
                alertFragment2.show(this.mFragmentManager, DialogModule.FRAGMENT_TAG);
                return;
            }
            this.mFragmentToShow = alertFragment2;
        }
    }

    class AlertFragmentListener implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
        private final Callback mCallback;
        private boolean mCallbackConsumed = false;

        public AlertFragmentListener(Callback callback) {
            this.mCallback = callback;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            if (!this.mCallbackConsumed && DialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                this.mCallback.invoke(DialogModule.ACTION_BUTTON_CLICKED, Integer.valueOf(which));
                this.mCallbackConsumed = true;
            }
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialog) {
            if (!this.mCallbackConsumed && DialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                this.mCallback.invoke(DialogModule.ACTION_DISMISSED);
                this.mCallbackConsumed = true;
            }
        }
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        return CONSTANTS;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        getReactApplicationContext().addLifecycleEventListener(this);
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        this.mIsInForeground = false;
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        this.mIsInForeground = true;
        FragmentManagerHelper fragmentManagerHelper = getFragmentManagerHelper();
        if (fragmentManagerHelper != null) {
            fragmentManagerHelper.showPendingAlert();
        } else {
            FLog.w((Class<?>) DialogModule.class, "onHostResume called but no FragmentManager found");
        }
    }

    @ReactMethod
    public void showAlert(ReadableMap options, Callback errorCallback, Callback actionCallback) {
        FragmentManagerHelper fragmentManagerHelper = getFragmentManagerHelper();
        if (fragmentManagerHelper == null) {
            errorCallback.invoke("Tried to show an alert while not attached to an Activity");
            return;
        }
        Bundle args = new Bundle();
        if (options.hasKey("title")) {
            args.putString("title", options.getString("title"));
        }
        if (options.hasKey("message")) {
            args.putString("message", options.getString("message"));
        }
        if (options.hasKey(KEY_BUTTON_POSITIVE)) {
            args.putString("button_positive", options.getString(KEY_BUTTON_POSITIVE));
        }
        if (options.hasKey(KEY_BUTTON_NEGATIVE)) {
            args.putString("button_negative", options.getString(KEY_BUTTON_NEGATIVE));
        }
        if (options.hasKey(KEY_BUTTON_NEUTRAL)) {
            args.putString("button_neutral", options.getString(KEY_BUTTON_NEUTRAL));
        }
        if (options.hasKey(KEY_ITEMS)) {
            ReadableArray items = options.getArray(KEY_ITEMS);
            CharSequence[] itemsArray = new CharSequence[items.size()];
            for (int i = 0; i < items.size(); i++) {
                itemsArray[i] = items.getString(i);
            }
            args.putCharSequenceArray(KEY_ITEMS, itemsArray);
        }
        if (options.hasKey(KEY_CANCELABLE)) {
            args.putBoolean(KEY_CANCELABLE, options.getBoolean(KEY_CANCELABLE));
        }
        fragmentManagerHelper.showNewAlert(this.mIsInForeground, args, actionCallback);
    }

    private FragmentManagerHelper getFragmentManagerHelper() {
        Activity activity = getCurrentActivity();
        if (activity == null) {
            return null;
        }
        if (activity instanceof FragmentActivity) {
            return new FragmentManagerHelper(((FragmentActivity) activity).getSupportFragmentManager());
        }
        return new FragmentManagerHelper(activity.getFragmentManager());
    }
}
