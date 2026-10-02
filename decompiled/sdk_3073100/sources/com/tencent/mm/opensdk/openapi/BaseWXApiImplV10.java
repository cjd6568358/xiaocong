package com.tencent.mm.opensdk.openapi;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.tencent.mm.opensdk.channel.MMessageActV2;
import com.tencent.mm.opensdk.channel.a.a;
import com.tencent.mm.opensdk.channel.a.b;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.AddCardToWXCardPackage;
import com.tencent.mm.opensdk.modelbiz.ChooseCardFromWXCardPackage;
import com.tencent.mm.opensdk.modelbiz.CreateChatroom;
import com.tencent.mm.opensdk.modelbiz.HandleScanResult;
import com.tencent.mm.opensdk.modelbiz.JoinChatroom;
import com.tencent.mm.opensdk.modelbiz.OpenWebview;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.mm.opensdk.modelbiz.SubscribeMiniProgramMsg;
import com.tencent.mm.opensdk.modelbiz.WXInvoiceAuthInsert;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelbiz.WXNontaxPay;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessWebview;
import com.tencent.mm.opensdk.modelbiz.WXPayInsurance;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.LaunchFromWX;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.JumpToOfflinePay;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.utils.ILog;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.d;
import java.net.URLEncoder;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class BaseWXApiImplV10 implements IWXAPI {
    protected static final String TAG = "MicroMsg.SDK.WXApiImplV10";
    private static String wxappPayEntryClassname = null;
    protected String appId;
    protected boolean checkSignature;
    protected Context context;
    protected boolean detached = false;

    BaseWXApiImplV10(Context context, String str, boolean z) {
        this.checkSignature = false;
        Log.d(TAG, "<init>, appId = " + str + ", checkSignature = " + z);
        this.context = context;
        this.appId = str;
        this.checkSignature = z;
    }

    private boolean checkSumConsistent(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr.length == 0 || bArr2 == null || bArr2.length == 0) {
            Log.e(TAG, "checkSumConsistent fail, invalid arguments");
            return false;
        }
        if (bArr.length != bArr2.length) {
            Log.e(TAG, "checkSumConsistent fail, length is different");
            return false;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean createChatroom(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/createChatroom"), null, null, new String[]{this.appId, bundle.getString("_wxapi_basereq_transaction", ""), bundle.getString("_wxapi_create_chatroom_group_id", ""), bundle.getString("_wxapi_create_chatroom_chatroom_name", ""), bundle.getString("_wxapi_create_chatroom_chatroom_nickname", ""), bundle.getString("_wxapi_create_chatroom_ext_msg", ""), bundle.getString("_wxapi_basereq_openid", "")}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private String getTokenFromWX(Context context) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/genTokenForOpenSdk"), null, null, new String[]{this.appId, "620824064"}, null);
        if (cursorQuery == null || !cursorQuery.moveToFirst()) {
            return null;
        }
        String string = cursorQuery.getString(0);
        Log.i(TAG, "getTokenFromWX token is " + string);
        cursorQuery.close();
        return string;
    }

    private boolean handleWxInternalRespType(String str, IWXAPIEventHandler iWXAPIEventHandler) {
        Log.i(TAG, "handleWxInternalRespType, extInfo = " + str);
        try {
            Uri uri = Uri.parse(str);
            String queryParameter = uri.getQueryParameter("wx_internal_resptype");
            Log.i(TAG, "handleWxInternalRespType, respType = " + queryParameter);
            if (d.i(queryParameter)) {
                Log.e(TAG, "handleWxInternalRespType fail, respType is null");
                return false;
            }
            if (queryParameter.equals("subscribemessage")) {
                SubscribeMessage.Resp resp = new SubscribeMessage.Resp();
                String queryParameter2 = uri.getQueryParameter("ret");
                if (queryParameter2 != null && queryParameter2.length() > 0) {
                    resp.errCode = d.j(queryParameter2);
                }
                resp.openId = uri.getQueryParameter("openid");
                resp.templateID = uri.getQueryParameter("template_id");
                resp.scene = d.j(uri.getQueryParameter("scene"));
                resp.action = uri.getQueryParameter("action");
                resp.reserved = uri.getQueryParameter("reserved");
                iWXAPIEventHandler.onResp(resp);
                return true;
            }
            if (queryParameter.contains("invoice_auth_insert")) {
                WXInvoiceAuthInsert.Resp resp2 = new WXInvoiceAuthInsert.Resp();
                String queryParameter3 = uri.getQueryParameter("ret");
                if (queryParameter3 != null && queryParameter3.length() > 0) {
                    resp2.errCode = d.j(queryParameter3);
                }
                resp2.wxOrderId = uri.getQueryParameter("wx_order_id");
                iWXAPIEventHandler.onResp(resp2);
                return true;
            }
            if (queryParameter.contains("payinsurance")) {
                WXPayInsurance.Resp resp3 = new WXPayInsurance.Resp();
                String queryParameter4 = uri.getQueryParameter("ret");
                if (queryParameter4 != null && queryParameter4.length() > 0) {
                    resp3.errCode = d.j(queryParameter4);
                }
                resp3.wxOrderId = uri.getQueryParameter("wx_order_id");
                iWXAPIEventHandler.onResp(resp3);
                return true;
            }
            if (queryParameter.contains("nontaxpay")) {
                WXNontaxPay.Resp resp4 = new WXNontaxPay.Resp();
                String queryParameter5 = uri.getQueryParameter("ret");
                if (queryParameter5 != null && queryParameter5.length() > 0) {
                    resp4.errCode = d.j(queryParameter5);
                }
                resp4.wxOrderId = uri.getQueryParameter("wx_order_id");
                iWXAPIEventHandler.onResp(resp4);
                return true;
            }
            if (!"subscribeminiprogrammsg".equals(queryParameter) && !"5".equals(queryParameter)) {
                Log.e(TAG, "this open sdk version not support the request type");
                return false;
            }
            SubscribeMiniProgramMsg.Resp resp5 = new SubscribeMiniProgramMsg.Resp();
            String queryParameter6 = uri.getQueryParameter("ret");
            if (queryParameter6 != null && queryParameter6.length() > 0) {
                resp5.errCode = d.j(queryParameter6);
            }
            resp5.openId = uri.getQueryParameter("openid");
            resp5.unionId = uri.getQueryParameter("unionid");
            resp5.nickname = uri.getQueryParameter("nickname");
            resp5.errStr = uri.getQueryParameter("errmsg");
            iWXAPIEventHandler.onResp(resp5);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "handleWxInternalRespType fail, ex = " + e.getMessage());
            return false;
        }
    }

    private boolean joinChatroom(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/joinChatroom"), null, null, new String[]{this.appId, bundle.getString("_wxapi_basereq_transaction", ""), bundle.getString("_wxapi_join_chatroom_group_id", ""), bundle.getString("_wxapi_join_chatroom_chatroom_nickname", ""), bundle.getString("_wxapi_join_chatroom_ext_msg", ""), bundle.getString("_wxapi_basereq_openid", "")}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendAddCardToWX(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/addCardToWX"), null, null, new String[]{this.appId, bundle.getString("_wxapi_add_card_to_wx_card_list"), bundle.getString("_wxapi_basereq_transaction")}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendChooseCardFromWX(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/chooseCardFromWX"), null, null, new String[]{bundle.getString("_wxapi_choose_card_from_wx_card_app_id"), bundle.getString("_wxapi_choose_card_from_wx_card_location_id"), bundle.getString("_wxapi_choose_card_from_wx_card_sign_type"), bundle.getString("_wxapi_choose_card_from_wx_card_card_sign"), bundle.getString("_wxapi_choose_card_from_wx_card_time_stamp"), bundle.getString("_wxapi_choose_card_from_wx_card_nonce_str"), bundle.getString("_wxapi_choose_card_from_wx_card_card_id"), bundle.getString("_wxapi_choose_card_from_wx_card_card_type"), bundle.getString("_wxapi_choose_card_from_wx_card_can_multi_select"), bundle.getString("_wxapi_basereq_transaction")}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendHandleScanResult(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/handleScanResult"), null, null, new String[]{this.appId, bundle.getString("_wxapi_scan_qrcode_result")}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendInvoiceAuthInsert(Context context, BaseReq baseReq) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "2", URLEncoder.encode(String.format("url=%s", URLEncoder.encode(((WXInvoiceAuthInsert.Req) baseReq).url)))}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendJumpToBizProfileReq(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToBizProfile"), null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_biz_profile_req_to_user_name"), bundle.getString("_wxapi_jump_to_biz_profile_req_ext_msg"), new StringBuilder().append(bundle.getInt("_wxapi_jump_to_biz_profile_req_scene")).toString(), new StringBuilder().append(bundle.getInt("_wxapi_jump_to_biz_profile_req_profile_type")).toString()}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendJumpToBizTempSessionReq(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToBizTempSession"), null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_biz_webview_req_to_user_name"), bundle.getString("_wxapi_jump_to_biz_webview_req_session_from"), new StringBuilder().append(bundle.getInt("_wxapi_jump_to_biz_webview_req_show_type")).toString()}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendJumpToBizWebviewReq(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToBizProfile"), null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_biz_webview_req_to_user_name"), bundle.getString("_wxapi_jump_to_biz_webview_req_ext_msg"), new StringBuilder().append(bundle.getInt("_wxapi_jump_to_biz_webview_req_scene")).toString()}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendJumpToOfflinePayReq(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToOfflinePay"), null, null, new String[]{this.appId}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendLaunchWXMiniprogram(Context context, BaseReq baseReq) {
        WXLaunchMiniProgram.Req req = (WXLaunchMiniProgram.Req) baseReq;
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/launchWXMiniprogram"), null, null, new String[]{this.appId, req.userName, req.path, new StringBuilder().append(req.miniprogramType).toString()}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendNonTaxPay(Context context, BaseReq baseReq) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "3", URLEncoder.encode(String.format("url=%s", URLEncoder.encode(((WXNontaxPay.Req) baseReq).url)))}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendOpenBusiLuckyMoney(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusiLuckyMoney"), null, null, new String[]{this.appId, bundle.getString("_wxapi_open_busi_lucky_money_timeStamp"), bundle.getString("_wxapi_open_busi_lucky_money_nonceStr"), bundle.getString("_wxapi_open_busi_lucky_money_signType"), bundle.getString("_wxapi_open_busi_lucky_money_signature"), bundle.getString("_wxapi_open_busi_lucky_money_package")}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendOpenBusinessWebview(Context context, BaseReq baseReq) {
        WXOpenBusinessWebview.Req req = (WXOpenBusinessWebview.Req) baseReq;
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusinessWebview");
        String string = "";
        if (req.queryInfo != null && req.queryInfo.size() > 0) {
            string = new JSONObject(req.queryInfo).toString();
        }
        Cursor cursorQuery = contentResolver.query(uri, null, null, new String[]{this.appId, new StringBuilder().append(req.businessType).toString(), string}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendOpenRankListReq(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openRankList"), null, null, new String[0], null);
        if (cursorQuery == null) {
            return true;
        }
        cursorQuery.close();
        return true;
    }

    private boolean sendOpenWebview(Context context, Bundle bundle) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openWebview"), null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_webview_url"), bundle.getString("_wxapi_basereq_transaction")}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendPayInSurance(Context context, BaseReq baseReq) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "4", URLEncoder.encode(String.format("url=%s", URLEncoder.encode(((WXPayInsurance.Req) baseReq).url)))}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendPayReq(Context context, Bundle bundle) {
        if (wxappPayEntryClassname == null) {
            wxappPayEntryClassname = new MMSharedPreferences(context).getString("_wxapp_pay_entry_classname_", null);
            Log.d(TAG, "pay, set wxappPayEntryClassname = " + wxappPayEntryClassname);
            if (wxappPayEntryClassname == null) {
                try {
                    wxappPayEntryClassname = context.getPackageManager().getApplicationInfo("com.tencent.mm", 128).metaData.getString("com.tencent.mm.BuildInfo.OPEN_SDK_PAY_ENTRY_CLASSNAME", null);
                } catch (Exception e) {
                    Log.e(TAG, "get from metaData failed : " + e.getMessage());
                }
            }
            if (wxappPayEntryClassname == null) {
                Log.e(TAG, "pay fail, wxappPayEntryClassname is null");
                return false;
            }
        }
        MMessageActV2.Args args = new MMessageActV2.Args();
        args.bundle = bundle;
        args.targetPkgName = "com.tencent.mm";
        args.targetClassName = wxappPayEntryClassname;
        return MMessageActV2.send(context, args);
    }

    private boolean sendSubscribeMessage(Context context, BaseReq baseReq) {
        SubscribeMessage.Req req = (SubscribeMessage.Req) baseReq;
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "1", String.valueOf(req.scene), req.templateID, req.reserved}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    private boolean sendSubscribeMiniProgramMsg(Context context, BaseReq baseReq) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "5", ((SubscribeMiniProgramMsg.Req) baseReq).miniProgramAppId}, null);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return true;
    }

    public void detach() {
        Log.d(TAG, "detach");
        this.detached = true;
        this.context = null;
    }

    public int getWXAppSupportAPI() {
        if (this.detached) {
            throw new IllegalStateException("getWXAppSupportAPI fail, WXMsgImpl has been detached");
        }
        if (!isWXAppInstalled()) {
            Log.e(TAG, "open wx app failed, not installed or signature check failed");
            return 0;
        }
        int i = new MMSharedPreferences(this.context).getInt("_build_info_sdk_int_", 0);
        if (i != 0) {
            return i;
        }
        try {
            return this.context.getPackageManager().getApplicationInfo("com.tencent.mm", 128).metaData.getInt("com.tencent.mm.BuildInfo.OPEN_SDK_VERSION", 0);
        } catch (Exception e) {
            Log.e(TAG, "get from metaData failed : " + e.getMessage());
            return i;
        }
    }

    public boolean handleIntent(Intent intent, IWXAPIEventHandler iWXAPIEventHandler) {
        try {
            if (!WXApiImplComm.isIntentFromWx(intent, "com.tencent.mm.openapi.token")) {
                Log.i(TAG, "handleIntent fail, intent not from weixin msg");
                return false;
            }
            if (this.detached) {
                throw new IllegalStateException("handleIntent fail, WXMsgImpl has been detached");
            }
            String stringExtra = intent.getStringExtra("_mmessage_content");
            int intExtra = intent.getIntExtra("_mmessage_sdkVersion", 0);
            String stringExtra2 = intent.getStringExtra("_mmessage_appPackage");
            if (stringExtra2 == null || stringExtra2.length() == 0) {
                Log.e(TAG, "invalid argument");
                return false;
            }
            if (!checkSumConsistent(intent.getByteArrayExtra("_mmessage_checksum"), b.a(stringExtra, intExtra, stringExtra2))) {
                Log.e(TAG, "checksum fail");
                return false;
            }
            int intExtra2 = intent.getIntExtra("_wxapi_command_type", 0);
            Log.i(TAG, "handleIntent, cmd = " + intExtra2);
            switch (intExtra2) {
                case 1:
                    iWXAPIEventHandler.onResp(new SendAuth.Resp(intent.getExtras()));
                    return true;
                case 2:
                    iWXAPIEventHandler.onResp(new SendMessageToWX.Resp(intent.getExtras()));
                    return true;
                case 3:
                    iWXAPIEventHandler.onReq(new GetMessageFromWX.Req(intent.getExtras()));
                    return true;
                case 4:
                    ShowMessageFromWX.Req req = new ShowMessageFromWX.Req(intent.getExtras());
                    String str = req.message.messageExt;
                    if (str != null && str.contains("wx_internal_resptype")) {
                        boolean zHandleWxInternalRespType = handleWxInternalRespType(str, iWXAPIEventHandler);
                        Log.i(TAG, "handleIntent, extInfo contains wx_internal_resptype, ret = " + zHandleWxInternalRespType);
                        return zHandleWxInternalRespType;
                    }
                    if (str != null && str.contains("openbusinesswebview")) {
                        try {
                            Uri uri = Uri.parse(str);
                            if (uri != null && "openbusinesswebview".equals(uri.getHost())) {
                                WXOpenBusinessWebview.Resp resp = new WXOpenBusinessWebview.Resp();
                                String queryParameter = uri.getQueryParameter("ret");
                                if (queryParameter != null && queryParameter.length() > 0) {
                                    resp.errCode = d.j(queryParameter);
                                }
                                resp.resultInfo = uri.getQueryParameter("resultInfo");
                                resp.errStr = uri.getQueryParameter("errmsg");
                                String queryParameter2 = uri.getQueryParameter("type");
                                if (queryParameter2 != null && queryParameter2.length() > 0) {
                                    resp.businessType = d.j(queryParameter2);
                                }
                                iWXAPIEventHandler.onResp(resp);
                                return true;
                            }
                            Log.d(TAG, "not openbusinesswebview %" + str);
                        } catch (Exception e) {
                            Log.e(TAG, "parse fail, ex = " + e.getMessage());
                        }
                    }
                    iWXAPIEventHandler.onReq(req);
                    return true;
                case 5:
                    iWXAPIEventHandler.onResp(new PayResp(intent.getExtras()));
                    return true;
                case 6:
                    iWXAPIEventHandler.onReq(new LaunchFromWX.Req(intent.getExtras()));
                    return true;
                case 7:
                case 8:
                case 10:
                case 11:
                case 13:
                case 18:
                case 20:
                case 21:
                case 22:
                case 23:
                default:
                    Log.e(TAG, "unknown cmd = " + intExtra2);
                    return false;
                case 9:
                    iWXAPIEventHandler.onResp(new AddCardToWXCardPackage.Resp(intent.getExtras()));
                    return true;
                case 12:
                    iWXAPIEventHandler.onResp(new OpenWebview.Resp(intent.getExtras()));
                    return true;
                case 14:
                    iWXAPIEventHandler.onResp(new CreateChatroom.Resp(intent.getExtras()));
                    return true;
                case 15:
                    iWXAPIEventHandler.onResp(new JoinChatroom.Resp(intent.getExtras()));
                    return true;
                case 16:
                    iWXAPIEventHandler.onResp(new ChooseCardFromWXCardPackage.Resp(intent.getExtras()));
                    return true;
                case 17:
                    iWXAPIEventHandler.onResp(new HandleScanResult.Resp(intent.getExtras()));
                    return true;
                case 19:
                    iWXAPIEventHandler.onResp(new WXLaunchMiniProgram.Resp(intent.getExtras()));
                    return true;
                case 24:
                    iWXAPIEventHandler.onResp(new JumpToOfflinePay.Resp(intent.getExtras()));
                    return true;
                case 25:
                    iWXAPIEventHandler.onResp(new WXOpenBusinessWebview.Resp(intent.getExtras()));
                    return true;
            }
            Log.e(TAG, "handleIntent fail, ex = " + e.getMessage());
            return false;
        } catch (Exception e2) {
            Log.e(TAG, "handleIntent fail, ex = " + e2.getMessage());
            return false;
        }
    }

    public boolean isWXAppInstalled() {
        if (this.detached) {
            throw new IllegalStateException("isWXAppInstalled fail, WXMsgImpl has been detached");
        }
        try {
            PackageInfo packageInfo = this.context.getPackageManager().getPackageInfo("com.tencent.mm", 64);
            if (packageInfo == null) {
                return false;
            }
            return WXApiImplComm.validateAppSignature(this.context, packageInfo.signatures, this.checkSignature);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean openWXApp() {
        if (this.detached) {
            throw new IllegalStateException("openWXApp fail, WXMsgImpl has been detached");
        }
        if (!isWXAppInstalled()) {
            Log.e(TAG, "open wx app failed, not installed or signature check failed");
            return false;
        }
        try {
            this.context.startActivity(this.context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm"));
            return true;
        } catch (Exception e) {
            Log.e(TAG, "startActivity fail, exception = " + e.getMessage());
            return false;
        }
    }

    public boolean registerApp(String str) {
        return registerApp(str, 0L);
    }

    public boolean registerApp(String str, long j) {
        if (this.detached) {
            throw new IllegalStateException("registerApp fail, WXMsgImpl has been detached");
        }
        if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.e(TAG, "register app failed for wechat app signature check failed");
            return false;
        }
        Log.d(TAG, "registerApp, appId = " + str);
        if (str != null) {
            this.appId = str;
        }
        Log.d(TAG, "registerApp, appId = " + str);
        if (str != null) {
            this.appId = str;
        }
        Log.d(TAG, "register app " + this.context.getPackageName());
        a.a aVar = new a.a();
        aVar.W = "com.tencent.mm";
        aVar.action = "com.tencent.mm.plugin.openapi.Intent.ACTION_HANDLE_APP_REGISTER";
        aVar.content = "weixin://registerapp?appid=" + this.appId;
        aVar.X = j;
        return a.a(this.context, aVar);
    }

    public boolean sendReq(BaseReq baseReq) {
        if (this.detached) {
            throw new IllegalStateException("sendReq fail, WXMsgImpl has been detached");
        }
        if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.e(TAG, "sendReq failed for wechat app signature check failed");
            return false;
        }
        if (!baseReq.checkArgs()) {
            Log.e(TAG, "sendReq checkArgs fail");
            return false;
        }
        Log.i(TAG, "sendReq, req type = " + baseReq.getType());
        Bundle bundle = new Bundle();
        baseReq.toBundle(bundle);
        if (baseReq.getType() == 5) {
            return sendPayReq(this.context, bundle);
        }
        if (baseReq.getType() == 7) {
            return sendJumpToBizProfileReq(this.context, bundle);
        }
        if (baseReq.getType() == 8) {
            return sendJumpToBizWebviewReq(this.context, bundle);
        }
        if (baseReq.getType() == 10) {
            return sendJumpToBizTempSessionReq(this.context, bundle);
        }
        if (baseReq.getType() == 9) {
            return sendAddCardToWX(this.context, bundle);
        }
        if (baseReq.getType() == 16) {
            return sendChooseCardFromWX(this.context, bundle);
        }
        if (baseReq.getType() == 11) {
            return sendOpenRankListReq(this.context, bundle);
        }
        if (baseReq.getType() == 12) {
            return sendOpenWebview(this.context, bundle);
        }
        if (baseReq.getType() == 25) {
            return sendOpenBusinessWebview(this.context, baseReq);
        }
        if (baseReq.getType() == 13) {
            return sendOpenBusiLuckyMoney(this.context, bundle);
        }
        if (baseReq.getType() == 14) {
            return createChatroom(this.context, bundle);
        }
        if (baseReq.getType() == 15) {
            return joinChatroom(this.context, bundle);
        }
        if (baseReq.getType() == 17) {
            return sendHandleScanResult(this.context, bundle);
        }
        if (baseReq.getType() == 18) {
            return sendSubscribeMessage(this.context, baseReq);
        }
        if (baseReq.getType() == 23) {
            return sendSubscribeMiniProgramMsg(this.context, baseReq);
        }
        if (baseReq.getType() == 19) {
            return sendLaunchWXMiniprogram(this.context, baseReq);
        }
        if (baseReq.getType() == 20) {
            return sendInvoiceAuthInsert(this.context, baseReq);
        }
        if (baseReq.getType() == 21) {
            return sendNonTaxPay(this.context, baseReq);
        }
        if (baseReq.getType() == 22) {
            return sendPayInSurance(this.context, baseReq);
        }
        if (baseReq.getType() == 24) {
            return sendJumpToOfflinePayReq(this.context, bundle);
        }
        if (baseReq.getType() == 2 && bundle.getInt("_wxapi_sendmessagetowx_req_media_type") == 36) {
            SendMessageToWX.Req req = (SendMessageToWX.Req) baseReq;
            if (getWXAppSupportAPI() < 620756993) {
                WXWebpageObject wXWebpageObject = new WXWebpageObject();
                wXWebpageObject.webpageUrl = bundle.getString("_wxminiprogram_webpageurl");
                req.message.mediaObject = wXWebpageObject;
            } else {
                WXMiniProgramObject wXMiniProgramObject = req.message.mediaObject;
                wXMiniProgramObject.userName += "@app";
                String str = wXMiniProgramObject.path;
                if (!d.i(str)) {
                    String[] strArrSplit = str.split("\\?");
                    wXMiniProgramObject.path = strArrSplit.length > 1 ? strArrSplit[0] + ".html?" + strArrSplit[1] : strArrSplit[0] + ".html";
                }
            }
            if (req.scene != 3) {
                req.scene = 0;
            }
            baseReq.toBundle(bundle);
        }
        String tokenFromWX = getTokenFromWX(this.context);
        MMessageActV2.Args args = new MMessageActV2.Args();
        args.bundle = bundle;
        args.content = "weixin://sendreq?appid=" + this.appId;
        args.targetPkgName = "com.tencent.mm";
        args.targetClassName = "com.tencent.mm.plugin.base.stub.WXEntryActivity";
        args.token = tokenFromWX;
        return MMessageActV2.send(this.context, args);
    }

    public boolean sendResp(BaseResp baseResp) {
        if (this.detached) {
            throw new IllegalStateException("sendResp fail, WXMsgImpl has been detached");
        }
        if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.e(TAG, "sendResp failed for wechat app signature check failed");
            return false;
        }
        if (!baseResp.checkArgs()) {
            Log.e(TAG, "sendResp checkArgs fail");
            return false;
        }
        Bundle bundle = new Bundle();
        baseResp.toBundle(bundle);
        MMessageActV2.Args args = new MMessageActV2.Args();
        args.bundle = bundle;
        args.content = "weixin://sendresp?appid=" + this.appId;
        args.targetPkgName = "com.tencent.mm";
        args.targetClassName = "com.tencent.mm.plugin.base.stub.WXEntryActivity";
        return MMessageActV2.send(this.context, args);
    }

    public void setLogImpl(ILog iLog) {
        Log.setLogImpl(iLog);
    }

    public void unregisterApp() {
        if (this.detached) {
            throw new IllegalStateException("unregisterApp fail, WXMsgImpl has been detached");
        }
        if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.e(TAG, "unregister app failed for wechat app signature check failed");
            return;
        }
        Log.d(TAG, "unregisterApp, appId = " + this.appId);
        if (this.appId == null || this.appId.length() == 0) {
            Log.e(TAG, "unregisterApp fail, appId is empty");
            return;
        }
        Log.d(TAG, "unregister app " + this.context.getPackageName());
        a.a aVar = new a.a();
        aVar.W = "com.tencent.mm";
        aVar.action = "com.tencent.mm.plugin.openapi.Intent.ACTION_HANDLE_APP_UNREGISTER";
        aVar.content = "weixin://unregisterapp?appid=" + this.appId;
        a.a(this.context, aVar);
    }
}
