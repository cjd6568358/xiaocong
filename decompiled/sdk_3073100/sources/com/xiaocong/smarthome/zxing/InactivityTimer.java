package com.xiaocong.smarthome.zxing;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.xiaocong.smarthome.zxing.executor.AsyncTaskExecInterface;
import com.xiaocong.smarthome.zxing.executor.AsyncTaskExecManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class InactivityTimer {
    private static final String TAG = InactivityTimer.class.getSimpleName();
    private final Activity activity;
    private InactivityAsyncTask inactivityTask;
    private final AsyncTaskExecInterface taskExec = new AsyncTaskExecManager().build();
    private final BroadcastReceiver powerStatusReceiver = new PowerStatusReceiver();

    InactivityTimer(Activity activity) {
        this.activity = activity;
        onActivity();
    }

    synchronized void onActivity() {
        cancel();
        this.inactivityTask = new InactivityAsyncTask();
        this.taskExec.execute(this.inactivityTask, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void cancel() {
        AsyncTask<?, ?, ?> task = this.inactivityTask;
        if (task != null) {
            task.cancel(true);
            this.inactivityTask = null;
        }
    }

    void shutdown() {
        cancel();
    }

    private final class PowerStatusReceiver extends BroadcastReceiver {
        private PowerStatusReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                boolean onBatteryNow = intent.getIntExtra("plugged", -1) <= 0;
                if (!onBatteryNow) {
                    InactivityTimer.this.cancel();
                } else {
                    InactivityTimer.this.onActivity();
                }
            }
        }
    }

    private final class InactivityAsyncTask extends AsyncTask<Object, Object, Object> {
        private InactivityAsyncTask() {
        }

        @Override // android.os.AsyncTask
        protected Object doInBackground(Object... objects) {
            try {
                Thread.sleep(300000L);
                InactivityTimer.this.activity.finish();
                return null;
            } catch (InterruptedException e) {
                return null;
            }
        }
    }
}
