package com.bumptech.glide.gifdecoder;

import android.util.Log;
import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GifHeaderParser {
    private final byte[] block = new byte[256];
    private int blockSize = 0;
    private GifHeader header;
    private ByteBuffer rawData;

    public GifHeaderParser setData(byte[] data) {
        reset();
        if (data != null) {
            this.rawData = ByteBuffer.wrap(data);
            this.rawData.rewind();
            this.rawData.order(ByteOrder.LITTLE_ENDIAN);
        } else {
            this.rawData = null;
            this.header.status = 2;
        }
        return this;
    }

    public void clear() {
        this.rawData = null;
        this.header = null;
    }

    private void reset() {
        this.rawData = null;
        Arrays.fill(this.block, (byte) 0);
        this.header = new GifHeader();
        this.blockSize = 0;
    }

    public GifHeader parseHeader() {
        if (this.rawData == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        }
        if (err()) {
            return this.header;
        }
        readHeader();
        if (!err()) {
            readContents();
            if (this.header.frameCount < 0) {
                this.header.status = 1;
            }
        }
        return this.header;
    }

    private void readContents() {
        boolean done = false;
        while (!done && !err()) {
            int code = read();
            switch (code) {
                case 33:
                    int code2 = read();
                    switch (code2) {
                        case 1:
                            skip();
                            break;
                        case 249:
                            this.header.currentFrame = new GifFrame();
                            readGraphicControlExt();
                            break;
                        case 254:
                            skip();
                            break;
                        case 255:
                            readBlock();
                            String app = Constants.MAIN_VERSION_TAG;
                            for (int i = 0; i < 11; i++) {
                                app = app + ((char) this.block[i]);
                            }
                            if (app.equals("NETSCAPE2.0")) {
                                readNetscapeExt();
                            } else {
                                skip();
                            }
                            break;
                        default:
                            skip();
                            break;
                    }
                    break;
                case 44:
                    if (this.header.currentFrame == null) {
                        this.header.currentFrame = new GifFrame();
                    }
                    readBitmap();
                    break;
                case 59:
                    done = true;
                    break;
                default:
                    this.header.status = 1;
                    break;
            }
        }
    }

    private void readGraphicControlExt() {
        read();
        int packed = read();
        this.header.currentFrame.dispose = (packed & 28) >> 2;
        if (this.header.currentFrame.dispose == 0) {
            this.header.currentFrame.dispose = 1;
        }
        this.header.currentFrame.transparency = (packed & 1) != 0;
        int delayInHundredthsOfASecond = readShort();
        if (delayInHundredthsOfASecond < 3) {
            delayInHundredthsOfASecond = 10;
        }
        this.header.currentFrame.delay = delayInHundredthsOfASecond * 10;
        this.header.currentFrame.transIndex = read();
        read();
    }

    private void readBitmap() {
        this.header.currentFrame.ix = readShort();
        this.header.currentFrame.iy = readShort();
        this.header.currentFrame.iw = readShort();
        this.header.currentFrame.ih = readShort();
        int packed = read();
        boolean lctFlag = (packed & ParserConstants.LSHIFTASSIGN) != 0;
        int lctSize = (int) Math.pow(2.0d, (packed & 7) + 1);
        this.header.currentFrame.interlace = (packed & 64) != 0;
        if (lctFlag) {
            this.header.currentFrame.lct = readColorTable(lctSize);
        } else {
            this.header.currentFrame.lct = null;
        }
        this.header.currentFrame.bufferFrameStart = this.rawData.position();
        skipImageData();
        if (!err()) {
            this.header.frameCount++;
            this.header.frames.add(this.header.currentFrame);
        }
    }

    private void readNetscapeExt() {
        do {
            readBlock();
            if (this.block[0] == 1) {
                int b1 = this.block[1] & Constants.NETWORK_TYPE_UNCONNECTED;
                int b2 = this.block[2] & Constants.NETWORK_TYPE_UNCONNECTED;
                this.header.loopCount = (b2 << 8) | b1;
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }

    private void readHeader() {
        String id = Constants.MAIN_VERSION_TAG;
        for (int i = 0; i < 6; i++) {
            id = id + ((char) read());
        }
        if (!id.startsWith("GIF")) {
            this.header.status = 1;
            return;
        }
        readLSD();
        if (this.header.gctFlag && !err()) {
            this.header.gct = readColorTable(this.header.gctSize);
            this.header.bgColor = this.header.gct[this.header.bgIndex];
        }
    }

    private void readLSD() {
        this.header.width = readShort();
        this.header.height = readShort();
        int packed = read();
        this.header.gctFlag = (packed & ParserConstants.LSHIFTASSIGN) != 0;
        this.header.gctSize = 2 << (packed & 7);
        this.header.bgIndex = read();
        this.header.pixelAspect = read();
    }

    private int[] readColorTable(int ncolors) {
        int nbytes = ncolors * 3;
        int[] tab = null;
        byte[] c = new byte[nbytes];
        try {
            this.rawData.get(c);
            tab = new int[256];
            int j = 0;
            int i = 0;
            while (i < ncolors) {
                int j2 = j + 1;
                int r = c[j] & Constants.NETWORK_TYPE_UNCONNECTED;
                int j3 = j2 + 1;
                int g = c[j2] & Constants.NETWORK_TYPE_UNCONNECTED;
                int j4 = j3 + 1;
                int b = c[j3] & Constants.NETWORK_TYPE_UNCONNECTED;
                int i2 = i + 1;
                tab[i] = (-16777216) | (r << 16) | (g << 8) | b;
                j = j4;
                i = i2;
            }
        } catch (BufferUnderflowException e) {
            if (Log.isLoggable("GifHeaderParser", 3)) {
                Log.d("GifHeaderParser", "Format Error Reading Color Table", e);
            }
            this.header.status = 1;
        }
        return tab;
    }

    private void skipImageData() {
        read();
        skip();
    }

    private void skip() {
        int blockSize;
        do {
            blockSize = read();
            this.rawData.position(this.rawData.position() + blockSize);
        } while (blockSize > 0);
    }

    private int readBlock() {
        this.blockSize = read();
        int n = 0;
        if (this.blockSize > 0) {
            int count = 0;
            while (n < this.blockSize) {
                try {
                    count = this.blockSize - n;
                    this.rawData.get(this.block, n, count);
                    n += count;
                } catch (Exception e) {
                    if (Log.isLoggable("GifHeaderParser", 3)) {
                        Log.d("GifHeaderParser", "Error Reading Block n: " + n + " count: " + count + " blockSize: " + this.blockSize, e);
                    }
                    this.header.status = 1;
                }
            }
        }
        return n;
    }

    private int read() {
        try {
            int curByte = this.rawData.get() & Constants.NETWORK_TYPE_UNCONNECTED;
            return curByte;
        } catch (Exception e) {
            this.header.status = 1;
            return 0;
        }
    }

    private int readShort() {
        return this.rawData.getShort();
    }

    private boolean err() {
        return this.header.status != 0;
    }
}
