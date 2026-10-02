package com.tencent.mm.opensdk.diffdev;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface OAuthListener {
    void onAuthFinish(OAuthErrCode oAuthErrCode, String str);

    void onAuthGotQrcode(String str, byte[] bArr);

    void onQrcodeScanned();
}
