package math;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArithmeticOperationsTest {


    @Test(expected = ArithmeticException.class)
    public void divide_by_zero() {
        (new ArithmeticOperations()).divide(10,0);
    }

    @Test
    public void divide_with_zero() {
        double actual = (new ArithmeticOperations()).divide(0,4);
        assertEquals(0, actual, 1e-5);
    }

    @Test
    public void divide_normal() {
        double expected = 5.0;
        double actual = (new ArithmeticOperations()).divide(10,2);
        assertEquals(expected, actual, 1e-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void multiply_when_first_negative() {
        (new ArithmeticOperations()).multiply(-4,5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void multiply_when_second_negative() {
        (new ArithmeticOperations()).multiply(4,-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void multiply_both_negative() {
        (new ArithmeticOperations()).multiply(-4,-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void multiply_integer_overflow() {
        int x = Integer.MAX_VALUE / 2 + 1;
        int y = 2;
        (new ArithmeticOperations()).multiply(x, y);
    }

    @Test
    public void multiply_zero() {
        int actual = (new ArithmeticOperations()).multiply(4,0);
        assertEquals(0,actual,1e-5);
    }

    @Test
    public void multiply_normal() {
        int actual = (new ArithmeticOperations()).multiply(4,5);
        assertEquals(20,actual,1e-5);
    }
}