package skin.support.widget;

import android.content.res.TypedArray;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import skin.support.R;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatCompoundButtonHelper extends SkinCompatHelper {
    private int mButtonResourceId = 0;
    private int mButtonTintResId = 0;
    private final CompoundButton mView;

    public SkinCompatCompoundButtonHelper(CompoundButton view) {
        this.mView = view;
    }

    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = this.mView.getContext().obtainStyledAttributes(attrs, R.styleable.CompoundButton, defStyleAttr, 0);
        try {
            if (a.hasValue(R.styleable.CompoundButton_android_button)) {
                this.mButtonResourceId = a.getResourceId(R.styleable.CompoundButton_android_button, 0);
            }
            if (a.hasValue(R.styleable.CompoundButton_buttonTint)) {
                this.mButtonTintResId = a.getResourceId(R.styleable.CompoundButton_buttonTint, 0);
            }
            a.recycle();
            applySkin();
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    public void setButtonDrawable(int resId) {
        this.mButtonResourceId = resId;
        applySkin();
    }

    public void applySkin() {
        this.mButtonResourceId = SkinCompatHelper.checkResourceId(this.mButtonResourceId);
        if (this.mButtonResourceId != 0) {
            this.mView.setButtonDrawable(SkinCompatResources.getDrawableCompat(this.mView.getContext(), this.mButtonResourceId));
        }
        this.mButtonTintResId = SkinCompatHelper.checkResourceId(this.mButtonTintResId);
        if (this.mButtonTintResId != 0) {
            CompoundButtonCompat.setButtonTintList(this.mView, SkinCompatResources.getColorStateList(this.mView.getContext(), this.mButtonTintResId));
        }
    }
}
