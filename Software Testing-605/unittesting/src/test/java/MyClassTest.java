import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyClassTest {

    @org.junit.Test
    public void div() {
        float expected = 3.0F;
        float actual = (new MyClass()).div(10,0);
        assertEquals(expected, actual, 1e-5);
    }

    @org.junit.Test
    public void div_with_zero(){
        assertThrows(ArithmeticException.class, ()->{new MyClass().div(3,0);});
    }
}