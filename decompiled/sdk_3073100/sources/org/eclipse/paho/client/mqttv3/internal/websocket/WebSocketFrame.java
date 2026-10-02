package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WebSocketFrame {
    private boolean closeFlag;
    private boolean fin;
    private byte opcode;
    private byte[] payload;

    public byte[] getPayload() {
        return this.payload;
    }

    public boolean isCloseFlag() {
        return this.closeFlag;
    }

    public WebSocketFrame(byte opcode, boolean fin, byte[] payload) {
        this.closeFlag = false;
        this.opcode = opcode;
        this.fin = fin;
        this.payload = payload;
    }

    private void setFinAndOpCode(byte incomingByte) {
        this.fin = (incomingByte & 128) != 0;
        this.opcode = (byte) (incomingByte & 15);
    }

    public WebSocketFrame(InputStream input) throws IOException {
        this.closeFlag = false;
        byte firstByte = (byte) input.read();
        setFinAndOpCode(firstByte);
        if (this.opcode == 2) {
            byte maskLengthByte = (byte) input.read();
            boolean masked = (maskLengthByte & 128) != 0;
            int payloadLength = (byte) (maskLengthByte & 127);
            int byteCount = 0;
            if (payloadLength == 127) {
                byteCount = 8;
            } else if (payloadLength == 126) {
                byteCount = 2;
            }
            payloadLength = byteCount > 0 ? 0 : payloadLength;
            while (true) {
                byteCount--;
                if (byteCount < 0) {
                    break;
                } else {
                    payloadLength |= (((byte) input.read()) & 255) << (byteCount * 8);
                }
            }
            byte[] maskingKey = null;
            if (masked) {
                maskingKey = new byte[4];
                input.read(maskingKey, 0, 4);
            }
            this.payload = new byte[payloadLength];
            int offsetIndex = 0;
            int tempLength = payloadLength;
            while (offsetIndex != payloadLength) {
                int bytesRead = input.read(this.payload, offsetIndex, tempLength);
                offsetIndex += bytesRead;
                tempLength -= bytesRead;
            }
            if (masked) {
                for (int i = 0; i < this.payload.length; i++) {
                    byte[] bArr = this.payload;
                    bArr[i] = (byte) (bArr[i] ^ maskingKey[i % 4]);
                }
                return;
            }
            return;
        }
        if (this.opcode == 8) {
            this.closeFlag = true;
            return;
        }
        throw new IOException(new StringBuffer("Invalid Frame: Opcode: ").append((int) this.opcode).toString());
    }

    public byte[] encodeFrame() {
        int length = this.payload.length + 6;
        if (this.payload.length > 65535) {
            length += 8;
        } else if (this.payload.length >= 126) {
            length += 2;
        }
        ByteBuffer buffer = ByteBuffer.allocate(length);
        appendFinAndOpCode(buffer, this.opcode, this.fin);
        byte[] mask = generateMaskingKey();
        appendLengthAndMask(buffer, this.payload.length, mask);
        for (int i = 0; i < this.payload.length; i++) {
            byte[] bArr = this.payload;
            byte b = (byte) (bArr[i] ^ mask[i % 4]);
            bArr[i] = b;
            buffer.put(b);
        }
        buffer.flip();
        return buffer.array();
    }

    public static void appendLengthAndMask(ByteBuffer buffer, int length, byte[] mask) {
        if (mask != null) {
            appendLength(buffer, length, true);
            buffer.put(mask);
        } else {
            appendLength(buffer, length, false);
        }
    }

    private static void appendLength(ByteBuffer buffer, int length, boolean masked) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        byte b = masked ? (byte) -128 : (byte) 0;
        if (length > 65535) {
            buffer.put((byte) (b | 127));
            buffer.put((byte) 0);
            buffer.put((byte) 0);
            buffer.put((byte) 0);
            buffer.put((byte) 0);
            buffer.put((byte) ((length >> 24) & 255));
            buffer.put((byte) ((length >> 16) & 255));
            buffer.put((byte) ((length >> 8) & 255));
            buffer.put((byte) (length & 255));
            return;
        }
        if (length >= 126) {
            buffer.put((byte) (b | 126));
            buffer.put((byte) (length >> 8));
            buffer.put((byte) (length & 255));
            return;
        }
        buffer.put((byte) (b | length));
    }

    public static void appendFinAndOpCode(ByteBuffer buffer, byte opcode, boolean fin) {
        byte b = 0;
        if (fin) {
            b = (byte) 128;
        }
        buffer.put((byte) ((opcode & 15) | b));
    }

    public static byte[] generateMaskingKey() {
        SecureRandom secureRandomGenerator = new SecureRandom();
        int a = secureRandomGenerator.nextInt(255);
        int b = secureRandomGenerator.nextInt(255);
        int c = secureRandomGenerator.nextInt(255);
        int d = secureRandomGenerator.nextInt(255);
        return new byte[]{(byte) a, (byte) b, (byte) c, (byte) d};
    }
}
