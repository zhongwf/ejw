package com.octopusthu.ejw.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangyu octopusthu@gmail.com
 */
public class JsonUtils {

	protected final static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}

	public static String objectToJsonString(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

	public static <T> T stringToObject(String str, Class<T> valueType) throws IOException {
		return mapper.readValue(str, valueType);
	}

	public static Map<?, ?> stringToMap(String str) throws IOException {
		return stringToObject(str, Map.class);
	}
}
