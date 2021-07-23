package vova.model;

import vova.model.entry.NumberWithIndex;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntArrayParser {

    public int averageOfArray(int[] numbers) {
        return (int) Arrays.stream(numbers).average().orElseThrow(IllegalArgumentException::new);
    }

    public NumberWithIndex minValueAndIndexOfArray(int[] numbers) {
        return IntStream.range(0, numbers.length)
                .mapToObj(num -> new NumberWithIndex(numbers[num], num))
                .min(Comparator.comparingInt(NumberWithIndex::getNumber)).orElseThrow(IllegalArgumentException::new);
    }

    public int NumberOfZerosInArray(int[] numbers) {
        return (int) Arrays.stream(numbers).filter(num -> num == 0).count();
    }

    public int NumberOfMoreThanZerosInArray(int[] numbers) {
        return (int) Arrays.stream(numbers).filter(num -> num > 0).count();
    }

    public int[] MultArrayOnNumber(int[] numbers, int k) {
        return Arrays.stream(numbers).map(num -> num*k).toArray();
    }
}
