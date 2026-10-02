package org.eclipse.paho.client.mqttv3.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ResourceBundleCatalog extends MessageCatalog {
    private ResourceBundle bundle = ResourceBundle.getBundle("org.eclipse.paho.client.mqttv3.internal.nls.messages");

    @Override // org.eclipse.paho.client.mqttv3.internal.MessageCatalog
    protected String getLocalizedMessage(int id) {
        try {
            return this.bundle.getString(Integer.toString(id));
        } catch (MissingResourceException e) {
            return "MqttException";
        }
    }
}
