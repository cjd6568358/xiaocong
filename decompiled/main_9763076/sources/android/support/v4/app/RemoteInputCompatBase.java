package android.support.v4.app;

import android.os.Bundle;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
class RemoteInputCompatBase {

    public static abstract class RemoteInput {

        public interface Factory {
        }

        protected abstract boolean getAllowFreeFormInput();

        protected abstract Set<String> getAllowedDataTypes();

        protected abstract CharSequence[] getChoices();

        protected abstract Bundle getExtras();

        protected abstract CharSequence getLabel();

        protected abstract String getResultKey();
    }
}
