package retrofit2.adapter.rxjava;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Scheduler;
import rx.Single;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class RxJavaCallAdapterFactory extends CallAdapter.Factory {
    private final boolean isAsync;
    private final Scheduler scheduler;

    public static RxJavaCallAdapterFactory create() {
        return new RxJavaCallAdapterFactory(null, false);
    }

    private RxJavaCallAdapterFactory(Scheduler scheduler, boolean isAsync) {
        this.scheduler = scheduler;
        this.isAsync = isAsync;
    }

    @Override // retrofit2.CallAdapter.Factory
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Type responseType;
        Class<?> rawType = getRawType(returnType);
        boolean isSingle = rawType == Single.class;
        boolean isCompletable = "rx.Completable".equals(rawType.getCanonicalName());
        if (rawType != Observable.class && !isSingle && !isCompletable) {
            return null;
        }
        if (isCompletable) {
            return new RxJavaCallAdapter(Void.class, this.scheduler, this.isAsync, false, true, false, true);
        }
        boolean isResult = false;
        boolean isBody = false;
        if (!(returnType instanceof ParameterizedType)) {
            String name = isSingle ? "Single" : "Observable";
            throw new IllegalStateException(name + " return type must be parameterized as " + name + "<Foo> or " + name + "<? extends Foo>");
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType == Response.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
        } else if (rawObservableType == Result.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("Result must be parameterized as Result<Foo> or Result<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
            isResult = true;
        } else {
            responseType = observableType;
            isBody = true;
        }
        return new RxJavaCallAdapter(responseType, this.scheduler, this.isAsync, isResult, isBody, isSingle, false);
    }
}
