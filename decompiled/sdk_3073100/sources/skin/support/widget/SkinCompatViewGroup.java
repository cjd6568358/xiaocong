package skin.support.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class SkinCompatViewGroup extends ViewGroup implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatViewGroup(Context context) {
        this(context, null);
    }

    public SkinCompatViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinCompatViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
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
}
