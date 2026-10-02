package com.fasterxml.jackson.core.sym;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class Name1 extends Name {
    static final Name1 sEmptyName = new Name1(Constants.MAIN_VERSION_TAG, 0, 0);
    final int mQuad;

    Name1(String name, int hash, int quad) {
        super(name, hash);
        this.mQuad = quad;
    }

    static final Name1 getEmptyName() {
        return sEmptyName;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int quad) {
        return quad == this.mQuad;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int quad1, int quad2) {
        return quad1 == this.mQuad && quad2 == 0;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int[] quads, int qlen) {
        return qlen == 1 && quads[0] == this.mQuad;
    }
}
