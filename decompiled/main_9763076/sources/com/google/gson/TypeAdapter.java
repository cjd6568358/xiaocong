package com.google.gson;

import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class TypeAdapter<T> {
    /* JADX INFO: renamed from: read */
    public abstract T read2(JsonReader jsonReader) throws IOException;

    public abstract void write(JsonWriter jsonWriter, T t) throws IOException;

    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>() { // from class: com.google.gson.TypeAdapter.1
            @Override // com.google.gson.TypeAdapter
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    TypeAdapter.this.write(out, value);
                }
            }

            @Override // com.google.gson.TypeAdapter
            /* JADX INFO: renamed from: read */
            public T read2(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() != JsonToken.NULL) {
                    return (T) TypeAdapter.this.read2(jsonReader);
                }
                jsonReader.nextNull();
                return null;
            }
        };
    }

    public final JsonElement toJsonTree(T value) {
        try {
            JsonTreeWriter jsonWriter = new JsonTreeWriter();
            write(jsonWriter, value);
            return jsonWriter.get();
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }
}
