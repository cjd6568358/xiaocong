package com.facebook.imagepipeline.nativecode;

import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class JpegTranscoder {
    @DoNotStrip
    private static native void nativeTranscodeJpeg(InputStream inputStream, OutputStream outputStream, int i, int i2, int i3) throws IOException;

    static {
        ImagePipelineNativeLoader.load();
    }

    public static boolean isRotationAngleAllowed(int degrees) {
        return degrees >= 0 && degrees <= 270 && degrees % 90 == 0;
    }

    public static void transcodeJpeg(InputStream inputStream, OutputStream outputStream, int rotationAngle, int scaleNumerator, int quality) throws IOException {
        Preconditions.checkArgument(scaleNumerator >= 1);
        Preconditions.checkArgument(scaleNumerator <= 16);
        Preconditions.checkArgument(quality >= 0);
        Preconditions.checkArgument(quality <= 100);
        Preconditions.checkArgument(isRotationAngleAllowed(rotationAngle));
        Preconditions.checkArgument((scaleNumerator == 8 && rotationAngle == 0) ? false : true, "no transformation requested");
        nativeTranscodeJpeg((InputStream) Preconditions.checkNotNull(inputStream), (OutputStream) Preconditions.checkNotNull(outputStream), rotationAngle, scaleNumerator, quality);
    }
}
