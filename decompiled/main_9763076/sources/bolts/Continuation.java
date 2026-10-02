package bolts;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Continuation<TTaskResult, TContinuationResult> {
    TContinuationResult then(Task<TTaskResult> task) throws Exception;
}
