package org.vova;

import java.util.Arrays;
import java.util.List;

public class View {
    // Text's constants
    public static final String INPUT_INT_DATA = "Input INT: ";
    public static final String WRONG_INPUT_DATA = "Wrong input: not number! Repeat please! ";
    public static final String WRONG_RANGE_DATA = "Wrong input: out of range! Repeat please! ";
    public static final String RESULT = "You guess number: ";
    public static final String TRY_AGAIN = "Try again: ";
    public static final String OUT_HISTORY = "History: ";

    public void printMessage(String message){
        System.out.println(message);
    }

    public void printMessageAndResult(String message, int number){
        System.out.println(message + number);
    }

    public void printList(List<Integer> list){
        list.forEach(System.out::println);
    }
}
