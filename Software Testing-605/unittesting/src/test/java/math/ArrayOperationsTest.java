package math;

import io.FileIO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.File;

public class ArrayOperationsTest {
    FileIO fileIO;
    MyMath myMath;
    ArrayOperations arrayOperations;

    @Before
    public void setUp() {
        fileIO =  mock(FileIO.class);
        myMath = mock(MyMath.class);
        arrayOperations = new ArrayOperations();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindPrimesInFileNormal() {
        String filePath = "/home/tashrif/Downloads/unittesting/src/test/resources/grades_valid.txt";
        int[] inputArray = {3,9,2,10,9,3,8,3,0};
        when(fileIO.readFile(filePath)).thenReturn(inputArray);

        when(myMath.isPrime(3)).thenReturn(true);
        when(myMath.isPrime(9)).thenReturn(false);
        when(myMath.isPrime(2)).thenReturn(true);
        when(myMath.isPrime(10)).thenReturn(false);
        when(myMath.isPrime(9)).thenReturn(false);
        when(myMath.isPrime(3)).thenReturn(true);
        when(myMath.isPrime(8)).thenReturn(false);
        when(myMath.isPrime(3)).thenReturn(true);
        when(myMath.isPrime(0)).thenThrow(IllegalArgumentException.class);


        int[] result = arrayOperations.findPrimesInFile(fileIO,filePath,myMath);
        int[] expected = {3,2,3,3};
        assertArrayEquals(expected, result);
    }
}
