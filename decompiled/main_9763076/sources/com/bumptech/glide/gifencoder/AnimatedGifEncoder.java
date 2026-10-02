package com.bumptech.glide.gifencoder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AnimatedGifEncoder {
    private int colorDepth;
    private byte[] colorTab;
    private boolean hasTransparentPixels;
    private int height;
    private Bitmap image;
    private byte[] indexedPixels;
    private OutputStream out;
    private byte[] pixels;
    private int transIndex;
    private int width;
    private Integer transparent = null;
    private int repeat = -1;
    private int delay = 0;
    private boolean started = false;
    private boolean[] usedEntry = new boolean[256];
    private int palSize = 7;
    private int dispose = -1;
    private boolean closeStream = false;
    private boolean firstFrame = true;
    private boolean sizeSet = false;
    private int sample = 10;

    public void setDelay(int ms) {
        this.delay = Math.round(ms / 10.0f);
    }

    public boolean addFrame(Bitmap im) {
        if (im == null || !this.started) {
            return false;
        }
        try {
            if (!this.sizeSet) {
                setSize(im.getWidth(), im.getHeight());
            }
            this.image = im;
            getImagePixels();
            analyzePixels();
            if (this.firstFrame) {
                writeLSD();
                writePalette();
                if (this.repeat >= 0) {
                    writeNetscapeExt();
                }
            }
            writeGraphicCtrlExt();
            writeImageDesc();
            if (!this.firstFrame) {
                writePalette();
            }
            writePixels();
            this.firstFrame = false;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean finish() {
        if (!this.started) {
            return false;
        }
        boolean ok = true;
        this.started = false;
        try {
            this.out.write(59);
            this.out.flush();
            if (this.closeStream) {
                this.out.close();
            }
        } catch (IOException e) {
            ok = false;
        }
        this.transIndex = 0;
        this.out = null;
        this.image = null;
        this.pixels = null;
        this.indexedPixels = null;
        this.colorTab = null;
        this.closeStream = false;
        this.firstFrame = true;
        return ok;
    }

    public void setSize(int w, int h) {
        if (!this.started || this.firstFrame) {
            this.width = w;
            this.height = h;
            if (this.width < 1) {
                this.width = 320;
            }
            if (this.height < 1) {
                this.height = 240;
            }
            this.sizeSet = true;
        }
    }

    public boolean start(OutputStream os) {
        if (os == null) {
            return false;
        }
        boolean ok = true;
        this.closeStream = false;
        this.out = os;
        try {
            writeString("GIF89a");
        } catch (IOException e) {
            ok = false;
        }
        this.started = ok;
        return ok;
    }

    private void analyzePixels() {
        int len = this.pixels.length;
        int nPix = len / 3;
        this.indexedPixels = new byte[nPix];
        NeuQuant nq = new NeuQuant(this.pixels, len, this.sample);
        this.colorTab = nq.process();
        for (int i = 0; i < this.colorTab.length; i += 3) {
            byte temp = this.colorTab[i];
            this.colorTab[i] = this.colorTab[i + 2];
            this.colorTab[i + 2] = temp;
            this.usedEntry[i / 3] = false;
        }
        int i2 = 0;
        int k = 0;
        while (i2 < nPix) {
            int k2 = k + 1;
            int i3 = this.pixels[k] & Constants.NETWORK_TYPE_UNCONNECTED;
            int k3 = k2 + 1;
            int index = nq.map(i3, this.pixels[k2] & Constants.NETWORK_TYPE_UNCONNECTED, this.pixels[k3] & Constants.NETWORK_TYPE_UNCONNECTED);
            this.usedEntry[index] = true;
            this.indexedPixels[i2] = (byte) index;
            i2++;
            k = k3 + 1;
        }
        this.pixels = null;
        this.colorDepth = 8;
        this.palSize = 7;
        if (this.transparent != null) {
            this.transIndex = findClosest(this.transparent.intValue());
        } else if (this.hasTransparentPixels) {
            this.transIndex = findClosest(0);
        }
    }

    private int findClosest(int color) {
        if (this.colorTab == null) {
            return -1;
        }
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int minpos = 0;
        int dmin = 16777216;
        int len = this.colorTab.length;
        int i = 0;
        while (i < len) {
            int i2 = i + 1;
            int dr = r - (this.colorTab[i] & Constants.NETWORK_TYPE_UNCONNECTED);
            int i3 = i2 + 1;
            int dg = g - (this.colorTab[i2] & Constants.NETWORK_TYPE_UNCONNECTED);
            int db = b - (this.colorTab[i3] & Constants.NETWORK_TYPE_UNCONNECTED);
            int d = (dr * dr) + (dg * dg) + (db * db);
            int index = i3 / 3;
            if (this.usedEntry[index] && d < dmin) {
                dmin = d;
                minpos = index;
            }
            i = i3 + 1;
        }
        return minpos;
    }

    private void getImagePixels() {
        int w = this.image.getWidth();
        int h = this.image.getHeight();
        if (w != this.width || h != this.height) {
            Bitmap temp = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(temp);
            canvas.drawBitmap(temp, 0.0f, 0.0f, (Paint) null);
            this.image = temp;
        }
        int[] pixelsInt = new int[w * h];
        this.image.getPixels(pixelsInt, 0, w, 0, 0, w, h);
        this.pixels = new byte[pixelsInt.length * 3];
        this.hasTransparentPixels = false;
        int totalTransparentPixels = 0;
        int len$ = pixelsInt.length;
        int i$ = 0;
        int pixelsIndex = 0;
        while (i$ < len$) {
            int pixel = pixelsInt[i$];
            if (pixel == 0) {
                totalTransparentPixels++;
            }
            int pixelsIndex2 = pixelsIndex + 1;
            this.pixels[pixelsIndex] = (byte) (pixel & 255);
            int pixelsIndex3 = pixelsIndex2 + 1;
            this.pixels[pixelsIndex2] = (byte) ((pixel >> 8) & 255);
            this.pixels[pixelsIndex3] = (byte) ((pixel >> 16) & 255);
            i$++;
            pixelsIndex = pixelsIndex3 + 1;
        }
        double transparentPercentage = ((double) (totalTransparentPixels * 100)) / ((double) pixelsInt.length);
        this.hasTransparentPixels = transparentPercentage > 4.0d;
        if (Log.isLoggable("AnimatedGifEncoder", 3)) {
            Log.d("AnimatedGifEncoder", "got pixels for frame with " + transparentPercentage + "% transparent pixels");
        }
    }

    private void writeGraphicCtrlExt() throws IOException {
        int transp;
        int disp;
        this.out.write(33);
        this.out.write(249);
        this.out.write(4);
        if (this.transparent == null && !this.hasTransparentPixels) {
            transp = 0;
            disp = 0;
        } else {
            transp = 1;
            disp = 2;
        }
        if (this.dispose >= 0) {
            disp = this.dispose & 7;
        }
        this.out.write((disp << 2) | 0 | 0 | transp);
        writeShort(this.delay);
        this.out.write(this.transIndex);
        this.out.write(0);
    }

    private void writeImageDesc() throws IOException {
        this.out.write(44);
        writeShort(0);
        writeShort(0);
        writeShort(this.width);
        writeShort(this.height);
        if (this.firstFrame) {
            this.out.write(0);
        } else {
            this.out.write(this.palSize | ParserConstants.LSHIFTASSIGN);
        }
    }

    private void writeLSD() throws IOException {
        writeShort(this.width);
        writeShort(this.height);
        this.out.write(this.palSize | 240);
        this.out.write(0);
        this.out.write(0);
    }

    private void writeNetscapeExt() throws IOException {
        this.out.write(33);
        this.out.write(255);
        this.out.write(11);
        writeString("NETSCAPE2.0");
        this.out.write(3);
        this.out.write(1);
        writeShort(this.repeat);
        this.out.write(0);
    }

    private void writePalette() throws IOException {
        this.out.write(this.colorTab, 0, this.colorTab.length);
        int n = 768 - this.colorTab.length;
        for (int i = 0; i < n; i++) {
            this.out.write(0);
        }
    }

    private void writePixels() throws IOException {
        LZWEncoder encoder = new LZWEncoder(this.width, this.height, this.indexedPixels, this.colorDepth);
        encoder.encode(this.out);
    }

    private void writeShort(int value) throws IOException {
        this.out.write(value & 255);
        this.out.write((value >> 8) & 255);
    }

    private void writeString(String s) throws IOException {
        for (int i = 0; i < s.length(); i++) {
            this.out.write((byte) s.charAt(i));
        }
    }
}
