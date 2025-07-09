package io;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class FileIOTest {
    public FileIO fileIO;

    @Before
    public void setup() {
        fileIO = new FileIO();
    }
    @Test
    public void readFileNormal() {
        int[] expected = {3,9,2,0,10,9,3,8,0,3};
        int[] actual = fileIO.readFile("/home/tashrif/Desktop/SEM-6/Software Testing-605/unittesting/src/test/resources/grades_valid.txt");
        assertArrayEquals(expected,actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readFileEmpty() {
        int[] expected = {};
        int[] actual = fileIO.readFile("/home/tashrif/Downloads/unittesting/src/test/resources/empty_file.txt");
        assertArrayEquals(expected,actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readFileDoesNotExist() {
        int[] actual = fileIO.readFile("/home/tashrif/Downloads/unittesting/src/test/resources/empty_files.txt");
    }

    @Test
    public void test_for_invalid_entry() {
        int[] expected = {3,9,2,10,8,0,3};
        int[] actual = fileIO.readFile("/home/tashrif/Desktop/SEM-6/Software Testing-605/unittesting/src/test/resources/grades_invalid.txt");
        assertArrayEquals(expected, actual);
    }
    @Test(expected = IllegalArgumentException.class)
    public void readFileDirectoryInsteadOfFile() {
        fileIO.readFile("/home/tashrif/Desktop/SEM-6/Software Testing-605/unittesting/src/test/resources");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIOExceptionPath() {
        // Test a scenario that will trigger IOException in the catch block on line 50
        // This ensures the IOException catch block (including e.printStackTrace()) is executed
        // The method should catch IOException, print stack trace, then throw IllegalArgumentException
        // because the numbersList will be empty after IOException
        fileIO.readFile("/proc/1/mem"); // This will cause IOException during reading
    }
    @Test
    public void testIOExceptionHandling() {
        File dir = new File("src/test/resources/io_exception_dir");
        if (!dir.exists()) {
            assertTrue(dir.mkdirs());  // create as a directory
        }
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        try {
            fileIO.readFile(dir.getAbsolutePath());
        } catch (Exception ignored) {
        } finally {
            System.setErr(originalErr);
            assertTrue(dir.delete());
        }
        String output = errContent.toString();
        assertTrue("Should contain IOException in stack trace", output.contains("IOException"));
    }
}