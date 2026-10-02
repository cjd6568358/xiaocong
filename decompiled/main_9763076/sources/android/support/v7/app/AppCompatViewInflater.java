package android.support.v7.app;

import android.R;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class AppCompatViewInflater {
    private final Object[] mConstructorArgs = new Object[2];
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = {R.attr.onClick};
    private static final String[] sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();

    AppCompatViewInflater() {
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
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
        View view = null;
        byte b = -1;
        switch (name.hashCode()) {
            case -1946472170:
                if (name.equals("RatingBar")) {
                    b = JceStruct.STRUCT_END;
                }
                break;
            case -1455429095:
                if (name.equals("CheckedTextView")) {
                    b = 8;
                }
                break;
            case -1346021293:
                if (name.equals("MultiAutoCompleteTextView")) {
                    b = 10;
                }
                break;
            case -938935918:
                if (name.equals("TextView")) {
                    b = 0;
                }
                break;
            case -937446323:
                if (name.equals("ImageButton")) {
                    b = 5;
                }
                break;
            case -658531749:
                if (name.equals("SeekBar")) {
                    b = JceStruct.ZERO_TAG;
                }
                break;
            case -339785223:
                if (name.equals("Spinner")) {
                    b = 4;
                }
                break;
            case 776382189:
                if (name.equals("RadioButton")) {
                    b = 7;
                }
                break;
            case 1125864064:
                if (name.equals("ImageView")) {
                    b = 1;
                }
                break;
            case 1413872058:
                if (name.equals("AutoCompleteTextView")) {
                    b = 9;
                }
                break;
            case 1601505219:
                if (name.equals("CheckBox")) {
                    b = 6;
                }
                break;
            case 1666676343:
                if (name.equals("EditText")) {
                    b = 3;
                }
                break;
            case 2001146706:
                if (name.equals("Button")) {
                    b = 2;
                }
                break;
        }
        switch (b) {
            case 0:
                view = new AppCompatTextView(context, attrs);
                break;
            case 1:
                view = new AppCompatImageView(context, attrs);
                break;
            case 2:
                view = new AppCompatButton(context, attrs);
                break;
            case 3:
                view = new AppCompatEditText(context, attrs);
                break;
            case 4:
                view = new AppCompatSpinner(context, attrs);
                break;
            case 5:
                view = new AppCompatImageButton(context, attrs);
                break;
            case 6:
                view = new AppCompatCheckBox(context, attrs);
                break;
            case 7:
                view = new AppCompatRadioButton(context, attrs);
                break;
            case 8:
                view = new AppCompatCheckedTextView(context, attrs);
                break;
            case 9:
                view = new AppCompatAutoCompleteTextView(context, attrs);
                break;
            case 10:
                view = new AppCompatMultiAutoCompleteTextView(context, attrs);
                break;
            case 11:
                view = new AppCompatRatingBar(context, attrs);
                break;
            case 12:
                view = new AppCompatSeekBar(context, attrs);
                break;
        }
        if (view == null && context != context) {
            view = createViewFromTag(context, name, attrs);
        }
        if (view != null) {
            checkOnClickListener(view, attrs);
        }
        return view;
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
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
            Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
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
            String idText = id == -1 ? Constants.MAIN_VERSION_TAG : " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + idText);
        }
    }
}
