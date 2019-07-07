/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softwareplumbers.common.jsonview;

import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author jonathan.local
 */
public class TestJsonViewFactory {
    
    @Test
    public void testConvertString() {
        assertEquals(Json.createValue("hello"), JsonViewFactory.asJson("hello"));
    }

    @Test
    public void testConvertNumbers() {
        assertEquals(Json.createValue(123), JsonViewFactory.asJson(123));
        assertEquals(Json.createValue(123.0), JsonViewFactory.asJson(123.0));
        assertEquals(Json.createValue(123L), JsonViewFactory.asJson(123L));
    }

    @Test
    public void testConvertList() {
        JsonArray testArray = Json.createArrayBuilder().add("one").add("two").add("three").build();
        assertEquals(testArray, JsonViewFactory.asJson(Arrays.asList("one","two","three")));
    }

    private static class SimpleTestObject {
        public int one = 1;
        public String two = "B";
        public double three = 3.0;
        public int getOne() { return one; }
        public String getTwo() { return two; }
        public double getThree() { return three; }
    }
    
    @Test
    public void testConvertSimpleTestObject() {
        JsonObject testObject = Json.createObjectBuilder().add("one",1).add("two","B").add("three",3.0).build();
        assertEquals(testObject, JsonViewFactory.asJson(new SimpleTestObject()));
    }
    
    private static class NestedTestObject {
        public String outer = "A";
        public SimpleTestObject inner = new SimpleTestObject();
        public String getOuter() { return outer; }
        public SimpleTestObject getInner() { return inner; }
    }

    @Test
    public void testConvertNestedTestObject() {
        JsonObject testObject = Json.createObjectBuilder()
            .add("outer", "A")
            .add("inner", Json.createObjectBuilder().add("one",1).add("two","B").add("three",3.0))
            .build();
        assertEquals(testObject, JsonViewFactory.asJson(new NestedTestObject()));
    }
}
