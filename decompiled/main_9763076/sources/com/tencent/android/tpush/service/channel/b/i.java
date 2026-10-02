package com.tencent.android.tpush.service.channel.b;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class i extends f {
    static final /* synthetic */ boolean o;
    protected short d;
    protected int e;
    protected long f;
    protected long g;
    protected short h;
    protected short i;
    protected short k;
    protected short l;
    protected short m;
    protected byte[] n = new byte[0];

    static {
        o = !i.class.desiredAssertionStatus();
    }

    public boolean e() {
        return (this.h & 128) != 0;
    }

    public byte f() {
        return (byte) (this.h & 127);
    }

    public String g() {
        switch (f()) {
            case 1:
                return "TPNS_COMMAND_PUSH";
            case 2:
                return "TPNS_COMMAND_GET_APLIST";
            case 3:
                return "TPNS_COMMAND_PULLCONFIG";
            case 4:
                return "TPNS_COMMAND_REGISTER";
            case 5:
                return "TPNS_COMMAND_UNREGISTER";
            case 6:
                return "TPNS_COMMAND_RECONNECT";
            case 7:
                return "TPNS_COMMAND_HEARTBEAT";
            case 8:
                return "TPNS_COMMAND_UNINSTALL";
            case 9:
                return "TPNS_COMMAND_CLIENTREPORT";
            case 10:
                return "TPNS_COMMAND_REDIRECT";
            case 11:
                return "TPNS_COMMAND_PUSH_VERIFY";
            case 12:
                return "TPNS_COMMAND_SPEEDTEST";
            case 13:
                return "TPNS_COMMAND_TRIGGER_REPORT";
            case 14:
                return "TPNS_COMMAND_CHECK_MSG";
            case 15:
                return "TPNS_COMMAND_TAG";
            case 16:
                return "TPNS_COMMAND_PUSH_CLICK";
            case 17:
                return "TPNS_COMMAND_UPDATE_OTH_TOKEN";
            case 18:
                return "TPNS_COMMAND_PUSH_COMM_REPORT";
            default:
                return "TPNS_COMMAND_UNKNOWN " + ((int) f());
        }
    }

    public short h() {
        return this.h;
    }

    public void a(short s) {
        this.h = s;
    }

    public int i() {
        return this.e;
    }

    public short j() {
        return this.k;
    }

    public void b(short s) {
        this.k = s;
    }

    public byte[] k() {
        return this.n;
    }

    public void a(byte[] bArr) {
        this.n = bArr;
    }

    public short l() {
        return this.m;
    }

    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append("(p:").append((int) this.k).append("|v:").append((int) this.l).append("|r:").append(this.g).append("|s:").append(this.e).append("|c:").append(Integer.toHexString(this.h)).append("|r:").append((int) this.m).append("|l:").append(this.f).append(")").toString();
    }

    public String m() {
        return new StringBuffer(getClass().getSimpleName()).append(" protocol = ").append((int) this.k).append(" command = ").append(g()).append(" isResponse = ").append(e()).append(" packetLength = ").append(this.f).toString();
    }
}
