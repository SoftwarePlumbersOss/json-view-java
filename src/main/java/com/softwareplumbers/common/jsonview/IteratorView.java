/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwareplumbers.common.jsonview;

import java.util.Iterator;
import java.util.function.Function;

/** Convert an iterator from one type to another using a mapping function
 *
 * @author jonathan.local
 */
class IteratorView<U,T> implements Iterator<T> {
    
    private Iterator<U> base;
    private Function<U,T> map;
    
    public IteratorView(Iterator<U> base, Function<U,T> map) {
        this.base = base;
        this.map = map;
    }

    @Override
    public boolean hasNext() {
        return base.hasNext();
    }

    @Override
    public T next() {
        return map.apply(base.next());
    }
    
}
