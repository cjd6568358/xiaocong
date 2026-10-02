package com.facebook.imagepipeline.backends.okhttp3;

import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseNetworkFetcher;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.producers.ProducerContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class OkHttpNetworkFetcher extends BaseNetworkFetcher<OkHttpNetworkFetchState> {
    private Executor mCancellationExecutor;
    private final OkHttpClient mOkHttpClient;

    @Override // com.facebook.imagepipeline.producers.NetworkFetcher
    public /* bridge */ /* synthetic */ FetchState createFetchState(Consumer consumer, ProducerContext producerContext) {
        return createFetchState((Consumer<EncodedImage>) consumer, producerContext);
    }

    public static class OkHttpNetworkFetchState extends FetchState {
        public long fetchCompleteTime;
        public long responseTime;
        public long submitTime;

        public OkHttpNetworkFetchState(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
            super(consumer, producerContext);
        }
    }

    public OkHttpNetworkFetcher(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
        this.mCancellationExecutor = okHttpClient.dispatcher().executorService();
    }

    @Override // com.facebook.imagepipeline.producers.NetworkFetcher
    public OkHttpNetworkFetchState createFetchState(Consumer<EncodedImage> consumer, ProducerContext context) {
        return new OkHttpNetworkFetchState(consumer, context);
    }

    @Override // com.facebook.imagepipeline.producers.NetworkFetcher
    public void fetch(final OkHttpNetworkFetchState fetchState, final NetworkFetcher.Callback callback) {
        fetchState.submitTime = SystemClock.elapsedRealtime();
        Uri uri = fetchState.getUri();
        Request request = new Request.Builder().cacheControl(new CacheControl.Builder().noStore().build()).url(uri.toString()).get().build();
        final Call call = this.mOkHttpClient.newCall(request);
        fetchState.getContext().addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher.1
            @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
            public void onCancellationRequested() {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    OkHttpNetworkFetcher.this.mCancellationExecutor.execute(new Runnable() { // from class: com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            call.cancel();
                        }
                    });
                } else {
                    call.cancel();
                }
            }
        });
        call.enqueue(new Callback() { // from class: com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher.2
            /* JADX INFO: Removed unreachable split cross block B:40:0x0034 */
            public void onResponse(Call call2, Response response) throws IOException {
                fetchState.responseTime = SystemClock.elapsedRealtime();
                ResponseBody body = response.body();
                try {
                    if (response.isSuccessful()) {
                        long contentLength = body.contentLength();
                        if (contentLength < 0) {
                            contentLength = 0;
                        }
                        callback.onResponse(body.byteStream(), (int) contentLength);
                    } else {
                        OkHttpNetworkFetcher.this.handleException(call2, new IOException("Unexpected HTTP code " + response), callback);
                    }
                } catch (Exception e) {
                    OkHttpNetworkFetcher.this.handleException(call2, e, callback);
                } finally {
                    try {
                        body.close();
                    } catch (Exception e2) {
                        FLog.w("OkHttpNetworkFetchProducer", "Exception when closing response body", e2);
                    }
                }
            }

            public void onFailure(Call call2, IOException e) {
                OkHttpNetworkFetcher.this.handleException(call2, e, callback);
            }
        });
    }

    @Override // com.facebook.imagepipeline.producers.BaseNetworkFetcher, com.facebook.imagepipeline.producers.NetworkFetcher
    public void onFetchCompletion(OkHttpNetworkFetchState fetchState, int byteSize) {
        fetchState.fetchCompleteTime = SystemClock.elapsedRealtime();
    }

    @Override // com.facebook.imagepipeline.producers.BaseNetworkFetcher, com.facebook.imagepipeline.producers.NetworkFetcher
    public Map<String, String> getExtraMap(OkHttpNetworkFetchState fetchState, int byteSize) {
        Map<String, String> extraMap = new HashMap<>(4);
        extraMap.put("queue_time", Long.toString(fetchState.responseTime - fetchState.submitTime));
        extraMap.put("fetch_time", Long.toString(fetchState.fetchCompleteTime - fetchState.responseTime));
        extraMap.put("total_time", Long.toString(fetchState.fetchCompleteTime - fetchState.submitTime));
        extraMap.put("image_size", Integer.toString(byteSize));
        return extraMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleException(Call call, Exception e, NetworkFetcher.Callback callback) {
        if (call.isCanceled()) {
            callback.onCancellation();
        } else {
            callback.onFailure(e);
        }
    }
}
