package skin.support.constraint.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import skin.support.app.SkinLayoutInflater;
import skin.support.constraint.SkinCompatConstraintLayout;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SkinConstraintViewInflater implements SkinLayoutInflater {
    @Override // skin.support.app.SkinLayoutInflater
    public View createView(Context context, String name, AttributeSet attrs) {
        switch (name) {
            case "android.support.constraint.ConstraintLayout":
                return new SkinCompatConstraintLayout(context, attrs);
            default:
                return null;
        }
    }
}
