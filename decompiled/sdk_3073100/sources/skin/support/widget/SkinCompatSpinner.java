package skin.support.widget;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.util.Log;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatSpinner extends AppCompatSpinner implements SkinCompatSupportable {
    private SkinCompatBackgroundHelper mBackgroundTintHelper;
    private int mPopupBackgroundResId;
    private static final String TAG = SkinCompatSpinner.class.getSimpleName();
    private static final int[] ATTRS_ANDROID_SPINNERMODE = {R.attr.spinnerMode};

    public SkinCompatSpinner(Context context) {
        this(context, null);
    }

    public SkinCompatSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, skin.support.R.attr.spinnerStyle);
    }

    public SkinCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public SkinCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        this(context, attrs, defStyleAttr, mode, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SkinCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
        this.mPopupBackgroundResId = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, skin.support.R.styleable.Spinner, defStyleAttr, 0);
        if (getPopupContext() != null) {
            if (mode == -1) {
                if (Build.VERSION.SDK_INT >= 11) {
                    TypedArray aa = null;
                    try {
                        try {
                            aa = context.obtainStyledAttributes(attrs, ATTRS_ANDROID_SPINNERMODE, defStyleAttr, 0);
                            mode = aa.hasValue(0) ? aa.getInt(0, 0) : mode;
                            if (aa != null) {
                                aa.recycle();
                            }
                        } catch (Exception e) {
                            Log.i(TAG, "Could not read android:spinnerMode", e);
                            if (aa != null) {
                                aa.recycle();
                            }
                        }
                    } catch (Throwable th) {
                        if (aa != null) {
                            aa.recycle();
                        }
                        throw th;
                    }
                } else {
                    mode = 1;
                }
            }
            if (mode == 1) {
                TypedArray pa = getPopupContext().obtainStyledAttributes(attrs, skin.support.R.styleable.Spinner, defStyleAttr, 0);
                this.mPopupBackgroundResId = pa.getResourceId(skin.support.R.styleable.Spinner_android_popupBackground, 0);
                pa.recycle();
            }
        }
        a.recycle();
        this.mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    public void setPopupBackgroundResource(int resId) {
        super.setPopupBackgroundResource(resId);
        this.mPopupBackgroundResId = resId;
        applyPopupBackground();
    }

    private void applyPopupBackground() {
        this.mPopupBackgroundResId = SkinCompatHelper.checkResourceId(this.mPopupBackgroundResId);
        if (this.mPopupBackgroundResId != 0) {
            setPopupBackgroundDrawable(SkinCompatResources.getDrawableCompat(getContext(), this.mPopupBackgroundResId));
        }
    }

    @Override // skin.support.widget.SkinCompatSupportable
    public void applySkin() {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySkin();
        }
        applyPopupBackground();
    }
}
