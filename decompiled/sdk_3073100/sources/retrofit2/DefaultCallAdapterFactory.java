package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class DefaultCallAdapterFactory extends CallAdapter.Factory {
    static final CallAdapter.Factory INSTANCE = new DefaultCallAdapterFactory();

    DefaultCallAdapterFactory() {
    }

    @Override // retrofit2.CallAdapter.Factory
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Call.class) {
            return null;
        }
        final Type responseType = Utils.getCallResponseType(returnType);
        return new CallAdapter<Object, Call<?>>() { // from class: retrofit2.DefaultCallAdapterFactory.1
            @Override // retrofit2.CallAdapter
            public Type responseType() {
                return responseType;
            }

            @Override // retrofit2.CallAdapter
            public Call<?> adapt(Call<Object> call) {
                return call;
            }
        };
    }
}
