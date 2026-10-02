package com.youzan.mobile.growinganalytics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: HttpService.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class HttpService implements IRemoteService {
    public static final Companion Companion = new Companion(null);
    private static HttpService instance;
    private static boolean isServerBlock;
    private OkHttpClient okhttpClient;

    private HttpService() {
    }

    public /* synthetic */ HttpService(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    /* JADX INFO: compiled from: HttpService.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private final HttpService getInstance() {
            return HttpService.instance;
        }

        private final void setInstance(HttpService httpService) {
            HttpService.instance = httpService;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isServerBlock() {
            return HttpService.isServerBlock;
        }

        public final synchronized HttpService get() {
            HttpService companion;
            if (getInstance() == null) {
                setInstance(new HttpService(null));
            }
            companion = getInstance();
            if (companion == null) {
                Intrinsics.throwNpe();
            }
            return companion;
        }
    }

    @Override // com.youzan.mobile.growinganalytics.IRemoteService
    public boolean isOnline(Context context, OfflineMode offlineMode) {
        NetworkInfo networkInfo;
        boolean zIsConnectedOrConnecting = true;
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (Companion.isServerBlock() || onOfflineMode(offlineMode) || !UtilKt.hasInternetPermission(context)) {
            return false;
        }
        Object cm = context.getSystemService("connectivity");
        try {
            if ((cm instanceof ConnectivityManager) && (networkInfo = ((ConnectivityManager) cm).getActiveNetworkInfo()) != null) {
                zIsConnectedOrConnecting = networkInfo.isConnectedOrConnecting();
            }
            return zIsConnectedOrConnecting;
        } catch (Exception e) {
            return true;
        }
    }

    @Override // com.youzan.mobile.growinganalytics.IRemoteService
    public Response performRequest(String url, JSONObject params, SSLSocketFactory ssl) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(params, RNMessageModule.PARAMS);
        Response response = (Response) null;
        int retry = 0;
        boolean succeeded = false;
        Request request = new Request.Builder().url(url).header(HTTP.CONTENT_ENCODING, "gzip").post(RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), params.toString())).build();
        Call call = getClient().newCall(request);
        while (retry < 3 && !succeeded) {
            try {
                response = call.execute();
                succeeded = response.isSuccessful();
                Logger.Companion.d("Http", "code:" + (response != null ? Integer.valueOf(response.code()) : null));
            } catch (Exception e) {
                retry++;
            }
        }
        return response;
    }

    private final boolean onOfflineMode(OfflineMode offlineMode) {
        if (offlineMode == null) {
            return false;
        }
        try {
            boolean onOfflineMode = offlineMode.isOffline();
            return onOfflineMode;
        } catch (Exception e) {
            return false;
        }
    }

    private final OkHttpClient getClient() {
        if (this.okhttpClient == null) {
            this.okhttpClient = new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).writeTimeout(20L, TimeUnit.SECONDS).build();
        }
        OkHttpClient okHttpClient = this.okhttpClient;
        if (okHttpClient == null) {
            Intrinsics.throwNpe();
        }
        return okHttpClient;
    }
}
