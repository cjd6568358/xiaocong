package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ObjectCodec {
    public abstract TreeNode createArrayNode();

    public abstract TreeNode createObjectNode();

    public abstract JsonFactory getJsonFactory();

    public abstract <T extends TreeNode> T readTree(JsonParser jsonParser) throws IOException;

    public abstract <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException;

    public abstract <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException;

    public abstract <T> T readValue(JsonParser jsonParser, Class<T> cls) throws IOException;

    public abstract <T> Iterator<T> readValues(JsonParser jsonParser, ResolvedType resolvedType) throws IOException;

    public abstract <T> Iterator<T> readValues(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException;

    public abstract <T> Iterator<T> readValues(JsonParser jsonParser, Class<T> cls) throws IOException;

    public abstract JsonParser treeAsTokens(TreeNode treeNode);

    public abstract <T> T treeToValue(TreeNode treeNode, Class<T> cls) throws JsonProcessingException;

    public abstract void writeValue(JsonGenerator jsonGenerator, Object obj) throws IOException;

    protected ObjectCodec() {
    }
}
