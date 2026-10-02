package com.fasterxml.jackson.core;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface TreeNode {
    JsonToken asToken();

    JsonParser.NumberType numberType();

    JsonParser traverse();
}
