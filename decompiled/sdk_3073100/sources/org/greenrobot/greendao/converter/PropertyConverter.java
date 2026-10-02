package org.greenrobot.greendao.converter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface PropertyConverter<P, D> {
    D convertToDatabaseValue(P p);

    P convertToEntityProperty(D d);
}
