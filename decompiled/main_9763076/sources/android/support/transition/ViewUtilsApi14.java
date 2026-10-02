package android.support.transition;

import android.graphics.Matrix;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ViewUtilsApi14 implements ViewUtilsImpl {
    ViewUtilsApi14() {
    }

    @Override // android.support.transition.ViewUtilsImpl
    public ViewOverlayImpl getOverlay(View view) {
        return ViewOverlayApi14.createFrom(view);
    }

    @Override // android.support.transition.ViewUtilsImpl
    public WindowIdImpl getWindowId(View view) {
        return new WindowIdApi14(view.getWindowToken());
    }

    @Override // android.support.transition.ViewUtilsImpl
    public void setTransitionAlpha(View view, float alpha) {
        Float savedAlpha = (Float) view.getTag(R.id.save_non_transition_alpha);
        if (savedAlpha != null) {
            view.setAlpha(savedAlpha.floatValue() * alpha);
        } else {
            view.setAlpha(alpha);
        }
    }

    @Override // android.support.transition.ViewUtilsImpl
    public float getTransitionAlpha(View view) {
        Float savedAlpha = (Float) view.getTag(R.id.save_non_transition_alpha);
        return savedAlpha != null ? view.getAlpha() / savedAlpha.floatValue() : view.getAlpha();
    }

    @Override // android.support.transition.ViewUtilsImpl
    public void saveNonTransitionAlpha(View view) {
        if (view.getTag(R.id.save_non_transition_alpha) == null) {
            view.setTag(R.id.save_non_transition_alpha, Float.valueOf(view.getAlpha()));
        }
    }

    @Override // android.support.transition.ViewUtilsImpl
    public void clearNonTransitionAlpha(View view) {
        if (view.getVisibility() == 0) {
            view.setTag(R.id.save_non_transition_alpha, null);
        }
    }

    @Override // android.support.transition.ViewUtilsImpl
    public void transformMatrixToGlobal(View view, Matrix matrix) {
        Object parent = view.getParent();
        if (parent instanceof View) {
            View vp = (View) parent;
            transformMatrixToGlobal(vp, matrix);
            matrix.preTranslate(-vp.getScrollX(), -vp.getScrollY());
        }
        matrix.preTranslate(view.getLeft(), view.getTop());
        Matrix vm = view.getMatrix();
        if (!vm.isIdentity()) {
            matrix.preConcat(vm);
        }
    }

    @Override // android.support.transition.ViewUtilsImpl
    public void transformMatrixToLocal(View view, Matrix matrix) {
        Object parent = view.getParent();
        if (parent instanceof View) {
            View vp = (View) parent;
            transformMatrixToLocal(vp, matrix);
            matrix.postTranslate(vp.getScrollX(), vp.getScrollY());
        }
        matrix.postTranslate(view.getLeft(), view.getTop());
        Matrix vm = view.getMatrix();
        if (!vm.isIdentity()) {
            Matrix inverted = new Matrix();
            if (vm.invert(inverted)) {
                matrix.postConcat(inverted);
            }
        }
    }

    @Override // android.support.transition.ViewUtilsImpl
    public void setLeftTopRightBottom(View v, int left, int top, int right, int bottom) {
        v.setLeft(left);
        v.setTop(top);
        v.setRight(right);
        v.setBottom(bottom);
    }
}
