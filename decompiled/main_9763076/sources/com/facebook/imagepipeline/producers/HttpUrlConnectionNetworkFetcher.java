package com.facebook.imagepipeline.producers;

import android.net.Uri;
import com.facebook.imagepipeline.image.EncodedImage;
import com.hzy.tvmao.ir.ac.ACConstants;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HttpUrlConnectionNetworkFetcher extends BaseNetworkFetcher<FetchState> {
    private final ExecutorService mExecutorService;

    public HttpUrlConnectionNetworkFetcher() {
        this(Executors.newFixedThreadPool(3));
    }

    HttpUrlConnectionNetworkFetcher(ExecutorService executorService) {
        this.mExecutorService = executorService;
    }

    @Override // com.facebook.imagepipeline.producers.NetworkFetcher
    public FetchState createFetchState(Consumer<EncodedImage> consumer, ProducerContext context) {
        return new FetchState(consumer, context);
    }

    @Override // com.facebook.imagepipeline.producers.NetworkFetcher
    public void fetch(final FetchState fetchState, final NetworkFetcher.Callback callback) {
        final Future<?> future = this.mExecutorService.submit(new Runnable() { // from class: com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher.1
            @Override // java.lang.Runnable
            public void run() {
                HttpUrlConnectionNetworkFetcher.this.fetchSync(fetchState, callback);
            }
        });
        fetchState.getContext().addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher.2
            @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
            public void onCancellationRequested() {
                if (future.cancel(false)) {
                    callback.onCancellation();
                }
            }
        });
    }

    void fetchSync(FetchState fetchState, NetworkFetcher.Callback callback) {
        HttpURLConnection connection = null;
        try {
            connection = downloadFrom(fetchState.getUri(), 5);
            if (connection != null) {
                callback.onResponse(connection.getInputStream(), -1);
            }
        } catch (IOException e) {
            callback.onFailure(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private HttpURLConnection downloadFrom(Uri uri, int maxRedirects) throws IOException {
        HttpURLConnection connection = openConnectionTo(uri);
        int responseCode = connection.getResponseCode();
        if (!isHttpSuccess(responseCode)) {
            if (isHttpRedirect(responseCode)) {
                String nextUriString = connection.getHeaderField("Location");
                connection.disconnect();
                Uri nextUri = nextUriString == null ? null : Uri.parse(nextUriString);
                String originalScheme = uri.getScheme();
                if (maxRedirects > 0 && nextUri != null && !nextUri.getScheme().equals(originalScheme)) {
                    return downloadFrom(nextUri, maxRedirects - 1);
                }
                String message = maxRedirects == 0 ? error("URL %s follows too many redirects", uri.toString()) : error("URL %s returned %d without a valid redirect", uri.toString(), Integer.valueOf(responseCode));
                throw new IOException(message);
            }
            connection.disconnect();
            throw new IOException(String.format("Image URL %s returned HTTP code %d", uri.toString(), Integer.valueOf(responseCode)));
        }
        return connection;
    }

    static HttpURLConnection openConnectionTo(Uri uri) throws IOException {
        URL url = new URL(uri.toString());
        return (HttpURLConnection) url.openConnection();
    }

    private static boolean isHttpSuccess(int responseCode) {
        return responseCode >= 200 && responseCode < 300;
    }

    private static boolean isHttpRedirect(int responseCode) {
        switch (responseCode) {
            case 300:
            case 301:
            case 302:
            case 303:
            case 307:
            case 308:
                return true;
            case 304:
            case 305:
            case ACConstants.TAG_LOW_BIT_FIRST /* 306 */:
            default:
                return false;
        }
    }

    private static String error(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }
}
