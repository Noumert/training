package org.vova;

import java.util.Scanner;

public class Controller {
    // The Constants

    // Constructor
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    // The Work method
    public void processUser() {
        Scanner sc = new Scanner(System.in);
        char result = '1';
        while (result != '0') {
            view.printMessage(View.OUT_HISTORY);
            view.printList(model.getHistory());
            int currentNumber = inputIntValueWithScannerAndDiapason(sc);
            view.printMessage("");
            result = model.CheckNumber(currentNumber);
            switch (result) {
                case '>':
                    view.printMessage("More " + View.TRY_AGAIN);
                    model.setMinBarrier(currentNumber);
                    break;
                case '<':
                    view.printMessage("Less " + View.TRY_AGAIN);
                    model.setMaxBarrier(currentNumber);
                    break;
                case '0':
                    view.printMessage(View.OUT_HISTORY);
                    view.printList(model.getHistory());
                    view.printMessageAndResult(View.RESULT, currentNumber);
                    break;
                default:
                    break;
            }
        }
    }

    // The Utility methods
    public int inputIntValueWithScannerAndDiapason(Scanner sc) {
        int res = 0;
        view.printMessage(View.INPUT_INT_DATA +
                model.getMinBarrier() + "-" + model.getMaxBarrier());

        while (true) {
            // check int - value
            while (!sc.hasNextInt()) {
                view.printMessage(View.WRONG_INPUT_DATA);
                sc.next();
            }
            // check value in diapason
            if ((res = sc.nextInt()) <= model.getMinBarrier() ||
                    res >= model.getMaxBarrier()) {
                view.printMessage(View.WRONG_RANGE_DATA);
                continue;
            }
            break;
        }
        return res;
    }
}
