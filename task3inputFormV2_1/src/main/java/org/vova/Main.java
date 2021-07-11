package org.vova;

import org.vova.controller.Controller;
import org.vova.model.Model;
import org.vova.view.View;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Controller controller = new Controller(new Model(), new View());
        // Run
        controller.processUser();
    }
}
