package skin.support.widget;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.xiaocong.smarthome.network.constant.NetworkConstant;
import skin.support.R;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatProgressBarHelper extends SkinCompatHelper {
    private Bitmap mSampleTile;
    private final ProgressBar mView;
    private int mIndeterminateDrawableResId = 0;
    private int mProgressDrawableResId = 0;
    private int mIndeterminateTintResId = 0;

    SkinCompatProgressBarHelper(ProgressBar view) {
        this.mView = view;
    }

    void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = this.mView.getContext().obtainStyledAttributes(attrs, R.styleable.SkinCompatProgressBar, defStyleAttr, 0);
        this.mIndeterminateDrawableResId = a.getResourceId(R.styleable.SkinCompatProgressBar_android_indeterminateDrawable, 0);
        this.mProgressDrawableResId = a.getResourceId(R.styleable.SkinCompatProgressBar_android_progressDrawable, 0);
        a.recycle();
        if (Build.VERSION.SDK_INT > 21) {
            TypedArray a2 = this.mView.getContext().obtainStyledAttributes(attrs, new int[]{android.R.attr.indeterminateTint}, defStyleAttr, 0);
            this.mIndeterminateTintResId = a2.getResourceId(0, 0);
            a2.recycle();
        }
        applySkin();
    }

    private Drawable tileify(Drawable drawable, boolean z) {
        if (drawable instanceof DrawableWrapper) {
            Drawable wrappedDrawable = ((DrawableWrapper) drawable).getWrappedDrawable();
            if (wrappedDrawable != null) {
                ((DrawableWrapper) drawable).setWrappedDrawable(tileify(wrappedDrawable, z));
            }
        } else {
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                Drawable[] drawableArr = new Drawable[numberOfLayers];
                for (int i = 0; i < numberOfLayers; i++) {
                    int id = layerDrawable.getId(i);
                    drawableArr[i] = tileify(layerDrawable.getDrawable(i), id == 16908301 || id == 16908303);
                }
                LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
                for (int i2 = 0; i2 < numberOfLayers; i2++) {
                    layerDrawable2.setId(i2, layerDrawable.getId(i2));
                }
                return layerDrawable2;
            }
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (this.mSampleTile == null) {
                    this.mSampleTile = bitmap;
                }
                ShapeDrawable shapeDrawable = new ShapeDrawable(getDrawableShape());
                shapeDrawable.getPaint().setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
                shapeDrawable.getPaint().setColorFilter(bitmapDrawable.getPaint().getColorFilter());
                Drawable clipDrawable = shapeDrawable;
                if (z) {
                    clipDrawable = new ClipDrawable(shapeDrawable, 3, 1);
                }
                return clipDrawable;
            }
        }
        return drawable;
    }

    private Drawable tileifyIndeterminate(Drawable drawable) {
        if (drawable instanceof AnimationDrawable) {
            AnimationDrawable background = (AnimationDrawable) drawable;
            int N = background.getNumberOfFrames();
            AnimationDrawable newBg = new AnimationDrawable();
            newBg.setOneShot(background.isOneShot());
            for (int i = 0; i < N; i++) {
                Drawable frame = tileify(background.getFrame(i), true);
                frame.setLevel(NetworkConstant.HTTP_TIMEOUT);
                newBg.addFrame(frame, background.getDuration(i));
            }
            newBg.setLevel(NetworkConstant.HTTP_TIMEOUT);
            return newBg;
        }
        return drawable;
    }

    private Shape getDrawableShape() {
        float[] roundedCorners = {5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
        return new RoundRectShape(roundedCorners, null, null);
    }

    public void applySkin() {
        this.mIndeterminateDrawableResId = checkResourceId(this.mIndeterminateDrawableResId);
        if (this.mIndeterminateDrawableResId != 0) {
            Drawable drawable = SkinCompatResources.getDrawableCompat(this.mView.getContext(), this.mIndeterminateDrawableResId);
            drawable.setBounds(this.mView.getIndeterminateDrawable().getBounds());
            this.mView.setIndeterminateDrawable(tileifyIndeterminate(drawable));
        }
        this.mProgressDrawableResId = checkProgressDrawableResId(this.mProgressDrawableResId);
        if (this.mProgressDrawableResId != 0) {
            this.mView.setProgressDrawable(tileify(SkinCompatResources.getDrawableCompat(this.mView.getContext(), this.mProgressDrawableResId), false));
        }
        if (Build.VERSION.SDK_INT > 21) {
            this.mIndeterminateTintResId = checkResourceId(this.mIndeterminateTintResId);
            if (this.mIndeterminateTintResId != 0) {
                this.mView.setIndeterminateTintList(SkinCompatResources.getColorStateList(this.mView.getContext(), this.mIndeterminateTintResId));
            }
        }
    }

    private int checkProgressDrawableResId(int mProgressDrawableResId) {
        if (mProgressDrawableResId == R.drawable.abc_ratingbar_material) {
            return 0;
        }
        return checkResourceId(mProgressDrawableResId);
    }
}
