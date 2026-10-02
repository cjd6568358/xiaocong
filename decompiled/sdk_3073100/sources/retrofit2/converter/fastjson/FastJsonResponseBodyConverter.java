package retrofit2.converter.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Feature[] EMPTY_SERIALIZER_FEATURES = new Feature[0];
    private ParserConfig config;
    private int featureValues;
    private Feature[] features;
    private Type mType;

    FastJsonResponseBodyConverter(Type type, ParserConfig config, int featureValues, Feature... features) {
        this.mType = type;
        this.config = config;
        this.featureValues = featureValues;
        this.features = features;
    }

    @Override // retrofit2.Converter
    public T convert(ResponseBody responseBody) throws IOException {
        try {
            return (T) JSON.parseObject(responseBody.string(), this.mType, this.config, this.featureValues, this.features != null ? this.features : EMPTY_SERIALIZER_FEATURES);
        } finally {
            responseBody.close();
        }
    }
}
