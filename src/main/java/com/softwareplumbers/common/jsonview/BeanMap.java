/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwareplumbers.common.jsonview;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jonathan.local
 */
public class BeanMap implements Map<String,Object> {
    
    private final Object bean;
    private final BeanMapper mapper;

    public BeanMap(Object bean) {
        this.bean = bean;
        this.mapper = BeanMapper.getMapper(bean.getClass());
    }
    
    @Override
    public int size() {
        return mapper.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return mapper.keySet().contains((String)key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public Object get(Object key) {
        return mapper.getValue(bean, (String)key);
    }

    @Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Set<String> keySet() {
        return mapper.keySet();
    }

    @Override
    public Collection<Object> values() {
        return new AbstractCollection<Object>() {
            @Override
            public Iterator<Object> iterator() {
                return new IteratorView(mapper.keySet().iterator(), key->mapper.getValue(bean, (String)key));
            }

            @Override
            public int size() {
                return mapper.size();
            }
        
        };
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return new AbstractSet<Entry<String,Object>>() {
            @Override
            public Iterator<Entry<String,Object>> iterator() {
                return new IteratorView(mapper.keySet().iterator(), key-> new AbstractMap.SimpleImmutableEntry(key, mapper.getValue(bean, (String)key)));
            }

            @Override
            public int size() {
                return mapper.size();
            }
        
        };
    }
}
