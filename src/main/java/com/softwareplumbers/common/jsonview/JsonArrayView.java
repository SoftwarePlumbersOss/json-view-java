/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwareplumbers.common.jsonview;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/** Make a Java List look like a JsonArray.
 *
 * @author jonathan.local
 */
class JsonArrayView extends AbstractList<JsonValue> implements JsonArray {
    
    private final List<?> base;
    
    public JsonArrayView(List<?> base) {
        this.base = base;
    }
    
    @Override
    public JsonValue get(int index) {
        return JsonViewFactory.asJson(base.get(index));
    }

    @Override
    public int size() {
        return base.size();
    }

    @Override
    public JsonObject getJsonObject(int i) {
        return (JsonObject)get(i);
    }

    @Override
    public JsonArray getJsonArray(int i) {
        return (JsonArray)get(i);
    }

    @Override
    public JsonNumber getJsonNumber(int i) {
        return (JsonNumber)get(i);
    }

    @Override
    public JsonString getJsonString(int i) {
        return (JsonString)get(i);
    }

    @Override
    public <T extends JsonValue> List<T> getValuesAs(Class<T> type) {
        return new JsonListView<T>(type, base);
    }

    @Override
    public String getString(int i) {
        return (String)base.get(i);
    }

    @Override
    public String getString(int i, String defaultValue) {
        return (i < 0 || i >= size()) ? defaultValue : getString(i);
    }

    @Override
    public int getInt(int i) {
        return ((Number)base.get(i)).intValue();
    }

    @Override
    public int getInt(int i, int defaultValue) {
        return (i < 0 || i >= size()) ? defaultValue : getInt(i);
    }

    @Override
    public boolean getBoolean(int i) {
        return (Boolean)base.get(i);
    }

    @Override
    public boolean getBoolean(int i, boolean defaultValue) {
        return (i < 0 || i >= size()) ? defaultValue : getBoolean(i);
    }

    @Override
    public boolean isNull(int i) {
        return get(i) == null;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.ARRAY;
    }
        
    private void write(Writer writer) throws IOException {
        writer.write("[");
        Iterator<JsonValue> items = iterator();
        if (items.hasNext()) writer.write(items.next().toString());
        while (items.hasNext()) {
            writer.write(", ");
            writer.write(items.next().toString());
        }
        writer.write("]");
    }
    
    public String toString() {
        try (Writer writer = new StringWriter()) {
            write(writer);
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        
}
