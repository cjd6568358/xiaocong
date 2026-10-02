package com.bumptech.glide.gifdecoder;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GifDecoder {
    private int[] act;
    private BitmapProvider bitmapProvider;
    private byte[] data;
    private int framePointer;
    private byte[] mainPixels;
    private int[] mainScratch;
    private byte[] pixelStack;
    private short[] prefix;
    private Bitmap previousImage;
    private ByteBuffer rawData;
    private boolean savePrevious;
    private int status;
    private byte[] suffix;
    private static final String TAG = GifDecoder.class.getSimpleName();
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private final byte[] block = new byte[256];
    private GifHeader header = new GifHeader();

    public interface BitmapProvider {
        Bitmap obtain(int i, int i2, Bitmap.Config config);

        void release(Bitmap bitmap);
    }

    public GifDecoder(BitmapProvider provider) {
        this.bitmapProvider = provider;
    }

    public void advance() {
        this.framePointer = (this.framePointer + 1) % this.header.frameCount;
    }

    public int getDelay(int n) {
        if (n < 0 || n >= this.header.frameCount) {
            return -1;
        }
        int delay = this.header.frames.get(n).delay;
        return delay;
    }

    public int getNextDelay() {
        if (this.header.frameCount <= 0 || this.framePointer < 0) {
            return -1;
        }
        return getDelay(this.framePointer);
    }

    public int getFrameCount() {
        return this.header.frameCount;
    }

    public int getCurrentFrameIndex() {
        return this.framePointer;
    }

    public int getLoopCount() {
        return this.header.loopCount;
    }

    public synchronized Bitmap getNextFrame() {
        Bitmap pixels = null;
        synchronized (this) {
            if (this.header.frameCount <= 0 || this.framePointer < 0) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "unable to decode frame, frameCount=" + this.header.frameCount + " framePointer=" + this.framePointer);
                }
                this.status = 1;
            }
            if (this.status == 1 || this.status == 2) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Unable to decode frame, status=" + this.status);
                }
            } else {
                this.status = 0;
                GifFrame currentFrame = this.header.frames.get(this.framePointer);
                GifFrame previousFrame = null;
                int previousIndex = this.framePointer - 1;
                if (previousIndex >= 0) {
                    GifFrame previousFrame2 = this.header.frames.get(previousIndex);
                    previousFrame = previousFrame2;
                }
                if (currentFrame.lct == null) {
                    this.act = this.header.gct;
                } else {
                    this.act = currentFrame.lct;
                    if (this.header.bgIndex == currentFrame.transIndex) {
                        this.header.bgColor = 0;
                    }
                }
                int save = 0;
                if (currentFrame.transparency) {
                    save = this.act[currentFrame.transIndex];
                    this.act[currentFrame.transIndex] = 0;
                }
                if (this.act == null) {
                    if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "No Valid Color Table");
                    }
                    this.status = 1;
                } else {
                    pixels = setPixels(currentFrame, previousFrame);
                    if (currentFrame.transparency) {
                        this.act[currentFrame.transIndex] = save;
                    }
                }
            }
        }
        return pixels;
    }

    public void clear() {
        this.header = null;
        this.data = null;
        this.mainPixels = null;
        this.mainScratch = null;
        if (this.previousImage != null) {
            this.bitmapProvider.release(this.previousImage);
        }
        this.previousImage = null;
        this.rawData = null;
    }

    public void setData(GifHeader header, byte[] data) {
        this.header = header;
        this.data = data;
        this.status = 0;
        this.framePointer = -1;
        this.rawData = ByteBuffer.wrap(data);
        this.rawData.rewind();
        this.rawData.order(ByteOrder.LITTLE_ENDIAN);
        this.savePrevious = false;
        for (GifFrame frame : header.frames) {
            if (frame.dispose == 3) {
                this.savePrevious = true;
                break;
            }
        }
        this.mainPixels = new byte[header.width * header.height];
        this.mainScratch = new int[header.width * header.height];
    }

    private Bitmap setPixels(GifFrame currentFrame, GifFrame previousFrame) {
        int width = this.header.width;
        int height = this.header.height;
        int[] dest = this.mainScratch;
        if (previousFrame != null && previousFrame.dispose > 0) {
            if (previousFrame.dispose == 2) {
                int c = 0;
                if (!currentFrame.transparency) {
                    c = this.header.bgColor;
                }
                Arrays.fill(dest, c);
            } else if (previousFrame.dispose == 3 && this.previousImage != null) {
                this.previousImage.getPixels(dest, 0, width, 0, 0, width, height);
            }
        }
        decodeBitmapData(currentFrame);
        int pass = 1;
        int inc = 8;
        int iline = 0;
        for (int i = 0; i < currentFrame.ih; i++) {
            int line = i;
            if (currentFrame.interlace) {
                if (iline >= currentFrame.ih) {
                    pass++;
                    switch (pass) {
                        case 2:
                            iline = 4;
                            break;
                        case 3:
                            iline = 2;
                            inc = 4;
                            break;
                        case 4:
                            iline = 1;
                            inc = 2;
                            break;
                    }
                }
                line = iline;
                iline += inc;
            }
            int line2 = line + currentFrame.iy;
            if (line2 < this.header.height) {
                int k = line2 * this.header.width;
                int dx = k + currentFrame.ix;
                int dlim = dx + currentFrame.iw;
                if (this.header.width + k < dlim) {
                    dlim = k + this.header.width;
                }
                int sx = i * currentFrame.iw;
                int sx2 = sx;
                while (dx < dlim) {
                    int sx3 = sx2 + 1;
                    int index = this.mainPixels[sx2] & 255;
                    int c2 = this.act[index];
                    if (c2 != 0) {
                        dest[dx] = c2;
                    }
                    dx++;
                    sx2 = sx3;
                }
            }
        }
        if (this.savePrevious && (currentFrame.dispose == 0 || currentFrame.dispose == 1)) {
            if (this.previousImage == null) {
                this.previousImage = getNextBitmap();
            }
            this.previousImage.setPixels(dest, 0, width, 0, 0, width, height);
        }
        Bitmap result = getNextBitmap();
        result.setPixels(dest, 0, width, 0, 0, width, height);
        return result;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 5231. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    private void decodeBitmapData(com.bumptech.glide.gifdecoder.GifFrame r25) {
        /*
            Method dump skipped, instruction units count: 523
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.gifdecoder.GifDecoder.decodeBitmapData(com.bumptech.glide.gifdecoder.GifFrame):void");
    }

    private int read() {
        try {
            int curByte = this.rawData.get() & Constants.NETWORK_TYPE_UNCONNECTED;
            return curByte;
        } catch (Exception e) {
            this.status = 1;
            return 0;
        }
    }

    private int readBlock() {
        int blockSize = read();
        int n = 0;
        if (blockSize > 0) {
            while (n < blockSize) {
                int count = blockSize - n;
                try {
                    this.rawData.get(this.block, n, count);
                    n += count;
                } catch (Exception e) {
                    Log.w(TAG, "Error Reading Block", e);
                    this.status = 1;
                }
            }
        }
        return n;
    }

    private Bitmap getNextBitmap() {
        Bitmap result = this.bitmapProvider.obtain(this.header.width, this.header.height, BITMAP_CONFIG);
        if (result == null) {
            result = Bitmap.createBitmap(this.header.width, this.header.height, BITMAP_CONFIG);
        }
        setAlpha(result);
        return result;
    }

    @TargetApi(12)
    private static void setAlpha(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 12) {
            bitmap.setHasAlpha(true);
        }
    }
}
