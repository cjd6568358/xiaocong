package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ConstraintWidget {
    public static float DEFAULT_BIAS = 0.5f;
    boolean mBottomHasCentered;
    private Object mCompanionWidget;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    boolean mHorizontalChainFixedPosition;
    boolean mHorizontalWrapVisited;
    boolean mLeftHasCentered;
    protected int mMinHeight;
    protected int mMinWidth;
    boolean mRightHasCentered;
    boolean mTopHasCentered;
    boolean mVerticalChainFixedPosition;
    boolean mVerticalWrapVisited;
    private int mWrapHeight;
    private int mWrapWidth;
    public int mHorizontalResolution = -1;
    public int mVerticalResolution = -1;
    int mMatchConstraintDefaultWidth = 0;
    int mMatchConstraintDefaultHeight = 0;
    int mMatchConstraintMinWidth = 0;
    int mMatchConstraintMaxWidth = 0;
    int mMatchConstraintMinHeight = 0;
    int mMatchConstraintMaxHeight = 0;
    ConstraintAnchor mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
    ConstraintAnchor mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
    ConstraintAnchor mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
    ConstraintAnchor mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
    ConstraintAnchor mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
    ConstraintAnchor mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
    ConstraintAnchor mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
    ConstraintAnchor mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
    protected ArrayList<ConstraintAnchor> mAnchors = new ArrayList<>();
    ConstraintWidget mParent = null;
    int mWidth = 0;
    int mHeight = 0;
    protected float mDimensionRatio = 0.0f;
    protected int mDimensionRatioSide = -1;
    private int mSolverLeft = 0;
    private int mSolverTop = 0;
    private int mSolverRight = 0;
    private int mSolverBottom = 0;
    protected int mX = 0;
    protected int mY = 0;
    private int mDrawX = 0;
    private int mDrawY = 0;
    private int mDrawWidth = 0;
    private int mDrawHeight = 0;
    protected int mOffsetX = 0;
    protected int mOffsetY = 0;
    int mBaselineDistance = 0;
    float mHorizontalBiasPercent = DEFAULT_BIAS;
    float mVerticalBiasPercent = DEFAULT_BIAS;
    DimensionBehaviour mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
    DimensionBehaviour mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
    private int mContainerItemSkip = 0;
    private int mVisibility = 0;
    private String mDebugName = null;
    private String mType = null;
    int mHorizontalChainStyle = 0;
    int mVerticalChainStyle = 0;
    float mHorizontalWeight = 0.0f;
    float mVerticalWeight = 0.0f;
    ConstraintWidget mHorizontalNextWidget = null;
    ConstraintWidget mVerticalNextWidget = null;

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mDrawX = 0;
        this.mDrawY = 0;
        this.mDrawWidth = 0;
        this.mDrawHeight = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mWrapWidth = 0;
        this.mWrapHeight = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        this.mHorizontalWeight = 0.0f;
        this.mVerticalWeight = 0.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
    }

    public ConstraintWidget() {
        addAnchors();
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mBaseline);
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public void setParent(ConstraintWidget widget) {
        this.mParent = widget;
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public String toString() {
        return (this.mType != null ? "type: " + this.mType + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : Constants.MAIN_VERSION_TAG) + (this.mDebugName != null ? "id: " + this.mDebugName + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR : Constants.MAIN_VERSION_TAG) + "(" + this.mX + ", " + this.mY + ") - (" + this.mWidth + " x " + this.mHeight + ") wrap: (" + this.mWrapWidth + " x " + this.mWrapHeight + ")";
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getOptimizerWrapWidth() {
        int w;
        int w2 = this.mWidth;
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            if (this.mMatchConstraintDefaultWidth == 1) {
                w = Math.max(this.mMatchConstraintMinWidth, w2);
            } else if (this.mMatchConstraintMinWidth > 0) {
                w = this.mMatchConstraintMinWidth;
                this.mWidth = w;
            } else {
                w = 0;
            }
            if (this.mMatchConstraintMaxWidth > 0 && this.mMatchConstraintMaxWidth < w) {
                return this.mMatchConstraintMaxWidth;
            }
            return w;
        }
        return w2;
    }

    public int getOptimizerWrapHeight() {
        int h;
        int h2 = this.mHeight;
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            if (this.mMatchConstraintDefaultHeight == 1) {
                h = Math.max(this.mMatchConstraintMinHeight, h2);
            } else if (this.mMatchConstraintMinHeight > 0) {
                h = this.mMatchConstraintMinHeight;
                this.mHeight = h;
            } else {
                h = 0;
            }
            if (this.mMatchConstraintMaxHeight > 0 && this.mMatchConstraintMaxHeight < h) {
                return this.mMatchConstraintMaxHeight;
            }
            return h;
        }
        return h2;
    }

    public int getWrapWidth() {
        return this.mWrapWidth;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public int getWrapHeight() {
        return this.mWrapHeight;
    }

    public int getDrawX() {
        return this.mDrawX + this.mOffsetX;
    }

    public int getDrawY() {
        return this.mDrawY + this.mOffsetY;
    }

    public int getDrawBottom() {
        return getDrawY() + this.mDrawHeight;
    }

    public int getDrawRight() {
        return getDrawX() + this.mDrawWidth;
    }

    protected int getRootX() {
        return this.mX + this.mOffsetX;
    }

    protected int getRootY() {
        return this.mY + this.mOffsetY;
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public boolean hasBaseline() {
        return this.mBaselineDistance > 0;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setX(int x) {
        this.mX = x;
    }

    public void setY(int y) {
        this.mY = y;
    }

    public void setOrigin(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public void setOffset(int x, int y) {
        this.mOffsetX = x;
        this.mOffsetY = y;
    }

    public void updateDrawPosition() {
        int left = this.mX;
        int top = this.mY;
        int right = this.mX + this.mWidth;
        int bottom = this.mY + this.mHeight;
        this.mDrawX = left;
        this.mDrawY = top;
        this.mDrawWidth = right - left;
        this.mDrawHeight = bottom - top;
    }

    public void setWidth(int w) {
        this.mWidth = w;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setHeight(int h) {
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setHorizontalMatchStyle(int horizontalMatchStyle, int min, int max) {
        this.mMatchConstraintDefaultWidth = horizontalMatchStyle;
        this.mMatchConstraintMinWidth = min;
        this.mMatchConstraintMaxWidth = max;
    }

    public void setVerticalMatchStyle(int verticalMatchStyle, int min, int max) {
        this.mMatchConstraintDefaultHeight = verticalMatchStyle;
        this.mMatchConstraintMinHeight = min;
        this.mMatchConstraintMaxHeight = max;
    }

    public void setDimensionRatio(String ratio) {
        int commaIndex;
        if (ratio == null || ratio.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int dimensionRatioSide = -1;
        float dimensionRatio = 0.0f;
        int len = ratio.length();
        int commaIndex2 = ratio.indexOf(44);
        if (commaIndex2 > 0 && commaIndex2 < len - 1) {
            String dimension = ratio.substring(0, commaIndex2);
            if (dimension.equalsIgnoreCase("W")) {
                dimensionRatioSide = 0;
            } else if (dimension.equalsIgnoreCase("H")) {
                dimensionRatioSide = 1;
            }
            commaIndex = commaIndex2 + 1;
        } else {
            commaIndex = 0;
        }
        int colonIndex = ratio.indexOf(58);
        if (colonIndex >= 0 && colonIndex < len - 1) {
            String nominator = ratio.substring(commaIndex, colonIndex);
            String denominator = ratio.substring(colonIndex + 1);
            if (nominator.length() > 0 && denominator.length() > 0) {
                try {
                    float nominatorValue = Float.parseFloat(nominator);
                    float denominatorValue = Float.parseFloat(denominator);
                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                        dimensionRatio = dimensionRatioSide == 1 ? Math.abs(denominatorValue / nominatorValue) : Math.abs(nominatorValue / denominatorValue);
                    }
                } catch (NumberFormatException e) {
                }
            }
        } else {
            String r = ratio.substring(commaIndex);
            if (r.length() > 0) {
                try {
                    dimensionRatio = Float.parseFloat(r);
                } catch (NumberFormatException e2) {
                }
            }
        }
        if (dimensionRatio > 0.0f) {
            this.mDimensionRatio = dimensionRatio;
            this.mDimensionRatioSide = dimensionRatioSide;
        }
    }

    public void setHorizontalBiasPercent(float horizontalBiasPercent) {
        this.mHorizontalBiasPercent = horizontalBiasPercent;
    }

    public void setVerticalBiasPercent(float verticalBiasPercent) {
        this.mVerticalBiasPercent = verticalBiasPercent;
    }

    public void setMinWidth(int w) {
        if (w < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = w;
        }
    }

    public void setMinHeight(int h) {
        if (h < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = h;
        }
    }

    public void setWrapWidth(int w) {
        this.mWrapWidth = w;
    }

    public void setWrapHeight(int h) {
        this.mWrapHeight = h;
    }

    public void setFrame(int left, int top, int right, int bottom) {
        int w = right - left;
        int h = bottom - top;
        this.mX = left;
        this.mY = top;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.FIXED && w < this.mWidth) {
            w = this.mWidth;
        }
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.FIXED && h < this.mHeight) {
            h = this.mHeight;
        }
        this.mWidth = w;
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setHorizontalDimension(int left, int right) {
        this.mX = left;
        this.mWidth = right - left;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setVerticalDimension(int top, int bottom) {
        this.mY = top;
        this.mHeight = bottom - top;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setBaselineDistance(int baseline) {
        this.mBaselineDistance = baseline;
    }

    public void setCompanionWidget(Object companion) {
        this.mCompanionWidget = companion;
    }

    public void setHorizontalWeight(float horizontalWeight) {
        this.mHorizontalWeight = horizontalWeight;
    }

    public void setVerticalWeight(float verticalWeight) {
        this.mVerticalWeight = verticalWeight;
    }

    public void setHorizontalChainStyle(int horizontalChainStyle) {
        this.mHorizontalChainStyle = horizontalChainStyle;
    }

    public void setVerticalChainStyle(int verticalChainStyle) {
        this.mVerticalChainStyle = verticalChainStyle;
    }

    public void immediateConnect(ConstraintAnchor.Type startType, ConstraintWidget target, ConstraintAnchor.Type endType, int margin, int goneMargin) {
        ConstraintAnchor startAnchor = getAnchor(startType);
        ConstraintAnchor endAnchor = target.getAnchor(endType);
        startAnchor.connect(endAnchor, margin, goneMargin, ConstraintAnchor.Strength.STRONG, 0, true);
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent != null && (parent instanceof ConstraintWidgetContainer)) {
            ConstraintWidgetContainer parentContainer = (ConstraintWidgetContainer) getParent();
            if (parentContainer.handlesInternalConstraints()) {
                return;
            }
        }
        int mAnchorsSize = this.mAnchors.size();
        for (int i = 0; i < mAnchorsSize; i++) {
            ConstraintAnchor anchor = this.mAnchors.get(i);
            anchor.reset();
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type anchorType) {
        switch (anchorType) {
            case LEFT:
                return this.mLeft;
            case TOP:
                return this.mTop;
            case RIGHT:
                return this.mRight;
            case BOTTOM:
                return this.mBottom;
            case BASELINE:
                return this.mBaseline;
            case CENTER_X:
                return this.mCenterX;
            case CENTER_Y:
                return this.mCenterY;
            case CENTER:
                return this.mCenter;
            default:
                return null;
        }
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mHorizontalDimensionBehaviour;
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mVerticalDimensionBehaviour;
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mHorizontalDimensionBehaviour = behaviour;
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            setWidth(this.mWrapWidth);
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mVerticalDimensionBehaviour = behaviour;
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            setHeight(this.mWrapHeight);
        }
    }

    public void addToSolver(LinearSystem system, int group) {
        SolverVariable left = null;
        SolverVariable right = null;
        SolverVariable top = null;
        SolverVariable bottom = null;
        SolverVariable baseline = null;
        if (group == Integer.MAX_VALUE || this.mLeft.mGroup == group) {
            left = system.createObjectVariable(this.mLeft);
        }
        if (group == Integer.MAX_VALUE || this.mRight.mGroup == group) {
            right = system.createObjectVariable(this.mRight);
        }
        if (group == Integer.MAX_VALUE || this.mTop.mGroup == group) {
            top = system.createObjectVariable(this.mTop);
        }
        if (group == Integer.MAX_VALUE || this.mBottom.mGroup == group) {
            bottom = system.createObjectVariable(this.mBottom);
        }
        if (group == Integer.MAX_VALUE || this.mBaseline.mGroup == group) {
            baseline = system.createObjectVariable(this.mBaseline);
        }
        boolean inHorizontalChain = false;
        boolean inVerticalChain = false;
        if (this.mParent != null) {
            if ((this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft) || (this.mRight.mTarget != null && this.mRight.mTarget.mTarget == this.mRight)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, 0);
                inHorizontalChain = true;
            }
            if ((this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) || (this.mBottom.mTarget != null && this.mBottom.mTarget.mTarget == this.mBottom)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, 1);
                inVerticalChain = true;
            }
            if (this.mParent.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT && !inHorizontalChain) {
                if (this.mLeft.mTarget == null || this.mLeft.mTarget.mOwner != this.mParent) {
                    SolverVariable parentLeft = system.createObjectVariable(this.mParent.mLeft);
                    ArrayRow row = system.createRow();
                    row.createRowGreaterThan(left, parentLeft, system.createSlackVariable(), 0);
                    system.addConstraint(row);
                } else if (this.mLeft.mTarget != null && this.mLeft.mTarget.mOwner == this.mParent) {
                    this.mLeft.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                }
                if (this.mRight.mTarget == null || this.mRight.mTarget.mOwner != this.mParent) {
                    SolverVariable parentRight = system.createObjectVariable(this.mParent.mRight);
                    ArrayRow row2 = system.createRow();
                    row2.createRowGreaterThan(parentRight, right, system.createSlackVariable(), 0);
                    system.addConstraint(row2);
                } else if (this.mRight.mTarget != null && this.mRight.mTarget.mOwner == this.mParent) {
                    this.mRight.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                }
            }
            if (this.mParent.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT && !inVerticalChain) {
                if (this.mTop.mTarget == null || this.mTop.mTarget.mOwner != this.mParent) {
                    SolverVariable parentTop = system.createObjectVariable(this.mParent.mTop);
                    ArrayRow row3 = system.createRow();
                    row3.createRowGreaterThan(top, parentTop, system.createSlackVariable(), 0);
                    system.addConstraint(row3);
                } else if (this.mTop.mTarget != null && this.mTop.mTarget.mOwner == this.mParent) {
                    this.mTop.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                }
                if (this.mBottom.mTarget == null || this.mBottom.mTarget.mOwner != this.mParent) {
                    SolverVariable parentBottom = system.createObjectVariable(this.mParent.mBottom);
                    ArrayRow row4 = system.createRow();
                    row4.createRowGreaterThan(parentBottom, bottom, system.createSlackVariable(), 0);
                    system.addConstraint(row4);
                } else if (this.mBottom.mTarget != null && this.mBottom.mTarget.mOwner == this.mParent) {
                    this.mBottom.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                }
            }
        }
        int width = this.mWidth;
        if (width < this.mMinWidth) {
            width = this.mMinWidth;
        }
        int height = this.mHeight;
        if (height < this.mMinHeight) {
            height = this.mMinHeight;
        }
        boolean horizontalDimensionFixed = this.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT;
        boolean verticalDimensionFixed = this.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT;
        if (!horizontalDimensionFixed && this.mLeft != null && this.mRight != null && (this.mLeft.mTarget == null || this.mRight.mTarget == null)) {
            horizontalDimensionFixed = true;
        }
        if (!verticalDimensionFixed && this.mTop != null && this.mBottom != null && ((this.mTop.mTarget == null || this.mBottom.mTarget == null) && (this.mBaselineDistance == 0 || (this.mBaseline != null && (this.mTop.mTarget == null || this.mBaseline.mTarget == null))))) {
            verticalDimensionFixed = true;
        }
        boolean useRatio = false;
        int dimensionRatioSide = this.mDimensionRatioSide;
        float dimensionRatio = this.mDimensionRatio;
        if (this.mDimensionRatio > 0.0f && this.mVisibility != 8) {
            if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                useRatio = true;
                if (horizontalDimensionFixed && !verticalDimensionFixed) {
                    dimensionRatioSide = 0;
                } else if (!horizontalDimensionFixed && verticalDimensionFixed) {
                    dimensionRatioSide = 1;
                    if (this.mDimensionRatioSide == -1) {
                        dimensionRatio = 1.0f / dimensionRatio;
                    }
                }
            } else if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                dimensionRatioSide = 0;
                width = (int) (this.mHeight * dimensionRatio);
                horizontalDimensionFixed = true;
            } else if (this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                dimensionRatioSide = 1;
                if (this.mDimensionRatioSide == -1) {
                    dimensionRatio = 1.0f / dimensionRatio;
                }
                height = (int) (this.mWidth * dimensionRatio);
                verticalDimensionFixed = true;
            }
        }
        boolean useHorizontalRatio = useRatio && (dimensionRatioSide == 0 || dimensionRatioSide == -1);
        boolean wrapContent = this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT && (this instanceof ConstraintWidgetContainer);
        if (this.mHorizontalResolution != 2 && (group == Integer.MAX_VALUE || (this.mLeft.mGroup == group && this.mRight.mGroup == group))) {
            if (useHorizontalRatio && this.mLeft.mTarget != null && this.mRight.mTarget != null) {
                SolverVariable begin = system.createObjectVariable(this.mLeft);
                SolverVariable end = system.createObjectVariable(this.mRight);
                SolverVariable beginTarget = system.createObjectVariable(this.mLeft.getTarget());
                SolverVariable endTarget = system.createObjectVariable(this.mRight.getTarget());
                system.addGreaterThan(begin, beginTarget, this.mLeft.getMargin(), 3);
                system.addLowerThan(end, endTarget, this.mRight.getMargin() * (-1), 3);
                if (!inHorizontalChain) {
                    system.addCentering(begin, beginTarget, this.mLeft.getMargin(), this.mHorizontalBiasPercent, endTarget, end, this.mRight.getMargin(), 4);
                }
            } else {
                applyConstraints(system, wrapContent, horizontalDimensionFixed, this.mLeft, this.mRight, this.mX, this.mX + width, width, this.mMinWidth, this.mHorizontalBiasPercent, useHorizontalRatio, inHorizontalChain, this.mMatchConstraintDefaultWidth, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth);
            }
        }
        if (this.mVerticalResolution != 2) {
            boolean wrapContent2 = this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT && (this instanceof ConstraintWidgetContainer);
            boolean useVerticalRatio = useRatio && (dimensionRatioSide == 1 || dimensionRatioSide == -1);
            if (this.mBaselineDistance > 0) {
                ConstraintAnchor endAnchor = this.mBottom;
                if (group == Integer.MAX_VALUE || (this.mBottom.mGroup == group && this.mBaseline.mGroup == group)) {
                    system.addEquality(baseline, top, getBaselineDistance(), 5);
                }
                int originalHeight = height;
                if (this.mBaseline.mTarget != null) {
                    height = this.mBaselineDistance;
                    endAnchor = this.mBaseline;
                }
                if (group == Integer.MAX_VALUE || (this.mTop.mGroup == group && endAnchor.mGroup == group)) {
                    if (useVerticalRatio && this.mTop.mTarget != null && this.mBottom.mTarget != null) {
                        SolverVariable begin2 = system.createObjectVariable(this.mTop);
                        SolverVariable end2 = system.createObjectVariable(this.mBottom);
                        SolverVariable beginTarget2 = system.createObjectVariable(this.mTop.getTarget());
                        SolverVariable endTarget2 = system.createObjectVariable(this.mBottom.getTarget());
                        system.addGreaterThan(begin2, beginTarget2, this.mTop.getMargin(), 3);
                        system.addLowerThan(end2, endTarget2, this.mBottom.getMargin() * (-1), 3);
                        if (!inVerticalChain) {
                            system.addCentering(begin2, beginTarget2, this.mTop.getMargin(), this.mVerticalBiasPercent, endTarget2, end2, this.mBottom.getMargin(), 4);
                        }
                    } else {
                        applyConstraints(system, wrapContent2, verticalDimensionFixed, this.mTop, endAnchor, this.mY, this.mY + height, height, this.mMinHeight, this.mVerticalBiasPercent, useVerticalRatio, inVerticalChain, this.mMatchConstraintDefaultHeight, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight);
                        system.addEquality(bottom, top, originalHeight, 5);
                    }
                }
            } else if (group == Integer.MAX_VALUE || (this.mTop.mGroup == group && this.mBottom.mGroup == group)) {
                if (useVerticalRatio && this.mTop.mTarget != null && this.mBottom.mTarget != null) {
                    SolverVariable begin3 = system.createObjectVariable(this.mTop);
                    SolverVariable end3 = system.createObjectVariable(this.mBottom);
                    SolverVariable beginTarget3 = system.createObjectVariable(this.mTop.getTarget());
                    SolverVariable endTarget3 = system.createObjectVariable(this.mBottom.getTarget());
                    system.addGreaterThan(begin3, beginTarget3, this.mTop.getMargin(), 3);
                    system.addLowerThan(end3, endTarget3, this.mBottom.getMargin() * (-1), 3);
                    if (!inVerticalChain) {
                        system.addCentering(begin3, beginTarget3, this.mTop.getMargin(), this.mVerticalBiasPercent, endTarget3, end3, this.mBottom.getMargin(), 4);
                    }
                } else {
                    applyConstraints(system, wrapContent2, verticalDimensionFixed, this.mTop, this.mBottom, this.mY, this.mY + height, height, this.mMinHeight, this.mVerticalBiasPercent, useVerticalRatio, inVerticalChain, this.mMatchConstraintDefaultHeight, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight);
                }
            }
            if (useRatio) {
                ArrayRow row5 = system.createRow();
                if (group == Integer.MAX_VALUE || (this.mLeft.mGroup == group && this.mRight.mGroup == group)) {
                    if (dimensionRatioSide == 0) {
                        system.addConstraint(row5.createRowDimensionRatio(right, left, bottom, top, dimensionRatio));
                        return;
                    }
                    if (dimensionRatioSide == 1) {
                        system.addConstraint(row5.createRowDimensionRatio(bottom, top, right, left, dimensionRatio));
                        return;
                    }
                    if (this.mMatchConstraintMinWidth > 0) {
                        system.addGreaterThan(right, left, this.mMatchConstraintMinWidth, 3);
                    }
                    if (this.mMatchConstraintMinHeight > 0) {
                        system.addGreaterThan(bottom, top, this.mMatchConstraintMinHeight, 3);
                    }
                    row5.createRowDimensionRatio(right, left, bottom, top, dimensionRatio);
                    SolverVariable error1 = system.createErrorVariable();
                    SolverVariable error2 = system.createErrorVariable();
                    error1.strength = 4;
                    error2.strength = 4;
                    row5.addError(error1, error2);
                    system.addConstraint(row5);
                }
            }
        }
    }

    private void applyConstraints(LinearSystem system, boolean wrapContent, boolean dimensionFixed, ConstraintAnchor beginAnchor, ConstraintAnchor endAnchor, int beginPosition, int endPosition, int dimension, int minDimension, float bias, boolean useRatio, boolean inChain, int matchConstraintDefault, int matchMinDimension, int matchMaxDimension) {
        SolverVariable begin = system.createObjectVariable(beginAnchor);
        SolverVariable end = system.createObjectVariable(endAnchor);
        SolverVariable beginTarget = system.createObjectVariable(beginAnchor.getTarget());
        SolverVariable endTarget = system.createObjectVariable(endAnchor.getTarget());
        int beginAnchorMargin = beginAnchor.getMargin();
        int endAnchorMargin = endAnchor.getMargin();
        if (this.mVisibility == 8) {
            dimension = 0;
            dimensionFixed = true;
        }
        if (beginTarget == null && endTarget == null) {
            system.addConstraint(system.createRow().createRowEquals(begin, beginPosition));
            if (!useRatio) {
                if (wrapContent) {
                    system.addConstraint(LinearSystem.createRowEquals(system, end, begin, minDimension, true));
                    return;
                } else if (dimensionFixed) {
                    system.addConstraint(LinearSystem.createRowEquals(system, end, begin, dimension, false));
                    return;
                } else {
                    system.addConstraint(system.createRow().createRowEquals(end, endPosition));
                    return;
                }
            }
            return;
        }
        if (beginTarget != null && endTarget == null) {
            system.addConstraint(system.createRow().createRowEquals(begin, beginTarget, beginAnchorMargin));
            if (wrapContent) {
                system.addConstraint(LinearSystem.createRowEquals(system, end, begin, minDimension, true));
                return;
            } else {
                if (!useRatio) {
                    if (dimensionFixed) {
                        system.addConstraint(system.createRow().createRowEquals(end, begin, dimension));
                        return;
                    } else {
                        system.addConstraint(system.createRow().createRowEquals(end, endPosition));
                        return;
                    }
                }
                return;
            }
        }
        if (beginTarget == null && endTarget != null) {
            system.addConstraint(system.createRow().createRowEquals(end, endTarget, endAnchorMargin * (-1)));
            if (wrapContent) {
                system.addConstraint(LinearSystem.createRowEquals(system, end, begin, minDimension, true));
                return;
            } else {
                if (!useRatio) {
                    if (dimensionFixed) {
                        system.addConstraint(system.createRow().createRowEquals(end, begin, dimension));
                        return;
                    } else {
                        system.addConstraint(system.createRow().createRowEquals(begin, beginPosition));
                        return;
                    }
                }
                return;
            }
        }
        if (dimensionFixed) {
            if (wrapContent) {
                system.addConstraint(LinearSystem.createRowEquals(system, end, begin, minDimension, true));
            } else {
                system.addConstraint(system.createRow().createRowEquals(end, begin, dimension));
            }
            if (beginAnchor.getStrength() != endAnchor.getStrength()) {
                if (beginAnchor.getStrength() == ConstraintAnchor.Strength.STRONG) {
                    system.addConstraint(system.createRow().createRowEquals(begin, beginTarget, beginAnchorMargin));
                    SolverVariable slack = system.createSlackVariable();
                    ArrayRow row = system.createRow();
                    row.createRowLowerThan(end, endTarget, slack, endAnchorMargin * (-1));
                    system.addConstraint(row);
                    return;
                }
                SolverVariable slack2 = system.createSlackVariable();
                ArrayRow row2 = system.createRow();
                row2.createRowGreaterThan(begin, beginTarget, slack2, beginAnchorMargin);
                system.addConstraint(row2);
                system.addConstraint(system.createRow().createRowEquals(end, endTarget, endAnchorMargin * (-1)));
                return;
            }
            if (beginTarget == endTarget) {
                system.addConstraint(LinearSystem.createRowCentering(system, begin, beginTarget, 0, 0.5f, endTarget, end, 0, true));
                return;
            }
            if (!inChain) {
                boolean useBidirectionalError = beginAnchor.getConnectionType() != ConstraintAnchor.ConnectionType.STRICT;
                system.addConstraint(LinearSystem.createRowGreaterThan(system, begin, beginTarget, beginAnchorMargin, useBidirectionalError));
                boolean useBidirectionalError2 = endAnchor.getConnectionType() != ConstraintAnchor.ConnectionType.STRICT;
                system.addConstraint(LinearSystem.createRowLowerThan(system, end, endTarget, endAnchorMargin * (-1), useBidirectionalError2));
                system.addConstraint(LinearSystem.createRowCentering(system, begin, beginTarget, beginAnchorMargin, bias, endTarget, end, endAnchorMargin, false));
                return;
            }
            return;
        }
        if (useRatio) {
            system.addGreaterThan(begin, beginTarget, beginAnchorMargin, 3);
            system.addLowerThan(end, endTarget, endAnchorMargin * (-1), 3);
            system.addConstraint(LinearSystem.createRowCentering(system, begin, beginTarget, beginAnchorMargin, bias, endTarget, end, endAnchorMargin, true));
            return;
        }
        if (!inChain) {
            if (matchConstraintDefault == 1) {
                if (matchMinDimension > dimension) {
                    dimension = matchMinDimension;
                }
                if (matchMaxDimension > 0) {
                    if (matchMaxDimension < dimension) {
                        dimension = matchMaxDimension;
                    } else {
                        system.addLowerThan(end, begin, matchMaxDimension, 3);
                    }
                }
                system.addEquality(end, begin, dimension, 3);
                system.addGreaterThan(begin, beginTarget, beginAnchorMargin, 2);
                system.addLowerThan(end, endTarget, -endAnchorMargin, 2);
                system.addCentering(begin, beginTarget, beginAnchorMargin, bias, endTarget, end, endAnchorMargin, 4);
                return;
            }
            if (matchMinDimension == 0 && matchMaxDimension == 0) {
                system.addConstraint(system.createRow().createRowEquals(begin, beginTarget, beginAnchorMargin));
                system.addConstraint(system.createRow().createRowEquals(end, endTarget, endAnchorMargin * (-1)));
                return;
            }
            if (matchMaxDimension > 0) {
                system.addLowerThan(end, begin, matchMaxDimension, 3);
            }
            system.addGreaterThan(begin, beginTarget, beginAnchorMargin, 2);
            system.addLowerThan(end, endTarget, -endAnchorMargin, 2);
            system.addCentering(begin, beginTarget, beginAnchorMargin, bias, endTarget, end, endAnchorMargin, 4);
        }
    }

    public void updateFromSolver(LinearSystem system, int group) {
        if (group == Integer.MAX_VALUE) {
            int left = system.getObjectVariableValue(this.mLeft);
            int top = system.getObjectVariableValue(this.mTop);
            int right = system.getObjectVariableValue(this.mRight);
            int bottom = system.getObjectVariableValue(this.mBottom);
            setFrame(left, top, right, bottom);
            return;
        }
        if (group == -2) {
            setFrame(this.mSolverLeft, this.mSolverTop, this.mSolverRight, this.mSolverBottom);
            return;
        }
        if (this.mLeft.mGroup == group) {
            this.mSolverLeft = system.getObjectVariableValue(this.mLeft);
        }
        if (this.mTop.mGroup == group) {
            this.mSolverTop = system.getObjectVariableValue(this.mTop);
        }
        if (this.mRight.mGroup == group) {
            this.mSolverRight = system.getObjectVariableValue(this.mRight);
        }
        if (this.mBottom.mGroup == group) {
            this.mSolverBottom = system.getObjectVariableValue(this.mBottom);
        }
    }
}
