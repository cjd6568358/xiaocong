package com.baidu.cloud.media.player;

import android.media.MediaCodecInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import bsh.ParserConstants;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static Map<String, Integer> d;
    public MediaCodecInfo a;
    public int b = 0;
    public String c;

    public static a a(MediaCodecInfo mediaCodecInfo, String str) {
        int iIntValue = 200;
        if (mediaCodecInfo == null || Build.VERSION.SDK_INT < 16) {
            return null;
        }
        String name = mediaCodecInfo.getName();
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        String lowerCase = name.toLowerCase(Locale.US);
        if (!lowerCase.startsWith("omx.")) {
            iIntValue = 100;
        } else if (!lowerCase.startsWith("omx.pv") && !lowerCase.startsWith("omx.google.") && !lowerCase.startsWith("omx.ffmpeg.") && !lowerCase.startsWith("omx.k3.ffmpeg.") && !lowerCase.startsWith("omx.avcodec.")) {
            if (lowerCase.startsWith("omx.ittiam.")) {
                iIntValue = 0;
            } else if (lowerCase.startsWith("omx.mtk.")) {
                iIntValue = (Build.VERSION.SDK_INT < 18 || lowerCase.endsWith("decoder.mpeg4")) ? 0 : IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING;
            } else {
                Integer num = a().get(lowerCase);
                if (num != null) {
                    iIntValue = num.intValue();
                } else {
                    try {
                        iIntValue = mediaCodecInfo.getCapabilitiesForType(str) != null ? IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING : 600;
                    } catch (Throwable th) {
                        iIntValue = 600;
                    }
                }
            }
        }
        a aVar = new a();
        aVar.a = mediaCodecInfo;
        aVar.b = iIntValue;
        aVar.c = str;
        return aVar;
    }

    public static String a(int i) {
        switch (i) {
            case 1:
                return "Baseline";
            case 2:
                return "Main";
            case 4:
                return "Extends";
            case 8:
                return "High";
            case 16:
                return "High10";
            case 32:
                return "High422";
            case 64:
                return "High444";
            default:
                return "Unknown";
        }
    }

    public static String a(int i, int i2) {
        return String.format(Locale.US, " %s Profile Level %s (%d,%d)", a(i), b(i2), Integer.valueOf(i), Integer.valueOf(i2));
    }

    private static synchronized Map<String, Integer> a() {
        Map<String, Integer> map;
        if (d != null) {
            map = d;
        } else {
            d = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            d.put("OMX.Nvidia.h264.decode", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.Nvidia.h264.decode.secure", 300);
            d.put("OMX.Intel.hw_vd.h264", Integer.valueOf(IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE));
            d.put("OMX.Intel.VideoDecoder.AVC", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.qcom.video.decoder.avc", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.ittiam.video.decoder.avc", 0);
            d.put("OMX.SEC.avc.dec", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.SEC.AVC.Decoder", 799);
            d.put("OMX.SEC.avcdec", 798);
            d.put("OMX.SEC.avc.sw.dec", 200);
            d.put("OMX.Exynos.avc.dec", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.Exynos.AVC.Decoder", 799);
            d.put("OMX.k3.video.decoder.avc", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.IMG.MSVDX.Decoder.AVC", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.TI.DUCATI1.VIDEO.DECODER", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.rk.video_decoder.avc", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.amlogic.avc.decoder.awesome", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.MARVELL.VIDEO.HW.CODA7542DECODER", Integer.valueOf(IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING));
            d.put("OMX.MARVELL.VIDEO.H264DECODER", 200);
            d.remove("OMX.Action.Video.Decoder");
            d.remove("OMX.allwinner.video.decoder.avc");
            d.remove("OMX.BRCM.vc4.decoder.avc");
            d.remove("OMX.brcm.video.h264.hw.decoder");
            d.remove("OMX.brcm.video.h264.decoder");
            d.remove("OMX.cosmo.video.decoder.avc");
            d.remove("OMX.duos.h264.decoder");
            d.remove("OMX.hantro.81x0.video.decoder");
            d.remove("OMX.hantro.G1.video.decoder");
            d.remove("OMX.hisi.video.decoder");
            d.remove("OMX.LG.decoder.video.avc");
            d.remove("OMX.MS.AVC.Decoder");
            d.remove("OMX.RENESAS.VIDEO.DECODER.H264");
            d.remove("OMX.RTK.video.decoder");
            d.remove("OMX.sprd.h264.decoder");
            d.remove("OMX.ST.VFM.H264Dec");
            d.remove("OMX.vpu.video_decoder.avc");
            d.remove("OMX.WMT.decoder.avc");
            d.remove("OMX.bluestacks.hw.decoder");
            d.put("OMX.google.h264.decoder", 200);
            d.put("OMX.google.h264.lc.decoder", 200);
            d.put("OMX.k3.ffmpeg.decoder", 200);
            d.put("OMX.ffmpeg.video.decoder", 200);
            d.put("OMX.sprd.soft.h264.decoder", 200);
            map = d;
        }
        return map;
    }

    public static String b(int i) {
        switch (i) {
            case 1:
                return "1";
            case 2:
                return "1b";
            case 4:
                return "11";
            case 8:
                return "12";
            case 16:
                return "13";
            case 32:
                return "2";
            case 64:
                return "21";
            case ParserConstants.LSHIFTASSIGN /* 128 */:
                return "22";
            case 256:
                return "3";
            case WXMediaMessage.TITLE_LENGTH_LIMIT /* 512 */:
                return "31";
            case WXMediaMessage.DESCRIPTION_LENGTH_LIMIT /* 1024 */:
                return "32";
            case 2048:
                return "4";
            case 4096:
                return "41";
            case 8192:
                return "42";
            case 16384:
                return "5";
            case WXMediaMessage.THUMB_LENGTH_LIMIT /* 32768 */:
                return "51";
            case 65536:
                return "52";
            default:
                return PushConstants.PUSH_TYPE_NOTIFY;
        }
    }

    public void a(String str) {
        int iMax;
        int iMax2 = 0;
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        try {
            MediaCodecInfo.CodecCapabilities capabilitiesForType = this.a.getCapabilitiesForType(str);
            if (capabilitiesForType == null || capabilitiesForType.profileLevels == null) {
                iMax = 0;
            } else {
                MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr = capabilitiesForType.profileLevels;
                iMax = 0;
                for (MediaCodecInfo.CodecProfileLevel codecProfileLevel : codecProfileLevelArr) {
                    if (codecProfileLevel != null) {
                        iMax = Math.max(iMax, codecProfileLevel.profile);
                        iMax2 = Math.max(iMax2, codecProfileLevel.level);
                    }
                }
            }
            Log.i("BDCloudMediaCodecInfo", String.format(Locale.US, "%s", a(iMax, iMax2)));
        } catch (Throwable th) {
            Log.i("BDCloudMediaCodecInfo", "profile-level: exception");
        }
    }
}
