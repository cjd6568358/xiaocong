package com.tencent.android.tpush.service.channel.protocol;

import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsRedirectRsp extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public long ip;
    public int port;

    static {
        $assertionsDisabled = !TpnsRedirectRsp.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsRedirectRsp";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsRedirectRsp";
    }

    public long getIp() {
        return this.ip;
    }

    public void setIp(long j) {
        this.ip = j;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public TpnsRedirectRsp() {
        this.ip = 0L;
        this.port = 0;
    }

    public TpnsRedirectRsp(long j, int i) {
        this.ip = 0L;
        this.port = 0;
        this.ip = j;
        this.port = i;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsRedirectRsp tpnsRedirectRsp = (TpnsRedirectRsp) obj;
        return JceUtil.equals(this.ip, tpnsRedirectRsp.ip) && JceUtil.equals(this.port, tpnsRedirectRsp.port);
    }

    public int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            if ($assertionsDisabled) {
                return null;
            }
            throw new AssertionError();
        }
    }

    @Override // com.qq.taf.jce.JceStruct
    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.ip, 0);
        jceOutputStream.write(this.port, 1);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.ip = jceInputStream.read(this.ip, 0, false);
        this.port = jceInputStream.read(this.port, 1, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.ip, BDCloudMediaPlayer.OnNativeInvokeListener.ARG_IP);
        jceDisplayer.display(this.port, "port");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.ip, true);
        jceDisplayer.displaySimple(this.port, false);
    }
}
