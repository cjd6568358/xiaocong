package com.alibaba.mtl.appmonitor.model;

import com.alibaba.mtl.appmonitor.f.a;
import com.alibaba.mtl.log.model.LogField;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UTDimensionValueSet extends DimensionValueSet {
    private static final Set<LogField> a = new HashSet<LogField>() { // from class: com.alibaba.mtl.appmonitor.model.UTDimensionValueSet.1
        {
            add(LogField.PAGE);
            add(LogField.ARG1);
            add(LogField.ARG2);
            add(LogField.ARG3);
            add(LogField.ARGS);
        }
    };

    public Integer getEventId() {
        int iA;
        String str;
        if (this.map == null || (str = this.map.get(LogField.EVENTID.toString())) == null) {
            iA = 0;
        } else {
            try {
                iA = a.a(str);
            } catch (NumberFormatException e) {
                iA = 0;
            }
        }
        return Integer.valueOf(iA);
    }

    @Override // com.alibaba.mtl.appmonitor.model.DimensionValueSet, com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        super.clean();
    }

    @Override // com.alibaba.mtl.appmonitor.model.DimensionValueSet, com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... params) {
        super.fill(params);
    }
}
