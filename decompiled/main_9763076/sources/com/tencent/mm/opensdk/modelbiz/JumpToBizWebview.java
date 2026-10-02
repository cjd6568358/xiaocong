package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.utils.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class JumpToBizWebview {

    public static class Req extends BaseReq {
        private static final int EXT_MSG_LENGTH = 1024;
        private static final String TAG = "MicroMsg.SDK.JumpToBizWebview.Req";
        public String extMsg;
        public int scene = 1;
        public String toUserName;
        public int webType;

        @Override // com.tencent.mm.opensdk.modelbase.BaseReq
        public boolean checkArgs() {
            if (this.toUserName == null || this.toUserName.length() <= 0) {
                Log.e(TAG, "checkArgs fail, toUserName is invalid");
                return false;
            }
            if (this.extMsg == null || this.extMsg.length() <= 1024) {
                return true;
            }
            Log.e(TAG, "ext msg is not null, while the length exceed 1024 bytes");
            return false;
        }

        @Override // com.tencent.mm.opensdk.modelbase.BaseReq
        public int getType() {
            return 8;
        }

        @Override // com.tencent.mm.opensdk.modelbase.BaseReq
        public void toBundle(Bundle bundle) {
            super.toBundle(bundle);
            bundle.putString("_wxapi_jump_to_biz_webview_req_to_user_name", this.toUserName);
            bundle.putString("_wxapi_jump_to_biz_webview_req_ext_msg", this.extMsg);
            bundle.putInt("_wxapi_jump_to_biz_webview_req_web_type", this.webType);
            bundle.putInt("_wxapi_jump_to_biz_webview_req_scene", this.scene);
        }
    }
}
