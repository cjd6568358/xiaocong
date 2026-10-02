package com.youzan.jsbridge.internal;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.youzan.jsbridge.method.JsMethod;
import com.youzan.jsbridge.method.JsMethodCompat;
import com.youzan.jsbridge.util.Logger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class JsMethodParser {
    private Gson mGson = new Gson();

    public JsMethod parse(JsMethodModel jsMethodModel) {
        try {
            JsMethod method = (JsMethod) this.mGson.fromJson(jsMethodModel.args.get(0), JsMethod.class);
            if (method == null || TextUtils.isEmpty(method.name)) {
                return null;
            }
            return method;
        } catch (JsonSyntaxException e) {
            Logger.d("JsMethodParser", "failed to parse new js method");
            return null;
        }
    }

    public JsMethodCompat parseCompat(JsMethodModel jsMethodModel) {
        return new JsMethodCompat(jsMethodModel.method, jsMethodModel.args.get(0));
    }

    public JsMethodModel deserialize(String data) {
        JsMethodModel methodModel = (JsMethodModel) this.mGson.fromJson(data, JsMethodModel.class);
        if (methodModel == null || methodModel.types == null || methodModel.types.size() == 0 || !methodModel.types.get(0).equalsIgnoreCase("String")) {
            return null;
        }
        return methodModel;
    }
}
