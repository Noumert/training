package org.vova;
import controller.Controller;
import model.Model;
import view.View;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Controller controller = new Controller(new Model(), new View());
        // Run
        controller.processUser();
    }
}
