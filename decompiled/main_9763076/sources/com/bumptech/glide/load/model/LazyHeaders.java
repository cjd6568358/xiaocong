package com.bumptech.glide.load.model;

import android.text.TextUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class LazyHeaders implements Headers {
    private volatile Map<String, String> combinedHeaders;
    private final Map<String, List<LazyHeaderFactory>> headers;

    LazyHeaders(Map<String, List<LazyHeaderFactory>> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    @Override // com.bumptech.glide.load.model.Headers
    public Map<String, String> getHeaders() {
        if (this.combinedHeaders == null) {
            synchronized (this) {
                if (this.combinedHeaders == null) {
                    this.combinedHeaders = Collections.unmodifiableMap(generateHeaders());
                }
            }
        }
        return this.combinedHeaders;
    }

    private Map<String, String> generateHeaders() {
        Map<String, String> combinedHeaders = new HashMap<>();
        for (Map.Entry<String, List<LazyHeaderFactory>> entry : this.headers.entrySet()) {
            StringBuilder sb = new StringBuilder();
            List<LazyHeaderFactory> factories = entry.getValue();
            for (int i = 0; i < factories.size(); i++) {
                LazyHeaderFactory factory = factories.get(i);
                sb.append(factory.buildHeader());
                if (i != factories.size() - 1) {
                    sb.append(',');
                }
            }
            combinedHeaders.put(entry.getKey(), sb.toString());
        }
        return combinedHeaders;
    }

    public String toString() {
        return "LazyHeaders{headers=" + this.headers + '}';
    }

    public boolean equals(Object o) {
        if (!(o instanceof LazyHeaders)) {
            return false;
        }
        LazyHeaders other = (LazyHeaders) o;
        return this.headers.equals(other.headers);
    }

    public int hashCode() {
        return this.headers.hashCode();
    }

    public static final class Builder {
        private static final Map<String, List<LazyHeaderFactory>> DEFAULT_HEADERS;
        private static final String DEFAULT_USER_AGENT = System.getProperty("http.agent");
        private boolean copyOnModify = true;
        private Map<String, List<LazyHeaderFactory>> headers = DEFAULT_HEADERS;
        private boolean isEncodingDefault = true;
        private boolean isUserAgentDefault = true;

        static {
            Map<String, List<LazyHeaderFactory>> temp = new HashMap<>(2);
            if (!TextUtils.isEmpty(DEFAULT_USER_AGENT)) {
                temp.put(HTTP.USER_AGENT, Collections.singletonList(new StringHeaderFactory(DEFAULT_USER_AGENT)));
            }
            temp.put("Accept-Encoding", Collections.singletonList(new StringHeaderFactory(HTTP.IDENTITY_CODING)));
            DEFAULT_HEADERS = Collections.unmodifiableMap(temp);
        }

        public LazyHeaders build() {
            this.copyOnModify = true;
            return new LazyHeaders(this.headers);
        }
    }

    static final class StringHeaderFactory implements LazyHeaderFactory {
        private final String value;

        StringHeaderFactory(String value) {
            this.value = value;
        }

        @Override // com.bumptech.glide.load.model.LazyHeaderFactory
        public String buildHeader() {
            return this.value;
        }

        public String toString() {
            return "StringHeaderFactory{value='" + this.value + "'}";
        }

        public boolean equals(Object o) {
            if (!(o instanceof StringHeaderFactory)) {
                return false;
            }
            StringHeaderFactory other = (StringHeaderFactory) o;
            return this.value.equals(other.value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }
    }
}
