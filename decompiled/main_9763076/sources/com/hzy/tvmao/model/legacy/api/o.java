package com.hzy.tvmao.model.legacy.api;

import com.hzy.tvmao.model.legacy.api.data.UIProgramData;
import java.util.Comparator;

/* JADX INFO: compiled from: TVWallDataUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class o implements Comparator<UIProgramData.ProgramItem> {
    o() {
    }

    @Override // java.util.Comparator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(UIProgramData.ProgramItem programItem, UIProgramData.ProgramItem programItem2) {
        if (programItem.getFirstItem().ilike > programItem2.getFirstItem().ilike) {
            return -1;
        }
        if (programItem.getFirstItem().ilike == programItem2.getFirstItem().ilike) {
            return 0;
        }
        return 1;
    }
}
