package skin.support.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import skin.support.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatCheckBox extends AppCompatCheckBox implements SkinCompatSupportable {
    private SkinCompatCompoundButtonHelper mCompoundButtonHelper;

    public SkinCompatCheckBox(Context context) {
        this(context, null);
    }

    public SkinCompatCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.checkboxStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinCompatCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCompoundButtonHelper = new SkinCompatCompoundButtonHelper(this);
        this.mCompoundButtonHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    public void setButtonDrawable(int resId) {
        super.setButtonDrawable(resId);
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setButtonDrawable(resId);
        }
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.applySkin();
        }
    }
}
