package skin.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatRadioGroup extends RadioGroup implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatRadioGroup(Context context) {
        this(context, null);
    }

    public SkinCompatRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, 0);
    }

    @Override // android.view.View
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySkin();
        }
    }
}
