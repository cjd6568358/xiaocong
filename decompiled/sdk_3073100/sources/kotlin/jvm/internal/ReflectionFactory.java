package kotlin.jvm.internal;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ReflectionFactory {
    public String renderLambdaToString(Lambda lambda) {
        String result = lambda.getClass().getGenericInterfaces()[0].toString();
        return result.startsWith("kotlin.jvm.functions.") ? result.substring("kotlin.jvm.functions.".length()) : result;
    }
}
