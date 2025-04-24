package io;

import org.junit.Before;
import org.junit.Test;

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
}