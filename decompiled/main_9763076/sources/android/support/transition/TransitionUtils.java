package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class TransitionUtils {
    static View copyViewImage(ViewGroup sceneRoot, View view, View parent) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(-parent.getScrollX(), -parent.getScrollY());
        ViewUtils.transformMatrixToGlobal(view, matrix);
        ViewUtils.transformMatrixToLocal(sceneRoot, matrix);
        RectF bounds = new RectF(0.0f, 0.0f, view.getWidth(), view.getHeight());
        matrix.mapRect(bounds);
        int left = Math.round(bounds.left);
        int top = Math.round(bounds.top);
        int right = Math.round(bounds.right);
        int bottom = Math.round(bounds.bottom);
        ImageView copy = new ImageView(view.getContext());
        copy.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Bitmap bitmap = createViewBitmap(view, matrix, bounds);
        if (bitmap != null) {
            copy.setImageBitmap(bitmap);
        }
        int widthSpec = View.MeasureSpec.makeMeasureSpec(right - left, 1073741824);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(bottom - top, 1073741824);
        copy.measure(widthSpec, heightSpec);
        copy.layout(left, top, right, bottom);
        return copy;
    }

    private static Bitmap createViewBitmap(View view, Matrix matrix, RectF bounds) {
        int bitmapWidth = Math.round(bounds.width());
        int bitmapHeight = Math.round(bounds.height());
        if (bitmapWidth <= 0 || bitmapHeight <= 0) {
            return null;
        }
        float scale = Math.min(1.0f, 1048576.0f / (bitmapWidth * bitmapHeight));
        matrix.postTranslate(-bounds.left, -bounds.top);
        matrix.postScale(scale, scale);
        Bitmap bitmap = Bitmap.createBitmap((int) (bitmapWidth * scale), (int) (bitmapHeight * scale), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.concat(matrix);
        view.draw(canvas);
        return bitmap;
    }

    static Animator mergeAnimators(Animator animator1, Animator animator2) {
        if (animator1 != null) {
            if (animator2 == null) {
                return animator1;
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator1, animator2);
            return animatorSet;
        }
        return animator2;
    }
}
