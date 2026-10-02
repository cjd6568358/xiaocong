package com.hzy.tvmao;

import android.content.Context;
import android.util.Log;
import com.hzy.tvmao.b.ae;
import com.hzy.tvmao.interf.IRequestResult;
import com.hzy.tvmao.ir.encode.CodeHelper;
import com.hzy.tvmao.model.db.bean.ChannelInfo;
import com.hzy.tvmao.model.legacy.api.StreamHelper;
import com.hzy.tvmao.model.legacy.api.data.EPGProgramData;
import com.hzy.tvmao.model.legacy.api.data.UIProgramData;
import com.hzy.tvmao.utils.LogUtil;
import com.kookong.app.data.BrandList;
import com.kookong.app.data.ChannelEpg;
import com.kookong.app.data.CountryList;
import com.kookong.app.data.IrDataList;
import com.kookong.app.data.LineupList;
import com.kookong.app.data.PlayingTimeDataV2;
import com.kookong.app.data.ProgramData;
import com.kookong.app.data.ProgramDetailData;
import com.kookong.app.data.ProgramGuideList;
import com.kookong.app.data.RcTestRemoteKeyList;
import com.kookong.app.data.RemoteList;
import com.kookong.app.data.SearchDataList;
import com.kookong.app.data.SpList;
import com.kookong.app.data.StbList;
import com.kookong.sdk.bean.ManualMatchLineupData;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class KookongSDK {
    public static String APPKEY;
    public static String DEVICEID;
    public static boolean isInitcustomer = false;
    private static Context sContext;

    public static boolean init(Context context, String str) {
        return init(context, str, Constants.MAIN_VERSION_TAG);
    }

    public static boolean init(Context context, String str, String str2) {
        APPKEY = str;
        sContext = context;
        DEVICEID = str2;
        LogUtil.d("StreamHelper init：" + StreamHelper.init(sContext, str));
        boolean zInit = CodeHelper.init(sContext, str);
        LogUtil.d("CodeHelper init：" + zInit);
        y.a();
        z.a();
        login();
        return zInit;
    }

    public static void login() {
        Log.d("KookongSDK", "isInitcustomer " + isInitcustomer);
        if (!isInitcustomer) {
            isInitcustomer = true;
            new Thread(new b()).start();
        }
    }

    public static void setDebugMode(boolean z) {
        LogUtil.setDebugMode(z);
    }

    public static Context getContext() {
        return sContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> void parseControlResponseBean(com.hzy.tvmao.b.a.a aVar, IRequestResult<T> iRequestResult) {
        if (aVar.d() && aVar.c() != null) {
            iRequestResult.onSuccess(aVar.b(), aVar.c());
        } else {
            iRequestResult.onFail(Integer.valueOf(aVar.a()), aVar.b());
        }
    }

    public static void getCountryList(IRequestResult<CountryList> iRequestResult) {
        new com.hzy.tvmao.b.d().a(new m(iRequestResult));
    }

    public static void getAreaId(String str, String str2, String str3, IRequestResult<Integer> iRequestResult) {
        new com.hzy.tvmao.b.o().a(str, str2, str3, new r(iRequestResult));
    }

    public static void getOperaters(int i, IRequestResult<SpList> iRequestResult) {
        new com.hzy.tvmao.b.o().a(i, new s(iRequestResult));
    }

    public static void searchSTB(String str, int i, IRequestResult<StbList> iRequestResult) {
        new com.hzy.tvmao.b.o().a(str, i, new t(iRequestResult));
    }

    public static void getIPTV(int i, IRequestResult<StbList> iRequestResult) {
        new com.hzy.tvmao.b.o().b(i, new u(iRequestResult));
    }

    public static void getAllRemoteIds(int i, int i2, int i3, int i4, IRequestResult<RemoteList> iRequestResult) {
        getRemoteIdsFormNet(i, i2, i3, i4, Constants.MAIN_VERSION_TAG, iRequestResult);
    }

    public static void getAllRemoteIds(int i, int i2, String str, IRequestResult<RemoteList> iRequestResult) {
        getRemoteIdsFormNet(i, i2, 0, 0, str, iRequestResult);
    }

    private static void getRemoteIdsFormNet(int i, int i2, int i3, int i4, String str, IRequestResult<RemoteList> iRequestResult) {
        new com.hzy.tvmao.b.f().a(i, i2, i3, i4, str, new v(iRequestResult));
    }

    public static void getIRDataById(String str, int i, IRequestResult<IrDataList> iRequestResult) {
        httpGetIRDataBydId(str, i, PushConstants.PUSH_TYPE_NOTIFY, false, iRequestResult);
    }

    public static void getIRDataById(String str, IRequestResult<IrDataList> iRequestResult) {
        httpGetIRDataBydId(str, -1, PushConstants.PUSH_TYPE_NOTIFY, false, iRequestResult);
    }

    public static void getIRDataById(String str, int i, boolean z, IRequestResult<IrDataList> iRequestResult) {
        httpGetIRDataBydId(str, i, PushConstants.PUSH_TYPE_NOTIFY, z, iRequestResult);
    }

    public static void getIRDataById(String str, boolean z, IRequestResult<IrDataList> iRequestResult) {
        httpGetIRDataBydId(str, -1, PushConstants.PUSH_TYPE_NOTIFY, z, iRequestResult);
    }

    public static void getNoStateIRDataById(String str, int i, IRequestResult<IrDataList> iRequestResult) {
        getNoStateIRDataById(str, i, false, iRequestResult);
    }

    public static void getNoStateIRDataById(String str, int i, boolean z, IRequestResult<IrDataList> iRequestResult) {
        httpGetIRDataBydId(str, i, PushConstants.PUSH_TYPE_NOTIFY, z, true, iRequestResult);
    }

    public static void getNoStateIRDataById(String str, IRequestResult<IrDataList> iRequestResult) {
        getNoStateIRDataById(str, -1, false, iRequestResult);
    }

    public static void getNoStateIRDataById(String str, boolean z, IRequestResult<IrDataList> iRequestResult) {
        httpGetIRDataBydId(str, -1, PushConstants.PUSH_TYPE_NOTIFY, z, true, iRequestResult);
    }

    public static void testIRDataById(String str, int i, IRequestResult<IrDataList> iRequestResult) {
        httpGetIRDataBydId(str, i, "1", false, iRequestResult);
    }

    public static void testIRDataById(String str, int i, boolean z, IRequestResult<IrDataList> iRequestResult) {
        httpGetIRDataBydId(str, i, "1", z, iRequestResult);
    }

    private static void httpGetIRDataBydId(String str, int i, String str2, boolean z, IRequestResult<IrDataList> iRequestResult) {
        httpGetIRDataBydId(str, i, str2, z, false, iRequestResult);
    }

    private static void httpGetIRDataBydId(String str, int i, String str2, boolean z, boolean z2, IRequestResult<IrDataList> iRequestResult) {
        new com.hzy.tvmao.b.f().a(str, i, str2, z, z2, new w(iRequestResult));
    }

    public static void getACIRDataByBrandId(int i, IRequestResult<IrDataList> iRequestResult) {
        new com.hzy.tvmao.b.f().a(i, new x(iRequestResult));
    }

    public static void getFilterIRData(int i, int i2, String str, IRequestResult<RemoteList> iRequestResult) {
        new com.hzy.tvmao.b.f().a(i, i2, str, new c(iRequestResult));
    }

    public static void getBrandListFromNet(int i, IRequestResult<BrandList> iRequestResult) {
        getBrandListFromNet(i, Constants.MAIN_VERSION_TAG, iRequestResult);
    }

    public static void getBrandListFromNet(int i, String str, IRequestResult<BrandList> iRequestResult) {
        new com.hzy.tvmao.b.b().a(i, str, new d(iRequestResult));
    }

    public static ChannelInfo getChannelInfo(int i, String str, int i2) {
        return com.hzy.tvmao.b.l.c().a(i, str, i2);
    }

    public static boolean loadLineupByDeviceId(int i) {
        return com.hzy.tvmao.b.l.c().a(i);
    }

    public static List<ChannelInfo> getLineupByDeviceId(int i) {
        return com.hzy.tvmao.b.l.c().b(i);
    }

    public static void getLineUpsList(int i, int i2, IRequestResult<LineupList> iRequestResult) {
        com.hzy.tvmao.b.l.c().a(i, i2, new e(iRequestResult));
    }

    public static void getLineupDataAndSave(int i, int i2, int i3, IRequestResult<String> iRequestResult) {
        com.hzy.tvmao.b.l.c().a(i, i2, i3, new f(iRequestResult));
    }

    public static void getProgramGuide(int i, int i2, String str, String str2, IRequestResult<ProgramGuideList> iRequestResult) {
        com.hzy.tvmao.b.u.c().a(i, i2, str, str2, new g(iRequestResult));
    }

    public static void searchProgram(String str, int i, IRequestResult<EPGProgramData> iRequestResult) {
        searchProgram(str, i, 0, null, true, iRequestResult);
    }

    public static void searchProgramByChannel(String str, int i, int i2, String str2, IRequestResult<EPGProgramData> iRequestResult) {
        searchProgram(str, i, i2, str2, false, iRequestResult);
    }

    private static void searchProgram(String str, int i, int i2, String str2, boolean z, IRequestResult<EPGProgramData> iRequestResult) {
        com.hzy.tvmao.b.u.c().a(str, (short) i, i2, str2, z, new h(iRequestResult));
    }

    public static void accurateSearchProgram(String str, short s, IRequestResult<PlayingTimeDataV2> iRequestResult) {
        com.hzy.tvmao.b.u.c().a(str, s, new i(iRequestResult));
    }

    public static void getProgramGuide(int i, String str, int i2, IRequestResult<ChannelEpg> iRequestResult) {
        com.hzy.tvmao.b.u.c().a(i, str, i2, new j(iRequestResult));
    }

    public static void getProgramsByCatID(int i, String str, String str2, IRequestResult<ProgramData> iRequestResult) {
        com.hzy.tvmao.b.u.c().a(i, str, str2, new k(iRequestResult));
    }

    public static void getProgramDetail(String str, short s, IRequestResult<ProgramDetailData> iRequestResult) {
        com.hzy.tvmao.b.u.c().b(str, s, new l(iRequestResult));
    }

    public static void searchPlayingProgram(int i, String str, IRequestResult<SearchDataList> iRequestResult) {
        com.hzy.tvmao.b.u.c().a(i, str, new n(iRequestResult));
    }

    public static void getTVWallData(int i, String str, IRequestResult<UIProgramData> iRequestResult) {
        ae.c().a(i, str, new o(iRequestResult));
    }

    public static void getRcTestKeys(int i, int i2, IRequestResult<RcTestRemoteKeyList> iRequestResult) {
        new com.hzy.tvmao.b.f().a(i, i2, new p(iRequestResult));
    }

    public static ChannelInfo getChannelInfo(int i, int i2) {
        return com.hzy.tvmao.b.l.c().a(i, Constants.MAIN_VERSION_TAG, i2);
    }

    public static void manualMatchLineup(int i, int i2, String str, IRequestResult<ManualMatchLineupData> iRequestResult) {
        new com.hzy.tvmao.b.o().a(i, i2, str, new q(iRequestResult));
    }

    public static void sendIR(int i, String str) {
        com.hzy.tvmao.ir.a.a().a(i, str);
    }

    public static boolean statTunein(String str, String str2, String str3, double d, double d2) {
        try {
            return com.hzy.tvmao.c.a.a("wch", String.valueOf(str) + "," + str2, str3, String.valueOf(d) + "," + d2);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean statSwitchRemotePannel(String str, String str2, String str3, String str4, double d, double d2) {
        try {
            return com.hzy.tvmao.c.a.a("oprc", String.valueOf(str) + "," + str2 + "," + str3, str4, String.valueOf(d) + "," + d2);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean statAddRemotePannel(String str, String str2, String str3, String str4, double d, double d2) {
        try {
            return com.hzy.tvmao.c.a.a("nrc", String.valueOf(str) + "," + str2 + "," + str3, str4, String.valueOf(d) + "," + d2);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void statistical(String str, String str2, String str3) {
        com.hzy.tvmao.c.a.a(str, str2, str3);
    }

    public static byte[] decode(byte[] bArr) {
        return StreamHelper.dec(bArr);
    }

    public static String decodeChannelNum(String str) {
        return new String(decode(com.hzy.tvmao.utils.c.a(str)));
    }
}
