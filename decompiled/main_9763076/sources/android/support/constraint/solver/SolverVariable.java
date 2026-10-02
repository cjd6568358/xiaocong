package android.support.constraint.solver;

import com.tencent.android.tpush.common.Constants;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SolverVariable {
    private static int uniqueId = 1;
    public float computedValue;
    private String mName;
    Type mType;
    public int id = -1;
    int definitionId = -1;
    public int strength = 0;
    float[] strengthVector = new float[6];
    ArrayRow[] mClientEquations = new ArrayRow[8];
    int mClientEquationsCount = 0;

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    public SolverVariable(Type type) {
        this.mType = type;
    }

    void clearStrengths() {
        for (int i = 0; i < 6; i++) {
            this.strengthVector[i] = 0.0f;
        }
    }

    String strengthsToString() {
        String representation = this + "[";
        for (int j = 0; j < this.strengthVector.length; j++) {
            String representation2 = representation + this.strengthVector[j];
            if (j < this.strengthVector.length - 1) {
                representation = representation2 + ", ";
            } else {
                representation = representation2 + "] ";
            }
        }
        return representation;
    }

    void addClientEquation(ArrayRow equation) {
        for (int i = 0; i < this.mClientEquationsCount; i++) {
            if (this.mClientEquations[i] == equation) {
                return;
            }
        }
        if (this.mClientEquationsCount >= this.mClientEquations.length) {
            this.mClientEquations = (ArrayRow[]) Arrays.copyOf(this.mClientEquations, this.mClientEquations.length * 2);
        }
        this.mClientEquations[this.mClientEquationsCount] = equation;
        this.mClientEquationsCount++;
    }

    void removeClientEquation(ArrayRow equation) {
        for (int i = 0; i < this.mClientEquationsCount; i++) {
            if (this.mClientEquations[i] == equation) {
                for (int j = 0; j < (this.mClientEquationsCount - i) - 1; j++) {
                    this.mClientEquations[i + j] = this.mClientEquations[i + j + 1];
                }
                this.mClientEquationsCount--;
                return;
            }
        }
    }

    public void reset() {
        this.mName = null;
        this.mType = Type.UNKNOWN;
        this.strength = 0;
        this.id = -1;
        this.definitionId = -1;
        this.computedValue = 0.0f;
        this.mClientEquationsCount = 0;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public String toString() {
        String result = Constants.MAIN_VERSION_TAG + this.mName;
        return result;
    }
}
