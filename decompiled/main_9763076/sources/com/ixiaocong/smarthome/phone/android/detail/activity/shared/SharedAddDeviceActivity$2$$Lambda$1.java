package com.ixiaocong.smarthome.phone.android.detail.activity.shared;

import android.util.SparseBooleanArray;
import android.widget.CompoundButton;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SharedAddDeviceActivity$2$$Lambda$1 implements CompoundButton.OnCheckedChangeListener {
    private final SharedAddDeviceActivity.AnonymousClass2 arg$1;
    private final int arg$2;
    private final SparseBooleanArray arg$3;

    private SharedAddDeviceActivity$2$$Lambda$1(SharedAddDeviceActivity.AnonymousClass2 anonymousClass2, int i, SparseBooleanArray sparseBooleanArray) {
        this.arg$1 = anonymousClass2;
        this.arg$2 = i;
        this.arg$3 = sparseBooleanArray;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(SharedAddDeviceActivity.AnonymousClass2 anonymousClass2, int i, SparseBooleanArray sparseBooleanArray) {
        return new SharedAddDeviceActivity$2$$Lambda$1(anonymousClass2, i, sparseBooleanArray);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$onSimpleItemChildClick$0(this.arg$2, this.arg$3, compoundButton, z);
    }
}
