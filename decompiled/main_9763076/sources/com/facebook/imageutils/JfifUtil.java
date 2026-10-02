package com.facebook.imageutils;

import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpStatus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JfifUtil {
    public static int getAutoRotateAngleFromOrientation(int orientation) {
        return TiffUtil.getAutoRotateAngleFromOrientation(orientation);
    }

    public static int getOrientation(InputStream is) {
        try {
            int length = moveToAPP1EXIF(is);
            if (length == 0) {
                return 0;
            }
            return TiffUtil.readOrientationFromTIFF(is, length);
        } catch (IOException e) {
            return 0;
        }
    }

    public static boolean moveToMarker(InputStream is, int markerToFind) throws IOException {
        Preconditions.checkNotNull(is);
        while (StreamProcessor.readPackedInt(is, 1, false) == 255) {
            int marker = 255;
            while (marker == 255) {
                marker = StreamProcessor.readPackedInt(is, 1, false);
            }
            if ((markerToFind == 192 && isSOFn(marker)) || marker == markerToFind) {
                return true;
            }
            if (marker != 216 && marker != 1) {
                if (marker == 217 || marker == 218) {
                    return false;
                }
                int length = StreamProcessor.readPackedInt(is, 2, false) - 2;
                is.skip(length);
            }
        }
        return false;
    }

    private static boolean isSOFn(int marker) {
        switch (marker) {
            case 192:
            case 193:
            case 194:
            case 195:
            case 197:
            case 198:
            case 199:
            case HttpStatus.SC_CREATED /* 201 */:
            case HttpStatus.SC_ACCEPTED /* 202 */:
            case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION /* 203 */:
            case HttpStatus.SC_RESET_CONTENT /* 205 */:
            case HttpStatus.SC_PARTIAL_CONTENT /* 206 */:
            case HttpStatus.SC_MULTI_STATUS /* 207 */:
                return true;
            case 196:
            case 200:
            case HttpStatus.SC_NO_CONTENT /* 204 */:
            default:
                return false;
        }
    }

    private static int moveToAPP1EXIF(InputStream is) throws IOException {
        int length;
        if (moveToMarker(is, 225) && (length = StreamProcessor.readPackedInt(is, 2, false) - 2) > 6) {
            int magic = StreamProcessor.readPackedInt(is, 4, false);
            int zero = StreamProcessor.readPackedInt(is, 2, false);
            int length2 = (length - 4) - 2;
            if (magic == 1165519206 && zero == 0) {
                return length2;
            }
        }
        return 0;
    }
}
