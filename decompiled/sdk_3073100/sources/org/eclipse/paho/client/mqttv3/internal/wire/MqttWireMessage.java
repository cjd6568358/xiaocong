package org.eclipse.paho.client.mqttv3.internal.wire;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class MqttWireMessage {
    private static final String[] PACKET_NAMES = {"reserved", "CONNECT", "CONNACK", "PUBLISH", "PUBACK", "PUBREC", "PUBREL", "PUBCOMP", "SUBSCRIBE", "SUBACK", "UNSUBSCRIBE", "UNSUBACK", "PINGREQ", "PINGRESP", "DISCONNECT"};
    protected boolean duplicate = false;
    protected int msgId = 0;
    private byte type;

    protected abstract byte getMessageInfo();

    protected abstract byte[] getVariableHeader() throws MqttException;

    public MqttWireMessage(byte type) {
        this.type = type;
    }

    public byte[] getPayload() throws MqttException {
        return new byte[0];
    }

    public byte getType() {
        return this.type;
    }

    public int getMessageId() {
        return this.msgId;
    }

    public void setMessageId(int msgId) {
        this.msgId = msgId;
    }

    public String getKey() {
        return new Integer(getMessageId()).toString();
    }

    public byte[] getHeader() throws MqttException {
        try {
            int first = ((getType() & 15) << 4) ^ (getMessageInfo() & 15);
            byte[] varHeader = getVariableHeader();
            int remLen = varHeader.length + getPayload().length;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeByte(first);
            dos.write(encodeMBI(remLen));
            dos.write(varHeader);
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ioe) {
            throw new MqttException(ioe);
        }
    }

    public boolean isMessageIdRequired() {
        return true;
    }

    public static MqttWireMessage createWireMessage(MqttPersistable data) throws MqttException {
        byte[] payload = data.getPayloadBytes();
        if (payload == null) {
            payload = new byte[0];
        }
        MultiByteArrayInputStream mbais = new MultiByteArrayInputStream(data.getHeaderBytes(), data.getHeaderOffset(), data.getHeaderLength(), payload, data.getPayloadOffset(), data.getPayloadLength());
        return createWireMessage(mbais);
    }

    public static MqttWireMessage createWireMessage(byte[] bytes) throws MqttException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return createWireMessage(bais);
    }

    private static MqttWireMessage createWireMessage(InputStream inputStream) throws MqttException {
        try {
            CountingInputStream counter = new CountingInputStream(inputStream);
            DataInputStream in = new DataInputStream(counter);
            int first = in.readUnsignedByte();
            byte type = (byte) (first >> 4);
            byte info = (byte) (first & 15);
            long remLen = readMBI(in).getValue();
            long totalToRead = ((long) counter.getCounter()) + remLen;
            long remainder = totalToRead - ((long) counter.getCounter());
            byte[] data = new byte[0];
            if (remainder > 0) {
                data = new byte[(int) remainder];
                in.readFully(data, 0, data.length);
            }
            if (type == 1) {
                MqttWireMessage result = new MqttConnect(info, data);
                return result;
            }
            if (type == 3) {
                MqttWireMessage result2 = new MqttPublish(info, data);
                return result2;
            }
            if (type == 4) {
                MqttWireMessage result3 = new MqttPubAck(info, data);
                return result3;
            }
            if (type == 7) {
                MqttWireMessage result4 = new MqttPubComp(info, data);
                return result4;
            }
            if (type == 2) {
                MqttWireMessage result5 = new MqttConnack(info, data);
                return result5;
            }
            if (type == 12) {
                MqttWireMessage result6 = new MqttPingReq(info, data);
                return result6;
            }
            if (type == 13) {
                MqttWireMessage result7 = new MqttPingResp(info, data);
                return result7;
            }
            if (type == 8) {
                MqttWireMessage result8 = new MqttSubscribe(info, data);
                return result8;
            }
            if (type == 9) {
                MqttWireMessage result9 = new MqttSuback(info, data);
                return result9;
            }
            if (type == 10) {
                MqttWireMessage result10 = new MqttUnsubscribe(info, data);
                return result10;
            }
            if (type == 11) {
                MqttWireMessage result11 = new MqttUnsubAck(info, data);
                return result11;
            }
            if (type == 6) {
                MqttWireMessage result12 = new MqttPubRel(info, data);
                return result12;
            }
            if (type == 5) {
                MqttWireMessage result13 = new MqttPubRec(info, data);
                return result13;
            }
            if (type == 14) {
                MqttWireMessage result14 = new MqttDisconnect(info, data);
                return result14;
            }
            throw ExceptionHelper.createMqttException(6);
        } catch (IOException io) {
            throw new MqttException(io);
        }
    }

    protected static byte[] encodeMBI(long number) {
        int numBytes = 0;
        long no = number;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        do {
            byte digit = (byte) (no % 128);
            no /= 128;
            if (no > 0) {
                digit = (byte) (digit | 128);
            }
            bos.write(digit);
            numBytes++;
            if (no <= 0) {
                break;
            }
        } while (numBytes < 4);
        return bos.toByteArray();
    }

    protected static MultiByteInteger readMBI(DataInputStream in) throws IOException {
        byte digit;
        long msgLength = 0;
        int multiplier = 1;
        int count = 0;
        do {
            digit = in.readByte();
            count++;
            msgLength += (long) ((digit & 127) * multiplier);
            multiplier *= 128;
        } while ((digit & 128) != 0);
        return new MultiByteInteger(msgLength, count);
    }

    protected byte[] encodeMessageId() throws MqttException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeShort(this.msgId);
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new MqttException(ex);
        }
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    protected void encodeUTF8(DataOutputStream dos, String stringToEncode) throws MqttException {
        try {
            byte[] encodedString = stringToEncode.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
            byte byte1 = (byte) ((encodedString.length >>> 8) & 255);
            byte byte2 = (byte) ((encodedString.length >>> 0) & 255);
            dos.write(byte1);
            dos.write(byte2);
            dos.write(encodedString);
        } catch (UnsupportedEncodingException ex) {
            throw new MqttException(ex);
        } catch (IOException ex2) {
            throw new MqttException(ex2);
        }
    }

    protected String decodeUTF8(DataInputStream input) throws MqttException {
        try {
            int encodedLength = input.readUnsignedShort();
            byte[] encodedString = new byte[encodedLength];
            input.readFully(encodedString);
            return new String(encodedString, AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (IOException ex) {
            throw new MqttException(ex);
        }
    }

    public String toString() {
        return PACKET_NAMES[this.type];
    }
}
