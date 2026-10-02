package com.bumptech.glide.gifencoder;

import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class LZWEncoder {
    int ClearCode;
    int EOFCode;
    int a_count;
    private int curPixel;
    int g_init_bits;
    private int imgH;
    private int imgW;
    private int initCodeSize;
    int maxcode;
    int n_bits;
    private byte[] pixAry;
    private int remaining;
    int maxbits = 12;
    int maxmaxcode = 4096;
    int[] htab = new int[5003];
    int[] codetab = new int[5003];
    int hsize = 5003;
    int free_ent = 0;
    boolean clear_flg = false;
    int cur_accum = 0;
    int cur_bits = 0;
    int[] masks = {0, 1, 3, 7, 15, 31, 63, ParserConstants.MODASSIGN, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535};
    byte[] accum = new byte[256];

    LZWEncoder(int width, int height, byte[] pixels, int color_depth) {
        this.imgW = width;
        this.imgH = height;
        this.pixAry = pixels;
        this.initCodeSize = Math.max(2, color_depth);
    }

    void char_out(byte c, OutputStream outs) throws IOException {
        byte[] bArr = this.accum;
        int i = this.a_count;
        this.a_count = i + 1;
        bArr[i] = c;
        if (this.a_count >= 254) {
            flush_char(outs);
        }
    }

    void cl_block(OutputStream outs) throws IOException {
        cl_hash(this.hsize);
        this.free_ent = this.ClearCode + 2;
        this.clear_flg = true;
        output(this.ClearCode, outs);
    }

    void cl_hash(int hsize) {
        for (int i = 0; i < hsize; i++) {
            this.htab[i] = -1;
        }
    }

    void compress(int init_bits, OutputStream outs) throws IOException {
        this.g_init_bits = init_bits;
        this.clear_flg = false;
        this.n_bits = this.g_init_bits;
        this.maxcode = MAXCODE(this.n_bits);
        this.ClearCode = 1 << (init_bits - 1);
        this.EOFCode = this.ClearCode + 1;
        this.free_ent = this.ClearCode + 2;
        this.a_count = 0;
        int ent = nextPixel();
        int hshift = 0;
        for (int fcode = this.hsize; fcode < 65536; fcode *= 2) {
            hshift++;
        }
        int hshift2 = 8 - hshift;
        int hsize_reg = this.hsize;
        cl_hash(hsize_reg);
        output(this.ClearCode, outs);
        while (true) {
            int c = nextPixel();
            if (c != -1) {
                int fcode2 = (c << this.maxbits) + ent;
                int i = (c << hshift2) ^ ent;
                if (this.htab[i] == fcode2) {
                    ent = this.codetab[i];
                } else {
                    if (this.htab[i] >= 0) {
                        int disp = hsize_reg - i;
                        if (i == 0) {
                            disp = 1;
                        }
                        while (true) {
                            i -= disp;
                            if (i < 0) {
                                i += hsize_reg;
                            }
                            if (this.htab[i] == fcode2) {
                                ent = this.codetab[i];
                            } else if (this.htab[i] < 0) {
                            }
                        }
                    }
                    output(ent, outs);
                    ent = c;
                    if (this.free_ent < this.maxmaxcode) {
                        int[] iArr = this.codetab;
                        int i2 = this.free_ent;
                        this.free_ent = i2 + 1;
                        iArr[i] = i2;
                        this.htab[i] = fcode2;
                    } else {
                        cl_block(outs);
                    }
                }
            } else {
                output(ent, outs);
                output(this.EOFCode, outs);
                return;
            }
        }
    }

    void encode(OutputStream os) throws IOException {
        os.write(this.initCodeSize);
        this.remaining = this.imgW * this.imgH;
        this.curPixel = 0;
        compress(this.initCodeSize + 1, os);
        os.write(0);
    }

    void flush_char(OutputStream outs) throws IOException {
        if (this.a_count > 0) {
            outs.write(this.a_count);
            outs.write(this.accum, 0, this.a_count);
            this.a_count = 0;
        }
    }

    final int MAXCODE(int n_bits) {
        return (1 << n_bits) - 1;
    }

    private int nextPixel() {
        if (this.remaining == 0) {
            return -1;
        }
        this.remaining--;
        byte[] bArr = this.pixAry;
        int i = this.curPixel;
        this.curPixel = i + 1;
        byte pix = bArr[i];
        return pix & Constants.NETWORK_TYPE_UNCONNECTED;
    }

    void output(int code, OutputStream outs) throws IOException {
        this.cur_accum &= this.masks[this.cur_bits];
        if (this.cur_bits > 0) {
            this.cur_accum |= code << this.cur_bits;
        } else {
            this.cur_accum = code;
        }
        this.cur_bits += this.n_bits;
        while (this.cur_bits >= 8) {
            char_out((byte) (this.cur_accum & 255), outs);
            this.cur_accum >>= 8;
            this.cur_bits -= 8;
        }
        if (this.free_ent > this.maxcode || this.clear_flg) {
            if (this.clear_flg) {
                int i = this.g_init_bits;
                this.n_bits = i;
                this.maxcode = MAXCODE(i);
                this.clear_flg = false;
            } else {
                this.n_bits++;
                if (this.n_bits == this.maxbits) {
                    this.maxcode = this.maxmaxcode;
                } else {
                    this.maxcode = MAXCODE(this.n_bits);
                }
            }
        }
        if (code == this.EOFCode) {
            while (this.cur_bits > 0) {
                char_out((byte) (this.cur_accum & 255), outs);
                this.cur_accum >>= 8;
                this.cur_bits -= 8;
            }
            flush_char(outs);
        }
    }
}
