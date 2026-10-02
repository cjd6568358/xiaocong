package retrofit2.converter.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class FastJsonConverterFactory extends Converter.Factory {
    private Feature[] features;
    private SerializeConfig serializeConfig;
    private SerializerFeature[] serializerFeatures;
    private ParserConfig mParserConfig = ParserConfig.getGlobalInstance();
    private int featureValues = JSON.DEFAULT_PARSER_FEATURE;

    public static FastJsonConverterFactory create() {
        return new FastJsonConverterFactory();
    }

    private FastJsonConverterFactory() {
    }

    @Override // retrofit2.Converter.Factory
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastJsonResponseBodyConverter(type, this.mParserConfig, this.featureValues, this.features);
    }

    @Override // retrofit2.Converter.Factory
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastJsonRequestBodyConverter(this.serializeConfig, this.serializerFeatures);
    }
}
