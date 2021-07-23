package vova.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import vova.model.entry.NumberWithIndex;

import java.util.Arrays;

import static org.junit.Assert.*;

public class IntArrayParserTest {
    private static IntArrayParser intArrayParser;

    @BeforeClass
    public static void setup(){
        intArrayParser = new IntArrayParser();
    }

    @Test
    public void averageOfArrayTest() {
        int[] arr = {10,-5,1,12};
        int expected = 4;
        int actual = intArrayParser.averageOfArray(arr);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void minValueAndIndexOfArrayTest() {
        int[] arr = {10,-5,1,12};
        NumberWithIndex expected = new NumberWithIndex(-5,1);
        NumberWithIndex actual = intArrayParser.minValueAndIndexOfArray(arr);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void NumberOfZerosInArrayArrayTest() {
        int[] arr = {0,10,-5,0,1,0,12,0};
        int expected = 4;
        int actual = intArrayParser.NumberOfZerosInArray(arr);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void NumberOfMoreThanZerosInArrayArrayTest() {
        int[] arr = {3,10,-5,0,1,0,12,0,8};
        int expected = 5;
        int actual = intArrayParser.NumberOfMoreThanZerosInArray(arr);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void MultArrayOnNumberTest() {
        int k = 2;
        int[] arr = {3,10,-5,3};
        int[] expected = {6,20,-10,6};
        int[] actual = intArrayParser.MultArrayOnNumber(arr,2);
        Assert.assertArrayEquals(expected,actual);
    }
}