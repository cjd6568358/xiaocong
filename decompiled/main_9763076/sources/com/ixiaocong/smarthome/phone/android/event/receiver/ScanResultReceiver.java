package com.ixiaocong.smarthome.phone.android.event.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.accredit.AccreditManagerActivity;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ScanResultReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("device.android.ScanContent")) {
            String scanResult = intent.getStringExtra("scanContent");
            if (!TextUtils.isEmpty(scanResult)) {
                Intent startIntent = new Intent(context, (Class<?>) AccreditManagerActivity.class);
                startIntent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                startIntent.putExtra("scanContent", scanResult);
                context.startActivity(startIntent);
            }
        }
    }

    public static ScanResultReceiver getInstance() {
        return ScanResultReceiverHolder.INSTANCE;
    }

    public void registerReceiver(Context context) {
        IntentFilter filter = new IntentFilter("device.android.ScanContent");
        LocalBroadcastManager.getInstance(context).registerReceiver(getInstance(), filter);
    }

    public void unregisterReceiver(Context context) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(getInstance());
    }

    private static final class ScanResultReceiverHolder {
        private static final ScanResultReceiver INSTANCE = new ScanResultReceiver();
    }
}
