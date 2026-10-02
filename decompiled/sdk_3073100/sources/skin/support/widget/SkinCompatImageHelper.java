package skin.support.widget;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import skin.support.R;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatImageHelper extends SkinCompatHelper {
    private static final String TAG = SkinCompatImageHelper.class.getSimpleName();
    private final ImageView mView;
    private int mSrcResId = 0;
    private int mSrcCompatResId = 0;

    public SkinCompatImageHelper(ImageView imageView) {
        this.mView = imageView;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        try {
            a = this.mView.getContext().obtainStyledAttributes(attrs, R.styleable.SkinCompatImageView, defStyleAttr, 0);
            this.mSrcResId = a.getResourceId(R.styleable.SkinCompatImageView_android_src, 0);
            this.mSrcCompatResId = a.getResourceId(R.styleable.SkinCompatImageView_srcCompat, 0);
            if (a != null) {
                a.recycle();
            }
            applySkin();
        } catch (Throwable th) {
            if (a != null) {
                a.recycle();
            }
            throw th;
        }
    }

    public void setImageResource(int resId) {
        this.mSrcResId = resId;
        applySkin();
    }

    public void applySkin() {
        Drawable drawable;
        this.mSrcCompatResId = checkResourceId(this.mSrcCompatResId);
        if (this.mSrcCompatResId != 0) {
            Drawable drawable2 = SkinCompatResources.getDrawableCompat(this.mView.getContext(), this.mSrcCompatResId);
            if (drawable2 != null) {
                this.mView.setImageDrawable(drawable2);
                return;
            }
            return;
        }
        this.mSrcResId = checkResourceId(this.mSrcResId);
        if (this.mSrcResId != 0 && (drawable = SkinCompatResources.getDrawableCompat(this.mView.getContext(), this.mSrcResId)) != null) {
            this.mView.setImageDrawable(drawable);
        }
    }
}
