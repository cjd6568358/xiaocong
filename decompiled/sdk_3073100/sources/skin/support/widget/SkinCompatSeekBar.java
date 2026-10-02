package skin.support.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import skin.support.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatSeekBar extends AppCompatSeekBar implements SkinCompatSupportable {
    private SkinCompatSeekBarHelper mSkinCompatSeekBarHelper;

    public SkinCompatSeekBar(Context context) {
        this(context, null);
    }

    public SkinCompatSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinCompatSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSkinCompatSeekBarHelper = new SkinCompatSeekBarHelper(this);
        this.mSkinCompatSeekBarHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        if (this.mSkinCompatSeekBarHelper != null) {
            this.mSkinCompatSeekBarHelper.applySkin();
        }
    }
}
