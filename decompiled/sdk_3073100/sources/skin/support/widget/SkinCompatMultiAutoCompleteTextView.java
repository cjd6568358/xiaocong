package skin.support.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.util.AttributeSet;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatMultiAutoCompleteTextView extends AppCompatMultiAutoCompleteTextView implements SkinCompatSupportable {
    private static final int[] TINT_ATTRS = {R.attr.popupBackground};
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private int mDropDownBackgroundResId;
    private SkinCompatTextHelper mTextHelper;

    public SkinCompatMultiAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public SkinCompatMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, skin.support.R.attr.editTextStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinCompatMultiAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDropDownBackgroundResId = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, TINT_ATTRS, defStyleAttr, 0);
        if (a.hasValue(0)) {
            this.mDropDownBackgroundResId = a.getResourceId(0, 0);
        }
        a.recycle();
        applyDropDownBackgroundResource();
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        this.mTextHelper = SkinCompatTextHelper.create(this);
        this.mTextHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    public void setDropDownBackgroundResource(int resId) {
        super.setDropDownBackgroundResource(resId);
        this.mDropDownBackgroundResId = resId;
        applyDropDownBackgroundResource();
    }

    private void applyDropDownBackgroundResource() {
        Drawable drawable;
        this.mDropDownBackgroundResId = SkinCompatHelper.checkResourceId(this.mDropDownBackgroundResId);
        if (this.mDropDownBackgroundResId != 0 && (drawable = SkinCompatResources.getDrawableCompat(getContext(), this.mDropDownBackgroundResId)) != null) {
            setDropDownBackgroundDrawable(drawable);
        }
    }

    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    public void setTextAppearance(int resId) {
        setTextAppearance(getContext(), resId);
    }

    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance(context, resId);
        }
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySkin();
        }
        if (this.mTextHelper != null) {
            this.mTextHelper.applySkin();
        }
        applyDropDownBackgroundResource();
    }
}
