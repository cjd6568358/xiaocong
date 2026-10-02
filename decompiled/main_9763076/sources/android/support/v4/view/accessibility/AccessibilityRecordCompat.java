package android.support.v4.view.accessibility;

import android.os.Build;
import android.view.accessibility.AccessibilityRecord;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AccessibilityRecordCompat {
    private static final AccessibilityRecordCompatBaseImpl IMPL;
    private final AccessibilityRecord mRecord;

    static class AccessibilityRecordCompatBaseImpl {
        AccessibilityRecordCompatBaseImpl() {
        }

        public void setMaxScrollX(AccessibilityRecord record, int maxScrollX) {
        }

        public void setMaxScrollY(AccessibilityRecord record, int maxScrollY) {
        }
    }

    static class AccessibilityRecordCompatApi15Impl extends AccessibilityRecordCompatBaseImpl {
        AccessibilityRecordCompatApi15Impl() {
        }

        @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordCompatBaseImpl
        public void setMaxScrollX(AccessibilityRecord record, int maxScrollX) {
            record.setMaxScrollX(maxScrollX);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityRecordCompat.AccessibilityRecordCompatBaseImpl
        public void setMaxScrollY(AccessibilityRecord record, int maxScrollY) {
            record.setMaxScrollY(maxScrollY);
        }
    }

    static class AccessibilityRecordCompatApi16Impl extends AccessibilityRecordCompatApi15Impl {
        AccessibilityRecordCompatApi16Impl() {
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityRecordCompatApi16Impl();
        } else if (Build.VERSION.SDK_INT >= 15) {
            IMPL = new AccessibilityRecordCompatApi15Impl();
        } else {
            IMPL = new AccessibilityRecordCompatBaseImpl();
        }
    }

    public static void setMaxScrollX(AccessibilityRecord record, int maxScrollX) {
        IMPL.setMaxScrollX(record, maxScrollX);
    }

    public static void setMaxScrollY(AccessibilityRecord record, int maxScrollY) {
        IMPL.setMaxScrollY(record, maxScrollY);
    }

    @Deprecated
    public int hashCode() {
        if (this.mRecord == null) {
            return 0;
        }
        return this.mRecord.hashCode();
    }

    @Deprecated
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AccessibilityRecordCompat other = (AccessibilityRecordCompat) obj;
            if (this.mRecord == null) {
                return other.mRecord == null;
            }
            return this.mRecord.equals(other.mRecord);
        }
        return false;
    }
}
