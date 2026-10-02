package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class f extends AsyncTask<Void, Void, a> {
    private OAuthListener ah;
    private String ak;
    private int aq;
    private String url;

    static class a {
        public OAuthErrCode aj;
        public String ar;
        public int as;

        a() {
        }

        public static a d(byte[] bArr) {
            a aVar = new a();
            if (bArr == null || bArr.length == 0) {
                Log.e("MicroMsg.SDK.NoopingResult", "parse fail, buf is null");
                aVar.aj = OAuthErrCode.WechatAuth_Err_NetworkErr;
            } else {
                try {
                    try {
                        JSONObject jSONObject = new JSONObject(new String(bArr, "utf-8"));
                        aVar.as = jSONObject.getInt("wx_errcode");
                        Log.d("MicroMsg.SDK.NoopingResult", String.format("nooping uuidStatusCode = %d", Integer.valueOf(aVar.as)));
                        switch (aVar.as) {
                            case HttpStatus.SC_PAYMENT_REQUIRED /* 402 */:
                                aVar.aj = OAuthErrCode.WechatAuth_Err_Timeout;
                                break;
                            case HttpStatus.SC_FORBIDDEN /* 403 */:
                                aVar.aj = OAuthErrCode.WechatAuth_Err_Cancel;
                                break;
                            case HttpStatus.SC_NOT_FOUND /* 404 */:
                                aVar.aj = OAuthErrCode.WechatAuth_Err_OK;
                                break;
                            case HttpStatus.SC_METHOD_NOT_ALLOWED /* 405 */:
                                aVar.aj = OAuthErrCode.WechatAuth_Err_OK;
                                aVar.ar = jSONObject.getString("wx_code");
                                break;
                            case HttpStatus.SC_REQUEST_TIMEOUT /* 408 */:
                                aVar.aj = OAuthErrCode.WechatAuth_Err_OK;
                                break;
                            case HttpStatus.SC_INTERNAL_SERVER_ERROR /* 500 */:
                                aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                                break;
                            default:
                                aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                                break;
                        }
                    } catch (Exception e) {
                        Log.e("MicroMsg.SDK.NoopingResult", String.format("parse json fail, ex = %s", e.getMessage()));
                        aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                    }
                } catch (Exception e2) {
                    Log.e("MicroMsg.SDK.NoopingResult", String.format("parse fail, build String fail, ex = %s", e2.getMessage()));
                    aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                }
            }
            return aVar;
        }
    }

    public f(String str, OAuthListener oAuthListener) {
        this.ak = str;
        this.ah = oAuthListener;
        this.url = String.format("https://long.open.weixin.qq.com/connect/l/qrconnect?f=json&uuid=%s", str);
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ a doInBackground(Void[] voidArr) {
        if (this.ak == null || this.ak.length() == 0) {
            Log.e("MicroMsg.SDK.NoopingTask", "run fail, uuid is null");
            a aVar = new a();
            aVar.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
            return aVar;
        }
        while (!isCancelled()) {
            String str = this.url + (this.aq == 0 ? Constants.MAIN_VERSION_TAG : "&last=" + this.aq);
            long jCurrentTimeMillis = System.currentTimeMillis();
            byte[] bArrH = e.h(str);
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            a aVarD = a.d(bArrH);
            Log.d("MicroMsg.SDK.NoopingTask", String.format("nooping, url = %s, errCode = %s, uuidStatusCode = %d, time consumed = %d(ms)", str, aVarD.aj.toString(), Integer.valueOf(aVarD.as), Long.valueOf(jCurrentTimeMillis2 - jCurrentTimeMillis)));
            if (aVarD.aj != OAuthErrCode.WechatAuth_Err_OK) {
                Log.e("MicroMsg.SDK.NoopingTask", String.format("nooping fail, errCode = %s, uuidStatusCode = %d", aVarD.aj.toString(), Integer.valueOf(aVarD.as)));
                return aVarD;
            }
            this.aq = aVarD.as;
            if (aVarD.as == g.UUID_SCANED.getCode()) {
                this.ah.onQrcodeScanned();
            } else if (aVarD.as != g.UUID_KEEP_CONNECT.getCode() && aVarD.as == g.UUID_CONFIRM.getCode()) {
                if (aVarD.ar != null && aVarD.ar.length() != 0) {
                    return aVarD;
                }
                Log.e("MicroMsg.SDK.NoopingTask", "nooping fail, confirm with an empty code!!!");
                aVarD.aj = OAuthErrCode.WechatAuth_Err_NormalErr;
                return aVarD;
            }
        }
        Log.i("MicroMsg.SDK.NoopingTask", "IDiffDevOAuth.stopAuth / detach invoked");
        a aVar2 = new a();
        aVar2.aj = OAuthErrCode.WechatAuth_Err_Auth_Stopped;
        return aVar2;
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ void onPostExecute(a aVar) {
        a aVar2 = aVar;
        this.ah.onAuthFinish(aVar2.aj, aVar2.ar);
    }
}
