package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatDelegate;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class VectorEnabledTintResources extends Resources {
    private final WeakReference<Context> mContextRef;

    public static boolean shouldBeUsed() {
        return AppCompatDelegate.isCompatVectorFromResourcesEnabled() && Build.VERSION.SDK_INT <= 20;
    }

    public VectorEnabledTintResources(Context context, Resources res) {
        super(res.getAssets(), res.getDisplayMetrics(), res.getConfiguration());
        this.mContextRef = new WeakReference<>(context);
    }

    @Override // android.content.res.Resources
    public Drawable getDrawable(int id) throws Resources.NotFoundException {
        Context context = this.mContextRef.get();
        return context != null ? AppCompatDrawableManager.get().onDrawableLoadedFromResources(context, this, id) : super.getDrawable(id);
    }

    final Drawable superGetDrawable(int id) {
        return super.getDrawable(id);
    }
}
