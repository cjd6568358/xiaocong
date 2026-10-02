package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ArrayListTypeFieldDeserializer extends FieldDeserializer {
    private ObjectDeserializer deserializer;
    private int itemFastMatchToken;
    private final Type itemType;

    public ArrayListTypeFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo) {
        super(clazz, fieldInfo);
        Type fieldType = fieldInfo.fieldType;
        if (fieldType instanceof ParameterizedType) {
            Type argType = ((ParameterizedType) fieldInfo.fieldType).getActualTypeArguments()[0];
            if (argType instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) argType;
                Type[] upperBounds = wildcardType.getUpperBounds();
                if (upperBounds.length == 1) {
                    argType = upperBounds[0];
                }
            }
            this.itemType = argType;
            return;
        }
        this.itemType = Object.class;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public int getFastMatchToken() {
        return 14;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public void parseField(DefaultJSONParser parser, Object object, Type objectType, Map<String, Object> fieldValues) {
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token == 8 || (token == 4 && lexer.stringVal().length() == 0)) {
            setValue(object, (String) null);
            return;
        }
        ArrayList list = new ArrayList();
        ParseContext context = parser.getContext();
        parser.setContext(context, object, this.fieldInfo.name);
        parseArray(parser, objectType, list);
        parser.setContext(context);
        if (object == null) {
            fieldValues.put(this.fieldInfo.name, list);
        } else {
            setValue(object, list);
        }
    }

    public final void parseArray(DefaultJSONParser parser, Type objectType, Collection array) {
        Type itemType = this.itemType;
        ObjectDeserializer itemTypeDeser = this.deserializer;
        if (objectType instanceof ParameterizedType) {
            if (itemType instanceof TypeVariable) {
                TypeVariable typeVar = (TypeVariable) itemType;
                ParameterizedType paramType = (ParameterizedType) objectType;
                Class<?> objectClass = null;
                if (paramType.getRawType() instanceof Class) {
                    objectClass = (Class) paramType.getRawType();
                }
                int paramIndex = -1;
                if (objectClass != null) {
                    int size = objectClass.getTypeParameters().length;
                    for (int i = 0; i < size; i++) {
                        if (objectClass.getTypeParameters()[i].getName().equals(typeVar.getName())) {
                            paramIndex = i;
                            break;
                        }
                    }
                }
                if (paramIndex != -1) {
                    itemType = paramType.getActualTypeArguments()[paramIndex];
                    if (!itemType.equals(this.itemType)) {
                        itemTypeDeser = parser.getConfig().getDeserializer(itemType);
                    }
                }
            } else if (itemType instanceof ParameterizedType) {
                ParameterizedType parameterizedItemType = (ParameterizedType) itemType;
                Type[] itemActualTypeArgs = parameterizedItemType.getActualTypeArguments();
                if (itemActualTypeArgs.length == 1 && (itemActualTypeArgs[0] instanceof TypeVariable)) {
                    TypeVariable typeVar2 = (TypeVariable) itemActualTypeArgs[0];
                    ParameterizedType paramType2 = (ParameterizedType) objectType;
                    Class<?> objectClass2 = null;
                    if (paramType2.getRawType() instanceof Class) {
                        objectClass2 = (Class) paramType2.getRawType();
                    }
                    int paramIndex2 = -1;
                    if (objectClass2 != null) {
                        int size2 = objectClass2.getTypeParameters().length;
                        for (int i2 = 0; i2 < size2; i2++) {
                            if (objectClass2.getTypeParameters()[i2].getName().equals(typeVar2.getName())) {
                                paramIndex2 = i2;
                                break;
                            }
                        }
                    }
                    if (paramIndex2 != -1) {
                        itemActualTypeArgs[0] = paramType2.getActualTypeArguments()[paramIndex2];
                        itemType = new ParameterizedTypeImpl(itemActualTypeArgs, parameterizedItemType.getOwnerType(), parameterizedItemType.getRawType());
                    }
                }
            }
        }
        JSONLexer lexer = parser.lexer;
        int token = lexer.token();
        if (token == 14) {
            if (itemTypeDeser == null) {
                itemTypeDeser = parser.getConfig().getDeserializer(itemType);
                this.deserializer = itemTypeDeser;
                this.itemFastMatchToken = this.deserializer.getFastMatchToken();
            }
            lexer.nextToken(this.itemFastMatchToken);
            int i3 = 0;
            while (true) {
                if (lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (lexer.token() == 16) {
                        lexer.nextToken();
                    }
                }
                if (lexer.token() != 15) {
                    Object val = itemTypeDeser.deserialze(parser, itemType, Integer.valueOf(i3));
                    array.add(val);
                    parser.checkListResolve(array);
                    if (lexer.token() == 16) {
                        lexer.nextToken(this.itemFastMatchToken);
                    }
                    i3++;
                } else {
                    lexer.nextToken(16);
                    return;
                }
            }
        } else {
            if (itemTypeDeser == null) {
                itemTypeDeser = parser.getConfig().getDeserializer(itemType);
                this.deserializer = itemTypeDeser;
            }
            Object val2 = itemTypeDeser.deserialze(parser, itemType, 0);
            array.add(val2);
            parser.checkListResolve(array);
        }
    }
}
