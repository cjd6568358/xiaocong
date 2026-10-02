package com.ixiaocong.smarthome.phone.android.detail.activity.accredit;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AccreditManagerActivity$$Lambda$1 implements View.OnClickListener {
    private final AccreditManagerActivity arg$1;

    private AccreditManagerActivity$$Lambda$1(AccreditManagerActivity accreditManagerActivity) {
        this.arg$1 = accreditManagerActivity;
    }

    public static View.OnClickListener lambdaFactory$(AccreditManagerActivity accreditManagerActivity) {
        return new AccreditManagerActivity$$Lambda$1(accreditManagerActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
