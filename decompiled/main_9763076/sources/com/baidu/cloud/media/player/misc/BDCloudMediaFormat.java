package com.baidu.cloud.media.player.misc;

import android.text.TextUtils;
import bsh.ParserConstants;
import com.baidu.cloud.media.player.b;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BDCloudMediaFormat implements IMediaFormat {
    public static final String CODEC_NAME_H264 = "h264";
    public static final String KEY_IJK_BIT_RATE_UI = "ijk-bit-rate-ui";
    public static final String KEY_IJK_CHANNEL_UI = "ijk-channel-ui";
    public static final String KEY_IJK_CODEC_LONG_NAME_UI = "ijk-codec-long-name-ui";
    public static final String KEY_IJK_CODEC_NAME_UI = "ijk-codec-name-ui";
    public static final String KEY_IJK_CODEC_PIXEL_FORMAT_UI = "ijk-pixel-format-ui";
    public static final String KEY_IJK_CODEC_PROFILE_LEVEL_UI = "ijk-profile-level-ui";
    public static final String KEY_IJK_FRAME_RATE_UI = "ijk-frame-rate-ui";
    public static final String KEY_IJK_RESOLUTION_UI = "ijk-resolution-ui";
    public static final String KEY_IJK_SAMPLE_RATE_UI = "ijk-sample-rate-ui";
    private static final Map<String, a> b = new HashMap();
    public final b.a a;

    private static abstract class a {
        private a() {
        }

        protected String a() {
            return "N/A";
        }

        protected abstract String a(BDCloudMediaFormat bDCloudMediaFormat);

        public String b(BDCloudMediaFormat bDCloudMediaFormat) {
            String strA = a(bDCloudMediaFormat);
            return TextUtils.isEmpty(strA) ? a() : strA;
        }
    }

    public BDCloudMediaFormat(b.a aVar) {
        b.put(KEY_IJK_CODEC_LONG_NAME_UI, new a() { // from class: com.baidu.cloud.media.player.misc.BDCloudMediaFormat.1
            @Override // com.baidu.cloud.media.player.misc.BDCloudMediaFormat.a
            public String a(BDCloudMediaFormat bDCloudMediaFormat) {
                return BDCloudMediaFormat.this.a.a("codec_long_name");
            }
        });
        b.put(KEY_IJK_CODEC_NAME_UI, new a() { // from class: com.baidu.cloud.media.player.misc.BDCloudMediaFormat.2
            @Override // com.baidu.cloud.media.player.misc.BDCloudMediaFormat.a
            public String a(BDCloudMediaFormat bDCloudMediaFormat) {
                return BDCloudMediaFormat.this.a.a("codec_name");
            }
        });
        b.put(KEY_IJK_BIT_RATE_UI, new a() { // from class: com.baidu.cloud.media.player.misc.BDCloudMediaFormat.3
            @Override // com.baidu.cloud.media.player.misc.BDCloudMediaFormat.a
            protected String a(BDCloudMediaFormat bDCloudMediaFormat) {
                int integer = bDCloudMediaFormat.getInteger("bitrate");
                if (integer <= 0) {
                    return null;
                }
                return integer < 1000 ? String.format(Locale.US, "%d bit/s", Integer.valueOf(integer)) : String.format(Locale.US, "%d kb/s", Integer.valueOf(integer / 1000));
            }
        });
        b.put(KEY_IJK_CODEC_PROFILE_LEVEL_UI, new a() { // from class: com.baidu.cloud.media.player.misc.BDCloudMediaFormat.4
            @Override // com.baidu.cloud.media.player.misc.BDCloudMediaFormat.a
            protected String a(BDCloudMediaFormat bDCloudMediaFormat) {
                String str;
                switch (bDCloudMediaFormat.getInteger("codec_profile_id")) {
                    case 44:
                        str = "CAVLC 4:4:4";
                        break;
                    case 66:
                        str = "Baseline";
                        break;
                    case 77:
                        str = "Main";
                        break;
                    case 88:
                        str = "Extended";
                        break;
                    case 100:
                        str = "High";
                        break;
                    case 110:
                        str = "High 10";
                        break;
                    case ParserConstants.ANDASSIGN /* 122 */:
                        str = "High 4:2:2";
                        break;
                    case 144:
                        str = "High 4:4:4";
                        break;
                    case 244:
                        str = "High 4:4:4 Predictive";
                        break;
                    case 578:
                        str = "Constrained Baseline";
                        break;
                    case 2158:
                        str = "High 10 Intra";
                        break;
                    case 2170:
                        str = "High 4:2:2 Intra";
                        break;
                    case 2292:
                        str = "High 4:4:4 Intra";
                        break;
                    default:
                        return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                String string = bDCloudMediaFormat.getString("codec_name");
                if (!TextUtils.isEmpty(string) && string.equalsIgnoreCase(BDCloudMediaFormat.CODEC_NAME_H264)) {
                    int integer = bDCloudMediaFormat.getInteger("codec_level");
                    if (integer < 10) {
                        return sb.toString();
                    }
                    sb.append(" Profile Level ");
                    sb.append((integer / 10) % 10);
                    if (integer % 10 != 0) {
                        sb.append(".");
                        sb.append(integer % 10);
                    }
                }
                return sb.toString();
            }
        });
        b.put(KEY_IJK_CODEC_PIXEL_FORMAT_UI, new a() { // from class: com.baidu.cloud.media.player.misc.BDCloudMediaFormat.5
            @Override // com.baidu.cloud.media.player.misc.BDCloudMediaFormat.a
            protected String a(BDCloudMediaFormat bDCloudMediaFormat) {
                return bDCloudMediaFormat.getString("codec_pixel_format");
            }
        });
        b.put(KEY_IJK_RESOLUTION_UI, new a() { // from class: com.baidu.cloud.media.player.misc.BDCloudMediaFormat.6
            @Override // com.baidu.cloud.media.player.misc.BDCloudMediaFormat.a
            protected String a(BDCloudMediaFormat bDCloudMediaFormat) {
                int integer = bDCloudMediaFormat.getInteger(IMediaFormat.KEY_WIDTH);
                int integer2 = bDCloudMediaFormat.getInteger(IMediaFormat.KEY_HEIGHT);
                int integer3 = bDCloudMediaFormat.getInteger("sar_num");
                int integer4 = bDCloudMediaFormat.getInteger("sar_den");
                if (integer <= 0 || integer2 <= 0) {
                    return null;
                }
                return (integer3 <= 0 || integer4 <= 0) ? String.format(Locale.US, "%d x %d", Integer.valueOf(integer), Integer.valueOf(integer2)) : String.format(Locale.US, "%d x %d [SAR %d:%d]", Integer.valueOf(integer), Integer.valueOf(integer2), Integer.valueOf(integer3), Integer.valueOf(integer4));
            }
        });
        b.put(KEY_IJK_FRAME_RATE_UI, new a() { // from class: com.baidu.cloud.media.player.misc.BDCloudMediaFormat.7
            @Override // com.baidu.cloud.media.player.misc.BDCloudMediaFormat.a
            protected String a(BDCloudMediaFormat bDCloudMediaFormat) {
                int integer = bDCloudMediaFormat.getInteger("fps_num");
                int integer2 = bDCloudMediaFormat.getInteger("fps_den");
                if (integer <= 0 || integer2 <= 0) {
                    return null;
                }
                return String.valueOf(integer / integer2);
            }
        });
        b.put(KEY_IJK_SAMPLE_RATE_UI, new a() { // from class: com.baidu.cloud.media.player.misc.BDCloudMediaFormat.8
            @Override // com.baidu.cloud.media.player.misc.BDCloudMediaFormat.a
            protected String a(BDCloudMediaFormat bDCloudMediaFormat) {
                int integer = bDCloudMediaFormat.getInteger("sample_rate");
                if (integer <= 0) {
                    return null;
                }
                return String.format(Locale.US, "%d Hz", Integer.valueOf(integer));
            }
        });
        b.put(KEY_IJK_CHANNEL_UI, new a() { // from class: com.baidu.cloud.media.player.misc.BDCloudMediaFormat.9
            @Override // com.baidu.cloud.media.player.misc.BDCloudMediaFormat.a
            protected String a(BDCloudMediaFormat bDCloudMediaFormat) {
                int integer = bDCloudMediaFormat.getInteger("channel_layout");
                if (integer <= 0) {
                    return null;
                }
                if (integer == 4) {
                    return "mono";
                }
                return ((long) integer) == 3 ? "stereo" : String.format(Locale.US, "%x", Integer.valueOf(integer));
            }
        });
        this.a = aVar;
    }

    @Override // com.baidu.cloud.media.player.misc.IMediaFormat
    public int getInteger(String str) {
        if (this.a == null) {
            return 0;
        }
        return this.a.b(str);
    }

    @Override // com.baidu.cloud.media.player.misc.IMediaFormat
    public String getString(String str) {
        if (this.a == null) {
            return null;
        }
        return b.containsKey(str) ? b.get(str).b(this) : this.a.a(str);
    }
}
