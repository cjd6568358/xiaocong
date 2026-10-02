package skin.support.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatCheckedTextView extends AppCompatCheckedTextView implements SkinCompatSupportable {
    private static final int[] TINT_ATTRS = {R.attr.checkMark};
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private int mCheckMarkResId;
    private SkinCompatTextHelper mTextHelper;

    public SkinCompatCheckedTextView(Context context) {
        this(context, null);
    }

    public SkinCompatCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, skin.support.R.attr.checkedTextViewStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinCompatCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCheckMarkResId = 0;
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        this.mTextHelper = SkinCompatTextHelper.create(this);
        this.mTextHelper.loadFromAttributes(attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, TINT_ATTRS, defStyleAttr, 0);
        this.mCheckMarkResId = a.getResourceId(0, 0);
        a.recycle();
        applyCheckMark();
    }

    public void setCheckMarkDrawable(int resId) {
        this.mCheckMarkResId = resId;
        applyCheckMark();
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
        applyCheckMark();
    }

    private void applyCheckMark() {
        this.mCheckMarkResId = SkinCompatHelper.checkResourceId(this.mCheckMarkResId);
        if (this.mCheckMarkResId != 0) {
            setCheckMarkDrawable(SkinCompatResources.getDrawableCompat(getContext(), this.mCheckMarkResId));
        }
    }
}
