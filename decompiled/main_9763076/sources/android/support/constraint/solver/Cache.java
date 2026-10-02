package android.support.constraint.solver;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Cache {
    Pools.Pool<ArrayRow> arrayRowPool = new Pools.SimplePool(256);
    Pools.Pool<SolverVariable> solverVariablePool = new Pools.SimplePool(256);
    SolverVariable[] mIndexedVariables = new SolverVariable[32];
}
