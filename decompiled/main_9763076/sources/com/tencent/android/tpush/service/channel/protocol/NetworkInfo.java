package com.tencent.android.tpush.service.channel.protocol;

import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class NetworkInfo extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public int ip;
    public byte network;
    public byte op;

    static {
        $assertionsDisabled = !NetworkInfo.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.NetworkInfo";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.NetworkInfo";
    }

    public int getIp() {
        return this.ip;
    }

    public void setIp(int i) {
        this.ip = i;
    }

    public byte getNetwork() {
        return this.network;
    }

    public void setNetwork(byte b) {
        this.network = b;
    }

    public byte getOp() {
        return this.op;
    }

    public void setOp(byte b) {
        this.op = b;
    }

    public NetworkInfo() {
        this.ip = 0;
        this.network = (byte) 0;
        this.op = (byte) 0;
    }

    public NetworkInfo(int i, byte b, byte b2) {
        this.ip = 0;
        this.network = (byte) 0;
        this.op = (byte) 0;
        this.ip = i;
        this.network = b;
        this.op = b2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        NetworkInfo networkInfo = (NetworkInfo) obj;
        return JceUtil.equals(this.ip, networkInfo.ip) && JceUtil.equals(this.network, networkInfo.network) && JceUtil.equals(this.op, networkInfo.op);
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
        jceOutputStream.write(this.network, 1);
        jceOutputStream.write(this.op, 2);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.ip = jceInputStream.read(this.ip, 0, true);
        this.network = jceInputStream.read(this.network, 1, true);
        this.op = jceInputStream.read(this.op, 2, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.ip, BDCloudMediaPlayer.OnNativeInvokeListener.ARG_IP);
        jceDisplayer.display(this.network, "network");
        jceDisplayer.display(this.op, "op");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.ip, true);
        jceDisplayer.displaySimple(this.network, true);
        jceDisplayer.displaySimple(this.op, false);
    }
}
