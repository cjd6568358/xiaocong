package skin.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;
import skin.support.content.res.SkinCompatResources;
import skin.support.design.R;
import skin.support.widget.SkinCompatBackgroundHelper;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinMaterialCollapsingToolbarLayout extends CollapsingToolbarLayout implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private int mContentScrimResId;
    private int mStatusBarScrimResId;

    public SkinMaterialCollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public SkinMaterialCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinMaterialCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContentScrimResId = 0;
        this.mStatusBarScrimResId = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CollapsingToolbarLayout, defStyleAttr, R.style.Widget_Design_CollapsingToolbar);
        this.mContentScrimResId = a.getResourceId(R.styleable.CollapsingToolbarLayout_contentScrim, 0);
        this.mStatusBarScrimResId = a.getResourceId(R.styleable.CollapsingToolbarLayout_statusBarScrim, 0);
        a.recycle();
        applyContentScrimResource();
        applyStatusBarScrimResource();
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, 0);
    }

    private void applyStatusBarScrimResource() {
        Drawable drawable;
        this.mStatusBarScrimResId = SkinCompatHelper.checkResourceId(this.mStatusBarScrimResId);
        if (this.mStatusBarScrimResId != 0 && (drawable = SkinCompatResources.getDrawableCompat(getContext(), this.mStatusBarScrimResId)) != null) {
            setStatusBarScrim(drawable);
        }
    }

    private void applyContentScrimResource() {
        Drawable drawable;
        this.mContentScrimResId = SkinCompatHelper.checkResourceId(this.mContentScrimResId);
        if (this.mContentScrimResId != 0 && (drawable = SkinCompatResources.getDrawableCompat(getContext(), this.mContentScrimResId)) != null) {
            setContentScrim(drawable);
        }
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        applyContentScrimResource();
        applyStatusBarScrimResource();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySkin();
        }
    }
}
