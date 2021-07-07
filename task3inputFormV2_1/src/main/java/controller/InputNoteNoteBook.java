package controller;


import view.View;

import java.util.Scanner;

import static controller.RegexContainer.REGEX_LOGIN;
import static controller.RegexContainer.REGEX_NAME;
import static view.TextConstant.FIRST_NAME;
import static view.TextConstant.LOGIN_DATA;

/**
 * Created by student on 26.09.2017.
 */
public class InputNoteNoteBook {
    private View view;
    private Scanner sc;

    private String firstName;
    private String login;

    public InputNoteNoteBook(View view, Scanner sc) {
        this.view = view;
        this.sc = sc;
    }

    public void inputNote() {
        UtilityController utilityController =
                new UtilityController(sc, view);
        String regexName = View.bundle.getString(REGEX_NAME);
        String regexLogin = View.bundle.getString(REGEX_LOGIN);

        this.firstName =
                utilityController.inputStringValueWithScanner
                        (FIRST_NAME, regexName);
        this.login =
                utilityController.inputStringValueWithScanner
                        (LOGIN_DATA, regexLogin);
    }
}
