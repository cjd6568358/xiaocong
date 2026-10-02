package skin.support.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import skin.support.R;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatToolbar extends Toolbar implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private int mNavigationIconResId;
    private int mSubtitleTextColorResId;
    private int mTitleTextColorResId;

    public SkinCompatToolbar(Context context) {
        this(context, null);
    }

    public SkinCompatToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinCompatToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTitleTextColorResId = 0;
        this.mSubtitleTextColorResId = 0;
        this.mNavigationIconResId = 0;
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0);
        this.mNavigationIconResId = a.getResourceId(R.styleable.Toolbar_navigationIcon, 0);
        int titleAp = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        int subtitleAp = a.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
        a.recycle();
        if (titleAp != 0) {
            TypedArray a2 = context.obtainStyledAttributes(titleAp, R.styleable.SkinTextAppearance);
            this.mTitleTextColorResId = a2.getResourceId(R.styleable.SkinTextAppearance_android_textColor, 0);
            a2.recycle();
        }
        if (subtitleAp != 0) {
            TypedArray a3 = context.obtainStyledAttributes(subtitleAp, R.styleable.SkinTextAppearance);
            this.mSubtitleTextColorResId = a3.getResourceId(R.styleable.SkinTextAppearance_android_textColor, 0);
            a3.recycle();
        }
        TypedArray a4 = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0);
        if (a4.hasValue(R.styleable.Toolbar_titleTextColor)) {
            this.mTitleTextColorResId = a4.getResourceId(R.styleable.Toolbar_titleTextColor, 0);
        }
        if (a4.hasValue(R.styleable.Toolbar_subtitleTextColor)) {
            this.mSubtitleTextColorResId = a4.getResourceId(R.styleable.Toolbar_subtitleTextColor, 0);
        }
        a4.recycle();
        applyTitleTextColor();
        applySubtitleTextColor();
        applyNavigationIcon();
    }

    private void applyTitleTextColor() {
        this.mTitleTextColorResId = SkinCompatHelper.checkResourceId(this.mTitleTextColorResId);
        if (this.mTitleTextColorResId != 0) {
            setTitleTextColor(SkinCompatResources.getColor(getContext(), this.mTitleTextColorResId));
        }
    }

    private void applySubtitleTextColor() {
        this.mSubtitleTextColorResId = SkinCompatHelper.checkResourceId(this.mSubtitleTextColorResId);
        if (this.mSubtitleTextColorResId != 0) {
            setSubtitleTextColor(SkinCompatResources.getColor(getContext(), this.mSubtitleTextColorResId));
        }
    }

    private void applyNavigationIcon() {
        this.mNavigationIconResId = SkinCompatHelper.checkResourceId(this.mNavigationIconResId);
        if (this.mNavigationIconResId != 0) {
            setNavigationIcon(SkinCompatResources.getDrawableCompat(getContext(), this.mNavigationIconResId));
        }
    }

    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    public void setNavigationIcon(int resId) {
        super.setNavigationIcon(resId);
        this.mNavigationIconResId = resId;
        applyNavigationIcon();
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySkin();
        }
        applyTitleTextColor();
        applySubtitleTextColor();
        applyNavigationIcon();
    }
}
