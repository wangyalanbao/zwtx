/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.util;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utils - JSON
 * 
 * 
 * 
 */
public final class JsonUtils {

	/** ObjectMapper */
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 不可实例化
	 */
	private JsonUtils() {
	}

	/**
	 * 将对象转换为JSON字符串
	 * 
	 * @param value
	 *            对象
	 * @return JSOn字符串
	 */
	public static String toJson(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param valueType
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		Assert.hasText(json);
		Assert.notNull(valueType);
		try {
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param typeReference
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, TypeReference<?> typeReference) {
		Assert.hasText(json);
		Assert.notNull(typeReference);
		try {
			return mapper.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param javaType
	 *            对象类型
	 * @return 对象
	 */
	public static <T> T toObject(String json, JavaType javaType) {
		Assert.hasText(json);
		Assert.notNull(javaType);
		try {
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将对象转换为JSON流
	 * 
	 * @param writer
	 *            writer
	 * @param value
	 *            对象
	 */
	public static void writeValue(Writer writer, Object value) {
		try {
			mapper.writeValue(writer, value);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将传递进来的json数组转换为List集合
	 *
	 * @param jsonArr
	 * @return
	 * @throws JSONException
	 */
	public static List<?> jsonToList(JSONArray jsonArr)
			throws Exception {
		List<Object> jsonToMapList = new ArrayList<Object>();
		for (int i = 0; i < jsonArr.length(); i++) {
			Object object = jsonArr.get(i);
			if (object instanceof JSONArray) {
				jsonToMapList.add(JsonUtils.jsonToList((JSONArray) object));
			} else if (object instanceof JSONObject) {
				jsonToMapList.add(JsonUtils.jsonToMap((JSONObject) object));
			} else {
				jsonToMapList.add(object);
			}
		}
		return jsonToMapList;
	}
	/**
	 * 将传递进来的json对象转换为Map集合
	 *
	 * @param jsonObj
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(JSONObject jsonObj)
			throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Iterator<String> jsonKeys = jsonObj.keys();
		while (jsonKeys.hasNext()) {
			String jsonKey = jsonKeys.next();
			Object jsonValObj = jsonObj.get(jsonKey);
			if (jsonValObj instanceof JSONArray) {
				jsonMap.put(jsonKey, JsonUtils.jsonToList((JSONArray) jsonValObj));
			} else if (jsonValObj instanceof JSONObject) {
				jsonMap.put(jsonKey, JsonUtils.jsonToMap((JSONObject) jsonValObj));
			} else {
				jsonMap.put(jsonKey, jsonValObj);
			}
		}
		return jsonMap;
	}

}