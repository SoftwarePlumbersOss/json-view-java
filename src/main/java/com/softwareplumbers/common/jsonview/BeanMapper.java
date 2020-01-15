/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwareplumbers.common.jsonview;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** Per-class cache of data that supports for mapping a bean to a java map interface.
 *
 * @author jonathan.local
 */
class BeanMapper {

    private static final Map<Class,BeanMapper> mappers = Collections.synchronizedMap(new HashMap<Class,BeanMapper>());
    
    private final Map<String,Method> getters = new HashMap<String,Method>();
    
    public Object getValue(Object object, String name) {
        Method getter = getters.get(name);
        try {
            return (getter == null) ? null : getter.invoke(object) ;
        } catch (InvocationTargetException | IllegalAccessException e) {
            return null;
        }
    }
    
    public Set<String> keySet() {
        return getters.keySet();
    }
    
    public int size() {
        return getters.size();
    }
    
    private BeanMapper(Class clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getParameterCount() == 0 && method.getReturnType() != Void.TYPE) {
                String name = method.getName();
                if (!name.equals("getClass")) {
                    if (name.startsWith("get") && name.length() > 3) {
                        String propName = name.substring(3,4).toLowerCase() + name.substring(4);
                        getters.put(propName, method);
                    }
                }
            }
        }
    }
    
    public static BeanMapper getMapper(Class clazz) {
        return mappers.computeIfAbsent(clazz, c -> new BeanMapper(c));
    }
}
