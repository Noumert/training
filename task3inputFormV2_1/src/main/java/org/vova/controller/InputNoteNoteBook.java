package org.vova.controller;


import org.vova.view.View;

import java.util.Scanner;

import static org.vova.controller.RegexContainer.REGEX_LOGIN;
import static org.vova.controller.RegexContainer.REGEX_NAME;
import static org.vova.view.TextConstant.FIRST_NAME;
import static org.vova.view.TextConstant.LOGIN_DATA;

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

    public void inputLogin() {
        UtilityController utilityController =
                new UtilityController(sc, view);
        String regexLogin = View.bundle.getString(REGEX_LOGIN);

        this.login =
                utilityController.inputStringValueWithScanner
                        (LOGIN_DATA, regexLogin);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLogin() {
        return login;
    }
}
