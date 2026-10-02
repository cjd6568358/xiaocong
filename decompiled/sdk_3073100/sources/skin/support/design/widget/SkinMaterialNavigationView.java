package skin.support.design.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.util.TypedValue;
import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatBackgroundHelper;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;
import skin.support.widget.SkinCompatThemeUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinMaterialNavigationView extends NavigationView implements SkinCompatSupportable {
    private static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    private static final int[] DISABLED_STATE_SET = {-16842910};
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private int mDefaultTintResId;
    private int mIconTintResId;
    private int mItemBackgroundResId;
    private int mTextColorResId;

    public SkinMaterialNavigationView(Context context) {
        this(context, null);
    }

    public SkinMaterialNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinMaterialNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        int textAppearance;
        super(context, attrs, defStyleAttr);
        this.mItemBackgroundResId = 0;
        this.mTextColorResId = 0;
        this.mDefaultTintResId = 0;
        this.mIconTintResId = 0;
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, 0);
        TypedArray a = context.obtainStyledAttributes(attrs, skin.support.design.R.styleable.NavigationView, defStyleAttr, skin.support.design.R.style.Widget_Design_NavigationView);
        if (a.hasValue(skin.support.design.R.styleable.NavigationView_itemIconTint)) {
            this.mIconTintResId = a.getResourceId(skin.support.design.R.styleable.NavigationView_itemIconTint, 0);
        } else {
            this.mDefaultTintResId = SkinCompatThemeUtils.getColorPrimaryResId(context);
        }
        if (a.hasValue(skin.support.design.R.styleable.NavigationView_itemTextAppearance) && (textAppearance = a.getResourceId(skin.support.design.R.styleable.NavigationView_itemTextAppearance, 0)) != 0) {
            TypedArray ap = context.obtainStyledAttributes(textAppearance, skin.support.design.R.styleable.SkinTextAppearance);
            if (ap.hasValue(skin.support.design.R.styleable.SkinTextAppearance_android_textColor)) {
                this.mTextColorResId = ap.getResourceId(skin.support.design.R.styleable.SkinTextAppearance_android_textColor, 0);
            }
            ap.recycle();
        }
        if (a.hasValue(skin.support.design.R.styleable.NavigationView_itemTextColor)) {
            this.mTextColorResId = a.getResourceId(skin.support.design.R.styleable.NavigationView_itemTextColor, 0);
        } else {
            this.mDefaultTintResId = SkinCompatThemeUtils.getColorPrimaryResId(context);
        }
        if (this.mTextColorResId == 0) {
            this.mTextColorResId = SkinCompatThemeUtils.getTextColorPrimaryResId(context);
        }
        this.mItemBackgroundResId = a.getResourceId(skin.support.design.R.styleable.NavigationView_itemBackground, 0);
        a.recycle();
        applyItemIconTintResource();
        applyItemTextColorResource();
        applyItemBackgroundResource();
    }

    public void setItemBackgroundResource(int resId) {
        super.setItemBackgroundResource(resId);
        this.mItemBackgroundResId = resId;
        applyItemBackgroundResource();
    }

    private void applyItemBackgroundResource() {
        Drawable drawable;
        this.mItemBackgroundResId = SkinCompatHelper.checkResourceId(this.mItemBackgroundResId);
        if (this.mItemBackgroundResId != 0 && (drawable = SkinCompatResources.getDrawableCompat(getContext(), this.mItemBackgroundResId)) != null) {
            setItemBackground(drawable);
        }
    }

    public void setItemTextAppearance(int resId) {
        super.setItemTextAppearance(resId);
        if (resId != 0) {
            TypedArray a = getContext().obtainStyledAttributes(resId, skin.support.design.R.styleable.SkinTextAppearance);
            if (a.hasValue(skin.support.design.R.styleable.SkinTextAppearance_android_textColor)) {
                this.mTextColorResId = a.getResourceId(skin.support.design.R.styleable.SkinTextAppearance_android_textColor, 0);
            }
            a.recycle();
            applyItemTextColorResource();
        }
    }

    private void applyItemTextColorResource() {
        this.mTextColorResId = SkinCompatHelper.checkResourceId(this.mTextColorResId);
        if (this.mTextColorResId != 0) {
            setItemTextColor(SkinCompatResources.getColorStateList(getContext(), this.mTextColorResId));
            return;
        }
        this.mDefaultTintResId = SkinCompatHelper.checkResourceId(this.mDefaultTintResId);
        if (this.mDefaultTintResId != 0) {
            setItemTextColor(createDefaultColorStateList(R.attr.textColorPrimary));
        }
    }

    private void applyItemIconTintResource() {
        this.mIconTintResId = SkinCompatHelper.checkResourceId(this.mIconTintResId);
        if (this.mIconTintResId != 0) {
            setItemIconTintList(SkinCompatResources.getColorStateList(getContext(), this.mIconTintResId));
            return;
        }
        this.mDefaultTintResId = SkinCompatHelper.checkResourceId(this.mDefaultTintResId);
        if (this.mDefaultTintResId != 0) {
            setItemIconTintList(createDefaultColorStateList(R.attr.textColorSecondary));
        }
    }

    private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = SkinCompatResources.getColorStateList(getContext(), value.resourceId);
        int colorPrimary = SkinCompatResources.getColor(getContext(), this.mDefaultTintResId);
        int defaultColor = baseColor.getDefaultColor();
        return new ColorStateList(new int[][]{DISABLED_STATE_SET, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{baseColor.getColorForState(DISABLED_STATE_SET, defaultColor), colorPrimary, defaultColor});
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySkin();
        }
        applyItemIconTintResource();
        applyItemTextColorResource();
        applyItemBackgroundResource();
    }
}
