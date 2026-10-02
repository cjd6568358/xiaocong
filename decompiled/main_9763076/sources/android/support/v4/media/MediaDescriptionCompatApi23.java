package android.support.v4.media;

import android.media.MediaDescription;
import android.net.Uri;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class MediaDescriptionCompatApi23 extends MediaDescriptionCompatApi21 {
    public static Uri getMediaUri(Object descriptionObj) {
        return ((MediaDescription) descriptionObj).getMediaUri();
    }

    static class Builder extends MediaDescriptionCompatApi21.Builder {
        public static void setMediaUri(Object builderObj, Uri mediaUri) {
            ((MediaDescription.Builder) builderObj).setMediaUri(mediaUri);
        }
    }
}
