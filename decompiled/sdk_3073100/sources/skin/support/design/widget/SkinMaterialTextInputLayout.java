package skin.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import skin.support.content.res.SkinCompatResources;
import skin.support.design.R;
import skin.support.widget.SkinCompatBackgroundHelper;
import skin.support.widget.SkinCompatEditText;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinMaterialTextInputLayout extends TextInputLayout implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private int mCounterTextColorResId;
    private int mDefaultTextColorResId;
    private int mErrorTextColorResId;
    private int mFocusedTextColorResId;
    private int mPasswordToggleResId;

    public SkinMaterialTextInputLayout(Context context) {
        this(context, null);
    }

    public SkinMaterialTextInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinMaterialTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPasswordToggleResId = 0;
        this.mCounterTextColorResId = 0;
        this.mErrorTextColorResId = 0;
        this.mFocusedTextColorResId = 0;
        this.mDefaultTextColorResId = 0;
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextInputLayout, defStyleAttr, R.style.Widget_Design_TextInputLayout);
        if (a.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            int resourceId = a.getResourceId(R.styleable.TextInputLayout_android_textColorHint, 0);
            this.mFocusedTextColorResId = resourceId;
            this.mDefaultTextColorResId = resourceId;
            applyFocusedTextColorResource();
        }
        int errorTextAppearance = a.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        loadErrorTextColorResFromAttributes(errorTextAppearance);
        int counterTextAppearance = a.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
        loadCounterTextColorResFromAttributes(counterTextAppearance);
        this.mPasswordToggleResId = a.getResourceId(R.styleable.TextInputLayout_passwordToggleDrawable, 0);
        a.recycle();
    }

    private void loadCounterTextColorResFromAttributes(int resId) {
        if (resId != 0) {
            TypedArray counterTA = getContext().obtainStyledAttributes(resId, skin.support.R.styleable.SkinTextAppearance);
            if (counterTA.hasValue(skin.support.R.styleable.SkinTextAppearance_android_textColor)) {
                this.mCounterTextColorResId = counterTA.getResourceId(skin.support.R.styleable.SkinTextAppearance_android_textColor, 0);
            }
            counterTA.recycle();
        }
        applyCounterTextColorResource();
    }

    public void setCounterEnabled(boolean enabled) {
        super.setCounterEnabled(enabled);
        if (enabled) {
            applyCounterTextColorResource();
        }
    }

    private void applyCounterTextColorResource() {
        TextView counterView;
        this.mCounterTextColorResId = SkinCompatHelper.checkResourceId(this.mCounterTextColorResId);
        if (this.mCounterTextColorResId != 0 && (counterView = getCounterView()) != null) {
            counterView.setTextColor(SkinCompatResources.getColor(getContext(), this.mCounterTextColorResId));
            updateEditTextBackground();
        }
    }

    private TextView getCounterView() {
        try {
            Field counterView = TextInputLayout.class.getDeclaredField("mCounterView");
            counterView.setAccessible(true);
            return (TextView) counterView.get(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setErrorTextAppearance(int resId) {
        super.setErrorTextAppearance(resId);
        loadErrorTextColorResFromAttributes(resId);
    }

    private void loadErrorTextColorResFromAttributes(int resId) {
        if (resId != 0) {
            TypedArray errorTA = getContext().obtainStyledAttributes(resId, skin.support.R.styleable.SkinTextAppearance);
            if (errorTA.hasValue(skin.support.R.styleable.SkinTextAppearance_android_textColor)) {
                this.mErrorTextColorResId = errorTA.getResourceId(skin.support.R.styleable.SkinTextAppearance_android_textColor, 0);
            }
            errorTA.recycle();
        }
        applyErrorTextColorResource();
    }

    public void setErrorEnabled(boolean enabled) {
        super.setErrorEnabled(enabled);
        if (enabled) {
            applyErrorTextColorResource();
        }
    }

    private void applyErrorTextColorResource() {
        TextView errorView;
        this.mErrorTextColorResId = SkinCompatHelper.checkResourceId(this.mErrorTextColorResId);
        if (this.mErrorTextColorResId != 0 && this.mErrorTextColorResId != R.color.design_error && (errorView = getErrorView()) != null) {
            errorView.setTextColor(SkinCompatResources.getColor(getContext(), this.mErrorTextColorResId));
            updateEditTextBackground();
        }
    }

    private TextView getErrorView() {
        try {
            Field errorView = TextInputLayout.class.getDeclaredField("mErrorView");
            errorView.setAccessible(true);
            return (TextView) errorView.get(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateEditTextBackground() {
        try {
            Method updateEditTextBackground = TextInputLayout.class.getDeclaredMethod("updateEditTextBackground", new Class[0]);
            updateEditTextBackground.setAccessible(true);
            updateEditTextBackground.invoke(this, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDefaultTextColor(ColorStateList colors) {
        try {
            Field defaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            defaultTextColor.setAccessible(true);
            defaultTextColor.set(this, colors);
            updateLabelState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyFocusedTextColorResource() {
        this.mFocusedTextColorResId = SkinCompatHelper.checkResourceId(this.mFocusedTextColorResId);
        if (this.mFocusedTextColorResId != 0 && this.mFocusedTextColorResId != R.color.abc_hint_foreground_material_light) {
            setFocusedTextColor(SkinCompatResources.getColorStateList(getContext(), this.mFocusedTextColorResId));
            return;
        }
        if (getEditText() != null) {
            int textColorResId = 0;
            if (getEditText() instanceof SkinCompatEditText) {
                textColorResId = ((SkinCompatEditText) getEditText()).getTextColorResId();
            } else if (getEditText() instanceof SkinMaterialTextInputEditText) {
                textColorResId = ((SkinMaterialTextInputEditText) getEditText()).getTextColorResId();
            }
            int textColorResId2 = SkinCompatHelper.checkResourceId(textColorResId);
            if (textColorResId2 != 0) {
                ColorStateList colors = SkinCompatResources.getColorStateList(getContext(), textColorResId2);
                setFocusedTextColor(colors);
            }
        }
    }

    private void setFocusedTextColor(ColorStateList colors) {
        try {
            Field focusedTextColor = TextInputLayout.class.getDeclaredField("mFocusedTextColor");
            focusedTextColor.setAccessible(true);
            focusedTextColor.set(this, colors);
            updateLabelState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLabelState() {
        try {
            Method updateLabelState = TextInputLayout.class.getDeclaredMethod("updateLabelState", Boolean.TYPE);
            updateLabelState.setAccessible(true);
            updateLabelState.invoke(this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        applyErrorTextColorResource();
        applyCounterTextColorResource();
        applyFocusedTextColorResource();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySkin();
        }
    }
}
