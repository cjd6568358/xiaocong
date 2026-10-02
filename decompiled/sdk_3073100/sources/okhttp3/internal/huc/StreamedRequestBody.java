package okhttp3.internal.huc;

import java.io.IOException;
import okhttp3.internal.http.UnrepeatableRequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Pipe;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class StreamedRequestBody extends OutputStreamRequestBody implements UnrepeatableRequestBody {
    private final Pipe pipe = new Pipe(8192);

    StreamedRequestBody(long expectedContentLength) {
        initOutputStream(Okio.buffer(this.pipe.sink()), expectedContentLength);
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink sink) throws IOException {
        Buffer buffer = new Buffer();
        while (this.pipe.source().read(buffer, 8192L) != -1) {
            sink.write(buffer, buffer.size());
        }
    }
}
