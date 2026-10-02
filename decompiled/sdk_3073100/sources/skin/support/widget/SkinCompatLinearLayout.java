package skin.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatLinearLayout extends LinearLayout implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatLinearLayout(Context context) {
        this(context, null);
    }

    public SkinCompatLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinCompatLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
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
