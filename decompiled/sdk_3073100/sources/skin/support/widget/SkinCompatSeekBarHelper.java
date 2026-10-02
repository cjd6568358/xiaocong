package skin.support.widget;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.SeekBar;
import skin.support.R;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatSeekBarHelper extends SkinCompatProgressBarHelper {
    private int mThumbResId;
    private final SeekBar mView;

    public SkinCompatSeekBarHelper(SeekBar view) {
        super(view);
        this.mThumbResId = 0;
        this.mView = view;
    }

    @Override // skin.support.widget.SkinCompatProgressBarHelper
    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        super.loadFromAttributes(attrs, defStyleAttr);
        TypedArray a = this.mView.getContext().obtainStyledAttributes(attrs, R.styleable.AppCompatSeekBar, defStyleAttr, 0);
        this.mThumbResId = a.getResourceId(R.styleable.AppCompatSeekBar_android_thumb, 0);
        a.recycle();
        applySkin();
    }

    @Override // skin.support.widget.SkinCompatProgressBarHelper
    public void applySkin() {
        super.applySkin();
        this.mThumbResId = checkResourceId(this.mThumbResId);
        if (this.mThumbResId != 0) {
            this.mView.setThumb(SkinCompatResources.getDrawableCompat(this.mView.getContext(), this.mThumbResId));
        }
    }
}
