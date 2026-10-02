package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;
import com.tencent.android.tpush.common.Constants;
import java.util.HashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ConstraintAnchor {
    final ConstraintWidget mOwner;
    SolverVariable mSolverVariable;
    ConstraintAnchor mTarget;
    final Type mType;
    public int mMargin = 0;
    int mGoneMargin = -1;
    private Strength mStrength = Strength.NONE;
    private ConnectionType mConnectionType = ConnectionType.RELAXED;
    private int mConnectionCreator = 0;
    int mGroup = Integer.MAX_VALUE;

    public enum ConnectionType {
        RELAXED,
        STRICT
    }

    public enum Strength {
        NONE,
        STRONG,
        WEAK
    }

    public enum Type {
        NONE,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y
    }

    public ConstraintAnchor(ConstraintWidget owner, Type type) {
        this.mOwner = owner;
        this.mType = type;
    }

    public SolverVariable getSolverVariable() {
        return this.mSolverVariable;
    }

    public void resetSolverVariable(Cache cache) {
        if (this.mSolverVariable == null) {
            this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED);
        } else {
            this.mSolverVariable.reset();
        }
    }

    public ConstraintWidget getOwner() {
        return this.mOwner;
    }

    public Type getType() {
        return this.mType;
    }

    public int getMargin() {
        if (this.mOwner.getVisibility() == 8) {
            return 0;
        }
        if (this.mGoneMargin > -1 && this.mTarget != null && this.mTarget.mOwner.getVisibility() == 8) {
            return this.mGoneMargin;
        }
        return this.mMargin;
    }

    public Strength getStrength() {
        return this.mStrength;
    }

    public ConstraintAnchor getTarget() {
        return this.mTarget;
    }

    public ConnectionType getConnectionType() {
        return this.mConnectionType;
    }

    public void setConnectionType(ConnectionType type) {
        this.mConnectionType = type;
    }

    public int getConnectionCreator() {
        return this.mConnectionCreator;
    }

    public void reset() {
        this.mTarget = null;
        this.mMargin = 0;
        this.mGoneMargin = -1;
        this.mStrength = Strength.STRONG;
        this.mConnectionCreator = 0;
        this.mConnectionType = ConnectionType.RELAXED;
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin, Strength strength, int creator) {
        return connect(toAnchor, margin, -1, strength, creator, false);
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin, int goneMargin, Strength strength, int creator, boolean forceConnection) {
        if (toAnchor == null) {
            this.mTarget = null;
            this.mMargin = 0;
            this.mGoneMargin = -1;
            this.mStrength = Strength.NONE;
            this.mConnectionCreator = 2;
            return true;
        }
        if (!forceConnection && !isValidConnection(toAnchor)) {
            return false;
        }
        this.mTarget = toAnchor;
        if (margin > 0) {
            this.mMargin = margin;
        } else {
            this.mMargin = 0;
        }
        this.mGoneMargin = goneMargin;
        this.mStrength = strength;
        this.mConnectionCreator = creator;
        return true;
    }

    public boolean isConnected() {
        return this.mTarget != null;
    }

    public boolean isValidConnection(ConstraintAnchor anchor) {
        if (anchor == null) {
            return false;
        }
        Type target = anchor.getType();
        if (target == this.mType) {
            if (this.mType != Type.CENTER) {
                return this.mType != Type.BASELINE || (anchor.getOwner().hasBaseline() && getOwner().hasBaseline());
            }
            return false;
        }
        switch (this.mType) {
            case CENTER:
                return (target == Type.BASELINE || target == Type.CENTER_X || target == Type.CENTER_Y) ? false : true;
            case LEFT:
            case RIGHT:
                boolean isCompatible = target == Type.LEFT || target == Type.RIGHT;
                if (anchor.getOwner() instanceof Guideline) {
                    isCompatible = isCompatible || target == Type.CENTER_X;
                }
                return isCompatible;
            case TOP:
            case BOTTOM:
                boolean isCompatible2 = target == Type.TOP || target == Type.BOTTOM;
                if (anchor.getOwner() instanceof Guideline) {
                    isCompatible2 = isCompatible2 || target == Type.CENTER_Y;
                }
                return isCompatible2;
            default:
                return false;
        }
    }

    public String toString() {
        HashSet<ConstraintAnchor> visited = new HashSet<>();
        return this.mOwner.getDebugName() + ":" + this.mType.toString() + (this.mTarget != null ? " connected to " + this.mTarget.toString(visited) : Constants.MAIN_VERSION_TAG);
    }

    private String toString(HashSet<ConstraintAnchor> visited) {
        if (visited.add(this)) {
            return this.mOwner.getDebugName() + ":" + this.mType.toString() + (this.mTarget != null ? " connected to " + this.mTarget.toString(visited) : Constants.MAIN_VERSION_TAG);
        }
        return "<-";
    }
}
