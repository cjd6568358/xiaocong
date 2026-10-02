package com.scwang.smartrefresh.layout.constant;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum DimensionStatus {
    DefaultUnNotify(false),
    Default(true),
    XmlWrapUnNotify(false),
    XmlWrap(true),
    XmlExactUnNotify(false),
    XmlExact(true),
    XmlLayoutUnNotify(false),
    XmlLayout(true),
    CodeExactUnNotify(false),
    CodeExact(true),
    DeadLockUnNotify(false),
    DeadLock(true);

    public final boolean notified;

    DimensionStatus(boolean notified) {
        this.notified = notified;
    }

    public DimensionStatus unNotify() {
        if (!this.notified) {
            return this;
        }
        DimensionStatus prev = values()[ordinal() - 1];
        if (prev.notified) {
            return DefaultUnNotify;
        }
        return prev;
    }

    public DimensionStatus notified() {
        if (!this.notified) {
            return values()[ordinal() + 1];
        }
        return this;
    }

    public boolean canReplaceWith(DimensionStatus status) {
        return ordinal() < status.ordinal() || ((!this.notified || CodeExact == this) && ordinal() == status.ordinal());
    }

    public boolean gteReplaceWith(DimensionStatus status) {
        return ordinal() >= status.ordinal();
    }
}
