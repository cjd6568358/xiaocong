package skin.support.design.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.util.TypedValue;
import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinMaterialBottomNavigationView extends BottomNavigationView implements SkinCompatSupportable {
    private static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    private static final int[] DISABLED_STATE_SET = {-16842910};
    private int mDefaultTintResId;
    private int mIconTintResId;
    private int mTextColorResId;

    public SkinMaterialBottomNavigationView(Context context) {
        this(context, null);
    }

    public SkinMaterialBottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinMaterialBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTextColorResId = 0;
        this.mIconTintResId = 0;
        this.mDefaultTintResId = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, skin.support.design.R.styleable.BottomNavigationView, defStyleAttr, skin.support.design.R.style.Widget_Design_BottomNavigationView);
        if (a.hasValue(skin.support.design.R.styleable.BottomNavigationView_itemIconTint)) {
            this.mIconTintResId = a.getResourceId(skin.support.design.R.styleable.BottomNavigationView_itemIconTint, 0);
        } else {
            this.mDefaultTintResId = resolveColorPrimary();
        }
        if (a.hasValue(skin.support.design.R.styleable.BottomNavigationView_itemTextColor)) {
            this.mTextColorResId = a.getResourceId(skin.support.design.R.styleable.BottomNavigationView_itemTextColor, 0);
        } else {
            this.mDefaultTintResId = resolveColorPrimary();
        }
        a.recycle();
        applyItemIconTintResource();
        applyItemTextColorResource();
    }

    private void applyItemTextColorResource() {
        this.mTextColorResId = SkinCompatHelper.checkResourceId(this.mTextColorResId);
        if (this.mTextColorResId != 0) {
            setItemTextColor(SkinCompatResources.getColorStateList(getContext(), this.mTextColorResId));
            return;
        }
        this.mDefaultTintResId = SkinCompatHelper.checkResourceId(this.mDefaultTintResId);
        if (this.mDefaultTintResId != 0) {
            setItemTextColor(createDefaultColorStateList(R.attr.textColorSecondary));
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

    private int resolveColorPrimary() {
        TypedValue value = new TypedValue();
        if (getContext().getTheme().resolveAttribute(skin.support.design.R.attr.colorPrimary, value, true)) {
            return value.resourceId;
        }
        return 0;
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        applyItemIconTintResource();
        applyItemTextColorResource();
    }
}
