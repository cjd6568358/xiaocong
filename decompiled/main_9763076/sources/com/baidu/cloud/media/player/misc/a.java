package com.baidu.cloud.media.player.misc;

import android.text.TextUtils;
import bsh.ParserConstants;
import com.baidu.cloud.media.player.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a implements ITrackInfo {
    private int a = 0;
    private b.a b;

    public a(b.a aVar) {
        this.b = aVar;
    }

    public void a(int i) {
        this.a = i;
    }

    @Override // com.baidu.cloud.media.player.misc.ITrackInfo
    public IMediaFormat getFormat() {
        return new BDCloudMediaFormat(this.b);
    }

    @Override // com.baidu.cloud.media.player.misc.ITrackInfo
    public String getInfoInline() {
        StringBuilder sb = new StringBuilder(ParserConstants.LSHIFTASSIGN);
        switch (this.a) {
            case 1:
                sb.append("VIDEO");
                sb.append(", ");
                sb.append(this.b.a());
                sb.append(", ");
                sb.append(this.b.c());
                sb.append(", ");
                sb.append(this.b.b());
                break;
            case 2:
                sb.append("AUDIO");
                sb.append(", ");
                sb.append(this.b.a());
                sb.append(", ");
                sb.append(this.b.c());
                sb.append(", ");
                sb.append(this.b.d());
                break;
            case 3:
                sb.append("TIMEDTEXT");
                break;
            case 4:
                sb.append("SUBTITLE");
                break;
            default:
                sb.append("UNKNOWN");
                break;
        }
        return sb.toString();
    }

    @Override // com.baidu.cloud.media.player.misc.ITrackInfo
    public String getLanguage() {
        return (this.b == null || TextUtils.isEmpty(this.b.d)) ? "und" : this.b.d;
    }

    @Override // com.baidu.cloud.media.player.misc.ITrackInfo
    public int getTrackType() {
        return this.a;
    }

    public String toString() {
        return getClass().getSimpleName() + '{' + getInfoInline() + "}";
    }
}
