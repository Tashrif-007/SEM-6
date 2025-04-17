package math;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyMathTest {

    @Test
    public void isPrimeTrue() {
        assertEquals(true, (new MyMath()).isPrime(5));
    }

    @Test
    public void isPrimeFalse() {
        assertEquals(false, (new MyMath()).isPrime(6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isPrimeLessThanTwo() {
        (new MyMath()).isPrime(1);
    }

    @Test
    public void factorialNormal() {
        assertEquals(120, (new MyMath()).factorial(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void factorialOutBoundGreaterThanThirteen() {
        (new MyMath()).factorial(13);
    }

    @Test(expected = IllegalArgumentException.class)
    public void factorialOutBoundLessThanZero() {
        (new MyMath()).factorial(-2);
    }
}