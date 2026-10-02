package com.bumptech.glide.gifencoder;

import com.hzy.tvmao.ir.ac.ACConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class NeuQuant {
    protected int alphadec;
    protected int lengthcount;
    protected int samplefac;
    protected byte[] thepicture;
    protected int[] netindex = new int[256];
    protected int[] bias = new int[256];
    protected int[] freq = new int[256];
    protected int[] radpower = new int[32];
    protected int[][] network = new int[256][];

    public NeuQuant(byte[] thepic, int len, int sample) {
        this.thepicture = thepic;
        this.lengthcount = len;
        this.samplefac = sample;
        for (int i = 0; i < 256; i++) {
            this.network[i] = new int[4];
            int[] p = this.network[i];
            int i2 = (i << 12) / 256;
            p[2] = i2;
            p[1] = i2;
            p[0] = i2;
            this.freq[i] = 256;
            this.bias[i] = 0;
        }
    }

    public byte[] colorMap() {
        byte[] map = new byte[768];
        int[] index = new int[256];
        for (int i = 0; i < 256; i++) {
            index[this.network[i][3]] = i;
        }
        int i2 = 0;
        int k = 0;
        while (i2 < 256) {
            int j = index[i2];
            int k2 = k + 1;
            map[k] = (byte) this.network[j][0];
            int k3 = k2 + 1;
            map[k2] = (byte) this.network[j][1];
            map[k3] = (byte) this.network[j][2];
            i2++;
            k = k3 + 1;
        }
        return map;
    }

    public void inxbuild() {
        int previouscol = 0;
        int startpos = 0;
        for (int i = 0; i < 256; i++) {
            int[] p = this.network[i];
            int smallpos = i;
            int smallval = p[1];
            for (int j = i + 1; j < 256; j++) {
                int[] q = this.network[j];
                if (q[1] < smallval) {
                    smallpos = j;
                    smallval = q[1];
                }
            }
            int[] q2 = this.network[smallpos];
            if (i != smallpos) {
                int j2 = q2[0];
                q2[0] = p[0];
                p[0] = j2;
                int j3 = q2[1];
                q2[1] = p[1];
                p[1] = j3;
                int j4 = q2[2];
                q2[2] = p[2];
                p[2] = j4;
                int j5 = q2[3];
                q2[3] = p[3];
                p[3] = j5;
            }
            if (smallval != previouscol) {
                this.netindex[previouscol] = (startpos + i) >> 1;
                for (int j6 = previouscol + 1; j6 < smallval; j6++) {
                    this.netindex[j6] = i;
                }
                previouscol = smallval;
                startpos = i;
            }
        }
        this.netindex[previouscol] = (startpos + 255) >> 1;
        for (int j7 = previouscol + 1; j7 < 256; j7++) {
            this.netindex[j7] = 255;
        }
    }

    public void learn() {
        int step;
        if (this.lengthcount < 1509) {
            this.samplefac = 1;
        }
        this.alphadec = ((this.samplefac - 1) / 3) + 30;
        byte[] p = this.thepicture;
        int pix = 0;
        int lim = this.lengthcount;
        int samplepixels = this.lengthcount / (this.samplefac * 3);
        int delta = samplepixels / 100;
        int alpha = WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
        int radius = 2048;
        int rad = 2048 >> 6;
        if (rad <= 1) {
            rad = 0;
        }
        for (int i = 0; i < rad; i++) {
            this.radpower[i] = ((((rad * rad) - (i * i)) * 256) / (rad * rad)) * WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
        }
        if (this.lengthcount < 1509) {
            step = 3;
        } else if (this.lengthcount % 499 != 0) {
            step = 1497;
        } else if (this.lengthcount % 491 != 0) {
            step = 1473;
        } else if (this.lengthcount % 487 != 0) {
            step = 1461;
        } else {
            step = ACConstants.TAG_BYTE_BIT_NUM;
        }
        int i2 = 0;
        while (i2 < samplepixels) {
            int b = (p[pix + 0] & Constants.NETWORK_TYPE_UNCONNECTED) << 4;
            int g = (p[pix + 1] & Constants.NETWORK_TYPE_UNCONNECTED) << 4;
            int r = (p[pix + 2] & Constants.NETWORK_TYPE_UNCONNECTED) << 4;
            int j = contest(b, g, r);
            altersingle(alpha, j, b, g, r);
            if (rad != 0) {
                alterneigh(rad, j, b, g, r);
            }
            pix += step;
            if (pix >= lim) {
                pix -= this.lengthcount;
            }
            i2++;
            if (delta == 0) {
                delta = 1;
            }
            if (i2 % delta == 0) {
                alpha -= alpha / this.alphadec;
                radius -= radius / 30;
                rad = radius >> 6;
                if (rad <= 1) {
                    rad = 0;
                }
                for (int j2 = 0; j2 < rad; j2++) {
                    this.radpower[j2] = ((((rad * rad) - (j2 * j2)) * 256) / (rad * rad)) * alpha;
                }
            }
        }
    }

    public int map(int b, int g, int r) {
        int bestd = 1000;
        int best = -1;
        int i = this.netindex[g];
        int j = i - 1;
        while (true) {
            if (i < 256 || j >= 0) {
                if (i < 256) {
                    int[] p = this.network[i];
                    int dist = p[1] - g;
                    if (dist >= bestd) {
                        i = 256;
                    } else {
                        i++;
                        if (dist < 0) {
                            dist = -dist;
                        }
                        int a = p[0] - b;
                        if (a < 0) {
                            a = -a;
                        }
                        int dist2 = dist + a;
                        if (dist2 < bestd) {
                            int a2 = p[2] - r;
                            if (a2 < 0) {
                                a2 = -a2;
                            }
                            int dist3 = dist2 + a2;
                            if (dist3 < bestd) {
                                bestd = dist3;
                                best = p[3];
                            }
                        }
                    }
                }
                if (j >= 0) {
                    int[] p2 = this.network[j];
                    int dist4 = g - p2[1];
                    if (dist4 >= bestd) {
                        j = -1;
                    } else {
                        j--;
                        if (dist4 < 0) {
                            dist4 = -dist4;
                        }
                        int a3 = p2[0] - b;
                        if (a3 < 0) {
                            a3 = -a3;
                        }
                        int dist5 = dist4 + a3;
                        if (dist5 < bestd) {
                            int a4 = p2[2] - r;
                            if (a4 < 0) {
                                a4 = -a4;
                            }
                            int dist6 = dist5 + a4;
                            if (dist6 < bestd) {
                                bestd = dist6;
                                best = p2[3];
                            }
                        }
                    }
                }
            } else {
                return best;
            }
        }
    }

    public byte[] process() {
        learn();
        unbiasnet();
        inxbuild();
        return colorMap();
    }

    public void unbiasnet() {
        for (int i = 0; i < 256; i++) {
            int[] iArr = this.network[i];
            iArr[0] = iArr[0] >> 4;
            int[] iArr2 = this.network[i];
            iArr2[1] = iArr2[1] >> 4;
            int[] iArr3 = this.network[i];
            iArr3[2] = iArr3[2] >> 4;
            this.network[i][3] = i;
        }
    }

    protected void alterneigh(int rad, int i, int b, int g, int r) {
        int lo = i - rad;
        if (lo < -1) {
            lo = -1;
        }
        int hi = i + rad;
        if (hi > 256) {
            hi = 256;
        }
        int j = i + 1;
        int k = i - 1;
        int m = 1;
        int k2 = k;
        int j2 = j;
        while (true) {
            if (j2 < hi || k2 > lo) {
                m++;
                int a = this.radpower[m];
                if (j2 < hi) {
                    j2++;
                    int[] p = this.network[j2];
                    try {
                        p[0] = p[0] - (((p[0] - b) * a) / 262144);
                        p[1] = p[1] - (((p[1] - g) * a) / 262144);
                        p[2] = p[2] - (((p[2] - r) * a) / 262144);
                    } catch (Exception e) {
                    }
                } else {
                    j2 = j2;
                }
                if (k2 > lo) {
                    int k3 = k2 - 1;
                    int[] p2 = this.network[k2];
                    try {
                        p2[0] = p2[0] - (((p2[0] - b) * a) / 262144);
                        p2[1] = p2[1] - (((p2[1] - g) * a) / 262144);
                        p2[2] = p2[2] - (((p2[2] - r) * a) / 262144);
                        k2 = k3;
                    } catch (Exception e2) {
                        k2 = k3;
                    }
                }
            } else {
                return;
            }
        }
    }

    protected void altersingle(int alpha, int i, int b, int g, int r) {
        int[] n = this.network[i];
        n[0] = n[0] - (((n[0] - b) * alpha) / WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
        n[1] = n[1] - (((n[1] - g) * alpha) / WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
        n[2] = n[2] - (((n[2] - r) * alpha) / WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
    }

    protected int contest(int b, int g, int r) {
        int bestd = Integer.MAX_VALUE;
        int bestbiasd = Integer.MAX_VALUE;
        int bestpos = -1;
        int bestbiaspos = -1;
        for (int i = 0; i < 256; i++) {
            int[] n = this.network[i];
            int dist = n[0] - b;
            if (dist < 0) {
                dist = -dist;
            }
            int a = n[1] - g;
            if (a < 0) {
                a = -a;
            }
            int dist2 = dist + a;
            int a2 = n[2] - r;
            if (a2 < 0) {
                a2 = -a2;
            }
            int dist3 = dist2 + a2;
            if (dist3 < bestd) {
                bestd = dist3;
                bestpos = i;
            }
            int biasdist = dist3 - (this.bias[i] >> 12);
            if (biasdist < bestbiasd) {
                bestbiasd = biasdist;
                bestbiaspos = i;
            }
            int betafreq = this.freq[i] >> 10;
            int[] iArr = this.freq;
            iArr[i] = iArr[i] - betafreq;
            int[] iArr2 = this.bias;
            iArr2[i] = iArr2[i] + (betafreq << 10);
        }
        int[] iArr3 = this.freq;
        iArr3[bestpos] = iArr3[bestpos] + 64;
        int[] iArr4 = this.bias;
        iArr4[bestpos] = iArr4[bestpos] - 65536;
        return bestbiaspos;
    }
}
