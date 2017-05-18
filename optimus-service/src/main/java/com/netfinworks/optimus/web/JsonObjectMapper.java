package com.netfinworks.optimus.web;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.CustomDeserializerFactory;
import org.codehaus.jackson.map.deser.StdDeserializerProvider;

import com.netfinworks.common.util.money.Money;

@SuppressWarnings("deprecation")
public class JsonObjectMapper extends ObjectMapper {

	public JsonObjectMapper() {
		// 忽略 前端js对象的属性 序列化成 java对象没有对应的get set 方法 映射错误
		configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);

		CustomDeserializerFactory factory = new CustomDeserializerFactory();
		factory.addSpecificMapping(Money.class, new MoneyDeserializer());
		setDeserializerProvider(new StdDeserializerProvider(factory));
	}
}

class MoneyDeserializer extends JsonDeserializer<Money> {

	@Override
	public Money deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonToken t = jp.getCurrentToken();
		if (t == JsonToken.VALUE_NUMBER_FLOAT) {
			return new Money(jp.getDecimalValue());
		}
		if (t == JsonToken.VALUE_STRING) {
			return new Money(jp.getText().trim());
		}
		throw new IOException("Can not deserialize Money out of " + t);
	}

}
