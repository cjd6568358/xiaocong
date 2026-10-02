package android.support.v4.app;

import android.os.Build;
import android.os.Bundle;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class RemoteInput extends RemoteInputCompatBase.RemoteInput {
    public static final RemoteInputCompatBase.RemoteInput.Factory FACTORY;
    private static final Impl IMPL;
    private final boolean mAllowFreeFormTextInput;
    private final Set<String> mAllowedDataTypes;
    private final CharSequence[] mChoices;
    private final Bundle mExtras;
    private final CharSequence mLabel;
    private final String mResultKey;

    interface Impl {
    }

    @Override // android.support.v4.app.RemoteInputCompatBase.RemoteInput
    public String getResultKey() {
        return this.mResultKey;
    }

    @Override // android.support.v4.app.RemoteInputCompatBase.RemoteInput
    public CharSequence getLabel() {
        return this.mLabel;
    }

    @Override // android.support.v4.app.RemoteInputCompatBase.RemoteInput
    public CharSequence[] getChoices() {
        return this.mChoices;
    }

    @Override // android.support.v4.app.RemoteInputCompatBase.RemoteInput
    public Set<String> getAllowedDataTypes() {
        return this.mAllowedDataTypes;
    }

    @Override // android.support.v4.app.RemoteInputCompatBase.RemoteInput
    public boolean getAllowFreeFormInput() {
        return this.mAllowFreeFormTextInput;
    }

    @Override // android.support.v4.app.RemoteInputCompatBase.RemoteInput
    public Bundle getExtras() {
        return this.mExtras;
    }

    static class ImplBase implements Impl {
        ImplBase() {
        }
    }

    static class ImplJellybean implements Impl {
        ImplJellybean() {
        }
    }

    static class ImplApi20 implements Impl {
        ImplApi20() {
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 20) {
            IMPL = new ImplApi20();
        } else if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new ImplJellybean();
        } else {
            IMPL = new ImplBase();
        }
        FACTORY = new RemoteInputCompatBase.RemoteInput.Factory() { // from class: android.support.v4.app.RemoteInput.1
        };
    }
}
