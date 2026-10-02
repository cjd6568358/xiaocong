package com.huawei.hms.update.e;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.huawei.android.hms.base.R;
import java.text.NumberFormat;

/* JADX INFO: compiled from: ProgressNoCancel.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class l extends b {
    private ProgressBar a;
    private TextView b;
    private DialogInterface.OnKeyListener c = new a();

    @Override // com.huawei.hms.update.e.b
    public AlertDialog a() {
        AlertDialog.Builder builder = new AlertDialog.Builder(f(), g());
        View viewInflate = View.inflate(f(), R.layout.hms_download_progress, null);
        builder.setView(viewInflate);
        builder.setCancelable(false);
        builder.setOnKeyListener(this.c);
        this.a = (ProgressBar) viewInflate.findViewById(R.id.download_info_progress);
        this.b = (TextView) viewInflate.findViewById(R.id.hms_progress_text);
        a(0);
        return builder.create();
    }

    void a(int i) {
        Activity activityF = f();
        if (activityF == null || activityF.isFinishing()) {
            com.huawei.hms.support.log.a.c("ProgressNoCancel", "In setDownloading, The activity is null or finishing.");
        } else if (this.b != null && this.a != null) {
            this.a.setProgress(i);
            this.b.setText(NumberFormat.getPercentInstance().format(i / 100.0f));
        }
    }

    /* JADX INFO: compiled from: ProgressNoCancel.java */
    private static class a implements DialogInterface.OnKeyListener {
        private a() {
        }

        @Override // android.content.DialogInterface.OnKeyListener
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            return i == 4 && keyEvent.getRepeatCount() == 0;
        }
    }
}
