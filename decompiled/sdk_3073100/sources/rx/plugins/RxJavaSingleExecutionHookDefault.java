package rx.plugins;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class RxJavaSingleExecutionHookDefault extends RxJavaSingleExecutionHook {
    private static final RxJavaSingleExecutionHookDefault INSTANCE = new RxJavaSingleExecutionHookDefault();

    private RxJavaSingleExecutionHookDefault() {
    }

    public static RxJavaSingleExecutionHook getInstance() {
        return INSTANCE;
    }
}
