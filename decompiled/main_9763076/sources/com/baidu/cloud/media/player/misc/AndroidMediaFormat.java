package com.baidu.cloud.media.player.misc;

import android.media.MediaFormat;
import bsh.ParserConstants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AndroidMediaFormat implements IMediaFormat {
    private final MediaFormat a;

    public AndroidMediaFormat(MediaFormat mediaFormat) {
        this.a = mediaFormat;
    }

    @Override // com.baidu.cloud.media.player.misc.IMediaFormat
    public int getInteger(String str) {
        if (this.a == null) {
            return 0;
        }
        return this.a.getInteger(str);
    }

    @Override // com.baidu.cloud.media.player.misc.IMediaFormat
    public String getString(String str) {
        if (this.a == null) {
            return null;
        }
        return this.a.getString(str);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(ParserConstants.LSHIFTASSIGN);
        sb.append(getClass().getName());
        sb.append('{');
        if (this.a != null) {
            sb.append(this.a.toString());
        } else {
            sb.append("null");
        }
        sb.append('}');
        return sb.toString();
    }
}
