package okhttp3;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class JavaNetCookieJar implements CookieJar {
    private final CookieHandler cookieHandler;

    public JavaNetCookieJar(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    @Override // okhttp3.CookieJar
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (this.cookieHandler != null) {
            List<String> cookieStrings = new ArrayList<>();
            for (Cookie cookie : cookies) {
                cookieStrings.add(cookie.toString());
            }
            Map<String, List<String>> multimap = Collections.singletonMap("Set-Cookie", cookieStrings);
            try {
                this.cookieHandler.put(url.uri(), multimap);
            } catch (IOException e) {
                Platform.get().log(5, "Saving cookies failed for " + url.resolve("/..."), e);
            }
        }
    }

    @Override // okhttp3.CookieJar
    public List<Cookie> loadForRequest(HttpUrl url) {
        Map<String, List<String>> headers = Collections.emptyMap();
        try {
            Map<String, List<String>> cookieHeaders = this.cookieHandler.get(url.uri(), headers);
            List<Cookie> cookies = null;
            for (Map.Entry<String, List<String>> entry : cookieHeaders.entrySet()) {
                String key = entry.getKey();
                if ("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key)) {
                    if (!entry.getValue().isEmpty()) {
                        for (String header : entry.getValue()) {
                            if (cookies == null) {
                                cookies = new ArrayList<>();
                            }
                            cookies.addAll(decodeHeaderAsJavaNetCookies(url, header));
                        }
                    }
                }
            }
            if (cookies != null) {
                return Collections.unmodifiableList(cookies);
            }
            return Collections.emptyList();
        } catch (IOException e) {
            Platform.get().log(5, "Loading cookies failed for " + url.resolve("/..."), e);
            return Collections.emptyList();
        }
    }

    private List<Cookie> decodeHeaderAsJavaNetCookies(HttpUrl url, String header) {
        List<Cookie> result = new ArrayList<>();
        int pos = 0;
        int limit = header.length();
        while (pos < limit) {
            int pairEnd = Util.delimiterOffset(header, pos, limit, ";,");
            int equalsSign = Util.delimiterOffset(header, pos, pairEnd, '=');
            String name = Util.trimSubstring(header, pos, equalsSign);
            if (!name.startsWith("$")) {
                String value = equalsSign < pairEnd ? Util.trimSubstring(header, equalsSign + 1, pairEnd) : "";
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                result.add(new Cookie.Builder().name(name).value(value).domain(url.host()).build());
            }
            pos = pairEnd + 1;
        }
        return result;
    }
}
