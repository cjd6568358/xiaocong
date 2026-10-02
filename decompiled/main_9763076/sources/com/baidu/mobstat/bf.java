package com.baidu.mobstat;

import android.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class bf {
    private static final bf b = new bf();
    private HashMap<String, Set<String>> a = new HashMap<>();
    private boolean c;
    private boolean d;

    bf() {
    }

    public static bf a() {
        return b;
    }

    public void a(Context context) {
        a(context, false);
    }

    @TargetApi(14)
    private void a(Context context, boolean z) {
        if (!this.d) {
            if (Build.VERSION.SDK_INT < 14) {
                if (z) {
                    db.a("module autotrace only support android os version bigger than 4.0");
                }
            } else {
                b(context);
                this.d = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Activity activity, boolean z) {
        if (!(activity instanceof IIgnoreAutoTrace)) {
            if (z) {
                bv.a().a(activity);
            }
            if (z) {
                ch.a().a((Context) activity, System.currentTimeMillis(), true);
            } else {
                ch.a().a(activity, System.currentTimeMillis(), true, null);
            }
        }
    }

    private void b(Context context) {
        try {
            ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new bg(this));
        } catch (Exception e) {
            db.a("registerActivityLifecycleCallbacks encounter exception");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Activity activity) {
        View decorView;
        ViewGroup viewGroup;
        Window window = activity.getWindow();
        if (window != null && (decorView = window.getDecorView()) != null) {
            try {
                viewGroup = (ViewGroup) ((ViewGroup) decorView.findViewById(R.id.content)).getChildAt(0);
            } catch (Exception e) {
                viewGroup = null;
            }
            if (viewGroup != null) {
                a(activity, viewGroup);
            }
        }
    }

    private void a(Activity activity, ViewGroup viewGroup) {
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt instanceof ViewGroup) {
                a(activity, (ViewGroup) childAt);
            }
            a(activity, childAt);
        }
    }

    private void a(Activity activity, View view) {
        if (view instanceof Button) {
            String string = ((Button) view).getText().toString();
            if (!TextUtils.isEmpty(string)) {
                a(activity, view, string);
            }
        }
    }

    private void a(Activity activity, View view, String str) {
        View.AccessibilityDelegate accessibilityDelegateA = a(view);
        if (!(accessibilityDelegateA instanceof bh)) {
            view.setAccessibilityDelegate(new bh(this, activity, view, str, accessibilityDelegateA));
        }
    }

    private View.AccessibilityDelegate a(View view) {
        try {
            return (View.AccessibilityDelegate) view.getClass().getMethod("getAccessibilityDelegate", new Class[0]).invoke(view, new Object[0]);
        } catch (Exception e) {
            db.b("getAccessibilityDelegate threw an exception when called");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Activity activity, View view, String str) {
        bv.a().a(activity);
        String str2 = view.hashCode() + "_" + view.getId();
        String name = activity.getClass().getName();
        synchronized (this.a) {
            Set<String> set = this.a.get(name);
            if (set == null || !set.contains(str2)) {
                bm.a().a(activity.getApplicationContext(), str, Constants.MAIN_VERSION_TAG, 1, System.currentTimeMillis(), a(view, activity), name, Config.EventViewType.BUTTON.getValue(), true);
            }
        }
    }

    private String a(View view, Activity activity) {
        if (view == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        ViewGroup viewGroup = null;
        try {
            viewGroup = (ViewGroup) ((ViewGroup) activity.getWindow().getDecorView().findViewById(R.id.content)).getChildAt(0);
        } catch (Exception e) {
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(view.getClass().getName());
        while (view != null && view != viewGroup) {
            View view2 = (View) view.getParent();
            arrayList.add(view2.getClass().getName());
            view = view2;
        }
        int size = arrayList.size() - 1;
        String str = Constants.MAIN_VERSION_TAG;
        while (size >= 0) {
            String str2 = str + ((String) arrayList.get(size)) + "/";
            size--;
            str = str2;
        }
        if (str.endsWith("/")) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }
}
