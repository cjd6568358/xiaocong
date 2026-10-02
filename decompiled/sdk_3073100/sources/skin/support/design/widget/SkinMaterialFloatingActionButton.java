package skin.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import skin.support.content.res.SkinCompatResources;
import skin.support.design.R;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatImageHelper;
import skin.support.widget.SkinCompatSupportable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinMaterialFloatingActionButton extends FloatingActionButton implements SkinCompatSupportable {
    private int mBackgroundTintResId;
    private SkinCompatImageHelper mImageHelper;
    private int mRippleColorResId;

    public SkinMaterialFloatingActionButton(Context context) {
        this(context, null);
    }

    public SkinMaterialFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinMaterialFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mRippleColorResId = 0;
        this.mBackgroundTintResId = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionButton, defStyleAttr, R.style.Widget_Design_FloatingActionButton);
        this.mBackgroundTintResId = a.getResourceId(R.styleable.FloatingActionButton_backgroundTint, 0);
        this.mRippleColorResId = a.getResourceId(R.styleable.FloatingActionButton_rippleColor, 0);
        a.recycle();
        applyBackgroundTintResource();
        applyRippleColorResource();
        this.mImageHelper = new SkinCompatImageHelper(this);
        this.mImageHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    private void applyBackgroundTintResource() {
        this.mBackgroundTintResId = SkinCompatHelper.checkResourceId(this.mBackgroundTintResId);
        if (this.mBackgroundTintResId != 0) {
            setBackgroundTintList(SkinCompatResources.getColorStateList(getContext(), this.mBackgroundTintResId));
        }
    }

    private void applyRippleColorResource() {
        this.mRippleColorResId = SkinCompatHelper.checkResourceId(this.mRippleColorResId);
        if (this.mRippleColorResId != 0) {
            setRippleColor(SkinCompatResources.getColor(getContext(), this.mRippleColorResId));
        }
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        applyBackgroundTintResource();
        applyRippleColorResource();
        if (this.mImageHelper != null) {
            this.mImageHelper.applySkin();
        }
    }
}
