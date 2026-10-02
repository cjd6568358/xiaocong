package com.hzy.tvmao;

import com.hzy.tvmao.b.ac;
import com.hzy.tvmao.interf.ISingleMatchResult;
import com.hzy.tvmao.utils.LogUtil;
import com.kookong.app.data.RcTestRemoteKeyV3;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class KKSingleMatchManager {
    private String deviceType;
    private String mr;
    private ac singleKeyControl = new ac();
    private String testPower;

    public void getMatchKey(int i, String str, boolean z, ISingleMatchResult iSingleMatchResult) {
        this.mr = str;
        this.deviceType = new StringBuilder(String.valueOf(i)).toString();
        this.testPower = z ? PushConstants.PUSH_TYPE_NOTIFY : "1";
        LogUtil.d("首次获取测试按键   deviceType：" + i + ",allRemote：" + this.mr + ",testSwitch：" + z);
        getSingleKey(this.deviceType, this.mr, this.testPower, iSingleMatchResult);
    }

    public void keyIsWorking(RcTestRemoteKeyV3 rcTestRemoteKeyV3, ISingleMatchResult iSingleMatchResult) {
        String str = (new StringBuilder("按键响应：cname=").append(rcTestRemoteKeyV3).toString() == null || new StringBuilder(String.valueOf(rcTestRemoteKeyV3.displayName)).append(",remoteid=").append(rcTestRemoteKeyV3).toString() == null) ? "null" : rcTestRemoteKeyV3.remoteIds;
        LogUtil.d(str);
        if (rcTestRemoteKeyV3.remoteIds.trim().split(",").length == 1) {
            LogUtil.d("测试按键只有一个remoteId，直接命中");
            iSingleMatchResult.onMatchedIR(rcTestRemoteKeyV3.remoteIds);
        } else {
            String strPackageWorkMr = packageWorkMr(rcTestRemoteKeyV3);
            LogUtil.d("按键组拼mr mr=" + strPackageWorkMr);
            getSingleKey(this.deviceType, strPackageWorkMr, this.testPower, iSingleMatchResult);
        }
    }

    public void groupKeyNotWork(List<RcTestRemoteKeyV3> list, ISingleMatchResult iSingleMatchResult) {
        String strPackageAllKeyMr = packageAllKeyMr(list);
        LogUtil.d("一组按键都不好用： mr=" + strPackageAllKeyMr);
        getSingleKey(this.deviceType, strPackageAllKeyMr, this.testPower, iSingleMatchResult);
    }

    private String packageWorkMr(RcTestRemoteKeyV3 rcTestRemoteKeyV3) {
        this.mr = String.valueOf(this.mr) + "_" + rcTestRemoteKeyV3.remoteIds + "|" + rcTestRemoteKeyV3.functionId + "|1";
        return this.mr;
    }

    private String packageAllKeyMr(List<RcTestRemoteKeyV3> list) {
        int i = 0;
        StringBuffer stringBuffer = new StringBuffer();
        int i2 = 0;
        while (i2 < list.size()) {
            RcTestRemoteKeyV3 rcTestRemoteKeyV3 = list.get(i2);
            int i3 = rcTestRemoteKeyV3.functionId;
            stringBuffer.append(rcTestRemoteKeyV3.remoteIds);
            if (i2 < list.size() - 1) {
                stringBuffer.append(",");
            }
            i2++;
            i = i3;
        }
        this.mr = String.valueOf(this.mr) + "_" + stringBuffer.append("|").append(i).append("|").append(PushConstants.PUSH_TYPE_NOTIFY).toString();
        return this.mr;
    }

    private void getSingleKey(String str, String str2, String str3, ISingleMatchResult iSingleMatchResult) {
        if (this.singleKeyControl == null) {
            this.singleKeyControl = new ac();
        }
        this.singleKeyControl.a(str, str3, str2, new a(this, iSingleMatchResult));
    }
}
