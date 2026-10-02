package com.xiaomi.push.service;

import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.network.Fallback;
import com.xiaomi.network.HostManager;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PacketSync {
    private XMPushService a;

    public interface PacketReceiveHandler extends Parcelable {
    }

    PacketSync(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    private void a(com.xiaomi.smack.packet.a aVar) {
        String strC = aVar.c();
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        String[] strArrSplit = strC.split(";");
        Fallback fallbacksByHost = HostManager.getInstance().getFallbacksByHost(com.xiaomi.smack.b.b(), false);
        if (fallbacksByHost == null || strArrSplit.length <= 0) {
            return;
        }
        fallbacksByHost.a(strArrSplit);
        this.a.a(20, (Exception) null);
        this.a.a(true);
    }

    private void b(com.xiaomi.smack.packet.d dVar) {
        ak.b bVarB;
        String strM = dVar.m();
        String strL = dVar.l();
        if (TextUtils.isEmpty(strM) || TextUtils.isEmpty(strL) || (bVarB = ak.a().b(strL, strM)) == null) {
            return;
        }
        com.xiaomi.smack.util.g.a(this.a, bVarB.a, com.xiaomi.smack.util.g.a(dVar.c()), true, System.currentTimeMillis());
    }

    private void c(com.xiaomi.slim.b bVar) {
        ak.b bVarB;
        String strJ = bVar.j();
        String string = Integer.toString(bVar.c());
        if (TextUtils.isEmpty(strJ) || TextUtils.isEmpty(string) || (bVarB = ak.a().b(string, strJ)) == null) {
            return;
        }
        com.xiaomi.smack.util.g.a(this.a, bVarB.a, bVar.l(), true, System.currentTimeMillis());
    }

    public void a(com.xiaomi.slim.b bVar) {
        if (5 != bVar.c()) {
            c(bVar);
        }
        try {
            b(bVar);
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("handle Blob chid = " + bVar.c() + " cmd = " + bVar.a() + " packetid = " + bVar.h() + " failure ", e);
        }
    }

    public void a(com.xiaomi.smack.packet.d dVar) {
        if (!"5".equals(dVar.l())) {
            b(dVar);
        }
        String strL = dVar.l();
        if (TextUtils.isEmpty(strL)) {
            strL = "1";
            dVar.l("1");
        }
        if (strL.equals("0")) {
            com.xiaomi.channel.commonutils.logger.b.a("Received wrong packet with chid = 0 : " + dVar.c());
        }
        if (dVar instanceof com.xiaomi.smack.packet.b) {
            com.xiaomi.smack.packet.a aVarP = dVar.p("kick");
            if (aVarP != null) {
                String strM = dVar.m();
                String strA = aVarP.a("type");
                String strA2 = aVarP.a("reason");
                com.xiaomi.channel.commonutils.logger.b.a("kicked by server, chid=" + strL + " userid=" + strM + " type=" + strA + " reason=" + strA2);
                if (!"wait".equals(strA)) {
                    this.a.a(strL, strM, 3, strA2, strA);
                    ak.a().a(strL, strM);
                    return;
                }
                ak.b bVarB = ak.a().b(strL, strM);
                if (bVarB != null) {
                    this.a.a(bVarB);
                    bVarB.a(ak.c.unbind, 3, 0, strA2, strA);
                    return;
                }
                return;
            }
        } else if (dVar instanceof com.xiaomi.smack.packet.c) {
            com.xiaomi.smack.packet.c cVar = (com.xiaomi.smack.packet.c) dVar;
            if ("redir".equals(cVar.a())) {
                com.xiaomi.smack.packet.a aVarP2 = cVar.p("hosts");
                if (aVarP2 != null) {
                    a(aVarP2);
                    return;
                }
                return;
            }
        }
        this.a.e().a(this.a, strL, dVar);
    }

    public void b(com.xiaomi.slim.b bVar) {
        String strA = bVar.a();
        switch (bVar.c()) {
            case 0:
                if ("PING".equals(strA)) {
                    byte[] bArrK = bVar.k();
                    if (bArrK != null && bArrK.length > 0) {
                        com.xiaomi.push.protobuf.b.j jVarB = com.xiaomi.push.protobuf.b.j.b(bArrK);
                        if (jVarB.f()) {
                            at.a().a(jVarB.g());
                        }
                    }
                    if (!"1".equals(bVar.h())) {
                        com.xiaomi.stats.h.b();
                    } else {
                        this.a.a();
                    }
                } else if (!"SYNC".equals(strA)) {
                    if ("NOTIFY".equals(bVar.a())) {
                        com.xiaomi.push.protobuf.b.h hVarB = com.xiaomi.push.protobuf.b.h.b(bVar.k());
                        com.xiaomi.channel.commonutils.logger.b.a("notify by server err = " + hVarB.d() + " desc = " + hVarB.f());
                    }
                } else if ("CONF".equals(bVar.b())) {
                    at.a().a(com.xiaomi.push.protobuf.b.C0012b.b(bVar.k()));
                } else if (TextUtils.equals("U", bVar.b())) {
                    com.xiaomi.push.protobuf.b.k kVarB = com.xiaomi.push.protobuf.b.k.b(bVar.k());
                    com.xiaomi.push.log.b.a(this.a).a(kVarB.d(), kVarB.f(), new Date(kVarB.h()), new Date(kVarB.j()), kVarB.n() * 1024, kVarB.l());
                    com.xiaomi.slim.b bVar2 = new com.xiaomi.slim.b();
                    bVar2.a(0);
                    bVar2.a(bVar.a(), "UCA");
                    bVar2.a(bVar.h());
                    this.a.a(new as(this.a, bVar2));
                } else if (TextUtils.equals("P", bVar.b())) {
                    com.xiaomi.push.protobuf.b.i iVarB = com.xiaomi.push.protobuf.b.i.b(bVar.k());
                    com.xiaomi.slim.b bVar3 = new com.xiaomi.slim.b();
                    bVar3.a(0);
                    bVar3.a(bVar.a(), "PCA");
                    bVar3.a(bVar.h());
                    com.xiaomi.push.protobuf.b.i iVar = new com.xiaomi.push.protobuf.b.i();
                    if (iVarB.e()) {
                        iVar.a(iVarB.d());
                    }
                    bVar3.a(iVar.c(), (String) null);
                    this.a.a(new as(this.a, bVar3));
                    com.xiaomi.channel.commonutils.logger.b.a("ACK msgP: id = " + bVar.h());
                }
                break;
            default:
                String string = Integer.toString(bVar.c());
                if (!"SECMSG".equals(bVar.a())) {
                    if ("BIND".equals(strA)) {
                        com.xiaomi.push.protobuf.b.d dVarB = com.xiaomi.push.protobuf.b.d.b(bVar.k());
                        String strJ = bVar.j();
                        ak.b bVarB = ak.a().b(string, strJ);
                        if (bVarB != null) {
                            if (!dVarB.d()) {
                                String strF = dVarB.f();
                                if ("auth".equals(strF)) {
                                    if ("invalid-sig".equals(dVarB.h())) {
                                        com.xiaomi.channel.commonutils.logger.b.a("SMACK: bind error invalid-sig token = " + bVarB.c + " sec = " + bVarB.i);
                                        com.xiaomi.stats.h.a(0, com.xiaomi.push.thrift.a.BIND_INVALID_SIG.a(), 1, null, 0);
                                    }
                                    bVarB.a(ak.c.unbind, 1, 5, dVarB.h(), strF);
                                    ak.a().a(string, strJ);
                                } else if ("cancel".equals(strF)) {
                                    bVarB.a(ak.c.unbind, 1, 7, dVarB.h(), strF);
                                    ak.a().a(string, strJ);
                                } else if ("wait".equals(strF)) {
                                    this.a.a(bVarB);
                                    bVarB.a(ak.c.unbind, 1, 7, dVarB.h(), strF);
                                }
                                com.xiaomi.channel.commonutils.logger.b.a("SMACK: channel bind failed, chid=" + string + " reason=" + dVarB.h());
                            } else {
                                com.xiaomi.channel.commonutils.logger.b.a("SMACK: channel bind succeeded, chid=" + bVar.c());
                                bVarB.a(ak.c.binded, 1, 0, null, null);
                            }
                        }
                    } else if ("KICK".equals(strA)) {
                        com.xiaomi.push.protobuf.b.g gVarB = com.xiaomi.push.protobuf.b.g.b(bVar.k());
                        String strJ2 = bVar.j();
                        String strD = gVarB.d();
                        String strF2 = gVarB.f();
                        com.xiaomi.channel.commonutils.logger.b.a("kicked by server, chid=" + string + " userid=" + strJ2 + " type=" + strD + " reason=" + strF2);
                        if (!"wait".equals(strD)) {
                            this.a.a(string, strJ2, 3, strF2, strD);
                            ak.a().a(string, strJ2);
                        } else {
                            ak.b bVarB2 = ak.a().b(string, strJ2);
                            if (bVarB2 != null) {
                                this.a.a(bVarB2);
                                bVarB2.a(ak.c.unbind, 3, 0, strF2, strD);
                            }
                        }
                    }
                } else if (!bVar.d()) {
                    this.a.e().a(this.a, string, bVar);
                } else {
                    com.xiaomi.channel.commonutils.logger.b.a("Recv SECMSG errCode = " + bVar.e() + " errStr = " + bVar.f());
                }
                break;
        }
    }
}
