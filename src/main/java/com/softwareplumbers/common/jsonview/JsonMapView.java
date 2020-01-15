/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwareplumbers.common.jsonview;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/** Create a JsonObject view of a Java map.
 *
 * @author jonathan.local
 */
class JsonMapView implements JsonObject {
    
    private static class EntrySetView extends AbstractSet<Entry<String,JsonValue>> {
        
        private static class EntrySetIteratorView implements Iterator<Entry<String,JsonValue>> {
            
            private static class JsonEntry implements Entry<String, JsonValue> {
                
                private final Entry<String,?> base;
                
                public JsonEntry(Entry<String,?> base) {
                    this.base = base;
                }

                @Override
                public String getKey() {
                    return base.getKey();
                }

                @Override
                public JsonValue getValue() {
                    return JsonViewFactory.asJson(base.getValue());
                }

                @Override
                public JsonValue setValue(JsonValue value) {
                    throw new UnsupportedOperationException("Json views are read only"); 
                }
            }
            
            private final Iterator<Entry<String,?>> base;
            
            public EntrySetIteratorView(Iterator<Entry<String,?>> base) {
                this.base = base;
            }

            @Override
            public boolean hasNext() {
                return base.hasNext();
            }

            @Override
            public Entry<String, JsonValue> next() {
                return new JsonEntry(base.next());
            }
        
        }
        
        private final Set<Entry<String, ?>> base;
        
        public EntrySetView(Set<Entry<String,?>> base) {
            this.base = base;
        }
        
        @Override
        public Iterator<Entry<String, JsonValue>> iterator() {
            return new EntrySetIteratorView(base.iterator());
        }

        @Override
        public int size() {
            return base.size();
        }
        
    }
    
    private Map<String,?> base;
    
    public JsonMapView(Map<String,?> base) {
        this.base = base;
    }

    @Override
    public JsonArray getJsonArray(String key) {
        return (JsonArray)get(key);
    }

    @Override
    public JsonObject getJsonObject(String key) {
        return (JsonObject)get(key);
    }

    @Override
    public JsonNumber getJsonNumber(String key) {
        return (JsonNumber)get(key);
    }

    @Override
    public JsonString getJsonString(String key) {
        return (JsonString)get(key);
    }     

    @Override
    public String getString(String key) {
        return (String)base.get(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return (String)((Map)base).getOrDefault(key, defaultValue); // DFN why I need to cast to Map. Type erasure sucks.
    }

    @Override
    public int getInt(String key) {
        // NPE is what we are supposed to throw
        return ((Number)base.get(key)).intValue();
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return ((Number)((Map)base).getOrDefault(key, defaultValue)).intValue(); // DFN why I need to cast to Map. Type erasure sucks.
    }

    @Override
    public boolean getBoolean(String key) {
        return (Boolean)base.get(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return (Boolean)((Map)base).getOrDefault(key, defaultValue); // DFN why I need to cast to Map. Type erasure sucks.
    }

    @Override
    public boolean isNull(String key) {
        return base.computeIfAbsent(key, k->{ throw new NullPointerException("No mapping for " +k); }) == null;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.OBJECT;
    }

    @Override
    public int size() {
        return base.size();
    }

    @Override
    public boolean isEmpty() {
        return base.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return base.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains((JsonValue)value);
    }

    @Override
    public JsonValue get(Object key) {
        return JsonViewFactory.asJson(base.get(key));
    }

    @Override
    public JsonValue put(String key, JsonValue value) {
        throw new UnsupportedOperationException("Json views are read-only"); 
    }

    @Override
    public JsonValue remove(Object key) {
        throw new UnsupportedOperationException("Json views are read-only"); 
    }

    @Override
    public void putAll(Map<? extends String, ? extends JsonValue> m) {
        throw new UnsupportedOperationException("Json views are read-only"); 
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Json views are read-only"); 
    }

    @Override
    public Set<String> keySet() {
        return base.keySet();
    }

    @Override
    public Collection<JsonValue> values() {
        return new JsonCollectionView(base.values()); 
    }

    @Override
    public Set<Entry<String, JsonValue>> entrySet() {
        return new EntrySetView((Set)base.entrySet());
    }
    
    private void writeEntry(Writer writer, String key) throws IOException {
        writer.write("\"");
        writer.write(key);
        writer.write("\":");
        writer.write(get(key).toString());        
    }
    
    private void write(Writer writer) throws IOException {
        writer.write("{");
        Iterator<String> keys = keySet().iterator();
        if (keys.hasNext()) writeEntry(writer, keys.next());
        while (keys.hasNext()) {
            writer.write(", ");
            writeEntry(writer, keys.next());
        }
        writer.write("}");
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
