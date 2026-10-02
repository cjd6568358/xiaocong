package com.youzan.androidsdk.event;

import android.content.Context;
import com.youzan.androidsdk.model.goods.GoodsShareModel;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class AbsShareEvent implements Event {
    public abstract void call(Context context, GoodsShareModel goodsShareModel);

    public String subscribe() {
        return EventAPI.EVENT_SHARE;
    }

    public final void call(Context context, String data) {
        try {
            GoodsShareModel model = new GoodsShareModel(new JSONObject(data));
            call(context, model);
        } catch (JSONException e) {
        }
    }
}
