package com.baidu.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.baidu.location.d.j;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BDLocation implements Parcelable {
    public static final Parcelable.Creator<BDLocation> CREATOR = new a();
    private int A;
    private String B;
    private int C;
    private String D;
    private int E;
    private int F;
    private int G;
    private int H;
    private String I;
    private String J;
    private String K;
    private List<Poi> L;
    private String M;
    private String N;
    private HashMap<String, String> O;
    private int P;
    private int Q;
    private int a;
    private String b;
    private double c;
    private double d;
    private boolean e;
    private double f;
    private boolean g;
    private float h;
    private boolean i;
    private float j;
    private boolean k;
    private int l;
    private float m;
    private String n;
    private boolean o;
    private String p;
    private String q;
    private String r;
    private String s;
    private boolean t;
    private Address u;
    private String v;
    private String w;
    private String x;
    private boolean y;
    private int z;

    public BDLocation() {
        this.a = 0;
        this.b = null;
        this.c = Double.MIN_VALUE;
        this.d = Double.MIN_VALUE;
        this.e = false;
        this.f = Double.MIN_VALUE;
        this.g = false;
        this.h = 0.0f;
        this.i = false;
        this.j = 0.0f;
        this.k = false;
        this.l = -1;
        this.m = -1.0f;
        this.n = null;
        this.o = false;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = false;
        this.u = new Address.Builder().build();
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = false;
        this.z = 0;
        this.A = 1;
        this.B = null;
        this.D = Constants.MAIN_VERSION_TAG;
        this.E = -1;
        this.F = 0;
        this.G = 2;
        this.H = 0;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.N = null;
        this.O = new HashMap<>();
        this.P = 0;
        this.Q = 0;
    }

    private BDLocation(Parcel parcel) {
        this.a = 0;
        this.b = null;
        this.c = Double.MIN_VALUE;
        this.d = Double.MIN_VALUE;
        this.e = false;
        this.f = Double.MIN_VALUE;
        this.g = false;
        this.h = 0.0f;
        this.i = false;
        this.j = 0.0f;
        this.k = false;
        this.l = -1;
        this.m = -1.0f;
        this.n = null;
        this.o = false;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = false;
        this.u = new Address.Builder().build();
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = false;
        this.z = 0;
        this.A = 1;
        this.B = null;
        this.D = Constants.MAIN_VERSION_TAG;
        this.E = -1;
        this.F = 0;
        this.G = 2;
        this.H = 0;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.N = null;
        this.O = new HashMap<>();
        this.P = 0;
        this.Q = 0;
        this.a = parcel.readInt();
        this.b = parcel.readString();
        this.c = parcel.readDouble();
        this.d = parcel.readDouble();
        this.f = parcel.readDouble();
        this.h = parcel.readFloat();
        this.j = parcel.readFloat();
        this.l = parcel.readInt();
        this.m = parcel.readFloat();
        this.v = parcel.readString();
        this.z = parcel.readInt();
        this.w = parcel.readString();
        this.x = parcel.readString();
        this.B = parcel.readString();
        String string = parcel.readString();
        String string2 = parcel.readString();
        String string3 = parcel.readString();
        String string4 = parcel.readString();
        String string5 = parcel.readString();
        String string6 = parcel.readString();
        parcel.readString();
        String string7 = parcel.readString();
        String string8 = parcel.readString();
        this.u = new Address.Builder().country(string7).countryCode(string8).province(string).city(string2).cityCode(string6).district(string3).street(string4).streetNumber(string5).adcode(parcel.readString()).build();
        boolean[] zArr = new boolean[7];
        this.C = parcel.readInt();
        this.D = parcel.readString();
        this.q = parcel.readString();
        this.r = parcel.readString();
        this.s = parcel.readString();
        this.A = parcel.readInt();
        this.M = parcel.readString();
        this.E = parcel.readInt();
        this.F = parcel.readInt();
        this.G = parcel.readInt();
        this.H = parcel.readInt();
        this.I = parcel.readString();
        this.J = parcel.readString();
        this.K = parcel.readString();
        this.P = parcel.readInt();
        this.N = parcel.readString();
        this.Q = parcel.readInt();
        try {
            parcel.readBooleanArray(zArr);
            this.e = zArr[0];
            this.g = zArr[1];
            this.i = zArr[2];
            this.k = zArr[3];
            this.o = zArr[4];
            this.t = zArr[5];
            this.y = zArr[6];
        } catch (Exception e) {
        }
        ArrayList arrayList = new ArrayList();
        parcel.readList(arrayList, Poi.class.getClassLoader());
        if (arrayList.size() == 0) {
            this.L = null;
        } else {
            this.L = arrayList;
        }
    }

    /* synthetic */ BDLocation(Parcel parcel, a aVar) {
        this(parcel);
    }

    public BDLocation(BDLocation bDLocation) {
        int i = 0;
        this.a = 0;
        this.b = null;
        this.c = Double.MIN_VALUE;
        this.d = Double.MIN_VALUE;
        this.e = false;
        this.f = Double.MIN_VALUE;
        this.g = false;
        this.h = 0.0f;
        this.i = false;
        this.j = 0.0f;
        this.k = false;
        this.l = -1;
        this.m = -1.0f;
        this.n = null;
        this.o = false;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = false;
        this.u = new Address.Builder().build();
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = false;
        this.z = 0;
        this.A = 1;
        this.B = null;
        this.D = Constants.MAIN_VERSION_TAG;
        this.E = -1;
        this.F = 0;
        this.G = 2;
        this.H = 0;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.N = null;
        this.O = new HashMap<>();
        this.P = 0;
        this.Q = 0;
        this.a = bDLocation.a;
        this.b = bDLocation.b;
        this.c = bDLocation.c;
        this.d = bDLocation.d;
        this.e = bDLocation.e;
        this.f = bDLocation.f;
        this.g = bDLocation.g;
        this.h = bDLocation.h;
        this.i = bDLocation.i;
        this.j = bDLocation.j;
        this.k = bDLocation.k;
        this.l = bDLocation.l;
        this.m = bDLocation.m;
        this.n = bDLocation.n;
        this.o = bDLocation.o;
        this.p = bDLocation.p;
        this.t = bDLocation.t;
        this.u = new Address.Builder().country(bDLocation.u.country).countryCode(bDLocation.u.countryCode).province(bDLocation.u.province).city(bDLocation.u.city).cityCode(bDLocation.u.cityCode).district(bDLocation.u.district).street(bDLocation.u.street).streetNumber(bDLocation.u.streetNumber).adcode(bDLocation.u.adcode).build();
        this.v = bDLocation.v;
        this.w = bDLocation.w;
        this.x = bDLocation.x;
        this.A = bDLocation.A;
        this.z = bDLocation.z;
        this.y = bDLocation.y;
        this.B = bDLocation.B;
        this.C = bDLocation.C;
        this.D = bDLocation.D;
        this.q = bDLocation.q;
        this.r = bDLocation.r;
        this.s = bDLocation.s;
        this.E = bDLocation.E;
        this.F = bDLocation.F;
        this.G = bDLocation.F;
        this.H = bDLocation.H;
        this.I = bDLocation.I;
        this.J = bDLocation.J;
        this.K = bDLocation.K;
        this.P = bDLocation.P;
        this.N = bDLocation.N;
        if (bDLocation.L == null) {
            this.L = null;
        } else {
            ArrayList arrayList = new ArrayList();
            while (true) {
                int i2 = i;
                if (i2 >= bDLocation.L.size()) {
                    break;
                }
                Poi poi = bDLocation.L.get(i2);
                arrayList.add(new Poi(poi.getId(), poi.getName(), poi.getRank()));
                i = i2 + 1;
            }
            this.L = arrayList;
        }
        this.M = bDLocation.M;
        this.O = bDLocation.O;
        this.Q = bDLocation.Q;
    }

    public BDLocation(String str) throws JSONException {
        boolean z;
        JSONObject jSONObject;
        String string;
        boolean z2;
        this.a = 0;
        this.b = null;
        this.c = Double.MIN_VALUE;
        this.d = Double.MIN_VALUE;
        this.e = false;
        this.f = Double.MIN_VALUE;
        this.g = false;
        this.h = 0.0f;
        this.i = false;
        this.j = 0.0f;
        this.k = false;
        this.l = -1;
        this.m = -1.0f;
        this.n = null;
        this.o = false;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = false;
        this.u = new Address.Builder().build();
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = false;
        this.z = 0;
        this.A = 1;
        this.B = null;
        this.D = Constants.MAIN_VERSION_TAG;
        this.E = -1;
        this.F = 0;
        this.G = 2;
        this.H = 0;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.N = null;
        this.O = new HashMap<>();
        this.P = 0;
        this.Q = 0;
        if (str == null || str.equals(Constants.MAIN_VERSION_TAG)) {
            return;
        }
        try {
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                JSONObject jSONObject3 = jSONObject2.getJSONObject("result");
                int i = Integer.parseInt(jSONObject3.getString(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_ERROR));
                setLocType(i);
                setTime(jSONObject3.getString("time"));
                if (i == 61) {
                    JSONObject jSONObject4 = jSONObject2.getJSONObject("content");
                    JSONObject jSONObject5 = jSONObject4.getJSONObject("point");
                    setLatitude(Double.parseDouble(jSONObject5.getString("y")));
                    setLongitude(Double.parseDouble(jSONObject5.getString("x")));
                    setRadius(Float.parseFloat(jSONObject4.getString("radius")));
                    setSpeed(Float.parseFloat(jSONObject4.getString(NotifyType.SOUND)));
                    setDirection(Float.parseFloat(jSONObject4.getString("d")));
                    setSatelliteNumber(Integer.parseInt(jSONObject4.getString("n")));
                    if (jSONObject4.has("h")) {
                        try {
                            setAltitude(jSONObject4.getDouble("h"));
                        } catch (Exception e) {
                        }
                    }
                    try {
                        if (jSONObject4.has("in_cn")) {
                            setLocationWhere(Integer.parseInt(jSONObject4.getString("in_cn")));
                        } else {
                            setLocationWhere(1);
                        }
                    } catch (Exception e2) {
                    }
                    if (this.A == 0) {
                        setCoorType("wgs84");
                        return;
                    } else {
                        setCoorType("gcj02");
                        return;
                    }
                }
                if (i != 161) {
                    if (i != 66 && i != 68) {
                        if (i == 167) {
                            setLocationWhere(2);
                            return;
                        }
                        return;
                    }
                    JSONObject jSONObject6 = jSONObject2.getJSONObject("content");
                    JSONObject jSONObject7 = jSONObject6.getJSONObject("point");
                    setLatitude(Double.parseDouble(jSONObject7.getString("y")));
                    setLongitude(Double.parseDouble(jSONObject7.getString("x")));
                    setRadius(Float.parseFloat(jSONObject6.getString("radius")));
                    a(Boolean.valueOf(Boolean.parseBoolean(jSONObject6.getString("isCellChanged"))));
                    setCoorType("gcj02");
                    return;
                }
                JSONObject jSONObject8 = jSONObject2.getJSONObject("content");
                JSONObject jSONObject9 = jSONObject8.getJSONObject("point");
                setLatitude(Double.parseDouble(jSONObject9.getString("y")));
                setLongitude(Double.parseDouble(jSONObject9.getString("x")));
                setRadius(Float.parseFloat(jSONObject8.getString("radius")));
                if (jSONObject8.has("sema")) {
                    JSONObject jSONObject10 = jSONObject8.getJSONObject("sema");
                    if (jSONObject10.has("aptag")) {
                        String string2 = jSONObject10.getString("aptag");
                        if (TextUtils.isEmpty(string2)) {
                            this.q = Constants.MAIN_VERSION_TAG;
                        } else {
                            this.q = string2;
                        }
                    }
                    if (jSONObject10.has("aptagd")) {
                        JSONArray jSONArray = jSONObject10.getJSONObject("aptagd").getJSONArray("pois");
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                            JSONObject jSONObject11 = jSONArray.getJSONObject(i2);
                            arrayList.add(new Poi(jSONObject11.getString("pid"), jSONObject11.getString("pname"), jSONObject11.getDouble("pr")));
                        }
                        this.L = arrayList;
                    }
                    if (jSONObject10.has("poiregion")) {
                        String string3 = jSONObject10.getString("poiregion");
                        if (!TextUtils.isEmpty(string3)) {
                            this.r = string3;
                        }
                    }
                    if (jSONObject10.has("regular")) {
                        String string4 = jSONObject10.getString("regular");
                        if (!TextUtils.isEmpty(string4)) {
                            this.s = string4;
                        }
                    }
                }
                if (jSONObject8.has("addr")) {
                    String string5 = null;
                    String string6 = null;
                    String string7 = null;
                    String string8 = null;
                    String string9 = null;
                    String string10 = null;
                    String string11 = null;
                    String string12 = null;
                    try {
                        z = true;
                        jSONObject = jSONObject8.getJSONObject("addr");
                    } catch (Exception e3) {
                        z = false;
                        jSONObject = null;
                    }
                    if (jSONObject != null) {
                        string5 = Constants.MAIN_VERSION_TAG;
                        string6 = Constants.MAIN_VERSION_TAG;
                        string7 = Constants.MAIN_VERSION_TAG;
                        string8 = Constants.MAIN_VERSION_TAG;
                        string9 = Constants.MAIN_VERSION_TAG;
                        string10 = Constants.MAIN_VERSION_TAG;
                        string11 = Constants.MAIN_VERSION_TAG;
                        string12 = Constants.MAIN_VERSION_TAG;
                        string8 = jSONObject.has("city") ? jSONObject.getString("city") : string8;
                        string9 = jSONObject.has("city_code") ? jSONObject.getString("city_code") : string9;
                        string5 = jSONObject.has("country") ? jSONObject.getString("country") : string5;
                        string6 = jSONObject.has("country_code") ? jSONObject.getString("country_code") : string6;
                        string7 = jSONObject.has("province") ? jSONObject.getString("province") : string7;
                        string10 = jSONObject.has("district") ? jSONObject.getString("district") : string10;
                        string11 = jSONObject.has("street") ? jSONObject.getString("street") : string11;
                        string12 = jSONObject.has("street_number") ? jSONObject.getString("street_number") : string12;
                        if (jSONObject.has("adcode")) {
                            boolean z3 = z;
                            string = jSONObject.getString("adcode");
                            z2 = z3;
                        } else {
                            boolean z4 = z;
                            string = Constants.MAIN_VERSION_TAG;
                            z2 = z4;
                        }
                    } else {
                        try {
                            String[] strArrSplit = jSONObject8.getString("addr").split(",");
                            int length = strArrSplit.length;
                            string7 = length > 0 ? strArrSplit[0] : null;
                            string8 = length > 1 ? strArrSplit[1] : null;
                            string10 = length > 2 ? strArrSplit[2] : null;
                            string11 = length > 3 ? strArrSplit[3] : null;
                            string12 = length > 4 ? strArrSplit[4] : null;
                            string9 = length > 5 ? strArrSplit[5] : null;
                            string5 = length > 6 ? strArrSplit[6] : null;
                            string6 = length > 7 ? strArrSplit[7] : null;
                            string = length > 8 ? strArrSplit[8] : null;
                            z2 = true;
                        } catch (Exception e4) {
                            e4.printStackTrace();
                            string = null;
                            z2 = false;
                        }
                    }
                    if (z2) {
                        this.u = new Address.Builder().country(string5).countryCode(string6).province(string7).city(string8).cityCode(string9).district(string10).street(string11).streetNumber(string12).adcode(string).build();
                        this.o = true;
                    }
                } else {
                    this.o = false;
                    setAddrStr(null);
                }
                if (jSONObject8.has("floor")) {
                    this.v = jSONObject8.getString("floor");
                    if (TextUtils.isEmpty(this.v)) {
                        this.v = null;
                    }
                }
                if (jSONObject8.has("indoor")) {
                    String string13 = jSONObject8.getString("indoor");
                    if (!TextUtils.isEmpty(string13)) {
                        setUserIndoorState(Integer.valueOf(string13).intValue());
                    }
                }
                if (jSONObject8.has("loctp")) {
                    this.B = jSONObject8.getString("loctp");
                    if (TextUtils.isEmpty(this.B)) {
                        this.B = null;
                    }
                }
                if (jSONObject8.has("bldgid")) {
                    this.w = jSONObject8.getString("bldgid");
                    if (TextUtils.isEmpty(this.w)) {
                        this.w = null;
                    }
                }
                if (jSONObject8.has("bldg")) {
                    this.x = jSONObject8.getString("bldg");
                    if (TextUtils.isEmpty(this.x)) {
                        this.x = null;
                    }
                }
                if (jSONObject8.has("ibav")) {
                    String string14 = jSONObject8.getString("ibav");
                    if (TextUtils.isEmpty(string14) || string14.equals(PushConstants.PUSH_TYPE_NOTIFY)) {
                        this.z = 0;
                    } else {
                        this.z = Integer.valueOf(string14).intValue();
                    }
                }
                if (jSONObject8.has("indoorflags")) {
                    try {
                        JSONObject jSONObject12 = jSONObject8.getJSONObject("indoorflags");
                        if (jSONObject12.has("area")) {
                            int iIntValue = Integer.valueOf(jSONObject12.getString("area")).intValue();
                            if (iIntValue == 0) {
                                setIndoorLocationSurpport(2);
                            } else if (iIntValue == 1) {
                                setIndoorLocationSurpport(1);
                            }
                        }
                        if (jSONObject12.has("support")) {
                            setIndoorLocationSource(Integer.valueOf(jSONObject12.getString("support")).intValue());
                        }
                        if (jSONObject12.has("inbldg")) {
                            this.I = jSONObject12.getString("inbldg");
                        }
                        if (jSONObject12.has("inbldgid")) {
                            this.J = jSONObject12.getString("inbldgid");
                        }
                        if (jSONObject12.has("polygon")) {
                            setIndoorSurpportPolygon(jSONObject12.getString("polygon"));
                        }
                        if (jSONObject12.has("ret_fields")) {
                            try {
                                String[] strArrSplit2 = jSONObject12.getString("ret_fields").split("\\|");
                                for (String str2 : strArrSplit2) {
                                    String[] strArrSplit3 = str2.split("=");
                                    this.O.put(strArrSplit3[0], strArrSplit3[1]);
                                }
                            } catch (Exception e5) {
                                e5.printStackTrace();
                            }
                        }
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                if (jSONObject8.has("gpscs")) {
                    setGpsCheckStatus(jSONObject8.getInt("gpscs"));
                } else {
                    setGpsCheckStatus(0);
                }
                try {
                    if (jSONObject8.has("in_cn")) {
                        setLocationWhere(Integer.parseInt(jSONObject8.getString("in_cn")));
                    } else {
                        setLocationWhere(1);
                    }
                } catch (Exception e7) {
                }
                if (this.A == 0) {
                    setCoorType("wgs84");
                } else {
                    setCoorType("gcj02");
                }
            } catch (Error e8) {
                e8.printStackTrace();
                this.a = 0;
                this.o = false;
            }
        } catch (Exception e9) {
            e9.printStackTrace();
            this.a = 0;
            this.o = false;
        }
    }

    private void a(Boolean bool) {
        this.t = bool.booleanValue();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAdCode() {
        return this.u.adcode;
    }

    public Address getAddress() {
        return this.u;
    }

    public String getCity() {
        return this.u.city;
    }

    public String getCoorType() {
        return this.n;
    }

    public String getDistrict() {
        return this.u.district;
    }

    public double getLatitude() {
        return this.c;
    }

    public int getLocType() {
        return this.a;
    }

    public String getLocationDescribe() {
        return this.q;
    }

    public double getLongitude() {
        return this.d;
    }

    public String getNetworkLocationType() {
        return this.B;
    }

    public List<Poi> getPoiList() {
        return this.L;
    }

    public String getProvince() {
        return this.u.province;
    }

    public float getRadius() {
        return this.j;
    }

    public String getStreet() {
        return this.u.street;
    }

    public String getTime() {
        return this.b;
    }

    public int getUserIndoorState() {
        return this.E;
    }

    public boolean hasAddr() {
        return this.o;
    }

    public void setAddr(Address address) {
        if (address != null) {
            this.u = address;
            this.o = true;
        }
    }

    public void setAddrStr(String str) {
        this.p = str;
        if (str == null) {
            this.o = false;
        } else {
            this.o = true;
        }
    }

    public void setAltitude(double d) {
        this.f = d;
        this.e = true;
    }

    public void setCoorType(String str) {
        this.n = str;
    }

    public void setDirection(float f) {
        this.m = f;
    }

    public void setGpsCheckStatus(int i) {
        this.Q = i;
    }

    public void setIndoorLocationSource(int i) {
        this.H = i;
    }

    public void setIndoorLocationSurpport(int i) {
        this.F = i;
    }

    public void setIndoorSurpportPolygon(String str) {
        this.K = str;
    }

    public void setLatitude(double d) {
        this.c = d;
    }

    public void setLocType(int i) {
        this.a = i;
        switch (i) {
            case 61:
                setLocTypeDescription("GPS location successful!");
                setUserIndoorState(0);
                break;
            case 62:
                setLocTypeDescription("Location failed beacuse we can not get any loc information!");
                break;
            case 63:
            case 67:
                setLocTypeDescription("Offline location failed, please check the net (wifi/cell)!");
                break;
            case 66:
                setLocTypeDescription("Offline location successful!");
                break;
            case 161:
                setLocTypeDescription("NetWork location successful!");
                break;
            case 162:
                setLocTypeDescription("NetWork location failed because baidu location service can not decrypt the request query, please check the so file !");
                break;
            case 167:
                setLocTypeDescription("NetWork location failed because baidu location service can not caculate the location!");
                break;
            case HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED /* 505 */:
                setLocTypeDescription("NetWork location failed because baidu location service check the key is unlegal, please check the key in AndroidManifest.xml !");
                break;
            default:
                setLocTypeDescription("UnKnown!");
                break;
        }
    }

    public void setLocTypeDescription(String str) {
        this.M = str;
    }

    public void setLocationDescribe(String str) {
        this.q = str;
    }

    public void setLocationID(String str) {
        this.N = str;
    }

    public void setLocationWhere(int i) {
        this.A = i;
    }

    public void setLongitude(double d) {
        this.d = d;
    }

    public void setOperators(int i) {
        this.C = i;
    }

    public void setPoiList(List<Poi> list) {
        this.L = list;
    }

    public void setRadius(float f) {
        this.j = f;
        this.i = true;
    }

    public void setSatelliteNumber(int i) {
        this.l = i;
    }

    public void setSpeed(float f) {
        this.h = f;
        this.g = true;
    }

    public void setTime(String str) {
        this.b = str;
        setLocationID(j.a(str));
    }

    public void setUserIndoorState(int i) {
        this.E = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeString(this.b);
        parcel.writeDouble(this.c);
        parcel.writeDouble(this.d);
        parcel.writeDouble(this.f);
        parcel.writeFloat(this.h);
        parcel.writeFloat(this.j);
        parcel.writeInt(this.l);
        parcel.writeFloat(this.m);
        parcel.writeString(this.v);
        parcel.writeInt(this.z);
        parcel.writeString(this.w);
        parcel.writeString(this.x);
        parcel.writeString(this.B);
        parcel.writeString(this.u.province);
        parcel.writeString(this.u.city);
        parcel.writeString(this.u.district);
        parcel.writeString(this.u.street);
        parcel.writeString(this.u.streetNumber);
        parcel.writeString(this.u.cityCode);
        parcel.writeString(this.u.address);
        parcel.writeString(this.u.country);
        parcel.writeString(this.u.countryCode);
        parcel.writeString(this.u.adcode);
        parcel.writeInt(this.C);
        parcel.writeString(this.D);
        parcel.writeString(this.q);
        parcel.writeString(this.r);
        parcel.writeString(this.s);
        parcel.writeInt(this.A);
        parcel.writeString(this.M);
        parcel.writeInt(this.E);
        parcel.writeInt(this.F);
        parcel.writeInt(this.G);
        parcel.writeInt(this.H);
        parcel.writeString(this.I);
        parcel.writeString(this.J);
        parcel.writeString(this.K);
        parcel.writeInt(this.P);
        parcel.writeString(this.N);
        parcel.writeInt(this.Q);
        parcel.writeBooleanArray(new boolean[]{this.e, this.g, this.i, this.k, this.o, this.t, this.y});
        parcel.writeList(this.L);
    }
}
