package com.hzy.tvmao.model.legacy.api;

import android.text.TextUtils;
import android.util.Log;
import com.hzy.tvmao.model.legacy.api.data.ManualMatchData;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.kookong.app.data.BrandList;
import com.kookong.app.data.ChannelEpg;
import com.kookong.app.data.CountryList;
import com.kookong.app.data.IrDataList;
import com.kookong.app.data.LineupList;
import com.kookong.app.data.PlayingTimeData;
import com.kookong.app.data.PlayingTimeDataV2;
import com.kookong.app.data.ProgramData;
import com.kookong.app.data.ProgramDetailData;
import com.kookong.app.data.ProgramGuideList;
import com.kookong.app.data.RcTestRemoteKeyList;
import com.kookong.app.data.RcTestRemoteKeyListV3;
import com.kookong.app.data.RemoteList;
import com.kookong.app.data.SearchDataList;
import com.kookong.app.data.SpList;
import com.kookong.app.data.StbList;
import com.kookong.app.data.api.LineupData;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: compiled from: ObjectDataHelper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    public static i<ManualMatchData> a(int i, int i2, String str) {
        i<ManualMatchData> iVarA;
        HashMap map = new HashMap();
        map.put("areaid", new StringBuilder(String.valueOf(i)).toString());
        map.put("spid", new StringBuilder(String.valueOf(i2)).toString());
        if (!TextUtils.isEmpty(str)) {
            map.put("mr", str);
        }
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/manuallineup", map, ManualMatchData.class);
        } catch (Exception e) {
            Log.e("manuallineup", "failed to get manualLineup ", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<ProgramGuideList> a(int i, int i2, String str, String str2) {
        i<ProgramGuideList> iVarA;
        HashMap map = new HashMap();
        map.put("cid", new StringBuilder(String.valueOf(i)).toString());
        map.put("ctryId", Constants.MAIN_VERSION_TAG);
        map.put("isHd", String.valueOf(i2) + "'");
        if (!TextUtils.isEmpty(str)) {
            map.put("stime", String.valueOf(str) + "'");
        }
        if (!TextUtils.isEmpty(str2)) {
            map.put("etime", String.valueOf(str2) + "'");
        }
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/programguide", map, ProgramGuideList.class);
        } catch (Exception e) {
            Log.e("getChannelGuide", "failed to get drama counter data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<IrDataList> a(int i, int i2) {
        i<IrDataList> iVarA;
        HashMap map = new HashMap();
        map.put("brandid", String.valueOf(i));
        map.put("devicetypeid", String.valueOf(i2));
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/rctestkey", map, IrDataList.class);
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<IrDataList> a(String str, int i, String str2, boolean z, boolean z2) {
        i<IrDataList> iVarA;
        HashMap map = new HashMap();
        map.put("rids", str);
        if (z) {
            map.put("alg", "1");
        }
        if (i != -1) {
            map.put("deviceType", new StringBuilder(String.valueOf(i)).toString());
        }
        map.put("mcode", str2);
        map.put("ackey", new StringBuilder(String.valueOf(z2)).toString());
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/irs", map, IrDataList.class);
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<RemoteList> b(int i, int i2, String str) {
        i<RemoteList> iVarA;
        HashMap map = new HashMap();
        map.put("functionid", String.valueOf(i));
        if (str != null) {
            map.put("remoteids", str);
        }
        if (i2 != -1) {
            map.put("remoteid", String.valueOf(i2));
        }
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/filterrc", map, new e());
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<RcTestRemoteKeyListV3> a(String str, String str2, String str3) {
        i<RcTestRemoteKeyListV3> iVarA;
        HashMap map = new HashMap();
        map.put("encrypt", PushConstants.PUSH_TYPE_NOTIFY);
        map.put("devicetypeid", str);
        map.put("switch", str2);
        map.put("mr", str3);
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/rctestkey", map, RcTestRemoteKeyListV3.class);
        } catch (Exception e) {
            Log.e("CommentListData", "failed to get comment list data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<RemoteList> a(int i, int i2, int i3, int i4, String str) {
        i<RemoteList> iVarA;
        HashMap map = new HashMap();
        if (i != 0) {
            map.put("did", String.valueOf(i));
        }
        if (i2 != 0) {
            map.put("bid", String.valueOf(i2));
        }
        if (i3 != 0) {
            map.put("spId", String.valueOf(i3));
        }
        if (i4 != 0) {
            map.put("areaId", String.valueOf(i4));
        }
        if (!TextUtils.isEmpty(str)) {
            map.put("countryCode", str);
        }
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/remotes", map, RemoteList.class);
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<StbList> a(String str, int i) {
        i<StbList> iVarA;
        HashMap map = new HashMap();
        map.put(RNMessageModule.NAME, str);
        map.put("areaId", String.valueOf(i));
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/stbs", map, new f());
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<StbList> a(int i) {
        i<StbList> iVarA;
        HashMap map = new HashMap();
        map.put("spId", String.valueOf(i));
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/stbs", map, new g());
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<LineupList> b(int i, int i2) {
        i<LineupList> iVarA;
        HashMap map = new HashMap();
        map.put("areaId", String.valueOf(i));
        map.put("spId", String.valueOf(i2));
        map.put("exact", String.valueOf(1));
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/lineups", map, new h());
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<SpList> b(int i) {
        i<SpList> iVarA;
        HashMap map = new HashMap();
        map.put("areaId", String.valueOf(i));
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/sps", map, SpList.class);
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<Integer> b(String str, String str2, String str3) {
        i<Integer> iVarA;
        HashMap map = new HashMap();
        map.put("p", str);
        map.put("c", str2);
        map.put("a", str3);
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/address", map, Integer.class);
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<BrandList> a(String str, String str2) {
        i<BrandList> iVarA;
        HashMap map = new HashMap();
        map.put("deviceType", str);
        map.put("countryCode", str2);
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/brands", map, BrandList.class);
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<PlayingTimeData> a(short s, String str, int i, String str2, boolean z) {
        i<PlayingTimeData> iVar;
        HashMap map = new HashMap();
        map.put("resourceId", str);
        map.put("typeId", String.valueOf((int) s));
        if (!z) {
            map.put("chid", String.valueOf(i));
            map.put("ctry", str2);
        }
        i<PlayingTimeData> iVarA = null;
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/objplayingtime", map, PlayingTimeData.class);
            if (iVarA.e == null || ((PlayingTimeData) iVarA.e).now <= 0) {
                iVar = iVarA;
            } else {
                p.a(((PlayingTimeData) iVarA.e).now);
                iVar = iVarA;
            }
        } catch (Exception e) {
            Log.e("PlayingTimeDataV2", "failed to get drama playintime data,", e);
        }
        if (iVar == null) {
            return i.b();
        }
        return iVar;
    }

    public static i<PlayingTimeDataV2> a(short s, String str) {
        i<PlayingTimeDataV2> iVarA;
        HashMap map = new HashMap();
        map.put("resourceId", str);
        map.put("typeId", String.valueOf((int) s));
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/objplayingtimev2", map, PlayingTimeDataV2.class);
        } catch (Exception e) {
            Log.e("PlayingTimeDataV2", "failed to get drama playintime data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<LineupData> c(int i, int i2) {
        i<LineupData> iVarA;
        HashMap map = new HashMap();
        map.put("lid", String.valueOf(i2));
        map.put("rid", String.valueOf(i));
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/lineup", map, LineupData.class, false, true);
        } catch (Exception e) {
            Log.e("LineupData", "failed to get Lineup data list,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<ProgramDetailData> a(String str, short s) {
        i<ProgramDetailData> iVar;
        i<ProgramDetailData> iVar2 = null;
        HashMap map = new HashMap();
        map.put("resourceId", str);
        map.put("typeId", String.valueOf((int) s));
        try {
            i<ProgramDetailData> iVarA = j.a("http://sdkapi.kookong.com/m/programdetail", map, ProgramDetailData.class);
            try {
                if (!iVarA.a() || iVarA.e == null) {
                    iVar = iVarA;
                } else {
                    ((ProgramDetailData) iVarA.e).desc = k.a((CharSequence) ((ProgramDetailData) iVarA.e).desc);
                    iVar = iVarA;
                }
            } catch (Exception e) {
                e = e;
                iVar2 = iVarA;
                Log.e("getProgramDetail", "failed to get ProgramData data,", e);
                iVar = iVar2;
            }
        } catch (Exception e2) {
            e = e2;
        }
        if (iVar == null) {
            return i.b();
        }
        return iVar;
    }

    public static i<ProgramData> c(String str, String str2, String str3) {
        i<ProgramData> iVar;
        int time;
        i<ProgramData> iVarA = null;
        HashMap map = new HashMap();
        map.put("time", str2);
        map.put("lid", str);
        if (!TextUtils.isEmpty(str3)) {
            map.put("cid", str3);
        }
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/programdata", map, ProgramData.class, false, true);
            if (iVarA.e == null || ((ProgramData) iVarA.e).nowTime == null || (time = (int) (((ProgramData) iVarA.e).nowTime.getTime() / 1000)) <= 0) {
                iVar = iVarA;
            } else {
                p.a(time);
                iVar = iVarA;
            }
        } catch (Exception e) {
            Log.e("ProgramData", "failed to get ProgramData data,", e);
        }
        if (iVar == null) {
            return i.b();
        }
        return iVar;
    }

    public static i<ChannelEpg> a(int i, String str, int i2) {
        i<ChannelEpg> iVarA;
        HashMap map = new HashMap();
        map.put("chid", String.valueOf(i));
        map.put("ctry", str);
        if (i2 != 0) {
            map.put("day", String.valueOf(i2));
        }
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/chepg", map, ChannelEpg.class, false, true);
        } catch (Exception e) {
            Log.e("ChannelEpg", "failed to get program list,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<JSONObject> a() {
        i<JSONObject> iVarA;
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/initcustomer", new HashMap(), JSONObject.class);
        } catch (Exception e) {
            Log.e("initcustomer", "failed", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<JSONObject> d(String str, String str2, String str3) {
        i<JSONObject> iVarA;
        HashMap map = new HashMap();
        map.put("action", str);
        map.put(RNMessageModule.PARAMS, str2);
        map.put("coo", str3);
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/useraction", map, JSONObject.class);
        } catch (Exception e) {
            Log.e("postUserAction", "failed", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<SearchDataList> b(String str, String str2) {
        i<SearchDataList> iVarA;
        HashMap map = new HashMap();
        map.put("term", str);
        map.put("cinfo", str2);
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/search", map, SearchDataList.class);
        } catch (Exception e) {
            e.printStackTrace();
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<RcTestRemoteKeyList> d(int i, int i2) {
        i<RcTestRemoteKeyList> iVarA;
        HashMap map = new HashMap();
        map.put("brandid", String.valueOf(i));
        map.put("devicetypeid", String.valueOf(i2));
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/rctestkey", map, RcTestRemoteKeyList.class);
        } catch (Exception e) {
            Log.e("getIRDataById", "failed to get home object data,", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }

    public static i<CountryList> b() {
        i<CountryList> iVarA;
        try {
            iVarA = j.a("http://sdkapi.kookong.com/m/countrylist", null, CountryList.class);
        } catch (Exception e) {
            Log.e("initcustomer", "failed", e);
            iVarA = null;
        }
        if (iVarA == null) {
            return i.b();
        }
        return iVarA;
    }
}
