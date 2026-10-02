package com.huawei.hms.update.e;

import android.app.AlertDialog;
import com.huawei.android.hms.base.R;

/* JADX INFO: compiled from: PromptDialogs.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class m {

    /* JADX INFO: compiled from: PromptDialogs.java */
    public static class b extends a {
        public b() {
            super();
        }

        @Override // com.huawei.hms.update.e.m.a, com.huawei.hms.update.e.b
        public /* bridge */ /* synthetic */ AlertDialog a() {
            return super.a();
        }

        @Override // com.huawei.hms.update.e.m.a
        protected int h() {
            return R.string.hms_check_failure;
        }
    }

    /* JADX INFO: compiled from: PromptDialogs.java */
    public static class c extends a {
        public c() {
            super();
        }

        @Override // com.huawei.hms.update.e.m.a, com.huawei.hms.update.e.b
        public /* bridge */ /* synthetic */ AlertDialog a() {
            return super.a();
        }

        @Override // com.huawei.hms.update.e.m.a
        protected int h() {
            return R.string.hms_download_failure;
        }
    }

    /* JADX INFO: compiled from: PromptDialogs.java */
    public static class d extends a {
        public d() {
            super();
        }

        @Override // com.huawei.hms.update.e.m.a, com.huawei.hms.update.e.b
        public /* bridge */ /* synthetic */ AlertDialog a() {
            return super.a();
        }

        @Override // com.huawei.hms.update.e.m.a
        protected int h() {
            return R.string.hms_download_no_space;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: PromptDialogs.java */
    static abstract class a extends com.huawei.hms.update.e.b {
        protected abstract int h();

        private a() {
        }

        @Override // com.huawei.hms.update.e.b
        public AlertDialog a() {
            AlertDialog.Builder builder = new AlertDialog.Builder(f(), g());
            builder.setMessage(h());
            builder.setPositiveButton(i(), new n(this));
            return builder.create();
        }

        protected int i() {
            return R.string.hms_confirm;
        }
    }
}
