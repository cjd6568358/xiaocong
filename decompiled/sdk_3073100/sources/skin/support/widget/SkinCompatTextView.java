package skin.support.widget;

import android.R;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatTextView extends AppCompatTextView implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private SkinCompatTextHelper mTextHelper;

    public SkinCompatTextView(Context context) {
        this(context, null);
    }

    public SkinCompatTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.textViewStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        this.mTextHelper = SkinCompatTextHelper.create(this);
        this.mTextHelper.loadFromAttributes(attrs, defStyleAttr);
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
    }
}
