package com.hzy.tvmao.model.legacy.api;

import com.kookong.app.data.ProgramData;
import java.util.Comparator;

/* JADX INFO: compiled from: TVWallDataUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class n implements Comparator<ProgramData.PairProgram> {
    n() {
    }

    @Override // java.util.Comparator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(ProgramData.PairProgram pairProgram, ProgramData.PairProgram pairProgram2) {
        if (pairProgram.ishd > pairProgram2.ishd) {
            return 1;
        }
        if (pairProgram.ishd == pairProgram2.ishd) {
            return 0;
        }
        return -1;
    }
}
