package okhttp3.internal.framed;

import okhttp3.Protocol;
import okio.BufferedSink;
import okio.BufferedSource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface Variant {
    Protocol getProtocol();

    FrameReader newReader(BufferedSource bufferedSource, boolean z);

    FrameWriter newWriter(BufferedSink bufferedSink, boolean z);
}
