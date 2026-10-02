package skin.support.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.AttributeSet;
import skin.support.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatRatingBar extends AppCompatRatingBar implements SkinCompatSupportable {
    private SkinCompatProgressBarHelper mSkinCompatProgressBarHelper;

    public SkinCompatRatingBar(Context context) {
        this(context, null);
    }

    public SkinCompatRatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.ratingBarStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinCompatRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
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
