package android.support.constraint;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.View;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ConstraintSet {
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private static SparseIntArray mapToConstant = new SparseIntArray();
    private HashMap<Integer, Constraint> mConstraints = new HashMap<>();

    static {
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintLeft_toLeftOf, 25);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintLeft_toRightOf, 26);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintRight_toLeftOf, 29);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintRight_toRightOf, 30);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintTop_toTopOf, 36);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintTop_toBottomOf, 35);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBottom_toTopOf, 4);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBottom_toBottomOf, 3);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBaseline_toBaselineOf, 1);
        mapToConstant.append(R.styleable.ConstraintSet_layout_editor_absoluteX, 6);
        mapToConstant.append(R.styleable.ConstraintSet_layout_editor_absoluteY, 7);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintGuide_begin, 17);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintGuide_end, 18);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintGuide_percent, 19);
        mapToConstant.append(R.styleable.ConstraintSet_android_orientation, 27);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintStart_toEndOf, 32);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintStart_toStartOf, 33);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintEnd_toStartOf, 10);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintEnd_toEndOf, 9);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginLeft, 13);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginTop, 16);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginRight, 14);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginBottom, 11);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginStart, 15);
        mapToConstant.append(R.styleable.ConstraintSet_layout_goneMarginEnd, 12);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintVertical_weight, 40);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHorizontal_weight, 39);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHorizontal_chainStyle, 41);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintVertical_chainStyle, 42);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHorizontal_bias, 20);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintVertical_bias, 37);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintDimensionRatio, 5);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintLeft_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintTop_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintRight_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBottom_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintBaseline_creator, 60);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginLeft, 24);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginRight, 28);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginStart, 31);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginEnd, 8);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginTop, 34);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_marginBottom, 2);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_width, 23);
        mapToConstant.append(R.styleable.ConstraintSet_android_layout_height, 21);
        mapToConstant.append(R.styleable.ConstraintSet_android_visibility, 22);
        mapToConstant.append(R.styleable.ConstraintSet_android_alpha, 43);
        mapToConstant.append(R.styleable.ConstraintSet_android_elevation, 44);
        mapToConstant.append(R.styleable.ConstraintSet_android_rotationX, 45);
        mapToConstant.append(R.styleable.ConstraintSet_android_rotationY, 46);
        mapToConstant.append(R.styleable.ConstraintSet_android_scaleX, 47);
        mapToConstant.append(R.styleable.ConstraintSet_android_scaleY, 48);
        mapToConstant.append(R.styleable.ConstraintSet_android_transformPivotX, 49);
        mapToConstant.append(R.styleable.ConstraintSet_android_transformPivotY, 50);
        mapToConstant.append(R.styleable.ConstraintSet_android_translationX, 51);
        mapToConstant.append(R.styleable.ConstraintSet_android_translationY, 52);
        mapToConstant.append(R.styleable.ConstraintSet_android_translationZ, 53);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintWidth_default, 54);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHeight_default, 55);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintWidth_max, 56);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHeight_max, 57);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintWidth_min, 58);
        mapToConstant.append(R.styleable.ConstraintSet_layout_constraintHeight_min, 59);
        mapToConstant.append(R.styleable.ConstraintSet_android_id, 38);
    }

    private static class Constraint {
        public float alpha;
        public boolean applyElevation;
        public int baselineToBaseline;
        public int bottomMargin;
        public int bottomToBottom;
        public int bottomToTop;
        public String dimensionRatio;
        public int editorAbsoluteX;
        public int editorAbsoluteY;
        public float elevation;
        public int endMargin;
        public int endToEnd;
        public int endToStart;
        public int goneBottomMargin;
        public int goneEndMargin;
        public int goneLeftMargin;
        public int goneRightMargin;
        public int goneStartMargin;
        public int goneTopMargin;
        public int guideBegin;
        public int guideEnd;
        public float guidePercent;
        public int heightDefault;
        public int heightMax;
        public int heightMin;
        public float horizontalBias;
        public int horizontalChainStyle;
        public float horizontalWeight;
        public int leftMargin;
        public int leftToLeft;
        public int leftToRight;
        public int mHeight;
        boolean mIsGuideline;
        int mViewId;
        public int mWidth;
        public int orientation;
        public int rightMargin;
        public int rightToLeft;
        public int rightToRight;
        public float rotationX;
        public float rotationY;
        public float scaleX;
        public float scaleY;
        public int startMargin;
        public int startToEnd;
        public int startToStart;
        public int topMargin;
        public int topToBottom;
        public int topToTop;
        public float transformPivotX;
        public float transformPivotY;
        public float translationX;
        public float translationY;
        public float translationZ;
        public float verticalBias;
        public int verticalChainStyle;
        public float verticalWeight;
        public int visibility;
        public int widthDefault;
        public int widthMax;
        public int widthMin;

        private Constraint() {
            this.mIsGuideline = false;
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.leftMargin = -1;
            this.rightMargin = -1;
            this.topMargin = -1;
            this.bottomMargin = -1;
            this.endMargin = -1;
            this.startMargin = -1;
            this.visibility = 0;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneEndMargin = -1;
            this.goneStartMargin = -1;
            this.verticalWeight = 0.0f;
            this.horizontalWeight = 0.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.alpha = 1.0f;
            this.applyElevation = false;
            this.elevation = 0.0f;
            this.rotationX = 0.0f;
            this.rotationY = 0.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.transformPivotX = 0.0f;
            this.transformPivotY = 0.0f;
            this.translationX = 0.0f;
            this.translationY = 0.0f;
            this.translationZ = 0.0f;
            this.widthDefault = -1;
            this.heightDefault = -1;
            this.widthMax = -1;
            this.heightMax = -1;
            this.widthMin = -1;
            this.heightMin = -1;
        }

        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Constraint m0clone() {
            Constraint clone = new Constraint();
            clone.mIsGuideline = this.mIsGuideline;
            clone.mWidth = this.mWidth;
            clone.mHeight = this.mHeight;
            clone.guideBegin = this.guideBegin;
            clone.guideEnd = this.guideEnd;
            clone.guidePercent = this.guidePercent;
            clone.leftToLeft = this.leftToLeft;
            clone.leftToRight = this.leftToRight;
            clone.rightToLeft = this.rightToLeft;
            clone.rightToRight = this.rightToRight;
            clone.topToTop = this.topToTop;
            clone.topToBottom = this.topToBottom;
            clone.bottomToTop = this.bottomToTop;
            clone.bottomToBottom = this.bottomToBottom;
            clone.baselineToBaseline = this.baselineToBaseline;
            clone.startToEnd = this.startToEnd;
            clone.startToStart = this.startToStart;
            clone.endToStart = this.endToStart;
            clone.endToEnd = this.endToEnd;
            clone.horizontalBias = this.horizontalBias;
            clone.verticalBias = this.verticalBias;
            clone.dimensionRatio = this.dimensionRatio;
            clone.editorAbsoluteX = this.editorAbsoluteX;
            clone.editorAbsoluteY = this.editorAbsoluteY;
            clone.horizontalBias = this.horizontalBias;
            clone.horizontalBias = this.horizontalBias;
            clone.horizontalBias = this.horizontalBias;
            clone.horizontalBias = this.horizontalBias;
            clone.horizontalBias = this.horizontalBias;
            clone.orientation = this.orientation;
            clone.leftMargin = this.leftMargin;
            clone.rightMargin = this.rightMargin;
            clone.topMargin = this.topMargin;
            clone.bottomMargin = this.bottomMargin;
            clone.endMargin = this.endMargin;
            clone.startMargin = this.startMargin;
            clone.visibility = this.visibility;
            clone.goneLeftMargin = this.goneLeftMargin;
            clone.goneTopMargin = this.goneTopMargin;
            clone.goneRightMargin = this.goneRightMargin;
            clone.goneBottomMargin = this.goneBottomMargin;
            clone.goneEndMargin = this.goneEndMargin;
            clone.goneStartMargin = this.goneStartMargin;
            clone.verticalWeight = this.verticalWeight;
            clone.horizontalWeight = this.horizontalWeight;
            clone.horizontalChainStyle = this.horizontalChainStyle;
            clone.verticalChainStyle = this.verticalChainStyle;
            clone.alpha = this.alpha;
            clone.applyElevation = this.applyElevation;
            clone.elevation = this.elevation;
            clone.rotationX = this.rotationX;
            clone.rotationY = this.rotationY;
            clone.scaleX = this.scaleX;
            clone.scaleY = this.scaleY;
            clone.transformPivotX = this.transformPivotX;
            clone.transformPivotY = this.transformPivotY;
            clone.translationX = this.translationX;
            clone.translationY = this.translationY;
            clone.translationZ = this.translationZ;
            clone.widthDefault = this.widthDefault;
            clone.heightDefault = this.heightDefault;
            clone.widthMax = this.widthMax;
            clone.heightMax = this.heightMax;
            clone.widthMin = this.widthMin;
            clone.heightMin = this.heightMin;
            return clone;
        }

        public void applyTo(ConstraintLayout.LayoutParams param) {
            param.leftToLeft = this.leftToLeft;
            param.leftToRight = this.leftToRight;
            param.rightToLeft = this.rightToLeft;
            param.rightToRight = this.rightToRight;
            param.topToTop = this.topToTop;
            param.topToBottom = this.topToBottom;
            param.bottomToTop = this.bottomToTop;
            param.bottomToBottom = this.bottomToBottom;
            param.baselineToBaseline = this.baselineToBaseline;
            param.startToEnd = this.startToEnd;
            param.startToStart = this.startToStart;
            param.endToStart = this.endToStart;
            param.endToEnd = this.endToEnd;
            param.leftMargin = this.leftMargin;
            param.rightMargin = this.rightMargin;
            param.topMargin = this.topMargin;
            param.bottomMargin = this.bottomMargin;
            param.goneStartMargin = this.goneStartMargin;
            param.goneEndMargin = this.goneEndMargin;
            param.horizontalBias = this.horizontalBias;
            param.verticalBias = this.verticalBias;
            param.dimensionRatio = this.dimensionRatio;
            param.editorAbsoluteX = this.editorAbsoluteX;
            param.editorAbsoluteY = this.editorAbsoluteY;
            param.verticalWeight = this.verticalWeight;
            param.horizontalWeight = this.horizontalWeight;
            param.verticalChainStyle = this.verticalChainStyle;
            param.horizontalChainStyle = this.horizontalChainStyle;
            param.matchConstraintDefaultWidth = this.widthDefault;
            param.matchConstraintDefaultHeight = this.heightDefault;
            param.matchConstraintMaxWidth = this.widthMax;
            param.matchConstraintMaxHeight = this.heightMax;
            param.matchConstraintMinWidth = this.widthMin;
            param.matchConstraintMinHeight = this.heightMin;
            param.orientation = this.orientation;
            param.guidePercent = this.guidePercent;
            param.guideBegin = this.guideBegin;
            param.guideEnd = this.guideEnd;
            param.width = this.mWidth;
            param.height = this.mHeight;
            if (Build.VERSION.SDK_INT >= 17) {
                param.setMarginStart(this.startMargin);
                param.setMarginEnd(this.endMargin);
            }
            param.validate();
        }
    }

    void applyToInternal(ConstraintLayout constraintLayout) {
        int count = constraintLayout.getChildCount();
        HashSet<Integer> used = new HashSet<>(this.mConstraints.keySet());
        for (int i = 0; i < count; i++) {
            View view = constraintLayout.getChildAt(i);
            int id = view.getId();
            if (this.mConstraints.containsKey(Integer.valueOf(id))) {
                used.remove(Integer.valueOf(id));
                Constraint constraint = this.mConstraints.get(Integer.valueOf(id));
                ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                constraint.applyTo(param);
                view.setLayoutParams(param);
                view.setVisibility(constraint.visibility);
                if (Build.VERSION.SDK_INT >= 17) {
                    view.setAlpha(constraint.alpha);
                    view.setRotationX(constraint.rotationX);
                    view.setRotationY(constraint.rotationY);
                    view.setScaleX(constraint.scaleX);
                    view.setScaleY(constraint.scaleY);
                    view.setPivotX(constraint.transformPivotX);
                    view.setPivotY(constraint.transformPivotY);
                    view.setTranslationX(constraint.translationX);
                    view.setTranslationY(constraint.translationY);
                    if (Build.VERSION.SDK_INT >= 21) {
                        view.setTranslationZ(constraint.translationZ);
                        if (constraint.applyElevation) {
                            view.setElevation(constraint.elevation);
                        }
                    }
                }
            }
        }
        for (Integer id2 : used) {
            Constraint constraint2 = this.mConstraints.get(id2);
            if (constraint2.mIsGuideline) {
                Guideline g = new Guideline(constraintLayout.getContext());
                g.setId(id2.intValue());
                ConstraintLayout.LayoutParams param2 = constraintLayout.generateDefaultLayoutParams();
                constraint2.applyTo(param2);
                constraintLayout.addView(g, param2);
            }
        }
    }

    public void load(Context context, int resourceId) {
        Resources res = context.getResources();
        XmlPullParser parser = res.getXml(resourceId);
        try {
            for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                switch (eventType) {
                    case 0:
                        parser.getName();
                        break;
                    case 2:
                        String tagName = parser.getName();
                        Constraint constraint = fillFromAttributeList(context, Xml.asAttributeSet(parser));
                        if (tagName.equalsIgnoreCase("Guideline")) {
                            constraint.mIsGuideline = true;
                        }
                        this.mConstraints.put(Integer.valueOf(constraint.mViewId), constraint);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    private static int lookupID(TypedArray a, int index, int def) {
        int ret = a.getResourceId(index, def);
        if (ret == -1) {
            return a.getInt(index, -1);
        }
        return ret;
    }

    private Constraint fillFromAttributeList(Context context, AttributeSet attrs) {
        Constraint c = new Constraint();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConstraintSet);
        populateConstraint(c, a);
        a.recycle();
        return c;
    }

    private void populateConstraint(Constraint c, TypedArray a) {
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (mapToConstant.get(attr)) {
                case 1:
                    c.baselineToBaseline = lookupID(a, attr, c.baselineToBaseline);
                    break;
                case 2:
                    c.bottomMargin = a.getDimensionPixelSize(attr, c.bottomMargin);
                    break;
                case 3:
                    c.bottomToBottom = lookupID(a, attr, c.bottomToBottom);
                    break;
                case 4:
                    c.bottomToTop = lookupID(a, attr, c.bottomToTop);
                    break;
                case 5:
                    c.dimensionRatio = a.getString(attr);
                    break;
                case 6:
                    c.editorAbsoluteX = a.getDimensionPixelOffset(attr, c.editorAbsoluteX);
                    break;
                case 7:
                    c.editorAbsoluteY = a.getDimensionPixelOffset(attr, c.editorAbsoluteY);
                    break;
                case 8:
                    c.endMargin = a.getDimensionPixelSize(attr, c.endMargin);
                    break;
                case 9:
                    c.bottomToTop = lookupID(a, attr, c.endToEnd);
                    break;
                case 10:
                    c.endToStart = lookupID(a, attr, c.endToStart);
                    break;
                case 11:
                    c.goneBottomMargin = a.getDimensionPixelSize(attr, c.goneBottomMargin);
                    break;
                case 12:
                    c.goneEndMargin = a.getDimensionPixelSize(attr, c.goneEndMargin);
                    break;
                case 13:
                    c.goneLeftMargin = a.getDimensionPixelSize(attr, c.goneLeftMargin);
                    break;
                case 14:
                    c.goneRightMargin = a.getDimensionPixelSize(attr, c.goneRightMargin);
                    break;
                case 15:
                    c.goneStartMargin = a.getDimensionPixelSize(attr, c.goneStartMargin);
                    break;
                case 16:
                    c.goneTopMargin = a.getDimensionPixelSize(attr, c.goneTopMargin);
                    break;
                case 17:
                    c.guideBegin = a.getDimensionPixelOffset(attr, c.guideBegin);
                    break;
                case 18:
                    c.guideEnd = a.getDimensionPixelOffset(attr, c.guideEnd);
                    break;
                case 19:
                    c.guidePercent = a.getFloat(attr, c.guidePercent);
                    break;
                case 20:
                    c.horizontalBias = a.getFloat(attr, c.horizontalBias);
                    break;
                case 21:
                    c.mHeight = a.getLayoutDimension(attr, c.mHeight);
                    break;
                case 22:
                    c.visibility = a.getInt(attr, c.visibility);
                    c.visibility = VISIBILITY_FLAGS[c.visibility];
                    break;
                case 23:
                    c.mWidth = a.getLayoutDimension(attr, c.mWidth);
                    break;
                case 24:
                    c.leftMargin = a.getDimensionPixelSize(attr, c.leftMargin);
                    break;
                case 25:
                    c.leftToLeft = lookupID(a, attr, c.leftToLeft);
                    break;
                case 26:
                    c.leftToRight = lookupID(a, attr, c.leftToRight);
                    break;
                case 27:
                    c.orientation = a.getInt(attr, c.orientation);
                    break;
                case 28:
                    c.rightMargin = a.getDimensionPixelSize(attr, c.rightMargin);
                    break;
                case 29:
                    c.rightToLeft = lookupID(a, attr, c.rightToLeft);
                    break;
                case 30:
                    c.rightToRight = lookupID(a, attr, c.rightToRight);
                    break;
                case 31:
                    c.startMargin = a.getDimensionPixelSize(attr, c.startMargin);
                    break;
                case 32:
                    c.startToEnd = lookupID(a, attr, c.startToEnd);
                    break;
                case 33:
                    c.startToStart = lookupID(a, attr, c.startToStart);
                    break;
                case 34:
                    c.topMargin = a.getDimensionPixelSize(attr, c.topMargin);
                    break;
                case 35:
                    c.topToBottom = lookupID(a, attr, c.topToBottom);
                    break;
                case 36:
                    c.topToTop = lookupID(a, attr, c.topToTop);
                    break;
                case 37:
                    c.verticalBias = a.getFloat(attr, c.verticalBias);
                    break;
                case 38:
                    c.mViewId = a.getResourceId(attr, c.mViewId);
                    break;
                case 39:
                    c.horizontalWeight = a.getFloat(attr, c.horizontalWeight);
                    break;
                case 40:
                    c.verticalWeight = a.getFloat(attr, c.verticalWeight);
                    break;
                case 41:
                    c.horizontalChainStyle = a.getInt(attr, c.horizontalChainStyle);
                    break;
                case 42:
                    c.verticalChainStyle = a.getInt(attr, c.verticalChainStyle);
                    break;
                case 43:
                    c.alpha = a.getFloat(attr, c.alpha);
                    break;
                case 44:
                    c.applyElevation = true;
                    c.elevation = a.getFloat(attr, c.elevation);
                    break;
                case 45:
                    c.rotationX = a.getFloat(attr, c.rotationX);
                    break;
                case 46:
                    c.rotationY = a.getFloat(attr, c.rotationY);
                    break;
                case 47:
                    c.scaleX = a.getFloat(attr, c.scaleX);
                    break;
                case 48:
                    c.scaleY = a.getFloat(attr, c.scaleY);
                    break;
                case 49:
                    c.transformPivotX = a.getFloat(attr, c.transformPivotX);
                    break;
                case 50:
                    c.transformPivotY = a.getFloat(attr, c.transformPivotY);
                    break;
                case 51:
                    c.translationX = a.getFloat(attr, c.translationX);
                    break;
                case 52:
                    c.translationY = a.getFloat(attr, c.translationY);
                    break;
                case 53:
                    c.translationZ = a.getFloat(attr, c.translationZ);
                    break;
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                default:
                    Log.w("ConstraintSet", "Unknown attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                    break;
                case 60:
                    Log.w("ConstraintSet", "unused attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                    break;
            }
        }
    }
}
