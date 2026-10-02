package okhttp3.ws;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface WebSocket {
    public static final MediaType TEXT = MediaType.parse("application/vnd.okhttp.websocket+text; charset=utf-8");
    public static final MediaType BINARY = MediaType.parse("application/vnd.okhttp.websocket+binary");

    void close(int i, String str) throws IOException;

    void sendMessage(RequestBody requestBody) throws IOException;

    void sendPing(Buffer buffer) throws IOException;
}
