package com.xiaomi.mipush.sdk;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import java.util.concurrent.ConcurrentLinkedQueue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MessageHandleService extends IntentService {
    private static ConcurrentLinkedQueue<a> a = new ConcurrentLinkedQueue<>();

    public static class a {
        private PushMessageReceiver a;
        private Intent b;

        public a(Intent intent, PushMessageReceiver pushMessageReceiver) {
            this.a = pushMessageReceiver;
            this.b = intent;
        }

        public PushMessageReceiver a() {
            return this.a;
        }

        public Intent b() {
            return this.b;
        }
    }

    public MessageHandleService() {
        super("MessageHandleThread");
    }

    public static void addJob(a aVar) {
        if (aVar != null) {
            a.add(aVar);
        }
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        a aVarPoll;
        if (intent == null || (aVarPoll = a.poll()) == null) {
            return;
        }
        try {
            PushMessageReceiver pushMessageReceiverA = aVarPoll.a();
            Intent intentB = aVarPoll.b();
            switch (intentB.getIntExtra("message_type", 1)) {
                case 1:
                    PushMessageHandler.a aVarA = s.a(this).a(intentB);
                    if (aVarA != null) {
                        if (aVarA instanceof MiPushMessage) {
                            MiPushMessage miPushMessage = (MiPushMessage) aVarA;
                            if (!miPushMessage.isArrivedMessage()) {
                                pushMessageReceiverA.onReceiveMessage(this, miPushMessage);
                            }
                            if (miPushMessage.getPassThrough() == 1) {
                                pushMessageReceiverA.onReceivePassThroughMessage(this, miPushMessage);
                            } else if (!miPushMessage.isNotified()) {
                                pushMessageReceiverA.onNotificationMessageArrived(this, miPushMessage);
                            } else {
                                pushMessageReceiverA.onNotificationMessageClicked(this, miPushMessage);
                            }
                        } else if (aVarA instanceof MiPushCommandMessage) {
                            MiPushCommandMessage miPushCommandMessage = (MiPushCommandMessage) aVarA;
                            pushMessageReceiverA.onCommandResult(this, miPushCommandMessage);
                            if (TextUtils.equals(miPushCommandMessage.getCommand(), "register")) {
                                pushMessageReceiverA.onReceiveRegisterResult(this, miPushCommandMessage);
                            }
                        }
                    }
                    break;
                case 2:
                default:
                    break;
                case 3:
                    MiPushCommandMessage miPushCommandMessage2 = (MiPushCommandMessage) intentB.getSerializableExtra("key_command");
                    pushMessageReceiverA.onCommandResult(this, miPushCommandMessage2);
                    if (TextUtils.equals(miPushCommandMessage2.getCommand(), "register")) {
                        pushMessageReceiverA.onReceiveRegisterResult(this, miPushCommandMessage2);
                    }
                    break;
                case 4:
                    break;
            }
        } catch (RuntimeException e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
        }
    }
}
