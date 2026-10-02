package org.apache.http.impl.auth;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface NTLMEngine {
    String generateType1Msg(String str, String str2) throws NTLMEngineException;

    String generateType3Msg(String str, String str2, String str3, String str4, String str5) throws NTLMEngineException;
}
