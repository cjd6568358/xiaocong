package retrofit2.converter.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private SerializeConfig serializeConfig;
    private SerializerFeature[] serializerFeatures;

    FastJsonRequestBodyConverter(SerializeConfig config, SerializerFeature... features) {
        this.serializeConfig = config;
        this.serializerFeatures = features;
    }

    @Override // retrofit2.Converter
    public RequestBody convert(T value) throws IOException {
        byte[] content;
        if (this.serializeConfig != null) {
            if (this.serializerFeatures != null) {
                content = JSON.toJSONBytes(value, this.serializeConfig, this.serializerFeatures);
            } else {
                content = JSON.toJSONBytes(value, this.serializeConfig, new SerializerFeature[0]);
            }
        } else if (this.serializerFeatures != null) {
            content = JSON.toJSONBytes(value, this.serializerFeatures);
        } else {
            content = JSON.toJSONBytes(value, new SerializerFeature[0]);
        }
        return RequestBody.create(MEDIA_TYPE, content);
    }
}
