package org.eclipse.paho.client.mqttv3;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DisconnectedBufferOptions {
    private int bufferSize = 5000;
    private boolean bufferEnabled = false;
    private boolean persistBuffer = false;
    private boolean deleteOldestMessages = false;

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        if (bufferSize < 1) {
            throw new IllegalArgumentException();
        }
        this.bufferSize = bufferSize;
    }

    public boolean isBufferEnabled() {
        return this.bufferEnabled;
    }

    public void setBufferEnabled(boolean bufferEnabled) {
        this.bufferEnabled = bufferEnabled;
    }

    public boolean isPersistBuffer() {
        return this.persistBuffer;
    }

    public void setPersistBuffer(boolean persistBuffer) {
        this.persistBuffer = persistBuffer;
    }

    public boolean isDeleteOldestMessages() {
        return this.deleteOldestMessages;
    }

    public void setDeleteOldestMessages(boolean deleteOldestMessages) {
        this.deleteOldestMessages = deleteOldestMessages;
    }
}
