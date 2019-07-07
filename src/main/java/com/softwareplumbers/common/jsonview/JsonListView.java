/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwareplumbers.common.jsonview;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Function;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 *
 * @author jonathan.local
 */
public class JsonListView<T extends JsonValue> extends AbstractList<T> {
    
    private List<?> base;
    private Function<Object,T> castFunction;

    @Override
    public T get(int index) {
        return castFunction.apply(base.get(index));
    }

    @Override
    public int size() {
        return base.size();
    }
    
    public JsonListView(Class<T> type, List<?> base) {
        this.base = base;
        
        if (JsonString.class.isAssignableFrom(type)) {
            castFunction = obj -> (T)Json.createValue(obj.toString());
        } else if (JsonNumber.class.isAssignableFrom(type)) {
            castFunction = obj -> (T)JsonViewFactory.asJson((Number)obj);            
        } else if (JsonArray.class.isAssignableFrom(type)) {
            castFunction = obj -> (T)JsonViewFactory.asJson((List)obj);
        } else if (JsonObject.class.isAssignableFrom(type)) {
            castFunction = obj -> (T)JsonViewFactory.asJson(obj);
        } else {
            throw new IllegalArgumentException("Can't convert to list of type:" + type);
        }        
    }
}
