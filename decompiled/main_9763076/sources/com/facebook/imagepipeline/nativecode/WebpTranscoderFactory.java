package com.facebook.imagepipeline.nativecode;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WebpTranscoderFactory {
    private static WebpTranscoder sWebpTranscoder;
    public static boolean sWebpTranscoderPresent;

    static {
        sWebpTranscoderPresent = false;
        try {
            sWebpTranscoder = (WebpTranscoder) Class.forName("com.facebook.imagepipeline.nativecode.WebpTranscoderImpl").newInstance();
            sWebpTranscoderPresent = true;
        } catch (Throwable th) {
            sWebpTranscoderPresent = false;
        }
    }

    public static WebpTranscoder getWebpTranscoder() {
        return sWebpTranscoder;
    }
}
