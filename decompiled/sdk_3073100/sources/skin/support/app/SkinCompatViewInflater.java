package skin.support.app;

import android.R;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import skin.support.SkinCompatManager;
import skin.support.widget.SkinCompatAutoCompleteTextView;
import skin.support.widget.SkinCompatButton;
import skin.support.widget.SkinCompatCheckBox;
import skin.support.widget.SkinCompatCheckedTextView;
import skin.support.widget.SkinCompatEditText;
import skin.support.widget.SkinCompatFrameLayout;
import skin.support.widget.SkinCompatImageButton;
import skin.support.widget.SkinCompatImageView;
import skin.support.widget.SkinCompatLinearLayout;
import skin.support.widget.SkinCompatMultiAutoCompleteTextView;
import skin.support.widget.SkinCompatProgressBar;
import skin.support.widget.SkinCompatRadioButton;
import skin.support.widget.SkinCompatRadioGroup;
import skin.support.widget.SkinCompatRatingBar;
import skin.support.widget.SkinCompatRelativeLayout;
import skin.support.widget.SkinCompatScrollView;
import skin.support.widget.SkinCompatSeekBar;
import skin.support.widget.SkinCompatSpinner;
import skin.support.widget.SkinCompatTextView;
import skin.support.widget.SkinCompatToolbar;
import skin.support.widget.SkinCompatView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatViewInflater {
    private final Object[] mConstructorArgs = new Object[2];
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = {R.attr.onClick};
    private static final String[] sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();

    public final View createView(View parent, String name, Context context, AttributeSet attrs, boolean inheritContext, boolean readAndroidTheme, boolean readAppTheme, boolean wrapContext) {
        if (inheritContext && parent != null) {
            context = parent.getContext();
        }
        if (readAndroidTheme || readAppTheme) {
            context = themifyContext(context, attrs, readAndroidTheme, readAppTheme);
        }
        if (wrapContext) {
            context = TintContextWrapper.wrap(context);
        }
        View view = createViewFromHackInflater(context, name, attrs);
        if (view == null) {
            view = createViewFromFV(context, name, attrs);
        }
        if (view == null) {
            view = createViewFromV7(context, name, attrs);
        }
        if (view == null) {
            view = createViewFromInflater(context, name, attrs);
        }
        if (view == null) {
            view = createViewFromTag(context, name, attrs);
        }
        if (view != null) {
            checkOnClickListener(view, attrs);
        }
        return view;
    }

    private View createViewFromHackInflater(Context context, String name, AttributeSet attrs) {
        View view = null;
        for (SkinLayoutInflater inflater : SkinCompatManager.getInstance().getHookInflaters()) {
            view = inflater.createView(context, name, attrs);
            if (view != null) {
                break;
            }
        }
        return view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v24, types: [android.view.View] */
    private View createViewFromFV(Context context, String name, AttributeSet attrs) {
        Object skinCompatScrollView = null;
        if (name.contains(".")) {
            return null;
        }
        switch (name) {
            case "View":
                skinCompatScrollView = new SkinCompatView(context, attrs);
                break;
            case "LinearLayout":
                skinCompatScrollView = new SkinCompatLinearLayout(context, attrs);
                break;
            case "RelativeLayout":
                skinCompatScrollView = new SkinCompatRelativeLayout(context, attrs);
                break;
            case "FrameLayout":
                skinCompatScrollView = new SkinCompatFrameLayout(context, attrs);
                break;
            case "TextView":
                skinCompatScrollView = new SkinCompatTextView(context, attrs);
                break;
            case "ImageView":
                skinCompatScrollView = new SkinCompatImageView(context, attrs);
                break;
            case "Button":
                skinCompatScrollView = new SkinCompatButton(context, attrs);
                break;
            case "EditText":
                skinCompatScrollView = new SkinCompatEditText(context, attrs);
                break;
            case "Spinner":
                skinCompatScrollView = new SkinCompatSpinner(context, attrs);
                break;
            case "ImageButton":
                skinCompatScrollView = new SkinCompatImageButton(context, attrs);
                break;
            case "CheckBox":
                skinCompatScrollView = new SkinCompatCheckBox(context, attrs);
                break;
            case "RadioButton":
                skinCompatScrollView = new SkinCompatRadioButton(context, attrs);
                break;
            case "RadioGroup":
                skinCompatScrollView = new SkinCompatRadioGroup(context, attrs);
                break;
            case "CheckedTextView":
                skinCompatScrollView = new SkinCompatCheckedTextView(context, attrs);
                break;
            case "AutoCompleteTextView":
                skinCompatScrollView = new SkinCompatAutoCompleteTextView(context, attrs);
                break;
            case "MultiAutoCompleteTextView":
                skinCompatScrollView = new SkinCompatMultiAutoCompleteTextView(context, attrs);
                break;
            case "RatingBar":
                skinCompatScrollView = new SkinCompatRatingBar(context, attrs);
                break;
            case "SeekBar":
                skinCompatScrollView = new SkinCompatSeekBar(context, attrs);
                break;
            case "ProgressBar":
                skinCompatScrollView = new SkinCompatProgressBar(context, attrs);
                break;
            case "ScrollView":
                skinCompatScrollView = new SkinCompatScrollView(context, attrs);
                break;
        }
        return skinCompatScrollView;
    }

    private View createViewFromV7(Context context, String name, AttributeSet attrs) {
        switch (name) {
            case "android.support.v7.widget.Toolbar":
                return new SkinCompatToolbar(context, attrs);
            default:
                return null;
        }
    }

    private View createViewFromInflater(Context context, String name, AttributeSet attrs) {
        View view = null;
        for (SkinLayoutInflater inflater : SkinCompatManager.getInstance().getInflaters()) {
            view = inflater.createView(context, name, attrs);
            if (view != null) {
                break;
            }
        }
        return view;
    }

    public View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attrs;
            if (-1 != name.indexOf(46)) {
                return createView(context, name, null);
            }
            for (int i = 0; i < sClassPrefixList.length; i++) {
                View view = createView(context, name, sClassPrefixList[i]);
                if (view != null) {
                    return view;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
        }
    }

    private void checkOnClickListener(View view, AttributeSet attrs) {
        Context context = view.getContext();
        if (context instanceof ContextWrapper) {
            if (Build.VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners(view)) {
                TypedArray a = context.obtainStyledAttributes(attrs, sOnClickAttrs);
                String handlerName = a.getString(0);
                if (handlerName != null) {
                    view.setOnClickListener(new DeclaredOnClickListener(view, handlerName));
                }
                a.recycle();
            }
        }
    }

    private View createView(Context context, String name, String prefix) throws InflateException, ClassNotFoundException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        if (constructor == null) {
            try {
                constructor = context.getClassLoader().loadClass(prefix != null ? prefix + name : name).asSubclass(View.class).getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            } catch (Exception e) {
                return null;
            }
        }
        constructor.setAccessible(true);
        return constructor.newInstance(this.mConstructorArgs);
    }

    private static Context themifyContext(Context context, AttributeSet attrs, boolean useAndroidTheme, boolean useAppTheme) {
        TypedArray a = context.obtainStyledAttributes(attrs, android.support.v7.appcompat.R.styleable.View, 0, 0);
        int themeId = 0;
        if (useAndroidTheme) {
            themeId = a.getResourceId(android.support.v7.appcompat.R.styleable.View_android_theme, 0);
        }
        if (useAppTheme && themeId == 0 && (themeId = a.getResourceId(android.support.v7.appcompat.R.styleable.View_theme, 0)) != 0) {
            Log.i("SkinCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        a.recycle();
        if (themeId != 0) {
            if (!(context instanceof ContextThemeWrapper) || ((ContextThemeWrapper) context).getThemeResId() != themeId) {
                return new ContextThemeWrapper(context, themeId);
            }
            return context;
        }
        return context;
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View hostView, String methodName) {
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, v);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        private void resolveMethod(Context context, String name) {
            Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, View.class)) != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException e) {
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    context = null;
                }
            }
            int id = this.mHostView.getId();
            String idText = id == -1 ? "" : " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + idText);
        }
    }
}
