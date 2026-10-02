package com.huawei.hms.update.e;

import android.app.AlertDialog;
import com.huawei.android.hms.base.R;

/* JADX INFO: compiled from: ConfirmDialogs.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class e {

    /* JADX INFO: compiled from: ConfirmDialogs.java */
    public static class b extends a {
        public b() {
            super();
        }

        @Override // com.huawei.hms.update.e.e.a, com.huawei.hms.update.e.b
        public /* bridge */ /* synthetic */ AlertDialog a() {
            return super.a();
        }

        @Override // com.huawei.hms.update.e.e.a
        protected int h() {
            return R.string.hms_download_retry;
        }

        @Override // com.huawei.hms.update.e.e.a
        protected int i() {
            return R.string.hms_retry;
        }

        @Override // com.huawei.hms.update.e.e.a
        protected int j() {
            return R.string.hms_cancel;
        }
    }

    /* JADX INFO: compiled from: ConfirmDialogs.java */
    public static class c extends a {
        public c() {
            super();
        }

        @Override // com.huawei.hms.update.e.e.a, com.huawei.hms.update.e.b
        public /* bridge */ /* synthetic */ AlertDialog a() {
            return super.a();
        }

        @Override // com.huawei.hms.update.e.e.a
        protected int h() {
            return R.string.hms_abort_message;
        }

        @Override // com.huawei.hms.update.e.e.a
        protected int i() {
            return R.string.hms_abort;
        }

        @Override // com.huawei.hms.update.e.e.a
        protected int j() {
            return R.string.hms_cancel;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ConfirmDialogs.java */
    static abstract class a extends com.huawei.hms.update.e.b {
        protected abstract int h();

        protected abstract int i();

        protected abstract int j();

        private a() {
        }

        @Override // com.huawei.hms.update.e.b
        public AlertDialog a() {
            AlertDialog.Builder builder = new AlertDialog.Builder(f(), g());
            builder.setMessage(h());
            builder.setPositiveButton(i(), new f(this));
            builder.setNegativeButton(j(), new g(this));
            return builder.create();
        }
    }
}
