package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class CompoundButtonCompat {
    private static final CompoundButtonCompatBaseImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new CompoundButtonCompatApi23Impl();
        } else if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new CompoundButtonCompatApi21Impl();
        } else {
            IMPL = new CompoundButtonCompatBaseImpl();
        }
    }

    static class CompoundButtonCompatBaseImpl {
        private static Field sButtonDrawableField;
        private static boolean sButtonDrawableFieldFetched;

        CompoundButtonCompatBaseImpl() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void setButtonTintList(CompoundButton compoundButton, ColorStateList tint) {
            if (compoundButton instanceof TintableCompoundButton) {
                ((TintableCompoundButton) compoundButton).setSupportButtonTintList(tint);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void setButtonTintMode(CompoundButton compoundButton, PorterDuff.Mode tintMode) {
            if (compoundButton instanceof TintableCompoundButton) {
                ((TintableCompoundButton) compoundButton).setSupportButtonTintMode(tintMode);
            }
        }

        public Drawable getButtonDrawable(CompoundButton button) {
            if (!sButtonDrawableFieldFetched) {
                try {
                    sButtonDrawableField = CompoundButton.class.getDeclaredField("mButtonDrawable");
                    sButtonDrawableField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    Log.i("CompoundButtonCompat", "Failed to retrieve mButtonDrawable field", e);
                }
                sButtonDrawableFieldFetched = true;
            }
            if (sButtonDrawableField != null) {
                try {
                    return (Drawable) sButtonDrawableField.get(button);
                } catch (IllegalAccessException e2) {
                    Log.i("CompoundButtonCompat", "Failed to get button drawable via reflection", e2);
                    sButtonDrawableField = null;
                }
            }
            return null;
        }
    }

    static class CompoundButtonCompatApi21Impl extends CompoundButtonCompatBaseImpl {
        CompoundButtonCompatApi21Impl() {
        }

        @Override // android.support.v4.widget.CompoundButtonCompat.CompoundButtonCompatBaseImpl
        public void setButtonTintList(CompoundButton button, ColorStateList tint) {
            button.setButtonTintList(tint);
        }

        @Override // android.support.v4.widget.CompoundButtonCompat.CompoundButtonCompatBaseImpl
        public void setButtonTintMode(CompoundButton button, PorterDuff.Mode tintMode) {
            button.setButtonTintMode(tintMode);
        }
    }

    static class CompoundButtonCompatApi23Impl extends CompoundButtonCompatApi21Impl {
        CompoundButtonCompatApi23Impl() {
        }

        @Override // android.support.v4.widget.CompoundButtonCompat.CompoundButtonCompatBaseImpl
        public Drawable getButtonDrawable(CompoundButton button) {
            return button.getButtonDrawable();
        }
    }

    public static void setButtonTintList(CompoundButton button, ColorStateList tint) {
        IMPL.setButtonTintList(button, tint);
    }

    public static void setButtonTintMode(CompoundButton button, PorterDuff.Mode tintMode) {
        IMPL.setButtonTintMode(button, tintMode);
    }

    public static Drawable getButtonDrawable(CompoundButton button) {
        return IMPL.getButtonDrawable(button);
    }
}
