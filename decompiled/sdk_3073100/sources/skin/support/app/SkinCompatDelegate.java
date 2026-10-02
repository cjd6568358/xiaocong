package skin.support.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import skin.support.widget.SkinCompatSupportable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatDelegate implements LayoutInflaterFactory {
    private final Context mContext;
    private SkinCompatViewInflater mSkinCompatViewInflater;
    private List<WeakReference<SkinCompatSupportable>> mSkinHelpers = new ArrayList();

    private SkinCompatDelegate(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View viewCreateView = createView(parent, name, context, attrs);
        if (viewCreateView == 0) {
            return null;
        }
        if (viewCreateView instanceof SkinCompatSupportable) {
            this.mSkinHelpers.add(new WeakReference<>((SkinCompatSupportable) viewCreateView));
            return viewCreateView;
        }
        return viewCreateView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public View createView(View view, String name, Context context, AttributeSet attrs) {
        boolean isPre21 = Build.VERSION.SDK_INT < 21;
        if (this.mSkinCompatViewInflater == null) {
            this.mSkinCompatViewInflater = new SkinCompatViewInflater();
        }
        boolean inheritContext = isPre21 && shouldInheritContext((ViewParent) view);
        return this.mSkinCompatViewInflater.createView(view, name, context, attrs, inheritContext, isPre21, true, VectorEnabledTintResources.shouldBeUsed());
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent != null && (this.mContext instanceof Activity)) {
            View windowDecor = ((Activity) this.mContext).getWindow().getDecorView();
            while (parent != null) {
                if (parent == windowDecor || !(parent instanceof View) || ViewCompat.isAttachedToWindow((View) parent)) {
                    return false;
                }
                parent = parent.getParent();
            }
            return true;
        }
        return false;
    }

    public static SkinCompatDelegate create(Context context) {
        return new SkinCompatDelegate(context);
    }

    public void applySkin() {
        if (this.mSkinHelpers != null && !this.mSkinHelpers.isEmpty()) {
            for (WeakReference<SkinCompatSupportable> weakReference : this.mSkinHelpers) {
                if (weakReference != null && weakReference.get() != null) {
                    weakReference.get().applySkin();
                }
            }
        }
    }
}
