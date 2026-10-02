package android.support.v4.media;

import android.media.MediaMetadata;
import android.os.Parcel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class MediaMetadataCompatApi21 {
    public static void writeToParcel(Object metadataObj, Parcel dest, int flags) {
        ((MediaMetadata) metadataObj).writeToParcel(dest, flags);
    }
}
