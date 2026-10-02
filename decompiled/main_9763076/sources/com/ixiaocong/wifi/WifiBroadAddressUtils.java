package com.ixiaocong.wifi;

import com.ixiaocong.log.XConfigLog;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WifiBroadAddressUtils {
    public static String getBroadcastAddress(int subnet, int ip) {
        String[] ips = intToIp(ip).split("\\.");
        String[] subnets = intToIp(subnet).split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ips.length; i++) {
            ips[i] = String.valueOf((Integer.parseInt(subnets[i]) ^ (-1)) | Integer.parseInt(ips[i]));
            sb.append(turnToStr(Integer.parseInt(ips[i])));
            if (i != ips.length - 1) {
                sb.append(".");
            }
        }
        XConfigLog.e("getBroadcastAddress", ip + "--ip--" + intToIp(ip) + "--//" + subnet + "//subnet--" + intToIp(subnet) + "//broad--" + turnToIp(sb.toString()));
        return turnToIp(sb.toString());
    }

    private static String turnToStr(int num) {
        String str = Integer.toBinaryString(num);
        int len = 8 - str.length();
        for (int i = 0; i < len; i++) {
            str = PushConstants.PUSH_TYPE_NOTIFY + str;
        }
        if (len < 0) {
            return str.substring(24, 32);
        }
        return str;
    }

    private static String turnToIp(String str) {
        String[] ips = str.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (String str2 : ips) {
            sb.append(turnToInt(str2));
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static int turnToInt(String str) {
        int total = 0;
        int top = str.length();
        for (int i = 0; i < str.length(); i++) {
            String h = String.valueOf(str.charAt(i));
            top--;
            total += ((int) Math.pow(2.0d, top)) * Integer.parseInt(h);
        }
        return total;
    }

    private static String intToIp(int paramInt) {
        return (paramInt & 255) + "." + ((paramInt >> 8) & 255) + "." + ((paramInt >> 16) & 255) + "." + ((paramInt >> 24) & 255);
    }
}
