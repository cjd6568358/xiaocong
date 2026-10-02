package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class AppCompatDelegate {
    private static int sDefaultNightMode = -1;
    private static boolean sCompatVectorFromResourcesEnabled = false;

    public abstract void addContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract boolean applyDayNight();

    public abstract <T extends View> T findViewById(int i);

    public abstract MenuInflater getMenuInflater();

    public abstract ActionBar getSupportActionBar();

    public abstract void installViewFactory();

    public abstract void invalidateOptionsMenu();

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onCreate(Bundle bundle);

    public abstract void onDestroy();

    public abstract void onPostCreate(Bundle bundle);

    public abstract void onPostResume();

    public abstract void onSaveInstanceState(Bundle bundle);

    public abstract void onStart();

    public abstract void onStop();

    public abstract boolean requestWindowFeature(int i);

    public abstract void setContentView(int i);

    public abstract void setContentView(View view);

    public abstract void setContentView(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void setTitle(CharSequence charSequence);

    public static AppCompatDelegate create(Activity activity, AppCompatCallback callback) {
        return create(activity, activity.getWindow(), callback);
    }

    public static AppCompatDelegate create(Dialog dialog, AppCompatCallback callback) {
        return create(dialog.getContext(), dialog.getWindow(), callback);
    }

    private static AppCompatDelegate create(Context context, Window window, AppCompatCallback callback) {
        if (Build.VERSION.SDK_INT >= 24) {
            return new AppCompatDelegateImplN(context, window, callback);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return new AppCompatDelegateImplV23(context, window, callback);
        }
        if (Build.VERSION.SDK_INT >= 14) {
            return new AppCompatDelegateImplV14(context, window, callback);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            return new AppCompatDelegateImplV11(context, window, callback);
        }
        return new AppCompatDelegateImplV9(context, window, callback);
    }

    AppCompatDelegate() {
    }

    public static int getDefaultNightMode() {
        return sDefaultNightMode;
    }

    public static boolean isCompatVectorFromResourcesEnabled() {
        return sCompatVectorFromResourcesEnabled;
    }
}
