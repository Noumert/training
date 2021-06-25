package org.vova;

public class Model {
    private String greeting;

    // The Program logic

    /**
     * in this method add greetingPart with this.greeting
     * @param greetingPart
     * @return sum
     */
    public String addPartToGreeting(String greetingPart){
        return greeting += (" " + greetingPart);
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
