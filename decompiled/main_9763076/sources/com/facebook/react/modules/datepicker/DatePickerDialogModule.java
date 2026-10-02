package com.facebook.react.modules.datepicker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DatePickerDialogModule extends ReactContextBaseJavaModule {
    static final String ACTION_DATE_SET = "dateSetAction";
    static final String ACTION_DISMISSED = "dismissedAction";
    static final String ARG_DATE = "date";
    static final String ARG_MAXDATE = "maxDate";
    static final String ARG_MINDATE = "minDate";
    static final String ARG_MODE = "mode";
    private static final String ERROR_NO_ACTIVITY = "E_NO_ACTIVITY";
    public static final String FRAGMENT_TAG = "DatePickerAndroid";

    public DatePickerDialogModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return FRAGMENT_TAG;
    }

    private class DatePickerDialogListener implements DatePickerDialog.OnDateSetListener, DialogInterface.OnDismissListener {
        private final Promise mPromise;
        private boolean mPromiseResolved = false;

        public DatePickerDialogListener(Promise promise) {
            this.mPromise = promise;
        }

        @Override // android.app.DatePickerDialog.OnDateSetListener
        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (!this.mPromiseResolved && DatePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                WritableMap result = new WritableNativeMap();
                result.putString("action", DatePickerDialogModule.ACTION_DATE_SET);
                result.putInt("year", year);
                result.putInt("month", month);
                result.putInt("day", day);
                this.mPromise.resolve(result);
                this.mPromiseResolved = true;
            }
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialog) {
            if (!this.mPromiseResolved && DatePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
                WritableMap result = new WritableNativeMap();
                result.putString("action", DatePickerDialogModule.ACTION_DISMISSED);
                this.mPromise.resolve(result);
                this.mPromiseResolved = true;
            }
        }
    }

    @ReactMethod
    public void open(ReadableMap options, Promise promise) {
        Activity activity = getCurrentActivity();
        if (activity == null) {
            promise.reject(ERROR_NO_ACTIVITY, "Tried to open a DatePicker dialog while not attached to an Activity");
            return;
        }
        if (activity instanceof FragmentActivity) {
            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            DialogFragment oldFragment = (DialogFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (oldFragment != null) {
                oldFragment.dismiss();
            }
            SupportDatePickerDialogFragment fragment = new SupportDatePickerDialogFragment();
            if (options != null) {
                Bundle args = createFragmentArguments(options);
                fragment.setArguments(args);
            }
            DatePickerDialogListener listener = new DatePickerDialogListener(promise);
            fragment.setOnDismissListener(listener);
            fragment.setOnDateSetListener(listener);
            fragment.show(fragmentManager, FRAGMENT_TAG);
            return;
        }
        android.app.FragmentManager fragmentManager2 = activity.getFragmentManager();
        android.app.DialogFragment oldFragment2 = (android.app.DialogFragment) fragmentManager2.findFragmentByTag(FRAGMENT_TAG);
        if (oldFragment2 != null) {
            oldFragment2.dismiss();
        }
        DatePickerDialogFragment fragment2 = new DatePickerDialogFragment();
        if (options != null) {
            Bundle args2 = createFragmentArguments(options);
            fragment2.setArguments(args2);
        }
        DatePickerDialogListener listener2 = new DatePickerDialogListener(promise);
        fragment2.setOnDismissListener(listener2);
        fragment2.setOnDateSetListener(listener2);
        fragment2.show(fragmentManager2, FRAGMENT_TAG);
    }

    private Bundle createFragmentArguments(ReadableMap options) {
        Bundle args = new Bundle();
        if (options.hasKey("date") && !options.isNull("date")) {
            args.putLong("date", (long) options.getDouble("date"));
        }
        if (options.hasKey(ARG_MINDATE) && !options.isNull(ARG_MINDATE)) {
            args.putLong(ARG_MINDATE, (long) options.getDouble(ARG_MINDATE));
        }
        if (options.hasKey(ARG_MAXDATE) && !options.isNull(ARG_MAXDATE)) {
            args.putLong(ARG_MAXDATE, (long) options.getDouble(ARG_MAXDATE));
        }
        if (options.hasKey(ARG_MODE) && !options.isNull(ARG_MODE)) {
            args.putString(ARG_MODE, options.getString(ARG_MODE));
        }
        return args;
    }
}
