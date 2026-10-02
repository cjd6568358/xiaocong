package skin.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import skin.support.content.res.SkinCompatResources;
import skin.support.design.R;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinMaterialTabLayout extends TabLayout implements SkinCompatSupportable {
    private int mTabIndicatorColorResId;
    private int mTabSelectedTextColorResId;
    private int mTabTextColorsResId;

    public SkinMaterialTabLayout(Context context) {
        this(context, null);
    }

    public SkinMaterialTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinMaterialTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTabIndicatorColorResId = 0;
        this.mTabTextColorsResId = 0;
        this.mTabSelectedTextColorResId = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayout, defStyleAttr, 0);
        this.mTabIndicatorColorResId = a.getResourceId(R.styleable.TabLayout_tabIndicatorColor, 0);
        int tabTextAppearance = a.getResourceId(R.styleable.TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
        TypedArray ta = context.obtainStyledAttributes(tabTextAppearance, R.styleable.SkinTextAppearance);
        try {
            this.mTabTextColorsResId = ta.getResourceId(R.styleable.SkinTextAppearance_android_textColor, 0);
            ta.recycle();
            if (a.hasValue(R.styleable.TabLayout_tabTextColor)) {
                this.mTabTextColorsResId = a.getResourceId(R.styleable.TabLayout_tabTextColor, 0);
            }
            if (a.hasValue(R.styleable.TabLayout_tabSelectedTextColor)) {
                this.mTabSelectedTextColorResId = a.getResourceId(R.styleable.TabLayout_tabSelectedTextColor, 0);
            }
            a.recycle();
            applySkin();
        } catch (Throwable th) {
            ta.recycle();
            throw th;
        }
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        this.mTabIndicatorColorResId = SkinCompatHelper.checkResourceId(this.mTabIndicatorColorResId);
        if (this.mTabIndicatorColorResId != 0) {
            setSelectedTabIndicatorColor(SkinCompatResources.getColor(getContext(), this.mTabIndicatorColorResId));
        }
        this.mTabTextColorsResId = SkinCompatHelper.checkResourceId(this.mTabTextColorsResId);
        if (this.mTabTextColorsResId != 0) {
            setTabTextColors(SkinCompatResources.getColorStateList(getContext(), this.mTabTextColorsResId));
        }
        this.mTabSelectedTextColorResId = SkinCompatHelper.checkResourceId(this.mTabSelectedTextColorResId);
        if (this.mTabSelectedTextColorResId != 0) {
            int selected = SkinCompatResources.getColor(getContext(), this.mTabSelectedTextColorResId);
            if (getTabTextColors() != null) {
                setTabTextColors(getTabTextColors().getDefaultColor(), selected);
            }
        }
    }
}
