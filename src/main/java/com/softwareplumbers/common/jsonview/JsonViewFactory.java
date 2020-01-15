package com.softwareplumbers.common.jsonview;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonNumber;
import javax.json.JsonString;

/** Create dynamic Json views of complex object types.
 * 
 * @author jonathan
 */
public class JsonViewFactory {
    
    
	public static JsonString asJson(String obj) {
		return new JsonString() {
            @Override public String getString() { return obj; }
            @Override public CharSequence getChars() { return obj; }
            @Override public ValueType getValueType() { return ValueType.STRING; } 
        };
	}
    
    public static JsonNumber asJson(int number) {
        return new JsonNumber() {
            @Override public JsonValue.ValueType getValueType() { return JsonValue.ValueType.NUMBER; } 
            @Override public boolean isIntegral() { return true; }
            @Override public int intValue() { return number; }
            @Override public int intValueExact() { return number; }
            @Override public long longValue() { return (long)number; }
            @Override public long longValueExact() { return (long) number; }
            @Override public BigInteger bigIntegerValue() { return BigInteger.valueOf(number); }
            @Override public BigInteger bigIntegerValueExact() { return BigInteger.valueOf(number); }
            @Override public double doubleValue() { return (double)number; }
            @Override public BigDecimal bigDecimalValue() { return BigDecimal.valueOf(number); }
        };
    }
    
    static int toIntExact(long number) throws ArithmeticException {
        if (number < Integer.MIN_VALUE || number > Integer.MAX_VALUE) throw new ArithmeticException(number + "cannot be exactly converted to an int");
        return (int)number;
    }

    public static JsonNumber asJson(long number) {
        return new JsonNumber() {
            @Override public JsonValue.ValueType getValueType() { return JsonValue.ValueType.NUMBER; } 
            @Override public boolean isIntegral() { return true; }
            @Override public int intValue() { return (int)number; }
            @Override public int intValueExact() { throw new ArithmeticException(number + "cannot be exactly converted to an int"); }
            @Override public long longValue() { return number; }
            @Override public long longValueExact() { return number; }
            @Override public BigInteger bigIntegerValue() { return BigInteger.valueOf(number); }
            @Override public BigInteger bigIntegerValueExact() { return BigInteger.valueOf(number); }
            @Override public double doubleValue() { return (double)number; }
            @Override public BigDecimal bigDecimalValue() { return BigDecimal.valueOf(number); }
        };
    }

    public static JsonNumber asJson(double number) {
        return new JsonNumber() {
            @Override public JsonValue.ValueType getValueType() { return JsonValue.ValueType.NUMBER; } 
            @Override public boolean isIntegral() { return true; }
            @Override public int intValue() { return (int)number; }
            @Override public int intValueExact() { throw new ArithmeticException(number + "cannot be exactly converted to an int"); }
            @Override public long longValue() { return (long)number; }
            @Override public long longValueExact() { throw new ArithmeticException(number + "cannot be exactly converted to an long"); }
            @Override public BigInteger bigIntegerValue() { return BigInteger.valueOf((long)number); }
            @Override public BigInteger bigIntegerValueExact() { throw new ArithmeticException(number + "cannot be exactly converted to a BigInteger"); }
            @Override public double doubleValue() { return number; }
            @Override public BigDecimal bigDecimalValue() { return BigDecimal.valueOf(number); }
        };
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
    
    /** Convert any Java object to a dynamic Json view
     * 
     * @param obj Object to convert
     * @return json view of the object
     */
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