package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.producers.FetchState;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BaseNetworkFetcher<FETCH_STATE extends FetchState> implements NetworkFetcher<FETCH_STATE> {
    @Override // com.facebook.imagepipeline.producers.NetworkFetcher
    public boolean shouldPropagate(FETCH_STATE fetchState) {
        return true;
    }

    @Override // com.facebook.imagepipeline.producers.NetworkFetcher
    public void onFetchCompletion(FETCH_STATE fetchState, int byteSize) {
    }

    @Override // com.facebook.imagepipeline.producers.NetworkFetcher
    public Map<String, String> getExtraMap(FETCH_STATE fetchState, int byteSize) {
        return null;
    }
}
