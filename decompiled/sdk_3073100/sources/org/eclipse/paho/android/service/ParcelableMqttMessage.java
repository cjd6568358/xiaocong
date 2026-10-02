package org.eclipse.paho.android.service;

import android.os.Parcel;
import android.os.Parcelable;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ParcelableMqttMessage extends MqttMessage implements Parcelable {
    public static final Parcelable.Creator<ParcelableMqttMessage> CREATOR = new Parcelable.Creator<ParcelableMqttMessage>() { // from class: org.eclipse.paho.android.service.ParcelableMqttMessage.1
        @Override // android.os.Parcelable.Creator
        public ParcelableMqttMessage createFromParcel(Parcel parcel) {
            return new ParcelableMqttMessage(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ParcelableMqttMessage[] newArray(int size) {
            return new ParcelableMqttMessage[size];
        }
    };
    String messageId;

    ParcelableMqttMessage(MqttMessage original) {
        super(original.getPayload());
        this.messageId = null;
        setQos(original.getQos());
        setRetained(original.isRetained());
        setDuplicate(original.isDuplicate());
    }

    ParcelableMqttMessage(Parcel parcel) {
        super(parcel.createByteArray());
        this.messageId = null;
        setQos(parcel.readInt());
        boolean[] flags = parcel.createBooleanArray();
        setRetained(flags[0]);
        setDuplicate(flags[1]);
        this.messageId = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeByteArray(getPayload());
        parcel.writeInt(getQos());
        parcel.writeBooleanArray(new boolean[]{isRetained(), isDuplicate()});
        parcel.writeString(this.messageId);
    }
}
