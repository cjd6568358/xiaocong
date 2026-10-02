package rx.plugins;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class RxJavaObservableExecutionHookDefault extends RxJavaObservableExecutionHook {
    private static final RxJavaObservableExecutionHookDefault INSTANCE = new RxJavaObservableExecutionHookDefault();

    private RxJavaObservableExecutionHookDefault() {
    }

    public static RxJavaObservableExecutionHook getInstance() {
        return INSTANCE;
    }
}
