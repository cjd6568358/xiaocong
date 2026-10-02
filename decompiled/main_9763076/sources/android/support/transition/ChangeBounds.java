package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ChangeBounds extends Transition {
    private static final String[] sTransitionProperties = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY = new Property<Drawable, PointF>(PointF.class, "boundsOrigin") { // from class: android.support.transition.ChangeBounds.1
        private Rect mBounds = new Rect();

        @Override // android.util.Property
        public void set(Drawable object, PointF value) {
            object.copyBounds(this.mBounds);
            this.mBounds.offsetTo(Math.round(value.x), Math.round(value.y));
            object.setBounds(this.mBounds);
        }

        @Override // android.util.Property
        public PointF get(Drawable object) {
            object.copyBounds(this.mBounds);
            return new PointF(this.mBounds.left, this.mBounds.top);
        }
    };
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "topLeft") { // from class: android.support.transition.ChangeBounds.2
        @Override // android.util.Property
        public void set(ViewBounds viewBounds, PointF topLeft) {
            viewBounds.setTopLeft(topLeft);
        }

        @Override // android.util.Property
        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    };
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "bottomRight") { // from class: android.support.transition.ChangeBounds.3
        @Override // android.util.Property
        public void set(ViewBounds viewBounds, PointF bottomRight) {
            viewBounds.setBottomRight(bottomRight);
        }

        @Override // android.util.Property
        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    };
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "bottomRight") { // from class: android.support.transition.ChangeBounds.4
        @Override // android.util.Property
        public void set(View view, PointF bottomRight) {
            int left = view.getLeft();
            int top = view.getTop();
            int right = Math.round(bottomRight.x);
            int bottom = Math.round(bottomRight.y);
            ViewUtils.setLeftTopRightBottom(view, left, top, right, bottom);
        }

        @Override // android.util.Property
        public PointF get(View view) {
            return null;
        }
    };
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "topLeft") { // from class: android.support.transition.ChangeBounds.5
        @Override // android.util.Property
        public void set(View view, PointF topLeft) {
            int left = Math.round(topLeft.x);
            int top = Math.round(topLeft.y);
            int right = view.getRight();
            int bottom = view.getBottom();
            ViewUtils.setLeftTopRightBottom(view, left, top, right, bottom);
        }

        @Override // android.util.Property
        public PointF get(View view) {
            return null;
        }
    };
    private static final Property<View, PointF> POSITION_PROPERTY = new Property<View, PointF>(PointF.class, "position") { // from class: android.support.transition.ChangeBounds.6
        @Override // android.util.Property
        public void set(View view, PointF topLeft) {
            int left = Math.round(topLeft.x);
            int top = Math.round(topLeft.y);
            int right = left + view.getWidth();
            int bottom = top + view.getHeight();
            ViewUtils.setLeftTopRightBottom(view, left, top, right, bottom);
        }

        @Override // android.util.Property
        public PointF get(View view) {
            return null;
        }
    };
    private static RectEvaluator sRectEvaluator = new RectEvaluator();
    private int[] mTempLocation = new int[2];
    private boolean mResizeClip = false;
    private boolean mReparent = false;

    @Override // android.support.transition.Transition
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            values.values.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            values.values.put("android:changeBounds:parent", values.view.getParent());
            if (this.mReparent) {
                values.view.getLocationInWindow(this.mTempLocation);
                values.values.put("android:changeBounds:windowX", Integer.valueOf(this.mTempLocation[0]));
                values.values.put("android:changeBounds:windowY", Integer.valueOf(this.mTempLocation[1]));
            }
            if (this.mResizeClip) {
                values.values.put("android:changeBounds:clip", ViewCompat.getClipBounds(view));
            }
        }
    }

    @Override // android.support.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.support.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private boolean parentMatches(View startParent, View endParent) {
        if (!this.mReparent) {
            return true;
        }
        TransitionValues endValues = getMatchedTransitionValues(startParent, true);
        if (endValues == null) {
            return startParent == endParent;
        }
        return endParent == endValues.view;
    }

    @Override // android.support.transition.Transition
    public Animator createAnimator(final ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Animator anim;
        if (startValues == null || endValues == null) {
            return null;
        }
        Map<String, Object> startParentVals = startValues.values;
        Map<String, Object> endParentVals = endValues.values;
        ViewGroup startParent = (ViewGroup) startParentVals.get("android:changeBounds:parent");
        ViewGroup endParent = (ViewGroup) endParentVals.get("android:changeBounds:parent");
        if (startParent == null || endParent == null) {
            return null;
        }
        final View view = endValues.view;
        if (parentMatches(startParent, endParent)) {
            Rect startBounds = (Rect) startValues.values.get("android:changeBounds:bounds");
            Rect endBounds = (Rect) endValues.values.get("android:changeBounds:bounds");
            int startLeft = startBounds.left;
            final int endLeft = endBounds.left;
            int startTop = startBounds.top;
            final int endTop = endBounds.top;
            int startRight = startBounds.right;
            final int endRight = endBounds.right;
            int startBottom = startBounds.bottom;
            final int endBottom = endBounds.bottom;
            int startWidth = startRight - startLeft;
            int startHeight = startBottom - startTop;
            int endWidth = endRight - endLeft;
            int endHeight = endBottom - endTop;
            Rect startClip = (Rect) startValues.values.get("android:changeBounds:clip");
            final Rect endClip = (Rect) endValues.values.get("android:changeBounds:clip");
            int numChanges = 0;
            if ((startWidth != 0 && startHeight != 0) || (endWidth != 0 && endHeight != 0)) {
                numChanges = (startLeft == endLeft && startTop == endTop) ? 0 : 0 + 1;
                if (startRight != endRight || startBottom != endBottom) {
                    numChanges++;
                }
            }
            if ((startClip != null && !startClip.equals(endClip)) || (startClip == null && endClip != null)) {
                numChanges++;
            }
            if (numChanges > 0) {
                if (!this.mResizeClip) {
                    ViewUtils.setLeftTopRightBottom(view, startLeft, startTop, startRight, startBottom);
                    if (numChanges == 2) {
                        if (startWidth == endWidth && startHeight == endHeight) {
                            Path topLeftPath = getPathMotion().getPath(startLeft, startTop, endLeft, endTop);
                            anim = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, topLeftPath);
                        } else {
                            final ViewBounds viewBounds = new ViewBounds(view);
                            Path topLeftPath2 = getPathMotion().getPath(startLeft, startTop, endLeft, endTop);
                            ObjectAnimator topLeftAnimator = ObjectAnimatorUtils.ofPointF(viewBounds, TOP_LEFT_PROPERTY, topLeftPath2);
                            Path bottomRightPath = getPathMotion().getPath(startRight, startBottom, endRight, endBottom);
                            ObjectAnimator bottomRightAnimator = ObjectAnimatorUtils.ofPointF(viewBounds, BOTTOM_RIGHT_PROPERTY, bottomRightPath);
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(topLeftAnimator, bottomRightAnimator);
                            anim = set;
                            set.addListener(new AnimatorListenerAdapter() { // from class: android.support.transition.ChangeBounds.7
                                private ViewBounds mViewBounds;

                                {
                                    this.mViewBounds = viewBounds;
                                }
                            });
                        }
                    } else if (startLeft != endLeft || startTop != endTop) {
                        Path topLeftPath3 = getPathMotion().getPath(startLeft, startTop, endLeft, endTop);
                        anim = ObjectAnimatorUtils.ofPointF(view, TOP_LEFT_ONLY_PROPERTY, topLeftPath3);
                    } else {
                        Path bottomRight = getPathMotion().getPath(startRight, startBottom, endRight, endBottom);
                        anim = ObjectAnimatorUtils.ofPointF(view, BOTTOM_RIGHT_ONLY_PROPERTY, bottomRight);
                    }
                } else {
                    int maxWidth = Math.max(startWidth, endWidth);
                    int maxHeight = Math.max(startHeight, endHeight);
                    ViewUtils.setLeftTopRightBottom(view, startLeft, startTop, startLeft + maxWidth, startTop + maxHeight);
                    ObjectAnimator positionAnimator = null;
                    if (startLeft != endLeft || startTop != endTop) {
                        Path topLeftPath4 = getPathMotion().getPath(startLeft, startTop, endLeft, endTop);
                        positionAnimator = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, topLeftPath4);
                    }
                    if (startClip == null) {
                        startClip = new Rect(0, 0, startWidth, startHeight);
                    }
                    if (endClip == null) {
                        endClip = new Rect(0, 0, endWidth, endHeight);
                    }
                    ObjectAnimator clipAnimator = null;
                    if (!startClip.equals(endClip)) {
                        ViewCompat.setClipBounds(view, startClip);
                        clipAnimator = ObjectAnimator.ofObject(view, "clipBounds", sRectEvaluator, startClip, endClip);
                        clipAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.support.transition.ChangeBounds.8
                            private boolean mIsCanceled;

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationCancel(Animator animation) {
                                this.mIsCanceled = true;
                            }

                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animation) {
                                if (!this.mIsCanceled) {
                                    ViewCompat.setClipBounds(view, endClip);
                                    ViewUtils.setLeftTopRightBottom(view, endLeft, endTop, endRight, endBottom);
                                }
                            }
                        });
                    }
                    anim = TransitionUtils.mergeAnimators(positionAnimator, clipAnimator);
                }
                if (view.getParent() instanceof ViewGroup) {
                    final ViewGroup parent = (ViewGroup) view.getParent();
                    ViewGroupUtils.suppressLayout(parent, true);
                    Transition.TransitionListener transitionListener = new TransitionListenerAdapter() { // from class: android.support.transition.ChangeBounds.9
                        boolean mCanceled = false;

                        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
                        public void onTransitionEnd(Transition transition) {
                            if (!this.mCanceled) {
                                ViewGroupUtils.suppressLayout(parent, false);
                            }
                            transition.removeListener(this);
                        }

                        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
                        public void onTransitionPause(Transition transition) {
                            ViewGroupUtils.suppressLayout(parent, false);
                        }

                        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
                        public void onTransitionResume(Transition transition) {
                            ViewGroupUtils.suppressLayout(parent, true);
                        }
                    };
                    addListener(transitionListener);
                    return anim;
                }
                return anim;
            }
        } else {
            int startX = ((Integer) startValues.values.get("android:changeBounds:windowX")).intValue();
            int startY = ((Integer) startValues.values.get("android:changeBounds:windowY")).intValue();
            int endX = ((Integer) endValues.values.get("android:changeBounds:windowX")).intValue();
            int endY = ((Integer) endValues.values.get("android:changeBounds:windowY")).intValue();
            if (startX != endX || startY != endY) {
                sceneRoot.getLocationInWindow(this.mTempLocation);
                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
                final BitmapDrawable drawable = new BitmapDrawable(bitmap);
                final float transitionAlpha = ViewUtils.getTransitionAlpha(view);
                ViewUtils.setTransitionAlpha(view, 0.0f);
                ViewUtils.getOverlay(sceneRoot).add(drawable);
                Path topLeftPath5 = getPathMotion().getPath(startX - this.mTempLocation[0], startY - this.mTempLocation[1], endX - this.mTempLocation[0], endY - this.mTempLocation[1]);
                PropertyValuesHolder origin = PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, topLeftPath5);
                ObjectAnimator anim2 = ObjectAnimator.ofPropertyValuesHolder(drawable, origin);
                anim2.addListener(new AnimatorListenerAdapter() { // from class: android.support.transition.ChangeBounds.10
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        ViewUtils.getOverlay(sceneRoot).remove(drawable);
                        ViewUtils.setTransitionAlpha(view, transitionAlpha);
                    }
                });
                return anim2;
            }
        }
        return null;
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        void setTopLeft(PointF topLeft) {
            this.mLeft = Math.round(topLeft.x);
            this.mTop = Math.round(topLeft.y);
            this.mTopLeftCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        void setBottomRight(PointF bottomRight) {
            this.mRight = Math.round(bottomRight.x);
            this.mBottom = Math.round(bottomRight.y);
            this.mBottomRightCalls++;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }
    }
}
