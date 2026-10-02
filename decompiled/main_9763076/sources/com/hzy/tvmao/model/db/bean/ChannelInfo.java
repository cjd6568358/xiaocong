package com.hzy.tvmao.model.db.bean;

import com.hzy.tvmao.model.legacy.api.StreamHelper;
import com.hzy.tvmao.utils.LogUtil;
import com.hzy.tvmao.utils.c;
import com.tencent.android.tpush.common.Constants;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ChannelInfo implements Serializable {
    private static final long serialVersionUID = -3812070525470731599L;
    public byte[] encname;
    public byte[] encnum;
    private a key;
    public int linupId;
    public String pulse;
    public int channelId = 0;
    public String name = Constants.MAIN_VERSION_TAG;
    public String logo = Constants.MAIN_VERSION_TAG;
    public String llogo = Constants.MAIN_VERSION_TAG;
    public int ishidden = 0;
    public int num = 0;
    public short isHd = 0;
    public String countryId = Constants.MAIN_VERSION_TAG;
    public int sequence = 0;
    public short type = 0;
    public short fee = 0;
    public int deviceId = 0;

    public static class a {
        public int a;
        public String b;
        public int c;

        public a(int i, String str, int i2) {
            this.a = 0;
            this.b = Constants.MAIN_VERSION_TAG;
            this.c = 0;
            this.a = i;
            this.b = str;
            this.c = i2;
        }

        public a() {
            this.a = 0;
            this.b = Constants.MAIN_VERSION_TAG;
            this.c = 0;
        }

        public int hashCode() {
            return (((this.b == null ? 0 : this.b.hashCode()) + ((this.a + 31) * 31)) * 31) + this.c;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                a aVar = (a) obj;
                if (this.a != aVar.a) {
                    return false;
                }
                if (this.b == null) {
                    if (aVar.b != null) {
                        return false;
                    }
                } else if (!this.b.equals(aVar.b)) {
                    return false;
                }
                return this.c == aVar.c;
            }
            return false;
        }
    }

    public String toString() {
        return "ChannelInfo [channelId=" + this.channelId + ", name=" + this.name + ", logo=" + this.logo + ", llogo=" + this.llogo + ", ishidden=" + this.ishidden + ", num=" + this.num + ", isHd=" + ((int) this.isHd) + ", countryId=" + this.countryId + ", linupId=" + this.linupId + ", sequence=" + this.sequence + ", type=" + ((int) this.type) + ", fee=" + ((int) this.fee) + ", deviceId=" + this.deviceId + ", encname=" + Arrays.toString(this.encname) + ", encnum=" + Arrays.toString(this.encnum) + ", pulse=" + this.pulse + "]";
    }

    public void encrypt() {
        try {
            this.encname = StreamHelper.enc1(this.name.getBytes(HTTP.UTF_8));
            this.encnum = StreamHelper.enc1(String.valueOf(this.num).getBytes(HTTP.UTF_8));
            this.num = -1;
            this.name = Constants.MAIN_VERSION_TAG;
        } catch (UnsupportedEncodingException e) {
            LogUtil.d("encrypt failed");
            e.printStackTrace();
        }
    }

    public void decrypt() {
        try {
            this.name = new String(StreamHelper.dec1(this.encname), HTTP.UTF_8);
            if (this.encnum != null) {
                this.num = c.b(new String(StreamHelper.dec1(this.encnum), HTTP.UTF_8));
            }
        } catch (UnsupportedEncodingException e) {
            LogUtil.d("encrypt failed");
            e.printStackTrace();
        }
    }

    public a getKey() {
        if (this.key == null) {
            this.key = new a(this.channelId, this.countryId, this.isHd);
        }
        return this.key;
    }
}
