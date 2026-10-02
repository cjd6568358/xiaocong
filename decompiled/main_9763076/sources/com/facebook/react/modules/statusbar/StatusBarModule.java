package com.facebook.react.modules.statusbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.WindowInsets;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StatusBarModule extends ReactContextBaseJavaModule {
    private static final String HEIGHT_KEY = "HEIGHT";

    public StatusBarModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "StatusBarManager";
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        Context context = getReactApplicationContext();
        int heightResId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        float height = heightResId > 0 ? PixelUtil.toDIPFromPixel(context.getResources().getDimensionPixelSize(heightResId)) : 0.0f;
        return MapBuilder.of(HEIGHT_KEY, Float.valueOf(height));
    }

    @ReactMethod
    public void setColor(final int color, final boolean animated) {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            FLog.w("React", "StatusBarModule: Ignored status bar change, current activity is null.");
        } else if (Build.VERSION.SDK_INT >= 21) {
            UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.statusbar.StatusBarModule.1
                @Override // java.lang.Runnable
                @TargetApi(21)
                public void run() {
                    if (animated) {
                        int curColor = activity.getWindow().getStatusBarColor();
                        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(curColor), Integer.valueOf(color));
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.facebook.react.modules.statusbar.StatusBarModule.1.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public void onAnimationUpdate(ValueAnimator animator) {
                                activity.getWindow().setStatusBarColor(((Integer) animator.getAnimatedValue()).intValue());
                            }
                        });
                        colorAnimation.setDuration(300L).setStartDelay(0L);
                        colorAnimation.start();
                        return;
                    }
                    activity.getWindow().setStatusBarColor(color);
                }
            });
        }
    }

    @ReactMethod
    public void setTranslucent(final boolean translucent) {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            FLog.w("React", "StatusBarModule: Ignored status bar change, current activity is null.");
        } else if (Build.VERSION.SDK_INT >= 21) {
            UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.statusbar.StatusBarModule.2
                @Override // java.lang.Runnable
                @TargetApi(21)
                public void run() {
                    View decorView = activity.getWindow().getDecorView();
                    if (translucent) {
                        decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.facebook.react.modules.statusbar.StatusBarModule.2.1
                            @Override // android.view.View.OnApplyWindowInsetsListener
                            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                                return defaultInsets.replaceSystemWindowInsets(defaultInsets.getSystemWindowInsetLeft(), 0, defaultInsets.getSystemWindowInsetRight(), defaultInsets.getSystemWindowInsetBottom());
                            }
                        });
                    } else {
                        decorView.setOnApplyWindowInsetsListener(null);
                    }
                    ViewCompat.requestApplyInsets(decorView);
                }
            });
        }
    }

    @ReactMethod
    public void setHidden(final boolean hidden) {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            FLog.w("React", "StatusBarModule: Ignored status bar change, current activity is null.");
        } else {
            UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.statusbar.StatusBarModule.3
                @Override // java.lang.Runnable
                public void run() {
                    if (hidden) {
                        activity.getWindow().addFlags(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
                        activity.getWindow().clearFlags(2048);
                    } else {
                        activity.getWindow().addFlags(2048);
                        activity.getWindow().clearFlags(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
                    }
                }
            });
        }
    }

    @ReactMethod
    public void setStyle(final String style) {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            FLog.w("React", "StatusBarModule: Ignored status bar change, current activity is null.");
        } else if (Build.VERSION.SDK_INT >= 23) {
            UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.statusbar.StatusBarModule.4
                @Override // java.lang.Runnable
                @TargetApi(23)
                public void run() {
                    View decorView = activity.getWindow().getDecorView();
                    decorView.setSystemUiVisibility(style.equals("dark-content") ? 8192 : 0);
                }
            });
        }
    }
}
