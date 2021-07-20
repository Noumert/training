package org.vova.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListToMapWithAmountOfEachElementParser {

    public Map<Integer, Long> listToMapWithAmountOfEachElementJava8(List<Integer> numbers) {
        return numbers.stream().collect(Collectors.groupingBy(integer -> integer, Collectors.counting()));
    }

    public Map<Integer, Long> listToMapWithAmountOfEachElement(List<Integer> numbers) {
        Map<Integer, Long> result = new HashMap<Integer, Long>();
        for (Integer integer : numbers) {
            if (result.containsKey(integer)) {
                result.replace(integer, result.get(integer) + 1);
            } else {
                result.put(integer, 1L);
            }
        }
        return result;
    }
}
