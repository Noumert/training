package org.vova;

public class View {
    // Text's constants
    public static final String INPUT_HELLO_WORD = "Input first \"Hello\" then \"world!\": ";
    public static final String WRONG_INPUT_STRING = "Wrong input text! Repeat please! ";
    public static final String OUR_STRING = "Greeting: ";

    public void printMessage(String message){
        System.out.println(message);
    }

    public void printMessageAndResult(String message, String greeting){
        System.out.println(message + greeting);
    }

}
