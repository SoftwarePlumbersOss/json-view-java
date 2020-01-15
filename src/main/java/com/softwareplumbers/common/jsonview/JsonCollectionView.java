/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwareplumbers.common.jsonview;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import javax.json.JsonValue;

/** View a collection of beans as a collection of JsonValue objects
 *
 * @author jonathan.local
 */
class JsonCollectionView extends AbstractCollection<JsonValue> {
    
    private Collection<?> base;
    
    public JsonCollectionView(Collection<?> base) {
        this.base = base;
    }

    @Override
    public Iterator<JsonValue> iterator() {
        return new IteratorView<Object,JsonValue>((Iterator)base.iterator(), JsonViewFactory::asJson);
    }

    @Override
    public int size() {
        return base.size();
    }
    
}
