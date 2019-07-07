package com.softwareplumbers.common.jsonview;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonString;


public class JsonViewFactory {
	public JsonString asJson(String obj) {
		return Json.createValue(obj);
	}
}