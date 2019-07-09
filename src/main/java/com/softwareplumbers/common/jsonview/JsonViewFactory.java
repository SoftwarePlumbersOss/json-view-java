package com.softwareplumbers.common.jsonview;

import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonNumber;
import javax.json.JsonString;

public class JsonViewFactory {
    
	public static JsonString asJson(String obj) {
		return Json.createValue(obj);
	}
    
    public static JsonNumber asJson(int number) {
        return Json.createValue(number);
    }

    public static JsonNumber asJson(long number) {
        return Json.createValue(number);
    }

    public static JsonNumber asJson(double number) {
        return Json.createValue(number);
    }
    
    public static JsonNumber asJson(Number number) {
        if (number instanceof Integer) return asJson(number.intValue());
        if (number instanceof Long) return asJson(number.longValue());
        if (number instanceof Byte) return asJson(number.intValue());
        if (number instanceof Short) return asJson(number.intValue());
        return asJson(number.doubleValue());
    }

    public static JsonArray asJson(List<?> items) {
        return new JsonArrayView(items);
    }
    
    public static JsonObject asJson(Map<String,?> items) {
        return new JsonMapView(items);
    }
    
    public static JsonObject asJsonObject(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Map) return asJson((Map<String,?>)obj);
        return new JsonMapView(new BeanMap(obj));
    }
    
    public static JsonValue asJson(Object obj) {
        if (obj == null) return JsonValue.NULL;
        if (obj instanceof JsonValue) return (JsonValue)obj;
        if (obj instanceof String) return asJson((String)obj);
        if (obj instanceof Number) return asJson((Number)obj);
        if (obj instanceof List) return asJson((List<?>)obj);
        if (obj instanceof Map) return asJson((Map<String,?>)obj);
        return new JsonMapView(new BeanMap(obj));
    }
}