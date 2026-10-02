package skin.support.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatImageView extends AppCompatImageView implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private SkinCompatImageHelper mImageHelper;

    public SkinCompatImageView(Context context) {
        this(context, null);
    }

    public SkinCompatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinCompatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
        this.mImageHelper = new SkinCompatImageHelper(this);
        this.mImageHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    public void setImageResource(int resId) {
        if (this.mImageHelper != null) {
            this.mImageHelper.setImageResource(resId);
        }
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySkin();
        }
        if (this.mImageHelper != null) {
            this.mImageHelper.applySkin();
        }
    }
}
