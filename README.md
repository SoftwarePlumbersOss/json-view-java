# View Java Beans as JSON

Given a java Bean, create a dynamic Json view of that object. Updating the object
will automatically update the view.

Given the following sample class definitions:

```
class SimpleTestObject {
    int one = 1;
    String two = "B";
    double three = 3.0;

    int getOne() { return one; }
    String getTwo() { return two; }
    double getThree() { return three; }
}

class NestedTestObject {
    String outer = "A";
    SimpleTestObject inner = new SimpleTestObject();
    
    public String getOuter() { return outer; }
    public SimpleTestObject getInner() { return inner; }
}
```

The following code:

```
JsonViewFactory.asJson(new NestedTestObject())).toString();
```

Should return something like:

```
{ "inner": { "one":1, "two":"B", "three":3.0 }, "outer": "A" } 
```