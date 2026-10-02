package com.alibaba.mtl.log.e;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.mtl.log.model.LogField;
import com.alibaba.mtl.log.sign.BaseRequestAuth;
import com.alibaba.mtl.log.sign.IRequestAuth;
import com.alibaba.mtl.log.sign.SecurityRequestAuth;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: UrlWrapper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class t {
    private static final String TAG = t.class.getSimpleName();

    public static String a(String str, Map<String, Object> map, Map<String, Object> map2) throws Exception {
        String strA;
        String str2 = Constants.MAIN_VERSION_TAG;
        if (map2 != null && map2.size() > 0) {
            Set<String> setKeySet = map2.keySet();
            String[] strArr = new String[setKeySet.size()];
            setKeySet.toArray(strArr);
            String[] strArrA = g.a().a(strArr, true);
            String str3 = Constants.MAIN_VERSION_TAG;
            for (String str4 : strArrA) {
                str3 = str3 + str4 + j.b((byte[]) map2.get(str4));
            }
            str2 = str3;
        }
        try {
            strA = a(str, null, null, str2);
        } catch (Throwable th) {
            strA = a(com.alibaba.mtl.log.a.a.M, null, null, str2);
        }
        String str5 = com.alibaba.mtl.log.a.a.N;
        if (!TextUtils.isEmpty(str5)) {
            return strA + "&dk=" + URLEncoder.encode(str5, HTTP.UTF_8);
        }
        return strA;
    }

    public static String b(String str, Map<String, Object> map, Map<String, Object> map2) throws Exception {
        if (map == null) {
            new HashMap();
        }
        Context context = com.alibaba.mtl.log.a.getContext();
        String appkey = b.getAppkey();
        String strL = b.l();
        String str2 = strL == null ? Constants.MAIN_VERSION_TAG : strL;
        String str3 = d.a(context).get(LogField.APPVERSION.toString());
        String str4 = d.a(context).get(LogField.OS.toString());
        String str5 = d.a(context).get(LogField.UTDID.toString());
        String strValueOf = String.valueOf(System.currentTimeMillis());
        IRequestAuth iRequestAuthA = com.alibaba.mtl.log.a.a();
        String str6 = PushConstants.PUSH_TYPE_NOTIFY;
        if (iRequestAuthA instanceof SecurityRequestAuth) {
            str6 = "1";
        }
        String sign = iRequestAuthA.getSign(j.b((appkey + str3 + str2 + str4 + str5 + "2.6.0_for_bc" + strValueOf + str6 + map.get("_b01n15") + map.get("_b01na")).getBytes()));
        StringBuilder sb = new StringBuilder(str);
        sb.append("?");
        sb.append("ak").append("=").append(appkey);
        sb.append("&").append("av").append("=").append(str3);
        sb.append("&").append("c").append("=").append(URLEncoder.encode(str2));
        sb.append("&").append("d").append("=").append(str5);
        sb.append("&").append("sv").append("=").append("2.6.0_for_bc");
        sb.append("&").append("t").append("=").append(strValueOf);
        sb.append("&").append("is").append("=").append(str6);
        sb.append("&").append("_b01n15").append("=").append(map.get("_b01n15"));
        sb.append("&").append("_b01na").append("=").append(map.get("_b01na"));
        sb.append("&").append(NotifyType.SOUND).append("=").append(sign);
        return sb.toString();
    }

    private static String a(String str, String str2, String str3, String str4) throws Exception {
        String str5;
        String str6;
        Context context = com.alibaba.mtl.log.a.getContext();
        String appkey = b.getAppkey();
        String strL = b.l();
        String str7 = strL == null ? Constants.MAIN_VERSION_TAG : strL;
        String str8 = d.a(context).get(LogField.APPVERSION.toString());
        String str9 = d.a(context).get(LogField.OS.toString());
        String str10 = d.a(context).get(LogField.UTDID.toString());
        String strValueOf = String.valueOf(System.currentTimeMillis());
        IRequestAuth iRequestAuthA = com.alibaba.mtl.log.a.a();
        if (iRequestAuthA instanceof SecurityRequestAuth) {
            str5 = "1";
            str6 = PushConstants.PUSH_TYPE_NOTIFY;
        } else if (!(iRequestAuthA instanceof BaseRequestAuth)) {
            str5 = PushConstants.PUSH_TYPE_NOTIFY;
            str6 = PushConstants.PUSH_TYPE_NOTIFY;
        } else {
            str6 = ((BaseRequestAuth) iRequestAuthA).isEncode() ? "1" : PushConstants.PUSH_TYPE_NOTIFY;
            str5 = PushConstants.PUSH_TYPE_NOTIFY;
        }
        StringBuilder sbAppend = new StringBuilder().append(appkey).append(str7).append(str8).append(str9).append("2.6.0_for_bc").append(str10).append(strValueOf).append("3.0").append(str5);
        if (str3 == null) {
            str3 = Constants.MAIN_VERSION_TAG;
        }
        StringBuilder sbAppend2 = sbAppend.append(str3);
        if (str4 == null) {
            str4 = Constants.MAIN_VERSION_TAG;
        }
        String sign = iRequestAuthA.getSign(j.b(sbAppend2.append(str4).toString().getBytes()));
        String str11 = Constants.MAIN_VERSION_TAG;
        if (!TextUtils.isEmpty(str2)) {
            str11 = str2 + "&";
        }
        return String.format("%s?%sak=%s&av=%s&c=%s&v=%s&s=%s&d=%s&sv=%s&p=%s&t=%s&u=%s&is=%s&k=%s", str, str11, e(appkey), e(str8), e(str7), e("3.0"), e(sign), e(str10), "2.6.0_for_bc", str9, strValueOf, Constants.MAIN_VERSION_TAG, str5, str6);
    }

    private static String e(String str) {
        if (str == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            return URLEncoder.encode(str, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }
}
