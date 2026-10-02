package com.facebook.react.modules.timepicker;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TimePicker;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TimePickerDialogModule extends ReactContextBaseJavaModule {
    static final String ACTION_DISMISSED = "dismissedAction";
    static final String ACTION_TIME_SET = "timeSetAction";
    static final String ARG_HOUR = "hour";
    static final String ARG_IS24HOUR = "is24Hour";
    static final String ARG_MINUTE = "minute";
    private static final String ERROR_NO_ACTIVITY = "E_NO_ACTIVITY";
    public static final String FRAGMENT_TAG = "TimePickerAndroid";

    public TimePickerDialogModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return FRAGMENT_TAG;
    }

    private class TimePickerDialogListener implements TimePickerDialog.OnTimeSetListener, DialogInterface.OnDismissListener {
        private final Promise mPromise;
        private boolean mPromiseResolved = false;

        public TimePickerDialogListener(Promise promise) {
            this.mPromise = promise;
        }

        @Override // android.app.TimePickerDialog.OnTimeSetListener
        public void onTimeSet(TimePicker view, int hour, int minute) {
            if (!this.mPromiseResolved && TimePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                WritableMap result = new WritableNativeMap();
                result.putString("action", TimePickerDialogModule.ACTION_TIME_SET);
                result.putInt("hour", hour);
                result.putInt(TimePickerDialogModule.ARG_MINUTE, minute);
                this.mPromise.resolve(result);
                this.mPromiseResolved = true;
            }
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialog) {
            if (!this.mPromiseResolved && TimePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                WritableMap result = new WritableNativeMap();
                result.putString("action", TimePickerDialogModule.ACTION_DISMISSED);
                this.mPromise.resolve(result);
                this.mPromiseResolved = true;
            }
        }
    }

    @ReactMethod
    public void open(ReadableMap options, Promise promise) {
        Activity activity = getCurrentActivity();
        if (activity == null) {
            promise.reject(ERROR_NO_ACTIVITY, "Tried to open a TimePicker dialog while not attached to an Activity");
            return;
        }
        if (activity instanceof FragmentActivity) {
            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            DialogFragment oldFragment = (DialogFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (oldFragment != null) {
                oldFragment.dismiss();
            }
            SupportTimePickerDialogFragment fragment = new SupportTimePickerDialogFragment();
            if (options != null) {
                Bundle args = createFragmentArguments(options);
                fragment.setArguments(args);
            }
            TimePickerDialogListener listener = new TimePickerDialogListener(promise);
            fragment.setOnDismissListener(listener);
            fragment.setOnTimeSetListener(listener);
            fragment.show(fragmentManager, FRAGMENT_TAG);
            return;
        }
        android.app.FragmentManager fragmentManager2 = activity.getFragmentManager();
        android.app.DialogFragment oldFragment2 = (android.app.DialogFragment) fragmentManager2.findFragmentByTag(FRAGMENT_TAG);
        if (oldFragment2 != null) {
            oldFragment2.dismiss();
        }
        TimePickerDialogFragment fragment2 = new TimePickerDialogFragment();
        if (options != null) {
            Bundle args2 = createFragmentArguments(options);
            fragment2.setArguments(args2);
        }
        TimePickerDialogListener listener2 = new TimePickerDialogListener(promise);
        fragment2.setOnDismissListener(listener2);
        fragment2.setOnTimeSetListener(listener2);
        fragment2.show(fragmentManager2, FRAGMENT_TAG);
    }

    private Bundle createFragmentArguments(ReadableMap options) {
        Bundle args = new Bundle();
        if (options.hasKey("hour") && !options.isNull("hour")) {
            args.putInt("hour", options.getInt("hour"));
        }
        if (options.hasKey(ARG_MINUTE) && !options.isNull(ARG_MINUTE)) {
            args.putInt(ARG_MINUTE, options.getInt(ARG_MINUTE));
        }
        if (options.hasKey(ARG_IS24HOUR) && !options.isNull(ARG_IS24HOUR)) {
            args.putBoolean(ARG_IS24HOUR, options.getBoolean(ARG_IS24HOUR));
        }
        return args;
    }
}
