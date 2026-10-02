package android.support.v4.app;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class BaseFragmentActivityApi14 extends SupportActivity {
    boolean mStartedIntentSenderFromFragment;

    abstract View dispatchFragmentsOnCreateView(View view, String str, Context context, AttributeSet attributeSet);

    BaseFragmentActivityApi14() {
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory2
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View v = dispatchFragmentsOnCreateView(parent, name, context, attrs);
        if (v == null) {
            return super.onCreateView(parent, name, context, attrs);
        }
        return v;
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View v = dispatchFragmentsOnCreateView(null, name, context, attrs);
        if (v == null) {
            return super.onCreateView(name, context, attrs);
        }
        return v;
    }

    @Override // android.app.Activity
    public void startIntentSenderForResult(IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        if (!this.mStartedIntentSenderFromFragment && requestCode != -1) {
            checkForValidRequestCode(requestCode);
        }
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    static void checkForValidRequestCode(int requestCode) {
        if (((-65536) & requestCode) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
    }
}
