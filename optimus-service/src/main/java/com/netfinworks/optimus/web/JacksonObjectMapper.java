package com.netfinworks.optimus.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.netfinworks.common.util.money.Money;

import java.io.IOException;

/**
 * spring 升级之后使用了不同的Jackson依赖包
 */
public class JacksonObjectMapper extends ObjectMapper {


    public JacksonObjectMapper() {

        enable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Money.class, new MoneyDeserializer());
        registerModule(module);
    }

    class MoneyDeserializer extends JsonDeserializer<Money> {

        @Override
        public Money deserialize(JsonParser jp, DeserializationContext ctxt)
                throws IOException, JsonParseException {
            JsonToken t = jp.getCurrentToken();
            if (t == JsonToken.VALUE_NUMBER_FLOAT) {
                return new Money(jp.getDecimalValue());
            }
            if (t == JsonToken.VALUE_STRING) {
                return new Money(jp.getText().trim());
            }
            if (t == JsonToken.START_OBJECT) {
                Money money = null;
                t = jp.nextToken();
                String name = null;
                while (true) {
                    if (t == JsonToken.FIELD_NAME) {
                        name = jp.getCurrentName();
                    }
                    if ("amount".equals(name)) {
                        jp.nextToken();
                        money = new Money(jp.getDecimalValue());
                        break;
                    } else {
                        t = jp.nextToken();
                    }
                }
                while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
                    jp.nextToken();
                }
                return money;
            }
            return null;
//            throw new IOException("Can not deserialize Money out of " + t);
        }

    }
}


