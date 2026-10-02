package skin.support;

import android.os.AsyncTask;
import android.text.TextUtils;
import skin.support.content.res.SkinCompatResources;
import skin.support.utils.SkinPreference;

/* JADX INFO: Access modifiers changed from: private */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatManager$SkinLoadTask extends AsyncTask<String, Void, String> {
    private final SkinCompatManager$SkinLoaderListener mListener;
    private final SkinCompatManager$SkinLoaderStrategy mStrategy;
    final /* synthetic */ SkinCompatManager this$0;

    SkinCompatManager$SkinLoadTask(SkinCompatManager skinCompatManager, SkinCompatManager$SkinLoaderListener listener, SkinCompatManager$SkinLoaderStrategy strategy) {
        this.this$0 = skinCompatManager;
        this.mListener = listener;
        this.mStrategy = strategy;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        if (this.mListener != null) {
            this.mListener.onStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:27:0x005d  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x005d -> B:28:0x003f). Please report as a decompilation issue!!! */
    @Override // android.os.AsyncTask
    public String doInBackground(String... params) {
        String str;
        synchronized (SkinCompatManager.access$000(this.this$0)) {
            while (SkinCompatManager.access$100(this.this$0)) {
                try {
                    SkinCompatManager.access$000(this.this$0).wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            SkinCompatManager.access$102(this.this$0, true);
        }
        try {
            if (params.length == 1) {
                if (TextUtils.isEmpty(params[0])) {
                    SkinCompatResources.getInstance().reset();
                    str = params[0];
                } else if (!TextUtils.isEmpty(this.mStrategy.loadSkinInBackground(SkinCompatManager.access$200(this.this$0), params[0]))) {
                    str = params[0];
                } else {
                    SkinCompatResources.getInstance().reset();
                    str = null;
                }
            } else {
                SkinCompatResources.getInstance().reset();
                str = null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(String skinName) {
        synchronized (SkinCompatManager.access$000(this.this$0)) {
            try {
                if (skinName != null) {
                    SkinPreference.getInstance().setSkinName(skinName).setSkinStrategy(this.mStrategy.getType()).commitEditor();
                    this.this$0.notifyUpdateSkin();
                    if (this.mListener != null) {
                        this.mListener.onSuccess();
                    }
                } else {
                    SkinPreference.getInstance().setSkinName("").setSkinStrategy(-1).commitEditor();
                    if (this.mListener != null) {
                        this.mListener.onFailed("皮肤资源获取失败");
                    }
                }
                SkinCompatManager.access$102(this.this$0, false);
                SkinCompatManager.access$000(this.this$0).notifyAll();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
