import org.junit.Assert;
import org.vova.model.ListToMapWithAmountOfEachElementParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class collectionsTest {
    private static ListToMapWithAmountOfEachElementParser parser;

    @BeforeClass
    public static void setup() {
        parser = new ListToMapWithAmountOfEachElementParser();
    }

    //Passed
    @Test public void listToMapWithAmountOfEachElementJava8Test() {
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(2);
        numbers.add(1);
        numbers.add(-1);
        numbers.add(-1);
        numbers.add(-1);
        Map<Integer,Long> expected = new HashMap<Integer,Long>();
        expected.put(1, 2L);
        expected.put(2, 2L);
        expected.put(3, 1L);
        expected.put(-1, 3L);
        Map<Integer,Long> actual = parser.listToMapWithAmountOfEachElementJava8(numbers);
//        System.out.println(expected);
//        System.out.println(actual);
        Assert.assertEquals(expected,actual);
    }

    //Passed
    @Test public void listToMapWithAmountOfEachElementTest() {
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(2);
        numbers.add(1);
        numbers.add(-1);
        numbers.add(-1);
        numbers.add(-1);
        Map<Integer,Long> expected = new HashMap<Integer,Long>();
        expected.put(1, 2L);
        expected.put(2, 2L);
        expected.put(3, 1L);
        expected.put(-1, 3L);
        Map<Integer,Long> actual = parser.listToMapWithAmountOfEachElement(numbers);
//        System.out.println(expected);
//        System.out.println(actual);
        Assert.assertEquals(expected,actual);
    }
}
