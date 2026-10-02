package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class d extends AsyncTask<Void, Void, a> {
    private static final String ad = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tencent/MicroMsg/oauth_qrcode.png";
    private static String ae;
    private String af;
    private String ag;
    private OAuthListener ah;
    private f ai;
    private String appId;
    private String scope;
    private String signature;

    static class a {
        public OAuthErrCode aj;
        public String ak;
        public String al;
        public String am;
        public int an;
        public String ao;
        public byte[] ap;

        private a() {
        }

        public static a c(byte[] bArr) {
            a aVar = new a();
            if (bArr == null || bArr.length == 0) {
                Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, buf is null");
                aVar.aj = OAuthErrCode.WechatAuth_Err_NetworkErr;
            } else {
                try {
                    try {
                        JSONObject jSONObject = new JSONObject(new String(bArr, "utf-8"));
                        int i = jSONObject.getInt("errcode");
                        if (i != 0) {
                            Log.e("MicroMsg.SDK.GetQRCodeResult", String.format("resp errcode = %d", Integer.valueOf(i)));
                            aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                            aVar.an = i;
                            aVar.ao = jSONObject.optString("errmsg");
                        } else {
                            String string = jSONObject.getJSONObject("qrcode").getString("qrcodebase64");
                            if (string == null || string.length() == 0) {
                                Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBase64 is null");
                                aVar.aj = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
                            } else {
                                byte[] bArrDecode = Base64.decode(string, 0);
                                if (bArrDecode == null || bArrDecode.length == 0) {
                                    Log.e("MicroMsg.SDK.GetQRCodeResult", "parse fail, qrcodeBuf is null");
                                    aVar.aj = OAuthErrCode.WechatAuth_Err_JsonDecodeErr;
                                } else {
                                    aVar.aj = OAuthErrCode.WechatAuth_Err_OK;
                                    aVar.ap = bArrDecode;
                                    aVar.ak = jSONObject.getString("uuid");
                                    aVar.al = jSONObject.getString("appname");
                                    Log.d("MicroMsg.SDK.GetQRCodeResult", String.format("parse succ, save in memory, uuid = %s, appname = %s, imgBufLength = %d", aVar.ak, aVar.al, Integer.valueOf(aVar.ap.length)));
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("MicroMsg.SDK.GetQRCodeResult", String.format("parse json fail, ex = %s", e.getMessage()));
                        aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                    }
                } catch (Exception e2) {
                    Log.e("MicroMsg.SDK.GetQRCodeResult", String.format("parse fail, build String fail, ex = %s", e2.getMessage()));
                    aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                }
            }
            return aVar;
        }
    }

    static {
        ae = null;
        ae = "https://open.weixin.qq.com/connect/sdk/qrconnect?appid=%s&noncestr=%s&timestamp=%s&scope=%s&signature=%s";
    }

    public d(String str, String str2, String str3, String str4, String str5, OAuthListener oAuthListener) {
        this.appId = str;
        this.scope = str2;
        this.af = str3;
        this.ag = str4;
        this.signature = str5;
        this.ah = oAuthListener;
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ a doInBackground(Void[] voidArr) {
        Log.i("MicroMsg.SDK.GetQRCodeTask", "external storage available = false");
        String str = String.format(ae, this.appId, this.af, this.ag, this.scope, this.signature);
        long jCurrentTimeMillis = System.currentTimeMillis();
        byte[] bArrH = e.h(str);
        Log.d("MicroMsg.SDK.GetQRCodeTask", String.format("doInBackground, url = %s, time consumed = %d(ms)", str, Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis)));
        return a.c(bArrH);
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ void onPostExecute(a aVar) {
        a aVar2 = aVar;
        if (aVar2.aj != OAuthErrCode.WechatAuth_Err_OK) {
            Log.e("MicroMsg.SDK.GetQRCodeTask", String.format("onPostExecute, get qrcode fail, OAuthErrCode = %s", aVar2.aj));
            this.ah.onAuthFinish(aVar2.aj, null);
            return;
        }
        Log.d("MicroMsg.SDK.GetQRCodeTask", "onPostExecute, get qrcode success");
        this.ah.onAuthGotQrcode(aVar2.am, aVar2.ap);
        this.ai = new f(aVar2.ak, this.ah);
        f fVar = this.ai;
        if (Build.VERSION.SDK_INT >= 11) {
            fVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        } else {
            fVar.execute(new Void[0]);
        }
    }

    public final boolean q() {
        Log.i("MicroMsg.SDK.GetQRCodeTask", "cancelTask");
        return this.ai == null ? cancel(true) : this.ai.cancel(true);
    }
}
