package skin.support.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatProgressBar extends ProgressBar implements SkinCompatSupportable {
    private SkinCompatProgressBarHelper mSkinCompatProgressBarHelper;

    public SkinCompatProgressBar(Context context) {
        this(context, null);
    }

    public SkinCompatProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.progressBarStyle);
    }

    public SkinCompatProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSkinCompatProgressBarHelper = new SkinCompatProgressBarHelper(this);
        this.mSkinCompatProgressBarHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        if (this.mSkinCompatProgressBarHelper != null) {
            this.mSkinCompatProgressBarHelper.applySkin();
        }
    }
}
