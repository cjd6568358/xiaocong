package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.cookie.ClientCookie;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ApList extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static ArrayList cache_portList;
    static Map cache_primary;
    static Map cache_secondary;
    static ArrayList cache_speedTestIpList;
    public long backup;
    public String domain;
    public ArrayList portList;
    public Map primary;
    public Map secondary;
    public ArrayList speedTestIpList;

    static {
        $assertionsDisabled = !ApList.class.desiredAssertionStatus();
        cache_primary = new HashMap();
        cache_primary.put((byte) 0, 0L);
        cache_secondary = new HashMap();
        cache_secondary.put((byte) 0, 0L);
        cache_portList = new ArrayList();
        cache_portList.add(0);
        cache_speedTestIpList = new ArrayList();
        cache_speedTestIpList.add(0L);
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.ApList";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.ApList";
    }

    public Map getPrimary() {
        return this.primary;
    }

    public void setPrimary(Map map) {
        this.primary = map;
    }

    public Map getSecondary() {
        return this.secondary;
    }

    public void setSecondary(Map map) {
        this.secondary = map;
    }

    public long getBackup() {
        return this.backup;
    }

    public void setBackup(long j) {
        this.backup = j;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String str) {
        this.domain = str;
    }

    public ArrayList getPortList() {
        return this.portList;
    }

    public void setPortList(ArrayList arrayList) {
        this.portList = arrayList;
    }

    public ArrayList getSpeedTestIpList() {
        return this.speedTestIpList;
    }

    public void setSpeedTestIpList(ArrayList arrayList) {
        this.speedTestIpList = arrayList;
    }

    public ApList() {
        this.primary = null;
        this.secondary = null;
        this.backup = 0L;
        this.domain = Constants.MAIN_VERSION_TAG;
        this.portList = null;
        this.speedTestIpList = null;
    }

    public ApList(Map map, Map map2, long j, String str, ArrayList arrayList, ArrayList arrayList2) {
        this.primary = null;
        this.secondary = null;
        this.backup = 0L;
        this.domain = Constants.MAIN_VERSION_TAG;
        this.portList = null;
        this.speedTestIpList = null;
        this.primary = map;
        this.secondary = map2;
        this.backup = j;
        this.domain = str;
        this.portList = arrayList;
        this.speedTestIpList = arrayList2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        ApList apList = (ApList) obj;
        return JceUtil.equals(this.primary, apList.primary) && JceUtil.equals(this.secondary, apList.secondary) && JceUtil.equals(this.backup, apList.backup) && JceUtil.equals(this.domain, apList.domain) && JceUtil.equals(this.portList, apList.portList) && JceUtil.equals(this.speedTestIpList, apList.speedTestIpList);
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
        jceOutputStream.write(this.primary, 0);
        jceOutputStream.write(this.secondary, 1);
        jceOutputStream.write(this.backup, 2);
        jceOutputStream.write(this.domain, 3);
        jceOutputStream.write((Collection) this.portList, 4);
        jceOutputStream.write((Collection) this.speedTestIpList, 5);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.primary = (Map) jceInputStream.read(cache_primary, 0, true);
        this.secondary = (Map) jceInputStream.read(cache_secondary, 1, true);
        this.backup = jceInputStream.read(this.backup, 2, true);
        this.domain = jceInputStream.readString(3, true);
        this.portList = (ArrayList) jceInputStream.read(cache_portList, 4, true);
        this.speedTestIpList = (ArrayList) jceInputStream.read(cache_speedTestIpList, 5, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.primary, "primary");
        jceDisplayer.display(this.secondary, "secondary");
        jceDisplayer.display(this.backup, "backup");
        jceDisplayer.display(this.domain, ClientCookie.DOMAIN_ATTR);
        jceDisplayer.display((Collection) this.portList, "portList");
        jceDisplayer.display((Collection) this.speedTestIpList, "speedTestIpList");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.primary, true);
        jceDisplayer.displaySimple(this.secondary, true);
        jceDisplayer.displaySimple(this.backup, true);
        jceDisplayer.displaySimple(this.domain, true);
        jceDisplayer.displaySimple((Collection) this.portList, true);
        jceDisplayer.displaySimple((Collection) this.speedTestIpList, false);
    }
}
